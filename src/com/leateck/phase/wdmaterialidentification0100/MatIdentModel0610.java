package com.leateck.phase.wdmaterialidentification0100;

import com.rockwell.mes.commons.base.ifc.utility.*;
import com.datasweep.plantops.common.measuredvalue.*;
import com.rockwell.mes.commons.base.ifc.choicelist.*;
import com.rockwell.mes.commons.base.ifc.nameduda.*;
import com.rockwell.mes.services.s88.ifc.recipe.*;
import com.rockwell.mes.commons.base.ifc.exceptions.*;
import com.rockwell.mes.services.order.ifc.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import com.datasweep.compatibility.client.*;
import com.rockwell.mes.services.recipe.ifc.weighing.*;
import com.rockwell.mes.services.wd.ifc.*;
import com.rockwell.mes.shared.product.wd.*;
import com.rockwell.mes.apps.ebr.ifc.phase.*;
import org.apache.commons.logging.*;
import java.util.*;

public class MatIdentModel0610 extends WDAbstractWeighModel0610<MESRtPhaseDataWDMatIdent0010> implements IWDMatIdentModel0610
{
    private static final Log LOGGER;
    public static final String PARAMETER_EXPIRY_DATE_CHECK = "Minimum time to expire configuration";
    public static final String PARAMETER_EXPIRY_DATE_PASSED_CHECK = "Expiry date passed configuration";
    public static final String PARAMETER_BATCH_STATUS_CHECK = "Batch status check configuration";
    public static final String PARAMETER_RETEST_DATE_CHECK = "Minimum time to retest configuration";
    public static final String PARAMETER_RETEST_DATE_PASSED_CHECK = "Retest date passed configuration";
    public static final String PARAMETER_ALLOCATION_CHECK = "Allocation check configuration";
    public static final String PARAMETER_USE_BY_DATE_CHECK = "Use-by date check configuration";
    public static final String PARAMETER_MFC_POSITION_SEQUENCE_CHECK = "Sequence check configuration";
    public static final String PARAMETER_SUBLOT_STATUS_CHECK = "Sublot status check configuration";
    private long identifiedOSIKey;
    private int weighingCondition;
    private MultiMap<Long, Long> predecessorOSOKeysToOsis;
    private IMeasuredValue prorateFactor;
    private IMeasuredValue declaredWaste;
    private IMeasuredValue newRemainingQty;
    private boolean doResetBOMPosition;
    private final Map<String, Boolean> idOnly;
    private boolean doUserTriggeredReleaseScale;
    private static final List<IMESChoiceElement> ALLOWED_CAMPAIGN_OSI_STATES;
    private List<String> usedPositionsOfMaterialParams;

    public IMeasuredValue getProrateFactor() {
        return this.prorateFactor;
    }

    public void setProrateFactor(final IMeasuredValue prorateFactor) {
        this.prorateFactor = prorateFactor;
    }

    public IMeasuredValue getDeclaredWaste() {
        return this.declaredWaste;
    }

    public void setDeclaredWaste(final IMeasuredValue declaredWaste) {
        this.declaredWaste = declaredWaste;
    }

    public void setNewRemainingQty(final IMeasuredValue remainingQty) {
        this.newRemainingQty = remainingQty;
    }

    public IMeasuredValue getNewRemainingQty() {
        return this.newRemainingQty;
    }

    public void setDoResetBOMPosition(final boolean doReset) {
        this.doResetBOMPosition = doReset;
        if (this.doResetBOMPosition) {
            this.newRemainingQty = null;
            this.declaredWaste = null;
        }
    }

    public boolean isDoResetBOMPosition() {
        return this.doResetBOMPosition;
    }

    protected MatIdentModel0610(final RtPhaseExecutorWDMatIdent0010 inPhaseExecutor) {
        super((WDAbstractWeighPhaseExecutor0610)inPhaseExecutor);
        this.weighingCondition = -1;
        this.predecessorOSOKeysToOsis = null;
        this.doResetBOMPosition = false;
        this.idOnly = new HashMap<String, Boolean>();
        this.usedPositionsOfMaterialParams = null;
    }

    public long getIdentifiedOSIKey() {
        return this.identifiedOSIKey;
    }

    public OrderStepInput getIdentifiedOSI() {
        return this.getOSIByKey(this.identifiedOSIKey);
    }

    public void setIdentifiedOSI(final OrderStepInput identifiedOSI) {
        this.identifiedOSIKey = ((identifiedOSI != null) ? identifiedOSI.getKey() : 0L);
    }

    public Sublot getIdentifiedSublot() {
        final OrderStepInput identifiedOSI = this.getIdentifiedOSI();
        return (identifiedOSI != null) ? identifiedOSI.getAttachSublot() : null;
    }

    public boolean hasSublotBeenIdentified() {
        return this.getIdentifiedOSI() != null;
    }

    public boolean isUndoIdentificationApplicable() {
        return this.isPhaseActive() && this.getIdentifiedSublot() != null && this.isIdentificationOnly(this.getIdentifiedOSI());
    }

    public List<OrderStepInput> getMasterOSIsRelevantForThisRtPhase(final boolean applyCampaignFilter) {
        final MESAllMasterOSIs theOSIs = new MESAllMasterOSIs(this.getRefreshedOS());
        theOSIs.sortByWeighingSequence(true);
        this.filterOutNotRelevantOSIs((List<OrderStepInput>)theOSIs, applyCampaignFilter);
        return (List<OrderStepInput>)theOSIs;
    }

    public List<OrderStepInput> getAllOSIsRelevantForThisRtPhase(final boolean applyCampaignFilter) {
        final Vector orderStepInputItems = this.getRefreshedOS().getOrderStepInputItems();
        this.filterOutNotRelevantOSIs(orderStepInputItems, applyCampaignFilter);
        return (List<OrderStepInput>)orderStepInputItems;
    }

    private void filterOutNotRelevantOSIs(final List<OrderStepInput> theOSIs, final boolean applyCampaignFilter) {
        final boolean b = this.isCampaign() && applyCampaignFilter;
        if (this.isDispenseWeighing() && !b) {
            return;
        }
        final List<String> positionsOfMatList = b ? Collections.emptyList() : this.getUsedPositionsOfMaterialParameters();
        final Iterator<OrderStepInput> iterator = theOSIs.iterator();
        while (iterator.hasNext()) {
            final OrderStepInput orderStepInput = iterator.next();
            if (!(b ? this.isVisibleInCampaign(orderStepInput) : this.isPositionInMaterialList(orderStepInput, positionsOfMatList))) {
                iterator.remove();
            }
        }
    }

    public boolean isOSIRelevantForThisRtPhase(final OrderStepInput osiBoundToCurrentRoom) {
        return this.isDispenseWeighing() || this.getUsedPositionsOfMaterialParameters().contains(MESNamedUDAOrderStepInput.getPosition(osiBoundToCurrentRoom));
    }

    private List<String> getUsedPositionsOfMaterialParameters() {
        if (this.usedPositionsOfMaterialParams == null) {
            this.setUsedPositionsOfMaterialParams(this.determineUsedPositionsOfMaterialParams());
        }
        return this.usedPositionsOfMaterialParams;
    }

    private void setUsedPositionsOfMaterialParams(final List<String> positions) {
        this.usedPositionsOfMaterialParams = positions;
    }

    private List<String> determineUsedPositionsOfMaterialParams() {
        final OrderStep orderStep = this.getOrderStep();
        final MESAllMasterOSIs mesAllMasterOSIs = new MESAllMasterOSIs(orderStep);
        final ArrayList<String> list = new ArrayList<String>();
        final List<String> positionsOfMaterialParameters = this.getPositionsOfMaterialParameters();
        final Vector orderStepInputItems = orderStep.getOrderStepInputItems();
        for (final OrderStepInput osi : mesAllMasterOSIs) {
            final String position = MESNamedUDAOrderStepInput.getPosition(osi);
            if (positionsOfMaterialParameters.contains(position) || this.isOriginalMasterOSIAPositionOf(osi, positionsOfMaterialParameters, orderStepInputItems)) {
                list.add(position);
            }
        }
        return list;
    }

    private List<String> getPositionsOfMaterialParameters() {
        final ArrayList<String> list = new ArrayList<String>();
        final Iterator<IMESMaterialParameter> iterator = this.executor.getPhase().getMaterialParameters().iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().getMFCPosition());
        }
        return list;
    }

    private boolean isOriginalMasterOSIAPositionOf(final OrderStepInput osi, final List<String> positionsOfMatList, final List<OrderStepInput> allOsis) {
        OrderStepInput osiByKeyInList;
        for (Long osiKey = MESNamedUDAOrderStepInput.getReplacedOSI(osi); osiKey != null; osiKey = MESNamedUDAOrderStepInput.getReplacedOSI(osiByKeyInList)) {
            osiByKeyInList = this.findOsiByKeyInList(osiKey, allOsis);
            if (this.isPositionInMaterialList(osiByKeyInList, positionsOfMatList)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPositionInMaterialList(final OrderStepInput replacedOsi, final List<String> positionsOfMatList) {
        return positionsOfMatList.contains(MESNamedUDAOrderStepInput.getPosition(replacedOsi));
    }

    private OrderStepInput findOsiByKeyInList(final Long osiKey, final List<OrderStepInput> listOfAllOsis) {
        for (final OrderStepInput orderStepInput : listOfAllOsis) {
            if (orderStepInput.getKey() == osiKey) {
                return orderStepInput;
            }
        }
        return null;
    }

    public boolean isOSOPredecessor(final Long key) {
        if (this.predecessorOSOKeysToOsis == null) {
            this.createPredecessorOSOKeysToOsis();
        }
        return this.predecessorOSOKeysToOsis.containsKey(key);
    }

    private void createPredecessorOSOKeysToOsis() {
        this.predecessorOSOKeysToOsis = new MultiMap();
        List var1 = this.getMasterOSIsRelevantForThisRtPhase(true);
        Iterator var2 = var1.iterator();

        while(var2.hasNext()) {
            OrderStepInput var3 = (OrderStepInput)var2.next();
            Vector var4 = var3.getPredecessorOrderStepOutputs();
            Iterator var5 = var4.iterator();

            while(var5.hasNext()) {
                OrderStepOutput var6 = (OrderStepOutput)var5.next();
                this.predecessorOSOKeysToOsis.add(var6.getKey(), var3.getKey());
            }
        }

    }

    public Collection<OrderStepInput> getMasterOsisOfPredecessorOSO(final Long key) {
        if (this.predecessorOSOKeysToOsis == null) {
            this.createPredecessorOSOKeysToOsis();
        }
        final Collection value = this.predecessorOSOKeysToOsis.get(key);
        return (Collection<OrderStepInput>)(value.isEmpty() ? Collections.emptyList() : this.getOsisFor(value));
    }

    private Collection<OrderStepInput> getOsisFor(final Collection<Long> osiKeys) {
        final ArrayList<OrderStepInput> list = new ArrayList<OrderStepInput>();
        for (final OrderStepInput orderStepInput : this.getMasterOSIsRelevantForThisRtPhase(true)) {
            if (osiKeys.contains(orderStepInput.getKey())) {
                list.add(orderStepInput);
            }
        }
        return list;
    }

    private boolean isVisibleInCampaign(final OrderStepInput originOSI) {
        final OrderStepInput unfinishedOSI = OrderUtils.getUnfinishedOSI(originOSI);
        return unfinishedOSI != null && PCContext.getFunctions().getCurrentWorkCenter().equals((Object)MESNamedUDAOrderStepInput.getWorkCenter(unfinishedOSI)) && MatIdentModel0610.ALLOWED_CAMPAIGN_OSI_STATES.contains(MESNamedUDAOrderStepInput.getStatus(unfinishedOSI));
    }

    public List<OrderStepInput> getSplitOSIsForOSI(final OrderStepInput osi) {
        OrderStep var2 = this.getOrderStep();
        String var3 = MESNamedUDAOrderStepInput.getPosition(osi);
        ArrayList var4 = new ArrayList<>();
        Iterator var5 = var2.getOrderStepInputItems().iterator();

        while(var5.hasNext()) {
            OrderStepInput var6 = (OrderStepInput)var5.next();
            String var7 = MESNamedUDAOrderStepInput.getPosition(var6);
            Long var8 = MESNamedUDAOrderStepInput.getSplitNumber(var6);
            if (var3.equals(var7) && var8 != null && var8 > 0L) {
                var4.add(var6);
            }
        }

        return var4;
    }

    private OrderStepInput getLastFinishedSplitOsiWithAttachedSublotIntern() {
        OrderStepInput var1 = this.getSelectedOSI();
        if (var1 == null) {
            return null;
        } else if (var1.getAttachSublot() != null) {
            return var1;
        } else {
            Long var2 = MESNamedUDAOrderStepInput.getSplitNumber(var1);
            if (var2 != null && var2 == 0L) {
                return var1;
            } else {
                List var3 = this.getAllSplitOSIsOfSelectedOSI();
                Iterator var4 = var3.iterator();

                OrderStepInput var5;
                do {
                    if (!var4.hasNext()) {
                        return var1;
                    }

                    var5 = (OrderStepInput)var4.next();
                } while(var5.getAttachSublot() == null);

                return var5;
            }
        }
    }

    public OrderStepInput getLastFinishedSplitOsiWithAttachedSublot() {
        final OrderStepInput lastFinishedSplitOsiWithAttachedSublotIntern = this.getLastFinishedSplitOsiWithAttachedSublotIntern();
        if (lastFinishedSplitOsiWithAttachedSublotIntern == null || lastFinishedSplitOsiWithAttachedSublotIntern.getAttachSublot() == null) {
            throw new MESRuntimeException("Inconsistence, 'keepTarget' is true, but no last finished osi, with attached sublot.");
        }
        return lastFinishedSplitOsiWithAttachedSublotIntern;
    }

    public OrderStepInput findOSIForSublotUsingPart(final Sublot sublot, final OrderStepInput selectedOsi) {
        final List<OrderStepInput> allOSIsRelevantForThisRtPhase = this.getAllOSIsRelevantForThisRtPhase(true);
        OrderStepInput orderStepInput = null;
        if (this.getKeepTarget() && this.getSplitOSI() != null && this.getSplitOSI().getPart().equals((Object)sublot.getPart())) {
            if (MatIdentModel0610.LOGGER.isInfoEnabled()) {
                MatIdentModel0610.LOGGER.info((Object)("found-osi   /" + WDHelper0610.getReadableOSIInfo(orderStepInput)));
            }
            return this.getSplitOSI();
        }
        final OrderStepInputNumberComparator create = OrderStepInputComparatorFactory.WEIGHING_SEQUENCE.create((Collection)allOSIsRelevantForThisRtPhase);
        final String s = (selectedOsi != null && sublot.getPart().equals((Object)selectedOsi.getPart())) ? MESNamedUDAOrderStepInput.getPosition(selectedOsi) : null;
        final WorkCenter currentWorkCenter = PCContext.getFunctions().getCurrentWorkCenter();
        OrderStepInput orderStepInput2 = null;
        for (final OrderStepInput orderStepInput3 : allOSIsRelevantForThisRtPhase) {
            if (orderStepInput3.getPart().equals((Object)sublot.getPart()) && (MESNamedUDAOrderStepInput.getStatus(orderStepInput3).equals(EnumOrderStepInputStatus.OSI_STATUS_CREATED) || MESNamedUDAOrderStepInput.getStatus(orderStepInput3).equals(EnumOrderStepInputStatus.OSI_STATUS_SELECTED)) && (!this.isCampaign() || currentWorkCenter.equals((Object)MESNamedUDAOrderStepInput.getWorkCenter(orderStepInput3)))) {
                if (s != null) {
                    if (MESNamedUDAOrderStepInput.getPosition(orderStepInput3).equals(s)) {
                        orderStepInput = orderStepInput3;
                        break;
                    }
                    continue;
                }
                else {
                    if (orderStepInput != null && create.compare(orderStepInput3, orderStepInput) >= 0) {
                        continue;
                    }
                    orderStepInput = orderStepInput3;
                }
            }
            else {
                if (this.isCampaign() || !orderStepInput3.getPart().equals((Object)sublot.getPart()) || MESNamedUDAOrderStepInput.getStatus(orderStepInput3).equals(EnumOrderStepInputStatus.OSI_STATUS_COMPLETED) || MESNamedUDAOrderStepInput.getStatus(orderStepInput3).equals(EnumOrderStepInputStatus.OSI_STATUS_CANCELLED) || currentWorkCenter.equals((Object)MESNamedUDAOrderStepInput.getWorkCenter(orderStepInput3))) {
                    continue;
                }
                orderStepInput2 = orderStepInput3;
            }
        }
        if (MatIdentModel0610.LOGGER.isInfoEnabled()) {
            MatIdentModel0610.LOGGER.info((Object)("found-osi   /" + WDHelper0610.getReadableOSIInfo(orderStepInput)));
        }
        if (orderStepInput == null && orderStepInput2 != null) {
            WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "osiLocked_ErrorMsg", new Object[0]);
        }
        else if (orderStepInput == null) {
            WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "osiNotFound_ErrorMsg", new Object[0]);
        }
        return orderStepInput;
    }

    public boolean isMaterialComplete() {
        boolean var1 = true;
        WorkCenter var2 = PCContext.getFunctions().getCurrentWorkCenter();
        OrderStep var3 = this.getOrderStep();
        IWDOrderStepInputService var4 = (IWDOrderStepInputService)ServiceFactory.getService(IWDOrderStepInputService.class);
        if (this.isCampaign()) {
            if (var4.isUncompleted(var3)) {
                try {
                    var3.refresh();
                } catch (DatasweepException var8) {
                    throw new MESRuntimeException(var8);
                }

                Vector var5 = var3.getOrderStepInputItems();
                Iterator var6 = var5.iterator();

                while(var6.hasNext()) {
                    OrderStepInput var7 = (OrderStepInput)var6.next();
                    if (var2.equals(MESNamedUDAOrderStepInput.getWorkCenter(var7)) && var4.isUncompleted(var7)) {
                        var1 = false;
                    }
                }
            }
        } else {
            List var9 = this.getAllOSIsRelevantForThisRtPhase(false);
            var1 = var4.isOSIComplete(var9);
        }

        return var1;
    }

    public MESRtPhaseOutputWDMatIdent0010 getRtPhaseOutput() {
        return ((RtPhaseExecutorWDMatIdent0010)this.executor).getRtPhaseOutput();
    }

    public Sublot getPrintedSublot() {
        final Long printedSublotKey = ((MESRtPhaseDataWDMatIdent0010)this.getRtPhaseData()).getPrintedSublotKey();
        return (printedSublotKey == null) ? null : PCContext.getFunctions().getSublotByKey((long)printedSublotKey);
    }

    public void setPrintedSublot(final Sublot targetSublot) {
        ((MESRtPhaseDataWDMatIdent0010)this.getRtPhaseData()).setPrintedSublotKey((targetSublot == null) ? null : targetSublot.getKey());
    }

    public int getWeighingCondition() {
        return this.weighingCondition;
    }

    public void setWeighingCondition(final int weighingCondition) {
        this.weighingCondition = weighingCondition;
    }

    public boolean getIdOnly(final OrderStepInput osi) {
        if (osi == null) {
            return false;
        }
        final String position = MESNamedUDAOrderStepInput.getPosition(osi);
        if (!this.idOnly.containsKey(position)) {
            final IMESChoiceElement defaultWeighingMethod = MESNamedUDAOrderStepInput.getDefaultWeighingMethod(osi);
            final IMESChoiceElement restrictedWeighingMethod = this.getRestrictedWeighingMethod(osi);
            this.setIdOnly(osi, (restrictedWeighingMethod == null || EnumWeighingMethods.IDENTIFICATION_ONLY.equals(restrictedWeighingMethod)) && EnumWeighingMethods.IDENTIFICATION_ONLY.equals(defaultWeighingMethod));
        }
        return this.idOnly.get(position);
    }

    public void setIdOnly(final OrderStepInput osi, final boolean flag) {
        this.idOnly.put(MESNamedUDAOrderStepInput.getPosition(osi), flag);
    }

    public boolean isEditableIdOnly(final OrderStepInput osi) {
        if (this.executor != null) {
            final OSIPositionStatus value = OSIPositionStatus.get(osi);
            if (value == OSIPositionStatus.NOT_STARTED || value == OSIPositionStatus.IN_PROCESS) {
                final List allowedWeighingMethods = this.getAllowedWeighingMethods(osi, false);
                return allowedWeighingMethods.contains(EnumWeighingMethods.IDENTIFICATION_ONLY) && allowedWeighingMethods.size() > 1;
            }
        }
        return false;
    }

    public boolean isDisplaySplitPositionsEnabled() {
        return WeighingOperationType0610.getWeighingOperationType((IPhaseExecutor)this.executor).isDisplaySplitPositionsEnabled();
    }

    public boolean getReleaseScaleTriggered() {
        this.verifyStatusActive("releaseScaleTriggered");
        return OperationContext0610.getReleaseScaleTriggered((IPhaseExecutorBasic)this.executor);
    }

    public void setReleaseScaleTriggered() {
        this.setReleaseScaleTriggered(true);
    }

    private void setReleaseScaleTriggered(final boolean value) {
        this.verifyStatusActive("releaseScaleTriggered");
        OperationContext0610.setReleaseScaleTriggered((IPhaseExecutorBasic)this.executor, value);
        OperationContext0610.save((IPhaseExecutorBasic)this.executor);
    }

    public boolean isBlocked() {
        return super.isBlocked() || (this.hasSublotBeenIdentified() && !this.isUndoIdentificationApplicable()) || this.getReleaseScaleTriggered() || this.executor.isExceptionSigned("RECORD_WAREHOUSE_ERROR_CHECK_KEY");
    }

    public boolean isDoUserTriggeredReleaseScale() {
        return !this.isBlocked() && this.doUserTriggeredReleaseScale;
    }

    public void setDoUserTriggeredReleaseScale(final boolean doReleaseScale) {
        this.doUserTriggeredReleaseScale = doReleaseScale;
    }

    public WeighingOperationTypeWDMatIdent0610 getWeighingOperationTypeWDMatIdent0610() {
        return WeighingOperationTypeWDMatIdent0610.getWeighingOperationType((IPhaseExecutor)this.executor);
    }

    public void setKeepTarget(final boolean keepTarget) {
        super.setKeepTarget(keepTarget);
        this.getRtPhaseOutput().setTargetClosed(!keepTarget);
    }

    public IdentificationResult0610 getIdentificationResult() {
        if (!this.isPhaseCompleted()) {
            return null;
        }
        return this.getRtPhaseOutput().getIdentificationResult();
    }

    public boolean isWeighingSequence() {
        for (final IMESMaterialParameter imesMaterialParameter : this.getRtPhase().getPhase().getMaterialParameters()) {
            if (imesMaterialParameter.isMFCRelevant()) {
                return imesMaterialParameter.getWeighingSequence() != null;
            }
        }
        return false;
    }

    static {
        LOGGER = LogFactory.getLog((Class)MatIdentModel0610.class);
        ALLOWED_CAMPAIGN_OSI_STATES = Arrays.asList(EnumOrderStepInputStatus.OSI_STATUS_SELECTED, EnumOrderStepInputStatus.OSI_STATUS_IDENTIFIED);
    }
}
