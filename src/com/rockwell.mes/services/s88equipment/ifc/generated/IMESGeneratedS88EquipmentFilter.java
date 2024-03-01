package com.rockwell.mes.services.s88equipment.ifc.generated;

import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.ReportDesign;
import com.datasweep.compatibility.client.StateProxy;
import com.datasweep.compatibility.ui.Time;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObjectFilter;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88Equipment;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88EquipmentFilter;
import java.util.List;

public interface IMESGeneratedS88EquipmentFilter extends IMESATObjectFilter {
    public List<IMESS88Equipment> getFilteredObjects();

    //
    IMESS88EquipmentFilter forLCequipaccPrivEqualTo(AccessPrivilege p0) throws DatasweepException;

    IMESS88EquipmentFilter forLCequipaccPrivNotEqualTo(AccessPrivilege p0) throws DatasweepException;

    //
    public IMESS88EquipmentFilter forProcessPackNameContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forProcessPackNamEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forProcessPackNamNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forProcessPackNamStartingWith(String var1) throws DatasweepException;


    public IMESS88EquipmentFilter forLDTemplateNameContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forLDTemplateNameEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forLDTemplateNameNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forLDTemplateNameStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forBarcodeContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forBarcodeEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forBarcodeNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forBarcodeStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forDescriptionContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forDescriptionEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forDescriptionNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forDescriptionStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forDisposableEqualTo(Boolean var1) throws DatasweepException;

    public IMESS88EquipmentFilter forDisposableNotEqualTo(Boolean var1) throws DatasweepException;

    public IMESS88EquipmentFilter forEquipmentLevelEqualTo(Long var1) throws DatasweepException;

    public IMESS88EquipmentFilter forEquipmentLevelGreaterThanOrEqualTo(Long var1) throws DatasweepException;

    public IMESS88EquipmentFilter forEquipmentLevelLessThan(Long var1) throws DatasweepException;

    public IMESS88EquipmentFilter forEquipmentLevelNotEqualTo(Long var1) throws DatasweepException;

    public IMESS88EquipmentFilter forExpiryChgErrBehaviorEqualTo(Long var1) throws DatasweepException;

    public IMESS88EquipmentFilter forExpiryChgErrBehaviorGreaterThanOrEqualTo(Long var1) throws DatasweepException;

    public IMESS88EquipmentFilter forExpiryChgErrBehaviorLessThan(Long var1) throws DatasweepException;

    public IMESS88EquipmentFilter forExpiryChgErrBehaviorNotEqualTo(Long var1) throws DatasweepException;

    public IMESS88EquipmentFilter forHistorianProviderContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forHistorianProviderEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forHistorianProviderNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forHistorianProviderStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forIdentifierContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forIdentifierEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forIdentifierNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forIdentifierStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forInventoryNumberContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forInventoryNumberEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forInventoryNumberNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forInventoryNumberStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forIsMigratedEqualTo(Boolean var1) throws DatasweepException;

    public IMESS88EquipmentFilter forIsMigratedNotEqualTo(Boolean var1) throws DatasweepException;

    public IMESS88EquipmentFilter forIsTemplateEqualTo(Boolean var1) throws DatasweepException;

    public IMESS88EquipmentFilter forIsTemplateNotEqualTo(Boolean var1) throws DatasweepException;

    public IMESS88EquipmentFilter forLabelReportDesignEqualTo(ReportDesign var1) throws DatasweepException;

    public IMESS88EquipmentFilter forLabelReportDesignNotEqualTo(ReportDesign var1) throws DatasweepException;

    public IMESS88EquipmentFilter forLogbookEnabledEqualTo(Boolean var1) throws DatasweepException;

    public IMESS88EquipmentFilter forLogbookEnabledNotEqualTo(Boolean var1) throws DatasweepException;

    public IMESS88EquipmentFilter forManufacturerContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forManufacturerEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forManufacturerNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forManufacturerStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forManufacturingDateEqualTo(Time var1) throws DatasweepException;

    public IMESS88EquipmentFilter forManufacturingDateGreaterThanOrEqualTo(Time var1) throws DatasweepException;

    public IMESS88EquipmentFilter forManufacturingDateLessThan(Time var1) throws DatasweepException;

    public IMESS88EquipmentFilter forManufacturingDateNotEqualTo(Time var1) throws DatasweepException;

    public IMESS88EquipmentFilter forModeEqualTo(Long var1) throws DatasweepException;

    public IMESS88EquipmentFilter forModeGreaterThanOrEqualTo(Long var1) throws DatasweepException;

    public IMESS88EquipmentFilter forModeLessThan(Long var1) throws DatasweepException;

    public IMESS88EquipmentFilter forModeNotEqualTo(Long var1) throws DatasweepException;

    public IMESS88EquipmentFilter forModelContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forModelEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forModelNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forModelStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideAIServerNameContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideAIServerNameEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideAIServerNameNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideAIServerNameStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideHistorianAISrvContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideHistorianAISrvEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideHistorianAISrvNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideHistorianAISrvStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideHistorianASrvContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideHistorianASrvEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideHistorianASrvNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideHistorianASrvStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideHistorianSrvContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideHistorianSrvEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideHistorianSrvNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideHistorianSrvStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideLDAreaPathContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideLDAreaPathEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideLDAreaPathNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forOverrideLDAreaPathStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forParentEntityEqualTo(IMESS88Equipment var1) throws DatasweepException;

    public IMESS88EquipmentFilter forParentEntityNotEqualTo(IMESS88Equipment var1) throws DatasweepException;

    public IMESS88EquipmentFilter forSerialNumberContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forSerialNumberEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forSerialNumberNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forSerialNumberStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forShortDescriptionContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forShortDescriptionEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forShortDescriptionNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forShortDescriptionStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forStateProxyEqualTo(StateProxy var1) throws DatasweepException;

    public IMESS88EquipmentFilter forStateProxyNotEqualTo(StateProxy var1) throws DatasweepException;

    public IMESS88EquipmentFilter forTemplateEntityEqualTo(IMESS88Equipment var1) throws DatasweepException;

    public IMESS88EquipmentFilter forTemplateEntityNotEqualTo(IMESS88Equipment var1) throws DatasweepException;
}