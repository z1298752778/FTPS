package com.leateck.phase.materialalternativeidentification0010;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.rockwell.mes.apps.ebr.ifc.phase.IPhaseExecutor.Status;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.PhaseExceptionPanelUI;
import com.rockwell.mes.apps.ebr.ifc.swing.ConfirmButton;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseColumnLayout;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseSwingHelper;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.utility.StringUtilsEx;
import com.rockwell.mes.commons.shared.phase.mvc.AbstractPhaseExceptionView0200;
import com.rockwell.mes.commons.shared.phase.mvc.PhaseViewHelper0200;
import com.rockwell.mes.shared.product.material.MaterialModel0710;
import com.rockwell.mes.shared0400.product.ui.basics.impl.PhaseSeparator;
import com.rockwell.mes.shared0400.product.ui.basics.impl.ProductPhaseColumnLayoutPanel;
import com.rockwell.mes.shared0400.product.ui.basics.impl.ProductPhaseColumnLayoutPanel.Column;
import com.rockwell.mes.shared0400.product.ui.basics.impl.ProductPhaseColumnLayoutPanel.Layout;
import com.rockwell.mes.shared0400.product.ui.basics.util.ProductPhaseSwingHelper;

/**
 * Exception view of phase e.g. manual material identification (as part of MVC structure)
 * 
 * @author ikoleva, (c) Copyright 2014 Rockwell Automation Technologies, Inc. All Rights Reserved.
 * 
 */
public class MatIdentExceptionView0710 extends AbstractPhaseExceptionView0200<MatIdentModel0710> {

    private static final long serialVersionUID = 1L;

    /**
     * Exception types provided by the view
     * @author ikoleva
     *
     */
    private enum ExceptionType {
        /** manual identification */
        MANUAL("Manual"),
        /** undo  identification */
        UNDO("Undo");
        
        /** The name of the input field */
        private String controlName;

        /** Default constructor
         * @param argControlName the name of the input field
         */
        private ExceptionType(String argControlName) {
            controlName = argControlName;
        }
    }
    
    private static final String RECORD_WAREHOUSE_ERROR_LABEL = "recordWarehouseErrorLabel";

    private static final String PANEL_RECORD_WAREHOUSE_ERROR_LABEL = RECORD_WAREHOUSE_ERROR_LABEL + "Panel";

    private static final String LABEL_RECORD_WAREHOUSE_ERROR_LABEL = RECORD_WAREHOUSE_ERROR_LABEL + "Label";

    /** Input field for the id of the identified item */
    private JTextField itemInputManual;
    
    /**
     * the input field used for entering item identifier for undo identification manually
     */
    private JTextField itemInputUndo;

    private transient IMatIdentEventListener0710 eventListener;
    
    /**
     * @param theModel the phase model
     * @param theListener the listener that should react to events from this view
     */
    public MatIdentExceptionView0710(MatIdentModel0710 theModel, IMatIdentEventListener0710 theListener) {
        super(theModel);
        eventListener = theListener;
    }
    
    /**
     * @return the id of the identified item id entered by the user for manual identification
     */
    public String getItemIDForManualIdent() {
        return itemInputManual.getText();
    }
    
    /**
     * Clear the input field for manual identification
     */
    public void clearManualInputField() {
        itemInputManual.setText("");
    }
    
    /**
     * @return the if of the identified item entered by the user for undo identification
     */
    public String getItemIDForUndoIdent() { 
        return itemInputUndo.getText();
    }
        
    /**
     * Clear the input field for undo identification
     */
    public void clearUndoInputField() {
        itemInputUndo.setText("");
    }
    
    @Override
    public void createUI() {
        setOpaque(false);
        setEnabled(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        if (getModel().isBlockedByWarehouseError()) {
            JPanel recordWarehouseErrorPanel = createRecordWarehouseErrorPanel(RtPhaseExecutorMatAlterIdent0010.RECORD_WAREHOUSE_ERROR_EVENT);
            add(recordWarehouseErrorPanel);
            return;
        }

        ProductPhaseColumnLayoutPanel undoPanel = createPanel(ExceptionType.UNDO);
        add(undoPanel);
        add(new PhaseSeparator());

        ProductPhaseColumnLayoutPanel manualPanel = createPanel(ExceptionType.MANUAL);
        add(manualPanel);
        add(new PhaseSeparator());

        add(createForceCompletionPanel());
        add(new PhaseSeparator());

        if (getModel().isPhaseBlocked()) {
            PhaseViewHelper0200.disableComponentTree(this);
        }
    }
       

    /**
     * Create a panel containing the UI for the specified exception. 
     * @param exception the exception type
     * 
     * @return the panel for the exception
     */
    private ProductPhaseColumnLayoutPanel createPanel(final ExceptionType exception) {
        ProductPhaseColumnLayoutPanel layoutPanel = new ProductPhaseColumnLayoutPanel(
                Layout.LAYOUT_TWO_1ST_WIDER_COLUMN, Status.ACTIVE);
        
        JPanel firstColumnPanel = new JPanel(new GridBagLayout());

        String instruction = getInstruction(exception);

        JLabel label = ProductPhaseSwingHelper.createJLabel(instruction,
                Layout.LAYOUT_TWO_1ST_WIDER_COLUMN.getColumnWidth(Column.FIRST_COLUMN));
        label.setVerticalAlignment(SwingConstants.TOP);
         
        firstColumnPanel.add(label, ProductPhaseSwingHelper.createLabelWithTextFieldConstraints());
        
        layoutPanel.addToColumn(Column.FIRST_COLUMN, firstColumnPanel, GridBagConstraints.BOTH);

        JPanel layoutColunm2 = new JPanel();
        layoutColunm2.setLayout(new GridBagLayout());

        JTextField inputField = ProductPhaseSwingHelper.createPhaseSwingAlphaNumericTouchField(Status.ACTIVE,
                instruction, JTextField.LEFT);
        inputField.setName(inputField.getName().concat(exception.controlName));
        layoutColunm2.add(inputField, ProductPhaseSwingHelper.createTextFieldConstraints());
        layoutPanel.addToColumn(Column.SECOND_COLUMN, layoutColunm2, GridBagConstraints.BOTH);

        ConfirmButton completeButton = layoutPanel.getConfirmButton();
        switch (exception) {
        case MANUAL:
            itemInputManual = inputField;
            completeButton.addActionListener(e -> eventListener.onManualIdent(getItemIDForManualIdent()));
            break;
            
        case UNDO:
            itemInputUndo = inputField;
            completeButton.addActionListener(e -> eventListener.onUndoIdent(getItemIDForUndoIdent()));
            break;

        default:
            break;
        }
        completeButton.setName("button" + exception.controlName);

        layoutPanel.setUI(new PhaseExceptionPanelUI(this));
        return layoutPanel;
    }

    /**
     * @return panel to record a warehouse error
     */
    private JPanel createRecordWarehouseErrorPanel(final String controlName) {
        JPanel recordWarehouseErrorPanel =
                PhaseSwingHelper.createExceptionPanel(PhaseColumnLayout.Layout.LAYOUT_TWO_1ST_WIDER_COLUMN);
        recordWarehouseErrorPanel.setName(PANEL_RECORD_WAREHOUSE_ERROR_LABEL); // for Marathon
        recordWarehouseErrorPanel.setUI(new PhaseExceptionPanelUI(recordWarehouseErrorPanel));

        String recordWarehouseErrorLabelText =
                I18nMessageUtility.getLocalizedMessage(MaterialModel0710.PHASE_PRODUCT_MATERIAL_MSGPACK, "RecordWarehouseError_Label");
        recordWarehouseErrorPanel.add(PhaseSwingHelper.createMultiLineLabel(recordWarehouseErrorLabelText,
                PhaseColumnLayout.LEFT_COLUMN_WIDTH + PhaseColumnLayout.MIDDLE_COLUMN_WIDTH + PhaseColumnLayout.RIGHT_COLUMN_WIDTH,
                LABEL_RECORD_WAREHOUSE_ERROR_LABEL), PhaseColumnLayout.Column.FIRST_COLUMN);

        ConfirmButton confirmButton = ((PhaseColumnLayout) recordWarehouseErrorPanel.getLayout()).getConfirmButton();
        confirmButton.addActionListener(e -> eventListener.onWarehouseErrorRecordRequest());
        confirmButton.setName("button" + controlName);

        return recordWarehouseErrorPanel;
    }


    private String getInstruction(final ExceptionType exception) {
        final boolean isWarehouseSystemConnected = getModel().isWarehauseApplicationAvailable();
        String instruction = StringUtilsEx.EMPTY;
        switch (exception) {
        case MANUAL:
            final String instructionManualIdentMsgID = isWarehouseSystemConnected ? "LUInstructionManualIdent" : "InstructionManualIdent";
            instruction = I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, instructionManualIdentMsgID);
            break;
        case UNDO:
            final String instructionUndoIdentMsgID = isWarehouseSystemConnected ? "LUInstructionUndoIdent" : "InstructionUndoIdent";
            instruction = I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, instructionUndoIdentMsgID);
            break;
        default:
            break;
        }
        return instruction;
    }

    private Component createForceCompletionPanel() {
        ProductPhaseColumnLayoutPanel layoutPanel = new ProductPhaseColumnLayoutPanel(Layout.LAYOUT_TWO_1ST_WIDER_COLUMN, Status.ACTIVE);

        JPanel firstColumnPanel = new JPanel(new GridBagLayout());
        String instruction = I18nMessageUtility.getLocalizedMessage(RtPhaseExecutorMatAlterIdent0010.MSG_PACK, "InstructionExceptionForceCompletion");
        JLabel label = ProductPhaseSwingHelper.createJLabel(instruction, Layout.LAYOUT_TWO_1ST_WIDER_COLUMN.getColumnWidth(Column.FIRST_COLUMN));
        label.setVerticalAlignment(SwingConstants.TOP);

        firstColumnPanel.add(label, ProductPhaseSwingHelper.createLabelWithTextFieldConstraints());

        layoutPanel.addToColumn(Column.FIRST_COLUMN, firstColumnPanel, GridBagConstraints.BOTH);

        ConfirmButton completeButton = layoutPanel.getConfirmButton();
        completeButton.addActionListener(e -> eventListener.onForceCompletion());
        completeButton.setName("buttonForceCompletion");

        layoutPanel.setUI(new PhaseExceptionPanelUI(this));
        return layoutPanel;
    }

    /**
     * Refresh the view
     */
    public void refreshView() {
        itemInputManual.setText("");
        itemInputUndo.setText("");
    }
}
