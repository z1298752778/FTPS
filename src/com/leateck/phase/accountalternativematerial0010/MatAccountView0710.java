package com.leateck.phase.accountalternativematerial0010;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.rockwell.mes.apps.ebr.ifc.phase.IPhaseExecutor.Status;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.SwingPhasePanelFocusTraversalPolicy;
import com.rockwell.mes.apps.ebr.ifc.swing.ConfirmButton;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseSwingHelper;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.shared.phase.components.swing.PhaseRadioButton0300;
import com.rockwell.mes.commons.shared.phase.mvc.AbstractPhaseMainView0200;
import com.rockwell.mes.services.s88.ifc.recipe.PrivilegeUsage;
import com.rockwell.mes.shared.product.material.AbstractMaterialDAO0710;
import com.rockwell.mes.shared.product.material.AccountMaterialDAO0710;
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
 * A view for material accounting (as part of MVC structure)
 * 
 * @author ikoleva, (c) Copyright 2014 Rockwell Automation Technologies, Inc. All Rights Reserved.
 * 
 */
public class MatAccountView0710 extends AbstractPhaseMainView0200<MatAccountModel0710> {
    private static final long serialVersionUID = 1L;

    /** the grid used for displaying accounted materials */
    private transient PhaseStandardGrid grid;

    private static final int POS_COLUMN_WIDTH = 60;

    private static final int MATERIAL_COLUMN_WIDTH = 175;

    private static final int BATCH_SUBLOT_COLUMN_WIDTH = 174;

    private static final int QUANTITY_LIMITS_COLUMN_WIDTH = 96;

    private static final int QTY_COLUMN_WIDTH = 96;

    private static final int STATUS_COLUMN_WIDTH = 96;
    
    private transient IMatAccountEventListener0710 eventListener;

    private PhaseRadioButton0300 continueButton;

    private PhaseRadioButton0300 doneButton;

    /**
     * @param theModel the phase model
     * @param argListener the listener that should react to events from this view
     */
    protected MatAccountView0710(MatAccountModel0710 theModel, IMatAccountEventListener0710 argListener) {
        super(theModel);
        eventListener = argListener;
    }

    /**
     * @return the list of the sublots that were edited
     */
    public List<AccountMaterialDAO0710> getChangedRows() {
        @SuppressWarnings("unchecked") //this is the class bound to the grid
        List<AccountMaterialDAO0710> rows = grid.getMESGrid().getSelectedRowObjects();
        return rows;
    }
   
    /**
     * @return caption of Account button
     */
    public static String getBtnTextAccount() {
        return I18nMessageUtility.getLocalizedMessage(AccountMaterialDAO0710.MSG_PACK, "BtnTextAccount");
    }
    
    /**
     * @return caption of Refresh button
     */
    private static String getBtnTextRefresh() {
        return I18nMessageUtility.getLocalizedMessage(LcAccountMaterialDAO0710.MSG_PACK, "Lc_BtnTextRefresh");
    }
    
    @Override
    public void createUI() {
        boolean isActive = (getModel().getStatus().equals(Status.ACTIVE));
        enableBarcodeInput(false);
        
        setOpaque(false);
        setLayout(new BorderLayout());
        
        createInputPanel(isActive);
        
        createGridPanel(isActive);
        createCompletePanel(getModel().isLoopEnabled());
    }
    
    /**
     * Create panel for complete button
     */
    private void createCompletePanel(boolean addLoopBtns) {
        ProductPhaseColumnLayoutPanel completePanel = new ProductPhaseColumnLayoutPanel(Layout.LAYOUT_SINGLE_COLUMN,
                getModel().getStatus());
        final ConfirmButton confirmButton = completePanel.getConfirmButton();
        setConfirmButton(confirmButton);
        configureConfirmButton();

        JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setOpaque(false);

        if (addLoopBtns) {
            addContinueDoneButtons(outerPanel);
        } else {
            // lock button is not needed in loop mode
            addEnableButtonIfNeeded(outerPanel);
        }


        completePanel.addToColumn(Column.FIRST_COLUMN, outerPanel, GridBagConstraints.WEST);

        add(completePanel, BorderLayout.SOUTH);
    }

    private void addEnableButtonIfNeeded(JPanel outerPanel) {
        boolean isCompletionSigConfigured = !getModel().getPrivilegeParameters(PrivilegeUsage.PHASE_COMPLETION).isEmpty();
        if (!isCompletionSigConfigured) {
            JPanel enablePanel = createaEnableCompleteBtnPanel();
            GridBagConstraints constrEnablePanel = new GridBagConstraints();
            constrEnablePanel.fill = GridBagConstraints.BOTH;
            constrEnablePanel.weightx = 1;
            outerPanel.add(enablePanel, constrEnablePanel);

            // by default the complete button is disabled
            eventListener.enableConfirmButton(false);
        }
    }

    private JPanel createaEnableCompleteBtnPanel() {
        JPanel groupButtonsPanel = new JPanel(new GridBagLayout());
        groupButtonsPanel.setOpaque(false);

        PhaseEnableToggleButton enableCompleteButton = new PhaseEnableToggleButton("enableCompleteButton", getModel().getStatus());
        enableCompleteButton.addValueChangedEventListener(evt -> eventListener.enableConfirmButton(enableCompleteButton.isPressed()));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        constraints.weightx = 1;
        constraints.weighty = 1;
        groupButtonsPanel.add(enableCompleteButton, constraints);

        return groupButtonsPanel;

    }

    private void addContinueDoneButtons(JPanel outerPanel) {
        JPanel groupButtonsPanel = createContinueDoneButtonPanel();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.weightx = 1;
        outerPanel.add(groupButtonsPanel, constraints);
    }

    // CHECKSTYLE:MagicNumber:off (Reason: panel or component borders/sizes varies from 0 to 10 ...)
    private JPanel createContinueDoneButtonPanel() {
        boolean isActive = (getModel().getStatus().equals(Status.ACTIVE));

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
     * Create phase grid panel
     * @param isActive if the phase is active
     */
    private void createGridPanel(boolean isActive) {
        List<ColumnInfo> columns = generateColumnsInfo();
        
        grid = new PhaseStandardGrid(getModel().getStatus(), LcAccountMaterialDAO0710.class, columns,
                new AbstractMaterialDAO0710.MaterialPhaseStandardGridConfigurator());
        JPanel gridPanel = new JPanel();
        gridPanel.setEnabled(isActive);
        gridPanel.setOpaque(false);
        gridPanel.setLayout(new BorderLayout());        
        gridPanel.add(grid.getMESGrid(), BorderLayout.CENTER);
        
        add(gridPanel, BorderLayout.CENTER);
        
        grid.getMESGrid().setAllowMultiSelection(true);
        grid.getMESGrid().setAutoSelectFirstRow(false);
        refreshGrid();
        
        if (isActive) {
            grid.getMESGrid().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() > 1) {
                        eventListener.onAccount();
                    }
                }
            });
        }
    }
          
    /**
     * Generate the UI for the panel above the grid
     * 
     * @param isActive if the panel should be enabled
     */
    private void createInputPanel(boolean isActive) { 
        //input panel: for the instruction and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setName("inputPanel");
        inputPanel.setEnabled(isActive);
        inputPanel.setOpaque(false);
        inputPanel.setLayout(new GridBagLayout());
        
        JLabel label = ProductPhaseSwingHelper.createJLabel(getModel().getInstructionTextColumn1(),
                Layout.LAYOUT_TWO_1ST_WIDER_COLUMN.getColumnWidth(Column.FIRST_COLUMN));
        
        GridBagConstraints constraintsLabel = new GridBagConstraints();
        constraintsLabel.gridx = 0; // First column
        constraintsLabel.gridy = 0; // First row
        constraintsLabel.fill = GridBagConstraints.BOTH; // Fill in both directions
        constraintsLabel.weighty = Short.MAX_VALUE; // Grow in height
        constraintsLabel.weightx = Short.MAX_VALUE; // Grow in width
        constraintsLabel.insets = new Insets(PhaseUIConstants.DEFAULT_GAP, PhaseUIConstants.DEFAULT_GAP,
                PhaseUIConstants.DEFAULT_GAP, 0);

        inputPanel.add(label, constraintsLabel);
        

        JPanel refreshBtnPanel = createRefreshButtonPanel();
        GridBagConstraints constraintsBtn = new GridBagConstraints();
        constraintsBtn.gridx = 1; // Second column
        constraintsBtn.gridy = 0; // First row
        constraintsBtn.anchor = GridBagConstraints.SOUTHEAST;
        constraintsBtn.weighty = 0; // Fixed height
        constraintsBtn.weightx = 0; // Fixed width
        inputPanel.add(refreshBtnPanel, constraintsBtn);
        
        JPanel accountBtnPanel = createAccountButtonPanel();
        constraintsBtn.gridx = 2;
        inputPanel.add(accountBtnPanel, constraintsBtn);
        
        add(inputPanel, BorderLayout.NORTH);
    }

    /**
     * Create the panel for the refresh button
     * 
     * @return the panel for the refresh button
     */
    private JPanel createRefreshButtonPanel() {
        com.rockwell.mes.apps.ebr.ifc.swing.PhaseButton refreshBtn = PhaseSwingHelper.createPhaseButton(
                getBtnTextRefresh(), getModel().getStatus());
        refreshBtn.setName("refreshButton");
        refreshBtn.addActionListener(e -> eventListener.onRefresh());

        JPanel refreshPanel = new JPanel(new GridBagLayout());
        refreshPanel.setPreferredSize(new Dimension(Layout.LAYOUT_TWO_1ST_WIDER_COLUMN
                .getColumnWidth(Column.SECOND_COLUMN), PhaseUIConstants.CONFIRM_BUTTON_PANEL_SIZE.height));
        refreshPanel.setOpaque(false);
        refreshPanel.setEnabled(refreshBtn.isEnabled());
        GridBagConstraints constraints = new GridBagConstraints();
        int gap = PhaseUIConstants.BUTTON_GAP;
        //do not use ProductPhaseSwingHelper.createButtonWrapperPanel: the right gap should be 0
        constraints.insets = new Insets(gap, gap, gap, 0);
        constraints.weighty = 1; // needed to use the anchor to bottom
        constraints.weightx = 1; // needed to use the anchor to the right
        constraints.anchor = GridBagConstraints.LAST_LINE_END;

        refreshPanel.add(refreshBtn, constraints);
        return refreshPanel;
    }
    
    /**
     * Create the panel for the account button
     * 
     * @return the panel for the account button
     */
    private JPanel createAccountButtonPanel() {
        com.rockwell.mes.apps.ebr.ifc.swing.PhaseButton accountBtn = PhaseSwingHelper.createPhaseButton(
                getBtnTextAccount(), getModel().getStatus());
        accountBtn.setName("accountButton");
        accountBtn.addActionListener(e -> eventListener.onAccount());
        
        // set the focus to the confirm btn
        setFocusTraversalPolicy(new SwingPhasePanelFocusTraversalPolicy(accountBtn));
        setFocusTraversalPolicyProvider(true);

        return ProductPhaseSwingHelper.createButtonWrapperPanel(accountBtn);
    }

    /**
     * Generate a list containing the info for the grid columns. 
     * @return a list 
     */
    private List<ColumnInfo> generateColumnsInfo() {
        List<ColumnInfo> columns = new ArrayList<>();
        columns.add(new ColumnInfo("mfcPosition", POS_COLUMN_WIDTH, false, SwingConstants.CENTER));
        columns.add(new ColumnInfo("materialDisplayString", MATERIAL_COLUMN_WIDTH, true));
        ColumnInfo batchSublotCol = new ColumnInfo("batchOrSublot", BATCH_SUBLOT_COLUMN_WIDTH, false);
        // setting the header messages separately somehow fixes a multiline issue
        batchSublotCol.setHeaderMsg("batchOrSublot");
        columns.add(batchSublotCol);
        final ColumnInfo plannedQtyWithLimitsCol = new ColumnInfo("plannedQtyWithLimits", QUANTITY_LIMITS_COLUMN_WIDTH, true, SwingConstants.RIGHT);
        plannedQtyWithLimitsCol.setHeaderMsg("plannedQtyWithLimits");
        columns.add(plannedQtyWithLimitsCol);
        columns.add(new ColumnInfo("identifiedQty", QTY_COLUMN_WIDTH, true, SwingConstants.RIGHT));
        columns.add(new ColumnInfo("consumedQty", QTY_COLUMN_WIDTH, true, SwingConstants.RIGHT));
        final ColumnInfo wastedSampledCol = new ColumnInfo("wastedAndSampledQty", QTY_COLUMN_WIDTH, true, SwingConstants.RIGHT);
        wastedSampledCol.setHeaderMsg("wastedAndSampledQty");
        columns.add(wastedSampledCol);
        columns.add(new ColumnInfo("returnedQty", QTY_COLUMN_WIDTH, true, SwingConstants.RIGHT));
        columns.add(new ColumnInfo("statusString", STATUS_COLUMN_WIDTH, true));

        return columns;
    }

    /**
     * Refresh the grid contents
     */
    private void refreshGrid() {
        grid.setObjects(getModel().getMaterialList());
    }
    
    /**
     * Refresh the view
     */
    public void refreshView() {
        refreshGrid();
    }

    @Override
    protected String getNavigatorInfoColumn() {
        return getModel().getNavigatorInfoColumn();
    }

    /**
     * Modify the UI to indicate that the phase was modified
     */
    public void setModified() {
        if (continueButton != null) {
            String modidiedCaption = I18nMessageUtility.getLocalizedMessage(AccountMaterialDAO0710.MSG_PACK, "ModifiedMarker",
                    new Object[] { PhaseResult0710.CONTINUE.getLocalizedString() });
            continueButton.setTextAsHtml(modidiedCaption, false);
        }
    }
}
