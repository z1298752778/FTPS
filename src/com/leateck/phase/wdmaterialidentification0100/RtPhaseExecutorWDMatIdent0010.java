package com.leateck.phase.wdmaterialidentification0100;

import com.datasweep.compatibility.client.*;
import com.datasweep.plantops.common.measuredvalue.IMeasuredValue;
import com.leateck.parameter.materialpositioncontrol0010.MESParamMatPositionCtr0100;
import com.leateck.phase.wdmaterialidentification0100.checks.FixedCheckSuite0610;
import com.leateck.phase.wdmaterialidentification0100.checks.PhaseIdentificationCheckSuite0610;
import com.leateck.phase.wdmaterialidentification0100.messaging.MatIdentMessageController0610;
import com.rockwell.mes.apps.ebr.ifc.phase.IPhaseCompleter;
import com.rockwell.mes.apps.ebr.ifc.phase.IPhaseCompletionSignatureUIExtension;
import com.rockwell.mes.apps.ebr.ifc.phase.IPhaseExecutor;
import com.rockwell.mes.apps.ebr.ifc.phase.uiextensions.IPhaseUIExtension;
import com.rockwell.mes.apps.wd.ifc.ScalesResource;
import com.rockwell.mes.clientfw.pec.ifc.dialog.ErrorDialogWithDetails;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.choicelist.MESChoiceListHelper;
import com.rockwell.mes.commons.base.ifc.exceptions.*;
import com.rockwell.mes.commons.base.ifc.functional.MeasuredValueUtilities;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.nameduda.MESNamedUDAOrderStepInput;
import com.rockwell.mes.commons.base.ifc.nameduda.MESNamedUDAPart;
import com.rockwell.mes.commons.base.ifc.nameduda.MESNamedUDASublot;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.commons.base.ifc.utility.MesClassUtility;
import com.rockwell.mes.commons.base.ifc.utility.ObjectUtilsEx;
import com.rockwell.mes.commons.base.ifc.utility.Pair;
import com.rockwell.mes.commons.base.ifc.utility.StringConstants;
import com.rockwell.mes.commons.base.ifc.utility.SwingUtilitiesEx;
import com.rockwell.mes.commons.deviation.ifc.IESignatureExecutor;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.IMESExceptionRecord;
import com.rockwell.mes.commons.parameter.exceptiondef.MESParamExceptionDef0300;
import com.rockwell.mes.commons.parameter.instruction.MESParamInstruction0300;
import com.rockwell.mes.commons.parameter.measuredvalue.MESParamMeasuredValue0100;
import com.rockwell.mes.parameter.eqmstatuscheckdef.MESParamEqStatCheckDef0300;
import com.rockwell.mes.services.eqm.ifc.*;
import com.rockwell.mes.services.eqm.ifc.exceptions.GxpContextSetVetoException;
import com.rockwell.mes.services.inventory.ifc.ISublotService;
import com.rockwell.mes.services.inventory.ifc.InventoryServiceProvider;
import com.rockwell.mes.services.inventory.ifc.checks.ExecuteCheckParameter;
import com.rockwell.mes.services.inventory.ifc.checks.IExecuteCheckParameter;
import com.rockwell.mes.services.inventory.ifc.checks.IIdentificationCheckSuite;
import com.rockwell.mes.services.order.ifc.EnumOrderStepInputStatus;
import com.rockwell.mes.services.order.ifc.IMESOrderService;
import com.rockwell.mes.services.recipe.ifc.weighing.EnumWeighingMethods;
import com.rockwell.mes.services.recipe.ifc.weighing.EnumWeighingTypes;
import com.rockwell.mes.services.s88.ifc.IS88EquipmentExecutionService;
import com.rockwell.mes.services.s88.ifc.execution.IMESRtPhase;
import com.rockwell.mes.services.s88.ifc.execution.IRuntimeEntity;
import com.rockwell.mes.services.s88.ifc.execution.equipment.EquipmentIdentificationResult;
import com.rockwell.mes.services.s88.ifc.execution.equipment.EquipmentReqValidationErrorResult;
import com.rockwell.mes.services.s88.ifc.execution.equipment.statusgraph.IS88StatusGraphFireTriggerResult;
import com.rockwell.mes.services.s88.ifc.recipe.IMESPhase;
import com.rockwell.mes.services.s88.ifc.recipe.IMESProcessParameterInstance;
import com.rockwell.mes.services.s88.ifc.recipe.PlannedQuantityMode;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88Equipment;
import com.rockwell.mes.services.s88equipment.ifc.EquipmentValidationErrorResult.EqmValidationFailureType;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IMESS88StatusGraphAssignment;
import com.rockwell.mes.services.wd.ifc.AllowedWithWarningException;
import com.rockwell.mes.services.wd.ifc.IWDOrderStepInputService;
import com.rockwell.mes.services.wd.ifc.IWeighingService;
import com.rockwell.mes.services.wd.ifc.InvalidDataException;
import com.rockwell.mes.services.wd.ifc.NotAllowedException;
import com.rockwell.mes.services.wd.ifc.ProrateFactorInvalidValueException;
import com.rockwell.mes.services.wd.ifc.ProrateFactorNoEligibleOSIOSOException;
import com.rockwell.mes.services.wd.ifc.ProrateFactorNotApplicableException;

import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;
import javax.swing.SwingUtilities;

import com.rockwell.mes.shared.product.wd.*;
import com.rockwell.mes.shared0400.product.ui.basics.util.ProductPhaseSwingHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RtPhaseExecutorWDMatIdent0010 extends WDAbstractWeighPhaseExecutor0610<MatIdentModel0610, MatIdentView0610, MatIdentExceptionView0610, MatIdentActionView0610>
{
    private static final String BOM_POSITION_COULD_NOT_BE_ABORTED_CLOSED = "Bom position could not be aborted / closed.";
    private static final String PRORATE_FACTOR_COULD_NOT_BE_APPLIED = "Prorate factor could not be applied";
    private static final Log LOGGER;
    public static final String VERSION = "0610";
    public static final String MSGCAT = "wd_MaterialIdentification0610";
    public static final String KEY_IDENTIFICATION = "EXCEPTION_KEY_IDENTIFICATION";
    public static final String RISK_CLASS_CHOICE = "RiskClass";
    static final String PARAMETER_MANUAL_IDENT = "Identify manually";
    private static final String PARAMETER_CONTAINER_STATUS_CHECK = "Container status check";
    private static final String PARAMETER_PROPERTY_VALUE_CHECK = "Property value check";
    public static final String PARAMETER_PRORATE_FACTOR = "Prorate factor";
    private static final IWeighingService WEIGHING_SERVICE;
    private static final String MSG_PACK_ERROR_EQUIPOMENT_EXPIRY_TRIGGER_FAILURE = "pec_ExceptionMessage";
    private static final String ERROR_EQUIPOMENT_EXPIRY_TRIGGER_FAILURE = "cannotIdentifyExpiryTriggerFailure_ErrorMsg";
    private long currentOSIKey;
    private final Set<Long> osisToUpdateForDynamicAsProduced;
    private long osiInExceptionKey;
    private Sublot identifiedSublot;
    private IMESContainerEquipment identifiedSourceContainer;
    private boolean sourceSublotChangedRemovalWeighing;
    private boolean confirmPhaseForOperationCompletion;
    private IdOnlyWeighingMethodWDMatIdent0610 idOnlyWeighingMethod;
    private CloseTargetHandler closeTargetHandler;
    private boolean detachOperation;
    private static final String CHECK_KEY_TRIGGER_TRANSITION_ROOM_END_USE_FAILED_ON_FALLTHROUGH = "TriggerTransitionRoomEndUseFailedOnFallThrough";
    private static final String CHECK_KEY_TRIGGER_TRANSITION_CONTAINER_EMPTY_FAILED_ON_FALLTHROUGH = "TriggerTransitionContainerEmptyFailedOnFallThrough";
    private static final String CHECK_KEY_TRIGGER_ROOM_END_USE_FAILED_ON_IDONLY = "TriggerTransitionRoomEndUseFailedOnIDOnly";
    private static final String CHECK_KEY_TRIGGER_ROOM_END_USE_FAILED = "TriggerTransitionRoomEndUseFailed";
    private StatusTransitionFailureHelper0610.RecordStatusTransitionFailed recordFallThrougExceptions;
    private StatusTransitionFailureHelper0610.RecordStatusTransitionFailed recordRoomEndUseFailedTrigger;
    private final IdentifiedItemBarcodeProcessor0610 barcodeProcessor;
    private boolean osiWasBoundToWorkcenterBeforeIdentification;
    private Long weighingConditionCompleteDynAsProduced;

    public Sublot getIdentifiedSublot() {
        return this.identifiedSublot;
    }

    public RtPhaseExecutorWDMatIdent0010(final IPhaseCompleter inPhaseCompleter, final IMESRtPhase inRtPhase) {
        super(inPhaseCompleter, inRtPhase);
        this.osisToUpdateForDynamicAsProduced = Collections.synchronizedSet(new HashSet<Long>());
        this.osiInExceptionKey = 0L;
        this.confirmPhaseForOperationCompletion = false;
        this.closeTargetHandler = null;
        this.detachOperation = false;
        this.barcodeProcessor = new IdentifiedItemBarcodeProcessor0610();
        this.weighingConditionCompleteDynAsProduced = null;
    }

    public RtPhaseExecutorWDMatIdent0010(final IMESPhase inPhase, final ActivitySetStep inStep) {
        super(inPhase, inStep);
        this.osisToUpdateForDynamicAsProduced = Collections.synchronizedSet(new HashSet<Long>());
        this.osiInExceptionKey = 0L;
        this.confirmPhaseForOperationCompletion = false;
        this.closeTargetHandler = null;
        this.detachOperation = false;
        this.barcodeProcessor = new IdentifiedItemBarcodeProcessor0610();
        this.weighingConditionCompleteDynAsProduced = null;
    }

    public MESParamEqStatCheckDef0300 getParamContainerStatusCheck() {
        return (MESParamEqStatCheckDef0300)this.getProcessParameterData((Class)MESParamEqStatCheckDef0300.class, "Container status check");
    }

    public MESParamExceptionDef0300 getParamPropertyValueCheck() {
        return (MESParamExceptionDef0300)this.getProcessParameterData((Class)MESParamExceptionDef0300.class, "Property value check");
    }

    protected MatIdentModel0610 createModel() {
        final MatIdentModel0610 model = new MatIdentModel0610(this);
        if (this.getStatus() == Status.ACTIVE && this.isResuming() && !model.isFallThrough()) {
            this.setCurrentOSI(getAlreadyIdentifiedOSI(model));
            final OrderStepInput osiByKey = model.getOSIByKey(this.currentOSIKey);
            this.identifiedSublot = ((osiByKey != null) ? osiByKey.getAttachSublot() : null);
            if (this.identifiedSublot != null) {
                model.setCurrentSourceContainer(this.identifiedSourceContainer = this.getSourceContainerForSublot(this.identifiedSublot));
            }
            model.setIdentifiedOSI(osiByKey);
        }
        return model;
    }

    private static OrderStepInput getAlreadyIdentifiedOSI(final IWDMatIdentModel0610 model) {
        final IMESRoomEquipment currentRoomWithoutException = WDHelper0610.getCurrentRoomWithoutException(false);
        if (currentRoomWithoutException == null) {
            return null;
        }
        final OrderStepInput osi = (OrderStepInput)((IMESRoomEquipmentService)ServiceFactory.getService((Class)IMESRoomEquipmentService.class)).getGxPContext((IMESEquipment)currentRoomWithoutException, GxPContextItemClass.OrderStepInput);
        if (osi != null && osi.getOrderStep().equals((Object)model.getOrderStep()) && osi.getAttachSublot() != null && model.isOSIRelevantForThisRtPhase(osi)) {
            if (RtPhaseExecutorWDMatIdent0010.LOGGER.isInfoEnabled()) {
                RtPhaseExecutorWDMatIdent0010.LOGGER.info((Object)("Resuming with identified sublot " + osi.getAttachSublot()));
            }
            return osi;
        }
        return null;
    }

    protected MatIdentView0610 createView() {
        this.idOnlyWeighingMethod = new IdOnlyWeighingMethodWDMatIdent0610(this);
        return new MatIdentView0610((IDelegate0610<IWDMatIdentModel0610>)this.createWDMatIdentDelegate());
    }

    protected MatIdentExceptionView0610 createExceptionView() {
        return new MatIdentExceptionView0610((IDelegate0610<IWDMatIdentModel0610>)this.createWDMatIdentDelegate());
    }

    private IDelegateWDMatIdent0610 createWDMatIdentDelegate() {
        return new IDelegateWDMatIdent0610() {
            public IWDMatIdentModel0610 getModel() {
                return (IWDMatIdentModel0610)RtPhaseExecutorWDMatIdent0010.this.getModel();
            }

            @Override
            public RtPhaseExecutorWDMatIdent0010 getExecutor() {
                return RtPhaseExecutorWDMatIdent0010.this;
            }
        };
    }

    protected boolean stopFallThrough() {
        return true;
    }

    protected void cleanupAfterFallThrough() {
        PerformReleaseIdentification0610.releaseIdentificationSelectedOSIUnbindRoom(this, this.getRtPhase());
    }

    void addFireEndUseOfRoomFailureOnFallThrough(final IS88StatusGraphFireTriggerResult result) {
        this.setExceptionRequested("TriggerTransitionRoomEndUseFailedOnFallThrough");
        this.createOrAppendFailureResult(result, "TriggerTransitionRoomEndUseFailedOnFallThrough");
    }

    void addFireContainerEmptyTriggerFailureOnFallThrough(final IS88StatusGraphFireTriggerResult result) {
        this.setExceptionRequested("TriggerTransitionContainerEmptyFailedOnFallThrough");
        this.createOrAppendFailureResult(result, "TriggerTransitionContainerEmptyFailedOnFallThrough");
    }

    private void createOrAppendFailureResult(final IS88StatusGraphFireTriggerResult result, final String checkKey) {
        if (this.recordFallThrougExceptions == null) {
            this.recordFallThrougExceptions = new StatusTransitionFailureHelper0610.RecordStatusTransitionFailed((IPhaseExecutor)this, checkKey, result);
        }
        else {
            this.recordFallThrougExceptions.addResult(result);
        }
    }

    protected boolean isScaleActive() {
        return ((IWDMatIdentModel0610)this.getModel()).isScalesEquipmentInUse() && ((IWDMatIdentModel0610)this.getModel()).containerRemainsOnScale();
    }

    private void handleFallThroughExceptionsOnResume() {
        this.reCalculateContainerFailureResultOnResume();
        this.reCalculateRoomFailureResultOnResume();
        this.triggerFallThroughExceptionsLater();
    }

    private void reCalculateContainerFailureResultOnResume() {
        if (this.isContainerTransitionFailureOnFallthroughRequestedNotSigned()) {
            IMESContainerEquipmentService var1 = (IMESContainerEquipmentService)ServiceFactory.getService(IMESContainerEquipmentService.class);
            IMESContainerEquipment var2 = ((IWDMatIdentModel0610)this.getModel()).getCurrentTargetContainer();

            List<Pair<IMESS88StatusGraphAssignment, IS88StatusGraphFireTriggerResult>> var3;
            try {
                var3 = var1.simulateContainerEmptyTriggerOnGroup(var2);
            } catch (FireStatusGraphTriggersFailureException var7) {
                var3 = var7.getAllResults();
            }


            List var4 = (List) var3.stream().map(Pair::getSecond).filter((res) -> {
                return !res.wasTransitionExecuted();
            }).collect(Collectors.toList());
            if (var4.isEmpty()) {
                IS88StatusGraphFireTriggerResult var5 = (IS88StatusGraphFireTriggerResult) ((Pair) var3.get(0)).getSecond();
                this.createOrAppendFailureResult(var5, "TriggerTransitionContainerEmptyFailedOnFallThrough");
            } else {
                Iterator var8 = var4.iterator();

                while(var8.hasNext()) {
                    IS88StatusGraphFireTriggerResult var6 = (IS88StatusGraphFireTriggerResult)var8.next();
                    this.createOrAppendFailureResult(var6, "TriggerTransitionContainerEmptyFailedOnFallThrough");
                }
            }
        }

    }

    private boolean isContainerTransitionFailureOnFallthroughRequestedNotSigned() {
        return this.isExceptionRequested("TriggerTransitionContainerEmptyFailedOnFallThrough") && !this.isExceptionSigned("TriggerTransitionContainerEmptyFailedOnFallThrough");
    }

    private void reCalculateRoomFailureResultOnResume() {
        if (this.isExceptionRequested("TriggerTransitionRoomEndUseFailedOnFallThrough") && !this.isExceptionSigned("TriggerTransitionRoomEndUseFailedOnFallThrough")) {
            final IS88StatusGraphFireTriggerResult simulateRoomEndUseTrigger = RoomTriggerHelper0610.simulateRoomEndUseTrigger();
            if (simulateRoomEndUseTrigger == null) {
                RtPhaseExecutorWDMatIdent0010.LOGGER.error((Object)"On resume room END_USE Trigger does not fail although it has been failed before closing PEC.");
                this.removeIsExceptionRequested("TriggerTransitionRoomEndUseFailedOnFallThrough");
            }
            else {
                this.createOrAppendFailureResult(simulateRoomEndUseTrigger, "TriggerTransitionRoomEndUseFailedOnFallThrough");
            }
        }
    }

    void triggerFallThroughExceptionsLater() {
        if (this.recordFallThrougExceptions == null) {
            return;
        }
        final class RecordSystemTriggeredExc implements Runnable
        {
            @Override
            public void run() {
                RtPhaseExecutorWDMatIdent0010.this.recordFallThrougExceptions.recordException();
            }
        }
        SwingUtilities.invokeLater(new RecordSystemTriggeredExc());
    }

    protected void start() {
        if (WeighingOperationType0610.isDispense((IPhaseExecutor)this) && !((IWDMatIdentModel0610)this.getModel()).containerRemainsOnScale() && WDHelper0610.getCurrentRoomWithoutException(true) == null) {
            this.detachFromCurrentRtOperation();
            return;
        }
        if (this.isResuming()) {
            this.handleFallThroughExceptionsOnResume();
            this.handleCheckTriggerRoomEndUseForIdOnlyHandlingIfNecessaryOnResume();
        }
        if (this.isResuming() && ((IWDMatIdentModel0610)this.getModel()).getReleaseScaleTriggered()) {
            this.performCloseTargetReleaseScaleOnResume();
            return;
        }
        this.prepareMaterialPositionsIfNeeded();
        final OrderStepInput selectedOSI = ((IWDMatIdentModel0610)this.getModel()).getSelectedOSI();
        if (!((IWDMatIdentModel0610)this.getModel()).hasSublotBeenIdentified()) {
            this.freeScalesResourceAndDetachMaterialForAsCampaignOnStart(selectedOSI);
        }
        if (!((IWDMatIdentModel0610)this.getModel()).getKeepTarget() && ((IWDMatIdentModel0610)this.getModel()).hasTargetContainer() && !this.isContainerTransitionFailureOnFallthroughRequestedNotSigned()) {
            ((IWDMatIdentModel0610)this.getModel()).resetCurrentTargetContainer();
        }
        if (((IWDMatIdentModel0610)this.getModel()).getSplitOSI() != null) {
            this.resetGxPContextAndUnbindOsisOnStart();
        }
        if (!((IWDMatIdentModel0610)this.getModel()).isDispenseWeighing() && !((IWDMatIdentModel0610)this.getModel()).getKeepTarget() && !((IWDMatIdentModel0610)this.getModel()).getKeepSource()) {
            this.unbindOSIsFromWorkcenter(Collections.singletonList(EnumOrderStepInputStatus.OSI_STATUS_SELECTED));
        }
        ScalesResource.getInstance().preLoadScales();
        ((IMESOrderService)ServiceFactory.getService((Class)IMESOrderService.class)).enableAndFlushCache(true);
        this.startReceivingMessagesIfApplicable();
        WDOSIServiceHelper0610.calculateAndSetPlannedQuantityAndFieldsForDynamicAsProduced(this.getOrderStep());
    }

    private void freeScalesResourceAndDetachMaterialForAsCampaignOnStart(final OrderStepInput selectedOSI) {
        if (selectedOSI != null && !((IWDMatIdentModel0610)this.getModel()).containerRemainsOnScale() && !((IWDMatIdentModel0610)this.getModel()).getKeepTarget()) {
            ScalesResource.getInstance().freeCurrentScale();
            if (((IWDMatIdentModel0610)this.getModel()).isCampaign() && !((IWDMatIdentModel0610)this.getModel()).isLastReleaseCheckFailed()) {
                this.detachIfMaterialIsComplete();
            }
        }
    }

    private void resetGxPContextAndUnbindOsisOnStart() {
        final IMESScaleEquipment selectedScalesEquipment = ((IWDMatIdentModel0610)this.getModel()).getSelectedScalesEquipment();
        if (selectedScalesEquipment != null && !((IWDMatIdentModel0610)this.getModel()).containerRemainsOnScale()) {
            WDScalesHelper0610.resetGxPContextAndReleaseScaleIfMatchingGxPContext(((IWDMatIdentModel0610)this.getModel()).getSplitOSI(), selectedScalesEquipment, (IRuntimeEntity)this.rtPhase);
        }
        if (!((IWDMatIdentModel0610)this.getModel()).roomRemainsInUse()) {
            WDHelper0610.fireEndUseIfPossibleAndReleaseCurrentRoomIfBound(this.rtPhase);
        }
    }

    private void startReceivingMessagesIfApplicable() {
        if (((IWDMatIdentModel0610)this.getModel()).isInlineWeighing() && WDOSIServiceHelper0610.hasDynamicAsProducedPosition(((IWDMatIdentModel0610)this.getModel()).getOrderStep())) {
            MatIdentMessageController0610 var1 = new MatIdentMessageController0610(this);
            var1.addMessageHandlerForProduceTargetSublots();
            var1.addMessageHandlerForOutputWeighingPositionCompleted();
            this.startReceivingMessages();
        }

    }

    private void handleCheckTriggerRoomEndUseForIdOnlyHandlingIfNecessaryOnResume() {
        final class RecordTriggerRoomEndUseFailureExc implements Runnable
        {
            @Override
            public void run() {
                RtPhaseExecutorWDMatIdent0010.this.checkTriggerRoomEndUseForIdOnlyHandlingIfNecessary();
            }
        }
        SwingUtilities.invokeLater(new RecordTriggerRoomEndUseFailureExc());
    }

    private void performCloseTargetReleaseScaleOnResume() {
        final class PerformCloseTargetReleaseScale implements Runnable
        {
            @Override
            public void run() {
                RtPhaseExecutorWDMatIdent0010.this.initializeFields();
                RtPhaseExecutorWDMatIdent0010.this.initCloseTarget();
                RtPhaseExecutorWDMatIdent0010.this.phaseConfirmed();
            }
        }
        SwingUtilities.invokeLater(new PerformCloseTargetReleaseScale());
    }

    private void prepareMaterialPositionsIfNeeded() {
        WDOSIServiceHelper0610.calculateAndSetPlannedQuantityAsProduced(((IWDMatIdentModel0610)this.getModel()).getOrderStep());
        this.triggerPlannedQuantityCalculationIfApplicable(((IWDMatIdentModel0610)this.getModel()).getOrderStep());
        this.applyProrateFactorAutomatically();
    }

    private void applyProrateFactorAutomatically() {
        IMeasuredValue var1 = this.getProrateFactor();
        boolean var2 = var1 == null && ((IWDMatIdentModel0610)this.getModel()).isParameterFixed("Prorate factor");
        if (!this.isResuming() && this.isFirstInstanceOfRtPhaseAtAll() && !var2) {
            try {
                WEIGHING_SERVICE.applyInitialProrateFactorToOrderStepInputs(var1, this.getOSIsToProrate());
            } catch (ProrateFactorInvalidValueException var6) {
                LOGGER.error("Prorate factor value invalid", var6);

                try {
                    WEIGHING_SERVICE.applyZeroProrateFactorToOrderStepInputs(this.getOSIsToProrate());
                    WDHelper0610.showErrorDialog(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "OverrideProrateFactorInvalidValue", new Object[]{var1}));
                } catch (ProrateFactorNoEligibleOSIOSOException | ProrateFactorNotApplicableException var5) {
                    LOGGER.error("Prorate factor could not be applied", var5);
                    WDHelper0610.showErrorDialog(var5.getLocalizedMessage());
                }
            } catch (ProrateFactorNoEligibleOSIOSOException var7) {
                LOGGER.error("No eligible OSI's / OSO's to apply the prorate factor", var7);
                WDHelper0610.showErrorDialog(var7.getLocalizedMessage());
            } catch (ProrateFactorNotApplicableException var8) {
                LOGGER.error("Prorate factor could not be applied", var8);
                WDHelper0610.showErrorDialog(var8.getLocalizedMessage());
            }

        }
    }

    private List<OrderStepInput> getOSIsToProrate() {
        return ((IWDMatIdentModel0610)this.getModel()).getMasterOSIsRelevantForThisRtPhase(false);
    }

    public IMeasuredValue getProrateFactor() {
        final IMESProcessParameterInstance parameter = this.getParameter("Prorate factor");
        if (parameter == null) {
            return null;
        }
        return (IMeasuredValue)((MESParamMeasuredValue0100)parameter.getAssociatedProcessParameterData((Class)MESParamMeasuredValue0100.class)).getValue();
    }

    private void detachIfMaterialIsComplete() {
        if (((IWDMatIdentModel0610)this.getModel()).isMaterialComplete()) {
            SwingUtilities.invokeLater(this::detachFromCurrentRtOperation);
        }
    }

    protected boolean performPhaseCompletionCheck() {
        if (this.isExceptionSigned("RECORD_WAREHOUSE_ERROR_CHECK_KEY")) {
            return true;
        }
        if (((IWDMatIdentModel0610)this.getModel()).isBlockedByWarehouseError()) {
            WDHelper0610.showErrorDialog(((IWDMatIdentModel0610)this.getModel()).getMessagePackWarehouseError(), "SignWarehouseError_ErrorMsg", new Object[0]);
            return false;
        }
        if (!this.checkTriggerRoomEndUseForIdOnlyHandlingIfNecessary()) {
            return false;
        }
        if (this.closeTargetHandler != null && !this.isExceptionSigned("CLOSE_TARGET") && !this.checkTriggerRoomEndUse()) {
            return false;
        }
        if (this.sourceSublotChangedRemovalWeighing && !this.checkTriggerRoomEndUse()) {
            return false;
        }
        if (((IWDMatIdentModel0610)this.getModel()).hasSublotBeenIdentified()) {
            final OrderStepInput currentOSI = this.getCurrentOSI();
            ((IWDMatIdentModel0610)this.getModel()).setSelectedOSI(currentOSI);
            if (((IWDMatIdentModel0610)this.getModel()).isIdentificationOnly(currentOSI)) {
                return this.idOnlyWeighingMethod.finishSublotForOnlyIdWeighingMethod(currentOSI);
            }
        }
        if (((IWDMatIdentModel0610)this.getModel()).hasSublotBeenIdentified() || this.closeTargetHandler != null || this.sourceSublotChangedRemovalWeighing || this.isNetRemovalClosedTarget()) {
            return true;
        }
        if (((IWDMatIdentModel0610)this.getModel()).getReleaseScaleTriggered() || ((IWDMatIdentModel0610)this.getModel()).isDoUserTriggeredReleaseScale()) {
            return this.isReleaseScalePossible();
        }
        if (this.canCompleteOperation()) {
            if (WDHelper0610.showQuestionDialogYesNo("wd_MaterialIdentification0610", "completeOperation_Query", new Object[0])) {
                return this.confirmPhaseForOperationCompletion = true;
            }
        }
        else if (!((IWDMatIdentModel0610)this.getModel()).containerRemainsOnScale()) {
            this.detachOperation = ((IWDMatIdentModel0610)this.getModel()).getWeighingOperationTypeWDMatIdent0610().handleDetachOperation((IWDMatIdentModel0610)this.getModel());
        }
        else if (((IWDMatIdentModel0610)this.getModel()).isScalesEquipmentInUse()) {
            WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "cannotStopOrderStep", new Object[0]);
        }
        else {
            WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "cannotStopOrderStepCloseTargetFirst", new Object[0]);
        }
        return false;
    }

    private boolean checkTriggerRoomEndUseForIdOnlyHandlingIfNecessary() {
        if (((IWDMatIdentModel0610)this.getModel()).hasSublotBeenIdentified() && EnumWeighingMethods.IDENTIFICATION_ONLY.equals(MESNamedUDAOrderStepInput.getWeighingMethod(this.getCurrentOSI()))) {
            if (this.recordRoomEndUseFailedTrigger != null && this.recordRoomEndUseFailedTrigger.isExceptionSigned()) {
                return true;
            }
            if (this.recordRoomEndUseFailedTrigger != null && !this.recordRoomEndUseFailedTrigger.isExceptionSigned()) {
                this.recordRoomEndUseFailedTrigger.recordException();
                return false;
            }
            if (!this.checkTriggerRoomEndUseForIdOnly()) {
                return false;
            }
        }
        return true;
    }

    private boolean checkTriggerRoomEndUseForIdOnly() {
        final IS88StatusGraphFireTriggerResult simulateRoomEndUseTrigger = RoomTriggerHelper0610.simulateRoomEndUseTrigger();
        if (simulateRoomEndUseTrigger != null) {
            (this.recordRoomEndUseFailedTrigger = new StatusTransitionFailureHelper0610.RecordStatusTransitionFailed((IPhaseExecutor)this, "TriggerTransitionRoomEndUseFailedOnIDOnly", simulateRoomEndUseTrigger)).recordException();
            return false;
        }
        return true;
    }

    private boolean isReleaseScalePossible() {
        if (!((IWDMatIdentModel0610)this.getModel()).isScalesEquipmentInUse()) {
            return true;
        }
        final IMESScaleEquipment selectedScalesEquipment = ((IWDMatIdentModel0610)this.getModel()).getSelectedScalesEquipment();
        if (selectedScalesEquipment != null) {
            try {
                WDScalesHelper0610.checkBindAndSetGxPContextOnScaleForOSI(((IWDMatIdentModel0610)this.getModel()).getSplitOSI(), selectedScalesEquipment, (IRuntimeEntity)this.rtPhase);
            }
            catch (GxpContextSetVetoException ex) {
                WDHelper0610.showErrorDialog(ex.getLocalizedMessage());
                return false;
            }
            return true;
        }
        return false;
    }

    boolean canCompleteOperation() {
        return ((IWDOrderStepInputService)ServiceFactory.getService(IWDOrderStepInputService.class)).isOSIComplete((this.getModel()).getAllOSIsRelevantForThisRtPhase(false));
    }

    public void cancelPhaseExecution() {
        this.stopReceivingMessages();
    }

    protected void performPhaseCompletion() {
        this.stopReceivingMessages();
        this.handleUserTriggeredReleaseScale();
        if (this.confirmPhaseForOperationCompletion) {
            this.getRtPhaseOutput().setIdentificationResult(IdentificationResult0610.COMPLETED);
            this.getRtPhase().setHideAfterCompletion(!this.getRtPhase().hasExceptionRecords());
            ((IWDMatIdentModel0610)this.getModel()).setSelectedOSI((OrderStepInput)null);
            return;
        }
        if (this.closeTargetHandler != null) {
            this.closeTargetHandler.printLabelAndCleanSplitOSI();
        }
        final OrderStepInput currentOSI = this.getCurrentOSI();
        ((IWDMatIdentModel0610)this.getModel()).setSelectedOSI(currentOSI);
        if (((IWDMatIdentModel0610)this.getModel()).isIdentificationOnly(currentOSI)) {
            this.getRtPhaseOutput().setTargetClosed(true);
            final Sublot targetSublot = ((IWDMatIdentModel0610)this.getModel()).getTargetSublot();
            this.getRtPhaseOutput().setSublotIdentifier((targetSublot == null) ? null : targetSublot.getUniqueName());
        }
        final IdentificationResult0610 identificationResult = this.getIdentificationResult();
        this.getRtPhaseOutput().setIdentificationResult(identificationResult);
        this.getRtPhaseOutput().setMFCPosition((currentOSI != null) ? MESNamedUDAOrderStepInput.getPosition(currentOSI) : null);
        final Sublot sublot = (currentOSI != null) ? currentOSI.getAttachSublot() : null;
        final Sublot sublot2 = (identificationResult == IdentificationResult0610.RELEASE_SCALE) ? this.identifiedSublot : sublot;
        final IMESContainerEquipment usedSourceContainer = (identificationResult == IdentificationResult0610.RELEASE_SCALE) ? this.identifiedSourceContainer : ((IWDMatIdentModel0610)this.getModel()).getCurrentSourceContainer();
        this.fillPhaseData(sublot2, usedSourceContainer, currentOSI, identificationResult);
        if (usedSourceContainer != null) {
            this.getRtPhaseOutput().setContObject(usedSourceContainer.getS88Equipment());
            this.getRtPhaseOutput().setContId(usedSourceContainer.getName());
        }
    }

    private void handleUserTriggeredReleaseScale() {
        if (((IWDMatIdentModel0610)this.getModel()).isDoUserTriggeredReleaseScale()) {
            this.setCurrentOSI(((IWDMatIdentModel0610)this.getModel()).getLastFinishedSplitOsiWithAttachedSublot());
            ((IWDMatIdentModel0610)this.getModel()).setKeepSource(false);
        }
    }

    private IdentificationResult0610 getIdentificationResult() {
        if (((IWDMatIdentModel0610)this.getModel()).isIdentificationOnly(this.getCurrentOSI())) {
            return this.idOnlyWeighingMethod.getIdentificationResult();
        }
        if (this.isNetRemovalClosedTarget() || ((IWDMatIdentModel0610)this.getModel()).getReleaseScaleTriggered() || ((IWDMatIdentModel0610)this.getModel()).isDoUserTriggeredReleaseScale()) {
            return IdentificationResult0610.RELEASE_SCALE;
        }
        return IdentificationResult0610.WEIGH;
    }

    private boolean isNetRemovalClosedTarget() {
        return ((IWDMatIdentModel0610)this.getModel()).isRemovalWeighing() && (this.isExceptionSigned("CLOSE_TARGET") || this.closeTargetHandler != null);
    }

    private void fillPhaseData(final Sublot sublot, final IMESContainerEquipment usedSourceContainer, final OrderStepInput osi, final IdentificationResult0610 phaseResult) {
        final MESRtPhaseDataWDMatIdent0010 MESRtPhaseDataWDMatIdent0010 = (MESRtPhaseDataWDMatIdent0010)((IWDMatIdentModel0610)this.getModel()).getRtPhaseData();
        String fillSublotRelatedPhaseData = null;
        if (sublot != null) {
            fillSublotRelatedPhaseData = this.fillSublotRelatedPhaseData(MESRtPhaseDataWDMatIdent0010, sublot);
        }
        if (usedSourceContainer != null) {
            MESRtPhaseDataWDMatIdent0010.setSrcContainerId(usedSourceContainer.getName());
            MESRtPhaseDataWDMatIdent0010.setSrcContainerObject(usedSourceContainer.getS88Equipment());
            MESRtPhaseDataWDMatIdent0010.setSrcContainerShortDesc(usedSourceContainer.getShortDescription());
        }
        if (osi != null) {
            this.fillOSIRelatedPhaseData(MESRtPhaseDataWDMatIdent0010, osi, phaseResult, fillSublotRelatedPhaseData);
        }
    }

    private String fillSublotRelatedPhaseData(final MESRtPhaseDataWDMatIdent0010 matIdentData, final Sublot sublot) {
        matIdentData.setSublotIdentifier(AbstractWeighView0610.getSublotName(sublot));
        final Batch batch = sublot.getBatch();
        final String batchName = AbstractWeighView0610.getBatchName(batch);
        matIdentData.setBatchIdentifier(batchName);
        final Part part = batch.getPart();
        matIdentData.setMaterialIdentifier(part.getPartNumber());
        matIdentData.setMaterialShortDescr(WDHelper0610.getMaterialShortDescription(part));
        matIdentData.setMaterialType(MESNamedUDAPart.getMaterialType(part).getLocalizedMessage());
        final Long temporary = MESNamedUDASublot.getTemporary(sublot);
        matIdentData.setSublotIdentified(temporary == null || MesClassUtility.LONG_FALSE.equals(temporary));
        return batchName;
    }

    private void fillOSIRelatedPhaseData(final MESRtPhaseDataWDMatIdent0010 matIdentData, final OrderStepInput osi, final IdentificationResult0610 phaseResult, final String batchIdentifier) {
        boolean differentBatchIdentified = false;
        boolean empty = false;
        if (phaseResult != IdentificationResult0610.RELEASE_SCALE) {
            if (!WDOSIServiceHelper0610.isPlannedQtyModeNone(osi) && !WDOSIServiceHelper0610.isDynamicAsProduced(osi)) {
                matIdentData.setPlannedQuantity(osi.getPlannedQuantity());
                matIdentData.setOriginalQuantity(MESNamedUDAOrderStepInput.getOriginalPlannedQuantity(osi));
                matIdentData.setNominalQuantity(MESNamedUDAOrderStepInput.getNominalQuantity(osi));
            }
            matIdentData.setPlannedPotency(MESNamedUDAOrderStepInput.getPlannedPotency(osi));
            matIdentData.setActualPotency(MESNamedUDAOrderStepInput.getActualPotency(osi));
            matIdentData.setWeighingType(MESNamedUDAOrderStepInput.getWeighingType(osi).getLocalizedMessage());
        }
        else if (!((IWDMatIdentModel0610)this.getModel()).isDoUserTriggeredReleaseScale()) {
            empty = StringUtils.isEmpty((CharSequence)batchIdentifier);
            differentBatchIdentified = !StringUtils.isEmpty((CharSequence)batchIdentifier);
        }
        this.fillCommonOSIPhaseData(osi, matIdentData, differentBatchIdentified, empty);
    }

    private void fillCommonOSIPhaseData(final OrderStepInput osi, final MESRtPhaseDataWDMatIdent0010 matIdentData, final boolean differentBatchIdentified, final boolean targetContainerClosed) {
        matIdentData.setSplitPosition(MESNamedUDAOrderStepInput.getSplitNumber(osi));
        matIdentData.setIsSplitPosition(MesClassUtility.LONG_TRUE.equals(MESNamedUDAOrderStepInput.getIsSpitCopy(osi)));
        final boolean identificationOnly = ((IWDMatIdentModel0610)this.getModel()).isIdentificationOnly(osi);
        if (identificationOnly) {
            this.idOnlyWeighingMethod.fillData(matIdentData, osi);
        }
        matIdentData.setIdentifiedOnly(identificationOnly);
        matIdentData.setDiffBatchIdentified(differentBatchIdentified);
        matIdentData.setTargetContainerClosed(targetContainerClosed);
    }

    protected boolean completePhase() {
        final boolean completePhase = super.completePhase();
        if (!completePhase) {
            if (this.detachOperation) {
                this.unbindOSIsFromWorkcenter((Collection<IMESChoiceElement>)Collections.unmodifiableList((List<IMESChoiceElement>)Arrays.asList(EnumOrderStepInputStatus.OSI_STATUS_SELECTED, EnumOrderStepInputStatus.OSI_STATUS_CREATED)));
                this.detachFromCurrentRtOperation();
            }
            else {
                ((MatIdentView0610)this.getView()).recreateActiveUIIfBlocked();
            }
        }
        return completePhase;
    }

    private void unbindOSIsFromWorkcenter(final Collection<IMESChoiceElement> allowedOSIStates) {
        Vector var2 = ((IWDMatIdentModel0610)this.getModel()).getOrderStep().getOrderStepInputItems();
        Iterator var3 = var2.iterator();

        while(var3.hasNext()) {
            OrderStepInput var4 = (OrderStepInput)var3.next();
            unbindBoundedOSIFromWorkCenter(var4, allowedOSIStates);
        }

    }

    private static void unbindBoundedOSIFromWorkCenter(final OrderStepInput osi, final Collection<IMESChoiceElement> allowedOSIStates) {
        final WorkCenter currentWorkCenter = PCContext.getFunctions().getCurrentWorkCenter();
        final WorkCenter workCenter = MESNamedUDAOrderStepInput.getWorkCenter(osi);
        if (workCenter == null || workCenter.getKey() != currentWorkCenter.getKey()) {
            return;
        }
        if (!allowedOSIStates.contains(MESNamedUDAOrderStepInput.getStatus(osi))) {
            return;
        }
        if (!WDOSIServiceHelper0610.unbindOSIFromWorkcenter(osi)) {
            throw new MESRuntimeException("Could not unbind OSI from current work center (" + MESNamedUDAOrderStepInput.getPosition(osi) + "." + MESNamedUDAOrderStepInput.getSplitNumber(osi) + ")");
        }
    }

    private boolean isSublotAlreadyIdentified() {
        if (this.hasIdentifiedSublot()) {
            WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "sublotAlreadyIdentified_ErrorMsg", new Object[0]);
            return true;
        }
        return false;
    }

    private boolean hasIdentifiedSublot() {
        return ((IWDMatIdentModel0610)this.getModel()).getIdentifiedSublot() != null;
    }

    IdentifiedItemBarcodeProcessor0610 getBarcodeProcessor() {
        return this.barcodeProcessor;
    }

    private boolean handleKeepTarget() {
        if (((IWDMatIdentModel0610)this.getModel()).isRemovalWeighing()) {
            return this.userConfirmedUsingThisBatch();
        }
        if (this.userConfirmedUsingThisBatch()) {
            ((IWDMatIdentModel0610)this.getModel()).setReleaseScaleTriggered();
            return true;
        }
        return false;
    }

    private boolean userConfirmedUsingThisBatch() {
        return this.closeTargetHandler != null;
    }

    private void initCloseTarget() {
        this.closeTargetHandler = new CloseTargetHandler(((IWDMatIdentModel0610)this.getModel()).getLastFinishedSplitOsiWithAttachedSublot());
    }

    private boolean handleCleaningCheckResult(final IIdentificationCheckSuite checkSuite) {
        String s = null;
        if (!checkSuite.getErrorList().isEmpty()) {
            s = (String)checkSuite.getErrorList().toArray()[0];
        }
        else if (!checkSuite.getWarningList().isEmpty()) {
            s = (String)checkSuite.getWarningList().toArray()[0];
        }
        if (s != null) {
            WDHelper0610.showErrorDialog(s);
            return false;
        }
        return true;
    }

    private PhaseIdentificationCheckSuite0610 performIdentificationChecks() {
        final OrderStepInput currentOSI = this.getCurrentOSI();
        ExecuteCheckParameter executeCheckParameter;
        if (((ISublotService)ServiceFactory.getService((Class)ISublotService.class)).isSublotTemporary(this.identifiedSublot)) {
            executeCheckParameter = new ExecuteCheckParameter(this.identifiedSublot.getBatch(), currentOSI, ((IWDMatIdentModel0610)this.getModel()).getIdOnly(currentOSI));
        }
        else {
            executeCheckParameter = new ExecuteCheckParameter(this.identifiedSublot, currentOSI, ((IWDMatIdentModel0610)this.getModel()).getIdOnly(currentOSI));
        }
        this.triggerPlannedQuantityCalculationIfApplicable(currentOSI);
        final PhaseIdentificationCheckSuite0610 phaseIdentificationCheckSuite0610 = new PhaseIdentificationCheckSuite0610(this.getParameters(), ((IWDMatIdentModel0610)this.getModel()).getMasterOSIsRelevantForThisRtPhase(false));
        phaseIdentificationCheckSuite0610.createCheckSuite().executeCheck((IExecuteCheckParameter)executeCheckParameter);
        return phaseIdentificationCheckSuite0610;
    }

    public void triggerPlannedQuantityCalculationIfApplicable(final OrderStepInput osi) {
        if (((IWDMatIdentModel0610)this.getModel()).isInlineWeighing()) {
            if (this.isAllowedToTriggerPlannedQuantityCalculation()) {
                IMESChoiceElement var2 = MESNamedUDAOrderStepInput.getStatus(WDOSIServiceHelper0610.getLastOSIOfBomItem(osi));
                if (ObjectUtilsEx.areEqual(EnumOrderStepInputStatus.OSI_STATUS_CREATED, var2) || ObjectUtilsEx.areEqual(EnumOrderStepInputStatus.OSI_STATUS_SELECTED, var2)) {
                    try {
                        ((IWDOrderStepInputService)ServiceFactory.getService(IWDOrderStepInputService.class)).calculateAndSetPlannedQuantityAndFieldsForDynamicAsProduced(osi);
                    } catch (MESIncompatibleUoMException | InvalidDataException var4) {
                        throw new MESRuntimeException(var4);
                    }
                }
            } else {
                this.osisToUpdateForDynamicAsProduced.add(osi.getKey());
            }

        }
    }

    private void triggerPlannedQuantityCalculationIfApplicable(final OrderStep orderStep) {
        if (((IWDMatIdentModel0610)this.getModel()).isInlineWeighing()) {
            try {
                ((IWDOrderStepInputService)ServiceFactory.getService(IWDOrderStepInputService.class)).calculateAndSetPlannedQuantityAndFieldsForDynamicAsProduced(orderStep);
            } catch (MESIncompatibleUoMException | InvalidDataException var3) {
                throw new MESRuntimeException(var3);
            }
        }
    }

    private boolean isAllowedToTriggerPlannedQuantityCalculation() {
        return this.getLastCheckKey() == null;
    }

    private void processPendingCalculationForDynamicAsProduced() {
        SwingUtilitiesEx.invokeInEventDispatchThread(this::triggerPendingCalculationForDynamicAsProduced);
    }

    private void triggerPendingCalculationForDynamicAsProduced() {
        boolean var1 = false;
        synchronized(this.osisToUpdateForDynamicAsProduced) {
            Iterator var3 = this.osisToUpdateForDynamicAsProduced.iterator();

            while(true) {
                if (!var3.hasNext()) {
                    break;
                }

                OrderStepInput var4 = ((IWDMatIdentModel0610)this.getModel()).getOSIByKey((Long)var3.next());
                if (var4 != null && (ObjectUtilsEx.areEqual(EnumOrderStepInputStatus.OSI_STATUS_CREATED, MESNamedUDAOrderStepInput.getStatus(var4)) || ObjectUtilsEx.areEqual(EnumOrderStepInputStatus.OSI_STATUS_SELECTED, MESNamedUDAOrderStepInput.getStatus(var4)))) {
                    try {
                        var1 = ((IWDOrderStepInputService)ServiceFactory.getService(IWDOrderStepInputService.class)).calculateAndSetPlannedQuantityAndFieldsForDynamicAsProduced(var4);
                    } catch (MESIncompatibleUoMException | InvalidDataException var7) {
                        throw new MESRuntimeException(var7);
                    }
                }

                var3.remove();
            }
        }

        if (var1) {
            ((MatIdentView0610)this.getView()).refreshActiveGrid();
            MatIdentExceptionView0610 var2 = (MatIdentExceptionView0610)this.getUserTriggeredExceptionView();
            if (var2 != null) {
                var2.refreshGrids();
            }
        }

    }

    public void doAfterReturnFromExceptionView() {
        this.processPendingCalculationForDynamicAsProduced();
    }

    private IIdentificationCheckSuite performCleaningChecks() {
        final IMESRoomEquipment currentRoom = WDHelper0610.getCurrentRoom();
        final WorkCenter currentWorkCenter = PCContext.getFunctions().getCurrentWorkCenter();
        final IIdentificationCheckSuite newIdentificationCheckSuite = InventoryServiceProvider.getNewIdentificationCheckSuite();
        newIdentificationCheckSuite.addCheck(InventoryServiceProvider.getNewIdentificationCheck("CheckBindingForOrderStepInput"));
        newIdentificationCheckSuite.executeCheck((IExecuteCheckParameter)new ExecuteCheckParameter(((IWDMatIdentModel0610)this.getModel()).getCurrentWeighingMode(), (IMESChoiceElement)null, currentWorkCenter, currentRoom, this.getCurrentOSI()));
        return newIdentificationCheckSuite;
    }

    public boolean vetoIdentification(final Sublot sublot, final PhaseIdentificationCheckSuite0610 suite) {
        final OrderStepInput currentOSI = this.getCurrentOSI();
        return ((IWDMatIdentModel0610)this.getModel()).getIdOnly(currentOSI) && !PlannedQuantityMode.NONE.equals((Object)((IWDOrderStepInputService)ServiceFactory.getService((Class)IWDOrderStepInputService.class)).getPlannedQuantityMode(currentOSI)) && !this.idOnlyWeighingMethod.continueEvenIfOverweight(currentOSI, sublot, suite);
    }

    void doIdentification(final Sublot sublot) {
        PerformIdentification0610.doIdentification(this, this.getCurrentOSI(), sublot, (IWDMatIdentModel0610)this.getModel());
        if (!SwingUtilities.isEventDispatchThread()) {
            final class RefreshSignaturePanel implements Runnable
            {
                @Override
                public void run() {
                    ((MatIdentView0610)RtPhaseExecutorWDMatIdent0010.this.getView()).configureSignaturePanel();
                }
            }
            SwingUtilities.invokeLater(new RefreshSignaturePanel());
        }
        else {
            ((MatIdentView0610)this.getView()).configureSignaturePanel();
        }
    }

    void undoIdentification() {
        PerformUndoIdentificationIdOnly0610.undoIdentificationUnbindRoom((IWDMatIdentModel0610)this.getModel());
        this.cleanupAfterUndoIdentification();
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> ((MatIdentView0610)this.getView()).recreateActiveUI());
        }
        else {
            ((MatIdentView0610)this.getView()).recreateActiveUI();
        }
    }

    private void cleanupAfterUndoIdentification() {
        this.unbindOSIFromWorkcenterIfNotBoundBefore();
        this.identifiedSublot = null;
        this.identifiedSourceContainer = null;
        this.setCurrentOSI(null);
    }

    private void doAbortBomPos() {
        final MatIdentRow0610 selectedCancelBOMPosRow = ((MatIdentExceptionView0610)this.getUserTriggeredExceptionView()).getSelectedCancelBOMPosRow();
        WDOSIServiceHelper0610.abortOSI(selectedCancelBOMPosRow.getOSI(), (IRuntimeEntity)this.rtPhase);
        this.doOsiCleanup(selectedCancelBOMPosRow);
    }

    private void doCompleteBomPos() {
        final MatIdentRow0610 selectedCompleteBOMPosRow = ((MatIdentExceptionView0610)this.getUserTriggeredExceptionView()).getSelectedCompleteBOMPosRow();
        final OrderStepInput osi = selectedCompleteBOMPosRow.getOSI();
        if (WDOSIServiceHelper0610.isPlannedQtyModeNone(osi)) {
            WDOSIServiceHelper0610.declareFinish(osi, (IRuntimeEntity)this.rtPhase);
        }
        else {
            WDOSIServiceHelper0610.declareFinishAsProduced(osi, (IRuntimeEntity)this.rtPhase, (int)(Object)this.weighingConditionCompleteDynAsProduced);
        }
        this.doOsiCleanup(selectedCompleteBOMPosRow);
    }

    private void doOsiCleanup(final MatIdentRow0610 selectedBOMPosRow) {
        final OrderStepInput splitOSI = selectedBOMPosRow.getSplitOSI();
        final OrderStepInput selectedOSI = ((IWDMatIdentModel0610)this.getModel()).getSelectedOSI();
        if (selectedOSI != null && selectedBOMPosRow.getPosition().equals(MESNamedUDAOrderStepInput.getPosition(selectedOSI))) {
            ((IWDMatIdentModel0610)this.getModel()).setSelectedOSI((OrderStepInput)null);
        }
        if (splitOSI.getKey() == this.currentOSIKey) {
            this.setCurrentOSI(null);
        }
        if (((IWDMatIdentModel0610)this.getModel()).getSplitOSI() != null && selectedBOMPosRow.getPosition().equals(MESNamedUDAOrderStepInput.getPosition(((IWDMatIdentModel0610)this.getModel()).getSplitOSI()))) {
            ((IWDMatIdentModel0610)this.getModel()).setSplitOSI((OrderStepInput)null);
        }
    }

    private void doReopenBomPos() {
        final OrderStepInput osi = ((MatIdentExceptionView0610)this.getUserTriggeredExceptionView()).getSelectedReopenBOMPosRow().getOSI();
        if (((IWDMatIdentModel0610)this.getModel()).isDoResetBOMPosition()) {
            WDOSIServiceHelper0610.resetOSI(osi);
        }
        else {
            WDOSIServiceHelper0610.reopenOSI(osi, ((IWDMatIdentModel0610)this.getModel()).getNewRemainingQty(), ((IWDMatIdentModel0610)this.getModel()).getDeclaredWaste());
        }
    }

    public void exceptionSigned(final String checkKey) {
        if ("MANUAL_IDENTIFICATION".equals(checkKey) || "EXCEPTION_KEY_IDENTIFICATION".equals(checkKey)) {
            this.triggerAutoCompleteAfterReturnFromExceptionView();
        }
        else if ("CLOSE_TARGET".equals(checkKey)) {
            if (((IWDMatIdentModel0610)this.getModel()).getReleaseScaleTriggered() || ((IWDMatIdentModel0610)this.getModel()).isRemovalWeighing()) {
                this.triggerAutoCompleteAfterReturnFromExceptionView();
            }
        }
        else if ("TriggerTransitionRoomEndUseFailedOnIDOnly".equals(checkKey)) {
            this.triggerAutoCompleteAfterReturnFromExceptionView();
        }
        else if ("TriggerTransitionRoomEndUseFailed".equals(checkKey)) {
            this.triggerAutoCompleteAfterReturnFromExceptionView();
        }
        else if ("RECORD_WAREHOUSE_ERROR_CHECK_KEY".equals(checkKey)) {
            ((IWDMatIdentModel0610)this.getModel()).resetLastWarehouseExceptionAndError();
            this.triggerAutoCompleteAfterReturnFromExceptionView();
        }
        else if ("ABORT_BOM_POSITION".equals(checkKey) || "COMPLETE_BOM_POSITION".equals(checkKey) || "REOPEN_POSITION_DECLARE_WASTE".equals(checkKey)) {
            ((MatIdentExceptionView0610)this.getUserTriggeredExceptionView()).refreshGrids();
        }
        else if ("UNDO_IDENTIFICATION".equals(checkKey)) {
            ((MatIdentView0610)this.getView()).refreshActiveGrid();
        }
        else if (checkKey != null && checkKey.startsWith("ActionReprintSublotLabel")) {
            this.reprintTargetSublot();
        }
        this.osiInExceptionKey = 0L;
        super.exceptionSigned(checkKey);
    }

    public void exceptionCanceled(final String checkKey) {
        if ("MANUAL_IDENTIFICATION".equals(checkKey) || "EXCEPTION_KEY_IDENTIFICATION".equals(checkKey)) {
            this.cleanupAfterRoomIdentifedOnFailedPerformIdentification();
        }
        else if (this.osiInExceptionKey > 0L) {
            if (!((IWDMatIdentModel0610)this.getModel()).isDispenseWeighing()) {
                WDOSIServiceHelper0610.unbindOSIFromWorkcenter(((IWDMatIdentModel0610)this.getModel()).getOSIByKey(this.osiInExceptionKey));
            }
            this.osiInExceptionKey = 0L;
        }
        super.exceptionCanceled(checkKey);
    }

    private void reprintTargetSublot() {
        final Sublot printedSublot = ((IWDMatIdentModel0610)this.getModel()).getPrintedSublot();
        if (ActionSublotHelper0610.doesTargetSublotLabelExists(printedSublot)) {
            try {
                ActionSublotHelper0610.reprintTargetSublotLabel(printedSublot);
                return;
            }
            catch (MESPrintException ex) {
                throw new MESRuntimeException((Throwable)ex);
            }
        }
        LabelPrinter0610.printDispensingLabel(printedSublot, this.getSelectedOSIFromRtPhaseData());
    }

    public void exceptionTransactionCallback(final String checkKey, final IMESExceptionRecord exceptionRecord, final IESignatureExecutor sigExecutor) {
        if ("EXCEPTION_KEY_IDENTIFICATION".equals(checkKey) || "MANUAL_IDENTIFICATION".equals(checkKey)) {
            this.doIdentification(this.identifiedSublot);
        }
        else if ("TriggerTransitionRoomEndUseFailedOnFallThrough".equals(checkKey) || "TriggerTransitionContainerEmptyFailedOnFallThrough".equals(checkKey)) {
            if (this.isExceptionRequested("TriggerTransitionRoomEndUseFailedOnFallThrough")) {
                this.setExceptionRecorded("TriggerTransitionRoomEndUseFailedOnFallThrough");
            }
            if (this.isExceptionRequested("TriggerTransitionContainerEmptyFailedOnFallThrough")) {
                this.setExceptionRecorded("TriggerTransitionContainerEmptyFailedOnFallThrough");
            }
            ((IWDMatIdentModel0610)this.getModel()).resetCurrentTargetContainer();
        }
        else if ("ABORT_BOM_POSITION".equals(checkKey)) {
            this.doAbortBomPos();
        }
        else if ("COMPLETE_BOM_POSITION".equals(checkKey)) {
            this.doCompleteBomPos();
        }
        else if ("REOPEN_POSITION_DECLARE_WASTE".equals(checkKey)) {
            this.doReopenBomPos();
        }
        else if ("CLOSE_TARGET".equals(checkKey)) {
            this.doCloseTarget();
        }
        else if ("UNDO_IDENTIFICATION".equals(checkKey)) {
            this.undoIdentification();
        }
        else if (checkKey != null && checkKey.startsWith("ActionReplaceSublot")) {
            this.doReplaceSublot();
        }
        else if ("OVERRIDE_PRORATE_FACTOR".equals(checkKey)) {
            this.doOverrideProrateFactor();
        }
    }

    private void doCloseTarget() {
        this.initializeFields();
        this.initCloseTarget();
        if (((IWDMatIdentModel0610)this.getModel()).isNetWeighing() || ((IWDMatIdentModel0610)this.getModel()).isQuantityEntry()) {
            ((IWDMatIdentModel0610)this.getModel()).setReleaseScaleTriggered();
        }
    }

    private void doReplaceSublot() {
        ActionSublotHelper0610.replaceTargetSublot(((IWDMatIdentModel0610)this.getModel()).getPrintedSublot(), ((IWDMatIdentModel0610)this.getModel()).getOrderStep(), ((IWDMatIdentModel0610)this.getModel()).isCampaign());
    }

    private void doOverrideProrateFactor() {
        try {
            WEIGHING_SERVICE.applyProrateFactorToOrderStepInputs(((IWDMatIdentModel0610)this.getModel()).getProrateFactor(), this.getOSIsToProrate());
        } catch (ProrateFactorInvalidValueException | ProrateFactorNoEligibleOSIOSOException | ProrateFactorNotApplicableException var2) {
            LOGGER.error("Prorate factor could not be applied", var2);
            throw new MESRuntimeException(var2);
        }
    }

    public void propertyChange(final PropertyChangeEvent evt) {
        if (this.requestStatusFailedExceptionOnFallThrough()) {
            return;
        }
        final String propertyName = evt.getPropertyName();
        if (propertyName.equals("MANUAL_IDENTIFICATION")) {
            this.displayManualIdentificationException((MatIdentExceptionView0610.ManualIdentificationModel)evt.getNewValue());
        }
        else if (propertyName.equals("ABORT_BOM_POSITION")) {
            if (!this.checkAndLockOsiIfNecessaryForAbort()) {
                return;
            }
            MatIdentViewHelper0610.fillCancelBOMPosException((evt.getNewValue() == null) ? null : ((MatIdentRow0610)evt.getNewValue()), this);
        }
        else if (propertyName.equals("COMPLETE_BOM_POSITION")) {
            if (!this.checkAndLockOsiIfNecessaryForComplete()) {
                return;
            }
            this.handleAndPrepareExceptionForCompleteBOMPosition((MatIdentRow0610)evt.getNewValue());
        }
        else if (propertyName.equals("REOPEN_POSITION_DECLARE_WASTE")) {
            this.handleReopenPositionDeclareWaste(evt);
        }
        else if (propertyName.equals("CLOSE_TARGET")) {
            MatIdentViewHelper0610.fillPrintCloseTargetException(this, (IWDMatIdentModel0610)this.getModel(), this.handleCheckRoomEndUseTriggerAtUserTriggeredException());
        }
        else if (propertyName.equals("UNDO_IDENTIFICATION")) {
            MatIdentViewHelper0610.fillUndoIdentificationException(this, (IWDMatIdentModel0610)this.getModel(), this.handleCheckRoomEndUseTriggerAtUserTriggeredException());
        }
        else if (propertyName.startsWith("ActionReprintSublotLabel")) {
            ActionSublotHelper0610.fillConfirmReprintLabelAction(propertyName, (AbstractWeighPhaseBaseExecutor0610)this, ((IWDMatIdentModel0610)this.getModel()).getPrintedSublot());
        }
        else if (propertyName.startsWith("ActionReplaceSublot")) {
            this.checkAndFillReplaceSublotAction(propertyName);
        }
        else if (propertyName.equals("OVERRIDE_PRORATE_FACTOR") && evt.getNewValue() instanceof BigDecimal) {
            this.handleOverrideProrateFactor((BigDecimal)evt.getNewValue());
        }
        else if (propertyName.equals("WAREHOUSE_ERROR")) {
            ((IWDMatIdentModel0610)this.getModel()).fillRecordWarehouseErrorException(((IWDMatIdentModel0610)this.getModel()).getMessagePackWarehouseError(), (List)null);
        }
        super.propertyChange(evt);
    }

    private void handleAndPrepareExceptionForCompleteBOMPosition(final MatIdentRow0610 row) {
        this.weighingConditionCompleteDynAsProduced = null;
        final Pair<Long, String> prepareToFinishPositionForDynamicAsProduced = this.idOnlyWeighingMethod.prepareToFinishPositionForDynamicAsProduced(row.getOSI());
        this.weighingConditionCompleteDynAsProduced = (Long)prepareToFinishPositionForDynamicAsProduced.getFirst();
        MatIdentViewHelper0610.fillCompleteBOMPosException(row, (String)prepareToFinishPositionForDynamicAsProduced.getSecond(), this);
    }

    private StatusTransitionFailureHelper0610.StatusTransitionFailureSupport handleCheckRoomEndUseTriggerAtUserTriggeredException() {
        final StatusTransitionFailureHelper0610.StatusTransitionFailureSupport checkRoomEndUseTriggerAtUserTriggeredExc = this.checkRoomEndUseTriggerAtUserTriggeredExc();
        if (checkRoomEndUseTriggerAtUserTriggeredExc != null) {
            WDHelper0610.showErrorDialog(checkRoomEndUseTriggerAtUserTriggeredExc.getDialogMsg());
        }
        return checkRoomEndUseTriggerAtUserTriggeredExc;
    }

    private StatusTransitionFailureHelper0610.StatusTransitionFailureSupport checkRoomEndUseTriggerAtUserTriggeredExc() {
        final IS88StatusGraphFireTriggerResult simulateRoomEndUseTrigger = RoomTriggerHelper0610.simulateRoomEndUseTrigger();
        if (simulateRoomEndUseTrigger != null) {
            return new StatusTransitionFailureHelper0610.StatusTransitionFailureSupport((IPhaseExecutor)this, simulateRoomEndUseTrigger);
        }
        return null;
    }

    private void checkAndFillReplaceSublotAction(final String propertyName) {
        final Sublot printedSublot = ((IWDMatIdentModel0610)this.getModel()).getPrintedSublot();
        if (!ActionSublotHelper0610.checkReplaceTargetSublotAllowed(printedSublot, ((IWDMatIdentModel0610)this.getModel()).getOrderStep())) {
            return;
        }
        ActionSublotHelper0610.fillConfirmReplaceSublotAction(propertyName, (AbstractWeighPhaseBaseExecutor0610)this, printedSublot);
    }

    private void handleReopenPositionDeclareWaste(final PropertyChangeEvent evt) {
        final Object[] array = (Object[])evt.getNewValue();
        final MatIdentRow0610 matIdentRow0610 = (MatIdentRow0610)array[0];
        final IMeasuredValue newRemainingQty = (IMeasuredValue)array[1];
        final IMeasuredValue declaredWaste = (IMeasuredValue)array[2];
        final boolean doResetBOMPosition = ((IWDMatIdentModel0610)this.getModel()).isDoResetBOMPosition();
        final Pair<Boolean, AllowedWithWarningException.WarningReason> checkIsAllowedToReopen = this.checkIsAllowedToReopen(matIdentRow0610, doResetBOMPosition, newRemainingQty, declaredWaste);
        if ((boolean)checkIsAllowedToReopen.getFirst() && this.checkAndLockOsiIfNecessary(matIdentRow0610, BOMPositionType0610.REOPEN_OR_WASTE_ALLOWED)) {
            ((IWDMatIdentModel0610)this.getModel()).setDeclaredWaste(declaredWaste);
            ((IWDMatIdentModel0610)this.getModel()).setNewRemainingQty(newRemainingQty);
            if (doResetBOMPosition) {
                MatIdentViewHelper0610.fillResetBOMPosException(matIdentRow0610, (AllowedWithWarningException.WarningReason)checkIsAllowedToReopen.getSecond(), this);
            }
            else {
                MatIdentViewHelper0610.fillReopenBOMPosException(newRemainingQty, declaredWaste, matIdentRow0610, (AllowedWithWarningException.WarningReason)checkIsAllowedToReopen.getSecond(), this);
            }
        }
    }

    private Pair<Boolean, AllowedWithWarningException.WarningReason> checkIsAllowedToReopen(final MatIdentRow0610 selectedReopenBOMPosRow, final boolean doReset, final IMeasuredValue newRemainingQty, final IMeasuredValue declaredWaste) {
        try {
            IWDOrderStepInputService var5 = (IWDOrderStepInputService)ServiceFactory.getService(IWDOrderStepInputService.class);
            if (doReset) {
                var5.checkIsAllowedToResetPosition(selectedReopenBOMPosRow.getOSI());
            } else {
                var5.checkIsAllowedToReopenPosition(selectedReopenBOMPosRow.getOSI(), newRemainingQty, declaredWaste);
            }

            return new Pair(Boolean.TRUE, (Object)null);
        } catch (AllowedWithWarningException var7) {
            AllowedWithWarningException.WarningReason var6 = var7.getWarningReason();
            return new Pair(Boolean.TRUE, var6);
        } catch (MESIncompatibleUoMException | NotAllowedException var8) {
            LOGGER.error("Reopen of BOM position not allowed", var8);
            WDHelper0610.showErrorDialog(var8.getLocalizedMessage());
            return new Pair(Boolean.FALSE, (Object)null);
        }
    }

    private void handleOverrideProrateFactor(final BigDecimal newProrateFactorBd) {
        MeasuredValue var2 = MeasuredValueUtilities.createScalar(newProrateFactorBd);

        try {
            Collection var3 = WEIGHING_SERVICE.checkIsProrateFactorApplicableToOrderStepInputs(var2, this.getOSIsToProrate());
            (this.getModel()).setProrateFactor(var2);
            MatIdentViewHelper0610.fillOverrideProrateFactorException(this, var2, var3);
        } catch (ProrateFactorInvalidValueException | ProrateFactorNoEligibleOSIOSOException | ProrateFactorNotApplicableException var4) {
            WDHelper0610.showErrorDialog(var4.getLocalizedMessage());
        }
    }

    private boolean checkAndLockOsiIfNecessaryForAbort() {
        return this.checkAndLockOsiIfNecessary(((MatIdentExceptionView0610)this.getUserTriggeredExceptionView()).getSelectedCancelBOMPosRow(), BOMPositionType0610.ABORTABLE);
    }

    private boolean checkAndLockOsiIfNecessaryForComplete() {
        return this.checkAndLockOsiIfNecessary(((MatIdentExceptionView0610)this.getUserTriggeredExceptionView()).getSelectedCompleteBOMPosRow(), BOMPositionType0610.COMPLETABLE);
    }

    private boolean checkAndLockOsiIfNecessary(final MatIdentRow0610 selectedBOMPosRow, final BOMPositionType0610 bomPositionType) {
        final OrderStepInput splitOSI = selectedBOMPosRow.getSplitOSI();
        if (this.isOsiInProcess(selectedBOMPosRow.getOSI())) {
            if (!this.checkKeepTarget(bomPositionType) || !this.checkScale(bomPositionType)) {
                return false;
            }
            final WorkCenter workCenter = MESNamedUDAOrderStepInput.getWorkCenter(splitOSI);
            if (workCenter != null) {
                return this.checkWorkcenter(bomPositionType, workCenter);
            }
        }
        if (!WDOSIServiceHelper0610.bindOSItoWorkcenter(splitOSI)) {
            WDHelper0610.showErrorDialog(WDOSIServiceHelper0610.getErrorMsgOSIBoundByAnotherWorkCenter(splitOSI));
            return false;
        }
        this.osiInExceptionKey = splitOSI.getKey();
        return true;
    }

    private boolean checkWorkcenter(final BOMPositionType0610 bomPositionType, final WorkCenter workCenterOSI) {
        if (workCenterOSI.getKey() != PCContext.getFunctions().getCurrentWorkCenter().getKey()) {
            if (BOMPositionType0610.ABORTABLE.equals(bomPositionType)) {
                WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "osiLockedAtAbort_ErrorMsg", new Object[0]);
            }
            else if (BOMPositionType0610.COMPLETABLE.equals(bomPositionType)) {
                WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "osiLockedAtComplete_ErrorMsg", new Object[0]);
            }
            else {
                if (!BOMPositionType0610.REOPEN_OR_WASTE_ALLOWED.equals(bomPositionType)) {
                    throw new MESRuntimeException("Bom position could not be aborted / closed.");
                }
                WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "osiLockedAtReopen_ErrorMsg", new Object[0]);
            }
            return false;
        }
        return true;
    }

    private boolean checkScale(final BOMPositionType0610 bomPositionType) {
        if (((IWDMatIdentModel0610)this.getModel()).containerRemainsOnScale()) {
            if (BOMPositionType0610.ABORTABLE.equals(bomPositionType)) {
                WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "releaseScaleFirst_ErrorMsg", new Object[0]);
            }
            else if (BOMPositionType0610.COMPLETABLE.equals(bomPositionType)) {
                WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "releaseScaleFirstComplete_ErrorMsg", new Object[0]);
            }
            else {
                if (!BOMPositionType0610.REOPEN_OR_WASTE_ALLOWED.equals(bomPositionType)) {
                    throw new MESRuntimeException("Bom position could not be aborted / closed.");
                }
                WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "releaseScaleFirstReopen_ErrorMsg", new Object[0]);
            }
            return false;
        }
        return true;
    }

    private boolean checkKeepTarget(final BOMPositionType0610 bomPositionType) {
        if (((IWDMatIdentModel0610)this.getModel()).getKeepTarget()) {
            if (BOMPositionType0610.ABORTABLE.equals(bomPositionType)) {
                WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "closeTargetFirst_ErrorMsg", new Object[0]);
            }
            else if (BOMPositionType0610.COMPLETABLE.equals(bomPositionType)) {
                WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "closeTargetFirstComplete_ErrorMsg", new Object[0]);
            }
            else {
                if (!BOMPositionType0610.REOPEN_OR_WASTE_ALLOWED.equals(bomPositionType)) {
                    throw new MESRuntimeException("Bom position could not be aborted / closed.");
                }
                WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "closeTargetFirstReopen_ErrorMsg", new Object[0]);
            }
            return false;
        }
        return true;
    }

    private boolean isOsiInProcess(final OrderStepInput osi) {
        final IMESChoiceElement status = MESNamedUDAOrderStepInput.getStatus(osi);
        return !EnumOrderStepInputStatus.OSI_STATUS_CREATED.equals(status) && !EnumOrderStepInputStatus.OSI_STATUS_SELECTED.equals(status);
    }

    /**
     * 扫描校验开始
     * @param barcode
     */
    protected void handleBarcode(final String barcode) {
        if (this.performIdentification(barcode)) {
            ((MatIdentView0610)this.getView()).firePhaseConfirmed();
        }
    }

    private void displayManualIdentificationException(final MatIdentExceptionView0610.ManualIdentificationModel model) {
        this.performIdentification(model);
    }

    private boolean performIdentification(final Object arg) {

        if (!this.checkIdentificationIsAllowed()) {
            return false;
        }
        if (!this.parseBarcodeAndGetIdentifiedObjects(arg)) {
            return false;
        }
        /**
         * 开启扫描校验子批次
         * LC
         * 谭鑫
         */
        if(this.getMaterialPosiControl().getEnable()){
            //开启校验
            if(!this.CheckMaterialLocationRule()){
                return false;
            }
        }
        this.findCorrespondingOSI();
        if (!this.checkCurrentOSIAndBindToWorkcenter()) {
            return false;
        }
        final IdentificationMode0610 value = IdentificationMode0610.valueOf(arg);
        return this.handleUnderweigh(value) && (((IWDMatIdentModel0610)this.getModel()).roomRemainsInUse() || WDHelper0610.identifyEquipment(WDHelper0610.getCurrentRoom().getS88Equipment(), this.getRtPhase())) && this.doCleaningChecks() && this.identifySourceContainerAndCheckErrors() && this.doIdentificationChecksAndOtherChecksForCombinedException(value);
    }

    /**
     * 校验扫描的子批次存储位置是否与phsae配置的一致
     * LC
     * 谭鑫
     * @return
     */
    private boolean CheckMaterialLocationRule(){
        //获取子批次的位置
        String locationStr = this.identifiedSublot.getCarrier().getLocation();
        if(locationStr == null){
            return false;
        }
        //通过位置Str获取存储位置
        Location location = PCContext.getFunctions().getLocation(locationStr);
        //通过存储位置获取存储区域
        String  parentLocationStr = location.getParentLocation().toString();
        if(parentLocationStr == null){
            return false;
        }

        //获取phase中配置的 存储区域、存储位置
        String storageArea = getMaterialPosiControl().getStorageArea();
        String storageLocation = getMaterialPosiControl().getStorageLocation();
        String msg = I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "IdentifyMaterialLocation_Error");
        if(storageArea == null && storageLocation == null){
            //存储区域、存储位置都为空 不进行校验
            return true;
        }else if(storageArea != null && storageLocation != null){
            //存储区域，存储位置都不为空，判断子批次的存储位置、区域是否与处方中设置的存储位置、区域一致
            if(!storageArea.equals(parentLocationStr) || !storageLocation.equals(locationStr)){
                ProductPhaseSwingHelper.showErrorDlg(msg);
                return false;
            }
        }else if(storageArea == null && storageLocation != null){
            //存储区域为空，存储位置不为空，判断子批次的存储位置是否与处方中设置的存储位置一致
            if(!storageLocation.equals(locationStr)){
                ProductPhaseSwingHelper.showErrorDlg(msg);
                return false;
            }
        }else if(storageArea != null && storageLocation == null){
            //存储区域不为空，存储位置为空，判断子批次的存储区域是否与处方中设置的存储区域一致
            if(!storageArea.equals(parentLocationStr)){
                ProductPhaseSwingHelper.showErrorDlg(msg);
                return false;
            }
        }
        return true;
    }

    /**
     * 物料流入控制 过参
     * @return
     */
    private MESParamMatPositionCtr0100 getMaterialPosiControl(){
        return getProcessParameterData(MESParamMatPositionCtr0100.class,"Material position control");
    }

    private boolean checkIdentificationIsAllowed() {
        if (this.requestStatusFailedExceptionOnFallThrough()) {
            return false;
        }
        if (((IWDMatIdentModel0610)this.getModel()).isBlocked()) {
            return false;
        }
        if (((IWDMatIdentModel0610)this.getModel()).isBlockedByWarehouseError()) {
            WDHelper0610.showErrorDialog(((IWDMatIdentModel0610)this.getModel()).getMessagePackWarehouseError(), "SignWarehouseError_ErrorMsg", new Object[0]);
            return false;
        }
        if (((IWDMatIdentModel0610)this.getModel()).isDoUserTriggeredReleaseScale()) {
            WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "cannotIdentReleaseScale_ErrorMsg", new Object[0]);
            return false;
        }
        return !this.isSublotAlreadyIdentified();
    }

    private boolean parseBarcodeAndGetIdentifiedObjects(final Object arg) {
        this.initializeFields();
        final IIdentifiedItem0610 fetchIdentifiedItem = IdentificationMode0610.valueOf(arg).fetchIdentifiedItem(this, arg);
        if (fetchIdentifiedItem == null) {
            return false;
        }
        this.identifiedSublot = this.getSublot(fetchIdentifiedItem);
        if (this.identifiedSublot == null) {
            return false;
        }
        this.identifiedSourceContainer = this.getSourceContainer(fetchIdentifiedItem);
        RtPhaseExecutorWDMatIdent0010.LOGGER.info((Object)("Identified sublot: " + this.identifiedSublot + "  source container: " + this.identifiedSourceContainer));
        return true;
    }

    private Sublot getSublot(final IIdentifiedItem0610 inIdentifiedItem) {
        if (inIdentifiedItem.isContainer()) {
            final IMESContainerEquipment container = inIdentifiedItem.getContainer();
            if (container.getAssignedSublot() == null) {
                WDHelper0610.showErrorDialog(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "containerNoSublot_ErrorMsg", new Object[] { inIdentifiedItem.getContainer().getName() }));
            }
            return container.getAssignedSublot();
        }
        if (inIdentifiedItem.isSublot()) {
            return inIdentifiedItem.getSublot();
        }
        return null;
    }

    private IMESContainerEquipment getSourceContainer(final IIdentifiedItem0610 inIdentifiedItem) {
        if (inIdentifiedItem.isContainer()) {
            return inIdentifiedItem.getContainer();
        }
        if (inIdentifiedItem.isSublot()) {
            return this.getSourceContainerForSublot(inIdentifiedItem.getSublot());
        }
        return null;
    }

    private IMESContainerEquipment getSourceContainerForSublot(final Sublot sublot) {
        return ((IMESContainerEquipmentService)ServiceFactory.getService((Class)IMESContainerEquipmentService.class)).findContainerForSublot(sublot);
    }

    private void findCorrespondingOSI() {
        this.setCurrentOSI(((IWDMatIdentModel0610)this.getModel()).findOSIForSublotUsingPart(this.identifiedSublot, ((MatIdentView0610)this.getView()).getSelectedOsiInGrid()));
        this.osiWasBoundToWorkcenterBeforeIdentification = WDOSIServiceHelper0610.isOSIBoundToWorkcenter(this.getCurrentOSI());
    }

    private boolean checkCurrentOSIAndBindToWorkcenter() {
        final OrderStepInput currentOSI = this.getCurrentOSI();
        if (currentOSI == null) {
            return false;
        }
        if (!WDOSIServiceHelper0610.isUncompleted(currentOSI)) {
            WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "osiWrongStatus_ErrorMsg", new Object[] { MESNamedUDAOrderStepInput.getStatus(currentOSI).getLocalizedMessage() });
            return false;
        }
        if (!WDOSIServiceHelper0610.bindOSItoWorkcenter(currentOSI)) {
            WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "osiLocked_ErrorMsg", new Object[0]);
            return false;
        }
        return true;
    }

    private boolean identifySourceContainerAndCheckErrors() {
        if (this.identifiedSourceContainer == null) {
            return true;
        }
        if (!this.identifyAndCheckExpiryTriggerFailures(this.identifiedSourceContainer.getS88Equipment())) {
            this.cleanupAfterRoomIdentifedOnFailedPerformIdentification();
            return false;
        }
        ((IWDMatIdentModel0610)this.getModel()).setCurrentSourceContainer(this.identifiedSourceContainer);
        if (this.checkClassRequirements(this.identifiedSourceContainer.getS88Equipment())) {
            return true;
        }
        this.cleanupAfterRoomIdentifedOnFailedPerformIdentification();
        return false;
    }

    private boolean identifyAndCheckExpiryTriggerFailures(final IMESS88Equipment eq) {
        final IS88EquipmentExecutionService is88EquipmentExecutionService = (IS88EquipmentExecutionService)ServiceFactory.getService((Class)IS88EquipmentExecutionService.class);
        try {
            if (!this.checkExpiryTriggerFailures(eq, is88EquipmentExecutionService.identifyEquipmentGroup(eq.getRootEntity(), this.getRtPhase(), false))) {
                ((IMESContainerEquipmentService)ServiceFactory.getService((Class)IMESContainerEquipmentService.class)).releaseGroup(eq, this.getRtPhase());
                return false;
            }
            return true;
        }
        catch (MESTransitionFailedException ex) {
            WDHelper0610.showErrorDialog(ex.getLocalizedMessage());
            return false;
        }
    }

    private boolean checkExpiryTriggerFailures(final IMESS88Equipment eq, final List<Pair<IMESS88Equipment, EquipmentIdentificationResult>> identificationResults) {
        final StringBuilder sb = new StringBuilder();
        final Iterator<Pair<IMESS88Equipment, EquipmentIdentificationResult>> iterator = identificationResults.iterator();
        while (iterator.hasNext()) {
            final EquipmentIdentificationResult equipmentIdentificationResult = (EquipmentIdentificationResult)iterator.next().getSecond();
            if (equipmentIdentificationResult.hasExpiryTriggerFailures()) {
                if (sb.length() > 0) {
                    sb.append(StringConstants.LINE_BREAK);
                    sb.append(StringConstants.LINE_BREAK);
                }
                sb.append(equipmentIdentificationResult.getCombinedErrorMsgWithContextOfExpiryTriggerFailures());
            }
        }
        final String string = sb.toString();
        if (StringUtils.isEmpty((CharSequence)string)) {
            return true;
        }
        new ErrorDialogWithDetails().showDialog(I18nMessageUtility.getLocalizedMessage("pec_ExceptionMessage", "cannotIdentifyExpiryTriggerFailure_ErrorMsg", (Object[])new String[] { eq.getIdentifier() }), string);
        return false;
    }

    private boolean checkClassRequirements(final IMESS88Equipment equipment) {
        IS88EquipmentExecutionService var2 = (IS88EquipmentExecutionService)ServiceFactory.getService(IS88EquipmentExecutionService.class);
        EquipmentReqValidationErrorResult var3 = var2.checkClassRequirements(equipment, this.getRtPhase());
        if (var3 != null) {
            if (var3.getFailureType().equals(EqmValidationFailureType.EQM_CLASS_NOT_MATCH)) {
                WDHelper0610.showErrorDialog(var3.getI18nErrorMessage());
            }

            return false;
        } else {
            return true;
        }
    }

    private boolean requestStatusFailedExceptionOnFallThrough() {
        final boolean containerTransitionFailureOnFallthroughRequestedNotSigned = this.isContainerTransitionFailureOnFallthroughRequestedNotSigned();
        final boolean b = this.isExceptionRequested("TriggerTransitionRoomEndUseFailedOnFallThrough") && !this.isExceptionSigned("TriggerTransitionRoomEndUseFailedOnFallThrough");
        if (containerTransitionFailureOnFallthroughRequestedNotSigned || b) {
            this.recordFallThrougExceptions.recordException();
            return true;
        }
        return false;
    }

    private boolean handleUnderweigh(final IdentificationMode0610 mode) {
        if (((IWDMatIdentModel0610)this.getModel()).getKeepTarget()) {
            if (this.checkChangeOfBatchKeepTarget() || this.checkSourceAndTargetHaveDifferentSublotQualityStatus()) {
                this.unbindOSIFromWorkcenterIfNotBoundBefore();
                if (this.handleKeepTarget()) {
                    mode.triggerAutoComplete(this);
                }
                return false;
            }
        }
        else if (((IWDMatIdentModel0610)this.getModel()).getKeepSource() && !this.handleChangeSourceSublotRemovalWeighing()) {
            this.unbindOSIFromWorkcenterIfNotBoundBefore();
            if (((IWDMatIdentModel0610)this.getModel()).getReleaseScaleTriggered()) {
                mode.triggerAutoComplete(this);
            }
            return false;
        }
        return true;
    }

    private boolean checkChangeOfBatchKeepTarget() {
        final OrderStepInput lastFinishedSplitOsiWithAttachedSublot = ((IWDMatIdentModel0610)this.getModel()).getLastFinishedSplitOsiWithAttachedSublot();
        final Batch batch = lastFinishedSplitOsiWithAttachedSublot.getAttachSublot().getBatch();
        final Batch batch2 = this.identifiedSublot.getBatch();
        return !batch2.equals((Object)batch) && this.askCloseTargetAndPrepare(lastFinishedSplitOsiWithAttachedSublot, this.getMessageIdForCloseTargetDialogDueToDifferentBatch(batch2, batch, lastFinishedSplitOsiWithAttachedSublot), new Object[0]);
    }

    private String getMessageIdForCloseTargetDialogDueToDifferentBatch(final Batch identifiedBatch, final Batch requiredBatch, final OrderStepInput finishedOSI) {
        String s = null;
        if (WeighingOperationType0610.isDispense((IPhaseExecutor)this)) {
            s = (((IWDMatIdentModel0610)this.getModel()).isRemovalWeighing() ? "differentSourceBatchIdentifiedRemoval_ErrorMsg" : "differentSourceBatchIdentified_ErrorMsg");
        }
        else if (WeighingOperationType0610.isInlineWeighing((IPhaseExecutor)this)) {
            if (EnumWeighingTypes.ACTIVE.equals(MESNamedUDAOrderStepInput.getWeighingType(finishedOSI))) {
                s = (((IWDMatIdentModel0610)this.getModel()).isRemovalWeighing() ? "differentSourceBatchIdentifiedInlineRemoval_ErrorMsg" : "differentSourceBatchIdentifiedInline_ErrorMsg");
            }
            else if (!identifiedBatch.getPart().equals((Object)requiredBatch.getPart())) {
                s = (((IWDMatIdentModel0610)this.getModel()).isRemovalWeighing() ? "differentMaterialIdentifiedInlineRemoval_ErrorMsg" : "differentMaterialIdentifiedInline_ErrorMsg");
            }
        }
        return s;
    }

    private boolean checkSourceAndTargetHaveDifferentSublotQualityStatus() {
        if (WeighingOperationType0610.isInlineWeighing((IPhaseExecutor)this)) {
            return false;
        }
        final OrderStepInput lastFinishedSplitOsiWithAttachedSublot = ((IWDMatIdentModel0610)this.getModel()).getLastFinishedSplitOsiWithAttachedSublot();
        final Sublot lastTargetSublot = ((IWDOrderStepInputService)ServiceFactory.getService((Class)IWDOrderStepInputService.class)).getLastTargetSublot(lastFinishedSplitOsiWithAttachedSublot);
        if (lastTargetSublot == null) {
            return false;
        }
        final ISublotService sublotService = (ISublotService)ServiceFactory.getService((Class)ISublotService.class);
        return !sublotService.isSublotTemporary(this.identifiedSublot) && !Objects.equals(sublotService.getSublotQualityStatus(this.identifiedSublot), sublotService.getSublotQualityStatus(lastTargetSublot)) && this.askCloseTargetAndPrepare(lastFinishedSplitOsiWithAttachedSublot, this.getMessageIdForCloseTargetDialogDueToDifferentSublotQualityStatus(), sublotService.getLocalizedSublotQualityStatusName(this.identifiedSublot, ISublotService.SublotQualityStatusDefaultIfNullEnum.LOCALIZED_NONE), sublotService.getLocalizedSublotQualityStatusName(lastTargetSublot, ISublotService.SublotQualityStatusDefaultIfNullEnum.LOCALIZED_NONE));
    }

    private String getMessageIdForCloseTargetDialogDueToDifferentSublotQualityStatus() {
        String s = null;
        if (WeighingOperationType0610.isDispense((IPhaseExecutor)this)) {
            s = (((IWDMatIdentModel0610)this.getModel()).isRemovalWeighing() ? "differentSublotStatusIdentifiedRemoval_ErrorMsg" : "differentSublotStatusIdentified_ErrorMsg");
        }
        return s;
    }

    private boolean askCloseTargetAndPrepare(final OrderStepInput finishedOSI, final String msgId, final Object... msgArgs) {
        if (StringUtils.isEmpty((CharSequence)msgId)) {
            return false;
        }
        if (WDHelper0610.showQuestionDialogYesNo("wd_MaterialIdentification0610", msgId, msgArgs)) {
            this.closeTargetHandler = new CloseTargetHandler(finishedOSI);
        }
        else {
            this.identifiedSublot = null;
        }
        return true;
    }

    private boolean handleChangeSourceSublotRemovalWeighing() {
        return !((IWDMatIdentModel0610)this.getModel()).getKeepSource() || ((IWDMatIdentModel0610)this.getModel()).getSelectedOSI() == null || !((IWDMatIdentModel0610)this.getModel()).isRemovalWeighing(((IWDMatIdentModel0610)this.getModel()).getSelectedOSI()) || this.checkChangeSourceSublotRemovalWeighing();
    }

    private boolean checkChangeSourceSublotRemovalWeighing() {
        final OrderStepInput lastFinishedSplitOsiWithAttachedSublot = ((IWDMatIdentModel0610)this.getModel()).getLastFinishedSplitOsiWithAttachedSublot();
        final Sublot attachSublot = lastFinishedSplitOsiWithAttachedSublot.getAttachSublot();
        final boolean sublotTemporary = WDHelper0610.isSublotTemporary(attachSublot);
        final boolean sublotTemporary2 = WDHelper0610.isSublotTemporary(this.identifiedSublot);
        boolean equals = true;
        if (sublotTemporary2) {
            equals = this.identifiedSublot.getBatch().equals((Object)lastFinishedSplitOsiWithAttachedSublot.getAttachSublot().getBatch());
        }
        if ((!sublotTemporary2 && !this.identifiedSublot.equals((Object)attachSublot)) || (sublotTemporary2 && !equals) || (sublotTemporary2 && equals && !sublotTemporary)) {
            if (WDHelper0610.showQuestionDialogYesNo("wd_MaterialIdentification0610", "differentSourceSublotIdentifiedRemovalNet_ErrorMsg", new Object[0])) {
                this.setCurrentOSI(lastFinishedSplitOsiWithAttachedSublot);
                this.sourceSublotChangedRemovalWeighing = true;
                ((IWDMatIdentModel0610)this.getModel()).setReleaseScaleTriggered();
                ((IWDMatIdentModel0610)this.getModel()).setKeepSource(false);
            }
            else {
                this.identifiedSublot = null;
                this.identifiedSourceContainer = null;
            }
            return false;
        }
        return true;
    }

    private boolean doCleaningChecks() {
        if (!this.handleCleaningCheckResult(this.performCleaningChecks())) {
            this.cleanupAfterRoomIdentifedOnFailedPerformIdentification();
            ((MatIdentView0610)this.getView()).refreshActiveGrid();
            return false;
        }
        return true;
    }

    private void cleanupAfterRoomIdentifedOnFailedPerformIdentification() {
        this.unbindOSIFromWorkcenterIfNotBoundBefore();
        if (!((IWDMatIdentModel0610)this.getModel()).roomRemainsInUse()) {
            WDHelper0610.releaseCurrentRoom(this.getRtPhase());
        }
        if (((IWDMatIdentModel0610)this.getModel()).getCurrentSourceContainer() != null) {
            ((IMESContainerEquipmentService)ServiceFactory.getService((Class)IMESContainerEquipmentService.class)).releaseGroup(((IWDMatIdentModel0610)this.getModel()).getCurrentSourceContainer().getS88Equipment(), this.getRtPhase());
        }
    }

    private boolean doIdentificationChecksAndOtherChecksForCombinedException(final IdentificationMode0610 mode) {
        PhaseIdentificationCheckSuite0610 var2 = this.performIdentificationChecks();
        FixedCheckSuite0610 var3 = new FixedCheckSuite0610(this);
        var3.doCheck();
        IdentificationMode0610.CheckSuiteEvaluationResult var4 = mode.handleCheckResult(this, var2, var3, this.identifiedSublot, this.identifiedSourceContainer);
        if (var4 == IdentificationMode0610.CheckSuiteEvaluationResult.SUCCESS) {
            return true;
        } else {
            if (var4 == IdentificationMode0610.CheckSuiteEvaluationResult.ERROR || var4 == IdentificationMode0610.CheckSuiteEvaluationResult.ABORT) {
                this.cleanupAfterRoomIdentifedOnFailedPerformIdentification();
            }

            return false;
        }
    }

    public boolean shallTriggerContainerEmptyTransitionBeChecked() {
        return ((IWDMatIdentModel0610)this.getModel()).getIdOnly(this.getCurrentOSI()) && this.idOnlyWeighingMethod.shallTriggerContainerEmptyTransitionBeChecked();
    }

    private void unbindOSIFromWorkcenterIfNotBoundBefore() {
        final OrderStepInput currentOSI = this.getCurrentOSI();
        if (!this.osiWasBoundToWorkcenterBeforeIdentification && currentOSI != null) {
            WDOSIServiceHelper0610.unbindOSIFromWorkcenter(currentOSI);
        }
    }

    private void initializeFields() {
        this.identifiedSublot = null;
        this.identifiedSourceContainer = null;
        this.setCurrentOSI(null);
        this.closeTargetHandler = null;
        this.sourceSublotChangedRemovalWeighing = false;
        ((IWDMatIdentModel0610)this.getModel()).setCurrentSourceContainer((IMESContainerEquipment)null);
    }

    protected MatIdentActionView0610 createActionView() {
        return new MatIdentActionView0610(this.createDelegate());
    }

    protected final MESRtPhaseOutputWDMatIdent0010 getRtPhaseOutput() {
        return (MESRtPhaseOutputWDMatIdent0010)this.getRtPhase().getRtPhaseOutput();
    }

    public final OrderStepInput getCurrentOSI() {
        return ((IWDMatIdentModel0610)this.getModel()).getOSIByKey(this.currentOSIKey);
    }

    protected final void setCurrentOSI(final OrderStepInput osi) {
        if (osi == null) {
            this.currentOSIKey = 0L;
        }
        else {
            this.currentOSIKey = osi.getKey();
        }
    }

    protected String getInstructionTextColumn1FromParameter() {
        return ((MESParamInstruction0300)this.getProcessParameterData((Class)MESParamInstruction0300.class, "Instruction")).getColumn1();
    }

    protected String getExceptionTextFromParameter(final String exceptionParameterName) {
        return ((MESParamExceptionDef0300)this.getProcessParameterData((Class)MESParamExceptionDef0300.class, exceptionParameterName)).getExceptionDescr();
    }

    protected IMESExceptionRecord.RiskClass getExceptionRiskFromParameter(final String exceptionParameterName) {
        return IMESExceptionRecord.RiskClass.valueOf(MESChoiceListHelper.getChoiceElement("RiskClass", (long)((MESParamExceptionDef0300)this.getProcessParameterData((Class)MESParamExceptionDef0300.class, exceptionParameterName)).getRiskAssessment()));
    }

    public boolean isPhaseUIExtensionEnabled(final IPhaseUIExtension phaseUIExtension) {
        return !(phaseUIExtension instanceof IPhaseCompletionSignatureUIExtension);
    }

    private boolean checkTriggerRoomEndUse() {
        if (this.recordRoomEndUseFailedTrigger != null && this.recordRoomEndUseFailedTrigger.isExceptionSigned()) {
            return true;
        }
        if (this.recordRoomEndUseFailedTrigger != null && !this.recordRoomEndUseFailedTrigger.isExceptionSigned()) {
            this.recordRoomEndUseFailedTrigger.recordException();
            return false;
        }
        return this.checkAndTriggerRoomEndUseFailedExceptions();
    }

    private boolean checkAndTriggerRoomEndUseFailedExceptions() {
        final IS88StatusGraphFireTriggerResult simulateRoomEndUseTrigger = RoomTriggerHelper0610.simulateRoomEndUseTrigger();
        if (simulateRoomEndUseTrigger != null) {
            (this.recordRoomEndUseFailedTrigger = new StatusTransitionFailureHelper0610.RecordStatusTransitionFailed((IPhaseExecutor)this, "TriggerTransitionRoomEndUseFailed", simulateRoomEndUseTrigger)).recordException();
            return false;
        }
        return true;
    }

    static {
        LOGGER = LogFactory.getLog((Class)RtPhaseExecutorWDMatIdent0010.class);
        WEIGHING_SERVICE = (IWeighingService)ServiceFactory.getService((Class)IWeighingService.class);
    }

    public interface IDelegateWDMatIdent0610 extends IDelegate0610<IWDMatIdentModel0610>
    {
        RtPhaseExecutorWDMatIdent0010 getExecutor();
    }

    private class CloseTargetHandler
    {
        private final OrderStepInput finishedOSI;

        CloseTargetHandler(final OrderStepInput osi) {
            this.finishedOSI = osi;
            this.setCurrentOSI2Proceed();
        }

        void printLabelAndCleanSplitOSI() {
            final Sublot targetSublot = ((IWDMatIdentModel0610)RtPhaseExecutorWDMatIdent0010.this.getModel()).getTargetSublot();
            if (targetSublot != null) {
                LabelPrinter0610.printDispensingLabel(targetSublot, this.finishedOSI);
            }
            ((IWDMatIdentModel0610)RtPhaseExecutorWDMatIdent0010.this.getModel()).setPrintedSublot(targetSublot);
            RtPhaseExecutorWDMatIdent0010.this.getRtPhaseOutput().setSublotIdentifier((targetSublot == null) ? null : targetSublot.getUniqueName());
            final OrderStepInput splitOSI = ((IWDMatIdentModel0610)RtPhaseExecutorWDMatIdent0010.this.getModel()).getSplitOSI();
            MESNamedUDAOrderStepInput.setScaleName(splitOSI, (String)null);
            MESNamedUDAOrderStepInput.setTargetSublot(splitOSI, (Sublot)null);
            MESNamedUDAOrderStepInput.setTargetContainer(splitOSI, (Equipment)null);
            MESNamedUDAOrderStepInput.setIntendedSplitKeepTargetSL(splitOSI, 0L);
            ((IWDOrderStepInputService)ServiceFactory.getService((Class)IWDOrderStepInputService.class)).incrementTotalNumberTargetSublots(splitOSI, true);
            ((IWDMatIdentModel0610)RtPhaseExecutorWDMatIdent0010.this.getModel()).setKeepTarget(false);
            ((IWDMatIdentModel0610)RtPhaseExecutorWDMatIdent0010.this.getModel()).setKeepSource(false);
            if (((IWDMatIdentModel0610)RtPhaseExecutorWDMatIdent0010.this.getModel()).isRemovalWeighing()) {
                OperationContext0610.setNetRemovalChangeTargetAfterChangeSource(RtPhaseExecutorWDMatIdent0010.this, true);
            }
            else if (WeighingOperationType0610.isInlineWeighing((IPhaseExecutor)RtPhaseExecutorWDMatIdent0010.this)) {
                OperationContext0610.setPopulateRtOperationContextIntoNextRtOperationInstance(RtPhaseExecutorWDMatIdent0010.this);
            }
            ((IWDMatIdentModel0610)RtPhaseExecutorWDMatIdent0010.this.getModel()).setTargetSublot((Sublot)null);
            if (((IWDMatIdentModel0610)RtPhaseExecutorWDMatIdent0010.this.getModel()).hasTargetContainer()) {
                ((IMESContainerEquipmentService)ServiceFactory.getService((Class)IMESContainerEquipmentService.class)).releaseGroup(((IWDMatIdentModel0610)RtPhaseExecutorWDMatIdent0010.this.getModel()).getCurrentTargetContainer(), ((IWDMatIdentModel0610)RtPhaseExecutorWDMatIdent0010.this.getModel()).getRtPhase());
            }
            this.releaseRoomAtCloseTarget();
        }

        private void releaseRoomAtCloseTarget() {
            WDHelper0610.fireEndUseIfPossibleAndReleaseCurrentRoomIfBound(RtPhaseExecutorWDMatIdent0010.this.rtPhase);
        }

        private void setCurrentOSI2Proceed() {
            RtPhaseExecutorWDMatIdent0010.this.setCurrentOSI(((IWDMatIdentModel0610)RtPhaseExecutorWDMatIdent0010.this.getModel()).isRemovalWeighing() ? ((IWDMatIdentModel0610)RtPhaseExecutorWDMatIdent0010.this.getModel()).getSplitOSI() : this.finishedOSI);
        }
    }
}
