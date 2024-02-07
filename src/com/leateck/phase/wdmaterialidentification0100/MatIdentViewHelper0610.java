package com.leateck.phase.wdmaterialidentification0100;

import com.rockwell.mes.clientfw.commons.ifc.view.activities.*;
import com.rockwell.mes.apps.ebr.ifc.phase.*;
import com.rockwell.mes.clientfw.commons.ifc.swing.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import com.rockwell.mes.commons.base.ifc.nameduda.*;
import com.rockwell.mes.services.order.ifc.*;
import com.rockwell.mes.commons.base.ifc.choicelist.*;
import com.rockwell.mes.services.wd.ifc.*;
import com.rockwell.mes.commons.base.ifc.functional.*;
import com.datasweep.plantops.common.measuredvalue.*;
import com.rockwell.mes.commons.base.ifc.exceptions.*;
import org.apache.commons.lang3.*;
import com.datasweep.compatibility.client.*;
import com.rockwell.mes.commons.base.ifc.i18n.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import com.rockwell.mes.shared.product.wd.*;
import com.rockwell.mes.services.eqm.ifc.*;
import java.util.*;
import org.apache.commons.logging.*;

public class MatIdentViewHelper0610
{
    private static final String REOPEN_VALUE_NA = "ReopenValueNA";
    private static final int POS_COLUMN_WIDTH = 45;
    private static final int MATERIAL_COLUMN_WIDTH = 220;
    private static final int BATCH_COLUMN_WIDTH = 150;
    private static final int QUANTITY_COLUMN_WIDTH = 80;
    private static final int TARGET_COLUMN_WIDTH = 35;
    private static final int RESULT_COLUMN_WIDTH = 80;
    private static final int CLEANING_COLUMN_WIDTH = 50;
    private static final int IDONLY_COLUMN_WIDTH = 45;
    private static final Map<AllowedWithWarningException.WarningReason, String> WARNING_TO_MESSAGE_MAP;
    protected static final Log LOGGER;

    public static MatIdentActiveGrid0610 buildMatIdentActiveGrid(final IWDMatIdentModel0610 model, final boolean withIdOnly) {
        final ArrayList<StandardColumnConfig> list = new ArrayList<StandardColumnConfig>();
        list.add(getColumnConfigWeighingSequence(model));
        list.add(new StandardColumnConfig("matDisplayString", 220, StandardColumnConfig.LineType.MULTI_LINE));
        list.add(new StandardColumnConfig("relBatchesSublotsDisplayStr", 150, StandardColumnConfig.LineType.MULTI_LINE));
        list.add(new StandardColumnConfig("plannedOrigQtyDisplayStr", 80, StandardColumnConfig.LineType.MULTI_LINE, 4));
        list.add(new StandardColumnConfig("actualQuantity", 80, StandardColumnConfig.LineType.SINGLE_LINE, 4));
        list.add(new StandardColumnConfig("remainingQuantity", 80, StandardColumnConfig.LineType.SINGLE_LINE, 4));
        list.add(new StandardColumnConfig("targetSublotCount", 35, StandardColumnConfig.LineType.SINGLE_LINE, 0));
        list.add(new StandardColumnConfig("cleaning", 50, StandardColumnConfig.LineType.SINGLE_LINE, 0));
        list.add(new StandardColumnConfig("result", 80, StandardColumnConfig.LineType.SINGLE_LINE, 0));
        int size;
        if (withIdOnly) {
            list.add(new StandardColumnConfig("idOnly", 45, StandardColumnConfig.LineType.SINGLE_LINE, 0));
            size = list.size() - 1;
        }
        else {
            size = list.size();
        }
        MatIdentActiveGrid0610 matIdentActiveGrid0610 = new MatIdentActiveGrid0610(size, model);
        matIdentActiveGrid0610.setOpaque(false);
        matIdentActiveGrid0610.setEnableIgnored(true);
        matIdentActiveGrid0610.setEnabled(IPhaseExecutor.Status.ACTIVE.equals((Object)model.getStatus()));
        matIdentActiveGrid0610.setMinimalRowHeight(25);
        matIdentActiveGrid0610.setSortable(false);
        matIdentActiveGrid0610.setReorderingAllowed(false);
        matIdentActiveGrid0610.setCreateScrollbar(false);
        matIdentActiveGrid0610.setWithSelectColumn(false);
        matIdentActiveGrid0610.setUseShortLabels(false);
        matIdentActiveGrid0610.setBoundClassName(MatIdentRow0610.class.getName());
        matIdentActiveGrid0610.configureGrid((List)list);
        matIdentActiveGrid0610.startup();
        return matIdentActiveGrid0610;
    }

    private static StandardColumnConfig getColumnConfigWeighingSequence(final IWDMatIdentModel0610 model) {
        return model.isWeighingSequence() ? new StandardColumnConfig("weighingSequenceDisplayString", 45, StandardColumnConfig.LineType.MULTI_LINE, 0) : new StandardColumnConfig("position", 45, StandardColumnConfig.LineType.SINGLE_LINE, 0);
    }

    public static void refreshMaterialGrid(final MESStandardGrid grid, final IWDMatIdentModel0610 model, final BOMPositionType0610 bomPositionType) {
        final IMESRoomEquipment currentRoomWithoutException = WDHelper0610.getCurrentRoomWithoutException(true);
        if (currentRoomWithoutException == null) {
            return;
        }
        GxPContextMap.enableAndFlushCache(true);
        setGridObjects(grid, model, bomPositionType, getGridObjects(model, bomPositionType, ((IMESRoomEquipmentService)ServiceFactory.getService((Class)IMESRoomEquipmentService.class)).getLastGxPContext((IMESEquipment)currentRoomWithoutException), getBindingsForOS(model)));
    }

    private static List<MatIdentRow0610> getGridObjects(IWDMatIdentModel0610 model, BOMPositionType0610 bomPositionType, final GxPContextMap lastBindings, final GxPContextMap bindingsForOS) {
        ArrayList var4 = new ArrayList();
        IWDOrderStepInputService var5 = (IWDOrderStepInputService)ServiceFactory.getService(IWDOrderStepInputService.class);
        List var6 = model.getMasterOSIsRelevantForThisRtPhase(true);
        Map var7 = var5.getOpenSplitOSIorOSIMap(var6, model.getOrderStep());
        boolean var8 = false;
        Iterator var9 = var6.iterator();

        while(var9.hasNext()) {
            OrderStepInput var10 = (OrderStepInput)var9.next();
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info(WDHelper0610.getReadableOSIInfo(var10));
            }

            if (addOsiToRow(bomPositionType, var5, var10)) {
                if (!var8 && model.getRtPhase() != null) {
                    WDHelper0610.tryToUpdateExpiryOnCurrentRoomIfExpired(model.getRtPhase());
                    var8 = true;
                }

                MatIdentRowOSIMaster0610 var11 = new MatIdentRowOSIMaster0610(model, var10, (OrderStepInput)var7.get(var10.getKey()), lastBindings, bindingsForOS);
                var4.add(var11);
                if (com.rockwell.mes.phase.product.wdmaterialidentification.BOMPositionType0610.ANY.equals(bomPositionType) && model.isDisplaySplitPositionsEnabled()) {
                    addSplitOSIs(model, var4, var10, var11);
                }
            }
        }

        return var4;
    }

    private static GxPContextMap getBindingsForOS(final IWDMatIdentModel0610 model) {
        return ((IGxPContextMapService)ServiceFactory.getService((Class)IGxPContextMapService.class)).createGxPContextForOrderStep(model.getOrderStep(), model.getCurrentWeighingMode());
    }

    private static boolean addOsiToRow(final BOMPositionType0610 bomPositionType, final IWDOrderStepInputService wdOSISrv, final OrderStepInput masterOsi) {
        boolean b = true;
        if (BOMPositionType0610.ABORTABLE.equals(bomPositionType)) {
            final IMESChoiceElement status = MESNamedUDAOrderStepInput.getStatus(masterOsi);
            final OSIPositionStatus value = OSIPositionStatus.get(masterOsi);
            b = (EnumOrderStepInputStatus.OSI_STATUS_CREATED.equals(status) || EnumOrderStepInputStatus.OSI_STATUS_SELECTED.equals(status) || OSIPositionStatus.IN_PROCESS.equals((Object)value));
            if (b && WDOSIServiceHelper0610.isAllowedToDeclareFinish(masterOsi)) {
                b = false;
            }
        }
        else if (BOMPositionType0610.COMPLETABLE.equals(bomPositionType)) {
            b = WDOSIServiceHelper0610.isAllowedToDeclareFinish(masterOsi);
            if (b) {
                WDOSIServiceHelper0610.calculateAndSetPlannedQuantityAndFieldsForDynamicAsProduced(masterOsi);
            }
        }
        else if (BOMPositionType0610.REOPEN_OR_WASTE_ALLOWED.equals(bomPositionType)) {
            b = wdOSISrv.isPositionReopenable(masterOsi);
        }
        return b;
    }

    private static void addSplitOSIs(final IWDMatIdentModel0610 model, final List<MatIdentRow0610> matIdentRowActiveList, final OrderStepInput osi, final MatIdentRow0610 masterOsiRow) {
        final Iterator<List<OrderStepInput>> iterator = getOSIsOfTargetSublot(osi).iterator();
        while (iterator.hasNext()) {
            matIdentRowActiveList.add(new MatIdentRowOSISplit0610(model, osi, iterator.next(), masterOsiRow));
        }
    }

    private static void setGridObjects(final MESStandardGrid grid, final IWDMatIdentModel0610 model, final BOMPositionType0610 bomPositionType, final List<MatIdentRow0610> matIdentRowActiveList) {
        grid.setObjects((List)matIdentRowActiveList);
        if (grid instanceof MatIdentActiveGrid0610) {
            ((MatIdentActiveGrid0610)grid).setupCustomRenderers();
        }
        if (BOMPositionType0610.ANY.equals(bomPositionType)) {
            final int positionToProceed = getPositionToProceed(matIdentRowActiveList, model);
            if (positionToProceed < 0) {
                grid.setAllRowObjectsDeSelected();
            }
            else {
                grid.setSelectedRow(positionToProceed);
            }
        }
        else {
            grid.setAllRowObjectsDeSelected();
        }
    }

    private static int getPositionToProceed(final List<MatIdentRow0610> matIdentRowActiveList, final IWDMatIdentModel0610 model) {
        int n = -1;
        final OrderStepInput splitOSI = model.getSplitOSI();
        if (splitOSI == null) {
            return n;
        }
        final String position = MESNamedUDAOrderStepInput.getPosition(splitOSI);
        for (final MatIdentRow0610 matIdentRow0610 : matIdentRowActiveList) {
            ++n;
            if (matIdentRow0610.isMasterRow() && matIdentRow0610.getPosition().equals(position)) {
                return n;
            }
        }
        return n;
    }

    private static List<List<OrderStepInput>> getOSIsOfTargetSublot(final OrderStepInput osiOfPosition) {
        if (OSIPositionStatus.NOT_STARTED.equals((Object)OSIPositionStatus.get(osiOfPosition))) {
            return Collections.emptyList();
        }
        final MESAllSplitOSIs mesAllSplitOSIs = new MESAllSplitOSIs(osiOfPosition, false);
        mesAllSplitOSIs.sortByPosition(true);
        final ArrayList<List<OrderStepInput>> list = new ArrayList<List<OrderStepInput>>();
        List<OrderStepInput> list2 = null;
        for (final OrderStepInput osi : mesAllSplitOSIs) {
            final boolean osiCompleted = isOsiCompleted(osi);
            if (osiCompleted) {
                if (osiCompleted && MeasuredValueUtilities.isNullOrZero((IMeasuredValue)osi.getActualQuantity())) {
                    continue;
                }
                final Long intendedSplitKeepTargetSL = MESNamedUDAOrderStepInput.getIntendedSplitKeepTargetSL(osi);
                if (intendedSplitKeepTargetSL == null || (int)(Object)intendedSplitKeepTargetSL == 0) {
                    list2 = new ArrayList<OrderStepInput>();
                    list.add(list2);
                }
                if (list2 == null) {
                    throw new MESRuntimeException("List to add osi is unexpected null.");
                }
                list2.add(osi);
            }
        }
        return list;
    }

    private static boolean isOsiCompleted(final OrderStepInput osi) {
        return EnumOrderStepInputStatus.OSI_STATUS_COMPLETED.getValue().equals(MESNamedUDAOrderStepInput.getStatusAsValue(osi));
    }

    static void fillCancelBOMPosException(final MatIdentRow0610 row, final RtPhaseExecutorWDMatIdent0010 executor) {
        if (row == null) {
            WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "CancelBOMItemExceptionTxt", new Object[0]);
            return;
        }
        AbstractWeighExceptionView0610.fillParameterizedException("Abort BOM position", "ABORT_BOM_POSITION", (AbstractWeighPhaseBaseExecutor0610)executor, StringUtils.join(new Object[] { row.getPosition(), row.getMatIdentifier(), row.getMatShortDescription() }, " / "));
    }

    static void fillCompleteBOMPosException(final MatIdentRow0610 row, final String additionalExceptionText, final RtPhaseExecutorWDMatIdent0010 executor) {
        if (row == null) {
            WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "CompleteBOMItemExceptionTxt", new Object[0]);
            return;
        }
        String s = StringUtils.join(new Object[] { row.getPosition(), row.getMatIdentifier(), row.getMatShortDescription() }, " / ");
        if (!additionalExceptionText.isEmpty()) {
            s += additionalExceptionText;
        }
        AbstractWeighExceptionView0610.fillParameterizedException("Complete position", "COMPLETE_BOM_POSITION", (AbstractWeighPhaseBaseExecutor0610)executor, s);
    }

    static String buildAdditionalExceptionTextForDynAsProduced(final int weighingCondition, final IMeasuredValue[] limits, final MeasuredValue plannedQty, final MeasuredValue actualWeight) {
        return I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", (0 == weighingCondition) ? "CompleteBOMItemDynAsProducedAddExceptionTxt" : "CompleteBOMItemDynAsProducedWLimitsAddExceptionTxt", new Object[] { plannedQty, OSIPositionStatus.getOSIPositionStatus(weighingCondition).getLocalizedName(), limits[1], actualWeight, limits[2] });
    }

    static void fillPrintCloseTargetException(final RtPhaseExecutorWDMatIdent0010 executor, final IWDMatIdentModel0610 model, final StatusTransitionFailureHelper0610.StatusTransitionFailureSupport transitionFailure) {
        AbstractWeighExceptionView0610.fillParameterizedException("Close target sublot", "CLOSE_TARGET", (AbstractWeighPhaseBaseExecutor0610)executor, model.getWeighingOperationTypeWDMatIdent0610().getAdditionalInfoForCloseTargetExeption(model), (transitionFailure != null) ? transitionFailure.getRiskClass() : null, (transitionFailure != null) ? transitionFailure.getExceptionMsg() : "");
    }

    static void fillOverrideProrateFactorException(final RtPhaseExecutorWDMatIdent0010 executor, final IMeasuredValue newProrateFactor, final Collection<OrderStepInput> proratedOSIs) {
        final IMeasuredValue prorateFactor = executor.getProrateFactor();
        AbstractWeighExceptionView0610.fillParameterizedException("Override prorate factor", "OVERRIDE_PRORATE_FACTOR", (AbstractWeighPhaseBaseExecutor0610)executor, I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "OverrideProrateFactorTxtInline", new Object[] { (prorateFactor != null) ? prorateFactor : "N/A", newProrateFactor, StringUtilsEx.buildCommaSeparatedListForUI((List)getPositions(proratedOSIs)) }));
    }

    private static List<String> getPositions(final Collection<OrderStepInput> proratedOSIs) {
        final ArrayList<String> list = new ArrayList<String>(proratedOSIs.size());
        final Iterator<OrderStepInput> iterator = proratedOSIs.iterator();
        while (iterator.hasNext()) {
            list.add(MESNamedUDAOrderStepInput.getPosition((OrderStepInput)iterator.next()));
        }
        return list;
    }

    static void fillUndoIdentificationException(final RtPhaseExecutorWDMatIdent0010 executor, final IWDMatIdentModel0610 model, final StatusTransitionFailureHelper0610.StatusTransitionFailureSupport transitionFailure) {
        final String sublotName = AbstractWeighView0610.getSublotName(model.getIdentifiedSublot());
        final IMESContainerEquipment currentSourceContainer = model.getCurrentSourceContainer();
        String s;
        if (currentSourceContainer != null) {
            s = I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "undoIdentificationWithContainerExceptionRecTxt", new Object[] { sublotName, currentSourceContainer.getName() });
        }
        else {
            s = I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "undoIdentificationExceptionRecTxt", new Object[] { sublotName });
        }
        AbstractWeighExceptionView0610.fillParameterizedException("Undo identification", "UNDO_IDENTIFICATION", (AbstractWeighPhaseBaseExecutor0610)executor, s, (transitionFailure != null) ? transitionFailure.getRiskClass() : null, (transitionFailure != null) ? transitionFailure.getExceptionMsg() : "");
    }

    static void fillReopenBOMPosException(final IMeasuredValue newRemainingQty, final IMeasuredValue declaredWaste, final MatIdentRow0610 row, final AllowedWithWarningException.WarningReason warningReason, final RtPhaseExecutorWDMatIdent0010 executor) {
        if (row == null) {
            WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "selectBOMPosition_ErrorMsg", new Object[0]);
            return;
        }
        String s = getReopenExceptionText(row, newRemainingQty, declaredWaste, warningReason);
        if (warningReason != null && StringUtilsEx.isNotEmpty((CharSequence)MatIdentViewHelper0610.WARNING_TO_MESSAGE_MAP.get(warningReason))) {
            s = AbstractWeighExceptionView0610.combineString(s, (String)MatIdentViewHelper0610.WARNING_TO_MESSAGE_MAP.get(warningReason));
        }
        AbstractWeighExceptionView0610.fillParameterizedException("Re-open position / declare waste", "REOPEN_POSITION_DECLARE_WASTE", (AbstractWeighPhaseBaseExecutor0610)executor, s);
    }

    private static String getReopenExceptionText(final MatIdentRow0610 row, final IMeasuredValue newRemainingQty, final IMeasuredValue declaredWaste, final AllowedWithWarningException.WarningReason warningReason) {
        final OrderStepInput osi = row.getOSI();
        final String localizedMessage = I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "ReopenValueNA");
        return I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "ReopenExceptionTxtInline", new Object[] { getAdditionalInfoRow(row), getQtyValueForExceptionText(declaredWaste), getQtyValueForExceptionText((IMeasuredValue)MESNamedUDAOrderStepInput.getTotalActualQuantity(osi)), getQtyValueForExceptionText(getNewRecordedQuantity(osi, declaredWaste)), (WDOSIServiceHelper0610.isPlannedQtyModeNone(osi) || WDOSIServiceHelper0610.isDynamicAsProduced(osi)) ? localizedMessage : getQtyValueForExceptionText((IMeasuredValue)MESNamedUDAOrderStepInput.getTotalRemainingQuantity(osi)), (WDOSIServiceHelper0610.isPlannedQtyModeNone(osi) || (WDOSIServiceHelper0610.isDynamicAsProduced(osi) && !AllowedWithWarningException.WarningReason.INCREASE_QTY_LOSE_DYNAMIC_AS_PRODUCED.equals((Object)warningReason))) ? localizedMessage : newRemainingQty });
    }

    private static Object getQtyValueForExceptionText(final IMeasuredValue qty) {
        final String localizedMessage = I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "ReopenValueNA");
        return (qty == null) ? localizedMessage : qty;
    }

    private static IMeasuredValue getNewRecordedQuantity(final OrderStepInput masterOsi, final IMeasuredValue declaredWaste) {
        return ((IWDOrderStepInputService)ServiceFactory.getService((Class)IWDOrderStepInputService.class)).calcPotentialNewTotalActualQuantity(masterOsi, declaredWaste);
    }

    private static String getAdditionalInfoRow(final MatIdentRow0610 row) {
        return StringUtils.join(new Object[] { row.getPosition(), row.getMatIdentifier(), row.getMatShortDescription(), OSIPositionStatus.get(row.getOSI()).getLocalizedName() }, " / ");
    }

    static void fillResetBOMPosException(final MatIdentRow0610 row, final AllowedWithWarningException.WarningReason warningReason, final RtPhaseExecutorWDMatIdent0010 executor) {
        if (row == null) {
            WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "selectBOMPosition_ErrorMsg", new Object[0]);
            return;
        }
        AbstractWeighExceptionView0610.fillParameterizedException("Re-open position / declare waste", "REOPEN_POSITION_DECLARE_WASTE", (AbstractWeighPhaseBaseExecutor0610)executor, getResetExceptionText(row, warningReason));
    }

    private static String getResetExceptionText(final MatIdentRow0610 row, final AllowedWithWarningException.WarningReason warningReason) {
        final OrderStepInput osi = row.getOSI();
        final MeasuredValue totalActualQuantity = MESNamedUDAOrderStepInput.getTotalActualQuantity(osi);
        final Object qtyValueForExceptionText = getQtyValueForExceptionText((IMeasuredValue)totalActualQuantity);
        final Object qtyValueForExceptionText2 = getQtyValueForExceptionText((IMeasuredValue)MESNamedUDAOrderStepInput.getTotalActualQuantity(osi));
        final Object qtyValueForExceptionText3 = getQtyValueForExceptionText(getNewRecordedQuantity(osi, (IMeasuredValue)totalActualQuantity));
        final String localizedMessage = I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "ReopenValueNA");
        String s = I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "ResetExceptionTxtInline", new Object[] { getAdditionalInfoRow(row), qtyValueForExceptionText, qtyValueForExceptionText2, qtyValueForExceptionText3, (WDOSIServiceHelper0610.isPlannedQtyModeNone(osi) || WDOSIServiceHelper0610.isDynamicAsProduced(osi)) ? localizedMessage : getQtyValueForExceptionText((IMeasuredValue)osi.getPlannedQuantity()) });
        if (warningReason != null && StringUtilsEx.isNotEmpty((CharSequence)MatIdentViewHelper0610.WARNING_TO_MESSAGE_MAP.get(warningReason))) {
            s = AbstractWeighExceptionView0610.combineString(s, (String)MatIdentViewHelper0610.WARNING_TO_MESSAGE_MAP.get(warningReason));
        }
        return s;
    }

    static {
        (WARNING_TO_MESSAGE_MAP = new EnumMap<AllowedWithWarningException.WarningReason, String>(AllowedWithWarningException.WarningReason.class)).put(AllowedWithWarningException.WarningReason.INCREASE_QTY_LOSE_DYNAMIC_AS_PRODUCED, I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "abortContinuousIdentificationExceptionTxt"));
        LOGGER = LogFactory.getLog((Class)MatIdentViewHelper0610.class);
    }
}
