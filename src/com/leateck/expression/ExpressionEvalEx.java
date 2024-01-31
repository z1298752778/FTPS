package com.leateck.expression;


import com.datasweep.compatibility.client.*;
import com.leateck.model.ordermapping.IMESLCOrderMapping;
import com.leateck.model.ordermapping.MESLCOrderMappingFilter;
import com.leateck.phase.identifyequipment0100.MessageConstants;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentEntityDataHandler;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentEntityDataHolder;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentObjectType;
import com.rockwell.mes.apps.recipeeditor.ifc.expression.ExpressionEval;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.choicelist.MESChoiceListHelper;
import com.rockwell.mes.commons.base.ifc.expression.processing.function.ExpressionFunction;
import com.rockwell.mes.commons.base.ifc.objects.MESObjectDeletedCheckedException;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.commons.deviation.ifc.IMESSignature;
import com.rockwell.mes.phase.eqtriggergraphtransition.RtPhaseModelEqTriggerTrans0200;
import com.rockwell.mes.services.s88.ifc.IS88EquipmentExecutionService;
import com.rockwell.mes.services.s88.ifc.execution.IMESRtPhase;
import com.rockwell.mes.services.s88.ifc.execution.equipment.statusgraph.MESFireStatusGraphTriggerFailedException;
import com.rockwell.mes.services.s88.ifc.library.IBuildingBlockOutputDescriptor;
import com.rockwell.mes.services.s88.ifc.processdata.IMESRtPhaseOutput;
import com.rockwell.mes.services.s88.impl.execution.MESRtPhaseFilter;
import com.rockwell.mes.services.s88.impl.execution.MESRtUnitProcedureFilter;
import com.rockwell.mes.services.s88equipment.ifc.IMESEquipmentProperty;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88Equipment;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IMESS88StatusGraphAssignment;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IMESS88StatusGraphState;

import java.math.BigDecimal;
import java.util.List;


public class ExpressionEvalEx extends ExpressionEval {

    /**
     *获取其他工单数据
     * @param recipeNo
     * @param unitName
     * @param unitStepName
     * @param operationName
     * @param stepName
     * @param outputName
     * @return
     * @throws DatasweepException
     */
    @ExpressionFunction(typeInferencerClass = FunctionTypeInferencerGetOrderPhaseData.class, groupId = "100_CustomFunctionsGroup")
    public Object getOrderPhaseData(String recipeNo, String unitName, String unitStepName, String operationName, String stepName, String outputName) throws DatasweepException {
        MESRtUnitProcedureFilter filter = new MESRtUnitProcedureFilter();
        String sql = " select rtphase.atr_key,cr.control_recipe_name,pro.X_procedureName_S,xup.X_unitProcedureName_S,op1.X_operationName_S,phase.X_phaseName_S \n" +
                " from PROCESS_ORDER_ITEM poi\n" +
                "\tLEFT JOIN OBJECT_STATE os ON os.object_key = poi.order_item_key\n" +
                "\tLEFT JOIN STATE st ON st.state_key = os.state_key\n" +
                "\tLEFT JOIN CONTROL_RECIPE cr ON cr.order_item_key = poi.order_item_key\n" +
                "\tleft join MASTER_RECIPE mr on mr.master_recipe_key = cr.master_recipe_key \n" +
                "\tLEFT JOIN AT_X_RtProcedure rtpro ON rtpro.X_controlRecipe_113 = cr.control_recipe_key\n" +
                "\tLEFT JOIN AT_X_Procedure pro ON  pro.atr_key =rtpro.X_procedure_64 \n" +
                "\tLEFT JOIN ORDER_STEP orderstep ON orderstep.control_recipe_key = cr.control_recipe_key\n" +
                "\tLEFT JOIN AT_X_RtUnitProcedure up ON up.X_orderStep_114 = orderstep.order_step_key\n" +
                "\tLEFT JOIN AT_X_UnitProcedure xup ON xup.atr_key = up.X_unitProcedure_64\n" +
                "\tLEFT JOIN AT_X_RtOperation op ON op.X_parent_64 = up.atr_key\n" +
                "\tLEFT JOIN AT_X_Operation op1 on op1.atr_key = op.X_operation_64\n" +
                "\tLEFT JOIN AT_X_RtPhase rtphase ON rtphase.X_parent_64 = op.atr_key \n" +
                "\tLEFT JOIN AT_X_Phase phase ON phase.atr_key = rtphase.X_phase_64\n" +
                "\twhere mr.master_recipe_name = N'" + recipeNo + "'" +
                "\tand pro.X_procedureName_S = N'" + unitName + "'" +
                "and xup.X_unitProcedureName_S = N'" + unitStepName + "'" +
                "\tand op1.X_operationName_S = N'" + operationName + "'" +
                "and phase.X_phaseName_S = N'" + stepName + "'";
        List<String[]> list = null;
        //SQL获取ATR——key

        //将数据保存至数据表
        ControlRecipe cr = getControlRecipe();
        ProcessOrderItem poi = cr.getProcessOrderItem();
        String orderNumber = poi.getName();
        //获取当前工单的关联工单/工作流
        MESLCOrderMappingFilter meslcOrderMappingFilter = new MESLCOrderMappingFilter();
        meslcOrderMappingFilter.forOrdernameEqualTo(orderNumber);
        List<IMESLCOrderMapping> filteredObjects = meslcOrderMappingFilter.getFilteredObjects();
        for (IMESLCOrderMapping filteredObject : filteredObjects) {
            //一个工单只能关联不同处方的工单
            String mappingordername = filteredObject.getMappingordername();

            //根据关联工单号获取处方
            ProcessOrder processOrderByName = PCContext.getFunctions().getProcessOrderByName(mappingordername);
            ProcessOrderItem processOrderItem = processOrderByName.getProcessOrderItem(mappingordername);
            String name = processOrderItem.getControlRecipe().getMasterRecipe().getName();
            if (name != null && name.equals(recipeNo)) {
                sql += " and poi.order_item_name = N'" + mappingordername + "'";
                sql += " order by rtphase.creation_time ASC";
                //System.out.println("tets:"+sql);
                list = PCContext.getFunctions().getArrayDataFromActive(sql);
                if (list != null && list.size() > 0) {
                    String insertSql = "INSERT INTO LC_GetOrderPhase\n" +
                            "(current_order,order_name, phase_name, create_time)\n" +
                            "VALUES(N'" + orderNumber + "',N'" + mappingordername + "', N'" + stepName + "', CURRENT_TIMESTAMP)";
                    //保存数据
                    int[] ints = PCContext.getFunctions().executeStatements(new String[]{insertSql});
                }
            }
        }
        if (list != null && list.size() > 0) {
            //获取通用的标准的四个参数的输出
            //获取phase输出
            MESRtPhaseFilter t = new MESRtPhaseFilter();
            //SQL获取ATR——key
            t.forATRowKeyEqualTo(Integer.valueOf(list.get(list.size()-1)[0]));
            final List<IMESRtPhase> filteredObjects2 = t.getFilteredObjects();
            //获取通用的标准的四个参数的输出
            if ("Completion time".equals(outputName)) {
                return filteredObjects2.get(filteredObjects2.size() - 1).getCompleted();
            } else if ("Identifier".equals(outputName)) {
                return stepName;
            } else if ("Instance count".equals(outputName)) {
                return filteredObjects2.get(filteredObjects2.size() - 1).getInstanceCount();
            } else if ("Start time".equals(outputName)) {
                return filteredObjects2.get(filteredObjects2.size() - 1).getStarted();
            }
            //获取phase输出
            if (filteredObjects2.size() > 0) {
                Boolean isExist = false;
                IMESRtPhase imesRtPhase = filteredObjects2.get(filteredObjects2.size() - 1);
                IMESRtPhaseOutput rtPhaseOutput = imesRtPhase.getRtPhaseOutput();
                List<IBuildingBlockOutputDescriptor> outputDescriptors = rtPhaseOutput.getOutputDescriptors();
                for (IBuildingBlockOutputDescriptor outputDescriptor : outputDescriptors) {
                    final String name = outputDescriptor.getName();
                    if (name != null && name.equals(outputName)) {
                        isExist = true;
                    }
//                        final String displayName = outputDescriptor.getDisplayName();
//                        Object outputValue = rtPhaseOutput.getOutputValue(displayName);
//                        Object str= outputValue;

                }
                if (isExist) {
                    return filteredObjects2.get(filteredObjects2.size() - 1).getRtPhaseOutput().getOutputValue(outputName);
                }

            }

        }

        return null;
    }

    /**
     * 重置当前连续生产批次数属性
     * @param equipment
     * @param equPropertyNam
     * @return
     */
    @ExpressionFunction(typeInferencerClass = FunctionTypeInferencerResetNumber.class, groupId = "100_CustomFunctionsGroup")
    public Boolean resetNumber(IMESS88Equipment equipment,String equPropertyNam){
        if (equipment != null) {
            EquipmentEntityDataHandler dataHandler = (EquipmentEntityDataHandler) EquipmentObjectType.EQM_ENTITY.getDataHandler();
            try {
                EquipmentEntityDataHolder dataHolder = dataHandler.createProxy(equipment).getDataHolder();
                dataHolder.getObject().startMonitorChangeHistoryEvents();
                IMESS88Equipment holderObject = dataHolder.getObject();
                IMESEquipmentProperty<?> currentNumber = holderObject.getProperty(equPropertyNam);
                if (currentNumber != null ) {
                   //currentNumber.setStringValue("0");
                   currentNumber.setDecimalValue(BigDecimal.valueOf(0));
                }
                dataHolder.save();
                dataHolder.getObject().finishMonitorChangeHistoryEvents();
            } catch (MESObjectDeletedCheckedException | DatasweepException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 状态转换
     * @param equipment
     * @param transName
     * @param triggleName
     * @param isConversion
     * @return
     */
    @ExpressionFunction(typeInferencerClass = FunctionTypeInferencerTriggerStaph.class, groupId = "100_CustomFunctionsGroup")
    public Boolean triggerStaph(IMESS88Equipment equipment,String transName,String triggleName,Boolean isConversion){
        if(equipment != null && isConversion){
            try {
                 IMESChoiceElement s88StatusGraphPurpose = MESChoiceListHelper.getChoiceElement("S88StatusGraphPurpose", transName);
                ((IS88EquipmentExecutionService) ServiceFactory.getService(IS88EquipmentExecutionService.class)).fireGraphTrigger(equipment,s88StatusGraphPurpose ,triggleName,(IMESSignature)null);
            }catch (MESFireStatusGraphTriggerFailedException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

}
