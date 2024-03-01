package com.rockwell.mes.services.s88equipment.impl.generated;

import com.datasweep.compatibility.client.ATRowFilter;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.Server;
import com.datasweep.compatibility.client.StateProxy;
import com.datasweep.compatibility.ui.Time;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectFilter;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88EquipmentClass;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88EquipmentClassFilter;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88EquipmentFilter;
import com.rockwell.mes.services.s88equipment.ifc.generated.IMESGeneratedS88EquipmentClassFilter;
import java.util.List;

public abstract class MESGeneratedS88EquipmentClassFilter extends MESATObjectFilter implements IMESGeneratedS88EquipmentClassFilter {
    private static final long serialVersionUID = 1L;

    protected static final String ATDEFINITION_NAME = "X_S88EquipmentClass";

    public MESGeneratedS88EquipmentClassFilter(Server server) {
        super(server, ATDEFINITION_NAME);
    }

    public MESGeneratedS88EquipmentClassFilter() {
        this((Server) PCContext.getServerImpl());
    }

    public List<IMESS88EquipmentClass> getFilteredObjects() {
        return MESATObject.getFilteredMESATObjectList((ATRowFilter) this, IMESS88EquipmentClass.class);
    }

    //
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

    public IMESS88EquipmentClassFilter forDescriptionContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameContaining("X_description", string);
    }

    public IMESS88EquipmentClassFilter forDescriptionEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameEqualTo("X_description", (Object) string);
    }

    public IMESS88EquipmentClassFilter forDescriptionNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameNotEqualTo("X_description", (Object) string);
    }

    public IMESS88EquipmentClassFilter forDescriptionStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameStartingWith("X_description", string);
    }

    public IMESS88EquipmentClassFilter forDisposableEqualTo(Boolean bl) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameEqualTo("X_disposable", (Object) bl);
    }

    public IMESS88EquipmentClassFilter forDisposableNotEqualTo(Boolean bl) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameNotEqualTo("X_disposable", (Object) bl);
    }

    public IMESS88EquipmentClassFilter forEquipmentLevelEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameEqualTo("X_equipmentLevel", (Object) l);
    }

    public IMESS88EquipmentClassFilter forEquipmentLevelGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameGreaterThanOrEqualTo("X_equipmentLevel", (Object) l);
    }

    public IMESS88EquipmentClassFilter forEquipmentLevelLessThan(Long l) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameLessThan("X_equipmentLevel", (Object) l);
    }

    public IMESS88EquipmentClassFilter forEquipmentLevelNotEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameNotEqualTo("X_equipmentLevel", (Object) l);
    }

    public IMESS88EquipmentClassFilter forIdentifierContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameContaining("X_identifier", string);
    }

    public IMESS88EquipmentClassFilter forIdentifierEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameEqualTo("X_identifier", (Object) string);
    }

    public IMESS88EquipmentClassFilter forIdentifierNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameNotEqualTo("X_identifier", (Object) string);
    }

    public IMESS88EquipmentClassFilter forIdentifierStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameStartingWith("X_identifier", string);
    }

    public IMESS88EquipmentClassFilter forInventoryNumberContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameContaining("X_inventoryNumber", string);
    }

    public IMESS88EquipmentClassFilter forInventoryNumberEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameEqualTo("X_inventoryNumber", (Object) string);
    }

    public IMESS88EquipmentClassFilter forInventoryNumberNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameNotEqualTo("X_inventoryNumber", (Object) string);
    }

    public IMESS88EquipmentClassFilter forInventoryNumberStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameStartingWith("X_inventoryNumber", string);
    }

    public IMESS88EquipmentClassFilter forLogbookEnabledEqualTo(Boolean bl) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameEqualTo("X_logbookEnabled", (Object) bl);
    }

    public IMESS88EquipmentClassFilter forLogbookEnabledNotEqualTo(Boolean bl) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameNotEqualTo("X_logbookEnabled", (Object) bl);
    }

    public IMESS88EquipmentClassFilter forManufacturerContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameContaining("X_manufacturer", string);
    }

    public IMESS88EquipmentClassFilter forManufacturerEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameEqualTo("X_manufacturer", (Object) string);
    }

    public IMESS88EquipmentClassFilter forManufacturerNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameNotEqualTo("X_manufacturer", (Object) string);
    }

    public IMESS88EquipmentClassFilter forManufacturerStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameStartingWith("X_manufacturer", string);
    }

    public IMESS88EquipmentClassFilter forManufacturingDateEqualTo(Time time) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameEqualTo("X_manufacturingDate", (Object) time);
    }

    public IMESS88EquipmentClassFilter forManufacturingDateGreaterThanOrEqualTo(Time time) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameGreaterThanOrEqualTo("X_manufacturingDate", (Object) time);
    }

    public IMESS88EquipmentClassFilter forManufacturingDateLessThan(Time time) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameLessThan("X_manufacturingDate", (Object) time);
    }

    public IMESS88EquipmentClassFilter forManufacturingDateNotEqualTo(Time time) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameNotEqualTo("X_manufacturingDate", (Object) time);
    }

    public IMESS88EquipmentClassFilter forModelContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameContaining("X_model", string);
    }

    public IMESS88EquipmentClassFilter forModelEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameEqualTo("X_model", (Object) string);
    }

    public IMESS88EquipmentClassFilter forModelNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameNotEqualTo("X_model", (Object) string);
    }

    public IMESS88EquipmentClassFilter forModelStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameStartingWith("X_model", string);
    }

    public IMESS88EquipmentClassFilter forSerialNumberContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameContaining("X_serialNumber", string);
    }

    public IMESS88EquipmentClassFilter forSerialNumberEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameEqualTo("X_serialNumber", (Object) string);
    }

    public IMESS88EquipmentClassFilter forSerialNumberNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameNotEqualTo("X_serialNumber", (Object) string);
    }

    public IMESS88EquipmentClassFilter forSerialNumberStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameStartingWith("X_serialNumber", string);
    }

    public IMESS88EquipmentClassFilter forShortDescriptionContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameContaining("X_shortDescription", string);
    }

    public IMESS88EquipmentClassFilter forShortDescriptionEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameEqualTo("X_shortDescription", (Object) string);
    }

    public IMESS88EquipmentClassFilter forShortDescriptionNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameNotEqualTo("X_shortDescription", (Object) string);
    }

    public IMESS88EquipmentClassFilter forShortDescriptionStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameStartingWith("X_shortDescription", string);
    }

    protected IMESS88EquipmentClassFilter forStateProxyEqualTo(StateProxy stateProxy) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameEqualTo("X_stateProxy", (Object) stateProxy);
    }

    protected IMESS88EquipmentClassFilter forStateProxyNotEqualTo(StateProxy stateProxy) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameNotEqualTo("X_stateProxy", (Object) stateProxy);
    }

    protected IMESS88EquipmentClassFilter forTileColorEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameEqualTo("X_tileColor", (Object) l);
    }

    protected IMESS88EquipmentClassFilter forTileColorGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameGreaterThanOrEqualTo("X_tileColor", (Object) l);
    }

    protected IMESS88EquipmentClassFilter forTileColorLessThan(Long l) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameLessThan("X_tileColor", (Object) l);
    }

    protected IMESS88EquipmentClassFilter forTileColorNotEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameNotEqualTo("X_tileColor", (Object) l);
    }

    protected IMESS88EquipmentClassFilter forTileIconContaining(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameContaining("X_tileIcon", string);
    }

    protected IMESS88EquipmentClassFilter forTileIconEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameEqualTo("X_tileIcon", (Object) string);
    }

    protected IMESS88EquipmentClassFilter forTileIconNotEqualTo(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameNotEqualTo("X_tileIcon", (Object) string);
    }

    protected IMESS88EquipmentClassFilter forTileIconStartingWith(String string) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameStartingWith("X_tileIcon", string);
    }

    protected IMESS88EquipmentClassFilter forTileTextColorEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameEqualTo("X_tileTextColor", (Object) l);
    }

    protected IMESS88EquipmentClassFilter forTileTextColorGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameGreaterThanOrEqualTo("X_tileTextColor", (Object) l);
    }

    protected IMESS88EquipmentClassFilter forTileTextColorLessThan(Long l) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameLessThan("X_tileTextColor", (Object) l);
    }

    protected IMESS88EquipmentClassFilter forTileTextColorNotEqualTo(Long l) throws DatasweepException {
        return (IMESS88EquipmentClassFilter) this.forColumnNameNotEqualTo("X_tileTextColor", (Object) l);
    }
}