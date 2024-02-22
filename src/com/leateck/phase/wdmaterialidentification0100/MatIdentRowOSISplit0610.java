package com.leateck.phase.wdmaterialidentification0100;

import com.rockwell.mes.commons.base.ifc.nameduda.*;
import com.rockwell.mes.commons.base.ifc.i18n.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import org.apache.commons.lang3.*;
import java.util.*;
import com.rockwell.mes.services.eqm.ifc.*;
import com.datasweep.compatibility.client.*;
import com.datasweep.plantops.common.measuredvalue.*;
import com.rockwell.mes.commons.base.ifc.functional.*;
import com.rockwell.mes.commons.base.ifc.exceptions.*;
import com.rockwell.mes.commons.base.ifc.services.*;

public class MatIdentRowOSISplit0610 extends MatIdentRow0610 implements IMatIdentRow0610
{
    private static final IMESContainerEquipmentService CONTAINER_EQ_SERVICE;

    public MatIdentRowOSISplit0610(IWDMatIdentModel0610 model, OrderStepInput masterOrderStepInput,  List<OrderStepInput> splitOrderStepInputList,  IMatIdentRow0610 headerRow) {
        super(model, masterOrderStepInput, splitOrderStepInputList, null, null, null, false, headerRow);
    }

    public WorkCenter calculateLastWorkCenterOfPosition() {
        return this.getMasterRow().getLastWorkCenterOfBOMPosition();
    }

    @Override
    protected String calculatePosition() {
        return "";
    }

    @Override
    protected String calculateWeighingSequenceDisplayString() {
        return "";
    }

    @Override
    protected String calculateMatIdentifier() {
        return this.getMasterRow().getMatIdentifier();
    }

    @Override
    protected String calculateMatShortDescription() {
        return this.getMasterRow().getMatShortDescription();
    }

    @Override
    protected String calculateMatDisplayString() {
        return "";
    }

    @Override
    protected String calculateAllocatedBatches() {
        return this.calculateIdentifiedSublots();
    }

    @Override
    protected String calculateIdentifiedSublots() {
        if (!CollectionUtility.isEmpty((Collection)this.getSplitOSIs())) {
            final LinkedHashSet<String> set = new LinkedHashSet<String>();
            for (final OrderStepInput orderStepInput : this.getSplitOSIs()) {
                final Sublot attachSublot = orderStepInput.getAttachSublot();
                final Long sourceContainerKey = MESNamedUDAOrderStepInput.getSourceContainerKey(orderStepInput);
                IMESContainerEquipment loadMESContainerEquipmentByKey;
                if (sourceContainerKey != null && sourceContainerKey != 0L) {
                    loadMESContainerEquipmentByKey = MatIdentRowOSISplit0610.CONTAINER_EQ_SERVICE.loadMESContainerEquipmentByKey((long)sourceContainerKey);
                }
                else {
                    loadMESContainerEquipmentByKey = null;
                }
                set.add((attachSublot != null) ? MatIdentView0610.getDisplayStringForSplitOSI(attachSublot, loadMESContainerEquipmentByKey) : I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "wasteTxt"));
            }
            return StringUtils.join((Iterable)set, StringConstants.LINE_BREAK);
        }
        return "";
    }

    @Override
    protected String calculateRelatedBatchesSublotsDisplayString() {
        return this.calculateIdentifiedSublots();
    }

    @Override
    protected MeasuredValue calculatePlannedQuantity() {
        return null;
    }

    @Override
    protected MeasuredValue calculateOriginalQuantity() {
        return null;
    }

    @Override
    protected String calculatePlannedOriginalQuantityDisplayString() {
        return "";
    }

    @Override
    protected MeasuredValue calculateActualQuantity() {
        final List<OrderStepInput> splitOSIs = this.getSplitOSIs();
        if (!CollectionUtility.isEmpty((Collection)splitOSIs)) {
            MeasuredValue measuredValue = splitOSIs.get(0).getActualQuantity();
            try {
                for (int i = 1; i < this.getSplitOSIs().size(); ++i) {
                    measuredValue = MeasuredValueUtilities.add((IMeasuredValue)measuredValue, (IMeasuredValue)splitOSIs.get(i).getActualQuantity(), (IMeasuredValueConverter)null);
                }
            }
            catch (MESIncompatibleUoMException ex) {
                throw new MESRuntimeException((Throwable)ex);
            }
            return MeasuredValueUtilities.toMeasuredValue((IMeasuredValue)measuredValue);
        }
        return null;
    }

    @Override
    protected String calculateRemainingQuantity() {
        return "";
    }

    @Override
    protected Long calculateTargetSublotCount() {
        return null;
    }

    @Override
    protected String calculateResult() {
        return "";
    }

    @Override
    protected String calculateCleaning(GxPContextMap lastBindings, GxPContextMap bindingsForOS) {
        return "";
    }

    @Override
    protected boolean calculateIdOnly(IWDMatIdentModel0610 model) {
        return this.getMasterRow().getIdOnly();
    }

    @Override
    protected boolean calculateEditableIdOnlyFlag(IWDMatIdentModel0610 model) {
        return false;
    }

    static {
        CONTAINER_EQ_SERVICE = (IMESContainerEquipmentService)ServiceFactory.getService(IMESContainerEquipmentService.class);
    }
}
