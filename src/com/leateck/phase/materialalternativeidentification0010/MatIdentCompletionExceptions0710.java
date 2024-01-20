package com.leateck.phase.materialalternativeidentification0010;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.rockwell.mes.services.s88.ifc.recipe.IMESMaterialParameter;
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
                return isAutoConsume(executor) && !getQuantitiesOutOfRange(executor).isEmpty();
            }

            private Boolean isAutoConsume(RtPhaseExecutorMatAlterIdent0010 executor) {
                return executor.getModel().getAutoConsume();
            }

            private Boolean isDone(RtPhaseExecutorMatAlterIdent0010 executor) {
                return executor.getModel().isPhaseResultDone();
            }

            @Override
            void createException(final MatIdentCompletionExceptions0710 exceptions) {
                final String additionalInfoHeader =
                        I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "QuantitiesOufOfRangeMsg");
                StringBuilder msg = new StringBuilder(additionalInfoHeader);
                List<ResultSet> result = getQuantitiesOutOfRange(exceptions.executor);
                result.forEach(res -> {
                    msg.append(StringConstants.LINE_BREAK);
                    msg.append(I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "PositionOutOfRangeDetailsMsg",
                            new Object[] { res.materialID, res.limitsAndPlannedQuantity.getPlannedQuantity(),
                                    getLowerLimitToShow(res.limitsAndPlannedQuantity), getUpperLimitToShow(res.limitsAndPlannedQuantity),
                                    res.consumedQuantity }));
                });
                String exceptionText = exceptions.executor.getModel().getExceptionTextFromParameter(getParameterName());
                RiskClass riskClass = exceptions.executor.getModel().getExceptionRiskFromParameter(getParameterName());
                exceptions.addSystemtriggeredException(exceptionText, riskClass, getCheckKey(), msg.toString());
            }

            private IMeasuredValue getLowerLimitToShow(LimitsAndPlannedQuantity limits) {
                return (limits.getLowerLimit() == null) ? limits.getPlannedQuantity() : limits.getLowerLimit();
            }

            private IMeasuredValue getUpperLimitToShow(LimitsAndPlannedQuantity limits) {
                return (limits.getUpperLimit() == null) ? limits.getPlannedQuantity() : limits.getUpperLimit();
            }

            private List<ResultSet> getQuantitiesOutOfRange(RtPhaseExecutorMatAlterIdent0010 executor) {
                IOrderStepExecutionService service = ServiceFactory.getService(IOrderStepExecutionService.class);
                // we only check the osi, for which sublots has been identified in this phase instance
                List<OrderStepInput> allCheckMasterOSIs = executor.getModel().getMasterOSISForLimitChecks();
                List<OrderStepInput> masterOSIToCheck = new ArrayList();
                masterOSIToCheck.addAll(allCheckMasterOSIs.stream().filter(osi -> mustCheckQuantityForOsi(executor, osi)).collect(Collectors.toList()));
                List<ResultSet> result = new ArrayList();
                //dustin:获取物料参数配置集合
                List<IMESMaterialParameter> materialParameters = executor.getPhase().getMaterialParameters();
                List<OrderStepInput> allMasterOSIs = executor.getModel().getMasterOSIsForPhase();
                for (OrderStepInput osi : masterOSIToCheck) {
                    try {
                        // For AsProduced at completion it is checked whether all sublots are identified. Via Force
                        // Completion exception this could be overriden. In that case we don't want to have a further
                        // system triggered exception. Furthermore normally phase cannot completed without having
                        // all sublots identified.
                        final boolean asProduced = isAsProduced(osi);
                        // the entire identified quantity must be in the range
//                        MeasuredValue totalIdentifiedQty= getTotalIdentifiedQuantity(osi);
                        MeasuredValue totalIdentifiedQty = executor.getModel().getSelfAndReplaceIdentifiedQty(osi, allMasterOSIs, materialParameters);
                        // But for completion of position at auto consume we want to know the range condition to
                        // complete position
                        boolean isOutOfRange = (!isAutoConsume(executor) && asProduced) ? false
                                : getIsOutOfRangeAndSaveRangeCondition(osi, totalIdentifiedQty, executor);

                        if (!asProduced && isOutOfRange) {
                            result.add(
                                    new ResultSet(osi.getPart().getPartNumber(), service.determineLimitsAndPlannedQuantity(osi), totalIdentifiedQty));
                        }

                    } catch (MESException e) {
                        throw new MESRuntimeException(e);
                    }
                }
                return result;
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
}
