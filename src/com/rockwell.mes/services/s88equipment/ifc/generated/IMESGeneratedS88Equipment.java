//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.rockwell.mes.services.s88equipment.ifc.generated;

import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.ReportDesign;
import com.datasweep.compatibility.client.StateProxy;
import com.datasweep.compatibility.ui.Time;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObject;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88Equipment;

public interface IMESGeneratedS88Equipment extends IMESATObject {
    String ATDEFINITION_NAME = "X_S88Equipment";
    String SQL_TABLE_NAME = "AT_X_S88Equipment";
    String PROP_NAME_LDTEMPLATENAME = "LDTemplateName";
    String SQL_COL_NAME_LDTEMPLATENAME = "X_LDTemplateName_S";
    String COL_NAME_LDTEMPLATENAME = "X_LDTemplateName";
    String PROP_NAME_LCEQUIPACCPRIV = "equipAccPriv";//新增字段
    String SQL_COL_NAME_ACCESS = "LC_equip_accPriv_98";
    String COL_NAME_LCEQUIPACCPRIV = "LC_equip_accPriv";
    String PROP_NAME_BARCODE = "barcode";
    String SQL_COL_NAME_BARCODE = "X_barcode_S";
    String COL_NAME_BARCODE = "X_barcode";
    String COL_NAME_BARCODEPREFIX = "X_barcodePrefix";
    String COL_NAME_BARCODEPREFIXPOLICY = "X_barcodePrefixPolicy";
    String PROP_NAME_DESCRIPTION = "description";
    String SQL_COL_NAME_DESCRIPTION = "X_description_S";
    String COL_NAME_DESCRIPTION = "X_description";
    String PROP_NAME_DISPOSABLE = "disposable";
    String SQL_COL_NAME_DISPOSABLE = "X_disposable_Y";
    String COL_NAME_DISPOSABLE = "X_disposable";
    String PROP_NAME_EQUIPMENTLEVEL = "equipmentLevel";
    String SQL_COL_NAME_EQUIPMENTLEVEL = "X_equipmentLevel_I";
    String COL_NAME_EQUIPMENTLEVEL = "X_equipmentLevel";
    String PROP_NAME_EXPIRYCHGERRBEHAVIOR = "expiryChgErrBehavior";
    String SQL_COL_NAME_EXPIRYCHGERRBEHAVIOR = "X_expiryChgErrBehavior_I";
    String COL_NAME_EXPIRYCHGERRBEHAVIOR = "X_expiryChgErrBehavior";
    String COL_NAME_GENIDENTIFIERLENGTH = "X_genIdentifierLength";
    String PROP_NAME_HISTORIANPROVIDER = "historianProvider";
    String SQL_COL_NAME_HISTORIANPROVIDER = "X_HistorianProvider_S";
    String COL_NAME_HISTORIANPROVIDER = "X_HistorianProvider";
    String PROP_NAME_IDENTIFIER = "identifier";
    String SQL_COL_NAME_IDENTIFIER = "X_identifier_S";
    String COL_NAME_IDENTIFIER = "X_identifier";
    String COL_NAME_IDENTIFIERPREFIX = "X_identifierPrefix";
    String PROP_NAME_INVENTORYNUMBER = "inventoryNumber";
    String SQL_COL_NAME_INVENTORYNUMBER = "X_inventoryNumber_S";
    String COL_NAME_INVENTORYNUMBER = "X_inventoryNumber";
    String PROP_NAME_ISMIGRATED = "isMigrated";
    String SQL_COL_NAME_ISMIGRATED = "X_isMigrated_Y";
    String COL_NAME_ISMIGRATED = "X_isMigrated";
    String PROP_NAME_ISTEMPLATE = "isTemplate";
    String SQL_COL_NAME_ISTEMPLATE = "X_isTemplate_Y";
    String COL_NAME_ISTEMPLATE = "X_isTemplate";
    String PROP_NAME_LABELREPORTDESIGN = "labelReportDesign";
    String SQL_COL_NAME_LABELREPORTDESIGN = "X_labelReportDesign_117";
    String COL_NAME_LABELREPORTDESIGN = "X_labelReportDesign";
    String PROP_NAME_LOGBOOKENABLED = "logbookEnabled";
    String SQL_COL_NAME_LOGBOOKENABLED = "X_logbookEnabled_Y";
    String COL_NAME_LOGBOOKENABLED = "X_logbookEnabled";
    String PROP_NAME_MANUFACTURER = "manufacturer";
    String SQL_COL_NAME_MANUFACTURER = "X_manufacturer_S";
    String COL_NAME_MANUFACTURER = "X_manufacturer";
    String PROP_NAME_MANUFACTURINGDATE = "manufacturingDate";
    String SQL_COL_NAME_MANUFACTURINGDATE_UTC = "X_manufacturingDate_u";
    String COL_NAME_MANUFACTURINGDATE = "X_manufacturingDate";
    String PROP_NAME_MODE = "mode";
    String SQL_COL_NAME_MODE = "X_mode_I";
    String COL_NAME_MODE = "X_mode";
    String PROP_NAME_MODEL = "model";
    String SQL_COL_NAME_MODEL = "X_model_S";
    String COL_NAME_MODEL = "X_model";
    String PROP_NAME_OVERRIDEAISERVERNAME = "overrideAIServerName";
    String SQL_COL_NAME_OVERRIDEAISERVERNAME = "X_OverrideAIServerName_S";
    String COL_NAME_OVERRIDEAISERVERNAME = "X_OverrideAIServerName";
    String PROP_NAME_OVERRIDEHISTORIANAISRV = "overrideHistorianAISrv";
    String SQL_COL_NAME_OVERRIDEHISTORIANAISRV = "X_OverrideHistorianAISrv_S";
    String COL_NAME_OVERRIDEHISTORIANAISRV = "X_OverrideHistorianAISrv";
    String PROP_NAME_OVERRIDEHISTORIANASRV = "overrideHistorianASrv";
    String SQL_COL_NAME_OVERRIDEHISTORIANASRV = "X_OverrideHistorianASrv_S";
    String COL_NAME_OVERRIDEHISTORIANASRV = "X_OverrideHistorianASrv";
    String PROP_NAME_OVERRIDEHISTORIANSRV = "overrideHistorianSrv";
    String SQL_COL_NAME_OVERRIDEHISTORIANSRV = "X_OverrideHistorianSrv_S";
    String COL_NAME_OVERRIDEHISTORIANSRV = "X_OverrideHistorianSrv";
    String PROP_NAME_OVERRIDELDAREAPATH = "overrideLDAreaPath";
    String SQL_COL_NAME_OVERRIDELDAREAPATH = "X_OverrideLDAreaPath_S";
    String COL_NAME_OVERRIDELDAREAPATH = "X_OverrideLDAreaPath";
    String PROP_NAME_PARENTENTITY = "parentEntity";
    String SQL_COL_NAME_PARENTENTITY = "X_parentEntity_64";
    String COL_NAME_PARENTENTITY = "X_parentEntity";
    String PROP_NAME_SERIALNUMBER = "serialNumber";
    String SQL_COL_NAME_SERIALNUMBER = "X_serialNumber_S";
    String COL_NAME_SERIALNUMBER = "X_serialNumber";
    String PROP_NAME_SHORTDESCRIPTION = "shortDescription";
    String SQL_COL_NAME_SHORTDESCRIPTION = "X_shortDescription_S";
    String COL_NAME_SHORTDESCRIPTION = "X_shortDescription";
    String PROP_NAME_STATEPROXY = "stateProxy";
    String SQL_COL_NAME_STATEPROXY = "X_stateProxy_231";
    String COL_NAME_STATEPROXY = "X_stateProxy";
    String PROP_NAME_TEMPLATEENTITY = "templateEntity";
    String SQL_COL_NAME_TEMPLATEENTITY = "X_templateEntity_64";
    String COL_NAME_TEMPLATEENTITY = "X_templateEntity";

    String getLDTemplateName();

    void setLDTemplateName(String value);
    
    String getLCEquipAccPriv();
    
    void setLCEquipAccPriv(String value);

    String getBarcode();

    void setBarcode(String value);

    String getDescription();

    void setDescription(String value);

    Boolean getDisposable();

    void setDisposable(Boolean value);

    IMESChoiceElement getEquipmentLevel();

    String getEquipmentLevelAsMeaning();

    Long getEquipmentLevelAsValue();

    void setEquipmentLevel(IMESChoiceElement value);

    void setEquipmentLevelAsMeaning(String value);

    void setEquipmentLevelAsValue(Long value);

    IMESChoiceElement getExpiryChgErrBehavior();

    String getExpiryChgErrBehaviorAsMeaning();

    Long getExpiryChgErrBehaviorAsValue();

    void setExpiryChgErrBehavior(IMESChoiceElement value);

    void setExpiryChgErrBehaviorAsMeaning(String value);

    void setExpiryChgErrBehaviorAsValue(Long value);

    String getHistorianProvider();

    void setHistorianProvider(String value);

    String getIdentifier();

    void setIdentifier(String value);

    String getInventoryNumber();

    void setInventoryNumber(String value);

    Boolean getIsMigrated();

    void setIsMigrated(Boolean value);

    Boolean getIsTemplate();

    void setIsTemplate(Boolean value);

    ReportDesign getLabelReportDesign();

    void setLabelReportDesign(ReportDesign value);

    Boolean getLogbookEnabled();

    void setLogbookEnabled(Boolean value);

    String getManufacturer();

    void setManufacturer(String value);

    Time getManufacturingDate();

    void setManufacturingDate(Time value);

    IMESChoiceElement getMode();

    String getModeAsMeaning();

    Long getModeAsValue();

    void setMode(IMESChoiceElement value);

    void setModeAsMeaning(String value);

    void setModeAsValue(Long value);

    String getModel();

    void setModel(String value);

    String getOverrideAIServerName();

    void setOverrideAIServerName(String value);

    String getOverrideHistorianAISrv();

    void setOverrideHistorianAISrv(String value);

    String getOverrideHistorianASrv();

    void setOverrideHistorianASrv(String value);

    String getOverrideHistorianSrv();

    void setOverrideHistorianSrv(String value);

    String getOverrideLDAreaPath();

    void setOverrideLDAreaPath(String value);

    IMESS88Equipment getParentEntity();

    void setParentEntity(IMESS88Equipment value);

    String getSerialNumber();

    void setSerialNumber(String value);

    String getShortDescription();

    void setShortDescription(String value);

    StateProxy getStateProxy();

    void setStateProxy(StateProxy value);

    IMESS88Equipment getTemplateEntity();

    void setTemplateEntity(IMESS88Equipment value);
}
