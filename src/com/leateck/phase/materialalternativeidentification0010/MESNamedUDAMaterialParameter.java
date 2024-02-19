package com.leateck.phase.materialalternativeidentification0010;

import com.rockwell.mes.services.s88.ifc.recipe.IMESMaterialParameter;

import java.math.BigDecimal;


public class MESNamedUDAMaterialParameter {

    private static final String LC_COMBINATION_GROUP = "LC_combinationGroup";
    private static final String LC_REPLACE_RATIO = "LC_replaceRatio";
    private static final String LC_REPLACE_GROUP_NAME = "LC_replaceGroupName";
    private static final String LC_IS_MAIN_PART = "LC_isMainPart";
    private static final String LC_RESTRICTIONMODE = "LC_restrictionMode";

    private IMESMaterialParameter matParam;


    public IMESMaterialParameter getMatParam() {
        return matParam;
    }

    public void setMatParam(IMESMaterialParameter matParam) {
        this.matParam = matParam;
    }

    public MESNamedUDAMaterialParameter(IMESMaterialParameter matParam) {
        this.matParam = matParam;
    }

    public Boolean isMainPart() {
        return (Boolean) matParam.getATRow().getValue(LC_IS_MAIN_PART);
    }

    public String getReplaceGroupName() {
        return (String) matParam.getATRow().getValue(LC_REPLACE_GROUP_NAME);
    }

    public BigDecimal getReplaceRatio() {
        return (BigDecimal) matParam.getATRow().getValue(LC_REPLACE_RATIO);
    }

    public String getCombinationGroup() {
        return (String) matParam.getATRow().getValue(LC_COMBINATION_GROUP);
    }

    public Long getRestrictionMode(){
        return (Long) matParam.getATRow().getValue(LC_RESTRICTIONMODE);
    }
}
