package com.leateck.phase.materialalternativeidentification0010;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.apache.commons.lang3.StringUtils;

import com.rockwell.mes.apps.ebr.ifc.phase.IPhaseExecutor.Status;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.SwingPhasePanelFocusTraversalPolicy;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.UIConstants;
import com.rockwell.mes.apps.ebr.ifc.swing.ConfirmButton;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseSwingHelper;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.shared.phase.components.swing.PhaseRadioButton0300;
import com.rockwell.mes.commons.shared.phase.mvc.AbstractPhaseMainView0200;
import com.rockwell.mes.services.s88.ifc.recipe.PrivilegeUsage;
import com.rockwell.mes.shared.product.material.AbstractMaterialDAO0710;
import com.rockwell.mes.shared.product.material.IdentifiedMaterialDAO0710;
import com.rockwell.mes.shared.product.material.MaterialModel0710;
import com.rockwell.mes.shared.product.material.PhaseResult0710;
import com.rockwell.mes.shared0400.product.ui.basics.PhaseUIConstants;
import com.rockwell.mes.shared0400.product.ui.basics.impl.ColumnInfo;
import com.rockwell.mes.shared0400.product.ui.basics.impl.PhaseEnableToggleButton;
import com.rockwell.mes.shared0400.product.ui.basics.impl.PhaseStandardGrid;
import com.rockwell.mes.shared0400.product.ui.basics.impl.ProductPhaseColumnLayoutPanel;
import com.rockwell.mes.shared0400.product.ui.basics.impl.ProductPhaseColumnLayoutPanel.Column;
import com.rockwell.mes.shared0400.product.ui.basics.impl.ProductPhaseColumnLayoutPanel.Layout;
import com.rockwell.mes.shared0400.product.ui.basics.util.ProductPhaseSwingHelper;

/**
 * A view for material identification (as part of MVC structure)
 * 
 * @author ikoleva, (c) Copyright 2014 Rockwell Automation Technologies, Inc. All Rights Reserved.
 * 
 */
@SuppressWarnings("PMD.ExcessiveImports")
//All imports needed 
public class MatIdentView0710 extends AbstractPhaseMainView0200<MatIdentModel0710> {
    private static final long serialVersionUID = 1L;

    /** 
     * Input field displaying the scanned barcode
     */
    private transient JTextField inputField;
            
    /** the grid used for displaying identified materials */
    private transient PhaseStandardGrid grid;
    
    /** the toggle button used to enable/disable complete button */
    private transient PhaseEnableToggleButton enableCompleteButton;
    
    /** the continue button */
    private PhaseRadioButton0300 continueButton;

    /** the done button */
    private PhaseRadioButton0300 doneButton;

    /**
     * the panel that takes the center of the phase panel: includes the grid panel and the comment panel
     */
    private JPanel centralPanel;

    /** Width for position column */
    private static final int POS_COLUMN_WIDTH = 60;

    /** Width for description column */
    private static final int MATERIAL_COLUMN_WIDTH = 230;

    private static final int BATCH_COLUMN_WIDTH = 180;

    /** Width for quantities */
    private static final int QUANTITY_COLUMN_WIDTH = 110;

    /** Width for quantities */
    private static final int QUANTITY_LIMITS_COLUMN_WIDTH = 190;

    /** Width for result */
    private static final int STATUS_COLUMN_WIDTH = 110;

    private transient IMatIdentEventListener0710 eventListener;

    /**
     * @param theModel the phase model
     * @param theListener the listener that should react to events from this view
     */
    protected MatIdentView0710(MatIdentModel0710 theModel, IMatIdentEventListener0710 theListener) {
        super(theModel);
        eventListener = theListener;
    }

    /**
     * @return the matident event listener
     */
    public IMatIdentEventListener0710 getEventListener() {
        return eventListener;
    }

    /**
     * @return the barcode display field
     */
    public JTextField getInputField() {
        return inputField;
    }

    /**
     * @return the barcode entered by the user
     */
    public String getEnteredBarcode() { 
        return inputField.getText();
    }

    @Override
    public void createUI() {
        boolean isActive = (getModel().getStatus().equals(Status.ACTIVE));

        setOpaque(false);
        setLayout(new BorderLayout());

        createInputPanel(isActive);

        if (getModel().supportLooping()) {
            createCompletePanelWithLoopBtns();
        } else {
            createCompletePanel();
        }

        centralPanel = new JPanel();
        centralPanel.setEnabled(isActive);
        centralPanel.setOpaque(false);
        centralPanel.setLayout(new BorderLayout());

        //Panel for the grid
        createGridPanel(isActive);
        createCommentPanel(isActive);

        add(centralPanel, BorderLayout.CENTER);
    }


    /**
     * Create panel for the comment
     */
    private void createCommentPanel(boolean isActive) {
        String comment = getModel().getComment();
        if (StringUtils.isEmpty(comment)) {
            // nothing to display
            return;
        }
        // constraints for the inner panel with gaps
        GridBagConstraints constraintsPanel = new GridBagConstraints();
        constraintsPanel.fill = GridBagConstraints.BOTH; // Fill in both directions
        constraintsPanel.weighty = Short.MAX_VALUE; // Grow in height
        constraintsPanel.weightx = Short.MAX_VALUE; // Grow in width
        // big gap on the right: the place where the complete button is should stay empty
        constraintsPanel.insets =
                new Insets(PhaseUIConstants.DEFAULT_GAP, PhaseUIConstants.DEFAULT_GAP, PhaseUIConstants.DEFAULT_GAP, PhaseUIConstants.DEFAULT_GAP);

        JLabel commentLabel =
                PhaseSwingHelper.createMultiLineLabel(comment,
                        UIConstants.DEFAULT_PANEL_WIDTH
                        - constraintsPanel.insets.left
                        - constraintsPanel.insets.right);

        // inner panel with gaps
        JPanel innerPanel = new JPanel();
        innerPanel.setEnabled(isActive);
        innerPanel.setOpaque(false);
        innerPanel.setLayout(new GridBagLayout());
        innerPanel.add(commentLabel, constraintsPanel);

        centralPanel.add(innerPanel, BorderLayout.SOUTH);
    }

    /**
     * Create panel for complete button
     */
    private void createCompletePanel() {
        ProductPhaseColumnLayoutPanel layoutPanel = new ProductPhaseColumnLayoutPanel(Layout.LAYOUT_SINGLE_COLUMN,
                getModel().getStatus());

        final ConfirmButton confirmButton = layoutPanel.getConfirmButton();
        setConfirmButton(confirmButton);
        configureConfirmButton();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        
        boolean isCompletionSigConfigured = !getModel().getPrivilegeParameters(PrivilegeUsage.PHASE_COMPLETION).isEmpty();
        if (!isCompletionSigConfigured) {
            addEnableCompleteButton(buttonPanel);
            // by default the complete button is disabled
            eventListener.enableConfirmButton(false);
        }
        layoutPanel.addToColumn(Column.FIRST_COLUMN, buttonPanel, GridBagConstraints.BOTH);

        add(layoutPanel, BorderLayout.SOUTH);
    }

    private void addEnableCompleteButton(JPanel buttonPanel) {
        enableCompleteButton = new PhaseEnableToggleButton("enableCompleteButton", getModel().getStatus());
        enableCompleteButton.addValueChangedEventListener(evt -> eventListener.enableConfirmButton(enableCompleteButton.isPressed()));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        constraints.weightx = 1;
        constraints.weighty = 1;
        buttonPanel.add(enableCompleteButton, constraints);
    }

    // CHECKSTYLE:MagicNumber:off (Reason: panel or component borders/sizes varies from 0 to 10 ...)
    private void createCompletePanelWithLoopBtns() {
        ProductPhaseColumnLayoutPanel layoutPanel = new ProductPhaseColumnLayoutPanel(Layout.LAYOUT_SINGLE_COLUMN, getModel().getStatus());

        final ConfirmButton confirmButton = layoutPanel.getConfirmButton();
        setConfirmButton(confirmButton);
        configureConfirmButton();

        final GridBagLayout layout = new GridBagLayout();
        JPanel outerPanel = new JPanel(layout);
        outerPanel.setOpaque(false);

        addContinueDoneButtons(outerPanel);

        layoutPanel.addToColumn(Column.FIRST_COLUMN, outerPanel, GridBagConstraints.WEST);

        add(layoutPanel, BorderLayout.SOUTH);
    }

    private void addContinueDoneButtons(JPanel outerPanel) {
        JPanel groupButtonsPanel = createContinueDoneButtonPanel();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.weightx = 1;
        outerPanel.add(groupButtonsPanel, constraints);
    }



    private JPanel createContinueDoneButtonPanel() {
        boolean isActive = getModel().getStatus().equals(Status.ACTIVE);

        JPanel groupButtonsPanel = new JPanel(new GridBagLayout());
        groupButtonsPanel.setOpaque(false);

        continueButton =
                createResultSelectionBtn(isActive, PhaseResult0710.CONTINUE, "ContinueButton", evt -> eventListener.onContinueButtonPressed());
        if (getModel().isLoopStopEnabled()) {
            doneButton = createResultSelectionBtn(isActive, PhaseResult0710.STOP, "StopButton", evt -> eventListener.onStopButtonPressed());
        } else {
            doneButton = createResultSelectionBtn(isActive, PhaseResult0710.DONE, "DoneButton", evt -> eventListener.onDoneButtonPressed());
        }

        preSelectButton();

        /** ButtonGroup Continue and Done buttonGroup */
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(continueButton);
        buttonGroup.add(doneButton);


        GridBagConstraints optionButtonPanelConstraints = new GridBagConstraints();
        optionButtonPanelConstraints.insets = new Insets(0, 3, 0, 3);

        groupButtonsPanel.add(continueButton, optionButtonPanelConstraints);
        groupButtonsPanel.add(doneButton, optionButtonPanelConstraints);

        return groupButtonsPanel;
    }

    private PhaseRadioButton0300 createResultSelectionBtn(boolean isActive, PhaseResult0710 result, String name, ActionListener l) {
        PhaseRadioButton0300 button = new PhaseRadioButton0300(result.getKey(), result.getLocalizedString(), true, false);
        button.setName(name); // for Marathon
        button.setIconTextGap(10);
        button.setEnabled(isActive);
        button.addActionListener(l);
        return button;
    }
    // CHECKSTYLE:MagicNumber:on

    private void preSelectButton() {
        PhaseResult0710 result = getModel().getPhaseResult();
        if (PhaseResult0710.DONE.equals(result) || PhaseResult0710.STOP.equals(result)) {
            // this is possible in case of completed view
            doneButton.setSelected(true);
        } else {
            continueButton.setSelected(true);
        }
    }

    /**
     * select the done button and disable the buttons
     */
    public void selectDoneButtonAndDisable() {
        if (doneButton != null) {
            doneButton.setSelected(true);
            continueButton.setEnabled(false);
            doneButton.setEnabled(false);
        }
    }
    /**
     * Create phase grid panel
     * 
     * @param isActive if the phase is active
     */
    private void createGridPanel(boolean isActive) {
        List<ColumnInfo> columns = generateColumnsInfo();
        
        grid = new PhaseStandardGrid(getModel().getStatus(), IdentifiedMaterialDAO0710.class, columns, //
                new AbstractMaterialDAO0710.MaterialPhaseStandardGridConfigurator());
        
        JPanel gridPanel = new JPanel();
        gridPanel.setEnabled(isActive);
        gridPanel.setOpaque(false);
        gridPanel.setLayout(new BorderLayout());
        gridPanel.add(grid.getMESGrid(), BorderLayout.CENTER);
        
        centralPanel.add(gridPanel, BorderLayout.CENTER);
        
        refreshGrid();
        
        // per default the focus is on the confirm button, but here it is disabled...
        setFocusTraversalPolicy(new SwingPhasePanelFocusTraversalPolicy(grid.getMESGrid().getGrid()));
        setFocusTraversalPolicyProvider(true);

        if (isActive) {
            // Set the focus on the grid whenever the phase becomes visible: to detect barcode scanning
            grid.getMESGrid().addAncestorListener(new RequestFocusGridListener());
            requestFocusOnGrid();
        }
    }

    /**
     * If focus get lost e.g. when complete button has been pressed, then afterwards no barcode scanning is possible
     */
    public void reenableBarcodeScanning() {
        requestFocusOnGrid();
    }

    private void requestFocusOnGrid() {
        grid.getMESGrid().requestFocus();
    }

    /**
     * Create the top panel: for user input
     * 
     * @param isActive if the phase is active
     */
    private void createInputPanel(boolean isActive) {
        
        //input panel: for the instruction and input field
        JPanel inputPanel = new JPanel();
        inputPanel.setName("inputPanel");
        inputPanel.setEnabled(isActive);
        inputPanel.setOpaque(false);
        inputPanel.setLayout(new GridBagLayout());
        
        //constraints for the inner panel with gaps
        GridBagConstraints constraintsInnerPanel = new GridBagConstraints();
        constraintsInnerPanel.fill = GridBagConstraints.BOTH; // Fill in both directions
        constraintsInnerPanel.weighty = Short.MAX_VALUE; // Grow in height
        constraintsInnerPanel.weightx = Short.MAX_VALUE; // Grow in width
        constraintsInnerPanel.insets = new Insets(PhaseUIConstants.DEFAULT_GAP, PhaseUIConstants.DEFAULT_GAP, PhaseUIConstants.DEFAULT_GAP, 1);
        
        //inner panel with gaps
        JPanel innerPanel = ProductPhaseSwingHelper.addPanelWithGaps(inputPanel, constraintsInnerPanel);
        GroupLayout layout = new GroupLayout(innerPanel);
        innerPanel.setLayout(layout);
        
        JLabel label = ProductPhaseSwingHelper.createJLabel(getModel().getInstructionTextColumn1(),
                Layout.LAYOUT_TWO_1ST_WIDER_COLUMN.getColumnWidth(Column.FIRST_COLUMN));
                
        JPanel refreshBtnPanel = createRefreshButtonPanel();
        createInputField();
        
        addComponentsToInnerPanel(layout, label, refreshBtnPanel);
        
        add(inputPanel, BorderLayout.NORTH);
    }

    private void createInputField() {
        inputField = PhaseSwingHelper.createJTextField(getModel().getStatus());
        inputField.setName(inputField.getName().concat("BarcodeInput"));
        //Box width enlarged to span entire column
        inputField.setPreferredSize(
                new Dimension(Layout.LAYOUT_TWO_1ST_WIDER_COLUMN.getColumnWidth(Column.SECOND_COLUMN),
                        UIConstants.EDIT_PREFERRED_SIZE.height));
    }

    private void addComponentsToInnerPanel(GroupLayout layout, JLabel label, JPanel refreshBtnPanel) {
        // add a gap on the left side of the input field in order to simulate two column layout
        // set a minimum size for the label: otherwise the preferred size of the parent is not correct
        layout.setHorizontalGroup(layout
                .createSequentialGroup()
                .addComponent(label, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE)
                .addGap(Layout.LAYOUT_TWO_1ST_WIDER_COLUMN.getColumnWidth(Column.SECOND_COLUMN) - inputField.getPreferredSize().width)
                .addComponent(inputField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                .addComponent(refreshBtnPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(layout
                .createParallelGroup()
                .addComponent(label, GroupLayout.Alignment.CENTER, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE)
                .addComponent(inputField, GroupLayout.Alignment.CENTER, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(refreshBtnPanel, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));
    }
    
    private JPanel createRefreshButtonPanel() {
        com.rockwell.mes.apps.ebr.ifc.swing.PhaseButton refreshBtn = PhaseSwingHelper.createPhaseButton(getBtnTextRefresh(), getModel().getStatus());
        refreshBtn.setName("refreshButton");
        refreshBtn.addActionListener(e -> eventListener.onRefresh());

        return ProductPhaseSwingHelper.createButtonWrapperPanel(refreshBtn);
    }

    /**
     * @return caption of Refresh button
     */
    private static String getBtnTextRefresh() {
        return I18nMessageUtility.getLocalizedMessage(MaterialModel0710.PHASE_PRODUCT_MATERIAL_MSGPACK, "BtnTextRefresh");
    }

    /**
     * Generate a list containing the info for the grid columns.
     * 
     * @return a list
     */
    private List<ColumnInfo> generateColumnsInfo() {
        List<ColumnInfo> columns = new ArrayList<>();
        columns.add(new ColumnInfo("MfcPosition", POS_COLUMN_WIDTH, true, SwingConstants.CENTER));
        columns.add(new ColumnInfo("MaterialDisplayString", MATERIAL_COLUMN_WIDTH, true));
        final ColumnInfo colBatchesSublots = new ColumnInfo("RelatedBatchesSublotsDisplayString", BATCH_COLUMN_WIDTH, true);
        // special message in warehouse context
        if (getModel().isWarehauseApplicationAvailable()) {
            colBatchesSublots.setHeaderMsg("relatedBatchesLUSublotsDisplayString");
        }
        columns.add(colBatchesSublots);

        final ColumnInfo plannedQtyWithLimitsCol =
                new ColumnInfo("plannedQtyWithLimits", QUANTITY_LIMITS_COLUMN_WIDTH, true, SwingConstants.RIGHT);
        plannedQtyWithLimitsCol.setHeaderMsg("plannedQty");
        columns.add(plannedQtyWithLimitsCol);

        ColumnInfo identifiedCol = new ColumnInfo("processedQty", QUANTITY_COLUMN_WIDTH, true, SwingConstants.RIGHT);
        //a special message: the bound field means different things in different contexts 
        //(e.g. also used for produced qty)
        identifiedCol.setHeaderMsg("identifiedQty");
        columns.add(identifiedCol);
        
        if (!getModel().getAutoConsume()) {
            columns.add(new ColumnInfo("accountedQty", QUANTITY_COLUMN_WIDTH, true, SwingConstants.RIGHT));
            columns.add(new ColumnInfo("statusString", STATUS_COLUMN_WIDTH, true));
        }

        return columns;
    }
    
    /**
     * Refresh the grid contents
     */
    private void refreshGrid() {
        grid.setObjects(getModel().getMaterialList());
    }

    /**
     * Listener to set the focus on the material grid whenever it becomes visible
     */
    private class RequestFocusGridListener implements AncestorListener {

        @Override
        public void ancestorAdded(final AncestorEvent event) {
            grid.getMESGrid().requestFocus();
        }

        @Override
        public void ancestorRemoved(final AncestorEvent event) {
            // Ignore
        }

        @Override
        public void ancestorMoved(final AncestorEvent event) {
            // Ignore
        }
    }
    
    /**
     * Refresh the view
     */
    public void refreshView() {
        repaint();
        refreshGrid();
    }

    /**
     * Lock the complete button
     */
    public void lockCompleteButton() {
        if (enableCompleteButton != null) {
            enableCompleteButton.doClick();
        }
    }

    @Override
    protected String getNavigatorInfoColumn() {
        return getModel().getNavigatorInfoColumn();
    }
}