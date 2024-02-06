package com.leateck.phase.wdmaterialidentification0100;

import com.datasweep.compatibility.client.*;
import com.leateck.phase.wdmaterialidentification0100.checks.FixedCheckSuite0610;
import com.leateck.phase.wdmaterialidentification0100.checks.PhaseIdentificationCheckSuite0610;
import com.rockwell.mes.services.eqm.ifc.*;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.*;
import com.rockwell.mes.apps.ebr.ifc.phase.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.*;
import com.rockwell.mes.shared.product.wd.*;
import com.rockwell.mes.commons.base.ifc.choicelist.*;
import com.rockwell.mes.clientfw.commons.ifc.swing.*;
import com.rockwell.mes.services.inventory.ifc.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import java.io.*;

enum IdentificationMode0610
{
    SCAN_BARCODE {
        @Override
        IMESExceptionRecord.RiskClass getRiskClass( RtPhaseExecutorWDMatIdent0010 executor,  PhaseIdentificationCheckSuite0610 suite,  FixedCheckSuite0610 additionalChecks) {
            return IMESExceptionRecord.RiskClass.valueOf(MESChoiceListHelper.getChoiceElement("RiskClass", Math.max(suite.getMaxRisk(), additionalChecks.getRiskClass().longValue())));
        }

        @Override
        String getCheckKey() {
            return "EXCEPTION_KEY_IDENTIFICATION";
        }

        @Override
        public IIdentifiedItem0610 fetchIdentifiedItem(final RtPhaseExecutorWDMatIdent0010 executor, final Object arg) {
            return executor.getBarcodeProcessor().processBarcode((String)arg, (IBarcodeScannedListener)executor);
        }

        @Override
        boolean isSublotIdentification(final Object arg) {
            return ((IMFCService)ServiceFactory.getService((Class)IMFCService.class)).isValidSublotOrBatchBarcode((String)arg);
        }

        @Override
        void triggerAutoComplete(final RtPhaseExecutorWDMatIdent0010 executor) {
            ((MatIdentView0610)executor.getView()).firePhaseConfirmed();
        }

        @Override
        String getExtendedExceptionMessage(final RtPhaseExecutorWDMatIdent0010 executor, final PhaseIdentificationCheckSuite0610 suite, final FixedCheckSuite0610 additionalChecks, final Sublot sublot, final IMESContainerEquipment sourceContainer) {
            return getExtendedExceptionMessageSuiteAndOthers(suite, additionalChecks, sublot);
        }

        @Override
        CheckSuiteEvaluationResult handleCheckResult(final RtPhaseExecutorWDMatIdent0010 executor, final PhaseIdentificationCheckSuite0610 suite, final FixedCheckSuite0610 additionalChecks, final Sublot sublot, final IMESContainerEquipment sourceContainer) {
            final CheckSuiteEvaluationResult evaluateCheckSuiteResult = this.evaluateCheckSuiteResult(executor, suite, additionalChecks, sublot, sourceContainer);
            if (evaluateCheckSuiteResult == CheckSuiteEvaluationResult.SUCCESS) {
                executor.doIdentification(sublot);
            }
            return evaluateCheckSuiteResult;
        }
    },
    MANUAL {
        @Override
        IMESExceptionRecord.RiskClass getRiskClass(final RtPhaseExecutorWDMatIdent0010 executor, final PhaseIdentificationCheckSuite0610 suite, final FixedCheckSuite0610 additionalChecks) {
            return IMESExceptionRecord.RiskClass.valueOf(MESChoiceListHelper.getChoiceElement("RiskClass", Math.max(Long.valueOf(Math.max(ManualIdentificationHelper0610.getManualIdentRisk((IPhaseExecutor)executor).longValue(), suite.getMaxRisk())), additionalChecks.getRiskClass().longValue())));
        }

        @Override
        String getCheckKey() {
            return "MANUAL_IDENTIFICATION";
        }

        @Override
        public IIdentifiedItem0610 fetchIdentifiedItem(final RtPhaseExecutorWDMatIdent0010 executor, final Object arg) {
            return ManualIdentificationHelper0610.getIdentifiedItemByManualIdentificationModel((MatIdentExceptionView0610.ManualIdentificationModel)arg);
        }

        @Override
        boolean isSublotIdentification(final Object arg) {
            return ((MatIdentExceptionView0610.ManualIdentificationModel)arg).hasSublot();
        }

        @Override
        void triggerAutoComplete(final RtPhaseExecutorWDMatIdent0010 executor) {
            executor.triggerAutoCompleteAfterReturnFromExceptionView();
            executor.closeExceptionView();
        }

        @Override
        String getExtendedExceptionMessage(final RtPhaseExecutorWDMatIdent0010 executor, final PhaseIdentificationCheckSuite0610 suite, final FixedCheckSuite0610 additionalChecks, final Sublot sublot, final IMESContainerEquipment sourceContainer) {
            return ManualIdentificationHelper0610.getManualIdentExceptionText((IPhaseExecutor)executor, sublot, sourceContainer, getExtendedExceptionMessageSuiteAndOthers(suite, additionalChecks, sublot));
        }

        @Override
        CheckSuiteEvaluationResult handleCheckResult(final RtPhaseExecutorWDMatIdent0010 executor, final PhaseIdentificationCheckSuite0610 suite, final FixedCheckSuite0610 additionalChecks, final Sublot sublot, final IMESContainerEquipment sourceContainer) {
            final CheckSuiteEvaluationResult evaluateCheckSuiteResult = this.evaluateCheckSuiteResult(executor, suite, additionalChecks, sublot, sourceContainer);
            if (evaluateCheckSuiteResult == CheckSuiteEvaluationResult.SUCCESS) {
                executor.displayException("MANUAL_IDENTIFICATION", this.getRiskClass(executor, suite, additionalChecks), this.getExtendedExceptionMessage(executor, suite, additionalChecks, sublot, sourceContainer));
            }
            return evaluateCheckSuiteResult;
        }
    };

    protected CheckSuiteEvaluationResult evaluateCheckSuiteResult(final RtPhaseExecutorWDMatIdent0010 executor, final PhaseIdentificationCheckSuite0610 suite, final FixedCheckSuite0610 additionalChecks, final Sublot sublot, final IMESContainerEquipment sourceContainer) {
        if (!suite.getErrorMessage().isEmpty()) {
            WDHelper0610.showErrorDialog(suite.getErrorMessage());
            return CheckSuiteEvaluationResult.ERROR;
        }
        if (executor.vetoIdentification(sublot, suite)) {
            return CheckSuiteEvaluationResult.ABORT;
        }
        if (!suite.getCheckSuite().hasExceptionsButNoErrors() && !additionalChecks.isCheckFailed()) {
            return CheckSuiteEvaluationResult.SUCCESS;
        }
        if (PhaseSystemTriggeredExceptionHandler.recordException((IPhaseExecutor)executor, this.getDialogText(suite, additionalChecks, sublot), this.getExtendedExceptionMessage(executor, suite, additionalChecks, sublot, sourceContainer), this.getRiskClass(executor, suite, additionalChecks), this.getCheckKey()) == 0) {
            return CheckSuiteEvaluationResult.EXCEPTION;
        }
        return CheckSuiteEvaluationResult.ABORT;
    }

    private String getDialogText(final PhaseIdentificationCheckSuite0610 suite, final FixedCheckSuite0610 additionalChecks, final Sublot sublot) {
        final StringBuilder sb = new StringBuilder(suite.getExceptionMessage(IdentifiedItem0610.createItem(sublot)));
        if (sb.length() > 0) {
            sb.append(StringConstants.LINE_BREAK);
        }
        sb.append(additionalChecks.getDialogText());
        return sb.toString();
    }

    abstract IMESExceptionRecord.RiskClass getRiskClass(final RtPhaseExecutorWDMatIdent0010 executor, final PhaseIdentificationCheckSuite0610 suite, final FixedCheckSuite0610 additionalChecks);

    abstract String getCheckKey();

    public abstract IIdentifiedItem0610 fetchIdentifiedItem(final RtPhaseExecutorWDMatIdent0010 executor, final Object arg);

    abstract boolean isSublotIdentification(final Object arg);

    static IdentificationMode0610 valueOf(final Object arg) {
        if (arg instanceof String) {
            return IdentificationMode0610.SCAN_BARCODE;
        }
        if (arg instanceof MatIdentExceptionView0610.ManualIdentificationModel) {
            return IdentificationMode0610.MANUAL;
        }
        throw new IllegalArgumentException("Unsupported argument class: " + arg);
    }

    private static String getExtendedExceptionMessageSuiteAndOthers(final PhaseIdentificationCheckSuite0610 suite, final FixedCheckSuite0610 additionalChecks, final Sublot sublot) {
        final StringBuilder sb = new StringBuilder(suite.getExtendedExceptionMessage(IdentifiedItem0610.createItem(sublot)));
        if (sb.length() > 0) {
            sb.append(StringConstants.LINE_BREAK);
        }
        sb.append(additionalChecks.getExceptionMessage());
        return sb.toString();
    }

    abstract void triggerAutoComplete(final RtPhaseExecutorWDMatIdent0010 executor);

    abstract String getExtendedExceptionMessage(final RtPhaseExecutorWDMatIdent0010 executor, final PhaseIdentificationCheckSuite0610 suite, final FixedCheckSuite0610 additionalChecks, final Sublot sublot, final IMESContainerEquipment sourceContainer);

    abstract CheckSuiteEvaluationResult handleCheckResult(final RtPhaseExecutorWDMatIdent0010 executor, final PhaseIdentificationCheckSuite0610 suite, final FixedCheckSuite0610 additionalChecks, final Sublot sublot, final IMESContainerEquipment sourceContainer);

    enum CheckSuiteEvaluationResult
    {
        SUCCESS,
        ERROR,
        EXCEPTION,
        ABORT;
    }
}
