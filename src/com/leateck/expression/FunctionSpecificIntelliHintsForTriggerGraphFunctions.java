package com.leateck.expression;

import com.datasweep.compatibility.client.DatasweepException;
import com.rockwell.mes.clientfw.docking.ifc.expression.editor.codeeditor.intellihints.AbstractFunctionSpecificStaticIntelliHintsProvider;
import com.rockwell.mes.clientfw.docking.ifc.expression.editor.codeeditor.intellihints.IExpressionOpenFunctionCallDescriptor;
import com.rockwell.mes.clientfw.docking.ifc.expression.editor.intellihints.ChoiceElementIntelliHintDescriptor;
import com.rockwell.mes.clientfw.docking.ifc.expression.editor.intellihints.IStaticIntelliHintDescriptor;
import com.rockwell.mes.clientfw.docking.ifc.expression.editor.intellihints.IntelliHintDescriptorsSection;
import com.rockwell.mes.clientfw.docking.ifc.expression.editor.intellihints.StringConstantIntelliHintDescriptor;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.choicelist.MESChoiceListHelper;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IMESS88StatusGraph;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IMESS88StatusGraphFilter;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IMESS88StatusGraphTrigger;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IS88StatusGraphService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionSpecificIntelliHintsForTriggerGraphFunctions extends AbstractFunctionSpecificStaticIntelliHintsProvider {
    protected List<? extends IStaticIntelliHintDescriptor> getIntelliHintsForFunctionCall(
            final IExpressionOpenFunctionCallDescriptor functionCallDescriptor) {
        //目的名称
        if (functionCallDescriptor.getIndexOfOpenArgument() == 2) {
            ArrayList<StringConstantIntelliHintDescriptor> list = new ArrayList<>();
            //目的名称 Purposr_Label
             String purposrLabel = I18nMessageUtility.getLocalizedMessage("LC_Expression", "Purposr_Label");
            IntelliHintDescriptorsSection section = new IntelliHintDescriptorsSection(1000,
                    purposrLabel,
                    ChoiceElementIntelliHintDescriptor.getGenericChoiceListSectionIcon());
            IMESS88StatusGraphFilter var3 = ((IS88StatusGraphService) ServiceFactory.getService(IS88StatusGraphService.class)).createEquipmentStatusGraphFilter();
            final List<IMESS88StatusGraph> filteredObjects = var3.getFilteredObjects();
            ArrayList<String> list1 = new ArrayList<>();
            for (IMESS88StatusGraph filteredObject : filteredObjects) {
                //触发器键
                //final List<IMESS88StatusGraphTrigger> triggers = filteredObject.getTriggers();
                final IMESChoiceElement purpose = filteredObject.getPurpose();
                //目的
                final String meaning = purpose.getMeaning();

                final String localizedMessage = purpose.getLocalizedMessage();
                Boolean isAdd = true;
                for (String s : list1) {
                    if(localizedMessage != null && localizedMessage.equals(s)){
                        isAdd = false;
                    }
                }
                list1.add(localizedMessage);
                if(isAdd){
                    StringConstantIntelliHintDescriptor stringConstantIntelliHintDescriptor2 = new StringConstantIntelliHintDescriptor(meaning, localizedMessage, section);
                    list.add(stringConstantIntelliHintDescriptor2);
                }

            }
            return list;
        }
        //触发器键
        if (functionCallDescriptor.getIndexOfOpenArgument() == 3) {
            String name = functionCallDescriptor.getArgumentPrecedingTheOpenArgument(2);
            name = declear(name);
            ArrayList<StringConstantIntelliHintDescriptor> list = new ArrayList<>();
            //允许的触发器名称
            String purposrLabel = I18nMessageUtility.getLocalizedMessage("LC_Expression", "TriggerName_Label");

            IntelliHintDescriptorsSection section = new IntelliHintDescriptorsSection(1000,
                    purposrLabel,
                    ChoiceElementIntelliHintDescriptor.getGenericChoiceListSectionIcon());
            IMESS88StatusGraphFilter var3 = ((IS88StatusGraphService) ServiceFactory.getService(IS88StatusGraphService.class)).createEquipmentStatusGraphFilter();
            //获取key
             IMESChoiceElement s88StatusGraphPurpose = MESChoiceListHelper.getChoiceElement("S88StatusGraphPurpose", name);
             long key = s88StatusGraphPurpose.getValue();
            //final long key1 = s88StatusGraphPurpose.getKey();
            try {
                var3.forPurposeEqualTo(key);
            } catch (DatasweepException e) {
                throw new RuntimeException(e);
            }
            final List<IMESS88StatusGraph> filteredObjects = var3.getFilteredObjects();
            if(filteredObjects.size()>0){
                 IMESS88StatusGraph filteredObject = filteredObjects.get(0);
                //触发器键
                final List<IMESS88StatusGraphTrigger> triggers = filteredObject.getTriggers();
                if(triggers.size()>0){
                    for (IMESS88StatusGraphTrigger trigger : triggers) {
//                        final IMESS88StatusGraphTrigger trigger = triggers.get(0);
                        String displayString = trigger.getDisplayString();
                        String identifier = trigger.getIdentifier();
                        //String purposeAsMeaning = trigger.getStatusGraph().getPurposeAsMeaning();
                        StringConstantIntelliHintDescriptor stringConstantIntelliHintDescriptor2 = new StringConstantIntelliHintDescriptor(identifier, displayString, section);
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
