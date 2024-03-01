package com.leateck.phase.wdmaterialidentification0100.checks;

import com.leateck.phase.wdmaterialidentification0100.RtPhaseExecutorWDMatIdent0010;
import com.rockwell.mes.commons.base.ifc.services.*;
import java.util.*;
import java.util.stream.*;

import com.rockwell.mes.services.s88.ifc.execution.equipment.statusgraph.IS88StatusGraphFireTriggerResult;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IMESS88StatusGraphAssignment;
import com.rockwell.mes.shared.product.wd.*;
import com.rockwell.mes.services.eqm.ifc.*;
import com.rockwell.mes.commons.base.ifc.utility.Pair;

public class TriggerContainerEmptyTransitionChecker0610 extends TriggerTransitionChecker0610
{
    TriggerContainerEmptyTransitionChecker0610(final RtPhaseExecutorWDMatIdent0010 theExecutor) {
        super(theExecutor);
    }

    @Override
    public void doCheck() {
        if (this.executor.shallTriggerContainerEmptyTransitionBeChecked()) {
            IMESContainerEquipment var1 = (this.executor.getModel()).getCurrentSourceContainer();
            if (var1 != null) {
                IMESContainerEquipmentService var2 = ServiceFactory.getService(IMESContainerEquipmentService.class);

                try {
                    var2.simulateContainerEmptyTriggerOnGroup(var1);
                } catch (FireStatusGraphTriggersFailureException var6) {
                    List<Pair<IMESS88StatusGraphAssignment, IS88StatusGraphFireTriggerResult>> var4 = var6.getAllResults();
                    if (FireStatusGraphTriggersFailureException.containsError(var4)) {
                        List<IS88StatusGraphFireTriggerResult> var5 = var4.stream().map(Pair::getSecond).collect(Collectors.toList());
                        this.transitionFailure = new StatusTransitionFailureHelper0610.StatusTransitionFailureSupport(this.executor, var5);
                    }
                }
            }
        }

    }
}
