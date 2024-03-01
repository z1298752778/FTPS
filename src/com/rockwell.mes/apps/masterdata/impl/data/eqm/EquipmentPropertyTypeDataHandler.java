package com.rockwell.mes.apps.masterdata.impl.data.eqm;

import com.datasweep.compatibility.client.Error;
import com.datasweep.compatibility.client.FlexibleStateModel;
import com.datasweep.compatibility.client.UnitOfMeasure;
import com.rockwell.mes.apps.masterdata.ifc.data.ILoadMasterDataProxiesContext;
import com.rockwell.mes.apps.masterdata.ifc.data.IMasterDataObjectType;
import com.rockwell.mes.apps.masterdata.ifc.data.eqm.IEquipmentObjectType;
import com.rockwell.mes.apps.masterdata.ifc.eqmPropertyEditor.EquipmentPropertyTypeAutomationHelper;
import com.rockwell.mes.apps.masterdata.ifc.ui.UIConstants;
import com.rockwell.mes.apps.masterdata.ifc.ui.eqm.EqmUIConstants;
import com.rockwell.mes.apps.masterdata.impl.data.AbstractMasterDataHandler;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.AbstractEquipmentDataHandler;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentObjectType;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentPropertyTypeDataHolder;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.EquipmentPropertyTypeProxy;
import com.rockwell.mes.apps.masterdata.impl.data.eqm.loadproxycontext.EqmLoadMasterDataProxiesContext;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.choicelist.MESChoiceListHelper;
import com.rockwell.mes.commons.base.ifc.choicelist.MESChoiceListLabels;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.commons.base.ifc.sql.ISqlStatement;
import com.rockwell.mes.commons.base.ifc.sql.SqlStatementFactory;
import com.rockwell.mes.commons.base.ifc.utility.Pair;
import com.rockwell.mes.services.s88equipment.ifc.AbstractTechnicalEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.AttributeDefinition;
import com.rockwell.mes.services.s88equipment.ifc.EnumEqmPropertyFlexibleAttributeDataType;
import com.rockwell.mes.services.s88equipment.ifc.EnumEqmPropertyLiveDataType;
import com.rockwell.mes.services.s88equipment.ifc.EnumEqmPropertyTechnicalType;
import com.rockwell.mes.services.s88equipment.ifc.EnumEqmPropertyTypeUsage;
import com.rockwell.mes.services.s88equipment.ifc.IMESEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.IS88EquipmentService;
import com.rockwell.mes.services.s88equipment.ifc.TagDefinition;
import com.rockwell.mes.services.s88equipment.ifc.technical.AbstractNumberEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.technical.BooleanEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.technical.CapabilityValidationEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.technical.DurationEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.technical.FSMEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.technical.FlexibleAttributeEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.technical.LiveDataGroupEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.technical.MeasuredValueEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.technical.RangesEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.technical.RoomCleaningEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.technical.ScaleTestAndCalibrationEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.technical.ScalesConfigurationEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.technical.StringEquipmentPropertyType;
import com.rockwell.mes.services.s88equipment.ifc.technical.WorkCenterAssignmentEquipmentPropertyType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EquipmentPropertyTypeDataHandler
        extends AbstractEquipmentDataHandler<EquipmentPropertyTypeDataHolder, IMESEquipmentPropertyType, EquipmentPropertyTypeProxy> {
    private static final Log LOGGER = LogFactory.getLog(EquipmentPropertyTypeDataHandler.class);

    private static final ISqlStatement SQL_STATEMENT = EquipmentPropertyTypeDataHandler.createBasicSQLStatement();

    private static int keyColumnIdx;

    private static int identifierColumnIdx;

    private static int shortDescriptionColumnIdx;

    private static int descriptionColumnIdx;

    private static int classNameColumnIdx;

    private static int usageColumnIdx;

    private static int capabilityColumnIdx;

    private static int liveDataTypeColumnIdx;

    private static int engineeringUnitColumnIdx;

    private static int fsmColumnIdx;

    private static int tagGroupDefinitionColumnIdx;

    private static int purposeColumnIdx;

    private static int processPackNameColumnIdx;

    public static ISqlStatement createBasicSQLStatement() {
        ISqlStatement iSqlStatement = SqlStatementFactory.createSqlStatement((String) "AT_X_EquipmentPropertyType", (String) "Get Property Types");
        keyColumnIdx = iSqlStatement.addColumn("atr_key");
        identifierColumnIdx = iSqlStatement.addColumn("X_identifier_S");
        shortDescriptionColumnIdx = iSqlStatement.addColumn("X_shortDescription_S");
        descriptionColumnIdx = iSqlStatement.addColumn("X_description_S");
        classNameColumnIdx = iSqlStatement.addColumn("X_className_S");
        usageColumnIdx = iSqlStatement.addColumn("X_usage_I");
        capabilityColumnIdx = iSqlStatement.addColumn("X_capability_I");
        liveDataTypeColumnIdx = iSqlStatement.addColumn("X_liveDateType_I");
        engineeringUnitColumnIdx = iSqlStatement.addColumn("X_engineeringUnit_91");
        fsmColumnIdx = iSqlStatement.addColumn("X_automaticFSM_128");
        tagGroupDefinitionColumnIdx = iSqlStatement.addColumn("X_tagGroupDefinition_64");
        purposeColumnIdx = iSqlStatement.addColumn("X_purpose_I");
        //
        processPackNameColumnIdx = iSqlStatement.addColumn("processPackName_S");
        return iSqlStatement;
    }

    public EquipmentPropertyTypeDataHandler() {
        super(SQL_STATEMENT);
    }

    protected EquipmentPropertyTypeDataHandler(ISqlStatement iSqlStatement) {
        super(iSqlStatement);
    }

    public IMasterDataObjectType getObjectTypeOfHandledObjects() {
        return EquipmentObjectType.EQM_PROPERTY_TYPE;
    }

    public long getNumberOfSelectedObjects() {
        return this.countDataOnDB("X_identifier_S");
    }

    public Collection<EquipmentPropertyTypeProxy> loadAllDataFromDB(ILoadMasterDataProxiesContext iLoadMasterDataProxiesContext) {
        return this.loadAllDataFromDB("X_identifier_S", iLoadMasterDataProxiesContext);
    }

    protected EquipmentPropertyTypeDataHolder buildDataHolder(IMESEquipmentPropertyType iMESEquipmentPropertyType) {
        EqPropertyTypeDataHolderImpl eqPropertyTypeDataHolderImpl = new EqPropertyTypeDataHolderImpl(iMESEquipmentPropertyType);
        eqPropertyTypeDataHolderImpl.setReadOnly(true);
        return eqPropertyTypeDataHolderImpl;
    }

    protected IMESEquipmentPropertyType getBackendObjectByKey(long l) {
        IMESEquipmentPropertyType iMESEquipmentPropertyType = EquipmentPropertyTypeDataHandler.getS88EqmService().newEquipmentPropertyType(l);
        return iMESEquipmentPropertyType;
    }

    public boolean existsData(String string) {
        return EquipmentPropertyTypeDataHandler.getS88EqmService().existsEquipmentPropertyType(string);
    }

    protected EquipmentPropertyTypeProxy createProxy(String[] arrstring, ILoadMasterDataProxiesContext iLoadMasterDataProxiesContext) {
        return this.createProxy(arrstring, (EqmLoadMasterDataProxiesContext) iLoadMasterDataProxiesContext);
    }

    private EquipmentPropertyTypeProxy createProxy(String[] arrstring, EqmLoadMasterDataProxiesContext eqmLoadMasterDataProxiesContext) {
        EquipmentPropertyTypeProxy equipmentPropertyTypeProxy =
                this.createProxyWithoutProxiesContext((IEquipmentObjectType) this.getObjectTypeOfHandledObjects(), arrstring);
        EquipmentPropertyTypeDataHandler.initializeTechnicalPropertyTypeSpecificData(equipmentPropertyTypeProxy, arrstring,
                eqmLoadMasterDataProxiesContext);
        equipmentPropertyTypeProxy.setInitialized();
        eqmLoadMasterDataProxiesContext.addPropertyTypeProxy(equipmentPropertyTypeProxy);
        return equipmentPropertyTypeProxy;
    }

    protected EquipmentPropertyTypeProxy createProxyWithoutProxiesContext(IEquipmentObjectType iEquipmentObjectType, String[] arrstring) {
        EquipmentPropertyTypeProxy equipmentPropertyTypeProxy = new EquipmentPropertyTypeProxy(iEquipmentObjectType);
        equipmentPropertyTypeProxy.initDBKey(Long.parseLong(arrstring[keyColumnIdx]));
        equipmentPropertyTypeProxy.initIdentifier(arrstring[identifierColumnIdx]);
        equipmentPropertyTypeProxy.initShortDescription(arrstring[shortDescriptionColumnIdx]);
        equipmentPropertyTypeProxy.initDescription(arrstring[descriptionColumnIdx]);
        //
        equipmentPropertyTypeProxy.initProcessPackName(arrstring[processPackNameColumnIdx]);

        AbstractTechnicalEquipmentPropertyType abstractTechnicalEquipmentPropertyType =
                AbstractTechnicalEquipmentPropertyType.getTechnicalEquipmentPropertyTypeByClassName((String) arrstring[classNameColumnIdx]);
        equipmentPropertyTypeProxy.initTechnicalPropertyType(abstractTechnicalEquipmentPropertyType);
        IMESChoiceElement iMESChoiceElement = EquipmentPropertyTypeDataHandler.getUsage(arrstring[usageColumnIdx]);
        equipmentPropertyTypeProxy.initUsage(iMESChoiceElement);
        IMESChoiceElement iMESChoiceElement2 = EquipmentPropertyTypeDataHandler.getPurpose(arrstring[purposeColumnIdx]);
        equipmentPropertyTypeProxy.initPurpose(iMESChoiceElement2);
        return equipmentPropertyTypeProxy;
    }

    public EquipmentPropertyTypeProxy createProxy(IMESEquipmentPropertyType iMESEquipmentPropertyType) {
        EquipmentPropertyTypeProxy equipmentPropertyTypeProxy =
                new EquipmentPropertyTypeProxy((IEquipmentObjectType) this.getObjectTypeOfHandledObjects());
        this.initProxyFromS88PropertyType(equipmentPropertyTypeProxy, iMESEquipmentPropertyType);
        return equipmentPropertyTypeProxy;
    }

    protected void initProxyFromS88PropertyType(EquipmentPropertyTypeProxy equipmentPropertyTypeProxy,
            IMESEquipmentPropertyType iMESEquipmentPropertyType) {
        equipmentPropertyTypeProxy.initDBKey(iMESEquipmentPropertyType.getKey());
        equipmentPropertyTypeProxy.initIdentifier(iMESEquipmentPropertyType.getIdentifier());
        equipmentPropertyTypeProxy.initShortDescription(iMESEquipmentPropertyType.getShortDescription());
        equipmentPropertyTypeProxy.initDescription(iMESEquipmentPropertyType.getDescription());
        //
        equipmentPropertyTypeProxy.initProcessPackName(iMESEquipmentPropertyType.getProcessPackName());

        AbstractTechnicalEquipmentPropertyType abstractTechnicalEquipmentPropertyType = iMESEquipmentPropertyType.getTechnicalEquipmentPropertyType();
        equipmentPropertyTypeProxy.initTechnicalPropertyType(abstractTechnicalEquipmentPropertyType);
        IMESChoiceElement iMESChoiceElement = iMESEquipmentPropertyType.getUsage();
        equipmentPropertyTypeProxy.initUsage(iMESChoiceElement);
        IMESChoiceElement iMESChoiceElement2 = iMESEquipmentPropertyType.getPurpose();
        equipmentPropertyTypeProxy.initPurpose(iMESChoiceElement2);
        EquipmentPropertyTypeDataHandler.initializeTechnicalPropertyTypeSpecificData(equipmentPropertyTypeProxy, iMESEquipmentPropertyType);
        equipmentPropertyTypeProxy.setInitialized();
    }

    private static IMESChoiceElement getCapability(String string) {
        long l;
        if (StringUtils.isEmpty((CharSequence) string)) {
            return null;
        }
        try {
            l = Long.parseLong(string);
        } catch (NumberFormatException numberFormatException) {
            LOGGER.error((Object) ("Could not convert '" + string + "' to long."), (Throwable) numberFormatException);
            return null;
        }
        return MESChoiceListHelper.getChoiceElement((String) "S88EquipmentCapability", (Long) l);
    }

    private static IMESChoiceElement getPurpose(String string) {
        long l;
        if (StringUtils.isEmpty((CharSequence) string)) {
            return null;
        }
        try {
            l = Long.parseLong(string);
        } catch (NumberFormatException numberFormatException) {
            LOGGER.error((Object) ("Could not convert '" + string + "' to long."), (Throwable) numberFormatException);
            return null;
        }
        return MESChoiceListHelper.getChoiceElement((String) "S88EquipmentPropertyTypePurpose", (Long) l);
    }

    private static IMESChoiceElement getUsage(String string) {
        long l;
        if (StringUtils.isEmpty((CharSequence) string)) {
            return null;
        }
        try {
            l = Long.parseLong(string);
        } catch (NumberFormatException numberFormatException) {
            LOGGER.error((Object) ("Could not convert '" + string + "' to long."), (Throwable) numberFormatException);
            return null;
        }
        return MESChoiceListHelper.getChoiceElement((String) "EqmPropertyTypeUsage", (Long) l);
    }

    private static IMESChoiceElement getLiveDataType(String[] arrstring) {
        long l;
        String string = arrstring[liveDataTypeColumnIdx];
        if (StringUtils.isEmpty((CharSequence) string)) {
            LOGGER.warn((Object) ("Live data type of automation property type, number / mv data type not set: Property type '"
                    + arrstring[identifierColumnIdx] + "', " + arrstring[keyColumnIdx]));
            return null;
        }
        try {
            l = Long.parseLong(string);
        } catch (NumberFormatException numberFormatException) {
            LOGGER.error((Object) ("Could not convert '" + string + "' to long."), (Throwable) numberFormatException);
            return null;
        }
        if (l == 0L) {
            LOGGER.warn((Object) ("Live data type of automation property type, number / mv data type set to 0 (no data type): Property type '"
                    + arrstring[identifierColumnIdx] + "', " + arrstring[keyColumnIdx]));
            return null;
        }
        return MESChoiceListHelper.getChoiceElement((String) "EqmLiveDataTypeNumeric", (Long) l);
    }

    private static void initializeTechnicalPropertyTypeSpecificData(EquipmentPropertyTypeProxy equipmentPropertyTypeProxy, String[] arrstring,
            EqmLoadMasterDataProxiesContext eqmLoadMasterDataProxiesContext) {
        AbstractTechnicalEquipmentPropertyType abstractTechnicalEquipmentPropertyType =
                AbstractTechnicalEquipmentPropertyType.getTechnicalEquipmentPropertyTypeByClassName((String) arrstring[classNameColumnIdx]);
        if (abstractTechnicalEquipmentPropertyType instanceof AbstractNumberEquipmentPropertyType
                || abstractTechnicalEquipmentPropertyType instanceof MeasuredValueEquipmentPropertyType) {
            EquipmentPropertyTypeDataHandler.initProxyUiNumericProperties(equipmentPropertyTypeProxy, arrstring, eqmLoadMasterDataProxiesContext);
        } else if (abstractTechnicalEquipmentPropertyType instanceof StringEquipmentPropertyType
                || abstractTechnicalEquipmentPropertyType instanceof BooleanEquipmentPropertyType) {
            IMESChoiceElement iMESChoiceElement = EquipmentPropertyTypeDataHandler.getUsage(arrstring[usageColumnIdx]);
            EquipmentPropertyTypeDataHandler.initProxyUiLabelProperty(equipmentPropertyTypeProxy, abstractTechnicalEquipmentPropertyType,
                    iMESChoiceElement);
        } else if (abstractTechnicalEquipmentPropertyType instanceof CapabilityValidationEquipmentPropertyType) {
            String string = arrstring[capabilityColumnIdx];
            IMESChoiceElement iMESChoiceElement = null;
            if (!StringUtils.isEmpty((CharSequence) string)) {
                iMESChoiceElement = EquipmentPropertyTypeDataHandler.getCapability(string);
            }
            EquipmentPropertyTypeDataHandler.initProxyUiProperty(equipmentPropertyTypeProxy, iMESChoiceElement);
        } else if (abstractTechnicalEquipmentPropertyType instanceof ScalesConfigurationEquipmentPropertyType
                || abstractTechnicalEquipmentPropertyType instanceof ScaleTestAndCalibrationEquipmentPropertyType
                || abstractTechnicalEquipmentPropertyType instanceof RangesEquipmentPropertyType
                || abstractTechnicalEquipmentPropertyType instanceof WorkCenterAssignmentEquipmentPropertyType
                || abstractTechnicalEquipmentPropertyType instanceof DurationEquipmentPropertyType
                || abstractTechnicalEquipmentPropertyType instanceof RoomCleaningEquipmentPropertyType) {
            LOGGER.info((Object) ("Currently nothing displayed for this type: " + (Object) abstractTechnicalEquipmentPropertyType));
        } else if (abstractTechnicalEquipmentPropertyType instanceof LiveDataGroupEquipmentPropertyType
                || abstractTechnicalEquipmentPropertyType instanceof FlexibleAttributeEquipmentPropertyType) {
            EquipmentPropertyTypeDataHandler.initProxyUiProperties(equipmentPropertyTypeProxy, arrstring, eqmLoadMasterDataProxiesContext);
        } else if (abstractTechnicalEquipmentPropertyType instanceof FSMEquipmentPropertyType) {
            String string = arrstring[fsmColumnIdx];
            if (!StringUtils.isEmpty((CharSequence) string)) {
                long l = Long.parseLong(string);
                equipmentPropertyTypeProxy.initProperty(UIConstants.FSM_CARD_LABEL, eqmLoadMasterDataProxiesContext.getFSMName(l));
            }
        } else {
            LOGGER.error((Object) ("Technical property type " + (Object) abstractTechnicalEquipmentPropertyType
                    + " not handled in Card specific property rendering."));
        }
    }

    private static void initializeTechnicalPropertyTypeSpecificData(EquipmentPropertyTypeProxy equipmentPropertyTypeProxy,
            IMESEquipmentPropertyType iMESEquipmentPropertyType) {
        AbstractTechnicalEquipmentPropertyType abstractTechnicalEquipmentPropertyType = iMESEquipmentPropertyType.getTechnicalEquipmentPropertyType();
        if (abstractTechnicalEquipmentPropertyType instanceof AbstractNumberEquipmentPropertyType
                || abstractTechnicalEquipmentPropertyType instanceof MeasuredValueEquipmentPropertyType) {
            EquipmentPropertyTypeDataHandler.initProxyUiNumericProperties(equipmentPropertyTypeProxy, iMESEquipmentPropertyType);
        } else if (abstractTechnicalEquipmentPropertyType instanceof StringEquipmentPropertyType
                || abstractTechnicalEquipmentPropertyType instanceof BooleanEquipmentPropertyType) {
            IMESChoiceElement iMESChoiceElement = iMESEquipmentPropertyType.getUsage();
            EquipmentPropertyTypeDataHandler.initProxyUiLabelProperty(equipmentPropertyTypeProxy, abstractTechnicalEquipmentPropertyType,
                    iMESChoiceElement);
        } else if (abstractTechnicalEquipmentPropertyType instanceof CapabilityValidationEquipmentPropertyType) {
            IMESChoiceElement iMESChoiceElement = iMESEquipmentPropertyType.getCapability();
            EquipmentPropertyTypeDataHandler.initProxyUiProperty(equipmentPropertyTypeProxy, iMESChoiceElement);
        } else if (abstractTechnicalEquipmentPropertyType instanceof ScalesConfigurationEquipmentPropertyType
                || abstractTechnicalEquipmentPropertyType instanceof ScaleTestAndCalibrationEquipmentPropertyType
                || abstractTechnicalEquipmentPropertyType instanceof RangesEquipmentPropertyType
                || abstractTechnicalEquipmentPropertyType instanceof WorkCenterAssignmentEquipmentPropertyType
                || abstractTechnicalEquipmentPropertyType instanceof DurationEquipmentPropertyType
                || abstractTechnicalEquipmentPropertyType instanceof RoomCleaningEquipmentPropertyType) {
            LOGGER.info((Object) ("Currently nothing displayed for this type: " + (Object) abstractTechnicalEquipmentPropertyType));
        } else if (abstractTechnicalEquipmentPropertyType instanceof LiveDataGroupEquipmentPropertyType
                || abstractTechnicalEquipmentPropertyType instanceof FlexibleAttributeEquipmentPropertyType) {
            EquipmentPropertyTypeDataHandler.initProxyUiProperties(equipmentPropertyTypeProxy, iMESEquipmentPropertyType);
        } else if (abstractTechnicalEquipmentPropertyType instanceof FSMEquipmentPropertyType) {
            FlexibleStateModel flexibleStateModel = iMESEquipmentPropertyType.getAutomaticFSM();
            if (flexibleStateModel != null) {
                equipmentPropertyTypeProxy.initProperty(UIConstants.FSM_CARD_LABEL,
                        FSMEquipmentPropertyType.getDisplayString((FlexibleStateModel) flexibleStateModel));
            }
        } else {
            LOGGER.error((Object) ("Technical property type " + (Object) abstractTechnicalEquipmentPropertyType
                    + " not handled in Card specific property rendering."));
        }
    }

    private static void initProxyUiLabelProperty(EquipmentPropertyTypeProxy equipmentPropertyTypeProxy,
            AbstractTechnicalEquipmentPropertyType abstractTechnicalEquipmentPropertyType, IMESChoiceElement iMESChoiceElement) {
        boolean bl = EnumEqmPropertyTypeUsage.AUTOMATION.equals((Object) iMESChoiceElement);
        if (bl) {
            equipmentPropertyTypeProxy.initProperty(EqmUIConstants.LIVE_DATA_TYPE_CARD_LABEL,
                    abstractTechnicalEquipmentPropertyType.getTechnicalType().getLocalizedMessage());
        }
    }

    private static void initProxyUiProperty(EquipmentPropertyTypeProxy equipmentPropertyTypeProxy, IMESChoiceElement iMESChoiceElement) {
        String string = iMESChoiceElement != null ? iMESChoiceElement.getLocalizedMessage() : MESChoiceListLabels.getLocalizedNoChoiceEntry();
        equipmentPropertyTypeProxy.initProperty(EqmUIConstants.CAPABILITY_CARD_LABEL, string);
    }

    private static void initProxyUiNumericProperties(EquipmentPropertyTypeProxy equipmentPropertyTypeProxy, String[] arrstring,
            EqmLoadMasterDataProxiesContext eqmLoadMasterDataProxiesContext) {
        boolean bl = EnumEqmPropertyTypeUsage.AUTOMATION.equals((Object) EquipmentPropertyTypeDataHandler.getUsage(arrstring[usageColumnIdx]));
        if (bl) {
            IMESChoiceElement iMESChoiceElement = EquipmentPropertyTypeDataHandler.getLiveDataType(arrstring);
            String string = iMESChoiceElement != null ? iMESChoiceElement.getLocalizedMessage() : MESChoiceListLabels.getLocalizedNoChoiceEntry();
            equipmentPropertyTypeProxy.initProperty(EqmUIConstants.LIVE_DATA_TYPE_CARD_LABEL, string);
            EquipmentPropertyTypeDataHandler.initProxyUiProperties(equipmentPropertyTypeProxy, arrstring, eqmLoadMasterDataProxiesContext);
        }
        EquipmentPropertyTypeDataHandler.initProxyUiUoMProperty(equipmentPropertyTypeProxy, arrstring, eqmLoadMasterDataProxiesContext);
    }

    private static void initProxyUiNumericProperties(EquipmentPropertyTypeProxy equipmentPropertyTypeProxy,
            IMESEquipmentPropertyType iMESEquipmentPropertyType) {
        IMESChoiceElement iMESChoiceElement;
        String string;
        boolean bl = EnumEqmPropertyTypeUsage.AUTOMATION.equals((Object) iMESEquipmentPropertyType.getUsage());
        if (bl) {
            iMESChoiceElement = iMESEquipmentPropertyType.getLiveDateType();
            string = iMESChoiceElement != null ? iMESChoiceElement.getLocalizedMessage() : MESChoiceListLabels.getLocalizedNoChoiceEntry();
            equipmentPropertyTypeProxy.initProperty(EqmUIConstants.LIVE_DATA_TYPE_CARD_LABEL, string);
            EquipmentPropertyTypeDataHandler.initProxyUiProperties(equipmentPropertyTypeProxy, iMESEquipmentPropertyType);
        }
        if ((iMESChoiceElement = (IMESChoiceElement) iMESEquipmentPropertyType.getEngineeringUnit()) != null) {
            string = ((UnitOfMeasure) iMESChoiceElement).getSymbol();
            equipmentPropertyTypeProxy.initProperty(EqmUIConstants.ENGINEERING_UNITS_CARD_LABEL, string);
            String string2 = ((UnitOfMeasure) iMESChoiceElement).getFullName();
            equipmentPropertyTypeProxy.initExtraSearchText(string2);
        } else {
            equipmentPropertyTypeProxy.initProperty(EqmUIConstants.ENGINEERING_UNITS_CARD_LABEL, null);
        }
    }

    private static void initProxyUiUoMProperty(EquipmentPropertyTypeProxy equipmentPropertyTypeProxy, String[] arrstring,
            EqmLoadMasterDataProxiesContext eqmLoadMasterDataProxiesContext) {
        if (!StringUtils.isEmpty((CharSequence) arrstring[engineeringUnitColumnIdx])) {
            long l = Long.parseLong(arrstring[engineeringUnitColumnIdx]);
            String string = eqmLoadMasterDataProxiesContext.getUomSymbol(l);
            equipmentPropertyTypeProxy.initProperty(EqmUIConstants.ENGINEERING_UNITS_CARD_LABEL, string);
            String string2 = eqmLoadMasterDataProxiesContext.getUomName(l);
            if (!StringUtils.isEmpty((CharSequence) string2)) {
                equipmentPropertyTypeProxy.initExtraSearchText(string2);
            }
        } else {
            equipmentPropertyTypeProxy.initProperty(EqmUIConstants.ENGINEERING_UNITS_CARD_LABEL, null);
        }
    }

    private static void initProxyUiProperties(EquipmentPropertyTypeProxy equipmentPropertyTypeProxy,
            IMESEquipmentPropertyType iMESEquipmentPropertyType) {
        List<NameValuePair> list = EquipmentPropertyTypeDataHandler.createProperties(iMESEquipmentPropertyType);
        for (NameValuePair nameValuePair : list) {
            equipmentPropertyTypeProxy.initProperty(nameValuePair.getName(), nameValuePair.getValue());
        }
    }

    private static void initProxyUiProperties(EquipmentPropertyTypeProxy equipmentPropertyTypeProxy, String[] arrstring,
            EqmLoadMasterDataProxiesContext eqmLoadMasterDataProxiesContext) {
        List<NameValuePair> list = EquipmentPropertyTypeDataHandler.createProperties(arrstring, eqmLoadMasterDataProxiesContext);
        for (NameValuePair nameValuePair : list) {
            equipmentPropertyTypeProxy.initProperty(nameValuePair.getName(), nameValuePair.getValue());
        }
    }

    private static List<NameValuePair> createProperties(IMESEquipmentPropertyType iMESEquipmentPropertyType) {
        ArrayList<NameValuePair> arrayList = new ArrayList<NameValuePair>();
        IMESChoiceElement iMESChoiceElement = iMESEquipmentPropertyType.getTechnicalType();
        if (EnumEqmPropertyTechnicalType.DECIMAL.getChoiceElement().equals((Object) iMESChoiceElement)) {
            IMESChoiceElement iMESChoiceElement2 = iMESEquipmentPropertyType.getUsage();
            if (EnumEqmPropertyTypeUsage.AUTOMATION.equals((Object) iMESChoiceElement2)) {
                List<String> list = EquipmentPropertyTypeDataHandler.getIdentifiersForAutomationDecimalEnabledTags(iMESEquipmentPropertyType);
                String string =
                        EquipmentPropertyTypeDataHandler.getLocalizedValueOfTagDefinitionIdentifiers(list, iMESEquipmentPropertyType.getIdentifier());
                arrayList.add(new NameValuePair("CardLayout_EnabledTags_Label", string));
            }
        } else if (EnumEqmPropertyTechnicalType.FLEXIBLE_TAG_DEFINITION.getChoiceElement().equals((Object) iMESChoiceElement)) {
            List<Short> list = EquipmentPropertyTypeDataHandler.getLiveDataTypesForFlexibleTagGroup(iMESEquipmentPropertyType);
            String string = EquipmentPropertyTypeDataHandler.getLocalizedValueOfLiveDataTypes(list);
            arrayList.add(new NameValuePair("CardLayout_LiveDataType_Label", string));
            List<String> list2 = EquipmentPropertyTypeDataHandler.getIdentifiersForFlexibleTagGroupEnabledTags(iMESEquipmentPropertyType);
            string = EquipmentPropertyTypeDataHandler.getLocalizedValueOfStrings(list2);
            arrayList.add(new NameValuePair("CardLayout_EnabledTagsUnlocalized_Label", string));
            List<String> list3 = EquipmentPropertyTypeDataHandler.getUomSymbolsForFlexibleTagGroup(iMESEquipmentPropertyType);
            string = EquipmentPropertyTypeDataHandler.getLocalizedValueOfUOMSymbols(list3);
            arrayList.add(new NameValuePair("CardLayout_EngineeringUnits_Label", string));
        } else if (EnumEqmPropertyTechnicalType.FLEXIBLE_ATTRIBUTE_DEFINITION.getChoiceElement().equals((Object) iMESChoiceElement)) {
            List<Short> list = EquipmentPropertyTypeDataHandler.getDataTypesForFlexibleAttribute(iMESEquipmentPropertyType);
            String string = EquipmentPropertyTypeDataHandler.getLocalizedValueOfDataTypes(list);
            arrayList.add(new NameValuePair("CardLayout_FlexibleAttributeDataTypes_Label", string));
            List<String> list4 = EquipmentPropertyTypeDataHandler.getIdentifiersForFlexibleAttributes(iMESEquipmentPropertyType);
            string = EquipmentPropertyTypeDataHandler.getLocalizedValueOfStrings(list4);
            arrayList.add(new NameValuePair("CardLayout_FlexibleAttributeIdentifiers_Label", string));
        }
        return arrayList;
    }

    private static List<NameValuePair> createProperties(String[] arrstring, EqmLoadMasterDataProxiesContext eqmLoadMasterDataProxiesContext) {
        ArrayList<NameValuePair> arrayList = new ArrayList<NameValuePair>();
        IMESChoiceElement iMESChoiceElement = EquipmentPropertyTypeDataHandler.getUsage(arrstring[usageColumnIdx]);
        AbstractTechnicalEquipmentPropertyType abstractTechnicalEquipmentPropertyType =
                AbstractTechnicalEquipmentPropertyType.getTechnicalEquipmentPropertyTypeByClassName((String) arrstring[classNameColumnIdx]);
        IMESChoiceElement iMESChoiceElement2 = abstractTechnicalEquipmentPropertyType.getTechnicalType();
        if (EnumEqmPropertyTechnicalType.DECIMAL.getChoiceElement().equals((Object) iMESChoiceElement2)) {
            if (EnumEqmPropertyTypeUsage.AUTOMATION.equals((Object) iMESChoiceElement)) {
                long l = Long.parseLong(arrstring[tagGroupDefinitionColumnIdx]);
                Collection collection = eqmLoadMasterDataProxiesContext.getTagIdentifiersForTagGroup(l);
                String string = arrstring[identifierColumnIdx];
                String string2 = EquipmentPropertyTypeDataHandler.getLocalizedValueOfTagDefinitionIdentifiers(collection, string);
                arrayList.add(new NameValuePair("CardLayout_EnabledTags_Label", string2));
            }
        } else if (EnumEqmPropertyTechnicalType.FLEXIBLE_TAG_DEFINITION.getChoiceElement().equals((Object) iMESChoiceElement2)) {
            long l = Long.parseLong(arrstring[tagGroupDefinitionColumnIdx]);
            Collection collection = eqmLoadMasterDataProxiesContext.getTagDataTypesForTagGroup(l);
            String string = EquipmentPropertyTypeDataHandler.getLocalizedValueOfLiveDataTypes(collection);
            arrayList.add(new NameValuePair("CardLayout_LiveDataType_Label", string));
            Collection collection2 = eqmLoadMasterDataProxiesContext.getTagIdentifiersForTagGroup(l);
            string = EquipmentPropertyTypeDataHandler.getLocalizedValueOfStrings(collection2);
            arrayList.add(new NameValuePair("CardLayout_EnabledTagsUnlocalized_Label", string));
            Collection collection3 = eqmLoadMasterDataProxiesContext.getTagUomSymbolsForTagGroup(l);
            string = EquipmentPropertyTypeDataHandler.getLocalizedValueOfUOMSymbols(collection3);
            arrayList.add(new NameValuePair("CardLayout_EngineeringUnits_Label", string));
        } else if (EnumEqmPropertyTechnicalType.FLEXIBLE_ATTRIBUTE_DEFINITION.getChoiceElement().equals((Object) iMESChoiceElement2)) {
            long l = Long.parseLong(arrstring[keyColumnIdx]);
            Collection collection = eqmLoadMasterDataProxiesContext.getAttributeDataTypesForPropertyType(l);
            String string = EquipmentPropertyTypeDataHandler.getLocalizedValueOfDataTypes(collection);
            arrayList.add(new NameValuePair("CardLayout_FlexibleAttributeDataTypes_Label", string));
            Collection collection4 = eqmLoadMasterDataProxiesContext.getAttributeIdentifiersForPropertyType(l);
            string = EquipmentPropertyTypeDataHandler.getLocalizedValueOfStrings(collection4);
            arrayList.add(new NameValuePair("CardLayout_FlexibleAttributeIdentifiers_Label", string));
        }
        return arrayList;
    }

    private static List<String> getIdentifiersForAutomationDecimalEnabledTags(IMESEquipmentPropertyType iMESEquipmentPropertyType) {
        return EquipmentPropertyTypeDataHandler.getIdentifiersForFlexibleTagGroupEnabledTags(iMESEquipmentPropertyType);
    }

    private static List<Short> getLiveDataTypesForFlexibleTagGroup(IMESEquipmentPropertyType iMESEquipmentPropertyType) {
        ArrayList<Short> arrayList = new ArrayList<Short>();
        List list = iMESEquipmentPropertyType.getTagDefinitions();

        // for (TagDefinition tagDefinition : list) {
        // short s = tagDefinition.getLiveDataType();
        // arrayList.add(s);
        // }

        Iterator localIterator = list.iterator();
        while (localIterator.hasNext()) {
            TagDefinition tagDefinition = (TagDefinition) localIterator.next();
            short s = tagDefinition.getLiveDataType();
            arrayList.add(s);
        }
        return arrayList;
    }

    private static List<Short> getDataTypesForFlexibleAttribute(IMESEquipmentPropertyType iMESEquipmentPropertyType) {
        ArrayList<Short> arrayList = new ArrayList<Short>();
        List list = iMESEquipmentPropertyType.getAttributeDefinitions();
        // for (AttributeDefinition attributeDefinition : list) {
        // short s = attributeDefinition.getAttributeDataType();
        // arrayList.add(s);
        // }

        Iterator localIterator = list.iterator();
        while (localIterator.hasNext()) {
            AttributeDefinition attributeDefinition = (AttributeDefinition) localIterator.next();
            short s = attributeDefinition.getAttributeDataType();
            arrayList.add(s);
        }
        return arrayList;
    }

    private static List<String> getUomSymbolsForFlexibleTagGroup(IMESEquipmentPropertyType iMESEquipmentPropertyType) {
        ArrayList<String> arrayList = new ArrayList<String>();
        List list = iMESEquipmentPropertyType.getTagDefinitions();
        // for (TagDefinition tagDefinition : list) {
        // String string = tagDefinition.getEngineeringUnit();
        // arrayList.add(string);
        // }

        Iterator localIterator = list.iterator();
        while (localIterator.hasNext()) {
            TagDefinition tagDefinition = (TagDefinition) localIterator.next();
            String string = tagDefinition.getEngineeringUnit();
            arrayList.add(string);
        }
        return arrayList;
    }

    private static List<String> getIdentifiersForFlexibleTagGroupEnabledTags(IMESEquipmentPropertyType iMESEquipmentPropertyType) {
        ArrayList<String> arrayList = new ArrayList<String>();
        List list = iMESEquipmentPropertyType.getTagDefinitions();
        // for (TagDefinition tagDefinition : list) {
        // String string = tagDefinition.getTagId();
        // arrayList.add(string);
        // }

        Iterator localIterator = list.iterator();
        while (localIterator.hasNext()) {
            TagDefinition tagDefinition = (TagDefinition) localIterator.next();
            String string = tagDefinition.getTagId();
            arrayList.add(string);
        }
        return arrayList;
    }

    private static List<String> getIdentifiersForFlexibleAttributes(IMESEquipmentPropertyType iMESEquipmentPropertyType) {
        ArrayList<String> arrayList = new ArrayList<String>();
        List list = iMESEquipmentPropertyType.getAttributeDefinitions();
        // for (AttributeDefinition attributeDefinition : list) {
        // String string = attributeDefinition.getAttributeIdentifier();
        // arrayList.add(string);
        // }

        Iterator localIterator = list.iterator();
        while (localIterator.hasNext()) {
            AttributeDefinition attributeDefinition = (AttributeDefinition) localIterator.next();
            String string = attributeDefinition.getAttributeIdentifier();
            arrayList.add(string);
        }
        return arrayList;
    }

    private static String getLocalizedValueOfStrings(Collection<String> collection) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : collection) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    private static String getLocalizedValueOfUOMSymbols(Collection<String> collection) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : collection) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            String string2 = StringUtils.isEmpty((CharSequence) string)
                    ? I18nMessageUtility.getLocalizedMessage((String) "ui_MasterDataEditor", (String) "CardLayout_EngineeringUnit_NotAvailable_Label")
                    : string;
            stringBuilder.append(string2);
        }
        return stringBuilder.toString();
    }

    private static String getLocalizedValueOfTagDefinitionIdentifiers(Collection<String> collection, Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : collection) {
            String string2;
            String string3;
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            if ((string2 = EquipmentPropertyTypeAutomationHelper.getMessageIdOfTagName((String) string)) == null) {
                LOGGER.error((Object) ("TagDefinition with identifier " + string
                        + " not supported for  Numeric EquipmentPropertyTypes of property type " + object));
                string3 = "";
            } else {
                string3 = I18nMessageUtility.getLocalizedMessage((String) "ui_MasterDataEditor", (String) string2);
            }
            stringBuilder.append(string3);
        }
        return stringBuilder.toString();
    }

    private static String getLocalizedValueOfDataTypes(Collection<Short> collection) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Short s : collection) {
            IMESChoiceElement iMESChoiceElement;
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            String string = (iMESChoiceElement = EnumEqmPropertyFlexibleAttributeDataType.getChoiceElement((Long) ((Object) s))) == null ? ""
                    : iMESChoiceElement.getLocalizedMessage();
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    private static String getLocalizedValueOfLiveDataTypes(Collection<Short> collection) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Short s : collection) {
            IMESChoiceElement iMESChoiceElement;
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            String string = (iMESChoiceElement = EnumEqmPropertyLiveDataType.getChoiceElement((Long) ((Object) s))) == null ? ""
                    : iMESChoiceElement.getLocalizedMessage();
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    private static IS88EquipmentService getS88EqmService() {
        return (IS88EquipmentService) ServiceFactory.getService(IS88EquipmentService.class);
    }

    public String getErrorMessageIDForPCError(Error error, List<String> list) {
        String string = super.getConstraintErrorMessageID(error, "EqUPropTypeUqIdentifier");
        if (string == null) {
            string = super.getObjectIsReferencedErrorMessageID(error);
        }
        return string;
    }

    public EquipmentPropertyTypeDataHolder createNewObject(String string) {
        // IS88EquipmentService iS88EquipmentService = EquipmentPropertyTypeDataHandler.getS88EqmService();
        // IMESEquipmentPropertyType iMESEquipmentPropertyType =
        // iS88EquipmentService.createEquipmentPropertyType(string);
        // return (EquipmentPropertyTypeDataHolder) this.getDataHolder((IPersistentMESObject)
        // iMESEquipmentPropertyType);

        return ((AbstractMasterDataHandler<EquipmentPropertyTypeDataHolder, IMESEquipmentPropertyType, EquipmentPropertyTypeProxy>) this)
                .getDataHolder(getS88EqmService().createEquipmentPropertyType(string));
    }

    public EquipmentPropertyTypeDataHolder createDuplicate(EquipmentPropertyTypeDataHolder equipmentPropertyTypeDataHolder, String string) {
        // IMESEquipmentPropertyType iMESEquipmentPropertyType = (IMESEquipmentPropertyType)
        // equipmentPropertyTypeDataHolder.getObject();
        // IMESEquipmentPropertyType iMESEquipmentPropertyType2 = (IMESEquipmentPropertyType)
        // iMESEquipmentPropertyType.createCopy(string);
        // return (EquipmentPropertyTypeDataHolder) this.getDataHolder((IPersistentMESObject)
        // iMESEquipmentPropertyType2);
        return ((AbstractMasterDataHandler<EquipmentPropertyTypeDataHolder, IMESEquipmentPropertyType, EquipmentPropertyTypeProxy>) this)
                .getDataHolder((IMESEquipmentPropertyType) equipmentPropertyTypeDataHolder.getObject().createCopy(string));
    }

    private static class EqPropertyTypeDataHolderImpl extends EquipmentPropertyTypeDataHolder {

        public EqPropertyTypeDataHolderImpl(IMESEquipmentPropertyType iMESEquipmentPropertyType) {
            super(iMESEquipmentPropertyType);
        }

    }

    private static class NameValuePair extends Pair<String, String> {
        private NameValuePair(String string, String string2) {

            super(I18nMessageUtility.getLocalizedMessage("ui_MasterDataEditor", string), string2);

            // super((Object) I18nMessageUtility.getLocalizedMessage((String) "ui_MasterDataEditor", string), (Object)
            // string2);

        }

        public String getName() {
            return (String) this.getFirst();
        }

        public String getValue() {
            return (String) this.getSecond();
        }
    }

}