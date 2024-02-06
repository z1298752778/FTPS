package com.leateck.phase.wdmaterialidentification0100.checks;

import com.leateck.phase.wdmaterialidentification0100.RtPhaseExecutorWDMatIdent0010;
import com.rockwell.mes.services.eqm.ifc.*;
import com.rockwell.mes.services.s88.ifc.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import com.rockwell.mes.commons.base.ifc.i18n.*;
import com.rockwell.mes.phase.product.wdmaterialidentification.*;
import com.rockwell.mes.services.s88.ifc.recipe.equipment.*;
import java.util.*;
import com.rockwell.mes.services.s88.ifc.execution.equipment.*;
import com.rockwell.mes.services.s88equipment.ifc.*;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.*;

public class ContainerPropertyRequirementsChecker0610 implements ICheckOverrideablePerException0610
{
    private static final String ERROR_EQUIPMENT_PROPERTY_VALUE_EXC = "PropertyValueCheck_ExceptionMsg";
    private static final String ERROR_EQUIPMENT_PROPERTY_VALUE_RULE_EXC = "PropertyValueCheck_Rule_ExceptionMsg";
    private final RtPhaseExecutorWDMatIdent0010 executor;
    private IMESContainerEquipment container;
    private boolean isCheckFailed;
    private EquipmentReqValidationErrorResult propertyRequirementsValidationError;

    ContainerPropertyRequirementsChecker0610(final RtPhaseExecutorWDMatIdent0010 theExecutor) {
        this.executor = theExecutor;
    }

    @Override
    public void doCheck() {
        this.container = (this.executor.getModel()).getCurrentSourceContainer();
        if (this.container != null) {
            final IS88EquipmentExecutionService is88EquipmentExecutionService = (IS88EquipmentExecutionService)ServiceFactory.getService((Class)IS88EquipmentExecutionService.class);
            this.propertyRequirementsValidationError = is88EquipmentExecutionService.checkPropertyRequirements(this.container.getS88Equipment(), this.executor.getRtPhase());
            if (this.propertyRequirementsValidationError != null) {
                this.isCheckFailed = true;
                is88EquipmentExecutionService.writeLogbookEntryDenialPropertyReqs(this.container.getS88Equipment(), this.executor.getRtPhase());
            }
        }
    }

    @Override
    public String getExceptionMessage() {
        if (this.isCheckFailed) {
            return ExceptionHelper0610.buildExceptionText(this.executor.getParamPropertyValueCheck().getExceptionDescr(), I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "eqRequirementNotSufficientException_Msg", new Object[] { this.container.getS88Equipment().getIdentifier() }) + this.createConcatenatedPropertyValueCheckExceptionMsg(this.propertyRequirementsValidationError));
        }
        return "";
    }

    private String createConcatenatedPropertyValueCheckExceptionMsg(final EquipmentReqValidationErrorResult result) {
        final StringBuilder sb = new StringBuilder();
        for (final EquipmentPropertyValidationResult equipmentPropertyValidationResult : result.getPropertiesResult()) {
            final IMESEquipmentPropertyReq propReq = (IMESEquipmentPropertyReq)equipmentPropertyValidationResult.getRequired();
            final String identifier = propReq.getIdentifier();
            final String uiRepresentation = propReq.getUIRepresentation();
            final String requiredValue = this.getRequiredValue(equipmentPropertyValidationResult, propReq);
            final String actualPropertyValue = this.getActualPropertyValue(equipmentPropertyValidationResult);
            if (equipmentPropertyValidationResult instanceof EquipmentRuleValidationResult) {
                sb.append(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "PropertyValueCheck_Rule_ExceptionMsg", (Object[])new String[] { identifier, uiRepresentation, requiredValue, actualPropertyValue }));
            }
            else {
                final IMESEquipmentPropertyType equipmentPropertyType = propReq.getEquipmentPropertyType();
                sb.append(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "PropertyValueCheck_ExceptionMsg", (Object[])new String[] { identifier, uiRepresentation, (equipmentPropertyType != null) ? equipmentPropertyType.getIdentifier() : "", requiredValue, actualPropertyValue }));
            }
        }
        return sb.toString();
    }

    private String getRequiredValue(final EquipmentPropertyValidationResult validationResult, final IMESEquipmentPropertyReq propReq) {
        String s;
        if (validationResult instanceof EquipmentRuleValidationResult) {
            s = I18nMessageUtility.getI18NValue(true);
        }
        else if (propReq != null) {
            s = propReq.getValueOnlyDisplayString();
        }
        else {
            s = "";
        }
        return s;
    }

    private String getActualPropertyValue(final EquipmentPropertyValidationResult validationResult) {
        final IMESEquipmentProperty property = validationResult.getProperty();
        String s;
        if (property instanceof IMESFSMEquipmentProperty) {
            s = ((property.getPropertyValue() != null) ? property.getPropertyValue().toString() : "");
        }
        else if (validationResult instanceof EquipmentRuleValidationResult) {
            s = I18nMessageUtility.getI18NValue(false);
        }
        else if (validationResult instanceof EquipmentRuleEvaluationFailure) {
            s = I18nMessageUtility.getI18NValue((Boolean)null);
        }
        else if (property != null) {
            s = property.getValueOnlyDisplayString();
        }
        else {
            s = "";
        }
        return s;
    }

    @Override
    public String getDialogText() {
        if (this.isCheckFailed) {
            return I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "eqRequirementNotSufficientException_Dialog", new Object[] { this.container.getS88Equipment().getIdentifier() });
        }
        return "";
    }

    @Override
    public IMESExceptionRecord.RiskClass getRiskClass() {
        if (this.isCheckFailed) {
            return IMESExceptionRecord.RiskClass.valueOf((long)this.executor.getParamPropertyValueCheck().getRiskAssessment());
        }
        return IMESExceptionRecord.RiskClass.none;
    }

    @Override
    public boolean isCheckFailed() {
        return this.isCheckFailed;
    }
}
