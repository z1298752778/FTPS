package com.leateck.model.report;

import com.rockwell.mes.apps.order.ifc.report.BatchReportScriptlet;
import com.rockwell.mes.commons.base.ifc.choicelist.MESChoiceListHelper;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.commons.base.ifc.utility.ObjectFactory;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.IMESExceptionRecord;
import com.rockwell.mes.commons.shared.phase.reporthelper.AbstractReportScriptlet0100;
import com.rockwell.mes.services.batchreport.ifc.IMESB2MMLJRDataSource;
import com.rockwell.mes.services.batchreport.impl.ReportScriptletExceptions;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.text.ParseException;

public class MappingOrderReportScript extends AbstractReportScriptlet0100 {

    public static ArrayList getMapData(String orderNumber) {
        ArrayList<MappingOrderBean> list = new ArrayList<MappingOrderBean>();
        String sql = "SELECT distinct\n" +
                "\tlgop.order_name,\n" +
                "\tlgop.phase_name\n" +
                "from\n" +
                "\tPROCESS_ORDER_ITEM poi\n" +
                "left join LC_GetOrderPhase lgop on\n" +
                "\tlgop.current_order = poi.order_item_name \n" +
                "where\n" +
                "\tpoi.order_item_name = N'" + orderNumber + "'";
        List<String[]> list1 = PCContext.getFunctions().getArrayDataFromActive(sql);
        for (String[] strings : list1) {
            String mappingOrderName = strings[0];
            String phaseName = strings[1];
            String getsql = "SELECT poi.order_item_name ,--工单号\n" +
                    "case when poi.X_processingType_I = 10 THEN N'工单' WHEN poi.X_processingType_I = 30 THEN N'工作流' END AS PROCESS_TYPE, --工单类型\n" +
                    "ex.X_description_S,--说明\n" +
                    "ex.X_status_I,--状态\n" +
                    "X_riskClass_I,--等级\n" +
                    "X_exceptionCategory_I,--种类\n" +
                    "concat(pro.X_procedureName_S,'/',xup.X_unitProcedureName_S,'/',op1.X_operationName_S,'/',ph.X_phaseName_S) elements,\n" +
                    "es3.X_userFirstName_S, es3.X_userLastName_S ,es3.X_username_S,es3.X_reason_S ,--操作人员\n" +
                    "CONVERT(varchar(100), ex.creation_time, 20),ex.X_exceptionResult_I ,ex.X_excClassification_I--操作时间\n" +
                    "FROM\n" +
                    "AT_X_ExceptionRecord ex\n" +
                    "LEFT JOIN AT_X_ExceptionObjectRelation ex1 ON ex1.X_exceptionRecord_64 = ex.atr_key\n" +
                    "LEFT JOIN AT_X_ESignatureContext ex2 ON ex2.X_ckey_I = ex.atr_key\n" +
                    "LEFT JOIN AT_X_ESignature es3 ON ex2.X_sig_64 = es3.atr_key\n" +
                    "LEFT JOIN AT_X_RtPhase rtphase ON rtphase.atr_key = ex1.X_ctxKey_I\n" +
                    "LEFT JOIN AT_X_Phase ph ON ph.atr_key = rtphase.X_phase_64\n" +
                    "LEFT JOIN AT_X_RtOperation op ON rtphase.X_parent_64 = op.atr_key\n" +
                    "LEFT JOIN AT_X_Operation op1 on op1.atr_key = op.X_operation_64\n" +
                    "LEFT JOIN AT_X_RtUnitProcedure up ON op.X_parent_64 = up.atr_key\n" +
                    "LEFT JOIN AT_X_UnitProcedure xup ON xup.atr_key = up.X_unitProcedure_64\n" +
                    "LEFT JOIN ORDER_STEP orderstep ON up.X_orderStep_114 = orderstep.order_step_key\n" +
                    "LEFT JOIN CONTROL_RECIPE cr ON orderstep.control_recipe_key = cr.control_recipe_key\n" +
                    "left join MASTER_RECIPE mr on mr.master_recipe_key = cr.master_recipe_key \n" +
                    "\tLEFT JOIN AT_X_RtProcedure rtpro ON rtpro.X_controlRecipe_113 = cr.control_recipe_key\n" +
                    "\tLEFT JOIN AT_X_Procedure pro ON  pro.atr_key =rtpro.X_procedure_64 \n" +
                    "LEFT JOIN PROCESSORDERITEM_UV  poi ON cr.order_item_key = poi.order_item_key\n" +
                    "LEFT JOIN UDA_ProcessOrderItem udapoi ON udapoi.object_key = poi.order_item_key\n" +
                    "LEFT JOIN BATCH batch ON batch.batch_key = udapoi.X_batch_105\n" +
                    "LEFT JOIN PART part ON part.part_number = poi.part_number\n" +
                    "where poi.order_item_name = N'" + mappingOrderName + "' and ph.X_phaseName_S = N'" + phaseName + "'";
            List<String[]> lists = PCContext.getFunctions().getArrayDataFromActive(getsql);
            for (int i = 0; i < lists.size(); i++) {
                String[] strs = lists.get(i);
                MappingOrderBean mappingOrderBean = new MappingOrderBean();
                mappingOrderBean.setSeq(i + 1);
                mappingOrderBean.setOrderNumber(strs[0]);
                mappingOrderBean.setType(strs[1]);
                mappingOrderBean.setInstruction(strs[2]);
                String status = "";
                if (strs[3] != null && !"".equals(strs[3])) {
                    status = MESChoiceListHelper.getChoiceElement("ExceptionStatus", Long.valueOf(strs[3])).getLocalizedMessage();
                }
                //ExceptionResult
                String exceptionResult = "---";
                if (strs[12] != null && !"".equals(strs[12])) {
                    exceptionResult = MESChoiceListHelper.getChoiceElement("ExceptionResult", Long.valueOf(strs[12])).getLocalizedMessage();
                }
                String risk = "";
                if (strs[4] != null && !"".equals(strs[4])) {
                    risk = IMESExceptionRecord.RiskClass.toLocalizedString(Long.valueOf(strs[4]));
                }
                //X_excClassification_I
                String exceptionClassification = "---";
                if (strs[13] != null && !"".equals(strs[13])) {
                    exceptionClassification = MESChoiceListHelper.getChoiceElement("ExceptionClassification", Long.valueOf(strs[13])).getLocalizedMessage();
                }
                mappingOrderBean.setStatus(status + "\n" + exceptionResult);
                mappingOrderBean.setRisk(risk + "\n" + exceptionClassification);
                //种类 ExceptionCategory
                String category = "";
                if (strs[5] != null && !"".equals(strs[5])) {
                    category = MESChoiceListHelper.getChoiceElement("ExceptionCategory", Long.valueOf(strs[5])).getLocalizedMessage();
                }
                mappingOrderBean.setCategory(category);
                mappingOrderBean.setElement(strs[6]);
                String first = "---";
                if (strs[7] != null && !"".equals(strs[7])) {
                    first = strs[7];
                }
                String last = "---";
                if (strs[8] != null && !"".equals(strs[8])) {
                    last = strs[8];
                }
                String name = "";
                if (strs[9] != null && !"".equals(strs[9])) {
                    name = "(" + strs[9] + ")";
                }
                String reason = "N/A";
                if (strs[10] != null && !"".equals(strs[10])) {
                    reason = strs[10];
                }
                mappingOrderBean.setSign(first + last + name + "\n" + reason + "\n" + strs[11]);
                list.add(mappingOrderBean);
            }

        }
        for (int i = 0; i < list.size(); i++) {
             MappingOrderBean mappingOrderBean = list.get(i);
            mappingOrderBean.setSeq(i+1);
        }
        return list;
    }

    @SuppressWarnings({"rawtypes", "deprecation"})
    public static JRDataSource getSource(String key) {
        ArrayList al = getMapData(key);
        if (al != null) {
            Object aobj[] = {al};
            IMESB2MMLJRDataSource imesb2mmljrdatasource = ObjectFactory.getInstance().createObject(
                    IMESB2MMLJRDataSource.class, new Class[]{Collection.class}, aobj);
            imesb2mmljrdatasource.initExpressionMappings();
            return imesb2mmljrdatasource;
        }
        return null;
    }

}
