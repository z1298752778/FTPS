package com.rockwell.mes.services.s88equipment.ifc.generated;

import com.datasweep.compatibility.ui.Time;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObject;

public interface IMESGeneratedS88EquipmentClass extends IMESATObject {
    public static final String ATDEFINITION_NAME = "X_S88EquipmentClass";

    public static final String SQL_TABLE_NAME = "AT_X_S88EquipmentClass";

    public static final String PROP_NAME_DESCRIPTION = "description";

    public static final String SQL_COL_NAME_DESCRIPTION = "X_description_S";

    public static final String COL_NAME_DESCRIPTION = "X_description";

    public static final String PROP_NAME_DISPOSABLE = "disposable";

    public static final String SQL_COL_NAME_DISPOSABLE = "X_disposable_Y";

    public static final String COL_NAME_DISPOSABLE = "X_disposable";

    public static final String PROP_NAME_EQUIPMENTLEVEL = "equipmentLevel";

    public static final String SQL_COL_NAME_EQUIPMENTLEVEL = "X_equipmentLevel_I";

    public static final String COL_NAME_EQUIPMENTLEVEL = "X_equipmentLevel";

    public static final String PROP_NAME_IDENTIFIER = "identifier";

    public static final String SQL_COL_NAME_IDENTIFIER = "X_identifier_S";

    public static final String COL_NAME_IDENTIFIER = "X_identifier";

    public static final String PROP_NAME_INVENTORYNUMBER = "inventoryNumber";

    public static final String SQL_COL_NAME_INVENTORYNUMBER = "X_inventoryNumber_S";

    public static final String COL_NAME_INVENTORYNUMBER = "X_inventoryNumber";

    public static final String PROP_NAME_LOGBOOKENABLED = "logbookEnabled";

    public static final String SQL_COL_NAME_LOGBOOKENABLED = "X_logbookEnabled_Y";

    public static final String COL_NAME_LOGBOOKENABLED = "X_logbookEnabled";

    public static final String PROP_NAME_MANUFACTURER = "manufacturer";

    public static final String SQL_COL_NAME_MANUFACTURER = "X_manufacturer_S";

    public static final String COL_NAME_MANUFACTURER = "X_manufacturer";

    public static final String PROP_NAME_MANUFACTURINGDATE = "manufacturingDate";

    public static final String SQL_COL_NAME_MANUFACTURINGDATE_UTC = "X_manufacturingDate_u";

    public static final String COL_NAME_MANUFACTURINGDATE = "X_manufacturingDate";

    public static final String PROP_NAME_MODEL = "model";

    public static final String SQL_COL_NAME_MODEL = "X_model_S";

    public static final String COL_NAME_MODEL = "X_model";

    public static final String PROP_NAME_SERIALNUMBER = "serialNumber";

    public static final String SQL_COL_NAME_SERIALNUMBER = "X_serialNumber_S";

    public static final String COL_NAME_SERIALNUMBER = "X_serialNumber";

    public static final String PROP_NAME_SHORTDESCRIPTION = "shortDescription";

    public static final String SQL_COL_NAME_SHORTDESCRIPTION = "X_shortDescription_S";

    public static final String COL_NAME_SHORTDESCRIPTION = "X_shortDescription";

    public static final String COL_NAME_STATEPROXY = "X_stateProxy";

    public static final String COL_NAME_TILECOLOR = "X_tileColor";

    public static final String COL_NAME_TILEICON = "X_tileIcon";

    public static final String COL_NAME_TILETEXTCOLOR = "X_tileTextColor";

    //
    public static final String PROP_NAME_PROCESSPACKNAME = "processPackName";

    public static final String SQL_COL_NAME_PROCESSPACK = "processPackName_S";

    public static final String COL_NAME_PROCESSPACKNAME = "processPackName";

    //
    String getProcessPackName();

    void setProcessPackName(String p0);

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

    public String getIdentifier();

    public void setIdentifier(String var1);

    public String getInventoryNumber();

    public void setInventoryNumber(String var1);

    public Boolean getLogbookEnabled();

    public void setLogbookEnabled(Boolean var1);

    public String getManufacturer();

    public void setManufacturer(String var1);

    public Time getManufacturingDate();

    public void setManufacturingDate(Time var1);

    public String getModel();

    public void setModel(String var1);

    public String getSerialNumber();

    public void setSerialNumber(String var1);

    public String getShortDescription();

    public void setShortDescription(String var1);
}