package com.leateck.phase.accountalternativematerial0010;

import com.datasweep.compatibility.client.MeasuredValue;
import com.datasweep.compatibility.client.OrderStepInput;
import com.datasweep.compatibility.client.Part;
import com.datasweep.plantops.common.measuredvalue.IMeasuredValue;
import com.datasweep.plantops.common.measuredvalue.IUnitOfMeasure;
import com.jgoodies.common.base.Strings;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseSystemTriggeredExceptionsCollector;
import com.rockwell.mes.commons.base.ifc.exceptions.MESException;
import com.rockwell.mes.commons.base.ifc.exceptions.MESIncompatibleUoMException;
import com.rockwell.mes.commons.base.ifc.exceptions.MESRuntimeException;
import com.rockwell.mes.commons.base.ifc.functional.IMeasuredValueConverter;
import com.rockwell.mes.commons.base.ifc.functional.MeasuredValueUtilities;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.nameduda.MESNamedUDAOrderStepInput;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.commons.base.ifc.utility.StringConstants;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.IMESExceptionRecord.RiskClass;
import com.rockwell.mes.parameter.product.excptenabledef.MESParamExcptEnableDef0200;
import com.rockwell.mes.services.order.ifc.EnumOrderStepInputStatus;
import com.rockwell.mes.services.s88.ifc.recipe.IMESMaterialParameter;
import com.rockwell.mes.services.s88.ifc.recipe.PlannedQuantityMode;
import com.rockwell.mes.services.wip.ifc.IOrderStepExecutionService;
import com.rockwell.mes.services.wip.ifc.IOrderStepExecutionService.QuantityRangeCondition;
import com.rockwell.mes.services.wip.ifc.LimitsAndPlannedQuantity;
import com.rockwell.mes.shared.product.material.AccountMaterialDAO0710;
import com.rockwell.mes.shared0400.product.util.ParamClassConstants0400;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Extension of {@link PhaseSystemTriggeredExceptionsCollector}, which contains the specific system triggered exceptions
 * triggered on completion of this phase.
 * <p>
 *
 * @author ikoleva, (c) Copyright 2020 Rockwell Automation Technologies, Inc. All Rights Reserved.
 */
public class MatAccountCompletionExceptions0710 extends PhaseSystemTriggeredExceptionsCollector<RtPhaseExecutorMatAlterAcct0010> {
    /**
     * Enumeration containing all system triggered exceptions for this phase.
     */
    enum SystemtriggeredExceptionEnum {
        /**
         * Wrong number of items.
         */
        UnaccountedSublots(ParamClassConstants0400.PARAMETER_ACC_CONFIG) {
            @Override
            boolean conditionForSystemtriggeredException(RtPhaseExecutorMatAlterAcct0010 executor) {
                MESParamExcptEnableDef0200 accountConfig = executor.getModel().getAccountConfig();
                int unaccountedSublots = executor.getUnaccountedSublotsCount();
                return accountConfig.getEnabled() != null && accountConfig.getEnabled() && unaccountedSublots > 0;
            }

            @Override
            void createException(final MatAccountCompletionExceptions0710 exceptions) {
                final long unaccountedSublots = exceptions.executor.getUnaccountedSublotsCount();
                String additionalInfoText;
                if (unaccountedSublots == 1) {
                    additionalInfoText = I18nMessageUtility.getLocalizedMessage(AccountMaterialDAO0710.MSG_PACK, "UnaccountedRowMsg");
                } else {
                    additionalInfoText = I18nMessageUtility.getLocalizedMessage(AccountMaterialDAO0710.MSG_PACK, "UnaccountedRowsMsg",
                            new Object[]{unaccountedSublots});

                }
                String exceptionText = exceptions.executor.getModel().getExceptionTextFromParameter(getParameterName());
                RiskClass riskClass = exceptions.executor.getModel().getExceptionRiskFromParameter(getParameterName());
                exceptions.addSystemtriggeredException(exceptionText, riskClass, getCheckKey(), additionalInfoText);
            }
        },
        /**
         * Overweigh.
         */
        QuantityOutOfTolerance(ParamClassConstants0400.PARAMETER_QTY_OUF_OF_TOLERANCE) {
            /**
             * The result set
             * <p>
             *
             * @author ikoleva, (c) Copyright 2020 Rockwell Automation Technologies, Inc. All Rights Reserved.
             */
            final class ResultSet {
                private final Part material;

                private final LimitsAndPlannedQuantity limitsAndPlannedQuantity;

                private final MeasuredValue consumedQuantity;

                private final QuantityRangeCondition result;

                private ResultSet(Part mat, LimitsAndPlannedQuantity limits, MeasuredValue consumed, QuantityRangeCondition res) {
                    material = mat;
                    limitsAndPlannedQuantity = limits;
                    consumedQuantity = consumed;
                    result = res;
                }
            }

            @Override
            boolean conditionForSystemtriggeredException(RtPhaseExecutorMatAlterAcct0010 executor) {
                return !getQuantitiesOutOfRange(executor).isEmpty();
            }

            @Override
            void createException(final MatAccountCompletionExceptions0710 exceptions) {
                final String additionalInfoHeader =
                        I18nMessageUtility.getLocalizedMessage(AccountMaterialDAO0710.MSG_PACK, "QuantitiesOufOfRangeMsg");
                StringBuilder msg = new StringBuilder(additionalInfoHeader);
                List<ResultSet> result = getQuantitiesOutOfRange(exceptions.executor);
                result.forEach(res -> {
                    msg.append(StringConstants.LINE_BREAK);
                    final IMeasuredValue plannedQuantity = res.limitsAndPlannedQuantity.getPlannedQuantity();
                    final MeasuredValue accountedQuantity = res.consumedQuantity != null ? res.consumedQuantity
                            : MeasuredValueUtilities.createMV(BigDecimal.ZERO, plannedQuantity.getUnitOfMeasure());
                    IMeasuredValue convertedAccountedQuantity;
                    try {
                        convertedAccountedQuantity = MatAccountModel0710.convertMeasuredValue(accountedQuantity, plannedQuantity.getUnitOfMeasure(),
                                MeasuredValueUtilities.getMVConverter(res.material));
                    } catch (MESIncompatibleUoMException e) {
                        throw new MESRuntimeException(e);
                    }
                    final String consumedQuantityLocalized = MeasuredValueUtilities.toDisplayString(convertedAccountedQuantity);
                    if (res.result == QuantityRangeCondition.UNDEFINED) {
                        msg.append(I18nMessageUtility.getLocalizedMessage(AccountMaterialDAO0710.MSG_PACK, "PlannedQtyUndefinedDetailsMsg",
                                new Object[]{res.material.getPartNumber(), consumedQuantityLocalized}));
                    } else {
                        final String plannedQuantityLocalized = MeasuredValueUtilities.toDisplayString(plannedQuantity);
                        final String lowerLimitLocalized = MeasuredValueUtilities.toDisplayString(res.limitsAndPlannedQuantity.getLowerLimit());
                        final String upperLimitLocalized = MeasuredValueUtilities.toDisplayString(res.limitsAndPlannedQuantity.getUpperLimit());
                        if (StringUtils.isEmpty(lowerLimitLocalized) && StringUtils.isEmpty(upperLimitLocalized)) {
                            msg.append(I18nMessageUtility.getLocalizedMessage(AccountMaterialDAO0710.MSG_PACK, "PosOutOfRangeNoTolDetailsMsg",
                                    new Object[]{res.material.getPartNumber(), plannedQuantityLocalized, //
                                            consumedQuantityLocalized}));
                        } else {
                            msg.append(I18nMessageUtility.getLocalizedMessage(AccountMaterialDAO0710.MSG_PACK, "PositionOutOfRangeDetailsMsg",
                                    new Object[]{res.material.getPartNumber(), plannedQuantityLocalized, //
                                            lowerLimitLocalized, upperLimitLocalized, consumedQuantityLocalized}));
                        }
                    }
                });
                String exceptionText = exceptions.executor.getModel().getExceptionTextFromParameter(getParameterName());
                RiskClass riskClass = exceptions.executor.getModel().getExceptionRiskFromParameter(getParameterName());
                exceptions.addSystemtriggeredException(exceptionText, riskClass, getCheckKey(), msg.toString());
            }

            private List<ResultSet> getQuantitiesOutOfRange(RtPhaseExecutorMatAlterAcct0010 executor) {
                IOrderStepExecutionService service = ServiceFactory.getService(IOrderStepExecutionService.class);
                List<OrderStepInput> masterOSIToCheck = new ArrayList();
                List<OrderStepInput> allMasterOSIs = executor.getModel().getMasterOSIsForPhase();
                masterOSIToCheck.addAll(allMasterOSIs.stream().filter(osi -> mustCheckQuantityForOsi(executor, osi)).collect(Collectors.toList()));
                List<ResultSet> result = new ArrayList();
                //dustin:获取物料参数配置集合
                List<IMESMaterialParameter> materialParameters = executor.getPhase().getMaterialParameters();
                for (OrderStepInput osi : masterOSIToCheck) {
                    try {
                        //获取替代消耗数量和本身消耗数量总和
                        MeasuredValue totalConsumedQty = getSelfAndReplaceConsumedQty(osi, allMasterOSIs, materialParameters);
                        if (executor.getModel().getIncludeSampleAndWaste()) {
                            IMeasuredValueConverter converter = MeasuredValueUtilities.getMVConverter(osi.getPart());
                            totalConsumedQty = MeasuredValueUtilities.addArgsOptional(totalConsumedQty,
                                    MESNamedUDAOrderStepInput.getTotalSampledQuantity(osi), converter);
                            totalConsumedQty = MeasuredValueUtilities.addArgsOptional(totalConsumedQty,
                                    MESNamedUDAOrderStepInput.getTotalWastedQuantity(osi), converter);
                        }
                        if (totalConsumedQty == null) {
                            result.add(new ResultSet(osi.getPart(), service.determineLimitsAndPlannedQuantity(osi), null, null));
                        } else {
                            QuantityRangeCondition qtyCheckResult = service.checkQuantityInRange(osi, totalConsumedQty);
                            if (qtyCheckResult == QuantityRangeCondition.UNDEFINED || qtyCheckResult.isOutOfRange()) {
                                result.add(new ResultSet(osi.getPart(), service.determineLimitsAndPlannedQuantity(osi), totalConsumedQty,
                                        qtyCheckResult));
                            }
                        }


                    } catch (MESException e) {
                        throw new MESRuntimeException(e);
                    }
                }
                return result;
            }

            private boolean mustCheckQuantityForOsi(RtPhaseExecutorMatAlterAcct0010 executor, OrderStepInput osi) {
                // check master OSIs with planned quantity that are not finished yet
                return !PlannedQuantityMode.NONE.equals(executor.getModel().getPlannedQuantityMode(osi))
                        && !EnumOrderStepInputStatus.isFinishedStatus(MESNamedUDAOrderStepInput.getStatus(osi));
            }
        };

        private final String parameterName;

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
        abstract boolean conditionForSystemtriggeredException(RtPhaseExecutorMatAlterAcct0010 executor);

        /**
         * Creates a new system triggered exception within the given exception collection.
         * <p>
         *
         * @param exceptions the collection holding all system triggered exceptions
         */
        abstract void createException(final MatAccountCompletionExceptions0710 exceptions);

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
    MatAccountCompletionExceptions0710(final RtPhaseExecutorMatAlterAcct0010 executor) {
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
            if (e.conditionForSystemtriggeredException(executor) && isNotSigned(e.getCheckKey())) {
                e.createException(this);
            }
        }
        // If we don't show an exception dialog, then all checks have passed successfully.
        // We could remove pending checkkeys.
        return !showExceptionDialog();
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

    /**
     * dustin:获取主料及其对应的替代料数量
     *
     * @param osi
     * @param allMasterOSIs
     * @param materialParameters
     * @return
     */
    private static MeasuredValue getSelfAndReplaceConsumedQty(OrderStepInput osi, List<OrderStepInput> allMasterOSIs,
                                                              List<IMESMaterialParameter> materialParameters) throws MESIncompatibleUoMException {
        //获取本身消耗数量
        MeasuredValue totalConsumedQtyMV = MESNamedUDAOrderStepInput.getTotalConsumedQuantity(osi);
        /**计算替代料数量**/
        //获取该物料是否设置为主料
        List<IMESMaterialParameter> matParamList = materialParameters.stream()
                .filter(p -> p.getMaterial() == osi.getPart()
                        && p.getATRow().getValue("SCL_isMainPart") != null
                        && (Boolean) p.getATRow().getValue("SCL_isMainPart")).collect(Collectors.toList());
        if (matParamList == null || matParamList.size() == 0) {
            return totalConsumedQtyMV;
        }
        MESNamedUDAMaterialParameter matParam = new MESNamedUDAMaterialParameter(matParamList.get(0));
        //获取换算比例
        BigDecimal mainRatio = matParam.getReplaceRatio();
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
            MeasuredValue replaceConsumedQtyMV = MeasuredValueUtilities.createZero(unitOfMeasure);
            //组合组号HashMap:组合组号-可消耗最小值
            Map<String, MeasuredValue> groupConsumedMap = new HashMap<>();
            //遍历替代料集合
            for (IMESMaterialParameter item : replaceGroupNameList) {
                MESNamedUDAMaterialParameter itemMatParam = new MESNamedUDAMaterialParameter(item);
                BigDecimal replaceRatio = itemMatParam.getReplaceRatio();
                //获取组合组号
                String combinationGroup = itemMatParam.getCombinationGroup();
                //根据OSI获取替代消耗数量
                List<OrderStepInput> consumedList = allMasterOSIs.stream().filter(p -> p.getPart() == item.getMaterial()).collect(Collectors.toList());
                for (OrderStepInput replaceOSI : consumedList) {
                    MeasuredValue consumedQtyMV = MESNamedUDAOrderStepInput.getTotalConsumedQuantity(replaceOSI);
                    if (consumedQtyMV == null || replaceRatio == null) {
                        groupConsumedMap.put(combinationGroup, MeasuredValueUtilities.createZero(unitOfMeasure));
                        continue;
                    }
                    BigDecimal consumedQty = consumedQtyMV.getValue();
                    BigDecimal calcConsumedQty = consumedQty.divide(replaceRatio).multiply(mainRatio);
                    MeasuredValue calcConsumedQtyMV = MeasuredValueUtilities.createMV(calcConsumedQty, consumedQtyMV.getUnitOfMeasure());
                    //组合组号是空，表示是完全替代料计算
                    if (Strings.isEmpty(combinationGroup)) {
                        replaceConsumedQtyMV = MeasuredValueUtilities.addArgsOptional(replaceConsumedQtyMV,
                                calcConsumedQtyMV, converter);
                    } else {
                        //组合组号计算，以最小值计算
                        MeasuredValue minConsumedQtyMV = groupConsumedMap.get(combinationGroup);
                        if (minConsumedQtyMV == null) {
                            groupConsumedMap.put(combinationGroup, calcConsumedQtyMV);
                            continue;
                        }
                        BigDecimal minConsumedQty = minConsumedQtyMV.getValue();
                        minConsumedQtyMV = calcConsumedQty.compareTo(minConsumedQty) < 0 ? calcConsumedQtyMV : minConsumedQtyMV;
                        groupConsumedMap.put(combinationGroup, minConsumedQtyMV);
                    }
                }
            }
            //增加组合组号数量
            if (groupConsumedMap != null) {
                for (MeasuredValue minConsumedQtyMV : groupConsumedMap.values()) {
                    replaceConsumedQtyMV = MeasuredValueUtilities.addArgsOptional(replaceConsumedQtyMV,
                            minConsumedQtyMV, converter);
                }
            }
            //替代料数量+本身数量
            if (totalConsumedQtyMV != null) {
                replaceConsumedQtyMV = MeasuredValueUtilities.addArgsOptional(totalConsumedQtyMV,
                        replaceConsumedQtyMV, converter);
            }
            totalConsumedQtyMV = replaceConsumedQtyMV;
        }
        return totalConsumedQtyMV;
    }
}
