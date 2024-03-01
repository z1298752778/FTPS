package com.leateck.phase.wdmaterialidentification0100.checks;

import com.rockwell.mes.shared.product.wd.checks.*;
import com.rockwell.mes.shared.product.wd.*;
import com.rockwell.mes.shared.phase.matident.check.*;
import com.rockwell.mes.services.inventory.ifc.checks.*;
import java.util.*;
import com.rockwell.mes.services.inventory.ifc.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import com.datasweep.compatibility.client.*;

public class PhaseBatchStatusIdentificationCheck0610 extends AbstractPhaseIdentificationCheckWD0610<IIdentifiedItem0610>
{
    private static final String MSG_ID_EXPECTED_VALUE_TXT = "ExpectedValueTxt";
    private static final String MSG_ID_ACTUAL_VALUE_TXT = "ActualValueTxt";

    public PhaseBatchStatusIdentificationCheck0610(final AbstractPhaseIdentificationCheck0610.CheckType argType, final String argCheckKey, final PhaseCheckConfiguration0610 checkConfig, final List<Object> argParams, final Map<String, Object> context) {
        super(argType, "CheckBatchQualityStatus", argCheckKey, checkConfig, (List)argParams, (Map)context);
    }

    public IIdentificationCheck createIdentificationCheck() {
        super.createIdentificationCheck();
        final List params = this.getParams();
        if (params != null && !params.isEmpty()) {
            ((ICheckQualityStatus)this.getIdentificationCheck().getImplementationInstance()).setMinimalQualityStatus(params.get(0).toString());
        }
        return this.getIdentificationCheck();
    }

    public String getExtendedMessage(final IIdentifiedItem0610 item, final Collection<String> exceptionsFromCheck) {
        final StringBuilder sb = new StringBuilder(this.getMessage());
        final List params = this.getParams();
        if (params != null && !params.isEmpty()) {
            final Sublot sublot = item.getSublot();
            this.appendMessageBatchTxt(sb, sublot);
            this.appendMessage(sb, "ExpectedValueTxt", new Object[] { sublot.getBatch().getFlexibleStateModel("BatchQuality").getStateByName(params.get(0).toString()).getLocalizedName() });
            this.appendMessage(sb, "ActualValueTxt", new Object[] { ((IBatchService)ServiceFactory.getService((Class)IBatchService.class)).getLocalizedBatchStatusName(sublot.getBatch()) });
        }
        return sb.toString();
    }
}
