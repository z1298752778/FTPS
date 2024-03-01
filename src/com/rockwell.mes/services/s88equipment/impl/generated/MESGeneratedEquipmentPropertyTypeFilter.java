package com.rockwell.mes.services.s88equipment.impl.generated;

import com.datasweep.compatibility.client.ATRowFilter;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.FlexibleStateModel;
import com.datasweep.compatibility.client.Server;
import com.datasweep.compatibility.client.UnitOfMeasure;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceList;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectFilter;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.commons.livedata.ifc.IMESTagGroupDefinition;
import com.rockwell.mes.services.s88equipment.ifc.IMESEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.IMESEquipmentPropertyTypeFilter;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88EquipmentFilter;
import com.rockwell.mes.services.s88equipment.ifc.generated.IMESGeneratedEquipmentPropertyTypeFilter;
import java.util.List;

public abstract class MESGeneratedEquipmentPropertyTypeFilter extends MESATObjectFilter implements IMESGeneratedEquipmentPropertyTypeFilter {
    private static final long serialVersionUID = 1L;

    protected static final String ATDEFINITION_NAME = "X_EquipmentPropertyType";

    public MESGeneratedEquipmentPropertyTypeFilter(Server server) {
        super(server, ATDEFINITION_NAME);
    }

    public MESGeneratedEquipmentPropertyTypeFilter() {
        this((Server) PCContext.getServerImpl());
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

    public List<IMESEquipmentPropertyType> getFilteredObjects() {
        return MESATObject.getFilteredMESATObjectList((ATRowFilter) this, IMESEquipmentPropertyType.class);
    }

    public IMESEquipmentPropertyTypeFilter forAutomaticFSMEqualTo(FlexibleStateModel flexibleStateModel) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_automaticFSM", (Object) flexibleStateModel);
    }

    public IMESEquipmentPropertyTypeFilter forAutomaticFSMNotEqualTo(FlexibleStateModel flexibleStateModel) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_automaticFSM", (Object) flexibleStateModel);
    }

    public IMESEquipmentPropertyTypeFilter forCapabilityEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_capability", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forCapabilityGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameGreaterThanOrEqualTo("X_capability", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forCapabilityLessThan(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameLessThan("X_capability", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forCapabilityNotEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_capability", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forChoiceListEqualTo(IMESChoiceList iMESChoiceList) throws DatasweepException {
        Long l = iMESChoiceList == null ? null : Long.valueOf(iMESChoiceList.getKey());
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_choiceList", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forChoiceListNotEqualTo(IMESChoiceList iMESChoiceList) throws DatasweepException {
        Long l = iMESChoiceList == null ? null : Long.valueOf(iMESChoiceList.getKey());
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_choiceList", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forChoiceListNameContaining(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameContaining("X_choiceListName", string);
    }

    public IMESEquipmentPropertyTypeFilter forChoiceListNameEqualTo(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_choiceListName", (Object) string);
    }

    public IMESEquipmentPropertyTypeFilter forChoiceListNameNotEqualTo(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_choiceListName", (Object) string);
    }

    public IMESEquipmentPropertyTypeFilter forChoiceListNameStartingWith(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameStartingWith("X_choiceListName", string);
    }

    public IMESEquipmentPropertyTypeFilter forClassNameContaining(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameContaining("X_className", string);
    }

    public IMESEquipmentPropertyTypeFilter forClassNameEqualTo(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_className", (Object) string);
    }

    public IMESEquipmentPropertyTypeFilter forClassNameNotEqualTo(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_className", (Object) string);
    }

    public IMESEquipmentPropertyTypeFilter forClassNameStartingWith(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameStartingWith("X_className", string);
    }

    public IMESEquipmentPropertyTypeFilter forDescriptionContaining(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameContaining("X_description", string);
    }

    public IMESEquipmentPropertyTypeFilter forDescriptionEqualTo(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_description", (Object) string);
    }

    public IMESEquipmentPropertyTypeFilter forDescriptionNotEqualTo(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_description", (Object) string);
    }

    public IMESEquipmentPropertyTypeFilter forDescriptionStartingWith(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameStartingWith("X_description", string);
    }

    public IMESEquipmentPropertyTypeFilter forEngineeringUnitEqualTo(UnitOfMeasure unitOfMeasure) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_engineeringUnit", (Object) unitOfMeasure);
    }

    public IMESEquipmentPropertyTypeFilter forEngineeringUnitNotEqualTo(UnitOfMeasure unitOfMeasure) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_engineeringUnit", (Object) unitOfMeasure);
    }

    public IMESEquipmentPropertyTypeFilter forFsmRelationshipNameContaining(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameContaining("X_fsmRelationshipName", string);
    }

    public IMESEquipmentPropertyTypeFilter forFsmRelationshipNameEqualTo(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_fsmRelationshipName", (Object) string);
    }

    public IMESEquipmentPropertyTypeFilter forFsmRelationshipNameNotEqualTo(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_fsmRelationshipName", (Object) string);
    }

    public IMESEquipmentPropertyTypeFilter forFsmRelationshipNameStartingWith(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameStartingWith("X_fsmRelationshipName", string);
    }

    public IMESEquipmentPropertyTypeFilter forIdentifierContaining(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameContaining("X_identifier", string);
    }

    public IMESEquipmentPropertyTypeFilter forIdentifierEqualTo(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_identifier", (Object) string);
    }

    public IMESEquipmentPropertyTypeFilter forIdentifierNotEqualTo(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_identifier", (Object) string);
    }

    public IMESEquipmentPropertyTypeFilter forIdentifierStartingWith(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameStartingWith("X_identifier", string);
    }

    public IMESEquipmentPropertyTypeFilter forLiveDateTypeEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_liveDateType", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forLiveDateTypeGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameGreaterThanOrEqualTo("X_liveDateType", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forLiveDateTypeLessThan(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameLessThan("X_liveDateType", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forLiveDateTypeNotEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_liveDateType", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forObjectIdentifierContaining(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameContaining("X_objectIdentifier", string);
    }

    public IMESEquipmentPropertyTypeFilter forObjectIdentifierEqualTo(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_objectIdentifier", (Object) string);
    }

    public IMESEquipmentPropertyTypeFilter forObjectIdentifierNotEqualTo(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_objectIdentifier", (Object) string);
    }

    public IMESEquipmentPropertyTypeFilter forObjectIdentifierStartingWith(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameStartingWith("X_objectIdentifier", string);
    }

    public IMESEquipmentPropertyTypeFilter forObjectTypeEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_objectType", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forObjectTypeGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameGreaterThanOrEqualTo("X_objectType", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forObjectTypeLessThan(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameLessThan("X_objectType", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forObjectTypeNotEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_objectType", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forPurposeEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_purpose", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forPurposeGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameGreaterThanOrEqualTo("X_purpose", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forPurposeLessThan(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameLessThan("X_purpose", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forPurposeNotEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_purpose", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forShortDescriptionContaining(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameContaining("X_shortDescription", string);
    }

    public IMESEquipmentPropertyTypeFilter forShortDescriptionEqualTo(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_shortDescription", (Object) string);
    }

    public IMESEquipmentPropertyTypeFilter forShortDescriptionNotEqualTo(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_shortDescription", (Object) string);
    }

    public IMESEquipmentPropertyTypeFilter forShortDescriptionStartingWith(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameStartingWith("X_shortDescription", string);
    }

    public IMESEquipmentPropertyTypeFilter forStringValueContaining(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameContaining("X_stringValue", string);
    }

    public IMESEquipmentPropertyTypeFilter forStringValueEqualTo(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_stringValue", (Object) string);
    }

    public IMESEquipmentPropertyTypeFilter forStringValueNotEqualTo(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_stringValue", (Object) string);
    }

    public IMESEquipmentPropertyTypeFilter forStringValueStartingWith(String string) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameStartingWith("X_stringValue", string);
    }

    public IMESEquipmentPropertyTypeFilter forTagGroupDefinitionEqualTo(IMESTagGroupDefinition iMESTagGroupDefinition) throws DatasweepException {
        Long l = iMESTagGroupDefinition == null ? null : Long.valueOf(iMESTagGroupDefinition.getKey());
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_tagGroupDefinition", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forTagGroupDefinitionNotEqualTo(IMESTagGroupDefinition iMESTagGroupDefinition) throws DatasweepException {
        Long l = iMESTagGroupDefinition == null ? null : Long.valueOf(iMESTagGroupDefinition.getKey());
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_tagGroupDefinition", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forTechnicalTypeEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_technicalType", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forTechnicalTypeGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameGreaterThanOrEqualTo("X_technicalType", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forTechnicalTypeLessThan(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameLessThan("X_technicalType", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forTechnicalTypeNotEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_technicalType", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forUsageEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameEqualTo("X_usage", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forUsageGreaterThanOrEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameGreaterThanOrEqualTo("X_usage", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forUsageLessThan(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameLessThan("X_usage", (Object) l);
    }

    public IMESEquipmentPropertyTypeFilter forUsageNotEqualTo(Long l) throws DatasweepException {
        return (IMESEquipmentPropertyTypeFilter) this.forColumnNameNotEqualTo("X_usage", (Object) l);
    }
}