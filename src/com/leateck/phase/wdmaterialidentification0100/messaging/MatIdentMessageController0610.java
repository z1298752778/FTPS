package com.leateck.phase.wdmaterialidentification0100.messaging;

import com.leateck.phase.wdmaterialidentification0100.*;
import com.leateck.phase.wdmaterialidentification0100.IWDMatIdentModel0610;
import com.leateck.phase.wdmaterialidentification0100.MatIdentExceptionView0610;
import com.leateck.phase.wdmaterialidentification0100.MatIdentView0610;
import com.rockwell.mes.apps.ebr.ifc.phase.*;
import com.rockwell.mes.commons.messaging.ifc.*;
import com.datasweep.compatibility.client.*;
import java.util.*;

public class MatIdentMessageController0610
{
    private final IWDMatIdentModel0610 model;
    private final RtPhaseExecutorWDMatIdent0010 executor;

    public MatIdentMessageController0610(final RtPhaseExecutorWDMatIdent0010 matIdentExecutor) {
        this.model = (IWDMatIdentModel0610)matIdentExecutor.getModel();
        this.executor = matIdentExecutor;
    }

    private IWDMatIdentModel0610 getModel() {
        return this.model;
    }

    private RtPhaseExecutorWDMatIdent0010 getExecutor() {
        return this.executor;
    }

    private MatIdentView0610 getView() {
        return (MatIdentView0610)this.executor.getView();
    }

    public void addMessageHandlerForProduceTargetSublots() {
        this.getExecutor().addMessageHandler((PharmaSuiteMessageListener)new ProduceTargetSublotsHandler0610(new ProduceTargetSublotsHandler0610.IProduceTargetSublotsHandlerCallBack0610() {
            @Override
            public void callBack(final List<Long> sublotKeys, final Long osoKey) {
                if (sublotKeys == null || osoKey == null || sublotKeys.isEmpty() || MatIdentMessageController0610.this.getModel().getStatus() != IPhaseExecutor.Status.ACTIVE || !MatIdentMessageController0610.this.model.isOSOPredecessor(osoKey)) {
                    return;
                }
                MatIdentMessageController0610.this.checkForRelevantChangeAndTriggerRefresh(osoKey);
            }
        }));
    }

    public void addMessageHandlerForOutputWeighingPositionCompleted() {
        this.getExecutor().addMessageHandler((PharmaSuiteMessageListener)new OSOPositionClosedHandler0610(new OSOPositionClosedHandler0610.IOutputWeighingPositionClosedCallBack0610() {
            @Override
            public void callBack(final Long osoKey) {
                if (MatIdentMessageController0610.this.getModel().getStatus() != IPhaseExecutor.Status.ACTIVE || osoKey == null || !MatIdentMessageController0610.this.model.isOSOPredecessor(osoKey)) {
                    return;
                }
                MatIdentMessageController0610.this.checkForRelevantChangeAndTriggerRefresh(osoKey);
            }
        }));
    }

    private void checkForRelevantChangeAndTriggerRefresh(final Long osoKey) {
        final Collection<OrderStepInput> masterOsisOfPredecessorOSO = this.getModel().getMasterOsisOfPredecessorOSO(osoKey);
        if (!masterOsisOfPredecessorOSO.isEmpty()) {
            final Iterator<OrderStepInput> iterator = masterOsisOfPredecessorOSO.iterator();
            while (iterator.hasNext()) {
                this.getExecutor().triggerPlannedQuantityCalculationIfApplicable(iterator.next());
            }
            this.getView().refreshActiveGrid();
            final MatIdentExceptionView0610 matIdentExceptionView0610 = this.getExecutor().getUserTriggeredExceptionView();
            if (matIdentExceptionView0610 != null) {
                matIdentExceptionView0610.refreshGrids();
            }
        }
    }
}
