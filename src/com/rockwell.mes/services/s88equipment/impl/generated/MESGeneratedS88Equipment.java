//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.rockwell.mes.services.s88equipment.impl.generated;

import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.ReportDesign;
import com.datasweep.compatibility.client.Response;
import com.datasweep.compatibility.client.StateProxy;
import com.datasweep.compatibility.ui.Time;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceList;
import com.rockwell.mes.commons.base.ifc.choicelist.MESChoiceListHelper;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectReferenceFieldHandler;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.services.s88equipment.ifc.AbstractStatusControlledS88EquipmentWithPropertiesAndChangeHistory;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88Equipment;
import com.rockwell.mes.services.s88equipment.ifc.generated.IMESGeneratedS88Equipment;

public abstract class MESGeneratedS88Equipment extends AbstractStatusControlledS88EquipmentWithPropertiesAndChangeHistory implements IMESGeneratedS88Equipment {
    protected static final String PROP_NAME_BARCODEPREFIX = "barcodePrefix";
    protected static final String PROP_NAME_BARCODEPREFIXPOLICY = "barcodePrefixPolicy";
    protected static final String PROP_NAME_GENIDENTIFIERLENGTH = "genIdentifierLength";
    protected static final String PROP_NAME_IDENTIFIERPREFIX = "identifierPrefix";
    private final MESATObjectReferenceFieldHandler<ReportDesign> refLabelReportDesign = MESATObjectReferenceFieldHandler.createReferenceFieldHandler(this, ReportDesign.class, "X_labelReportDesign");
    private final MESATObjectReferenceFieldHandler<IMESS88Equipment> refParentEntity = MESATObjectReferenceFieldHandler.createReferenceFieldHandler(this, IMESS88Equipment.class, "X_parentEntity");
    private final MESATObjectReferenceFieldHandler<StateProxy> refStateProxy = MESATObjectReferenceFieldHandler.createReferenceFieldHandler(this, StateProxy.class, "X_stateProxy");
    private final MESATObjectReferenceFieldHandler<IMESS88Equipment> refTemplateEntity = MESATObjectReferenceFieldHandler.createReferenceFieldHandler(this, IMESS88Equipment.class, "X_templateEntity");

    public String getATDefinitionName() {
        return "X_S88Equipment";
    }

    public MESGeneratedS88Equipment(long var1) {
        super(var1);
    }

    public MESGeneratedS88Equipment(MESGeneratedS88Equipment var1) {
        super(var1);
        this.refLabelReportDesign.initFromCopyConstructor(var1.refLabelReportDesign);
        this.refParentEntity.initFromCopyConstructor(var1.refParentEntity);
        this.refStateProxy.initFromCopyConstructor(var1.refStateProxy);
        this.refTemplateEntity.initFromCopyConstructor(var1.refTemplateEntity);
    }

    public MESGeneratedS88Equipment(ATRow var1) {
        super(var1);
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
        return (String)this.dgtATRow.getValue("X_LDTemplateName");
    }

    public void setLDTemplateName(String var1) {
        String var2 = this.getLDTemplateName();
        this.dgtATRow.setValue("X_LDTemplateName", var1);
        this.firePropertyChange("LDTemplateName", var2, var1);
    }
    
    public String getBarcode() {
        return (String)this.dgtATRow.getValue("X_barcode");
    }

    public void setBarcode(String var1) {
        String var2 = this.getBarcode();
        this.dgtATRow.setValue("X_barcode", var1);
        this.firePropertyChange("barcode", var2, var1);
    }

    protected String getBarcodePrefix() {
        return (String)this.dgtATRow.getValue("X_barcodePrefix");
    }

    protected void setBarcodePrefix(String var1) {
        String var2 = this.getBarcodePrefix();
        this.dgtATRow.setValue("X_barcodePrefix", var1);
        this.firePropertyChange("barcodePrefix", var2, var1);
    }

    protected IMESChoiceElement getBarcodePrefixPolicy() {
        Long var1 = (Long)this.dgtATRow.getValue("X_barcodePrefixPolicy");
        return MESChoiceListHelper.getChoiceElement("EqmTemplateEntityBarcodePrefixPolicy", var1);
    }

    protected String getBarcodePrefixPolicyAsMeaning() {
        return null == this.getBarcodePrefixPolicy() ? null : this.getBarcodePrefixPolicy().getMeaning();
    }

    protected Long getBarcodePrefixPolicyAsValue() {
        return null == this.getBarcodePrefixPolicy() ? null : this.getBarcodePrefixPolicy().getValue();
    }

    protected void setBarcodePrefixPolicy(IMESChoiceElement var1) {
        IMESChoiceElement var2 = this.getBarcodePrefixPolicy();
        String var3 = var2 != null ? var2.getMeaning() : null;
        String var4 = var1 != null ? var1.getMeaning() : null;
        Long var5 = var2 != null ? var2.getValue() : null;
        Long var6 = var1 != null ? var1.getValue() : null;
        this.dgtATRow.setValue("X_barcodePrefixPolicy", var6);
        this.firePropertyChange("barcodePrefixPolicy", var2, var1);
        this.firePropertyChange("barcodePrefixPolicyAsMeaning", var3, var4);
        this.firePropertyChange("barcodePrefixPolicyAsValue", var5, var6);
    }

    protected void setBarcodePrefixPolicyAsMeaning(String var1) {
        this.setBarcodePrefixPolicy(null == var1 ? null : MESChoiceListHelper.getChoiceElement("EqmTemplateEntityBarcodePrefixPolicy", var1));
    }

    protected void setBarcodePrefixPolicyAsValue(Long var1) {
        this.setBarcodePrefixPolicy(null == var1 ? null : MESChoiceListHelper.getChoiceElement("EqmTemplateEntityBarcodePrefixPolicy", var1));
    }

    public String getDescription() {
        return (String)this.dgtATRow.getValue("X_description");
    }

    public void setDescription(String var1) {
        String var2 = this.getDescription();
        this.dgtATRow.setValue("X_description", var1);
        this.firePropertyChange("description", var2, var1);
    }

    public Boolean getDisposable() {
        return (Boolean)this.dgtATRow.getValue("X_disposable");
    }

    public void setDisposable(Boolean var1) {
        Boolean var2 = this.getDisposable();
        this.dgtATRow.setValue("X_disposable", var1);
        this.firePropertyChange("disposable", var2, var1);
    }
    
    public String getLCEquipAccPriv(){
    	Long var1 = (Long)this.dgtATRow.getValue("LC_equip_accPriv");
    	if(var1!=null){
    		AccessPrivilege access = PCContext.getFunctions().getAccessPrivilegeByKey(var1);
    		return (String)access.getName();
    	}
    	return null;
    }
    
    public void setLCEquipAccPriv(String var1){
    	String var2 = this.getLCEquipAccPriv();
    	AccessPrivilege access = PCContext.getFunctions().getAccessPrivilegeByName(var1);
    	this.dgtATRow.setValue("LC_equip_accPriv", access.getAccessPrivilegeKey());
    	this.firePropertyChange("equipAccPriv", var2, var1);
    }
    
    public IMESChoiceElement getEquipmentLevel() {
        Long var1 = (Long)this.dgtATRow.getValue("X_equipmentLevel");
        return MESChoiceListHelper.getChoiceElement("EquipmentHierarchy", var1);
    }

    public final String getEquipmentLevelAsMeaning() {
        return null == this.getEquipmentLevel() ? null : this.getEquipmentLevel().getMeaning();
    }

    public final Long getEquipmentLevelAsValue() {
        return null == this.getEquipmentLevel() ? null : this.getEquipmentLevel().getValue();
    }

    public void setEquipmentLevel(IMESChoiceElement var1) {
        IMESChoiceElement var2 = this.getEquipmentLevel();
        String var3 = var2 != null ? var2.getMeaning() : null;
        String var4 = var1 != null ? var1.getMeaning() : null;
        Long var5 = var2 != null ? var2.getValue() : null;
        Long var6 = var1 != null ? var1.getValue() : null;
        this.dgtATRow.setValue("X_equipmentLevel", var6);
        this.firePropertyChange("equipmentLevel", var2, var1);
        this.firePropertyChange("equipmentLevelAsMeaning", var3, var4);
        this.firePropertyChange("equipmentLevelAsValue", var5, var6);
    }

    public final void setEquipmentLevelAsMeaning(String var1) {
        this.setEquipmentLevel(null == var1 ? null : MESChoiceListHelper.getChoiceElement("EquipmentHierarchy", var1));
    }

    public final void setEquipmentLevelAsValue(Long var1) {
        this.setEquipmentLevel(null == var1 ? null : MESChoiceListHelper.getChoiceElement("EquipmentHierarchy", var1));
    }

    public IMESChoiceElement getExpiryChgErrBehavior() {
        Long var1 = (Long)this.dgtATRow.getValue("X_expiryChgErrBehavior");
        return MESChoiceListHelper.getChoiceElement("EqmExpiryChangeErrorBehavior", var1);
    }

    public final String getExpiryChgErrBehaviorAsMeaning() {
        return null == this.getExpiryChgErrBehavior() ? null : this.getExpiryChgErrBehavior().getMeaning();
    }

    public final Long getExpiryChgErrBehaviorAsValue() {
        return null == this.getExpiryChgErrBehavior() ? null : this.getExpiryChgErrBehavior().getValue();
    }

    public void setExpiryChgErrBehavior(IMESChoiceElement var1) {
        IMESChoiceElement var2 = this.getExpiryChgErrBehavior();
        String var3 = var2 != null ? var2.getMeaning() : null;
        String var4 = var1 != null ? var1.getMeaning() : null;
        Long var5 = var2 != null ? var2.getValue() : null;
        Long var6 = var1 != null ? var1.getValue() : null;
        this.dgtATRow.setValue("X_expiryChgErrBehavior", var6);
        this.firePropertyChange("expiryChgErrBehavior", var2, var1);
        this.firePropertyChange("expiryChgErrBehaviorAsMeaning", var3, var4);
        this.firePropertyChange("expiryChgErrBehaviorAsValue", var5, var6);
    }

    public final void setExpiryChgErrBehaviorAsMeaning(String var1) {
        this.setExpiryChgErrBehavior(null == var1 ? null : MESChoiceListHelper.getChoiceElement("EqmExpiryChangeErrorBehavior", var1));
    }

    public final void setExpiryChgErrBehaviorAsValue(Long var1) {
        this.setExpiryChgErrBehavior(null == var1 ? null : MESChoiceListHelper.getChoiceElement("EqmExpiryChangeErrorBehavior", var1));
    }

    protected Long getGenIdentifierLength() {
        return (Long)this.dgtATRow.getValue("X_genIdentifierLength");
    }

    protected void setGenIdentifierLength(Long var1) {
        Long var2 = this.getGenIdentifierLength();
        this.dgtATRow.setValue("X_genIdentifierLength", var1);
        this.firePropertyChange("genIdentifierLength", var2, var1);
    }

    public String getHistorianProvider() {
        return (String)this.dgtATRow.getValue("X_HistorianProvider");
    }

    public void setHistorianProvider(String var1) {
        String var2 = this.getHistorianProvider();
        this.dgtATRow.setValue("X_HistorianProvider", var1);
        this.firePropertyChange("historianProvider", var2, var1);
    }

    public String getIdentifier() {
        return (String)this.dgtATRow.getValue("X_identifier");
    }

    public void setIdentifier(String var1) {
        String var2 = this.getIdentifier();
        this.dgtATRow.setValue("X_identifier", var1);
        this.firePropertyChange("identifier", var2, var1);
    }

    protected String getIdentifierPrefix() {
        return (String)this.dgtATRow.getValue("X_identifierPrefix");
    }

    protected void setIdentifierPrefix(String var1) {
        String var2 = this.getIdentifierPrefix();
        this.dgtATRow.setValue("X_identifierPrefix", var1);
        this.firePropertyChange("identifierPrefix", var2, var1);
    }

    public String getInventoryNumber() {
        return (String)this.dgtATRow.getValue("X_inventoryNumber");
    }

    public void setInventoryNumber(String var1) {
        String var2 = this.getInventoryNumber();
        this.dgtATRow.setValue("X_inventoryNumber", var1);
        this.firePropertyChange("inventoryNumber", var2, var1);
    }

    public Boolean getIsMigrated() {
        return (Boolean)this.dgtATRow.getValue("X_isMigrated");
    }

    public void setIsMigrated(Boolean var1) {
        Boolean var2 = this.getIsMigrated();
        this.dgtATRow.setValue("X_isMigrated", var1);
        this.firePropertyChange("isMigrated", var2, var1);
    }

    public Boolean getIsTemplate() {
        return (Boolean)this.dgtATRow.getValue("X_isTemplate");
    }

    public void setIsTemplate(Boolean var1) {
        Boolean var2 = this.getIsTemplate();
        this.dgtATRow.setValue("X_isTemplate", var1);
        this.firePropertyChange("isTemplate", var2, var1);
    }

    public ReportDesign getLabelReportDesign() {
        return (ReportDesign)this.refLabelReportDesign.getReference();
    }

    public void setLabelReportDesign(ReportDesign var1) {
        this.refLabelReportDesign.setReference(var1, this.pcs, "labelReportDesign");
    }

    public Boolean getLogbookEnabled() {
        return (Boolean)this.dgtATRow.getValue("X_logbookEnabled");
    }

    public void setLogbookEnabled(Boolean var1) {
        Boolean var2 = this.getLogbookEnabled();
        this.dgtATRow.setValue("X_logbookEnabled", var1);
        this.firePropertyChange("logbookEnabled", var2, var1);
    }

    public String getManufacturer() {
        return (String)this.dgtATRow.getValue("X_manufacturer");
    }

    public void setManufacturer(String var1) {
        String var2 = this.getManufacturer();
        this.dgtATRow.setValue("X_manufacturer", var1);
        this.firePropertyChange("manufacturer", var2, var1);
    }

    public Time getManufacturingDate() {
        return (Time)this.dgtATRow.getValue("X_manufacturingDate");
    }

    public void setManufacturingDate(Time var1) {
        Time var2 = this.getManufacturingDate();
        this.dgtATRow.setValue("X_manufacturingDate", var1);
        this.firePropertyChange("manufacturingDate", var2, var1);
    }

    public IMESChoiceElement getMode() {
        Long var1 = (Long)this.dgtATRow.getValue("X_mode");
        return MESChoiceListHelper.getChoiceElement("EquipmentMode", var1);
    }

    public final String getModeAsMeaning() {
        return null == this.getMode() ? null : this.getMode().getMeaning();
    }

    public final Long getModeAsValue() {
        return null == this.getMode() ? null : this.getMode().getValue();
    }

    public void setMode(IMESChoiceElement var1) {
        IMESChoiceElement var2 = this.getMode();
        String var3 = var2 != null ? var2.getMeaning() : null;
        String var4 = var1 != null ? var1.getMeaning() : null;
        Long var5 = var2 != null ? var2.getValue() : null;
        Long var6 = var1 != null ? var1.getValue() : null;
        this.dgtATRow.setValue("X_mode", var6);
        this.firePropertyChange("mode", var2, var1);
        this.firePropertyChange("modeAsMeaning", var3, var4);
        this.firePropertyChange("modeAsValue", var5, var6);
    }

    public final void setModeAsMeaning(String var1) {
        this.setMode(null == var1 ? null : MESChoiceListHelper.getChoiceElement("EquipmentMode", var1));
    }

    public final void setModeAsValue(Long var1) {
        this.setMode(null == var1 ? null : MESChoiceListHelper.getChoiceElement("EquipmentMode", var1));
    }

    public String getModel() {
        return (String)this.dgtATRow.getValue("X_model");
    }

    public void setModel(String var1) {
        String var2 = this.getModel();
        this.dgtATRow.setValue("X_model", var1);
        this.firePropertyChange("model", var2, var1);
    }

    public String getOverrideAIServerName() {
        return (String)this.dgtATRow.getValue("X_OverrideAIServerName");
    }

    public void setOverrideAIServerName(String var1) {
        String var2 = this.getOverrideAIServerName();
        this.dgtATRow.setValue("X_OverrideAIServerName", var1);
        this.firePropertyChange("overrideAIServerName", var2, var1);
    }

    public String getOverrideHistorianAISrv() {
        return (String)this.dgtATRow.getValue("X_OverrideHistorianAISrv");
    }

    public void setOverrideHistorianAISrv(String var1) {
        String var2 = this.getOverrideHistorianAISrv();
        this.dgtATRow.setValue("X_OverrideHistorianAISrv", var1);
        this.firePropertyChange("overrideHistorianAISrv", var2, var1);
    }

    public String getOverrideHistorianASrv() {
        return (String)this.dgtATRow.getValue("X_OverrideHistorianASrv");
    }

    public void setOverrideHistorianASrv(String var1) {
        String var2 = this.getOverrideHistorianASrv();
        this.dgtATRow.setValue("X_OverrideHistorianASrv", var1);
        this.firePropertyChange("overrideHistorianASrv", var2, var1);
    }

    public String getOverrideHistorianSrv() {
        return (String)this.dgtATRow.getValue("X_OverrideHistorianSrv");
    }

    public void setOverrideHistorianSrv(String var1) {
        String var2 = this.getOverrideHistorianSrv();
        this.dgtATRow.setValue("X_OverrideHistorianSrv", var1);
        this.firePropertyChange("overrideHistorianSrv", var2, var1);
    }

    public String getOverrideLDAreaPath() {
        return (String)this.dgtATRow.getValue("X_OverrideLDAreaPath");
    }

    public void setOverrideLDAreaPath(String var1) {
        String var2 = this.getOverrideLDAreaPath();
        this.dgtATRow.setValue("X_OverrideLDAreaPath", var1);
        this.firePropertyChange("overrideLDAreaPath", var2, var1);
    }

    public IMESS88Equipment getParentEntity() {
        return (IMESS88Equipment)this.refParentEntity.getReference();
    }

    public void setParentEntity(IMESS88Equipment var1) {
        this.refParentEntity.setReference(var1, this.pcs, "parentEntity");
    }

    public String getSerialNumber() {
        return (String)this.dgtATRow.getValue("X_serialNumber");
    }

    public void setSerialNumber(String var1) {
        String var2 = this.getSerialNumber();
        this.dgtATRow.setValue("X_serialNumber", var1);
        this.firePropertyChange("serialNumber", var2, var1);
    }

    public String getShortDescription() {
        return (String)this.dgtATRow.getValue("X_shortDescription");
    }

    public void setShortDescription(String var1) {
        String var2 = this.getShortDescription();
        this.dgtATRow.setValue("X_shortDescription", var1);
        this.firePropertyChange("shortDescription", var2, var1);
    }

    public StateProxy getStateProxy() {
        return (StateProxy)this.refStateProxy.getReference();
    }

    public void setStateProxy(StateProxy var1) {
        this.refStateProxy.setReference(var1, this.pcs, "stateProxy");
    }

    public IMESS88Equipment getTemplateEntity() {
        return (IMESS88Equipment)this.refTemplateEntity.getReference();
    }

    public void setTemplateEntity(IMESS88Equipment var1) {
        this.refTemplateEntity.setReference(var1, this.pcs, "templateEntity");
    }

    protected Response prepareATRowForSave() {
        Response var1 = super.prepareATRowForSave();
        this.refLabelReportDesign.prepareATRowForSave(var1);
        this.refParentEntity.prepareATRowForSave(var1);
        this.refStateProxy.prepareATRowForSave(var1);
        this.refTemplateEntity.prepareATRowForSave(var1);
        return var1;
    }
}
