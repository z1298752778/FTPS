package com.leateck.commons.materiaInventoryImport;

import com.datasweep.compatibility.client.*;
import com.datasweep.compatibility.ui.Time;
import com.leateck.commons.materiaInventoryImport.entity.*;
import com.rockwell.mes.commons.base.ifc.exceptions.MESIncompatibleUoMException;
import com.rockwell.mes.commons.base.ifc.functional.MeasuredValueUtilities;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.services.inventory.ifc.CreateSublotInfo;
import com.rockwell.mes.services.inventory.ifc.ISublotService;
import com.rockwell.mes.services.inventory.ifc.TransactionHistoryContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * XXX
 *
 * @author litao
 * @version 1.01.1
 * @since 2023/3/27
 */
public class InventoryCreation {
    // 创建库存
    public static List<InventoryQuerySublots> inventoryCreateSublot(List<InventoryQueryTable> inventoryQueryTables,String locationString) throws DatasweepException {
        ArrayList<InventoryQuerySublots> querySublots = new ArrayList<>();
        if (!Objects.isNull(inventoryQueryTables)){
            for (int i = 0; i < inventoryQueryTables.size(); i++) {
                InventoryQueryTable queryTable = inventoryQueryTables.get(i);

                String sublotNum = queryTable.getSublotNum();
                // 入厂编号
                String inventoryQty = queryTable.getInventoryQty();
                // 规格
                String specifications = queryTable.getSpecifications();
                // 厂商
                String uom = queryTable.getUom();

                String locations = locationString.replaceAll("[\\[\\]\"\\\\]", "");
                Part part = PCContext.getFunctions().getPart(queryTable.getMaterialNo());
                // 此处创建子批次
                Location location = PCContext.getFunctions().getLocation(locations);
                //子批次信息(批次,每个子批次数量,存储位置)
                MeasuredValue takeSampleQtyMV = getQtyMV(queryTable.getPackagingSpecifications(), queryTable.getUnit());
                String batchName = queryTable.getBatchNo() + "   " + queryTable.getMaterialNo();
                Batch batch = PCContext.getFunctions().getBatchByName(batchName);
                if (Objects.isNull(batch)){
                    batch = PCContext.getFunctions().createBatch(batchName, part, PCContext.getCurrentServerTime());
                    batch.setUDA(inventoryQty,"CD_EntryNumber");
                    batch.setUDA(specifications,"CD_Spec");
                    batch.setUDA(uom,"CD_SupplierName");
                    Time time = PCContext.getFunctions().createTime(queryTable.getValidityData());
                    batch.setExpirationTime(time);
                    batch.save();
                }

//                Batch batch = PCContext.getFunctions().getBatchByName(queryTable.getBatchNo());
                CreateSublotInfo sublotInfo = new CreateSublotInfo(batch, takeSampleQtyMV, location);
                //子批次个数
                sublotInfo.numberOfSublots(Integer.parseInt(sublotNum));
                // 事务处理
                TransactionHistoryContext transactionHistoryContext = new TransactionHistoryContext();
                transactionHistoryContext.setRemark("导入子批次备注");
                //创建子批次(根据个数创建子批次数组)
                Sublot[] sublots;
                try {
                    sublots = ServiceFactory.getService(ISublotService.class).createSublots(sublotInfo, transactionHistoryContext);
                    // 做更改
                    MESCDMateriaInventoryImportFilter importFilter = new MESCDMateriaInventoryImportFilter();
                    List<IMESCDMateriaInventoryImport> filteredObjects = importFilter.forBatchNoEqualTo(queryTable.getBatchNo())
                            .forMaterialNoEqualTo(queryTable.getMaterialNo()).getFilteredObjects();
                    boolean b = true;
                    for (int j = 0; j < filteredObjects.size(); j++) {
                        IMESCDMateriaInventoryImport anImport = filteredObjects.get(j);
                        if (anImport.getTip().equals("未接收")&& b ){
                            anImport.setTip("已接收");
                            anImport.save();
                            b = false;
                        }

                    }

                } catch (MESIncompatibleUoMException e) {
                    throw new RuntimeException(e);
                } catch (DatasweepException e) {
                    throw new RuntimeException(e);
                }
                // 数据保存
                if (!Objects.isNull(sublots)){
                    for (int k = 0; k < sublots.length; k++) {
                        // 保存UDA字段
                        sublots[k].setUDA(inventoryQty,"CD_EntryNumber");
                        sublots[k].setUDA(specifications,"CD_Spec");
                        sublots[k].setUDA(uom,"CD_SupplierName");
                        sublots[k].save();
                    }
                }

                if (!Objects.isNull(sublots)){
                    for (int j = 0; j < sublots.length; j++) {
                        // 此处返回创建的子批次
                        InventoryQuerySublots inventoryQuerySublots = new InventoryQuerySublots();
                        inventoryQuerySublots.setMaterialNo(queryTable.getMaterialNo());
                        inventoryQuerySublots.setMaterialDesc(queryTable.getMaterialDesc());
                        inventoryQuerySublots.setBatchNo(batchName);
                        inventoryQuerySublots.setInventoryQty(queryTable.getInventoryQty());
                        inventoryQuerySublots.setUom(queryTable.getUom());
                        inventoryQuerySublots.setSpecifications(queryTable.getSpecifications());
                        inventoryQuerySublots.setQuantity(queryTable.getPackagingSpecifications());
                        inventoryQuerySublots.setUnit(queryTable.getUnit());
                        inventoryQuerySublots.setValidityData(queryTable.getValidityData());
                        inventoryQuerySublots.setSublotNumber(sublots[j].getName());

                        inventoryQuerySublots.setImportNo(queryTable.getImportNo());
                        //
                        querySublots.add(inventoryQuerySublots);
                    }

                }
                // 此处保存子批次

            }
        }
        return querySublots;
    }
    public static List<Sublot> returnCreateSublot(List<InventoryQuerySublots> inventoryCreateSublots){
        ArrayList<Sublot> sublots = new ArrayList<>();
        for (int i = 0; i < inventoryCreateSublots.size(); i++) {
            InventoryQuerySublots inventoryQuerySublots = inventoryCreateSublots.get(i);
            Sublot sublotByName = PCContext.getFunctions().getSublotByName(inventoryQuerySublots.getBatchNo(), inventoryQuerySublots.getSublotNumber());
            sublots.add(sublotByName);
        }
        return sublots;
    }
    // 打印
    public static void printSublots(List<Sublot> sublots){
        for (int i = 0; i < sublots.size(); i++) {
            Sublot sublot = sublots.get(i);
            Object[] fieldData = new Object[]{sublot, sublot.getBatch(), sublot.getBatch().getName(), sublot.getPart(),sublot.getKey(),sublot.getBatch().getKey()};
            PCContext.getFunctions().subroutine("CD_InventorySublot_Print", fieldData);
        }

    }
    // 重打印
    public static Report REprintSublots(List<InventoryQuerySublots> sublots){
        Report report = null;
        if (!Objects.isNull(sublots) && sublots.size() > 0 ){
            ReportFilter reportFilter = PCContext.getFunctions().createReportFilter();
            Sublot sublotByName = PCContext.getFunctions().getSublotByName(sublots.get(0).getBatchNo(), sublots.get(0).getSublotNumber());
            reportFilter.forUdaEqualTo("X_sublot", sublotByName.getKey());
            List<Report> filteredReports = PCContext.getFunctions().getFilteredReports(reportFilter);
            report = filteredReports.get(0);
        }


        return report;
    }
    /**
     * 字符串值转MeasuredValue
     * @param strQty    带单位数字加单位字符串
     * @param unitOfMeasureStr 字符串类型单位
     * @return
     */
    public static MeasuredValue getQtyMV(String strQty, String unitOfMeasureStr) {
        MeasuredValue qtyMV = null;
        try {
            qtyMV = MeasuredValueUtilities.createMV(new BigDecimal(strQty), unitOfMeasureStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qtyMV;
    }
}
