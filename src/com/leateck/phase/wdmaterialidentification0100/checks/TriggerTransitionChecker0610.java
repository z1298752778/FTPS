package com.leateck.phase.wdmaterialidentification0100.checks;

import com.leateck.phase.wdmaterialidentification0100.RtPhaseExecutorWDMatIdent0010;
import com.rockwell.mes.shared.product.wd.*;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.*;

public abstract class TriggerTransitionChecker0610 implements ICheckOverrideablePerException0610
{
    protected final RtPhaseExecutorWDMatIdent0010 executor;
    protected StatusTransitionFailureHelper0610.StatusTransitionFailureSupport transitionFailure;

    protected TriggerTransitionChecker0610(final RtPhaseExecutorWDMatIdent0010 theExecutor) {
        this.executor = theExecutor;
    }

    @Override
    public String getExceptionMessage() {
        return (this.transitionFailure != null) ? this.transitionFailure.getExceptionMsg() : "";
    }

    @Override
    public String getDialogText() {
        return (this.transitionFailure != null) ? this.transitionFailure.getDialogMsg() : "";
    }

    @Override
    public IMESExceptionRecord.RiskClass getRiskClass() {
        return (this.transitionFailure != null) ? this.transitionFailure.getRiskClass() : IMESExceptionRecord.RiskClass.none;
    }

    @Override
    public boolean isCheckFailed() {
        return this.transitionFailure != null;
    }
}
