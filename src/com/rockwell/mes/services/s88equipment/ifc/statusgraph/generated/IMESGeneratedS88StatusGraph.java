package com.rockwell.mes.services.s88equipment.ifc.statusgraph.generated;

import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObject;

public interface IMESGeneratedS88StatusGraph extends IMESATObject {
    public static final String ATDEFINITION_NAME = "X_S88StatusGraph";

    public static final String SQL_TABLE_NAME = "AT_X_S88StatusGraph";

    public static final String PROP_NAME_DESCRIPTION = "description";

    public static final String SQL_COL_NAME_DESCRIPTION = "X_description_S";

    public static final String COL_NAME_DESCRIPTION = "X_description";

    public static final String PROP_NAME_DISPLAYNAME = "displayName";

    public static final String SQL_COL_NAME_DISPLAYNAME = "X_displayName_S";

    public static final String COL_NAME_DISPLAYNAME = "X_displayName";

    public static final String PROP_NAME_IDENTIFIER = "identifier";

    public static final String SQL_COL_NAME_IDENTIFIER = "X_identifier_S";

    public static final String COL_NAME_IDENTIFIER = "X_identifier";

    public static final String COL_NAME_NEXTTRANSITIONID = "X_nextTransitionId";

    public static final String PROP_NAME_PURPOSE = "purpose";

    public static final String SQL_COL_NAME_PURPOSE = "X_purpose_I";

    public static final String COL_NAME_PURPOSE = "X_purpose";

    public static final String PROP_NAME_SHORTDESCRIPTION = "shortDescription";

    public static final String SQL_COL_NAME_SHORTDESCRIPTION = "X_shortDescription_S";

    public static final String COL_NAME_SHORTDESCRIPTION = "X_shortDescription";

    //
    public static final String PROP_NAME_PROCESSPACKNAME = "processPackName";

    public static final String SQL_COL_NAME_PROCESSPACK = "processPackName_S";

    public static final String COL_NAME_PROCESSPACKNAME = "processPackName";


    public static final String COL_NAME_STATEPROXY = "X_stateProxy";

    public static final String COL_NAME_TILECOLOR = "X_tileColor";

    public static final String COL_NAME_TILETEXTCOLOR = "X_tileTextColor";

    public String getDescription();

    public void setDescription(String var1);

    //
    String getProcessPackName();

    void setProcessPackName(String p0);

    public String getDisplayName();

    public void setDisplayName(String var1);

    public String getIdentifier();

    public void setIdentifier(String var1);

    public IMESChoiceElement getPurpose();

    public String getPurposeAsMeaning();

    public Long getPurposeAsValue();

    public void setPurpose(IMESChoiceElement var1);

    public void setPurposeAsMeaning(String var1);

    public void setPurposeAsValue(Long var1);

    public String getShortDescription();

    public void setShortDescription(String var1);
}