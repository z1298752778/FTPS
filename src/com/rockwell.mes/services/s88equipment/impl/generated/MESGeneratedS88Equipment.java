package com.rockwell.mes.services.s88equipment.impl.generated;

import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.ReportDesign;
import com.datasweep.compatibility.client.Response;
import com.datasweep.compatibility.client.StateProxy;
import com.datasweep.compatibility.ui.Time;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.choicelist.MESChoiceListHelper;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectReferenceFieldHandler;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.services.s88equipment.ifc.AbstractStatusControlledS88EquipmentWithPropertiesAndChangeHistory;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88Equipment;
import com.rockwell.mes.services.s88equipment.ifc.generated.IMESGeneratedS88Equipment;
import java.beans.PropertyChangeSupport;

public abstract class MESGeneratedS88Equipment extends AbstractStatusControlledS88EquipmentWithPropertiesAndChangeHistory
        implements IMESGeneratedS88Equipment {
    protected static final String PROP_NAME_BARCODEPREFIX = "barcodePrefix";

    protected static final String PROP_NAME_BARCODEPREFIXPOLICY = "barcodePrefixPolicy";

    protected static final String PROP_NAME_GENIDENTIFIERLENGTH = "genIdentifierLength";

    protected static final String PROP_NAME_IDENTIFIERPREFIX = "identifierPrefix";

    private final MESATObjectReferenceFieldHandler<ReportDesign> refLabelReportDesign =
            MESATObjectReferenceFieldHandler.createReferenceFieldHandler((IMESATObject) this, ReportDesign.class, (String) "X_labelReportDesign");

    private final MESATObjectReferenceFieldHandler<IMESS88Equipment> refParentEntity =
            MESATObjectReferenceFieldHandler.createReferenceFieldHandler((IMESATObject) this, IMESS88Equipment.class, (String) "X_parentEntity");

    private final MESATObjectReferenceFieldHandler<StateProxy> refStateProxy =
            MESATObjectReferenceFieldHandler.createReferenceFieldHandler((IMESATObject) this, StateProxy.class, (String) "X_stateProxy");

    private final MESATObjectReferenceFieldHandler<IMESS88Equipment> refTemplateEntity =
            MESATObjectReferenceFieldHandler.createReferenceFieldHandler((IMESATObject) this, IMESS88Equipment.class, (String) "X_templateEntity");

    public String getATDefinitionName() {
        return "X_S88Equipment";
    }

    public MESGeneratedS88Equipment(long l) {
        super(l);
    }

    public MESGeneratedS88Equipment(MESGeneratedS88Equipment mESGeneratedS88Equipment) {
        super((AbstractStatusControlledS88EquipmentWithPropertiesAndChangeHistory) mESGeneratedS88Equipment);
        this.refLabelReportDesign.initFromCopyConstructor(mESGeneratedS88Equipment.refLabelReportDesign);
        this.refParentEntity.initFromCopyConstructor(mESGeneratedS88Equipment.refParentEntity);
        this.refStateProxy.initFromCopyConstructor(mESGeneratedS88Equipment.refStateProxy);
        this.refTemplateEntity.initFromCopyConstructor(mESGeneratedS88Equipment.refTemplateEntity);
    }

    public MESGeneratedS88Equipment(ATRow aTRow) {
        super(aTRow);
    }

    public MESGeneratedS88Equipment() {
    }

    protected void synchronizeAfterATRowRefresh() {
        this.refLabelReportDesign.synchronizeAfterATRowRefresh();
        this.refParentEntity.synchronizeAfterATRowRefresh();
        this.refStateProxy.synchronizeAfterATRowRefresh();
        this.refTemplateEntity.synchronizeAfterATRowRefresh();
        super.synchronizeAfterATRowRefresh();
    }

    public String getLDTemplateName() {
        return (String) this.dgtATRow.getValue("X_LDTemplateName");
    }

    public void setLDTemplateName(String string) {
        String string2 = this.getLDTemplateName();
        this.dgtATRow.setValue("X_LDTemplateName", (Object) string);
        this.firePropertyChange("LDTemplateName", (Object) string2, (Object) string);
    }

    public String getBarcode() {
        return (String) this.dgtATRow.getValue("X_barcode");
    }

    public void setBarcode(String string) {
        String string2 = this.getBarcode();
        this.dgtATRow.setValue("X_barcode", (Object) string);
        this.firePropertyChange("barcode", (Object) string2, (Object) string);
    }

    protected String getBarcodePrefix() {
        return (String) this.dgtATRow.getValue("X_barcodePrefix");
    }

    protected void setBarcodePrefix(String string) {
        String string2 = this.getBarcodePrefix();
        this.dgtATRow.setValue("X_barcodePrefix", (Object) string);
        this.firePropertyChange(PROP_NAME_BARCODEPREFIX, (Object) string2, (Object) string);
    }

    protected IMESChoiceElement getBarcodePrefixPolicy() {
        Long l = (Long) this.dgtATRow.getValue("X_barcodePrefixPolicy");
        return MESChoiceListHelper.getChoiceElement((String) "EqmTemplateEntityBarcodePrefixPolicy", (Long) l);
    }

    protected String getBarcodePrefixPolicyAsMeaning() {
        return null == this.getBarcodePrefixPolicy() ? null : this.getBarcodePrefixPolicy().getMeaning();
    }

    protected Long getBarcodePrefixPolicyAsValue() {
        return null == this.getBarcodePrefixPolicy() ? null : this.getBarcodePrefixPolicy().getValue();
    }

    protected void setBarcodePrefixPolicy(IMESChoiceElement iMESChoiceElement) {
        IMESChoiceElement iMESChoiceElement2 = this.getBarcodePrefixPolicy();
        String string = iMESChoiceElement2 != null ? iMESChoiceElement2.getMeaning() : null;
        String string2 = iMESChoiceElement != null ? iMESChoiceElement.getMeaning() : null;
        Long l = iMESChoiceElement2 != null ? iMESChoiceElement2.getValue() : null;
        Long l2 = iMESChoiceElement != null ? iMESChoiceElement.getValue() : null;
        this.dgtATRow.setValue("X_barcodePrefixPolicy", (Object) l2);
        this.firePropertyChange(PROP_NAME_BARCODEPREFIXPOLICY, (Object) iMESChoiceElement2, (Object) iMESChoiceElement);
        this.firePropertyChange("barcodePrefixPolicyAsMeaning", (Object) string, (Object) string2);
        this.firePropertyChange("barcodePrefixPolicyAsValue", (Object) l, (Object) l2);
    }

    protected void setBarcodePrefixPolicyAsMeaning(String string) {
        this.setBarcodePrefixPolicy(
                null == string ? null : MESChoiceListHelper.getChoiceElement((String) "EqmTemplateEntityBarcodePrefixPolicy", (String) string));
    }

    protected void setBarcodePrefixPolicyAsValue(Long l) {
        this.setBarcodePrefixPolicy(
                null == l ? null : MESChoiceListHelper.getChoiceElement((String) "EqmTemplateEntityBarcodePrefixPolicy", (Long) l));
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

    public String getLCEquipAccPriv() {
        final Long var1 = (Long) this.dgtATRow.getValue("LC_equip_accPriv");
        if (var1 != null) {
            final AccessPrivilege access = PCContext.getFunctions().getAccessPrivilegeByKey((long) var1);
            return access.getName();
        }
        return null;
    }

    public void setLCEquipAccPriv(String var1) {
        final String var2 = this.getLCEquipAccPriv();
        final AccessPrivilege access = PCContext.getFunctions().getAccessPrivilegeByName(var1);
        this.dgtATRow.setValue("LC_equip_accPriv", (Object) access.getAccessPrivilegeKey());
        this.firePropertyChange("equipAccPriv", (Object) var2, (Object) var1);
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

    public IMESChoiceElement getExpiryChgErrBehavior() {
        Long l = (Long) this.dgtATRow.getValue("X_expiryChgErrBehavior");
        return MESChoiceListHelper.getChoiceElement((String) "EqmExpiryChangeErrorBehavior", (Long) l);
    }

    public final String getExpiryChgErrBehaviorAsMeaning() {
        return null == this.getExpiryChgErrBehavior() ? null : this.getExpiryChgErrBehavior().getMeaning();
    }

    public final Long getExpiryChgErrBehaviorAsValue() {
        return null == this.getExpiryChgErrBehavior() ? null : this.getExpiryChgErrBehavior().getValue();
    }

    public void setExpiryChgErrBehavior(IMESChoiceElement iMESChoiceElement) {
        IMESChoiceElement iMESChoiceElement2 = this.getExpiryChgErrBehavior();
        String string = iMESChoiceElement2 != null ? iMESChoiceElement2.getMeaning() : null;
        String string2 = iMESChoiceElement != null ? iMESChoiceElement.getMeaning() : null;
        Long l = iMESChoiceElement2 != null ? iMESChoiceElement2.getValue() : null;
        Long l2 = iMESChoiceElement != null ? iMESChoiceElement.getValue() : null;
        this.dgtATRow.setValue("X_expiryChgErrBehavior", (Object) l2);
        this.firePropertyChange("expiryChgErrBehavior", (Object) iMESChoiceElement2, (Object) iMESChoiceElement);
        this.firePropertyChange("expiryChgErrBehaviorAsMeaning", (Object) string, (Object) string2);
        this.firePropertyChange("expiryChgErrBehaviorAsValue", (Object) l, (Object) l2);
    }

    public final void setExpiryChgErrBehaviorAsMeaning(String string) {
        this.setExpiryChgErrBehavior(
                null == string ? null : MESChoiceListHelper.getChoiceElement((String) "EqmExpiryChangeErrorBehavior", (String) string));
    }

    public final void setExpiryChgErrBehaviorAsValue(Long l) {
        this.setExpiryChgErrBehavior(null == l ? null : MESChoiceListHelper.getChoiceElement((String) "EqmExpiryChangeErrorBehavior", (Long) l));
    }

    protected Long getGenIdentifierLength() {
        return (Long) this.dgtATRow.getValue("X_genIdentifierLength");
    }

    protected void setGenIdentifierLength(Long l) {
        Long l2 = this.getGenIdentifierLength();
        this.dgtATRow.setValue("X_genIdentifierLength", (Object) l);
        this.firePropertyChange(PROP_NAME_GENIDENTIFIERLENGTH, (Object) l2, (Object) l);
    }

    public String getHistorianProvider() {
        return (String) this.dgtATRow.getValue("X_HistorianProvider");
    }

    public void setHistorianProvider(String string) {
        String string2 = this.getHistorianProvider();
        this.dgtATRow.setValue("X_HistorianProvider", (Object) string);
        this.firePropertyChange("historianProvider", (Object) string2, (Object) string);
    }

    public String getIdentifier() {
        return (String) this.dgtATRow.getValue("X_identifier");
    }

    public void setIdentifier(String string) {
        String string2 = this.getIdentifier();
        this.dgtATRow.setValue("X_identifier", (Object) string);
        this.firePropertyChange("identifier", (Object) string2, (Object) string);
    }

    protected String getIdentifierPrefix() {
        return (String) this.dgtATRow.getValue("X_identifierPrefix");
    }

    protected void setIdentifierPrefix(String string) {
        String string2 = this.getIdentifierPrefix();
        this.dgtATRow.setValue("X_identifierPrefix", (Object) string);
        this.firePropertyChange(PROP_NAME_IDENTIFIERPREFIX, (Object) string2, (Object) string);
    }

    public String getInventoryNumber() {
        return (String) this.dgtATRow.getValue("X_inventoryNumber");
    }

    public void setInventoryNumber(String string) {
        String string2 = this.getInventoryNumber();
        this.dgtATRow.setValue("X_inventoryNumber", (Object) string);
        this.firePropertyChange("inventoryNumber", (Object) string2, (Object) string);
    }

    public Boolean getIsMigrated() {
        return (Boolean) this.dgtATRow.getValue("X_isMigrated");
    }

    public void setIsMigrated(Boolean bl) {
        Boolean bl2 = this.getIsMigrated();
        this.dgtATRow.setValue("X_isMigrated", (Object) bl);
        this.firePropertyChange("isMigrated", (Object) bl2, (Object) bl);
    }

    public Boolean getIsTemplate() {
        return (Boolean) this.dgtATRow.getValue("X_isTemplate");
    }

    public void setIsTemplate(Boolean bl) {
        Boolean bl2 = this.getIsTemplate();
        this.dgtATRow.setValue("X_isTemplate", (Object) bl);
        this.firePropertyChange("isTemplate", (Object) bl2, (Object) bl);
    }

    public ReportDesign getLabelReportDesign() {
        return (ReportDesign) this.refLabelReportDesign.getReference();
    }

    public void setLabelReportDesign(ReportDesign reportDesign) {
        this.refLabelReportDesign.setReference((ReportDesign) reportDesign, this.pcs, "labelReportDesign");
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

    public IMESChoiceElement getMode() {
        Long l = (Long) this.dgtATRow.getValue("X_mode");
        return MESChoiceListHelper.getChoiceElement((String) "EquipmentMode", (Long) l);
    }

    public final String getModeAsMeaning() {
        return null == this.getMode() ? null : this.getMode().getMeaning();
    }

    public final Long getModeAsValue() {
        return null == this.getMode() ? null : this.getMode().getValue();
    }

    public void setMode(IMESChoiceElement iMESChoiceElement) {
        IMESChoiceElement iMESChoiceElement2 = this.getMode();
        String string = iMESChoiceElement2 != null ? iMESChoiceElement2.getMeaning() : null;
        String string2 = iMESChoiceElement != null ? iMESChoiceElement.getMeaning() : null;
        Long l = iMESChoiceElement2 != null ? iMESChoiceElement2.getValue() : null;
        Long l2 = iMESChoiceElement != null ? iMESChoiceElement.getValue() : null;
        this.dgtATRow.setValue("X_mode", (Object) l2);
        this.firePropertyChange("mode", (Object) iMESChoiceElement2, (Object) iMESChoiceElement);
        this.firePropertyChange("modeAsMeaning", (Object) string, (Object) string2);
        this.firePropertyChange("modeAsValue", (Object) l, (Object) l2);
    }

    public final void setModeAsMeaning(String string) {
        this.setMode(null == string ? null : MESChoiceListHelper.getChoiceElement((String) "EquipmentMode", (String) string));
    }

    public final void setModeAsValue(Long l) {
        this.setMode(null == l ? null : MESChoiceListHelper.getChoiceElement((String) "EquipmentMode", (Long) l));
    }

    public String getModel() {
        return (String) this.dgtATRow.getValue("X_model");
    }

    public void setModel(String string) {
        String string2 = this.getModel();
        this.dgtATRow.setValue("X_model", (Object) string);
        this.firePropertyChange("model", (Object) string2, (Object) string);
    }

    public String getOverrideAIServerName() {
        return (String) this.dgtATRow.getValue("X_OverrideAIServerName");
    }

    public void setOverrideAIServerName(String string) {
        String string2 = this.getOverrideAIServerName();
        this.dgtATRow.setValue("X_OverrideAIServerName", (Object) string);
        this.firePropertyChange("overrideAIServerName", (Object) string2, (Object) string);
    }

    public String getOverrideHistorianAISrv() {
        return (String) this.dgtATRow.getValue("X_OverrideHistorianAISrv");
    }

    public void setOverrideHistorianAISrv(String string) {
        String string2 = this.getOverrideHistorianAISrv();
        this.dgtATRow.setValue("X_OverrideHistorianAISrv", (Object) string);
        this.firePropertyChange("overrideHistorianAISrv", (Object) string2, (Object) string);
    }

    public String getOverrideHistorianASrv() {
        return (String) this.dgtATRow.getValue("X_OverrideHistorianASrv");
    }

    public void setOverrideHistorianASrv(String string) {
        String string2 = this.getOverrideHistorianASrv();
        this.dgtATRow.setValue("X_OverrideHistorianASrv", (Object) string);
        this.firePropertyChange("overrideHistorianASrv", (Object) string2, (Object) string);
    }

    public String getOverrideHistorianSrv() {
        return (String) this.dgtATRow.getValue("X_OverrideHistorianSrv");
    }

    public void setOverrideHistorianSrv(String string) {
        String string2 = this.getOverrideHistorianSrv();
        this.dgtATRow.setValue("X_OverrideHistorianSrv", (Object) string);
        this.firePropertyChange("overrideHistorianSrv", (Object) string2, (Object) string);
    }

    public String getOverrideLDAreaPath() {
        return (String) this.dgtATRow.getValue("X_OverrideLDAreaPath");
    }

    public void setOverrideLDAreaPath(String string) {
        String string2 = this.getOverrideLDAreaPath();
        this.dgtATRow.setValue("X_OverrideLDAreaPath", (Object) string);
        this.firePropertyChange("overrideLDAreaPath", (Object) string2, (Object) string);
    }

    public IMESS88Equipment getParentEntity() {
        return (IMESS88Equipment) this.refParentEntity.getReference();
    }

    public void setParentEntity(IMESS88Equipment iMESS88Equipment) {
        this.refParentEntity.setReference((IMESS88Equipment) iMESS88Equipment, this.pcs, "parentEntity");
    }

    public String getSerialNumber() {
        return (String) this.dgtATRow.getValue("X_serialNumber");
    }

    public void setSerialNumber(String string) {
        String string2 = this.getSerialNumber();
        this.dgtATRow.setValue("X_serialNumber", (Object) string);
        this.firePropertyChange("serialNumber", (Object) string2, (Object) string);
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

    public String getShortDescription() {
        return (String) this.dgtATRow.getValue("X_shortDescription");
    }

    public void setShortDescription(String string) {
        String string2 = this.getShortDescription();
        this.dgtATRow.setValue("X_shortDescription", (Object) string);
        this.firePropertyChange("shortDescription", (Object) string2, (Object) string);
    }

    public StateProxy getStateProxy() {
        return (StateProxy) this.refStateProxy.getReference();
    }

    public void setStateProxy(StateProxy stateProxy) {
        this.refStateProxy.setReference((StateProxy) stateProxy, this.pcs, "stateProxy");
    }

    public IMESS88Equipment getTemplateEntity() {
        return (IMESS88Equipment) this.refTemplateEntity.getReference();
    }

    public void setTemplateEntity(IMESS88Equipment iMESS88Equipment) {
        this.refTemplateEntity.setReference((IMESS88Equipment) iMESS88Equipment, this.pcs, "templateEntity");
    }

    protected Response prepareATRowForSave() {
        Response response = super.prepareATRowForSave();
        this.refLabelReportDesign.prepareATRowForSave(response);
        this.refParentEntity.prepareATRowForSave(response);
        this.refStateProxy.prepareATRowForSave(response);
        this.refTemplateEntity.prepareATRowForSave(response);
        return response;
    }
}