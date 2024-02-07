package com.leateck.phase.wdmaterialidentification0100;

import com.rockwell.mes.clientfw.commons.ifc.swing.*;
import java.util.*;
import javax.swing.Box;
import javax.swing.plaf.*;
import java.awt.event.*;
import com.rockwell.mes.commons.base.ifc.i18n.*;
import java.math.*;
import com.rockwell.mes.apps.ebr.ifc.swing.*;
import com.rockwell.mes.apps.ebr.ifc.phase.*;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.*;
import java.awt.*;
import java.util.List;

import com.rockwell.mes.services.inventory.ifc.*;
import com.rockwell.mes.commons.base.ifc.services.*;
import com.rockwell.mes.commons.base.ifc.nameduda.*;
import com.datasweep.plantops.common.measuredvalue.*;
import com.rockwell.mes.shared0200.product.ui.basics.impl.PhaseSeparator;
import org.apache.commons.lang3.*;
import com.rockwell.mes.commons.base.ifc.functional.*;
import com.rockwell.mes.shared.product.wd.radiobuttons.*;
import com.rockwell.mes.shared.product.wd.*;
import com.datasweep.compatibility.client.*;
import com.rockwell.mes.services.eqm.ifc.*;
import javax.swing.*;
import javax.swing.event.*;

public class MatIdentExceptionView0610 extends WDOWAbstractWeighExceptionView0610<MatIdentModel0610>
{
    private static final int REOPEN_VERTICAL_STRUT = 10;
    private static final String RESET_POSITION_BUTTON = "resetPositionButton";
    private static final String ADJUST_VALUES_BUTTON = "adjustValuesButton";
    private static final long serialVersionUID = 1L;
    static final String PARAMETER_ABORT_BOM_POS = "Abort BOM position";
    static final String PARAMETER_COMPLETE_BOM_POS = "Complete position";
    static final String PARAMETER_CLOSE_TARGET_SUBLOT = "Close target sublot";
    static final String PARAMETER_UNDO_IDENTIFICATIO = "Undo identification";
    static final String PARAMETER_REOPEN_POSITION_DECLARE_WASTE = "Re-open position / declare waste";
    static final String PARAMETER_OVERRIDE_PRORATE_FACTOR = "Override prorate factor";
    public static final String MANUAL_IDENTIFICATION = "MANUAL_IDENTIFICATION";
    public static final String ABORT_BOM_POSITION = "ABORT_BOM_POSITION";
    public static final String COMPLETE_BOM_POSITION = "COMPLETE_BOM_POSITION";
    public static final String CLOSE_TARGET = "CLOSE_TARGET";
    public static final String UNDO_IDENTIFICATION = "UNDO_IDENTIFICATION";
    public static final String RECORD_WAREHOUSE_ERROR = "WAREHOUSE_ERROR";
    public static final String OVERRIDE_PRORATE_FACTOR = "OVERRIDE_PRORATE_FACTOR";
    public static final String OVERRIDE_PRORATE_FACTOR_EXCEPTION_TEXT_MSGID = "OverrideProrateFactorExceptionTxt";
    public static final String OVERRIDE_PRORATE_FACTOR_TXT_INLINE = "OverrideProrateFactorTxtInline";
    public static final String OVERRIDE_PRORATE_FACTOR_INVALID_INPUT = "OverrideProrateFactorInvalidInput";
    public static final String DECLARE_WASTE_INVALID_INPUT = "DeclareWasteInvalidInput";
    public static final String DECLARE_NEW_REMAINING_INVALID_INPUT = "DeclareNewRemainingInvalidInput";
    public static final String OVERRIDE_PRORATE_FACTOR_LABEL = "newProrateFactorLabel";
    public static final String REOPEN_POSITION_DECLARE_WASTE = "REOPEN_POSITION_DECLARE_WASTE";
    private static final String DECLARE_WASTE_INSTRUCTION_TEXT_LABEL_NAME = "DeclareWasteInstructionText";
    private static final String WAREHOUSE_ERROR_INSTRUCTION_TEXT_LABEL_NAME = "recordWarehouseErrorLabel";
    public static final String MANUALIDENT_EXCEPTION_TEXT_MSGID = "ManualIdentExceptionTxt";
    public static final String SELECT_BOM_POSITION_TEXT_MSGID = "selectBOMPosition_ErrorMsg";
    public static final String SELECT_BOM_POSITION_COMPLETE_TEXT_MSGID = "selectBOMPositionComplete_ErrorMsg";
    public static final String SELECT_BOM_POSITION_REOPEN_TEXT_MSGID = "selectBOMPositionReopen_ErrorMsg";
    public static final String ABORT_BOM_POSITION_EXCEPTION_TEXT_MSGID = "CancelBOMItemExceptionTxt";
    public static final String COMPLETE_BOM_POSITION_EXCEPTION_TEXT_MSGID = "CompleteBOMItemExceptionTxt";
    public static final String REOPEN_BOM_POSITION_INSTRUCTION_TEXT_MSGID = "ReopenBOMItemInstructionTxt";
    public static final String UNDO_IDENTIFICATION_EXC_INSTRUCTION_TEXT_MSGID = "undoIdentificationExceptionTxt";
    public static final String UNDO_IDENTIFICATION_WITH_CONTAINER_EXC_INSTRUCTION_TEXT_MSGID = "undoIdentificationWithContainerExcInstructionTxt";
    public static final String UNDO_IDENTIFICATION_WITH_CONTAINER_EXC_TEXT_MSGIED = "undoIdentificationWithContainerExceptionRecTxt";
    public static final String UNDO_IDENTIFICATION_EXC_TEXT_MSGIED = "undoIdentificationExceptionRecTxt";
    private static final String OVERRIDE_PRORATE_FACTOR_CONFIRM_BUTTON_NAME = "OverrideProrateConfirmButton";
    private static final String OVERRIDE_PRORATE_FACTOR_INSTRUCTION_TEXT_LABEL_NAME = "OverrideProrateFactorInstructionText";
    private static final String OVERRIDE_PRORATE_FACTOR_EDITFIELDNAME_SUFFIX = "_OverrideProrateFactor";
    private static final String EDITFIELDNAME_SUFFIX_BATCHID = "_BatchId";
    private static final String EDITFIELDNAME_SUFFIX_MATERIALID = "_MaterialId";
    private static final String EDITFIELDNAME_SUFFIX_SUBLOTID = "_SublotId";
    private static final String EDITFIELDNAME_SUFFIX_CONTAINERID = "_ContainerId";
    private static final int MAX_LENGTH = 4000;
    private JTextField edtNewRemaining;
    private JLabel lblNewRemainingUoM;
    private JTextField edtDeclareWaste;
    private JLabel lblDeclareWasteUoM;
    private JTextField edtProrateFactor;
    private JTextField edtBatchId;
    private JTextField edtMaterialId;
    private JTextField edtSublotId;
    private JTextField edtContainerId;
    private MESStandardGrid cancelBOMPosGrid;
    private MESStandardGrid completeBOMPosGrid;
    private MESStandardGrid reopenBOMPosGrid;
    private ManualIdentificationExceptionDocumentListener manualIdentificationDocumentListener;
    private ManualIdentificationModel manualIdentificationModel;

    public MatIdentExceptionView0610(final IDelegate0610<IWDMatIdentModel0610> delegate) {
        super((IDelegate0610)delegate);
        this.edtDeclareWaste = null;
        this.lblDeclareWasteUoM = null;
        this.edtProrateFactor = null;
        this.edtBatchId = null;
        this.edtMaterialId = null;
        this.edtSublotId = null;
        this.edtContainerId = null;
        this.cancelBOMPosGrid = null;
        this.completeBOMPosGrid = null;
        this.reopenBOMPosGrid = null;
    }

    protected void createUI() {
        this.setLayout((LayoutManager)new BoxLayout((Container)this, 1));
        final ArrayList<JPanel> exceptionPanels = new ArrayList<JPanel>();
        if (((IWDMatIdentModel0610)this.getModel()).isUndoIdentificationApplicable() && !((IWDMatIdentModel0610)this.getModel()).isBlockedByWarehouseError()) {
            exceptionPanels.add(this.createUndoIdentificationExceptionView());
        }
        else if (((IWDMatIdentModel0610)this.getModel()).isBlockedByWarehouseError()) {
            exceptionPanels.add(this.createRecordWarehouseErrorPanel());
        }
        else {
            exceptionPanels.add(this.createManualIdentificationExceptionView());
            exceptionPanels.add(this.createAbortBOMPosExceptionView());
            exceptionPanels.add(this.createCloseTargetExceptionView());
            if (this.hasPositionsAllowedToComplete(((IWDMatIdentModel0610)this.getModel()).getMasterOSIsRelevantForThisRtPhase(true))) {
                exceptionPanels.add(this.createCompleteBOMPosExceptionView());
            }
            if (((IWDMatIdentModel0610)this.getModel()).isInlineWeighing()) {
                exceptionPanels.add(this.createReopenBOMPosExceptionView());
            }
            exceptionPanels.add(this.createOverrideProrateFactorExceptionView());
        }
        this.disableUserTriggeredExceptionsIfReleaseScaleBtnIsActive(exceptionPanels);
    }

    private boolean hasPositionsAllowedToComplete(final List<OrderStepInput> masterOSIsForGrid) {
        for (final OrderStepInput anOsi : masterOSIsForGrid) {
            if (isPlannedQtyModeNone(anOsi) || WDOSIServiceHelper0610.isAllowedToDeclareFinish(anOsi)) {
                return true;
            }
        }
        return false;
    }

    static boolean isPlannedQtyModeNone(final OrderStepInput anOsi) {
        return WDOSIServiceHelper0610.isPlannedQtyModeNone(anOsi) || WDOSIServiceHelper0610.isPlannedQtyModeNone(WDOSIServiceHelper0610.getLastOSIOfBomItem(anOsi));
    }

    private void disableUserTriggeredExceptionsIfReleaseScaleBtnIsActive(final List<JPanel> exceptionPanels) {
        final boolean doUserTriggeredReleaseScale = ((IWDMatIdentModel0610)this.getModel()).isDoUserTriggeredReleaseScale();
        for (final JPanel panel : exceptionPanels) {
            if (doUserTriggeredReleaseScale) {
                WDHelper0610.disableComponentTree(new Component[] { panel });
            }
            this.add((Component)panel);
            this.add((Component)new PhaseSeparator());
        }
    }

    private JPanel createOverrideProrateFactorExceptionView() {
        final JPanel exceptionPanel = PhaseSwingHelper.createExceptionPanel(PhaseColumnLayout.Layout.LAYOUT_TWO_1ST_WIDER_COLUMN);
        exceptionPanel.setUI((PanelUI)new PhaseExceptionPanelUI((JComponent)exceptionPanel));
        this.createInstructionOverrideProrateFactorInFirstColumn(exceptionPanel);
        this.createFieldsOverrideProrateFactorInSecondColumn(exceptionPanel);
        final ConfirmButton confirmButton = ((PhaseColumnLayout)exceptionPanel.getLayout()).getConfirmButton();
        confirmButton.setName("OverrideProrateConfirmButton");
        confirmButton.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final BigDecimal decimal = WDHelper0610.parseDecimal(MatIdentExceptionView0610.this.edtProrateFactor.getText());
                if (decimal == null) {
                    WDHelper0610.showErrorDialog(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "OverrideProrateFactorInvalidInput"));
                    return;
                }
                MatIdentExceptionView0610.this.fireViewPropertyChange("OVERRIDE_PRORATE_FACTOR", (Object)null, (Object)decimal);
            }
        });
        return exceptionPanel;
    }

    private void createInstructionOverrideProrateFactorInFirstColumn(final JPanel exceptionPanel) {
        final JLabel jLabelSmall = PhaseSwingHelper.createJLabelSmall(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "OverrideProrateFactorExceptionTxt"), "InstructionText1");
        jLabelSmall.setName("OverrideProrateFactorInstructionText");
        exceptionPanel.add(jLabelSmall, PhaseColumnLayout.Column.FIRST_COLUMN);
    }

    private void createFieldsOverrideProrateFactorInSecondColumn(final JPanel exceptionPanel) {
        final JPanel panel = PhaseSwingHelper.createPanel();
        panel.setLayout(new GridLayout(0, 2, 9, 9));
        (this.edtProrateFactor = PhaseSwingHelper.createNumericTouchField(IPhaseExecutor.Status.ACTIVE, "Override prorate factor")).setName(this.edtProrateFactor.getName() + "_OverrideProrateFactor");
        panel.add(PhaseSwingHelper.createJLabelSmall(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "newProrateFactorLabel"), "newProrateFactorLabel"));
        final JPanel panel2 = PhaseSwingHelper.createPanel();
        panel2.setLayout(new BoxLayout(panel2, 1));
        panel2.add(Box.createRigidArea(new Dimension(UIConstants.EDIT_PREFERRED_SIZE.width, UIConstants.EDIT_PREFERRED_SIZE.height / 2)));
        panel2.add(this.edtProrateFactor);
        panel2.add(Box.createRigidArea(new Dimension(UIConstants.EDIT_PREFERRED_SIZE.width, UIConstants.EDIT_PREFERRED_SIZE.height / 2)));
        panel.add(panel2);
        exceptionPanel.add(panel, PhaseColumnLayout.Column.SECOND_COLUMN);
    }

    private JPanel createManualIdentificationExceptionView() {
        final JPanel exceptionPanel = PhaseSwingHelper.createExceptionPanel(PhaseColumnLayout.Layout.LAYOUT_TWO_1ST_WIDER_COLUMN);
        exceptionPanel.setUI((PanelUI)new PhaseExceptionPanelUI((JComponent)exceptionPanel));
        this.createInstructionManualIdentifyInFirstColumn(exceptionPanel);
        this.createFieldsManualIdentifyInSecondColumn(exceptionPanel);
        this.configureManualIdentificationExceptionView(exceptionPanel);
        return exceptionPanel;
    }

    private void createFieldsManualIdentifyInSecondColumn(final JPanel exceptionPanel) {
        final JPanel panel = PhaseSwingHelper.createPanel();
        panel.setLayout(new GridLayout(0, 2, 9, 9));
        final JLabel identLabel = this.createIdentLabel("Batch_Label", "BatchID");
        (this.edtBatchId = PhaseSwingHelper.createJTextField(IPhaseExecutor.Status.ACTIVE, 4000)).setName(this.edtBatchId.getName() + "_BatchId");
        final JLabel identLabel2 = this.createIdentLabel("Material_Label", "MaterialID");
        identLabel2.setHorizontalAlignment(4);
        (this.edtMaterialId = PhaseSwingHelper.createJTextField(IPhaseExecutor.Status.ACTIVE, 4000)).setName(this.edtMaterialId.getName() + "_MaterialId");
        final JLabel identLabel3 = this.createIdentLabel("Sublot_Label", "SublotID");
        identLabel3.setHorizontalAlignment(4);
        (this.edtSublotId = PhaseSwingHelper.createJTextField(IPhaseExecutor.Status.ACTIVE, 4000)).setName(this.edtSublotId.getName() + "_SublotId");
        final JLabel identLabel4 = this.createIdentLabel("Container_Label", "ContainerID");
        identLabel4.setHorizontalAlignment(4);
        (this.edtContainerId = PhaseSwingHelper.createJTextField(IPhaseExecutor.Status.ACTIVE, 4000)).setName(this.edtContainerId.getName() + "_ContainerId");
        this.changeDependentFieldEnabling();
        panel.add(identLabel);
        panel.add(this.edtBatchId);
        panel.add(identLabel2);
        panel.add(this.edtMaterialId);
        panel.add(identLabel3);
        panel.add(this.edtSublotId);
        panel.add(identLabel4);
        panel.add(this.edtContainerId);
        exceptionPanel.add(panel, PhaseColumnLayout.Column.SECOND_COLUMN);
    }

    private void changeDependentFieldEnabling() {
        this.manualIdentificationDocumentListener = new ManualIdentificationExceptionDocumentListener();
        this.manualIdentificationModel = new ManualIdentificationModel();
        this.edtBatchId.getDocument().addDocumentListener(this.manualIdentificationDocumentListener);
        this.edtMaterialId.getDocument().addDocumentListener(this.manualIdentificationDocumentListener);
        this.edtSublotId.getDocument().addDocumentListener(this.manualIdentificationDocumentListener);
        this.edtContainerId.getDocument().addDocumentListener(this.manualIdentificationDocumentListener);
    }

    private void createInstructionManualIdentifyInFirstColumn(final JPanel exceptionPanel) {
        final JPanel panel = PhaseSwingHelper.createPanel();
        panel.setLayout(new BorderLayout());
        exceptionPanel.add(panel, PhaseColumnLayout.Column.FIRST_COLUMN);
        panel.add(PhaseSwingHelper.createMultiLineLabel(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "ManualIdentExceptionTxt"), 140, "InstructionText1"), "Center");
    }

    private void configureManualIdentificationExceptionView(final JPanel exceptionPanel) {
        final ConfirmButton confirmButton = ((PhaseColumnLayout)exceptionPanel.getLayout()).getConfirmButton();
        confirmButton.setName("ConfirmButtonManualIdentificationException");
        confirmButton.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                MatIdentExceptionView0610.this.fireViewPropertyChange("MANUAL_IDENTIFICATION", (Object)null, (Object)MatIdentExceptionView0610.this.manualIdentificationModel);
            }
        });
        this.edtMaterialId.setEnabled(!((IBatchService)ServiceFactory.getService((Class)IBatchService.class)).areBatchIdentifiersUnique());
        this.setDefaultFocusComponentView((JComponent)this.edtSublotId);
    }

    private JLabel createIdentLabel(final String msgId, final String labelName) {
        final JLabel jLabelSmall = PhaseSwingHelper.createJLabelSmall(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", msgId), labelName);
        jLabelSmall.setHorizontalAlignment(4);
        return jLabelSmall;
    }

    private JPanel createAbortBOMPosExceptionView() {
        final JPanel panel = PhaseSwingHelper.createPanel();
        panel.setUI((PanelUI)new PhaseExceptionPanelUI((JComponent)panel));
        panel.setLayout(new BorderLayout());
        this.createInstructionPanelAbortBOMOnTop(panel);
        this.createMaterialGridAbortBOMInCenter(panel);
        this.createConfirmPanelAbortBOMOnBottom(panel);
        return panel;
    }

    private void createInstructionPanelAbortBOMOnTop(final JPanel exceptionPanel) {
        final JPanel exceptionPanel2 = PhaseSwingHelper.createExceptionPanel(PhaseColumnLayout.Layout.LAYOUT_SINGLE_COLUMN);
        ((PhaseColumnLayout)exceptionPanel2.getLayout()).getConfirmPanel().removeAll();
        exceptionPanel2.add(PhaseSwingHelper.createJLabelSmall(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "CancelBOMItemExceptionTxt"), "InstructionText1"), PhaseColumnLayout.Column.FIRST_COLUMN);
        exceptionPanel.add(exceptionPanel2, "North");
    }

    private void createMaterialGridAbortBOMInCenter(final JPanel exceptionPanel) {
        final JPanel panel = PhaseSwingHelper.createPanel(PhaseColumnLayout.Layout.LAYOUT_ICON_SINGLE_COLUMN, ((IWDMatIdentModel0610)this.getModel()).getStatus());
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        this.cancelBOMPosGrid = (MESStandardGrid)MatIdentViewHelper0610.buildMatIdentActiveGrid((IWDMatIdentModel0610)this.getModel(), false);
        this.cancelBOMPosGrid.getGrid().setName("CommonMESGrid$GridAutoRowTableAbort");
        MatIdentViewHelper0610.refreshMaterialGrid(this.cancelBOMPosGrid, (IWDMatIdentModel0610)this.getModel(), BOMPositionType0610.ABORTABLE);
        panel.add((Component)this.cancelBOMPosGrid);
        exceptionPanel.add(panel, "Center");
    }

    private ConfirmButton createConfirmPanelAbortBOMOnBottom(final JPanel exceptionPanel) {
        final JPanel panel = PhaseSwingHelper.createPanel(PhaseColumnLayout.Layout.LAYOUT_SINGLE_COLUMN, ((IWDMatIdentModel0610)this.getModel()).getStatus());
        exceptionPanel.add(panel, "South");
        final ConfirmButton confirmButton = ((PhaseColumnLayout)panel.getLayout()).getConfirmButton();
        confirmButton.setName("ConfirmButtonCancelBOMPosException");
        if (this.cancelBOMPosGrid.getObjects().isEmpty()) {
            WDHelper0610.disableComponentTree(new Component[] { exceptionPanel });
        }
        else {
            this.addActionListenerAbortBOMAndDefaultFocus(exceptionPanel, confirmButton);
        }
        return confirmButton;
    }

    private void addActionListenerAbortBOMAndDefaultFocus(final JPanel exceptionPanel, final ConfirmButton confirmButton) {
        confirmButton.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (MatIdentExceptionView0610.this.getSelectedCancelBOMPosRow() != null) {
                    MatIdentExceptionView0610.this.fireViewPropertyChange("ABORT_BOM_POSITION", (Object)null, (Object)MatIdentExceptionView0610.this.getSelectedCancelBOMPosRow());
                }
                else {
                    WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "selectBOMPosition_ErrorMsg", new Object[0]);
                }
            }
        });
        setDefaultFocusComponent(exceptionPanel, (JComponent)confirmButton);
    }

    private JPanel createCompleteBOMPosExceptionView() {
        final JPanel panel = PhaseSwingHelper.createPanel();
        panel.setUI((PanelUI)new PhaseExceptionPanelUI((JComponent)panel));
        panel.setLayout(new BorderLayout());
        this.createInstructionCompleteBOMOnTop(panel);
        this.createMaterialGridCompleteBOMInCenter(panel);
        this.createConfirmPanelOnBottom(panel);
        return panel;
    }

    private void createInstructionCompleteBOMOnTop(final JPanel exceptionPanel) {
        final JPanel exceptionPanel2 = PhaseSwingHelper.createExceptionPanel(PhaseColumnLayout.Layout.LAYOUT_SINGLE_COLUMN);
        ((PhaseColumnLayout)exceptionPanel2.getLayout()).getConfirmPanel().removeAll();
        exceptionPanel2.add(PhaseSwingHelper.createJLabelSmall(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "CompleteBOMItemExceptionTxt"), "InstructionText1"), PhaseColumnLayout.Column.FIRST_COLUMN);
        exceptionPanel.add(exceptionPanel2, "North");
    }

    private void createMaterialGridCompleteBOMInCenter(final JPanel exceptionPanel) {
        final JPanel panel = PhaseSwingHelper.createPanel(PhaseColumnLayout.Layout.LAYOUT_ICON_SINGLE_COLUMN, ((IWDMatIdentModel0610)this.getModel()).getStatus());
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        this.completeBOMPosGrid = (MESStandardGrid)MatIdentViewHelper0610.buildMatIdentActiveGrid((IWDMatIdentModel0610)this.getModel(), false);
        this.completeBOMPosGrid.getGrid().setName("CommonMESGrid$GridAutoRowTableComplete");
        MatIdentViewHelper0610.refreshMaterialGrid(this.completeBOMPosGrid, (IWDMatIdentModel0610)this.getModel(), BOMPositionType0610.COMPLETABLE);
        panel.add((Component)this.completeBOMPosGrid);
        exceptionPanel.add(panel, "Center");
    }

    private void createConfirmPanelOnBottom(final JPanel exceptionPanel) {
        final JPanel panel = PhaseSwingHelper.createPanel(PhaseColumnLayout.Layout.LAYOUT_SINGLE_COLUMN, ((IWDMatIdentModel0610)this.getModel()).getStatus());
        exceptionPanel.add(panel, "South");
        final ConfirmButton confirmButton = ((PhaseColumnLayout)panel.getLayout()).getConfirmButton();
        confirmButton.setName("ConfirmButtonCompleteBOMPosException");
        if (this.completeBOMPosGrid.getObjects().isEmpty()) {
            WDHelper0610.disableComponentTree(new Component[] { exceptionPanel });
        }
        else {
            this.addActionListenerCompleteBOMAndDefaultFocus(exceptionPanel, confirmButton);
        }
    }

    private void addActionListenerCompleteBOMAndDefaultFocus(final JPanel exceptionPanel, final ConfirmButton confirmButton) {
        confirmButton.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (MatIdentExceptionView0610.this.getSelectedCompleteBOMPosRow() != null) {
                    MatIdentExceptionView0610.this.fireViewPropertyChange("COMPLETE_BOM_POSITION", (Object)null, (Object)MatIdentExceptionView0610.this.getSelectedCompleteBOMPosRow());
                }
                else {
                    WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "selectBOMPositionComplete_ErrorMsg", new Object[0]);
                }
            }
        });
        setDefaultFocusComponent(exceptionPanel, (JComponent)confirmButton);
    }

    private JPanel createReopenBOMPosExceptionView() {
        final JPanel panel = PhaseSwingHelper.createPanel();
        panel.setUI((PanelUI)new PhaseExceptionPanelUI((JComponent)panel));
        panel.setLayout(new BoxLayout(panel, 3));
        final JPanel instructionReopenBOMOnTop = this.createInstructionReopenBOMOnTop();
        final JPanel materialGridReopenBOMInCenter = this.createMaterialGridReopenBOMInCenter();
        final JPanel reopenPositionInputPanel = this.createReopenPositionInputPanel(panel);
        panel.add(instructionReopenBOMOnTop);
        panel.add(materialGridReopenBOMInCenter);
        panel.add(reopenPositionInputPanel);
        if (this.reopenBOMPosGrid.getObjects().isEmpty()) {
            WDHelper0610.disableComponentTree(new Component[] { panel });
        }
        return panel;
    }

    private JPanel createInstructionReopenBOMOnTop() {
        final JPanel exceptionPanel = PhaseSwingHelper.createExceptionPanel(PhaseColumnLayout.Layout.LAYOUT_SINGLE_COLUMN);
        ((PhaseColumnLayout)exceptionPanel.getLayout()).getConfirmPanel().removeAll();
        exceptionPanel.add(PhaseSwingHelper.createJLabelSmall(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "ReopenBOMItemInstructionTxt"), "InstructionText1"), PhaseColumnLayout.Column.FIRST_COLUMN);
        return exceptionPanel;
    }

    private JPanel createMaterialGridReopenBOMInCenter() {
        final JPanel panel = PhaseSwingHelper.createPanel(PhaseColumnLayout.Layout.LAYOUT_ICON_SINGLE_COLUMN, ((IWDMatIdentModel0610)this.getModel()).getStatus());
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        this.reopenBOMPosGrid = (MESStandardGrid)MatIdentViewHelper0610.buildMatIdentActiveGrid((IWDMatIdentModel0610)this.getModel(), false);
        this.reopenBOMPosGrid.getGrid().setName("CommonMESGrid$GridAutoRowTableReopen");
        MatIdentViewHelper0610.refreshMaterialGrid(this.reopenBOMPosGrid, (IWDMatIdentModel0610)this.getModel(), BOMPositionType0610.REOPEN_OR_WASTE_ALLOWED);
        this.reopenBOMPosGrid.getGrid().getSelectionModel().addListSelectionListener(new ReopenBOMGridListSelectionListener());
        panel.add((Component)this.reopenBOMPosGrid);
        return panel;
    }

    private void resetUoMsAndInputFieldsAccordingSelection(final OrderStepInput masterOsi) {
        this.setUoMWaste(masterOsi);
        this.setUoMRemainingQty(masterOsi);
        this.resetInputFieldsAccordingSelection();
    }

    private void setUoMWaste(final OrderStepInput masterOsi) {
        String uoMFromQty = "";
        if (masterOsi != null) {
            uoMFromQty = this.getUoMFromQty((IMeasuredValue)MESNamedUDAOrderStepInput.getTotalActualQuantity(masterOsi));
        }
        this.lblDeclareWasteUoM.setText(uoMFromQty);
    }

    private void setUoMRemainingQty(final OrderStepInput masterOsi) {
        String text = "";
        if (masterOsi != null) {
            MeasuredValue qty = masterOsi.getPlannedQuantity();
            if (qty == null) {
                qty = MESNamedUDAOrderStepInput.getTotalRemainingQuantity(masterOsi);
            }
            if (qty == null) {
                text = masterOsi.getPart().getUnitOfMeasure();
            }
            else {
                text = this.getUoMFromQty((IMeasuredValue)qty);
            }
        }
        this.lblNewRemainingUoM.setText(text);
    }

    private String getUoMFromQty(final IMeasuredValue qty) {
        return (qty != null && qty.getUnitOfMeasure() != null) ? qty.getUnitOfMeasure().getSymbol() : "";
    }

    private JPanel createReopenPositionInputPanel(final JPanel exceptionPanel) {
        final JPanel exceptionPanel2 = PhaseSwingHelper.createExceptionPanel(PhaseColumnLayout.Layout.LAYOUT_SINGLE_COLUMN);
        exceptionPanel2.setUI((PanelUI)new PhaseExceptionPanelUI((JComponent)exceptionPanel2));
        this.configureReopenConfirmButton(exceptionPanel, exceptionPanel2);
        exceptionPanel2.add(this.createReopenInnerInputPanel(), PhaseColumnLayout.Column.FIRST_COLUMN);
        return exceptionPanel2;
    }

    private void configureReopenConfirmButton(final JPanel exceptionPanel, final JPanel confirmButtonPanel) {
        final ConfirmButton confirmButton = ((PhaseColumnLayout)confirmButtonPanel.getLayout()).getConfirmButton();
        confirmButton.setName("ConfirmButtonReopenBOMPosException");
        if (!this.reopenBOMPosGrid.getObjects().isEmpty()) {
            this.addActionListenerReopenBOMAndDefaultFocus(exceptionPanel, confirmButton);
        }
    }

    private void addActionListenerReopenBOMAndDefaultFocus(final JPanel exceptionPanel, final ConfirmButton confirmButton) {
        confirmButton.addActionListener((ActionListener)new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (MatIdentExceptionView0610.this.getSelectedReopenBOMPosRow() == null) {
                    WDHelper0610.showErrorDialog("wd_MaterialIdentification0610", "selectBOMPositionReopen_ErrorMsg", new Object[0]);
                    return;
                }
                final boolean doResetBOMPosition = ((IWDMatIdentModel0610)MatIdentExceptionView0610.this.getModel()).isDoResetBOMPosition();
                Object access$700 = null;
                Object access$701 = null;
                if (!doResetBOMPosition) {
                    if (!MatIdentExceptionView0610.this.selectedRowIsPlannedQtyNone()) {
                        access$701 = MatIdentExceptionView0610.this.parseAndCheck(MatIdentExceptionView0610.this.edtNewRemaining.getText(), MatIdentExceptionView0610.this.lblNewRemainingUoM.getText(), "DeclareNewRemainingInvalidInput");
                        if (access$701 == null) {
                            return;
                        }
                    }
                    final String text = MatIdentExceptionView0610.this.edtDeclareWaste.getText();
                    if (!StringUtils.isEmpty((CharSequence)text)) {
                        access$700 = MatIdentExceptionView0610.this.parseAndCheck(text, MatIdentExceptionView0610.this.lblDeclareWasteUoM.getText(), "DeclareWasteInvalidInput");
                        if (access$700 == null) {
                            return;
                        }
                    }
                }
                MatIdentExceptionView0610.this.fireViewPropertyChange("REOPEN_POSITION_DECLARE_WASTE", (Object)null, (Object)new Object[] { MatIdentExceptionView0610.this.getSelectedReopenBOMPosRow(), access$701, access$700 });
            }
        });
        setDefaultFocusComponent(exceptionPanel, (JComponent)confirmButton);
    }

    private IMeasuredValue parseAndCheck(final String decimalAsText, final String uomSymbol, final String errorMsgId) {
        final BigDecimal decimal = WDHelper0610.parseDecimal(decimalAsText);
        if (decimal == null) {
            WDHelper0610.showErrorDialog(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", errorMsgId));
            return null;
        }
        return (IMeasuredValue)MeasuredValueUtilities.createMV(decimal, uomSymbol);
    }

    private JPanel createReopenInnerInputPanel() {
        final JPanel fieldInputPanelReopenPosition = this.createFieldInputPanelReopenPosition();
        final JPanel resetModeButtons = this.createResetModeButtons();
        final JPanel panel = PhaseSwingHelper.createPanel();
        panel.setLayout(new BoxLayout(panel, 3));
        panel.add(resetModeButtons);
        panel.add(Box.createVerticalStrut(15));
        panel.add(fieldInputPanelReopenPosition);
        return panel;
    }

    private JPanel createResetModeButtons() {
        final String localizedMessage = I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "AdjustQuantitiesButtonLabel");
        final String localizedMessage2 = I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "ResetPositionButtonLabel");
        final RadioButtonConfigurationBuilder0610 radioButtonConfigurationBuilder0610 = new RadioButtonConfigurationBuilder0610();
        radioButtonConfigurationBuilder0610.addButtonSpecification("adjustValuesButton", "adjustValuesLabel", localizedMessage).addButtonSpecification("resetPositionButton", "resetPositionLabel", localizedMessage2);
        final RadioButtonGroup0610 radioButtonGroup0610 = new RadioButtonGroup0610(radioButtonConfigurationBuilder0610, ((IWDMatIdentModel0610)this.getModel()).getStatus());
        final JPanel buttonGroupPanel = radioButtonGroup0610.createButtonGroupPanel();
        class RadioButtonListener implements IRadioButtonListener0610
        {
            public void statusChanged(final String isActive, final String wasActive) {
                ((IWDMatIdentModel0610)MatIdentExceptionView0610.this.getModel()).setDoResetBOMPosition("resetPositionButton".equals(isActive));
                MatIdentExceptionView0610.this.resetInputFieldsAccordingSelection();
            }
        }
        radioButtonGroup0610.registerListener((IRadioButtonListener0610)new RadioButtonListener());
        radioButtonGroup0610.setButton("adjustValuesButton");
        return buttonGroupPanel;
    }

    private void resetInputFieldsAccordingSelection() {
        final boolean doResetBOMPosition = ((IWDMatIdentModel0610)this.getModel()).isDoResetBOMPosition();
        this.resetNewRemainingField(doResetBOMPosition);
        this.resetDeclareWasteField(doResetBOMPosition);
    }

    private void resetNewRemainingField(final boolean doResetPosition) {
        if (this.edtNewRemaining == null) {
            return;
        }
        final String localizedMessage = I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "ReopenValueNA");
        if (this.selectedRowIsPlannedQtyNone()) {
            this.edtNewRemaining.setEnabled(false);
            this.edtNewRemaining.setText(localizedMessage);
        }
        else {
            this.edtNewRemaining.setEnabled(!doResetPosition);
            this.edtNewRemaining.setText(doResetPosition ? localizedMessage : "");
        }
    }

    private void resetDeclareWasteField(final boolean doResetPosition) {
        if (this.edtDeclareWaste == null) {
            return;
        }
        this.edtDeclareWaste.setEnabled(!doResetPosition);
        final String localizedMessage = I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "ReopenValueNA");
        if (doResetPosition) {
            final MeasuredValue wasteForReset = this.getWasteForReset();
            this.edtDeclareWaste.setText((wasteForReset == null) ? localizedMessage : wasteForReset.getValue().toString());
        }
        else {
            this.edtDeclareWaste.setText("");
        }
    }

    private MeasuredValue getWasteForReset() {
        final MatIdentRow0610 selectedReopenBOMPosRow = this.getSelectedReopenBOMPosRow();
        if (selectedReopenBOMPosRow != null && selectedReopenBOMPosRow.getOSI() != null) {
            return MESNamedUDAOrderStepInput.getTotalActualQuantity(selectedReopenBOMPosRow.getOSI());
        }
        return null;
    }

    private boolean selectedRowIsPlannedQtyNone() {
        final MatIdentRow0610 selectedReopenBOMPosRow = this.getSelectedReopenBOMPosRow();
        return selectedReopenBOMPosRow != null && selectedReopenBOMPosRow.getOSI() != null && isPlannedQtyModeNone(selectedReopenBOMPosRow.getOSI());
    }

    private JPanel createFieldInputPanelReopenPosition() {
        final JPanel panel = PhaseSwingHelper.createPanel();
        panel.setLayout(new BoxLayout(panel, 2));
        final JPanel fieldInputLabelPanelReopenPosition = this.createFieldInputLabelPanelReopenPosition();
        final JPanel fieldInputFieldPanelReopenPosition = this.createFieldInputFieldPanelReopenPosition();
        panel.add(fieldInputLabelPanelReopenPosition);
        panel.add(Box.createHorizontalStrut(90));
        panel.add(fieldInputFieldPanelReopenPosition);
        panel.add(Box.createHorizontalStrut(390));
        panel.add(Box.createHorizontalGlue());
        return panel;
    }

    private JPanel createFieldInputLabelPanelReopenPosition() {
        final JPanel panel = PhaseSwingHelper.createPanel();
        panel.setLayout(new BoxLayout(panel, 3));
        final JLabel labelDeclareWaste = this.createLabelDeclareWaste();
        final JLabel labelNewRemaining = this.createLabelNewRemaining();
        panel.add(labelDeclareWaste);
        panel.add(Box.createVerticalStrut(10));
        panel.add(labelNewRemaining);
        return panel;
    }

    private JPanel createFieldInputFieldPanelReopenPosition() {
        final JPanel panel = PhaseSwingHelper.createPanel();
        panel.setLayout(new BoxLayout(panel, 3));
        final JPanel inputFieldDeclareWaste = this.createInputFieldDeclareWaste();
        final JPanel fieldsNewRemaining = this.createFieldsNewRemaining();
        panel.add(inputFieldDeclareWaste);
        panel.add(Box.createVerticalStrut(10));
        panel.add(fieldsNewRemaining);
        return panel;
    }

    private JLabel createLabelNewRemaining() {
        final JLabel jLabelSmall = PhaseSwingHelper.createJLabelSmall(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "NewRemainingQtyLabel"), "newRemainingLabel");
        jLabelSmall.setName("NewRemainingQtyLabel");
        return jLabelSmall;
    }

    private JLabel createLabelDeclareWaste() {
        final JLabel jLabelSmall = PhaseSwingHelper.createJLabelSmall(I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "DeclareWasteQtyLabel"), "InstructionText1");
        jLabelSmall.setName("DeclareWasteInstructionText");
        return jLabelSmall;
    }

    private JPanel createFieldsNewRemaining() {
        this.edtNewRemaining = this.createNumericInputField("New remaining quantity", "_NewRemainingQty");
        this.lblNewRemainingUoM = PhaseSwingHelper.createJLabelSmall("", "NewRemainingQtyUoM");
        return this.createInputFieldQuantityWithUoM(this.edtNewRemaining, this.lblNewRemainingUoM);
    }

    private JPanel createInputFieldDeclareWaste() {
        this.edtDeclareWaste = this.createNumericInputField("Declare waste", "_DeclareWaste");
        this.lblDeclareWasteUoM = PhaseSwingHelper.createJLabelSmall("", "DeclareWasteUoM");
        return this.createInputFieldQuantityWithUoM(this.edtDeclareWaste, this.lblDeclareWasteUoM);
    }

    private JPanel createInputFieldQuantityWithUoM(final JTextField editField, final JLabel uomLabel) {
        final JPanel panel = PhaseSwingHelper.createPanel();
        panel.setLayout(new BoxLayout(panel, 2));
        panel.add(editField);
        panel.add(Box.createHorizontalStrut(9));
        panel.add(uomLabel);
        panel.add(Box.createHorizontalGlue());
        return panel;
    }

    private JTextField createNumericInputField(final String caption, final String suffixNameForMarathon) {
        final JTextField numericTouchField = PhaseSwingHelper.createNumericTouchField(IPhaseExecutor.Status.ACTIVE, caption);
        numericTouchField.setMaximumSize(new Dimension(140, 30));
        numericTouchField.setMinimumSize(new Dimension(140, 30));
        numericTouchField.setPreferredSize(new Dimension(140, 30));
        numericTouchField.setName(numericTouchField.getName() + suffixNameForMarathon);
        return numericTouchField;
    }

    public String getDisplayedBatchId() {
        return this.edtBatchId.getText();
    }

    public String getDisplayedMaterialId() {
        return this.edtMaterialId.getText();
    }

    public MatIdentRow0610 getSelectedCancelBOMPosRow() {
        MatIdentRow0610 matIdentRow0610 = null;
        if (this.cancelBOMPosGrid != null) {
            final Object selectedRowObject = this.cancelBOMPosGrid.getSelectedRowObject();
            if (selectedRowObject != null) {
                matIdentRow0610 = (MatIdentRow0610)selectedRowObject;
            }
        }
        return matIdentRow0610;
    }

    public MatIdentRow0610 getSelectedCompleteBOMPosRow() {
        MatIdentRow0610 matIdentRow0610 = null;
        if (this.completeBOMPosGrid != null) {
            final Object selectedRowObject = this.completeBOMPosGrid.getSelectedRowObject();
            if (selectedRowObject != null) {
                matIdentRow0610 = (MatIdentRow0610)selectedRowObject;
            }
        }
        return matIdentRow0610;
    }

    public MatIdentRow0610 getSelectedReopenBOMPosRow() {
        MatIdentRow0610 matIdentRow0610 = null;
        if (this.reopenBOMPosGrid != null) {
            final Object selectedRowObject = this.reopenBOMPosGrid.getSelectedRowObject();
            if (selectedRowObject != null) {
                matIdentRow0610 = (MatIdentRow0610)selectedRowObject;
            }
        }
        return matIdentRow0610;
    }

    public void refreshGrids() {
        if (this.cancelBOMPosGrid != null) {
            MatIdentViewHelper0610.refreshMaterialGrid(this.cancelBOMPosGrid, (IWDMatIdentModel0610)this.getModel(), BOMPositionType0610.ABORTABLE);
            this.cancelBOMPosGrid.invalidate();
        }
        if (this.completeBOMPosGrid != null) {
            MatIdentViewHelper0610.refreshMaterialGrid(this.completeBOMPosGrid, (IWDMatIdentModel0610)this.getModel(), BOMPositionType0610.COMPLETABLE);
            this.completeBOMPosGrid.invalidate();
        }
        if (this.reopenBOMPosGrid != null) {
            MatIdentViewHelper0610.refreshMaterialGrid(this.reopenBOMPosGrid, (IWDMatIdentModel0610)this.getModel(), BOMPositionType0610.REOPEN_OR_WASTE_ALLOWED);
            this.reopenBOMPosGrid.getGrid().getSelectionModel().addListSelectionListener(new ReopenBOMGridListSelectionListener());
            this.reopenBOMPosGrid.invalidate();
        }
        this.validate();
        this.repaint();
    }

    private JPanel createCloseTargetExceptionView() {
        final JPanel exceptionPanel = PhaseSwingHelper.createExceptionPanel(PhaseColumnLayout.Layout.LAYOUT_SINGLE_COLUMN);
        exceptionPanel.setUI((PanelUI)new PhaseExceptionPanelUI((JComponent)exceptionPanel));
        this.createInstructionTextCloseTargetInFirstColumn(exceptionPanel);
        this.createConfirmButtonCloseTarget(exceptionPanel);
        if (!((IWDMatIdentModel0610)this.getModel()).getKeepTarget()) {
            WDHelper0610.disableComponentTree(new Component[] { exceptionPanel });
        }
        return exceptionPanel;
    }

    private void createInstructionTextCloseTargetInFirstColumn(final JPanel exceptionPanel) {
        exceptionPanel.add(PhaseSwingHelper.createJLabelSmall((this.getModel()).getWeighingOperationTypeWDMatIdent0610().getLabelCloseTargetExceptionPanel(this.getModel()), "InstructionText1"), PhaseColumnLayout.Column.FIRST_COLUMN);
    }

    private void createConfirmButtonCloseTarget(final JPanel exceptionPanel) {
        final ConfirmButton confirmButton = ((PhaseColumnLayout)exceptionPanel.getLayout()).getConfirmButton();
        confirmButton.setName("ConfirmButtonPrintLabelAndReleaseScale");
        this.configureViewPropertyButton((JButton)confirmButton, "CLOSE_TARGET");
    }

    private JPanel createUndoIdentificationExceptionView() {
        final JPanel exceptionPanel = PhaseSwingHelper.createExceptionPanel(PhaseColumnLayout.Layout.LAYOUT_SINGLE_COLUMN);
        exceptionPanel.setUI((PanelUI)new PhaseExceptionPanelUI((JComponent)exceptionPanel));
        this.createInfoLableUndoIdentifyInFirstColumn(exceptionPanel);
        this.createConfirmButtonUndoIdentify(exceptionPanel);
        return exceptionPanel;
    }

    private void createInfoLableUndoIdentifyInFirstColumn(final JPanel exceptionPanel) {
        final Sublot identifiedSublot = ((IWDMatIdentModel0610)this.getModel()).getIdentifiedSublot();
        final IMESContainerEquipment currentSourceContainer = ((IWDMatIdentModel0610)this.getModel()).getCurrentSourceContainer();
        String s;
        if (currentSourceContainer != null) {
            s = I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "undoIdentificationWithContainerExcInstructionTxt", new Object[] { AbstractWeighView0610.getSublotName(identifiedSublot), currentSourceContainer.getName() });
        }
        else {
            s = I18nMessageUtility.getLocalizedMessage("wd_MaterialIdentification0610", "undoIdentificationExceptionTxt", new Object[] { AbstractWeighView0610.getSublotName(identifiedSublot) });
        }
        exceptionPanel.add(PhaseSwingHelper.createJLabelSmall(s, "InstructionTextUndoIdentification"), PhaseColumnLayout.Column.FIRST_COLUMN);
    }

    private void createConfirmButtonUndoIdentify(final JPanel exceptionPanel) {
        final ConfirmButton confirmButton = ((PhaseColumnLayout)exceptionPanel.getLayout()).getConfirmButton();
        confirmButton.setName("ConfirmButtonUndoIdentification");
        this.configureViewPropertyButton((JButton)confirmButton, "UNDO_IDENTIFICATION");
    }

    private JPanel createRecordWarehouseErrorPanel() {
        final JPanel exceptionPanel = PhaseSwingHelper.createExceptionPanel(PhaseColumnLayout.Layout.LAYOUT_TWO_1ST_WIDER_COLUMN);
        exceptionPanel.setUI((PanelUI)new PhaseExceptionPanelUI((JComponent)exceptionPanel));
        final JLabel jLabelSmall = PhaseSwingHelper.createJLabelSmall(I18nMessageUtility.getLocalizedMessage(((IWDMatIdentModel0610)this.getModel()).getMessagePackWarehouseError(), "RecordWarehouseError_Label"), "recordWarehouseErrorLabel");
        jLabelSmall.setName("recordWarehouseErrorLabel");
        exceptionPanel.add(jLabelSmall, PhaseColumnLayout.Column.FIRST_COLUMN);
        this.createConfirmButtonRecordWarehouseError(exceptionPanel);
        return exceptionPanel;
    }

    private void createConfirmButtonRecordWarehouseError(final JPanel exceptionPanel) {
        final ConfirmButton confirmButton = ((PhaseColumnLayout)exceptionPanel.getLayout()).getConfirmButton();
        confirmButton.setName("ConfirmButtonWarehouseError");
        this.configureViewPropertyButton((JButton)confirmButton, "WAREHOUSE_ERROR");
    }

    private class ReopenBOMGridListSelectionListener implements ListSelectionListener
    {
        @Override
        public void valueChanged(final ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                return;
            }
            final MatIdentRow0610 selectedReopenBOMPosRow = MatIdentExceptionView0610.this.getSelectedReopenBOMPosRow();
            if (selectedReopenBOMPosRow == null) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        MatIdentExceptionView0610.this.resetUoMsAndInputFieldsAccordingSelection(null);
                    }
                });
                return;
            }
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    MatIdentExceptionView0610.this.resetUoMsAndInputFieldsAccordingSelection(selectedReopenBOMPosRow.getOSI());
                }
            });
        }
    }

    class ManualIdentificationExceptionDocumentListener implements DocumentListener
    {
        @Override
        public void insertUpdate(final DocumentEvent e) {
            this.setFieldStatus();
        }

        @Override
        public void removeUpdate(final DocumentEvent e) {
            this.setFieldStatus();
        }

        @Override
        public void changedUpdate(final DocumentEvent e) {
            this.setFieldStatus();
        }

        private void setFieldStatus() {
            MatIdentExceptionView0610.this.edtBatchId.setEnabled((StringUtils.isEmpty((CharSequence)MatIdentExceptionView0610.this.edtSublotId.getText()) && StringUtils.isEmpty((CharSequence)MatIdentExceptionView0610.this.edtContainerId.getText())) || !MatIdentExceptionView0610.this.manualIdentificationModel.isSublotUnique());
            MatIdentExceptionView0610.this.edtMaterialId.setEnabled(StringUtils.isEmpty((CharSequence)MatIdentExceptionView0610.this.edtSublotId.getText()) && StringUtils.isEmpty((CharSequence)MatIdentExceptionView0610.this.edtContainerId.getText()));
            MatIdentExceptionView0610.this.edtSublotId.setEnabled(StringUtils.isEmpty((CharSequence)MatIdentExceptionView0610.this.edtContainerId.getText()) && StringUtils.isEmpty((CharSequence)MatIdentExceptionView0610.this.edtBatchId.getText()) && StringUtils.isEmpty((CharSequence)MatIdentExceptionView0610.this.edtMaterialId.getText()));
            MatIdentExceptionView0610.this.edtContainerId.setEnabled(StringUtils.isEmpty((CharSequence)MatIdentExceptionView0610.this.edtSublotId.getText()) && StringUtils.isEmpty((CharSequence)MatIdentExceptionView0610.this.edtBatchId.getText()) && StringUtils.isEmpty((CharSequence)MatIdentExceptionView0610.this.edtMaterialId.getText()));
        }
    }

    class ManualIdentificationModel
    {
        private boolean sublotUnique;

        ManualIdentificationModel() {
            this.sublotUnique = true;
        }

        String getSublotIdentifier() {
            return MatIdentExceptionView0610.this.edtSublotId.getText();
        }

        String getBatchIdentifier() {
            return MatIdentExceptionView0610.this.edtBatchId.getText();
        }

        String getPartIdentifier() {
            return MatIdentExceptionView0610.this.edtMaterialId.getText();
        }

        String getContainerIdentifier() {
            return MatIdentExceptionView0610.this.edtContainerId.getText();
        }

        boolean hasContainer() {
            return StringUtils.isNotEmpty((CharSequence)this.getContainerIdentifier());
        }

        boolean hasSublot() {
            return StringUtils.isNotEmpty((CharSequence)this.getSublotIdentifier());
        }

        boolean isSublotUnique() {
            return this.sublotUnique;
        }

        void setSublotUnique(final boolean value) {
            this.sublotUnique = value;
        }

        boolean isEmpty() {
            final String[] array = { this.getSublotIdentifier(), this.getBatchIdentifier(), this.getPartIdentifier(), this.getContainerIdentifier() };
            for (int length = array.length, i = 0; i < length; ++i) {
                if (StringUtils.isNotEmpty((CharSequence)array[i])) {
                    return false;
                }
            }
            return true;
        }
    }
}
