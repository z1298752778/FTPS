package com.rockwell.mes.apps.masterdata.impl.data.eqm;

import com.rockwell.mes.apps.masterdata.ifc.data.AdvancedCardContentAttribute;
import com.rockwell.mes.apps.masterdata.ifc.data.IMasterDataObjectType;
import com.rockwell.mes.apps.masterdata.ifc.data.eqm.IEquipmentObjectType;
import com.rockwell.mes.apps.masterdata.ifc.ui.PropertyValue;
import com.rockwell.mes.apps.masterdata.ifc.ui.UIConstants;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.AbstractEquipmentProxy;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentStatusGraphDataHolder;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EquipmentStatusGraphProxy extends AbstractEquipmentProxy<EquipmentStatusGraphDataHolder> {
    private String displayName;

    private IMESChoiceElement purpose;

    private String purposeAsLocalizedMessage;

    @AdvancedCardContentAttribute
    private final List<String> propertyTypeNames = new ArrayList<String>();

    private String localizedStatus;

    private String processPackName;

    public EquipmentStatusGraphProxy(IEquipmentObjectType iEquipmentObjectType, Color color, Color color2) {
        super(iEquipmentObjectType, null, color, color2, null);
    }

    protected void addPropertyValuesForCardView(List<PropertyValue> list) {
        super.addPropertyValuesForCardView(list);
        list.add(PropertyValue.createSeparator());
        list.add(PropertyValue.createNameValuePair((String) UIConstants.DISPLAY_NAME_CARD_LABEL, (String) this.getDisplayName()));
        list.add(PropertyValue.createNameValuePair((String) UIConstants.STATUS_GRAPH_PURPOSE_CARD_LABEL, (String) this.purposeAsLocalizedMessage));
        list.add(PropertyValue.createSeparator());
        this.addDynamicPropertiesForCardView(list);
    }

    protected void addSearchTexts(Collection<String> collection) {
        super.addSearchTexts(collection);
        collection.add(this.getDisplayName());
        //
        collection.add(this.getProcessPackName());

        collection.add(this.purposeAsLocalizedMessage);
        for (String string : this.propertyTypeNames) {
            collection.add(string);
        }
    }

    protected final void initProcessPackName(String string) {
        this.assureNotInitialized("ProcessPackName");
        this.processPackName = string;
    }

    public String getProcessPackName() {
        return this.processPackName;
    }

    protected final void initDisplayName(String string) {
        this.assureNotInitialized("DisplayName");
        this.displayName = string;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    protected final void initPurpose(IMESChoiceElement iMESChoiceElement) {
        this.assureNotInitialized("Purpose");
        this.purpose = iMESChoiceElement;
        this.purposeAsLocalizedMessage = this.purpose.getLocalizedMessage();
    }

    protected final void initPropertyTypeNames(Collection<String> collection) {
        this.assureNotInitialized("PropertyTypeNames");
        this.propertyTypeNames.addAll(collection);
    }

    public IMESChoiceElement getPurpose() {
        return this.purpose;
    }

    protected void initLocalizedStatus(String string) {
        this.assureNotInitialized("LocalizedStatus");
        this.localizedStatus = string;
    }

    public String getStatus() {
        return this.localizedStatus;
    }

    public void normalize() {
        Collections.sort(this.propertyTypeNames);
    }

    public boolean isDraggable() {
        return true;
    }

    public String toString() {
        return "[equipment status graph proxy: identifier=" + this.getIdentifier() + ", object type=" + (Object) this.getObjectType() + ", dbKey="
                + this.getDBKey() + " title=" + this.getTitle() + ", groupId=" + this.getMainGroupId() + " / " + this.getSubGroupId()
                + ", short desc=" + this.getShortDescription() + ", desc=" + this.getDescription() + ", displayName=" + this.getDisplayName()
                + ", purpose=" + (Object) this.getPurpose() + ", status=" + this.getStatus() + ", propertyTypeNames=" + this.propertyTypeNames + "]";
    }
}