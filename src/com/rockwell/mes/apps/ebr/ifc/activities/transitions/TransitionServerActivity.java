package com.rockwell.mes.apps.ebr.ifc.activities.transitions;

import com.rockwell.mes.commons.base.ifc.activityset.*;
import com.rockwell.mes.commons.base.ifc.jmx.transition.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import com.rockwell.mes.commons.base.ifc.objectlock.*;
import com.rockwell.mes.apps.bulkchanges.ifc.*;
import com.rockwell.mes.services.order.ifc.messaging.*;
import com.rockwell.mes.commons.messaging.ifc.serverheartbeat.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.*;
import org.apache.log4j.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import com.rockwell.mes.services.inventory.ifc.*;
import com.rockwell.mes.commons.versioning.ifc.*;
import com.rockwell.mes.apps.recipeeditor.ifc.hierarchy.persistence.*;
import com.datasweep.compatibility.client.*;
import com.datasweep.compatibility.ui.Time;
import com.ibm.icu.text.SimpleDateFormat;
import com.leateck.model.userProcessPack.IMESLCUserProcessPack;
import com.leateck.model.userProcessPack.MESLCUserProcessPackFilter;
import com.rockwell.mes.services.s88.ifc.*;
import com.rockwell.mes.services.usernotifications.ifc.*;
import org.apache.commons.logging.*;

public class TransitionServerActivity extends AbstractBaseActivity {
    private static final Log LOGGER;

    private static TransitionServerActivity instance;

    private static final int INITIAL_VALUE = -1;

    private static void setInstance(final TransitionServerActivity theInstance) {
        TransitionServerActivity.instance = theInstance;
    }

    public static TransitionServerActivity getInstance() {
        return TransitionServerActivity.instance;
    }

    private static void checkInstance() {
        if (TransitionServerActivity.instance != null) {
            throw new IllegalStateException("Transition server startup not allowed because it is already started.");
        }
    }

    public void startup() {
        checkInstance();
        setInstance(this);
        PCContext.setEnvironment(this.environment);
        final TransitionServerMethods[] values = TransitionServerMethods.values();
        for (int length = values.length, i = 0; i < length; ++i) {
            TransitionStatJMXAgent.INSTANCE.registerMethod(values[i].getMethodName());
        }
        ((IMESObjectLockService) ServiceFactory.getService((Class) IMESObjectLockService.class))
                .setClientType(PharmaSuiteClientType.TRANSITION_SERVER.getChoiceElement());
        S88EquipmentEntityExpiryDateTransitionEnforcer.INSTANCE.startup();
        BulkChangeRequestExecutionEngine.INSTANCE.startup();
        OrderStatusTransitionController.INSTANCE.startup();
        ServerHeartbeatProducer.INSTANCE.startup();
        TransitionServerActivity.LOGGER.info((Object) "Transition server started.");
    }

    public void shutdown() {
        S88EquipmentEntityExpiryDateTransitionEnforcer.INSTANCE.shutdown();
        BulkChangeRequestExecutionEngine.INSTANCE.shutdown();
        OrderStatusTransitionController.INSTANCE.shutdown();
        ServerHeartbeatProducer.INSTANCE.shutdown();
        TransitionStatJMXAgent.INSTANCE.shutdown();
        setInstance(null);
        TransitionServerActivity.LOGGER.info((Object) "Transition server shutdown.");
    }

    public int triggerBatchStatusUpdate() {
        final AtomicInteger count = new AtomicInteger(-1);
        try (final AutoTimeMeasurementUtility autoTimeMeasurementUtility = new AutoTimeMeasurementUtility(
                () -> this.log("triggerBatchStatusUpdate()", "batches updated", count), TransitionServerActivity.LOGGER, Level.DEBUG)) {
            count.set(((IBatchService) ServiceFactory.getService((Class) IBatchService.class)).triggerBatchStatusUpdate());
            return count.get();
        }
    }

    public int triggerVersionStatusUpdate() {
        final AtomicInteger count = new AtomicInteger(-1);
        // 工艺包有效期校验
        checkExpiry();
        try (final AutoTimeMeasurementUtility autoTimeMeasurementUtility = new AutoTimeMeasurementUtility(
                () -> this.log("triggerVersionStatusUpdate", "master recipes updated", count), TransitionServerActivity.LOGGER, Level.DEBUG)) {
            count.set(((IVersioningService) ServiceFactory.getService((Class) IVersioningService.class)).triggerVersionStatusUpdate());
            return count.get();
        }
    }

    /**
     * 有效期校验
     * 
     * @Description
     * @return
     *
     * @author:HT
     * @create:下午5:08:23
     */
    @SuppressWarnings("deprecation")
    public void checkExpiry() {

        // 同步工艺包失效期和授权表的失效期

        MESLCUserProcessPackFilter filter = new MESLCUserProcessPackFilter();
        List<IMESLCUserProcessPack> userProcessPackList = filter.getFilteredObjects();
        long packKey = 0;

        for (int i = 0; i < userProcessPackList.size(); i++) {
            packKey = userProcessPackList.get(i).getKey();

            String packExpiryTime = timeToString(userProcessPackList.get(i).getExpiryTime());
            String sql = "SELECT CONVERT(varchar(100),expiryTime,23),expiryTime FROM PROCESS_PACK_RIGHTS_MANAGEMENT where processPackKey = '"
                    + packKey + "' ";
            Vector queryVector = PCContext.getFunctions().getArrayDataFromActive(sql);
            if (queryVector != null && queryVector.size() > 0) {

                String[] queryConditions = (String[]) queryVector.get(0);
                String expiry = queryConditions[0];
                String expiryString = queryConditions[1];
                // 如果时间不同步，就将授权表时间同步上去
                if (!expiry.equals(packExpiryTime)) {

                    Time newExpiry = PCContext.getFunctions().createTime(expiryString);
                    userProcessPackList.get(i).setExpiryTime(newExpiry);
                    userProcessPackList.get(i).save(PCContext.getFunctions().getDBTime(), "", null);
                }
            }
        }

        // 当前时间大于授权表的失效期，将工艺包设为失效状态-50
        String sqlAll = "SELECT processPackKey,CONVERT(varchar(100),expiryTime,120) FROM PROCESS_PACK_RIGHTS_MANAGEMENT";
        Vector queryVector = PCContext.getFunctions().getArrayDataFromActive(sqlAll);
        if (queryVector != null && queryVector.size() > 0) {
            String[] queryConditions = (String[]) queryVector.get(0);

            String key = queryConditions[0];
            String expiryTime = queryConditions[1];

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date expiryDate = sdf.parse(expiryTime);

                int number = expiryDate.compareTo(new Date());

                if (number != 1) {
                    MESLCUserProcessPackFilter filterUpdate = new MESLCUserProcessPackFilter();

                    filterUpdate.forATRowKeyEqualTo(Long.parseLong(key));
                    List<IMESLCUserProcessPack> userProcessPackUpdate = filterUpdate.getFilteredObjects();

                    if (userProcessPackUpdate != null && userProcessPackUpdate.size() > 0) {

                        for (int j = 0; j < userProcessPackUpdate.size(); j++) {
                            userProcessPackUpdate.get(j).setProcessPackState(Long.parseLong("50"));

                            userProcessPackUpdate.get(j).save(PCContext.getFunctions().getDBTime(), "", null);
                        }
                    }
                }

            } catch (ParseException e) {

                e.printStackTrace();
            }
        }
    }

    /**
     * time转string
     * 
     * @Description
     * @param time
     * @return
     *
     * @author:HT
     * @create:上午10:46:41
     */
    public String timeToString(Time time) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = time.getCalendar().getTime();
        str = sdf.format(date);

        return str;

    }

    public int deleteObsoleteExpandedRepresentations() throws DatasweepException {
        final AtomicInteger count = new AtomicInteger(-1);
        try (final AutoTimeMeasurementUtility autoTimeMeasurementUtility =
                new AutoTimeMeasurementUtility(() -> this.log("deleteObsoleteExpandedRepresentations", "expanded representations deleted", count),
                        TransitionServerActivity.LOGGER, Level.DEBUG)) {
            count.set(((IRecipePersistenceService) ServiceFactory.getService((Class) IRecipePersistenceService.class))
                    .deleteObsoleteExpandedRepresentations());
            return count.get();
        }
    }

    public int deleteDetachedControlRecipeStructures() throws DatasweepException {
        final AtomicInteger count = new AtomicInteger(-1);
        try (final AutoTimeMeasurementUtility autoTimeMeasurementUtility =
                new AutoTimeMeasurementUtility(() -> this.log("deleteDetachedControlRecipeStructures", "control recipe structures deleted", count),
                        TransitionServerActivity.LOGGER, Level.DEBUG)) {
            count.set(((IS88RecipeService) ServiceFactory.getService((Class) IS88RecipeService.class)).deleteDetachedControlRecipeStructures());
            return count.get();
        }
    }

    public int deleteObsoleteUserNotifications() throws DatasweepException {
        final AtomicInteger count = new AtomicInteger(-1);
        try (final AutoTimeMeasurementUtility autoTimeMeasurementUtility =
                new AutoTimeMeasurementUtility(() -> this.log("deleteObsoleteUserNotifications", "user notifications and settings deleted", count),
                        TransitionServerActivity.LOGGER, Level.DEBUG)) {
            count.set(((IUserNotificationsService) ServiceFactory.getService((Class) IUserNotificationsService.class)).purgeUserNotifications());
            return count.get();
        }
    }

    private String log(final String methodName, final String infoName, final AtomicInteger count) {
        if (count.get() == -1) {
            return methodName;
        }
        return String.format("%s() %s.", count.get(), infoName);
    }

    static {
        LOGGER = LogFactory.getLog((Class) TransitionServerActivity.class);
    }
}
