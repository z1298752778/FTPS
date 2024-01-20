package com.leateck.phase.materialalternativeidentification0010;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.choicelist.MESChoiceListHelper;
import com.rockwell.mes.commons.parameter.matident.check.sublotcheckdef.MESParamSublotCheckDef0100;
import com.rockwell.mes.parameter.product.batchcheckdef.MESParamBatchCheckDef0500;
import com.rockwell.mes.services.inventory.ifc.InventoryServiceProvider;
import com.rockwell.mes.services.inventory.ifc.SublotStatus;
import com.rockwell.mes.services.s88.ifc.recipe.IMESProcessParameterData;
import com.rockwell.mes.services.s88.ifc.recipe.IMESProcessParameterInstance;
import com.rockwell.mes.services.s88.ifc.recipe.IS88Parameter;
import com.rockwell.mes.services.wd.ifc.IWDOrderStepInputService;
import com.rockwell.mes.shared.product.material.ident.AbstractPhaseIdentificationCheckSuite0710;
import com.rockwell.mes.shared.product.material.ident.PhaseAllocationIdentificationCheck0710;
import com.rockwell.mes.shared.product.material.ident.PhaseBatchExpiryDateIdentificationCheck0710;
import com.rockwell.mes.shared.product.material.ident.PhaseBatchStatusIdentificationCheck0710;
import com.rockwell.mes.shared.product.material.ident.PhaseIdentificationCheck0710;
import com.rockwell.mes.shared.product.material.ident.PhaseIdentificationCheck0710.CheckType;
import com.rockwell.mes.shared.product.material.ident.PhaseSublotStatusIdentificationCheck0710;
import com.rockwell.mes.shared0200.product.util.IExceptionEnableDefinitionDAO;
import com.rockwell.mes.shared0400.product.util.ParamClassConstants0400;


/**
 * Identification checks data holder and setup
 * 
 * @author RBoyano, (c) Copyright 2014 Rockwell Automation Technologies, Inc. All Rights Reserved.
 */
public class PhaseIdentificationCheckSuite0710 extends AbstractPhaseIdentificationCheckSuite0710 {

    /**
     * Default constructor
     * 
     * @param config process parameters of the phase
     * @param context additional context for the identification check
     */
    public PhaseIdentificationCheckSuite0710(List<? extends IS88Parameter> config, Map<String, Object> context) {
        super(config, context);
    }

    /**
     * Add mandatory checks which result in errors
     * 
     * @param context additional context for the identification check
     */
    @Override
    protected void addFixedChecks(Map<String, Object> context) {
        addIdentificationCheckDAO(CheckType.ERROR, InventoryServiceProvider.SUBLOT_CHECK_BOM_SPECIFIC_MATERIAL_BEAN, null, null, context);
        addIdentificationCheckDAO(CheckType.ERROR, InventoryServiceProvider.SUBLOT_CHECK_SUBLOT_PRODUCED_OTHER_OS_BEAN, null, null, context);
        addIdentificationCheckDAO(CheckType.ERROR, InventoryServiceProvider.SUBLOT_CHECK_SUBLOT_BLOCKED_BY_PROCESSING_BEAN, null, null, context);
        addIdentificationCheckDAO(CheckType.ERROR, InventoryServiceProvider.SUBLOT_CHECK_MATERIAL_INTERMEDIATE_OSI_BEAN, null, null, context);
        addIdentificationCheckDAO(CheckType.ERROR, IWDOrderStepInputService.SUBLOT_CHECK_DELETED_BEAN, null, null, context);
        addIdentificationCheckDAO(CheckType.ERROR, IWDOrderStepInputService.SUBLOT_CHECK_MATERIAL_INTERMEDIATE_NOT_REPLACED_BEAN, //
                null, null, context);
        addIdentificationCheckDAO(CheckType.ERROR, IWDOrderStepInputService.SUBLOT_CHECK_PLANNED_QTY_AND_TOLERANCES_MAT_TRACKING_BEAN, null, null,
                context);
        addIdentificationCheckDAO(CheckType.ERROR, InventoryServiceProvider.SUBLOT_CHECK_QUANTITY_BEAN, null, null, context);
        addIdentificationCheckDAO(CheckType.ERROR, InventoryServiceProvider.TREATMENT_ID_CHECK_BATCHES_AND_ORDERS, null, null, context);
    }

    /**
     * Add configurable identification checks
     * 
     * @param config identification check configuration parameters of the phase
     * @param context additional context for the identification check
     */
    @Override
    protected void addConfigurableChecks(List<? extends IS88Parameter> config, Map<String, Object> context) {
        MESParamBatchCheckDef0500 batchCheckDefinition = getBatchCheckParam(config);
        for (IS88Parameter param : config) {
            if (param instanceof IMESProcessParameterInstance) {
                IMESProcessParameterInstance paramInstance = (IMESProcessParameterInstance) param;
                IMESProcessParameterData data = paramInstance.getAssociatedProcessParameterData();
                if (data instanceof IExceptionEnableDefinitionDAO && isEnabled((IExceptionEnableDefinitionDAO) data)) {
                    IExceptionEnableDefinitionDAO validationDef = (IExceptionEnableDefinitionDAO) data;
                    String paramInstanceName = paramInstance.getIdentifier();
                    if (paramInstanceName.equals(ParamClassConstants0400.PARAMETER_BATCH_STATUS) && batchCheckDefinition != null
                            && batchCheckDefinition.getMinBatchStatus() != null) {
                        getIdentChecks().add(
                                new PhaseBatchStatusIdentificationCheck0710(CheckType.EXCEPTION, paramInstanceName, validationDef,
                                        Collections.singletonList(batchCheckDefinition.getMinBatchStatus()), context));
                    } else if (paramInstanceName.equals(ParamClassConstants0400.PARAMETER_ALLOCATION)) {
                        getIdentChecks().add(
                                new PhaseAllocationIdentificationCheck0710(CheckType.EXCEPTION, paramInstanceName, validationDef, null, context));
                    } else if (paramInstanceName.equals(ParamClassConstants0400.PARAMETER_DATE_TO_EXPIRE) && batchCheckDefinition != null
                            && batchCheckDefinition.getTimeToExpire() != null) {
                        getIdentChecks().add(
                                new PhaseBatchExpiryDateIdentificationCheck0710(CheckType.EXCEPTION, paramInstanceName,
                                        (IExceptionEnableDefinitionDAO) data, Collections.singletonList(batchCheckDefinition.getTimeToExpire()),
                                        context));
                    } else if (paramInstanceName.equals(ParamClassConstants0400.PARAMETER_SUBLOT_STATUS)) {
                        final MESParamSublotCheckDef0100 sublotCheckDefinition = getParameterInstanceData(MESParamSublotCheckDef0100.class, config);
                        if (sublotCheckDefinition != null && sublotCheckDefinition.getMinSublotStatus() != null) {
                            addSublotStatusCheck(paramInstanceName, validationDef, sublotCheckDefinition, context);
                        }
                    }
                }
            }
        }
    }

    private void addSublotStatusCheck(final String paramInstanceName, final IExceptionEnableDefinitionDAO validationDef,
            final MESParamSublotCheckDef0100 paramValues, final Map<String, Object> context) {
        if (paramValues == null) {
            throw new IllegalArgumentException("paramValues must not be null");
        }
        final IMESChoiceElement choiceElement = MESChoiceListHelper.getChoiceElement(SublotStatus.CLNAME, paramValues.getMinSublotStatus());
        getIdentChecks()
                .add(new PhaseSublotStatusIdentificationCheck0710(CheckType.EXCEPTION, paramInstanceName, validationDef, choiceElement, context));
    }

    /**
     * Checks if the parameter is enabled and validation should be performed
     * 
     * @param valDef the validation definition parameter
     * @return if the parameter attribute enabled is true
     */
    private boolean isEnabled(IExceptionEnableDefinitionDAO valDef) {
        return valDef.getEnabled() != null && valDef.getEnabled().booleanValue();
    }

    private <T extends IMESProcessParameterData> T getParameterInstanceData(final Class<T> clazz, final List<? extends IS88Parameter> config) {
        for (final IS88Parameter param : config) {
            if (param instanceof IMESProcessParameterInstance) {
                final IMESProcessParameterInstance paramInstance = (IMESProcessParameterInstance) param;
                final IMESProcessParameterData data = paramInstance.getAssociatedProcessParameterData();
                if (clazz.isInstance(data)) {
                    return (T) data;
                }
            }
        }
        return null;
    }

    /**
     * Get identification check parameter
     * 
     * @param config identification check configuration parameters of the phase
     * @return identification check parameter
     */
    private MESParamBatchCheckDef0500 getBatchCheckParam(List<? extends IS88Parameter> config) {
        final MESParamBatchCheckDef0500 batchCheckDefinition = getParameterInstanceData(MESParamBatchCheckDef0500.class, config);
        return batchCheckDefinition;
    }

    /**
     * Add filtered exception messages to the list
     * 
     * @param type default type of a check result
     * @param beanName the name of the check
     * @param argCheckKey the key of the identification check
     * @param params list with parameters' values to customize the identification check
     * @param context additional context for the identification check
     */
    private void addIdentificationCheckDAO(CheckType type, String beanName, String argCheckKey, List<Object> params, Map<String, Object> context) {
        getIdentChecks().add(new PhaseIdentificationCheck0710(type, beanName, argCheckKey, null, params, context));
    }
}