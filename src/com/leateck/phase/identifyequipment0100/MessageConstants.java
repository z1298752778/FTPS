package com.leateck.phase.identifyequipment0100;

import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;

public class MessageConstants {
    public static final String MESSAGE_PACK = "LC_Phase_IdentifyEquipment0100";
    //清洁效期临近提醒
    public static final String PROPERTY_CLEAN_EXPIRATION_DATE;
    //连续生产批次数
    public static final String PROPERTY_BATCH_NUMBE;
    //当前连续生产批次数
    public static final String PROPERTY_BATCH_NUMBE_CURRENT;
    //连续生产批次数临近提醒
    public static final String PROPERTY_BATCH_NUMBE_NEAR;
    //灭菌效期临近提醒 Reminder of approaching sterilization expiration date
    public static final String PROPERTY_STERILIZATION_EXPIRATION_DATE;
    //Sterilization_LC_1 灭菌转换模型 StatusGraph
    public static final String STATUS_GRAPH_STERILIZATION;
    static {
        PROPERTY_CLEAN_EXPIRATION_DATE = I18nMessageUtility.getLocalizedMessage(MESSAGE_PACK, "Property_Clean_Date");
        PROPERTY_BATCH_NUMBE = I18nMessageUtility.getLocalizedMessage(MESSAGE_PACK, "Property_Batch_Number");
        PROPERTY_BATCH_NUMBE_CURRENT = I18nMessageUtility.getLocalizedMessage(MESSAGE_PACK, "Property_Batch_Number_Current");
        PROPERTY_BATCH_NUMBE_NEAR = I18nMessageUtility.getLocalizedMessage(MESSAGE_PACK, "Property_Batch_Number_Near");
        PROPERTY_STERILIZATION_EXPIRATION_DATE = I18nMessageUtility.getLocalizedMessage(MESSAGE_PACK, "Property_Sterilization_Expiration_Date");
        STATUS_GRAPH_STERILIZATION = I18nMessageUtility.getLocalizedMessage(MESSAGE_PACK, "StatusGraph_Sterilization");

    }
}
