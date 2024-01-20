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
import com.rockwell.mes.commons.base.ifc.services.PCContext;

public class FunctionSpecificIntelliHintsForTestFunctions extends AbstractFunctionSpecificStaticIntelliHintsProvider {
	protected List<? extends IStaticIntelliHintDescriptor> getIntelliHintsForFunctionCall(
			final IExpressionOpenFunctionCallDescriptor functionCallDescriptor) {
		//处方编号

		if (functionCallDescriptor.getIndexOfOpenArgument() == 1) {
			 ArrayList<StringConstantIntelliHintDescriptor> list = new ArrayList<>();
			IntelliHintDescriptorsSection section = new IntelliHintDescriptorsSection(1000,
					"处方",
					ChoiceElementIntelliHintDescriptor.getGenericChoiceListSectionIcon());
			 //获取所有的处方

			//获取状态为有效的处方
			String sql = "select\n" +
					"\tmr.description as '处方描述',\n" +
					"\tmr.master_recipe_name as '处方名',\n" +
					"\tst.state_name as '处方状态',\n" +
					"\tp2.part_number as '物料编码',\n" +
					"\tp2.description as '物料名称',\n" +
					"\taxp.X_procedureName_S as '工序',\n" +
					"\taxup.X_unitProcedureName_S as '单位工序' ,\n" +
					"\taxo.X_operationName_S as '操作' ,\n" +
					"\taxp2.X_phaseName_S as '步骤',\n" +
					"\taxp2.X_description_S as '标记'\n" +
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
			System.out.println(sql);
			Vector<String[]> list1 = PCContext.getFunctions().getArrayDataFromActive(sql);
			for (String[] strs : list1) {
				String name = strs[1];
				StringConstantIntelliHintDescriptor stringConstantIntelliHintDescriptor2 = new StringConstantIntelliHintDescriptor(name, name, section);
				list.add(stringConstantIntelliHintDescriptor2);
			}
			return list;
//			return Arrays.asList(com.leateck.expression.ITest.DataType.values()).stream()
//					.map(dataType -> new StringConstantIntelliHintDescriptor("输入的值",
//							"显示提示",
//							(IIntelliHintDescriptorsSection) new IntelliHintDescriptorsSection(1000,
//									"标题",
//									ChoiceElementIntelliHintDescriptor.getGenericChoiceListSectionIcon())))
//					.collect(Collectors.toList());

		}
		//工序名称
		if (functionCallDescriptor.getIndexOfOpenArgument() == 2) {
			String recipeName = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(1);
			ArrayList<StringConstantIntelliHintDescriptor> list = new ArrayList<>();
			IntelliHintDescriptorsSection section = new IntelliHintDescriptorsSection(1000,
					"工序",
					ChoiceElementIntelliHintDescriptor.getGenericChoiceListSectionIcon());
			//获取所有的处方

			//获取状态为有效的处方
			String sql = "select\n" +
					"\tmr.description as '处方描述',\n" +
					"\tmr.master_recipe_name as '处方名',\n" +
					"\tst.state_name as '处方状态',\n" +
					"\tp2.part_number as '物料编码',\n" +
					"\tp2.description as '物料名称',\n" +
					"\taxp.X_procedureName_S as '工序',\n" +
					"\taxup.X_unitProcedureName_S as '单位工序' ,\n" +
					"\taxo.X_operationName_S as '操作' ,\n" +
					"\taxp2.X_phaseName_S as '步骤',\n" +
					"\taxp2.X_description_S as '标记'\n" +
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
					"\tst.state_name = N'Valid' and mr.master_recipe_name = N'"+recipeName+"'";
			System.out.println(sql);
			Vector<String[]> list1 = PCContext.getFunctions().getArrayDataFromActive(sql);
			for (String[] strs : list1) {
				String name = strs[1];
				StringConstantIntelliHintDescriptor stringConstantIntelliHintDescriptor2 = new StringConstantIntelliHintDescriptor(name, name, section);
				list.add(stringConstantIntelliHintDescriptor2);

			}
			return list;
		}

		return Collections.emptyList();
	}
}
