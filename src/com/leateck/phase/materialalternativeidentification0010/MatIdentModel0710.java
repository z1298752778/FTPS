// CHECKSTYLE:FileLength:off (Reason: main model)
package com.leateck.phase.materialalternativeidentification0010;

import com.datasweep.compatibility.client.*;
import com.datasweep.plantops.common.measuredvalue.IMeasuredValue;
import com.datasweep.plantops.common.measuredvalue.IUnitOfMeasure;
import com.jgoodies.common.base.Strings;
import com.leateck.parameter.sequencerule0010.MESParamSeqRule0100;
import com.rockwell.mes.apps.ebr.ifc.phase.IPhaseExecutor.Status;
import com.rockwell.mes.commons.base.ifc.exceptions.MESException;
import com.rockwell.mes.commons.base.ifc.exceptions.MESIncompatibleUoMException;
import com.rockwell.mes.commons.base.ifc.exceptions.MESRuntimeException;
import com.rockwell.mes.commons.base.ifc.functional.IMeasuredValueConverter;
import com.rockwell.mes.commons.base.ifc.functional.MeasuredValueUtilities;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.nameduda.MESNamedUDAOrderStepInput;
import com.rockwell.mes.commons.base.ifc.nameduda.MESNamedUDAPart;
import com.rockwell.mes.commons.base.ifc.nameduda.MESNamedUDASublot;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.commons.base.ifc.utility.StringConstants;
import com.rockwell.mes.commons.base.ifc.utility.StringUtilsEx;
import com.rockwell.mes.commons.parameter.bool.MESParamBoolean0100;
import com.rockwell.mes.commons.shared.phase.mvc.AbstractPhaseExecutor0200;
import com.rockwell.mes.parameter.product.consumpttype.MESParamConsumptType0200;
import com.rockwell.mes.parameter.product.exceptiondef.MESParamExceptionDef0200;
import com.rockwell.mes.parameter.product.excptenabledef.MESParamExcptEnableDef0200;
import com.rockwell.mes.parameter.product.instruction.MESParamInstruction0200;
import com.rockwell.mes.parameter.product.loopconfig.LoopConfigurationType0100;
import com.rockwell.mes.parameter.product.loopconfig.MESParamLoopConfig0100;
import com.rockwell.mes.services.order.ifc.EnumOrderStepInputStatus;
import com.rockwell.mes.services.s88.ifc.recipe.IMESMaterialParameter;
import com.rockwell.mes.services.s88.ifc.recipe.PlannedQuantityMode;
import com.rockwell.mes.services.wd.ifc.IBasicOrderStepInputService;
import com.rockwell.mes.services.wd.ifc.MESAllMasterOSIs;
import com.rockwell.mes.services.wd.ifc.OSIPositionStatus;
import com.rockwell.mes.services.wip.ifc.*;
import com.rockwell.mes.services.wip.ifc.IOrderStepExecutionService.QuantityRangeCondition;
import com.rockwell.mes.shared.product.material.*;
import com.rockwell.mes.shared.product.material.util.MaterialHelper0710;
import com.rockwell.mes.shared0400.product.util.ParamClassConstants0400;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;


/**
 * The model class of the material accounting phase.
 * <p>
 *
 * @author ikoleva, (c) Copyright 2020 Rockwell Automation Technologies, Inc. All Rights Reserved.
 */
@SuppressWarnings({"PMD.ExcessiveImports", "PMD.CouplingBetweenObjects"})
public class MatIdentModel0710 extends MaterialModel0710<IdentifiedMaterialDAO0710, MESRtPhaseDataMatAlterIdent0010, MESRtPhaseOutputMatAlterIdent0010> {

    private static final Log LOGGER = LogFactory.getLog(MatIdentModel0710.class);

    /**
     * The list of all materials shown in the grid inclusive materials for each identified sublot. The header material
     * object is also in matInputList. matInputList contains only the header materials.
     */
    private List<AbstractProcMaterialDAO0710> materials;

    private boolean anIdentificationIsDone = false;

    /**
     * all by this rtphase instance identified sublots - uniquename as in phase data
     */
    private final List<String> locallyIdentifiedSublots = new ArrayList<>();

    private PhaseResult0710 phaseResult;

    // nothing allowed than confirm
    private boolean phaseIsBlocked = false;

    /**
     * OriginName of OSI to condition
     */
    private final Map<String, QuantityRangeCondition> quantityRangeConditions = new HashMap<>();

    /**
     * LC
     * @return
     */
    public MESParamExcptEnableDef0200 getLocationViolation() {
        return executor.getProcessParameterData(MESParamExcptEnableDef0200.class, RtPhaseExecutorMatAlterIdent0010.KEY_LOCATION_VIOLATION_EXCEPTION);
    }

    public MESParamExcptEnableDef0200 getOpenExpirationViolation() {
        return executor.getProcessParameterData(MESParamExcptEnableDef0200.class, RtPhaseExecutorMatAlterIdent0010.KEY_OPEN_EXPIRATION_VIOLATION_EXCEPTION);
    }

    public MESParamExcptEnableDef0200 getSequenceViolationException() {
        return executor.getProcessParameterData(MESParamExcptEnableDef0200.class, RtPhaseExecutorMatAlterIdent0010.KEY_SEQUENCE_VIOLATION_EXCEPTION);
    }



    public boolean getAdditionalBarcodeSupport() {
        return executor.getProcessParameterData(MESParamBoolean0100.class, "Additional Barcode Support").getEnabled();
    }

    public boolean getBatchExclusiveUse() {
        return executor.getProcessParameterData(MESParamBoolean0100.class, "Batch Exclusive Use").getEnabled();
    }

    public String getSequenceRule() {
        //MESParamInstruction0300 param = executor.getProcessParameterData(MESParamInstruction0300.class, "Sequence Rule");
        MESParamSeqRule0100 param = executor.getProcessParameterData(MESParamSeqRule0100.class, "Sequence Rule");

        System.out.println("SequenceRule\t" + param.getRule());
        return (param == null) ? StringUtils.EMPTY : param.getRule();
    }
    /**
     * @author Yonghao Xu
     *
     * SCL
     */

    /**
     * Simple class containing an OSI and its allcated batches
     * <p>
     *
     * @author ikoleva, (c) Copyright 2020 Rockwell Automation Technologies, Inc. All Rights Reserved.
     */
    static class MasterOSIWithBatchAllocations {
        private final OrderStepInput osi;

        private final List<Batch> allocatedBatches;

        private MasterOSIWithBatchAllocations(OrderStepInput argOsi) {
            osi = argOsi;
            allocatedBatches = new ArrayList();
        }

        /**
         * @return the order step input
         */
        public OrderStepInput getOsi() {
            return osi;
        }

        /**
         * @return all batches allocated to this OSI
         */
        public List<Batch> getAllocatedBatches() {
            return allocatedBatches;
        }
    }

    private Map<String, List<MasterOSIWithBatchAllocations>> masterOSIsWithBatchAllocations;

    //dustin：替代料关系Map
    private Map<IMESMaterialParameter, List<IMESMaterialParameter>> alterMaterialParameters;

    /**
     * @param theExecutor the phase executor
     */
    protected MatIdentModel0710(AbstractPhaseExecutor0200 theExecutor) {
        super(theExecutor);
    }

    /**
     * @return Returns the executor.
     */
    public RtPhaseExecutorMatAlterIdent0010 getExecutor() {
        return (RtPhaseExecutorMatAlterIdent0010) executor;
    }

    /**
     * @param message message
     */
    public void logPhaseData(String message) {
        if (!LOGGER.isDebugEnabled()) {
            return;
        }
        List<MESRtPhaseDataMatAlterIdent0010> allRtPhaseData = getAllRtPhaseData();
        if (allRtPhaseData == null) {
            LOGGER.debug("RtPhaseData not yet available at " + message);
            return;
        }
        LOGGER.debug("RtPhaseData " + message);

        for (MESRtPhaseDataMatAlterIdent0010 rtPhaseData : allRtPhaseData) {
            LOGGER.debug(rtPhaseData);
        }
    }

    private void logMaterial(String message) {
        if (!LOGGER.isDebugEnabled()) {
            return;
        } else if (materials == null) {
            LOGGER.debug("Material not yet available at " + message);
            return;
        }
        LOGGER.debug("Material " + message);
        for (AbstractProcMaterialDAO0710 material : materials) {
            LOGGER.debug(material);
        }
    }

    /**
     * @return key=master OSI for this phase, value=all allocated batched for this OSI
     */
    public Map<String, List<MasterOSIWithBatchAllocations>> getMasterOSIsWithBatchAllocations() {
        return masterOSIsWithBatchAllocations;
    }

    public Map<IMESMaterialParameter, List<IMESMaterialParameter>> getAlterMaterialParameters() {
        return alterMaterialParameters;
    }

    /**
     * @return list of material that should be displayed (input material parameters + identified sublots)
     */
    public List<AbstractProcMaterialDAO0710> getMaterialList() {
        return materials.stream().filter(material -> material.isHeader() || shallSublotBeenShown(material.getSublot())).collect(Collectors.toList());
    }

    private boolean shallSublotBeenShown(String uniqueSublotName) {
        return !supportLooping() || isLocallyIdentified(uniqueSublotName);
    }

    /**
     * @return the text for the navigator info: the % of identified quantity
     */
    public String getNavigatorInfoColumn() {
        return getLocalizedResult(phaseResult.getKey());
    }

    @Override
    public String getInstructionTextColumn1() {
        // TASK @ikoleva use Instruction 3.0?
        MESParamInstruction0200 param = executor.getProcessParameterData(MESParamInstruction0200.class, PARAMETER_INSTRUCTION);
        return (param == null) ? "<instruction text param is NULL>" : param.getColumn1();
    }

    @Override
    public boolean isBlockedByWarehouseError() {
        return lastWarehouseCallFailed() && !executor.isExceptionSigned(AbstractMaterialPhaseExecutor0710.WAREHOUSE_ERROR_CHECK_KEY);
    }

    /**
     * Block phase that nothing is allowed than confirm
     */
    public void setPhaseBlocked() {
        phaseIsBlocked = true;
    }

    /**
     * @return true if nothing is allowed as confirm
     */
    public boolean isPhaseBlocked() {
        return phaseIsBlocked;
    }

    /**
     * @return whether the sublots should be automatically consumed
     * 是否启用自动消耗按钮
     */
    public Boolean getAutoConsume() {
        Boolean autoConsume = executor.getProcessParameterData(MESParamConsumptType0200.class, ParamClassConstants0400.PARAMETER_INVENTORY_CORRECTION)
                .getAutoConsume();
        if (autoConsume == null) {
            autoConsume = false;
        }
        return autoConsume;
    }

    /**
     * @return the {@linkplain LoopConfigurationType0100} of the param
     */
    public LoopConfigurationType0100 getLoopConfiguration() {
        MESParamLoopConfig0100 param = executor.getProcessParameterData(MESParamLoopConfig0100.class, "Loop configuration");
        return param.getLoopConfigurationEnum();
    }

    /**
     * @return whether it is allowed to exit the loop without actually closing the positions and performing checks
     * (makes sense in an ETO)
     */
    public boolean isLoopStopEnabled() {
        MESParamBoolean0100 param = executor.getProcessParameterData(MESParamBoolean0100.class, ParamClassConstants0400.PARAMETER_ALLOW_LOOP_STOP);
        return param.getEnabled() != null && param.getEnabled();
    }

    /**
     * @return a combined comment for all material
     */
    public String getComment() {
        // combine the comments for all materials in a single field
        StringBuilder builder = new StringBuilder();

        for (IdentifiedMaterialDAO0710 material : matInputList) {
            if (!StringUtils.isEmpty(material.getComment())) {
                if (builder.length() > 0) {
                    builder.append(StringConstants.LINE_BREAK);
                }
                builder.append(I18nMessageUtility.getLocalizedMessage(MaterialModel0710.PHASE_PRODUCT_MATERIAL_MSGPACK, "CommentFormat",
                        new Object[]{material.getMaterialID(), material.getComment()}));
            }
        }

        return builder.toString();
    }

    /**
     * @return a new phase data row
     */
    public MESRtPhaseDataMatAlterIdent0010 addRtPhaseData() {
        return (MESRtPhaseDataMatAlterIdent0010) executor.getRtPhase().addRtPhaseData();
    }

    @Override
    protected void addNewInputMaterial(IMESMaterialParameter matParam) {
        IdentifiedMaterialDAO0710 material = new IdentifiedMaterialDAO0710();
        material.setIsHeader(true);
        material.setMaterialID(matParam.getMaterialIdentifier());
        material.setDescription(matParam.getMaterialShortDescription());
        material.setPlannedQtyMV(matParam.getPlannedQuantity());
        material.setDefaultUoM(getDefaultUoM(matParam));
        material.setProcessedQtyMV(MeasuredValueUtilities.createMV(BigDecimal.valueOf(0), material.getDefaultUoM()));
        material.setMfcPosition(matParam.getMFCPosition());
        matInputList.add(material);
    }

    private IUnitOfMeasure getDefaultUoM(IMESMaterialParameter matParam) {
        final MeasuredValue plannedQuantity = matParam.getPlannedQuantity();
        return (plannedQuantity == null) ? MESNamedUDAPart.getUnitOfMeasure(matParam.getMaterial()) : plannedQuantity.getUnitOfMeasure();
    }

    @Override
    protected boolean shallPlannedQtyModeInit() {
        return true;
    }

    @Override
    public boolean init() {
        initPhaseResult();
        return super.init();
    }

    @Override
    public void initMatInputList() {
        initLocalIdentifiedSublotsFromPhaseData();
        if (os == null) {
            matInputList = new ArrayList<>();
            super.initMatInputList();
            sortMaterialByMFCPos(matInputList);
        } else {
            initMatInputOSIList();
        }
    }

    /**
     * Initialize the list with OSI data
     */
    private void initMatInputOSIList() {
        initMapOfMasterOSIs();

        matInputList = new ArrayList<>();
        List<OrderStepInput> allMaterOSIs = getMasterOSIsForPhase();
        for (OrderStepInput osi : allMaterOSIs) {
            IdentifiedMaterialDAO0710 material = new IdentifiedMaterialDAO0710();
            material.setIsHeader(true);
            final Part part = osi.getPart();
            material.setMaterialID(part.getPartNumber());
            material.setDescription(MESNamedUDAPart.getShortDescription(part));
            setPlannedQtyWithLimits(material, osi);
            material.setDefaultUoM(getDefaultUoM(osi));
            material.setProcessedQtyMV(MeasuredValueUtilities.createMV(BigDecimal.valueOf(0), material.getDefaultUoM()));
            material.setOsiKey(osi.getKey());
            material.setMfcPosition(MESNamedUDAOrderStepInput.getPosition(osi));
            material.setComment(MESNamedUDAOrderStepInput.getComment(osi));
            material.setAccountedQtyMV(getConvertedTotalAccountedQty(osi));
            material.setStatusString(getDisplayStringQtyRangeConditionAndCompletedInfo(osi));
            matInputList.add(material);
        }
        initAllocBatchesSublots();
    }

    /**
     * @param osi an osi
     * @return default uom
     */
    public static IUnitOfMeasure getDefaultUoM(OrderStepInput osi) {
        return (osi.getPlannedQuantity() == null) ? MESNamedUDAPart.getUnitOfMeasure(osi.getPart()) : osi.getPlannedQuantity().getUnitOfMeasure();
    }

    private IMeasuredValue getConvertedTotalAccountedQty(OrderStepInput masterOsi) {
        final MeasuredValue totalAccountedQuantity = MESNamedUDAOrderStepInput.getTotalAccountedQuantity(masterOsi);
        IMeasuredValueConverter converter = MeasuredValueUtilities.getMVConverter(masterOsi.getPart());
        return convertMeasuredValue(totalAccountedQuantity, getDefaultUoM(masterOsi), converter);
    }

    /**
     * Initializes the list of unsplit OSI for this phase.
     */
    private void initMapOfMasterOSIs() {
        // Sequence of masterOSIsWithBatchAllocations controls sorting of the grid
        // use a linked hash map to preserve the order.
        masterOSIsWithBatchAllocations = new LinkedHashMap<>();
        alterMaterialParameters = new LinkedHashMap<>();
        MESAllMasterOSIs masterOSIs = new MESAllMasterOSIs(os);
        List<IMESMaterialParameter> allMaterialParameters = new ArrayList<>(executor.getPhase().getMaterialParameters());
        sortMaterialParamsByMFCPos(allMaterialParameters);

        for (IMESMaterialParameter matParam : allMaterialParameters) {
            Part paramMat = matParam.getMaterial();
            for (OrderStepInput osi : masterOSIs) {
                Part osiMat = osi.getPart();
                final Long matParamKeyFromOSI = MESNamedUDAOrderStepInput.getMaterialParameterKey(osi);
                if (osiMat.equals(paramMat) && (matParamKeyFromOSI == null || matParamKeyFromOSI == matParam.getKey())) {
                    if (!masterOSIsWithBatchAllocations.containsKey(osiMat.getPartNumber())) {
                        masterOSIsWithBatchAllocations.put(osiMat.getPartNumber(), new ArrayList());
                    }
                    masterOSIsWithBatchAllocations.get(osiMat.getPartNumber()).add(new MasterOSIWithBatchAllocations(osi));
                }
            }

//            //增加替代料关系
//            Boolean isMainPart = (Boolean) matParam.getATRow().getValue("SCL_isMainPart");
//            if (isMainPart != null && isMainPart) {
//                String masterReplaceGroupName = (String) matParam.getATRow().getValue("SCL_replaceGroupName");
//                if (StringUtils.isEmpty(masterReplaceGroupName)) {
//                    continue;
//                }
//                List<IMESMaterialParameter> replaceGroupNameList = allMaterialParameters.stream().filter(p -> {
//                    String replaceGroupName = (String) p.getATRow().getValue("SCL_replaceGroupName");
//                    Part replaceMaterial = p.getMaterial();
//                    return masterReplaceGroupName.equals(replaceGroupName) && !paramMat.equals(replaceMaterial);
//                }).collect(Collectors.toList());
//                alterMaterialParameters.put(matParam, replaceGroupNameList);
//                System.out.println("======");
//            }
        }

    }

    /**
     * 根据扫描的子批次物料，找到替代料主料
     *
     * @param scanPartNumber
     * @return
     */
    public Part findMainPartByScan(String scanPartNumber) {
        List<IMESMaterialParameter> materialParameters = executor.getPhase().getMaterialParameters();
        List<String> replaceGroupNames = materialParameters.stream().filter(p -> p.getMaterial().getPartNumber().equals(scanPartNumber))
                .map(p -> (String) p.getATRow().getValue("SCL_replaceGroupName")).distinct().collect(Collectors.toList());

        if (replaceGroupNames != null && replaceGroupNames.size() > 0) {
            String replaceGroupName = replaceGroupNames.get(0);

            List<IMESMaterialParameter> mainMaterialParameters = materialParameters.stream()
                    .filter(p -> {
                        String mainReplaceGroupName = (String) p.getATRow().getValue("SCL_replaceGroupName");
                        Boolean isMainPart = (Boolean) p.getATRow().getValue("SCL_isMainPart");
                        return replaceGroupName.equals(mainReplaceGroupName)
                                && (isMainPart != null && isMainPart);
                    })
                    .collect(Collectors.toList());
            if (mainMaterialParameters != null && mainMaterialParameters.size() > 0) {
                Part material = mainMaterialParameters.get(0).getMaterial();
                return material;
            }
        }
        return null;
    }


    /**
     * @param quantity  quanity
     * @param toUom     target uom
     * @param converter converter
     * @return the converted qty
     */
    public IMeasuredValue convertMeasuredValue(IMeasuredValue quantity, IUnitOfMeasure toUom, IMeasuredValueConverter converter) {

        if (quantity == null) {
            return null;
        }
        IMeasuredValue convertedValue = null;
        try {
            if (converter != null) {
                // If there is a part specific converter defined, use it to convert the identified quantity
                convertedValue = converter.convert(quantity, toUom);
            } else {
                // Otherwise, use the default measured value converter
                convertedValue = quantity.convert(toUom);
            }
        } catch (Exception e) {
            throw new MESRuntimeException(e);
        }
        return convertedValue;
    }

    @Override
    public Map<String, List<OrderStepInput>> getMasterOSIsOfMaterial() {
        Map<String, List<OrderStepInput>> map = new HashMap<>();
        for (Entry<String, List<MasterOSIWithBatchAllocations>> entry : masterOSIsWithBatchAllocations.entrySet()) {
            map.put(entry.getKey(), new ArrayList<>());
            entry.getValue().forEach(val -> map.get(entry.getKey()).add(val.getOsi()));
        }
        return map;
    }

    @Override
    public List<OrderStepInput> getMasterOSIsForPhase() {
        List<OrderStepInput> list = new ArrayList<>();
        for (Entry<String, List<MasterOSIWithBatchAllocations>> entry : masterOSIsWithBatchAllocations.entrySet()) {
            entry.getValue().forEach(val -> list.add(val.getOsi()));
        }
        return list;
    }

    /**
     * @param osi master osi or osi of position
     * @return true if the corresponding material position is completed (Position is completed when accounted or when
     * quantity reached by auto consumption
     */
    @Override
    public boolean isPositionCompleted(OrderStepInput osi) {
        OrderStepInput masterOsi = ServiceFactory.getService(IBasicOrderStepInputService.class).getMasterOSIForOSI(osi);
        // Position is completed when Identify Material makes auto consumption and quantity is reached or when
        // Account Material completes with DONE.
        // Identify Material needs in case of auto consume to differentiate whether position is completed or only master
        // osi is completed. (Master-)Osi is complete when auto consumption on this specific osi is done. The position
        // must still be open because not all sublots has been identified (and auto consumed) yet.
        OSIPositionStatus osiPositionStatus = OSIPositionStatus.get(masterOsi);
        boolean osiFinished = OSIPositionStatus.COMPLETED_STATES.contains(osiPositionStatus);
        return osiFinished;
    }

    /**
     * @param osi an osi
     * @return true when combined Postion Status and range condition shall be displayed
     */
    @Override
    protected boolean isDisplayCombinedPositionStatusAndRange(final OrderStepInput osi) {
        if (isOptionalPosition(osi) && !getAutoConsume()) {
            // NONE Positions are not completed. Account phase completes the osi at completion (no auto consume in that
            // case). Thus we can show that the position is accounted in that case.
            return super.isPositionCompleted(osi);
        } else {
            return isPositionCompleted(osi);
        }
    }

    /**
     *
     */
    public void clearQuantityRangeConditions() {
        quantityRangeConditions.clear();
    }

    /**
     * @param osi              the osi
     * @param resultLimitCheck of the given osi
     */
    public void setQuantityRangeCondition(OrderStepInput osi, QuantityRangeCondition resultLimitCheck) {
        quantityRangeConditions.put(MESNamedUDAOrderStepInput.getOriginOSIName(osi), resultLimitCheck);
    }

    /**
     * @param osi the osi
     * @return the consumption options to be used when consuming the sublot of the given osi
     */
    public ConsumptionOptions getConsumptionOptions(final OrderStepInput osi) {
        final ConsumptionOptions consumeOptions = new ConsumptionOptions().setReleaseIdentification(true);
        OSIPositionStatus status = getTotalPositionStatusToSet(osi);
        if (status != null) {
            consumeOptions.setCompletePosition(status.longValue());
        }
        return consumeOptions;
    }

    private OSIPositionStatus getTotalPositionStatusToSet(final OrderStepInput osi) {
        if (!getAutoConsume()) {
            return null;
        }
        final QuantityRangeCondition quantityRangeCondition = quantityRangeConditions.get(MESNamedUDAOrderStepInput.getOriginOSIName(osi));
        if (quantityRangeCondition == null) {
            // nothing identified in this instance or PlannedQtyMode None
            return null;
        } else if (QuantityRangeCondition.QUANTITY_IN_RANGE.equals(quantityRangeCondition)) {
            return OSIPositionStatus.COMPLETED_INTOLERANCE;
        } else if (QuantityRangeCondition.QUANTITY_UPPER_OUT_OF_RANGE.equals(quantityRangeCondition)) {
            return OSIPositionStatus.COMPLETED_OVERWEIGH;
        } else {
            return isPhaseResultDone() ? OSIPositionStatus.COMPLETED_UNDERWEIGH : null;
        }
    }


    /**
     * @return a list of master osis, that has consumptions and thus needs to be checked for limits
     */
    public List<OrderStepInput> getMasterOSISForLimitChecks() {
        if (!getAutoConsume()) {
            return Collections.emptyList();
        }
        return isPhaseResultDone() ? getMasterOSISForLimitChecksAtDone() : getMandatoryMasterOSIs(getMasterOSIsLocallyIdentified());
    }

    private List<OrderStepInput> getMandatoryMasterOSIs(List<OrderStepInput> masterOSIs) {
        return masterOSIs.stream().filter(masterOsi -> !isPlannedQtyModeNone(masterOsi)).collect(Collectors.toList());
    }

    private List<OrderStepInput> getMasterOSIsLocallyIdentified() {
        List<OrderStepInput> masterOsisOfPhase = getMasterOSIsForPhase();
        List<OrderStepInput> osisToConsume = getOSIsOfSublotsToConsume();
        Set<OrderStepInput> result = new HashSet<>();
        for (OrderStepInput osi : osisToConsume) {
            OrderStepInput masterOsi = getMasterOsiOfOsi(masterOsisOfPhase, osi);
            if (masterOsi != null) {
                result.add(masterOsi);
            }
        }
        return new ArrayList<>(result);
    }

    private OrderStepInput getMasterOsiOfOsi(List<OrderStepInput> masterOsisOfPhase, OrderStepInput theOsi) {
        final String originOSIName = MESNamedUDAOrderStepInput.getOriginOSIName(theOsi);
        for (OrderStepInput masterOsi : masterOsisOfPhase) {
            if (MESNamedUDAOrderStepInput.getOriginOSIName(masterOsi).equals(originOSIName)) {
                return masterOsi;
            }
        }
        return null;
    }

    /**
     * @return the osis of sublots to consume
     */
    public List<OrderStepInput> getOSIsOfSublotsToConsume() {
        List<OrderStepInput> osisOfSublotToConsume = new ArrayList<>();
        for (final List<OrderStepInput> osiList : getOsiMap().values()) {
            for (final OrderStepInput osi : osiList) {
                final Sublot attachSublot = osi.getAttachSublot();
                if (attachSublot == null || MESNamedUDASublot.getIdentifiedForOSI(attachSublot) != osi.getKey()) {
                    // if the sublot is not identified for this OSI ->
                    // undo identification is performed for this sublot
                    continue;
                }
                final MeasuredValue quantity = attachSublot.getQuantity();
                if (quantity != null && quantity.signum() > 0) {
                    osisOfSublotToConsume.add(osi);
                }
            }
        }
        return osisOfSublotToConsume;
    }

    private List<OrderStepInput> getMasterOSISForLimitChecksAtDone() {
        List<OrderStepInput> masterOSIsWithoutIdentifications = new ArrayList<>();

        // Check all and only! mandatory positions ...
        for (OrderStepInput masterOsi : getMandatoryMasterOSIs(getMasterOSIsForPhase())) {
            // ... except already completed positions with Overweigh because exception has already be signed in previous
            // instance. Locally identified positions are not yet completed here at this point of time.
            if (!isPositionCompletedOverweigh(masterOsi)) {
                masterOSIsWithoutIdentifications.add(masterOsi);
            }
        }
        return masterOSIsWithoutIdentifications;
    }

    private boolean isPositionCompletedOverweigh(final OrderStepInput masterOsi) {
        return (OSIPositionStatus.COMPLETED_OVERWEIGH.longValue() == MESNamedUDAOrderStepInput.getTotalPositionStatus(masterOsi));
    }

    /**
     * @return true if there are material positions without identifications yet
     */
    public boolean hasMandatoryPositionsWithoutIdentifications() {
        //获取当前扫描上去的OSI对象list  masterOSIsWithLocalIdentifications
        List<OrderStepInput> masterOSIsWithLocalIdentifications = getMasterOSIsLocallyIdentified();
        List<OrderStepInput> mandatoryMasterOSIsOfPhase = getMandatoryMasterOSIs(getMasterOSIsForPhase());

        //遍历OSI物料对象
        for (OrderStepInput masterOsi : mandatoryMasterOSIsOfPhase) {
            // there are identification in this phase instance or in previous instances
            final boolean hasIdentifications = masterOSIsWithLocalIdentifications.contains(masterOsi)
                    || hasIdentifiedQuantity(masterOsi);
            if (!hasIdentifications) {
                //考虑替代料情况
                boolean hasReplaceIdentifications = hasIdentifiedQuantity(masterOsi, masterOSIsWithLocalIdentifications);
                if (hasReplaceIdentifications) {
                    return false;
                }
                // Position without identifications yet.
                return true;
            }
        }
        return false;
    }


    private boolean hasIdentifiedQuantity(OrderStepInput masterOsi, List<OrderStepInput> allMasterOSIs) {
        //dustin-20220302
        List<IMESMaterialParameter> materialParameters = executor.getPhase().getMaterialParameters();
        MeasuredValue totalIdentifiedQty = null;
        try {
            totalIdentifiedQty = getSelfAndReplaceIdentifiedQty(masterOsi, allMasterOSIs, materialParameters);
        } catch (MESException e) {
            throw new MESRuntimeException(e);
        }
        if (totalIdentifiedQty == null || totalIdentifiedQty.getValue().compareTo(BigDecimal.ZERO) == 0) {
            return false;
        }
        List<IMESMaterialParameter> matParamList = materialParameters.stream().filter(p -> p.getMaterial() == masterOsi.getPart() && p.getATRow().getValue("LC_isMainPart") != null && (Boolean) p.getATRow().getValue("LC_isMainPart")).collect(Collectors.toList());
        if(matParamList.size() > 0){
            //当前物料是主料
            RtPhaseExecutorMatAlterIdent0010.masterOsiException = null;
            RtPhaseExecutorMatAlterIdent0010.totalConsumedQtyException = null;
            RtPhaseExecutorMatAlterIdent0010.masterOsiException = masterOsi;
            RtPhaseExecutorMatAlterIdent0010.totalConsumedQtyException = totalIdentifiedQty;
        }
        return true;
    }

    public MeasuredValue getSelfAndReplaceIdentifiedQty(OrderStepInput osi, List<OrderStepInput> allMasterOSIs,
                                                        List<IMESMaterialParameter> materialParameters) throws MESIncompatibleUoMException {
        //获取本身识别数量
        MeasuredValue totalIdentifiedQtyMV = MESNamedUDAOrderStepInput.getTotalIdentifiedQuantity(osi);
        /**计算替代料数量**/
        //获取该物料是否设置为主料
        List<IMESMaterialParameter> matParamList = materialParameters.stream()
                .filter(p -> p.getMaterial() == osi.getPart()
                        && p.getATRow().getValue("LC_isMainPart") != null
                        && (Boolean) p.getATRow().getValue("LC_isMainPart")).collect(Collectors.toList());
        if (matParamList == null || matParamList.size() == 0) {
            return totalIdentifiedQtyMV;
        }
        MESNamedUDAMaterialParameter matParam = new MESNamedUDAMaterialParameter(matParamList.get(0));
        //获取换算比例,如果主料替换比例
        BigDecimal mainRatio = matParam.getReplaceRatio();
        if (mainRatio == null) {
            mainRatio = BigDecimal.ONE;
        }
        //获取替代组号
        String masterReplaceGroupName = matParam.getReplaceGroupName();
        //判断是否存在替代组号
        if (!StringUtils.isEmpty(masterReplaceGroupName)) {
            //获取替代物料集合(不能包含本身)
            Part material = matParam.getMatParam().getMaterial();
            List<IMESMaterialParameter> replaceGroupNameList = materialParameters.stream().filter(p -> {
                MESNamedUDAMaterialParameter replaceMatParam = new MESNamedUDAMaterialParameter(p);
                String replaceGroupName = replaceMatParam.getReplaceGroupName();
                Part replaceMaterial = p.getMaterial();
                return masterReplaceGroupName.equals(replaceGroupName) && !material.equals(replaceMaterial);
            }).collect(Collectors.toList());

            //替代消耗量
            IMeasuredValueConverter converter = MeasuredValueUtilities.getMVConverter(osi.getPart());

            IUnitOfMeasure unitOfMeasure = osi.getPlannedQuantity().getUnitOfMeasure();
            MeasuredValue replaceIdentifiedQtyMV = MeasuredValueUtilities.createZero(unitOfMeasure);
            //组合组号HashMap:组合组号-可消耗最小值
            Map<String, MeasuredValue> groupMap = new HashMap<>();
            //遍历替代料集合
            for (IMESMaterialParameter item : replaceGroupNameList) {
                MESNamedUDAMaterialParameter itemMatParam = new MESNamedUDAMaterialParameter(item);
                BigDecimal replaceRatio = itemMatParam.getReplaceRatio();
                //获取组合组号
                String combinationGroup = itemMatParam.getCombinationGroup();
                //根据OSI获取替代消耗数量
                List<OrderStepInput> identifiedList = allMasterOSIs.stream().filter(p -> p.getPart() == item.getMaterial()).collect(Collectors.toList());
                for (final OrderStepInput replaceOSI : identifiedList) {
                    MeasuredValue identifiedQuantityMV = getTotalIdentifiedQuantity(replaceOSI);
                    if (identifiedQuantityMV == null || replaceRatio == null) {
                        groupMap.put(combinationGroup, MeasuredValueUtilities.createZero(unitOfMeasure));
                        continue;
                    }

                    BigDecimal identifiedQty = identifiedQuantityMV.getValue();
                    BigDecimal calcIdentifiedQtyQty = identifiedQty.divide(replaceRatio,4).multiply(mainRatio);
                    MeasuredValue calcIdentifiedQtyMV = MeasuredValueUtilities.createMV(calcIdentifiedQtyQty, identifiedQuantityMV.getUnitOfMeasure());
                    //组合组号是空，表示是完全替代料计算
                    if (Strings.isEmpty(combinationGroup)) {
                        replaceIdentifiedQtyMV = MeasuredValueUtilities.addArgsOptional(replaceIdentifiedQtyMV,
                                calcIdentifiedQtyMV, converter);
                    } else {
                        //组合组号计算，以最小值计算
                        MeasuredValue minIdentifiedQtyMV = groupMap.get(combinationGroup);
                        if (minIdentifiedQtyMV == null) {
                            groupMap.put(combinationGroup, calcIdentifiedQtyMV);
                            continue;
                        }
                        BigDecimal minIdentifiedQty = minIdentifiedQtyMV.getValue();
                        minIdentifiedQtyMV = calcIdentifiedQtyQty.compareTo(minIdentifiedQty) < 0 ? calcIdentifiedQtyMV : minIdentifiedQtyMV;
                        groupMap.put(combinationGroup, minIdentifiedQtyMV);
                    }
                }
            }
            //增加组合组号数量
            if (groupMap != null) {
                for (MeasuredValue minIdentifiedQtyMV : groupMap.values()) {
                    replaceIdentifiedQtyMV = MeasuredValueUtilities.addArgsOptional(replaceIdentifiedQtyMV,
                            minIdentifiedQtyMV, converter);
                }
            }
            //替代料数量+本身数量
            if (totalIdentifiedQtyMV != null) {
                replaceIdentifiedQtyMV = MeasuredValueUtilities.addArgsOptional(totalIdentifiedQtyMV,
                        replaceIdentifiedQtyMV, converter);
            }
            totalIdentifiedQtyMV = replaceIdentifiedQtyMV;
        }
        return totalIdentifiedQtyMV;
    }

    private MeasuredValue getTotalIdentifiedQuantity(final OrderStepInput masterOsi) {
        MeasuredValue totalIdentifiedQty = MESNamedUDAOrderStepInput.getTotalIdentifiedQuantity(masterOsi);
        return (totalIdentifiedQty == null) ? createZeroQty(masterOsi) : totalIdentifiedQty;
    }

    private MeasuredValue createZeroQty(final OrderStepInput osi) {
        return MeasuredValueUtilities.createZero(getDefaultUoM(osi));
    }


    private boolean hasIdentifiedQuantity(final OrderStepInput masterOsi) {
        return (!MeasuredValueUtilities.isNullOrZero(MESNamedUDAOrderStepInput.getTotalIdentifiedQuantity(masterOsi)));
    }

    private boolean isOptionalPosition(OrderStepInput masterOsi) {
        return isPlannedQtyModeNone(masterOsi);
    }

    private boolean isPlannedQtyModeNone(OrderStepInput masterOsi) {
        return PlannedQuantityMode.NONE.equals(getPlannedQuantityMode(masterOsi));
    }


    /**
     * initialize the materials list containing identified sublots as well
     */
    private void initMaterialsList() {
        materials = new ArrayList<>();
        final Map<String, String> sublotToLUMap = getSublotToLUMapFromPhaseData();

        for (final IdentifiedMaterialDAO0710 matParam : matInputList) {
            materials.add(matParam);
            // when there are identified sublots, their qty will be added to the
            // identified qty for the material
            IMeasuredValue identifiedQty = initSublotRowsAndCalcIdentifiedQuantity(matParam, sublotToLUMap);

            matParam.setProcessedQtyMV(identifiedQty);
        }
    }

    private IMeasuredValue initSublotRowsAndCalcIdentifiedQuantity(final IdentifiedMaterialDAO0710 matParam,
                                                                   final Map<String, String> sublotToLUMap) {
        IUnitOfMeasure unitOfMeasure = matParam.getProcessedQtyMV().getUnitOfMeasure();
        IMeasuredValue identifiedQty = MeasuredValueUtilities.createMV(BigDecimal.valueOf(0), unitOfMeasure);
        if (getStatus() == Status.PREVIEW) {
            return identifiedQty;
        }

        List<MasterOSIWithBatchAllocations> osiList = masterOSIsWithBatchAllocations.get(matParam.getMaterialID());

        for (MasterOSIWithBatchAllocations osiWithBatches : osiList) {
            if (osiWithBatches.getOsi().getKey() == matParam.getOsiKey()) {
                identifiedQty = addIdentifiedSublotsForPosition(sublotToLUMap, identifiedQty, osiWithBatches);
            }
        }
        return identifiedQty;
    }

    private IMeasuredValue addIdentifiedSublotsForPosition(final Map<String, String> sublotToLUMap, IMeasuredValue inIdentifiedQty,
                                                           MasterOSIWithBatchAllocations osiWithBatches) {
        final IOrderStepExecutionService osExecSrv = MaterialHelper0710.getOrderStepExecutionService();

        IMeasuredValue identifiedQty = inIdentifiedQty;
        final List<OrderStepInputSublot> identifiedOSISublots = osExecSrv.getIdentifiedOSISublots(osiWithBatches.getOsi(), false, false);

        for (OrderStepInputSublot osiSublot : identifiedOSISublots) {
            if (isSublotUnIdentified(osiSublot)) {
                continue;
            }
            IMeasuredValue qtyOfSublot = getConvertedIdentifiedQuantity(osiSublot, identifiedQty.getUnitOfMeasure());

            IdentifiedMaterialDAO0710 materialOfSublot = createMaterialFor(osiSublot, qtyOfSublot, sublotToLUMap);
            materials.add(materialOfSublot);

            identifiedQty = MaterialHelper0710.addMV(identifiedQty, qtyOfSublot);
        }
        return identifiedQty;
    }

    private boolean isSublotUnIdentified(OrderStepInputSublot osiSublot) {
        // if a sublot is released and no quantity was consumed: it was unidentified
        return osiSublot.isReleased() && osiSublot.getConsumedQuantity() == null;
    }


    private IdentifiedMaterialDAO0710 createMaterialFor(final OrderStepInputSublot osiSublot, IMeasuredValue qtyOfSublot,
                                                        final Map<String, String> sublotToLUMap) {
        IdentifiedMaterialDAO0710 materialOfSublot = new IdentifiedMaterialDAO0710();

        materialOfSublot.setMfcPosition(MESNamedUDAOrderStepInput.getPosition(osiSublot.getOrderStepInput()));
        final String batchName = osiSublot.getBatch().getUniqueName();
        materialOfSublot.setBatch(batchName);
        final String sublotName = osiSublot.getSublot().getUniqueName();
        final String logisticUnitName = sublotToLUMap.get(sublotName);
        final String sublotAndLUDDisplayString = createSublotAndLUDDisplayString(sublotName, logisticUnitName);
        materialOfSublot.setSublot(sublotAndLUDDisplayString);
        String displayString =
                createRelatedBatchesSublotsDisplayString(batchName, sublotName, logisticUnitName);
        materialOfSublot.setRelatedBatchesSublotsDisplayString(displayString);
        materialOfSublot.setPlannedQtyMV(null);
        materialOfSublot.setAccountedQtyMV(null);
        materialOfSublot.setLocalIdentified(isLocallyIdentified(sublotName));
        materialOfSublot.setProcessedQtyMV(qtyOfSublot);

        return materialOfSublot;
    }

    /**
     * @param batchName        the unique batch name
     * @param sublotName       the unique sublot name
     * @param logisticUnitName the LU or empty
     * @return combined display string
     */
    public String createRelatedBatchesSublotsDisplayString(final String batchName, final String sublotName, final String logisticUnitName) {
        return StringUtilsEx.isNoneEmpty(logisticUnitName) ? IdentifiedMaterialDAO0710.getDisplayString(batchName, logisticUnitName, sublotName)
                : IdentifiedMaterialDAO0710.getDisplayString(batchName, sublotName);
    }

    /**
     * @param sublotName       the unique sublot name
     * @param logisticUnitName the LU or empty
     * @return combined display string
     */
    public String createSublotAndLUDDisplayString(final String sublotName, final String logisticUnitName) {
        return StringUtilsEx.isNoneEmpty(logisticUnitName) ? logisticUnitName + StringConstants.LINE_BREAK + sublotName : sublotName;
    }

    private IMeasuredValue getConvertedIdentifiedQuantity(final OrderStepInputSublot osiSublot, final IUnitOfMeasure targetUoM) {
        IMeasuredValue qty = null;
        try {
            // if original identified quantity is not set (can be the case with the old data model)
            // get it from the sublot
            qty = osiSublot.getOriginalIdentifiedQuantity() != null ? //
                    osiSublot.getOriginalIdentifiedQuantity() : osiSublot.getIdentifiedQuantity();
            IMeasuredValueConverter converter = MeasuredValueUtilities.getMVConverter(osiSublot.getBatch().getPart());
            // If there is a part specific converter defined, try to convert the quantity
            if (converter != null) {
                qty = converter.convert(qty, targetUoM);
            } else {
                // If there is no part specific converter, use the default measured value conversion
                qty = qty.convert(targetUoM);
            }

        } catch (Exception e) {
            throw new MESRuntimeException(e);
        }
        return qty;
    }

    /**
     * Initialize the material list from the phase data
     */
    private void initMaterialsListFromPhaseData() {
        materials = new ArrayList<>();
        List<MESRtPhaseDataMatAlterIdent0010> sortedPhaseData = getSortedMaterialsListFromRtPhaseData();

        // re-init the matInputList as well, so that it contains the same as when the phase is active
        matInputList.clear();
        for (final MESRtPhaseDataMatAlterIdent0010 data : sortedPhaseData) {
            if (BooleanUtils.isTrue(data.getIsUnidentified())) {
                continue;
            }

            final IdentifiedMaterialDAO0710 row = createMaterialFromPhaseData(data);
            materials.add(row);

            if (row.isHeader()) {
                matInputList.add(row);
            }
        }
    }

    private IdentifiedMaterialDAO0710 createMaterialFromPhaseData(final MESRtPhaseDataMatAlterIdent0010 data) {
        final IdentifiedMaterialDAO0710 material = new IdentifiedMaterialDAO0710();

        material.setMaterialID(getMaterialNameOfPhaseDataEntry(data));
        material.setOsiKey(getOsiKeyOfPhaseDataEntry(data));
        material.setMfcPosition(data.getMfcPos());
        material.setDescription(data.getMaterialDescription());
        material.setIsHeader(data.getIsHeader());
        final String batchIDStr = data.getBatchIDStr();
        material.setBatch(batchIDStr);

        final String sublotName = data.getSublotIDStr();
        final String logisticUnitName = data.getLogisticUnitID();
        final String sublotAndLUDDisplayString =
                StringUtilsEx.isNoneEmpty(logisticUnitName) ? logisticUnitName + StringConstants.LINE_BREAK + sublotName : sublotName;
        material.setSublot(sublotAndLUDDisplayString);

        material.setRelatedBatchesSublotsDisplayString(data.getBatchSublotDisplString());
        material.setPlannedQtyMV(data.getPlannedQty());
        material.setProcessedQtyMV(data.getIdentifiedQty());
        material.setComment(data.getCommentToExecutionStr());
        material.setAccountedQtyMV(data.getAccountedQty());
        material.setStatusString(data.getAccountingStatus());
        material.setPlannedQtyWithLimits(data.getPlannedQtyWithLimits());
        material.setPurePlannedQtyDisplayString(data.getPlannedQtyDisplString());

        material.setLocalIdentified(data.getLocalIdentified());

        return material;
    }

    private List<MESRtPhaseDataMatAlterIdent0010> getSortedMaterialsListFromRtPhaseData() {
        Comparator<MESRtPhaseDataMatAlterIdent0010> comparator = new Comparator<MESRtPhaseDataMatAlterIdent0010>() {

            @Override
            public int compare(MESRtPhaseDataMatAlterIdent0010 m1, MESRtPhaseDataMatAlterIdent0010 m2) {
                final int compareResultMatID = ObjectUtils.compare(getMaterialNameOfPhaseDataEntry(m1), getMaterialNameOfPhaseDataEntry(m2));
                // same materials
                if (compareResultMatID == 0) {
                    return compareSameMaterial(m1, m2);
                } else {
                    return compareDifferentMaterials(m1, m2, compareResultMatID);
                }
            }

            private int compareSameMaterial(MESRtPhaseDataMatAlterIdent0010 m1, MESRtPhaseDataMatAlterIdent0010 m2) {
                // next put the header on top
                if (!m1.getIsHeader().equals(m2.getIsHeader())) {
                    return m1.getIsHeader() ? -1 : 1;
                }
                // for all subrows of the same material, keep the original sorting
                return 0;
            }

            private int compareDifferentMaterials(MESRtPhaseDataMatAlterIdent0010 m1, MESRtPhaseDataMatAlterIdent0010 m2, final int compareResultMatID) {
                String pos1 = m1.getMfcPos();
                String pos2 = m2.getMfcPos();
                final boolean pos1NotBlank = StringUtils.isNotBlank(pos1);
                final boolean pos2NotBlank = StringUtils.isNotBlank(pos2);

                if (pos1NotBlank && pos2NotBlank) {
                    // sort by MFC Position
                    return pos1.compareTo(pos2);
                } else if (pos1NotBlank) {
                    // MFC Position before empty
                    return -1;
                } else if (pos2NotBlank) {
                    return 1;
                } else {
                    // both without MFC positions: sort by MaterialID
                    return compareResultMatID;
                }
            }

        };

        List<MESRtPhaseDataMatAlterIdent0010> rtPhaseData = getAllRtPhaseData();
        // sort the phase data
        List<String> materialIDsSorted = new ArrayList<>();
        matInputList.forEach(mat -> materialIDsSorted.add(mat.getMaterialID()));
        // sort the phase data into another list
        return rtPhaseData.stream().sorted(comparator).collect(Collectors.toList());
    }

    private String getMaterialNameOfPhaseDataEntry(MESRtPhaseDataMatAlterIdent0010 data) {
        return getMaterialNameOfPhaseData(data.getMaterialID());
    }

    /**
     * @param materialID Column MaterialID of Phase Data
     * @return material name e.g D001-01 part of phase data
     */
    public static String getMaterialNameOfPhaseData(String materialID) {
        return materialID.contains(MATERIAL_ID_DELIMITER_IN_DATA) ? materialID.split(MATERIAL_ID_DELIMITER_IN_DATA)[1] : materialID;
    }

    private Long getOsiKeyOfPhaseDataEntry(MESRtPhaseDataMatAlterIdent0010 data) {
        String materialID = data.getMaterialID();
        String osiKeyString = materialID.contains(MATERIAL_ID_DELIMITER_IN_DATA) ? materialID.split(MATERIAL_ID_DELIMITER_IN_DATA)[0] : materialID;
        return Long.parseLong(osiKeyString);
    }

    private Map<String, String> getSublotToLUMapFromPhaseData() {
        final Map<String, String> sublotToLUMap = new HashMap<>();
        if (getStatus() != Status.PREVIEW) {
            final List<MESRtPhaseDataMatAlterIdent0010> allRtPhaseData = getAllRtPhaseData();
            for (MESRtPhaseDataMatAlterIdent0010 data : allRtPhaseData) {
                if (!data.getIsHeader() && !data.getIsUnidentified()) {
                    sublotToLUMap.put(data.getSublotIDStr(), data.getLogisticUnitID());
                }
            }
        }
        return sublotToLUMap;
    }

    /**
     * This Comparator enforces sorting by sublot number ascending (guaranteed by number generator)
     */
    public static final Comparator<AllocatedSublot> SUBLOT_COMPARATOR_BY_NUMBER =
            (s1, s2) -> s1.getSublot().getName().compareTo(s2.getSublot().getName());

    /**
     * Get the allocated batches and sublots for each material parameter
     */
    private void initAllocBatchesSublots() {
        for (IdentifiedMaterialDAO0710 material : matInputList) {
            initAllocBatchesSublotOnMaterial(material);
        }
    }

    /**
     * initialize sublot, batch and corresponding display string of the material
     *
     * @param material the material
     */
    public void initAllocBatchesSublotOnMaterial(IdentifiedMaterialDAO0710 material) {
        List<String> batches = new ArrayList<>();
        List<String> sublots = new ArrayList<>();
        Optional<MasterOSIWithBatchAllocations> optional =
                masterOSIsWithBatchAllocations.get(material.getMaterialID()).stream().filter(val -> val.getOsi().getKey() == material.getOsiKey())
                        .findFirst();
        if (!optional.isPresent()) {
            throw new MESRuntimeException("Could not find matching OSI with key " + material.getOsiKey());
        }
        MasterOSIWithBatchAllocations matchingOsi = optional.get();
        OrderStepInput osi = matchingOsi.getOsi();

        Set<String> batchSublotDisplayStrings = new LinkedHashSet();

        addAllocatedBatches(batches, matchingOsi, osi, batchSublotDisplayStrings);

        addAllocatedSublotsOfTransfer(matchingOsi, osi, batches, sublots, batchSublotDisplayStrings);

        material.setRelatedBatchesSublotsDisplayString(StringUtils.join(batchSublotDisplayStrings, StringConstants.LINE_BREAK));
        material.setBatch(join(batches, ", "));
        material.setSublot(join(sublots, ", "));
    }

    private void addAllocatedBatches(List<String> batches, MasterOSIWithBatchAllocations matchingOsi,
                                     OrderStepInput osi, Set<String> batchSublotDisplayStrings) {
        IOrderStepExecutionService service = MaterialHelper0710.getOrderStepExecutionService();
        try {
            for (Object oAllocatedBatch : service.getAllocatedBatches(osi)) {
                Batch allocatedBatch = ((AllocatedBatch) oAllocatedBatch).getBatch();
                batches.add(allocatedBatch.getUniqueName());
                matchingOsi.getAllocatedBatches().add(allocatedBatch);
                batchSublotDisplayStrings.add(allocatedBatch.getUniqueName());

            }
        } catch (MESIncompatibleUoMException e) {
            throw new MESRuntimeException("An exception occured by getting allocated batches. ", e);
        }
    }

    private void addAllocatedSublotsOfTransfer(MasterOSIWithBatchAllocations matchingOsi, OrderStepInput osi,
                                               List<String> batches, List<String> sublots, Set<String> batchSublotDisplayStrings) {
        if (osi.getProcessingType() == OrderStepInput.INPUT_TYPE_INTERMEDIATE) {
            IOrderStepExecutionService service = MaterialHelper0710.getOrderStepExecutionService();
            List<AllocatedSublot> allocatedSublots = service.getAllocatedSublots(osi);
            // do sorting here, according sublot number ascending
            Collections.sort(allocatedSublots, SUBLOT_COMPARATOR_BY_NUMBER);


            for (AllocatedSublot oAllocatedSublot : allocatedSublots) {
                Sublot allocatedSublot = oAllocatedSublot.getSublot();
                if (wasReplaced(allocatedSublot)) {
                    continue;
                }

                if (oAllocatedSublot.isIdentified() || isLocallyIdentified(allocatedSublot.getUniqueName())
                        || alreadyIdentifiedOrAccounted(allocatedSublot)) {
                    // Only show sublot that needs to be identified in the header row
                    continue;
                }

                // check if the batch is already added
                if (!batches.contains(allocatedSublot.getBatch().getUniqueName())) {
                    batches.add(allocatedSublot.getBatch().getUniqueName());
                    matchingOsi.getAllocatedBatches().add(allocatedSublot.getBatch());
                }
                sublots.add(allocatedSublot.getUniqueName());
                batchSublotDisplayStrings
                        .add(IdentifiedMaterialDAO0710.getDisplayString(allocatedSublot.getBatch().getUniqueName(), allocatedSublot.getUniqueName()));
            }
        }
    }

    private boolean wasReplaced(final Sublot sublot) {
        return AbstractMaterialPhaseExecutor0710.LONG_TRUE.equals(MESNamedUDASublot.getWasReplaced(sublot));
    }

    private boolean alreadyIdentifiedOrAccounted(Sublot allocatedSublot) {
        boolean accounted = false;
        boolean identified = false;
        final Long identifiedForOSIKey = MESNamedUDASublot.getIdentifiedForOSI(allocatedSublot);
        final boolean isReleased = identifiedForOSIKey == null || identifiedForOSIKey.longValue() == 0L;

        List<OrderStepInput> osiSAttachedToSublot = getOSIsAttachedToSublot(allocatedSublot);
        // multiple OSIs means that there has been multiple undo and re-identify actions on same sublot

        for (OrderStepInput osi : osiSAttachedToSublot) {
            // null is undo. In case of closing of account phase without a consumption, sublot is released and consumed
            // qty is 0 UoM
            boolean hasConsumptions = getConsumedQuantity(osi) != null;

            if (isReleased && !hasConsumptions) {
                // if the sublot is not identified for this OSI ->
                // undo identification is performed for this sublot
                continue;
            } else if (isReleased && hasConsumptions) {
                accounted = true;
            } else if (identifiedForOSIKey == osi.getKey()) {
                identified = true;
            }
        }
        return accounted || identified;
    }


    private List<OrderStepInput> getOSIsAttachedToSublot(Sublot allocatedSublot) {
        List<OrderStepInput> osiList = new ArrayList<>();
        List<OrderStepInput> list = (getOsiMap() == null) ? os.getOrderStepInputItems() : getOsiMap().get(allocatedSublot.getPart().getPartNumber());
        for (OrderStepInput osi : list) {
            final Sublot attachSublot = osi.getAttachSublot();
            if ((attachSublot != null) && (attachSublot.equals(allocatedSublot))) {
                osiList.add(osi);
            }
        }
        return osiList;
    }

    private IMeasuredValue getConsumedQuantity(final OrderStepInput osi) {
        return MESNamedUDAOrderStepInput.getConsumedQuantity(osi);
    }

    /**
     * Joins values in the list with the delimiter
     *
     * @param list      String values list
     * @param delimiter sting
     * @return joined values in the list with the delimiter
     */
    private String join(List<String> list, String delimiter) {
        StringBuilder batchString = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                batchString = batchString.append(delimiter);
            }
            batchString = batchString.append(list.get(i));
        }
        return batchString.toString();
    }

    @Override
    protected void refreshMaterialsList() {
        materials = new ArrayList<>();
        initLocalIdentifiedSublotsFromPhaseData();

        initOSI();
        if (getStatus() == Status.COMPLETED) {
            initMaterialsListFromPhaseData();
        } else {
            initMaterialsList();
        }
        logMaterial("After refreshMaterialsList");
    }


    /**
     * @return true if looping is supported
     */
    public boolean supportLooping() {
        return !LoopConfigurationType0100.NO_LOOP.equals(getLoopConfiguration());
    }

    /**
     * @return true, if user must loop
     */
    public boolean mustLoop() {
        return LoopConfigurationType0100.MUST_LOOP.equals(getLoopConfiguration());
    }

    /**
     * @return true if done button is selected or no looping is configured
     */
    public boolean isPhaseResultDone() {
        return PhaseResult0710.DONE.equals(phaseResult);
    }

    /**
     * @return the phase result stored in the phase output
     */
    public PhaseResult0710 getPhaseResult() {
        return phaseResult;
    }

    /**
     * @param res the selected phase result
     */
    public void setPhaseResult(PhaseResult0710 res) {
        phaseResult = res;
    }

    private void initPhaseResult() {
        // if the phase has an output: get the result from there
        MESRtPhaseOutputMatAlterIdent0010 rtPhaseOutput = getRtPhaseOutput();
        if (rtPhaseOutput != null && StringUtils.isNotEmpty(rtPhaseOutput.getResult())) {
            phaseResult = PhaseResult0710.valueOf(rtPhaseOutput.getResult());
        } else {
            phaseResult = supportLooping() ? PhaseResult0710.CONTINUE : PhaseResult0710.DONE;
        }
    }

    /**
     *
     */
    public void setPhaseOutputData() {
        MESRtPhaseOutputMatAlterIdent0010 rtPhaseOutput = getRtPhaseOutput();
        rtPhaseOutput.setResult(phaseResult.getKey());
    }

    /**
     * @return Returns the identificationDone.
     */
    public boolean isIdentificationDone() {
        return anIdentificationIsDone;
    }

    /**
     * @param identificationDone The identificationDone to set.
     */
    public void setIdentificationDone(boolean identificationDone) {
        this.anIdentificationIsDone = identificationDone;
    }

    /**
     * Initialze locally identified sublots from phase data
     */
    private void initLocalIdentifiedSublotsFromPhaseData() {
        if ((getStatus() == Status.PREVIEW) || !locallyIdentifiedSublots.isEmpty()) {
            // already initialized
            return;
        }
        List<MESRtPhaseDataMatAlterIdent0010> rtPhaseData = getAllRtPhaseData();
        locallyIdentifiedSublots.clear();

        for (MESRtPhaseDataMatAlterIdent0010 data : rtPhaseData) {
            if (BooleanUtils.isTrue(data.getIsUnidentified()) || BooleanUtils.isTrue(data.getIsHeader())) {
                continue;
            }
            if (data.isLocalIdentified()) {
                locallyIdentifiedSublots.add(data.getSublotIDStr());
            }
        }
    }

    /**
     * @param sublot a by this instance identified sublot
     */
    public void addLocalIdentifiedSublot(Sublot sublot) {
        locallyIdentifiedSublots.add(sublot.getUniqueName());
    }

    /**
     * @param sublot a by this instance identified sublot
     */
    public void removeLocalIdentifiedSublot(Sublot sublot) {
        locallyIdentifiedSublots.remove(sublot.getUniqueName());
    }

    /**
     * @param sublotUniqueName name
     * @return true if sublot has been identified in this phase instance
     */
    public boolean isLocallyIdentified(String sublotUniqueName) {
        return locallyIdentifiedSublots.contains(sublotUniqueName);
    }

    /**
     * 检查组合组号比例是否一致
     *
     * @return
     */
    public boolean checkCombineGroupIsEqualWithRate() {
        List<OrderStepInput> masterOSIToCheck = new ArrayList();
        //获取工单输入的OSI对象
        List<OrderStepInput> allMasterOSIs = getMasterOSIsForPhase();
        masterOSIToCheck.addAll(allMasterOSIs.stream().filter(osi -> mustCheckQuantityForOsi(osi))
                .collect(Collectors.toList()));
        //dustin: 获取物料参数配置集合
        List<IMESMaterialParameter> materialParameters = executor.getPhase().getMaterialParameters();
        for (OrderStepInput osi : masterOSIToCheck) {
            try {
                /**
                 * 带入每一个OSI对象检查
                 */
                boolean result = getCombineGroupIsEqualWithRate(osi, allMasterOSIs, materialParameters);
                if (!result) {
                    return false;
                }
                //若当前物料不为null，进入下一次循环
            } catch (MESIncompatibleUoMException e) {
                e.printStackTrace();
            }
        }
        //当所以的物料都检查完 如中途没有反馈的return，返回true；
        return true;
    }

    private boolean mustCheckQuantityForOsi(OrderStepInput osi) {
        return !PlannedQuantityMode.NONE.equals(getPlannedQuantityMode(osi))
                && !EnumOrderStepInputStatus.isFinishedStatus(MESNamedUDAOrderStepInput.getStatus(osi));
    }

    /**
     * 校验组合组号是否满足替代比例
     *
     * @param osi
     * @param allMasterOSIs
     * @param materialParameters
     * @return
     * @throws MESIncompatibleUoMException
     */
    private boolean getCombineGroupIsEqualWithRate(OrderStepInput osi, List<OrderStepInput> allMasterOSIs,
                                                   List<IMESMaterialParameter> materialParameters) throws MESIncompatibleUoMException {
        //获取该物料是否设置为主料
        List<IMESMaterialParameter> matParamList = materialParameters.stream()
                .filter(p -> p.getMaterial() == osi.getPart()
                        && p.getATRow().getValue("LC_isMainPart") != null
                        && (Boolean) p.getATRow().getValue("LC_isMainPart")).collect(Collectors.toList());
        if (matParamList == null || matParamList.size() == 0) {
            //不为主料 返回true
            return true;
        }

        //是 主料 保存主料物料号到全局变量
        RtPhaseExecutorMatAlterIdent0010.mainMaterial = osi.getPart().getPartNumber();

        //获取当前物料参数对象
        MESNamedUDAMaterialParameter matParam = new MESNamedUDAMaterialParameter(matParamList.get(0));
        //获取主料 替代组号
        String masterReplaceGroupName = matParam.getReplaceGroupName();
        //判断主料 是否存在替代组号
        if (!StringUtils.isEmpty(masterReplaceGroupName)) {
            //获取主料 物料对象
            Part material = matParam.getMatParam().getMaterial();
            //替代组号不为null，获取组合组号 替代物料集合(不能包含主料本身、也不包含独立替代物料)
            List<IMESMaterialParameter> combinationGroupList = materialParameters.stream().filter(p -> {
                MESNamedUDAMaterialParameter replaceMatParam = new MESNamedUDAMaterialParameter(p);
                //获取替代组号
                String replaceGroupName = replaceMatParam.getReplaceGroupName();
                //获取组合组号
                String combinationGroup = replaceMatParam.getCombinationGroup();
                Part replaceMaterial = p.getMaterial();
                return masterReplaceGroupName.equals(replaceGroupName)
                        && !material.equals(replaceMaterial)
                        && StringUtils.isNotEmpty(combinationGroup);
            }).sorted((o1, o2) -> {
                MESNamedUDAMaterialParameter p1 = new MESNamedUDAMaterialParameter(o1);
                MESNamedUDAMaterialParameter p2 = new MESNamedUDAMaterialParameter(o2);
                //组合组号相同
                return p1.getCombinationGroup().compareTo(p2.getCombinationGroup());
            }).collect(Collectors.toList());

            //获取主料 计划数量的单位
            IUnitOfMeasure unitOfMeasure = osi.getPlannedQuantity().getUnitOfMeasure();
            //遍历组合替代集合
            MeasuredValue firstIdentifiedMV = null;
            //第一个替代比例（0）
            BigDecimal firstRatio = BigDecimal.ZERO;
            String firstGroup = null;
            for (IMESMaterialParameter item : combinationGroupList) {
                //变量组合组号的物料集合
                MESNamedUDAMaterialParameter itemMatParam = new MESNamedUDAMaterialParameter(item);
                //获取替代比例
                BigDecimal replaceRatio = itemMatParam.getReplaceRatio();
                //获取组合组号
                String combinationGroup = itemMatParam.getCombinationGroup();
                //保存组合组号到全局变量
                RtPhaseExecutorMatAlterIdent0010.combineGroupName = combinationGroup;
                if (StringUtils.isEmpty(firstGroup)) {
                    //代表第一次遍历物料进入，保存组合组号、替代比例
                    firstGroup = combinationGroup;
                    firstRatio = replaceRatio;
                }
                if (!StringUtils.equals(firstGroup, combinationGroup)) {
                    //大于第一次进入，比较组合组号是否与先前保存的组合组号一致（这里不相等，会将当前物料的组合组号重新赋值）
                    firstIdentifiedMV = null;
                    firstRatio = replaceRatio;
                }

                //根据OSI获取替代消耗数量
                List<OrderStepInput> identifiedList = allMasterOSIs.stream().filter(p -> p.getPart() == item.getMaterial()).collect(Collectors.toList());
                //创建一个识别量（0+主料单位）
                MeasuredValue identifiedQtyMV = MeasuredValueUtilities.createZero(unitOfMeasure);
                for (OrderStepInput replaceOSI : identifiedList) {
                    identifiedQtyMV = MESNamedUDAOrderStepInput.getTotalIdentifiedQuantity(replaceOSI);
                    if (identifiedQtyMV == null || replaceRatio == null) {
                        //识别量 || 替代比例 为null，创建 0 + 主料单位 数量，进入下一个OSI判断
                        //这里的意义就是 识别量不能为null
                        identifiedQtyMV = MeasuredValueUtilities.createZero(unitOfMeasure);
                        continue;
                    }
                }
                //如果第一个识别数量为null，将当前的第一个扫描量赋值
                if (firstIdentifiedMV == null) {
                    firstIdentifiedMV = identifiedQtyMV;
                }
                //1的消耗*2的比例
                //获取第一个保存的识别量的值
                BigDecimal firstConsumedQty = firstIdentifiedMV.getValue();
                MeasuredValue firstCalcQtyMV = MeasuredValueUtilities.createMV(firstConsumedQty.multiply(replaceRatio),
                        firstIdentifiedMV.getUnitOfMeasure());
                //2的消耗*1的比例
                BigDecimal consumedQty = identifiedQtyMV.getValue();
                MeasuredValue calcConsumedQtyMV = MeasuredValueUtilities.createMV(consumedQty.multiply(firstRatio),
                        identifiedQtyMV.getUnitOfMeasure());
                if (MeasuredValueUtilities.compare(firstCalcQtyMV, calcConsumedQtyMV) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

}
