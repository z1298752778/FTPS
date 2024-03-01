package com.rockwell.mes.services.s88equipment.ifc.generated;

import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.ui.Time;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObjectFilter;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88EquipmentClass;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88EquipmentClassFilter;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88EquipmentFilter;

import java.util.List;

public interface IMESGeneratedS88EquipmentClassFilter extends IMESATObjectFilter {
    public List<IMESS88EquipmentClass> getFilteredObjects();

    //
    public IMESS88EquipmentFilter forProcessPackNameContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forProcessPackNamEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forProcessPackNamNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forProcessPackNamStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forDescriptionContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forDescriptionEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forDescriptionNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forDescriptionStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forDisposableEqualTo(Boolean var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forDisposableNotEqualTo(Boolean var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forEquipmentLevelEqualTo(Long var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forEquipmentLevelGreaterThanOrEqualTo(Long var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forEquipmentLevelLessThan(Long var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forEquipmentLevelNotEqualTo(Long var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forIdentifierContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forIdentifierEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forIdentifierNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forIdentifierStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forInventoryNumberContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forInventoryNumberEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forInventoryNumberNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forInventoryNumberStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forLogbookEnabledEqualTo(Boolean var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forLogbookEnabledNotEqualTo(Boolean var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forManufacturerContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forManufacturerEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forManufacturerNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forManufacturerStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forManufacturingDateEqualTo(Time var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forManufacturingDateGreaterThanOrEqualTo(Time var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forManufacturingDateLessThan(Time var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forManufacturingDateNotEqualTo(Time var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forModelContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forModelEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forModelNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forModelStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forSerialNumberContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forSerialNumberEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forSerialNumberNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forSerialNumberStartingWith(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forShortDescriptionContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forShortDescriptionEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forShortDescriptionNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentClassFilter forShortDescriptionStartingWith(String var1) throws DatasweepException;
}