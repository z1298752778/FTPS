package com.leateck.phase.accountalternativematerial0010;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.rockwell.mes.parameter.product.excptenabledef.MESParamExcptEnableDef0200;
import org.apache.commons.lang3.StringUtils;

import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.MeasuredValue;
import com.datasweep.plantops.common.measuredvalue.IMeasuredValue;
import com.rockwell.mes.apps.ebr.ifc.phase.IPhaseExecutor.Status;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.PhaseDialog;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.SwingPhasePanelFocusTraversalPolicy;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.UIConstants;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseButton;
import com.rockwell.mes.clientfw.commons.ifc.swing.GraphicalButton.BarcodeType;
import com.rockwell.mes.commons.base.ifc.exceptions.MESException;
import com.rockwell.mes.commons.base.ifc.functional.MeasuredValueUtilities;
import com.rockwell.mes.commons.base.ifc.i18n.I18nBigDecimalUtility;
import com.rockwell.mes.commons.base.ifc.i18n.I18nMessageUtility;
import com.rockwell.mes.commons.base.ifc.utility.StyleSheetUtility;
import com.rockwell.mes.commons.deviation.ifc.IESignatureExecutor;
import com.rockwell.mes.parameter.product.accountcalc.AccountCalculation0200;
import com.rockwell.mes.services.s88.ifc.recipe.IMESPrivilegeParameter;
import com.rockwell.mes.shared.product.material.AccountMaterialDAO0710;
import com.rockwell.mes.shared.product.material.AccountMaterialDAO0710.AccountType;
import com.rockwell.mes.shared0400.product.ui.basics.PhaseUIConstants;
import com.rockwell.mes.shared0400.product.ui.basics.impl.PhaseToggleObjectsButton.ValueChangedEvent;
import com.rockwell.mes.shared0400.product.ui.basics.impl.PhaseToggleObjectsButton.ValueChangedEventListener;
import com.rockwell.mes.shared0400.product.ui.basics.impl.PhaseToggleUomButton;
import com.rockwell.mes.shared0400.product.ui.basics.util.ProductPhaseSwingHelper;
import com.rockwell.mes.shared0400.product.util.MessageUtil;

/**
 * Dialog allowing the user to enter the quantities that should be accounted.
 * 
 * @author ikoleva, (c) Copyright 2014 Rockwell Automation Technologies, Inc. All Rights Reserved.
 * 
 */
@SuppressWarnings("PMD.ExcessiveImports")
public class AccountQuantitiesDialog0710 extends PhaseDialog {
    /** Serializable */
    private static final long serialVersionUID = 1L;

    private static final int RIGHT_GAP = 24;

    /** additional right border for all controls */
    public static final int RIGHT_BORDER = 30;

    /**
     * Spacing between UoM button and Cancel button (+12pxl from the button
     * panel, results in 36)
     */
    private static final int BOTTOM_GAP = 24;

    /**
     * Spacing between leftmost text start and left yellow margin (+3pxl from
     * the framework +1pxl from font issue, results in 27)
     */
    private static final int TOP_GAP = 25;

    /** consumed quantity Edit */
    private JTextField consumedEdit;

    /** wasted quantity Edit */
    private JTextField wastedEdit;

    /** sampled quantity Edit */
    private JTextField sampledEdit;

    /** returned quantity Edit */
    private JTextField returnedEdit;

    /** consumed quantity UoM */
    private transient PhaseToggleUomButton consumedUoM;

    /** wasted quantity UoM */
    private transient PhaseToggleUomButton wastedUoM;

    /** sampled quantity UoM */
    private transient PhaseToggleUomButton sampledUoM;

    /** returned quantity UoM */
    private transient PhaseToggleUomButton returnedUoM;

    private transient JButton accountButton;

    /**
     * Type of calculation; determines which quantities are entered by the user
     * and which are calculated based on user input.
     */
    private AccountCalculation0200 accountCalcType;

    /** preferred size for the input fields */
    private static final Dimension EDIT_SIZE = new Dimension(150, UIConstants.EDIT_PREFERRED_SIZE.height);

    /** layout of the quantities panel: all UI components are added there */
    private transient GroupLayout qtyPanelLayout;

    /** the measured value for identified quantity */
    private transient IMeasuredValue identifiedQty;

    /** is a single row was selected */
    private boolean isSingleRow;

    /** quantities the currently accounted quantities */
    private transient Map<AccountType, IMeasuredValue> quantities;

    private final AccessPrivilege phaseActionPrivilege;

    private final transient IMESPrivilegeParameter phaseActionPrivilegeParameter;

    /** account dialog prefered size */
    private static final Dimension ACCOUT_DIALOG_SIZE = new Dimension(470, 410);

    private ActionSignaturePanel0710 signaturePanel = null;

    private transient IESignatureExecutor signatureExecutor;

    private JPanel separator;
    private RtPhaseExecutorMatAlterAcct0010 phaseExecutorMatAlterAcct0010;

    /**
     * @param argActionPrivilege the action privilege used for the phase action signature, {@code null} if phase action
     *            signature is disabled
     * @param argPrivilegeParameter the privilege parameter related to the phase action, {@code null} if phase action
     *            signature is disabled
     */
    public AccountQuantitiesDialog0710(AccessPrivilege argActionPrivilege, IMESPrivilegeParameter argPrivilegeParameter,RtPhaseExecutorMatAlterAcct0010 phaseExecutorMatAlterAcct0010) {
        super(DialogType.PLAIN);
        phaseActionPrivilege = argActionPrivilege;
        phaseActionPrivilegeParameter = argPrivilegeParameter;
        this.phaseExecutorMatAlterAcct0010 = phaseExecutorMatAlterAcct0010;
    }

    @Override
    protected void createContentWithMessage(String... messages) {
        JPanel mainPanel = getMainPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel innerPanel = new JPanel();
        innerPanel.setOpaque(false);
        innerPanel.setBorder(
                BorderFactory.createEmptyBorder(BORDER_GAP, BORDER_GAP, BORDER_GAP, BORDER_GAP));
        innerPanel.setLayout(new FlowLayout());
        mainPanel.add(innerPanel, BorderLayout.CENTER);

        JPanel quantitiesPanel = createQuantitiesPanel();
        innerPanel.add(quantitiesPanel);
        initUI();


        // add buttons
        JPanel buttonsPanel = createButtonPanel();
        innerPanel.add(buttonsPanel);

        if (phaseActionPrivilege != null) {
            // move the panels a bit to the right
            quantitiesPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, RIGHT_BORDER));
            buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, RIGHT_BORDER));

            separator = createSeparator();
            innerPanel.add(separator);
            signaturePanel = new ActionSignaturePanel0710(phaseActionPrivilege, phaseActionPrivilegeParameter, accountButton);
            innerPanel.add(signaturePanel);
            addWindowListener(new WindowAdapter() {                
                @Override
                public void windowClosed(WindowEvent e) {
                    signaturePanel.cleanup();
                }
            });
        }

        StyleSheetUtility.style(mainPanel);

        getMainPanel().setFocusCycleRoot(true);
    }


    private JPanel createSeparator() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(ACCOUT_DIALOG_SIZE.width, 1));
        panel.setName("PhaseDialogSeparator");
        return panel;
    }

    /**
     * @param buttonText button text
     * @param dialogResult dialog result event
     * @return a {@link PhaseButton} if configured for this option
     */
    private PhaseButton createButton(String buttonText, DialogEvent dialogResult) {
        if (StringUtils.isEmpty(buttonText)) {
            return null;
        }
        final Runnable runnable = getCloseDialogRunnable(dialogResult);
        PhaseButton button =
                new PhaseButton(dialogResult == DialogEvent.CANCEL_OPTION ? BarcodeType.ActiveBarcode
                        : BarcodeType.NoBarcode);
        button.setText(buttonText);
        button.setName(PHASE_DIALOG_PREFIX + "Button" + dialogResult.toString());
        button.setOpaque(false);

        button.addActionListener(e -> runnable.run());
        return button;
    }

    @Override
    public Dimension getPreferredSize() {
        if (signaturePanel == null) {
            return ACCOUT_DIALOG_SIZE;
        }
        int width = Math.max(ACCOUT_DIALOG_SIZE.width, signaturePanel.getPreferredSize().width + 2 * UIConstants.DEFAULT_GAP + RIGHT_BORDER);
        int height =
                ACCOUT_DIALOG_SIZE.height + signaturePanel.getPreferredSize().height + separator.getPreferredSize().height + UIConstants.DEFAULT_GAP;
        return new Dimension(width, height);
    }

    /**
     * Creates an empty transparent {@link JPanel} that can be used as button
     * panel.
     * <p>
     * 
     * @return a new {@link JPanel}
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(TOP_GAP, PhaseUIConstants.DEFAULT_GAP, PhaseUIConstants.DEFAULT_GAP, PhaseUIConstants.DEFAULT_GAP);
        accountButton = createButton(MatAccountView0710.getBtnTextAccount(), DialogEvent.OK_OPTION);
        panel.add(accountButton, constraints);
        constraints.gridx++;
        panel.add(createButton(I18nMessageUtility.getLocalizedMessage(MessageUtil.SHARED_PRODUCT_MSG_PACK, "BtnTextCancel"),
                DialogEvent.CANCEL_OPTION), constraints);
        return panel;
    }

    /**
     * display the gradient dialog form
     * 
     * @param argMV the measured value for identified quantity
     * @param argIsSingleRow is a single row was selected
     * @param calculationType type of calculation
     * @param argQuantities the currently accounted quantities, null if total consumption or if the sublot was not
     *            accounted
     * @return the event type of the closed dialog
     */
    public int showDialog(IMeasuredValue argMV, boolean argIsSingleRow, AccountCalculation0200 calculationType,
            Map<AccountType, IMeasuredValue> argQuantities) {
        accountCalcType = calculationType;
        identifiedQty = argMV;
        isSingleRow = argIsSingleRow;
        quantities = argQuantities;
        return super.showDialog(null);
    }

    @Override
    public Runnable getCloseDialogRunnable(final DialogEvent evt) {
        return new CloseRunnable(evt);
    }

    /**
     * A close dialog runnable
     * <p>
     * 
     * @author ikoleva, (c) Copyright 2020 Rockwell Automation Technologies, Inc. All Rights Reserved.
     */
    private final class CloseRunnable implements Runnable {
        private final DialogEvent evt;

        private CloseRunnable(DialogEvent arg) {
            this.evt = arg;
        }

        @Override
        public void run() {
            if (DialogEvent.OK_OPTION.equals(evt)) {
                if (!checkQuantities()) {
                    return;
                }
                // if phase action signature is enabled: check the credentials and execute the signature
                if (signaturePanel != null) {
                    if (!signaturePanel.checkAndExecuteSignture()) {
                        return;
                    } else {
                        // the executor should be ready
                        signatureExecutor = signaturePanel.getSignatureExecutor();
                    }
                }
            }
            setDialogResult(evt.getValue());
            setVisible(false);
        }

        private boolean checkQuantities() {
            // check for negative quantities
            try {
                if (getConsumedQuantity().signum() < 0 || getSamlpedQuantity().signum() < 0 || getWastedQuantity().signum() < 0
                        || getReturnedQuantity().signum() < 0) {
                    ProductPhaseSwingHelper
                            .showErrorDlg(I18nMessageUtility.getLocalizedMessage(AccountMaterialDAO0710.MSG_PACK, "InvalidQtyTxt"));
                    return false;
                }
                if(getReturnedQuantity().compareTo(identifiedQty.subtract(getConsumedQuantity()).subtract(getSamlpedQuantity()).subtract(getWastedQuantity()) )  > 0){
                    //抛异常
                    throw new MESException(I18nMessageUtility.getLocalizedMessage(MessageUtil.SHARED_PRODUCT_MSG_PACK, MessageUtil.NON_PARSABLE_MSG));
                }
            } catch (MESException e) {
                ProductPhaseSwingHelper.showErrorDlg(e.getLocalizedMessage());
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    /**
     * If a phase action signature is configured and it was executed successfully, this method returns the corresponding
     * signature executor. If no signature is configured, or before it was executed, it returns {@code null}. Please
     * note: the signature executor is not saved yet.
     * 
     * @return the signature executor
     */
    public IESignatureExecutor getSignatureExecutor() {
        return signatureExecutor;
    }

    /**
     * Create the UI for the dialog
     */
    private void initUI() {
        // add quantities
        ParallelGroup labelsGroup = qtyPanelLayout.createParallelGroup(Alignment.TRAILING);
        ParallelGroup inputGroup = qtyPanelLayout.createParallelGroup();
        ParallelGroup uomGroup = qtyPanelLayout.createParallelGroup();
        SequentialGroup verticalGroup = qtyPanelLayout.createSequentialGroup();

        ParallelGroup identifiedGroup = addIdentifiedQttyLinePanel(labelsGroup, inputGroup, uomGroup);
        verticalGroup.addGroup(identifiedGroup);

        ParallelGroup consumedGroup = addQttyLine(AccountType.CONSUMED, isSingleRow, labelsGroup, inputGroup, uomGroup);
        // add dynamic gap between lines
        verticalGroup
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        verticalGroup.addGap(PhaseUIConstants.DEFAULT_GAP);
        verticalGroup.addGroup(consumedGroup);

        // input fields for wasted quantity should be disabled if wasted
        // quantity is calculated
        ParallelGroup wastedGroup =
                addQttyLine(AccountType.WASTED, isSingleRow && !accountCalcType.equals(AccountCalculation0200.WASTE),
                        labelsGroup, inputGroup, uomGroup);
        // add dynamic gap between lines
        verticalGroup
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        verticalGroup.addGap(PhaseUIConstants.DEFAULT_GAP);
        verticalGroup.addGroup(wastedGroup);

        ParallelGroup sampledGroup = addQttyLine(AccountType.SAMPLED, isSingleRow, labelsGroup, inputGroup, uomGroup);
        // add dynamic gap between lines
        verticalGroup
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        verticalGroup.addGap(PhaseUIConstants.DEFAULT_GAP);
        verticalGroup.addGroup(sampledGroup);

        // input fields for returned quantity should be disabled if returned
        // quantity is calculated

   ParallelGroup returnedGroup =
                addQttyLine(AccountType.RETURNED,
                        isSingleRow && isparamExcptEnableDefGetEnable(), labelsGroup, inputGroup,
                        uomGroup);

        // add dynamic gap between lines
        verticalGroup
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
        verticalGroup.addGap(PhaseUIConstants.DEFAULT_GAP);
        verticalGroup.addGroup(returnedGroup);
        verticalGroup.addGap(BOTTOM_GAP);

        if (quantities == null) {
            setTextForDefaultField();
        }

        // add the groups to the layout:
        // horizontally: three groups: for names, input fields and toggle
        // buttons
        // vertically: one group for each quantity
        qtyPanelLayout.setHorizontalGroup(
                qtyPanelLayout.createSequentialGroup().addGroup(labelsGroup)
                        .addGap(PhaseUIConstants.DEFAULT_GAP).addGroup(inputGroup).addGap(PhaseUIConstants.DEFAULT_GAP).addGroup(uomGroup)
                        .addGap(RIGHT_GAP));
        qtyPanelLayout.setVerticalGroup(verticalGroup);
    }

    /**
     * Create the panel where the input fields for the quantities should be added.
     * 
     * @return panel where the input fields for the quantities should be added
     */
    private JPanel createQuantitiesPanel() {
        JPanel outerQtysPanel = new JPanel();
        outerQtysPanel.setOpaque(false);
        qtyPanelLayout = new GroupLayout(outerQtysPanel);
        qtyPanelLayout.setAutoCreateGaps(true);
        outerQtysPanel.setLayout(qtyPanelLayout);

        return outerQtysPanel;
    }

    /**
     * Initially set the identified quantity in the default field.
     */
    private void setTextForDefaultField() {
        if (isSingleRow) {
            switch (accountCalcType) {
            case WASTE:
                wastedEdit.setText(I18nBigDecimalUtility.bigDecimalToLocalizedString(identifiedQty.getValue()));
                break;

            case RETURN:
            default:
                returnedEdit.setText(I18nBigDecimalUtility.bigDecimalToLocalizedString(identifiedQty.getValue()));
                break;
            }
        } else {
            // multiple rows: total consumption
            consumedEdit.setText(I18nBigDecimalUtility.bigDecimalToLocalizedString(identifiedQty.getValue()));
        }
    }

    /**
     * Refresh the field that is calculated based on the other fields
     */
    private void refreshDialog() {
        try {
            MeasuredValue resultQty;
            switch (accountCalcType) {

            case WASTE:
                // calculate wasted quantity based on the other quantities
                // use the MeasuredValue objects, the subtract method handles
                // conversionsq
                resultQty =
                        (MeasuredValue) identifiedQty.subtract(getConsumedQuantity()).subtract(getSamlpedQuantity())
                                .subtract(getReturnedQuantity());
                resultQty = (MeasuredValue) resultQty.convert(wastedUoM.getUnitOfMeasure());
                wastedEdit.setText(I18nBigDecimalUtility.bigDecimalToLocalizedString(resultQty.getValue()));
                break;

            case RETURN:
            default:
                // calculate quantity to be returned based on the other
                // quantities
                // use the MeasuredValue objects, the subtract method handles
                // conversions
                resultQty =
                        (MeasuredValue) identifiedQty.subtract(getConsumedQuantity()).subtract(getSamlpedQuantity())
                                .subtract(getWastedQuantity());
                resultQty = (MeasuredValue) resultQty.convert(returnedUoM.getUnitOfMeasure());
                if (!isparamExcptEnableDefGetEnable()){
                    returnedEdit.setText(I18nBigDecimalUtility.bigDecimalToLocalizedString(resultQty.getValue()));

                }else {
                    //实际退库量小于输入的退库量，提示参数不合法
                    if (resultQty.compareTo(getReturnedQuantity()) <0){
                        //抛异常
                        throw new MESException(I18nMessageUtility.getLocalizedMessage(MessageUtil.SHARED_PRODUCT_MSG_PACK, MessageUtil.NON_PARSABLE_MSG));


                    }
                }
                MeasuredValueUtilities.createMV(getReturnedQuantity().toString());

                break;
            }

        } catch (Exception e) {
            ProductPhaseSwingHelper.showErrorDlg(e.getLocalizedMessage());
        }
    }

    /**
     * The user input for consumed quantity
     * 
     * @return the consumed quantity input value
     * @throws MESException when the value in not parsable
     */
    public MeasuredValue getConsumedQuantity() throws MESException {
        return getQuantity(consumedEdit, consumedUoM);
    }

    /**
     * The user input for wasted quantity
     * 
     * @return the wasted quantity input value
     * @throws MESException when the value in not parsable
     */
    public MeasuredValue getWastedQuantity() throws MESException {
        return getQuantity(wastedEdit, wastedUoM);
    }

    /**
     * The user input for sampled quantity
     * 
     * @return the sampled quantity input value
     * @throws MESException when the value in not parsable
     */
    public MeasuredValue getSamlpedQuantity() throws MESException {
        return getQuantity(sampledEdit, sampledUoM);
    }

    /**
     * The user input for returned quantity
     * 
     * @return the returned quantity input value
     * @throws MESException when the value in not parsable
     */
    public MeasuredValue getReturnedQuantity() throws MESException {
        return getQuantity(returnedEdit, returnedUoM);
    }

    /**
     * The quantity from the user input
     * 
     * @param touchField the touch field
     * @param uomButton the unit of measure toggle button
     * @return MeasuredValue
     * @throws MESException when the value in not parsable
     */
    private MeasuredValue getQuantity(JTextField touchField, PhaseToggleUomButton uomButton) throws MESException {
        BigDecimal value = BigDecimal.valueOf(0);
        String text = touchField.getText();
        if (text != null && !text.isEmpty()) {
            try {
                value = I18nBigDecimalUtility.localizedStringToBigDecimal(text);
            } catch (NumberFormatException | ParseException e) {
                // set both the localized message and the cause
                throw new MESException(I18nMessageUtility.getLocalizedMessage(MessageUtil.SHARED_PRODUCT_MSG_PACK, MessageUtil.NON_PARSABLE_MSG), e);
            }
        }
        if (value == null) {
            throw new MESException(I18nMessageUtility.getLocalizedMessage(MessageUtil.SHARED_PRODUCT_MSG_PACK, MessageUtil.NON_PARSABLE_MSG));
        }

        return MeasuredValueUtilities.createMV(value, uomButton.getUnitOfMeasure());
    }

    /**
     * Create quantity line and add it to the table quantitiesPanel
     * 
     * @param labelsGroup parallel group for labels
     * @param inputGroup parallel group for inputFields
     * @param uomGroup parallel group for uom buttons
     * 
     * @return the parallel group for the identified line
     */
    private ParallelGroup addIdentifiedQttyLinePanel(ParallelGroup labelsGroup, ParallelGroup inputGroup,
            ParallelGroup uomGroup) {
        ParallelGroup rowGroup = qtyPanelLayout.createParallelGroup(Alignment.BASELINE, true);
        String labelText = AccountMaterialDAO0710.getIdentifiedLabel();
        addLabel(labelText, "identifiedLabel", labelsGroup, rowGroup);

        JTextField identifiedEdit =
                ProductPhaseSwingHelper.createPhaseSwingNumericTouchField(Status.PREVIEW, labelText, JTextField.RIGHT);
        identifiedEdit.setText(I18nBigDecimalUtility.bigDecimalToLocalizedString(identifiedQty.getValue()));

        identifiedEdit.setPreferredSize(EDIT_SIZE);
        identifiedEdit.setName(identifiedEdit.getName().concat("Identified"));
        addToGroup(inputGroup, identifiedEdit);
        addToGroup(rowGroup, identifiedEdit);

        PhaseToggleUomButton identifiedUoM =
                new PhaseToggleUomButton("PhaseDialogButton".concat("identified"), identifiedQty.getUnitOfMeasure(), Status.PREVIEW, true);
        addToGroup(uomGroup, identifiedUoM.getButton());
        addToGroup(rowGroup, identifiedUoM.getButton());

        getMainPanel().setFocusTraversalPolicy(new SwingPhasePanelFocusTraversalPolicy(identifiedEdit));
        getMainPanel().setFocusTraversalPolicyProvider(true);

        return rowGroup;
    }

    /**
     * Create the label
     * 
     * @param labelText the text of the label
     * @param controlName the name of the UI component (added to the default
     *            name)
     * @param labelsGroup parallel group for labels
     * @param rowGroup group for the row
     * 
     * @return the label
     */
    private JLabel addLabel(String labelText, String controlName, ParallelGroup labelsGroup, ParallelGroup rowGroup) {
        JLabel label = ProductPhaseSwingHelper.createJLabelFixedHeight(labelText);
        label.setName(label.getName().concat(controlName));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setVerticalAlignment(SwingConstants.CENTER);
        addToGroup(labelsGroup, label);
        addToGroup(rowGroup, label);

        return label;
    }

    /**
     * Listener for value changed of the input fields
     * 
     * @author RBoyano
     * 
     */
    private class MyCComponentEventListener implements DocumentListener {
        @Override
        public void removeUpdate(DocumentEvent e) {
            refreshDialog();

        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            refreshDialog();

        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            refreshDialog();
        }
    }

    /**
     * Listener for value changed of the toggle buttons
     * 
     * @author ikoleva
     * 
     */
    private class ToggleButtonValueChangedListener implements ValueChangedEventListener {

        @Override
        public void valueChanged(ValueChangedEvent evt) {
            refreshDialog();
        }
    }

    /**
     * Create quantity line and add it to the table quantitiesPanel
     * 
     * @param typeConsumption the type of the consumption
     * @param isEnabed the row will be read only
     * @param labelsGroup parallel group for labels
     * @param inputGroup parallel group for inputFields
     * @param uomGroup parallel group for uom buttons
     * 
     * @return parallel group for this line
     */
    private ParallelGroup addQttyLine(AccountType typeConsumption, boolean isEnabed, ParallelGroup labelsGroup,
            ParallelGroup inputGroup, ParallelGroup uomGroup) {
        ParallelGroup rowGroup = qtyPanelLayout.createParallelGroup(Alignment.BASELINE, true);
        String controlName = typeConsumption.getControlName();

        String quantitiesLabelText = LcAccountMaterialDAO0710.getQuantitiesLabelText(typeConsumption);
        // label
        String labelText = quantitiesLabelText;
        addLabel(labelText, controlName, labelsGroup, rowGroup);

        // get the current value, if any
        IMeasuredValue currentValue = null;
        if (quantities != null) {
            currentValue = quantities.get(typeConsumption);
        }

        JTextField qttyEdit =
                ProductPhaseSwingHelper.createPhaseSwingNumericTouchField(isEnabed ? Status.ACTIVE : Status.PREVIEW, labelText, JTextField.RIGHT);
        qttyEdit.setPreferredSize(EDIT_SIZE);
        qttyEdit.setName(qttyEdit.getName().concat(controlName));
        if (currentValue != null) {
            qttyEdit.setText(I18nBigDecimalUtility.bigDecimalToLocalizedString(currentValue.getValue()));
        }
        addToGroup(inputGroup, qttyEdit);
        addToGroup(rowGroup, qttyEdit);

        PhaseToggleUomButton uomEdit =
                new PhaseToggleUomButton("PhaseDialogButton".concat(controlName),
                        currentValue == null ? identifiedQty.getUnitOfMeasure() : currentValue.getUnitOfMeasure(),
                        isEnabed ? Status.ACTIVE : Status.PREVIEW, true);
        addToGroup(uomGroup, uomEdit.getButton());
        addToGroup(rowGroup, uomEdit.getButton());
        if (isEnabed) {
            qttyEdit.getDocument().addDocumentListener(new MyCComponentEventListener());
            uomEdit.addValueChangedEventListener(new ToggleButtonValueChangedListener());
        }
        switch (typeConsumption) {
        case CONSUMED:
            consumedEdit = qttyEdit;
            consumedUoM = uomEdit;
            break;

        case SAMPLED:
            sampledEdit = qttyEdit;
            sampledUoM = uomEdit;
            break;

        case WASTED:
            wastedEdit = qttyEdit;
            wastedUoM = uomEdit;
            break;

        case RETURNED:
            returnedEdit = qttyEdit;
            returnedUoM = uomEdit;
            break;

        default:
            break;
        }

        return rowGroup;
    }

    /**
     * Add a control to a parallel group with fixed size
     * 
     * @param group group to add to
     * @param control control to be added
     */
    private void addToGroup(ParallelGroup group, JComponent control) {
        group.addComponent(control, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
    }

    /**
     * 判断物料平衡是否启用
     * @return
     */
    private Boolean isparamExcptEnableDefGetEnable(){
        //判断是否启用了异常
        MESParamExcptEnableDef0200 paramExcptEnableDef0200  =  phaseExecutorMatAlterAcct0010.getProcessParameterData(MESParamExcptEnableDef0200.class, LcAccountMaterialDAO0710.Material_Balance_Check_Configuration);
        return paramExcptEnableDef0200.getEnabled();
    }
}
