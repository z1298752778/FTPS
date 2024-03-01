package com.rockwell.mes.apps.masterdata.impl.data;

import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.Error;
import com.leateck.model.userProcessPack.IMESLCUserProcessPack;
import com.leateck.model.userProcessPack.MESLCUserProcessPackFilter;
import com.rockwell.mes.apps.masterdata.ifc.data.IDataHandler;
import com.rockwell.mes.apps.masterdata.ifc.data.ILoadMasterDataProxiesContext;
import com.rockwell.mes.apps.masterdata.ifc.data.IMasterDataHolder;
import com.rockwell.mes.apps.masterdata.ifc.data.IMasterDataObjectType;
import com.rockwell.mes.apps.masterdata.ifc.data.IMasterDataProxy;
import com.rockwell.mes.apps.masterdata.impl.controller.MDTEditorMasterController;
import com.rockwell.mes.commons.base.ifc.exceptions.MESRuntimeException;
import com.rockwell.mes.commons.base.ifc.objects.IPersistentMESObject;
import com.rockwell.mes.commons.base.ifc.objects.MESObjectDeletedCheckedException;
import com.rockwell.mes.commons.base.ifc.sql.DataBaseUtility;
import com.rockwell.mes.commons.base.ifc.sql.ISqlStatement;
import com.rockwell.mes.commons.base.ifc.utility.AppConfigurationUtility;
import com.rockwell.mes.commons.base.ifc.utility.CollectionUtility;
import com.rockwell.mes.commons.base.ifc.utility.TimeMeasurementUtility;
import com.rockwell.mes.services.s88equipment.impl.statusgraph.MESS88StatusGraph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.UIManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractMasterDataHandler<HolderType extends IMasterDataHolder, MasterDataBackendType extends IPersistentMESObject, ProxyType extends IMasterDataProxy>
        implements IDataHandler<HolderType, MasterDataBackendType, ProxyType> {
    private static final Log LOGGER = LogFactory.getLog(AbstractMasterDataHandler.class);

    private ISqlStatement sqlStatementToLoadProxy;

    public static final String OBJECT_IS_BEING_REFERENCED_ERROR_MSG = "ObjectIsBeingReferenced_ErrorMsg";

    public static final String OBJECT_DELETED_ERROR_MSG = "ObjectDeleted_ErrorMsg";

    public static final Color DEFAULT_FONT_COLOR = UIManager.getColor("RA-TileableComponent.text.color");

    private static final String STATE_ARCHIVED = "Archived";

    public static final String HIDE_ARCHIVED_OBJECTS_WHERE_CLAUSE = "state_name <> 'Archived'";

    private final MasterDataHolderIdentityMap masterDataHolderIdentityMap =
            new MasterDataHolderIdentityMap();

    protected abstract MasterDataBackendType getBackendObjectByKey(long var1);

    protected abstract HolderType buildDataHolder(MasterDataBackendType var1);

    public AbstractMasterDataHandler(ISqlStatement iSqlStatement) {
        this.sqlStatementToLoadProxy = iSqlStatement;
    }

    protected long countDataOnDB(String string) {
        String string2 = this.getAdditionalWhereClause(string);
        long l = this.sqlStatementToLoadProxy.countWithWhere(string2);
        return l;
    }

    protected Collection<ProxyType> loadAllDataFromDB(String string, ILoadMasterDataProxiesContext iLoadMasterDataProxiesContext) {
        TimeMeasurementUtility timeMeasurementUtility = null;
        TimeMeasurementUtility timeMeasurementUtility2 = null;
        if (LOGGER.isDebugEnabled()) {
            timeMeasurementUtility = new TimeMeasurementUtility();
            timeMeasurementUtility.start();
        }
        String string2 = this.getAdditionalWhereClause(string);
        if (LOGGER.isDebugEnabled()) {
            timeMeasurementUtility2 = new TimeMeasurementUtility();
            timeMeasurementUtility2.start();
        }
        List list = this.sqlStatementToLoadProxy.executeWithWhere(string2);
        if (LOGGER.isDebugEnabled()) {
            timeMeasurementUtility2.stop();
            LOGGER.debug((Object) ("Executing SQL statement for object type " + this.getObjectTypeOfHandledObjects().getName() + " took "
                    + timeMeasurementUtility2.deltaAsMilliSeconds() + " ms / " + timeMeasurementUtility2.deltaAsSeconds() + " s."));
        }

        ArrayList arrayList = new ArrayList();
        Iterator localIterator = list.iterator();
        while (localIterator.hasNext()) {
            String[] arrayOfString = (String[]) localIterator.next();
            IMasterDataProxy localIMasterDataProxy = createProxy(arrayOfString, iLoadMasterDataProxiesContext);
            arrayList.add(localIMasterDataProxy);
        }
        
        
        // ArrayList<ProxyType> arrayList = new ArrayList<ProxyType>();
        // for (String[] arrstring : list) {
        // ProxyType ProxyType = this.createProxy(arrstring, iLoadMasterDataProxiesContext);
        // arrayList.add(ProxyType);
        // }
        
        if (LOGGER.isDebugEnabled()) {
            timeMeasurementUtility.stop();
            LOGGER.debug((Object) ("Initializing data for object type " + this.getObjectTypeOfHandledObjects().getName() + " took "
                    + timeMeasurementUtility.deltaAsMilliSeconds() + " ms / " + timeMeasurementUtility.deltaAsSeconds() + " s."));
        }
        return arrayList;
    }

    /**
     * 条件where条件方法
     */
    private String getAdditionalWhereClause(String string) {
        String string2;
        boolean bl = MDTEditorMasterController.getInstance().isHideArchivedObjects(this.getObjectTypeOfHandledObjects());
        String typeName = getObjectTypeOfHandledObjects().getName();

        // 过滤出状态为启用得工艺包
        MESLCUserProcessPackFilter userPackFilter = new MESLCUserProcessPackFilter();
        String processPackList = "";
        try {
            userPackFilter.forProcessPackStateEqualTo(Long.parseLong("20"));
            List<IMESLCUserProcessPack> packList = userPackFilter.getFilteredObjects();

            for (int i = 0; i <= packList.size() - 1; i++) {

                if (packList.size() - 1 == i) {
                    processPackList += "'" + packList.get(i).getKey() + "'";
                } else {
                    processPackList += "'" + packList.get(i).getKey() + "',";
                }
            }

            if ("".equals(processPackList)) {
                processPackList = "''";
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (DatasweepException e) {
            e.printStackTrace();
        }

        String string3 = "";
        if (bl) {
            string3 = string3 + "state_name <> 'Archived'";

        }
        if (bl && !"EqmClass".equals(typeName) && !"EqmTemplateEntity".equals(typeName)) {

            string3 = string3 + " AND ((isProcessPack_Y = 1 and processPack_64 in(" + processPackList
                    + ") ) or (isProcessPack_Y is null and processPack_64 is null ))";
        }

        if (!StringUtils.isEmpty((CharSequence) (string2 = AppConfigurationUtility.getFilterPrefix()))) {
            if (!StringUtils.isEmpty((CharSequence) string3)) {
                string3 = string3 + " AND ";
            }
            string3 = string3 + string + " like '" + string2 + "%'";
        }

        MESS88StatusGraph DE = new MESS88StatusGraph();
        return string3;
    }

    protected abstract ProxyType createProxy(String[] var1, ILoadMasterDataProxiesContext var2);

    public HolderType getDataHolder(long l) throws MESObjectDeletedCheckedException {
        HolderType HolderType = this.masterDataHolderIdentityMap.getHolderWithKey(l);
        if (HolderType == null) {
            MasterDataBackendType MasterDataBackendType;
            try {
                MasterDataBackendType = this.getBackendObjectByKey(l);
            } catch (MESRuntimeException mESRuntimeException) {
                MasterDataBackendType = null;
            }
            if (MasterDataBackendType == null) {
                throw new MESObjectDeletedCheckedException("Master data holder with key '" + l + "' was already deleted.");
            }
            HolderType = this.getDataHolder(MasterDataBackendType);
        }
        return HolderType;
    }

    public HolderType getDataHolder(MasterDataBackendType MasterDataBackendType) {
        HolderType HolderType = this.getOrBuildDataHolder(MasterDataBackendType);
        return HolderType;
    }

    private HolderType getOrBuildDataHolder(MasterDataBackendType MasterDataBackendType) {
        HolderType HolderType;
        long l = MasterDataBackendType.getKey();
        if (this.masterDataHolderIdentityMap.containsHolderWithKey(l)) {
            HolderType = this.masterDataHolderIdentityMap.getHolderWithKey(l);
        } else {
            HolderType = this.buildDataHolder(MasterDataBackendType);
            this.masterDataHolderIdentityMap.put(HolderType);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object) ("New data holder added: " + HolderType));
            }
        }
        return HolderType;
    }

    public void cleanUpDataHolderCaches() {
        Collection<HolderType> collection;
        AbstractMasterDataHandler abstractMasterDataHandler;
        HashSet<IMasterDataObjectType> hashSet = new HashSet<IMasterDataObjectType>();
        this.determineAllRelevantObjectTypes(hashSet, this.getObjectTypeOfHandledObjects());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug((Object) ("allObjectTypes of " + (Object) this.getObjectTypeOfHandledObjects() + " = " + hashSet));
        }
        HashSet<IMasterDataHolder> hashSet2 = new HashSet<IMasterDataHolder>();
        for (IMasterDataObjectType iMasterDataObjectType : hashSet) {
            abstractMasterDataHandler = (AbstractMasterDataHandler) iMasterDataObjectType.getDataHandler();
            collection = abstractMasterDataHandler.getCachedMasterDataHolders();
            for (IMasterDataHolder iMasterDataHolder : collection) {
                if (iMasterDataHolder.wasDestructed() || !iMasterDataHolder.isCurrentlyEdited())
                    continue;
                this.rememberCachedDataHolderIncludingSubtree(hashSet2, iMasterDataHolder);
            }
        }
        for (IMasterDataObjectType iMasterDataObjectType : hashSet) {
            abstractMasterDataHandler = (AbstractMasterDataHandler) iMasterDataObjectType.getDataHandler();
            collection = abstractMasterDataHandler.getCachedMasterDataHolders();
            for (IMasterDataHolder iMasterDataHolder : collection) {
                if (iMasterDataHolder.wasDestructed() || hashSet2.contains((Object) iMasterDataHolder))
                    continue;
                ((AbstractMasterDataHandler) iMasterDataHolder.getObjectType().getDataHandler())
                        .removeDataHolderFromCacheInternally(iMasterDataHolder);
            }
        }
    }

    private void determineAllRelevantObjectTypes(Set<IMasterDataObjectType> set, IMasterDataObjectType iMasterDataObjectType) {
        if (set.contains((Object) iMasterDataObjectType)) {
            return;
        }
        set.add(iMasterDataObjectType);
        for (IMasterDataObjectType iMasterDataObjectType2 : iMasterDataObjectType.getReferencingObjectTypes()) {
            this.determineAllRelevantObjectTypes(set, iMasterDataObjectType2);
        }
    }

    private void rememberCachedDataHolderIncludingSubtree(Set<IMasterDataHolder> set, IMasterDataHolder iMasterDataHolder) {
        if (iMasterDataHolder.wasDestructed()) {
            return;
        }
        if (set.contains((Object) iMasterDataHolder)) {
            return;
        }
        set.add(iMasterDataHolder);
        // Collection collection = iMasterDataHolder.getCurrentlyReferencedDataHolders();
        // for (IMasterDataHolder iMasterDataHolder2 : collection) {
        // this.rememberCachedDataHolderIncludingSubtree(set, iMasterDataHolder2);
        // }

        Collection localCollection = iMasterDataHolder.getCurrentlyReferencedDataHolders();
        Iterator localIterator = localCollection.iterator();
        while (localIterator.hasNext()) {
            IMasterDataHolder localIMasterDataHolder = (IMasterDataHolder) localIterator.next();
            rememberCachedDataHolderIncludingSubtree(set, localIMasterDataHolder);
        }
    }

    private void removeDataHolderFromCacheInternally(IMasterDataHolder iMasterDataHolder) {
        if (this.masterDataHolderIdentityMap.contains(iMasterDataHolder)) {
            iMasterDataHolder.destruct();
            LOGGER.debug((Object) ("Removed data holder '" + iMasterDataHolder.getIdentifier() + "'."));
            this.masterDataHolderIdentityMap.remove(iMasterDataHolder);
        } else {
            LOGGER.debug((Object) ("Data holder '" + iMasterDataHolder.getIdentifier() + "'was already removed from cache."));
        }
    }

    private Collection<HolderType> getCachedMasterDataHolders() {
        return this.masterDataHolderIdentityMap.getAll();
    }

    public boolean isDataHolderWithKeyInCache(long l) {
        return this.masterDataHolderIdentityMap.containsHolderWithKey(l);
    }

    public void replaceDataHolderInCache(HolderType HolderType, long l) {
        this.masterDataHolderIdentityMap.removeHolderWithKey(l);
        this.masterDataHolderIdentityMap.put(HolderType);
    }

    protected boolean isObjectBeingReferencedViolation(String string) {
        boolean bl = "0213".equals(string);
        return bl;
    }

    protected String getUniqueConstraintErrorMessageID(Error error, String string, String string2) {
        boolean bl = DataBaseUtility.isUniqueConstraintViolation((Error) error, (String) string);
        if (!bl) {
            return null;
        }
        return string2;
    }

    protected String getConstraintErrorMessageID(Error error, String string) {
        return this.getUniqueConstraintErrorMessageID(error, string, "identifierNotUnique_ErrorMsg");
    }

    protected String getObjectIsReferencedErrorMessageID(Error error) {
        boolean bl = this.isObjectBeingReferencedViolation(error.getMessageId());
        if (!bl) {
            return null;
        }
        return "ObjectIsBeingReferenced_ErrorMsg";
    }

    public void setSQLStatement(ISqlStatement iSqlStatement) {
        this.sqlStatementToLoadProxy = iSqlStatement;
    }

    public ISqlStatement getSQLStatement() {
        return this.sqlStatementToLoadProxy;
    }

    public class MasterDataHolderIdentityMap {
        private final Map<Long, HolderType> masterDataHolders = new HashMap<Long, HolderType>();

        public boolean containsHolderWithKey(long l) {
            return this.masterDataHolders.containsKey(l);
        }

        public boolean contains(IMasterDataHolder iMasterDataHolder) {
            long l = iMasterDataHolder.getDBKey();
            return this.masterDataHolders.containsKey(l);
        }

        public void put(HolderType HolderType) {
            long l = HolderType.getDBKey();
            this.masterDataHolders.put(l, HolderType);
        }

        public HolderType getHolderWithKey(long l) {
            return (HolderType) ((IMasterDataHolder) this.masterDataHolders.get(l));
        }

        public Collection<HolderType> getAll() {
            return CollectionUtility.createUnmodifiableCollection(this.masterDataHolders.values());
        }

        public boolean remove(IMasterDataHolder iMasterDataHolder) {
            long l = iMasterDataHolder.getDBKey();
            return this.removeHolderWithKey(l);
        }

        public boolean removeHolderWithKey(long l) {
            IMasterDataHolder iMasterDataHolder = (IMasterDataHolder) this.masterDataHolders.remove(l);
            return iMasterDataHolder != null;
        }

        public String toString() {
            return this.masterDataHolders.toString();
        }
    }

}