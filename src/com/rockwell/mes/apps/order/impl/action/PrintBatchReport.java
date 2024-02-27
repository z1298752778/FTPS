package com.rockwell.mes.apps.order.impl.action;

import com.datasweep.compatibility.client.Batch;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.ProcessOrderItem;
import com.datasweep.compatibility.client.User;
import com.leateck.utility.UserMessageUtility;
import com.rockwell.mes.apps.order.ifc.action.AbstractBatchReportAction;
import com.rockwell.mes.apps.order.ifc.model.IOrderModel;
import com.rockwell.mes.commons.base.ifc.services.IFunctionsEx;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.services.batchreport.ifc.IMESReport;
import org.apache.commons.collections.ListUtils;

import java.util.List;

public class PrintBatchReport extends AbstractBatchReportAction {

    private static final long serialVersionUID = 1L;
    //平台function
    private static IFunctionsEx functions = PCContext.getFunctions();

    public PrintBatchReport() {
        super(true, false);
    }

    protected void executeBatchReportAction(IOrderModel iOrderModel, String string, boolean bl, boolean bl2)
            throws Exception {
        ProcessOrderItem processOrderItem = iOrderModel.getProcessOrderItem();
        IMESReport iMESReport = this.createBatchReport(processOrderItem, string, bl);
        this.showBatchReportPreview(iOrderModel, iMESReport, bl2);
        iMESReport.destruct();
        PrintBatchReportMessage(processOrderItem);
    }
    /**
     * 打印批记录消息提醒
     */
    public static void PrintBatchReportMessage(ProcessOrderItem processOrderItem) throws DatasweepException {
        //查询打印批记录任务模板
        List<String[]>messageTemplates = UserMessageUtility.getMessageTemplates(UserMessageUtility.REPORT_PRINTING);
        if (messageTemplates.size() == 0) {
            return;
        }
        String printBatch = ((Batch)processOrderItem.getUDA("X_batch")).getName();
        String printOrder = processOrderItem.getOrderName();
        String printUser = functions.getCurrentUser().getName();
        //执行模板任务
        for (String[] messageTemplate : messageTemplates) {
            String id = messageTemplate[0];
            String codeName = messageTemplate[1];
            String customMessage = messageTemplate[2];
            String userGroupStr = messageTemplate[3];
            String userStr = messageTemplate[4];
            String templateType = messageTemplate[6];
            //查询工单
            String orderSql = "SELECT * from PROCESSORDERITEM_UV t1 \n" +
                    "join CONTROL_RECIPE t4 on t1.order_item_key = t4.order_item_key \n" +
                    "join MASTER_RECIPE t5 on t4.master_recipe_key = t5.master_recipe_key " +
                    "where t1.order_item_name = N'" + printOrder + "'";
            //添加处方对象条件
            if (codeName != null) {
                orderSql += "and t5.master_recipe_name = N'" + codeName + "'";
            }
            List<String[]> orderInfos = functions.getArrayDataFromActive(orderSql);
            if (orderInfos.size() == 0) {
                continue;
            }

            //拼接消息提醒
            String message = UserMessageUtility.getMessageStr("MessageInfo_printBatchReportMessage");
            message = message.replace("{1}", printUser);
            message = message.replace("{2}", printOrder);
            message = message.replace("{3}", printBatch);
            message = message.replace("{4}", customMessage == null ? "" :"," +  customMessage);
            String triggerObject = printOrder + "-" + printUser;
            //获取需提醒用户对象集合
            List<User> users = UserMessageUtility.getUsersByUsersStr(userStr);
            List<User> groupUsers = UserMessageUtility.getUsersByUserGroupsStr(userGroupStr);
            List<User> allUser = ListUtils.sum(users, groupUsers);
            for (User user : allUser) {
                //查询是否已存在消息
                String messageSql = "select * from  AT_LC_UserMessage where receiveUser_S = N'" + user + "'" +
                        " and messageContent_S = N'" + message + "'";
                List<String[]> messages = functions.getArrayDataFromActive(messageSql);
                if (messages.size() == 0) {
                    UserMessageUtility.saveUserMessage(id,templateType,user.getName(),codeName,triggerObject,
                            null,message);
                }
            }

        }

    }
}

