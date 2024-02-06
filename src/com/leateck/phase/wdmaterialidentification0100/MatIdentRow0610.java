package com.leateck.phase.wdmaterialidentification0100;

import java.util.*;
import com.datasweep.compatibility.client.*;
import com.rockwell.mes.services.inventory.ifc.*;
import com.rockwell.mes.services.eqm.ifc.*;
import com.rockwell.mes.commons.base.ifc.nameduda.*;
import com.rockwell.mes.commons.base.ifc.exceptions.*;
import com.rockwell.mes.commons.base.ifc.services.*;

public abstract class MatIdentRow0610 implements IMatIdentRow0610
{
    private final OrderStepInput masterOSI;
    private final List<OrderStepInput> splitOSIs;
    private final OrderStepInput openSplitOSI;
    private final String position;
    private final String weighingSequenceDisplayString;
    private final String matIdentifier;
    private final String matShortDescription;
    private final String matDisplayString;
    private final String allocatedBatches;
    private final String identifiedSublots;
    private final MeasuredValue plannedQuantity;
    private final MeasuredValue originalQuantity;
    private final String plannedOrigQtyDisplayStr;
    private final MeasuredValue actualQuantity;
    private final String remainingQuantity;
    private final Long targetSublotCount;
    private final String result;
    private final String cleaning;
    private boolean idOnly;
    private final boolean isEditableIdOnly;
    private final WorkCenter lastWorkCenterOfBOMPosition;
    private final boolean isMasterRow;
    private final IMatIdentRow0610 masterRow;
    protected final IMatMgmtSupportService serviceMatMgmt;
    private final String relatedBatchesSublotsDisplayString;
    protected static final String BATCHSEPARATOR = ", ";

    public MatIdentRow0610(final IWDMatIdentModel0610 model, final OrderStepInput masterOrderStepInput, final List<OrderStepInput> splitOrderStepInputList, final OrderStepInput openSplitOrderStepInput, final GxPContextMap bindings, final GxPContextMap bindingsForOS, final boolean isHeaderRow, final IMatIdentRow0610 headerRow) {
        this.serviceMatMgmt = (IMatMgmtSupportService)ServiceFactory.getService((Class)IMatMgmtSupportService.class);
        if (!isHeaderRow && headerRow == null) {
            throw new IllegalArgumentException("Unsupported arguments: masterRow row must not be null for split OSIs!");
        }
        this.isMasterRow = isHeaderRow;
        this.masterRow = headerRow;
        this.masterOSI = masterOrderStepInput;
        this.splitOSIs = splitOrderStepInputList;
        this.openSplitOSI = openSplitOrderStepInput;
        this.lastWorkCenterOfBOMPosition = this.calculateLastWorkCenterOfPosition();
        this.position = this.calculatePosition();
        this.weighingSequenceDisplayString = this.calculateWeighingSequenceDisplayString();
        this.matIdentifier = this.calculateMatIdentifier();
        this.matShortDescription = this.calculateMatShortDescription();
        this.allocatedBatches = this.calculateAllocatedBatches();
        this.identifiedSublots = this.calculateIdentifiedSublots();
        this.relatedBatchesSublotsDisplayString = this.calculateRelatedBatchesSublotsDisplayString();
        this.plannedQuantity = this.calculatePlannedQuantity();
        this.originalQuantity = this.calculateOriginalQuantity();
        this.actualQuantity = this.calculateActualQuantity();
        this.remainingQuantity = this.calculateRemainingQuantity();
        this.targetSublotCount = this.calculateTargetSublotCount();
        this.result = this.calculateResult();
        this.cleaning = this.calculateCleaning(bindings, bindingsForOS);
        this.idOnly = this.calculateIdOnly(model);
        this.isEditableIdOnly = this.calculateEditableIdOnlyFlag(model);
        this.matDisplayString = this.calculateMatDisplayString();
        this.plannedOrigQtyDisplayStr = this.calculatePlannedOriginalQuantityDisplayString();
        final Long splitNumber = MESNamedUDAOrderStepInput.getSplitNumber(this.masterOSI);
        if (splitNumber != null && splitNumber > 0L) {
            throw new MESRuntimeException("MatIdentRow0610 does not support OrderStepInput's with splitnumber > 0");
        }
    }

    protected abstract WorkCenter calculateLastWorkCenterOfPosition();

    @Override
    public WorkCenter getLastWorkCenterOfBOMPosition() {
        return this.lastWorkCenterOfBOMPosition;
    }

    protected abstract String calculatePosition();

    protected abstract String calculateWeighingSequenceDisplayString();

    protected abstract String calculateMatIdentifier();

    protected abstract String calculateMatShortDescription();

    protected abstract String calculateMatDisplayString();

    protected abstract String calculateAllocatedBatches();

    protected abstract String calculateIdentifiedSublots();

    protected abstract String calculateRelatedBatchesSublotsDisplayString();

    protected abstract MeasuredValue calculatePlannedQuantity();

    protected abstract MeasuredValue calculateOriginalQuantity();

    protected abstract String calculatePlannedOriginalQuantityDisplayString();

    protected abstract MeasuredValue calculateActualQuantity();

    protected abstract String calculateRemainingQuantity();

    protected abstract Long calculateTargetSublotCount();

    protected abstract String calculateResult();

    protected abstract String calculateCleaning(final GxPContextMap lastBindings, final GxPContextMap bindingsForOS);

    protected abstract boolean calculateIdOnly(final IWDMatIdentModel0610 model);

    protected abstract boolean calculateEditableIdOnlyFlag(final IWDMatIdentModel0610 model);

    @Override
    public String getPosition() {
        return this.position;
    }

    @Override
    public String getWeighingSequenceDisplayString() {
        return this.weighingSequenceDisplayString;
    }

    @Override
    public String getMatIdentifier() {
        return this.matIdentifier;
    }

    @Override
    public String getMatShortDescription() {
        return this.matShortDescription;
    }

    @Override
    public String getMatDisplayString() {
        return this.matDisplayString;
    }

    @Override
    public String getAllocatedBatches() {
        return this.allocatedBatches;
    }

    @Override
    public String getIdentifiedSublots() {
        return this.identifiedSublots;
    }

    @Override
    public String getRelBatchesSublotsDisplayStr() {
        return this.relatedBatchesSublotsDisplayString;
    }

    @Override
    public MeasuredValue getPlannedQuantity() {
        return this.plannedQuantity;
    }

    @Override
    public MeasuredValue getOriginalQuantity() {
        return this.originalQuantity;
    }

    @Override
    public String getPlannedOrigQtyDisplayStr() {
        return this.plannedOrigQtyDisplayStr;
    }

    @Override
    public MeasuredValue getActualQuantity() {
        return this.actualQuantity;
    }

    @Override
    public String getRemainingQuantity() {
        return this.remainingQuantity;
    }

    @Override
    public Long getTargetSublotCount() {
        return this.targetSublotCount;
    }

    @Override
    public Long getTargetContainerCount() {
        return null;
    }

    @Override
    public String getResult() {
        return this.result;
    }

    @Override
    public String getCleaning() {
        return this.cleaning;
    }

    @Override
    public Boolean getIdOnly() {
        return this.idOnly;
    }

    @Override
    public void setIdOnly(final Boolean idOnlyFlag) {
        this.idOnly = idOnlyFlag;
    }

    @Override
    public OrderStepInput getOSI() {
        return this.masterOSI;
    }

    @Override
    public boolean isEditableIdOnly() {
        return this.isEditableIdOnly;
    }

    @Override
    public boolean isMasterRow() {
        return this.isMasterRow;
    }

    @Override
    public IMatIdentRow0610 getMasterRow() {
        return this.masterRow;
    }

    @Override
    public List<OrderStepInput> getSplitOSIs() {
        return this.splitOSIs;
    }

    @Override
    public OrderStepInput getSplitOSI() {
        return this.openSplitOSI;
    }

    protected boolean isOSIProcessedOnForeignWorkCenter() {
        final WorkCenter lastWorkCenterOfBOMPosition = this.getLastWorkCenterOfBOMPosition();
        return lastWorkCenterOfBOMPosition != null && PCContext.getFunctions().getCurrentWorkCenter().getKey() != lastWorkCenterOfBOMPosition.getKey();
    }
}
