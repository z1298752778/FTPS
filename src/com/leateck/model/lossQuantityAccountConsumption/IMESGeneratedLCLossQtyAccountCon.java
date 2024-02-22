package com.leateck.model.lossQuantityAccountConsumption;


/**
 * This file was generated by ATDefAccessClassGenerator and FMPP 2.3.15
 *
 * Please do not modify this file manually !!
 */
import com.datasweep.compatibility.client.MeasuredValue;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObject;

/**
 * Generated interface definition for application table LC_LossQtyAccountCon.<BR>
 * Application table description: 保存消耗phase损耗量
 * 
 * @ftps.exclude  
 */
public interface IMESGeneratedLCLossQtyAccountCon extends IMESATObject {

    /** name of the corresponding AT definition */
    public static final String ATDEFINITION_NAME = "LC_LossQtyAccountCon";

    /** Generated attribute definition */
    public static final String SQL_TABLE_NAME = SQL_TABLE_NAME_PREFIX + ATDEFINITION_NAME;

    /** Generated property name */
    public static final String PROP_NAME_LOSSQTY = "lossQty";

    /** Generated column name */
    public static final String SQL_COL_NAME_LOSSQTY = "lossQty_V";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_LOSSQTY = "lossQty";

    /** Generated property name */
    public static final String PROP_NAME_PHASEKEY = "phaseKey";

    /** Generated column name */
    public static final String SQL_COL_NAME_PHASEKEY = "phaseKey_I";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_PHASEKEY = "phaseKey";

    /** Generated property name */
    public static final String PROP_NAME_SUBLOTNUMBER = "sublotNumber";

    /** Generated column name */
    public static final String SQL_COL_NAME_SUBLOTNUMBER = "sublotNumber_S";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_SUBLOTNUMBER = "sublotNumber";

    /**
     * Generated getter for column lossQty.<BR>
     * Column description: 损耗量
     * 
     * @return the requested value
     */
    public MeasuredValue getLossQty();

    /**
     * Generated setter for column lossQty.<BR>
     * Column description: 损耗量
     * 
     * @param value The new value to be assigned
     */
    public void setLossQty(MeasuredValue value);

    /**
     * Generated getter for column phaseKey.<BR>
     * Column description: phasekey
     * 
     * @return the requested value
     */
    public Long getPhaseKey();

    /**
     * Generated setter for column phaseKey.<BR>
     * Column description: phasekey
     * 
     * @param value The new value to be assigned
     */
    public void setPhaseKey(Long value);

    /**
     * Generated getter for column sublotNumber.<BR>
     * Column description: 子批次号
     * 
     * @return the requested value
     */
    public String getSublotNumber();

    /**
     * Generated setter for column sublotNumber.<BR>
     * Column description: 子批次号
     * 
     * @param value The new value to be assigned
     */
    public void setSublotNumber(String value);

}