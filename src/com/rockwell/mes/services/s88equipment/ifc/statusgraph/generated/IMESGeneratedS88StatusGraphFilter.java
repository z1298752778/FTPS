package com.rockwell.mes.services.s88equipment.ifc.statusgraph.generated;

import com.datasweep.compatibility.client.DatasweepException;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObjectFilter;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88EquipmentFilter;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IMESS88StatusGraph;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IMESS88StatusGraphFilter;
import java.util.List;

public interface IMESGeneratedS88StatusGraphFilter extends IMESATObjectFilter {
    public List<IMESS88StatusGraph> getFilteredObjects();

    public IMESS88StatusGraphFilter forDescriptionContaining(String var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forDescriptionEqualTo(String var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forDescriptionNotEqualTo(String var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forDescriptionStartingWith(String var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forDisplayNameContaining(String var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forDisplayNameEqualTo(String var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forDisplayNameNotEqualTo(String var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forDisplayNameStartingWith(String var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forIdentifierContaining(String var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forIdentifierEqualTo(String var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forIdentifierNotEqualTo(String var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forIdentifierStartingWith(String var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forPurposeEqualTo(Long var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forPurposeGreaterThanOrEqualTo(Long var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forPurposeLessThan(Long var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forPurposeNotEqualTo(Long var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forShortDescriptionContaining(String var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forShortDescriptionEqualTo(String var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forShortDescriptionNotEqualTo(String var1) throws DatasweepException;

    public IMESS88StatusGraphFilter forShortDescriptionStartingWith(String var1) throws DatasweepException;

    //
    public IMESS88EquipmentFilter forProcessPackNameContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forProcessPackNamEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forProcessPackNamNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forProcessPackNamStartingWith(String var1) throws DatasweepException;

}