package com.rockwell.mes.services.s88equipment.impl.generated;

import com.datasweep.compatibility.client.ATRowFilter;
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

    public MESGeneratedS88EquipmentFilter(Server server) {
        super(server, ATDEFINITION_NAME);
    }

    public MESGeneratedS88EquipmentFilter() {
        this((Server) PCContext.getServerImpl());
    }

    public List<IMESS88Equipment> getFilteredObjects() {
        return MESATObject.getFilteredMESATObjectList((ATRowFilter) this, IMESS88Equipment.class);
    }

    public IMESS88EquipmentFilter forLDTemplateNameContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_LDTemplateName", string);
    }

    public IMESS88EquipmentFilter forLDTemplateNameEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_LDTemplateName", (Object) string);
    }

    public IMESS88EquipmentFilter forLDTemplateNameNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_LDTemplateName", (Object) string);
    }

    public IMESS88EquipmentFilter forLDTemplateNameStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_LDTemplateName", string);
    }

    public IMESS88EquipmentFilter forLCequipaccPrivEqualTo(AccessPrivilege var1) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("LC_equip_accPriv", (Object) var1.getKey());
    }

    public IMESS88EquipmentFilter forLCequipaccPrivNotEqualTo(AccessPrivilege var1) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("LC_equip_accPriv", (Object) var1.getKey());
    }

    public IMESS88EquipmentFilter forBarcodeContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_barcode", string);
    }

    public IMESS88EquipmentFilter forBarcodeEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_barcode", (Object) string);
    }

    public IMESS88EquipmentFilter forBarcodeNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_barcode", (Object) string);
    }

    public IMESS88EquipmentFilter forBarcodeStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_barcode", string);
    }

    protected IMESS88EquipmentFilter forBarcodePrefixContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_barcodePrefix", string);
    }

    protected IMESS88EquipmentFilter forBarcodePrefixEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_barcodePrefix", (Object) string);
    }

    protected IMESS88EquipmentFilter forBarcodePrefixNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_barcodePrefix", (Object) string);
    }

    protected IMESS88EquipmentFilter forBarcodePrefixStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_barcodePrefix", string);
    }

    protected IMESS88EquipmentFilter forBarcodePrefixPolicyEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_barcodePrefixPolicy", (Object) l);
    }

    protected IMESS88EquipmentFilter forBarcodePrefixPolicyGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameGreaterThanOrEqualTo("X_barcodePrefixPolicy", (Object) l);
    }

    protected IMESS88EquipmentFilter forBarcodePrefixPolicyLessThan(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameLessThan("X_barcodePrefixPolicy", (Object) l);
    }

    protected IMESS88EquipmentFilter forBarcodePrefixPolicyNotEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_barcodePrefixPolicy", (Object) l);
    }

    public IMESS88EquipmentFilter forDescriptionContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_description", string);
    }

    public IMESS88EquipmentFilter forDescriptionEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_description", (Object) string);
    }

    public IMESS88EquipmentFilter forDescriptionNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_description", (Object) string);
    }

    public IMESS88EquipmentFilter forDescriptionStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_description", string);
    }

    public IMESS88EquipmentFilter forDisposableEqualTo(Boolean bl) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_disposable", (Object) bl);
    }

    public IMESS88EquipmentFilter forDisposableNotEqualTo(Boolean bl) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_disposable", (Object) bl);
    }

    public IMESS88EquipmentFilter forEquipmentLevelEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_equipmentLevel", (Object) l);
    }

    public IMESS88EquipmentFilter forEquipmentLevelGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameGreaterThanOrEqualTo("X_equipmentLevel", (Object) l);
    }

    public IMESS88EquipmentFilter forEquipmentLevelLessThan(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameLessThan("X_equipmentLevel", (Object) l);
    }

    public IMESS88EquipmentFilter forEquipmentLevelNotEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_equipmentLevel", (Object) l);
    }

    public IMESS88EquipmentFilter forExpiryChgErrBehaviorEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_expiryChgErrBehavior", (Object) l);
    }

    public IMESS88EquipmentFilter forExpiryChgErrBehaviorGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameGreaterThanOrEqualTo("X_expiryChgErrBehavior", (Object) l);
    }

    public IMESS88EquipmentFilter forExpiryChgErrBehaviorLessThan(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameLessThan("X_expiryChgErrBehavior", (Object) l);
    }

    public IMESS88EquipmentFilter forExpiryChgErrBehaviorNotEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_expiryChgErrBehavior", (Object) l);
    }

    protected IMESS88EquipmentFilter forGenIdentifierLengthEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_genIdentifierLength", (Object) l);
    }

    protected IMESS88EquipmentFilter forGenIdentifierLengthGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameGreaterThanOrEqualTo("X_genIdentifierLength", (Object) l);
    }

    protected IMESS88EquipmentFilter forGenIdentifierLengthLessThan(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameLessThan("X_genIdentifierLength", (Object) l);
    }

    protected IMESS88EquipmentFilter forGenIdentifierLengthNotEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_genIdentifierLength", (Object) l);
    }

    public IMESS88EquipmentFilter forHistorianProviderContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_HistorianProvider", string);
    }

    public IMESS88EquipmentFilter forHistorianProviderEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_HistorianProvider", (Object) string);
    }

    public IMESS88EquipmentFilter forHistorianProviderNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_HistorianProvider", (Object) string);
    }

    public IMESS88EquipmentFilter forHistorianProviderStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_HistorianProvider", string);
    }

    public IMESS88EquipmentFilter forIdentifierContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_identifier", string);
    }

    public IMESS88EquipmentFilter forIdentifierEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_identifier", (Object) string);
    }

    public IMESS88EquipmentFilter forIdentifierNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_identifier", (Object) string);
    }

    public IMESS88EquipmentFilter forIdentifierStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_identifier", string);
    }

    protected IMESS88EquipmentFilter forIdentifierPrefixContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_identifierPrefix", string);
    }

    protected IMESS88EquipmentFilter forIdentifierPrefixEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_identifierPrefix", (Object) string);
    }

    protected IMESS88EquipmentFilter forIdentifierPrefixNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_identifierPrefix", (Object) string);
    }

    protected IMESS88EquipmentFilter forIdentifierPrefixStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_identifierPrefix", string);
    }

    public IMESS88EquipmentFilter forInventoryNumberContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_inventoryNumber", string);
    }

    public IMESS88EquipmentFilter forInventoryNumberEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_inventoryNumber", (Object) string);
    }

    public IMESS88EquipmentFilter forInventoryNumberNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_inventoryNumber", (Object) string);
    }

    public IMESS88EquipmentFilter forInventoryNumberStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_inventoryNumber", string);
    }

    public IMESS88EquipmentFilter forIsMigratedEqualTo(Boolean bl) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_isMigrated", (Object) bl);
    }

    public IMESS88EquipmentFilter forIsMigratedNotEqualTo(Boolean bl) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_isMigrated", (Object) bl);
    }

    public IMESS88EquipmentFilter forIsTemplateEqualTo(Boolean bl) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_isTemplate", (Object) bl);
    }

    public IMESS88EquipmentFilter forIsTemplateNotEqualTo(Boolean bl) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_isTemplate", (Object) bl);
    }

    public IMESS88EquipmentFilter forLabelReportDesignEqualTo(ReportDesign reportDesign) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_labelReportDesign", (Object) reportDesign);
    }

    public IMESS88EquipmentFilter forLabelReportDesignNotEqualTo(ReportDesign reportDesign) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_labelReportDesign", (Object) reportDesign);
    }

    public IMESS88EquipmentFilter forLogbookEnabledEqualTo(Boolean bl) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_logbookEnabled", (Object) bl);
    }

    public IMESS88EquipmentFilter forLogbookEnabledNotEqualTo(Boolean bl) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_logbookEnabled", (Object) bl);
    }

    public IMESS88EquipmentFilter forManufacturerContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_manufacturer", string);
    }

    public IMESS88EquipmentFilter forManufacturerEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_manufacturer", (Object) string);
    }

    public IMESS88EquipmentFilter forManufacturerNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_manufacturer", (Object) string);
    }

    public IMESS88EquipmentFilter forManufacturerStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_manufacturer", string);
    }

    public IMESS88EquipmentFilter forManufacturingDateEqualTo(Time time) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_manufacturingDate", (Object) time);
    }

    public IMESS88EquipmentFilter forManufacturingDateGreaterThanOrEqualTo(Time time) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameGreaterThanOrEqualTo("X_manufacturingDate", (Object) time);
    }

    public IMESS88EquipmentFilter forManufacturingDateLessThan(Time time) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameLessThan("X_manufacturingDate", (Object) time);
    }

    public IMESS88EquipmentFilter forManufacturingDateNotEqualTo(Time time) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_manufacturingDate", (Object) time);
    }

    public IMESS88EquipmentFilter forModeEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_mode", (Object) l);
    }

    public IMESS88EquipmentFilter forModeGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameGreaterThanOrEqualTo("X_mode", (Object) l);
    }

    public IMESS88EquipmentFilter forModeLessThan(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameLessThan("X_mode", (Object) l);
    }

    public IMESS88EquipmentFilter forModeNotEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_mode", (Object) l);
    }

    public IMESS88EquipmentFilter forModelContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_model", string);
    }

    public IMESS88EquipmentFilter forModelEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_model", (Object) string);
    }

    public IMESS88EquipmentFilter forModelNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_model", (Object) string);
    }

    public IMESS88EquipmentFilter forModelStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_model", string);
    }

    public IMESS88EquipmentFilter forOverrideAIServerNameContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_OverrideAIServerName", string);
    }

    public IMESS88EquipmentFilter forOverrideAIServerNameEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_OverrideAIServerName", (Object) string);
    }

    public IMESS88EquipmentFilter forOverrideAIServerNameNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_OverrideAIServerName", (Object) string);
    }

    public IMESS88EquipmentFilter forOverrideAIServerNameStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_OverrideAIServerName", string);
    }

    public IMESS88EquipmentFilter forOverrideHistorianAISrvContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_OverrideHistorianAISrv", string);
    }

    public IMESS88EquipmentFilter forOverrideHistorianAISrvEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_OverrideHistorianAISrv", (Object) string);
    }

    public IMESS88EquipmentFilter forOverrideHistorianAISrvNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_OverrideHistorianAISrv", (Object) string);
    }

    public IMESS88EquipmentFilter forOverrideHistorianAISrvStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_OverrideHistorianAISrv", string);
    }

    public IMESS88EquipmentFilter forOverrideHistorianASrvContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_OverrideHistorianASrv", string);
    }

    public IMESS88EquipmentFilter forOverrideHistorianASrvEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_OverrideHistorianASrv", (Object) string);
    }

    public IMESS88EquipmentFilter forOverrideHistorianASrvNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_OverrideHistorianASrv", (Object) string);
    }

    public IMESS88EquipmentFilter forOverrideHistorianASrvStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_OverrideHistorianASrv", string);
    }

    public IMESS88EquipmentFilter forOverrideHistorianSrvContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_OverrideHistorianSrv", string);
    }

    public IMESS88EquipmentFilter forOverrideHistorianSrvEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_OverrideHistorianSrv", (Object) string);
    }

    public IMESS88EquipmentFilter forOverrideHistorianSrvNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_OverrideHistorianSrv", (Object) string);
    }

    public IMESS88EquipmentFilter forOverrideHistorianSrvStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_OverrideHistorianSrv", string);
    }

    public IMESS88EquipmentFilter forOverrideLDAreaPathContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_OverrideLDAreaPath", string);
    }

    public IMESS88EquipmentFilter forOverrideLDAreaPathEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_OverrideLDAreaPath", (Object) string);
    }

    public IMESS88EquipmentFilter forOverrideLDAreaPathNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_OverrideLDAreaPath", (Object) string);
    }

    public IMESS88EquipmentFilter forOverrideLDAreaPathStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_OverrideLDAreaPath", string);
    }

    public IMESS88EquipmentFilter forParentEntityEqualTo(IMESS88Equipment iMESS88Equipment) throws DatasweepException {
        Long l = iMESS88Equipment == null ? null : Long.valueOf(iMESS88Equipment.getKey());
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_parentEntity", (Object) l);
    }

    public IMESS88EquipmentFilter forParentEntityNotEqualTo(IMESS88Equipment iMESS88Equipment) throws DatasweepException {
        Long l = iMESS88Equipment == null ? null : Long.valueOf(iMESS88Equipment.getKey());
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_parentEntity", (Object) l);
    }

    public IMESS88EquipmentFilter forSerialNumberContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_serialNumber", string);
    }

    public IMESS88EquipmentFilter forSerialNumberEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_serialNumber", (Object) string);
    }

    public IMESS88EquipmentFilter forSerialNumberNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_serialNumber", (Object) string);
    }

    public IMESS88EquipmentFilter forSerialNumberStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_serialNumber", string);
    }

    public IMESS88EquipmentFilter forProcessPackNameContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("processPackName", string);
    }

    public IMESS88EquipmentFilter forProcessPackNameEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("processPackName", (Object) string);
    }

    public IMESS88EquipmentFilter forProcessPackNameNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("processPackName", (Object) string);
    }

    public IMESS88EquipmentFilter forProcessPackNameStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("processPackName", string);
    }

    public IMESS88EquipmentFilter forShortDescriptionContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameContaining("X_shortDescription", string);
    }

    public IMESS88EquipmentFilter forShortDescriptionEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_shortDescription", (Object) string);
    }

    public IMESS88EquipmentFilter forShortDescriptionNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_shortDescription", (Object) string);
    }

    public IMESS88EquipmentFilter forShortDescriptionStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameStartingWith("X_shortDescription", string);
    }

    public IMESS88EquipmentFilter forStateProxyEqualTo(StateProxy stateProxy) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_stateProxy", (Object) stateProxy);
    }

    public IMESS88EquipmentFilter forStateProxyNotEqualTo(StateProxy stateProxy) throws DatasweepException {
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_stateProxy", (Object) stateProxy);
    }

    public IMESS88EquipmentFilter forTemplateEntityEqualTo(IMESS88Equipment iMESS88Equipment) throws DatasweepException {
        Long l = iMESS88Equipment == null ? null : Long.valueOf(iMESS88Equipment.getKey());
        return (IMESS88EquipmentFilter) this.forColumnNameEqualTo("X_templateEntity", (Object) l);
    }

    public IMESS88EquipmentFilter forTemplateEntityNotEqualTo(IMESS88Equipment iMESS88Equipment) throws DatasweepException {
        Long l = iMESS88Equipment == null ? null : Long.valueOf(iMESS88Equipment.getKey());
        return (IMESS88EquipmentFilter) this.forColumnNameNotEqualTo("X_templateEntity", (Object) l);
    }
}