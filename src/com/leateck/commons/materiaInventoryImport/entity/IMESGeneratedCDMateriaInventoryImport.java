package com.leateck.commons.materiaInventoryImport.entity;


/**
 * This file was generated by ATDefAccessClassGenerator and FMPP 2.3.15
 *
 * Please do not modify this file manually !!
 */
import com.rockwell.mes.commons.base.ifc.objects.IMESATObject;

/**
 * Generated interface definition for application table CD_MateriaInventoryImport.<BR>
 * Application table description: 
 * 
 * @ftps.exclude  
 */
public interface IMESGeneratedCDMateriaInventoryImport extends IMESATObject {

    /** name of the corresponding AT definition */
    public static final String ATDEFINITION_NAME = "CD_MateriaInventoryImport";

    /** Generated attribute definition */
    public static final String SQL_TABLE_NAME = SQL_TABLE_NAME_PREFIX + ATDEFINITION_NAME;

    /** Generated property name */
    public static final String PROP_NAME_BATCHNO = "batchNo";

    /** Generated column name */
    public static final String SQL_COL_NAME_BATCHNO = "batchNo_S";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_BATCHNO = "batchNo";

    /** Generated property name */
    public static final String PROP_NAME_IMPORTNUMBER = "importNumber";

    /** Generated column name */
    public static final String SQL_COL_NAME_IMPORTNUMBER = "ImportNumber_S";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_IMPORTNUMBER = "ImportNumber";

    /** Generated property name */
    public static final String PROP_NAME_INVENTORYQTY = "inventoryQty";

    /** Generated column name */
    public static final String SQL_COL_NAME_INVENTORYQTY = "inventoryQty_S";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_INVENTORYQTY = "inventoryQty";

    /** Generated property name */
    public static final String PROP_NAME_MATERIALDESC = "materialDesc";

    /** Generated column name */
    public static final String SQL_COL_NAME_MATERIALDESC = "materialDesc_S";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_MATERIALDESC = "materialDesc";

    /** Generated property name */
    public static final String PROP_NAME_MATERIALNO = "materialNo";

    /** Generated column name */
    public static final String SQL_COL_NAME_MATERIALNO = "materialNo_S";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_MATERIALNO = "materialNo";

    /** Generated property name */
    public static final String PROP_NAME_PACKAGINGSPECIFICATIONS = "packagingSpecifications";

    /** Generated column name */
    public static final String SQL_COL_NAME_PACKAGINGSPECIFICATIONS = "packagingSpecifications_S";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_PACKAGINGSPECIFICATIONS = "packagingSpecifications";

    /** Generated property name */
    public static final String PROP_NAME_SPECIFICATIONS = "specifications";

    /** Generated column name */
    public static final String SQL_COL_NAME_SPECIFICATIONS = "specifications_S";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_SPECIFICATIONS = "specifications";

    /** Generated property name */
    public static final String PROP_NAME_SUBLOTNUM = "sublotNum";

    /** Generated column name */
    public static final String SQL_COL_NAME_SUBLOTNUM = "sublotNum_S";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_SUBLOTNUM = "sublotNum";

    /** Generated property name */
    public static final String PROP_NAME_TIP = "tip";

    /** Generated column name */
    public static final String SQL_COL_NAME_TIP = "tip_S";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_TIP = "tip";

    /** Generated property name */
    public static final String PROP_NAME_UNIT = "unit";

    /** Generated column name */
    public static final String SQL_COL_NAME_UNIT = "unit_S";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_UNIT = "unit";

    /** Generated property name */
    public static final String PROP_NAME_UOM = "uom";

    /** Generated column name */
    public static final String SQL_COL_NAME_UOM = "uom_S";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_UOM = "uom";

    /** Generated property name */
    public static final String PROP_NAME_VALIDITYDATA = "validityData";

    /** Generated column name */
    public static final String SQL_COL_NAME_VALIDITYDATA = "validityData_S";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_VALIDITYDATA = "validityData";

    /**
     * Generated getter for column batchNo.<BR>
     * Column description: 物料批次
     * 
     * @return the requested value
     */
    public String getBatchNo();

    /**
     * Generated setter for column batchNo.<BR>
     * Column description: 物料批次
     * 
     * @param value The new value to be assigned
     */
    public void setBatchNo(String value);

    /**
     * Generated getter for column ImportNumber.<BR>
     * Column description: 导入编号
     * 
     * @return the requested value
     */
    public String getImportNumber();

    /**
     * Generated setter for column ImportNumber.<BR>
     * Column description: 导入编号
     * 
     * @param value The new value to be assigned
     */
    public void setImportNumber(String value);

    /**
     * Generated getter for column inventoryQty.<BR>
     * Column description: 入厂编号
     * 
     * @return the requested value
     */
    public String getInventoryQty();

    /**
     * Generated setter for column inventoryQty.<BR>
     * Column description: 入厂编号
     * 
     * @param value The new value to be assigned
     */
    public void setInventoryQty(String value);

    /**
     * Generated getter for column materialDesc.<BR>
     * Column description: 物料名称
     * 
     * @return the requested value
     */
    public String getMaterialDesc();

    /**
     * Generated setter for column materialDesc.<BR>
     * Column description: 物料名称
     * 
     * @param value The new value to be assigned
     */
    public void setMaterialDesc(String value);

    /**
     * Generated getter for column materialNo.<BR>
     * Column description: 物料代码
     * 
     * @return the requested value
     */
    public String getMaterialNo();

    /**
     * Generated setter for column materialNo.<BR>
     * Column description: 物料代码
     * 
     * @param value The new value to be assigned
     */
    public void setMaterialNo(String value);

    /**
     * Generated getter for column packagingSpecifications.<BR>
     * Column description: 最小包装规格
     * 
     * @return the requested value
     */
    public String getPackagingSpecifications();

    /**
     * Generated setter for column packagingSpecifications.<BR>
     * Column description: 最小包装规格
     * 
     * @param value The new value to be assigned
     */
    public void setPackagingSpecifications(String value);

    /**
     * Generated getter for column specifications.<BR>
     * Column description: 规格
     * 
     * @return the requested value
     */
    public String getSpecifications();

    /**
     * Generated setter for column specifications.<BR>
     * Column description: 规格
     * 
     * @param value The new value to be assigned
     */
    public void setSpecifications(String value);

    /**
     * Generated getter for column sublotNum.<BR>
     * Column description: 子批次个数
     * 
     * @return the requested value
     */
    public String getSublotNum();

    /**
     * Generated setter for column sublotNum.<BR>
     * Column description: 子批次个数
     * 
     * @param value The new value to be assigned
     */
    public void setSublotNum(String value);

    /**
     * Generated getter for column tip.<BR>
     * Column description: 接收状态N/Y
     * 
     * @return the requested value
     */
    public String getTip();

    /**
     * Generated setter for column tip.<BR>
     * Column description: 接收状态N/Y
     * 
     * @param value The new value to be assigned
     */
    public void setTip(String value);

    /**
     * Generated getter for column unit.<BR>
     * Column description: 单位
     * 
     * @return the requested value
     */
    public String getUnit();

    /**
     * Generated setter for column unit.<BR>
     * Column description: 单位
     * 
     * @param value The new value to be assigned
     */
    public void setUnit(String value);

    /**
     * Generated getter for column uom.<BR>
     * Column description: 供应商名称
     * 
     * @return the requested value
     */
    public String getUom();

    /**
     * Generated setter for column uom.<BR>
     * Column description: 供应商名称
     * 
     * @param value The new value to be assigned
     */
    public void setUom(String value);

    /**
     * Generated getter for column validityData.<BR>
     * Column description: 有效期
     * 
     * @return the requested value
     */
    public String getValidityData();

    /**
     * Generated setter for column validityData.<BR>
     * Column description: 有效期
     * 
     * @param value The new value to be assigned
     */
    public void setValidityData(String value);

}