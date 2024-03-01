package com.rockwell.mes.apps.masterdata.impl.data.eqm;

import com.datasweep.compatibility.client.Error;
import com.rockwell.mes.apps.masterdata.ifc.data.IDataHandler;
import com.rockwell.mes.apps.masterdata.ifc.data.ILoadMasterDataProxiesContext;
import com.rockwell.mes.apps.masterdata.ifc.data.IMasterDataHolder;
import com.rockwell.mes.apps.masterdata.ifc.data.IMasterDataObjectType;
import com.rockwell.mes.apps.masterdata.ifc.data.eqm.IEquipmentEntityBaseDataHolder;
import com.rockwell.mes.apps.masterdata.ifc.data.eqm.IEquipmentObjectType;
import com.rockwell.mes.apps.masterdata.impl.data.AbstractMasterDataHandler;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.AbstractEquipmentDataHandler;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.BasicProxyStylingAttributes;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentClassDataHolder;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentClassProxy;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentObjectType;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentPropertyTypeProxy;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.loadproxycontext.EqmAssignedObjectDescription;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.loadproxycontext.EqmAssignedObjectDescriptionComparator;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.loadproxycontext.EqmLoadMasterDataProxiesContext;
import com.rockwell.mes.commons.base.ifc.choicelist.MESChoiceListHelper;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.commons.base.ifc.sql.ISqlStatement;
import com.rockwell.mes.commons.base.ifc.sql.SqlStatementFactory;
import com.rockwell.mes.services.s88.ifc.state.S88EquipmentStateSQLHelper;
import com.rockwell.mes.services.s88equipment.ifc.AbstractTechnicalEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.IMESEquipmentProperty;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88EquipmentClass;
import com.rockwell.mes.services.s88equipment.ifc.IS88EquipmentEntityBase;
import com.rockwell.mes.services.s88equipment.ifc.IS88EquipmentService;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.IMESS88StatusGraphAssignment;
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

public class EquipmentClassDataHandler extends AbstractEquipmentDataHandler<EquipmentClassDataHolder, IMESS88EquipmentClass, EquipmentClassProxy> {
    public static final Color DEFAULT_CLASS_BACKGROUND_COLOR = UIManager.getColor("RA-TileableComponent.EquipmentClass.background.color");

    private static final S88EquipmentStateSQLHelper S88_EQUIPMENT_CLASS_STATE_SQL_HELPER =
            new S88EquipmentStateSQLHelper.S88EquipmentClassStateSQLHelper();

    private static final ISqlStatement SQL_STATEMENT = EquipmentClassDataHandler.createBasicSQLStatement();

    private static int keyColumnIdx;

    private static int identifierColumnIdx;

    private static int shortDescriptionColumnIdx;

    private static int descriptionColumnIdx;

    private static int manufacturerColumnIdx;

    private static int modelColumnIdx;

    private static int serialNumberColumnIdx;

    private static int equipmentLevelColumnIdx;

    private static int backgroundColorColumnIdx;

    private static int textColorColumnIdx;

    private static int iconNameColumnIdx;

    private static int statusMessageIdColumnIdx;

    private static int fsmMessagePackColumnIdx;

    private static int processPackNameColumnIdx;

    private static final Comparator EQM_ASSIGNED_OBJECTS_COMPARATOR;

    protected static final Comparator EQM_PROPERTY_DESCRIPTION_COMPARATOR;

    public static void addEquipmentClassObjectStateJoinsToSQLStatement(ISqlStatement iSqlStatement) {
        S88_EQUIPMENT_CLASS_STATE_SQL_HELPER.addObjectStateJoinsToSQLStatement(iSqlStatement);
    }

    public static void addEquipmentClassObjectStateJoinsToSQLStatementWithAliasPrefix(ISqlStatement iSqlStatement, String string) {
        S88_EQUIPMENT_CLASS_STATE_SQL_HELPER.addObjectStateJoinsToSQLStatementWithAliasPrefix(iSqlStatement, string);
    }

    public static void addEquipmentClassObjectStateJoinsToSQLStatement(ISqlStatement iSqlStatement, String string) {
        S88_EQUIPMENT_CLASS_STATE_SQL_HELPER.addObjectStateJoinsToSQLStatement(iSqlStatement, string);
    }

    public static void addEquipmentClassObjectStateJoinsToSQLStatementWithAliasPrefix(ISqlStatement iSqlStatement, String string, String string2) {
        S88_EQUIPMENT_CLASS_STATE_SQL_HELPER.addObjectStateJoinsToSQLStatementWithAliasPrefix(iSqlStatement, string, string2);
    }

    public EquipmentClassDataHandler() {
        super(SQL_STATEMENT);
    }

    protected EquipmentClassDataHandler(ISqlStatement iSqlStatement) {
        super(iSqlStatement);
    }

    public static ISqlStatement createBasicSQLStatement() {
        ISqlStatement iSqlStatement = SqlStatementFactory.createSqlStatement((String) "AT_X_S88EquipmentClass", (String) "Get Equipment Classes");
        keyColumnIdx = iSqlStatement.addColumn("atr_key");
        identifierColumnIdx = iSqlStatement.addColumn("X_identifier_S");
        shortDescriptionColumnIdx = iSqlStatement.addColumn("X_shortDescription_S");
        descriptionColumnIdx = iSqlStatement.addColumn("X_description_S");
        manufacturerColumnIdx = iSqlStatement.addColumn("X_manufacturer_S");
        modelColumnIdx = iSqlStatement.addColumn("X_model_S");
        equipmentLevelColumnIdx = iSqlStatement.addColumn("X_equipmentLevel_I");
        backgroundColorColumnIdx = iSqlStatement.addColumn("X_tileColor_I");
        textColorColumnIdx = iSqlStatement.addColumn("X_tileTextColor_I");
        iconNameColumnIdx = iSqlStatement.addColumn("X_tileIcon_S");
        serialNumberColumnIdx = iSqlStatement.addColumn("X_serialNumber_S");
        statusMessageIdColumnIdx = iSqlStatement.addColumn("state_name_message_id", "state");
        fsmMessagePackColumnIdx = iSqlStatement.addColumn("message_pack_name", "mp");

        //
        processPackNameColumnIdx = iSqlStatement.addColumn("processPackName_S");
        EquipmentClassDataHandler.addEquipmentClassObjectStateJoinsToSQLStatement(iSqlStatement);
        return iSqlStatement;
    }

    public IMasterDataObjectType getObjectTypeOfHandledObjects() {
        return EquipmentObjectType.EQM_CLASS;
    }

    public long getNumberOfSelectedObjects() {
        return this.countDataOnDB("X_identifier_S");
    }

    public Collection<EquipmentClassProxy> loadAllDataFromDB(ILoadMasterDataProxiesContext iLoadMasterDataProxiesContext) {
        return this.loadAllDataFromDB("X_identifier_S", iLoadMasterDataProxiesContext);
    }

    protected EquipmentClassDataHolder buildDataHolder(IMESS88EquipmentClass iMESS88EquipmentClass) {
        return new EquipmentClassDataHolderImpl(iMESS88EquipmentClass);
    }

    protected IMESS88EquipmentClass getBackendObjectByKey(long l) {
        IMESS88EquipmentClass iMESS88EquipmentClass = EquipmentClassDataHandler.getS88EqmService().newEquipmentClass(l);
        return iMESS88EquipmentClass;
    }

    public boolean existsData(String string) {
        return EquipmentClassDataHandler.getS88EqmService().existsEquipmentClass(string);
    }

    protected EquipmentClassProxy createProxy(String[] arrstring, ILoadMasterDataProxiesContext iLoadMasterDataProxiesContext) {
        return this.createProxy(arrstring, (EqmLoadMasterDataProxiesContext) iLoadMasterDataProxiesContext);
    }

    private EquipmentClassProxy createProxy(String[] arrstring, EqmLoadMasterDataProxiesContext eqmLoadMasterDataProxiesContext) {
        Object object;
        Object object2;
        Object object32;
        Long l;
        BasicProxyStylingAttributes basicProxyStylingAttributes = this.determineBasicProxyAttributes(arrstring);
        EquipmentClassProxy equipmentClassProxy =
                new EquipmentClassProxy((IEquipmentObjectType) this.getObjectTypeOfHandledObjects(), basicProxyStylingAttributes.getBackgroundColor(),
                        basicProxyStylingAttributes.getForegroundColor(), basicProxyStylingAttributes.getIconName());
        long l2 = basicProxyStylingAttributes.getDBKey();
        this.initProxyPropertiesFromSQLResult(arrstring, equipmentClassProxy, l2);

        // Collection collection = eqmLoadMasterDataProxiesContext.getPropertiesPropertyTypesForClass(l2);
        // for (Object object32 : collection) {
        // object2 = eqmLoadMasterDataProxiesContext.getPropertyTypeProxy(object32.longValue());
        // if (object2 == null)
        // continue;
        // equipmentClassProxy.initPropertyType(object2.getIdentifier());
        // }

        Iterator<Long> iterator = eqmLoadMasterDataProxiesContext.getPropertiesPropertyTypesForClass(l2).iterator();
        while (iterator.hasNext()) {
            EquipmentPropertyTypeProxy propertyTypeProxy = eqmLoadMasterDataProxiesContext.getPropertyTypeProxy(iterator.next());
            if (propertyTypeProxy != null) {
                equipmentClassProxy.initPropertyType(propertyTypeProxy.getIdentifier());
            }
        }

        equipmentClassProxy.initEntityNames(eqmLoadMasterDataProxiesContext.getEntitiesForClass(l2));
        ArrayList arrayList = new ArrayList();
        object32 = eqmLoadMasterDataProxiesContext.getPropertiesDisplayedOnTilesForClass(l2);
        object2 = ((Collection<EquipmentClassProxy>) object32).iterator();
        while (((Iterator) object2).hasNext()) {
            l = (Long) ((Iterator) object2).next();
            object = eqmLoadMasterDataProxiesContext.getPropertyDisplayedOnTile(l.longValue());
            if (object == null)
                continue;
            arrayList.add(object);
        }
        Collections.sort(arrayList, EQM_PROPERTY_DESCRIPTION_COMPARATOR);

        // object2 = arrayList.iterator();
        // while (((Iterator) object2).hasNext()) {
        // String string;
        // AbstractTechnicalEquipmentPropertyType abstractTechnicalEquipmentPropertyType;
        // l = (Long) ((Iterator) object2).next();
        // object = l.getIdentifier();
        // EquipmentPropertyTypeProxy equipmentPropertyTypeProxy =
        // eqmLoadMasterDataProxiesContext.getPropertyTypeProxy(l.getAssignedObjectKey());
        // if (equipmentPropertyTypeProxy != null) {
        // abstractTechnicalEquipmentPropertyType = equipmentPropertyTypeProxy.getTechnicalPropertyType();
        // } else {
        // string = l.getShortDescription();
        // abstractTechnicalEquipmentPropertyType =
        // AbstractTechnicalEquipmentPropertyType.getTechnicalEquipmentPropertyTypeByClassName((String) string);
        // }
        // string = l.getUiInternalValues();
        // String string2 =
        // abstractTechnicalEquipmentPropertyType.getDisplayStringFromUnlocalizedRepresentation(string);
        // equipmentClassProxy.initProperty((String) object, string2);
        // }

        //
        Iterator localIterator3 = arrayList.iterator();
        while (localIterator3.hasNext()) {

            EqmAssignedObjectDescription eqmAssignedObjectDescription = (EqmAssignedObjectDescription) localIterator3.next();

            String identifier = eqmAssignedObjectDescription.getIdentifier();
            EquipmentPropertyTypeProxy propertyTypeProxy2 =
                    eqmLoadMasterDataProxiesContext.getPropertyTypeProxy(eqmAssignedObjectDescription.getAssignedObjectKey());
            AbstractTechnicalEquipmentPropertyType abstractTechnicalEquipmentPropertyType;
            if (propertyTypeProxy2 != null) {
                abstractTechnicalEquipmentPropertyType = propertyTypeProxy2.getTechnicalPropertyType();
            } else {
                abstractTechnicalEquipmentPropertyType = AbstractTechnicalEquipmentPropertyType
                        .getTechnicalEquipmentPropertyTypeByClassName(eqmAssignedObjectDescription.getShortDescription());
            }
            equipmentClassProxy.initProperty(identifier, abstractTechnicalEquipmentPropertyType
                    .getDisplayStringFromUnlocalizedRepresentation(eqmAssignedObjectDescription.getUiInternalValues()));
        }

        this.initStatusGraphNamesForSearchFromContext(eqmLoadMasterDataProxiesContext, equipmentClassProxy, l2);
        this.initStatusGraphRefsDisplayedOnCardFromContext(eqmLoadMasterDataProxiesContext, equipmentClassProxy, l2);
        equipmentClassProxy.setInitialized();
        return equipmentClassProxy;
    }

    protected void initProxyPropertiesFromSQLResult(String[] arrstring, EquipmentClassProxy equipmentClassProxy, long l) {
        equipmentClassProxy.initDBKey(l);
        equipmentClassProxy.initIdentifier(arrstring[identifierColumnIdx]);
        equipmentClassProxy.initShortDescription(arrstring[shortDescriptionColumnIdx]);
        equipmentClassProxy.initDescription(arrstring[descriptionColumnIdx]);
        if (!StringUtils.isEmpty((CharSequence) arrstring[equipmentLevelColumnIdx])) {
            long l2 = Long.parseLong(arrstring[equipmentLevelColumnIdx]);
            equipmentClassProxy.initEquipmentLevel(MESChoiceListHelper.getChoiceElement((String) "EquipmentHierarchy", (Long) l2));
        }
        String string = arrstring[fsmMessagePackColumnIdx];
        String string2 = arrstring[statusMessageIdColumnIdx];
        String string3 = I18nMessageUtility.getLocalizedMessage((String) string, (String) string2);
        equipmentClassProxy.initLocalizedStatus(string3);
        equipmentClassProxy.initManufacturer(arrstring[manufacturerColumnIdx]);
        equipmentClassProxy.initModelName(arrstring[modelColumnIdx]);
        equipmentClassProxy.initSerialNumber(arrstring[serialNumberColumnIdx]);

        equipmentClassProxy.initProcessPackName(arrstring[processPackNameColumnIdx]);
    }

    protected BasicProxyStylingAttributes determineBasicProxyAttributes(String[] arrstring) {
        Color color = EquipmentClassDataHandler.calculateColor((Color) DEFAULT_CLASS_BACKGROUND_COLOR, (String) arrstring[backgroundColorColumnIdx]);
        Color color2 = EquipmentClassDataHandler.calculateColor((Color) DEFAULT_FONT_COLOR, (String) arrstring[textColorColumnIdx]);
        String string = null;
        String string2 = arrstring[iconNameColumnIdx];
        if (!StringUtils.isEmpty((CharSequence) string2)) {
            string = string2;
        }
        long l = Long.parseLong(arrstring[keyColumnIdx]);
        return new BasicProxyStylingAttributes(l, null, color, color2, string);
    }

    private void initStatusGraphNamesForSearchFromContext(EqmLoadMasterDataProxiesContext eqmLoadMasterDataProxiesContext,
            EquipmentClassProxy equipmentClassProxy, long l) {
        equipmentClassProxy.initStatusGraphNames(eqmLoadMasterDataProxiesContext.getStatusGraphsForClass(l));
    }

    private void initStatusGraphRefsDisplayedOnCardFromContext(EqmLoadMasterDataProxiesContext eqmLoadMasterDataProxiesContext,
            EquipmentClassProxy equipmentClassProxy, long l) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(eqmLoadMasterDataProxiesContext.getStatusGraphsDisplayedOnTileForClass(l));
        Collections.sort(arrayList, EQM_PROPERTY_DESCRIPTION_COMPARATOR);
        // for (EqmAssignedObjectDescription eqmAssignedObjectDescription : arrayList) {
        // String string = eqmAssignedObjectDescription.getIdentifier();
        // String string2 = eqmAssignedObjectDescription.getShortDescription();
        // equipmentClassProxy.initProperty(string, string2);
        // }

        Iterator localIterator = arrayList.iterator();
        while (localIterator.hasNext()) {

            EqmAssignedObjectDescription eqmAssignedObjectDescription = (EqmAssignedObjectDescription) localIterator.next();
            String string = eqmAssignedObjectDescription.getIdentifier();
            String string2 = eqmAssignedObjectDescription.getShortDescription();
            equipmentClassProxy.initProperty(string, string2);

        }
    }

    public EquipmentClassProxy createProxy(IMESS88EquipmentClass iMESS88EquipmentClass) {
        BasicProxyStylingAttributes basicProxyStylingAttributes = this.determineBasicProxyAttributesFromClass(iMESS88EquipmentClass);
        EquipmentClassProxy equipmentClassProxy =
                new EquipmentClassProxy((IEquipmentObjectType) this.getObjectTypeOfHandledObjects(), basicProxyStylingAttributes.getBackgroundColor(),
                        basicProxyStylingAttributes.getForegroundColor(), basicProxyStylingAttributes.getIconName());
        this.initProxyPropertiesFromClass(iMESS88EquipmentClass, equipmentClassProxy);
        equipmentClassProxy.setInitialized();
        return equipmentClassProxy;
    }

    protected void initProxyPropertiesFromClass(IMESS88EquipmentClass eqmClass, EquipmentClassProxy proxy) {
        proxy.initDBKey(eqmClass.getKey());
        proxy.initIdentifier(eqmClass.getIdentifier());
        proxy.initShortDescription(eqmClass.getShortDescription());
        proxy.initDescription(eqmClass.getDescription());

        proxy.initProcessPackName(eqmClass.getProcessPackName());

        List properties = eqmClass.getProperties();
        Iterator<IMESEquipmentProperty> iterator = properties.iterator();
        while (iterator.hasNext()) {
            proxy.initPropertyType(iterator.next().getIdentifier());
        }
        List associatedEntities = eqmClass.getAssociatedEntities();
        ArrayList<String> inEntities = new ArrayList<String>();
        Iterator<IS88EquipmentEntityBase> iterator2 = associatedEntities.iterator();
        while (iterator2.hasNext()) {
            inEntities.add(iterator2.next().getIdentifier());
        }
        proxy.initEntityNames(inEntities);
        ArrayList<Object> list = new ArrayList<Object>();
        // for (IMESEquipmentProperty imesEquipmentProperty : properties) {
        // if (imesEquipmentProperty.getUiShowOnTile()) {
        // list.add(imesEquipmentProperty);
        // }
        // }

        while (iterator.hasNext()) {
            IMESEquipmentProperty imesEquipmentProperty = iterator.next();
            if (imesEquipmentProperty.getUiShowOnTile()) {
                list.add(imesEquipmentProperty);
            }
        }
        Collections.sort(list, EquipmentClassDataHandler.EQM_ASSIGNED_OBJECTS_COMPARATOR);
        //
        Iterator localIterator = list.iterator();
        while (localIterator.hasNext()) {

            IMESEquipmentProperty imesEquipmentProperty2 = (IMESEquipmentProperty) localIterator.next();
            proxy.initProperty(imesEquipmentProperty2.getIdentifier(), imesEquipmentProperty2.getDisplayString());

        }

        // for (IMESEquipmentProperty imesEquipmentProperty2 : list) {
        // proxy.initProperty(imesEquipmentProperty2.getIdentifier(), imesEquipmentProperty2.getDisplayString());
        // }
        List statusGraphAssignments = eqmClass.getStatusGraphAssignments();
        ArrayList<String> inStatusGraphs = new ArrayList<String>();
        Iterator<IMESS88StatusGraphAssignment> iterator5 = statusGraphAssignments.iterator();
        while (iterator5.hasNext()) {
            inStatusGraphs.add(iterator5.next().getIdentifier());
        }
        proxy.initStatusGraphNames(inStatusGraphs);
        ArrayList<Object> list2 = new ArrayList<Object>();
        for (IMESS88StatusGraphAssignment imess88StatusGraphAssignment : eqmClass.getStatusGraphAssignments()) {
            if (imess88StatusGraphAssignment.getUiShowOnTile()) {
                list2.add(imess88StatusGraphAssignment);
            }
        }
        Collections.sort(list2, EquipmentClassDataHandler.EQM_ASSIGNED_OBJECTS_COMPARATOR);
        // for (IMESS88StatusGraphAssignment imess88StatusGraphAssignment2 : list2) {
        // proxy.initProperty(imess88StatusGraphAssignment2.getIdentifier(),
        // imess88StatusGraphAssignment2.getDisplayString());
        // }

        Iterator localIterator2 = list2.iterator();
        while (localIterator2.hasNext()) {
            IMESS88StatusGraphAssignment imess88StatusGraphAssignment2 = (IMESS88StatusGraphAssignment) localIterator2.next();
            proxy.initProperty(imess88StatusGraphAssignment2.getIdentifier(), imess88StatusGraphAssignment2.getDisplayString());
        }

        if (eqmClass.getEquipmentLevel() != null) {
            proxy.initEquipmentLevel(eqmClass.getEquipmentLevel());
        }
        proxy.initLocalizedStatus(eqmClass.getStatus().getLocalizedName());
        proxy.initManufacturer(eqmClass.getManufacturer());
        proxy.initModelName(eqmClass.getModel());
        proxy.initSerialNumber(eqmClass.getSerialNumber());
    }

    protected BasicProxyStylingAttributes determineBasicProxyAttributesFromClass(IMESS88EquipmentClass iMESS88EquipmentClass) {
        Color color = DEFAULT_CLASS_BACKGROUND_COLOR;
        Color color2 = DEFAULT_FONT_COLOR;
        String string = null;
        Color color3 = iMESS88EquipmentClass.getUiBackgroundColor();
        Color color4 = iMESS88EquipmentClass.getUiForegroundColor();
        String string2 = iMESS88EquipmentClass.getUiIconName();
        if (color3 != null) {
            color = color3;
        }
        if (color4 != null) {
            color2 = color4;
        }
        if (!StringUtils.isEmpty((CharSequence) string2)) {
            string = string2;
        }
        return new BasicProxyStylingAttributes(iMESS88EquipmentClass.getKey(), null, color, color2, string);
    }

    private static IS88EquipmentService getS88EqmService() {
        return (IS88EquipmentService) ServiceFactory.getService(IS88EquipmentService.class);
    }

    public String getErrorMessageIDForPCError(Error error, List<String> list) {
        String string = super.getConstraintErrorMessageID(error, "EquClassIdentifier");
        if (string == null) {
            string = super.getObjectIsReferencedErrorMessageID(error);
        }
        if (string == null) {
            string = super.getObjectsDeletedErrorMessageID(error);
        }
        return string;
    }

    public EquipmentClassDataHolder createNewObject(String string) {
        // IS88EquipmentService iS88EquipmentService = EquipmentClassDataHandler.getS88EqmService();
        // IMESS88EquipmentClass iMESS88EquipmentClass = iS88EquipmentService.createEquipmentClass(string);
        // return (EquipmentClassDataHolder) this.getDataHolder((IPersistentMESObject) iMESS88EquipmentClass);

        return ((AbstractMasterDataHandler<EquipmentClassDataHolder, IMESS88EquipmentClass, EquipmentClassProxy>) this)
                .getDataHolder(getS88EqmService().createEquipmentClass(string));
    }

    public EquipmentClassDataHolder createDuplicate(EquipmentClassDataHolder equipmentClassDataHolder, String string) {
        // IMESS88EquipmentClass iMESS88EquipmentClass = (IMESS88EquipmentClass) equipmentClassDataHolder.getObject();
        // IMESS88EquipmentClass iMESS88EquipmentClass2 = (IMESS88EquipmentClass)
        // iMESS88EquipmentClass.createCopy(string);
        // EquipmentClassDataHolder equipmentClassDataHolder2 =
        // (EquipmentClassDataHolder) this.getDataHolder((IPersistentMESObject) iMESS88EquipmentClass2);
        //
        //
        // IDataHandler iDataHandler = this.getDataHandlerForEntities();
        // List list = iMESS88EquipmentClass2.getAssociatedEntities();
        // for (IS88EquipmentEntityBase iS88EquipmentEntityBase : list) {
        // IEquipmentEntityDataHolder iEquipmentEntityDataHolder =
        // (IEquipmentEntityDataHolder) iDataHandler.getDataHolder((Object) iS88EquipmentEntityBase.getActualObject());
        // equipmentClassDataHolder2.setAddedAssignedEntity((IEquipmentEntityBaseDataHolder)
        // iEquipmentEntityDataHolder);
        // }
        // return equipmentClassDataHolder2;

        IMESS88EquipmentClass masterDataBackendType = (IMESS88EquipmentClass) equipmentClassDataHolder.getObject().createCopy(string);
        EquipmentClassDataHolder equipmentClassDataHolder1 =
                ((AbstractMasterDataHandler<EquipmentClassDataHolder, IMESS88EquipmentClass, EquipmentClassProxy>) this)
                        .getDataHolder(masterDataBackendType);
        IDataHandler dataHandlerForEntities = this.getDataHandlerForEntities();
        Iterator iterator = masterDataBackendType.getAssociatedEntities().iterator();
        while (iterator.hasNext()) {
            equipmentClassDataHolder1.setAddedAssignedEntity((IEquipmentEntityBaseDataHolder) dataHandlerForEntities
                    .getDataHolder((Object) ((IS88EquipmentEntityBase) iterator.next()).getActualObject()));
        }
        return equipmentClassDataHolder1;
    }

    public IDataHandler getDataHandlerForEntities() {
        IDataHandler iDataHandler = EquipmentObjectType.EQM_ENTITY.getDataHandler();
        return iDataHandler;
    }

    public IDataHandler getDataHandlerForTemplateEntities() {
        IDataHandler iDataHandler = EquipmentObjectType.EQM_TEMPLATE_ENTITY.getDataHandler();
        return iDataHandler;
    }

    static {
        EQM_ASSIGNED_OBJECTS_COMPARATOR = new EqmAssignedObjectsComparator();
        EQM_PROPERTY_DESCRIPTION_COMPARATOR = new EqmAssignedObjectDescriptionComparator();
    }

    private static class EquipmentClassDataHolderImpl extends EquipmentClassDataHolder {
        public EquipmentClassDataHolderImpl(IMESS88EquipmentClass iMESS88EquipmentClass) {
            super(iMESS88EquipmentClass);
        }
    }

}