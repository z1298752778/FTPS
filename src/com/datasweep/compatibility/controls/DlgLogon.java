//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.datasweep.compatibility.controls;

import com.datasweep.core.eventlog.EventLog;
import com.datasweep.plantops.swing.JBaseDialog;
import com.datasweep.plantops.swing.UIConstants;
import com.datasweep.plantops.swing.util.UIUtilities;
import com.rockwell.external.AuthenticationEvent;
import com.rockwell.external.AuthenticationEventListener;
import com.rockwell.external.CustomAuthentication;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DlgLogon extends JBaseDialog implements AuthenticationEventListener {
    boolean rc;
    DlgLogon.ExternalAuthentication extAuthenticator;
    DlgLogon.ImagePanel icon;
    JTextArea label;
    JLabel label1;
    JLabel label2;
    JTextField edUserName;
    JPasswordField edPassword;
    JButton btnOK;
    JButton btnCancel;

    public DlgLogon(Component var1) {
        this((Frame)((Frame)(var1 instanceof Frame ? var1 : SwingUtilities.getAncestorOfClass(Frame.class, var1))), true);
        this.setLocationRelativeTo(var1);
    }

    private DlgLogon(Frame var1, boolean var2) {
        super(var1, var2);
        this.rc = false;
        this.extAuthenticator = null;
        this.icon = new DlgLogon.ImagePanel("images/loginKey.gif");
        this.label = new JTextArea();
        this.label1 = new JLabel();
        this.label2 = new JLabel();
        this.edUserName = new JTextField();
        this.edPassword = new JPasswordField();
        this.btnOK = new JButton();
        this.btnCancel = new JButton();
        this.initForm();
        UIUtilities.setPreferredWidthToMaximumPreferredWidth(new JComponent[]{this.label1, this.label2});
        UIUtilities.setPreferredWidthToMaximumPreferredWidth(new JComponent[]{this.btnOK, this.btnCancel});
    }

    public String[] getResult() {
        return new String[]{this.edUserName.getText(), new String(this.edPassword.getPassword())};
    }

    public boolean showDialog() {
        try {
            String var1 = System.getProperty("CustomAuthentication", "com.rockwell.external.BiometricAuthentication");
            this.extAuthenticator = new DlgLogon.ExternalAuthentication(this, var1);
            if (this.extAuthenticator != null) {
                this.extAuthenticator.addEventListener(this);
                (new Thread(this.extAuthenticator)).start();
            }
        } catch (Throwable var2) {
        }

        this.show();
        return this.rc;
    }

    public void onAuthenticationEvent(AuthenticationEvent var1) {
        final AuthenticationEvent var2 = var1;
        if (SwingUtilities.isEventDispatchThread()) {
            this.edUserName.setText(var1.getUserName());
            this.edPassword.setText(var1.getPassword());
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        DlgLogon.this.edUserName.setText(var2.getUserName());
                        DlgLogon.this.edPassword.setText(var2.getPassword());
                    }
                });
            } catch (InvocationTargetException var8) {
                EventLog.logException(var8, this, "onAuthenticationEvent(), setUserName or setPassword in Event Dispatch Thread throw InvocationTargetException: " + var8.getStackTrace());
            } catch (InterruptedException var9) {
                EventLog.logException(var9, this, "onAuthenticationEvent(), setUserName or setPassword in Event Dispatch Thread throw InterruptedException: " + var9.getStackTrace());
            }
        }

        if (var1.isAutoOK()) {
            int var3 = this.extAuthenticator != null ? this.extAuthenticator.getAutoOKDelay() : 0;
            if (var3 > 0) {
                try {
                    Thread.sleep((long)var3);
                } catch (Throwable var7) {
                }
            }

            if (this.btnOK.isEnabled()) {
                if (SwingUtilities.isEventDispatchThread()) {
                    this.btnOK.doClick();
                } else {
                    try {
                        SwingUtilities.invokeAndWait(new Runnable() {
                            public void run() {
                                DlgLogon.this.btnOK.doClick();
                            }
                        });
                    } catch (InvocationTargetException var5) {
                        EventLog.logException(var5, this, "onAuthenticationEvent(), btnOK.doClick() in Event Dispatch Thread throw InvocationTargetException: " + var5.getStackTrace());
                    } catch (InterruptedException var6) {
                        EventLog.logException(var6, this, "onAuthenticationEvent(), btnOK.doClick() in Event Dispatch Thread throw InterruptedException: " + var6.getStackTrace());
                    }
                }
            }
        }

    }

    private void doConfirm() {
        String var1 = new String(this.edPassword.getPassword());
        this.btnOK.setEnabled(this.edUserName.getText().length() != 0 && var1.length() != 0);
    }

    private void close(boolean var1) {
        if (this.extAuthenticator != null) {
            this.extAuthenticator.removeEventListener(this);
            this.extAuthenticator = null;
        }

        this.rc = var1;
        this.DlgLogon_windowClosing();
    }

    private void DlgLogon_windowClosing() {
        this.setVisible(false);
    }

    protected void initForm() {
        this.icon.setMinimumSize(new Dimension(32, 32));
        this.icon.setPreferredSize(new Dimension(32, 32));
        this.icon.setMaximumSize(new Dimension(32, 32));
        String var1 = System.getProperty("user.language.format");
        if (var1 == null) {
            var1 = System.getProperty("user.language");
        }

        String var2 = System.getProperty("user.country.format");
        if (var2 == null) {
            var2 = System.getProperty("user.country");
        }

        Locale var3 = new Locale(var1, var2);
        UIConstants.switchLocale(var3);
        this.label.setText("使用有效的用户名和密码登录到系统。");//TODO 修改国际化
        this.label.setEditable(false);
        this.label.setLineWrap(true);
        this.label.setWrapStyleWord(true);
        this.label.setFocusable(false);
        this.label.setBackground(this.getBackground());
        this.label1.setText("用户名：");//TODO 修改国际化
        this.label2.setText("密码：");//TODO 修改国际化
        this.edUserName.setText("");
        this.edUserName.getDocument().addDocumentListener(new DlgLogon.DocumentListenerAdapter());
        this.edPassword.setText("");
        this.edPassword.getDocument().addDocumentListener(new DlgLogon.DocumentListenerAdapter());
        this.btnOK.setEnabled(false);
        this.btnOK.setText(UIConstants.okButtonText);
        this.btnOK.setMnemonic(UIConstants.okButtonMnemonic);
        this.btnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                DlgLogon.this.close(true);
            }
        });
        this.btnCancel.setText(UIConstants.cancelButtonText);
        this.btnCancel.setMnemonic(UIConstants.cancelButtonMnemonic);
        this.btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                DlgLogon.this.close(false);
            }
        });
        JPanel var4 = new JPanel();
        var4.setLayout(new BorderLayout());
        var4.add(this.label, "Center");
        JPanel var5 = new JPanel();
        var5.setLayout(new BoxLayout(var5, 0));
        var5.add(this.label1);
        var5.add(Box.createRigidArea(new Dimension(8, 0)));
        var5.add(this.edUserName);
        JPanel var6 = new JPanel();
        var6.setLayout(new BoxLayout(var6, 0));
        var6.add(this.label2);
        var6.add(Box.createRigidArea(new Dimension(8, 0)));
        var6.add(this.edPassword);
        JPanel var7 = new JPanel();
        var7.setLayout(new BoxLayout(var7, 1));
        var7.add(var5);
        var7.add(Box.createRigidArea(new Dimension(0, 11)));
        var7.add(var6);
        JPanel var8 = new JPanel();
        var8.setLayout(new FlowLayout(1, 4, 0));
        var8.add(this.btnOK);
        var8.add(this.btnCancel);
        JPanel var9 = new JPanel();
        var9.setLayout(new BoxLayout(var9, 0));
        var9.add(this.icon);
        var9.add(Box.createRigidArea(new Dimension(11, 0)));
        var9.add(var7);
        JPanel var10 = new JPanel();
        var10.setLayout(new BoxLayout(var10, 1));
        var10.add(var4);
        var10.add(Box.createRigidArea(new Dimension(0, 11)));
        var10.add(var9);
        JPanel var11 = new JPanel();
        var11.setLayout(new BorderLayout(0, 11));
        var11.add(var10, "North");
        var11.add(var8, "South");
        var11.setBorder(UIConstants.dialogBorder);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(var11, "Center");
        this.getRootPane().setDefaultButton(this.btnOK);
        this.setTitle("登录信息");//TODO 修改国际化
        this.setVisible(false);
        this.setSize(307, 190);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent var1) {
                DlgLogon.this.close(false);
            }
        });
    }

    protected JTextField getEdUserName() {
        return this.edUserName;
    }

    protected JTextField getEdPassword() {
        return this.edPassword;
    }

    protected JButton getBtnOK() {
        return this.btnOK;
    }

    protected JButton getBtnCancel() {
        return this.btnCancel;
    }

    protected void setIconVisible(boolean var1) {
        this.icon.setVisible(var1);
    }

    protected void closeDialog() {
        this.close(false);
    }

    class ExternalAuthentication implements Runnable {
        JDialog container = null;
        CustomAuthentication authenticator = null;

        ExternalAuthentication(JDialog var2, String var3) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
            this.container = var2;
            Class var4 = Class.forName(var3);
            if (var4 != null) {
                this.authenticator = (CustomAuthentication)var4.newInstance();
            }

        }

        void addEventListener(AuthenticationEventListener var1) {
            if (this.authenticator != null) {
                this.authenticator.addEventListener(var1);
            }

        }

        void removeEventListener(AuthenticationEventListener var1) {
            if (this.authenticator != null) {
                this.authenticator.removeEventListener(var1);
            }

        }

        int getAutoOKDelay() {
            return this.authenticator != null ? this.authenticator.getAutoOKDelay() : 0;
        }

        public void run() {
            if (this.container != null) {
                while(!this.container.isShowing()) {
                    try {
                        Thread.sleep(100L);
                    } catch (Throwable var2) {
                    }
                }
            }

            this.authenticator.readyToAuthenticate();
        }
    }

    class DocumentListenerAdapter implements DocumentListener {
        DocumentListenerAdapter() {
        }

        public void insertUpdate(DocumentEvent var1) {
            DlgLogon.this.doConfirm();
        }

        public void removeUpdate(DocumentEvent var1) {
            DlgLogon.this.doConfirm();
        }

        public void changedUpdate(DocumentEvent var1) {
            DlgLogon.this.doConfirm();
        }
    }

    class ImagePanel extends JPanel {
        Image image;

        ImagePanel(String var2) {
            this.image = Toolkit.getDefaultToolkit().getImage(DlgLogon.class.getResource(var2));
        }

        public void paintComponent(Graphics var1) {
            super.paintComponent(var1);
            var1.drawImage(this.image, 0, 0, this);
        }
    }
}
