package com.leateck.phase.wdmaterialidentification0100.checks;

import com.rockwell.mes.shared.product.wd.checks.*;
import com.rockwell.mes.shared.product.wd.*;
import com.rockwell.mes.shared.phase.matident.check.*;
import com.rockwell.mes.services.inventory.ifc.checks.*;
import com.rockwell.mes.shared.phase.matident.check.identcheck.*;
import com.datasweep.compatibility.ui.*;
import java.util.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import com.datasweep.compatibility.client.*;

public abstract class PhaseBatchAgainstDateIdentificationCheck0610 extends AbstractPhaseIdentificationCheckWD0610<IIdentifiedItem0610>
{
    public PhaseBatchAgainstDateIdentificationCheck0610(final AbstractPhaseIdentificationCheck0610.CheckType argType, final String argBeanName, final String argCheckKey, final PhaseCheckConfiguration0610 checkConfig, final List<Object> argParams, final Map<String, Object> argContext) {
        super(argType, argBeanName, argCheckKey, checkConfig, (List)argParams, (Map)argContext);
    }

    public IIdentificationCheck createIdentificationCheck() {
        final AbstractCheckBatchAgainstDate0610 checkBatchAgainstDate = this.createCheckBatchAgainstDate();
        this.setIdentificationCheck((IIdentificationCheck)checkBatchAgainstDate);
        final List params = this.getParams();
        if (params != null && !params.isEmpty()) {
            checkBatchAgainstDate.setOffsetDays((long)params.get(0));
        }
        return this.getIdentificationCheck();
    }

    protected abstract AbstractCheckBatchAgainstDate0610 createCheckBatchAgainstDate();

    private Time getDateToCompare(final Batch batch) {
        return ((AbstractCheckBatchAgainstDate0610)this.getIdentificationCheck().getImplementationInstance()).getDateToCompare(batch);
    }

    public String getExtendedMessage(final IIdentifiedItem0610 item, final Collection<String> exceptionsFromCheck) {
        final StringBuilder sb = new StringBuilder(this.getMessage());
        final List params = this.getParams();
        if (params != null && !params.isEmpty()) {
            final Sublot sublot = item.getSublot();
            this.appendMessageBatchTxt(sb, sublot);
            this.appendMessage(sb, this.getExpectedValueMsgCatId(), new Object[] { MESDateUtility.getLocalizedDateTime(PCContext.getCurrentServerTime().addDays((double)params.get(0)), IMESDateFormatConfig.DATE_ALONE) });
            final Time dateToCompare = this.getDateToCompare(sublot.getBatch());
            this.appendMessage(sb, this.getActualValueMsgCatId(), new Object[] { (dateToCompare == null) ? "" : MESDateUtility.getLocalizedDateTime(dateToCompare, IMESDateFormatConfig.DATE_ALONE) });
        }
        return sb.toString();
    }

    public abstract String getExpectedValueMsgCatId();

    public abstract String getActualValueMsgCatId();

    public String getAdditionalExceptionMsgCatId() {
        return null;
    }

    public String getMessage() {
        final StringBuilder sb = new StringBuilder(super.getMessage());
        if (this.getAdditionalExceptionMsgCatId() != null) {
            this.appendMessage(sb, this.getAdditionalExceptionMsgCatId(), new Object[0]);
        }
        return sb.toString();
    }
}
