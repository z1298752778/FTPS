package com.leateck.phase.accountalternativematerial0010;

import com.rockwell.mes.services.s88.ifc.recipe.IMESMaterialParameter;

import java.math.BigDecimal;

/**
 * @author: dustin_zhou
 * @title: UDAMESMaterialParameter
 * @projectName: sinovac_beijing
 * @description: TODO
 * @date: 2022/3/2 14:24
 */
public class MESNamedUDAMaterialParameter {

    private static final String SCL_COMBINATION_GROUP = "SCL_combinationGroup";
    private static final String SCL_REPLACE_RATIO = "SCL_replaceRatio";
    private static final String SCL_REPLACE_GROUP_NAME = "SCL_replaceGroupName";
    private static final String SCL_IS_MAIN_PART = "SCL_isMainPart";

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
        return (Boolean) matParam.getATRow().getValue(SCL_IS_MAIN_PART);
    }

    public String getReplaceGroupName() {
        return (String) matParam.getATRow().getValue(SCL_REPLACE_GROUP_NAME);
    }

    public BigDecimal getReplaceRatio() {
        return (BigDecimal) matParam.getATRow().getValue(SCL_REPLACE_RATIO);
    }

    public String getCombinationGroup() {
        return (String) matParam.getATRow().getValue(SCL_COMBINATION_GROUP);
    }
}
