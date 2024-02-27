package com.leateck.model.messagetemplatesub;


/**
 * This file was generated by ATDefAccessClassGenerator and FMPP 2.3.15
 *
 * Please do not modify this file manually !!
 */
import com.datasweep.compatibility.client.MeasuredValue;
import com.leateck.model.messagetemplate.IMESLCMessageTemplate;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObject;

/**
 * Generated interface definition for application table LC_MessageTemplateSub.<BR>
 * Application table description: 
 * 
 * @ftps.exclude  
 */
public interface IMESGeneratedLCMessageTemplateSub extends IMESATObject {

    /** name of the corresponding AT definition */
    public static final String ATDEFINITION_NAME = "LC_MessageTemplateSub";

    /** Generated attribute definition */
    public static final String SQL_TABLE_NAME = SQL_TABLE_NAME_PREFIX + ATDEFINITION_NAME;

    /** Generated property name */
    public static final String PROP_NAME_CRITICALTIME = "criticalTime";

    /** Generated column name */
    public static final String SQL_COL_NAME_CRITICALTIME = "criticalTime_V";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_CRITICALTIME = "criticalTime";

    /** Generated property name */
    public static final String PROP_NAME_EQUIPSTATUSMODEL = "equipStatusModel";

    /** Generated column name */
    public static final String SQL_COL_NAME_EQUIPSTATUSMODEL = "equipStatusModel_S";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_EQUIPSTATUSMODEL = "equipStatusModel";

    /** Generated property name */
    public static final String PROP_NAME_MESSAGETEMPLATE = "messageTemplate";

    /** Generated column name */
    public static final String SQL_COL_NAME_MESSAGETEMPLATE = "messageTemplate_64";

    /** 
     * Generated AT column name
     * @ftps.exclude 2 (reason: for internal use only)  
     */
    public static final String COL_NAME_MESSAGETEMPLATE = "messageTemplate";

    /**
     * Generated getter for column criticalTime.<BR>
     * Column description: 临期时间
     * 
     * @return the requested value
     */
    public MeasuredValue getCriticalTime();

    /**
     * Generated setter for column criticalTime.<BR>
     * Column description: 临期时间
     * 
     * @param value The new value to be assigned
     */
    public void setCriticalTime(MeasuredValue value);

    /**
     * Generated getter for column equipStatusModel.<BR>
     * Column description: 状态转换模型
     * 
     * @return the requested value
     */
    public String getEquipStatusModel();

    /**
     * Generated setter for column equipStatusModel.<BR>
     * Column description: 状态转换模型
     * 
     * @param value The new value to be assigned
     */
    public void setEquipStatusModel(String value);

    /**
     * Generated getter for column messageTemplate.<BR>
     * Column description: 
     * 
     * @return the requested value
     */
    public IMESLCMessageTemplate getMessageTemplate();

    /**
     * Generated setter for column messageTemplate.<BR>
     * Column description: 
     * 
     * @param value The new value to be assigned
     */
    public void setMessageTemplate(IMESLCMessageTemplate value);

}