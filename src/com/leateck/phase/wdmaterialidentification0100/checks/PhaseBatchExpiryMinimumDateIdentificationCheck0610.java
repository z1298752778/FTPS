package com.leateck.phase.wdmaterialidentification0100.checks;

import com.rockwell.mes.shared.phase.matident.check.*;
import java.util.*;

public class PhaseBatchExpiryMinimumDateIdentificationCheck0610 extends PhaseBatchExpiryDateIdentificationCheck0610
{
    private static final String MINIMUM_TIME_TO_EXPIRY_PASSED_EXCEPTION_MSG = "minimumTimeToExpiryPassedException_Msg";

    public PhaseBatchExpiryMinimumDateIdentificationCheck0610(final AbstractPhaseIdentificationCheck0610.CheckType argType, final String argCheckKey, final PhaseCheckConfiguration0610 checkConfig, final List<Object> argParams, final Map<String, Object> context) {
        super(argType, argCheckKey, checkConfig, argParams, context);
    }

    @Override
    public String getAdditionalExceptionMsgCatId() {
        return "minimumTimeToExpiryPassedException_Msg";
    }
}
