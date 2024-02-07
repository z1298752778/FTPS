package com.leateck.phase.wdmaterialidentification0100.checks;

import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.*;

public interface ICheckOverrideablePerException0610
{
    void doCheck();

    String getExceptionMessage();

    String getDialogText();

    IMESExceptionRecord.RiskClass getRiskClass();

    boolean isCheckFailed();
}
