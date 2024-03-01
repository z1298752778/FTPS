package com.rockwell.mes.apps.masterdata.impl.data.eqm;

import com.datasweep.compatibility.client.Error;
import com.datasweep.compatibility.client.State;
import com.rockwell.mes.apps.masterdata.ifc.data.ILoadMasterDataProxiesContext;
import com.rockwell.mes.apps.masterdata.ifc.data.IMasterDataHolder;
import com.rockwell.mes.apps.masterdata.ifc.data.IMasterDataObjectType;
import com.rockwell.mes.apps.masterdata.ifc.data.IMasterDataProxy;
import com.rockwell.mes.apps.masterdata.ifc.data.eqm.IEquipmentObjectType;
import com.rockwell.mes.apps.masterdata.impl.data.AbstractMasterDataHandler;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.AbstractEquipmentDataHandler;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.BasicProxyStylingAttributes;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentObjectType;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentPropertyTypeProxy;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentStatusGraphDataHolder;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentStatusGraphProxy;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.loadproxycontext.EqmAssignedObjectDescription;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.loadproxycontext.EqmAssignedObjectDescriptionComparator;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.loadproxycontext.EqmLoadMasterDataProxiesContext;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.choicelist.MESChoiceListHelper;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.objects.IPersistentMESObject;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.commons.base.ifc.sql.ISqlStatement;
import com.rockwell.mes.commons.base.ifc.sql.SqlStatementFactory;
import com.rockwell.mes.commons.base.ifc.utility.ICopyable;
import com.rockwell.mes.services.s88.ifc.state.S88EquipmentStateSQLHelper;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IMESRefPropTypeOfStatusGraph;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IMESS88StatusGraph;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IS88StatusGraphService;
import com.rockwell.mes.services.s88equipment.ifc.utils.EqmAssignedObjectsComparator;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.swing.UIManager;
import org.apache.commons.lang3.StringUtils;

public class EquipmentStatusGraphDataHandler
        extends AbstractEquipmentDataHandler<EquipmentStatusGraphDataHolder, IMESS88StatusGraph, EquipmentStatusGraphProxy> {
    private static final Color DEFAULT_STATUS_GRAPH_BACKGROUND_COLOR =
            UIManager.getColor("RA-TileableComponent.EquipmentStatusGraph.background.color");

    private static final S88EquipmentStateSQLHelper S88_EQUIPMENT_STATUS_GRAPH_STATE_SQL_HELPER =
            new S88EquipmentStateSQLHelper.S88EquipmentStatusGraphStateSQLHelper();

    private static int keyColumnIdx;

    private static int identifierColumnIdx;

    private static int shortDescriptionColumnIdx;

    private static int descriptionColumnIdx;

    private static int displayNameColumnIdx;

    private static int purposeColumnIdx;

    private static int backgroundColorColumnIdx;

    private static int textColorColumnIdx;

    private static int statusMessageIdColumnIdx;

    private static int fsmMessagePackColumnIdx;

    private static final ISqlStatement SQL_STATEMENT;

    private static final Comparator EQM_ASSIGNED_OBJECT_DESCRIPTION_COMPARATOR;

    private static final Comparator EQM_ASSIGNED_OBJECTS_COMPARATOR;

    private static int processPackNameColumnIdx;

    public EquipmentStatusGraphDataHandler() {
        super(SQL_STATEMENT);
    }

    protected EquipmentStatusGraphDataHandler(ISqlStatement iSqlStatement) {
        super(iSqlStatement);
    }

    protected static ISqlStatement createBasicSQLStatement() {
        ISqlStatement iSqlStatement = SqlStatementFactory.createSqlStatement((String) "AT_X_S88StatusGraph", (String) "Get S88 status graphs");
        keyColumnIdx = iSqlStatement.addColumn("atr_key");
        identifierColumnIdx = iSqlStatement.addColumn("X_identifier_S");
        shortDescriptionColumnIdx = iSqlStatement.addColumn("X_shortDescription_S");
        descriptionColumnIdx = iSqlStatement.addColumn("X_description_S");
        displayNameColumnIdx = iSqlStatement.addColumn("X_displayName_S");
        purposeColumnIdx = iSqlStatement.addColumn("X_purpose_I");
        backgroundColorColumnIdx = iSqlStatement.addColumn("X_tileColor_I");
        textColorColumnIdx = iSqlStatement.addColumn("X_tileTextColor_I");

        processPackNameColumnIdx = iSqlStatement.addColumn("processPackName_S");
        statusMessageIdColumnIdx = iSqlStatement.addColumn("state_name_message_id", "state");
        fsmMessagePackColumnIdx = iSqlStatement.addColumn("message_pack_name", "mp");
        S88_EQUIPMENT_STATUS_GRAPH_STATE_SQL_HELPER.addObjectStateJoinsToSQLStatement(iSqlStatement);
        return iSqlStatement;
    }

    public IMasterDataObjectType getObjectTypeOfHandledObjects() {
        return EquipmentObjectType.EQM_STATUS_GRAPH;
    }

    public long getNumberOfSelectedObjects() {
        return this.countDataOnDB("X_identifier_S");
    }

    public Collection<EquipmentStatusGraphProxy> loadAllDataFromDB(ILoadMasterDataProxiesContext iLoadMasterDataProxiesContext) {
        return this.loadAllDataFromDB("X_identifier_S", iLoadMasterDataProxiesContext);
    }

    public EquipmentStatusGraphProxy createProxy(IMESS88StatusGraph iMESS88StatusGraph) {
        BasicProxyStylingAttributes basicProxyStylingAttributes = this.determineBasicProxyAttributesFromStatusGraph(iMESS88StatusGraph);
        EquipmentStatusGraphProxy equipmentStatusGraphProxy =
                new EquipmentStatusGraphProxy((IEquipmentObjectType) this.getObjectTypeOfHandledObjects(),
                        basicProxyStylingAttributes.getBackgroundColor(), basicProxyStylingAttributes.getForegroundColor());
        this.initProxyAttributesFromStatusGraph(equipmentStatusGraphProxy, iMESS88StatusGraph);
        equipmentStatusGraphProxy.setInitialized();
        return equipmentStatusGraphProxy;
    }

    protected BasicProxyStylingAttributes determineBasicProxyAttributesFromStatusGraph(IMESS88StatusGraph iMESS88StatusGraph) {
        Color color = DEFAULT_STATUS_GRAPH_BACKGROUND_COLOR;
        Color color2 = DEFAULT_FONT_COLOR;
        Color color3 = iMESS88StatusGraph.getUiBackgroundColor();
        Color color4 = iMESS88StatusGraph.getUiForegroundColor();
        if (color3 != null) {
            color = color3;
        }
        if (color4 != null) {
            color2 = color4;
        }
        String string = null;
        return new BasicProxyStylingAttributes(iMESS88StatusGraph.getKey(), null, color, color2, string);
    }

    protected void initProxyAttributesFromStatusGraph(EquipmentStatusGraphProxy equipmentStatusGraphProxy, IMESS88StatusGraph iMESS88StatusGraph) {
        equipmentStatusGraphProxy.initDBKey(iMESS88StatusGraph.getKey());
        equipmentStatusGraphProxy.initIdentifier(iMESS88StatusGraph.getIdentifier());
        equipmentStatusGraphProxy.initShortDescription(iMESS88StatusGraph.getShortDescription());
        equipmentStatusGraphProxy.initDescription(iMESS88StatusGraph.getDescription());
        equipmentStatusGraphProxy.initDisplayName(iMESS88StatusGraph.getDisplayName());

        //
        equipmentStatusGraphProxy.initProcessPackName(iMESS88StatusGraph.getProcessPackName());
        if (iMESS88StatusGraph.getPurpose() != null) {
            equipmentStatusGraphProxy.initPurpose(iMESS88StatusGraph.getPurpose());
        }
        String string = iMESS88StatusGraph.getStatus().getLocalizedName();
        equipmentStatusGraphProxy.initLocalizedStatus(string);
        this.initPropertyTypeNamesForSearchFromStatusGraph(iMESS88StatusGraph, equipmentStatusGraphProxy);
        this.initPropertyTypeRefsDisplayedOnCardFromStatusGraph(iMESS88StatusGraph, equipmentStatusGraphProxy);
    }

    private void initPropertyTypeNamesForSearchFromStatusGraph(IMESS88StatusGraph iMESS88StatusGraph,
            EquipmentStatusGraphProxy equipmentStatusGraphProxy) {
        List list = iMESS88StatusGraph.getReferencedPropertyTypes();
        ArrayList<String> arrayList = new ArrayList<String>();
        // for (IMESRefPropTypeOfStatusGraph iMESRefPropTypeOfStatusGraph : list) {
        // arrayList.add(iMESRefPropTypeOfStatusGraph.getIdentifier());
        // }

        //
        Iterator localIterator = list.iterator();
        while (localIterator.hasNext()) {
            IMESRefPropTypeOfStatusGraph iMESRefPropTypeOfStatusGraph = (IMESRefPropTypeOfStatusGraph) localIterator.next();
            arrayList.add(iMESRefPropTypeOfStatusGraph.getIdentifier());
        }
        equipmentStatusGraphProxy.initPropertyTypeNames(arrayList);
    }

    private void initPropertyTypeRefsDisplayedOnCardFromStatusGraph(IMESS88StatusGraph iMESS88StatusGraph,
            EquipmentStatusGraphProxy equipmentStatusGraphProxy) {
        List list = iMESS88StatusGraph.getReferencedPropertyTypes();
        ArrayList<IMESRefPropTypeOfStatusGraph> arrayList = new ArrayList<IMESRefPropTypeOfStatusGraph>();
        // for (IMESRefPropTypeOfStatusGraph iMESRefPropTypeOfStatusGraph : list) {
        // if (!iMESRefPropTypeOfStatusGraph.getUiShowOnTile())
        // continue;
        // arrayList.add(iMESRefPropTypeOfStatusGraph);
        // }

        Iterator localIterator = list.iterator();
        while (localIterator.hasNext()) {
            IMESRefPropTypeOfStatusGraph iMESRefPropTypeOfStatusGraph = (IMESRefPropTypeOfStatusGraph) localIterator.next();
            if (!iMESRefPropTypeOfStatusGraph.getUiShowOnTile())
                continue;
            arrayList.add(iMESRefPropTypeOfStatusGraph);
        }

        Collections.sort(arrayList, EQM_ASSIGNED_OBJECTS_COMPARATOR);
        for (IMESRefPropTypeOfStatusGraph iMESRefPropTypeOfStatusGraph : arrayList) {
            equipmentStatusGraphProxy.initProperty(iMESRefPropTypeOfStatusGraph.getIdentifier(), iMESRefPropTypeOfStatusGraph.getDisplayString());
        }
    }

    protected EquipmentStatusGraphProxy createProxy(String[] arrstring, ILoadMasterDataProxiesContext iLoadMasterDataProxiesContext) {
        BasicProxyStylingAttributes basicProxyStylingAttributes = this.determineBasicProxyAttributes(arrstring);
        EquipmentStatusGraphProxy equipmentStatusGraphProxy =
                new EquipmentStatusGraphProxy((IEquipmentObjectType) this.getObjectTypeOfHandledObjects(),
                        basicProxyStylingAttributes.getBackgroundColor(), basicProxyStylingAttributes.getForegroundColor());
        long l = basicProxyStylingAttributes.getDBKey();
        this.initProxyAttributesFromSQLResult(arrstring, equipmentStatusGraphProxy, l);
        EqmLoadMasterDataProxiesContext eqmLoadMasterDataProxiesContext = (EqmLoadMasterDataProxiesContext) iLoadMasterDataProxiesContext;
        this.initPropertyTypeNamesForSearchFromContext(eqmLoadMasterDataProxiesContext, equipmentStatusGraphProxy, l);
        this.initPropertyTypeRefsDisplayedOnCardFromContext(eqmLoadMasterDataProxiesContext, equipmentStatusGraphProxy, l);
        equipmentStatusGraphProxy.setInitialized();
        return equipmentStatusGraphProxy;
    }

    protected BasicProxyStylingAttributes determineBasicProxyAttributes(String[] arrstring) {
        Color color = EquipmentStatusGraphDataHandler.calculateColor((Color) DEFAULT_STATUS_GRAPH_BACKGROUND_COLOR,
                (String) arrstring[backgroundColorColumnIdx]);
        Color color2 = EquipmentStatusGraphDataHandler.calculateColor((Color) DEFAULT_FONT_COLOR, (String) arrstring[textColorColumnIdx]);
        String string = null;
        long l = Long.parseLong(arrstring[keyColumnIdx]);
        return new BasicProxyStylingAttributes(l, null, color, color2, string);
    }

    protected void initProxyAttributesFromSQLResult(String[] arrstring, EquipmentStatusGraphProxy equipmentStatusGraphProxy, long l) {
        equipmentStatusGraphProxy.initDBKey(l);
        equipmentStatusGraphProxy.initIdentifier(arrstring[identifierColumnIdx]);
        equipmentStatusGraphProxy.initShortDescription(arrstring[shortDescriptionColumnIdx]);
        equipmentStatusGraphProxy.initDescription(arrstring[descriptionColumnIdx]);
        String string = arrstring[fsmMessagePackColumnIdx];
        String string2 = arrstring[statusMessageIdColumnIdx];
        String string3 = I18nMessageUtility.getLocalizedMessage((String) string, (String) string2);
        equipmentStatusGraphProxy.initLocalizedStatus(string3);
        equipmentStatusGraphProxy.initDisplayName(arrstring[displayNameColumnIdx]);
        //
        equipmentStatusGraphProxy.initProcessPackName(arrstring[processPackNameColumnIdx]);

        if (!StringUtils.isEmpty((CharSequence) arrstring[purposeColumnIdx])) {
            long l2 = Long.parseLong(arrstring[purposeColumnIdx]);
            equipmentStatusGraphProxy.initPurpose(MESChoiceListHelper.getChoiceElement((String) "S88StatusGraphPurpose", (Long) l2));
        }
    }

    private void initPropertyTypeNamesForSearchFromContext(EqmLoadMasterDataProxiesContext eqmLoadMasterDataProxiesContext,
            EquipmentStatusGraphProxy equipmentStatusGraphProxy, long l) {
        equipmentStatusGraphProxy.initPropertyTypeNames(eqmLoadMasterDataProxiesContext.getPropertyTypeRefsForStatusGraph(l));
    }

    private void initPropertyTypeRefsDisplayedOnCardFromContext(EqmLoadMasterDataProxiesContext eqmLoadMasterDataProxiesContext,
            EquipmentStatusGraphProxy equipmentStatusGraphProxy, long l) {
        EqmAssignedObjectDescription eqmAssignedObjectDescription;
        ArrayList<EqmAssignedObjectDescription> arrayList = new ArrayList<EqmAssignedObjectDescription>();
        Collection collection = eqmLoadMasterDataProxiesContext.getPropertyTypeRefsDisplayedOnTilesForGraph(l);
        // for (Long l2 : collection) {
        // eqmAssignedObjectDescription =
        // eqmLoadMasterDataProxiesContext.getPropertyTypeRefsDisplayedOnTile(l2.longValue());
        // if (eqmAssignedObjectDescription == null)
        // continue;
        // arrayList.add(eqmAssignedObjectDescription);
        // }

        Iterator localIterator = collection.iterator();
        while (localIterator.hasNext()) {
            Long l2 = (Long) localIterator.next();
            eqmAssignedObjectDescription = eqmLoadMasterDataProxiesContext.getPropertyTypeRefsDisplayedOnTile(l2.longValue());
            if (eqmAssignedObjectDescription == null)
                continue;
            arrayList.add(eqmAssignedObjectDescription);

        }
        // TODO
        Collections.sort(arrayList, EQM_ASSIGNED_OBJECT_DESCRIPTION_COMPARATOR);

        for (EqmAssignedObjectDescription eqmAssignedObjectDescription2 : arrayList) {
            EquipmentPropertyTypeProxy eqmAssignedObjectDescription3 =
                    eqmLoadMasterDataProxiesContext.getPropertyTypeProxy(eqmAssignedObjectDescription2.getAssignedObjectKey());

            
            if (eqmAssignedObjectDescription3 == null)
                continue;
            String string = eqmAssignedObjectDescription3.getIdentifier();
            String string2 = eqmAssignedObjectDescription3.getShortDescription();
            equipmentStatusGraphProxy.initProperty(string, string2);
        }
    }

    public boolean existsData(String string) {
        return EquipmentStatusGraphDataHandler.getStatusGraphService().existsGraph(string);
    }

    public String getErrorMessageIDForPCError(Error error, List<String> list) {
        String string = super.getConstraintErrorMessageID(error, "Index_Id");
        if (string == null) {
            string = super.getObjectIsReferencedErrorMessageID(error);
        }
        if (string == null) {
            string = super.getObjectsDeletedErrorMessageID(error);
        }
        if (string == null) {
            string = this.getReferencedStateDeletedErrorMessageID(error, list);
        }
        return string;
    }

    private String getReferencedStateDeletedErrorMessageID(Error error, List<String> list) {
        if ("DeletedStateReferencedByEntities_errorMsg".equals(error.getMessageId())) {
            if (error.getArguments() != null && error.getArguments().length > 0) {
                list.clear();
                for (String string : error.getArguments()) {
                    list.add(string);
                }
            }
            return error.getMessageId();
        }
        return null;
    }

    protected IMESS88StatusGraph getBackendObjectByKey(long l) {
        IMESS88StatusGraph iMESS88StatusGraph = EquipmentStatusGraphDataHandler.getStatusGraphService().getGraphFromCache(l);
        return iMESS88StatusGraph;
    }

    public EquipmentStatusGraphDataHolder createNewObject(String string) {
        // IMESS88StatusGraph iMESS88StatusGraph =
        // EquipmentStatusGraphDataHandler.getStatusGraphService().createGraph(string);
        // return (EquipmentStatusGraphDataHolder) this.getDataHolder((IPersistentMESObject) iMESS88StatusGraph);

        return ((AbstractMasterDataHandler<EquipmentStatusGraphDataHolder, IMESS88StatusGraph, EquipmentStatusGraphProxy>) this)
                .getDataHolder(getStatusGraphService().createGraph(string));
    }

    public EquipmentStatusGraphDataHolder createDuplicate(EquipmentStatusGraphDataHolder equipmentStatusGraphDataHolder, String string) {
        // IMESS88StatusGraph iMESS88StatusGraph = (IMESS88StatusGraph) equipmentStatusGraphDataHolder.getObject();
        // IMESS88StatusGraph iMESS88StatusGraph2 = (IMESS88StatusGraph) iMESS88StatusGraph.createCopy(string);
        // EquipmentStatusGraphDataHolder equipmentStatusGraphDataHolder2 =
        // (EquipmentStatusGraphDataHolder) this.getDataHolder((IPersistentMESObject) iMESS88StatusGraph2);
        // return equipmentStatusGraphDataHolder2;

        return ((AbstractMasterDataHandler<EquipmentStatusGraphDataHolder, IMESS88StatusGraph, EquipmentStatusGraphProxy>) this)
                .getDataHolder((IMESS88StatusGraph) equipmentStatusGraphDataHolder.getObject().createCopy(string));
    }

    protected EquipmentStatusGraphDataHolder buildDataHolder(IMESS88StatusGraph iMESS88StatusGraph) {
        return new EqmStatusGraphDataHolderImpl(iMESS88StatusGraph);
    }

    private static IS88StatusGraphService getStatusGraphService() {
        return (IS88StatusGraphService) ServiceFactory.getService(IS88StatusGraphService.class);
    }

    static {
        SQL_STATEMENT = EquipmentStatusGraphDataHandler.createBasicSQLStatement();
        EQM_ASSIGNED_OBJECT_DESCRIPTION_COMPARATOR = new EqmAssignedObjectDescriptionComparator();
        EQM_ASSIGNED_OBJECTS_COMPARATOR = new EqmAssignedObjectsComparator();
    }

    private static class EqmStatusGraphDataHolderImpl extends EquipmentStatusGraphDataHolder {
        public EqmStatusGraphDataHolderImpl(IMESS88StatusGraph iMESS88StatusGraph) {
            super(iMESS88StatusGraph);
        }
    }

}