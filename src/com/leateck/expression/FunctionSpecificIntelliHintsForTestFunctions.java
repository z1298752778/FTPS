package com.leateck.expression;

import java.util.*;
import java.util.stream.Collectors;

import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.MasterRecipe;
import com.datasweep.compatibility.client.MasterRecipeFilter;
import com.rockwell.mes.clientfw.docking.ifc.expression.editor.codeeditor.intellihints.AbstractFunctionSpecificStaticIntelliHintsProvider;
import com.rockwell.mes.clientfw.docking.ifc.expression.editor.codeeditor.intellihints.IExpressionOpenFunctionCallDescriptor;
import com.rockwell.mes.clientfw.docking.ifc.expression.editor.intellihints.ChoiceElementIntelliHintDescriptor;
import com.rockwell.mes.clientfw.docking.ifc.expression.editor.intellihints.IIntelliHintDescriptorsSection;
import com.rockwell.mes.clientfw.docking.ifc.expression.editor.intellihints.IStaticIntelliHintDescriptor;
import com.rockwell.mes.clientfw.docking.ifc.expression.editor.intellihints.IntelliHintDescriptorsSection;
import com.rockwell.mes.clientfw.docking.ifc.expression.editor.intellihints.StringConstantIntelliHintDescriptor;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.services.s88.ifc.execution.IMESRtPhase;
import com.rockwell.mes.services.s88.ifc.library.IBuildingBlockOutputDescriptor;
import com.rockwell.mes.services.s88.impl.execution.MESRtPhaseFilter;

public class FunctionSpecificIntelliHintsForTestFunctions extends AbstractFunctionSpecificStaticIntelliHintsProvider {
    protected List<? extends IStaticIntelliHintDescriptor> getIntelliHintsForFunctionCall(
            final IExpressionOpenFunctionCallDescriptor functionCallDescriptor) {
        //处方编号

        if (functionCallDescriptor.getIndexOfOpenArgument() == 1) {
            ArrayList<StringConstantIntelliHintDescriptor> list = new ArrayList<>();
            //处方
            String purposrLabel = I18nMessageUtility.getLocalizedMessage("LC_Expression", "Recipe_Label");

            IntelliHintDescriptorsSection section = new IntelliHintDescriptorsSection(1000,
                    purposrLabel,
                    ChoiceElementIntelliHintDescriptor.getGenericChoiceListSectionIcon());
            //获取所有的处方

            //获取状态为有效的处方
            String sql = "select DISTINCT\n" +
                    "\tmr.master_recipe_name as '处方名'\n" +
                    "from\n" +
                    "\tMASTER_RECIPE mr\n" +
                    "left join AT_X_Procedure axp on\n" +
                    "\taxp.X_masterRecipe_107 = mr.master_recipe_key\n" +
                    "left join AT_X_UnitProcedure axup on\n" +
                    "\taxup .X_parent_64 = axp.atr_key\n" +
                    "left join AT_X_Operation axo on\n" +
                    "\taxo.X_parent_64 = axup .atr_key\n" +
                    "left join AT_X_Phase axp2 on\n" +
                    "\taxp2.X_parent_64 = axo.atr_key\n" +
                    "LEFT JOIN OBJECT_STATE os ON\n" +
                    "\tos.object_key = mr.master_recipe_key\n" +
                    "LEFT JOIN STATE st ON\n" +
                    "\tst.state_key = os.state_key\n" +
                    "left join PROCESSBOM_UV pbi on\n" +
                    "\tpbi.bom_key = mr.bom_key\n" +
                    "inner join PART_UV p2 on\n" +
                    "\tp2.part_key = pbi.produced_part_key\n" +
                    "where\n" +
                    "\tst.state_name = N'Valid'";
            //System.out.println(sql);
            Vector<String[]> list1 = PCContext.getFunctions().getArrayDataFromActive(sql);
            for (String[] strs : list1) {
                String name = strs[0];
                StringConstantIntelliHintDescriptor stringConstantIntelliHintDescriptor2 = new StringConstantIntelliHintDescriptor(name, name, section);
                list.add(stringConstantIntelliHintDescriptor2);
            }
            return list;

        }
        //工序名称
        if (functionCallDescriptor.getIndexOfOpenArgument() == 2) {
            String recipeName = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(1);
            recipeName = declear(recipeName);
            ArrayList<StringConstantIntelliHintDescriptor> list = new ArrayList<>();
            //"工序"
            String purposrLabel = I18nMessageUtility.getLocalizedMessage("LC_Expression", "Procedure_Label");
            IntelliHintDescriptorsSection section = new IntelliHintDescriptorsSection(1000,
                    purposrLabel,
                    ChoiceElementIntelliHintDescriptor.getGenericChoiceListSectionIcon());
            //获取所有的处方

            //获取状态为有效的处方
            String sql = "select DISTINCT\n" +
                    "\taxp.X_procedureName_S as '工序'\n" +
                    "from\n" +
                    "\tMASTER_RECIPE mr\n" +
                    "left join AT_X_Procedure axp on\n" +
                    "\taxp.X_masterRecipe_107 = mr.master_recipe_key\n" +
                    "left join AT_X_UnitProcedure axup on\n" +
                    "\taxup .X_parent_64 = axp.atr_key\n" +
                    "left join AT_X_Operation axo on\n" +
                    "\taxo.X_parent_64 = axup .atr_key\n" +
                    "left join AT_X_Phase axp2 on\n" +
                    "\taxp2.X_parent_64 = axo.atr_key\n" +
                    "LEFT JOIN OBJECT_STATE os ON\n" +
                    "\tos.object_key = mr.master_recipe_key\n" +
                    "LEFT JOIN STATE st ON\n" +
                    "\tst.state_key = os.state_key\n" +
                    "left join PROCESSBOM_UV pbi on\n" +
                    "\tpbi.bom_key = mr.bom_key\n" +
                    "inner join PART_UV p2 on\n" +
                    "\tp2.part_key = pbi.produced_part_key\n" +
                    "where\n" +
                    "\tst.state_name = N'Valid' and mr.master_recipe_name = N'" + recipeName + "'";
            //System.out.println(sql);
            Vector<String[]> list1 = PCContext.getFunctions().getArrayDataFromActive(sql);
            for (String[] strs : list1) {
                String name = strs[0];
                StringConstantIntelliHintDescriptor stringConstantIntelliHintDescriptor2 = new StringConstantIntelliHintDescriptor(name, name, section);
                list.add(stringConstantIntelliHintDescriptor2);
            }
            return list;
        }

        //单位工序名称
        if (functionCallDescriptor.getIndexOfOpenArgument() == 3) {
            String recipeName = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(1);
            String processName = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(2);
            recipeName = declear(recipeName);
            processName = declear(processName);
            ArrayList<StringConstantIntelliHintDescriptor> list = new ArrayList<>();
            //"单位工序"
            String purposrLabel = I18nMessageUtility.getLocalizedMessage("LC_Expression", "UnitProcedure_Label");

            IntelliHintDescriptorsSection section = new IntelliHintDescriptorsSection(1000,
                    purposrLabel,
                    ChoiceElementIntelliHintDescriptor.getGenericChoiceListSectionIcon());
            //获取所有的处方

            //获取状态为有效的处方
            String sql = "select DISTINCT\n" +
                    "\taxup.X_unitProcedureName_S as '单位工序' \n" +
                    "from\n" +
                    "\tMASTER_RECIPE mr\n" +
                    "left join AT_X_Procedure axp on\n" +
                    "\taxp.X_masterRecipe_107 = mr.master_recipe_key\n" +
                    "left join AT_X_UnitProcedure axup on\n" +
                    "\taxup .X_parent_64 = axp.atr_key\n" +
                    "left join AT_X_Operation axo on\n" +
                    "\taxo.X_parent_64 = axup .atr_key\n" +
                    "left join AT_X_Phase axp2 on\n" +
                    "\taxp2.X_parent_64 = axo.atr_key\n" +
                    "LEFT JOIN OBJECT_STATE os ON\n" +
                    "\tos.object_key = mr.master_recipe_key\n" +
                    "LEFT JOIN STATE st ON\n" +
                    "\tst.state_key = os.state_key\n" +
                    "left join PROCESSBOM_UV pbi on\n" +
                    "\tpbi.bom_key = mr.bom_key\n" +
                    "inner join PART_UV p2 on\n" +
                    "\tp2.part_key = pbi.produced_part_key\n" +
                    "where\n" +
                    "\tst.state_name = N'Valid' and mr.master_recipe_name = N'" + recipeName + "' and axp.X_procedureName_S = N'" + processName + "'";
            //System.out.println(sql);
            Vector<String[]> list1 = PCContext.getFunctions().getArrayDataFromActive(sql);
            for (String[] strs : list1) {
                String name = strs[0];
                StringConstantIntelliHintDescriptor stringConstantIntelliHintDescriptor2 = new StringConstantIntelliHintDescriptor(name, name, section);
                list.add(stringConstantIntelliHintDescriptor2);

            }
            return list;
        }

        //操作名称
        if (functionCallDescriptor.getIndexOfOpenArgument() == 4) {
            String recipeName = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(1);
            String processName = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(2);
            String operationName = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(3);
            recipeName = declear(recipeName);
            processName = declear(processName);
            operationName = declear(operationName);
            ArrayList<StringConstantIntelliHintDescriptor> list = new ArrayList<>();
            //操作
            String purposrLabel = I18nMessageUtility.getLocalizedMessage("LC_Expression", "Operation_Label");

            IntelliHintDescriptorsSection section = new IntelliHintDescriptorsSection(1000,
                    purposrLabel,
                    ChoiceElementIntelliHintDescriptor.getGenericChoiceListSectionIcon());
            //获取所有的处方

            //获取状态为有效的处方
            String sql = "select DISTINCT\n" +
                    "\taxo.X_operationName_S as '操作' \n" +
                    "from\n" +
                    "\tMASTER_RECIPE mr\n" +
                    "left join AT_X_Procedure axp on\n" +
                    "\taxp.X_masterRecipe_107 = mr.master_recipe_key\n" +
                    "left join AT_X_UnitProcedure axup on\n" +
                    "\taxup .X_parent_64 = axp.atr_key\n" +
                    "left join AT_X_Operation axo on\n" +
                    "\taxo.X_parent_64 = axup .atr_key\n" +
                    "left join AT_X_Phase axp2 on\n" +
                    "\taxp2.X_parent_64 = axo.atr_key\n" +
                    "LEFT JOIN OBJECT_STATE os ON\n" +
                    "\tos.object_key = mr.master_recipe_key\n" +
                    "LEFT JOIN STATE st ON\n" +
                    "\tst.state_key = os.state_key\n" +
                    "left join PROCESSBOM_UV pbi on\n" +
                    "\tpbi.bom_key = mr.bom_key\n" +
                    "inner join PART_UV p2 on\n" +
                    "\tp2.part_key = pbi.produced_part_key\n" +
                    "where\n" +
                    "\tst.state_name = N'Valid' and mr.master_recipe_name = N'" + recipeName + "' and axp.X_procedureName_S = N'" + processName + "'";
            sql += " and axup.X_unitProcedureName_S = N'" + operationName + "'";
            //System.out.println(sql);
            Vector<String[]> list1 = PCContext.getFunctions().getArrayDataFromActive(sql);
            for (String[] strs : list1) {
                String name = strs[0];
                StringConstantIntelliHintDescriptor stringConstantIntelliHintDescriptor2 = new StringConstantIntelliHintDescriptor(name, name, section);
                list.add(stringConstantIntelliHintDescriptor2);

            }


            return list;
        }
        //步骤名称
        if (functionCallDescriptor.getIndexOfOpenArgument() == 5) {
            String recipeName = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(1);
            String processName = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(2);
            String operationName = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(3);
            String stepName = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(4);
            recipeName = declear(recipeName);
            processName = declear(processName);
            operationName = declear(operationName);
            stepName = declear(stepName);
            ArrayList<StringConstantIntelliHintDescriptor> list = new ArrayList<>();
            //步骤
            String purposrLabel = I18nMessageUtility.getLocalizedMessage("LC_Expression", "PhaseName_Label");

            IntelliHintDescriptorsSection section = new IntelliHintDescriptorsSection(1000,
                    purposrLabel,
                    ChoiceElementIntelliHintDescriptor.getGenericChoiceListSectionIcon());
            //获取所有的处方

            //获取状态为有效的处方
            String sql = "select DISTINCT\n" +
                    "\taxp2.X_phaseName_S as '步骤'\n" +
                    "from\n" +
                    "\tMASTER_RECIPE mr\n" +
                    "left join AT_X_Procedure axp on\n" +
                    "\taxp.X_masterRecipe_107 = mr.master_recipe_key\n" +
                    "left join AT_X_UnitProcedure axup on\n" +
                    "\taxup .X_parent_64 = axp.atr_key\n" +
                    "left join AT_X_Operation axo on\n" +
                    "\taxo.X_parent_64 = axup .atr_key\n" +
                    "left join AT_X_Phase axp2 on\n" +
                    "\taxp2.X_parent_64 = axo.atr_key\n" +
                    "LEFT JOIN OBJECT_STATE os ON\n" +
                    "\tos.object_key = mr.master_recipe_key\n" +
                    "LEFT JOIN STATE st ON\n" +
                    "\tst.state_key = os.state_key\n" +
                    "left join PROCESSBOM_UV pbi on\n" +
                    "\tpbi.bom_key = mr.bom_key\n" +
                    "inner join PART_UV p2 on\n" +
                    "\tp2.part_key = pbi.produced_part_key\n" +
                    "where\n" +
                    "\tst.state_name = N'Valid' and mr.master_recipe_name = N'" + recipeName + "' and axp.X_procedureName_S = N'" + processName + "'";
            sql += " and axup.X_unitProcedureName_S = N'" + operationName + "'";
            sql += " and axo.X_operationName_S = N'" + stepName + "'";
            //System.out.println(sql);
            Vector<String[]> list1 = PCContext.getFunctions().getArrayDataFromActive(sql);
            for (String[] strs : list1) {
                String name = strs[0];
                StringConstantIntelliHintDescriptor stringConstantIntelliHintDescriptor2 = new StringConstantIntelliHintDescriptor(name, name, section);
                list.add(stringConstantIntelliHintDescriptor2);

            }
            return list;
        }
        //获取所有的输出
        if (functionCallDescriptor.getIndexOfOpenArgument() == 6) {
            String recipeName = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(1);
            String processName = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(2);
            String operationName = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(3);
            String stepName = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(4);
            String phaseName = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(5);
            recipeName = declear(recipeName);
            processName = declear(processName);
            operationName = declear(operationName);
            stepName = declear(stepName);
            phaseName = declear(phaseName);
            ArrayList<StringConstantIntelliHintDescriptor> list = new ArrayList<>();
//            输出
            String purposrLabel = I18nMessageUtility.getLocalizedMessage("LC_Expression", "OutPut_Label");
            IntelliHintDescriptorsSection section = new IntelliHintDescriptorsSection(1000,
                    purposrLabel,
                    ChoiceElementIntelliHintDescriptor.getGenericChoiceListSectionIcon());
            //获取所有的处方

            //获取状态为有效的处方
            String sql = "select DISTINCT rtphase.atr_key\n" +
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
                    "\twhere mr.master_recipe_name = N'" + recipeName + "'\n" +
                    "\tand pro.X_procedureName_S = N'" + processName + "' and xup.X_unitProcedureName_S = N'" + operationName + "'\n" +
                    "\tand op1.X_operationName_S = N'" + stepName + "' and phase.X_phaseName_S = N'" + phaseName + "'";
            //System.out.println(sql);
            Vector<String[]> list1 = PCContext.getFunctions().getArrayDataFromActive(sql);
            if (list1.size() > 0) {
                list.add(new StringConstantIntelliHintDescriptor("Identifier", "Identifier : String", section));
                list.add(new StringConstantIntelliHintDescriptor("Completion time", "Completion time : Timestamp", section));
                list.add(new StringConstantIntelliHintDescriptor("Start time", "Start time : Timestamp", section));
                list.add(new StringConstantIntelliHintDescriptor("Instance count", "Instance count : Long", section));
                //获取phase输出
                MESRtPhaseFilter t = new MESRtPhaseFilter();
                //SQL获取ATR——key
                t.forATRowKeyEqualTo(Integer.valueOf(list1.get(0)[0]));
                final List<IMESRtPhase> filteredObjects2 = t.getFilteredObjects();
                if(filteredObjects2.size()>0) {
                    List<IBuildingBlockOutputDescriptor> listDesc = filteredObjects2.get(0).getRtPhaseOutput().getOutputDescriptors();
                    //filteredObjects2.get(0).get
                    for (IBuildingBlockOutputDescriptor descriptor : listDesc) {
                        //有效
                        String showValue = descriptor.getDisplayName() + " : " + descriptor.getDataType().getSimpleName();
                        String setValue = descriptor.getName();
                        StringConstantIntelliHintDescriptor stringConstantIntelliHintDescriptor2 = new StringConstantIntelliHintDescriptor(setValue, showValue, section);
                        list.add(stringConstantIntelliHintDescriptor2);
                    }
                }
            }
            return list;
        }
        return Collections.emptyList();
    }

    public String declear(String str) {
        if (str != null && str.contains("\"")) {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }
}
