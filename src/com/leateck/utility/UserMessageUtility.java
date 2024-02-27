package com.leateck.utility;

import com.datasweep.compatibility.client.ATHandler;
import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.User;
import com.datasweep.compatibility.client.UserGroup;
import com.datasweep.compatibility.ui.Time;
import com.rockwell.mes.commons.base.ifc.functional.MeasuredValueUtilities;
import com.rockwell.mes.commons.base.ifc.services.IFunctionsEx;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import org.apache.commons.collections.ListUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户消息提醒
 */
public class UserMessageUtility {

    //平台function
    static IFunctionsEx functions = PCContext.getFunctions();
    //工单创建
    public final static int ORDER_CREATE = 10;
    //工单完成
    public final static int ORDER_END = 20;
    //密码过期
    public final static int PASSWORD_EXPIRATION = 30;
    //设备过期
    public final static int EQUIPMENT_EXPIRATION = 40;
    //设备类过期
    public final static int EQUIPMENT_CLASS_EXPIRATION = 50;
    //物料过期
    public final static int MATERIAL_EXPIRATION = 60;
    //异常报警
    public final static int EXCEPTION_ALARM = 70;
    //批报打印
    public final static int REPORT_PRINTING = 80;


    /**
     * 工单创建消息提醒
     */
    public static void OrderCreateMessage() {
        //查询工单创建任务模板
        List<String[]> messageTemplates = getMessageTemplates(ORDER_CREATE);
        if (messageTemplates.size() == 0) {
            return;
        }
        //执行模板任务
        for (String[] messageTemplate : messageTemplates) {
            String id = messageTemplate[0];
            String codeName = messageTemplate[1];
            String customMessage = messageTemplate[2];
            String userGroupStr = messageTemplate[3];
            String userStr = messageTemplate[4];
            String criticalTime = messageTemplate[5];
            String templateType = messageTemplate[6];

            Long minute = 0l;
            if (!isEmptyStr(criticalTime)) {
                minute = MeasuredValueUtilities.createMV(criticalTime).getValue().longValue();
            }
            //查询工单
            String orderSql = "SELECT order_item_name ,convert (nvarchar ,X_plannedStartDate_T,120)planTime,\n" +
                    "convert(nvarchar ,GETDATE(),120) nowDate , t5.master_recipe_name ,t1.part_number ,\n" +
                    "t6.description ,t7.batch_name from PROCESSORDERITEM_UV t1 \n" +
                    "join OBJECT_STATE t2 on t1.order_item_key = object_key and t2.object_type = 112\n" +
                    "join STATE t3 on t2.state_key = t3.state_key \n" +
                    "join CONTROL_RECIPE t4 on t1.order_item_key = t4.order_item_key \n" +
                    "join MASTER_RECIPE t5 on t4.master_recipe_key = t5.master_recipe_key \n" +
                    "join PART t6 on t1.part_number = t6.part_number \n" +
                    "left join BATCH t7 on t1.X_batch_105 = batch_key \n" +
                    "where t3.state_name = 'Released' \n" +
                    "and  (t1.X_plannedStartDate_T > GETDATE()) \n" +
                    "and (dateadd(MINUTE, " + -minute + ", t1.X_plannedStartDate_T) <= getdate())";
            //添加处方对象条件
            if (codeName != null) {
                orderSql += "and t5.master_recipe_name = N'" + codeName + "'";
            }
            List<String[]> orderInfos = functions.getArrayDataFromActive(orderSql);
            if (orderInfos.size() == 0) {
                continue;
            }
            for (String[] orderInfo : orderInfos) {
                String orderName = orderInfo[0];
                String planTimeStr = orderInfo[1];
                String nowTime = orderInfo[2];
                String masterRecipeName = orderInfo[3];
                String partNumber = orderInfo[4];
                String partName = orderInfo[5];
                String batch = orderInfo[6];

                //拼接消息提醒
                String message = getMessageStr("MessageInfo_orderCreateMessage");
                message = message.replace("{1}", masterRecipeName);
                message = message.replace("{2}", partNumber);
                message = message.replace("{3}", partName);
                message = message.replace("{4}", orderName);
                message = message.replace("{5}", batch == null ? "null" : batch);
                message = message.replace("{6}", planTimeStr == null ? "null" : planTimeStr);
                message = message.replace("{7}", customMessage == null ? "" : "," + customMessage);
                //转换计划时间
                Timestamp planTime = Timestamp.valueOf(planTimeStr);
                //查询是否已存在消息
                String messageSql = "select * from  AT_LC_UserMessage where triggerObject_S = '" + orderName + "'" +
                        " and planTime_T > '" + nowTime + "'";
                List<String[]> messages = functions.getArrayDataFromActive(messageSql);
                if (messages.size() == 0) {
                    //获取需提醒用户对象集合
                    List<User> users = getUsersByUsersStr(userStr);
                    List<User> groupUsers = getUsersByUserGroupsStr(userGroupStr);
                    List<User> allUser = ListUtils.sum(users, groupUsers);
                    for (User user :
                            allUser) {
                        saveUserMessage(id,templateType,user.getName(),codeName,orderName,
                                planTime,message);
                    }
                }

            }

        }

    }

    /**
     * 工单完成消息提醒
     */
    public static void OrderFinishMessage() {
        //查询工单结束任务模板
        List<String[]> messageTemplates = getMessageTemplates(ORDER_END);
        if (messageTemplates.size() == 0) {
            return;
        }
        //执行模板任务
        for (String[] messageTemplate : messageTemplates) {
            String id = messageTemplate[0];
            String codeName = messageTemplate[1];
            String customMessage = messageTemplate[2];
            String userGroupStr = messageTemplate[3];
            String userStr = messageTemplate[4];
            String templateType = messageTemplate[6];

            //查询消息中工单最后的完成时间
            String messageSql = "SELECT TOP 1 convert(nvarchar ,um.planTime_T,120) time from AT_LC_UserMessage um " +
                    " where um.messageType_I = 20 order by um.planTime_T DESC";
            List<String[]> messages = functions.getArrayDataFromActive(messageSql);
            String finishedTime = "";
            if (messages.size() != 0) {
                finishedTime = messages.get(0)[0];
            }
            //查询满足条件工单
            String orderSql = "SELECT order_item_name ,convert (nvarchar ,X_actualFinishDate_T,120) finishTime,\n" +
                    " t5.master_recipe_name ,t1.part_number ,\n" +
                    " t6.description ,t7.batch_name from PROCESSORDERITEM_UV t1 \n" +
                    " join OBJECT_STATE t2 on t1.order_item_key = object_key and t2.object_type = 112\n" +
                    " join STATE t3 on t2.state_key = t3.state_key \n" +
                    " join CONTROL_RECIPE t4 on t1.order_item_key = t4.order_item_key \n" +
                    " join MASTER_RECIPE t5 on t4.master_recipe_key = t5.master_recipe_key \n" +
                    " join PART t6 on t1.part_number = t6.part_number \n" +
                    " left join BATCH t7 on t1.X_batch_105 = batch_key \n" +
                    " where t3.state_name = 'Finished' ";
            //添加处方对象条件
            if (!isEmptyStr(codeName)) {
                orderSql += "and t5.master_recipe_name = N'" + codeName + "'";
            }
            //添加完成日期条件
            if (!isEmptyStr(finishedTime)) {
                orderSql += "and convert (nvarchar ,X_actualFinishDate_T,120) > '" + finishedTime + "'";
            }
            List<String[]> orderInfos = functions.getArrayDataFromActive(orderSql);
            if (orderInfos.size() == 0) {
                continue;
            }
            for (String[] orderInfo : orderInfos) {
                String orderName = orderInfo[0];
                String finishTimeStr = orderInfo[1];
                String masterRecipeName = orderInfo[2];
                String partNumber = orderInfo[3];
                String partName = orderInfo[4];
                String batch = orderInfo[5];

                //拼接消息提醒
                String message = getMessageStr("MessageInfo_orderFinishMessage");
                message = message.replace("{1}", masterRecipeName);
                message = message.replace("{2}", partNumber);
                message = message.replace("{3}", partName);
                message = message.replace("{4}", orderName);
                message = message.replace("{5}", batch == null ? "null" : batch);
                message = message.replace("{6}", finishTimeStr == null ? "null" : finishTimeStr);
                message = message.replace("{7}", customMessage == null ? "" : "," + customMessage);
                //转换完成时间
                Timestamp finishTime = Timestamp.valueOf(finishTimeStr);

                //获取需提醒用户对象集合
                List<User> users = getUsersByUsersStr(userStr);
                List<User> groupUsers = getUsersByUserGroupsStr(userGroupStr);
                List<User> allUser = ListUtils.sum(users, groupUsers);
                for (User user :
                        allUser) {
                    saveUserMessage(id,templateType,user.getName(),codeName,orderName,
                            finishTime,message);
                }

            }

        }

    }

    /**
     * 用户密码过期消息提醒
     */
    public static void PasswordExpirationMessage() {
        //查询密码过期提醒任务模板
        List<String[]> messageTemplates = getMessageTemplates(PASSWORD_EXPIRATION);
        if (messageTemplates.size() == 0) {
            return;
        }
        //执行模板任务
        for (String[] messageTemplate : messageTemplates) {
            String id = messageTemplate[0];
            String customMessage = messageTemplate[2];
            String userGroupStr = messageTemplate[3];
            String userStr = messageTemplate[4];
            String criticalTime = messageTemplate[5];
            String templateType = messageTemplate[6];

            //计算临期时间
            Long minute = 0l;
            if (!isEmptyStr(criticalTime)) {
                minute = MeasuredValueUtilities.createMV(criticalTime).getValue().longValue();
            }

            //查询满足条件的用户
            String userSql = "select user_name,first_name + last_name name," +
                    " convert (nvarchar ,password_expiration,120) password_expiration" +
                    " from APP_USER where status != 'Disable' " +
                    " and dateadd(MINUTE, " + -minute + ", password_expiration) <= getdate()";
            List<String[]> userInfos = functions.getArrayDataFromActive(userSql);
            if (userInfos.size() == 0) {
                continue;
            }
            for (String [] userInfo:userInfos) {
                String userNumber = userInfo[0];
                String userName = userInfo[1];
                String passwordExpiration = userInfo[2];
                //查询满足条件工单
                String messageSql = "select atr_key from AT_LC_UserMessage where messageType_I = 30 " +
                        " and planTime_T <= getdate() and triggerObject_S = '" + userNumber +"'";
                List<String[]> messageInfos = functions.getArrayDataFromActive(messageSql);
                if (messageInfos.size() != 0) {
                    continue;
                }

                //拼接消息提醒
                String message = getMessageStr("MessageInfo_passwordExpirationMessage");
                message = message.replace("{1}", userNumber);
                message = message.replace("{2}", userName);
                message = message.replace("{3}", passwordExpiration);
                message = message.replace("{4}", customMessage == null ? "" :"," + customMessage);

                //转换密码过期时间
                Timestamp passwordExpirationDate = Timestamp.valueOf(passwordExpiration);
                //获取需提醒用户对象集合
                List<User> users = getUsersByUsersStr(userStr);
                List<User> groupUsers = getUsersByUserGroupsStr(userGroupStr);
                List<User> allUser = ListUtils.sum(users, groupUsers);
                for (User user :
                        allUser) {
                    saveUserMessage(id,templateType,user.getName(),null,userNumber,
                            passwordExpirationDate,message);
                }
            }

        }
    }

    /**
     * 物料过期消息提醒
     */
    public static void MaterialExpirationMessage() {
        //查询物料过期提醒任务模板
        List<String[]> messageTemplates = getMessageTemplates(MATERIAL_EXPIRATION);
        if (messageTemplates.size() == 0) {
            return;
        }
        //执行模板任务
        for (String[] messageTemplate : messageTemplates) {
            String id = messageTemplate[0];
            String codeName = messageTemplate[1];
            String customMessage = messageTemplate[2];
            String userGroupStr = messageTemplate[3];
            String userStr = messageTemplate[4];
            String criticalTime = messageTemplate[5];
            String templateType = messageTemplate[6];

            //计算临期时间
            Long minute = 0l;
            if (!isEmptyStr(criticalTime)) {
                minute = MeasuredValueUtilities.createMV(criticalTime).getValue().longValue();
            }

            //查询满足条件的批次
            String materialSql = "select t2.part_number ,t2.description ,t1.batch_name,convert (nvarchar ,t1.expiration_time,120),\n" +
                    " DATEDIFF(DAY,getdate(),t1.expiration_time) expirationDay from BATCH_UV  t1 \n" +
                    " join PART t2 on t1.part_key  = t2.part_key \n" +
                    " join OBJECT_STATE t3 on t1.batch_key = t3.object_key \n" +
                    " join STATE t4 on t3.state_key = t4.state_key and t3.object_type = 105\n" +
                    " where t4.state_name = 'Released' \n" +
                    " and (dbo.raMVNumeric(t1.quantity) is not null and dbo.raMVNumeric(t1.quantity) != 0)" +
                    " and dateadd(MINUTE, " + -minute + ", t1.expiration_time) <= getdate()";

            //添加处方对象条件
            if (!isEmptyStr(codeName)) {
                materialSql += " and t2.part_number = N'" + codeName + "'";
            }

            List<String[]> materialInfos = functions.getArrayDataFromActive(materialSql);
            if (materialInfos.size() == 0) {
                continue;
            }
            for (String [] materialInfo:materialInfos) {
                String materialNumber = materialInfo[0];
                String materialName = materialInfo[1];
                String batch = materialInfo[2];
                String expirationTime = materialInfo[3];
                String expirationDay = materialInfo[4];

                //判断信息是否已存在
                String messageSql = "select * from  AT_LC_UserMessage where triggerObject_S = '" + batch + "'" +
                        " and planTime_T > getdate()";
                List<String[]> messageInfos = functions.getArrayDataFromActive(messageSql);
                if (messageInfos.size() != 0) {
                    continue;
                }

                //拼接消息提醒
                String message = getMessageStr("MessageInfo_materialExpirationMessage");
                message = message.replace("{1}", materialNumber);
                message = message.replace("{2}", materialName);
                message = message.replace("{3}", batch);
                message = message.replace("{4}", expirationDay);
                message = message.replace("{5}", customMessage == null ? "" :"," + customMessage);

                //转换密码过期时间
                Timestamp expirationTimeDate = Timestamp.valueOf(expirationTime);
                //获取需提醒用户对象集合
                List<User> users = getUsersByUsersStr(userStr);
                List<User> groupUsers = getUsersByUserGroupsStr(userGroupStr);
                List<User> allUser = ListUtils.sum(users, groupUsers);
                for (User user :
                        allUser) {
                    saveUserMessage(id,templateType,user.getName(),codeName,batch,expirationTimeDate,message);
                }
            }

        }

    }

    /**
     * 异常报警消息提醒
     */
    public static void ExceptionAlarmMessage() {
        //查询异常报警提醒任务模板
        List<String[]> messageTemplates = getMessageTemplates(EXCEPTION_ALARM);
        if (messageTemplates.size() == 0) {
            return;
        }
        //执行模板任务
        for (String[] messageTemplate : messageTemplates) {
            String id = messageTemplate[0];
            String codeName = messageTemplate[1];
            String customMessage = messageTemplate[2];
            String userGroupStr = messageTemplate[3];
            String userStr = messageTemplate[4];
            String templateType = messageTemplate[6];
            int riskClass = getExceptionRiskValue(codeName);

            //查询消息中异常记录的最后时间
            String messageSql = "SELECT TOP 1 convert(nvarchar ,um.planTime_T,120) time from AT_LC_UserMessage um " +
                    " where um.messageType_I = 70 ";
            //添加异常等级条件
            if (riskClass != 100) {
                messageSql += "and um.messageCode_S = N'" + codeName + "'";
            }
            messageSql += " order by um.planTime_T DESC";

            List<String[]> messages = functions.getArrayDataFromActive(messageSql);
            String exceptionTime = "";
            if (messages.size() != 0) {
                exceptionTime = messages.get(0)[0];
            }
            //查询满足条件异常记录
            String exceptionSql = "SELECT t1.atr_key,t1.X_description_S,t2.X_ctxKey_I ,t2.X_ctxName_S, " +
                    " convert(nvarchar ,t1.creation_time ,120) from AT_X_ExceptionRecord t1" +
                    " join AT_X_ExceptionObjectRelation t2 on t1.atr_key = t2.X_exceptionRecord_64" +
                    " where t1.X_status_I = 10 ";
            //添加异常等级条件
            if (riskClass != 100) {
                exceptionSql += "and t1.X_riskClass_I = " + riskClass ;
            }
            //添加创建日期条件
            if (!isEmptyStr(exceptionTime)) {
                exceptionSql += "and convert (nvarchar ,t1.creation_time ,120) > '" + exceptionTime + "'";
            }
            List<String[]> exceptionRecords = functions.getArrayDataFromActive(exceptionSql);
            if (exceptionRecords.size() == 0) {
                continue;
            }
            for (String[] exceptionRecord : exceptionRecords) {
                String exceptionKey = exceptionRecord[0];
                String description = exceptionRecord[1];
                String ctxKey = exceptionRecord[2];
                String ctxName = exceptionRecord[3];
                String createTimeStr = exceptionRecord[4];
                String exceptionRoute = getExceptionRoute(ctxName,ctxKey);

                //拼接消息提醒
                String message = getMessageStr("MessageInfo_ExceptionAlarmMessage");
                message = message.replace("{1}", exceptionRoute);
                message = message.replace("{2}", description);
                message = message.replace("{3}", customMessage == null ? "" :"," +  customMessage);
                //转换创建时间
                Timestamp createTime = Timestamp.valueOf(createTimeStr);

                //获取需提醒用户对象集合
                List<User> users = getUsersByUsersStr(userStr);
                List<User> groupUsers = getUsersByUserGroupsStr(userGroupStr);
                List<User> allUser = ListUtils.sum(users, groupUsers);
                for (User user :
                        allUser) {
                    saveUserMessage(id,templateType,user.getName(),codeName,exceptionKey,
                            createTime,message);
                }

            }

        }

    }

    /**
     * 设备状态过期消息提醒
     */
    public static void EquipmentExpirationMessage() {
        //查询设备状态过期提醒任务模板
        List<String[]> messageTemplates = getMessageTemplates(EQUIPMENT_EXPIRATION);
        if (messageTemplates.size() == 0) {
            return;
        }
        //执行模板任务
        for (String[] messageTemplate : messageTemplates) {
            String id = messageTemplate[0];
            String codeName = messageTemplate[1];
            String customMessage = messageTemplate[2];
            String userGroupStr = messageTemplate[3];
            String userStr = messageTemplate[4];
            String templateType = messageTemplate[6];
            String criticalTime = messageTemplate[5];

            //计算临期时间
            Long minute = 0l;
            if (!isEmptyStr(criticalTime)) {
                minute = MeasuredValueUtilities.createMV(criticalTime).getValue().longValue();
            }

            String equSql = "select t1.X_identifier_S from AT_X_S88Equipment t1\n" +
                    " join OBJECT_STATE t2 on t1.X_stateProxy_231 = t2.object_key \n" +
                    " join STATE t3 on t2.state_key = t3.state_key \n" +
                    " join FSM t4 on t2.fsm_key = t4.fsm_key and t4.fsm_name = 'S88EquipmentEntityStatusGraph'\n" +
                    " where t3.state_name = 'Approved'\n" ;
            //设备对应状态转换
            Map<String,List<String>> equipStatusModels = new HashMap<>();
            //若消息代码不为空则查询单个设备状态，否则查询所有设备
            if(!isEmptyStr(codeName)){
                equSql += " and t1.X_identifier_S = N'" + codeName + "'";
                List<String[]> equipments = functions.getArrayDataFromActive(equSql);
                if (equipments.size() == 0) {
                    continue;
                }
                //查询消息模板子项
                String templateSubSql = "select t2.equipStatusModel_S from AT_LC_MessageTemplate t1" +
                        " join AT_LC_MessageTemplateSub t2 on t1.atr_key = t2.messageTemplate_64 " +
                        " where t1.messageId_S = N'" + id + "'";

                List<String[]> templateSubs = functions.getArrayDataFromActive(templateSubSql);
                if (templateSubs.size() == 0) {
                    continue;
                }
                List<String> statusModel = new ArrayList<>();
                for (String[] templateSub: templateSubs) {
                    statusModel.add(templateSub[0]);
                }
                equipStatusModels.put(codeName,statusModel);
            }else {
                //查询所有设备的状态模型
                List<String[]> equipments = functions.getArrayDataFromActive(equSql);
                if (equipments.size() == 0) {
                    continue;
                }
                for (String[] equipment: equipments){
                    String equipmentId = equipment[0];
                    String statuModelSql = "select t4.X_identifier_S \n" +
                            " from AT_X_S88Equipment t1\n" +
                            " join AT_X_S88StatusGraphAssignment t2 on t1.atr_key = t2.X_equipmentEntity_64 \n" +
                            " join AT_X_S88StatusGraphState t3 on t2.X_currentState_64 = t3.atr_key\n" +
                            " join AT_X_S88StatusGraph t4 on t4.atr_key = t3.X_statusGraph_64 \n" +
                            " where t1.X_identifier_S = N'" + equipmentId + "'";
                    List<String[]> statuModels = functions.getArrayDataFromActive(statuModelSql);
                    if (statuModels.size() == 0) {
                        continue;
                    }
                    List<String> statusModelList = new ArrayList<>();
                    for (String[] statuModel: statuModels){
                        statusModelList.add(statuModel[0]);
                    }
                    equipStatusModels.put(equipmentId,statusModelList);
                }
            }
            //查询设备对应的状态是否过期
            for (Map.Entry<String, List<String>> stringListEntry : equipStatusModels.entrySet()) {
                String equId = stringListEntry.getKey();
                List<String> equStatusModels = stringListEntry.getValue();
                for (String equStatusModel : equStatusModels) {
                    String statusModelSql = "select t1.X_identifier_S ,t1.X_shortDescription_S ,t3.X_displayName_S," +
                            " t4.X_identifier_S," +
                            " CONVERT(varchar(20),t2.X_expiryDate_T,120),DATEDIFF(DAY,getdate(),t2.X_expiryDate_T)  " +
                            " from AT_X_S88Equipment t1" +
                            " left join AT_X_S88StatusGraphAssignment t2 on t1.atr_key = t2.X_equipmentEntity_64 " +
                            " left join AT_X_S88StatusGraphState t3 on t2.X_currentState_64 = t3.atr_key" +
                            " left join AT_X_S88StatusGraph t4 on t4.atr_key = t3.X_statusGraph_64 " +
                            " where t4.X_identifier_S = N'"+ equStatusModel + "'" +
                            " and t1.X_identifier_S = N'" + equId + "'" +
                            " and t2.X_expiryDate_T > getdate()"+
                            " and (t2.X_expiryDate_T is not null and dateadd(MINUTE, " + -minute + ",t2.X_expiryDate_T) <= getdate())";
                    List<String[]> expireStatusModels = functions.getArrayDataFromActive(statusModelSql);
                    if (expireStatusModels.size() == 0) {
                        continue;
                    }
                    String equipId = expireStatusModels.get(0)[0];
                    String shortDescription = expireStatusModels.get(0)[1];
                    String statusName = expireStatusModels.get(0)[2];
                    String statusModelName = expireStatusModels.get(0)[3];
                    String expiryDateStr = expireStatusModels.get(0)[4];
                    String expiryDateDay = expireStatusModels.get(0)[5];
                    String triggerObject = equipId + "，" + statusName + "，" + statusModelName ;

                    //查询消息提醒是否存在
                    String messageSql = "select * from  AT_LC_UserMessage where triggerObject_S = N'" + triggerObject + "'" +
                            " and planTime_T > getdate()";

                    //若已存在，则跳过
                    List<String[]> messages = functions.getArrayDataFromActive(messageSql);
                    if (messages.size() != 0) {
                        continue;
                    }

                    //拼接消息提醒
                    String message = getMessageStr("MessageInfo_equipmentExpirationMessage");
                    message = message.replace("{1}", equipId);
                    message = message.replace("{2}", shortDescription);
                    message = message.replace("{3}", statusModelName);
                    message = message.replace("{4}", expiryDateDay);
                    message = message.replace("{5}", customMessage == null ? "" :"," +  customMessage);

                    //转换创建时间
                    Timestamp expiryDate = Timestamp.valueOf(expiryDateStr);

                    //获取需提醒用户对象集合
                    List<User> users = getUsersByUsersStr(userStr);
                    List<User> groupUsers = getUsersByUserGroupsStr(userGroupStr);
                    List<User> allUser = ListUtils.sum(users, groupUsers);
                    for (User user :
                            allUser) {
                        saveUserMessage(id,templateType,user.getName(),codeName,triggerObject,
                                expiryDate,message);
                    }

                }
            }
        }

    }

    /**
     * 设备类状态过期消息提醒
     */
    public static void EquipmentClassExpirationMessage() {
        //查询设备类状态过期提醒任务模板
        List<String[]> messageTemplates = getMessageTemplates(EQUIPMENT_CLASS_EXPIRATION);
        if (messageTemplates.size() == 0) {
            return;
        }
        //执行模板任务
        for (String[] messageTemplate : messageTemplates) {
            String id = messageTemplate[0];
            String codeName = messageTemplate[1];
            String customMessage = messageTemplate[2];
            String userGroupStr = messageTemplate[3];
            String userStr = messageTemplate[4];
            String templateType = messageTemplate[6];
            String criticalTime = messageTemplate[5];

            //计算临期时间
            Long minute = 0l;
            if (!isEmptyStr(criticalTime)) {
                minute = MeasuredValueUtilities.createMV(criticalTime).getValue().longValue();
            }
            //若消息代码不为空则查询单个设备类下的设备，否则所有设备类下的设备
            if(!isEmptyStr(codeName)){
                //查询模板中配置的设备类下的批准的设备
                String equClassSql = "select t3.X_identifier_S ,t1.X_identifier_S  from AT_X_S88EquipmentClass t1\n" +
                        " join AT_X_S88EquClass2Equipment t2 on t1.atr_key = t2.X_equipmentClass_64 \n" +
                        " join AT_X_S88Equipment t3 on t2.X_equipment_64 = t3.atr_key \n" +
                        " join OBJECT_STATE t4 on t3.X_stateProxy_231 = t4.object_key \n" +
                        " join STATE t5 on t4.state_key = t5.state_key \n" +
                        " join FSM t6 on t4.fsm_key = t6.fsm_key and t6.fsm_name = 'S88EquipmentEntityStatusGraph'\n" +
                        " join OBJECT_STATE t7 on t1.X_stateProxy_231 = t7.object_key \n" +
                        " join STATE t8 on t7.state_key = t8.state_key \n" +
                        " join FSM t9 on t8.fsm_key = t9.fsm_key and t9.fsm_name = 'S88EquipmentClassStatusGraph'\n" +
                        " where t5.state_name = 'Approved'\n" +
                        " and t8.state_name = 'Approved'\n"+
                        " and t1.X_identifier_S = N'" + codeName + "'";
                List<String[]> equipments = functions.getArrayDataFromActive(equClassSql);
                if (equipments.size() == 0) {
                    continue;
                }
                //查询消息模板子项
                String templateSubSql = "select t2.equipStatusModel_S from AT_LC_MessageTemplate t1" +
                        " join AT_LC_MessageTemplateSub t2 on t1.atr_key = t2.messageTemplate_64 " +
                        " where t1.messageId_S = N'" + id + "'";
                List<String[]> templateSubs = functions.getArrayDataFromActive(templateSubSql);
                if (templateSubs.size() == 0) {
                    continue;
                }
                for (String[] templateSub: templateSubs) {
                    String statusModel = templateSub[0];
                    for (String[] equ : equipments) {
                        String equId = equ[0];
                        String statusModelSql = "select t1.X_identifier_S ,t1.X_shortDescription_S ,t3.X_displayName_S," +
                                " t4.X_identifier_S," +
                                " CONVERT(varchar(20),t2.X_expiryDate_T,120),DATEDIFF(DAY,getdate(),t2.X_expiryDate_T)  " +
                                " from AT_X_S88Equipment t1" +
                                " left join AT_X_S88StatusGraphAssignment t2 on t1.atr_key = t2.X_equipmentEntity_64 " +
                                " left join AT_X_S88StatusGraphState t3 on t2.X_currentState_64 = t3.atr_key" +
                                " left join AT_X_S88StatusGraph t4 on t4.atr_key = t3.X_statusGraph_64 " +
                                " where t4.X_identifier_S = N'"+ statusModel + "'" +
                                " and t1.X_identifier_S = N'" + equId + "'" +
                                " and t2.X_expiryDate_T > getdate()"+
                                " and (t2.X_expiryDate_T is not null and dateadd(MINUTE, " + -minute + ",t2.X_expiryDate_T) <= getdate())";
                        List<String[]> expireStatusModels = functions.getArrayDataFromActive(statusModelSql);
                        if (expireStatusModels.size() == 0) {
                            continue;
                        }
                        String equipId = expireStatusModels.get(0)[0];
                        String shortDescription = expireStatusModels.get(0)[1];
                        String statusName = expireStatusModels.get(0)[2];
                        String statusModelName = expireStatusModels.get(0)[3];
                        String expiryDateStr = expireStatusModels.get(0)[4];
                        String expiryDateDay = expireStatusModels.get(0)[5];
                        String triggerObject = equipId + "，" + statusName + "，" + statusModelName ;

                        //查询消息提醒是否存在
                        String messageSql = "select * from  AT_LC_UserMessage where triggerObject_S = N'" + triggerObject + "'" +
                                " and planTime_T > getdate()";

                        //若已存在，则跳过
                        List<String[]> messages = functions.getArrayDataFromActive(messageSql);
                        if (messages.size() != 0) {
                            continue;
                        }

                        //拼接消息提醒
                        String message = getMessageStr("MessageInfo_equipmentExpirationMessage");
                        message = message.replace("{1}", equipId);
                        message = message.replace("{2}", shortDescription);
                        message = message.replace("{3}", statusModelName);
                        message = message.replace("{4}", expiryDateDay);
                        message = message.replace("{5}", customMessage == null ? "" :"," +  customMessage);

                        //转换创建时间
                        Timestamp expiryDate = Timestamp.valueOf(expiryDateStr);

                        //获取需提醒用户对象集合
                        List<User> users = getUsersByUsersStr(userStr);
                        List<User> groupUsers = getUsersByUserGroupsStr(userGroupStr);
                        List<User> allUser = ListUtils.sum(users, groupUsers);
                        for (User user :
                                allUser) {
                            saveUserMessage(id,templateType,user.getName(),codeName,triggerObject,
                                    expiryDate,message);
                        }

                    }
                }

            }else {
                String equClassStatusSql = "select t1.X_identifier_S ,t3.X_identifier_S \n" +
                        " from AT_X_S88EquipmentClass t1\n" +
                        " join AT_X_S88StatusGraphAssignment t2 on t1.atr_key = t2.X_equipmentClass_64 \n" +
                        " join AT_X_S88StatusGraph t3 on t3.atr_key = t2.X_statusGraph_64 " ;
                //查询所有设备类下的设备
                List<String[]> equipmentClassStatus = functions.getArrayDataFromActive(equClassStatusSql);
                if (equipmentClassStatus.size() == 0) {
                    continue;
                }
                for (String[] classStatus: equipmentClassStatus) {
                    String equipmentClassId = classStatus[0];
                    String statusModel = classStatus[1];
                    String equClassSql = "select t3.X_identifier_S from AT_X_S88EquipmentClass t1\n" +
                                " join AT_X_S88EquClass2Equipment t2 on t1.atr_key = t2.X_equipmentClass_64 \n" +
                                " join AT_X_S88Equipment t3 on t2.X_equipment_64 = t3.atr_key \n" +
                                " join OBJECT_STATE t4 on t3.X_stateProxy_231 = t4.object_key \n" +
                                " join STATE t5 on t4.state_key = t5.state_key \n" +
                                " join FSM t6 on t4.fsm_key = t6.fsm_key and t6.fsm_name = 'S88EquipmentEntityStatusGraph'\n" +
                                " join OBJECT_STATE t7 on t1.X_stateProxy_231 = t7.object_key \n" +
                                " join STATE t8 on t7.state_key = t8.state_key \n" +
                                " join FSM t9 on t8.fsm_key = t9.fsm_key and t9.fsm_name = 'S88EquipmentClassStatusGraph'\n" +
                                " where t5.state_name = 'Approved'\n" +
                                " and t8.state_name = 'Approved'\n"+
                                " and t1.X_identifier_S = N'" + equipmentClassId + "'";
                        List<String[]> equipments = functions.getArrayDataFromActive(equClassSql);
                        if (equipments.size() == 0) {
                            continue;
                        }
                    for (String[] equipment : equipments) {
                        String equId = equipment[0];
                        String statusModelSql = "select t1.X_identifier_S ,t1.X_shortDescription_S ,t3.X_displayName_S," +
                                " t4.X_identifier_S," +
                                " CONVERT(varchar(20),t2.X_expiryDate_T,120),DATEDIFF(DAY,getdate(),t2.X_expiryDate_T)  " +
                                " from AT_X_S88Equipment t1" +
                                " left join AT_X_S88StatusGraphAssignment t2 on t1.atr_key = t2.X_equipmentEntity_64 " +
                                " left join AT_X_S88StatusGraphState t3 on t2.X_currentState_64 = t3.atr_key" +
                                " left join AT_X_S88StatusGraph t4 on t4.atr_key = t3.X_statusGraph_64 " +
                                " where t4.X_identifier_S = N'"+ statusModel + "'" +
                                " and t1.X_identifier_S = N'" + equId + "'" +
                                " and t2.X_expiryDate_T > getdate()"+
                                " and (t2.X_expiryDate_T is not null and dateadd(MINUTE, " + -minute + ",t2.X_expiryDate_T) <= getdate())";
                        List<String[]> expireStatusModels = functions.getArrayDataFromActive(statusModelSql);
                        if (expireStatusModels.size() == 0) {
                            continue;
                        }
                        String equipId = expireStatusModels.get(0)[0];
                        String shortDescription = expireStatusModels.get(0)[1];
                        String statusName = expireStatusModels.get(0)[2];
                        String statusModelName = expireStatusModels.get(0)[3];
                        String expiryDateStr = expireStatusModels.get(0)[4];
                        String expiryDateDay = expireStatusModels.get(0)[5];
                        String triggerObject = equipId + "，" + statusName + "，" + statusModelName ;

                        //查询消息提醒是否存在
                        String messageSql = "select * from  AT_LC_UserMessage where triggerObject_S = N'" + triggerObject + "'" +
                                " and planTime_T > getdate()";

                        //若已存在，则跳过
                        List<String[]> messages = functions.getArrayDataFromActive(messageSql);
                        if (messages.size() != 0) {
                            continue;
                        }

                        //拼接消息提醒
                        String message = getMessageStr("MessageInfo_equipmentExpirationMessage");
                        message = message.replace("{1}", equipId);
                        message = message.replace("{2}", shortDescription);
                        message = message.replace("{3}", statusModelName);
                        message = message.replace("{4}", expiryDateDay);
                        message = message.replace("{5}", customMessage == null ? "" :"," +  customMessage);

                        //转换创建时间
                        Timestamp expiryDate = Timestamp.valueOf(expiryDateStr);

                        //获取需提醒用户对象集合
                        List<User> users = getUsersByUsersStr(userStr);
                        List<User> groupUsers = getUsersByUserGroupsStr(userGroupStr);
                        List<User> allUser = ListUtils.sum(users, groupUsers);
                        for (User user :
                                allUser) {
                            saveUserMessage(id,templateType,user.getName(),codeName,triggerObject,
                                    expiryDate,message);
                        }

                    }

                }

            }
        }
    }


    /**
     * 保存用户消息提醒
     * @param messageId 模板ID
     * @param messageType 模板类型
     * @param receiveUser 通知用户
     * @param messageCode 消息代码
     * @param planTime 触发业务时间
     * @param messageContent 消息提醒内容
     */
    public static void saveUserMessage(String messageId, String messageType, String receiveUser,
                                        String messageCode,String triggerObject, Timestamp planTime, String messageContent){
        ATHandler atHandler = functions.createATHandler("LC_UserMessage");
        ATRow atRow = atHandler.createATRow();
        atRow.setValue("messageId", messageId);
        atRow.setValue("messageType", messageType);
        atRow.setValue("receiveUser", receiveUser);
        atRow.setValue("messageCode", messageCode);
        atRow.setValue("triggerObject", triggerObject);
        atRow.setValue("planTime", planTime);
        atRow.setValue("state", "未读");
        atRow.setValue("messageContent", messageContent);
        atRow.save(new Time(), "", null);
    }

    /**
     * 通过用户文本获取用户集合
     *
     * @param usersStr 用户文本
     * @return 用户集合
     */
    public static List<User> getUsersByUsersStr(String usersStr) {
        List<User> users = new ArrayList<>();
        if (isEmptyStr(usersStr)) {
            return users;
        }
        String[] userDatas = usersStr.split(",");
        for (String userData :
                userDatas) {
            String userStr = userData.substring(1, userData.length() - 1);
            User user = functions.getUser(userStr);
            users.add(user);
        }
        return users;
    }

    /**
     * 通过用户组文本获取用户集合
     *
     * @param userGroupsStr 用户组文本
     * @return 用户集合
     */
    public static List<User> getUsersByUserGroupsStr(String userGroupsStr) {
        List<User> users = new ArrayList<>();
        if (isEmptyStr(userGroupsStr)) {
            return users;
        }
        String[] userGroupDatas = userGroupsStr.split(",");
        for (String userGroupData :
                userGroupDatas) {
            String userGroupStr = userGroupData.substring(1, userGroupData.length() - 1);
            UserGroup userGroup = functions.getUserGroupByName(userGroupStr);
            List<User> groupUsers = userGroup.getUsers();
            for (User user :
                    groupUsers) {
                if (users.contains(user)) {
                    continue;
                }
                users.add(user);
            }
        }
        return users;
    }

    /**
     * 判断字符串是否为空指针或者空值
     * 为空返回true，不为空返回false
     *
     * @param: str 字符串
     * @return: boolean true或者false
     */
    public static boolean isEmptyStr(String str) {
        boolean result = false;
        if (str == null || str.trim().length() < 1) {
            result = true;
        }
        return result;
    }

    /**
     * 获取异常等级对应值
     * @param riskLevel 异常等级文本
     * @return 值
     */
    private static int getExceptionRiskValue(String riskLevel){
        if("High".equals(riskLevel)){
            return 30;
        }else if ("Medium".equals(riskLevel)){
            return 20;
        }else if ("Low".equals(riskLevel)){
            return 10;
        }else if ("None".equals(riskLevel)) {
            return 0;
        }else {
            return 100;
        }
    }

    /**
     * 获取异常记录来源
     * @param ctxName 异常对象类型
     * @param ctxKey 异常对象Key
     * @return 异常来源文本
     */
    private static String getExceptionRoute(String ctxName,String ctxKey){
        //Phase类型对象来源查询
        if("ATRow#X_RtPhase".equals(ctxName)){
            String querySql = "select t7.order_item_name + '\\' + t10.X_unitProcedureName_S + '\\' + t9.X_operationName_S  " +
                    " + '\\' +  t8.X_phaseName_S as route" +
                    " from  AT_X_ExceptionObjectRelation t1" +
                    " join AT_X_RtPhase t2 on t2.atr_key = t1.X_ctxKey_I" +
                    " join AT_X_RtOperation t3 on t3.atr_key = t2.X_parent_64" +
                    " join AT_X_RtUnitProcedure t4 on t4.atr_key = t3.X_parent_64" +
                    " join AT_X_RtProcedure t5 on t5.atr_key = t4.X_parent_64" +
                    " join CONTROL_RECIPE t6 on t5.X_controlRecipe_113 = t6.control_recipe_key" +
                    " join PROCESS_ORDER_ITEM t7 on t7.order_item_key = t6.order_item_key" +
                    " join AT_X_Phase t8 on t2.X_phase_64 = t8.atr_key \n" +
                    " join AT_X_Operation t9 on t3.X_operation_64 = t9.atr_key \n" +
                    " join AT_X_UnitProcedure t10 on t4.X_unitProcedure_64 = t10.atr_key " +
                    " where t1.X_ctxName_S = '"+ ctxName + "'" +
                    " and t1.X_ctxKey_I = " + ctxKey ;

            List<String[]> routes = functions.getArrayDataFromActive(querySql);
            if (routes.size() == 0) {
                return "";
            }
            return routes.get(0)[0];
        }else if("ATRow#X_RtOperation".equals(ctxName)){
            //操作类型对象来源查询
            String querySql = "select t7.order_item_name + '\\' + t10.X_unitProcedureName_S + '\\' + t9.X_operationName_S as route" +
                    " from  AT_X_ExceptionObjectRelation t1" +
                    " join AT_X_RtOperation t3 on t3.atr_key = t1.X_ctxKey_I" +
                    " join AT_X_RtUnitProcedure t4 on t4.atr_key = t3.X_parent_64" +
                    " join AT_X_RtProcedure t5 on t5.atr_key = t4.X_parent_64" +
                    " join CONTROL_RECIPE t6 on t5.X_controlRecipe_113 = t6.control_recipe_key" +
                    " join PROCESS_ORDER_ITEM t7 on t7.order_item_key = t6.order_item_key" +
                    " join AT_X_Operation t9 on t3.X_operation_64 = t9.atr_key \n" +
                    " join AT_X_UnitProcedure t10 on t4.X_unitProcedure_64 = t10.atr_key " +
                    " where t1.X_ctxName_S = '"+ ctxName + "'" +
                    " and t1.X_ctxKey_I = " + ctxKey ;

            List<String[]> routes = functions.getArrayDataFromActive(querySql);
            if (routes.size() == 0) {
                return "";
            }
            return routes.get(0)[0];
        }else if("ATRow#X_RtOperation".equals(ctxName)){
            //单位工序类型对象来源查询
            String querySql = "select t7.order_item_name + '\\' + t10.X_unitProcedureName_S as route" +
                    " from  AT_X_ExceptionObjectRelation t1" +
                    " join AT_X_RtUnitProcedure t4 on t4.atr_key = t1.X_ctxKey_I" +
                    " join AT_X_RtProcedure t5 on t5.atr_key = t4.X_parent_64" +
                    " join CONTROL_RECIPE t6 on t5.X_controlRecipe_113 = t6.control_recipe_key" +
                    " join PROCESS_ORDER_ITEM t7 on t7.order_item_key = t6.order_item_key" +
                    " join AT_X_UnitProcedure t10 on t4.X_unitProcedure_64 = t10.atr_key " +
                    " where t1.X_ctxName_S = '"+ ctxName + "'" +
                    " and t1.X_ctxKey_I = " + ctxKey ;

            List<String[]> routes = functions.getArrayDataFromActive(querySql);
            if (routes.size() == 0) {
                return "";
            }
            return routes.get(0)[0];
        }
        return "";

    }

    /**
     * 获取message的值
     *
     * @param megId message ID
     * @return message value
     */
    public static String getMessageStr(String megId) {
        return functions.getMessage("LC_MessageTemplate", megId);
    }

    /**
     * 获取消息模板的值
     * @param type 模板类型
     * @return 模板对象集合
     */
    public static List<String[]> getMessageTemplates(int type) {
        String sql = "SELECT messageId_S ,codeName_S ,customMessage_S ,userGroup_S ,user_S ,criticalTime_V \n" +
                ",templateType_I FROM AT_LC_MessageTemplate where templateType_I = " + type + " and status_Y = 1";
        List<String[]> messageTemplates = functions.getArrayDataFromActive(sql);
        return messageTemplates;
    }
}
