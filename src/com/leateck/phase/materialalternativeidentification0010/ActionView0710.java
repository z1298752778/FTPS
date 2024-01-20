package com.leateck.phase.materialalternativeidentification0010;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.rockwell.mes.apps.ebr.ifc.phase.IPhaseExecutor.Status;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.utility.StringUtilsEx;
import com.rockwell.mes.commons.shared.phase.mvc.AbstractPhaseActionView0200;
import com.rockwell.mes.shared0400.product.ui.basics.impl.ProductPhaseColumnLayoutPanel;
import com.rockwell.mes.shared0400.product.ui.basics.impl.ProductPhaseColumnLayoutPanel.Column;
import com.rockwell.mes.shared0400.product.ui.basics.impl.ProductPhaseColumnLayoutPanel.Layout;
import com.rockwell.mes.shared0400.product.ui.basics.util.ProductPhaseSwingHelper;

/**
 * A view for additional/undo identification actions (as part of MVC structure)
 * 
 * @author ikoleva, (c) Copyright 2014 Rockwell Automation Technologies, Inc. All Rights Reserved.
 * 
 */
public class ActionView0710 extends AbstractPhaseActionView0200<MatIdentModel0710> {
    private static final long serialVersionUID = 1L;

    /** additional identification */
    public static final int ADDITIONAL_IDENT = 0;

    /** undo identification */
    public static final int UNDO_IDENT = 1;
        
    /** the type of the action for which the view is created */
    private int actionType;
    
    /** the input field used for entering barcode manually */
    private JTextField inputField;

    private transient IMatIdentEventListener0710 eventListener;
    
    /**
     * @param theModel the phase model
     * @param theListener the listener that should react to events from this view
     */
    public ActionView0710(MatIdentModel0710 theModel, IMatIdentEventListener0710 theListener) {
        super(theModel);
        eventListener = theListener;
    }

    /**
     * @return the type of the action for which the view is created
     */
    public int getActionType() {
        return actionType;
    }
    
    /**
     * @return the identifier entered by the user
     */
    public String getItemId() { 
        return inputField.getText();    
    }
    
    /**
     * Clear the item id input field
     */
    public void clearItemId() {
        inputField.setText(StringUtilsEx.EMPTY);
    }
    
    @Override
    protected List<String> getActionButtonTexts() {
        List<String> buttonTexts = new ArrayList<>();
        buttonTexts.add(I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "BtnTextAdditional"));
        buttonTexts.add(I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "BtnTextUndo"));
        return buttonTexts;
    }

    /**
     * @return the instruction for the action
     */
    private String getInstruction() {
        boolean isWarehouseSystemConnected = getModel().isWarehauseApplicationAvailable();
        switch (actionType) {
        case ADDITIONAL_IDENT:
            final String instructionAdditionalIdent = isWarehouseSystemConnected ? "LUInstructionAdditionalIdent" : "InstructionAdditionalIdent";
            return I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, instructionAdditionalIdent);
        case UNDO_IDENT:
            final String instructionUndoIdent = isWarehouseSystemConnected ? "LUInstructionUndoIdent" : "InstructionUndoIdent";
            return I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, instructionUndoIdent);
        default:
            return StringUtilsEx.EMPTY;

        }
    }

    @Override
    public void createActionPanel(final int actionIndex) {
        setOpaque(false);
        ProductPhaseColumnLayoutPanel layoutPanel = new ProductPhaseColumnLayoutPanel(
                Layout.LAYOUT_TWO_1ST_WIDER_COLUMN, Status.ACTIVE);
        layoutPanel.setOpaque(false);

        this.actionType = actionIndex;

        // instruction label
        JPanel firstColumnPanel = new JPanel(new GridBagLayout());        
        JLabel label = ProductPhaseSwingHelper.createJLabel(getInstruction(),
                Layout.LAYOUT_TWO_1ST_WIDER_COLUMN.getColumnWidth(Column.FIRST_COLUMN));
        label.setVerticalAlignment(SwingConstants.TOP);        
        firstColumnPanel.add(label, ProductPhaseSwingHelper.createLabelWithTextFieldConstraints());
        
        layoutPanel.addToColumn(Column.FIRST_COLUMN, firstColumnPanel, GridBagConstraints.BOTH);
        
        // input field
        inputField = ProductPhaseSwingHelper
                .createPhaseSwingAlphaNumericTouchField(Status.ACTIVE, getInstruction(), JTextField.LEFT);
        inputField.setName(inputField.getName().concat("Input"));
        if (actionType == ADDITIONAL_IDENT) {
            inputField.setName(inputField.getName().concat("PostIdentification"));
        } else {
            inputField.setName(inputField.getName().concat("PostUndo"));
        }
        JPanel column2 = new JPanel();
        column2.setLayout(new GridBagLayout());

        column2.add(inputField, ProductPhaseSwingHelper.createTextFieldConstraints());
        layoutPanel.addToColumn(Column.SECOND_COLUMN, column2, GridBagConstraints.BOTH);

        /** confirm button listener */
        class ConfirmButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (actionIndex == ADDITIONAL_IDENT) {
                    eventListener.onAdditionalIdent(getItemId());
                } else {
                    eventListener.onUndoIdentAction(getItemId());
                }
            }
        }
        layoutPanel.getConfirmButton().addActionListener(new ConfirmButtonListener());
        add(layoutPanel);
    }
    
    /**
     * Refresh the view
     */
    public void refreshView() {
        inputField.setText(StringUtilsEx.EMPTY);
    }
}
