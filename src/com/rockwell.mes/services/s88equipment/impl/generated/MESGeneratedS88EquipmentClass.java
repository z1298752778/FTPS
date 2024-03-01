package com.rockwell.mes.services.s88equipment.impl.generated;

import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.Response;
import com.datasweep.compatibility.client.StateProxy;
import com.datasweep.compatibility.ui.Time;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.choicelist.MESChoiceListHelper;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectReferenceFieldHandler;
import com.rockwell.mes.services.s88equipment.ifc.AbstractStatusControlledS88EquipmentWithPropertiesAndChangeHistory;
import com.rockwell.mes.services.s88equipment.ifc.generated.IMESGeneratedS88EquipmentClass;
import java.beans.PropertyChangeSupport;

public abstract class MESGeneratedS88EquipmentClass extends AbstractStatusControlledS88EquipmentWithPropertiesAndChangeHistory
        implements IMESGeneratedS88EquipmentClass {
    protected static final String PROP_NAME_STATEPROXY = "stateProxy";

    private final MESATObjectReferenceFieldHandler<StateProxy> refStateProxy =
            MESATObjectReferenceFieldHandler.createReferenceFieldHandler((IMESATObject) this, StateProxy.class, (String) "X_stateProxy");

    protected static final String PROP_NAME_TILECOLOR = "tileColor";

    protected static final String PROP_NAME_TILEICON = "tileIcon";

    protected static final String PROP_NAME_TILETEXTCOLOR = "tileTextColor";

    public String getATDefinitionName() {
        return "X_S88EquipmentClass";
    }

    public MESGeneratedS88EquipmentClass(long l) {
        super(l);
    }

    public MESGeneratedS88EquipmentClass(MESGeneratedS88EquipmentClass mESGeneratedS88EquipmentClass) {
        super((AbstractStatusControlledS88EquipmentWithPropertiesAndChangeHistory) mESGeneratedS88EquipmentClass);
        this.refStateProxy.initFromCopyConstructor(mESGeneratedS88EquipmentClass.refStateProxy);
    }

    public MESGeneratedS88EquipmentClass(ATRow aTRow) {
        super(aTRow);
    }

    public MESGeneratedS88EquipmentClass() {
    }

    protected void synchronizeAfterATRowRefresh() {
        this.refStateProxy.synchronizeAfterATRowRefresh();
        super.synchronizeAfterATRowRefresh();
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

    public String getDescription() {
        return (String) this.dgtATRow.getValue("X_description");
    }

    public void setDescription(String string) {
        String string2 = this.getDescription();
        this.dgtATRow.setValue("X_description", (Object) string);
        this.firePropertyChange("description", (Object) string2, (Object) string);
    }

    public Boolean getDisposable() {
        return (Boolean) this.dgtATRow.getValue("X_disposable");
    }

    public void setDisposable(Boolean bl) {
        Boolean bl2 = this.getDisposable();
        this.dgtATRow.setValue("X_disposable", (Object) bl);
        this.firePropertyChange("disposable", (Object) bl2, (Object) bl);
    }

    public IMESChoiceElement getEquipmentLevel() {
        Long l = (Long) this.dgtATRow.getValue("X_equipmentLevel");
        return MESChoiceListHelper.getChoiceElement((String) "EquipmentHierarchy", (Long) l);
    }

    public final String getEquipmentLevelAsMeaning() {
        return null == this.getEquipmentLevel() ? null : this.getEquipmentLevel().getMeaning();
    }

    public final Long getEquipmentLevelAsValue() {
        return null == this.getEquipmentLevel() ? null : this.getEquipmentLevel().getValue();
    }

    public void setEquipmentLevel(IMESChoiceElement iMESChoiceElement) {
        IMESChoiceElement iMESChoiceElement2 = this.getEquipmentLevel();
        String string = iMESChoiceElement2 != null ? iMESChoiceElement2.getMeaning() : null;
        String string2 = iMESChoiceElement != null ? iMESChoiceElement.getMeaning() : null;
        Long l = iMESChoiceElement2 != null ? iMESChoiceElement2.getValue() : null;
        Long l2 = iMESChoiceElement != null ? iMESChoiceElement.getValue() : null;
        this.dgtATRow.setValue("X_equipmentLevel", (Object) l2);
        this.firePropertyChange("equipmentLevel", (Object) iMESChoiceElement2, (Object) iMESChoiceElement);
        this.firePropertyChange("equipmentLevelAsMeaning", (Object) string, (Object) string2);
        this.firePropertyChange("equipmentLevelAsValue", (Object) l, (Object) l2);
    }

    public final void setEquipmentLevelAsMeaning(String string) {
        this.setEquipmentLevel(null == string ? null : MESChoiceListHelper.getChoiceElement((String) "EquipmentHierarchy", (String) string));
    }

    public final void setEquipmentLevelAsValue(Long l) {
        this.setEquipmentLevel(null == l ? null : MESChoiceListHelper.getChoiceElement((String) "EquipmentHierarchy", (Long) l));
    }

    public String getIdentifier() {
        return (String) this.dgtATRow.getValue("X_identifier");
    }

    public void setIdentifier(String string) {
        String string2 = this.getIdentifier();
        this.dgtATRow.setValue("X_identifier", (Object) string);
        this.firePropertyChange("identifier", (Object) string2, (Object) string);
    }

    public String getInventoryNumber() {
        return (String) this.dgtATRow.getValue("X_inventoryNumber");
    }

    public void setInventoryNumber(String string) {
        String string2 = this.getInventoryNumber();
        this.dgtATRow.setValue("X_inventoryNumber", (Object) string);
        this.firePropertyChange("inventoryNumber", (Object) string2, (Object) string);
    }

    public Boolean getLogbookEnabled() {
        return (Boolean) this.dgtATRow.getValue("X_logbookEnabled");
    }

    public void setLogbookEnabled(Boolean bl) {
        Boolean bl2 = this.getLogbookEnabled();
        this.dgtATRow.setValue("X_logbookEnabled", (Object) bl);
        this.firePropertyChange("logbookEnabled", (Object) bl2, (Object) bl);
    }

    public String getManufacturer() {
        return (String) this.dgtATRow.getValue("X_manufacturer");
    }

    public void setManufacturer(String string) {
        String string2 = this.getManufacturer();
        this.dgtATRow.setValue("X_manufacturer", (Object) string);
        this.firePropertyChange("manufacturer", (Object) string2, (Object) string);
    }

    public Time getManufacturingDate() {
        return (Time) this.dgtATRow.getValue("X_manufacturingDate");
    }

    public void setManufacturingDate(Time time) {
        Time time2 = this.getManufacturingDate();
        this.dgtATRow.setValue("X_manufacturingDate", (Object) time);
        this.firePropertyChange("manufacturingDate", (Object) time2, (Object) time);
    }

    public String getModel() {
        return (String) this.dgtATRow.getValue("X_model");
    }

    public void setModel(String string) {
        String string2 = this.getModel();
        this.dgtATRow.setValue("X_model", (Object) string);
        this.firePropertyChange("model", (Object) string2, (Object) string);
    }

    public String getSerialNumber() {
        return (String) this.dgtATRow.getValue("X_serialNumber");
    }

    public void setSerialNumber(String string) {
        String string2 = this.getSerialNumber();
        this.dgtATRow.setValue("X_serialNumber", (Object) string);
        this.firePropertyChange("serialNumber", (Object) string2, (Object) string);
    }

    public String getShortDescription() {
        return (String) this.dgtATRow.getValue("X_shortDescription");
    }

    public void setShortDescription(String string) {
        String string2 = this.getShortDescription();
        this.dgtATRow.setValue("X_shortDescription", (Object) string);
        this.firePropertyChange("shortDescription", (Object) string2, (Object) string);
    }

    protected StateProxy getStateProxy() {
        return (StateProxy) this.refStateProxy.getReference();
    }

    protected void setStateProxy(StateProxy stateProxy) {
        this.refStateProxy.setReference((StateProxy) stateProxy, this.pcs, PROP_NAME_STATEPROXY);
    }

    protected Long getTileColor() {
        return (Long) this.dgtATRow.getValue("X_tileColor");
    }

    protected void setTileColor(Long l) {
        Long l2 = this.getTileColor();
        this.dgtATRow.setValue("X_tileColor", (Object) l);
        this.firePropertyChange(PROP_NAME_TILECOLOR, (Object) l2, (Object) l);
    }

    protected String getTileIcon() {
        return (String) this.dgtATRow.getValue("X_tileIcon");
    }

    protected void setTileIcon(String string) {
        String string2 = this.getTileIcon();
        this.dgtATRow.setValue("X_tileIcon", (Object) string);
        this.firePropertyChange(PROP_NAME_TILEICON, (Object) string2, (Object) string);
    }

    protected Long getTileTextColor() {
        return (Long) this.dgtATRow.getValue("X_tileTextColor");
    }

    protected void setTileTextColor(Long l) {
        Long l2 = this.getTileTextColor();
        this.dgtATRow.setValue("X_tileTextColor", (Object) l);
        this.firePropertyChange(PROP_NAME_TILETEXTCOLOR, (Object) l2, (Object) l);
    }

    protected Response prepareATRowForSave() {
        Response response = super.prepareATRowForSave();
        this.refStateProxy.prepareATRowForSave(response);
        return response;
    }
}