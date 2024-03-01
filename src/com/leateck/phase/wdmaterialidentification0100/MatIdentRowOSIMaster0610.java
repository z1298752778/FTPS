package com.leateck.phase.wdmaterialidentification0100;

import java.util.*;
import com.rockwell.mes.commons.base.ifc.nameduda.*;
import com.rockwell.mes.services.wd.ifc.*;
import com.rockwell.mes.commons.base.ifc.i18n.*;
import org.apache.commons.lang3.*;
import com.rockwell.mes.shared.product.wd.*;
import com.datasweep.compatibility.client.*;
import com.datasweep.plantops.common.measuredvalue.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import com.rockwell.mes.services.eqm.ifc.*;

public class MatIdentRowOSIMaster0610 extends MatIdentRow0610 implements IMatIdentRow0610
{
    public MatIdentRowOSIMaster0610(final IWDMatIdentModel0610 model, final OrderStepInput masterOrderStepInput, final OrderStepInput openSplitOrderStepInput, final GxPContextMap bindings, final GxPContextMap bindingsForOS) {
        super(model, masterOrderStepInput, null, openSplitOrderStepInput, bindings, bindingsForOS, true, null);
    }

    @Override
    protected WorkCenter calculateLastWorkCenterOfPosition() {
        final OSIPositionStatus value = OSIPositionStatus.get(this.getOSI());
        if (OSIPositionStatus.IN_PROCESS.equals((Object)value) || OSIPositionStatus.NOT_STARTED.equals((Object)value)) {
            return MESNamedUDAOrderStepInput.getWorkCenter(this.getSplitOSI());
        }
        final MESAllSplitOSIs mesAllSplitOSIs = new MESAllSplitOSIs(this.getOSI(), true);
        mesAllSplitOSIs.sortByPosition(false);
        return MESNamedUDAOrderStepInput.getWorkCenter(mesAllSplitOSIs.get(0));
    }

    @Override
    protected String calculatePosition() {
        return MESNamedUDAOrderStepInput.getPosition(this.getOSI());
    }

    @Override
    protected String calculateWeighingSequenceDisplayString() {
        final Long weighingSequence = MESNamedUDAOrderStepInput.getWeighingSequence(this.getOSI());
        if (weighingSequence == null) {
            return this.calculatePosition();
        }
        return I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "weighingSequence_DisplayString", (Object[])new String[] { Long.toString(weighingSequence), this.calculatePosition() });
    }

    @Override
    protected String calculateMatIdentifier() {
        return this.getOSI().getPart().getPartNumber();
    }

    @Override
    protected String calculateMatShortDescription() {
        return WDHelper0610.getMaterialShortDescription(this.getOSI().getPart());
    }

    @Override
    protected String calculateMatDisplayString() {
        return I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "Material_DisplayString", (Object[])new String[] { this.getMatIdentifier(), this.getMatShortDescription() });
    }

    @Override
    protected String calculateAllocatedBatches() {
        final Batch[] allocatedBatchesForOrderStepInput = this.serviceMatMgmt.getAllocatedBatchesForOrderStepInput(this.getOSI());
        final String[] array = new String[allocatedBatchesForOrderStepInput.length];
        for (int i = 0; i < array.length; ++i) {
            array[i] = AbstractWeighView0610.getBatchName(allocatedBatchesForOrderStepInput[i]);
        }
        return StringUtils.join((Object[])array, ", ");
    }

    @Override
    protected String calculateIdentifiedSublots() {
        return MatIdentView0610.getDisplayStringForMasterOSI(WDOSIServiceHelper0610.getProducedSublots(this.getOSI()));
    }

    @Override
    protected String calculateRelatedBatchesSublotsDisplayString() {
        return this.calculateAllocatedBatches() + this.calculateIdentifiedSublots();
    }

    @Override
    protected MeasuredValue calculatePlannedQuantity() {
        return this.getOSI().getPlannedQuantity();
    }

    @Override
    protected MeasuredValue calculateOriginalQuantity() {
        return MESNamedUDAOrderStepInput.getOriginalPlannedQuantity(this.getOSI());
    }

    @Override
    protected String calculatePlannedOriginalQuantityDisplayString() {
        if (WDOSIServiceHelper0610.isPlannedQtyModeNone(this.getOSI()) || WDOSIServiceHelper0610.isDynamicAsProduced(this.getOSI())) {
            return WDHelper0610.getNotApplicableMessage();
        }
        return I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "PlannedOriginalQuantity_DisplayString", (Object[])new String[] { WDHelper0610.toDisplayString((IMeasuredValue)this.getPlannedQuantity()), WDHelper0610.toDisplayString((IMeasuredValue)this.getOriginalQuantity()) });
    }

    @Override
    protected MeasuredValue calculateActualQuantity() {
        return MESNamedUDAOrderStepInput.getTotalActualQuantity(this.getOSI());
    }

    @Override
    protected String calculateRemainingQuantity() {
        if (WDOSIServiceHelper0610.isPlannedQtyModeNone(this.getOSI()) || WDOSIServiceHelper0610.isDynamicAsProduced(this.getOSI())) {
            return WDHelper0610.getNotApplicableMessage();
        }
        return WDHelper0610.toDisplayString((IMeasuredValue)MESNamedUDAOrderStepInput.getTotalRemainingQuantity(this.getOSI()), "");
    }

    @Override
    protected Long calculateTargetSublotCount() {
        return MESNamedUDAOrderStepInput.getTotalNumTargetSublots(this.getOSI());
    }

    @Override
    protected String calculateResult() {
        final OSIPositionStatus value = OSIPositionStatus.get(this.getOSI());
        if (!OSIPositionStatus.COMPLETED_STATES.contains(value) && !OSIPositionStatus.ABORTED.equals((Object)value) && !OSIPositionStatus.CANCELED.equals((Object)value) && this.isOSIProcessedOnForeignWorkCenter()) {
            return this.getLastWorkCenterOfBOMPosition().getName();
        }
        if (value != null) {
            return value.getLocalizedName();
        }
        return "";
    }

    @Override
    protected String calculateCleaning(final GxPContextMap lastBindings, final GxPContextMap bindingsForOS) {
        ((IGxPContextMapService)ServiceFactory.getService((Class)IGxPContextMapService.class)).addGxPContextForOrderStepInput(bindingsForOS, this.getOSI());
        return ((IMESRoomEquipmentService)ServiceFactory.getService((Class)IMESRoomEquipmentService.class)).calculateCleaningDemand(WDHelper0610.getCurrentRoom(false), lastBindings, bindingsForOS).getLocalizedMessage();
    }

    @Override
    protected boolean calculateIdOnly(final IWDMatIdentModel0610 model) {
        return model.getIdOnly(this.getSplitOSI());
    }

    @Override
    protected boolean calculateEditableIdOnlyFlag(final IWDMatIdentModel0610 model) {
        return model.isEditableIdOnly(this.getSplitOSI());
    }
}
