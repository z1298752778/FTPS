package com.leateck.phase.wdmaterialidentification0100.checks;

import com.leateck.phase.wdmaterialidentification0100.RtPhaseExecutorWDMatIdent0010;
import com.rockwell.mes.services.s88.ifc.*;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import com.rockwell.mes.phase.product.wdmaterialidentification.*;
import java.util.stream.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import com.rockwell.mes.commons.base.ifc.i18n.*;
import com.rockwell.mes.services.eqm.ifc.*;
import com.rockwell.mes.parameter.eqmstatuscheckdef.*;
import java.util.*;
import com.rockwell.mes.services.s88.ifc.execution.equipment.*;
import com.rockwell.mes.commons.base.ifc.choicelist.*;
import com.rockwell.mes.services.s88equipment.ifc.*;

public class ContainerEntityStatusChecker0610 implements ICheckOverrideablePerException0610
{
    protected final RtPhaseExecutorWDMatIdent0010 executor;
    private static final String ERROR_EQUIPMENT_REQUIRED_MIN_STATUS_LABEL = "RequiredMinimumStatus_Label";
    private static final String ERROR_EQUIPMENT_MULTIPLE_EXCEPTIONS = "MultipleExceptionsTxt";
    private boolean isCheckFailed;
    private List<IMESS88Equipment> failed;
    private IS88EquipmentExecutionService eqmS88ExecutionService;
    private String validationErrorMsg;
    private String dialogMsg;
    private IMESExceptionRecord.RiskClass maxRisk;

    ContainerEntityStatusChecker0610(final RtPhaseExecutorWDMatIdent0010 theExecutor) {
        this.failed = Collections.emptyList();
        this.eqmS88ExecutionService = (IS88EquipmentExecutionService)ServiceFactory.getService((Class)IS88EquipmentExecutionService.class);
        this.validationErrorMsg = "";
        this.dialogMsg = "";
        this.maxRisk = null;
        this.executor = theExecutor;
    }

    @Override
    public void doCheck() {
        this.dialogMsg = "";
        this.validationErrorMsg = "";
        this.maxRisk = null;
        final IMESContainerEquipment currentSourceContainer = (this.executor.getModel()).getCurrentSourceContainer();
        if (currentSourceContainer != null) {
            final MESParamEqStatCheckDef0300 paramContainerStatusCheck = this.executor.getParamContainerStatusCheck();
            final String minimumEntityStatus = paramContainerStatusCheck.getMinimumEntityStatus();
            this.failed = (List<IMESS88Equipment>)currentSourceContainer.getS88Equipment().getRootEntity().getGroup().stream().filter(eq -> this.hasInvalidStatus(minimumEntityStatus, eq)).collect(Collectors.toList());
            if (!this.failed.isEmpty()) {
                this.isCheckFailed = true;
                this.failed.forEach(this::writeLogbookEntryDenial);
                final Iterator<IMESS88Equipment> iterator = this.failed.iterator();
                while (iterator.hasNext()) {
                    final EquipmentEntityStatusValidationErrorResult checkEntityState = this.eqmS88ExecutionService.checkEntityState((IMESS88Equipment)iterator.next(), this.executor.getRtPhase(), minimumEntityStatus);
                    this.dialogMsg = paramContainerStatusCheck.getExceptionText() + StringConstants.LINE_BREAK + checkEntityState.getI18nErrorMessage();
                    final StringBuilder sb = new StringBuilder();
                    sb.append(paramContainerStatusCheck.getExceptionText()).append(StringConstants.LINE_BREAK).append(checkEntityState.getI18nErrorMessage()).append(StringConstants.LINE_BREAK).append(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "RequiredMinimumStatus_Label", (Object[])new String[] { minimumEntityStatus }));
                    this.addValidationError(this.dialogMsg, sb.toString(), this.getRiskClass(paramContainerStatusCheck.getRiskAssessment()));
                }
            }
        }
    }

    private IMESExceptionRecord.RiskClass getRiskClass(final Long riskAssessment) {
        return IMESExceptionRecord.RiskClass.valueOf(MESChoiceListHelper.getChoiceElement("RiskClass", riskAssessment));
    }

    private boolean hasInvalidStatus(final String minimumEntityStateName, final IMESS88Equipment eq) {
        final EquipmentEntityStatusValidationErrorResult checkEntityState = this.eqmS88ExecutionService.checkEntityState(eq, this.executor.getRtPhase(), minimumEntityStateName);
        return checkEntityState != null && EquipmentValidationErrorResult.EqmValidationFailureType.EQM_INVALID_ENTITY_STATUS.equals((Object)checkEntityState.getFailureType());
    }

    private void writeLogbookEntryDenial(final IMESS88Equipment entity) {
        this.eqmS88ExecutionService.writeLogbookEntryDenialEntityStatus(entity, this.executor.getRtPhase());
    }

    private void addValidationError(final String singleDialogMsg, final String singleExceptionMsg, final IMESExceptionRecord.RiskClass theSingleRisk) {
        if (this.validationErrorMsg.isEmpty()) {
            this.dialogMsg = singleDialogMsg;
            this.maxRisk = theSingleRisk;
            this.validationErrorMsg = singleExceptionMsg;
        }
        else {
            this.dialogMsg = I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "MultipleExceptionsTxt");
            if (this.maxRisk.compareTo(theSingleRisk) < 0) {
                this.maxRisk = theSingleRisk;
            }
            this.validationErrorMsg = this.validationErrorMsg + StringConstants.LINE_BREAK + StringConstants.LINE_BREAK;
            this.validationErrorMsg += singleExceptionMsg;
        }
    }

    @Override
    public String getExceptionMessage() {
        if (this.isCheckFailed) {
            return this.validationErrorMsg;
        }
        return "";
    }

    @Override
    public String getDialogText() {
        if (this.isCheckFailed) {
            return this.dialogMsg;
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
