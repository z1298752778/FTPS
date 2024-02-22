package com.leateck.phase.materialproduction0010;

import java.math.*;

import com.datasweep.compatibility.client.Error;
import com.rockwell.mes.commons.base.ifc.choicelist.*;
import com.rockwell.mes.shared.product.material.*;
import com.rockwell.mes.services.s88.ifc.recipe.*;
import com.rockwell.mes.commons.parameter.plaininstruction.*;
import com.rockwell.mes.parameter.product.prodbatchbef.*;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.*;
import com.rockwell.mes.commons.parameter.exceptiondef.*;
import com.rockwell.mes.shared.product.material.choicelist.*;
import com.rockwell.mes.commons.parameter.bool.*;
import com.rockwell.mes.parameter.product.prodsublotstat.*;
import com.rockwell.mes.commons.base.ifc.i18n.*;
import com.rockwell.mes.commons.base.ifc.configuration.*;
import com.datasweep.plantops.common.measuredvalue.*;
import com.rockwell.mes.shared.product.material.util.*;
import com.rockwell.mes.commons.base.ifc.exceptions.*;
import com.rockwell.mes.services.commons.ifc.functional.*;
import com.rockwell.mes.commons.base.ifc.functional.*;
import org.apache.commons.lang3.*;
import java.nio.charset.*;
import com.datasweep.compatibility.ui.*;
import com.rockwell.mes.services.commons.ifc.order.*;
import com.rockwell.mes.commons.base.ifc.nameduda.*;
import com.rockwell.mes.services.inventory.ifc.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import com.rockwell.mes.shared0400.product.ui.basics.util.*;
import com.rockwell.mes.services.order.ifc.*;
import com.rockwell.mes.shared.product.material.warehouseintegration.*;
import com.rockwell.mes.commons.shared.phase.mvc.*;
import com.rockwell.mes.apps.ebr.ifc.phase.*;
import java.util.*;
import com.rockwell.mes.services.s88.ifc.*;
import com.rockwell.mes.services.warehouseintegration.ifc.*;
import com.rockwell.mes.services.s88.ifc.execution.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import com.datasweep.compatibility.client.*;
import org.apache.commons.logging.*;

public class RtPhaseModelLCMatProduce0010 extends AbstractPhaseModel0200<MESRtPhaseDataLCMatProduce0010, MESRtPhaseOutputLCMatProduce0010> implements IWarehouseInteractionModel0710
{
    private static final Log LOGGER;
    public static final String MSGPACK = "PhaseProductProduceMaterial0710";
    protected static final String PARAMETER_CANCELSUBLOT_ACTION = "Cancel sublot";
    protected static final String PARAMETER_OVERWRITE_SUBLOT_STATUS = "Overwrite target sublot status";
    protected ProcessedMaterialDAO0710 matOutputParameter;
    private List<AbstractProcMaterialDAO0710> materials;
    private final List<String> generatedSublotsList;
    protected OrderStep os;
    protected OrderStepOutput oso;
    private IUnitOfMeasure defaultUoM;
    protected Batch batch;
    private String definedPackingLevels;
    private String definedPackingLevelNames;
    private List<BigDecimal> containedNumbersOfBasePackagingLevel;
    private MeasuredValue sizeOfSLLevelForPackagingLevel;
    private MeasuredValue sizeOfLULevelForPackagingLevel;
    public static final String PACKING_LEVEL_NA = "NotApplicable";
    private IMESChoiceElement defaultPackingChoiceElement;
    private Limits limits;
    public static final String RECORD_WAREHOUSE_ERROR_EVENT = "recordWarehouseError";
    private String lastWarehouseExceptionLocalizedMessage;
    private String lastWarehouseErrorMessageId;
    private PhaseResult0710 phaseResult;
    private boolean sublotProductionStopped;
    private long osoKey;
    private Location location;
    private boolean osoCompletedAtStart;

    protected RtPhaseModelLCMatProduce0010(final RtPhaseExecutorLCMatProduce0010 inPhaseExecutor) {
        super(inPhaseExecutor);
        this.generatedSublotsList = new ArrayList<>();
        this.defaultUoM = null;
        this.definedPackingLevels = null;
        this.definedPackingLevelNames = null;
        this.containedNumbersOfBasePackagingLevel = new ArrayList<>();
        this.sizeOfSLLevelForPackagingLevel = null;
        this.sizeOfLULevelForPackagingLevel = null;
        this.defaultPackingChoiceElement = null;
        this.limits = null;
        this.lastWarehouseExceptionLocalizedMessage = null;
        this.lastWarehouseErrorMessageId = null;
        this.sublotProductionStopped = false;
    }

    public RtPhaseExecutorLCMatProduce0010 getExecutor() {
        return (RtPhaseExecutorLCMatProduce0010)this.executor;
    }

    public boolean init() {
        final IMESMaterialParameter phaseOutputMaterialParameter = this.getPhaseOutputMaterialParameter();
        if (phaseOutputMaterialParameter != null) {
            this.addNewOutputMaterial(phaseOutputMaterialParameter);
        }
        this.os = this.getExecutor().getOrderStep();
        this.initOSO();
        this.refreshMaterialsList();
        this.initPhaseResult();
        if (!this.getStatus().equals(IPhaseExecutor.Status.PREVIEW) && this.arePackagingLevelsDefined()) {
            this.getExecutor().calculateContainedNumbersOfBasePackagingLevel();
        }
        return true;
    }

    public String getInstruction() {
        final MESParamPlainInstr0100 mesParamPlainInstr0100 = (MESParamPlainInstr0100)this.executor.getProcessParameterData((Class)MESParamPlainInstr0100.class, "Instruction");
        return (mesParamPlainInstr0100.getText() == null) ? "" : mesParamPlainInstr0100.getText();
    }

    protected String getBatchStatus() {
        return ((MESParamProdBatchDef0500)this.getExecutor().getProcessParameterData((Class)MESParamProdBatchDef0500.class, "Batch definition")).getBatchStatus();
    }

    public IMESExceptionRecord.RiskClass getRiskClassActionReprint() {
        return ChoiceLists0710.getRiskClass(((MESParamExceptionDef0300)this.getExecutor().getProcessParameterData((Class)MESParamExceptionDef0300.class, "Reprint")).getRiskAssessment());
    }

    public String getMsgActionReprint() {
        return ((MESParamExceptionDef0300)this.getExecutor().getProcessParameterData((Class)MESParamExceptionDef0300.class, "Reprint")).getExceptionDescr();
    }

    public IMESExceptionRecord.RiskClass getRiskClassActionCancelSublot() {
        return ChoiceLists0710.getRiskClass(((MESParamExceptionDef0300)this.getExecutor().getProcessParameterData((Class)MESParamExceptionDef0300.class, "Cancel sublot")).getRiskAssessment());
    }

    public String getMsgActionCancelSublot() {
        return ((MESParamExceptionDef0300)this.getExecutor().getProcessParameterData((Class)MESParamExceptionDef0300.class, "Cancel sublot")).getExceptionDescr();
    }

    public boolean isLoopStopEnabled() {
        final MESParamBoolean0100 mesParamBoolean0100 = (MESParamBoolean0100)this.executor.getProcessParameterData((Class)MESParamBoolean0100.class, "Allow loop exit with Stop");
        return mesParamBoolean0100.getEnabled() != null && mesParamBoolean0100.getEnabled();
    }

    public boolean isSublotStatusOverwriteEnabled() {
        final MESParamProdSublotStat0100 mesParamProdSublotStat0100 = (MESParamProdSublotStat0100)this.executor.getProcessParameterData((Class)MESParamProdSublotStat0100.class, "Overwrite target sublot status");
        return mesParamProdSublotStat0100.getEnabled() != null && mesParamProdSublotStat0100.getEnabled();
    }

    public IMESChoiceElement getSublotStatusFromParameter() {
        final MESParamProdSublotStat0100 mesParamProdSublotStat0100 = (MESParamProdSublotStat0100)this.executor.getProcessParameterData((Class)MESParamProdSublotStat0100.class, "Overwrite target sublot status");
        if (mesParamProdSublotStat0100.isInvalidStatus()) {
            throw new MESRuntimeException(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "InvalidSublotStatus", new Object[] { mesParamProdSublotStat0100.getInvalidMeaning() }));
        }
        return (mesParamProdSublotStat0100.getSublotStatusEnum() == null) ? null : mesParamProdSublotStat0100.getSublotStatusEnum().getChoiceElement();
    }

    public String getSeparatorForSublotIdentifiers() {
        return MESConfiguration.getMESConfiguration().getString("LibraryHolder/services-inventory-impl.jar/OutputSeparator", ",", "Separator character used for Sublots identifiers");
    }

    public boolean arePackagingLevelsDefined() {
        return !this.getExecutor().getConfiguredPackingLevels().isEmpty();
    }

    public String getDefinedPackingLevels() {
        return this.hasRtPhaseData() ? this.getRtPhaseData().getDefinedPackingLevels() : "";
    }

    public String getResult() {
        return this.hasRtPhaseData() ? this.getRtPhaseData().getResult() : "";
    }

    MeasuredValue getQtyFromValueforPackagingLevel(final BigDecimal value) {
        if (this.getExecutor().isUoMConversionNeededAndPossible()) {
            return (MeasuredValue)this.getExecutor().convertSourceUoMToTargetUoM(value);
        }
        return PCContext.getFunctions().createMeasuredValue(value, (UnitOfMeasure)this.getDefaultUoM(), 0);
    }

    public MeasuredValue getSizeOfSLLevelForPackagingLevel() {
        return this.sizeOfSLLevelForPackagingLevel;
    }

    public void setSizeOfSLLevelForPackagingLevel(final MeasuredValue sizeOfSLForPackagingLevel) {
        this.sizeOfSLLevelForPackagingLevel = sizeOfSLForPackagingLevel;
    }

    public MeasuredValue getSizeOfLULevelForPackagingLevel() {
        return this.sizeOfLULevelForPackagingLevel;
    }

    public void setSizeOfLULevelForPackagingLevel(final MeasuredValue sizeOfUForPackagingLevel) {
        this.sizeOfLULevelForPackagingLevel = sizeOfUForPackagingLevel;
    }

    public List<BigDecimal> getContainedNumbersOfBasePackagingLevel() {
        return this.containedNumbersOfBasePackagingLevel;
    }

    public void setContainedNumbersOfBasePackagingLevel(final List<BigDecimal> factorsForBasePackagingLevel) {
        this.containedNumbersOfBasePackagingLevel = factorsForBasePackagingLevel;
    }

    public IMESChoiceElement getDefaultPackingChoiceElement() {
        return this.defaultPackingChoiceElement;
    }

    public void setDefaultPackingChoiceElement(final IMESChoiceElement defaultPackingChoiceElement) {
        this.defaultPackingChoiceElement = defaultPackingChoiceElement;
    }

    protected IUnitOfMeasure getDefaultUoM() {
        if (this.defaultUoM == null) {
            final Part phaseOutputMaterial = this.getPhaseOutputMaterial();
            if (phaseOutputMaterial != null) {
                this.defaultUoM = MESNamedUDAPart.getUnitOfMeasure(phaseOutputMaterial);
            }
            else if (this.getStatus().equals(IPhaseExecutor.Status.PREVIEW)) {
                this.defaultUoM = this.getSourceUoM();
            }
        }
        return this.defaultUoM;
    }

    public IUnitOfMeasure getSourceUoM() {
        return PCContext.getFunctions().getUnitOfMeasureBySymbol(MESConfiguration.getMESConfiguration().getString("Phase/ProduceMaterial/BaseUnitOfMeasure", "ea", "Default UoM for Packaging Levels"));
    }

    public IMESMaterialParameter getPhaseOutputMaterialParameter() {
        final List<IMESMaterialParameter> materialParameters = this.getExecutor().getPhase().getMaterialParameters();
        return materialParameters.isEmpty() ? null : materialParameters.get(0);
    }

    private void addNewOutputMaterial(final IMESMaterialParameter matParam) {
        (this.matOutputParameter = new ProcessedMaterialDAO0710()).setIsHeader(true);
        this.matOutputParameter.setMaterialID(matParam.getMaterialIdentifier());
        this.matOutputParameter.setDescription(matParam.getMaterialShortDescription());
        this.matOutputParameter.setDefaultUoM(MESNamedUDAPart.getUnitOfMeasure(matParam.getMaterial()));
        this.matOutputParameter.setProcessedQtyMV(MeasuredValueUtilities.createMV(BigDecimal.valueOf(0L), this.matOutputParameter.getDefaultUoM()));
    }

    protected void initMaterialsListFromPhaseData() {
        materials = new ArrayList<>();
        List<MESRtPhaseDataLCMatProduce0010> rtPhaseData = getAllRtPhaseDataWithoutSummary();
        Collections.sort(rtPhaseData, (o1, o2) -> {
            if (!o1.getIsHeader().equals(o2.getIsHeader())) {
                // put the header on top
                return o1.getIsHeader() ? -1 : 1;
            } else {
                return (o1.getSublotID() == null) ? -1 : o1.getSublotID().compareTo(o2.getSublotID());
            }
        });
        for (final MESRtPhaseDataLCMatProduce0010 mesRtPhaseDataMatProduce0710 : rtPhaseData) {
            final ProcessedMaterialDAO0710 matOutputParameter = new ProcessedMaterialDAO0710();
            matOutputParameter.setMaterialID(mesRtPhaseDataMatProduce0710.getMaterialID());
            matOutputParameter.setDescription(mesRtPhaseDataMatProduce0710.getMaterialDescription());
            matOutputParameter.setIsHeader(mesRtPhaseDataMatProduce0710.getIsHeader());
            matOutputParameter.setBatch(mesRtPhaseDataMatProduce0710.getBatchID());
            matOutputParameter.setSublot(mesRtPhaseDataMatProduce0710.getSublotID());
            matOutputParameter.setPlannedQtyMV(mesRtPhaseDataMatProduce0710.getPlannedQty());
            matOutputParameter.setSublotQtyMV(mesRtPhaseDataMatProduce0710.getSublotQty());
            matOutputParameter.setComment(mesRtPhaseDataMatProduce0710.getCommentToExecutionStr());
            if (mesRtPhaseDataMatProduce0710.getIsHeader()) {
                matOutputParameter.setProcessedQtyMV(mesRtPhaseDataMatProduce0710.getProducedQty());
                this.matOutputParameter = matOutputParameter;
            }
            else {
                matOutputParameter.setProcessedQtyMV(null);
            }
            this.materials.add(matOutputParameter);
        }
    }

    protected void refreshMaterialsList() {
        this.initMaterialsListFromPhaseData();
        if (this.materials.isEmpty() && this.matOutputParameter != null) {
            this.materials.add(this.matOutputParameter);
            IMeasuredValue addMV = null;
            if (this.os != null) {
                final IOrderStepOutputService orderStepOutputService = MaterialHelper0710.getOrderStepOutputService();
                this.matOutputParameter.setPlannedQtyMV(this.oso.getPlannedQuantity());
                this.matOutputParameter.setComment(MESNamedUDAOrderStepOutput.getComment(this.oso));
                for (final Sublot sublot : orderStepOutputService.getProducedSublotsRefreshed(this.oso, false, true)) {
                    this.matOutputParameter.setBatch(sublot.getBatch().getUniqueName());
                    addMV = MaterialHelper0710.addMV(addMV, MESNamedUDASublot.getInitialWeight(sublot));
                }
            }
            Object processedQtyMV;
            if (addMV == null && this.matOutputParameter.getPlannedQtyMV() != null) {
                processedQtyMV = MeasuredValueUtilities.createMV(BigDecimal.valueOf(0L), this.matOutputParameter.getPlannedQtyMV().getUnitOfMeasure());
            }
            else {
                processedQtyMV = this.convertMeasuredValueToUoMOfPlannedQuantity(addMV);
            }
            this.matOutputParameter.setProcessedQtyMV((IMeasuredValue)processedQtyMV);
        }
    }

    private IUnitOfMeasure getUoMOfPlannedQuantity(final ProcessedMaterialDAO0710 materialWithTargetUoM) {
        final IMeasuredValue plannedQtyMV = materialWithTargetUoM.getPlannedQtyMV();
        return (plannedQtyMV == null) ? null : plannedQtyMV.getUnitOfMeasure();
    }

    public IUnitOfMeasure getUoMOfPlannedOrProcessedQuantity() {
        if (this.matOutputParameter == null) {
            return null;
        }
        IUnitOfMeasure uoMOfPlannedQuantity = this.getUoMOfPlannedQuantity(this.matOutputParameter);
        if (uoMOfPlannedQuantity == null) {
            final IMeasuredValue processedQtyMV = this.matOutputParameter.getProcessedQtyMV();
            uoMOfPlannedQuantity = ((processedQtyMV == null) ? null : processedQtyMV.getUnitOfMeasure());
        }
        return uoMOfPlannedQuantity;
    }

    public IMeasuredValue convertMeasuredValueToUoMOfPlannedQuantity(final IMeasuredValue valueToConvert) {
        return this.convertMeasuredValueToUoMForTargetMaterial(valueToConvert, this.getUoMOfPlannedOrProcessedQuantity());
    }

    private IMeasuredValue convertMeasuredValueToUoMForTargetMaterial(final IMeasuredValue valueToConvert, final IUnitOfMeasure targetUoM) {
        final Part phaseOutputMaterial = this.getPhaseOutputMaterial();
        IMeasuredValue convertMeasuredValueToUoM = valueToConvert;
        try {
            convertMeasuredValueToUoM = MaterialHelper0710.convertMeasuredValueToUoM(phaseOutputMaterial, valueToConvert, targetUoM);
        }
        catch (MESIncompatibleUoMException ex) {
            RtPhaseModelLCMatProduce0010.LOGGER.error("UoM Conversion failed (" + convertMeasuredValueToUoM + " to " + targetUoM + ")", (Throwable)ex);
        }
        return convertMeasuredValueToUoM;
    }

    public List<AbstractProcMaterialDAO0710> getMaterialList() {
        return this.materials;
    }

    private void addPhaseDataForSublotsAndUpdateHeader(final String materialID, final List<Sublot> sublots, final IMeasuredValue qtyPerSublot, final boolean mayAddSummaryOfAllProducedSublots) {
        final List<MESRtPhaseDataLCMatProduce0010> allRtPhaseDataWithoutSummary = this.getAllRtPhaseDataWithoutSummary();
        MESGeneratedRtPhaseDataLCMatProduce0010 mesGeneratedRtPhaseDataMatProduce0710 = null;
        for (final MESRtPhaseDataLCMatProduce0010 mesRtPhaseDataMatProduce0710 : allRtPhaseDataWithoutSummary) {
            if (mesRtPhaseDataMatProduce0710.getIsHeader() && mesRtPhaseDataMatProduce0710.getMaterialID().equals(materialID)) {
                mesGeneratedRtPhaseDataMatProduce0710 = mesRtPhaseDataMatProduce0710;
                break;
            }
        }
        if (mesGeneratedRtPhaseDataMatProduce0710 == null) {
            throw new MESRuntimeException("The specified material ID cannot be found in the phase data");
        }
        MeasuredValue valueToConvert = mesGeneratedRtPhaseDataMatProduce0710.getProducedQty();
        final IMeasuredValueConverter measuredValueConverter = sublots.isEmpty() ? null : PartRelatedMeasuredValueUtilities.getMeasuredValueConverterForPart(sublots.get(0).getPart());
        for (final Sublot sublot : sublots) {
            final MESRtPhaseDataLCMatProduce0010 addRtPhaseData = this.addRtPhaseData();
            addRtPhaseData.setMaterialID(materialID);
            addRtPhaseData.setIsHeader(false);
            addRtPhaseData.setBatchID(sublot.getBatch().getUniqueName());
            addRtPhaseData.setSublotID(sublot.getUniqueName());
            addRtPhaseData.setProducedQty(MESNamedUDASublot.getInitialWeight(sublot));
            addRtPhaseData.setSublotQty((MeasuredValue)qtyPerSublot);
            addRtPhaseData.setDefinedPackingLevels(this.definedPackingLevels);
            addRtPhaseData.setPackingLevelNames(this.definedPackingLevelNames);
            addRtPhaseData.setSublotLevelQty(this.sizeOfSLLevelForPackagingLevel);
            addRtPhaseData.setLogisticUnitLevelQty(this.sizeOfLULevelForPackagingLevel);
            addRtPhaseData.setResult(this.phaseResult.getKey());
            addRtPhaseData.setIsSummaryData(false);
            this.generatedSublotsList.add(sublot.getUniqueName());
            try {
                valueToConvert = MeasuredValueUtilities.addArgsOptional(valueToConvert, MESNamedUDASublot.getInitialWeight(sublot), measuredValueConverter);
            }
            catch (MESIncompatibleUoMException ex) {
                throw new MESRuntimeException(ex);
            }
            if (mesGeneratedRtPhaseDataMatProduce0710.getBatchID() == null) {
                mesGeneratedRtPhaseDataMatProduce0710.setBatchID(sublot.getBatch().getUniqueName());
            }
        }
        mesGeneratedRtPhaseDataMatProduce0710.setProducedQty((MeasuredValue)this.convertMeasuredValueToUoMOfPlannedQuantity(valueToConvert));
        if (mayAddSummaryOfAllProducedSublots) {
            this.addPhaseDataForSummaryInDonePhase();
        }
    }

    protected void setPhaseDataForSublotsAndHeader(final String materialID, final List<Sublot> sublots, final IMeasuredValue qtyPerSublot, final boolean mayAddSummaryData) {
        if (this.addPhaseDataForHeader()) {
            this.saveRtPhase();
        }
        this.addPhaseDataForSublotsAndUpdateHeader(materialID, sublots, qtyPerSublot, mayAddSummaryData);
        this.saveRtPhase();
    }

    void addPhaseDataNoSublotsProduced() {
        this.addPhaseDataForHeader();
        if (this.isPhaseResultDone() && !this.isOSOCompleted()) {
            this.addPhaseDataForSummaryInDonePhase();
        }
    }

    protected boolean addPhaseDataForHeader() {
        if (this.getRtPhaseData() == null) {
            final MESRtPhaseDataLCMatProduce0010 addRtPhaseData = this.addRtPhaseData();
            addRtPhaseData.setMaterialID(this.matOutputParameter.getMaterialID());
            addRtPhaseData.setIsHeader(true);
            addRtPhaseData.setMaterialDescription((this.matOutputParameter.getDescription() == null) ? " " : this.matOutputParameter.getDescription());
            addRtPhaseData.setPlannedQty((MeasuredValue)this.matOutputParameter.getPlannedQtyMV());
            addRtPhaseData.setProducedQty((MeasuredValue)this.matOutputParameter.getProcessedQtyMV());
            addRtPhaseData.setDefinedPackingLevels(this.definedPackingLevels);
            addRtPhaseData.setPackingLevelNames(this.definedPackingLevelNames);
            addRtPhaseData.setSublotLevelQty(this.sizeOfSLLevelForPackagingLevel);
            addRtPhaseData.setLogisticUnitLevelQty(this.sizeOfLULevelForPackagingLevel);
            addRtPhaseData.setResult(this.phaseResult.getKey());
            addRtPhaseData.setIsSummaryData(false);
            if (!StringUtils.isEmpty(this.matOutputParameter.getComment())) {
                addRtPhaseData.setCommentToExecution(this.matOutputParameter.getComment().getBytes(Charset.defaultCharset()));
            }
            addRtPhaseData.setBatchID(this.matOutputParameter.getBatch());
            return true;
        }
        return false;
    }

    protected void addPhaseDataForSummaryInDonePhase() {
        if (this.isPhaseResultDone() && !this.sublotProductionStopped) {
            for (final Sublot sublot : MaterialHelper0710.getOrderStepOutputService().getProducedSublotsRefreshed(this.oso, false, true)) {
                final MESRtPhaseDataLCMatProduce0010 addRtPhaseData = this.addRtPhaseData();
                addRtPhaseData.setMaterialID(this.matOutputParameter.getMaterialID());
                addRtPhaseData.setIsHeader(false);
                addRtPhaseData.setBatchID(sublot.getBatch().getUniqueName());
                addRtPhaseData.setSublotID(sublot.getUniqueName());
                addRtPhaseData.setProducedQty(MESNamedUDASublot.getInitialWeight(sublot));
                addRtPhaseData.setSublotQty(MESNamedUDASublot.getInitialWeight(sublot));
                addRtPhaseData.setResult(this.phaseResult.getKey());
                addRtPhaseData.setIsSummaryData(true);
                addRtPhaseData.setDefinedPackingLevels(this.definedPackingLevels);
                addRtPhaseData.setSublotLevelQty(this.sizeOfSLLevelForPackagingLevel);
            }
        }
    }

    protected final MESRtPhaseDataLCMatProduce0010 addRtPhaseData() {
        return (MESRtPhaseDataLCMatProduce0010)this.getRtPhase().addRtPhaseData();
    }

    private void saveRtPhase() {
        try {
            this.getRtPhase().Save(null, null, PCContext.getDefaultAccessPrivilege());
        }
        catch (DatasweepException ex) {
            throw new MESRuntimeException(ex);
        }
    }

    public final MESRtPhaseDataLCMatProduce0010 getRtPhaseData() {
        return (MESRtPhaseDataLCMatProduce0010)this.getRtPhase().getRtPhaseData();
    }

    public final MESRtPhaseDataLCMatProduce0010 getRtPhaseDataWithoutSummary() {
        final List<MESRtPhaseDataLCMatProduce0010> allRtPhaseDataWithoutSummary = this.getAllRtPhaseDataWithoutSummary();
        if (!allRtPhaseDataWithoutSummary.isEmpty()) {
            return allRtPhaseDataWithoutSummary.get(0);
        }
        return null;
    }

    protected final List<MESRtPhaseDataLCMatProduce0010> getAllRtPhaseDataWithoutSummary() {
        final ArrayList<MESRtPhaseDataLCMatProduce0010> list = new ArrayList<MESRtPhaseDataLCMatProduce0010>();
        final List<MESRtPhaseDataLCMatProduce0010> allRtPhaseData = this.getAllRtPhaseData();
        if (allRtPhaseData != null) {
            for (final MESRtPhaseDataLCMatProduce0010 mesRtPhaseDataMatProduce0710 : allRtPhaseData) {
                final Boolean isSummaryData = mesRtPhaseDataMatProduce0710.getIsSummaryData();
                if (isSummaryData == null || isSummaryData.equals(Boolean.FALSE)) {
                    list.add(mesRtPhaseDataMatProduce0710);
                }
            }
        }
        return list;
    }

    public void setPhaseOutputData() {
        final MESRtPhaseOutputLCMatProduce0010 mesRtPhaseOutputMatProduce0710 = this.getRtPhaseOutput();
        mesRtPhaseOutputMatProduce0710.setResult(this.phaseResult.getKey());
        mesRtPhaseOutputMatProduce0710.setLUQuantity(this.getSizeOfLULevelForPackagingLevel());
        mesRtPhaseOutputMatProduce0710.setSublotIdentifiers(String.join(this.getSeparatorForSublotIdentifiers(), this.generatedSublotsList));
    }

    public boolean isExceptionSigned() {
        final RtPhaseExecutorLCMatProduce0010.SystemTriggeredExceptions[] values = RtPhaseExecutorLCMatProduce0010.SystemTriggeredExceptions.values();
        for (int length = values.length, i = 0; i < length; ++i) {
            if (this.executor.isExceptionSigned(values[i].toString())) {
                return true;
            }
        }
        return false;
    }

    public boolean isExceptionRequested() {
        final RtPhaseExecutorLCMatProduce0010.SystemTriggeredExceptions[] values = RtPhaseExecutorLCMatProduce0010.SystemTriggeredExceptions.values();
        for (int length = values.length, i = 0; i < length; ++i) {
            if (this.executor.isExceptionRequested(values[i].toString())) {
                return true;
            }
        }
        return false;
    }

    protected OrderStepOutput getOSO() {
        return this.oso;
    }

    protected void initOSO() {
        if (this.os == null) {
            return;
        }
        final ArrayList<OrderStepOutput> possibleMatches = new ArrayList<OrderStepOutput>();
        for (Object ooso : os.getOrderStepOutputItems()) {
            OrderStepOutput anOSO = (OrderStepOutput) ooso;
            Long matParamKeyForOSO = MESNamedUDAOrderStepOutput.getMaterialParameterKey(anOSO);
            if (matParamKeyForOSO != null) {
                // if the OSO contains the key of the corresponding material parameter: use it to determine whether
                // it belongs to this phase
                if (getPhaseOutputMaterialParameter().getKey() == matParamKeyForOSO) {
                    // we found the perfect match: the material parameter is specifically referenced; no need to
                    // search any more
                    oso = anOSO;
                    osoKey = oso.getKey();
                    return;
                }
            } else {
                if (matOutputParameter.getMaterialID().equals(anOSO.getPart().getPartNumber())) {
                    // the OSO is for the same material: if we cannot find anything better, we will use it
                    possibleMatches.add(anOSO);
                }
            }
        }
        if (possibleMatches.size() != 1) {
            throw new MESRuntimeException("Could not find a unique OSO corresponding to that phase, instead found " + possibleMatches.size());
        }
        this.oso = possibleMatches.get(0);
        this.osoKey = this.oso.getKey();
    }

    public void refreshOSAndDependents(final boolean doRefreshMaterialsList) {
        try {
            this.os.refresh();
        }
        catch (DatasweepException ex) {
            throw new MESRuntimeException((Throwable)ex);
        }
        this.oso = this.os.getOrderStepOutput(this.osoKey);
        if (doRefreshMaterialsList) {
            this.refreshMaterialsList();
        }
    }

    public boolean isOSOCompleted() {
        return OSOPositionStatus.COMPLETED_STATES.contains(OSOPositionStatus.get(this.oso));
    }

    public void setCurrentOSOClompletedStatus() {
        this.osoCompletedAtStart = this.isOSOCompleted();
    }

    public boolean getRememberedOSCompletedStatus() {
        return this.osoCompletedAtStart;
    }

    protected void initBatch() {
        if (this.batch == null) {
            final Batch batchProducedByOrder = this.getBatchProducedByOrder();
            if (batchProducedByOrder != null && batchProducedByOrder.getPart().equals(this.oso.getPart())) {
                this.batch = batchProducedByOrder;
                return;
            }
            final List<Sublot> producedSublotsRefreshed = MaterialHelper0710.getOrderStepOutputService().getProducedSublotsRefreshed(this.oso, false, true);
            if (producedSublotsRefreshed != null && !producedSublotsRefreshed.isEmpty()) {
                final Sublot sublot = producedSublotsRefreshed.get(0);
                if (sublot.getPart().equals(this.oso.getPart())) {
                    this.batch = sublot.getBatch();
                }
            }
        }
    }

    protected void setInitialWeight(final Sublot targetSublot) {
        MESNamedUDASublot.setInitialWeight(targetSublot, targetSublot.getQuantity());
        try {
            targetSublot.Save(null, null, PCContext.getDefaultAccessPrivilege());
        }
        catch (DatasweepException ex) {
            throw new MESRuntimeException(ex);
        }
    }

    private Batch getBatchProducedByOrder() {
        final ProcessOrderItem orderForOrderStepOutput = MaterialHelper0710.getMESOrderService().getOrderForOrderStepOutput(this.oso);
        Batch batch = null;
        final Vector associatedOrderStepInputs = this.oso.getAssociatedOrderStepInputs();
        if (associatedOrderStepInputs == null || associatedOrderStepInputs.isEmpty()) {
            batch = MESNamedUDAProcessOrderItem.getBatch(orderForOrderStepOutput);
        }
        return batch;
    }

    protected String[] loadLabelIDAndSublotQty(final String sublotID) {
        final ISublotService sublotService = (ISublotService)ServiceFactory.getService((Class)ISublotService.class);
        this.initBatch();
        if (this.batch == null) {
            return new String[0];
        }
        final Sublot loadSublot = sublotService.loadSublot(this.batch.getUniqueName(), sublotID, true);
        if (loadSublot == null) {
            return new String[0];
        }
        return new String[] { sublotService.getSublotLabelID(loadSublot), loadSublot.getQuantity().toString() };
    }

    protected void saveSublotReprintData(final Report report, final Sublot sublot) {
        MESNamedUDASublot.setLabelID(sublot, report.getOriginalGuid());
        final Response save = sublot.save();
        if (save.isError()) {
            ProductPhaseSwingHelper.showErrorDlg(this.responseToString(save));
        }
    }

    private String responseToString(final Response response) {
        final StringBuilder sb = new StringBuilder();
        for (final Error error : response.getErrors()) {
            sb.append(StringConstants.LINE_BREAK);
            sb.append(error.getLocalizedErrorMessage());
        }
        return sb.toString();
    }

    public Limits getLimits() {
        if (this.limits == null) {
            this.calculateLimits();
        }
        return this.limits;
    }

    private void calculateLimits() {
        if (this.matOutputParameter == null) {
            this.limits = new Limits(null, null);
            return;
        }
        try {
            final IMeasuredValue plannedQtyMV = this.matOutputParameter.getPlannedQtyMV();
            final IMeasuredValue[] tolerances = this.getTolerances(plannedQtyMV);
            final IMeasuredValue measuredValue = tolerances[0];
            final IMeasuredValue measuredValue2 = tolerances[1];
            this.limits = new Limits((MeasuredValue)((measuredValue != null) ? plannedQtyMV.subtract(measuredValue) : plannedQtyMV), (MeasuredValue)((measuredValue2 != null) ? plannedQtyMV.add(measuredValue2) : plannedQtyMV));
        }
        catch (Exception ex) {
            throw new MESRuntimeException("Calculation of limits failed", ex);
        }
    }

    private IMeasuredValue[] getTolerances(final IMeasuredValue plannedQtyMV) {
        final IMESMaterialParameter phaseOutputMaterialParameter = this.getPhaseOutputMaterialParameter();
        final Part phaseOutputMaterial = this.getPhaseOutputMaterial();
        final MeasuredValue upperToleranceAbsolute = phaseOutputMaterialParameter.getUpperToleranceAbsolute();
        final MeasuredValue lowerToleranceAbsolute = phaseOutputMaterialParameter.getLowerToleranceAbsolute();
        final MeasuredValue upperToleranceRelative = phaseOutputMaterialParameter.getUpperToleranceRelative();
        final MeasuredValue lowerToleranceRelative = phaseOutputMaterialParameter.getLowerToleranceRelative();
        IMeasuredValue[] calcTolerances;
        try {
            calcTolerances = OrderUtils.calcTolerances(phaseOutputMaterial, plannedQtyMV, new IMeasuredValue[] { lowerToleranceRelative, upperToleranceRelative }, new IMeasuredValue[] { lowerToleranceAbsolute, upperToleranceAbsolute });
        }
        catch (Exception ex) {
            throw new MESRuntimeException("Calculation of tolerances failed", ex);
        }
        return calcTolerances;
    }

    public Part getPhaseOutputMaterial() {
        Part material = null;
        final IMESMaterialParameter phaseOutputMaterialParameter = this.getPhaseOutputMaterialParameter();
        if (phaseOutputMaterialParameter != null && phaseOutputMaterialParameter.isMFCRelevant()) {
            material = phaseOutputMaterialParameter.getMaterial();
        }
        return material;
    }

    public boolean lastWarehouseCallFailed() {
        return this.getLastWarehouseExceptionLocalizedMessage() != null;
    }

    private String getLastWarehouseExceptionLocalizedMessage() {
        if (this.getStatus() == IPhaseExecutor.Status.ACTIVE) {
            synchronized (WarehouseInteractionPropertyEnum0710.LAST_WAREHOUSE_EXCEPTION_LOCALIZED_MESSAGE) {
                return WarehouseInteractionPropertyEnum0710.LAST_WAREHOUSE_EXCEPTION_LOCALIZED_MESSAGE.getValue(this);
            }
        }
        if (this.getStatus() == IPhaseExecutor.Status.COMPLETED) {
            return this.lastWarehouseExceptionLocalizedMessage;
        }
        throw new IllegalStateException("getLastWarehouseExceptionLocalizedMessage is only available in status ACTIVE or COMPLETED.");
    }

    private String getLastWarehouseErrorMsgId() {
        if (this.getStatus() == IPhaseExecutor.Status.ACTIVE) {
            synchronized (WarehouseInteractionPropertyEnum0710.LAST_WAREHOUSE_ERROR_MESSAGE_ID) {
                return WarehouseInteractionPropertyEnum0710.LAST_WAREHOUSE_ERROR_MESSAGE_ID.getValue(this);
            }
        }
        if (this.getStatus() == IPhaseExecutor.Status.COMPLETED) {
            return this.lastWarehouseErrorMessageId;
        }
        throw new IllegalStateException("getLastWarehouseExceptionLocalizedMessage is only available in status ACTIVE or COMPLETED.");
    }

    public void setLastWarehouseExceptionAndError(final WarehouseRuntimeException lastWarehouseExc, final String lastWarehouseErrorMsgId) {
        final String lastWarehouseExceptionLocalizedMessage = (lastWarehouseExc != null) ? lastWarehouseExc.getLocalizedMessage() : null;
        if (this.getStatus() == IPhaseExecutor.Status.ACTIVE) {
            synchronized (WarehouseInteractionPropertyEnum0710.LAST_WAREHOUSE_EXCEPTION_LOCALIZED_MESSAGE) {
                WarehouseInteractionPropertyEnum0710.LAST_WAREHOUSE_EXCEPTION_LOCALIZED_MESSAGE.setValue(this, (Object)lastWarehouseExceptionLocalizedMessage);
            }
            synchronized (WarehouseInteractionPropertyEnum0710.LAST_WAREHOUSE_ERROR_MESSAGE_ID) {
                WarehouseInteractionPropertyEnum0710.LAST_WAREHOUSE_ERROR_MESSAGE_ID.setValue(this, (Object)lastWarehouseErrorMsgId);
            }
            this.getExecutor().saveRtPhaseContext();
        }
        else if (this.getStatus() == IPhaseExecutor.Status.COMPLETED) {
            this.lastWarehouseExceptionLocalizedMessage = lastWarehouseExceptionLocalizedMessage;
            this.lastWarehouseErrorMessageId = lastWarehouseErrorMsgId;
        }
    }

    public void resetLastWarehouseExceptionAndError() {
        this.setLastWarehouseExceptionAndError(null, null);
    }

    public void fillRecordWarehouseErrorException(final String msgPack, final List<String> msgArgs) {
        AbstractPhaseExceptionView0200.fillParameterizedException("Warehouse error", "RECORD_WAREHOUSE_ERROR_CHECK_KEY", this.getExecutor(), this.createWarehouseErrorMessage(msgPack, msgArgs, false));
    }

    public String createWarehouseErrorMessage(final String msgPack, final List<String> msgArgs, final boolean appendInfoMessage) {
        final ArrayList<String> list = new ArrayList<>();
        if (msgArgs != null) {
            list.addAll(msgArgs);
        }
        final String lastWarehouseErrorMsgId = this.getLastWarehouseErrorMsgId();
        final String lastWarehouseExceptionLocalizedMessage = this.getLastWarehouseExceptionLocalizedMessage();
        list.add((lastWarehouseExceptionLocalizedMessage != null) ? lastWarehouseExceptionLocalizedMessage : "");
        final String localizedMessage = I18nMessageUtility.getLocalizedMessage(StringUtilsEx.isEmpty(msgPack) ? "PhaseProductMaterial0710" : msgPack, lastWarehouseErrorMsgId, list.toArray(new String[list.size()]));
        if (appendInfoMessage) {
            return localizedMessage + StringConstants.LINE_BREAK + I18nMessageUtility.getLocalizedMessage("PhaseProductMaterial0710", "SignWarehouseError_Error");
        }
        return localizedMessage;
    }

    public IMESExceptionRecord recordSystemTriggeredWarehouseInteractionException(final String exceptionMessage) {
        return this.recordSystemTriggeredException((MESParamExceptionDef0300)this.getExecutor().getProcessParameterData((Class)MESParamExceptionDef0300.class, "Warehouse error"), exceptionMessage);
    }

    private IMESExceptionRecord recordSystemTriggeredException(final MESParamExceptionDef0300 excDef, final String exceptionMessage) {
        return ((IS88ExceptionRecordingService)ServiceFactory.getService((Class)IS88ExceptionRecordingService.class)).recordSystemTriggeredExceptionWithoutSignature(this.getRtPhase(), IMESExceptionRecord.RiskClass.valueOf(excDef.getRiskAssessment()), AbstractPhaseExceptionView0200.buildExceptionText(excDef, exceptionMessage));
    }

    public boolean isWarehauseApplicationAvailable() {
        return ((IOutboundWarehouseService)ServiceFactory.getService((Class)IOutboundWarehouseService.class)).isWarehouseSystemConnected();
    }

    public boolean isBlockedByWarehouseError() {
        return this.lastWarehouseCallFailed() && !this.getExecutor().isExceptionSigned("RECORD_WAREHOUSE_ERROR_CHECK_KEY");
    }

    public RtPhaseContext getContext() {
        return this.getExecutor().getRtPhaseContext();
    }

    public boolean isPhaseResultDone() {
        return PhaseResult0710.DONE.equals(this.phaseResult);
    }

    public PhaseResult0710 getPhaseResult() {
        return this.phaseResult;
    }

    public void setPhaseResult(final PhaseResult0710 res) {
        this.phaseResult = res;
    }

    private void initPhaseResult() {
        final MESRtPhaseOutputLCMatProduce0010 mesRtPhaseOutputMatProduce0710 = this.getRtPhaseOutput();
        if (mesRtPhaseOutputMatProduce0710 != null && StringUtils.isNotEmpty(mesRtPhaseOutputMatProduce0710.getResult())) {
            this.phaseResult = PhaseResult0710.valueOf(mesRtPhaseOutputMatProduce0710.getResult());
        }
        else {
            this.phaseResult = PhaseResult0710.CONTINUE;
        }
    }

    public boolean emptyFieldsAllowed() {
        return this.phaseResult.equals(PhaseResult0710.DONE) || this.phaseResult.equals(PhaseResult0710.STOP);
    }

    public boolean isSublotProductionStopped() {
        return this.sublotProductionStopped;
    }

    public void setSublotProductionStopped(final boolean isStopped) {
        this.sublotProductionStopped = isStopped;
    }

    public IMESChoiceElement getTargetSublotStatus() {
        if (this.isSublotStatusOverwriteEnabled()) {
            return this.getSublotStatusFromParameter();
        }
        return (this.oso == null) ? null : MESNamedUDAOrderStepOutput.getSublotQualityStatus(this.oso);
    }

    public String getDefinedPackingLevelNames() {
        return this.definedPackingLevelNames;
    }

    public void setDefinedPackingLevelNames(final String definedPackingLevelNames) {
        this.definedPackingLevelNames = definedPackingLevelNames;
    }

    public void setDefinedPackingLevels(final String definedPackingLevels) {
        this.definedPackingLevels = definedPackingLevels;
    }

    public boolean checkAndInitLocation() {
        final Location locationOfWorkCenter = this.getLocationOfWorkCenter();
        if (locationOfWorkCenter == null) {
            return false;
        }
        final Vector locations = locationOfWorkCenter.getLocations();
        this.location = ((locations != null && !locations.isEmpty()) ? ((Location)locations.get(0)) : null);
        return true;
    }

    private Location getLocationOfWorkCenter() {
        final WorkCenter currentWorkCenter = PCContext.getFunctions().getCurrentWorkCenter();
        final Location location = currentWorkCenter.getLocation();
        if (location == null) {
            ProductPhaseSwingHelper.showErrorDlg(I18nMessageUtility.getLocalizedMessage("ui_Inventory", "NoStorageAreaForWorkcenterConfigured_ErrorMsg", new Object[] { ExtendedBeanUtility.getDisplayName(currentWorkCenter) }));
            return null;
        }
        return location;
    }

    public Location getLocation() {
        return this.location;
    }

    static {
        LOGGER = LogFactory.getLog((Class)RtPhaseModelLCMatProduce0010.class);
    }
}
