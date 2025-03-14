package com.leateck.phase.materialalternativeidentification0010;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.leateck.phase.accountalternativematerial0010.LcAccountMaterialDAO0710;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.PhaseQuestionDialog;
import com.rockwell.mes.commons.parameter.exceptiondef.MESParamExceptionDef0300;
import com.rockwell.mes.parameter.product.excptenabledef.MESParamExcptEnableDef0200;
import com.rockwell.mes.services.s88.ifc.recipe.IMESMaterialParameter;
import com.rockwell.mes.shared.product.material.MaterialModel0710;
import org.apache.commons.lang3.StringUtils;

import com.datasweep.compatibility.client.MeasuredValue;
import com.datasweep.compatibility.client.OrderStepInput;
import com.datasweep.plantops.common.measuredvalue.IMeasuredValue;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseSystemTriggeredExceptionsCollector;
import com.rockwell.mes.commons.base.ifc.exceptions.MESException;
import com.rockwell.mes.commons.base.ifc.exceptions.MESRuntimeException;
import com.rockwell.mes.commons.base.ifc.functional.MeasuredValueUtilities;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.nameduda.MESNamedUDAOrderStepInput;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.commons.base.ifc.utility.StringConstants;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.IMESExceptionRecord.RiskClass;
import com.rockwell.mes.services.s88.ifc.recipe.PlannedQuantityMode;
import com.rockwell.mes.services.wip.ifc.IOrderStepExecutionService;
import com.rockwell.mes.services.wip.ifc.IOrderStepExecutionService.QuantityRangeCondition;
import com.rockwell.mes.services.wip.ifc.LimitsAndPlannedQuantity;
import com.rockwell.mes.shared.product.material.AccountMaterialDAO0710;
import com.rockwell.mes.shared0400.product.util.ParamClassConstants0400;

import javax.xml.transform.Result;


/**
 * Extension of {@link PhaseSystemTriggeredExceptionsCollector}, which contains the specific system triggered exceptions
 * triggered on completion of this phase.
 * <p>
 * 
 * @author ikoleva, (c) Copyright 2020 Rockwell Automation Technologies, Inc. All Rights Reserved.
 */
public class MatIdentCompletionExceptions0710 extends PhaseSystemTriggeredExceptionsCollector<RtPhaseExecutorMatAlterIdent0010> {

    /** Enumeration containing all system triggered exceptions for this phase. */
    enum SystemtriggeredExceptionEnum {
        proportionalAnomaly(RtPhaseExecutorMatAlterIdent0010.PROPORTIONALANOMALY) {
            @Override
            boolean conditionForSystemtriggeredException(RtPhaseExecutorMatAlterIdent0010 executor) {
                /**
                 * 组合物料 组合比例不一致校验（开启自消耗才校验）
                 */
                if(executor.getModel().getAutoConsume()){
                    if (executor.getModel().checkCombineGroupIsEqualWithRate() == false) {
                        final String isContinueMsg = I18nMessageUtility.getLocalizedMessage(MaterialModel0710.PHASE_PRODUCT_MATERIAL_MSGPACK, "CombineGroupIsEqualWithRate_Error",new Object[]{RtPhaseExecutorMatAlterIdent0010.mainMaterial,RtPhaseExecutorMatAlterIdent0010.combineGroupName});
                        PhaseQuestionDialog questionDialog = new PhaseQuestionDialog();
                        int userChoice = questionDialog.showDialog(isContinueMsg);
                        flag = userChoice;
                        if (userChoice != 0) {
                            return false;
                        }else{
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            void createException(final MatIdentCompletionExceptions0710 exceptions) {
                String msg = I18nMessageUtility.getLocalizedMessage(MaterialModel0710.PHASE_PRODUCT_MATERIAL_MSGPACK, "CombineGroupIsEqualWithRate_Msg",new Object[]{RtPhaseExecutorMatAlterIdent0010.mainMaterial,RtPhaseExecutorMatAlterIdent0010.combineGroupName});
                MESParamExceptionDef0300 def = exceptions.executor.getProportionalAnomaly();
                String exceptionStr = def.getExceptionDescr() + "\r\n" + msg;
                long risk = def.getRiskAssessment();
                RiskClass riskclass = RiskClass.valueOf(risk);
                exceptions.executor.displayException(getCheckKey(),riskclass,exceptionStr);
            }
        },
        Insufficien(RtPhaseExecutorMatAlterIdent0010.INSUFFICIENTRECOGNITIONQUANTITY) {
            @Override
            boolean conditionForSystemtriggeredException(RtPhaseExecutorMatAlterIdent0010 executor) {
                /**
                 * 校验主料识别量是否满足计划量
                 * 不是自消耗：通过当前物料消耗量 / 替代比例 * 主料替代比例 = 主料识别量总量
                 */
                if(!executor.getModel().getAutoConsume()){
                    if (RtPhaseExecutorMatAlterIdent0010.masterOsiException != null && RtPhaseExecutorMatAlterIdent0010.totalConsumedQtyException != null){
                        BigDecimal plannedQuantity = RtPhaseExecutorMatAlterIdent0010.masterOsiException.getPlannedQuantity().getValue();
                        if(plannedQuantity != null && plannedQuantity.compareTo(BigDecimal.ZERO) != 0){
                            if((RtPhaseExecutorMatAlterIdent0010.totalConsumedQtyException.getValue()).compareTo(plannedQuantity) < 0){
                                //主料实际识别量不足
                                PhaseQuestionDialog questionDialog = new PhaseQuestionDialog();
                                int torf = questionDialog.showDialog(I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "MainMatInsufficientException", new Object[]{RtPhaseExecutorMatAlterIdent0010.masterOsiException.getPart().getPartNumber()}));
                                flag = torf;
                                if(torf != 0){
                                    return false;
                                }else{
                                    return true;
                                }
                            }
                        }
                    }
                }
                return false;
            }

            @Override
            void createException(final MatIdentCompletionExceptions0710 exceptions) {
                String msg = I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "MainMatInsufficientExceptionMsg", new Object[]{RtPhaseExecutorMatAlterIdent0010.masterOsiException.getPart().getPartNumber()});
                MESParamExceptionDef0300 def = exceptions.executor.getRecognitionQuantityInsufficient();
                String exceptionStr = def.getExceptionDescr() + "\r\n" + msg;
                long risk = def.getRiskAssessment();
                RiskClass riskclass = RiskClass.valueOf(risk);
                exceptions.executor.displayException(getCheckKey(),riskclass,exceptionStr);
            }
        },
        /** Overweigh. */
        QuantityOutOfTolerance(ParamClassConstants0400.PARAMETER_QTY_OUF_OF_TOLERANCE) {
            /**
             * The result set
             * <p>
             * 
             * @author ikoleva, (c) Copyright 2020 Rockwell Automation Technologies, Inc. All Rights Reserved.
             */
            final class ResultSet {
                private final String materialID;

                private final LimitsAndPlannedQuantity limitsAndPlannedQuantity;

                private final MeasuredValue consumedQuantity;


                private ResultSet(String material, LimitsAndPlannedQuantity limits, MeasuredValue consumed) {
                    materialID = material;
                    limitsAndPlannedQuantity = limits;
                    consumedQuantity = consumed;
                }
            }


            @Override
            boolean conditionForSystemtriggeredException(RtPhaseExecutorMatAlterIdent0010 executor) {
                return isAutoConsume(executor) && getQuantitiesOutOfRange(executor);
            }

            private Boolean isAutoConsume(RtPhaseExecutorMatAlterIdent0010 executor) {
                return executor.getModel().getAutoConsume();
            }

            private Boolean isDone(RtPhaseExecutorMatAlterIdent0010 executor) {
                return executor.getModel().isPhaseResultDone();
            }

            @Override
            void createException(final MatIdentCompletionExceptions0710 exceptions) {
                List<ResultSet> limitPlanned = new ArrayList<>();//保存消耗量到计划量
                List<ResultSet> limitPlannedRange = new ArrayList<>();//保存消耗量到计划量范围
                IOrderStepExecutionService service = ServiceFactory.getService(IOrderStepExecutionService.class);
                List<OrderStepInput> allCheckMasterOSIs = exceptions.executor.getModel().getMasterOSISForLimitChecks();
                List<OrderStepInput> masterOSIToCheck = new ArrayList();
                masterOSIToCheck.addAll(allCheckMasterOSIs.stream().filter(osi -> mustCheckQuantityForOsi(exceptions.executor, osi)).collect(Collectors.toList()));
                //dustin:获取物料参数配置集合
                List<IMESMaterialParameter> materialParameters = exceptions.executor.getPhase().getMaterialParameters();
                List<OrderStepInput> allMasterOSIs = exceptions.executor.getModel().getMasterOSIsForPhase();
                for (OrderStepInput osi : masterOSIToCheck) {
                    try {
                        final boolean asProduced = isAsProduced(osi);
                        //获取当前物料的限制类型
                        List<IMESMaterialParameter> matParamList = materialParameters.stream()
                                .filter(p -> p.getMaterial() == osi.getPart()
                                        && p.getATRow().getValue("LC_restrictionMode") != null).collect(Collectors.toList());
                        if (matParamList.size() > 0){
                            MESNamedUDAMaterialParameter matParam = new MESNamedUDAMaterialParameter(matParamList.get(0));
                            Long restrictionMode = matParam.getRestrictionMode();
                            if( null == restrictionMode){
                                continue;
                            }
                            //获识别量取消耗量（消耗量）
                            MeasuredValue totalIdentifiedQty = exceptions.executor.getModel().getSelfAndReplaceIdentifiedQty(osi, allMasterOSIs, materialParameters);
                            // But for completion of position at auto consume we want to know the range condition to
                            // complete position
                            boolean isOutOfRange = (!isAutoConsume(exceptions.executor) && asProduced) ? false
                                    : getIsOutOfRangeAndSaveRangeCondition(osi, totalIdentifiedQty,exceptions.executor);
                            if(!asProduced && isOutOfRange && restrictionMode == 20){
                                //计划量
                                limitPlanned.add(
                                        new ResultSet(osi.getPart().getPartNumber(), service.determineLimitsAndPlannedQuantity(osi), totalIdentifiedQty));
                            }
                            if(!asProduced && isOutOfRange && restrictionMode == 30){
                                //计划量模式
                                limitPlannedRange.add(
                                        new ResultSet(osi.getPart().getPartNumber(), service.determineLimitsAndPlannedQuantity(osi), totalIdentifiedQty));
                            }
                        }

                    } catch (MESException e) {
                        throw new MESRuntimeException(e);
                    }
                }

                StringBuffer msg = new StringBuffer();
                if(limitPlanned.size() > 0){
                    msg.append(I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "QuantitiesOufOfRangeMsg"));
                    limitPlanned.forEach(res -> {
                        msg.append(StringConstants.LINE_BREAK);
                        msg.append(I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "PositionOutOfRangeDetailsMsg",
                                new Object[] { res.materialID, res.limitsAndPlannedQuantity.getPlannedQuantity(),
                                        getLowerLimitToShow(res.limitsAndPlannedQuantity), getUpperLimitToShow(res.limitsAndPlannedQuantity),
                                        res.consumedQuantity }));
                    });
                }
                msg.append("\n");
                if(limitPlannedRange.size() > 0){
                    msg.append(I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "QuantitiesOutOfRangeModeMsg"));
                    limitPlannedRange.forEach(res -> {
                        msg.append(StringConstants.LINE_BREAK);
                        msg.append(I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "PositionOutOfRangeDetailsMsg",
                                new Object[] { res.materialID, res.limitsAndPlannedQuantity.getPlannedQuantity(),
                                        getLowerLimitToShow(res.limitsAndPlannedQuantity), getUpperLimitToShow(res.limitsAndPlannedQuantity),
                                        res.consumedQuantity }));
                    });
                }

                RiskClass riskClass = exceptions.executor.getModel().getExceptionRiskFromParameter(getParameterName());
                exceptions.executor.displayException(getCheckKey(),riskClass,msg.toString());
            }

            private IMeasuredValue getLowerLimitToShow(LimitsAndPlannedQuantity limits) {
                return (limits.getLowerLimit() == null) ? limits.getPlannedQuantity() : limits.getLowerLimit();
            }

            private IMeasuredValue getUpperLimitToShow(LimitsAndPlannedQuantity limits) {
                return (limits.getUpperLimit() == null) ? limits.getPlannedQuantity() : limits.getUpperLimit();
            }

            private boolean getQuantitiesOutOfRange(RtPhaseExecutorMatAlterIdent0010 executor) {
                List<ResultSet> limitPlanned = new ArrayList<>();//保存消耗量到计划量
                List<ResultSet> limitPlannedRange = new ArrayList<>();//保存消耗量到计划量范围
                IOrderStepExecutionService service = ServiceFactory.getService(IOrderStepExecutionService.class);
                // we only check the osi, for which sublots has been identified in this phase instance
                List<OrderStepInput> allCheckMasterOSIs = executor.getModel().getMasterOSISForLimitChecks();
                List<OrderStepInput> masterOSIToCheck = new ArrayList();
                masterOSIToCheck.addAll(allCheckMasterOSIs.stream().filter(osi -> mustCheckQuantityForOsi(executor, osi)).collect(Collectors.toList()));
                //dustin:获取物料参数配置集合
                List<IMESMaterialParameter> materialParameters = executor.getPhase().getMaterialParameters();
                List<OrderStepInput> allMasterOSIs = executor.getModel().getMasterOSIsForPhase();
                for (OrderStepInput osi : masterOSIToCheck) {
                    try {
                        // For AsProduced at completion it is checked whether all sublots are identified. Via Force
                        // Completion exception this could be overriden. In that case we don't want to have a further
                        // system triggered exception. Furthermore normally phase cannot completed without having
                        // all sublots identified.
                        //当前物料设置为 按照生产量模式 则返回true
                        boolean asProduced = isAsProduced(osi);
                        // the entire identified quantity must be in the range
//                        MeasuredValue totalIdentifiedQty= getTotalIdentifiedQuantity(osi);
                        /**
                         * LC
                         * 谭鑫
                         * 获取物料限制模式
                         *  无限制(null)：不做任何校验
                         *  计划量(10)：点击确认的时候 确认是否满足了计划量
                         *  计划量范围(20)：点击确认的时候，确认是否满足了计划量的上下限的范围
                         */
                        //获取当前物料的限制类型
                        List<IMESMaterialParameter> matParamList = materialParameters.stream()
                                .filter(p -> p.getMaterial() == osi.getPart()
                                        && p.getATRow().getValue("LC_restrictionMode") != null).collect(Collectors.toList());
                        if (matParamList.size()>0){
                            MESNamedUDAMaterialParameter matParam = new MESNamedUDAMaterialParameter(matParamList.get(0));
                            Long restrictionMode = matParam.getRestrictionMode();
                            if( null == restrictionMode){
                                continue;
                            }
                            //获识别量取消耗量（消耗量）
                            MeasuredValue totalIdentifiedQty = executor.getModel().getSelfAndReplaceIdentifiedQty(osi, allMasterOSIs, materialParameters);
                            // But for completion of position at auto consume we want to know the range condition to
                            // complete position
                            boolean isOutOfRange = (!isAutoConsume(executor) && asProduced) ? false
                                    : getIsOutOfRangeAndSaveRangeCondition(osi, totalIdentifiedQty, executor);
                            if(!asProduced && isOutOfRange && restrictionMode == 20){
                                //计划量
                                limitPlanned.add(
                                        new ResultSet(osi.getPart().getPartNumber(), service.determineLimitsAndPlannedQuantity(osi), totalIdentifiedQty));
                            }
                            if(!asProduced && isOutOfRange && restrictionMode == 30){
                                //计划量模式
                                limitPlannedRange.add(
                                        new ResultSet(osi.getPart().getPartNumber(), service.determineLimitsAndPlannedQuantity(osi), totalIdentifiedQty));
                            }
                        }

                    } catch (MESException e) {
                        throw new MESRuntimeException(e);
                    }
                }

                StringBuffer msg = new StringBuffer();
                if(limitPlanned.size() > 0){
                    msg.append(I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "QuantitiesOufOfRangeMsg"));
                    limitPlanned.forEach(res -> {
                        msg.append(StringConstants.LINE_BREAK);
                        msg.append(I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "PositionOutOfRangeDetailsMsg",
                                new Object[] { res.materialID, res.limitsAndPlannedQuantity.getPlannedQuantity(),
                                        getLowerLimitToShow(res.limitsAndPlannedQuantity), getUpperLimitToShow(res.limitsAndPlannedQuantity),
                                        res.consumedQuantity }));
                    });
                }
                msg.append("\n");
                if(limitPlannedRange.size() > 0){
                    msg.append(I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "QuantitiesOutOfRangeModeMsg"));
                    limitPlannedRange.forEach(res -> {
                        msg.append(StringConstants.LINE_BREAK);
                        msg.append(I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "PositionOutOfRangeDetailsMsg",
                                new Object[] { res.materialID, res.limitsAndPlannedQuantity.getPlannedQuantity(),
                                        getLowerLimitToShow(res.limitsAndPlannedQuantity), getUpperLimitToShow(res.limitsAndPlannedQuantity),
                                        res.consumedQuantity }));
                    });
                }
                msg.append("\n");
                if(limitPlanned.size() > 0 || limitPlannedRange.size() > 0){
                    msg.append(I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "RecordAnomalieDialog" ));
                    PhaseQuestionDialog questionDialog = new PhaseQuestionDialog();
                    int userChoice = questionDialog.showDialog(msg.toString());
                    flag = userChoice;
                    if (userChoice != 0) {
                        //点击否
                        return false;
                    }else {
                        //点击是
                        return true;
                    }
                }
                return false;
            }


            private MeasuredValue getTotalIdentifiedQuantity(final OrderStepInput masterOsi) {
                MeasuredValue totalIdentifiedQty = MESNamedUDAOrderStepInput.getTotalIdentifiedQuantity(masterOsi);
                return (totalIdentifiedQty == null) ? createZeroQty(masterOsi) : totalIdentifiedQty;
            }

            private boolean getIsOutOfRangeAndSaveRangeCondition(final OrderStepInput osi, MeasuredValue totalIdentifiedQty,
                    final RtPhaseExecutorMatAlterIdent0010 executor) throws MESException {
                boolean isOutOfRange = false;
                IOrderStepExecutionService service = ServiceFactory.getService(IOrderStepExecutionService.class);
                QuantityRangeCondition resultLimitCheck = service.checkQuantityInRange(osi, totalIdentifiedQty);
                // if continue completion we check only for upper out of range. if lower we want to identify more
                // in next phase instances
                isOutOfRange = isDone(executor) ? resultLimitCheck.isOutOfRange()
                        : QuantityRangeCondition.QUANTITY_UPPER_OUT_OF_RANGE.equals(resultLimitCheck);
                // save range condition to be used at auto consumption later on
                executor.getModel().setQuantityRangeCondition(osi, resultLimitCheck);
                return isOutOfRange;
            }

            private boolean isAsProduced(OrderStepInput osi) {
                return PlannedQuantityMode.AS_PRODUCED.getChoiceElement().equals(MESNamedUDAOrderStepInput.getPlannedQuantityMode(osi));
            }

            private boolean mustCheckQuantityForOsi(RtPhaseExecutorMatAlterIdent0010 executor, OrderStepInput osi) {
                // check master OSIs with planned quantity
                return !PlannedQuantityMode.NONE.equals(executor.getModel().getPlannedQuantityMode(osi));
            }

            private MeasuredValue createZeroQty(final OrderStepInput osi) {
                return MeasuredValueUtilities.createZero(MatIdentModel0710.getDefaultUoM(osi));
            }
        };

        private final String parameterName;

        public static int flag = -1;
        private SystemtriggeredExceptionEnum(final String name) {
            parameterName = name;
        }

        /**
         * Determine if condition to record a system triggered exception is fulfilled.
         * <p>
         * 
         * @param executor the executor
         * @return {@code true} if the condition for system triggered exception is fulfilled
         */
        abstract boolean conditionForSystemtriggeredException(RtPhaseExecutorMatAlterIdent0010 executor);

        /**
         * Creates a new system triggered exception within the given exception collection.
         * <p>
         * 
         * @param exceptions the collection holding all system triggered exceptions
         */
        abstract void createException(final MatIdentCompletionExceptions0710 exceptions);

        /**
         * @return the name of the parameter
         */
        String getParameterName() {
            return parameterName;
        }

        /**
         * @return the check key for this system triggered exception
         */
        String getCheckKey() {
            return name();
        }
    }

    /**
     * Constructor.
     * <p>
     * 
     * @param executor the executor
     */
    MatIdentCompletionExceptions0710(final RtPhaseExecutorMatAlterIdent0010 executor) {
        super(executor);
    }

    /**
     * Checks condition for any of the available system triggered exception and triggers a combined system triggered
     * exception if at least one exception condition is fulfilled.
     * <p>
     * 
     * @return {@code true} if no system triggered exception was triggered, i.e. all checks passed successfully
     */
    public boolean checkAndHandleExceptions() {
        for (final SystemtriggeredExceptionEnum e : SystemtriggeredExceptionEnum.values()) {
            if (isNotSigned(e.getCheckKey()) && e.conditionForSystemtriggeredException(executor)) {
                e.createException(this);
                if(SystemtriggeredExceptionEnum.proportionalAnomaly.equals(e)
                        || SystemtriggeredExceptionEnum.Insufficien.equals(e)
                || SystemtriggeredExceptionEnum.QuantityOutOfTolerance.equals(e)){
                    return false;
                }
                return !showExceptionDialog();
            }else if (SystemtriggeredExceptionEnum.proportionalAnomaly.equals(e) && SystemtriggeredExceptionEnum.proportionalAnomaly.flag == 2){
                SystemtriggeredExceptionEnum.proportionalAnomaly.flag = -1;
                return false;
            }else if(SystemtriggeredExceptionEnum.Insufficien.equals(e) && SystemtriggeredExceptionEnum.Insufficien.flag == 2){
                SystemtriggeredExceptionEnum.proportionalAnomaly.flag = -1;
                return false;
            } else if (SystemtriggeredExceptionEnum.QuantityOutOfTolerance.equals(e) && SystemtriggeredExceptionEnum.QuantityOutOfTolerance.flag == 2) {
                SystemtriggeredExceptionEnum.proportionalAnomaly.flag = -1;
                return false;
            }
        }
        // If we don't show an exception dialog, then all checks have passed successfully.
        // We could remove pending checkkeys.
        return true;
    }

    /**
     * @param checkKey the check key to test. It may be a concatenated check key
     * @return {@code true} if the check key or a part of it belongs to system triggered exception checkkey
     */
    public static boolean isCompletionCheck(final String checkKey) {
        final String[] checkKeys = StringUtils.split(checkKey, StringConstants.LINE_BREAK);
        for (final String key : checkKeys) {
            for (final SystemtriggeredExceptionEnum value : SystemtriggeredExceptionEnum.values()) {
                if (value.getCheckKey().equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected String getDialogTextForMultipleSystemtriggeredExceptions() {
        return I18nMessageUtility.getLocalizedMessage(AccountMaterialDAO0710.MSG_PACK, "MultipleSystemtriggeredExceptions_Text");
    }
}
