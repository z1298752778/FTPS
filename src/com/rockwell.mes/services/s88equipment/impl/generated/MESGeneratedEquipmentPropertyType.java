package com.rockwell.mes.services.s88equipment.impl.generated;

import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.FlexibleStateModel;
import com.datasweep.compatibility.client.Response;
import com.datasweep.compatibility.client.UnitOfMeasure;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceList;
import com.rockwell.mes.commons.base.ifc.choicelist.MESChoiceListHelper;
import com.rockwell.mes.commons.base.ifc.objects.BulkSaveableMESATObject;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectReferenceFieldHandler;
import com.rockwell.mes.commons.livedata.ifc.IMESTagGroupDefinition;
import com.rockwell.mes.services.s88equipment.ifc.generated.IMESGeneratedEquipmentPropertyType;
import java.beans.PropertyChangeSupport;

public abstract class MESGeneratedEquipmentPropertyType extends BulkSaveableMESATObject implements IMESGeneratedEquipmentPropertyType {
    private final MESATObjectReferenceFieldHandler<FlexibleStateModel> refAutomaticFSM =
            MESATObjectReferenceFieldHandler.createReferenceFieldHandler((IMESATObject) this, FlexibleStateModel.class, (String) "X_automaticFSM");

    private final MESATObjectReferenceFieldHandler<IMESChoiceList> refChoiceList =
            MESATObjectReferenceFieldHandler.createReferenceFieldHandler((IMESATObject) this, IMESChoiceList.class, (String) "X_choiceList");

    private final MESATObjectReferenceFieldHandler<UnitOfMeasure> refEngineeringUnit =
            MESATObjectReferenceFieldHandler.createReferenceFieldHandler((IMESATObject) this, UnitOfMeasure.class, (String) "X_engineeringUnit");

    private final MESATObjectReferenceFieldHandler<IMESTagGroupDefinition> refTagGroupDefinition = MESATObjectReferenceFieldHandler
            .createReferenceFieldHandler((IMESATObject) this, IMESTagGroupDefinition.class, (String) "X_tagGroupDefinition");

    public String getATDefinitionName() {
        return "X_EquipmentPropertyType";
    }

    public MESGeneratedEquipmentPropertyType(long l) {
        super(l);
    }

    public MESGeneratedEquipmentPropertyType(MESGeneratedEquipmentPropertyType mESGeneratedEquipmentPropertyType) {
        super((BulkSaveableMESATObject) mESGeneratedEquipmentPropertyType);
        this.refAutomaticFSM.initFromCopyConstructor(mESGeneratedEquipmentPropertyType.refAutomaticFSM);
        this.refChoiceList.initFromCopyConstructor(mESGeneratedEquipmentPropertyType.refChoiceList);
        this.refEngineeringUnit.initFromCopyConstructor(mESGeneratedEquipmentPropertyType.refEngineeringUnit);
        this.refTagGroupDefinition.initFromCopyConstructor(mESGeneratedEquipmentPropertyType.refTagGroupDefinition);
    }

    public MESGeneratedEquipmentPropertyType(ATRow aTRow) {
        super(aTRow);
    }

    public MESGeneratedEquipmentPropertyType() {
    }

    protected void synchronizeAfterATRowRefresh() {
        this.refAutomaticFSM.synchronizeAfterATRowRefresh();
        this.refChoiceList.synchronizeAfterATRowRefresh();
        this.refEngineeringUnit.synchronizeAfterATRowRefresh();
        this.refTagGroupDefinition.synchronizeAfterATRowRefresh();
        super.synchronizeAfterATRowRefresh();
    }

    public FlexibleStateModel getAutomaticFSM() {
        return (FlexibleStateModel) this.refAutomaticFSM.getReference();
    }

    public void setAutomaticFSM(FlexibleStateModel flexibleStateModel) {
        this.refAutomaticFSM.setReference((FlexibleStateModel) flexibleStateModel, this.pcs, "automaticFSM");
    }

    public IMESChoiceElement getCapability() {
        Long l = (Long) this.dgtATRow.getValue("X_capability");
        return MESChoiceListHelper.getChoiceElement((String) "S88EquipmentCapability", (Long) l);
    }

    public final String getCapabilityAsMeaning() {
        return null == this.getCapability() ? null : this.getCapability().getMeaning();
    }

    public final Long getCapabilityAsValue() {
        return null == this.getCapability() ? null : this.getCapability().getValue();
    }

    public void setCapability(IMESChoiceElement iMESChoiceElement) {
        IMESChoiceElement iMESChoiceElement2 = this.getCapability();
        String string = iMESChoiceElement2 != null ? iMESChoiceElement2.getMeaning() : null;
        String string2 = iMESChoiceElement != null ? iMESChoiceElement.getMeaning() : null;
        Long l = iMESChoiceElement2 != null ? iMESChoiceElement2.getValue() : null;
        Long l2 = iMESChoiceElement != null ? iMESChoiceElement.getValue() : null;
        this.dgtATRow.setValue("X_capability", (Object) l2);
        this.firePropertyChange("capability", (Object) iMESChoiceElement2, (Object) iMESChoiceElement);
        this.firePropertyChange("capabilityAsMeaning", (Object) string, (Object) string2);
        this.firePropertyChange("capabilityAsValue", (Object) l, (Object) l2);
    }

    public String getProcessPackName() {
        return (String) this.dgtATRow.getValue("processPackName");
    }

    public void setProcessPackName(String string) {

        // TODO
        String string2 = this.getProcessPackName();
        this.dgtATRow.setValue("processPackName", (Object) string);
        this.firePropertyChange("processPackName", (Object) string2, (Object) string);
    }

    public final void setCapabilityAsMeaning(String string) {
        this.setCapability(null == string ? null : MESChoiceListHelper.getChoiceElement((String) "S88EquipmentCapability", (String) string));
    }

    public final void setCapabilityAsValue(Long l) {
        this.setCapability(null == l ? null : MESChoiceListHelper.getChoiceElement((String) "S88EquipmentCapability", (Long) l));
    }

    public IMESChoiceList getChoiceList() {
        return (IMESChoiceList) this.refChoiceList.getReference();
    }

    public void setChoiceList(IMESChoiceList iMESChoiceList) {
        this.refChoiceList.setReference((IMESChoiceList) iMESChoiceList, this.pcs, "choiceList");
    }

    public String getChoiceListName() {
        return (String) this.dgtATRow.getValue("X_choiceListName");
    }

    public void setChoiceListName(String string) {
        String string2 = this.getChoiceListName();
        this.dgtATRow.setValue("X_choiceListName", (Object) string);
        this.firePropertyChange("choiceListName", (Object) string2, (Object) string);
    }

    public String getClassName() {
        return (String) this.dgtATRow.getValue("X_className");
    }

    public void setClassName(String string) {
        String string2 = this.getClassName();
        this.dgtATRow.setValue("X_className", (Object) string);
        this.firePropertyChange("className", (Object) string2, (Object) string);
    }

    public String getDescription() {
        return (String) this.dgtATRow.getValue("X_description");
    }

    public void setDescription(String string) {
        String string2 = this.getDescription();
        this.dgtATRow.setValue("X_description", (Object) string);
        this.firePropertyChange("description", (Object) string2, (Object) string);
    }

    public UnitOfMeasure getEngineeringUnit() {
        return (UnitOfMeasure) this.refEngineeringUnit.getReference();
    }

    public void setEngineeringUnit(UnitOfMeasure unitOfMeasure) {
        this.refEngineeringUnit.setReference((UnitOfMeasure) unitOfMeasure, this.pcs, "engineeringUnit");
    }

    public String getFsmRelationshipName() {
        return (String) this.dgtATRow.getValue("X_fsmRelationshipName");
    }

    public void setFsmRelationshipName(String string) {
        String string2 = this.getFsmRelationshipName();
        this.dgtATRow.setValue("X_fsmRelationshipName", (Object) string);
        this.firePropertyChange("fsmRelationshipName", (Object) string2, (Object) string);
    }

    public String getIdentifier() {
        return (String) this.dgtATRow.getValue("X_identifier");
    }

    public void setIdentifier(String string) {
        String string2 = this.getIdentifier();
        this.dgtATRow.setValue("X_identifier", (Object) string);
        this.firePropertyChange("identifier", (Object) string2, (Object) string);
    }

    public IMESChoiceElement getLiveDateType() {
        Long l = (Long) this.dgtATRow.getValue("X_liveDateType");
        return MESChoiceListHelper.getChoiceElement((String) "EqmLiveDataTypeNumeric", (Long) l);
    }

    public final String getLiveDateTypeAsMeaning() {
        return null == this.getLiveDateType() ? null : this.getLiveDateType().getMeaning();
    }

    public final Long getLiveDateTypeAsValue() {
        return null == this.getLiveDateType() ? null : this.getLiveDateType().getValue();
    }

    public void setLiveDateType(IMESChoiceElement iMESChoiceElement) {
        IMESChoiceElement iMESChoiceElement2 = this.getLiveDateType();
        String string = iMESChoiceElement2 != null ? iMESChoiceElement2.getMeaning() : null;
        String string2 = iMESChoiceElement != null ? iMESChoiceElement.getMeaning() : null;
        Long l = iMESChoiceElement2 != null ? iMESChoiceElement2.getValue() : null;
        Long l2 = iMESChoiceElement != null ? iMESChoiceElement.getValue() : null;
        this.dgtATRow.setValue("X_liveDateType", (Object) l2);
        this.firePropertyChange("liveDateType", (Object) iMESChoiceElement2, (Object) iMESChoiceElement);
        this.firePropertyChange("liveDateTypeAsMeaning", (Object) string, (Object) string2);
        this.firePropertyChange("liveDateTypeAsValue", (Object) l, (Object) l2);
    }

    public final void setLiveDateTypeAsMeaning(String string) {
        this.setLiveDateType(null == string ? null : MESChoiceListHelper.getChoiceElement((String) "EqmLiveDataTypeNumeric", (String) string));
    }

    public final void setLiveDateTypeAsValue(Long l) {
        this.setLiveDateType(null == l ? null : MESChoiceListHelper.getChoiceElement((String) "EqmLiveDataTypeNumeric", (Long) l));
    }

    public String getObjectIdentifier() {
        return (String) this.dgtATRow.getValue("X_objectIdentifier");
    }

    public void setObjectIdentifier(String string) {
        String string2 = this.getObjectIdentifier();
        this.dgtATRow.setValue("X_objectIdentifier", (Object) string);
        this.firePropertyChange("objectIdentifier", (Object) string2, (Object) string);
    }

    public Long getObjectType() {
        return (Long) this.dgtATRow.getValue("X_objectType");
    }

    public void setObjectType(Long l) {
        Long l2 = this.getObjectType();
        this.dgtATRow.setValue("X_objectType", (Object) l);
        this.firePropertyChange("objectType", (Object) l2, (Object) l);
    }

    public IMESChoiceElement getPurpose() {
        Long l = (Long) this.dgtATRow.getValue("X_purpose");
        return MESChoiceListHelper.getChoiceElement((String) "S88EquipmentPropertyTypePurpose", (Long) l);
    }

    public final String getPurposeAsMeaning() {
        return null == this.getPurpose() ? null : this.getPurpose().getMeaning();
    }

    public final Long getPurposeAsValue() {
        return null == this.getPurpose() ? null : this.getPurpose().getValue();
    }

    public void setPurpose(IMESChoiceElement iMESChoiceElement) {
        IMESChoiceElement iMESChoiceElement2 = this.getPurpose();
        String string = iMESChoiceElement2 != null ? iMESChoiceElement2.getMeaning() : null;
        String string2 = iMESChoiceElement != null ? iMESChoiceElement.getMeaning() : null;
        Long l = iMESChoiceElement2 != null ? iMESChoiceElement2.getValue() : null;
        Long l2 = iMESChoiceElement != null ? iMESChoiceElement.getValue() : null;
        this.dgtATRow.setValue("X_purpose", (Object) l2);
        this.firePropertyChange("purpose", (Object) iMESChoiceElement2, (Object) iMESChoiceElement);
        this.firePropertyChange("purposeAsMeaning", (Object) string, (Object) string2);
        this.firePropertyChange("purposeAsValue", (Object) l, (Object) l2);
    }

    public final void setPurposeAsMeaning(String string) {
        this.setPurpose(null == string ? null : MESChoiceListHelper.getChoiceElement((String) "S88EquipmentPropertyTypePurpose", (String) string));
    }

    public final void setPurposeAsValue(Long l) {
        this.setPurpose(null == l ? null : MESChoiceListHelper.getChoiceElement((String) "S88EquipmentPropertyTypePurpose", (Long) l));
    }

    public String getShortDescription() {
        return (String) this.dgtATRow.getValue("X_shortDescription");
    }

    public void setShortDescription(String string) {
        String string2 = this.getShortDescription();
        this.dgtATRow.setValue("X_shortDescription", (Object) string);
        this.firePropertyChange("shortDescription", (Object) string2, (Object) string);
    }

    public String getStringValue() {
        return (String) this.dgtATRow.getValue("X_stringValue");
    }

    public void setStringValue(String string) {
        String string2 = this.getStringValue();
        this.dgtATRow.setValue("X_stringValue", (Object) string);
        this.firePropertyChange("stringValue", (Object) string2, (Object) string);
    }

    public IMESTagGroupDefinition getTagGroupDefinition() {
        return (IMESTagGroupDefinition) this.refTagGroupDefinition.getReference();
    }

    public void setTagGroupDefinition(IMESTagGroupDefinition iMESTagGroupDefinition) {
        this.refTagGroupDefinition.setReference((IMESTagGroupDefinition) iMESTagGroupDefinition, this.pcs, "tagGroupDefinition");
    }

    public IMESChoiceElement getTechnicalType() {
        Long l = (Long) this.dgtATRow.getValue("X_technicalType");
        return MESChoiceListHelper.getChoiceElement((String) "TechnicalEquipmentPropertyType", (Long) l);
    }

    public final String getTechnicalTypeAsMeaning() {
        return null == this.getTechnicalType() ? null : this.getTechnicalType().getMeaning();
    }

    public final Long getTechnicalTypeAsValue() {
        return null == this.getTechnicalType() ? null : this.getTechnicalType().getValue();
    }

    public void setTechnicalType(IMESChoiceElement iMESChoiceElement) {
        IMESChoiceElement iMESChoiceElement2 = this.getTechnicalType();
        String string = iMESChoiceElement2 != null ? iMESChoiceElement2.getMeaning() : null;
        String string2 = iMESChoiceElement != null ? iMESChoiceElement.getMeaning() : null;
        Long l = iMESChoiceElement2 != null ? iMESChoiceElement2.getValue() : null;
        Long l2 = iMESChoiceElement != null ? iMESChoiceElement.getValue() : null;
        this.dgtATRow.setValue("X_technicalType", (Object) l2);
        this.firePropertyChange("technicalType", (Object) iMESChoiceElement2, (Object) iMESChoiceElement);
        this.firePropertyChange("technicalTypeAsMeaning", (Object) string, (Object) string2);
        this.firePropertyChange("technicalTypeAsValue", (Object) l, (Object) l2);
    }

    public final void setTechnicalTypeAsMeaning(String string) {
        this.setTechnicalType(
                null == string ? null : MESChoiceListHelper.getChoiceElement((String) "TechnicalEquipmentPropertyType", (String) string));
    }

    public final void setTechnicalTypeAsValue(Long l) {
        this.setTechnicalType(null == l ? null : MESChoiceListHelper.getChoiceElement((String) "TechnicalEquipmentPropertyType", (Long) l));
    }

    public IMESChoiceElement getUsage() {
        Long l = (Long) this.dgtATRow.getValue("X_usage");
        return MESChoiceListHelper.getChoiceElement((String) "EqmPropertyTypeUsage", (Long) l);
    }

    public final String getUsageAsMeaning() {
        return null == this.getUsage() ? null : this.getUsage().getMeaning();
    }

    public final Long getUsageAsValue() {
        return null == this.getUsage() ? null : this.getUsage().getValue();
    }

    public void setUsage(IMESChoiceElement iMESChoiceElement) {
        IMESChoiceElement iMESChoiceElement2 = this.getUsage();
        String string = iMESChoiceElement2 != null ? iMESChoiceElement2.getMeaning() : null;
        String string2 = iMESChoiceElement != null ? iMESChoiceElement.getMeaning() : null;
        Long l = iMESChoiceElement2 != null ? iMESChoiceElement2.getValue() : null;
        Long l2 = iMESChoiceElement != null ? iMESChoiceElement.getValue() : null;
        this.dgtATRow.setValue("X_usage", (Object) l2);
        this.firePropertyChange("usage", (Object) iMESChoiceElement2, (Object) iMESChoiceElement);
        this.firePropertyChange("usageAsMeaning", (Object) string, (Object) string2);
        this.firePropertyChange("usageAsValue", (Object) l, (Object) l2);
    }

    public final void setUsageAsMeaning(String string) {
        this.setUsage(null == string ? null : MESChoiceListHelper.getChoiceElement((String) "EqmPropertyTypeUsage", (String) string));
    }

    public final void setUsageAsValue(Long l) {
        this.setUsage(null == l ? null : MESChoiceListHelper.getChoiceElement((String) "EqmPropertyTypeUsage", (Long) l));
    }

    protected Response prepareATRowForSave() {
        Response response = super.prepareATRowForSave();
        this.refAutomaticFSM.prepareATRowForSave(response);
        this.refChoiceList.prepareATRowForSave(response);
        this.refEngineeringUnit.prepareATRowForSave(response);
        this.refTagGroupDefinition.prepareATRowForSave(response);
        return response;
    }
}