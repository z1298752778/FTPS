package com.leateck.phase.wdmaterialidentification0100;

import java.util.concurrent.*;
import com.rockwell.mes.commons.base.ifc.exceptions.*;
import com.rockwell.mes.commons.base.ifc.nameduda.*;
import com.rockwell.mes.commons.base.ifc.choicelist.*;
import com.rockwell.mes.services.s88.ifc.execution.*;
import com.datasweep.compatibility.client.*;
import com.rockwell.mes.shared.product.wd.*;
import com.rockwell.mes.services.s88.ifc.execution.equipment.statusgraph.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import com.rockwell.mes.services.eqm.ifc.*;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.*;
import java.util.stream.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import java.util.function.*;
import java.util.*;
import org.apache.commons.logging.*;

public class PerformReleaseIdentification0610 implements Callable<Void>
{
    private static final Log LOGGER = LogFactory.getLog(PerformReleaseIdentification0610.class);
    private final IWDMatIdentModel0610 model;
    private final RtPhaseExecutorWDMatIdent0010 executor;

    private PerformReleaseIdentification0610(final RtPhaseExecutorWDMatIdent0010 identExecutor) {
        this.executor = identExecutor;
        this.model = (IWDMatIdentModel0610)this.executor.getModel();
    }

    static void releaseIdentificationSelectedOSIUnbindRoom(final RtPhaseExecutorWDMatIdent0010 executor, final IMESRtPhase rtPhase) {
        try {
            TransactionInterceptor.callInTransactionImpl((Callable)new PerformReleaseIdentification0610(executor));
        }
        catch (Exception ex) {
            throw new MESRuntimeException((Throwable)ex);
        }
    }

    @Override
    public Void call() {
        final OrderStepInput selectedOSI = this.model.getSelectedOSI();
        if (selectedOSI == null) {
            PerformReleaseIdentification0610.LOGGER.warn((Object)"No releaseIdentification because selected OrderStepInput is null.");
        }
        else {
            final Sublot attachSublot = selectedOSI.getAttachSublot();
            if (attachSublot != null) {
                if (!this.model.containerRemainsOnScale()) {
                    MESNamedUDAOrderStepInput.setScaleName(selectedOSI, (String)null);
                    MESNamedUDAOrderStepInput.setWeighingMethod(selectedOSI, (IMESChoiceElement)null);
                }
                WDOSIServiceHelper0610.undoSublotIdentification(attachSublot, selectedOSI, this.model.getRtPhase());
                this.model.resetCurrentSourceContainer();
                this.model.updateOrderStep(selectedOSI.getOrderStep());
            }
            this.releaseRoom();
            final IMESScaleEquipment selectedScalesEquipment = this.model.getSelectedScalesEquipment();
            if (selectedScalesEquipment != null && !this.model.containerRemainsOnScale()) {
                WDScalesHelper0610.resetGxPContextAndReleaseScaleIfMatchingGxPContext(selectedOSI, selectedScalesEquipment, (IRuntimeEntity)this.model.getRtPhase());
            }
            if (!this.model.isDispenseWeighing()) {
                WDOSIServiceHelper0610.unbindOSIFromWorkcenter(selectedOSI);
            }
            this.unbindTargetContainer();
            this.executor.triggerFallThroughExceptionsLater();
        }
        return null;
    }

    private void releaseRoom() {
        if (this.model.roomRemainsInUse()) {
            WDHelper0610.resetGxPContextOnBoundCurrentRoom((IRuntimeEntity)this.model.getRtPhase());
        }
        else {
            final IS88StatusGraphFireTriggerResult resetGXPContFireEndUseIfPossibleAndReleaseCurrentRoom = WDHelper0610.resetGXPContFireEndUseIfPossibleAndReleaseCurrentRoom(this.model.getRtPhase());
            if (resetGXPContFireEndUseIfPossibleAndReleaseCurrentRoom != null) {
                this.executor.addFireEndUseOfRoomFailureOnFallThrough(resetGXPContFireEndUseIfPossibleAndReleaseCurrentRoom);
            }
        }
    }

    private void unbindTargetContainer() {
        if (this.model.hasTargetContainer() && !this.model.getKeepTarget()) {
            final IMESContainerEquipment currentTargetContainer = this.model.getCurrentTargetContainer();
            final IMESContainerEquipmentService imesContainerEquipmentService = (IMESContainerEquipmentService)ServiceFactory.getService((Class)IMESContainerEquipmentService.class);
            final boolean fireContainerEmptyTrigger = this.fireContainerEmptyTrigger();
            imesContainerEquipmentService.releaseGroup(currentTargetContainer, this.model.getRtPhase());
            if (fireContainerEmptyTrigger) {
                this.model.resetCurrentTargetContainer();
            }
        }
    }

    private boolean fireContainerEmptyTrigger() {
        IMESContainerEquipmentService var1 = (IMESContainerEquipmentService)ServiceFactory.getService(IMESContainerEquipmentService.class);
        IMESContainerEquipment var2 = this.model.getCurrentTargetContainer();
        List<Pair<IMESS88StatusGraphAssignment, IS88StatusGraphFireTriggerResult>> var3 = var1.fireContainerEmptyTriggerOnGroup(var2, this.model.getRtPhase());
        if (LOGGER.isWarnEnabled()) {
            String var4 = var2.getS88Equipment().getIdentifier();
            String var5 = (String)var3.stream().map((res) -> {
                return String.format("(%s, %s)", ((IMESS88StatusGraphAssignment) res.getFirst()).getIdentifier(), ((IS88StatusGraphFireTriggerResult)res.getSecond()).getResultCode().toString());
            }).collect(Collectors.joining(", "));
            LOGGER.warn("Fire trigger CONT_EMPTY failed for container " + var4 + " with result: " + var5);
        }

        if (var1.wereTransitionsExecuted(var3)) {
            return true;
        } else {
            List var7 = (List)var3.stream().map(Pair::getSecond).filter((res) -> {
                return !res.wasTransitionExecuted();
            }).collect(Collectors.toList());
            Iterator var8 = var7.iterator();

            while(var8.hasNext()) {
                IS88StatusGraphFireTriggerResult var6 = (IS88StatusGraphFireTriggerResult)var8.next();
                this.executor.addFireContainerEmptyTriggerFailureOnFallThrough(var6);
            }

            return false;
        }
    }
}
