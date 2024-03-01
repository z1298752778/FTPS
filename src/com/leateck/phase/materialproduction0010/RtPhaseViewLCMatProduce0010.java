package com.leateck.phase.materialproduction0010;

import com.datasweep.compatibility.client.MeasuredValue;
import com.datasweep.compatibility.client.Part;
import com.datasweep.compatibility.client.UnitOfMeasure;
import com.datasweep.plantops.common.measuredvalue.IMeasuredValue;
import com.datasweep.plantops.common.measuredvalue.IUnitOfMeasure;
import com.rockwell.mes.apps.ebr.ifc.phase.IPhaseExecutor;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.IPhaseStandardGridConfigurator;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.UIConstants;
import com.rockwell.mes.apps.ebr.ifc.swing.ConfirmButton;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseButton;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseSwingHelper;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.exceptions.MESRuntimeException;
import com.rockwell.mes.commons.base.ifc.functional.BigDecimalUtilities;
import com.rockwell.mes.commons.base.ifc.functional.MeasuredValueUtilities;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import com.rockwell.mes.commons.base.ifc.utility.Limits;
import com.rockwell.mes.commons.base.ifc.utility.WaitCursor;
import com.rockwell.mes.commons.shared.phase.components.swing.PhaseRadioButton0300;
import com.rockwell.mes.commons.shared.phase.mvc.AbstractPhaseMainView0200;
import com.rockwell.mes.services.commons.ifc.packaginglevels.IPackagingLevelBean;
import com.rockwell.mes.services.s88.ifc.recipe.PlannedQuantityMode;
import com.rockwell.mes.shared.product.material.AbstractMaterialDAO0710;
import com.rockwell.mes.shared.product.material.AbstractProcMaterialDAO0710;
import com.rockwell.mes.shared.product.material.PhaseResult0710;
import com.rockwell.mes.shared0400.product.ui.basics.PhaseUIConstants;
import com.rockwell.mes.shared0400.product.ui.basics.impl.ColumnInfo;
import com.rockwell.mes.shared0400.product.ui.basics.impl.PhaseStandardGrid;
import com.rockwell.mes.shared0400.product.ui.basics.impl.PhaseToggleUomButton;
import com.rockwell.mes.shared0400.product.ui.basics.impl.ProductPhaseColumnLayoutPanel;
import com.rockwell.mes.shared0400.product.ui.basics.util.ProductPhaseSwingHelper;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class RtPhaseViewLCMatProduce0010 extends AbstractPhaseMainView0200<RtPhaseModelLCMatProduce0010>
{
    private static final long serialVersionUID = 1L;
    private JPanel phasePanel;
    private JPanel centralPanel;
    private transient PhaseStandardGrid grid;
    private transient PhaseToggleUomButton toggleUoM;
    private JTextField inputQty;
    private JTextField inputNumOfSublots;
    private PhaseRadioButton0300 continueButton;
    private PhaseRadioButton0300 doneButton;
    public static final int SMALL_COLUMN_WIDTH = 120;
    public static final int MEDIUM_COLUMN_WIDTH = 200;
    public static final int BIG_COLUMN_WIDTH = 300;
    private static final int HGAP_FOR_SUMMARY_PANEL = 30;
    private JLabel totalValueLabel;
    private static final String EQUAL_OPERATOR = " =";
    private UnitOfMeasure targetUoM;
    private transient List<JTextField> contentEntryTextFieldsList;
    private transient List<JLabel> resultLabelsList;
    private String lowerCaseLevelName;
    private static final String END_CHAR_SINGULAR = "_S";
    private static final String END_CHAR_PLURAL = "_P";
    private static final String END_CHAR_SINGULAR_PLURAL = "_SP";
    private static final String PREFIX_FOR_PACKING_NAME = "PackagingLevel_";
    private final BigDecimal resultValue;
    public static final String COMMA_SEPARATOR = ",";
    private static final int INPUT_FIELD_WIDTH = 125;
    private static final int INPUT_FIELD_HEIGHT = 20;
    protected static final String NAVIGATOR_FALL_THROUGH = "---";
    private static final String CONTAIN_MSG;
    private static final int SEPARATOR_PANEL_WIDTH = 340;
    private static final int WIDTH_FOR_TOTAL_VALUE_LABEL = 155;
    private static final DecimalFormatSymbols CURRENT_DECIMAL_FORMATS;
    public static final char DECIMAL_SEPARATOR;
    private boolean waiting;
    private transient WaitCursor waitCursor;
    private transient IMatProduceEventListener0710 eventListener;

    protected RtPhaseViewLCMatProduce0010(final RtPhaseModelLCMatProduce0010 theModel, final IMatProduceEventListener0710 theListener) {
        super(theModel);
        this.totalValueLabel = null;
        this.targetUoM = null;
        this.contentEntryTextFieldsList = null;
        this.resultLabelsList = null;
        this.lowerCaseLevelName = "";
        this.resultValue = new BigDecimal(0);
        this.eventListener = theListener;
    }

    protected void createUI() {
        this.setOpaque(false);
        final boolean equals = this.getModel().getStatus().equals(IPhaseExecutor.Status.ACTIVE);
        (this.phasePanel = new JPanel()).setEnabled(equals);
        this.phasePanel.setOpaque(false);
        this.phasePanel.setLayout(new BorderLayout());
        this.createTopPanel(equals);
        this.createCompletePanel();
        (this.centralPanel = new JPanel()).setEnabled(equals);
        this.centralPanel.setOpaque(false);
        this.centralPanel.setLayout(new BorderLayout());
        this.createSummaryPanel(equals);
        this.createGridPanel(equals);
        this.createCommentPanel(equals);
        this.phasePanel.add(this.centralPanel, "Center");
        this.setLayout(new GridBagLayout());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = 10;
        this.add(this.phasePanel, gridBagConstraints);
    }

    private void createTopPanel(final boolean isActive) {
        final JPanel panel = new JPanel();
        panel.setEnabled(isActive);
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        final JPanel panel2 = new JPanel();
        panel2.setEnabled(isActive);
        panel2.setOpaque(false);
        this.createNoPackinglevelsPanel(panel2);
        this.createPackinglevelsPanel(panel2, isActive);
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = 26;
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.weightx = 0.0;
        panel.add(panel2, gridBagConstraints);
        final JPanel inputPanel = this.createInputPanel(isActive);
        final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
        gridBagConstraints2.gridx = 0;
        gridBagConstraints2.gridy = 0;
        gridBagConstraints2.fill = 1;
        gridBagConstraints2.weighty = 1.0;
        gridBagConstraints2.weightx = 1.0;
        panel.add(inputPanel, gridBagConstraints2);
        this.phasePanel.add(panel, "North");
    }

    private JPanel createInputPanel(final boolean isActive) {
        final JPanel panel = new JPanel();
        panel.setName("inputPanel");
        panel.setEnabled(isActive);
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        final JLabel jLabel = ProductPhaseSwingHelper.createJLabel(this.getModel().getInstruction(), ProductPhaseColumnLayoutPanel.Layout.LAYOUT_TWO_1ST_WIDER_COLUMN.getColumnWidth(ProductPhaseColumnLayoutPanel.Column.FIRST_COLUMN));
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weighty = 32767.0;
        gridBagConstraints.weightx = 32767.0;
        gridBagConstraints.insets = new Insets(9, 9, 9, 0);
        panel.add(jLabel, gridBagConstraints);
        return panel;
    }

    private static String getBtnTextRefresh() {
        return I18nMessageUtility.getLocalizedMessage("PhaseProductMaterial0710", "BtnTextRefresh");
    }

    private void createNoPackinglevelsPanel(final JPanel panel) {
        final IUnitOfMeasure defaultUoM = this.getModel().getDefaultUoM();
        if (defaultUoM != null) {
            this.targetUoM = (UnitOfMeasure)defaultUoM;
        }
        if (!this.getModel().arePackagingLevelsDefined()) {
            final JLabel initSmallLabel = this.initSmallLabel(this.getNumOfSublotsLabel());
            initSmallLabel.setName("LabelNumberOfSublots");
            final JLabel initSmallLabel2 = this.initSmallLabel(this.getQtyLabel());
            initSmallLabel2.setName("LabelQuantityPerSublots");
            this.initNumOfSublotsField();
            this.initQtyField();
            final Part phaseOutputMaterial = this.getModel().getPhaseOutputMaterial();
            if (phaseOutputMaterial != null) {
                toggleUoM = new PhaseToggleUomButton("phaseUoMButton", phaseOutputMaterial, getModel().getStatus(), true);
            }
            else {
                this.toggleUoM = new PhaseToggleUomButton("phaseUoMButton", this.getModel().getDefaultUoM(), this.getModel().getStatus(), true);
            }
            this.toggleUoM.getButton().setName("ButtonToggleUom");
            this.toggleUoM.getButton().addChangeListener(e -> this.getModel().getExecutor().totalCalculationForNoPackingLevel());
            final GroupLayout layout = new GroupLayout(panel);
            panel.setBorder(new EmptyBorder(0, 0, 0, 14));
            panel.setLayout(layout);
            final int n = -2;
            final int n2 = -1;
            final int n3 = (this.toggleUoM.getButton().getPreferredSize().height - UIConstants.EDIT_PREFERRED_SIZE.height) / 2;
            layout.setHorizontalGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup().addComponent(initSmallLabel, n, n2, n).addComponent(initSmallLabel2, n, n2, n)).addGap(5).addGroup(layout.createParallelGroup().addComponent(this.inputNumOfSublots, n, n2, n).addComponent(this.inputQty, n, n2, n)).addGroup(layout.createParallelGroup().addGroup(layout.createSequentialGroup().addGap(PhaseUIConstants.BUTTON_LEFT_GAP).addComponent(this.toggleUoM.getButton(), n, n2, n).addGap(39))));
            layout.setVerticalGroup(layout.createSequentialGroup().addGap(10).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(initSmallLabel, n, n2, n).addComponent(this.inputNumOfSublots, n, n2, n)).addGap(14 - n3).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(n3).addComponent(initSmallLabel2, n, n2, n)).addGroup(layout.createSequentialGroup().addGap(n3).addComponent(this.inputQty, n, n2, n)).addComponent(this.toggleUoM.getButton(), GroupLayout.Alignment.TRAILING, n, n2, n)).addGap(1));
        }
    }

    private void createPackinglevelsPanel(final JPanel panel, final boolean isActive) {
        final IUnitOfMeasure defaultUoM = this.getModel().getDefaultUoM();
        if (defaultUoM != null) {
            this.targetUoM = (UnitOfMeasure)defaultUoM;
        }
        if (this.getModel().arePackagingLevelsDefined()) {
            this.contentEntryTextFieldsList = new ArrayList<>();
            this.resultLabelsList = new ArrayList<>();
            final JPanel panel2 = new JPanel();
            panel2.setLayout(new GridBagLayout());
            final GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = 1;
            panel2.setBorder(new EmptyBorder(0, 0, 0, 15));
            panel2.setOpaque(false);
            int n = 0;
            int n2 = 0;
            final ArrayList<IPackagingLevelBean> pakgLevelContainValuesList = new ArrayList<>();
            pakgLevelContainValuesList.addAll(this.getModel().getExecutor().getConfiguredPackingLevels());
            this.createTextFieldsAndResultLabels(pakgLevelContainValuesList, isActive);
            for (int i = 0; i < pakgLevelContainValuesList.size(); ++i) {
                if (i == 0) {
                    n = pakgLevelContainValuesList.size() - 1;
                }
                else if (i == pakgLevelContainValuesList.size() - 1) {
                    n = 0;
                }
                else {
                    --n;
                }
                final IPackagingLevelBean packagingLevelBean = pakgLevelContainValuesList.get(n);
                final JLabel jLabel = PhaseSwingHelper.createJLabel("  " + (packagingLevelBean.getPackagingLevel().getMeaning().equals("NotApplicable") ? "" : I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "PackagingLevel_" + packagingLevelBean.getPackagingLevel().getMeaning() + "_SP")));
                jLabel.setPreferredSize(new Dimension(168, 20));
                jLabel.setName("packingLevelNameLabel" + i);
                jLabel.setBorder(new EmptyBorder(0, 4, 0, 0));
                JLabel jLabel2;
                if (null != packagingLevelBean.getInventoryLevel() && !packagingLevelBean.getInventoryLevel().getMeaning().equals("None")) {
                    jLabel2 = PhaseSwingHelper.createJLabel(" " + getInventoryShortName(packagingLevelBean.getInventoryLevel().getMeaning()));
                }
                else {
                    jLabel2 = new JLabel(" ");
                }
                jLabel2.setPreferredSize(new Dimension(80, 20));
                jLabel2.setName("inventoryLevelNameLabel" + i);
                final JLabel jLabel3 = PhaseSwingHelper.createJLabel(RtPhaseViewLCMatProduce0010.CONTAIN_MSG);
                jLabel3.setName("containingTextLabel" + i);
                jLabel3.setHorizontalAlignment(2);
                jLabel3.setPreferredSize(new Dimension(82, 20));
                if (i == 0) {
                    n2 = pakgLevelContainValuesList.size() - 2;
                }
                else if (i == pakgLevelContainValuesList.size() - 1) {
                    n2 = 0;
                }
                else {
                    --n2;
                }
                String s = pakgLevelContainValuesList.get(n2 + 1).getPackagingLevelContent().toString();
                String s2 = " ";
                if (pakgLevelContainValuesList.size() > 1) {
                    s2 = pakgLevelContainValuesList.get(n2).getPackagingLevel().getMeaning();
                }
                if (i == pakgLevelContainValuesList.size() - 1) {
                    final IMESChoiceElement defaultPackingChoiceElement = this.getModel().getDefaultPackingChoiceElement();
                    if (defaultPackingChoiceElement == null || defaultPackingChoiceElement.getMeaning().equals("NotApplicable")) {
                        s2 = "";
                    }
                    else {
                        s2 = defaultPackingChoiceElement.getMeaning();
                    }
                    s = pakgLevelContainValuesList.get(0).getPackagingLevelContent().toString();
                    if (pakgLevelContainValuesList.size() > 1 && pakgLevelContainValuesList.get(n2).getPackagingLevelNumber() == 0 && !pakgLevelContainValuesList.get(i).isHiddenDuringExecution()) {
                        s2 = "";
                        s = "";
                        jLabel3.setText("");
                    }
                }
                final int n3 = s.equals("") ? 0 : Integer.parseInt(s);
                if (n3 == 1) {
                    this.lowerCaseLevelName = (StringUtils.isNotBlank(s2) ? convertToFirstLetterLower(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "PackagingLevel_" + s2 + "_S")) : "");
                }
                else if (n3 > 1) {
                    this.lowerCaseLevelName = (StringUtils.isNotBlank(s2) ? convertToFirstLetterLower(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "PackagingLevel_" + s2 + "_P")) : "");
                }
                if (n3 == 0 && n2 == 0) {
                    this.lowerCaseLevelName = "";
                }
                final JLabel jLabel4 = PhaseSwingHelper.createJLabel(s + " " + this.lowerCaseLevelName);
                jLabel4.setName("containedNumberLabel" + i);
                jLabel4.setHorizontalAlignment(4);
                jLabel4.setPreferredSize(new Dimension(180, 32));
                jLabel4.setBorder(new EmptyBorder(0, 3, 0, 0));
                final JLabel jLabel5 = PhaseSwingHelper.createJLabel("  =");
                jLabel5.setName("equalOperatorLabel" + i);
                jLabel5.setHorizontalAlignment(2);
                jLabel5.setPreferredSize(new Dimension(27, 20));
                if (!packagingLevelBean.isHiddenDuringExecution()) {
                    gridBagConstraints.insets = new Insets(6, 0, 6, 0);
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = i;
                    panel2.add(this.contentEntryTextFieldsList.get(i), gridBagConstraints);
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = i;
                    panel2.add(jLabel, gridBagConstraints);
                    gridBagConstraints.gridx = 2;
                    gridBagConstraints.gridy = i;
                    panel2.add(jLabel2, gridBagConstraints);
                    gridBagConstraints.gridx = 3;
                    gridBagConstraints.gridy = i;
                    panel2.add(jLabel3, gridBagConstraints);
                    gridBagConstraints.gridx = 4;
                    gridBagConstraints.gridy = i;
                    panel2.add(jLabel4, gridBagConstraints);
                    gridBagConstraints.gridx = 5;
                    gridBagConstraints.gridy = i;
                    panel2.add(jLabel5, gridBagConstraints);
                    gridBagConstraints.gridx = 6;
                    gridBagConstraints.gridy = i;
                    panel2.add(this.resultLabelsList.get(i), gridBagConstraints);
                    panel2.repaint();
                    panel2.revalidate();
                }
            }
            panel.add(panel2);
        }
    }

    private void createTextFieldsAndResultLabels(final List<IPackagingLevelBean> pakgLevelContainValuesList, final boolean isActive) {
        for (int i = 0; i < pakgLevelContainValuesList.size(); ++i) {
            final JTextField jTextField = PhaseSwingHelper.createJTextField(IPhaseExecutor.Status.ACTIVE, 9);
            jTextField.setEnabled(isActive);
            jTextField.setName("PackingLevelTextField_" + i);
            jTextField.setBorder(new EmptyBorder(0, 3, 0, 0));
            jTextField.setPreferredSize(new Dimension(125, 20));
            jTextField.setHorizontalAlignment(4);
            jTextField.getDocument().putProperty("owner", jTextField);
            this.contentEntryTextFieldsList.add(jTextField);
            ((AbstractDocument)jTextField.getDocument()).setDocumentFilter(new DocumentFilter() {
                @Override
                public void replace(final FilterBypass fb, final int offset, final int length, final String text, final AttributeSet attrs) throws BadLocationException {
                    if (text.matches("^[0-9]{0,9}$")) {
                        final JTextField textField = (JTextField)fb.getDocument().getProperty("owner");
                        super.replace(fb, offset, length, text, attrs);
                        RtPhaseViewLCMatProduce0010.this.calculateSummary(textField.getName());
                    }
                }

                @Override
                public void insertString(final FilterBypass fb, final int offset, final String text, final AttributeSet attr) throws BadLocationException {
                    if (text.matches("^[0-9]{0,9}$")) {
                        final JTextField textField = (JTextField)fb.getDocument().getProperty("owner");
                        super.insertString(fb, offset, text, attr);
                        RtPhaseViewLCMatProduce0010.this.calculateSummary(textField.getName());
                    }
                }

                @Override
                public void remove(final FilterBypass fb, final int offset, final int length) throws BadLocationException {
                    final JTextField textField = (JTextField)fb.getDocument().getProperty("owner");
                    super.remove(fb, offset, length);
                    RtPhaseViewLCMatProduce0010.this.calculateSummary(textField.getName());
                }
            });
            jTextField.repaint();
            jTextField.revalidate();
            final JLabel jLabel = PhaseSwingHelper.createJLabel(PCContext.getFunctions().createMeasuredValue(this.resultValue, this.targetUoM, 0).toString());
            jLabel.setName("calculatedValue" + i);
            jLabel.setHorizontalAlignment(4);
            jLabel.setPreferredSize(new Dimension(138, 20));
            this.resultLabelsList.add(jLabel);
        }
    }

    private void createCompletePanel() {
        final ProductPhaseColumnLayoutPanel productPhaseColumnLayoutPanel = new ProductPhaseColumnLayoutPanel(ProductPhaseColumnLayoutPanel.Layout.LAYOUT_SINGLE_COLUMN, this.getModel().getStatus());
        final boolean equals = this.getModel().getStatus().equals(IPhaseExecutor.Status.ACTIVE);
        final JPanel panel = new JPanel(new GridBagLayout());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 3, 0, 3);
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(2, 0, -2, 530));
        this.continueButton = this.createResultSelectionBtn(equals, PhaseResult0710.CONTINUE, "ContinueButton", evt -> this.eventListener.onContinueButtonPressed());
        if (this.getModel().isLoopStopEnabled()) {
            this.doneButton = this.createResultSelectionBtn(equals, PhaseResult0710.STOP, "StopButton", evt -> this.eventListener.onStopButtonPressed());
        }
        else {
            this.doneButton = this.createResultSelectionBtn(equals, PhaseResult0710.DONE, "DoneButton", evt -> this.eventListener.onDoneButtonPressed());
        }
        this.selectButtonAsPhaseResult();
        final ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(this.continueButton);
        buttonGroup.add(this.doneButton);
        panel.add(this.continueButton, gridBagConstraints);
        panel.add(this.doneButton, gridBagConstraints);
        productPhaseColumnLayoutPanel.setBorder(new EmptyBorder(2, 0, 0, 0));
        productPhaseColumnLayoutPanel.addToColumn(ProductPhaseColumnLayoutPanel.Column.FIRST_COLUMN, panel, 17);
        final ConfirmButton confirmButton = productPhaseColumnLayoutPanel.getConfirmButton();
        confirmButton.setName("ConfirmButton");
        this.setConfirmButton(confirmButton);
        this.configureConfirmButton();
        if (this.getModel().getExecutor().uomConversionError) {
            confirmButton.setEnabled(false);
        }
        this.phasePanel.add(productPhaseColumnLayoutPanel, "South");
    }

    private PhaseRadioButton0300 createResultSelectionBtn(final boolean isActive, final PhaseResult0710 result, final String name, final ActionListener l) {
        final PhaseRadioButton0300 phaseRadioButton0300 = new PhaseRadioButton0300(result.getKey(), result.getLocalizedString(), true, false);
        phaseRadioButton0300.setName(name);
        phaseRadioButton0300.setIconTextGap(10);
        phaseRadioButton0300.setEnabled(isActive);
        phaseRadioButton0300.addActionListener(l);
        return phaseRadioButton0300;
    }

    public void selectButtonAsPhaseResult() {
        final PhaseResult0710 phaseResult = this.getModel().getPhaseResult();
        if (PhaseResult0710.DONE.equals(phaseResult) || PhaseResult0710.STOP.equals(phaseResult)) {
            this.doneButton.setSelected(true);
        }
        else {
            this.continueButton.setSelected(true);
        }
    }

    private JPanel createLimitsPanel(final boolean isActive, final String matLimits) {
        final JPanel panel = new JPanel();
        panel.setEnabled(isActive);
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 1;
        gridBagConstraints.weighty = 32767.0;
        gridBagConstraints.weightx = 32767.0;
        final JLabel jLabel = ProductPhaseSwingHelper.createJLabel(matLimits, ProductPhaseColumnLayoutPanel.Layout.LAYOUT_TWO_1ST_WIDER_COLUMN.getColumnWidth(ProductPhaseColumnLayoutPanel.Column.FIRST_COLUMN));
        jLabel.setName("LimitsLabel");
        panel.add(jLabel, gridBagConstraints);
        return panel;
    }

    private JPanel createSublotStatusPanel(final boolean isActive, final IMESChoiceElement targetSublotStatus) {
        final JPanel panel = new JPanel();
        panel.setEnabled(isActive);
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 1;
        gridBagConstraints.weighty = 32767.0;
        gridBagConstraints.weightx = 32767.0;
        final JLabel jLabel = ProductPhaseSwingHelper.createJLabel(I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "SublotStatus_Label", new Object[] { targetSublotStatus.getLocalizedMessage() }), ProductPhaseColumnLayoutPanel.Layout.LAYOUT_TWO_1ST_WIDER_COLUMN.getColumnWidth(ProductPhaseColumnLayoutPanel.Column.FIRST_COLUMN));
        jLabel.setName("SublotStatusLabel");
        panel.add(jLabel, gridBagConstraints);
        return panel;
    }

    private void createSummaryPanel(final boolean isActive) {
        final JPanel separatorPanel = this.createSeparatorPanel(isActive);
        final JLabel jLabel = PhaseSwingHelper.createJLabel(this.getTotalTextLabel());
        jLabel.setName("LabelForTotal");
        jLabel.setHorizontalAlignment(4);
        jLabel.setVerticalAlignment(1);
        jLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
        if (this.targetUoM != null) {
            this.totalValueLabel = ProductPhaseSwingHelper.createJLabel(PCContext.getFunctions().createMeasuredValue(this.resultValue, this.targetUoM, 0).toString());
        }
        else {
            this.totalValueLabel = ProductPhaseSwingHelper.createJLabel(this.resultValue.toString() + " ");
        }
        this.totalValueLabel.setName("LabelForTotalQuantity");
        this.totalValueLabel.setHorizontalAlignment(4);
        this.totalValueLabel.setBorder(new EmptyBorder(8, 0, 0, 16));
        this.totalValueLabel.setPreferredSize(new Dimension(155, 30));
        final JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.add(separatorPanel, "North");
        panel.add(jLabel, "Center");
        panel.add(this.totalValueLabel, "East");
        panel.setBorder(BorderFactory.createEmptyBorder(0, 9, 20, 0));
        final JPanel panel2 = new JPanel();
        panel2.setOpaque(false);
        panel2.setLayout(new BorderLayout());
        panel2.setBorder(new EmptyBorder(17, 9, 0, 0));
        final String matLimits = isActive ? this.getProduceMaterialLimits() : "";
        String s = "North";
        if (StringUtils.isNotEmpty(matLimits)) {
            panel2.add(this.createLimitsPanel(isActive, matLimits), s);
            s = "South";
        }
        final IMESChoiceElement targetSublotStatus = this.getModel().getStatus().equals(IPhaseExecutor.Status.PREVIEW) ? null : this.getModel().getTargetSublotStatus();
        if (targetSublotStatus != null) {
            panel2.add(this.createSublotStatusPanel(isActive, targetSublotStatus), s);
        }
        final JPanel panel3 = new JPanel();
        panel3.setOpaque(false);
        panel3.setLayout(new BorderLayout());
        panel3.add(panel2, "West");
        panel3.add(panel, "East");
        this.centralPanel.add(panel3, "North");
    }

    private JPanel createSeparatorPanel(final boolean isActive) {
        final JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setEnabled(isActive);
        final JSeparator separator = new JSeparator(0);
        separator.setPreferredSize(new Dimension(340, 2));
        separator.setBorder(new LineBorder(Color.WHITE));
        panel.setBorder(new EmptyBorder(0, 0, 0, 10));
        panel.add(separator);
        return panel;
    }

    private void createCommentPanel(final boolean isActive) {
        final String comment = this.getComment();
        if (StringUtils.isEmpty(comment)) {
            return;
        }
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 1;
        gridBagConstraints.weighty = 32767.0;
        gridBagConstraints.weightx = 32767.0;
        gridBagConstraints.insets = new Insets(9, 9, 9, 9);
        final JLabel multiLineLabel = PhaseSwingHelper.createMultiLineLabel(comment, 994 - gridBagConstraints.insets.left - gridBagConstraints.insets.right);
        final JPanel panel = new JPanel();
        panel.setEnabled(isActive);
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        panel.add(multiLineLabel, gridBagConstraints);
        this.centralPanel.add(panel, "South");
    }

    private JPanel createRefreshButtonPanel() {
        final PhaseButton phaseButton = PhaseSwingHelper.createPhaseButton(getBtnTextRefresh(), this.getModel().getStatus());
        phaseButton.setName("refreshButton");
        phaseButton.addActionListener(e -> this.eventListener.onRefresh());
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(ProductPhaseColumnLayoutPanel.Layout.LAYOUT_TWO_1ST_WIDER_COLUMN.getColumnWidth(ProductPhaseColumnLayoutPanel.Column.SECOND_COLUMN), PhaseUIConstants.CONFIRM_BUTTON_PANEL_SIZE.height));
        panel.setOpaque(false);
        panel.setEnabled(phaseButton.isEnabled());
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        final int n = 9;
        gridBagConstraints.insets = new Insets(0, n, 0, n);
        gridBagConstraints.anchor = 12;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.weightx = 1.0;
        panel.add(phaseButton, gridBagConstraints);
        return panel;
    }

    private void createGridPanel(final boolean isActive) {
        final List<ColumnInfo> generateColumnsInfo = this.generateColumnsInfo();
        final JPanel refreshButtonPanel = this.createRefreshButtonPanel();
        this.grid = new PhaseStandardGrid(this.getModel().getStatus(), AbstractProcMaterialDAO0710.class, (List)generateColumnsInfo, new AbstractMaterialDAO0710.MaterialPhaseStandardGridConfigurator());
        this.grid.getMESGrid().getGrid().setName("TableSublotsGrid");
        this.grid.getMESGrid().getGrid().getTableHeader().setBackground(Color.RED);
        final JPanel panel = new JPanel();
        panel.setEnabled(isActive);
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.add(refreshButtonPanel, "North");
        panel.add((Component)this.grid.getMESGrid(), "Center");
        this.centralPanel.add(panel, "Center");
        this.refreshGrid(false);
    }

    private List<ColumnInfo> generateColumnsInfo() {
        final ArrayList<ColumnInfo> list = new ArrayList<ColumnInfo>();
        list.add(new ColumnInfo("materialIDStr", 120, true));
        list.add(new ColumnInfo("descriptionStr", 200, true));
        list.add(new ColumnInfo("batchStr", 120, false));
        list.add(new ColumnInfo("sublot", 120, false));
        list.add(new ColumnInfo("plannedQty", 120, false, 4));
        final ColumnInfo columnInfo = new ColumnInfo("processedQty", 120, false, 4);
        columnInfo.setHeaderMsg("producedQty");
        list.add(columnInfo);
        list.add(new ColumnInfo("sublotQty", 120, false, 4));
        return list;
    }

    protected void refreshGrid(final boolean navigatorCall) {
        final List<AbstractProcMaterialDAO0710> materialList = this.getModel().getMaterialList();
        if (!this.getModel().getStatus().equals(IPhaseExecutor.Status.PREVIEW)) {
            this.displayValuesIntoFields(materialList, navigatorCall);
        }
        this.grid.setObjects((List)materialList);
    }

    private void displayValuesIntoFields(final List<AbstractProcMaterialDAO0710> materials, final boolean navigatorCall) {
        if (materials.size() > 1 && !this.getModel().arePackagingLevelsDefined()) {
            this.inputNumOfSublots.setText(String.valueOf(materials.size() - 1));
            if (this.getModel().getRtPhaseData().getSublotLevelQty() != null && this.getModel().getRtPhaseData().getDefinedPackingLevels() == null) {
                this.inputQty.setText(BigDecimalUtilities.toStringAsDecimal(this.getModel().getRtPhaseData().getSublotLevelQty().getValue()));
                this.toggleUoM.getButton().setText(this.getModel().getRtPhaseData().getSublotLevelQty().getUnitOfMeasure().getSymbol());
                this.getModel().getExecutor().totalCalculationForNoPackingLevelForCompletePhase(new BigDecimal(this.inputNumOfSublots.getText()), this.getModel().getRtPhaseData().getSublotLevelQty().getValue(), this.getModel().getRtPhaseData().getSublotLevelQty().getUnitOfMeasure());
            }
        }
        else if (!navigatorCall) {
            this.setDefinedPackingLevels();
        }
    }

    private void documentTextFields(final JTextField textFields, final String regularExpressionValue) {
        ((AbstractDocument)textFields.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(final FilterBypass fb, final int offset, final int length, final String text, final AttributeSet attrs) throws BadLocationException {
                if (text.matches(regularExpressionValue)) {
                    super.replace(fb, offset, length, text, attrs);
                    RtPhaseViewLCMatProduce0010.this.getModel().getExecutor().totalCalculationForNoPackingLevel();
                }
            }

            @Override
            public void insertString(final FilterBypass fb, final int offset, final String text, final AttributeSet attr) throws BadLocationException {
                if (text.matches(regularExpressionValue)) {
                    super.insertString(fb, offset, text, attr);
                    RtPhaseViewLCMatProduce0010.this.getModel().getExecutor().totalCalculationForNoPackingLevel();
                }
            }

            @Override
            public void remove(final FilterBypass fb, final int offset, final int length) throws BadLocationException {
                super.remove(fb, offset, length);
                RtPhaseViewLCMatProduce0010.this.getModel().getExecutor().totalCalculationForNoPackingLevel();
            }
        });
        textFields.repaint();
        textFields.revalidate();
    }

    private void addValidationToQtyTextField(final JTextField textField) {
        this.addDocumentFilterForQtyTextField(textField, "(?!.{10,})^[0-9]*[" + RtPhaseViewLCMatProduce0010.DECIMAL_SEPARATOR + "]?[0-9]*$");
        this.addFocusListenerForQtyTextField(textField);
        textField.repaint();
        textField.revalidate();
    }

    private void addDocumentFilterForQtyTextField(final JTextField textField, final String regExprCompleteText) {
        ((AbstractDocument)textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(final FilterBypass fb, final int offset, final int length, final String text, final AttributeSet attrs) throws BadLocationException {
                if (this.getTotalText(fb, text).matches(regExprCompleteText)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void insertString(final FilterBypass fb, final int offset, final String text, final AttributeSet attr) throws BadLocationException {
                if (this.getTotalText(fb, text).matches(regExprCompleteText)) {
                    super.insertString(fb, offset, text, attr);
                }
            }

            private String getTotalText(final FilterBypass fb, final String text) throws BadLocationException {
                return fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
            }
        });
    }

    private void addFocusListenerForQtyTextField(final JTextField textField) {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent e) {
                final String text = textField.getText();
                if (text.isEmpty()) {
                    return;
                }
                if (text.equals(String.valueOf(RtPhaseViewLCMatProduce0010.DECIMAL_SEPARATOR))) {
                    textField.setText("");
                }
                else {
                    RtPhaseViewLCMatProduce0010.this.getModel().getExecutor().totalCalculationForNoPackingLevel();
                }
            }
        });
    }

    public IUnitOfMeasure getUoM() {
        return this.toggleUoM.getUnitOfMeasure();
    }

    public IUnitOfMeasure getUoMForPackingLevels() {
        return this.targetUoM;
    }

    public String getDefinedPackingLevels() {
        final ArrayList<String> list = new ArrayList<String>();
        String join = null;
        if (this.getModel().arePackagingLevelsDefined()) {
            for (final JTextField textField : this.contentEntryTextFieldsList) {
                list.add(textField.getText().equals("") ? "0" : textField.getText());
            }
            join = String.join(",", list);
        }
        return join;
    }

    public String getDefinedPackingLevelNames() {
        final ArrayList<String> list = new ArrayList<String>();
        String join = null;
        if (this.getModel().arePackagingLevelsDefined()) {
            final Iterator<IPackagingLevelBean> iterator = MatProducePackingLevelsHelper0710.getConfiguredPackagingLevels(this.getModel().getExecutor().getAllPackagingLevels()).iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next().getPackagingLevel().getMeaning());
            }
            join = String.join(",", list);
        }
        return join;
    }

    public void setDefinedPackingLevels() {
        final String definedPackingLevels = this.getModel().getDefinedPackingLevels();
        if (this.contentEntryTextFieldsList != null && definedPackingLevels != null && StringUtils.isNoneBlank(new CharSequence[] { definedPackingLevels })) {
            final String[] split = definedPackingLevels.split(",");
            for (int i = 0; i < split.length; ++i) {
                final JTextField textField = this.contentEntryTextFieldsList.get(i);
                textField.setText("");
                if (!split[i].equals("0")) {
                    textField.setText(split[i]);
                }
            }
        }
    }

    public void calculateSummary(final String textFieldID) {
        final ArrayList<BigDecimal> numbersForPackagingLevels = new ArrayList<BigDecimal>();
        int scale = 0;
        final int int1 = Integer.parseInt(textFieldID.split("_")[1]);
        for (int i = 0; i < this.contentEntryTextFieldsList.size(); ++i) {
            final JTextField textField = this.contentEntryTextFieldsList.get(i);
            numbersForPackagingLevels.add(textField.getText().equals("") ? new BigDecimal(0) : new BigDecimal(textField.getText()));
        }
        final BigDecimal calculateNumberForCurrentLevel = this.getModel().getExecutor().calculateNumberForCurrentLevel(numbersForPackagingLevels, int1);
        MeasuredValue measuredValue;
        if (calculateNumberForCurrentLevel.compareTo(BigDecimal.ZERO) > 0) {
            measuredValue = this.getModel().getQtyFromValueforPackagingLevel(calculateNumberForCurrentLevel);
        }
        else {
            measuredValue = PCContext.getFunctions().createMeasuredValue(new BigDecimal(0), (UnitOfMeasure) this.getModel().getDefaultUoM(), scale);
        }
        this.resultLabelsList.get(int1).setText(measuredValue.toString());
        final ArrayList<IMeasuredValue> resultLabelValues = new ArrayList<IMeasuredValue>();
        for (int j = 0; j < this.resultLabelsList.size(); ++j) {
            final MeasuredValue mv = MeasuredValueUtilities.createMV(this.resultLabelsList.get(j).getText(), I18nMessageUtility.getCurrentLocale());
            if (((IMeasuredValue)mv).getValue().compareTo(BigDecimal.ZERO) > 0) {
                resultLabelValues.add(mv);
                scale = ((IMeasuredValue)mv).getScale();
            }
        }
        this.totalValueLabel.setText(this.getModel().getExecutor().calculateTotalValue(resultLabelValues, scale).toString());
    }

    private String getProduceMaterialLimits() {
        if (!PlannedQuantityMode.AS_DEFINED.getChoiceElement().equals(this.getModel().getPhaseOutputMaterialParameter().getPlannedQuantityMode())) {
            return "";
        }
        final Limits limits = this.getModel().getLimits();
        return I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "Limits_Label", new Object[] { limits.getLower(), limits.getUpper() });
    }

    public String getQty() {
        return this.inputQty.getText();
    }

    public void clearAll() {
        if (this.contentEntryTextFieldsList != null) {
            final Iterator<JTextField> iterator = this.contentEntryTextFieldsList.iterator();
            while (iterator.hasNext()) {
                iterator.next().setText("");
            }
        }
        else {
            this.clearQty();
            this.clearNumberOfSublots();
        }
    }

    public void clearQty() {
        this.inputQty.setText("");
    }

    public String getNumberOfSublots() {
        return this.inputNumOfSublots.getText();
    }

    public void clearNumberOfSublots() {
        this.inputNumOfSublots.setText("");
    }

    private String getTotalTextLabel() {
        final IMESChoiceElement defaultPackingChoiceElement = this.getModel().getDefaultPackingChoiceElement();
        return I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "Total_Label") + ((defaultPackingChoiceElement == null || defaultPackingChoiceElement.getMeaning().equals("NotApplicable")) ? " " : (" (" + I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "PackagingLevel_" + defaultPackingChoiceElement.getMeaning() + "_P") + ")")) + "  = ";
    }

    public JLabel totalValueLabel() {
        return this.totalValueLabel;
    }

    public List<JTextField> contentEntryTextFieldsList() {
        return this.contentEntryTextFieldsList;
    }

    protected String getComment() {
        String localizedMessage = "";
        if (this.getModel().matOutputParameter != null && !StringUtils.isEmpty((CharSequence) this.getModel().matOutputParameter.getComment())) {
            localizedMessage = I18nMessageUtility.getLocalizedMessage("PhaseProductMaterial0710", "CommentFormat", new Object[] { this.getModel().matOutputParameter.getMaterialID(), this.getModel().matOutputParameter.getComment() });
        }
        return localizedMessage;
    }

    private static String getInventoryShortName(final String inventoryMeaningName) {
        String s = " ";
        if (inventoryMeaningName.equals("SublotAndLogisticUnit")) {
            s = I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "SLU_inventory_Label");
        }
        else if (inventoryMeaningName.equals("LogisticUnit")) {
            s = I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "LU_inventory_Label");
        }
        else if (inventoryMeaningName.equals("Sublot")) {
            s = I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "SL_inventory_Label");
        }
        return s;
    }

    private String getNumOfSublotsLabel() {
        return I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "NumOfSublots_Label");
    }

    private JLabel initSmallLabel(final String text) {
        final JLabel jLabel = ProductPhaseSwingHelper.createJLabel(text, ProductPhaseColumnLayoutPanel.Layout.LAYOUT_THREE_COLUMN.getColumnWidth(ProductPhaseColumnLayoutPanel.Column.SECOND_COLUMN) - 100);
        jLabel.setHorizontalAlignment(2);
        return jLabel;
    }

    private void initNumOfSublotsField() {
        (this.inputNumOfSublots = ProductPhaseSwingHelper.createPhaseSwingNumericTouchField(this.getModel().getStatus(), this.getNumOfSublotsLabel(), 2)).setName(this.inputNumOfSublots.getName().concat("NumberOfSublots"));
        this.inputNumOfSublots.setPreferredSize(new Dimension(ProductPhaseColumnLayoutPanel.Layout.LAYOUT_THREE_COLUMN.getColumnWidth(ProductPhaseColumnLayoutPanel.Column.THIRD_COLUMN) / 2 - 18 - 2, this.inputNumOfSublots.getPreferredSize().height));
        this.documentTextFields(this.inputNumOfSublots, "^[0-9]{0,9}$");
    }

    private void initQtyField() {
        (this.inputQty = ProductPhaseSwingHelper.createPhaseSwingNumericTouchField(this.getModel().getStatus(), this.getQtyLabel(), 2)).setName(this.inputQty.getName().concat("Quantity"));
        this.inputQty.setPreferredSize(new Dimension(ProductPhaseColumnLayoutPanel.Layout.LAYOUT_THREE_COLUMN.getColumnWidth(ProductPhaseColumnLayoutPanel.Column.THIRD_COLUMN) / 2 - 18 - 2, this.inputQty.getPreferredSize().height));
        if (((RtPhaseModelLCMatProduce0010)this.getModel()).getStatus() == IPhaseExecutor.Status.ACTIVE) {
            this.addValidationToQtyTextField(this.inputQty);
        }
    }

    private String getQtyLabel() {
        return I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "SublotQty_Label");
    }

    protected String getNavigatorInfoColumn() {
        this.getModel().refreshMaterialsList();
        this.refreshGrid(true);
        final List<MESRtPhaseDataLCMatProduce0010> allRtPhaseDataWithoutSummary = this.getModel().getAllRtPhaseDataWithoutSummary();
        Object o = MeasuredValueUtilities.createMV(new BigDecimal(0), this.getModel().getDefaultUoM());
        for (MESRtPhaseDataLCMatProduce0010 mesRtPhaseDataMatProduce0710 : allRtPhaseDataWithoutSummary) {
            if (mesRtPhaseDataMatProduce0710.getSublotQty() != null) {
                try {
                    o = ((IMeasuredValue)o).add(mesRtPhaseDataMatProduce0710.getSublotQty());
                }
                catch (Exception ex) {
                    throw new MESRuntimeException("Error in navigator information column.", ex);
                }
            }
            if (mesRtPhaseDataMatProduce0710.getIsHeader()) {
                return o.toString();
            }
        }
        return "---";
    }

    public void redraw() {
        this.setWaiting(false);
        this.setEnabled(true);
    }

    public void blockPhaseCompletionRelevantInput() {
        this.doneButton.setEnabled(false);
        this.continueButton.setEnabled(false);
        if (!this.getModel().arePackagingLevelsDefined()) {
            this.inputNumOfSublots.setEnabled(false);
            this.inputQty.setEnabled(false);
        }
        else {
            final Iterator<JTextField> iterator = this.contentEntryTextFieldsList.iterator();
            while (iterator.hasNext()) {
                iterator.next().setEnabled(false);
            }
        }
    }

    public void setWaiting(final boolean wait) {
        if (this.waiting == wait) {
            return;
        }
        if (this.waiting) {
            this.getWaitCursor().end();
        }
        else {
            this.getWaitCursor().begin();
        }
        this.waiting = wait;
    }

    private synchronized WaitCursor getWaitCursor() {
        if (this.waitCursor == null) {
            this.waitCursor = new WaitCursor(this.phasePanel);
        }
        return this.waitCursor;
    }

    private static String convertToFirstLetterLower(final String convertableWord) {
        String string = "";
        if (convertableWord != null) {
            string = Character.toLowerCase(convertableWord.charAt(0)) + convertableWord.substring(1);
        }
        return string;
    }

    static {
        CONTAIN_MSG = I18nMessageUtility.getLocalizedMessage("PhaseProductProduceMaterial0710", "Contain_Label");
        CURRENT_DECIMAL_FORMATS = new DecimalFormatSymbols(PCContext.getEnvironment().getCurrentLocale());
        DECIMAL_SEPARATOR = RtPhaseViewLCMatProduce0010.CURRENT_DECIMAL_FORMATS.getDecimalSeparator();
    }
}
