package com.leateck.phase.wdmaterialidentification0100;

import java.util.*;
import com.datasweep.compatibility.client.*;

public interface IMatIdentRow0610
{
    String getPosition();

    String getWeighingSequenceDisplayString();

    String getMatIdentifier();

    String getMatShortDescription();

    String getMatDisplayString();

    String getAllocatedBatches();

    String getIdentifiedSublots();

    String getRelBatchesSublotsDisplayStr();

    MeasuredValue getPlannedQuantity();

    MeasuredValue getOriginalQuantity();

    String getPlannedOrigQtyDisplayStr();

    MeasuredValue getActualQuantity();

    String getRemainingQuantity();

    Long getTargetSublotCount();

    Long getTargetContainerCount();

    String getResult();

    String getCleaning();

    Boolean getIdOnly();

    void setIdOnly(final Boolean idOnlyFlag);

    OrderStepInput getOSI();

    boolean isEditableIdOnly();

    boolean isMasterRow();

    IMatIdentRow0610 getMasterRow();

    List<OrderStepInput> getSplitOSIs();

    OrderStepInput getSplitOSI();

    WorkCenter getLastWorkCenterOfBOMPosition();
}
