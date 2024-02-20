package com.leateck.phase.materialproduction0010;

import java.awt.*;
import com.rockwell.mes.commons.shared.phase.mvc.*;
import com.rockwell.mes.apps.ebr.ifc.swing.*;
import javax.swing.plaf.*;
import com.rockwell.mes.commons.base.ifc.i18n.*;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.*;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.*;
import com.rockwell.mes.apps.ebr.ifc.phase.*;
import com.rockwell.mes.commons.parameter.excptenablenodef.*;
import javax.swing.*;
import com.rockwell.mes.commons.base.ifc.choicelist.*;
import org.apache.commons.lang3.*;
import com.rockwell.mes.commons.base.ifc.utility.*;

public class RtPhaseExceptionViewLCMatProduce0010 extends AbstractPhaseExceptionView0200<RtPhaseModelLCMatProduce0010>
{
    private static final long serialVersionUID = 1L;
    private static final String RECORD_WAREHOUSE_ERROR_LABEL = "recordWarehouseErrorLabel";
    private static final String PANEL_RECORD_WAREHOUSE_ERROR_LABEL = "recordWarehouseErrorLabelPanel";
    private static final String LABEL_RECORD_WAREHOUSE_ERROR_LABEL = "recordWarehouseErrorLabelLabel";

    public RtPhaseExceptionViewLCMatProduce0010(final RtPhaseModelLCMatProduce0010 theModel) {
        super(theModel);
    }

    protected void createUI() {
        this.setLayout(new BoxLayout(this, 1));
        final boolean blockedByWarehouseError = this.getModel().isBlockedByWarehouseError();
        final JPanel recordWarehouseErrorPanel = this.createRecordWarehouseErrorPanel();
        if (!blockedByWarehouseError) {
            PhaseViewHelper0200.disableComponentTree(new Component[] { recordWarehouseErrorPanel });
        }
        this.add(recordWarehouseErrorPanel);
    }

    private JPanel createRecordWarehouseErrorPanel() {
        final JPanel exceptionPanel = PhaseSwingHelper.createExceptionPanel(PhaseColumnLayout.Layout.LAYOUT_TWO_1ST_WIDER_COLUMN);
        exceptionPanel.setName("recordWarehouseErrorLabelPanel");
        exceptionPanel.setUI(new PhaseExceptionPanelUI(exceptionPanel));
        exceptionPanel.add(PhaseSwingHelper.createMultiLineLabel(I18nMessageUtility.getLocalizedMessage("PhaseProductMaterial0710", "RecordWarehouseError_Label"), 200 + PhaseColumnLayout.MIDDLE_COLUMN_WIDTH + PhaseColumnLayout.RIGHT_COLUMN_WIDTH, "recordWarehouseErrorLabelLabel"), PhaseColumnLayout.Column.FIRST_COLUMN);
        this.configureConfirmButton(exceptionPanel, "recordWarehouseError");
        return exceptionPanel;
    }

    private void configureConfirmButton(final JPanel exceptionPanel, final String checkKey) {
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }

    public static int displayParametrizedExceptionDialogAndReturnResult(final IMESExceptionRecord.RiskClass risk, final String exceptionCheckKey, final AbstractPhaseExecutor executor, final String dialogMsg, final String additionalInfo) {
        return PhaseSystemTriggeredExceptionHandler.recordException(executor, dialogMsg, additionalInfo, risk, exceptionCheckKey);
    }

    public static void displayParametrizedExceptionDialogMsg(final String parameterName, final String exceptionCheckKey, final AbstractPhaseExecutor executor, final String additionalInfo, final String exceptionMessage) {
        final MESParamExcpEnableNDef0200 mesParamExcpEnableNDef0200 = (MESParamExcpEnableNDef0200)executor.getProcessParameterData((Class)MESParamExcpEnableNDef0200.class, parameterName);
        displayParametrizedExceptionDialogMsg(buildExceptionText(mesParamExcpEnableNDef0200, additionalInfo), getRiskAssessmentObject(mesParamExcpEnableNDef0200), exceptionCheckKey, executor, exceptionMessage);
    }

    public static void displayParametrizedExceptionDialogMsg(final String exceptionText, final IMESExceptionRecord.RiskClass risk, final String exceptionCheckKey, final AbstractPhaseExecutor executor, final String exceptionMessage) {
        SwingUtilities.invokeLater(() -> PhaseSystemTriggeredExceptionHandler.recordException(executor, exceptionMessage, exceptionText, risk, exceptionCheckKey));
    }

    public static IMESExceptionRecord.RiskClass getRiskAssessmentObject(final MESParamExcpEnableNDef0200 param) {
        return IMESExceptionRecord.RiskClass.valueOf(MESChoiceListHelper.getChoiceElement("RiskClass", param.getRiskAssessment()));
    }

    public static String buildExceptionText(final MESParamExcpEnableNDef0200 param, final String additionalInfo) {
        final StringBuilder sb = new StringBuilder();
        final String message = param.getMessage();
        if (message != null) {
            sb.append(message);
        }
        if (StringUtils.isNotEmpty(additionalInfo)) {
            if (sb.length() != 0) {
                sb.append(StringConstants.LINE_BREAK);
            }
            sb.append(additionalInfo);
        }
        return sb.toString();
    }
}
