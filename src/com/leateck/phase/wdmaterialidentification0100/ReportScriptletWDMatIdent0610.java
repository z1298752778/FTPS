package com.leateck.phase.wdmaterialidentification0100;

import com.rockwell.mes.shared.product.wd.*;
import org.apache.commons.lang3.*;
import com.rockwell.mes.commons.base.ifc.i18n.*;

public class ReportScriptletWDMatIdent0610 extends AbstractPhaseScriptlet0610
{
    public String getMaterialLabel() {
        return this.getLocalizedMessage("ReportLabel_Material");
    }

    public String getMaterialValue(final Object materialIdent, final Object materialDescr, final String defaultText) {
        if (StringUtils.isEmpty((CharSequence)materialIdent.toString()) && StringUtils.isEmpty((CharSequence)materialDescr.toString())) {
            return defaultText;
        }
        String string;
        if (StringUtils.isEmpty((CharSequence)materialIdent.toString())) {
            string = defaultText;
        }
        else {
            string = materialIdent.toString();
        }
        String string2;
        if (StringUtils.isEmpty((CharSequence)materialDescr.toString())) {
            string2 = defaultText;
        }
        else {
            string2 = materialDescr.toString();
        }
        return string + " / " + string2;
    }

    public String getMaterialTypeLabel() {
        return this.getLocalizedMessage("ReportLabel_MaterialType");
    }

    public String getWeighingTypeLabel() {
        return this.getLocalizedMessage("ReportLabel_WeighingType");
    }

    public String getBatchLabel() {
        return this.getLocalizedMessage("ReportLabel_Batch");
    }

    public String getSublotLabel() {
        return this.getLocalizedMessage("ReportLabel_Sublot");
    }

    public String getContainerLabel() {
        return this.getLocalizedMessage("ReportLabel_Container");
    }

    public String getOriginalPlannedQuantityLabel() {
        return this.getLocalizedMessage("ReportLabel_OriginalPlannedQuantity");
    }

    public String getPlannedQuantityLabel() {
        return this.getLocalizedMessage("ReportLabel_PlannedQuantity");
    }

    public String getNominalQuantityLabel() {
        return this.getLocalizedMessage("ReportLabel_NominalQuantity");
    }

    public String getPlannedPotencyLabel() {
        return this.getLocalizedMessage("ReportLabel_PlannedPotency");
    }

    public String getActualPotencyLabel() {
        return this.getLocalizedMessage("ReportLabel_ActualPotency");
    }

    public String getActualQuantityLabel() {
        return this.getLocalizedMessage("ReportLabel_ActualQuantity");
    }

    public String getWeighingMethodLabel() {
        return this.getLocalizedMessage("ReportLabel_WeighingMethod");
    }

    public String getTargetSublotLabel() {
        return this.getLocalizedMessage("ReportLabel_TargetSublot");
    }

    public String getBOMPositionLabel() {
        return this.getLocalizedMessage("ReportLabel_BOMPosition");
    }

    public String getDifferentBatchIdentifiedText() {
        return this.getLocalizedMessage("differentBatchWasScannedTxt");
    }

    public String getTargetContainerClosedText() {
        return this.getLocalizedMessage("targetContainerClosedTxt");
    }

    public String getIdentifiedOnlyLabel() {
        return this.getLocalizedMessage("identifiedOnlyLabel");
    }

    protected String getLocalizedMessage(final String msgId) {
        return I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", msgId);
    }
}
