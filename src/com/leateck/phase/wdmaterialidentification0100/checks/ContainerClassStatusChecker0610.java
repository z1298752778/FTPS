package com.leateck.phase.wdmaterialidentification0100.checks;

import com.leateck.phase.wdmaterialidentification0100.RtPhaseExecutorWDMatIdent0010;
import com.rockwell.mes.services.eqm.ifc.*;
import com.rockwell.mes.services.s88.ifc.execution.equipment.*;
import com.rockwell.mes.services.s88.ifc.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import com.rockwell.mes.services.s88equipment.ifc.*;
import com.rockwell.mes.commons.base.ifc.i18n.*;
import com.rockwell.mes.phase.product.wdmaterialidentification.*;
import com.rockwell.mes.parameter.eqmstatuscheckdef.*;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.*;

public class ContainerClassStatusChecker0610 implements ICheckOverrideablePerException0610
{
    protected final RtPhaseExecutorWDMatIdent0010 executor;
    private IMESContainerEquipment container;
    private boolean isCheckFailed;
    private EquipmentClassStatusValidationErrorResult classStatusValidationError;

    ContainerClassStatusChecker0610(final RtPhaseExecutorWDMatIdent0010 theExecutor) {
        this.executor = theExecutor;
    }

    @Override
    public void doCheck() {
        this.container = (this.executor.getModel()).getCurrentSourceContainer();
        if (this.container != null) {
            this.classStatusValidationError = ((IS88EquipmentExecutionService)ServiceFactory.getService((Class)IS88EquipmentExecutionService.class)).checkClassState(this.container.getS88Equipment(), this.executor.getRtPhase(), this.executor.getParamContainerStatusCheck().getMinimumClassStatus());
            if (this.classStatusValidationError != null && EquipmentValidationErrorResult.EqmValidationFailureType.EQM_INVALID_CLASS_STATUS.equals((Object)this.classStatusValidationError.getFailureType())) {
                this.isCheckFailed = true;
                final IMESS88EquipmentClass validatedClass = this.classStatusValidationError.getValidatedClass();
                if (validatedClass != null) {
                    this.writeLogbookDenialEntry(validatedClass);
                }
            }
        }
    }

    private void writeLogbookDenialEntry(final IMESS88EquipmentClass eqClass) {
        ((IS88EquipmentExecutionService)ServiceFactory.getService((Class)IS88EquipmentExecutionService.class)).writeLogbookEntryDenialClassStatus(this.container.getS88Equipment(), eqClass, this.executor.getRtPhase());
    }

    @Override
    public String getExceptionMessage() {
        if (this.isCheckFailed) {
            final MESParamEqStatCheckDef0300 paramContainerStatusCheck = this.executor.getParamContainerStatusCheck();
            final String minimumClassStatus = paramContainerStatusCheck.getMinimumClassStatus();
            final IMESS88EquipmentClass validatedClass = this.classStatusValidationError.getValidatedClass();
            return ExceptionHelper0610.buildExceptionText(paramContainerStatusCheck.getExceptionText(), I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "containerClassStatusFailureException_Msg", new Object[] { this.container.getName(), validatedClass.getIdentifier(), validatedClass.getStatus(), minimumClassStatus }));
        }
        return "";
    }

    @Override
    public String getDialogText() {
        if (this.isCheckFailed) {
            final IMESS88EquipmentClass validatedClass = this.classStatusValidationError.getValidatedClass();
            return I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "containerClassStatusFailureException_Dialog", new Object[] { this.container.getName(), validatedClass.getIdentifier(), validatedClass.getStatus() });
        }
        return "";
    }

    @Override
    public IMESExceptionRecord.RiskClass getRiskClass() {
        if (this.isCheckFailed) {
            return IMESExceptionRecord.RiskClass.valueOf((long)this.executor.getParamContainerStatusCheck().getRiskAssessment());
        }
        return IMESExceptionRecord.RiskClass.none;
    }

    @Override
    public boolean isCheckFailed() {
        return this.isCheckFailed;
    }
}
