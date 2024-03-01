
package com.rockwell.mes.apps.masterdata.impl.data.eqm;

import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.DsList;
import com.datasweep.compatibility.client.Error;
import com.datasweep.compatibility.client.Filter;
import com.datasweep.compatibility.client.NamedFilter;
import com.datasweep.compatibility.client.User;
import com.jidesoft.swing.StyleRange;
import com.rockwell.mes.apps.masterdata.ifc.data.IMasterDataProxy;
import com.rockwell.mes.apps.masterdata.ifc.data.eqm.IEquipmentEntityBaseDataHolder;
import com.rockwell.mes.apps.masterdata.ifc.eqmPropertyEditor.StatusGraphAssignmentCellRenderer;
import com.rockwell.mes.apps.masterdata.ifc.frame.editor.MDTEditorMessages;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.loadproxycontext.EqmAssignedObjectDescription;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.loadproxycontext.EqmAssignedObjectDescriptionComparator;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.loadproxycontext.EqmLoadMasterDataProxiesContext;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.loadproxycontext.IEqmLoadMasterDataProxiesStyleClassContext;
import com.rockwell.mes.commons.base.ifc.choicelist.MESChoiceListHelper;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.commons.base.ifc.sql.ISqlStatement;
import com.rockwell.mes.commons.base.ifc.sql.SqlStatementFactory;
import com.rockwell.mes.services.s88.ifc.state.S88EquipmentStateSQLHelper;
import com.rockwell.mes.services.s88.ifc.state.S88EquipmentStateSQLHelper.S88EquipmentEntityStateSQLHelper;
import com.rockwell.mes.services.s88equipment.ifc.AbstractTechnicalEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.IMESEquipmentProperty;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88Equipment;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractEquipmentEntityDataHandler<HolderType extends IEquipmentEntityBaseDataHolder, EqmBackendObjectType extends IS88EquipmentEntityBase, ProxyType extends IMasterDataProxy> extends AbstractEquipmentDataHandler<HolderType, EqmBackendObjectType, ProxyType> {
    private static final Log LOGGER = LogFactory.getLog(AbstractEquipmentEntityDataHandler.class);
    private static final IS88EquipmentService S88_EQUIPMENT_SERVICE = (IS88EquipmentService)ServiceFactory.getService(IS88EquipmentService.class);
    private static final S88EquipmentStateSQLHelper S88_EQUIPMENT_ENTITY_STATE_SQL_HELPER = new S88EquipmentEntityStateSQLHelper();
    public static final Color DEFAULT_ENTITY_BACKGROUND_COLOR = UIManager.getColor("RA-TileableComponent.EquipmentEntity.background.color");
    private static final Comparator EQM_ASSIGNED_OBJECTS_COMPARATOR = new EqmAssignedObjectsComparator();
    private static final Comparator EQM_ASSIGNED_OBJECTS_DESCRIPTION_COMPARATOR = new EqmAssignedObjectDescriptionComparator();
    private static int manufacturerColumn;
    private static int serialNumberColumn;
    private static int modelColumn;
    private static int equipmentLevelColumn;
    private static int statusMsgIdColumn;
    private static int fsmMsgPackNameColumn;
    private static int barcodeColumn;
    private static int expiryChangeErrorBehaviorColumn;

    private static int processPackNameColumn;

    protected static final String ACCESS_FILTER_LIST = "LC_AccessFilterList";

    public AbstractEquipmentEntityDataHandler(ISqlStatement var1) {
        super(var1);
    }

    protected EqmBackendObjectType getBackendObjectByKey(long var1) {
        IMESS88Equipment var3 = S88_EQUIPMENT_SERVICE.newEquipment(var1);
        return var3.getActualObject();
    }

    public String getErrorMessageIDForPCError(Error var1, List<String> var2) {
        String var3 = super.getConstraintErrorMessageID(var1, "S88EquIdent");
        if (var3 == null) {
            var3 = this.getUniqueConstraintErrorMessageID(var1, "UQ_Barcode", "barcodeNotUnique_EqEntity_ErrorMsg");
        }

        if (var3 == null) {
            var3 = this.getObjectIsReferencedErrorMessageID(var1);
        }

        if (var3 == null) {
            var3 = super.getObjectsDeletedErrorMessageID(var1);
        }

        return var3;
    }

    public String getErrorMessageForObjectWithIdentifierAlreadyExistsError() {
        String var1 = MDTEditorMessages.getMessage("TemplateEntityOrEntity_Label");
        String var2 = MDTEditorMessages.getMessage("EntityAlreadyExistent_ErrorMsg", new Object[]{this.getObjectTypeOfHandledObjects().getLocalizedLabel(), var1});
        return var2;
    }

    public static ISqlStatement createBasicSQLStatement(final String var0) {
        ISqlStatement var1 = SqlStatementFactory.createSqlStatement("AT_X_S88Equipment", var0);
        var1.addColumn("atr_key");
        var1.addColumn("X_identifier_S");
        var1.addColumn("X_shortDescription_S");
        var1.addColumn("X_description_S");
        manufacturerColumn = var1.addColumn("X_manufacturer_S");
        modelColumn = var1.addColumn("X_model_S");
        equipmentLevelColumn = var1.addColumn("X_equipmentLevel_I");
        serialNumberColumn = var1.addColumn("X_serialNumber_S");
        barcodeColumn = var1.addColumn("X_barcode_S");
        expiryChangeErrorBehaviorColumn = var1.addColumn("X_expiryChgErrBehavior_I");

        processPackNameColumn = var1.addColumn("processPackName_S");
        statusMsgIdColumn = var1.addColumn("state_name_message_id", "state");
        fsmMsgPackNameColumn = var1.addColumn("message_pack_name", "mp");
        addEquipmentEntityObjectStateJoinsToSQLStatement(var1);
        addEquipmentAccessJoinsToSQL(var1);

        return var1;
    }

    // where 条件
    private static void addEquipmentAccessJoinsToSQL(ISqlStatement paramISqlStatement) {
    	List<AccessPrivilege> equipAccess = EquipAccessList();
    	String accessList = "";
    	if(equipAccess.size()>0){
    		for (AccessPrivilege accessPrivilege : equipAccess) {
    			accessList = accessList+"'"+accessPrivilege.getAccessPrivilegeKey()+"',";
			}
    		accessList = accessList.substring(0, accessList.length()-1);
    	}else{
    		accessList = "''";
    	}
    	paramISqlStatement.addWhereClause("(LC_equip_accPriv_98 IN ("+accessList+") OR LC_equip_accPriv_98 IS NULL )");
    	
	}

	public static void addEquipmentEntityObjectStateJoinsToSQLStatement(final ISqlStatement var0) {
        S88_EQUIPMENT_ENTITY_STATE_SQL_HELPER.addObjectStateJoinsToSQLStatement(var0);
    }

    public static void addEquipmentEntityObjectStateJoinsToSQLStatementWithAliasPrefix(final ISqlStatement var0, String var1) {
        S88_EQUIPMENT_ENTITY_STATE_SQL_HELPER.addObjectStateJoinsToSQLStatementWithAliasPrefix(var0, var1);
    }

    public static void addEquipmentEntityObjectStateJoinsToSQLStatement(final ISqlStatement var0, final String var1) {
        S88_EQUIPMENT_ENTITY_STATE_SQL_HELPER.addObjectStateJoinsToSQLStatement(var0, var1);
    }

    public static void addEquipmentEntityObjectStateJoinsToSQLStatementWithAliasPrefix(final ISqlStatement var0, final String var1, String var2) {
        S88_EQUIPMENT_ENTITY_STATE_SQL_HELPER.addObjectStateJoinsToSQLStatementWithAliasPrefix(var0, var1, var2);
    }
    
    
    
    protected void initCommonProxyProperties(ISqlStatement var1, EquipmentEntityProxy var2, String[] var3, long var4, EqmLoadMasterDataProxiesContext var6) {
    	
    	var2.initDBKey(var4);
        var2.initIdentifier(var3[getIdentifierColumnIndex(var1)]);
        var2.initShortDescription(var3[getShortDescriptionColumnIndex(var1)]);
        var2.initDescription(var3[getDescriptionColumnIndex(var1)]);
        this.initProxyPropertyTypesAndClasses(var2, var6);
        this.initProxyPropertiesDisplayedOnTile(var2, var6);
        this.initProxyStatusGraphNamesForSearch(var2, var6, var4);
        this.initProxyStatusGraphInfoDisplayedOnCard(var2, var6, var4);
        this.initProxyLocalizedStatus(var2, var3);
        var2.initManufacturer(var3[manufacturerColumn]);
        var2.initModelName(var3[modelColumn]);
        var2.initSerialNumber(var3[serialNumberColumn]);
        // 加初始
        var2.initProcessPackName(var3[processPackNameColumn]);

        if (!StringUtils.isEmpty(var3[equipmentLevelColumn])) {
            long var7 = Long.parseLong(var3[equipmentLevelColumn]);
            var2.initEquipmentLevel(MESChoiceListHelper.getChoiceElement("EquipmentHierarchy", var7));
        }

    }

    /**
     * 设备权限
     * 
     * @Description
     * @return
     *
     * @author:HT
     * @create:下午2:31:47
     */
	private static List<AccessPrivilege> EquipAccessList() {
		User user = PCContext.getFunctions().getCurrentUser();
		List<AccessPrivilege> accessEqm = new ArrayList<AccessPrivilege>();
		try {

	        DsList partAccessFilter = PCContext.getFunctions().getList(ACCESS_FILTER_LIST);
	        NamedFilter filter = PCContext.getFunctions().getFilterByName(partAccessFilter.getItem(2).toString());
	        Filter accessFilter = filter.getFilter();
	        List<AccessPrivilege> accessRights = accessFilter.exec();
		
	        for (AccessPrivilege accessRight : accessRights) {
	            if (user.hasPrivilege(accessRight)) {
	         	   accessEqm.add(accessRight);
	            }
	        }
		} catch (DatasweepException e) {
			e.printStackTrace();
		}
		return accessEqm;
	}

	private void initProxyPropertyTypesAndClasses(EquipmentEntityProxy var1, EqmLoadMasterDataProxiesContext var2) {
        long var3 = var1.getDBKey();
        Collection var5 = var2.getPropertiesPropertyTypesForEntity(var3);
        Iterator var6 = var5.iterator();

        while(var6.hasNext()) {
            Long var7 = (Long)var6.next();
            EquipmentPropertyTypeProxy var8 = var2.getPropertyTypeProxy(var7);
            if (var8 != null) {
                var1.initPropertyType(var8.getIdentifier());
            }
        }

        var1.initClassNames(var2.getClassesForEntity(var3));
    }

    private void initProxyPropertiesDisplayedOnTile(EquipmentEntityProxy var1, EqmLoadMasterDataProxiesContext var2) {
        long var3 = var1.getDBKey();
        ArrayList var5 = new ArrayList();
        Collection var6 = var2.getPropertiesDisplayedOnTilesForEntity(var3);
        Iterator var7 = var6.iterator();

        while(var7.hasNext()) {
            Long var8 = (Long)var7.next();
            EqmAssignedObjectDescription var9 = var2.getPropertyDisplayedOnTile(var8);
            if (var9 != null) {
                var5.add(var9);
            }
        }

        Collections.sort(var5, EQM_ASSIGNED_OBJECTS_DESCRIPTION_COMPARATOR);
        var7 = var5.iterator();

        while(var7.hasNext()) {
            EqmAssignedObjectDescription var14 = (EqmAssignedObjectDescription)var7.next();
            String var15 = var14.getIdentifier();
            EquipmentPropertyTypeProxy var11 = var2.getPropertyTypeProxy(var14.getAssignedObjectKey());
            AbstractTechnicalEquipmentPropertyType var10;
            String var12;
            if (var11 != null) {
                var10 = var11.getTechnicalPropertyType();
            } else {
                var12 = var14.getShortDescription();
                var10 = AbstractTechnicalEquipmentPropertyType.getTechnicalEquipmentPropertyTypeByClassName(var12);
            }

            var12 = var14.getUiInternalValues();
            String var13 = var10.getDisplayStringFromUnlocalizedRepresentation(var12);
            var1.initProperty(var15, var13);
        }

    }

    private void initProxyStatusGraphNamesForSearch(EquipmentEntityProxy var1, EqmLoadMasterDataProxiesContext var2, final long var3) {
        var1.initStatusGraphNames(var2.getStatusGraphsForEntity(var3));
    }

    private void initProxyStatusGraphInfoDisplayedOnCard(EquipmentEntityProxy var1, EqmLoadMasterDataProxiesContext var2, final long var3) {
        ArrayList var5 = new ArrayList();
        var5.addAll(var2.getStatusGraphsDisplayedOnTileForEntity(var3));
        Collections.sort(var5, EQM_ASSIGNED_OBJECTS_DESCRIPTION_COMPARATOR);
        Iterator var6 = var5.iterator();

        while(var6.hasNext()) {
            EqmAssignedObjectDescription var7 = (EqmAssignedObjectDescription)var6.next();
            String var8 = var7.getIdentifier();
            String var9 = var7.getUiInternalValues();
            StyleRange var10 = var7.getUiInternalValuesFormatting();
            var1.initProperty(var8, var9, var10);
        }

    }

    private void initProxyLocalizedStatus(EquipmentEntityProxy var1, String[] var2) {
        String var3 = var2[fsmMsgPackNameColumn];
        String var4 = var2[statusMsgIdColumn];
        String var5 = I18nMessageUtility.getLocalizedMessage(var3, var4);
        var1.initLocalizedStatus(var5);
    }

    protected void initCommonProxyPropertiesFromEntity(EquipmentEntityProxy var1, IS88EquipmentEntityBase var2) {
        var1.initDBKey(var2.getKey());
        var1.initIdentifier(var2.getIdentifier());
        var1.initShortDescription(var2.getShortDescription());
        var1.initDescription(var2.getDescription());
        this.initProxyAssignedClassesFromEntity(var1, var2);
        this.initProxyPropertyInformationFromEntity(var1, var2);
        this.initProxyStatusGraphInformationFromEntity(var1, var2);
        var1.initManufacturer(var2.getManufacturer());
        var1.initModelName(var2.getModel());
        var1.initSerialNumber(var2.getSerialNumber());
        // 加初始
        var1.initProcessPackName(var2.getProcessPackName());

        if (var2.getEquipmentLevel() != null) {
            var1.initEquipmentLevel(var2.getEquipmentLevel());
        }

        String var3 = var2.getStatus().getLocalizedName();
        var1.initLocalizedStatus(var3);
    }

    private void initProxyAssignedClassesFromEntity(EquipmentEntityProxy var1, IS88EquipmentEntityBase var2) {
        List var3 = var2.getEquipmentClasses();
        ArrayList var4 = new ArrayList();
        Iterator var5 = var3.iterator();

        while(var5.hasNext()) {
            IMESS88EquipmentClass var6 = (IMESS88EquipmentClass)var5.next();
            var4.add(var6.getIdentifier());
        }

        var1.initClassNames(var4);
    }

    private void initProxyPropertyInformationFromEntity(EquipmentEntityProxy var1, IS88EquipmentEntityBase var2) {
        List var3 = var2.getProperties();
        Iterator var4 = var3.iterator();

        while(var4.hasNext()) {
            IMESEquipmentProperty var5 = (IMESEquipmentProperty)var4.next();
            var1.initPropertyType(var5.getIdentifier());
        }

        List var8 = this.getPropertiesDisplayedOnTileable(var2.getStyleClass(), var3);
        Collections.sort(var8, EQM_ASSIGNED_OBJECTS_COMPARATOR);
        Iterator var7 = var8.iterator();

        while(var7.hasNext()) {
            IMESEquipmentProperty var6 = (IMESEquipmentProperty)var7.next();
            var1.initProperty(var6.getIdentifier(), var6.getDisplayString());
        }

    }

    private List<IMESEquipmentProperty<?>> getPropertiesDisplayedOnTileable(IMESS88EquipmentClass var1, List<IMESEquipmentProperty<?>> var2) {
        ArrayList var3 = new ArrayList();
        if (var1 != null) {
            Iterator var4 = var2.iterator();

            while(var4.hasNext()) {
                IMESEquipmentProperty var5 = (IMESEquipmentProperty)var4.next();
                IMESEquipmentProperty var6 = var1.getPropertyOfType(var5.getEquipmentPropertyType());
                if (var6 != null && var6.getUiShowOnTile()) {
                    var3.add(var5);
                }
            }
        }

        return var3;
    }

    private void initProxyStatusGraphInformationFromEntity(EquipmentEntityProxy var1, IS88EquipmentEntityBase var2) {
        List var3 = var2.getStatusGraphAssignments();
        ArrayList var4 = new ArrayList();
        Iterator var5 = var3.iterator();

        while(var5.hasNext()) {
            IMESS88StatusGraphAssignment var6 = (IMESS88StatusGraphAssignment)var5.next();
            var4.add(var6.getIdentifier());
        }

        var1.initStatusGraphNames(var4);
        List var9 = this.getAssignedStatusGraphsDisplayedOnTileable(var2.getStyleClass(), var2.getStatusGraphAssignments());
        Collections.sort(var9, EQM_ASSIGNED_OBJECTS_COMPARATOR);
        Iterator var10 = var9.iterator();

        while(var10.hasNext()) {
            IMESS88StatusGraphAssignment var7 = (IMESS88StatusGraphAssignment)var10.next();
            StyleRange var8 = StatusGraphAssignmentCellRenderer.getStatusGraphAssignmentFormatting(var7);
            var1.initProperty(var7.getIdentifier(), var7.getDisplayStringForCard(), var8);
        }

    }

    public static BasicProxyStylingAttributes determineBasicProxyAttributes(ISqlStatement var0, String[] var1, IEqmLoadMasterDataProxiesStyleClassContext var2) {
        long var3 = Long.parseLong(var1[getKeyColumnIndex(var0)]);
        Color var5 = DEFAULT_ENTITY_BACKGROUND_COLOR;
        Color var6 = DEFAULT_FONT_COLOR;
        String var7 = null;
        String var8 = null;
        String var9 = var2.getStyleClassForEntity(var3);
        if (!StringUtils.isEmpty(var9)) {
            BasicProxyStylingAttributes var10 = var2.getStyleClassAttributes(var9);
            if (var10 != null) {
                var5 = var10.getBackgroundColor() != null ? var10.getBackgroundColor() : DEFAULT_ENTITY_BACKGROUND_COLOR;
                var6 = var10.getForegroundColor() != null ? var10.getForegroundColor() : DEFAULT_FONT_COLOR;
                var7 = var10.getIconName();
            } else {
                LOGGER.warn("Attributes for style class " + var9 + " not found!");
            }

            var8 = var9;
        }

        return new BasicProxyStylingAttributes(var3, var8, var5, var6, var7);
    }

    public static BasicProxyStylingAttributes determineBasicProxyAttributesFromEntity(IS88EquipmentEntityBase var0) {
        Color var1 = DEFAULT_ENTITY_BACKGROUND_COLOR;
        Color var2 = DEFAULT_FONT_COLOR;
        String var3 = null;
        String var4 = null;
        IMESS88EquipmentClass var5 = var0.getStyleClass();
        if (var5 != null) {
            Color var6 = var5.getUiBackgroundColor();
            Color var7 = var5.getUiForegroundColor();
            String var8 = var5.getUiIconName();
            if (var6 != null) {
                var1 = var6;
            } else {
                var1 = EquipmentClassDataHandler.DEFAULT_CLASS_BACKGROUND_COLOR;
            }

            if (var7 != null) {
                var2 = var7;
            }

            if (!StringUtils.isEmpty(var8)) {
                var3 = var8;
            }

            var4 = var5.getIdentifier();
        }

        return new BasicProxyStylingAttributes(var0.getKey(), var4, var1, var2, var3);
    }

    private List<IMESS88StatusGraphAssignment> getAssignedStatusGraphsDisplayedOnTileable(IMESS88EquipmentClass var1, List<IMESS88StatusGraphAssignment> var2) {
        ArrayList var3 = new ArrayList();
        if (var1 != null) {
            Iterator var4 = var2.iterator();

            while(var4.hasNext()) {
                IMESS88StatusGraphAssignment var5 = (IMESS88StatusGraphAssignment)var4.next();
                IMESS88StatusGraphAssignment var6 = var1.getStatusGraphAssignment(var5.getIdentifier());
                if (var6 != null && var6.getUiShowOnTile()) {
                    var3.add(var5);
                }
            }
        }

        return var3;
    }

    public static int getKeyColumnIndex(ISqlStatement var0) {
        return var0.getColumnIndex("atr_key");
    }

    public static int getIdentifierColumnIndex(ISqlStatement var0) {
        return var0.getColumnIndex("X_identifier_S");
    }

    public static int getDescriptionColumnIndex(ISqlStatement var0) {
        return var0.getColumnIndex("X_description_S");
    }

    public static int getShortDescriptionColumnIndex(ISqlStatement var0) {
        return var0.getColumnIndex("X_shortDescription_S");
    }

    public static int getFSMMsgPackNameColumnIndex() {
        return fsmMsgPackNameColumn;
    }

    public static int getStatusMsgIdColumnIndex() {
        return statusMsgIdColumn;
    }

    public static int getBarcodeColumnIndex() {
        return barcodeColumn;
    }

    public static int getExpiryChangeErrorBehaviorColumnIndex() {
        return expiryChangeErrorBehaviorColumn;
    }
}
