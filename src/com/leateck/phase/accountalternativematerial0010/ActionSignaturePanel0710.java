package com.leateck.phase.accountalternativematerial0010;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.apache.commons.lang3.StringUtils;

import com.datasweep.compatibility.client.AccessPrivilege;
import com.rockwell.mes.apps.ebr.ifc.exception.PasswordClearance;
import com.rockwell.mes.apps.ebr.ifc.exception.PasswordClearance.IPasswordField;
import com.rockwell.mes.apps.ebr.ifc.exception.PasswordClearance.JTextToPasswordFieldAdapter;
import com.rockwell.mes.apps.ebr.ifc.exception.PhaseSignatureUtil;
import com.rockwell.mes.apps.ebr.ifc.exception.SignatureInputVerifier;
import com.rockwell.mes.apps.ebr.ifc.phase.IPhaseExecutor.Status;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.UIConstants;
import com.rockwell.mes.apps.ebr.ifc.swing.ListFocusTraversalPolicy;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseSwingHelper;
import com.rockwell.mes.clientfw.commons.ifc.external.ExternalAuthenticationSupport;
import com.rockwell.mes.clientfw.commons.ifc.swing.IComponentWithVirtualKeyboard;
import com.rockwell.mes.commons.base.ifc.services.ServiceFactory;
import com.rockwell.mes.commons.deviation.ifc.IESignatureExecutor;
import com.rockwell.mes.commons.deviation.ifc.IESignatureService;
import com.rockwell.mes.commons.deviation.ifc.SignatureUtil;
import com.rockwell.mes.services.s88.ifc.recipe.IMESPrivilegeParameter;

/**
 * Panel containing the signature fields used for phase action signature
 * <p>
 * 
 * @author ikoleva, (c) Copyright 2020 Rockwell Automation Technologies, Inc. All Rights Reserved.
 */
public class ActionSignaturePanel0710 extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField editUser1;

    private JTextField editUser2;

    private JTextField editPassword1;

    private JTextField editPassword2;

    private String userName1;

    private String password1;

    private String userName2;

    private String password2;

    private transient PasswordClearance passwordClearance;

    private final transient AccessPrivilege phaseActionPrivilege;

    private final transient IMESPrivilegeParameter phaseActionPrivilegeParameter;

    private final transient IESignatureExecutor signatureExecutor;

    private final JButton confirmComponent;

    private static final Dimension EDIT_SIZE = new Dimension(150, UIConstants.EDIT_PREFERRED_SIZE.height);

    private static final int DIALOG_WIDTH = 500;

    private int dialogHeight;

    private final boolean isDoubleSignature;

    private transient ExternalAuthenticationSupport extAuthenticatorUser1;

    private transient ExternalAuthenticationSupport extAuthenticatorUser2;

    private transient ExtAuthenticationAncestorListener extAuthenticationListener;

    private transient SignatureInputVerifier fieldInputVerifier;

    /**
     * AncestorListener used by External Authentication to allow to deactivate/cancel outstanding click events if
     * execution view is leaving
     */
    private class ExtAuthenticationAncestorListener implements AncestorListener {

        @Override
        public void ancestorAdded(AncestorEvent event) {
            extAuthenticatorUser1.activate();
            if (extAuthenticatorUser2 != null) {
                extAuthenticatorUser2.activate();
            }
        }

        @Override
        public void ancestorRemoved(AncestorEvent event) {
            // remove outstanding click events when execution is not visible anymore
            cleanUpExtAuthenticationSupport();
            extAuthenticatorUser1.deactivate();
            if (extAuthenticatorUser2 != null) {
                extAuthenticatorUser2.deactivate();
            }
        }

        @Override
        public void ancestorMoved(AncestorEvent event) {
            // empty by intent
        }
    }

    /**
     * @param argPrivilege the access privilege for the phase action
     * @param argParam the privilege parameter in the phase
     * @param argConfirmComponent the button that will confirm the signatures
     */
    public ActionSignaturePanel0710(AccessPrivilege argPrivilege, IMESPrivilegeParameter argParam, JButton argConfirmComponent) {
        super();
        phaseActionPrivilege = argPrivilege;
        phaseActionPrivilegeParameter = argParam;
        signatureExecutor = ServiceFactory.getService(IESignatureService.class).getESignatureExecutor(phaseActionPrivilege.getName());
        confirmComponent = argConfirmComponent;
        isDoubleSignature = SignatureUtil.isDoubleSignature(phaseActionPrivilege);
        init();
    }

    private void init() {
        signatureExecutor.setErrorCallback(PhaseSignatureUtil.ERROR_CALLBACK);
        PhaseSignatureUtil.setExternalPerformerAndVerifierReason(signatureExecutor, phaseActionPrivilege, phaseActionPrivilegeParameter);
        createUI();
        initFocusTraversalPolicy();
        createPasswordClearance();
        createExternalAuthenticationSupport();
        setInputVerifierOnSignatureFields();
    }

    private void createUI() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(0, UIConstants.DEFAULT_GAP, 0, AccountQuantitiesDialog0710.RIGHT_BORDER));

        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.fill = GridBagConstraints.HORIZONTAL;
        gbConstraints.weighty = 1;
        gbConstraints.weightx = 1;
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbConstraints.insets = new Insets(UIConstants.DEFAULT_GAP, 0, 0, UIConstants.DEFAULT_GAP);

        // the row height will be determined by the highest component
        int firstRowHeight = EDIT_SIZE.height;
        // the label width should be what remains after the edits and all gaps and borders are considered
        final int labelWidth = DIALOG_WIDTH - 2 * EDIT_SIZE.width - 4 * UIConstants.DEFAULT_GAP - AccountQuantitiesDialog0710.RIGHT_BORDER;

        String reason1 = PhaseSignatureUtil.getPerformerSignatureReason(phaseActionPrivilege, phaseActionPrivilegeParameter);
        JLabel reason1Lbl = createReasonLabel(reason1, 1, labelWidth);
        add(reason1Lbl, gbConstraints);
        firstRowHeight = Math.max(firstRowHeight, reason1Lbl.getPreferredSize().height);

        editUser1 = createUserEdit(reason1, 1);
        gbConstraints.gridx++;
        add(editUser1, gbConstraints);

        editPassword1 = createPasswordEdit(reason1, 1);
        gbConstraints.gridx++;
        add(editPassword1, gbConstraints);

        dialogHeight = firstRowHeight + 2 * UIConstants.DEFAULT_GAP;

        if (isDoubleSignature) {
            gbConstraints.gridy++;
            gbConstraints.gridx = 0;
            int secondRowHeight = EDIT_SIZE.height;
            String reason2 = PhaseSignatureUtil.getVerifierSignatureReason(phaseActionPrivilege, phaseActionPrivilegeParameter);
            JLabel reason2Lbl = createReasonLabel(reason2, 2, labelWidth);
            add(reason2Lbl, gbConstraints);
            secondRowHeight = Math.max(secondRowHeight, reason2Lbl.getPreferredSize().height);

            editUser2 = createUserEdit(reason2, 2);
            gbConstraints.gridx++;
            add(editUser2, gbConstraints);

            editPassword2 = createPasswordEdit(reason2, 2);
            gbConstraints.gridx++;
            add(editPassword2, gbConstraints);
            dialogHeight += secondRowHeight + 2 * UIConstants.DEFAULT_GAP;
        }
    }

    private JLabel createReasonLabel(String reason, int signatureNo, int labelWidth) {
        JLabel reasonLbl = PhaseSwingHelper.createMultiLineLabel(reason, labelWidth, SwingConstants.TRAILING);
        reasonLbl.setVerticalAlignment(SwingConstants.TOP);
        reasonLbl.setName("PhaseDialogLabel_reason" + signatureNo);
        // create a border for the label: this way the text of the label will be aligned with the text in an edit field
        final int labelTopGap = 3;
        reasonLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, labelTopGap, 0));
        return reasonLbl;
    }

    private JTextField createUserEdit(String reason, int signatureNo) {
        JTextField edit = PhaseSwingHelper.createAlphaNumericTouchField(Status.ACTIVE, reason);
        edit.setPreferredSize(EDIT_SIZE);
        edit.setName("phaseEditUser" + signatureNo);
        return edit;
    }

    private JTextField createPasswordEdit(String reason, int signatureNo) {
        JTextField edit = PhaseSwingHelper.createAlphaNumericPasswordTouchField(Status.ACTIVE, reason);
        edit.setPreferredSize(EDIT_SIZE);
        edit.setName("phaseEditPassword" + signatureNo);
        return edit;
    }

    /**
     * initialize FocusTraversalPolicy for the input fields
     */
    private void initFocusTraversalPolicy() {
        ListFocusTraversalPolicy ftp;
        List<JComponent> focusableComponents = new ArrayList<>();
        focusableComponents.add(editUser1);
        focusableComponents.add(editPassword1);
        if (isDoubleSignature) {
            focusableComponents.add(editUser2);
            focusableComponents.add(editPassword2);
        }
        focusableComponents.add(confirmComponent);
        ftp = new ListFocusTraversalPolicy(Collections.unmodifiableList(focusableComponents));
        setFocusTraversalPolicy(ftp);
        setFocusTraversalPolicyProvider(true);
    }

    /**
     * Creates a password clearance object so that if the user tries to modify something after signing, the password(s)
     * are deleted.
     */
    private void createPasswordClearance() {
        List<JComponent> focusableComponents = new ArrayList<>();
        List<IPasswordField> passwordFields = new ArrayList<>();
        focusableComponents.add(editUser1);
        focusableComponents.add(editPassword1);
        if (confirmComponent != null) {
            focusableComponents.add(confirmComponent);
        }
        passwordFields.add(new JTextToPasswordFieldAdapter(editPassword1));

        if (signatureExecutor.isDoubleSignature()) {
            focusableComponents.add(editUser2);
            focusableComponents.add(editPassword2);
            passwordFields.add(new JTextToPasswordFieldAdapter(editPassword2));
        }
        // find all focusable components that support a virtual keyboard
        List<IComponentWithVirtualKeyboard> virualKeyboardComponents = new ArrayList<>();
        for (JComponent component : focusableComponents) {
            if (component instanceof IComponentWithVirtualKeyboard) {
                virualKeyboardComponents.add((IComponentWithVirtualKeyboard) component);
            }
        }
        passwordClearance = new PasswordClearance(false, focusableComponents, passwordFields, virualKeyboardComponents);
    }

    private void createExternalAuthenticationSupport() {
        extAuthenticatorUser1 = new ExternalAuthenticationSupport(editUser1, editPassword1, confirmComponent);
        String exceptionView = "PhaseCompletion";
        extAuthenticatorUser1.setIdentifier(exceptionView);
        extAuthenticatorUser1.setPasswordClearance(passwordClearance);
        extAuthenticatorUser1.activate();

        if (isDoubleSignature) {
            // in case of double signature authentication of first user doesn't trigger auto ok, because second is not
            // filled yet at that time. Auto OK works only meaningfull if entered in the sequence user1, user2.
            extAuthenticatorUser1.setEnabledAutoOk(false);

            extAuthenticatorUser2 = new ExternalAuthenticationSupport(editUser2, editPassword2, confirmComponent);
            extAuthenticatorUser2.setIdentifier(exceptionView);
            extAuthenticatorUser2.setPasswordClearance(passwordClearance);
            // second signature might always trigger the auto ok if configured
            extAuthenticatorUser2.setEnabledAutoOk(true);
            extAuthenticatorUser2.activate();
        }

        extAuthenticationListener = new ExtAuthenticationAncestorListener();
        editUser1.addAncestorListener(extAuthenticationListener);
    }

    /**
     * Set input Verifier on signature fields.
     */
    private void setInputVerifierOnSignatureFields() {
        fieldInputVerifier = new SignatureInputVerifier(signatureExecutor, passwordClearance);
        fieldInputVerifier.setFieldsForSignature1(editUser1, editPassword1, extAuthenticatorUser1);
        if (isDoubleSignature) {
            fieldInputVerifier.setFieldsForSignature2(editUser2, editPassword2, extAuthenticatorUser2);
        }
        setInputVerifierOnInputFields();
    }

    /**
     * Set the given input verifier on the input fields
     */
    private void setInputVerifierOnInputFields() {
        // only does verify on focus changes
        editUser1.setInputVerifier(fieldInputVerifier);
        editPassword1.setInputVerifier(fieldInputVerifier);
        if (isDoubleSignature) {
            editUser2.setInputVerifier(fieldInputVerifier);
            editPassword2.setInputVerifier(fieldInputVerifier);
        }

        // now it can also detect single char changes in the field
        editUser1.addKeyListener(fieldInputVerifier);
        editPassword1.addKeyListener(fieldInputVerifier);
        if (isDoubleSignature) {
            editUser2.addKeyListener(fieldInputVerifier);
            editPassword2.addKeyListener(fieldInputVerifier);
        }
    }

    private void cleanInputVerifierOnInputFields() {
        // for removing the inputverifier from as KeyListener, we cannot use null.
        editUser1.setInputVerifier(null);
        editUser1.removeKeyListener(fieldInputVerifier);
        editPassword1.setInputVerifier(null);
        editPassword1.removeKeyListener(fieldInputVerifier);
        if (isDoubleSignature) {
            editUser2.setInputVerifier(null);
            editUser2.removeKeyListener(fieldInputVerifier);
            editPassword2.setInputVerifier(null);
            editPassword2.removeKeyListener(fieldInputVerifier);
        }
    }

    /** Performs clean-up actions */
    public void cleanup() {
        passwordClearance.cleanUp();
        cleanInputVerifierOnInputFields();
        shutdownExtAuthenticationSupport();
    }

    private void shutdownExtAuthenticationSupport() {
        extAuthenticatorUser1.shutdown();
        if (extAuthenticatorUser2 != null) {
            extAuthenticatorUser2.shutdown();
        }
        editUser1.removeAncestorListener(extAuthenticationListener);
    }

    private void cleanUpExtAuthenticationSupport() {
        extAuthenticatorUser1.cleanUpCurrentWorkInProgress();
        if (extAuthenticatorUser2 != null) {
            extAuthenticatorUser2.cleanUpCurrentWorkInProgress();
        }
    }

    /**
     * Check if the credentials are valid and execute the signature
     * 
     * @return {@code true} if the signature was executed successfully
     */
    public boolean checkAndExecuteSignture() {
        prepareForSignatureCheck();
        JTextField emptyField = getFirstEmptySignatureField();
        if (emptyField != null) {
            emptyField.requestFocus();
            // if not all fields has been entered yet, set focus
            // and prevent completion but without annoying error message
            return false;
        }
        signatureExecutor.clear();
        return signatureExecutor.execute(userName1, password1, fieldInputVerifier.getTimestamp1(), userName2, password2,
                fieldInputVerifier.getTimestamp2());
    }

    private void prepareForSignatureCheck() {
        // store the user credentials because they will be cleared after the focus is lost for some time
        // and some components or the phase itself can perform time-consuming actions on performCompletionCheck and
        // performComplete
        userName1 = editUser1.getText();
        password1 = editPassword1.getText();
        if (isDoubleSignature) {
            userName2 = editUser2.getText();
            password2 = editPassword2.getText();
        } else {
            userName2 = StringUtils.EMPTY;
            password2 = StringUtils.EMPTY;
        }

        // If delay is long for autoOk, user might have been clicked manually. At this point of time Button has been
        // clicked, so we stop here the delayed task if there is any.
        cleanUpExtAuthenticationSupport();
    }

    private JTextField getFirstEmptySignatureField() {
        JTextField emptyField = null;
        if (StringUtils.isEmpty(userName1)) {
            emptyField = editUser1;
        } else if (StringUtils.isEmpty(password1)) {
            emptyField = editPassword1;
        } else if (isDoubleSignature && StringUtils.isEmpty(userName2)) {
            emptyField = editUser2;
        } else if (isDoubleSignature && StringUtils.isEmpty(password2)) {
            emptyField = editPassword2;
        }
        return emptyField;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DIALOG_WIDTH, dialogHeight);
    }

    /**
     * @return the signature executor for this action signature; Please note: the signature executor is not saved yet.
     */
    public IESignatureExecutor getSignatureExecutor() {
        return signatureExecutor;
    }
}
