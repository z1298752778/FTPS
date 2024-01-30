package com.leateck.phase.accountalternativematerial0010;

import com.rockwell.mes.services.s88.ifc.recipe.IMESMaterialParameter;

import java.math.BigDecimal;

/**
 * 获取物料配置参数
 *
 */
public class MESNamedUDAMaterialParameter {

    private static final String LC_COMBINATION_GROUP = "LC_combinationGroup";//组合组号
    private static final String LC_REPLACE_RATIO = "LC_replaceRatio";//替代比例
    private static final String LC_REPLACE_GROUP_NAME = "LC_replaceGroupName";//替代组号
    private static final String LC_IS_MAIN_PART = "LC_isMainPart";//是否主料

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
}
