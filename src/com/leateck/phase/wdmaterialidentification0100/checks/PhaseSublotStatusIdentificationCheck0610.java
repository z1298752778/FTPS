package com.leateck.phase.wdmaterialidentification0100.checks;

import com.rockwell.mes.shared.product.wd.checks.*;
import com.rockwell.mes.shared.product.wd.*;
import com.rockwell.mes.shared.phase.matident.check.*;
import com.rockwell.mes.commons.base.ifc.choicelist.*;
import com.rockwell.mes.services.inventory.ifc.checks.*;
import java.util.*;
import com.rockwell.mes.services.inventory.ifc.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import com.datasweep.compatibility.client.*;

public class PhaseSublotStatusIdentificationCheck0610 extends AbstractPhaseIdentificationCheckWD0610<IIdentifiedItem0610>
{
    private static final String MSG_ID_EXPECTED_VALUE_TXT = "ExpectedValueTxt";
    private static final String MSG_ID_ACTUAL_VALUE_TXT = "ActualValueTxt";

    public PhaseSublotStatusIdentificationCheck0610(final AbstractPhaseIdentificationCheck0610.CheckType argType, final String argCheckKey, final PhaseCheckConfiguration0610 checkConfig, final IMESChoiceElement minimumRequiredSublotQualityStatus, final Map<String, Object> context) {
        super(argType, "CheckSublotQualityStatus", argCheckKey, checkConfig, (List)Collections.singletonList(minimumRequiredSublotQualityStatus), (Map)context);
    }

    public IIdentificationCheck createIdentificationCheck() {
        this.setIdentificationCheck(super.createIdentificationCheck());
        this.getQualityStatusCheck().setMinimalQualityStatus((IMESChoiceElement)this.getParams().get(0));
        return this.getIdentificationCheck();
    }

    private CheckSublotQualityStatus getQualityStatusCheck() {
        return (CheckSublotQualityStatus)this.getIdentificationCheck().getImplementationInstance();
    }

    public String getExtendedMessage(final IIdentifiedItem0610 item, final Collection<String> exceptionsFromCheck) {
        final StringBuilder sb = new StringBuilder(this.getMessage());
        final List params = this.getParams();
        if (params != null && !params.isEmpty()) {
            final Sublot sublot = item.getSublot();
            this.appendMessageBatchTxt(sb, sublot);
            this.appendMessage(sb, "ExpectedValueTxt", new Object[] { this.getQualityStatusCheck().getMinimalQualityStatus().getLocalizedMessage() });
            this.appendMessage(sb, "ActualValueTxt", new Object[] { ((ISublotService)ServiceFactory.getService((Class)ISublotService.class)).getLocalizedSublotQualityStatusName(sublot, ISublotService.SublotQualityStatusDefaultIfNullEnum.LOCALIZED_NONE) });
        }
        return sb.toString();
    }
}
