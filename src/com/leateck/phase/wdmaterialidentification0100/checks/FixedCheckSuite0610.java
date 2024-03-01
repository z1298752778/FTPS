package com.leateck.phase.wdmaterialidentification0100.checks;

import com.leateck.phase.wdmaterialidentification0100.RtPhaseExecutorWDMatIdent0010;
import java.util.*;

import org.apache.commons.lang3.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.*;
import java.io.*;

public class FixedCheckSuite0610 implements ICheckOverrideablePerException0610
{
    private final RtPhaseExecutorWDMatIdent0010 executor;
    private final List<ICheckOverrideablePerException0610> suite;

    public FixedCheckSuite0610(final RtPhaseExecutorWDMatIdent0010 theExecutor) {
        this.executor = theExecutor;
        (this.suite = new ArrayList<ICheckOverrideablePerException0610>()).add((ICheckOverrideablePerException0610) new TriggerRoomInUseTransitionChecker0610(this.executor));
        this.suite.add((ICheckOverrideablePerException0610) new TriggerContainerEmptyTransitionChecker0610(this.executor));
        this.suite.add((ICheckOverrideablePerException0610) new ContainerEntityStatusChecker0610(this.executor));
        this.suite.add((ICheckOverrideablePerException0610) new ContainerClassStatusChecker0610(this.executor));
        this.suite.add((ICheckOverrideablePerException0610) new ContainerPropertyRequirementsChecker0610(this.executor));
    }

    @Override
    public void doCheck() {
        final Iterator<ICheckOverrideablePerException0610> iterator = this.suite.iterator();
        while (iterator.hasNext()) {
            iterator.next().doCheck();
        }
    }

    @Override
    public boolean isCheckFailed() {
        final Iterator<ICheckOverrideablePerException0610> iterator = this.suite.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isCheckFailed()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getExceptionMessage() {
        final StringBuilder sb = new StringBuilder();
        final Iterator<ICheckOverrideablePerException0610> iterator = this.suite.iterator();
        while (iterator.hasNext()) {
            final String exceptionMessage = iterator.next().getExceptionMessage();
            if (sb.length() > 0 && !StringUtils.isEmpty((CharSequence)exceptionMessage)) {
                sb.append(StringConstants.LINE_BREAK);
            }
            sb.append(exceptionMessage);
        }
        return sb.toString();
    }

    @Override
    public String getDialogText() {
        final StringBuilder sb = new StringBuilder();
        final Iterator<ICheckOverrideablePerException0610> iterator = this.suite.iterator();
        while (iterator.hasNext()) {
            final String dialogText = iterator.next().getDialogText();
            if (sb.length() > 0 && !StringUtils.isEmpty((CharSequence)dialogText)) {
                sb.append(StringConstants.LINE_BREAK);
            }
            sb.append(dialogText);
        }
        return sb.toString();
    }

    @Override
    public IMESExceptionRecord.RiskClass getRiskClass() {
        Long n = IMESExceptionRecord.RiskClass.none.longValue();
        final Iterator<ICheckOverrideablePerException0610> iterator = this.suite.iterator();
        while (iterator.hasNext()) {
            n = Math.max(n, iterator.next().getRiskClass().longValue());
        }
        return IMESExceptionRecord.RiskClass.valueOf((long)n);
    }
}
