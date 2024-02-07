package com.leateck.phase.wdmaterialidentification0100;

import java.util.concurrent.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import com.rockwell.mes.commons.base.ifc.exceptions.*;
import com.rockwell.mes.commons.base.ifc.nameduda.*;
import com.rockwell.mes.commons.base.ifc.choicelist.*;
import com.rockwell.mes.shared.product.wd.*;
import com.datasweep.compatibility.client.*;

public class PerformUndoIdentificationIdOnly0610 implements Callable<Void>
{
    private final IWDMatIdentModel0610 model;

    private PerformUndoIdentificationIdOnly0610(final IWDMatIdentModel0610 identModel) {
        this.model = identModel;
    }

    static void undoIdentificationUnbindRoom(final IWDMatIdentModel0610 identModel) {
        try {
            TransactionInterceptor.callInTransactionImpl((Callable)new PerformUndoIdentificationIdOnly0610(identModel));
        }
        catch (Exception ex) {
            throw new MESRuntimeException((Throwable)ex);
        }
    }

    @Override
    public Void call() {
        final OrderStepInput identifiedOSI = this.model.getIdentifiedOSI();
        final Sublot identifiedSublot = this.model.getIdentifiedSublot();
        if (identifiedOSI == null || identifiedSublot == null) {
            throw new MESRuntimeException("No undoIdentification because identified OrderStepInput or Sublot is null!");
        }
        MESNamedUDAOrderStepInput.setWeighingMethod(identifiedOSI, (IMESChoiceElement)null);
        WDOSIServiceHelper0610.undoSublotIdentification(identifiedSublot, identifiedOSI, this.model.getRtPhase());
        this.model.resetCurrentSourceContainer();
        this.model.updateOrderStep(identifiedOSI.getOrderStep());
        WDHelper0610.resetGXPContFireEndUseIfPossibleAndReleaseCurrentRoom(this.model.getRtPhase());
        this.model.setIdentifiedOSI(null);
        return null;
    }
}
