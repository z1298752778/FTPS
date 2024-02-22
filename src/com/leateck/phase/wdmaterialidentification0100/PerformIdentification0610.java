package com.leateck.phase.wdmaterialidentification0100;

import java.util.concurrent.*;
import com.datasweep.compatibility.client.*;
import com.rockwell.mes.services.recipe.ifc.weighing.*;
import com.rockwell.mes.commons.base.ifc.nameduda.*;
import com.rockwell.mes.commons.base.ifc.choicelist.*;
import com.rockwell.mes.services.wd.ifc.*;
import com.rockwell.mes.apps.wd.ifc.*;
import com.rockwell.mes.services.eqm.ifc.exceptions.*;
import com.rockwell.mes.shared.product.wd.*;
import com.rockwell.mes.services.eqm.ifc.cleaning.*;
import com.rockwell.mes.services.s88.ifc.*;
import com.rockwell.mes.services.s88.ifc.execution.*;
import com.rockwell.mes.commons.base.ifc.exceptions.*;
import com.rockwell.mes.services.eqm.ifc.*;
import com.rockwell.mes.commons.base.ifc.services.*;

final class PerformIdentification0610 implements Callable<Void>
{
    private final long currentOSIKey;
    private final Sublot identifiedSublot;
    private final IWDMatIdentModel0610 identModel;
    private final RtPhaseExecutorWDMatIdent0010 executor;

    private PerformIdentification0610(final RtPhaseExecutorWDMatIdent0010 theExecutor, final OrderStepInput osi, final Sublot sublot, final IWDMatIdentModel0610 model) {
        this.executor = theExecutor;
        this.currentOSIKey = ((osi == null) ? 0L : osi.getKey());
        this.identifiedSublot = sublot;
        this.identModel = model;
    }

    private IWDMatIdentModel0610 getModel() {
        return this.identModel;
    }

    private OrderStepInput getCurrentOSI() {
        return this.getModel().getOSIByKey(this.currentOSIKey);
    }

    @Override
    public Void call() {
        final IMESRoomEquipment currentRoom = WDHelper0610.getCurrentRoom();
        WDOSIServiceHelper0610.autoIdentifySublot(this.getCurrentOSI(), this.identifiedSublot);
        this.roomHandling(currentRoom);
        if (this.getModel().getIdOnly(this.getCurrentOSI())) {
            MESNamedUDAOrderStepInput.setWeighingMethod(this.getCurrentOSI(), EnumWeighingMethods.IDENTIFICATION_ONLY);
        }
        else if (EnumWeighingMethods.IDENTIFICATION_ONLY.equals(MESNamedUDAOrderStepInput.getWeighingMethod(this.getCurrentOSI()))) {
            MESNamedUDAOrderStepInput.setWeighingMethod(this.getCurrentOSI(), (IMESChoiceElement)null);
        }
        final IMESContainerEquipment currentSourceContainer = this.getModel().getCurrentSourceContainer();
        if (currentSourceContainer != null) {
            MESNamedUDAOrderStepInput.setSourceContainerKey(this.getCurrentOSI(), currentSourceContainer.getKey());
        }
        ((IBasicOrderStepInputService)ServiceFactory.getService((Class)IBasicOrderStepInputService.class)).saveOrderStepInput(this.getCurrentOSI());
        this.getModel().setIdentifiedOSI(this.getCurrentOSI());
        this.bindSourceContainer();
        return null;
    }

    private void roomHandling(final IMESRoomEquipment room) {
        final MesWeighingMode currentWeighingMode = this.getModel().getCurrentWeighingMode();
        try {
            if (!((IWDMatIdentModel0610)this.executor.getModel()).roomRemainsInUse()) {
                WDHelper0610.bindEquipment(room.getS88Equipment(), this.identModel.getRtPhase());
            }
            this.ensureNoRoomCleaningDemand(room);
            ((IWDEquipmentService)ServiceFactory.getService((Class)IWDEquipmentService.class)).setGxPContextOnRoom(room, this.getCurrentOSI(), currentWeighingMode, (IRuntimeEntity)this.identModel.getRtPhase());
        }
        catch (GxpContextSetVetoException ex) {
            throw new MESRuntimeException("Room binding failed!", (Throwable)ex);
        }
        if (!((IWDMatIdentModel0610)this.executor.getModel()).roomRemainsInUse()) {
            RoomTriggerHelper0610.fireRoomInUseTriggerIfPossible(room, this.executor.getRtPhase());
        }
    }

    private void ensureNoRoomCleaningDemand(final IMESRoomEquipment room) {
        final IMESRoomEquipmentService imesRoomEquipmentService = (IMESRoomEquipmentService)ServiceFactory.getService((Class)IMESRoomEquipmentService.class);
        final IMESChoiceElement calculateCleaningDemand = imesRoomEquipmentService.calculateCleaningDemand(room, ((IGxPContextMapService)ServiceFactory.getService((Class)IGxPContextMapService.class)).createGxPContextForOrderStepInput(this.getCurrentOSI(), this.getModel().getCurrentWeighingMode()));
        if (CleaningDemand.hasCleaningDemand(calculateCleaningDemand)) {
            throw new MESRuntimeException(imesRoomEquipmentService.getLocalizedReasonFor(calculateCleaningDemand));
        }
    }

    private void bindSourceContainer() {
        final IMESContainerEquipment currentSourceContainer = this.getModel().getCurrentSourceContainer();
        if (currentSourceContainer != null) {
            this.bindIdentifiedSourceContainer(currentSourceContainer);
        }
    }

    private void bindIdentifiedSourceContainer(final IMESContainerEquipment container) {
        try {
            IS88EquipmentExecutionService var2 = (IS88EquipmentExecutionService)ServiceFactory.getService(IS88EquipmentExecutionService.class);
            var2.bindExcGroup(container.getS88Equipment().getRootEntity(), this.getModel().getRtPhase());
            GxPContextMap var3 = this.getCurrentGxPContext();
            ExecuteInBoundContextConfiguration var4 = new ExecuteInBoundContextConfiguration(false, false, true);
            if (container.getS88Equipment().isMemberOfGroup()) {
                var2.setGxPContextOnGroup(container.getS88Equipment().getRootEntity(), this.getModel().getRtPhase(), var3, var4, true);
            } else {
                var2.setGxPContext(container.getS88Equipment(), this.getModel().getRtPhase(), var3, var4, true);
            }
        } catch (GxpContextSetVetoException | MESTransitionFailedException var6) {
            WDHelper0610.showErrorDialog(var6.getLocalizedMessage());
        }

    }

    private GxPContextMap getCurrentGxPContext() {
        final OrderStepInput currentOSI = this.getCurrentOSI();
        if (currentOSI != null) {
            return ((IGxPContextMapService)ServiceFactory.getService((Class)IGxPContextMapService.class)).createGxPContextForOrderStepInput(currentOSI, (MesWeighingMode)null);
        }
        return null;
    }

    static void doIdentification(final RtPhaseExecutorWDMatIdent0010 executor, final OrderStepInput currentOSI, final Sublot identifiedSublot, final IWDMatIdentModel0610 matIdentModel) {
        try {
            TransactionInterceptor.callInTransactionImpl((Callable)new PerformIdentification0610(executor, currentOSI, identifiedSublot, matIdentModel));
        }
        catch (Exception ex) {
            throw new MESRuntimeException((Throwable)ex);
        }
    }
}
