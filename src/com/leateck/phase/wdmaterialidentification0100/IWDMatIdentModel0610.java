package com.leateck.phase.wdmaterialidentification0100;

import com.rockwell.mes.shared.product.wd.*;
import com.datasweep.plantops.common.measuredvalue.*;
import com.datasweep.compatibility.client.*;
import java.util.*;

public interface IWDMatIdentModel0610 extends IWDCommonWeighModel0610<MESRtPhaseDataWDMatIdent0010>
{
    IMeasuredValue getProrateFactor();

    void setProrateFactor(final IMeasuredValue prorateFactor);

    IMeasuredValue getDeclaredWaste();

    void setDeclaredWaste(final IMeasuredValue declaredWaste);

    void setNewRemainingQty(final IMeasuredValue remainingQty);

    void setDoResetBOMPosition(final boolean doReset);

    boolean isDoResetBOMPosition();

    IMeasuredValue getNewRemainingQty();

    long getIdentifiedOSIKey();

    OrderStepInput getIdentifiedOSI();

    void setIdentifiedOSI(final OrderStepInput identifiedOSI);

    Sublot getIdentifiedSublot();

    boolean hasSublotBeenIdentified();

    boolean isUndoIdentificationApplicable();

    List<OrderStepInput> getMasterOSIsRelevantForThisRtPhase(final boolean applyCampaignFilter);

    List<OrderStepInput> getAllOSIsRelevantForThisRtPhase(final boolean applyCampaignFilter);

    OrderStepInput getLastFinishedSplitOsiWithAttachedSublot();

    OrderStepInput findOSIForSublotUsingPart(final Sublot sublot, final OrderStepInput selectedOsi);

    boolean isOSOPredecessor(final Long key);

    Collection<OrderStepInput> getMasterOsisOfPredecessorOSO(final Long key);

    boolean isMaterialComplete();

    Sublot getPrintedSublot();

    void setPrintedSublot(final Sublot targetSublot);

    int getWeighingCondition();

    void setWeighingCondition(final int weighingCondition);

    boolean getIdOnly(final OrderStepInput osi);

    void setIdOnly(final OrderStepInput osi, final boolean flag);

    boolean isEditableIdOnly(final OrderStepInput osi);

    boolean isDisplaySplitPositionsEnabled();

    boolean getReleaseScaleTriggered();

    void setReleaseScaleTriggered();

    boolean isDoUserTriggeredReleaseScale();

    void setDoUserTriggeredReleaseScale(final boolean doReleaseScale);

    WeighingOperationTypeWDMatIdent0610 getWeighingOperationTypeWDMatIdent0610();

    void setKeepTarget(final boolean keepTarget);

    IdentificationResult0610 getIdentificationResult();

    boolean isWeighingSequence();

    boolean isOSIRelevantForThisRtPhase(final OrderStepInput osi);
}
