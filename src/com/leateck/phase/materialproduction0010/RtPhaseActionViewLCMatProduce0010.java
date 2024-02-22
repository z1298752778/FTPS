package com.leateck.phase.materialproduction0010;

import com.rockwell.mes.commons.shared.phase.mvc.*;
import com.rockwell.mes.commons.base.ifc.i18n.*;
import com.rockwell.mes.apps.ebr.ifc.phase.*;
import com.rockwell.mes.commons.base.ifc.exceptions.*;
import java.awt.event.*;

import com.rockwell.mes.phase.product.materialproduction.RtPhaseModelMatProduce0710;
import com.rockwell.mes.shared0400.product.ui.basics.impl.*;
import java.awt.*;
import com.rockwell.mes.shared0400.product.ui.basics.util.*;
import javax.swing.*;
import com.rockwell.mes.shared.product.material.*;
import org.apache.commons.lang3.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import com.rockwell.mes.commons.deviation.ifc.exceptionrecording.*;
import java.util.*;
import java.io.*;
import java.util.List;

public class RtPhaseActionViewLCMatProduce0010 extends AbstractPhaseActionView0200<RtPhaseModelLCMatProduce0010> implements Observer
{
    private static final long serialVersionUID = 1L;
    protected static final int MAXIMAL_NUMBER_ACTIONS = 3;
    private ActionType action;
    public static final String KEY_REPRINT = "ReprintLabel";
    public static final String KEY_CANCEL_SUBLOT = "CancelSublot";
    private JTextField inputField;
    protected String labelID;
    private String sublotID;
    private String sublotQty;

    protected ActionType getActionType() {
        return this.action;
    }

    public String getSublotID() {
        return this.inputField.getText();
    }

    public void clearSublotsID() {
        this.inputField.setText("");
    }

    public RtPhaseActionViewLCMatProduce0010(final RtPhaseModelLCMatProduce0010 theModel) {
        super(theModel);
    }

    public void setModel(final RtPhaseModelLCMatProduce0010 theModel) {
        theModel.getExecutor().getMaterialObservable().addObserver(this);
    }

    public List<String> getActionButtonTexts() {
        List<String> buttonTexts = new ArrayList<>(MAXIMAL_NUMBER_ACTIONS);
        buttonTexts.add(I18nMessageUtility.getLocalizedMessage(RtPhaseModelMatProduce0710.MSGPACK, "Reprint_Label"));
        buttonTexts.add(I18nMessageUtility.getLocalizedMessage(RtPhaseModelMatProduce0710.MSGPACK, "CancelSublot_Label"));
        return buttonTexts;
    }

    protected void createActionPanel(final int actionIndex) {
        this.setModel(this.getModel());
        this.setLayout(new BoxLayout(this, 0));
        this.setOpaque(false);
        final boolean equals = this.getModel().getStatus().equals((Object)IPhaseExecutor.Status.ACTIVE);
        JPanel panel;
        switch (actionIndex) {
            case 0: {
                panel = this.createUI(ActionType.Reprint);
                break;
            }
            case 1: {
                panel = this.createUI(ActionType.cancelSublot);
                break;
            }
            default: {
                throw new MESRuntimeException("Unsupported index " + actionIndex);
            }
        }
        panel.setEnabled(equals);
        panel.setOpaque(false);
        this.add(panel);
    }

    protected JPanel createUI(final ActionType actionType) {
        this.action = actionType;
        final ProductPhaseColumnLayoutPanel productPhaseColumnLayoutPanel = new ProductPhaseColumnLayoutPanel(ProductPhaseColumnLayoutPanel.Layout.LAYOUT_TWO_1ST_WIDER_COLUMN, IPhaseExecutor.Status.ACTIVE);
        final JPanel panel = new JPanel(new GridBagLayout());
        final JLabel jLabel = ProductPhaseSwingHelper.createJLabel(this.getInstruction(), ProductPhaseColumnLayoutPanel.Layout.LAYOUT_TWO_1ST_WIDER_COLUMN.getColumnWidth(ProductPhaseColumnLayoutPanel.Column.FIRST_COLUMN));
        jLabel.setVerticalAlignment(1);
        panel.add(jLabel, ProductPhaseSwingHelper.createLabelWithTextFieldConstraints());
        productPhaseColumnLayoutPanel.addToColumn(ProductPhaseColumnLayoutPanel.Column.FIRST_COLUMN, panel, 1);
        (this.inputField = ProductPhaseSwingHelper.createPhaseSwingAlphaNumericTouchField(IPhaseExecutor.Status.ACTIVE, this.getInstruction(), 2)).setName(this.inputField.getName().concat("inputField"));
        if (this.action == ActionType.Reprint) {
            this.inputField.setName(this.inputField.getName().concat("Reprint"));
        }
        else {
            this.inputField.setName(this.inputField.getName().concat("CancelSublot"));
        }
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel2.add(this.inputField, ProductPhaseSwingHelper.createTextFieldConstraints());
        productPhaseColumnLayoutPanel.addToColumn(ProductPhaseColumnLayoutPanel.Column.SECOND_COLUMN, (JComponent)panel2, 1);
        class ConfirmButtonListener implements ActionListener
        {
            @Override
            public void actionPerformed(final ActionEvent event) {
                RtPhaseActionViewLCMatProduce0010.this.handleCompleteBtnPushed();
            }
        }
        productPhaseColumnLayoutPanel.getConfirmButton().addActionListener(new ConfirmButtonListener());
        return productPhaseColumnLayoutPanel;
    }

    public void update(final Observable o, final Object arg) {
        if (!(o instanceof MaterialObservable0710)) {
            return;
        }
        final MaterialObservable0710 materialObservable0710 = (MaterialObservable0710)o;
        switch ((MaterialObservable0710.Event)arg) {
            case OBSERVER_ADDED: {
                if (materialObservable0710.getObserverToAdd() instanceof RtPhaseActionViewLCMatProduce0010) {
                    this.getModel().getExecutor().getMaterialObservable().deleteObserver((Observer)this);
                    break;
                }
                break;
            }
            case EXCEPTION_CANCELED: {
                if (this.checkExceptionRelevant(materialObservable0710.getCanceledCheckKey())) {
                    this.labelID = null;
                    this.getModel().getExecutor().getView().redraw();
                    this.getModel().getExecutor().getView().setEnabled(true);
                    break;
                }
                break;
            }
            case EXCEPTION_IN_TRANS: {
                if (this.checkExceptionRelevant(materialObservable0710.getTransactionCheckKey())) {
                    try {
                        this.getModel().getExecutor().getView().setWaiting(true);
                        if (this.labelID != null && this.getActionType().equals(ActionType.Reprint)) {
                            this.getModel().getExecutor().reprint(this.labelID);
                        }
                        if (this.getActionType().equals(ActionType.cancelSublot)) {
                            this.getModel().getExecutor().cancelSublot(this.sublotID);
                        }
                    }
                    finally {
                        this.getModel().getExecutor().getView().setWaiting(false);
                        this.getModel().getExecutor().getView().redraw();
                    }
                    break;
                }
                break;
            }
        }
    }

    private String getInstruction() {
        switch (this.getActionType()) {
            case Reprint: {
                return I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "InstructionReprint_Label");
            }
            case cancelSublot: {
                return I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "InstructionCancelSublot_Label");
            }
            default: {
                return "";
            }
        }
    }

    private boolean checkExceptionRelevant(final String checkKey) {
        return (this.getActionType().equals(ActionType.Reprint) && "ReprintLabel".equals(checkKey)) || (this.getActionType().equals(ActionType.cancelSublot) && "CancelSublot".equals(checkKey));
    }

    private void loadLabelIDAndSublotQty() {
        final String[] loadLabelIDAndSublotQty = this.getModel().loadLabelIDAndSublotQty(this.sublotID);
        if (loadLabelIDAndSublotQty != null && loadLabelIDAndSublotQty.length > 0) {
            this.labelID = loadLabelIDAndSublotQty[0];
            this.sublotQty = loadLabelIDAndSublotQty[1];
        }
    }

    private void handleCompleteBtnPushed() {
        this.sublotID = this.getSublotID();
        this.clearSublotsID();
        if (StringUtils.isEmpty(this.sublotID)) {
            ProductPhaseSwingHelper.showErrorDlg(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "ReprintEmptyVal_Txt"));
            return;
        }
        this.loadLabelIDAndSublotQty();
        if (this.labelID == null && this.getActionType().equals(ActionType.Reprint)) {
            ProductPhaseSwingHelper.showErrorDlg(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "ReprintLabelNotFound_Txt"));
            return;
        }
        if (this.labelID == null && this.getActionType().equals(ActionType.cancelSublot)) {
            ProductPhaseSwingHelper.showErrorDlg(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "SublotNotFoundToCancel_Txt"));
            return;
        }
        if (this.checkSublotIDAvailabilityInCurrentInstance()) {
            if (this.inputField.getName().equals("phaseEditinputFieldCancelSublot")) {
                ProductPhaseSwingHelper.showErrorDlg(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "SublotNotFoundInCurrentInstance_Txt"));
            }
            else {
                ProductPhaseSwingHelper.showErrorDlg(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "SublotNotFoundInCurrentInstanceToReprint_Txt"));
            }
            return;
        }
        this.setEnabled(false);
        final StringBuilder sb = new StringBuilder();
        IMESExceptionRecord.RiskClass riskClass;
        String s;
        String s2;
        switch (this.getActionType()) {
            case Reprint: {
                riskClass = this.getModel().getRiskClassActionReprint();
                if (this.getModel().getMsgActionReprint() != null) {
                    sb.append(this.getModel().getMsgActionReprint());
                }
                s = "ReprintLabel";
                s2 = I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "Reprint_Msg", new Object[] { this.sublotID });
                break;
            }
            case cancelSublot: {
                riskClass = this.getModel().getRiskClassActionCancelSublot();
                if (this.getModel().getMsgActionCancelSublot() != null) {
                    sb.append(this.getModel().getMsgActionCancelSublot());
                }
                s = "CancelSublot";
                s2 = I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "CancelSublot_Msg", new Object[] { this.sublotID, this.sublotQty });
                break;
            }
            default: {
                throw new MESRuntimeException("View mode not supported");
            }
        }
        if (sb.length() > 0) {
            sb.append(StringConstants.LINE_BREAK);
        }
        sb.append(s2);
        this.getModel().getExecutor().displayException(s, riskClass, sb.toString());
    }

    private boolean checkSublotIDAvailabilityInCurrentInstance() {
        boolean b = true;
        for (final MESRtPhaseDataLCMatProduce0010 mesRtPhaseDataMatProduce0710 : this.getModel().getAllRtPhaseDataWithoutSummary()) {
            if (mesRtPhaseDataMatProduce0710.getSublotID() != null && mesRtPhaseDataMatProduce0710.getSublotID().equals(this.sublotID)) {
                b = false;
                break;
            }
        }
        return b;
    }

    protected enum ActionType
    {
        Reprint,
        cancelSublot;
    }
}
