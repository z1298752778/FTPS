package com.leateck.phase.accountalternativematerial0010;

import com.datasweep.compatibility.client.*;
import com.datasweep.plantops.common.measuredvalue.IMeasuredValue;
import com.datasweep.plantops.common.measuredvalue.IUnitOfMeasure;
import com.rockwell.mes.apps.ebr.ifc.phase.IPhaseExecutor.Status;
import com.rockwell.mes.commons.base.ifc.OSILock.LockException;
import com.rockwell.mes.commons.base.ifc.OSILockCollection;
import com.rockwell.mes.commons.base.ifc.exceptions.MESException;
import com.rockwell.mes.commons.base.ifc.exceptions.MESIncompatibleUoMException;
import com.rockwell.mes.commons.base.ifc.exceptions.MESRuntimeException;
import com.rockwell.mes.commons.base.ifc.functional.IMeasuredValueConverter;
import com.rockwell.mes.commons.base.ifc.functional.MeasuredValueUtilities;
import com.rockwell.mes.commons.base.ifc.nameduda.MESNamedUDAOrderStepInput;
import com.rockwell.mes.commons.base.ifc.nameduda.MESNamedUDAPart;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.IMESExceptionRecord.RiskClass;
import com.rockwell.mes.commons.parameter.bool.MESParamBoolean0100;
import com.rockwell.mes.commons.shared.phase.mvc.AbstractPhaseExecutor0200;
import com.rockwell.mes.parameter.product.accountcalc.AccountCalculation0200;
import com.rockwell.mes.parameter.product.accountcalc.MESParamAccountCalc0200;
import com.rockwell.mes.parameter.product.excptenabledef.MESParamExcptEnableDef0200;
import com.rockwell.mes.parameter.product.instruction.MESParamInstruction0200;
import com.rockwell.mes.services.inventory.ifc.TransactionSubtype;
import com.rockwell.mes.services.order.ifc.EnumOrderStepInputStatus;
import com.rockwell.mes.services.s88.ifc.recipe.IMESMaterialParameter;
import com.rockwell.mes.services.s88.ifc.recipe.IMESMaterialParameter.TYPE;
import com.rockwell.mes.services.s88.ifc.recipe.PlannedQuantityMode;
import com.rockwell.mes.services.wd.ifc.MESAllMasterOSIs;
import com.rockwell.mes.services.wip.ifc.IOrderStepExecutionService;
import com.rockwell.mes.services.wip.ifc.OrderStepInputSublot;
import com.rockwell.mes.shared.product.material.AccountMaterialDAO0710;
import com.rockwell.mes.shared.product.material.AccountMaterialDAO0710.AccountType;
import com.rockwell.mes.shared.product.material.MaterialModel0710;
import com.rockwell.mes.shared.product.material.PhaseResult0710;
import com.rockwell.mes.shared.product.material.util.MaterialHelper0710;
import com.rockwell.mes.shared0400.product.ui.basics.util.ProductPhaseSwingHelper;
import com.rockwell.mes.shared0400.product.util.ParamClassConstants0400;
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
public class MatAccountModel0710 extends MaterialModel0710<AccountMaterialDAO0710, MESRtPhaseDataMatAlterAcct0010, MESRtPhaseOutputMatAlterAcct0010> {

    private static final Log LOGGER = LogFactory.getLog(MatAccountModel0710.class);
    private List<String[]> NotEqualRatePartList = new ArrayList<>();
    /**
     * list of the input materials + identified sublots
     */
    private List<AccountMaterialDAO0710> materials;

    private Map<String, List<OrderStepInput>> masterOSIsOfMaterial;

    /**
     * The quantities accounted in this phase instance: key = DB key of the split OSI for which accounted qtys were
     * modified; value = map of accounted qtys (key = transaction type, value = qty)
     */
    private Map<Long, Map<TransactionSubtype, IMeasuredValue>> qtysAccountedInPhaseInstance = new HashMap();

    private PhaseResult0710 phaseResult;

    private boolean modified = false;

    /**
     * @param theExecutor the phase executor
     */
    protected MatAccountModel0710(AbstractPhaseExecutor0200 theExecutor) {
        super(theExecutor);
    }

    /**
     * @return Returns the executor.
     */
    public RtPhaseExecutorMatAlterAcct0010 getExecutor() {
        return (RtPhaseExecutorMatAlterAcct0010) executor;
    }

    /**
     * @return the text for the navigator info: the phase result
     */
    public String getNavigatorInfoColumn() {
        return getLocalizedResult(phaseResult.getKey());
    }

    @Override
    public boolean isBlockedByWarehouseError() {
        return lastWarehouseCallFailed();
    }

    @Override
    public String getInstructionTextColumn1() {
        MESParamInstruction0200 param = executor.getProcessParameterData(MESParamInstruction0200.class, PARAMETER_INSTRUCTION);
        return (param == null) ? "<instruction text param is NULL>" : param.getColumn1();
    }

    /**
     * @return the configuration parameter for Account configuration
     */
    public MESParamExcptEnableDef0200 getAccountConfig() {
        return executor.getProcessParameterData(MESParamExcptEnableDef0200.class, ParamClassConstants0400.PARAMETER_ACC_CONFIG);
    }

    /**
     * @return account calculation type
     */
    public AccountCalculation0200 getCalcConfig() {
        MESParamAccountCalc0200 calcConfig = executor.getProcessParameterData(MESParamAccountCalc0200.class,
                ParamClassConstants0400.PARAMETER_CALC_CONFIG);
        return AccountCalculation0200.valueOf(calcConfig.getResult());
    }

    /**
     * @return whether loop mode is enabled for this phase
     */
    public boolean isLoopEnabled() {
        MESParamBoolean0100 param = executor.getProcessParameterData(MESParamBoolean0100.class, ParamClassConstants0400.PARAMETER_ENABLE_LOOP);
        return param.getEnabled() != null && param.getEnabled();
    }

    /**
     * @return whether it is allowed to exit the loop without actually closing the positions and performing checks
     * (makes sense in an ETO)
     */
    public boolean isLoopStopEnabled() {
        MESParamBoolean0100 param = executor.getProcessParameterData(MESParamBoolean0100.class, ParamClassConstants0400.PARAMETER_ALLOW_LOOP_STOP);
        return param.getEnabled() != null && param.getEnabled();
    }

    @Override
    public RiskClass getExceptionRiskFromParameter(String exceptionParameterName) {
        if (exceptionParameterName.equals(ParamClassConstants0400.PARAMETER_ACC_CONFIG)) {
            // the Account Config param has a different parameter class than the default
            MESParamExcptEnableDef0200 param = executor.getProcessParameterData(MESParamExcptEnableDef0200.class, exceptionParameterName);
            return RiskClass.valueOf(param.getRiskAssessment());
        } else {
            return super.getExceptionRiskFromParameter(exceptionParameterName);
        }
    }

    @Override
    public String getExceptionTextFromParameter(String exceptionParameterName) {
        if (exceptionParameterName.equals(ParamClassConstants0400.PARAMETER_ACC_CONFIG)) {
            // the Account Config param has a different parameter class than the default
            MESParamExcptEnableDef0200 param = executor.getProcessParameterData(MESParamExcptEnableDef0200.class, exceptionParameterName);
            return param.getMessage();
        } else {
            return super.getExceptionTextFromParameter(exceptionParameterName);
        }
    }

    /**
     * @return whether the sampled and waste quantities should be included in the total quantities for completion checks
     */
    public boolean getIncludeSampleAndWaste() {
        MESParamBoolean0100 param =
                executor.getProcessParameterData(MESParamBoolean0100.class, ParamClassConstants0400.PARAMETER_ACC_INCLUDE_SAMPLE_WASTE);
        return param.getEnabled() != null && param.getEnabled();
    }

    /**
     * Set the phase output
     */
    public void setPhaseOutputData() {
        MESRtPhaseOutputMatAlterAcct0010 rtPhaseOutput = getRtPhaseOutput();
        rtPhaseOutput.setResult(phaseResult.getKey());
    }

    /**
     * @return the list of accounted sublots
     */
    public List<AccountMaterialDAO0710> getMaterialList() {
        return materials;
    }

    @Override
    protected void addNewInputMaterial(IMESMaterialParameter matParam) {
        // Used by Preview
        for (int i = 0; i < matInputList.size(); i++) {
            AccountMaterialDAO0710 mat = matInputList.get(i);
            if (mat.getMaterialID().equals(matParam.getMaterialIdentifier())) {
                return;
            }
        }

        AccountMaterialDAO0710 material = new AccountMaterialDAO0710();
        material.setIsHeader(true);
        material.setAccounted(false);
        material.setAccountComplete(false);
        material.setMaterialID(matParam.getMaterialIdentifier());
        material.setDescription(matParam.getMaterialShortDescription());
        material.setDefaultUoM(MeasuredValueUtilities.createUoM(matParam.getMaterial().getUnitOfMeasure()));
        // MFC Position in material parameter is mostly empty.
        material.setMfcPosition(matParam.getMFCPosition());
        matInputList.add(material);
    }

    @Override
    protected boolean shallPlannedQtyModeInit() {
        return true;
    }

    @Override
    protected boolean isInput(IMESMaterialParameter matParam) {
        return TYPE.INPUT.equals(matParam.getType());
    }

    @Override
    protected void refreshMaterialsList() {
        if (getStatus() == Status.COMPLETED) {
            initMaterialsListFromPhaseData();
        } else {
            initOSI();
            initMaterialsList();
        }
    }

    private void initMaterialsList() {
        materials = new ArrayList<>();

        for (AccountMaterialDAO0710 matParam : matInputList) {
            // reset the totals from the last loading
            matParam.setIdentifiedQtyMV(null);
            matParam.setQtyMV(AccountType.CONSUMED, null);
            matParam.setQtyMV(AccountType.WASTED, null);
            matParam.setQtyMV(AccountType.SAMPLED, null);
            matParam.setQtyMV(AccountType.RETURNED, null);
            matParam.setCountIdentifiedSublots(0); // reset the counter

            materials.add(matParam);
            if (getStatus() == Status.PREVIEW) {
                continue;
            }
            // first, the sublots are added to the map according to their batch, so that they can be ordered in the
            // grid
            Map<String, List<AccountMaterialDAO0710>> map = new HashMap<>();
            final OrderStepInput masterOSI = getMasterOSIForHeaderRow(matParam);
            //dustin:状态赋值
            matParam.setStatusString(getDisplayStringQtyRangeConditionAndCompletedInfo(masterOSI));
            addIdentifiedSublotsToMap(masterOSI, matParam, map);

            map.keySet().stream().sorted().forEach(batchId -> {
                AccountMaterialDAO0710 batch = new AccountMaterialDAO0710();
                batch.setIsHeader(true);
                batch.setBatch(batchId);
                // set the key of the master OSI
                batch.setOsiKey(matParam.getOsiKey());
                if (masterOSI != null) {
                    batch.setMfcPosition(MESNamedUDAOrderStepInput.getPosition(masterOSI));
                }
                materials.add(batch);
                map.get(batchId).stream().sorted((o1, o2) -> o1.getSublot().compareTo(o2.getSublot())).forEach(materials::add);
            });

        }
    }

    @Override
    public boolean init() {
        boolean result = super.init();
        initModifiedFlagFromPhaseData();
        initPhaseResult();
        return result;
    }

    @Override
    public void initMatInputList() {
        if (os == null) {
            matInputList = new ArrayList<>();
            super.initMatInputList();
            sortMaterialByMFCPos(matInputList);
        } else {
            initMatOSIList();
        }
    }

    /**
     * Initialize the list with OSI data
     */
    private void initMatOSIList() {
        initMapOfMasterOSIs();
        // When predecessor completes during phase is not running or message comes in between two phase instances
        // (continue), no message comes to trigger missing update
        updateTotalAccountStatus();

        matInputList = new ArrayList<>();
        List<OrderStepInput> allMaterOSIs = getMasterOSIsForPhase();
        // if there are multiple master OSIs for the same material: create a separate header for each
        for (OrderStepInput osi : allMaterOSIs) {
            AccountMaterialDAO0710 material = new AccountMaterialDAO0710();
            material.setIsHeader(true);
            material.setAccounted(false);
            material.setMaterialID(osi.getPart().getPartNumber());
            material.setDescription(MESNamedUDAPart.getShortDescription(osi.getPart()));
            material.setDefaultUoM(MESNamedUDAPart.getUnitOfMeasure(osi.getPart()));
            setPlannedQtyWithLimits(material, osi);
            material.setMfcPosition(MESNamedUDAOrderStepInput.getPosition(osi));
            material.setStatusString(getDisplayStringQtyRangeConditionAndCompletedInfo(osi));
            material.setOsiKey(osi.getKey());

            matInputList.add(material);
        }
    }

    private void initMapOfMasterOSIs() {
        // Sorting masterOSIsOfMaterial control the sorting in the grid
        // use a linked hash map to preserve the order: we want the grid to be sorted
        masterOSIsOfMaterial = new LinkedHashMap<>();
        MESAllMasterOSIs masterOSIs = new MESAllMasterOSIs(os);
        masterOSIs.sortByPosition(true);
        List<IMESMaterialParameter> allMaterialParameters = executor.getPhase().getMaterialParameters();

        for (IMESMaterialParameter matParam : allMaterialParameters) {
            addCorrespondingOsiToUnsplittetOSIList(masterOSIs, matParam);
        }
        // To sort we need the MFC Position of the OSI. Thus we sort after getting the osi
        sortMasterOSIsOfMaterialByMFCPos();
    }

    private void addCorrespondingOsiToUnsplittetOSIList(MESAllMasterOSIs masterOSIs, IMESMaterialParameter matParam) {
        Part paramMat = matParam.getMaterial();
        String matMfcPosition = matParam.getMFCPosition();
        for (OrderStepInput osi : masterOSIs) {
            // add only OSIs for the same material if their MFC position corresponds to the one in the material
            // parameter, or no MFC position is specified in the material parameter
            Part osiMat = osi.getPart();
            final String osiMFCPos = MESNamedUDAOrderStepInput.getPosition(osi);
            if (osiMat.equals(paramMat) && (StringUtils.isEmpty(matMfcPosition) || matMfcPosition.equals(osiMFCPos))) {
                if (!masterOSIsOfMaterial.containsKey(osiMat.getPartNumber())) {
                    masterOSIsOfMaterial.put(osiMat.getPartNumber(), new ArrayList());
                }
                masterOSIsOfMaterial.get(osiMat.getPartNumber()).add(osi);
            }
        }
    }

    private void sortMasterOSIsOfMaterialByMFCPos() {
        Comparator<Entry<String, List<OrderStepInput>>> comparator = new Comparator<Entry<String, List<OrderStepInput>>>() {

            @Override
            public int compare(Entry<String, List<OrderStepInput>> entry1, Entry<String, List<OrderStepInput>> entry2) {
                OrderStepInput osi1 = getOSIFromEntry(entry1);
                OrderStepInput osi2 = getOSIFromEntry(entry2);
                String pos1 = MESNamedUDAOrderStepInput.getPosition(osi1);
                String pos2 = MESNamedUDAOrderStepInput.getPosition(osi2);
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
                    String materialID1 = osi1.getPart().getPartNumber();
                    String materialID2 = osi2.getPart().getPartNumber();
                    return ObjectUtils.compare(materialID1, materialID2);
                }
            }

            private OrderStepInput getOSIFromEntry(Entry<String, List<OrderStepInput>> entry) {
                List<OrderStepInput> osiList = entry.getValue();
                if (osiList.size() > 1) {
                    LOGGER.warn("Material Parameter is not uniquely referencing identifying material");
                }
                return osiList.get(0);
            }

        };
        List<Entry<String, List<OrderStepInput>>> entries = new ArrayList<>(masterOSIsOfMaterial.entrySet());
        Collections.sort(entries, comparator);
        masterOSIsOfMaterial = new LinkedHashMap<>();
        entries.stream().forEach(entry -> masterOSIsOfMaterial.put(entry.getKey(), entry.getValue()));
    }

    /**
     * Update the total account status after Dynamic AsProduced become static. If there are already consumptions made,
     * status is missing.
     */
    public void updateTotalAccountStatus() {
        List<OrderStepInput> masterOsiSAsProduced =
                getMasterOSIsOfMaterialWithAsProduced().stream().filter(this::accountStatusNeedsUpdate).collect(Collectors.toList());
        if (masterOsiSAsProduced.isEmpty()) {
            return;
        }
        final IOrderStepExecutionService osExecSrv = MaterialHelper0710.getOrderStepExecutionService();

        try (OSILockCollection lockCollection = new OSILockCollection(masterOsiSAsProduced, getRtPhase())) {
            if (lockCollection.isOwnerChange()) {
                getOrderStep(true);
            }
            masterOsiSAsProduced.stream().forEach(osi -> {
                try {
                    osExecSrv.updateTotalAccountStatus(osi);
                } catch (MESException exc) {
                    throw new MESRuntimeException(exc);
                }
            });
            OrderStep os = getOrderStep(false);
            os.Save(null, "", PCContext.getDefaultAccessPrivilege());
        } catch (LockException | DatasweepException exc) {
            throw new MESRuntimeException(exc);
        }
    }

    private boolean accountStatusNeedsUpdate(final OrderStepInput masterOsi) {
        return (MESNamedUDAOrderStepInput.getTotalAccountStatus(masterOsi) == null)
                && !MeasuredValueUtilities.isNullOrZero(MESNamedUDAOrderStepInput.getTotalAccountedQuantity(masterOsi))
                && !MeasuredValueUtilities.isNullOrZero(masterOsi.getPlannedQuantity());
    }

    @Override
    public Map<String, List<OrderStepInput>> getMasterOSIsOfMaterial() {
        return masterOSIsOfMaterial;
    }

    @Override
    public List<OrderStepInput> getMasterOSIsForPhase() {
        List<OrderStepInput> list = new ArrayList<>();
        masterOSIsOfMaterial.values().forEach(list::addAll);
        return list;
    }

    private void addIdentifiedSublotsToMap(OrderStepInput masterOsi, AccountMaterialDAO0710 matParam, Map<String, List<AccountMaterialDAO0710>> map) {
        IOrderStepExecutionService osExecSrv = MaterialHelper0710.getOrderStepExecutionService();
        List<OrderStepInputSublot> identifiedOSISublots = osExecSrv.getIdentifiedOSISublots(masterOsi, false, false);
        for (OrderStepInputSublot osiSublot : identifiedOSISublots) {
            // if a sublot is released and no quantity was consumed: it was unidentified
            if (osiSublot.isReleased() && osiSublot.getConsumedQuantity() == null) {
                continue;
            }
            // init the AccountMaterialDAO0710 object
            AccountMaterialDAO0710 sublot = addOrderStepInputSublot(osiSublot, matParam, masterOsi.getPlannedQuantity().getUnitOfMeasure());
            // add the sublot to the map
            if (!map.containsKey(sublot.getBatch())) {
                map.put(sublot.getBatch(), new ArrayList<AccountMaterialDAO0710>());
            }
            map.get(sublot.getBatch()).add(sublot);
        }
    }

    // CHECKSTYLE:MethodLength:off (Reason: logically coherent)
    private AccountMaterialDAO0710 addOrderStepInputSublot(OrderStepInputSublot osiSublot, AccountMaterialDAO0710 matParam,
                                                           IUnitOfMeasure defaultUoM) {
        AccountMaterialDAO0710 sublot = new AccountMaterialDAO0710();
        sublot.setIsHeader(false);
        // if the sublot is released, this means it was accounted (caller has checked that there is consumed quantity)
        sublot.setAccountComplete(osiSublot.isReleased());

        // get the master OSI key from the header
        sublot.setOsiKey(matParam.getOsiKey());
        sublot.setMaterialID(matParam.getMaterialID());
        sublot.setDescription(matParam.getDescription());
        sublot.setOrderStepInput(osiSublot.getOrderStepInput());
        sublot.setBatch(osiSublot.getBatch().getUniqueName());
        sublot.setSublot(osiSublot.getSublot().getUniqueName());
        sublot.setMfcPosition(MESNamedUDAOrderStepInput.getPosition(osiSublot.getOrderStepInput()));
        sublot.setDefaultUoM(defaultUoM);

        Part part = PCContext.getFunctions().getPart(matParam.getMaterialID());
        IMeasuredValueConverter converter = MeasuredValueUtilities.getMVConverter(part);

        try {
            // if any of the quantities is different than null, accounting was already performed
            sublot.setAccounted(sublot.isAccountComplete() || osiSublot.getAccountedQuantity() != null);
            // if original identified quantity is not set (can be the case when identification
            // was performed with a previous version using the old data model) get it from the sublot
            IMeasuredValue identifiedQty = osiSublot.getOriginalIdentifiedQuantity() != null ? //
                    osiSublot.getOriginalIdentifiedQuantity() : osiSublot.getIdentifiedQuantity();
            sublot.setIdentifiedQtyMV(convertMeasuredValue(identifiedQty, defaultUoM, converter));
            sublot.setQtyMV(AccountType.CONSUMED, osiSublot.getConsumedQuantity());
            setQuantityInMaterialRow(defaultUoM, sublot, osiSublot.getSampledQuantity(), AccountType.SAMPLED);
            setQuantityInMaterialRow(defaultUoM, sublot, osiSublot.getWastedQuantity(), AccountType.WASTED);
            // returned quantity: quantity that was identified but not accounted
            if (sublot.isAccounted()) {
                sublot.setQtyMV(AccountType.RETURNED, MeasuredValueUtilities.subtract(identifiedQty, osiSublot.getAccountedQuantity(), converter));
            }
        } catch (Exception exc) {
            ProductPhaseSwingHelper.showErrorDlg(exc.getLocalizedMessage());
        }

        // add to the total identified qty for this material
        matParam.setIdentifiedQtyMV(MaterialHelper0710.addMV(matParam.getIdentifiedQtyMV(), sublot.getIdentifiedQtyMV()));
        // increment the counter of identified sublots for this material
        matParam.setCountIdentifiedSublots(matParam.getCountIdentifiedSublots() + 1);

        // add the accounted quantities to the totals for this material
        for (AccountType qtyType : AccountType.values()) {
            // convert all quantities to the default unit of measure for this order step
            IMeasuredValue quantity = sublot.getQtyMV(qtyType);
            if (quantity != null && !quantity.getUnitOfMeasure().equals(defaultUoM)) {
                try {
                    quantity = convertMeasuredValue(quantity, defaultUoM, converter);
                    sublot.setQtyMV(qtyType, quantity);
                } catch (Exception e) {
                    ProductPhaseSwingHelper.showErrorDlg(e.getLocalizedMessage());
                }
            }
            matParam.setQtyMV(qtyType, MaterialHelper0710.addMV(matParam.getQtyMV(qtyType), quantity));
        }

        return sublot;
    }

    private void setQuantityInMaterialRow(IUnitOfMeasure defaultUoM, AccountMaterialDAO0710 sublot, IMeasuredValue qtyFromOsi,
                                          AccountType accountType) {
        if (sublot.isAccounted() && qtyFromOsi == null) {
            // if there is no quantity: show 0
            sublot.setQtyMV(accountType, MeasuredValueUtilities.createMV(BigDecimal.valueOf(0), defaultUoM));
        } else {
            sublot.setQtyMV(accountType, qtyFromOsi);
        }
    }
    // CHECKSTYLE:MethodLength:on

    /**
     * @param quantity  the MV to convert
     * @param toUom     target UoM
     * @param converter material-specific converter
     * @return the converted MV
     * @throws MESIncompatibleUoMException if the conversion fails
     */
    public static IMeasuredValue convertMeasuredValue(IMeasuredValue quantity, IUnitOfMeasure toUom, IMeasuredValueConverter converter)
            throws MESIncompatibleUoMException {

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
            throw new MESIncompatibleUoMException("Cannot convert quantity '" + quantity + "' to unit of measure '" + toUom.getSymbol() + '"');
        }
        return convertedValue;
    }

    /**
     * Initialize the material list from the phase data
     */
    protected void initMaterialsListFromPhaseData() {
        List<MESRtPhaseDataMatAlterAcct0010> sortedPhaseData = getSortedMaterialListPhaseData();

        // re-init the matInputList as well, so that it contains the same as when the phase is active
        reinitMaterialsAndMatInputListFromPhaseData(sortedPhaseData);
    }

    private void reinitMaterialsAndMatInputListFromPhaseData(List<MESRtPhaseDataMatAlterAcct0010> sortedPhaseData) {
        materials = new ArrayList<>();
        matInputList.clear();
        String currentBatchID = null;
        for (MESRtPhaseDataMatAlterAcct0010 data : sortedPhaseData) {
            if (data.getBatchID() != null && !data.getBatchID().equals(currentBatchID)) {
                // new batch: create header entry
                AccountMaterialDAO0710 batch = new AccountMaterialDAO0710();
                batch.setIsHeader(true);
                batch.setBatch(data.getBatchID());
                materials.add(batch);
            }
            currentBatchID = data.getBatchID();

            AccountMaterialDAO0710 row = new AccountMaterialDAO0710();
            row.setMaterialID(getMaterialNameOfPhaseDataEntry(data));
            row.setDescription(data.getMaterialDescription());
            row.setMfcPosition(data.getMfcPos());
            row.setIsHeader(data.getIsHeader());
            row.setBatch(data.getBatchID());
            row.setSublot(data.getSublotID());
            row.setIdentifiedQtyMV(data.getIdentifiedQty());
            row.setQtyMV(AccountType.CONSUMED, data.getConsumedQty());
            row.setQtyMV(AccountType.SAMPLED, data.getSampledQty());
            row.setQtyMV(AccountType.WASTED, data.getWastedQty());
            row.setQtyMV(AccountType.RETURNED, data.getReturnedQty());
            row.setPlannedQtyWithLimits(data.getPlannedQtyWithLimits());
            row.setStatusString(data.getAccountingStatus());

            if (row.isHeader()) {
                matInputList.add(row);
            }
            materials.add(row);
        }
    }

    private List<MESRtPhaseDataMatAlterAcct0010> getSortedMaterialListPhaseData() {
        List<MESRtPhaseDataMatAlterAcct0010> rtPhaseData = getAllRtPhaseData();
        // sort the phase data
        List<String> materialIDsSorted = new ArrayList<>();
        for (AccountMaterialDAO0710 mat : matInputList) {
            materialIDsSorted.add(mat.getMaterialID());
        }
        // sort the phase data into another list
        return rtPhaseData.stream().sorted((o1, o2) -> compareDataRows(materialIDsSorted, o1, o2)).collect(Collectors.toList());
    }

    private int compareDataRows(List<String> materialIDsSorted, MESRtPhaseDataMatAlterAcct0010 o1, MESRtPhaseDataMatAlterAcct0010 o2) {
        if (!o1.getMaterialID().equals(o2.getMaterialID())) {
            String materialName1 = getMaterialNameOfPhaseDataEntry(o1);
            String materialName2 = getMaterialNameOfPhaseDataEntry(o2);
            // the rows belong to different material positions: first sort by 'pure' material ID (the material
            // name)
            if (!materialName1.equals(materialName2)) {
                // for sorting of materials we use the sorting of the material parameters
                return materialIDsSorted.indexOf(materialName1) < materialIDsSorted.indexOf(materialName2) ? -1 : 1;
            }
            // if the two positions are for the same material: sort by the material ID as stored in the phase
            // data (includes OSI key)
            return o1.getMaterialID().compareTo(o2.getMaterialID());
        }
        // next put the header on top
        if (!o1.getIsHeader().equals(o2.getIsHeader())) {
            return o1.getIsHeader() ? -1 : 1;
        }
        // next sort by batch id
        if (!StringUtils.equals(o1.getBatchID(), o2.getBatchID())) {
            return (o1.getBatchID() == null) ? -1 : o1.getBatchID().compareTo(o2.getBatchID());
        }
        // last, sort by sublot id
        return (o1.getSublotID() == null) ? -1 : o1.getSublotID().compareTo(o2.getSublotID());
    }

    private String getMaterialNameOfPhaseDataEntry(MESRtPhaseDataMatAlterAcct0010 data) {
        return getMaterialNameOfPhaseData(data.getMaterialID());
    }

    /**
     * @param materialID Column MaterialID of Phase Data
     * @return material name e.g D001-01 part of phase data
     */
    public static String getMaterialNameOfPhaseData(String materialID) {
        return materialID.contains(MATERIAL_ID_DELIMITER_IN_DATA) ? materialID.split(MATERIAL_ID_DELIMITER_IN_DATA)[1] : materialID;
    }

    /**
     * Refresh the fields of the header rows that could have been modified from another phase and are needed to generate
     * a correct phase data
     */
    public void refreshHeaderRowsOnComplete() {
        for (AccountMaterialDAO0710 material : matInputList) {
            OrderStepInput masterOSI = getMasterOSIForHeaderRow(material);
            material.setStatusString(getDisplayStringQtyRangeConditionAndCompletedInfo(masterOSI));
        }
    }

    /**
     * @return true if the positions should be closed
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
        MESRtPhaseOutputMatAlterAcct0010 rtPhaseOutput = getRtPhaseOutput();
        if (rtPhaseOutput != null && StringUtils.isNotEmpty(rtPhaseOutput.getResult())) {
            phaseResult = PhaseResult0710.valueOf(rtPhaseOutput.getResult());
        } else {
            phaseResult = isLoopEnabled() ? PhaseResult0710.CONTINUE : PhaseResult0710.DONE;
        }
    }

    /**
     * @return {@code true} if changes were performed in this phase instance
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * @param isModified {@code true} if changes were performed in this phase instance
     */
    public void setModified(boolean isModified) {
        modified = isModified;
    }

    private void initModifiedFlagFromPhaseData() {
        if ((getStatus() == Status.PREVIEW) || modified) {
            // already initialized
            return;
        }
        //System.out.println(MESRtPhaseDataMatAlterAcct0010::getIsHeader);
        List<MESRtPhaseDataMatAlterAcct0010> headerData =
                getAllRtPhaseData().stream().filter(MESRtPhaseDataMatAlterAcct0010::getIsHeader).collect(Collectors.toList());
        for (MESRtPhaseDataMatAlterAcct0010 data : headerData) {
            if (Boolean.TRUE.equals(data.getIsModified())) {
                // if at least one data entry has the modified flag, the entire phase should be marked as modified
                modified = true;
                return;
            }
        }
    }

    /**
     * @param osi oder step input split position
     * @return The quantities accounted for this OSI in the current phase instance
     */
    public Map<TransactionSubtype, IMeasuredValue> getAccountedInThisPhaseInstance(OrderStepInput osi) {
        return qtysAccountedInPhaseInstance.get(osi.getKey());
    }

    /**
     * @param osi  the OSI for which was accounted
     * @param qtys the accounted quantities for this OSI in the current phase instance
     */
    public void addAccountedInThisPhaseInstance(OrderStepInput osi, Map<TransactionSubtype, IMeasuredValue> qtys) {
        qtysAccountedInPhaseInstance.put(osi.getKey(), qtys);
    }

    /**
     * Clear the in-memory information about quantities accounted in the current phase instance
     */
    public void clearQtysAccountedInPhaseInstance() {
        qtysAccountedInPhaseInstance.clear();
    }

    /**
     * 是否所有的子批次都已经消耗
     * @return
     */
    public List<String> isAllSublotConsume(List<AccountMaterialDAO0710> listAccountMaterial) {
        List<String> notConsumeSub = new ArrayList<>();
        for (AccountMaterialDAO0710 accountMaterialDao : listAccountMaterial) {
            if (accountMaterialDao.isHeader() || accountMaterialDao.getSublot() == null){
                continue;
            }
            if (accountMaterialDao.getConsumedQty() == null || "".equals(accountMaterialDao.getConsumedQty())) {
                notConsumeSub.add(accountMaterialDao.getSublot());
            }
        }
        return notConsumeSub;
    }
    /**
     * 检查组合组号比例是否一致
     * @return
     */
    public boolean checkCombineGroupIsEqualWithRate() {
        NotEqualRatePartList.clear();
        List<OrderStepInput> masterOSIToCheck = new ArrayList();
        List<OrderStepInput> allMasterOSIs = getMasterOSIsForPhase();
        masterOSIToCheck.addAll(allMasterOSIs.stream().filter(osi -> mustCheckQuantityForOsi(osi))
                .collect(Collectors.toList()));
        //dustin:获取物料参数配置集合
        List<IMESMaterialParameter> materialParameters = executor.getPhase().getMaterialParameters();
        for (OrderStepInput osi : masterOSIToCheck) {
            try {
                boolean result = getCombineGroupIsEqualWithRate(osi, allMasterOSIs, materialParameters);
                /*if (!result) {
                    return false;
                }*/
            } catch (MESIncompatibleUoMException e) {
                e.printStackTrace();
            }
        }
        if(NotEqualRatePartList.size() > 0 ) return false;
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
            return true;
        }
        MESNamedUDAMaterialParameter matParam = new MESNamedUDAMaterialParameter(matParamList.get(0));

        //获取替代组号
        String masterReplaceGroupName = matParam.getReplaceGroupName();
        //判断是否存在替代组号
        if (!StringUtils.isEmpty(masterReplaceGroupName)) {
            //获取替代物料集合(不能包含本身)
            Part material = matParam.getMatParam().getMaterial();
            List<IMESMaterialParameter> combinationGroupList = materialParameters.stream().filter(p -> {
                MESNamedUDAMaterialParameter replaceMatParam = new MESNamedUDAMaterialParameter(p);
                String replaceGroupName = replaceMatParam.getReplaceGroupName();
                String combinationGroup = replaceMatParam.getCombinationGroup();
                Part replaceMaterial = p.getMaterial();
                return masterReplaceGroupName.equals(replaceGroupName)
                        && !material.equals(replaceMaterial)
                        && StringUtils.isNotEmpty(combinationGroup);
            }).sorted((o1, o2) -> {
                MESNamedUDAMaterialParameter p1 = new MESNamedUDAMaterialParameter(o1);
                MESNamedUDAMaterialParameter p2 = new MESNamedUDAMaterialParameter(o2);
                return p1.getCombinationGroup().compareTo(p2.getCombinationGroup());
            }).collect(Collectors.toList());

            //替代消耗量
            IUnitOfMeasure unitOfMeasure = osi.getPlannedQuantity().getUnitOfMeasure();
            //遍历组合替代集合
            MeasuredValue firstConsumedMV = null;
            BigDecimal firstRatio = BigDecimal.ZERO;//组合比例
            String firstGroup = null;//组合组号
            for (IMESMaterialParameter item : combinationGroupList) {

                MESNamedUDAMaterialParameter itemMatParam = new MESNamedUDAMaterialParameter(item);
                BigDecimal replaceRatio = itemMatParam.getReplaceRatio();
                //获取组合组号
                String combinationGroup = itemMatParam.getCombinationGroup();
                if (StringUtils.isEmpty(firstGroup)) {

                    firstGroup = combinationGroup;
                    firstRatio = replaceRatio;
                }
                if (!StringUtils.equals(firstGroup, combinationGroup)) {
                    firstConsumedMV = null;
                    firstRatio = replaceRatio;
                }

                //根据OSI获取替代消耗数量
                List<OrderStepInput> consumedList = allMasterOSIs.stream().filter(p -> p.getPart() == item.getMaterial()).collect(Collectors.toList());
                MeasuredValue consumedQtyMV = MeasuredValueUtilities.createZero(unitOfMeasure);
                for (OrderStepInput replaceOSI : consumedList) {
                    consumedQtyMV = MESNamedUDAOrderStepInput.getTotalConsumedQuantity(replaceOSI);
                    if (consumedQtyMV == null || replaceRatio == null) {
                        consumedQtyMV = MeasuredValueUtilities.createZero(unitOfMeasure);
                        continue;
                    }
                }
                String[]  NotEqualRatePart= new String[3];
                if (firstConsumedMV == null) {
                    firstConsumedMV = consumedQtyMV;//消耗总量
                }
                //1的消耗*2的比例
                BigDecimal firstConsumedQty = firstConsumedMV.getValue();
                MeasuredValue firstCalcQtyMV = MeasuredValueUtilities.createMV(firstConsumedQty.multiply(replaceRatio),
                        firstConsumedMV.getUnitOfMeasure());
                //2的消耗*1的比例
                BigDecimal consumedQty = consumedQtyMV.getValue();
                MeasuredValue calcConsumedQtyMV = MeasuredValueUtilities.createMV(consumedQty.multiply(firstRatio),
                        consumedQtyMV.getUnitOfMeasure());
                if (MeasuredValueUtilities.compare(firstCalcQtyMV, calcConsumedQtyMV) != 0) {
                    //保存主料物料号
                    NotEqualRatePart[0] = matParamList.get(0).getMaterial().getPartNumber();
                    NotEqualRatePart[1] =  firstGroup;
                    NotEqualRatePart[2] =  item.getMaterial().getPartNumber();
                    NotEqualRatePartList.add(NotEqualRatePart);
//                    return false;
                }
            }
        }
        if (NotEqualRatePartList.size()>0) {
            return false;
        }
        return true;
    }

    public List<String[]> getNotEqualRatePartList() {
        return NotEqualRatePartList;
    }
}
