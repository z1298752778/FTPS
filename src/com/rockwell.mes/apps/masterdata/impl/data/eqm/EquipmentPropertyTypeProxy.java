package com.rockwell.mes.apps.masterdata.impl.data.eqm;

import com.rockwell.mes.apps.masterdata.ifc.data.eqm.IEquipmentObjectType;
import com.rockwell.mes.apps.masterdata.ifc.ui.PropertyValue;
import com.rockwell.mes.apps.masterdata.ifc.ui.UIConstants;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.AbstractEquipmentProxy;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentPropertyTypeDataHolder;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.services.s88equipment.ifc.AbstractTechnicalEquipmentPropertyType;
import java.awt.Color;
import java.util.Collection;
import java.util.List;
import javax.swing.UIManager;

public class EquipmentPropertyTypeProxy extends AbstractEquipmentProxy<EquipmentPropertyTypeDataHolder> {
    private static final Color PROPERTYTYPE_BACKGROUND_COLOR = UIManager.getColor("RA-TileableComponent.PropertyType.background.color");

    private AbstractTechnicalEquipmentPropertyType technicalPropertyType;

    private IMESChoiceElement usage;

    private IMESChoiceElement purpose;

    private String processPackName;

    public EquipmentPropertyTypeProxy(IEquipmentObjectType iEquipmentObjectType) {
        super(iEquipmentObjectType, null, PROPERTYTYPE_BACKGROUND_COLOR, DEFAULT_FONT_COLOR, null);
    }

    protected final void initTechnicalPropertyType(AbstractTechnicalEquipmentPropertyType abstractTechnicalEquipmentPropertyType) {
        this.assureNotInitialized("TechnicalPropertyType");
        this.technicalPropertyType = abstractTechnicalEquipmentPropertyType;
    }

    public AbstractTechnicalEquipmentPropertyType getTechnicalPropertyType() {
        return this.technicalPropertyType;
    }

    private String getLocalizedTechnicalPropertyType() {
        return this.technicalPropertyType != null ? this.technicalPropertyType.getTechnicalType().getLocalizedMessage() : "";
    }

    private String getTechnicalPropertyTypeMeaning() {
        return this.technicalPropertyType != null ? this.technicalPropertyType.getTechnicalType().getMeaning() : null;
    }

    protected final void initUsage(IMESChoiceElement iMESChoiceElement) {
        this.usage = iMESChoiceElement;
    }

    private String getLocalizedUsage() {
        return this.usage != null ? this.usage.getLocalizedMessage() : "";
    }

    protected final void initPurpose(IMESChoiceElement iMESChoiceElement) {
        this.purpose = iMESChoiceElement;
    }

    private String getLocalizedPurpose() {
        return this.purpose != null ? this.purpose.getLocalizedMessage() : "";
    }

    protected void addPropertyValuesForCardView(List<PropertyValue> list) {
        super.addPropertyValuesForCardView(list);
        list.add(PropertyValue.createSeparator());
        list.add(PropertyValue.createNameValuePair((String) UIConstants.USAGE_CARD_LABEL, (String) this.getLocalizedUsage()));
        list.add(
                PropertyValue.createNameValuePair((String) UIConstants.TECHNICAL_TYPE_CARD_LABEL, (String) this.getLocalizedTechnicalPropertyType()));
        list.add(PropertyValue.createNameValuePair((String) UIConstants.PROPERTY_TYPE_PURPOSE_CARD_LABEL, (String) this.getLocalizedPurpose()));
        this.addDynamicPropertiesForCardView(list);
    }

    protected void addSearchTexts(Collection<String> collection) {
        super.addSearchTexts(collection);

        //
        collection.add(this.getProcessPackName());

        collection.add(this.getLocalizedTechnicalPropertyType());
        collection.add(this.getLocalizedUsage());
    }

    protected final void initProcessPackName(String string) {
        this.assureNotInitialized("ProcessPackName");
        this.processPackName = string;
    }

    public String getProcessPackName() {
        return this.processPackName;
    }

    public boolean isDraggable() {
        return true;
    }

    public String toString() {
        return "[equipment property type proxy: identifier=" + this.getIdentifier() + ", dbKey=" + this.getDBKey() + ", object type="
                + (Object) this.getObjectType() + ", title=" + this.getTitle() + ", groupId=" + this.getMainGroupId() + ", short desc="
                + this.getShortDescription() + ", desc=" + this.getDescription() + ", technical class name=" + this.getTechnicalPropertyTypeMeaning()
                + "]";
    }
}