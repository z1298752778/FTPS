package com.leateck.phase.wdmaterialidentification0100;

import com.rockwell.mes.apps.ebr.ifc.phase.*;
import com.rockwell.mes.commons.base.ifc.i18n.*;
import com.rockwell.mes.shared.product.wd.*;

public enum WeighingOperationTypeWDMatIdent0610
{
    DISPENSE {
        @Override
        String getLabelCloseTargetExceptionPanel(final IWDMatIdentModel0610 model) {
            return I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "printLabelAndReleaseScaleTxt");
        }

        @Override
        String getAdditionalInfoForCloseTargetExeption(final IWDMatIdentModel0610 model) {
            return I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "closeTargetExceptionAddTxt", new Object[] { model.getTargetSublot().getName() });
        }

        @Override
        boolean handleDetachOperation(final IWDMatIdentModel0610 model) {
            return WDHelper0610.showQuestionDialogYesNo("wd_MaterialIdentification0610", "detachFromOperation_Query", new Object[0]);
        }
    },
    INLINE {
        @Override
        String getLabelCloseTargetExceptionPanel(final IWDMatIdentModel0610 model) {
            return I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "printLabelAndReleaseScaleTxtInline");
        }

        @Override
        String getAdditionalInfoForCloseTargetExeption(final IWDMatIdentModel0610 model) {
            return I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "closeTargetExceptionAddTxtInline", new Object[] { model.getSelectedOSI().getActualQuantity() });
        }

        @Override
        boolean handleDetachOperation(final IWDMatIdentModel0610 model) {
            WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "detachFromOperation_ErrorMsg", new Object[0]);
            return false;
        }
    };

    abstract String getLabelCloseTargetExceptionPanel(final IWDMatIdentModel0610 model);

    abstract String getAdditionalInfoForCloseTargetExeption(final IWDMatIdentModel0610 model);

    abstract boolean handleDetachOperation(final IWDMatIdentModel0610 model);

    public static WeighingOperationTypeWDMatIdent0610 getWeighingOperationType(final IPhaseExecutor executor) {
        if (WeighingOperationType0610.isInlineWeighing(executor)) {
            return WeighingOperationTypeWDMatIdent0610.INLINE;
        }
        return WeighingOperationTypeWDMatIdent0610.DISPENSE;
    }
}
