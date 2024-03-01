package com.rockwell.mes.services.s88equipment.ifc.generated;

import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.FlexibleStateModel;
import com.datasweep.compatibility.client.UnitOfMeasure;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceList;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObjectFilter;
import com.rockwell.mes.commons.livedata.ifc.IMESTagGroupDefinition;
import com.rockwell.mes.services.s88equipment.ifc.IMESEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.IMESEquipmentPropertyTypeFilter;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88EquipmentFilter;

import java.util.List;

public interface IMESGeneratedEquipmentPropertyTypeFilter extends IMESATObjectFilter {
    public List<IMESEquipmentPropertyType> getFilteredObjects();

    //
    public IMESS88EquipmentFilter forProcessPackNameContaining(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forProcessPackNamEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forProcessPackNamNotEqualTo(String var1) throws DatasweepException;

    public IMESS88EquipmentFilter forProcessPackNamStartingWith(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forAutomaticFSMEqualTo(FlexibleStateModel var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forAutomaticFSMNotEqualTo(FlexibleStateModel var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forCapabilityEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forCapabilityGreaterThanOrEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forCapabilityLessThan(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forCapabilityNotEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forChoiceListEqualTo(IMESChoiceList var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forChoiceListNotEqualTo(IMESChoiceList var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forChoiceListNameContaining(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forChoiceListNameEqualTo(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forChoiceListNameNotEqualTo(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forChoiceListNameStartingWith(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forClassNameContaining(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forClassNameEqualTo(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forClassNameNotEqualTo(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forClassNameStartingWith(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forDescriptionContaining(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forDescriptionEqualTo(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forDescriptionNotEqualTo(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forDescriptionStartingWith(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forEngineeringUnitEqualTo(UnitOfMeasure var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forEngineeringUnitNotEqualTo(UnitOfMeasure var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forFsmRelationshipNameContaining(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forFsmRelationshipNameEqualTo(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forFsmRelationshipNameNotEqualTo(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forFsmRelationshipNameStartingWith(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forIdentifierContaining(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forIdentifierEqualTo(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forIdentifierNotEqualTo(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forIdentifierStartingWith(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forLiveDateTypeEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forLiveDateTypeGreaterThanOrEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forLiveDateTypeLessThan(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forLiveDateTypeNotEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forObjectIdentifierContaining(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forObjectIdentifierEqualTo(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forObjectIdentifierNotEqualTo(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forObjectIdentifierStartingWith(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forObjectTypeEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forObjectTypeGreaterThanOrEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forObjectTypeLessThan(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forObjectTypeNotEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forPurposeEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forPurposeGreaterThanOrEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forPurposeLessThan(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forPurposeNotEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forShortDescriptionContaining(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forShortDescriptionEqualTo(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forShortDescriptionNotEqualTo(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forShortDescriptionStartingWith(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forStringValueContaining(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forStringValueEqualTo(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forStringValueNotEqualTo(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forStringValueStartingWith(String var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forTagGroupDefinitionEqualTo(IMESTagGroupDefinition var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forTagGroupDefinitionNotEqualTo(IMESTagGroupDefinition var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forTechnicalTypeEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forTechnicalTypeGreaterThanOrEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forTechnicalTypeLessThan(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forTechnicalTypeNotEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forUsageEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forUsageGreaterThanOrEqualTo(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forUsageLessThan(Long var1) throws DatasweepException;

    public IMESEquipmentPropertyTypeFilter forUsageNotEqualTo(Long var1) throws DatasweepException;
}