package com.leateck.commons.materiaInventoryImport;

import com.datasweep.compatibility.client.DatasweepException;
import com.google.common.base.Strings;
import com.leateck.commons.materiaInventoryImport.entity.IMESCDMateriaInventoryImport;
import com.leateck.commons.materiaInventoryImport.entity.InventoryQueryTable;
import com.leateck.commons.materiaInventoryImport.entity.MESCDMateriaInventoryImportFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * XXX
 *
 * @author litao
 * @version 1.01.1
 * @since 2023/3/27
 */
public class InventoryQuery {
    // 查询库存
    public static List<InventoryQueryTable> inventoryQuery(String materialNo,String inventoryQty,String batchNo,String tip){
        MESCDMateriaInventoryImportFilter importFilter = new MESCDMateriaInventoryImportFilter();

        if (!Strings.isNullOrEmpty(materialNo)){
            try {
//                importFilter.forMaterialNoEqualTo(materialNo);
                importFilter.forMaterialNoContaining(materialNo);
            } catch (DatasweepException e) {
                throw new RuntimeException(e);
            }
        }
        if (!Strings.isNullOrEmpty(batchNo)){
            try {
                importFilter.forBatchNoContaining(batchNo);
            } catch (DatasweepException e) {
                throw new RuntimeException(e);
            }
        }
        if (!Strings.isNullOrEmpty(tip)){
            try {
                importFilter.forTipEqualTo(tip);
            } catch (DatasweepException e) {
                throw new RuntimeException(e);
            }
        }
        if (!Strings.isNullOrEmpty(inventoryQty)){
            try {
                importFilter.forInventoryQtyContaining(inventoryQty);
            } catch (DatasweepException e) {
                throw new RuntimeException(e);
            }
        }
        List<IMESCDMateriaInventoryImport> filteredObjects = importFilter.getFilteredObjects();
        ArrayList<InventoryQueryTable> inventoryQueryArrayList = new ArrayList<>();
        for (int i = 0; i < filteredObjects.size(); i++) {
            InventoryQueryTable inventoryQueryTable = new InventoryQueryTable();
            IMESCDMateriaInventoryImport anImport = filteredObjects.get(i);
            inventoryQueryTable.setInventoryQty(anImport.getInventoryQty());
            inventoryQueryTable.setBatchNo(anImport.getBatchNo());
            inventoryQueryTable.setMaterialDesc(anImport.getMaterialDesc());
            inventoryQueryTable.setSpecifications(anImport.getSpecifications());
            inventoryQueryTable.setTip(anImport.getTip());
            inventoryQueryTable.setUnit(anImport.getUnit());
            inventoryQueryTable.setUom(anImport.getUom());
            inventoryQueryTable.setValidityData(anImport.getValidityData());
            inventoryQueryTable.setSublotNum(anImport.getSublotNum());
            inventoryQueryTable.setPackagingSpecifications(anImport.getPackagingSpecifications());
            inventoryQueryTable.setMaterialNo(anImport.getMaterialNo());
            inventoryQueryArrayList.add(inventoryQueryTable);
        }
        return inventoryQueryArrayList;
    }
}
