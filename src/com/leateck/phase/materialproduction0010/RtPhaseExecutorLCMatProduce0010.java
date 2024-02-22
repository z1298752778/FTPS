package com.leateck.phase.materialproduction0010;

import com.leateck.parameter.materialpositioncontrol0010.MESParamMatPositionCtr0100;
import com.rockwell.mes.services.commons.ifc.packaginglevels.*;
import com.rockwell.mes.commons.base.ifc.i18n.*;
import com.rockwell.mes.shared0400.product.ui.basics.util.*;
import java.awt.*;
import com.rockwell.mes.shared.product.material.*;
import com.rockwell.mes.services.s88.ifc.execution.*;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.*;
import com.rockwell.mes.commons.base.ifc.configuration.*;
import com.rockwell.mes.shared.product.material.util.*;
import com.rockwell.mes.services.commons.ifc.order.*;
import com.rockwell.mes.services.order.ifc.*;
import java.math.*;
import java.text.*;
import org.apache.commons.lang3.*;
import com.rockwell.mes.commons.parameter.exceptiondef.*;
import com.rockwell.mes.commons.parameter.excptenablenodef.*;
import com.rockwell.mes.apps.ebr.ifc.phase.*;
import com.rockwell.mes.services.s88.ifc.recipe.*;
import com.rockwell.mes.services.s88.ifc.*;
import com.rockwell.mes.services.commons.ifc.functional.*;
import com.datasweep.plantops.common.measuredvalue.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import com.rockwell.mes.commons.base.ifc.functional.*;
import com.rockwell.mes.services.order.ifc.exceptions.*;
import com.rockwell.mes.services.warehouseintegration.ifc.*;
import com.rockwell.mes.commons.base.ifc.printing.*;
import com.rockwell.mes.commons.deviation.ifc.*;
import java.beans.*;
import com.rockwell.mes.commons.base.ifc.labeling.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import com.datasweep.compatibility.ui.*;
import com.rockwell.mes.commons.base.ifc.exceptions.*;
import com.rockwell.mes.services.inventory.ifc.*;
import com.rockwell.mes.commons.base.ifc.fsm.*;
import com.rockwell.mes.services.inventory.ifc.exceptions.*;
import com.datasweep.compatibility.client.*;
import com.rockwell.mes.commons.base.ifc.nameduda.*;
import java.util.*;
import java.util.List;

import com.rockwell.mes.commons.shared.phase.mvc.*;

public class RtPhaseExecutorLCMatProduce0010 extends AbstractPhaseExecutor0200<RtPhaseModelLCMatProduce0010, RtPhaseViewLCMatProduce0010, RtPhaseExceptionViewLCMatProduce0010, RtPhaseActionViewLCMatProduce0010> implements IMatProduceEventListener0710
{
    private static final String INVALID_NUM_OF_SUBLOTS_TXT = "InvalidNumOfSublots_Txt";
    private static final String INVALID_QTY_TXT = "InvalidQty_Txt";
    protected MaterialObservable0710 materialObservable;
    private static final String PARAMETER_FOR_SINGLE_SUBLOT_QTY = "Limit quantity to single sublot";
    private static final String PARAMETER_FOR_SINGLE_LOGISTICUNIT_QTY = "Limit quantity to single logistic unit";
    private static final String PARAMETER_FOR_PLANNED_QTY = "Check planned quantity";
    private boolean pqLimitExceededContinueFlag;
    private boolean pqLimitExceededDoneFlag;
    private boolean pqLimitNotReachedDoneFlag;
    private List<IPackagingLevelBean> configPackingLevelsCacheList;
    private IMeasuredValue overallProducedQuantity;
    private Map<SystemTriggeredExceptions, Long> checkRiskAssesmentMap;
    protected boolean uomConversionError;
    private int nrProducedSublots;
    private List<Sublot> lastProducedSublots;
    private int nrRequestedSublots;
    private IMeasuredValue qtyOverweightForException;

    public RtPhaseExecutorLCMatProduce0010(final IPhaseCompleter inPhaseCompleter, final IMESRtPhase inRtPhase) {
        super(inPhaseCompleter, inRtPhase);
        this.pqLimitExceededContinueFlag = false;
        this.pqLimitExceededDoneFlag = false;
        this.pqLimitNotReachedDoneFlag = false;
        this.configPackingLevelsCacheList = null;
        this.overallProducedQuantity = null;
        this.uomConversionError = false;
        this.nrProducedSublots = 0;
        this.lastProducedSublots = null;
        this.nrRequestedSublots = 0;
        this.qtyOverweightForException = null;
    }

    public RtPhaseExecutorLCMatProduce0010(final IMESPhase inPhase, final ActivitySetStep inStep) {
        super(inPhase, inStep);
        this.pqLimitExceededContinueFlag = false;
        this.pqLimitExceededDoneFlag = false;
        this.pqLimitNotReachedDoneFlag = false;
        this.configPackingLevelsCacheList = null;
        this.overallProducedQuantity = null;
        this.uomConversionError = false;
        this.nrProducedSublots = 0;
        this.lastProducedSublots = null;
        this.nrRequestedSublots = 0;
        this.qtyOverweightForException = null;
    }

    protected void start() {
        final Part phaseOutputMaterial = this.model.getPhaseOutputMaterial();
        try {
            if (phaseOutputMaterial != null) {
                phaseOutputMaterial.refresh();
            }
        }
        catch (DatasweepException ex) {
            throw new MESRuntimeException("Could not refresh output material.", ex);
        }
        if (this.isResuming() && this.getModel().isBlockedByWarehouseError()) {
            this.getView().blockPhaseCompletionRelevantInput();
        }
    }

    protected RtPhaseModelLCMatProduce0010 createModel() {
        return new RtPhaseModelLCMatProduce0010(this);
    }

    protected RtPhaseViewLCMatProduce0010 createView(final RtPhaseModelLCMatProduce0010 theModel) {
        return new RtPhaseViewLCMatProduce0010(theModel, this);
    }

    protected RtPhaseExceptionViewLCMatProduce0010 createExceptionView(final RtPhaseModelLCMatProduce0010 theModel) {
        return new RtPhaseExceptionViewLCMatProduce0010(theModel);
    }

    protected RtPhaseActionViewLCMatProduce0010 createActionView(final RtPhaseModelLCMatProduce0010 theModel) {
        return new RtPhaseActionViewLCMatProduce0010(theModel);
    }

    protected void performPhaseCompletion() {
        this.getModel().setPhaseOutputData();
    }

    protected boolean performPhaseCompletionCheck() {
        if (this.isExceptionSigned("RECORD_WAREHOUSE_ERROR_CHECK_KEY")) {
            return true;
        }
        if (this.getModel().isBlockedByWarehouseError()) {
            ProductPhaseSwingHelper.showErrorDlg(I18nMessageUtility.getLocalizedMessage("PhaseProductMaterial0710", "SignWarehouseError_Error"));
            return false;
        }
        boolean produceRequestedSublots = false;
        try (final AutoWaitCursor autoWaitCursor = new AutoWaitCursor(this.getView())) {
            this.getModel().refreshOSAndDependents(true);
            this.getModel().setCurrentOSOClompletedStatus();
            if (!this.getModel().isExceptionSigned() && !this.checkForErrors()) {
                return false;
            }
            produceRequestedSublots = this.produceRequestedSublots();
        }
        if (this.getModel().isBlockedByWarehouseError()) {
            this.getView().blockPhaseCompletionRelevantInput();
        }
        return produceRequestedSublots;
    }

    private boolean checkForErrors() {
        if (!this.getModel().checkAndInitLocation()) {
            return false;
        }
        if (this.getModel().arePackagingLevelsDefined() && this.getView().getDefinedPackingLevels() != null) {
            if (!this.validationForWithPackingLevels()) {
                return false;
            }
        }
        else if (!this.checkInvalidQuantityForNoPackagingLevelsError()) {
            return false;
        }
        return this.checkNumberOfSublotsError() && this.showExceptionDialogForSingleAndMultipleExceptions(this.getAllExceptionChecksAndMessages()) && !this.getModel().isExceptionRequested();
    }

    private boolean produceRequestedSublots() {
        boolean prepareAndProduceSublots = false;
        try {
            prepareAndProduceSublots = this.prepareAndProduceSublots();
            this.getModel().refreshMaterialsList();
            if (this.getModel().isSublotProductionStopped()) {
                this.handleStoppedProduction();
            }
            else if (!this.getModel().getRememberedOSCompletedStatus()) {
                this.handleNotSignedOverweight();
            }
            if (!this.closeOSOIfNoProductionWithDone()) {
                return false;
            }
        }
        finally {
            this.getView().refreshGrid(false);
        }
        return prepareAndProduceSublots;
    }

    private void handleNotSignedOverweight() {
        if ((this.isOverweight() || this.isCurrentProducedQtyExceedsUpperLimit()) && !this.isCurrentOverweightAlreadySigned()) {
            ProductPhaseSwingHelper.showErrorDlg(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "OSOSTROverweight_Error"));
            this.createSystemTriggeredExceptionRecord("OSOSTROverweight_ExceptionMsg");
        }
    }

    private boolean isCurrentProducedQtyExceedsUpperLimit() {
        if (!PlannedQuantityMode.AS_DEFINED.getChoiceElement().equals(this.getModel().getPhaseOutputMaterialParameter().getPlannedQuantityMode())) {
            return false;
        }
        final IMeasuredValue processedQtyMV = this.model.matOutputParameter.getProcessedQtyMV();
        final Limits limits = this.getModel().getLimits();
        try {
            return MeasuredValueUtilities.compare(processedQtyMV, limits.getUpper()) == 1;
        }
        catch (MESIncompatibleUoMException ex) {
            throw new MESRuntimeException(ex.getLocalizedMessage(), ex);
        }
    }

    private void handleStoppedProduction() {
        this.getModel().setPhaseResult(PhaseResult0710.DONE);
        this.getView().selectButtonAsPhaseResult();
        this.getModel().addPhaseDataForHeader();
        final boolean b = this.isOverweight() && !this.isCurrentOverweightAlreadySigned();
        ProductPhaseSwingHelper.showErrorDlg(this.getErrorDialogMsgStoppedProduction(b));
        this.createSystemTriggeredExceptionsStoppedProduction(b);
    }

    private boolean isOverweight() {
        return Long.valueOf(OSOPositionStatus.COMPLETED_OVERWEIGH.longValue()).equals(MESNamedUDAOrderStepOutput.getTotalPositionStatus(this.getModel().getOSO()));
    }

    private boolean isCurrentOverweightAlreadySigned() {
        if (this.qtyOverweightForException == null) {
            return false;
        }
        final MeasuredValue mv = MeasuredValueUtilities.createMV(this.model.matOutputParameter.getProcessedQty(), I18nMessageUtility.getCurrentLocale());
        try {
            return MeasuredValueUtilities.compare(this.qtyOverweightForException, mv) == 0;
        }
        catch (MESIncompatibleUoMException ex) {
            RtPhaseExecutorLCMatProduce0010.LOGGER.debug("Catched Exception : " + ex);
            throw new MESRuntimeException(ex.getLocalizedMessage(), ex);
        }
    }

    private void createSystemTriggeredExceptionsStoppedProduction(final boolean isOverweightWithoutSignedException) {
        this.createSystemTriggeredExceptionRecord((this.nrRequestedSublots == 0) ? "OSOComplete_ExceptionMsg" : "OSOCompleteStopped_ExceptionMsg");
        if (isOverweightWithoutSignedException) {
            this.createSystemTriggeredExceptionRecord("OSOCompleteOverweight_ExceptionMsg");
        }
    }

    private String getErrorDialogMsgStoppedProduction(final boolean isOverweightWithoutSignedException) {
        final StringBuilder errorDialogMsg = new StringBuilder(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "OSOCompleteMeantime_Error"));
        if (this.nrProducedSublots < this.nrRequestedSublots) {
            this.appendNewMsg(errorDialogMsg, (this.nrProducedSublots == 0) ? "OSOCompleteNoSublotsProduced_Error" : "OSOClompleteSomeSublotsProduced_Error");
        }
        if (isOverweightWithoutSignedException) {
            this.appendNewMsg(errorDialogMsg, "OSOCompleteOverweight_Error");
        }
        this.appendNewMsg(errorDialogMsg, "OSOCompleteStoppedProd_Error");
        return errorDialogMsg.toString();
    }

    private void appendNewMsg(final StringBuilder errorDialogMsg, final String msgID) {
        errorDialogMsg.append(StringConstants.LINE_BREAK);
        errorDialogMsg.append(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", msgID));
    }

    private void createSystemTriggeredExceptionRecord(final String msgId) {
        ((IS88ExceptionRecordingService)ServiceFactory.getService((Class)IS88ExceptionRecordingService.class)).recordSystemTriggeredExceptionWithoutSignature(this.rtPhase, this.getRiskClass(), I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", msgId));
    }

    private IMESExceptionRecord.RiskClass getRiskClass() {
        return IMESExceptionRecord.RiskClass.valueOf(MESConfiguration.getMESConfiguration().getLong("Phase/ProduceMaterial/PhaseCompletesMatOutputClosedExcRisk", IMESExceptionRecord.RiskClass.high.longValue(), "The exception-specific risk level of a system-triggered exception added to a Material Produce runtime phase because it is automatically closed due to output material is already closed."));
    }

    private boolean closeOSOIfNoProductionWithDone() {
        if (this.nrRequestedSublots == 0 && this.getModel().isPhaseResultDone() && !this.getModel().isOSOCompleted()) {
            final IMESOrderService mesOrderService = MaterialHelper0710.getMESOrderService();
            final OrderStepOutputProductionConfig setLimits = new OrderStepOutputProductionConfig().setSupportConcurrentProduction(true).setDoCloseOSO(((RtPhaseModelLCMatProduce0010)this.model).isPhaseResultDone()).setLimits(this.getModel().getLimits());
            try {
                mesOrderService.closeOSOPosition(this.getModel().getOSO(), setLimits);
            }
            catch (MESIncompatibleUoMException ex) {
                ProductPhaseSwingHelper.showErrorDlg(ex.getLocalizedMessage());
                return false;
            }
        }
        return true;
    }

    private boolean validationForWithPackingLevels() {
        final MatProducePackingLevelsHelper0710.PackingLevelSublotsQuantitiesResult calculateSublotQtyAndNumberOfSubLots = MatProducePackingLevelsHelper0710.calculateSublotQtyAndNumberOfSubLots(this.getModel().getSizeOfSLLevelForPackagingLevel(), MeasuredValueUtilities.createMV(this.getView().totalValueLabel().getText(), I18nMessageUtility.getCurrentLocale()));
        if (!this.model.emptyFieldsAllowed() && calculateSublotQtyAndNumberOfSubLots.getNumOfFullSublots() <= 0 && calculateSublotQtyAndNumberOfSubLots.getNumOfPartialSublots() <= 0) {
            ProductPhaseSwingHelper.showErrorDlg(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "NoPackagingLevelValues_Txt"));
            return false;
        }
        return this.checkInvalidQuantityForPackagingLevelsError(calculateSublotQtyAndNumberOfSubLots);
    }

    private boolean checkInvalidQuantityForPackagingLevelsError(final MatProducePackingLevelsHelper0710.PackingLevelSublotsQuantitiesResult sublotQtysHelper) {
        IMeasuredValue fullSublotQty = null;
        IMeasuredValue partialSublotQty = null;
        try {
            if (sublotQtysHelper.getNumOfFullSublots() > 0) {
                fullSublotQty = sublotQtysHelper.getFullSublotQty();
            }
            if (sublotQtysHelper.getNumOfPartialSublots() > 0) {
                partialSublotQty = sublotQtysHelper.getPartialSublotQty();
            }
        }
        catch (NumberFormatException ex) {
            ProductPhaseSwingHelper.showErrorDlg(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "InvalidQty_Txt"));
            return false;
        }
        if (fullSublotQty != null && fullSublotQty.getValue().compareTo(BigDecimal.ZERO) <= 0 && partialSublotQty != null && partialSublotQty.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            ProductPhaseSwingHelper.showErrorDlg(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "InvalidQty_Txt"));
            return false;
        }
        return true;
    }

    private boolean checkInvalidQuantityForNoPackagingLevelsError() {
        if (this.model.emptyFieldsAllowed() && this.getView().getQty().isEmpty() && this.getView().getNumberOfSublots().isEmpty()) {
            return true;
        }
        final int checkInvalidNumberOfSublotsForNoPackagingLevelsError = this.checkInvalidNumberOfSublotsForNoPackagingLevelsError();
        BigDecimal fromString;
        try {
            fromString = BigDecimalUtilities.fromString(this.getView().getQty().isEmpty() ? "0" : this.getView().getQty());
        }
        catch (ParseException ex) {
            throw new MESRuntimeException(ex);
        }
        final MeasuredValue mv = MeasuredValueUtilities.createMV(fromString, this.getView().getUoM());
        if (checkInvalidNumberOfSublotsForNoPackagingLevelsError <= 0) {
            return false;
        }
        if (mv == null || mv.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            ProductPhaseSwingHelper.showErrorDlg(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "InvalidQty_Txt"));
            return false;
        }
        return true;
    }

    private int checkInvalidNumberOfSublotsForNoPackagingLevelsError() {
        int int1;
        try {
            int1 = Integer.parseInt(this.getView().getNumberOfSublots());
        }
        catch (NumberFormatException ex) {
            ProductPhaseSwingHelper.showErrorDlg(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "InvalidNumOfSublots_Txt"));
            this.getView().clearNumberOfSublots();
            return -1;
        }
        if (int1 <= 0) {
            ProductPhaseSwingHelper.showErrorDlg(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "InvalidNumOfSublots_Txt"));
            this.getView().clearNumberOfSublots();
            return -1;
        }
        return int1;
    }

    private boolean checkNumberOfSublotsError() {
        int max = 0;
        final Iterator<String> iterator = this.getConfiguredBarcodeTemplates().iterator();
        while (iterator.hasNext()) {
            max = Math.max(max, StringUtils.length(iterator.next()));
        }
        final int n = 4000 / (max + StringUtils.length(this.getModel().getSeparatorForSublotIdentifiers()));
        int int1;
        if (this.getModel().arePackagingLevelsDefined()) {
            final MatProducePackingLevelsHelper0710.PackingLevelSublotsQuantitiesResult calculateSublotQtyAndNumberOfSubLots = MatProducePackingLevelsHelper0710.calculateSublotQtyAndNumberOfSubLots(this.getModel().getSizeOfSLLevelForPackagingLevel(), MeasuredValueUtilities.createMV(this.getView().totalValueLabel().getText(), I18nMessageUtility.getCurrentLocale()));
            int1 = calculateSublotQtyAndNumberOfSubLots.getNumOfFullSublots() + calculateSublotQtyAndNumberOfSubLots.getNumOfPartialSublots();
        }
        else {
            int1 = Integer.parseInt(this.getView().getNumberOfSublots().isEmpty() ? "0" : this.getView().getNumberOfSublots());
        }
        if (int1 > n) {
            ProductPhaseSwingHelper.showErrorDlg(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "TooManySublotsMsg", new Object[] { int1, n }));
            return false;
        }
        return true;
    }

    private List<String> getConfiguredBarcodeTemplates() {
        final List<String> list = Arrays.asList(MESConfiguration.getMESConfiguration().getString("LibraryHolder/services-inventory-impl.jar/DefaultSublotBarcodeTemplate", "$ssssssssssbbbbbbbbbb", "Default template for sublot barcodes"));
        final List list2 = MESConfiguration.getMESConfiguration().getList("LibraryHolder/services-inventory-impl.jar/SublotBarcodeTemplates", (List)list, "Sublot barcode templates. Must be a list of template strings, whereas each template string must be of the form <prefix><ssss...s><bbbb...b><pppp...p>");
        final ArrayList<String> list3 = new ArrayList<String>();
        list3.addAll(list);
        list3.addAll(list2);
        return list3;
    }

    private boolean showExceptionDialogForSingleAndMultipleExceptions(final Map<String, String> exceptionMessageMap) {
        Long n = null;
        for (final Map.Entry<SystemTriggeredExceptions, Long> entry : this.checkRiskAssesmentMap.entrySet()) {
            if (n == null || entry.getValue().compareTo(n) > 0) {
                n = entry.getValue();
            }
        }
        final StringBuilder exceptionText = new StringBuilder();
        if (exceptionMessageMap.size() > 1) {
            this.buildExceptionText(exceptionText, exceptionMessageMap);
            this.showExceptionDialogForLimitCheck(null, IMESExceptionRecord.RiskClass.valueOf(n), SystemTriggeredExceptions.CHECK_KEY_FOR_MULTIPLE_EXCEPTIONS, this, exceptionText.toString(), I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "MultipleExceptionsTxt"));
            return false;
        }
        if (exceptionMessageMap.size() == 1) {
            this.showExceptionDialogForSingleMessage(exceptionMessageMap.entrySet().iterator().next());
            return false;
        }
        return !((RtPhaseModelLCMatProduce0010)this.getModel()).isExceptionSigned() || true;
    }

    private void buildExceptionText(final StringBuilder exceptionText, final Map<String, String> map) {
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            this.getMultipleExceptionsMessage(exceptionText, entry, entry.getKey(), this.determineCheckKey(entry));
        }
    }

    private void showExceptionDialogForSingleMessage(final Map.Entry<String, String> entry) {
        final String parameterName = entry.getKey();
        final String s = entry.getValue();
        this.showExceptionDialogForLimitCheck(parameterName, null, this.determineCheckKey(entry), this, s, s);
    }

    private SystemTriggeredExceptions determineCheckKey(final Map.Entry<String, String> entry) {
        final String s = entry.getKey();
        switch (s) {
            case "Limit quantity to single logistic unit": {
                return SystemTriggeredExceptions.CHECK_KEY_FOR_SINGLE_LOGISTICUNIT_QTY;
            }
            case "Limit quantity to single sublot": {
                return SystemTriggeredExceptions.CHECK_KEY_FOR_SINGLE_SUBLOT_QTY;
            }
            case "Check planned quantity": {
                return SystemTriggeredExceptions.CHECK_KEY_FOR_PLANNED_QTY;
            }
            default: {
                throw new MESRuntimeException("Exception type not supported: " + entry.getKey());
            }
        }
    }

    private StringBuilder getMultipleExceptionsMessage(final StringBuilder exceptionText, final Map.Entry<String, String> entry, final String parameterName, final SystemTriggeredExceptions checkKey) {
        exceptionText.append(this.getExceptionTextForProcessParameter(parameterName, checkKey));
        exceptionText.append(StringConstants.LINE_BREAK);
        exceptionText.append(entry.getValue());
        exceptionText.append(StringConstants.LINE_BREAK);
        return exceptionText;
    }

    private String getExceptionTextForProcessParameter(final String parameterName, final SystemTriggeredExceptions checkKey) {
        if (checkKey.equals(SystemTriggeredExceptions.CHECK_KEY_FOR_PLANNED_QTY)) {
            final MESParamExceptionDef0300 mesParamExceptionDef0300 = (MESParamExceptionDef0300) this.getModel().getExecutor().getProcessParameterData((Class)MESParamExceptionDef0300.class, parameterName);
            return (mesParamExceptionDef0300.getExceptionDescr() == null) ? "" : mesParamExceptionDef0300.getExceptionDescr();
        }
        final MESParamExcpEnableNDef0200 mesParamExcpEnableNDef0200 = (MESParamExcpEnableNDef0200) this.getModel().getExecutor().getProcessParameterData((Class)MESParamExcpEnableNDef0200.class, parameterName);
        return (mesParamExcpEnableNDef0200.getMessage() == null) ? "" : mesParamExcpEnableNDef0200.getMessage();
    }

    private boolean getEnableParamCheck(final String paramName) {
        return ((MESParamExcpEnableNDef0200)this.getProcessParameterData((Class)MESParamExcpEnableNDef0200.class, paramName)).getEnabled();
    }

    private List<String> inventoryLevelsForWithoutPackingLevels() {
        final ArrayList<String> list = new ArrayList<String>();
        final Iterator<IPackagingLevelBean> iterator = this.getConfiguredPackingLevels().iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().getInventoryLevel().getMeaning());
        }
        return list;
    }

    private Map<String, String> getAllExceptionChecksAndMessages() {
        this.checkRiskAssesmentMap = new EnumMap<>(SystemTriggeredExceptions.class);
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        if (!this.getModel().arePackagingLevelsDefined()) {
            this.exceptionCheckListForwithoutPackingLevels(hashMap);
        }
        else {
            this.exceptionCheckListForwithPackingLevels(hashMap);
        }
        final String text = this.getView().totalValueLabel().getText();
        final MeasuredValue totalProducedQty = StringUtils.isEmpty(text) ? MeasuredValueUtilities.createMV(BigDecimal.ZERO, this.getModel().getDefaultUoM()) : MeasuredValueUtilities.createMV(text, I18nMessageUtility.getCurrentLocale());
        if (!this.isExceptionSigned(SystemTriggeredExceptions.CHECK_KEY_FOR_PLANNED_QTY.toString()) && !this.isExceptionSigned(SystemTriggeredExceptions.CHECK_KEY_FOR_MULTIPLE_EXCEPTIONS.toString()) && this.isProducedQtyOutOfRange(totalProducedQty)) {
            hashMap.put("Check planned quantity", this.getExceptionMessageForPlannedQtyCheck());
            this.checkRiskAssesmentMap.put(SystemTriggeredExceptions.CHECK_KEY_FOR_PLANNED_QTY, ((MESParamExceptionDef0300)this.getProcessParameterData((Class)MESParamExceptionDef0300.class, "Check planned quantity")).getRiskAssessment());
        }
        return hashMap;
    }

    private Map<String, String> exceptionCheckListForwithoutPackingLevels(final Map<String, String> exceptionMessageMap) {
        if (!this.isExceptionSigned(SystemTriggeredExceptions.CHECK_KEY_FOR_MULTIPLE_EXCEPTIONS.toString()) && !this.isExceptionSigned(SystemTriggeredExceptions.CHECK_KEY_FOR_SINGLE_SUBLOT_QTY.toString()) && this.getEnableParamCheck("Limit quantity to single sublot") && !StringUtils.isEmpty(this.getView().getNumberOfSublots()) && Integer.parseInt(this.getView().getNumberOfSublots()) > 1) {
            exceptionMessageMap.put("Limit quantity to single sublot", I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "Limit_Sublot_Qty_ExceptionMsg"));
            this.checkRiskAssesmentMap.put(SystemTriggeredExceptions.CHECK_KEY_FOR_SINGLE_SUBLOT_QTY, ((MESParamExcpEnableNDef0200)this.getProcessParameterData((Class)MESParamExcpEnableNDef0200.class, "Limit quantity to single sublot")).getRiskAssessment());
        }
        return exceptionMessageMap;
    }

    private Map<String, String> exceptionCheckListForwithPackingLevels(final Map<String, String> exceptionMessageMap) {
        final List<String> inventoryLevelsForWithoutPackingLevels = this.inventoryLevelsForWithoutPackingLevels();
        if ((inventoryLevelsForWithoutPackingLevels.contains("LogisticUnit") || inventoryLevelsForWithoutPackingLevels.contains("SublotAndLogisticUnit")) && this.getEnableParamCheck("Limit quantity to single logistic unit") && this.getLimitCheckForInventoryLevels("LogisticUnit", SystemTriggeredExceptions.CHECK_KEY_FOR_SINGLE_LOGISTICUNIT_QTY)) {
            exceptionMessageMap.put("Limit quantity to single logistic unit", I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "Limit_Logistic_Qty_ExceptionMsg"));
            this.checkRiskAssesmentMap.put(SystemTriggeredExceptions.CHECK_KEY_FOR_SINGLE_LOGISTICUNIT_QTY, ((MESParamExcpEnableNDef0200)this.getProcessParameterData((Class)MESParamExcpEnableNDef0200.class, "Limit quantity to single logistic unit")).getRiskAssessment());
        }
        if ((inventoryLevelsForWithoutPackingLevels.contains("Sublot") || inventoryLevelsForWithoutPackingLevels.contains("SublotAndLogisticUnit")) && this.getEnableParamCheck("Limit quantity to single sublot") && this.getLimitCheckForInventoryLevels("Sublot", SystemTriggeredExceptions.CHECK_KEY_FOR_SINGLE_SUBLOT_QTY)) {
            exceptionMessageMap.put("Limit quantity to single sublot", I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "Limit_Sublot_Qty_ExceptionMsg"));
            this.checkRiskAssesmentMap.put(SystemTriggeredExceptions.CHECK_KEY_FOR_SINGLE_SUBLOT_QTY, ((MESParamExcpEnableNDef0200)this.getProcessParameterData((Class)MESParamExcpEnableNDef0200.class, "Limit quantity to single sublot")).getRiskAssessment());
        }
        return exceptionMessageMap;
    }

    private boolean getLimitCheckForInventoryLevels(final String inventoryLevel, final SystemTriggeredExceptions checkKey) {
        return this.getView().getDefinedPackingLevels() != null && !this.limitQuantityCheckForSubLotAndLogisticUnit(inventoryLevel) && !this.isExceptionSigned(checkKey.toString()) && !this.isExceptionSigned(SystemTriggeredExceptions.CHECK_KEY_FOR_MULTIPLE_EXCEPTIONS.toString());
    }

    private void showExceptionDialogForLimitCheck(final String parameterName, final IMESExceptionRecord.RiskClass maxRisk, final SystemTriggeredExceptions checkKey, final RtPhaseExecutorLCMatProduce0010 executor, final String exceptionText, final String exceptionMessage) {
        if (checkKey.equals(SystemTriggeredExceptions.CHECK_KEY_FOR_PLANNED_QTY)) {
            AbstractPhaseExceptionView0200.displayParametrizedExceptionDialog("Check planned quantity", checkKey.toString(), this.getModel().getExecutor(), exceptionText);
        }
        else if (checkKey.equals(SystemTriggeredExceptions.CHECK_KEY_FOR_MULTIPLE_EXCEPTIONS)) {
            RtPhaseExceptionViewLCMatProduce0010.displayParametrizedExceptionDialogMsg(exceptionText, maxRisk, checkKey.toString(), executor, exceptionMessage);
        }
        else {
            RtPhaseExceptionViewLCMatProduce0010.displayParametrizedExceptionDialogMsg(parameterName, checkKey.toString(), (AbstractPhaseExecutor)executor, exceptionText, exceptionMessage);
        }
    }

    public List<IPackagingLevelBean> getAllPackagingLevels() {
        final IMESMaterialParameter phaseOutputMaterialParameter = this.model.getPhaseOutputMaterialParameter();
        if (phaseOutputMaterialParameter == null) {
            return MatProducePackingLevelsHelper0710.getAllPackagingLevels(null, null);
        }
        IMESMasterRecipe mr = null;
        if (this.getRtPhase() != null) {
            if (this.getRtPhase().getParent().getProcessOrderItem().getPart().equals(phaseOutputMaterialParameter.getMaterial())) {
                mr = this.getMasterRecipe();
            }
        }
        else {
            mr = this.getMasterRecipe();
            if (mr != null && !mr.getPartProduced().equals(phaseOutputMaterialParameter.getMaterial())) {
                mr = null;
            }
        }
        return MatProducePackingLevelsHelper0710.getAllPackagingLevels(phaseOutputMaterialParameter, mr);
    }

    private IMESMasterRecipe getMasterRecipe() {
        if (this.getPhase() != null && this.getPhase().getParentEntity() != null && this.getPhase().getParentEntity().getParentEntity() != null && this.getPhase().getParentEntity().getParentEntity().getParentEntity() != null) {
            final MasterRecipe masterRecipe = ((IMESProcedure)this.getPhase().getParentEntity().getParentEntity().getParentEntity()).getMasterRecipe();
            try {
                return ((IS88RecipeService)ServiceFactory.getService((Class)IS88RecipeService.class)).getMasterRecipeByNameRevision(masterRecipe.getName(), masterRecipe.getRevision());
            }
            catch (DatasweepException ex) {
                throw new MESRuntimeException("Retrieving Master Recipe failed", ex);
            }
        }
        return null;
    }

    public List<IPackagingLevelBean> getConfiguredPackingLevels() {
        final List<IPackagingLevelBean> allPackagingLevels = this.getAllPackagingLevels();
        this.getModel().setDefaultPackingChoiceElement(MatProducePackingLevelsHelper0710.getDefaultPackagingCE(allPackagingLevels));
        if (this.configPackingLevelsCacheList == null || this.configPackingLevelsCacheList.isEmpty()) {
            this.configPackingLevelsCacheList = MatProducePackingLevelsHelper0710.getConfiguredPackagingLevels(allPackagingLevels);
        }
        return this.configPackingLevelsCacheList;
    }

    void calculateContainedNumbersOfBasePackagingLevel() {
        final MatProducePackingLevelsHelper0710.ContainedNumbersOfBasePackagineLevelResult calculateContainedNumbersOfBasePackagingLevel = MatProducePackingLevelsHelper0710.calculateContainedNumbersOfBasePackagingLevel(this.getConfiguredPackingLevels());
        this.convertSourceUoMToTargetUoM(BigDecimal.ZERO);
        this.getModel().setContainedNumbersOfBasePackagingLevel(calculateContainedNumbersOfBasePackagingLevel.getContainedNumbersOfBasePackagingLevel());
        this.getModel().setSizeOfSLLevelForPackagingLevel((MeasuredValue)this.convertSourceUoMToTargetUoM(calculateContainedNumbersOfBasePackagingLevel.getContainedNumberOfSLLevelOfBasePackagingLevel()));
        this.getModel().setSizeOfLULevelForPackagingLevel((MeasuredValue)this.convertSourceUoMToTargetUoM(calculateContainedNumbersOfBasePackagingLevel.getContainedNumberOfLULevelOfBasePackagingLevel()));
    }

    public boolean isUoMConversionNeededAndPossible() {
        boolean b = false;
        final Part phaseOutputMaterial = this.model.getPhaseOutputMaterial();
        if (phaseOutputMaterial != null) {
            final IUnitOfMeasure sourceUoM = this.getModel().getSourceUoM();
            final UnitOfMeasure unitOfMeasure = MESNamedUDAPart.getUnitOfMeasure(phaseOutputMaterial);
            if (sourceUoM.getSymbol() != null && !sourceUoM.equals(unitOfMeasure)) {
                final boolean canConvertToUoM = PartRelatedMeasuredValueUtilities.canConvertToUoM(phaseOutputMaterial, sourceUoM, unitOfMeasure);
                if (canConvertToUoM) {
                    b = canConvertToUoM;
                }
                else {
                    final String localizedMessage = I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "UoMConversionNotPossible_Txt");
                    if (!this.uomConversionError) {
                        ProductPhaseSwingHelper.showErrorDlg(localizedMessage);
                        this.uomConversionError = true;
                    }
                }
            }
        }
        return b;
    }

    public IMeasuredValue convertSourceUoMToTargetUoM(final BigDecimal contentOfPackagingLevel) {
        if (contentOfPackagingLevel == null) {
            return null;
        }
        Object o;
        final MeasuredValue measuredValue = (MeasuredValue)(o = MeasuredValueUtilities.createMV(contentOfPackagingLevel, (UnitOfMeasure) this.model.getDefaultUoM()));
        if (this.isUoMConversionNeededAndPossible() && this.getModel().arePackagingLevelsDefined()) {
            final Part phaseOutputMaterial = this.model.getPhaseOutputMaterial();
            final int max = Math.max(MaterialHelper0710.getScaleOfConversionFactor(phaseOutputMaterial), (this.getModel().matOutputParameter != null && this.getModel().matOutputParameter.getPlannedQtyMV() != null) ? this.getModel().matOutputParameter.getPlannedQtyMV().getScale() : 0);
            final IUnitOfMeasure sourceUoM = this.getModel().getSourceUoM();
            final UnitOfMeasure unitOfMeasure = MESNamedUDAPart.getUnitOfMeasure(phaseOutputMaterial);
            if (sourceUoM != null || unitOfMeasure != null) {
                final MeasuredValue measuredValue2 = PCContext.getFunctions().createMeasuredValue(((IMeasuredValue)measuredValue).getValue(), (UnitOfMeasure)sourceUoM, max);
                try {
                    o = this.getConverterForPart().convert(measuredValue2, unitOfMeasure);
                }
                catch (MESIncompatibleUoMException ex) {
                    throw new MESRuntimeException(ex);
                }
            }
        }
        return (IMeasuredValue)o;
    }

    public IMeasuredValueConverter getConverterForPart() {
        return PartRelatedMeasuredValueUtilities.getMeasuredValueConverterForPart(this.model.getPhaseOutputMaterial());
    }

    private boolean prepareAndProduceSublots() {
        boolean b;
        if (this.getView().getDefinedPackingLevels() != null) {
            this.getModel().setDefinedPackingLevels(this.getView().getDefinedPackingLevels());
            this.getModel().setDefinedPackingLevelNames(this.getView().getDefinedPackingLevelNames());
            final MatProducePackingLevelsHelper0710.PackingLevelSublotsQuantitiesResult calculateSublotQtyAndNumberOfSubLots = MatProducePackingLevelsHelper0710.calculateSublotQtyAndNumberOfSubLots(this.getModel().getSizeOfSLLevelForPackagingLevel(), MeasuredValueUtilities.createMV(this.getView().totalValueLabel().getText(), I18nMessageUtility.getCurrentLocale()));
            b = this.produceSublotsForPackingLevels(calculateSublotQtyAndNumberOfSubLots.getNumOfFullSublots(), calculateSublotQtyAndNumberOfSubLots.getFullSublotQty(), calculateSublotQtyAndNumberOfSubLots.getNumOfPartialSublots(), calculateSublotQtyAndNumberOfSubLots.getPartialSublotQty());
        }
        else {
            this.setSizeOfSLLevelForNoPacking();
            b = this.validateFieldsAndProduceSublotsNoPackingLevels();
        }
        return b;
    }

    private void setSizeOfSLLevelForNoPacking() {
        BigDecimal fromString = new BigDecimal(0);
        final String qty = this.getView().getQty();
        if (qty != null && !qty.equals("")) {
            try {
                fromString = BigDecimalUtilities.fromString(qty);
            }
            catch (ParseException ex) {
                throw new MESRuntimeException(ex);
            }
        }
        this.getModel().setSizeOfSLLevelForPackagingLevel(MeasuredValueUtilities.createMV(fromString, this.getView().getUoM()));
    }

    private boolean produceSublotsForPackingLevels(final int numOfFullSublots, final IMeasuredValue fullSublotQty, final int numOfPartialSublots, final IMeasuredValue partialSublotQty) {
        this.nrProducedSublots = 0;
        this.nrRequestedSublots = numOfFullSublots + numOfPartialSublots;
        final boolean b = numOfPartialSublots > 0;
        boolean b2 = true;
        if (numOfFullSublots > 0) {
            b2 = this.produceSublotsAndBatch(numOfFullSublots, fullSublotQty, numOfPartialSublots <= 0, !b);
            if (this.lastProducedSublots != null) {
                this.nrProducedSublots += this.lastProducedSublots.size();
            }
        }
        if (b) {
            b2 = this.produceSublotsAndBatch(numOfPartialSublots, partialSublotQty, true, true);
            if (this.lastProducedSublots != null) {
                this.nrProducedSublots += this.lastProducedSublots.size();
            }
        }
        if (numOfFullSublots + numOfPartialSublots <= 0) {
            this.getModel().addPhaseDataNoSublotsProduced();
        }
        return b2;
    }

    void totalCalculationForNoPackingLevel() {
        BigDecimal bigDecimal = new BigDecimal(0);
        BigDecimal fromString = new BigDecimal(0);
        if (!this.getView().getNumberOfSublots().isEmpty()) {
            bigDecimal = new BigDecimal(this.getView().getNumberOfSublots());
        }
        if (!this.getView().getQty().isEmpty()) {
            try {
                fromString = BigDecimalUtilities.fromString(this.getView().getQty());
            }
            catch (ParseException ex) {
                throw new MESRuntimeException(ex);
            }
        }
        this.getView().totalValueLabel().setText(MeasuredValueUtilities.createMV(bigDecimal.multiply(fromString), (UnitOfMeasure) this.getView().getUoM()).toString());
    }

    void totalCalculationForNoPackingLevelForCompletePhase(final BigDecimal numOfSublotsValue, final BigDecimal sublotsQtyValue, final IUnitOfMeasure uomValue) {
        this.getView().totalValueLabel().setText(MeasuredValueUtilities.createMV(numOfSublotsValue.multiply(sublotsQtyValue), uomValue).toString());
    }

    private boolean produceSublotsAndBatch(final int numberOfSublots, final IMeasuredValue qtyPerSublot, final boolean mayAddSummaryData, final boolean doCloseIfNecessary) {
        this.getOrCreateBatch();
        WarehouseRuntimeException ex = null;
        this.lastProducedSublots = null;
        try {
            this.getModel().setSublotProductionStopped(false);
            this.lastProducedSublots = this.serviceProduceSublots(numberOfSublots, qtyPerSublot, doCloseIfNecessary);
            this.checkAndSetSublotProductionStopped(numberOfSublots);
        }
        catch (MESNoSuitableOrderStepInputsFoundException ex3) {
            ProductPhaseSwingHelper.showErrorDlg(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "NoOSI_Msg"));
            return false;
        }
        catch (WarehouseRuntimeException ex2) {
            ex = ex2;
            this.lastProducedSublots = this.getSublotsInCaseOfWarehouseException(ex);
            this.checkAndSetSublotProductionStopped(numberOfSublots);
        }
        catch (MESQuantityExceedsAllowedVariationException | MESSublotOperationNotAllowedException | MESQuantityMustNotBeNegativeException | MESIncompatibleUoMException | MESRuntimeException ex4) {
            final Object o = null;
            ProductPhaseSwingHelper.showErrorDlg(((Throwable)o).getLocalizedMessage());
            return false;
        }
        if (this.lastProducedSublots != null && !this.lastProducedSublots.isEmpty()) {
            this.printSublots(this.lastProducedSublots);
            if (this.lastProducedSublots != null) {
                this.getModel().setPhaseDataForSublotsAndHeader(((RtPhaseModelLCMatProduce0010)this.model).getOSO().getPart().getPartNumber(), this.lastProducedSublots, qtyPerSublot, mayAddSummaryData);
            }
        }
        if (ex != null) {
            this.getModel().setLastWarehouseExceptionAndError(ex, "ProduceSublotWarehouseError_Error");
            ProductPhaseSwingHelper.showErrorDlg(this.getModel().createWarehouseErrorMessage("PhaseProductProduceMaterial0710", null, true));
            return false;
        }
        this.getModel().resetLastWarehouseExceptionAndError();
        return true;
    }

    private void checkAndSetSublotProductionStopped(final int numberOfSublots) {
        if (this.lastProducedSublots != null && this.lastProducedSublots.size() < numberOfSublots) {
            this.getModel().setSublotProductionStopped(true);
        }
    }

    private List<Sublot> serviceProduceSublots(final int numberOfSublots, final IMeasuredValue qtyPerSublot, final boolean doCloseIfNecessary) throws MESQuantityExceedsAllowedVariationException, MESSublotOperationNotAllowedException, MESQuantityMustNotBeNegativeException, MESIncompatibleUoMException, MESNoSuitableOrderStepInputsFoundException {
        ProduceSublotInfo produceSublotInfo;
        if(getMaterialPosiControl().getEnable() != null && getMaterialPosiControl().getEnable() && getMaterialPosiControl().getStorageLocation() != null){
            //产出到指定位置
            produceSublotInfo = new ProduceSublotInfo((this.getModel()).batch, qtyPerSublot, null).sublotName(null).deleteSourceSublot(false).storageLocation(PCContext.getFunctions().getLocation(getMaterialPosiControl().getStorageLocation())).removeQty0FromSourceSublot(true).useDefaultSublotQualityStatusFromMaterial(false);
        }else {
            produceSublotInfo = new ProduceSublotInfo((this.getModel()).batch, qtyPerSublot, null).sublotName(null).deleteSourceSublot(false).storageLocation((this.getModel()).getLocation()).removeQty0FromSourceSublot(true).useDefaultSublotQualityStatusFromMaterial(false);
        }
        if (this.model.isSublotStatusOverwriteEnabled()) {
            produceSublotInfo.sublotQualityStatus(this.model.getSublotStatusFromParameter());
            produceSublotInfo.setIgnoreSublotQualityStatusFromOso(true);
        }
        final List produceSublotsConcurrently = MaterialHelper0710.getMESOrderService().produceSublotsConcurrently((this.getModel()).getOSO(), numberOfSublots, produceSublotInfo, new OrderStepOutputProductionConfig().setSupportConcurrentProduction(true).setDoCloseOSO(doCloseIfNecessary && (this.model).isPhaseResultDone()).setLimits((this.getModel()).getLimits()), new TransactionHistoryContext());
        (this.model).refreshOSAndDependents(false);
        return (List<Sublot>)produceSublotsConcurrently;
    }

    private void printSublots(final List<Sublot> sublots) {
        if (sublots != null) {
            this.setInitialWeight(sublots);
            this.printSublotsLabels(sublots);
        }
    }

    private void setInitialWeight(final List<Sublot> sublots) {
        final Iterator<Sublot> iterator = sublots.iterator();
        while (iterator.hasNext()) {
            this.getModel().setInitialWeight(iterator.next());
        }
    }

    private void printSublotsLabels(final List<Sublot> sublots) {
        final ReportConfiguration reportConfiguration = new ReportConfiguration(PCContext.getEnvironment());
        reportConfiguration.setConfigDisplayMessages(true);
        reportConfiguration.setPreProcessingSubroutine("");
        reportConfiguration.setGlobalDataSubroutine("wad_label_InventorySublot");
        reportConfiguration.setPostProcessingSubroutine("wip_label_InventorySublot_post");
        final String s = "MESSublotLabelRD";
        final String s2 = "1";
        final ReportDesign reportDesign = PCContext.getFunctions().getReportDesign(s, s2);
        if (reportDesign == null) {
            ProductPhaseSwingHelper.showErrorDlg(String.format(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "PrintError_Txt", new Object[] { s, s2 }), new Object[0]));
        }
        reportConfiguration.addReportDesignSubroutine(reportDesign, "");
        reportConfiguration.setPostProcessingSubroutine("");
        reportConfiguration.setTwoPassRendering(false);
        reportConfiguration.putInputPropertyPrint(true);
        reportConfiguration.putInputPropertyPrinter(ReportAndLabelConfiguration.getDefaultPrinterLabel());
        for (final Sublot sublot : sublots) {
            reportConfiguration.putInputProperty("sublot", sublot);
            ReportHelper.generateAndPrintReport(reportConfiguration);
            this.getModel().saveSublotReprintData(reportConfiguration.getOutputPropertyReport(), sublot);
        }
    }

    private List<Sublot> getSublotsInCaseOfWarehouseException(final WarehouseRuntimeException lastWHException) {
        List<Sublot> list = null;
        final Object result = lastWHException.getResult();
        if (result != null) {
            if (((List<Sublot>)result).getClass().isArray()) {
                list = Arrays.asList((Sublot[])result);
            }
            else {
                list = (List<Sublot>)result;
            }
        }
        return list;
    }

    private boolean validateFieldsAndProduceSublotsNoPackingLevels() {
        final int int1 = Integer.parseInt(this.getView().getNumberOfSublots().isEmpty() ? "0" : this.getView().getNumberOfSublots());
        final IMeasuredValue qtyPerSublotConvertedToTargetUoM = this.getQtyPerSublotConvertedToTargetUoM();
        this.getView().clearNumberOfSublots();
        this.getView().clearQty();
        boolean produceSublotsAndBatch = true;
        this.nrProducedSublots = 0;
        this.nrRequestedSublots = int1;
        if (int1 > 0) {
            produceSublotsAndBatch = this.produceSublotsAndBatch(int1, qtyPerSublotConvertedToTargetUoM, true, true);
            if (this.lastProducedSublots != null) {
                this.nrProducedSublots += this.lastProducedSublots.size();
            }
        }
        else {
            this.getModel().addPhaseDataNoSublotsProduced();
        }
        return produceSublotsAndBatch;
    }

    private IMeasuredValue getQtyPerSublotConvertedToTargetUoM() {
        final MeasuredValue quantityPerSublot = this.getQuantityPerSublot();
        try {
            return MaterialHelper0710.convertMeasuredValueToUoM(this.model.getPhaseOutputMaterial(), quantityPerSublot, this.getModel().getDefaultUoM());
        }
        catch (MESIncompatibleUoMException ex) {
            throw new MESRuntimeException(ex.getLocalizedMessage(), ex);
        }
    }

    private MeasuredValue getQuantityPerSublot() {
        try {
            return MeasuredValueUtilities.createMV(BigDecimalUtilities.fromString(this.getView().getQty().isEmpty() ? "0" : this.getView().getQty()), this.getView().getUoM());
        }
        catch (ParseException ex) {
            throw new MESRuntimeException(ex);
        }
    }

    protected void exceptionTransactionCallback(final String checkKey, final IMESExceptionRecord exceptionRecord, final IESignatureExecutor sigExecutor) {
        this.getMaterialObservable().notifyExceptionCallbackInSaveTransaction(checkKey, exceptionRecord, sigExecutor);
    }

    public void exceptionCanceled(final String checkKey) {
        this.removeIsExceptionRequested(checkKey);
        this.getMaterialObservable().notifyExceptionCanceled(checkKey);
    }

    public void exceptionSigned(final String checkKey, final MESRuntimeException afterCommitException) {
        this.getMaterialObservable().notifyExceptionSigned(checkKey);
        if ("RECORD_WAREHOUSE_ERROR_CHECK_KEY".equals(checkKey)) {
            try (final AutoWaitCursor autoWaitCursor = new AutoWaitCursor(this.getView())) {
                this.getModel().resetLastWarehouseExceptionAndError();
                this.triggerAutoCompleteAfterReturnFromExceptionView();
            }
        }
        else if (!checkKey.equals("CancelSublot") && !checkKey.equals("ReprintLabel")) {
            try (final AutoWaitCursor autoWaitCursor2 = new AutoWaitCursor(this.getView())) {
                this.triggerAutoCompleteAfterReturnFromExceptionView();
            }
        }
        else if (checkKey.equals("CancelSublot") && afterCommitException != null && afterCommitException instanceof WarehouseRuntimeException) {
            this.getModel().setLastWarehouseExceptionAndError((WarehouseRuntimeException)afterCommitException, "CancelSublotWarehouseError_Error");
            final String warehouseErrorMessage = this.getModel().createWarehouseErrorMessage("PhaseProductProduceMaterial0710", null, false);
            ProductPhaseSwingHelper.showErrorDlg(warehouseErrorMessage);
            this.getModel().recordSystemTriggeredWarehouseInteractionException(warehouseErrorMessage);
            this.getModel().resetLastWarehouseExceptionAndError();
        }
    }

    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("recordWarehouseError")) {
            this.getModel().fillRecordWarehouseErrorException("PhaseProductProduceMaterial0710", null);
        }
        super.propertyChange(evt);
    }

    public String getExceptionMessageForPlannedQtyCheck() {
        String s = null;
        final Limits limits = this.getModel().getLimits();
        if (this.overallProducedQuantity == null) {
            final String s2 = MeasuredValueUtilities.createMV(BigDecimal.ZERO, this.getModel().getUoMOfPlannedOrProcessedQuantity()).toString();
        }
        else {
            final String s2 = this.getModel().convertMeasuredValueToUoMOfPlannedQuantity(this.overallProducedQuantity).toString();
        }
        final String localizedMessage = I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "LimitsLabelForContinue_ExceptionMsg", new Object[] { limits.getLower(), limits.getUpper() });
        if (this.pqLimitExceededContinueFlag) {
            final String s2 = null;
            s = I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "PlannedQtyCheckForContinue_ExceptionMsg", new Object[] { (this.model.matOutputParameter.getProcessedQty() == null) ? this.getView().totalValueLabel().getText() : s2, this.model.matOutputParameter.getPlannedQtyMV().toString(), localizedMessage });
        }
        if (this.pqLimitExceededDoneFlag) {
            final String s2 = null;
            s = I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "PlannedQtyCheckForContinue_ExceptionMsg", new Object[] { (this.model.matOutputParameter.getProcessedQty() == null) ? this.getView().totalValueLabel().getText() : s2, this.model.matOutputParameter.getPlannedQtyMV().toString(), localizedMessage });
        }
        if (this.pqLimitNotReachedDoneFlag) {
            final String s2 = null;
            s = I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "PlannedQtyCheckForDone_ExceptionMsg", new Object[] { (this.model.matOutputParameter.getProcessedQty() == null) ? this.getView().totalValueLabel().getText() : s2, this.model.matOutputParameter.getPlannedQtyMV().toString(), localizedMessage });
        }
        return s;
    }

    public boolean limitQuantityCheckForSubLotAndLogisticUnit(final String inventoryLevel) {
        BigDecimal multiply = new BigDecimal(1);
        int n = 0;
        final BigDecimal value = MeasuredValueUtilities.createMV(this.getView().totalValueLabel().getText(), I18nMessageUtility.getCurrentLocale()).getValue();
        for (int i = this.getConfiguredPackingLevels().size(); i > 0; --i) {
            if (n == 0) {
                multiply = multiply.multiply(new BigDecimal(this.getConfiguredPackingLevels().get(this.getConfiguredPackingLevels().size() - i).getPackagingLevelContent().toString()));
            }
            if (this.getConfiguredPackingLevels().get(this.getConfiguredPackingLevels().size() - i).getInventoryLevel().getMeaning().equals(inventoryLevel) && n == 0) {
                n = 1;
            }
        }
        return this.getModel().getQtyFromValueforPackagingLevel(multiply).getValue().compareTo(value) >= 0;
    }

    private boolean isProducedQtyOutOfRange(final IMeasuredValue totalProducedQty) {
        boolean b = false;
        if (!PlannedQuantityMode.AS_DEFINED.getChoiceElement().equals(this.getModel().getPhaseOutputMaterialParameter().getPlannedQuantityMode()) || this.getModel().isOSOCompleted()) {
            return b;
        }
        final MeasuredValue mv = MeasuredValueUtilities.createMV(this.model.matOutputParameter.getProcessedQty(), I18nMessageUtility.getCurrentLocale());
        if (this.getModel().isOSOCompleted()) {
            this.overallProducedQuantity = mv;
        }
        else {
            this.overallProducedQuantity = MaterialHelper0710.addQuantityWithConversion(this.model.getPhaseOutputMaterial(), mv, totalProducedQty);
        }
        final IMeasuredValue convertMeasuredValueToUoMOfPlannedQuantity = this.getModel().convertMeasuredValueToUoMOfPlannedQuantity(this.overallProducedQuantity);
        final Limits limits = this.getModel().getLimits();
        try {
            if (!this.model.isPhaseResultDone()) {
                if (MeasuredValueUtilities.compare(convertMeasuredValueToUoMOfPlannedQuantity, limits.getUpper()) == 1) {
                    this.pqLimitExceededContinueFlag = true;
                    b = true;
                    this.qtyOverweightForException = convertMeasuredValueToUoMOfPlannedQuantity;
                }
            }
            else if (MeasuredValueUtilities.compare(convertMeasuredValueToUoMOfPlannedQuantity, limits.getLower()) == -1 && MeasuredValueUtilities.compare(convertMeasuredValueToUoMOfPlannedQuantity, limits.getUpper()) == -1) {
                this.pqLimitNotReachedDoneFlag = true;
                b = true;
            }
            else if (MeasuredValueUtilities.compare(convertMeasuredValueToUoMOfPlannedQuantity, limits.getUpper()) == 1) {
                this.pqLimitExceededDoneFlag = true;
                b = true;
                this.qtyOverweightForException = convertMeasuredValueToUoMOfPlannedQuantity;
            }
        }
        catch (MESIncompatibleUoMException ex) {
            throw new MESRuntimeException(ex.getLocalizedMessage(), ex);
        }
        return b;
    }

    void reprint(final String labelID) {
        try {
            MESLabelHelper.reprint(labelID, false, false, null);
        }
        catch (MESPrintException ex) {
            throw new MESRuntimeException(ex);
        }
    }

    protected void cancelSublot(final String sublotID) {
        this.getModel().initBatch();
        if (this.getModel().batch == null) {
            return;
        }
        final Sublot loadSublot = ((ISublotService)ServiceFactory.getService((Class)ISublotService.class)).loadSublot(this.getModel().batch.getUniqueName(), sublotID, true);
        if (loadSublot == null) {
            return;
        }
        this.consumeCancelledSublot(loadSublot);
        this.updateSublotQtyInPhaseData(sublotID);
    }

    private void consumeCancelledSublot(final Sublot sublot) {
        final TransactionHistoryContext transactionHistoryContext = new TransactionHistoryContext();
        transactionHistoryContext.setTransactionHistorySubtype(TransactionSubtype.PRODUCTION_OUTPUT_MATERIAL);
        try {
            ((ISublotService)ServiceFactory.getService((Class)ISublotService.class)).consumeSublot(sublot, sublot.getQuantity(), true, null, transactionHistoryContext);
            MESNamedUDASublot.setWasReplaced(sublot, MesClassUtility.LONG_TRUE);
            MESNamedUDASublot.setInitialWeight(sublot, MeasuredValueUtilities.createMV("0", MESNamedUDASublot.getInitialWeight(sublot).getUnitOfMeasure().getSymbol()));
            sublot.Save(null, null, PCContext.getDefaultAccessPrivilege());
        }
        catch (MESException | DatasweepException ex2) {
            final DatasweepException ex = null;
            final Throwable t = ex;
            throw new MESRuntimeException(t.getMessage(), t);
        }
    }

    private void updateSublotQtyInPhaseData(final String sublotID) {
        final List<MESRtPhaseDataLCMatProduce0010> allRtPhaseData = (this.getModel()).getAllRtPhaseData();
        boolean updateQtyInHeader = false;
        for (MESRtPhaseDataLCMatProduce0010 data : allRtPhaseData) {
            if (sublotID.equals(data.getSublotID())) {
                final MeasuredValue sublotQty = data.getSublotQty();
                data.setSublotQty(MeasuredValueUtilities.createMV(new BigDecimal(0), sublotQty.getUnitOfMeasure()));
                if (!updateQtyInHeader) {
                    updateQtyInHeader = this.updateQtyInHeader(allRtPhaseData, sublotQty);
                }
                this.saveData(data);
            }
        }
    }

    private boolean updateQtyInHeader(final List<MESRtPhaseDataLCMatProduce0010> rtPhaseData, final MeasuredValue sQty) {
        boolean b = false;
        for (final MESRtPhaseDataLCMatProduce0010 mesRtPhaseDataMatProduce0710 : rtPhaseData) {
            try {
                if (mesRtPhaseDataMatProduce0710.getIsHeader()) {
                    mesRtPhaseDataMatProduce0710.setProducedQty((MeasuredValue)mesRtPhaseDataMatProduce0710.getProducedQty().subtract(this.getModel().convertMeasuredValueToUoMOfPlannedQuantity(sQty)));
                    b = true;
                    break;
                }
                continue;
            }
            catch (Exception ex) {
                throw new MESRuntimeException("error in update produced quantity, in cancel sublot ", ex);
            }
        }
        return b;
    }

    private void saveData(final MESRtPhaseDataLCMatProduce0010 data) {
        try {
            data.save();
        }
        catch (DatasweepException ex) {
            throw new MESRuntimeException("Saving phase data failed, in cancel sublot ", ex);
        }
    }

    public MaterialObservable0710 getMaterialObservable() {
        if (this.materialObservable == null) {
            this.materialObservable = new MaterialObservable0710();
        }
        return this.materialObservable;
    }

    private void getOrCreateBatch() {
        this.getModel().initBatch();
        if (this.getModel().batch == null) {
            final OrderStepOutput oso = this.getModel().getOSO();
            final Part part = oso.getPart();
            final IBatchService batchService = (IBatchService)ServiceFactory.getService((Class)IBatchService.class);
            final String batchName = batchService.createBatchName(part);
            final StatusFSMInitializer statusFSMInitializer = new StatusFSMInitializer(this.getModel().getBatchStatus(), "BatchQuality");
            final BatchBuilder batchBuilder = new BatchBuilder(part);
            batchBuilder.batchName(batchName).productionDate(new Time()).thContext(new TransactionHistoryContext()).fsmInitializer((IFSMInitializer)statusFSMInitializer);
            final String treatmentIdOfOrder = this.getTreatmentIdOfOrder(oso);
            if (StringUtils.isNotEmpty(treatmentIdOfOrder)) {
                batchBuilder.treatmentId(treatmentIdOfOrder);
            }
            try {
                this.getModel().batch = batchService.createBatch(batchBuilder);
            }
            catch (MESWrongExpiryDateException | MESWrongRetestDateException | MESBatchIdentifierTooLongException | MESBatchIdentifierNotUniqueException ex) {
                final Object o = null;
                throw new MESRuntimeException((Throwable)o);
            }
        }
    }

    private String getTreatmentIdOfOrder(final OrderStepOutput oso) {
        return MESNamedUDAProcessOrderItem.getTreatmentID(oso.getOrderStep().getControlRecipe().getProcessOrderItem());
    }

    public BigDecimal calculateNumberForCurrentLevel(final List<BigDecimal> numbersForPackagingLevels, final int currentPackagingLevelIndex) {
        final ArrayList<BigDecimal> factorsForPackagingLevelsComparedToBaseLevel = new ArrayList<BigDecimal>(this.getModel().getContainedNumbersOfBasePackagingLevel());
        Collections.reverse(factorsForPackagingLevelsComparedToBaseLevel);
        return MatProducePackingLevelsHelper0710.calculateValueForCurrentLevel(numbersForPackagingLevels, factorsForPackagingLevelsComparedToBaseLevel, currentPackagingLevelIndex);
    }

    public IMeasuredValue calculateTotalValue(final List<IMeasuredValue> resultLabelValues, final int scale) {
        Object o = MatProducePackingLevelsHelper0710.calculateSumOfAllPackagingLevels(resultLabelValues);
        if (o == null) {
            o = PCContext.getFunctions().createMeasuredValue(new BigDecimal(0), (UnitOfMeasure) this.getModel().getDefaultUoM(), scale);
        }
        return (IMeasuredValue)o;
    }

    public void onDoneButtonPressed() {
        this.model.setPhaseResult(PhaseResult0710.DONE);
    }

    public void onStopButtonPressed() {
        this.model.setPhaseResult(PhaseResult0710.STOP);
    }

    public void onContinueButtonPressed() {
        this.model.setPhaseResult(PhaseResult0710.CONTINUE);
    }

    public void onRefresh() {
        this.getModel().refreshMaterialsList();
        this.getView().refreshGrid(false);
    }

    enum SystemTriggeredExceptions
    {
        CHECK_KEY_FOR_SINGLE_SUBLOT_QTY,
        CHECK_KEY_FOR_SINGLE_LOGISTICUNIT_QTY,
        CHECK_KEY_FOR_PLANNED_QTY,
        CHECK_KEY_FOR_MULTIPLE_EXCEPTIONS;
    }

    /**
     * MaterialPosiControl过参
     * @return
     */
    public MESParamMatPositionCtr0100 getMaterialPosiControl(){
        return getProcessParameterData(MESParamMatPositionCtr0100.class,"Material position control");
    }
}
