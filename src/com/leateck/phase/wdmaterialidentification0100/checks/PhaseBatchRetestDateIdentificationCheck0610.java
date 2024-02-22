package com.leateck.phase.wdmaterialidentification0100.checks;

import com.rockwell.mes.shared.phase.matident.check.*;
import java.util.*;
import com.rockwell.mes.shared.phase.matident.check.identcheck.*;

public class PhaseBatchRetestDateIdentificationCheck0610 extends PhaseBatchAgainstDateIdentificationCheck0610
{
    private static final String MSG_ID_CHECK_RETEST_DATE_EXPECTED_VALUE_TXT = "CheckRetestDate_ExpectedValueTxt";
    private static final String MSG_ID_CHECK_RETEST_DATE_ACTUAL_VALUE_TXT = "CheckRetestDate_ActualValueTxt";

    public PhaseBatchRetestDateIdentificationCheck0610(final AbstractPhaseIdentificationCheck0610.CheckType argType, final String argCheckKey, final PhaseCheckConfiguration0610 checkConfig, final List<Object> argParams, final Map<String, Object> context) {
        super(argType, "CheckBatchExpiryDate", argCheckKey, checkConfig, argParams, context);
    }

    @Override
    protected AbstractCheckBatchAgainstDate0610 createCheckBatchAgainstDate() {
        return (AbstractCheckBatchAgainstDate0610)new CheckBatchRetestDateCustomized0610();
    }

    @Override
    public String getExpectedValueMsgCatId() {
        return "CheckRetestDate_ExpectedValueTxt";
    }

    @Override
    public String getActualValueMsgCatId() {
        return "CheckRetestDate_ActualValueTxt";
    }
}
