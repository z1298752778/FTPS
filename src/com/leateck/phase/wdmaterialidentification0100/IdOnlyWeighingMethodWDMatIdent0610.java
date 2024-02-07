package com.leateck.phase.wdmaterialidentification0100;

import com.leateck.phase.wdmaterialidentification0100.checks.PhaseIdentificationCheckSuite0610;
import com.rockwell.mes.services.warehouseintegration.ifc.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import com.rockwell.mes.commons.base.ifc.exceptions.*;
import com.datasweep.plantops.common.measuredvalue.*;
import com.rockwell.mes.services.s88.ifc.recipe.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import com.rockwell.mes.apps.wd.ifc.*;
import com.rockwell.mes.commons.base.ifc.i18n.*;
import com.rockwell.mes.services.wd.ifc.*;
import com.rockwell.mes.commons.base.ifc.nameduda.*;
import com.rockwell.mes.apps.ebr.ifc.phase.*;
import java.util.*;
import com.datasweep.compatibility.client.*;
import com.rockwell.mes.services.eqm.ifc.*;
import com.rockwell.mes.services.s88.ifc.execution.*;
import com.rockwell.mes.shared.product.wd.*;
import org.apache.commons.logging.*;

public class IdOnlyWeighingMethodWDMatIdent0610
{
    private static final Log LOGGER;
    private final IWDMatIdentModel0610 model;
    private final RtPhaseExecutorWDMatIdent0010 executor;
    private WarehouseRuntimeException lastWHException;
    private static final String WAREHOUSE_ERROR_FINISH_POSITION_MSG = "FinishPositionWarehouseError_ErrorMsg";

    public IdOnlyWeighingMethodWDMatIdent0610(final RtPhaseExecutorWDMatIdent0010 matIdentExecutor) {
        this.model = (IWDMatIdentModel0610)matIdentExecutor.getModel();
        this.executor = matIdentExecutor;
    }

    public boolean finishSublotForOnlyIdWeighingMethod(final OrderStepInput currentOSI) {
        final IMeasuredValue quantityOfSublot = getQuantityOfSublot(currentOSI);
        boolean b = true;
        final IWDOrderStepInputService iwdOrderStepInputService = (IWDOrderStepInputService)ServiceFactory.getService((Class)IWDOrderStepInputService.class);
        int calcWeighingCondition;
        if (WDOSIServiceHelper0610.isPlannedQtyModeNone(currentOSI) || iwdOrderStepInputService.isDynamicAsProduced(currentOSI)) {
            calcWeighingCondition = 1;
        }
        else {
            final IMeasuredValue nominalQuantity = this.getModel().getNominalQuantity(currentOSI);
            final IMeasuredValue nominalLowerTolerance = this.getModel().getNominalLowerTolerance(currentOSI);
            final IMeasuredValue nominalUpperTolerance = this.getModel().getNominalUpperTolerance(currentOSI);
            final IUnitOfMeasure unitOfMeasure = quantityOfSublot.getUnitOfMeasure();
            final Part part = currentOSI.getPart();
            calcWeighingCondition = this.calcWeighingCondition((MeasuredValue)quantityOfSublot, WDCalcServiceHelper0610.convertToUOM(part, nominalQuantity, unitOfMeasure), new Tolerances(WDCalcServiceHelper0610.convertToUOM(part, nominalLowerTolerance, unitOfMeasure), WDCalcServiceHelper0610.convertToUOM(part, nominalUpperTolerance, unitOfMeasure)), WDOSIServiceHelper0610.getPlannedQuantityMode(currentOSI));
        }
        switch (calcWeighingCondition) {
            case 1: {
                b = this.performFinishUnderweigh(currentOSI, (MeasuredValue)quantityOfSublot);
                break;
            }
            case 0: {
                b = this.performFinishInTolerance(currentOSI, (MeasuredValue)quantityOfSublot);
                break;
            }
            case 2: {
                break;
            }
            default: {
                throw new MESRuntimeException("Undefined Weighing condition");
            }
        }
        this.getModel().setWeighingCondition(calcWeighingCondition);
        return b;
    }

    public static IMeasuredValue getQuantityOfSublot(final OrderStepInput currentOSI) {
        return (IMeasuredValue)currentOSI.getAttachSublot().getQuantity();
    }

    private IWDMatIdentModel0610 getModel() {
        return this.model;
    }

    private int calcWeighingCondition(final MeasuredValue actualWeight, final IMeasuredValue nominalQuantity, final Tolerances tolerances, final PlannedQuantityMode plannedQuantityMode) {
        return (int)this.calculateWeighingConditionAndLimits(actualWeight, nominalQuantity, tolerances, plannedQuantityMode).getFirst();
    }

    private Pair calculateWeighingConditionAndLimits(final MeasuredValue actualWeight, final IMeasuredValue nominalQuantity, final Tolerances tolerances, final PlannedQuantityMode plannedQuantityMode) {
        final IMeasuredValue[] nominalAndTolerances = ((IWDEquipmentService)ServiceFactory.getService((Class)IWDEquipmentService.class)).getNominalAndTolerances(nominalQuantity, tolerances, (IMESScaleEquipment)null, plannedQuantityMode);
        if (nominalAndTolerances == null) {
            throw new MESRuntimeException("Can't calculate nominal tolerances");
        }
        return new Pair((Object)(long)((IWDScaleService)ServiceFactory.getService((Class)IWDScaleService.class)).checkWeighingCondition(actualWeight, (MeasuredValue)nominalAndTolerances[1], (MeasuredValue)nominalAndTolerances[2]), (Object)nominalAndTolerances);
    }

    public Pair<Long, String> prepareToFinishPositionForDynamicAsProduced(final OrderStepInput osi) {
        if (PlannedQuantityMode.AS_PRODUCED != WDOSIServiceHelper0610.getPlannedQuantityMode(osi)) {
            return (Pair<Long, String>)new Pair((Object)null, (Object)"");
        }
        WDOSIServiceHelper0610.calculateAndSetPlannedQuantityAndFieldsForDynamicAsProduced(osi);
        final OrderStepInput masterOSIForOSI = WDOSIServiceHelper0610.getMasterOSIForOSI(osi);
        final MeasuredValue plannedQuantity = masterOSIForOSI.getPlannedQuantity();
        final MeasuredValue totalActualQuantity = MESNamedUDAOrderStepInput.getTotalActualQuantity(masterOSIForOSI);
        final Pair calculateWeighingConditionAndLimits = this.calculateWeighingConditionAndLimits(totalActualQuantity, (IMeasuredValue)plannedQuantity, new Tolerances((IMeasuredValue)MESNamedUDAOrderStepInput.getUsedLowerToleranceAbsolute(masterOSIForOSI), (IMeasuredValue)MESNamedUDAOrderStepInput.getUsedUpperToleranceAbsolute(masterOSIForOSI)), WDOSIServiceHelper0610.getPlannedQuantityMode(osi));
        return (Pair<Long, String>)new Pair(calculateWeighingConditionAndLimits.getFirst(), (Object)MatIdentViewHelper0610.buildAdditionalExceptionTextForDynAsProduced((int)calculateWeighingConditionAndLimits.getFirst(), (IMeasuredValue[])calculateWeighingConditionAndLimits.getSecond(), plannedQuantity, totalActualQuantity));
    }

    public boolean continueEvenIfOverweight( OrderStepInput currentOSI,  Sublot identifiedSublot,  PhaseIdentificationCheckSuite0610 suite) {
        if (!((IWDOrderStepInputService)ServiceFactory.getService((Class)IWDOrderStepInputService.class)).isDynamicAsProduced(currentOSI) && this.checkOverWeightConditionBeforeIdentification(currentOSI, identifiedSublot)) {
            final boolean showQuestionDialogYesNo = WDHelper0610.showQuestionDialogYesNo(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "idOnlyOverweightQuestionTxt"));
            this.model.setIdOnly(currentOSI, false);
            if (!showQuestionDialogYesNo) {
                ((MatIdentView0610)this.executor.getView()).refreshActiveGrid();
            }
            return showQuestionDialogYesNo;
        }
        return true;
    }

    private boolean checkOverWeightConditionBeforeIdentification(final OrderStepInput currentOSI, final Sublot identifiedSublot) {
        final IMeasuredValue[] calculatedQuantitiesAndTolerance = WDOSIServiceHelper0610.getCalculatedQuantitiesAndTolerance(currentOSI, (IMeasuredValue)((identifiedSublot != null) ? identifiedSublot.getPotency() : null));
        final IMeasuredValue measuredValue = calculatedQuantitiesAndTolerance[0];
        final IMeasuredValue measuredValue2 = calculatedQuantitiesAndTolerance[1];
        final IMeasuredValue measuredValue3 = calculatedQuantitiesAndTolerance[2];
        if (measuredValue != null && identifiedSublot != null) {
            final MeasuredValue quantity = identifiedSublot.getQuantity();
            final IUnitOfMeasure unitOfMeasure = quantity.getUnitOfMeasure();
            final Part part = currentOSI.getPart();
            return 2 == this.calcWeighingCondition(quantity, WDCalcServiceHelper0610.convertToUOM(part, measuredValue, unitOfMeasure), new Tolerances(WDCalcServiceHelper0610.convertToUOM(part, measuredValue2, unitOfMeasure), WDCalcServiceHelper0610.convertToUOM(part, measuredValue3, unitOfMeasure)), WDOSIServiceHelper0610.getPlannedQuantityMode(currentOSI));
        }
        return false;
    }

    private boolean finishCurrentOSI(final OrderStepInput currentOSI, final int weighingCondition, final MeasuredValue weight, final MeasuredValue remainingQty) {
        this.lastWHException = null;
        final Sublot attachSublot = currentOSI.getAttachSublot();
        final WeighingSituation targetSublotChanged = new WeighingSituation().weighingCondition(weighingCondition).remainingQty(remainingQty).keepTarget(false).sourceBatchChanged(((IWDOrderStepInputService)ServiceFactory.getService((Class)IWDOrderStepInputService.class)).getOSIWithGivenSourceBatch(currentOSI, attachSublot.getBatch()) == null).targetSublotChanged(true);
        final ProduceSublotInfoForFinish produceSublotInfoForFinish = (ProduceSublotInfoForFinish)((ProduceSublotInfoForFinish)new ProduceSublotInfoForFinish(weight, (MeasuredValue)null).deleteSourceSublot(true).useDefaultSublotQualityStatusFromMaterial(false)).sublotQualityStatus(MESNamedUDASublot.getQualityStatus(attachSublot));
        Sublot finish = null;
        try {
            finish = WeighingOperationType0610.getWeighingOperationType((IPhaseExecutor)this.executor).finish(currentOSI, targetSublotChanged, produceSublotInfoForFinish, (IRuntimeEntity)this.getModel().getRtPhase());
        }
        catch (WarehouseRuntimeException lastWHException) {
            this.lastWHException = lastWHException;
            this.getModel().setLastWarehouseExceptionAndError(this.lastWHException, "FinishPositionWarehouseError_ErrorMsg");
            final Object result = this.lastWHException.getResult();
            if (result != null) {
                if (((Sublot[])result).getClass().isArray()) {
                    final Sublot[] array = (Sublot[])result;
                    if (array.length == 1) {
                        finish = array[0];
                    }
                }
                else {
                    finish = (Sublot)result;
                }
            }
        }
        this.getModel().setTargetSublot(finish);
        this.releaseRoom();
        this.handleContainer(finish);
        if (finish != null) {
            this.printTargetSublot(currentOSI, finish);
        }
        if (this.getModel().isBlockedByWarehouseError()) {
            WDHelper0610.showErrorDialog(this.getModel().createWarehouseErrorMessage(this.getModel().getMessagePackWarehouseError(), (List)null, true));
            return false;
        }
        return true;
    }

    private void releaseRoom() {
        WDHelper0610.resetGXPContFireEndUseIfPossibleAndReleaseCurrentRoom(this.model.getRtPhase());
    }

    private void handleContainer(final Sublot targetSublot) {
        if (this.getModel().hasSourceContainer()) {
            final IMESContainerEquipment currentSourceContainer = this.getModel().getCurrentSourceContainer();
            final IMESContainerEquipmentService imesContainerEquipmentService = (IMESContainerEquipmentService)ServiceFactory.getService((Class)IMESContainerEquipmentService.class);
            final IMESRtPhase rtPhase = this.getModel().getRtPhase();
            if (WeighingOperationType0610.isDispense((IPhaseExecutor)this.executor)) {
                if (targetSublot != null) {
                    try {
                        imesContainerEquipmentService.assignSublot(currentSourceContainer, targetSublot, rtPhase);
                    }
                    catch (WarehouseRuntimeException lastWHException) {
                        if (this.lastWHException != null) {
                            this.lastWHException = (WarehouseRuntimeException)this.lastWHException.combineWith((MESRuntimeException)lastWHException);
                        }
                        else {
                            this.lastWHException = lastWHException;
                        }
                        this.getModel().setLastWarehouseExceptionAndError(this.lastWHException, "FinishPositionWarehouseError_ErrorMsg");
                    }
                }
            }
            else {
                try {
                    imesContainerEquipmentService.simulateContainerEmptyTriggerOnGroup(currentSourceContainer);
                    imesContainerEquipmentService.fireContainerEmptyTriggerOnGroup(currentSourceContainer, rtPhase);
                }
                catch (FireStatusGraphTriggersFailureException ex) {
                    IdOnlyWeighingMethodWDMatIdent0610.LOGGER.info((Object)ex);
                }
            }
            imesContainerEquipmentService.releaseGroup(currentSourceContainer, rtPhase);
            try {
                currentSourceContainer.save();
            }
            catch (DatasweepException ex2) {
                throw new MESRuntimeException("Saving container " + currentSourceContainer.getS88Equipment().getIdentifier() + "failed", (Throwable)ex2);
            }
        }
    }

    public boolean shallTriggerContainerEmptyTransitionBeChecked() {
        return !WeighingOperationType0610.isDispense((IPhaseExecutor)this.executor);
    }

    private void printTargetSublot(final OrderStepInput currentOSI, final Sublot targetSublot) {
        LabelPrinter0610.printDispensingLabel(targetSublot, currentOSI);
        this.getModel().setPrintedSublot(targetSublot);
    }

    private boolean performFinishInTolerance(final OrderStepInput currentOSI, final MeasuredValue weight) {
        return this.finishCurrentOSI(currentOSI, 0, weight, null);
    }

    private boolean performFinishUnderweigh(final OrderStepInput currentOSI, final MeasuredValue weight) {
        final OrderStepInput split = WDOSIServiceHelper0610.split(currentOSI, weight, false);
        final boolean finishCurrentOSI = this.finishCurrentOSI(currentOSI, 1, weight, split.getPlannedQuantity());
        if (!WDOSIServiceHelper0610.bindOSItoWorkcenter(split)) {
            throw new MESRuntimeException("Bind OrderStepInput to current work center failed.");
        }
        this.getModel().setSplitOSI(split);
        return finishCurrentOSI;
    }

    public IdentificationResult0610 getIdentificationResult() {
        switch (this.getModel().getWeighingCondition()) {
            case 1: {
                return IdentificationResult0610.ONLY_IDENTIFICATION;
            }
            case 0: {
                if (this.getModel().isDispenseWeighing() && this.executor.canCompleteOperation()) {
                    return IdentificationResult0610.COMPLETED;
                }
                return IdentificationResult0610.ONLY_IDENTIFICATION;
            }
            case 2: {
                return IdentificationResult0610.WEIGH;
            }
            default: {
                throw new MESRuntimeException("Only identification with illegal weighing condition! " + this.getModel().getWeighingCondition());
            }
        }
    }

    public void fillData(final MESRtPhaseDataWDMatIdent0010 matIdentData, final OrderStepInput osi) {
        matIdentData.setWeighingMethod(MESNamedUDAOrderStepInput.getWeighingMethod(osi).getLocalizedMessage());
        matIdentData.setActualQuantity(osi.getActualQuantity());
        final Sublot targetSublot = MESNamedUDAOrderStepInput.getTargetSublot(osi);
        matIdentData.setTargetSublotIdent((targetSublot != null) ? targetSublot.getName() : "");
    }

    static {
        LOGGER = LogFactory.getLog((Class)IdOnlyWeighingMethodWDMatIdent0610.class);
    }
}
