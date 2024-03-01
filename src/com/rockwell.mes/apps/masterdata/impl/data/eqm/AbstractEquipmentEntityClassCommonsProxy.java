package com.rockwell.mes.apps.masterdata.impl.data.eqm;

import com.rockwell.mes.apps.masterdata.ifc.data.eqm.IEquipmentObjectType;
import com.rockwell.mes.apps.masterdata.ifc.ui.PropertyValue;
import com.rockwell.mes.apps.masterdata.ifc.ui.UIConstants;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.AbstractEquipmentEntityClassCommonsDataHolder;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.AbstractEquipmentProxy;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import java.awt.Color;
import java.util.List;

public abstract class AbstractEquipmentEntityClassCommonsProxy<HolderType extends AbstractEquipmentEntityClassCommonsDataHolder>
        extends AbstractEquipmentProxy<HolderType> {
    private String manufacturer;

    private String modelName;

    private String serialNumber;

    private IMESChoiceElement equipmentLevel;

    private String equipmentLevelAsLocalizedMessage;

    private String localizedStatus;

    //
    private String processPackName;

    public AbstractEquipmentEntityClassCommonsProxy(IEquipmentObjectType iEquipmentObjectType, String string, Color color, Color color2,
            String string2) {
        super(iEquipmentObjectType, string, color, color2, string2);
    }

    //
    public String getProcessPackName() {
        return this.processPackName;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public String getModelName() {
        return this.modelName;
    }

    public IMESChoiceElement getEquipmentLevel() {
        return this.equipmentLevel;
    }

    public String getEquipmentLevelAsLocalizedMessage() {
        return this.equipmentLevelAsLocalizedMessage;
    }

    public String getStatus() {
        return this.localizedStatus;
    }

    protected void addPropertyValuesForCardView(List<PropertyValue> list) {
        super.addPropertyValuesForCardView(list);
        list.add(PropertyValue.createSeparator());
        list.add(PropertyValue.createNameValuePair((String) UIConstants.MODEL_CARD_LABEL, (String) this.getModelName()));
        list.add(PropertyValue.createNameValuePair((String) UIConstants.MANUFACTURER_CARD_LABEL, (String) this.getManufacturer()));
        list.add(PropertyValue.createNameValuePair((String) UIConstants.LEVEL_CARD_LABEL, (String) this.equipmentLevelAsLocalizedMessage));
        list.add(PropertyValue.createSeparator());
        this.addDynamicPropertiesForCardView(list);
    }

    protected final void initManufacturer(String string) {
        this.assureNotInitialized("Manufacturer");
        this.manufacturer = string;
    }

    protected final void initModelName(String string) {
        this.assureNotInitialized("ModelName");
        this.modelName = string;
    }

    protected final void initEquipmentLevel(IMESChoiceElement iMESChoiceElement) {
        this.assureNotInitialized("EquipmentLevel");
        this.equipmentLevel = iMESChoiceElement;
        this.equipmentLevelAsLocalizedMessage = this.equipmentLevel.getLocalizedMessage();
    }

    protected final void initSerialNumber(String string) {
        this.assureNotInitialized("SerialNumber");
        this.serialNumber = string;
    }

    public final void initLocalizedStatus(String string) {
        this.assureNotInitialized("LocalizedStatus");
        this.localizedStatus = string;
    }

    protected final void initProcessPackName(String string) {
        this.assureNotInitialized("ProcessPackName");
        this.processPackName = string;
    }

}