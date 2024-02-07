package com.leateck.phase.wdmaterialidentification0100.checks;

import com.rockwell.mes.shared.product.wd.checks.*;
import com.rockwell.mes.shared.product.wd.*;
import com.rockwell.mes.shared.phase.matident.check.*;
import com.datasweep.compatibility.client.*;
import com.datasweep.compatibility.ui.*;
import java.util.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import com.rockwell.mes.commons.base.ifc.nameduda.*;
import com.rockwell.mes.services.inventory.ifc.checks.*;
import com.rockwell.mes.commons.base.ifc.functional.*;
import com.rockwell.mes.commons.base.ifc.services.*;

public class PhaseSublotUseByDateIdentificationCheck0610 extends AbstractPhaseIdentificationCheckWD0610<IIdentifiedItem0610>
{
    private static final String MSG_ID_CHECK_USE_BY_DATE_EXPECTED_VALUE_TXT = "CheckUseByDate_ExpectedValueTxt";
    private static final String MSG_ID_CHECK_USE_BY_DATE_ACTUAL_VALUE_TXT = "CheckUseByDate_ActualValueTxt";

    public PhaseSublotUseByDateIdentificationCheck0610(final AbstractPhaseIdentificationCheck0610.CheckType argType, final String argCheckKey, final PhaseCheckConfiguration0610 checkConfig, final Map<String, Object> argContext) {
        super(argType, (String)null, argCheckKey, checkConfig, (List)null, (Map)argContext);
    }

    private Time getDateToCompare(final Sublot sublot) {
        return ((CheckSublotUseByDate)this.getIdentificationCheck().getImplementationInstance()).getDateToCompare(sublot);
    }

    private Time getExpectedDate() {
        return ((CheckSublotUseByDate)this.getIdentificationCheck().getImplementationInstance()).getExpectedDate();
    }

    public IIdentificationCheck createIdentificationCheck() {
        this.setIdentificationCheck((IIdentificationCheck)new CheckSublotUseByDate());
        return this.getIdentificationCheck();
    }

    public String getExtendedMessage(final IIdentifiedItem0610 item, final Collection<String> exceptionsFromCheck) {
        final StringBuilder sb = new StringBuilder(this.getMessage());
        this.appendMessage(sb, "CheckUseByDate_ExpectedValueTxt", new Object[] { MESDateUtility.getLocalizedDateTime(this.getExpectedDate(), IMESDateFormatConfig.DATE_ALONE) });
        this.appendMessage(sb, "CheckUseByDate_ActualValueTxt", new Object[] { MESDateUtility.getLocalizedDateTime(this.getDateToCompare(item.getSublot()), IMESDateFormatConfig.DATE_ALONE) });
        return sb.toString();
    }

    private static final class CheckSublotUseByDate extends AbstractIdentificationCheck
    {
        private static final String NOT_USED_MESSAGE = "Not used message";

        private Time getDateToCompare(final Sublot sublot) {
            return MESNamedUDASublot.getUseByDate(sublot);
        }

        private Time getExpectedDate() {
            return PCContext.getCurrentServerTime();
        }

        public void performCheck(final IExecuteCheckParameter parameterObject) {
            final Sublot sublot = ((ExecuteCheckParameter)parameterObject).getSublot();
            if (sublot != null) {
                final Time dateToCompare = this.getDateToCompare(sublot);
                if (dateToCompare != null && ((ITimeService)ServiceFactory.getService((Class)ITimeService.class)).isExpired(dateToCompare.addDays(1), 1, true)) {
                    this.getExceptionList().add("Not used message");
                }
            }
        }
    }
}
