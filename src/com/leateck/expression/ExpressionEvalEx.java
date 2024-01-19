package com.leateck.expression;


import com.datasweep.compatibility.client.ATRowFilter;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.ProcessOrder;
import com.datasweep.compatibility.client.ProcessOrderItem;
import com.leateck.model.ordermapping.IMESLCOrderMapping;
import com.leateck.model.ordermapping.MESLCOrderMappingFilter;
import com.rockwell.mes.apps.commons.ifc.utility.FindLibraries;
import com.rockwell.mes.apps.recipeeditor.ifc.expression.ExpressionEval;
import com.rockwell.mes.commons.base.ifc.expression.processing.function.ExpressionFunction;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.services.s88.ifc.execution.IMESRtOperation;
import com.rockwell.mes.services.s88.ifc.execution.IMESRtPhase;
import com.rockwell.mes.services.s88.ifc.library.IMESPhaseLib;
import com.rockwell.mes.services.s88.impl.execution.MESRtOperationFilter;
import com.rockwell.mes.services.s88.impl.execution.MESRtPhaseFilter;
import com.rockwell.mes.services.s88.impl.execution.MESRtUnitProcedureFilter;

import java.util.List;
import java.util.Vector;


public class ExpressionEvalEx extends ExpressionEval {
    @ExpressionFunction(typeInferencerClass = FunctionTypeInferencerGetOrderPhaseData.class, groupId = "100_CustomFunctionsGroup")
    public Object getOrderPhaseData(String recipeNo,String unitName,String unitStepName,String operationName,String stepName,String outputName) throws DatasweepException {
        MESRtUnitProcedureFilter filter = new MESRtUnitProcedureFilter();
        String sql = " select rtphase.atr_key,cr.control_recipe_name,pro.X_procedureName_S,xup.X_unitProcedureName_S,op1.X_operationName_S,phase.X_phaseName_S \n" +
        " from PROCESS_ORDER_ITEM poi\n" +
        "\tLEFT JOIN OBJECT_STATE os ON os.object_key = poi.order_item_key\n" +
        "\tLEFT JOIN STATE st ON st.state_key = os.state_key\n" +
        "\tLEFT JOIN CONTROL_RECIPE cr ON cr.order_item_key = poi.order_item_key\n" +
        "\tLEFT JOIN AT_X_RtProcedure rtpro ON rtpro.X_controlRecipe_113 = cr.control_recipe_key\n" +
        "\tLEFT JOIN AT_X_Procedure pro ON  pro.atr_key =rtpro.X_procedure_64 \n" +
        "\tLEFT JOIN ORDER_STEP orderstep ON orderstep.control_recipe_key = cr.control_recipe_key\n" +
        "\tLEFT JOIN AT_X_RtUnitProcedure up ON up.X_orderStep_114 = orderstep.order_step_key\n" +
        "\tLEFT JOIN AT_X_UnitProcedure xup ON xup.atr_key = up.X_unitProcedure_64\n" +
        "\tLEFT JOIN AT_X_RtOperation op ON op.X_parent_64 = up.atr_key\n" +
        "\tLEFT JOIN AT_X_Operation op1 on op1.atr_key = op.X_operation_64\n" +
        "\tLEFT JOIN AT_X_RtPhase rtphase ON rtphase.X_parent_64 = op.atr_key \n" +
        "\tLEFT JOIN AT_X_Phase phase ON phase.atr_key = rtphase.X_phase_64\n" +
        "\twhere cr.control_recipe_name = N'" +recipeNo+"'"+
        "\tand pro.X_procedureName_S = N'" +unitName+"'"+
        "and xup.X_unitProcedureName_S = N'" +unitStepName+"'"+
        "\tand op1.X_operationName_S = N'" +operationName+"'"+
        "and phase.X_phaseName_S = N'" +stepName+"'";
        List<String[]> list = PCContext.getFunctions().getArrayDataFromActive(sql);
        //SQL获取ATR——key
        if(list.size()>0){
            //将数据保存至数据表
             ProcessOrder currentProcessOrder = PCContext.getFunctions().getCurrentProcessOrder();
            String orderNumber = currentProcessOrder.getOrderNumber();
            //获取当前工单的关联工单/工作流
            final MESLCOrderMappingFilter meslcOrderMappingFilter = new MESLCOrderMappingFilter();
            meslcOrderMappingFilter.forOrdernameEqualTo(orderNumber);
             List<IMESLCOrderMapping> filteredObjects = meslcOrderMappingFilter.getFilteredObjects();
            for (IMESLCOrderMapping filteredObject : filteredObjects) {
                 String mappingordername = filteredObject.getMappingordername();
                 //根据关联工单号获取处方
                 ProcessOrder processOrderByName = PCContext.getFunctions().getProcessOrderByName(mappingordername);
                 ProcessOrderItem processOrderItem = processOrderByName.getProcessOrderItem(mappingordername);
                 String name = processOrderItem.getControlRecipe().getMasterRecipe().getName();
                 if(name != null && name.equals(recipeNo)){
                     String insertSql = "INSERT INTO PharmaSuite1002.dbo.LC_GetOrderPhase\n" +
                             "(order_name, phase_name, create_time)\n" +
                             "VALUES('"+mappingordername+"', '"+stepName+"', getdate())";
                     //保存数据
                      int[] ints = PCContext.getFunctions().executeStatements(new String[]{insertSql});
                 }
            }
            //获取通用的标准的四个参数的输出
//            MESRtOperationFilter Q = new MESRtOperationFilter();
//            Q.forATRowKeyEqualTo(Integer.valueOf(list.get(0)[0]));
//            List<IMESRtOperation> filteredObjects1 = Q.getFilteredObjects();
//            MESRtUnitProcedureFilter a = new MESRtUnitProcedureFilter();
//            //filteredObjects1.get(0).getAllRtPhases().get(0).getRtPhaseOutput().getOutputValue("");
//            if("Completion time".equals(outputName)){
//               return filteredObjects1.get(0).getAllRtPhases().get(0).getCompleted();
//            }else if("Identifier".equals(outputName)){
//                return filteredObjects1.get(0).getAllRtPhases().get(0).getDeviceIdentifier();
//
//            }else if("Instance count".equals(outputName)){
//                return filteredObjects1.get(0).getAllRtPhases().get(0).getInstanceCount();
//            }else if("Start time".equals(outputName)){
//                return filteredObjects1.get(0).getAllRtPhases().get(0).getStarted();
//            }

            //获取phase输出
            MESRtPhaseFilter t =new MESRtPhaseFilter();
            //SQL获取ATR——key
            t.forATRowKeyEqualTo(Integer.valueOf(list.get(0)[0]));
            final List<IMESRtPhase> filteredObjects2 = t.getFilteredObjects();
            //获取通用的标准的四个参数的输出
            if("Completion time".equals(outputName)){
                return filteredObjects2.get(filteredObjects2.size()-1).getCompleted();
            }else if("Identifier".equals(outputName)){
                return filteredObjects2.get(filteredObjects2.size()-1).getDeviceIdentifier();

            }else if("Instance count".equals(outputName)){
                return filteredObjects2.get(filteredObjects2.size()-1).getInstanceCount();
            }else if("Start time".equals(outputName)){
                return filteredObjects2.get(filteredObjects2.size()-1).getStarted();
            }
            //获取phase输出
           return filteredObjects2.get(filteredObjects2.size()-1).getRtPhaseOutput().getOutputValue(outputName);

        }
        return null;
    }

}
