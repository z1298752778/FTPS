package com.rockwell.mes.services.s88equipment.impl.statusgraph.generated;


import com.datasweep.compatibility.client.ATRowFilter;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.Server;
import com.datasweep.compatibility.client.StateProxy;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectFilter;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88EquipmentFilter;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IMESS88StatusGraph;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IMESS88StatusGraphFilter;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.generated.IMESGeneratedS88StatusGraphFilter;
import java.util.List;

public abstract class MESGeneratedS88StatusGraphFilter extends MESATObjectFilter implements IMESGeneratedS88StatusGraphFilter {
    private static final long serialVersionUID = 1L;

    protected static final String ATDEFINITION_NAME = "X_S88StatusGraph";

    public MESGeneratedS88StatusGraphFilter(Server server) {
        super(server, ATDEFINITION_NAME);
    }

    public MESGeneratedS88StatusGraphFilter() {
        this((Server) PCContext.getServerImpl());
    }

    public List<IMESS88StatusGraph> getFilteredObjects() {
        return MESATObject.getFilteredMESATObjectList((ATRowFilter) this, IMESS88StatusGraph.class);
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

    public IMESS88StatusGraphFilter forDescriptionContaining(String string) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameContaining("X_description", string);
    }

    public IMESS88StatusGraphFilter forDescriptionEqualTo(String string) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameEqualTo("X_description", (Object) string);
    }

    public IMESS88StatusGraphFilter forDescriptionNotEqualTo(String string) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameNotEqualTo("X_description", (Object) string);
    }

    public IMESS88StatusGraphFilter forDescriptionStartingWith(String string) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameStartingWith("X_description", string);
    }

    public IMESS88StatusGraphFilter forDisplayNameContaining(String string) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameContaining("X_displayName", string);
    }

    public IMESS88StatusGraphFilter forDisplayNameEqualTo(String string) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameEqualTo("X_displayName", (Object) string);
    }

    public IMESS88StatusGraphFilter forDisplayNameNotEqualTo(String string) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameNotEqualTo("X_displayName", (Object) string);
    }

    public IMESS88StatusGraphFilter forDisplayNameStartingWith(String string) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameStartingWith("X_displayName", string);
    }

    public IMESS88StatusGraphFilter forIdentifierContaining(String string) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameContaining("X_identifier", string);
    }

    public IMESS88StatusGraphFilter forIdentifierEqualTo(String string) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameEqualTo("X_identifier", (Object) string);
    }

    public IMESS88StatusGraphFilter forIdentifierNotEqualTo(String string) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameNotEqualTo("X_identifier", (Object) string);
    }

    public IMESS88StatusGraphFilter forIdentifierStartingWith(String string) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameStartingWith("X_identifier", string);
    }

    protected IMESS88StatusGraphFilter forNextTransitionIdEqualTo(Long l) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameEqualTo("X_nextTransitionId", (Object) l);
    }

    protected IMESS88StatusGraphFilter forNextTransitionIdGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameGreaterThanOrEqualTo("X_nextTransitionId", (Object) l);
    }

    protected IMESS88StatusGraphFilter forNextTransitionIdLessThan(Long l) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameLessThan("X_nextTransitionId", (Object) l);
    }

    protected IMESS88StatusGraphFilter forNextTransitionIdNotEqualTo(Long l) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameNotEqualTo("X_nextTransitionId", (Object) l);
    }

    public IMESS88StatusGraphFilter forPurposeEqualTo(Long l) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameEqualTo("X_purpose", (Object) l);
    }

    public IMESS88StatusGraphFilter forPurposeGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameGreaterThanOrEqualTo("X_purpose", (Object) l);
    }

    public IMESS88StatusGraphFilter forPurposeLessThan(Long l) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameLessThan("X_purpose", (Object) l);
    }

    public IMESS88StatusGraphFilter forPurposeNotEqualTo(Long l) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameNotEqualTo("X_purpose", (Object) l);
    }

    public IMESS88StatusGraphFilter forShortDescriptionContaining(String string) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameContaining("X_shortDescription", string);
    }

    public IMESS88StatusGraphFilter forShortDescriptionEqualTo(String string) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameEqualTo("X_shortDescription", (Object) string);
    }

    public IMESS88StatusGraphFilter forShortDescriptionNotEqualTo(String string) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameNotEqualTo("X_shortDescription", (Object) string);
    }

    public IMESS88StatusGraphFilter forShortDescriptionStartingWith(String string) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameStartingWith("X_shortDescription", string);
    }

    protected IMESS88StatusGraphFilter forStateProxyEqualTo(StateProxy stateProxy) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameEqualTo("X_stateProxy", (Object) stateProxy);
    }

    protected IMESS88StatusGraphFilter forStateProxyNotEqualTo(StateProxy stateProxy) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameNotEqualTo("X_stateProxy", (Object) stateProxy);
    }

    protected IMESS88StatusGraphFilter forTileColorEqualTo(Long l) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameEqualTo("X_tileColor", (Object) l);
    }

    protected IMESS88StatusGraphFilter forTileColorGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameGreaterThanOrEqualTo("X_tileColor", (Object) l);
    }

    protected IMESS88StatusGraphFilter forTileColorLessThan(Long l) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameLessThan("X_tileColor", (Object) l);
    }

    protected IMESS88StatusGraphFilter forTileColorNotEqualTo(Long l) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameNotEqualTo("X_tileColor", (Object) l);
    }

    protected IMESS88StatusGraphFilter forTileTextColorEqualTo(Long l) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameEqualTo("X_tileTextColor", (Object) l);
    }

    protected IMESS88StatusGraphFilter forTileTextColorGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameGreaterThanOrEqualTo("X_tileTextColor", (Object) l);
    }

    protected IMESS88StatusGraphFilter forTileTextColorLessThan(Long l) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameLessThan("X_tileTextColor", (Object) l);
    }

    protected IMESS88StatusGraphFilter forTileTextColorNotEqualTo(Long l) throws DatasweepException {
        return (IMESS88StatusGraphFilter) this.forColumnNameNotEqualTo("X_tileTextColor", (Object) l);
    }
}