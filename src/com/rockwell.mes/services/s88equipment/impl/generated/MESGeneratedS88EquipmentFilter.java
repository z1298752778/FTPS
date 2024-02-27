package com.rockwell.mes.services.s88equipment.impl.generated;

import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.ReportDesign;
import com.datasweep.compatibility.client.Server;
import com.datasweep.compatibility.client.StateProxy;
import com.datasweep.compatibility.ui.Time;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectFilter;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88Equipment;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88EquipmentFilter;
import com.rockwell.mes.services.s88equipment.ifc.generated.IMESGeneratedS88EquipmentFilter;

import java.util.List;

public abstract class MESGeneratedS88EquipmentFilter extends MESATObjectFilter implements IMESGeneratedS88EquipmentFilter {
    private static final long serialVersionUID = 1L;
    protected static final String ATDEFINITION_NAME = "X_S88Equipment";

    public MESGeneratedS88EquipmentFilter(Server var1) {
        super(var1, "X_S88Equipment");
    }

    public MESGeneratedS88EquipmentFilter() {
        this(PCContext.getServerImpl());
    }

    public List<IMESS88Equipment> getFilteredObjects() {
        return MESATObject.getFilteredMESATObjectList(this, IMESS88Equipment.class);
    }

    public IMESS88EquipmentFilter forLDTemplateNameContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_LDTemplateName", var1);
    }

    public IMESS88EquipmentFilter forLDTemplateNameEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_LDTemplateName", var1);
    }

    public IMESS88EquipmentFilter forLDTemplateNameNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_LDTemplateName", var1);
    }

    public IMESS88EquipmentFilter forLDTemplateNameStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_LDTemplateName", var1);
    }

    public IMESS88EquipmentFilter forLCequipaccPrivEqualTo(AccessPrivilege var1) throws DatasweepException{
    	return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("LC_equip_accPriv", var1.getKey());
    }
    
    public IMESS88EquipmentFilter forLCequipaccPrivNotEqualTo(AccessPrivilege var1) throws DatasweepException{
    	return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("LC_equip_accPriv", var1.getKey());
    }
    
    public IMESS88EquipmentFilter forBarcodeContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_barcode", var1);
    }

    public IMESS88EquipmentFilter forBarcodeEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_barcode", var1);
    }

    public IMESS88EquipmentFilter forBarcodeNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_barcode", var1);
    }

    public IMESS88EquipmentFilter forBarcodeStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_barcode", var1);
    }

    protected IMESS88EquipmentFilter forBarcodePrefixContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_barcodePrefix", var1);
    }

    protected IMESS88EquipmentFilter forBarcodePrefixEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_barcodePrefix", var1);
    }

    protected IMESS88EquipmentFilter forBarcodePrefixNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_barcodePrefix", var1);
    }

    protected IMESS88EquipmentFilter forBarcodePrefixStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_barcodePrefix", var1);
    }

    protected IMESS88EquipmentFilter forBarcodePrefixPolicyEqualTo(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_barcodePrefixPolicy", var1);
    }

    protected IMESS88EquipmentFilter forBarcodePrefixPolicyGreaterThanOrEqualTo(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameGreaterThanOrEqualTo("X_barcodePrefixPolicy", var1);
    }

    protected IMESS88EquipmentFilter forBarcodePrefixPolicyLessThan(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameLessThan("X_barcodePrefixPolicy", var1);
    }

    protected IMESS88EquipmentFilter forBarcodePrefixPolicyNotEqualTo(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_barcodePrefixPolicy", var1);
    }

    public IMESS88EquipmentFilter forDescriptionContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_description", var1);
    }

    public IMESS88EquipmentFilter forDescriptionEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_description", var1);
    }

    public IMESS88EquipmentFilter forDescriptionNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_description", var1);
    }

    public IMESS88EquipmentFilter forDescriptionStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_description", var1);
    }

    public IMESS88EquipmentFilter forDisposableEqualTo(Boolean var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_disposable", var1);
    }

    public IMESS88EquipmentFilter forDisposableNotEqualTo(Boolean var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_disposable", var1);
    }

    public IMESS88EquipmentFilter forEquipmentLevelEqualTo(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_equipmentLevel", var1);
    }

    public IMESS88EquipmentFilter forEquipmentLevelGreaterThanOrEqualTo(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameGreaterThanOrEqualTo("X_equipmentLevel", var1);
    }

    public IMESS88EquipmentFilter forEquipmentLevelLessThan(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameLessThan("X_equipmentLevel", var1);
    }

    public IMESS88EquipmentFilter forEquipmentLevelNotEqualTo(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_equipmentLevel", var1);
    }

    public IMESS88EquipmentFilter forExpiryChgErrBehaviorEqualTo(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_expiryChgErrBehavior", var1);
    }

    public IMESS88EquipmentFilter forExpiryChgErrBehaviorGreaterThanOrEqualTo(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameGreaterThanOrEqualTo("X_expiryChgErrBehavior", var1);
    }

    public IMESS88EquipmentFilter forExpiryChgErrBehaviorLessThan(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameLessThan("X_expiryChgErrBehavior", var1);
    }

    public IMESS88EquipmentFilter forExpiryChgErrBehaviorNotEqualTo(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_expiryChgErrBehavior", var1);
    }

    protected IMESS88EquipmentFilter forGenIdentifierLengthEqualTo(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_genIdentifierLength", var1);
    }

    protected IMESS88EquipmentFilter forGenIdentifierLengthGreaterThanOrEqualTo(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameGreaterThanOrEqualTo("X_genIdentifierLength", var1);
    }

    protected IMESS88EquipmentFilter forGenIdentifierLengthLessThan(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameLessThan("X_genIdentifierLength", var1);
    }

    protected IMESS88EquipmentFilter forGenIdentifierLengthNotEqualTo(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_genIdentifierLength", var1);
    }

    public IMESS88EquipmentFilter forHistorianProviderContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_HistorianProvider", var1);
    }

    public IMESS88EquipmentFilter forHistorianProviderEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_HistorianProvider", var1);
    }

    public IMESS88EquipmentFilter forHistorianProviderNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_HistorianProvider", var1);
    }

    public IMESS88EquipmentFilter forHistorianProviderStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_HistorianProvider", var1);
    }

    public IMESS88EquipmentFilter forIdentifierContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_identifier", var1);
    }

    public IMESS88EquipmentFilter forIdentifierEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_identifier", var1);
    }

    public IMESS88EquipmentFilter forIdentifierNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_identifier", var1);
    }

    public IMESS88EquipmentFilter forIdentifierStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_identifier", var1);
    }

    protected IMESS88EquipmentFilter forIdentifierPrefixContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_identifierPrefix", var1);
    }

    protected IMESS88EquipmentFilter forIdentifierPrefixEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_identifierPrefix", var1);
    }

    protected IMESS88EquipmentFilter forIdentifierPrefixNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_identifierPrefix", var1);
    }

    protected IMESS88EquipmentFilter forIdentifierPrefixStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_identifierPrefix", var1);
    }

    public IMESS88EquipmentFilter forInventoryNumberContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_inventoryNumber", var1);
    }

    public IMESS88EquipmentFilter forInventoryNumberEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_inventoryNumber", var1);
    }

    public IMESS88EquipmentFilter forInventoryNumberNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_inventoryNumber", var1);
    }

    public IMESS88EquipmentFilter forInventoryNumberStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_inventoryNumber", var1);
    }

    public IMESS88EquipmentFilter forIsMigratedEqualTo(Boolean var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_isMigrated", var1);
    }

    public IMESS88EquipmentFilter forIsMigratedNotEqualTo(Boolean var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_isMigrated", var1);
    }

    public IMESS88EquipmentFilter forIsTemplateEqualTo(Boolean var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_isTemplate", var1);
    }

    public IMESS88EquipmentFilter forIsTemplateNotEqualTo(Boolean var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_isTemplate", var1);
    }

    public IMESS88EquipmentFilter forLabelReportDesignEqualTo(ReportDesign var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_labelReportDesign", var1);
    }

    public IMESS88EquipmentFilter forLabelReportDesignNotEqualTo(ReportDesign var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_labelReportDesign", var1);
    }

    public IMESS88EquipmentFilter forLogbookEnabledEqualTo(Boolean var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_logbookEnabled", var1);
    }

    public IMESS88EquipmentFilter forLogbookEnabledNotEqualTo(Boolean var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_logbookEnabled", var1);
    }

    public IMESS88EquipmentFilter forManufacturerContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_manufacturer", var1);
    }

    public IMESS88EquipmentFilter forManufacturerEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_manufacturer", var1);
    }

    public IMESS88EquipmentFilter forManufacturerNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_manufacturer", var1);
    }

    public IMESS88EquipmentFilter forManufacturerStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_manufacturer", var1);
    }

    public IMESS88EquipmentFilter forManufacturingDateEqualTo(Time var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_manufacturingDate", var1);
    }

    public IMESS88EquipmentFilter forManufacturingDateGreaterThanOrEqualTo(Time var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameGreaterThanOrEqualTo("X_manufacturingDate", var1);
    }

    public IMESS88EquipmentFilter forManufacturingDateLessThan(Time var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameLessThan("X_manufacturingDate", var1);
    }

    public IMESS88EquipmentFilter forManufacturingDateNotEqualTo(Time var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_manufacturingDate", var1);
    }

    public IMESS88EquipmentFilter forModeEqualTo(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_mode", var1);
    }

    public IMESS88EquipmentFilter forModeGreaterThanOrEqualTo(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameGreaterThanOrEqualTo("X_mode", var1);
    }

    public IMESS88EquipmentFilter forModeLessThan(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameLessThan("X_mode", var1);
    }

    public IMESS88EquipmentFilter forModeNotEqualTo(Long var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_mode", var1);
    }

    public IMESS88EquipmentFilter forModelContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_model", var1);
    }

    public IMESS88EquipmentFilter forModelEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_model", var1);
    }

    public IMESS88EquipmentFilter forModelNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_model", var1);
    }

    public IMESS88EquipmentFilter forModelStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_model", var1);
    }

    public IMESS88EquipmentFilter forOverrideAIServerNameContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_OverrideAIServerName", var1);
    }

    public IMESS88EquipmentFilter forOverrideAIServerNameEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_OverrideAIServerName", var1);
    }

    public IMESS88EquipmentFilter forOverrideAIServerNameNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_OverrideAIServerName", var1);
    }

    public IMESS88EquipmentFilter forOverrideAIServerNameStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_OverrideAIServerName", var1);
    }

    public IMESS88EquipmentFilter forOverrideHistorianAISrvContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_OverrideHistorianAISrv", var1);
    }

    public IMESS88EquipmentFilter forOverrideHistorianAISrvEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_OverrideHistorianAISrv", var1);
    }

    public IMESS88EquipmentFilter forOverrideHistorianAISrvNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_OverrideHistorianAISrv", var1);
    }

    public IMESS88EquipmentFilter forOverrideHistorianAISrvStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_OverrideHistorianAISrv", var1);
    }

    public IMESS88EquipmentFilter forOverrideHistorianASrvContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_OverrideHistorianASrv", var1);
    }

    public IMESS88EquipmentFilter forOverrideHistorianASrvEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_OverrideHistorianASrv", var1);
    }

    public IMESS88EquipmentFilter forOverrideHistorianASrvNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_OverrideHistorianASrv", var1);
    }

    public IMESS88EquipmentFilter forOverrideHistorianASrvStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_OverrideHistorianASrv", var1);
    }

    public IMESS88EquipmentFilter forOverrideHistorianSrvContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_OverrideHistorianSrv", var1);
    }

    public IMESS88EquipmentFilter forOverrideHistorianSrvEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_OverrideHistorianSrv", var1);
    }

    public IMESS88EquipmentFilter forOverrideHistorianSrvNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_OverrideHistorianSrv", var1);
    }

    public IMESS88EquipmentFilter forOverrideHistorianSrvStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_OverrideHistorianSrv", var1);
    }

    public IMESS88EquipmentFilter forOverrideLDAreaPathContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_OverrideLDAreaPath", var1);
    }

    public IMESS88EquipmentFilter forOverrideLDAreaPathEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_OverrideLDAreaPath", var1);
    }

    public IMESS88EquipmentFilter forOverrideLDAreaPathNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_OverrideLDAreaPath", var1);
    }

    public IMESS88EquipmentFilter forOverrideLDAreaPathStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_OverrideLDAreaPath", var1);
    }

    public IMESS88EquipmentFilter forParentEntityEqualTo(IMESS88Equipment var1) throws DatasweepException {
        Long var2 = var1 == null ? null : var1.getKey();
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_parentEntity", var2);
    }

    public IMESS88EquipmentFilter forParentEntityNotEqualTo(IMESS88Equipment var1) throws DatasweepException {
        Long var2 = var1 == null ? null : var1.getKey();
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_parentEntity", var2);
    }

    public IMESS88EquipmentFilter forSerialNumberContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_serialNumber", var1);
    }

    public IMESS88EquipmentFilter forSerialNumberEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_serialNumber", var1);
    }

    public IMESS88EquipmentFilter forSerialNumberNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_serialNumber", var1);
    }

    public IMESS88EquipmentFilter forSerialNumberStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_serialNumber", var1);
    }

    public IMESS88EquipmentFilter forShortDescriptionContaining(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameContaining("X_shortDescription", var1);
    }

    public IMESS88EquipmentFilter forShortDescriptionEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_shortDescription", var1);
    }

    public IMESS88EquipmentFilter forShortDescriptionNotEqualTo(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_shortDescription", var1);
    }

    public IMESS88EquipmentFilter forShortDescriptionStartingWith(String var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameStartingWith("X_shortDescription", var1);
    }

    public IMESS88EquipmentFilter forStateProxyEqualTo(StateProxy var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_stateProxy", var1);
    }

    public IMESS88EquipmentFilter forStateProxyNotEqualTo(StateProxy var1) throws DatasweepException {
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_stateProxy", var1);
    }

    public IMESS88EquipmentFilter forTemplateEntityEqualTo(IMESS88Equipment var1) throws DatasweepException {
        Long var2 = var1 == null ? null : var1.getKey();
        return (IMESS88EquipmentFilter)this.forColumnNameEqualTo("X_templateEntity", var2);
    }

    public IMESS88EquipmentFilter forTemplateEntityNotEqualTo(IMESS88Equipment var1) throws DatasweepException {
        Long var2 = var1 == null ? null : var1.getKey();
        return (IMESS88EquipmentFilter)this.forColumnNameNotEqualTo("X_templateEntity", var2);
    }
}
