package com.leateck.phase.accountalternativematerial0010;

import com.datasweep.compatibility.client.MeasuredValue;
import com.datasweep.compatibility.client.OrderStepInput;
import com.datasweep.compatibility.client.Sublot;
import com.datasweep.plantops.common.measuredvalue.IMeasuredValue;
import com.datasweep.plantops.common.measuredvalue.IUnitOfMeasure;
import com.rockwell.mes.commons.base.ifc.exceptions.MESRuntimeException;
import com.rockwell.mes.commons.base.ifc.functional.MeasuredValueUtilities;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.shared.product.material.AbstractMaterialDAO0710;
import com.rockwell.mes.shared.product.material.AccountMaterialDAO0710;

/*
* 设置界面message
* */
public class LcAccountMaterialDAO0710 extends AccountMaterialDAO0710 {
    public static final String MSG_PACK = "Lc_PhaseProductAccountMaterial0710";
    public static final  String ABNORMAL_MATERIAL_COMBINATIONRATIO = "Abnormal material combination ratio";
    public static final  String UNCONSUMED_SUBLOT = "Unconsumed sublot";
    public static final String Material_Balance_Check_Configuration = "Material Balance check configuration";
    public static final String Position_And_Material_Balance = "Position And Material Balance";

    public LcAccountMaterialDAO0710() {
    }



    public static String getConsumedLabel() {
        return I18nMessageUtility.getLocalizedMessage(MSG_PACK, "ConsumedLabel");
    }
    public static String getSampledLabel() {
        return I18nMessageUtility.getLocalizedMessage(MSG_PACK, "SampledLabel");
    }

    public static String getWastedLabel() {
        return I18nMessageUtility.getLocalizedMessage(MSG_PACK, "WastedLabel");
    }
    public static String getReturnedLabel() {
        return I18nMessageUtility.getLocalizedMessage(MSG_PACK, "ReturnedLabel");
    }

    public static String getQuantitiesLabelText(AccountType accountType){
        switch (accountType.getControlName()){
            case "Consumed" : return getConsumedLabel();
            case "Sampled" : return getSampledLabel();
            case "Wasted" : return getWastedLabel();
            case "Returned" : return getReturnedLabel();
            default: return "";
        }
    }

}

