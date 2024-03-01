package com.rockwell.mes.services.s88equipment.ifc.generated;

import com.datasweep.compatibility.client.ReportDesign;
import com.datasweep.compatibility.client.StateProxy;
import com.datasweep.compatibility.ui.Time;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObject;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88Equipment;

public interface IMESGeneratedS88Equipment extends IMESATObject {
    public static final String ATDEFINITION_NAME = "X_S88Equipment";

    public static final String SQL_TABLE_NAME = "AT_X_S88Equipment";

    public static final String PROP_NAME_LDTEMPLATENAME = "LDTemplateName";

    public static final String SQL_COL_NAME_LDTEMPLATENAME = "X_LDTemplateName_S";

    public static final String COL_NAME_LDTEMPLATENAME = "X_LDTemplateName";

    //
    public static final String PROP_NAME_LCEQUIPACCPRIV = "equipAccPriv";

    public static final String SQL_COL_NAME_ACCESS = "LC_equip_accPriv_98";

    public static final String COL_NAME_LCEQUIPACCPRIV = "LC_equip_accPriv";

    public static final String PROP_NAME_PROCESSPACKNAME = "processPackName";

    public static final String SQL_COL_NAME_PROCESSPACK = "processPackName_S";

    public static final String COL_NAME_PROCESSPACKNAME = "processPackName";


    public static final String PROP_NAME_BARCODE = "barcode";

    public static final String SQL_COL_NAME_BARCODE = "X_barcode_S";

    public static final String COL_NAME_BARCODE = "X_barcode";

    public static final String COL_NAME_BARCODEPREFIX = "X_barcodePrefix";

    public static final String COL_NAME_BARCODEPREFIXPOLICY = "X_barcodePrefixPolicy";

    public static final String PROP_NAME_DESCRIPTION = "description";

    public static final String SQL_COL_NAME_DESCRIPTION = "X_description_S";

    public static final String COL_NAME_DESCRIPTION = "X_description";

    public static final String PROP_NAME_DISPOSABLE = "disposable";

    public static final String SQL_COL_NAME_DISPOSABLE = "X_disposable_Y";

    public static final String COL_NAME_DISPOSABLE = "X_disposable";

    public static final String PROP_NAME_EQUIPMENTLEVEL = "equipmentLevel";

    public static final String SQL_COL_NAME_EQUIPMENTLEVEL = "X_equipmentLevel_I";

    public static final String COL_NAME_EQUIPMENTLEVEL = "X_equipmentLevel";

    public static final String PROP_NAME_EXPIRYCHGERRBEHAVIOR = "expiryChgErrBehavior";

    public static final String SQL_COL_NAME_EXPIRYCHGERRBEHAVIOR = "X_expiryChgErrBehavior_I";

    public static final String COL_NAME_EXPIRYCHGERRBEHAVIOR = "X_expiryChgErrBehavior";

    public static final String COL_NAME_GENIDENTIFIERLENGTH = "X_genIdentifierLength";

    public static final String PROP_NAME_HISTORIANPROVIDER = "historianProvider";

    public static final String SQL_COL_NAME_HISTORIANPROVIDER = "X_HistorianProvider_S";

    public static final String COL_NAME_HISTORIANPROVIDER = "X_HistorianProvider";

    public static final String PROP_NAME_IDENTIFIER = "identifier";

    public static final String SQL_COL_NAME_IDENTIFIER = "X_identifier_S";

    public static final String COL_NAME_IDENTIFIER = "X_identifier";

    public static final String COL_NAME_IDENTIFIERPREFIX = "X_identifierPrefix";

    public static final String PROP_NAME_INVENTORYNUMBER = "inventoryNumber";

    public static final String SQL_COL_NAME_INVENTORYNUMBER = "X_inventoryNumber_S";

    public static final String COL_NAME_INVENTORYNUMBER = "X_inventoryNumber";

    public static final String PROP_NAME_ISMIGRATED = "isMigrated";

    public static final String SQL_COL_NAME_ISMIGRATED = "X_isMigrated_Y";

    public static final String COL_NAME_ISMIGRATED = "X_isMigrated";

    public static final String PROP_NAME_ISTEMPLATE = "isTemplate";

    public static final String SQL_COL_NAME_ISTEMPLATE = "X_isTemplate_Y";

    public static final String COL_NAME_ISTEMPLATE = "X_isTemplate";

    public static final String PROP_NAME_LABELREPORTDESIGN = "labelReportDesign";

    public static final String SQL_COL_NAME_LABELREPORTDESIGN = "X_labelReportDesign_117";

    public static final String COL_NAME_LABELREPORTDESIGN = "X_labelReportDesign";

    public static final String PROP_NAME_LOGBOOKENABLED = "logbookEnabled";

    public static final String SQL_COL_NAME_LOGBOOKENABLED = "X_logbookEnabled_Y";

    public static final String COL_NAME_LOGBOOKENABLED = "X_logbookEnabled";

    public static final String PROP_NAME_MANUFACTURER = "manufacturer";

    public static final String SQL_COL_NAME_MANUFACTURER = "X_manufacturer_S";

    public static final String COL_NAME_MANUFACTURER = "X_manufacturer";

    public static final String PROP_NAME_MANUFACTURINGDATE = "manufacturingDate";

    public static final String SQL_COL_NAME_MANUFACTURINGDATE_UTC = "X_manufacturingDate_u";

    public static final String COL_NAME_MANUFACTURINGDATE = "X_manufacturingDate";

    public static final String PROP_NAME_MODE = "mode";

    public static final String SQL_COL_NAME_MODE = "X_mode_I";

    public static final String COL_NAME_MODE = "X_mode";

    public static final String PROP_NAME_MODEL = "model";

    public static final String SQL_COL_NAME_MODEL = "X_model_S";

    public static final String COL_NAME_MODEL = "X_model";

    public static final String PROP_NAME_OVERRIDEAISERVERNAME = "overrideAIServerName";

    public static final String SQL_COL_NAME_OVERRIDEAISERVERNAME = "X_OverrideAIServerName_S";

    public static final String COL_NAME_OVERRIDEAISERVERNAME = "X_OverrideAIServerName";

    public static final String PROP_NAME_OVERRIDEHISTORIANAISRV = "overrideHistorianAISrv";

    public static final String SQL_COL_NAME_OVERRIDEHISTORIANAISRV = "X_OverrideHistorianAISrv_S";

    public static final String COL_NAME_OVERRIDEHISTORIANAISRV = "X_OverrideHistorianAISrv";

    public static final String PROP_NAME_OVERRIDEHISTORIANASRV = "overrideHistorianASrv";

    public static final String SQL_COL_NAME_OVERRIDEHISTORIANASRV = "X_OverrideHistorianASrv_S";

    public static final String COL_NAME_OVERRIDEHISTORIANASRV = "X_OverrideHistorianASrv";

    public static final String PROP_NAME_OVERRIDEHISTORIANSRV = "overrideHistorianSrv";

    public static final String SQL_COL_NAME_OVERRIDEHISTORIANSRV = "X_OverrideHistorianSrv_S";

    public static final String COL_NAME_OVERRIDEHISTORIANSRV = "X_OverrideHistorianSrv";

    public static final String PROP_NAME_OVERRIDELDAREAPATH = "overrideLDAreaPath";

    public static final String SQL_COL_NAME_OVERRIDELDAREAPATH = "X_OverrideLDAreaPath_S";

    public static final String COL_NAME_OVERRIDELDAREAPATH = "X_OverrideLDAreaPath";

    public static final String PROP_NAME_PARENTENTITY = "parentEntity";

    public static final String SQL_COL_NAME_PARENTENTITY = "X_parentEntity_64";

    public static final String COL_NAME_PARENTENTITY = "X_parentEntity";

    public static final String PROP_NAME_SERIALNUMBER = "serialNumber";

    public static final String SQL_COL_NAME_SERIALNUMBER = "X_serialNumber_S";

    public static final String COL_NAME_SERIALNUMBER = "X_serialNumber";

    public static final String PROP_NAME_SHORTDESCRIPTION = "shortDescription";

    public static final String SQL_COL_NAME_SHORTDESCRIPTION = "X_shortDescription_S";

    public static final String COL_NAME_SHORTDESCRIPTION = "X_shortDescription";

    public static final String PROP_NAME_STATEPROXY = "stateProxy";

    public static final String SQL_COL_NAME_STATEPROXY = "X_stateProxy_231";

    public static final String COL_NAME_STATEPROXY = "X_stateProxy";

    public static final String PROP_NAME_TEMPLATEENTITY = "templateEntity";

    public static final String SQL_COL_NAME_TEMPLATEENTITY = "X_templateEntity_64";

    public static final String COL_NAME_TEMPLATEENTITY = "X_templateEntity";

    public String getLDTemplateName();

    public void setLDTemplateName(String var1);

    //
    String getLCEquipAccPriv();

    void setLCEquipAccPriv(String p0);

    //
    String getProcessPackName();

    void setProcessPackName(String p0);


    public String getBarcode();

    public void setBarcode(String var1);

    public String getDescription();

    public void setDescription(String var1);

    public Boolean getDisposable();

    public void setDisposable(Boolean var1);

    public IMESChoiceElement getEquipmentLevel();

    public String getEquipmentLevelAsMeaning();

    public Long getEquipmentLevelAsValue();

    public void setEquipmentLevel(IMESChoiceElement var1);

    public void setEquipmentLevelAsMeaning(String var1);

    public void setEquipmentLevelAsValue(Long var1);

    public IMESChoiceElement getExpiryChgErrBehavior();

    public String getExpiryChgErrBehaviorAsMeaning();

    public Long getExpiryChgErrBehaviorAsValue();

    public void setExpiryChgErrBehavior(IMESChoiceElement var1);

    public void setExpiryChgErrBehaviorAsMeaning(String var1);

    public void setExpiryChgErrBehaviorAsValue(Long var1);

    public String getHistorianProvider();

    public void setHistorianProvider(String var1);

    public String getIdentifier();

    public void setIdentifier(String var1);

    public String getInventoryNumber();

    public void setInventoryNumber(String var1);

    public Boolean getIsMigrated();

    public void setIsMigrated(Boolean var1);

    public Boolean getIsTemplate();

    public void setIsTemplate(Boolean var1);

    public ReportDesign getLabelReportDesign();

    public void setLabelReportDesign(ReportDesign var1);

    public Boolean getLogbookEnabled();

    public void setLogbookEnabled(Boolean var1);

    public String getManufacturer();

    public void setManufacturer(String var1);

    public Time getManufacturingDate();

    public void setManufacturingDate(Time var1);

    public IMESChoiceElement getMode();

    public String getModeAsMeaning();

    public Long getModeAsValue();

    public void setMode(IMESChoiceElement var1);

    public void setModeAsMeaning(String var1);

    public void setModeAsValue(Long var1);

    public String getModel();

    public void setModel(String var1);

    public String getOverrideAIServerName();

    public void setOverrideAIServerName(String var1);

    public String getOverrideHistorianAISrv();

    public void setOverrideHistorianAISrv(String var1);

    public String getOverrideHistorianASrv();

    public void setOverrideHistorianASrv(String var1);

    public String getOverrideHistorianSrv();

    public void setOverrideHistorianSrv(String var1);

    public String getOverrideLDAreaPath();

    public void setOverrideLDAreaPath(String var1);

    public IMESS88Equipment getParentEntity();

    public void setParentEntity(IMESS88Equipment var1);

    public String getSerialNumber();

    public void setSerialNumber(String var1);

    public String getShortDescription();

    public void setShortDescription(String var1);

    public StateProxy getStateProxy();

    public void setStateProxy(StateProxy var1);

    public IMESS88Equipment getTemplateEntity();

    public void setTemplateEntity(IMESS88Equipment var1);
}