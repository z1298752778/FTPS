package com.rockwell.mes.services.s88equipment.ifc.generated;

import com.datasweep.compatibility.client.FlexibleStateModel;
import com.datasweep.compatibility.client.UnitOfMeasure;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceList;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObject;
import com.rockwell.mes.commons.livedata.ifc.IMESTagGroupDefinition;

public interface IMESGeneratedEquipmentPropertyType extends IMESATObject {
    public static final String ATDEFINITION_NAME = "X_EquipmentPropertyType";

    public static final String SQL_TABLE_NAME = "AT_X_EquipmentPropertyType";

    public static final String PROP_NAME_AUTOMATICFSM = "automaticFSM";

    public static final String SQL_COL_NAME_AUTOMATICFSM = "X_automaticFSM_128";

    public static final String COL_NAME_AUTOMATICFSM = "X_automaticFSM";

    public static final String PROP_NAME_CAPABILITY = "capability";

    public static final String SQL_COL_NAME_CAPABILITY = "X_capability_I";

    public static final String COL_NAME_CAPABILITY = "X_capability";

    public static final String PROP_NAME_CHOICELIST = "choiceList";

    public static final String SQL_COL_NAME_CHOICELIST = "X_choiceList_64";

    public static final String COL_NAME_CHOICELIST = "X_choiceList";

    public static final String PROP_NAME_CHOICELISTNAME = "choiceListName";

    public static final String SQL_COL_NAME_CHOICELISTNAME = "X_choiceListName_S";

    public static final String COL_NAME_CHOICELISTNAME = "X_choiceListName";

    public static final String PROP_NAME_CLASSNAME = "className";

    public static final String SQL_COL_NAME_CLASSNAME = "X_className_S";

    public static final String COL_NAME_CLASSNAME = "X_className";

    public static final String PROP_NAME_DESCRIPTION = "description";

    public static final String SQL_COL_NAME_DESCRIPTION = "X_description_S";

    public static final String COL_NAME_DESCRIPTION = "X_description";

    public static final String PROP_NAME_ENGINEERINGUNIT = "engineeringUnit";

    public static final String SQL_COL_NAME_ENGINEERINGUNIT = "X_engineeringUnit_91";

    public static final String COL_NAME_ENGINEERINGUNIT = "X_engineeringUnit";

    public static final String PROP_NAME_FSMRELATIONSHIPNAME = "fsmRelationshipName";

    public static final String SQL_COL_NAME_FSMRELATIONSHIPNAME = "X_fsmRelationshipName_S";

    public static final String COL_NAME_FSMRELATIONSHIPNAME = "X_fsmRelationshipName";

    public static final String PROP_NAME_IDENTIFIER = "identifier";

    public static final String SQL_COL_NAME_IDENTIFIER = "X_identifier_S";

    public static final String COL_NAME_IDENTIFIER = "X_identifier";

    public static final String PROP_NAME_LIVEDATETYPE = "liveDateType";

    public static final String SQL_COL_NAME_LIVEDATETYPE = "X_liveDateType_I";

    public static final String COL_NAME_LIVEDATETYPE = "X_liveDateType";

    public static final String PROP_NAME_OBJECTIDENTIFIER = "objectIdentifier";

    public static final String SQL_COL_NAME_OBJECTIDENTIFIER = "X_objectIdentifier_S";

    public static final String COL_NAME_OBJECTIDENTIFIER = "X_objectIdentifier";

    public static final String PROP_NAME_OBJECTTYPE = "objectType";

    public static final String SQL_COL_NAME_OBJECTTYPE = "X_objectType_I";

    public static final String COL_NAME_OBJECTTYPE = "X_objectType";

    public static final String PROP_NAME_PURPOSE = "purpose";

    public static final String SQL_COL_NAME_PURPOSE = "X_purpose_I";

    public static final String COL_NAME_PURPOSE = "X_purpose";

    public static final String PROP_NAME_SHORTDESCRIPTION = "shortDescription";

    public static final String SQL_COL_NAME_SHORTDESCRIPTION = "X_shortDescription_S";

    public static final String COL_NAME_SHORTDESCRIPTION = "X_shortDescription";

    public static final String PROP_NAME_STRINGVALUE = "stringValue";

    public static final String SQL_COL_NAME_STRINGVALUE = "X_stringValue_S";

    public static final String COL_NAME_STRINGVALUE = "X_stringValue";

    public static final String PROP_NAME_TAGGROUPDEFINITION = "tagGroupDefinition";

    public static final String SQL_COL_NAME_TAGGROUPDEFINITION = "X_tagGroupDefinition_64";

    public static final String COL_NAME_TAGGROUPDEFINITION = "X_tagGroupDefinition";

    public static final String PROP_NAME_TECHNICALTYPE = "technicalType";

    public static final String SQL_COL_NAME_TECHNICALTYPE = "X_technicalType_I";

    public static final String COL_NAME_TECHNICALTYPE = "X_technicalType";

    public static final String PROP_NAME_USAGE = "usage";

    public static final String SQL_COL_NAME_USAGE = "X_usage_I";

    public static final String COL_NAME_USAGE = "X_usage";

    //
    public static final String PROP_NAME_PROCESSPACKNAME = "processPackName";

    public static final String SQL_COL_NAME_PROCESSPACK = "processPackName_S";

    public static final String COL_NAME_PROCESSPACKNAME = "processPackName";

    //
    String getProcessPackName();

    void setProcessPackName(String p0);

    public FlexibleStateModel getAutomaticFSM();

    public void setAutomaticFSM(FlexibleStateModel var1);

    public IMESChoiceElement getCapability();

    public String getCapabilityAsMeaning();

    public Long getCapabilityAsValue();

    public void setCapability(IMESChoiceElement var1);

    public void setCapabilityAsMeaning(String var1);

    public void setCapabilityAsValue(Long var1);

    public IMESChoiceList getChoiceList();

    public void setChoiceList(IMESChoiceList var1);

    public String getChoiceListName();

    public void setChoiceListName(String var1);

    public String getClassName();

    public void setClassName(String var1);

    public String getDescription();

    public void setDescription(String var1);

    public UnitOfMeasure getEngineeringUnit();

    public void setEngineeringUnit(UnitOfMeasure var1);

    public String getFsmRelationshipName();

    public void setFsmRelationshipName(String var1);

    public String getIdentifier();

    public void setIdentifier(String var1);

    public IMESChoiceElement getLiveDateType();

    public String getLiveDateTypeAsMeaning();

    public Long getLiveDateTypeAsValue();

    public void setLiveDateType(IMESChoiceElement var1);

    public void setLiveDateTypeAsMeaning(String var1);

    public void setLiveDateTypeAsValue(Long var1);

    public String getObjectIdentifier();

    public void setObjectIdentifier(String var1);

    public Long getObjectType();

    public void setObjectType(Long var1);

    public IMESChoiceElement getPurpose();

    public String getPurposeAsMeaning();

    public Long getPurposeAsValue();

    public void setPurpose(IMESChoiceElement var1);

    public void setPurposeAsMeaning(String var1);

    public void setPurposeAsValue(Long var1);

    public String getShortDescription();

    public void setShortDescription(String var1);

    public String getStringValue();

    public void setStringValue(String var1);

    public IMESTagGroupDefinition getTagGroupDefinition();

    public void setTagGroupDefinition(IMESTagGroupDefinition var1);

    public IMESChoiceElement getTechnicalType();

    public String getTechnicalTypeAsMeaning();

    public Long getTechnicalTypeAsValue();

    public void setTechnicalType(IMESChoiceElement var1);

    public void setTechnicalTypeAsMeaning(String var1);

    public void setTechnicalTypeAsValue(Long var1);

    public IMESChoiceElement getUsage();

    public String getUsageAsMeaning();

    public Long getUsageAsValue();

    public void setUsage(IMESChoiceElement var1);

    public void setUsageAsMeaning(String var1);

    public void setUsageAsValue(Long var1);
}