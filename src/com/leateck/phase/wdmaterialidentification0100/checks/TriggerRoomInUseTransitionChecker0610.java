package com.leateck.phase.wdmaterialidentification0100.checks;

import com.leateck.phase.wdmaterialidentification0100.RtPhaseExecutorWDMatIdent0010;
import com.rockwell.mes.shared.product.wd.*;
import com.rockwell.mes.apps.ebr.ifc.phase.*;
import com.rockwell.mes.services.s88.ifc.execution.equipment.statusgraph.*;

public class TriggerRoomInUseTransitionChecker0610 extends TriggerTransitionChecker0610
{
    TriggerRoomInUseTransitionChecker0610(final RtPhaseExecutorWDMatIdent0010 theExecutor) {
        super(theExecutor);
    }

    @Override
    public void doCheck() {
        if ((this.executor.getModel()).roomRemainsInUse()) {
            return;
        }
        final IS88StatusGraphFireTriggerResult simulateRoomInUseTrigger = RoomTriggerHelper0610.simulateRoomInUseTrigger();
        if (simulateRoomInUseTrigger != null) {
            this.transitionFailure = new StatusTransitionFailureHelper0610.StatusTransitionFailureSupport((IPhaseExecutor)this.executor, simulateRoomInUseTrigger);
        }
    }
}
