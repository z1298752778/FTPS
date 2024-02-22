package com.leateck.phase.wdmaterialidentification0100.checks;


import com.rockwell.mes.shared.product.wd.AbstractWeighView0610;
import com.rockwell.mes.shared.product.wd.IIdentifiedItem0610;
import com.rockwell.mes.shared.product.wd.checks.*;
import com.rockwell.mes.shared.phase.matident.check.*;
import com.datasweep.compatibility.client.*;
import java.util.*;

public class PhaseAllocationIdentificationCheck0610 extends AbstractPhaseIdentificationCheckWD0610<IIdentifiedItem0610>
{
    private static final String MSG_ID_EXPECTED_VALUES_TXT = "ExpectedValuesTxt";
    private static final String MSG_ID_ACTUAL_VALUE2_TXT = "ActualValue2Txt";

    public PhaseAllocationIdentificationCheck0610(final AbstractPhaseIdentificationCheck0610.CheckType argType, final String argCheckKey, final PhaseCheckConfiguration0610 checkConfig, final List<Object> argParams, final Map<String, Object> context) {
        super(argType, "CheckMaterialBatchSuggestedMaterialBatches", argCheckKey, checkConfig, (List)argParams, (Map)context);
    }

    public String getExtendedMessage(final IIdentifiedItem0610 item, final Collection<String> exceptionsFromCheck) {
        StringBuilder var3 = new StringBuilder(this.getMessage());
        Sublot var4 = item.getSublot();
        if (this.getContext() != null) {
            Iterator var5 = this.getContext().entrySet().iterator();

            while(var5.hasNext()) {
                Map.Entry var6 = (Map.Entry)var5.next();
                if (((String)var6.getKey()).equals(var4.getPart().getPartNumber())) {
                    this.appendMessage(var3, "ExpectedValuesTxt", new Object[]{var6.getValue()});
                    break;
                }
            }
        }
        this.appendMessage(var3, getExtendedMessageID(var4, "ActualValue2Txt"), new Object[] { AbstractWeighView0610.getBatchName(var4.getBatch()), var4.getName() });
        return var3.toString();
    }
}
