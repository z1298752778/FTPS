package com.leateck.phase.wdmaterialidentification0100.checks;

import com.datasweep.compatibility.client.*;
import com.rockwell.mes.shared.phase.matident.check.*;
import com.rockwell.mes.parameter.product.wdbatchchecks.*;
import com.rockwell.mes.commons.parameter.matident.check.sublotcheckdef.*;
import com.rockwell.mes.commons.base.ifc.choicelist.*;
import java.util.*;
import com.rockwell.mes.parameter.product.wdcheckconfig.*;
import com.rockwell.mes.services.s88.ifc.recipe.*;
import com.rockwell.mes.shared.product.wd.checks.*;
import org.apache.commons.logging.*;

public class PhaseIdentificationCheckSuite0610 extends AbstractPhaseIdentificationCheckSuiteIdentifiedItem0610
{
    private static final Log LOGGER;
    private static final String MASTER_OSIS_SORTED = "MasterOSISContext";
    private static final String[] FIXED_IDENTIFICATION_CHECKS;

    public PhaseIdentificationCheckSuite0610(final List<? extends IS88Parameter> parameters, final List<OrderStepInput> masterOSIsSorted) {
        super((List)parameters, (Map)createContext(masterOSIsSorted));
    }

    private static Map<String, Object> createContext(final List<OrderStepInput> masterOSIsSorted) {
        HashMap var1 = new HashMap();
        var1.put("MasterOSISContext", masterOSIsSorted);
        return var1;
    }

    public static List<OrderStepInput> getMasterOSIsSortedFromContext(final AbstractPhaseIdentificationCheckWD0610 phaseIdentCheck) {
        return (List)phaseIdentCheck.getContext().get("MasterOSISContext");
    }

    protected void addFixedChecks(final Map<String, Object> context) {
        final String[] fixed_IDENTIFICATION_CHECKS = PhaseIdentificationCheckSuite0610.FIXED_IDENTIFICATION_CHECKS;
        for (int length = fixed_IDENTIFICATION_CHECKS.length, i = 0; i < length; ++i) {
            this.addIdentificationCheck(AbstractPhaseIdentificationCheck0610.CheckType.ERROR, fixed_IDENTIFICATION_CHECKS[i], null, null, context);
        }
    }

    private void addBatchStatusCheck(final String paramInstanceName, final PhaseCheckConfiguration0610 checkConfig, final MESParamWDBatchChecks0100 paramValues, final Map<String, Object> context) {
        List<Object> argParams = null;
        if (paramValues != null) {
            argParams = new ArrayList<Object>();
            argParams.add(paramValues.getMinimumBatchStatus());
        }
        this.getIdentChecks().add(new PhaseBatchStatusIdentificationCheck0610(AbstractPhaseIdentificationCheck0610.CheckType.EXCEPTION, paramInstanceName, checkConfig, argParams, context));
    }

    private void addSublotStatusCheck(final String paramInstanceName, final PhaseCheckConfiguration0610 checkConfig, final MESParamSublotCheckDef0100 paramValues, final Map<String, Object> context) {
        if (paramValues == null) {
            throw new IllegalArgumentException("paramValues must not be null");
        }
        this.getIdentChecks().add(new PhaseSublotStatusIdentificationCheck0610(AbstractPhaseIdentificationCheck0610.CheckType.EXCEPTION, paramInstanceName, checkConfig, MESChoiceListHelper.getChoiceElement("SublotStatus", paramValues.getMinSublotStatus()), context));
    }

    private void addBatchExpiryPassedCheck(final String paramInstanceName, final PhaseCheckConfiguration0610 checkConfig, final Map<String, Object> context) {
        ArrayList var4 = new ArrayList<>();
        var4.add(0L);
        this.getIdentChecks().add(new PhaseBatchExpiryDateIdentificationCheck0610(AbstractPhaseIdentificationCheck0610.CheckType.EXCEPTION, paramInstanceName, checkConfig, var4, context));
    }

    private void addBatchRetestPassedCheck(final String paramInstanceName, final PhaseCheckConfiguration0610 checkConfig, final Map<String, Object> context) {
        ArrayList var4 = new ArrayList<>();
        var4.add(0L);
        this.getIdentChecks().add(new PhaseBatchRetestDateIdentificationCheck0610(AbstractPhaseIdentificationCheck0610.CheckType.EXCEPTION, paramInstanceName, checkConfig, var4, context));
    }

    private void addBatchExpiryCheck(final String paramInstanceName, final PhaseCheckConfiguration0610 checkConfig, final MESParamWDBatchChecks0100 paramValues, final Map<String, Object> context) {
        List<Object> argParams = null;
        if (paramValues != null) {
            argParams = new ArrayList<Object>();
            argParams.add(paramValues.getMinimumDaysToExpire());
        }
        this.getIdentChecks().add(new PhaseBatchExpiryMinimumDateIdentificationCheck0610(AbstractPhaseIdentificationCheck0610.CheckType.EXCEPTION, paramInstanceName, checkConfig, argParams, context));
    }

    private void addBatchRetestCheck(final String paramInstanceName, final PhaseCheckConfiguration0610 checkConfig, final MESParamWDBatchChecks0100 paramValues, final Map<String, Object> context) {
        List<Object> argParams = null;
        if (paramValues != null) {
            argParams = new ArrayList<Object>();
            argParams.add(paramValues.getMinimumDaysToRetest());
        }
        this.getIdentChecks().add(new PhaseBatchRetestMinimumDateIdentificationCheck0610(AbstractPhaseIdentificationCheck0610.CheckType.EXCEPTION, paramInstanceName, checkConfig, argParams, context));
    }

    private void addUseByDateCheck(final String paramInstanceName, final PhaseCheckConfiguration0610 checkConfig, final Map<String, Object> context) {
        this.getIdentChecks().add(new PhaseSublotUseByDateIdentificationCheck0610(AbstractPhaseIdentificationCheck0610.CheckType.EXCEPTION, paramInstanceName, checkConfig, context));
    }

    protected void addConfigurableChecks(final List<? extends IS88Parameter> config, final Map<String, Object> context) {
        final MESParamWDBatchChecks0100 batchCheckDefinition = this.getParameterInstanceData(MESParamWDBatchChecks0100.class, config);
        for (final IS88Parameter is88Parameter : config) {
            if (is88Parameter instanceof IMESProcessParameterInstance) {
                this.addSingleConfigurableCheck(config, context, (IMESProcessParameterInstance)is88Parameter, batchCheckDefinition);
            }
        }
    }

    protected void addSingleConfigurableCheck(final List<? extends IS88Parameter> config, final Map<String, Object> context, final IMESProcessParameterInstance paramInstance, final MESParamWDBatchChecks0100 batchCheckDefinition) {
        final IMESProcessParameterData associatedProcessParameterData = paramInstance.getAssociatedProcessParameterData();
        final String identifier = paramInstance.getIdentifier();
        PhaseIdentificationCheckSuite0610.LOGGER.debug((Object)("ParamInstance: " + identifier + ", data: " + associatedProcessParameterData));
        if (associatedProcessParameterData instanceof MESParamWDCheckConfig0100 && this.isEnabled((MESParamWDCheckConfig0100)associatedProcessParameterData)) {
            final MESParamWDCheckConfig0100 mesParamWDCheckConfig0100 = (MESParamWDCheckConfig0100)associatedProcessParameterData;
            final PhaseCheckConfiguration0610 checkConfig = new PhaseCheckConfiguration0610(this.getMessagePack(), getRiskAssessment(mesParamWDCheckConfig0100), getExceptionText(mesParamWDCheckConfig0100));
            if (identifier.equals("Batch status check configuration") && batchCheckDefinition != null && batchCheckDefinition.getMinimumBatchStatus() != null) {
                this.addBatchStatusCheck(identifier, checkConfig, batchCheckDefinition, context);
            }
            else if (identifier.equals("Allocation check configuration")) {
                this.getIdentChecks().add(new PhaseAllocationIdentificationCheck0610(AbstractPhaseIdentificationCheck0610.CheckType.EXCEPTION, identifier, checkConfig, null, context));
            }
            else if (identifier.equals("Expiry date passed configuration")) {
                this.addBatchExpiryPassedCheck(identifier, checkConfig, context);
            }
            else if (identifier.equals("Retest date passed configuration")) {
                this.addBatchRetestPassedCheck(identifier, checkConfig, context);
            }
            else if (identifier.equals("Minimum time to expire configuration") && batchCheckDefinition != null && batchCheckDefinition.getMinimumDaysToExpire() != null) {
                this.addBatchExpiryCheck(identifier, checkConfig, batchCheckDefinition, context);
            }
            else if (identifier.equals("Minimum time to retest configuration") && batchCheckDefinition != null && batchCheckDefinition.getMinimumDaysToRetest() != null) {
                this.addBatchRetestCheck(identifier, checkConfig, batchCheckDefinition, context);
            }
            else if (identifier.equals("Use-by date check configuration")) {
                this.addUseByDateCheck(identifier, checkConfig, context);
            }
            else if (identifier.equals("Sequence check configuration")) {
                this.getIdentChecks().add(new PhaseMFCPositionSequenceCheck0610(AbstractPhaseIdentificationCheck0610.CheckType.EXCEPTION, identifier, checkConfig, context));
            }
            else if (identifier.equals("Sublot status check configuration")) {
                final MESParamSublotCheckDef0100 paramValues = this.getParameterInstanceData(MESParamSublotCheckDef0100.class, config);
                if (paramValues != null && paramValues.getMinSublotStatus() != null) {
                    this.addSublotStatusCheck(identifier, checkConfig, paramValues, context);
                }
            }
        }
    }

    private static long getRiskAssessment(final MESParamWDCheckConfig0100 validationDef) {
        return (validationDef != null) ? validationDef.getRiskAssessment() : 0L;
    }

    private static String getExceptionText(final MESParamWDCheckConfig0100 validationDef) {
        return (validationDef != null) ? validationDef.getExceptionText() : "";
    }

    private boolean isEnabled(final MESParamWDCheckConfig0100 valDef) {
        return this.isEnabled(valDef.getEnabled());
    }

    private <T extends IMESProcessParameterData> T getParameterInstanceData(final Class<T> clazz, final List<? extends IS88Parameter> config) {
        for (final IS88Parameter is88Parameter : config) {
            if (is88Parameter instanceof IMESProcessParameterInstance) {
                final IMESProcessParameterData associatedProcessParameterData = ((IMESProcessParameterInstance)is88Parameter).getAssociatedProcessParameterData();
                if (clazz.isInstance(associatedProcessParameterData)) {
                    return (T)associatedProcessParameterData;
                }
                continue;
            }
        }
        return null;
    }

    private void addIdentificationCheck(final AbstractPhaseIdentificationCheck0610.CheckType type, final String beanName, final String argCheckKey, final List<Object> params, final Map<String, Object> context) {
        this.getIdentChecks().add(new PhaseIdentificationCheckIdentifiedItem0610(type, beanName, argCheckKey, (PhaseCheckConfiguration0610)null, (List)params, (Map)context));
    }

    static {
        LOGGER = LogFactory.getLog((Class)PhaseIdentificationCheckSuite0610.class);
        FIXED_IDENTIFICATION_CHECKS = new String[] { "CheckBatchIdentificationAllowed", "CheckActive", "CheckActivePotencyAndWeight", "CheckValidWeighingType", "CheckCompensator", "CheckFiller", "CheckSublotDeleted", "CheckPlannedQuantityAndTolerances", "CheckQuantityCalculations", "CheckMaterialForIntermediateWDOSI", "CheckSublotForIntermediateWasNotReplaced", "CheckContinuousIdentificationAllowed", "CheckBOMSpecificMaterial", "CheckSublotQuantity", "CheckSublotProducedByOtherOS", "CheckSublotBlockedByProcessing", "CheckTreatmentID" };
    }
}
