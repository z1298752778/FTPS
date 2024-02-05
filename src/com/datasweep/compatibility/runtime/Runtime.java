//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.datasweep.compatibility.runtime;

import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.DBInfo;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.DeAnzaForm;
import com.datasweep.compatibility.client.DsMessages;
import com.datasweep.compatibility.client.Error;
import com.datasweep.compatibility.client.Response;
import com.datasweep.compatibility.client.ServerInfo;
import com.datasweep.compatibility.client.StationFilter;
import com.datasweep.compatibility.client.User;
import com.datasweep.compatibility.controls.DlgAbout;
import com.datasweep.compatibility.controls.DlgChangePassword;
import com.datasweep.compatibility.controls.DlgLogon;
import com.datasweep.compatibility.controls.SplashScreen;
import com.datasweep.compatibility.formdesigner.DebugWindow;
import com.datasweep.compatibility.formdesigner.DebuggerConsole;
import com.datasweep.compatibility.formdesigner.EditorPanel;
import com.datasweep.compatibility.manager.ServerImpl;
import com.datasweep.compatibility.pnuts.CurrentObjectEvent;
import com.datasweep.compatibility.pnuts.CurrentObjectListener;
import com.datasweep.compatibility.pnuts.EvaluateScriptEvent;
import com.datasweep.compatibility.pnuts.EvaluateScriptEventListener;
import com.datasweep.compatibility.pnuts.FormEnvironment;
import com.datasweep.compatibility.pnuts.FunctionsImpl;
import com.datasweep.compatibility.pnuts.IDebugSupport;
import com.datasweep.compatibility.pnuts.TimingData;
import com.datasweep.compatibility.pnutsfunctions.utility.OptionsSetting;
import com.datasweep.compatibility.ui.CComponent;
import com.datasweep.compatibility.ui.Form;
import com.datasweep.compatibility.ui.FormPrinter;
import com.datasweep.compatibility.ui.ObjectBinder;
import com.datasweep.compatibility.ui.SerialPort;
import com.datasweep.compatibility.ui.SocketControl;
import com.datasweep.compatibility.ui.Time;
import com.datasweep.core.ExceptionEvent;
import com.datasweep.core.IExceptionEventListener;
import com.datasweep.core.eventlog.EventLog;
import com.datasweep.core.message.MessagesUtil;
import com.datasweep.core.session.SessionMonitor;
import com.datasweep.core.utility.Utility;
import com.datasweep.plantops.common.dataobjects.DDBInfo;
import com.datasweep.plantops.common.dataobjects.DReadInfo;
import com.datasweep.plantops.controls.DlgLocale;
import com.datasweep.plantops.property.BeanBinder;
import com.datasweep.plantops.swing.DatasweepPlasticTheme;
import com.datasweep.plantops.swing.DlgMobileMessage;
import com.datasweep.plantops.swing.MessageBox;
import com.datasweep.plantops.swing.MultiLineToolTipUI;
import com.datasweep.plantops.swing.UIConstants;
import com.datasweep.plantops.utility.BrowserControl;
import com.jgoodies.plaf.plastic.PlasticLookAndFeel;
import com.jgoodies.plaf.plastic.PlasticXPLookAndFeel;
import com.rockwell.activity.ActivityList;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import pnuts.lang.PnutsException;
import pnuts.tools.CommandListener;

public class Runtime extends JPanel implements IExceptionEventListener, CurrentObjectListener, EvaluateScriptEventListener {
    private JFrame m_frame;
    private Runtime.FactoryOwner m_factoryOwner;
    private FormEnvironment m_environment;
    private OptionsSetting m_options;
    private DBInfo m_dbInfo;
    private Runtime.OutputWindow console;
    private int m_timeout;
    private Time m_sessionExpiration;
    private boolean m_disableAutoTimeout;
    private FunctionsImpl functions;
    private Timer sessionTimer;
    private Time m_closeExpiration;
    private Timer closeTimer;
    protected static final boolean USE_PLASTIC_LAF = true;
    JMenuBar menuBar;
    JMenu menuFile;
    JMenuItem miOpen;
    JMenuItem miClose;
    JMenuItem miPrint;
    JMenuItem miOptions;
    JMenuItem miMode;
    JCheckBoxMenuItem miTiming;
    JMenuItem miLogon;
    JMenuItem miLogoff;
    JMenuItem miChangeUser;
    JMenuItem miChangePassword;
    JMenuItem miExit;
    JMenu menuView;
    JCheckBoxMenuItem miOutputWindow;
    JCheckBoxMenuItem miStatusBar;
    JMenuItem miLocale;
    JMenuItem miViewLog;
    JMenu menuHelp;
    JMenuItem miTopics;
    JMenuItem miAbout;
    JPanel statusBar;
    private static final String DEFAULT_BUTTON_FOLLOW_FOCUS_PROPERTY = "uiDefaultButtonFollowFocus";

    public void setM_factoryOwner(Runtime.FactoryOwner var1) {
        this.m_factoryOwner = var1;
    }

    public Runtime(final JFrame var1) {
        this();
        if (SwingUtilities.isEventDispatchThread()) {
            this.m_frame = var1;
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        Runtime.this.m_frame = var1;
                    }
                });
            } catch (InvocationTargetException var5) {
                EventLog.logException(var5, this, "Runtime() in Event Dispatch Thread throw InvocationTargetException: " + var5.getStackTrace());
            } catch (InterruptedException var6) {
                EventLog.logException(var6, this, "Runtime() in Event Dispatch Thread throw InterruptedException: " + var6.getStackTrace());
            }
        }

        var1.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent var1) {
                if (Runtime.this.m_options.isChanged()) {
                    Runtime.this.functions.setOptionsSetting(Runtime.this.m_options);
                }

            }
        });
        var1.addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent var1) {
                Runtime.this.Runtime_closing();
            }
        });
        if (SwingUtilities.isEventDispatchThread()) {
            var1.getContentPane().add(this, "Center");
            var1.setJMenuBar(this.menuBar);
            var1.pack();
            var1.setSize(640, 400);
            var1.setLocationRelativeTo((Component)null);
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        var1.getContentPane().add(Runtime.this, "Center");
                        var1.setJMenuBar(Runtime.this.menuBar);
                        var1.pack();
                        var1.setSize(640, 400);
                        var1.setLocationRelativeTo((Component)null);
                    }
                });
            } catch (InvocationTargetException var3) {
                EventLog.logException(var3, this, "Runtime() in Event Dispatch Thread throw InvocationTargetException: " + var3.getStackTrace());
            } catch (InterruptedException var4) {
                EventLog.logException(var4, this, "Runtime() in Event Dispatch Thread throw InterruptedException: " + var4.getStackTrace());
            }
        }

        MultiLineToolTipUI.initialize();
    }

    private Runtime() {
        this.m_frame = null;
        this.m_factoryOwner = null;
        this.m_environment = null;
        this.m_options = null;
        this.m_dbInfo = null;
        this.console = null;
        this.m_timeout = 0;
        this.m_sessionExpiration = null;
        this.m_disableAutoTimeout = false;
        this.functions = null;
        this.sessionTimer = new Timer(10000, new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.sessionTimer();
            }
        });
        this.m_closeExpiration = null;
        this.closeTimer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.closeTimer();
            }
        });
        this.menuBar = new JMenuBar();
        this.menuFile = new JMenu("File");
        this.miOpen = new JMenuItem("Open...");
        this.miClose = new JMenuItem("Close");
        this.miPrint = new JMenuItem("Print...");
        this.miOptions = new JMenuItem("Options...");
        this.miMode = new JMenuItem("Form Factor...");
        this.miTiming = new JCheckBoxMenuItem("Time Scripts");
        this.miLogon = new JMenuItem("Logon...");
        this.miLogoff = new JMenuItem("Logoff");
        this.miChangeUser = new JMenuItem("Change User...");
        this.miChangePassword = new JMenuItem("Change Password...");
        this.miExit = new JMenuItem("Exit");
        this.menuView = new JMenu("View");
        this.miOutputWindow = new JCheckBoxMenuItem("Output Window");
        this.miStatusBar = new JCheckBoxMenuItem("Status Bar");
        this.miLocale = new JMenuItem("Locale...");
        this.miViewLog = new JMenuItem("View Log");
        this.menuHelp = new JMenu("Help");
        this.miTopics = new JMenuItem("Help Topics");
        this.miAbout = new JMenuItem("About Shop Operations");
        this.statusBar = new JPanel();
        if (SwingUtilities.isEventDispatchThread()) {
            this.initForm();
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        Runtime.this.initForm();
                    }
                });
            } catch (InvocationTargetException var2) {
                EventLog.logException(var2, Runtime.class, "initForm() in Event Dispatch Thread throw InvocationTargetException: " + var2.getStackTrace());
            } catch (InterruptedException var3) {
                EventLog.logException(var3, Runtime.class, "initForm() in Event Dispatch Thread throw InterruptedException: " + var3.getStackTrace());
            }
        }

        EventLog.addEventListener(this);
    }

    private boolean isIsoString(String var1) {
        return var1 != null && var1.trim().length() == 2;
    }

    private void setLocale(String var1) {
        Locale var2 = null;
        String[] var3 = var1.split("-", 3);
        String var4 = var3[0];
        String var5 = var3[1];
        String var6 = var3[2];
        if (var4 != null && var4.length() != 0) {
            if (this.isIsoString(var4) && this.isIsoString(var5) && var6 != null) {
                var2 = new Locale(var4, var5, var6);
            } else if (this.isIsoString(var4) && this.isIsoString(var5)) {
                var2 = new Locale(var4, var5);
            } else if (this.isIsoString(var4)) {
                var2 = new Locale(var4);
            }

            this.m_environment.switchLocale(var2);
            this.setupButton();
        }
    }

    protected FormEnvironment getEnvironment() {
        if (this.m_environment == null) {
            this.m_environment = new FormEnvironment(this.getFactoryOwner(), this.m_frame, (CommandListener)null, false, (IDebugSupport)null, this.console != null && this.console.isVisible() ? this.console.panel.getOut() : null, this.console != null && this.console.isVisible() ? this.console.panel.getErr() : null);
            this.m_environment.addCurrentObjectListener(this);
            this.m_environment.setStatusBarVisible(this.miStatusBar.isSelected());
            String var1 = System.getProperty("user.language.format");
            if (var1 == null) {
                var1 = System.getProperty("user.language");
            }

            String var2 = System.getProperty("user.country.format");
            if (var2 == null) {
                var2 = System.getProperty("user.country");
            }

            Locale var3 = new Locale(var1, var2);
            this.m_environment.refreshUIConstants(var3);
        }

        return this.m_environment;
    }

    private DBInfo getDBInfo() {
        return this.m_dbInfo;
    }

    private ServerImpl getFactoryOwner() {
        return this.m_factoryOwner;
    }

    private void closeTimer() {
        this.closeTimer.stop();
        if (this.m_environment.getCurrentDialog() != null) {
            this.m_environment.stopDialog();
            if (Time.compare(new Time(), this.m_closeExpiration) < 0) {
                this.closeTimer.start();
                return;
            }
        }

        String var1 = "Session expired on " + this.m_sessionExpiration.toString();
        this.m_environment.stopDialog();

        while(this.getFactoryOwner().isLoggedIn()) {
            this.logSessionTimeout();
            this.doLogoff();
        }

        this.m_frame.setExtendedState(1);
        this.m_frame.setExtendedState(0);
        this.m_frame.toFront();
        String var2 = "Session Expired";
        String var3 = this.getFormFactor();
        if (var3 != null && var3.equals("M")) {
            DlgMobileMessage var4 = new DlgMobileMessage(this.m_frame, var2, var1);
            var4.show();
            var4.dispose();
        } else {
            JOptionPane.showMessageDialog(this.m_frame, var1, var2, 0);
        }

        this.doLogon();
    }

    public void preconnect(String var1, String var2, String var3) {
        try {
            this.m_factoryOwner = new Runtime.FactoryOwner(new ServerInfo(var1, var2, var3));
        } catch (Exception var5) {
            EventLog.dispatchException(var5, this, "connect(" + var1 + "," + var2 + "," + var3 + ")");
        }

    }

    public void connect(String var1, String var2, String var3) {
        try {
            if (!this.m_factoryOwner.isLoggedIn()) {
                this.doLogon();
            }

            this.m_dbInfo = this.m_factoryOwner.getDBInfo();
            if (this.m_dbInfo == null) {
                return;
            }

            if (!this.m_dbInfo.isValidSchema()) {
                int[] var4 = this.m_dbInfo.getExpectedDBSchemaVersion();
                String var5 = "" + var4[0];
                String var6 = "" + var4[1];
                if (var4[1] < 10) {
                    var6 = "0" + var6;
                }

                String var7 = "" + this.m_dbInfo.getActiveDBSchemaMajorVersion();
                String var8 = "" + this.m_dbInfo.getActiveDBSchemaMinorVersion();
                if (var4[1] < 10) {
                    var8 = "0" + var8;
                }

                String var9 = "";
                var9 = "This build requires schema " + var5 + "." + var6 + ", you have " + var7 + "." + var8 + "." + System.getProperty("line.separator") + "Please contact your system administrator to migrate the database.";
                String var10 = "Schema Error";
                String var11 = this.getFormFactor();
                if (var11 != null && var11.equals("M")) {
                    DlgMobileMessage var12 = new DlgMobileMessage(this.m_frame, var10, var9);
                    var12.show();
                    var12.dispose();
                } else {
                    JOptionPane.showMessageDialog(this.m_frame, var9, var10, 0);
                }

                System.exit(0);
            }

            this.m_timeout = this.m_dbInfo.getClientTimeout();
            this.setupSession(this.getFactoryOwner().isLoggedIn());
            if (this.m_dbInfo != null) {
                EventLog.setConsolidatedLogEnabled(this.m_dbInfo.isEnableConsolidatedLogging());
            }
        } catch (Throwable var13) {
            EventLog.dispatchException(var13, this, "connect(" + var1 + "," + var2 + "," + var3 + ")");
        }

    }

    public void currentObjectChanged(CurrentObjectEvent var1) {
        if ("User".equals(var1.getCurrentClass())) {
            this.setupButton((User)var1.getValue());
            this.setupSession(this.getFactoryOwner().isLoggedIn());
        } else if ("Form".equals(var1.getCurrentClass())) {
            if (var1.getValue() == null) {
                this.m_frame.setTitle("操作客户端");//TODO 修改国际化
            }

            this.miPrint.setEnabled(var1.getValue() != null);
            this.miClose.setEnabled(var1.getValue() != null);
        }

    }

    private void doAbout() {
        DDBInfo var1 = null;
        String var2 = null;

        try {
            var2 = this.getFactoryOwner().getActiveProtocol();
            var1 = this.m_dbInfo.getDDBInfo();
        } catch (Throwable var5) {
        }

        Object var3 = null;
        String var4 = this.getFormFactor();
        if (var4 != null && var4.equals("N")) {
            var3 = new DlgAbout(this.m_frame, "Shop Operations", var1, var2);
        } else if (var4 != null && var4.equals("T")) {
            var3 = new DlgAbout(this.m_frame, "Shop Operations", var1, var2);
        } else if (var4 != null && var4.equals("M")) {
            var3 = new DlgMobileAbout(this.m_frame, "Shop Operations", var1, var2);
        }

        ((DlgAbout)var3).showDialog();
        ((DlgAbout)var3).dispose();
    }

    private void doChangePassword() {
        User var1 = this.getFactoryOwner().getUserManager().getCurrentUser();
        this.doChangePassword(var1 != null ? var1.getName() : null);
    }

    private void doChangePassword(String var1) {
        DlgChangePassword var2 = new DlgChangePassword(this.m_frame, var1);

        try {
            if (var2.showDialog()) {
                String[] var3 = var2.getResult();
                boolean var4 = this.getFactoryOwner().isLoggedIn();
                this.setCursor(Cursor.getPredefinedCursor(3));
                this.getEnvironment()._changePassword(var3[0], var3[1], var3[2]);
                if (var4) {
                    this.getFactoryOwner().logout();
                    this.getFactoryOwner().login(var3[0], var3[2]);
                }

                this.setCursor(Cursor.getPredefinedCursor(0));
            }
        } catch (Throwable var8) {
            this.setCursor(Cursor.getPredefinedCursor(0));
            EventLog.dispatchException(var8, this, "doChangePassword()");
        } finally {
            var2.dispose();
        }

    }

    private void doChangeUser() {
        String var1 = this.getFormFactor();
        if (var1 == null) {
            var1 = this.doMode();
        }

        Object var2 = null;
        if (var1.equals("N")) {
            var2 = new DlgLogon(this.m_frame);
        } else if (var1.equals("T")) {
            var2 = new DlgTouchLogon(this.m_frame);
        } else if (var1.equals("M")) {
            var2 = new DlgMobileLogon(this.m_frame);
        }

        String[] var3 = ((DlgLogon)var2).showDialog() ? ((DlgLogon)var2).getResult() : null;
        ((DlgLogon)var2).dispose();
        if (var3 != null) {
            if (this.getFactoryOwner().isLoggedIn()) {
                User var4 = this.getFactoryOwner().getUserManager().getCurrentUser();
                if (var4 != null && var3[0].equals(var4.getName())) {
                    String var5 = this.getLogonMsg("0426", (Object[])null);
                    String var6 = this.getLogonMsg("0425", new Object[]{var3[0]});
                    if (var1 != null && var1.equals("M")) {
                        DlgMobileMessage var7 = new DlgMobileMessage(this.m_frame, var5, var6);
                        var7.show();
                        var7.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this.m_frame, var6, var5, 0);
                    }

                    return;
                }
            }

            try {
                this.setCursor(Cursor.getPredefinedCursor(3));
                this.getEnvironment()._changeUser(var3[0], var3[1]);
                this.setCursor(Cursor.getPredefinedCursor(0));
            } catch (Throwable var8) {
                this.setCursor(Cursor.getPredefinedCursor(0));
                EventLog.dispatchException(var8, this, "doChangeUser()");
            }
        }

    }

    private void doClose() {
        this.setCursor(Cursor.getPredefinedCursor(3));

        try {
            this.getEnvironment().closeForm();
            if (this.m_frame.getTitle() == null || this.m_frame.getTitle().trim() == "") {
                this.m_frame.setTitle("操作客户端");//TODO 修改国际化
            }

            this.miLocale.setEnabled(this.m_environment.isShowLocale());
        } catch (Exception var2) {
            EventLog.dispatchException(var2, this, "doClose()");
        }

        this.setCursor(Cursor.getPredefinedCursor(0));
    }

    private void doLocale() {
        Locale var1 = this.getFactoryOwner().getMessagesManager().getTestLocale();
        String var2 = this.getFormFactor();
        Object var3 = null;
        Vector var4 = this.getJavaLocalesFromDB();
        if (var2 != null && var2.equals("N")) {
            var3 = new DlgLocale(this.m_frame, var1 != null ? var1 : Locale.getDefault(), var4);
        } else if (var2 != null && var2.equals("T")) {
            var3 = new DlgLocale(this.m_frame, var1 != null ? var1 : Locale.getDefault(), var4);
        } else if (var2 != null && var2.equals("M")) {
            var3 = new DlgMobileLocale(this.m_frame, var1 != null ? var1 : Locale.getDefault(), var4);
        }

        if (((DlgLocale)var3).showDialog()) {
            Locale var5 = ((DlgLocale)var3).getResult();
            if (var5 != null) {
                this.m_environment.switchLocale(var5);
                if (this.m_environment != null) {
                    this.m_environment.propagateLocale(var5);
                    this.m_environment.addLocale(var5);
                }

                String var6 = this.getFactoryOwner().getUserManager().getCurrentUser().getName();
                this.m_options.set("user." + var6, var5.getLanguage() + "-" + var5.getCountry() + "-" + var5.getVariant());
            }

            this.setupButton();
        }

        ((DlgLocale)var3).dispose();
    }

    private Vector getJavaLocalesFromDB() {
        Vector var1 = null;

        try {
            var1 = this.getFactoryOwner().getLocaleManager().getJavaLocales();
        } catch (DatasweepException var3) {
            EventLog.dispatchException(var3, this, "getJavaLocalesFromDB");
        }

        return var1;
    }

    private void doLogoff() {
        try {
            this.setCursor(Cursor.getPredefinedCursor(3));
            this.getEnvironment()._logoff();
            this.getFactoryOwner().reSetLoginInfo();
            this.miLocale.setEnabled(this.m_environment.isShowLocale());
            this.setCursor(Cursor.getPredefinedCursor(0));
        } catch (Throwable var2) {
            this.setCursor(Cursor.getPredefinedCursor(0));
            EventLog.dispatchException(var2, this, "doLogoff()");
        }

    }

    public void initializeFormFactor() {
        Dimension var1 = Toolkit.getDefaultToolkit().getScreenSize();
        if (var1.width < FormEnvironment.MOBILE_TRIGGER_WIDTH) {
            String var2 = this.m_options.get("FormFactor");
            if (var2 == null || var2.length() == 0) {
                this.m_frame.setSize(FormEnvironment.MOBILE_WIDTH, FormEnvironment.MOBILE_HEIGHT);
                this.setFormFactor("M");
            }
        }

    }

    public void setFormFactor(String var1) {
        this.m_options.set("FormFactor", var1);
        this.getEnvironment().setFormFactor(var1);
    }

    public String getFormFactor() {
        String var1 = null;
        if (this.m_options != null) {
            var1 = this.m_options.get("FormFactor");
        }

        if (var1 == null || !"M".equals(var1) && !"N".equals(var1) && !"T".equals(var1)) {
            var1 = "N";
            if (this.m_options != null) {
                this.setFormFactor(var1);
            }
        }

        if (!var1.equals(this.getEnvironment().getFormFactor())) {
            this.getEnvironment().setFormFactor(var1);
        }

        return var1;
    }

    private void doLogon() {
        if (SwingUtilities.isEventDispatchThread()) {
            this._doLogon();
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        Runtime.this._doLogon();
                    }
                });
            } catch (InvocationTargetException var2) {
                EventLog.logException(var2, this, "doLogon() in Event Dispatch Thread throw InvocationTargetException: " + var2.getStackTrace());
            } catch (InterruptedException var3) {
                EventLog.logException(var3, this, "doLogon() in Event Dispatch Thread throw InterruptedException: " + var3.getStackTrace());
            }
        }

    }

    private void _doLogon() {
        String var1 = this.getFormFactor();
        Object var2 = null;
        JFrame var3 = null;
        if (var1 != null && var1.equals("N")) {
            var3 = new JFrame();
            var3.setIconImage((new ImageIcon(Runtime.class.getResource("Runtime.gif"))).getImage());
            var3.setUndecorated(true);
            var3.setVisible(true);
            var3.setLocationRelativeTo((Component)null);
            var2 = new DlgLogon(var3);
            ((DlgLogon)var2).setModalityType(ModalityType.TOOLKIT_MODAL);
        } else if (var1 != null && var1.equals("T")) {
            var2 = new DlgTouchLogon(this.m_frame);
        } else if (var1 != null && var1.equals("M")) {
            var2 = new DlgMobileLogon(this.m_frame);
        }

        String[] var4 = null;
        boolean var5 = false;
        int var6 = 3;

        try {
            String var8;
            String var9;
            DlgMobileMessage var10;
            try {
                while(((DlgLogon)var2).showDialog()) {
                    var4 = ((DlgLogon)var2).getResult();
                    if (this.getFactoryOwner().isLoggedIn()) {
                        User var7 = this.getFactoryOwner().getUserManager().getCurrentUser();
                        if (var7 != null && var4[0].equals(var7.getName())) {
                            var8 = this.getLogonMsg("0426", (Object[])null);
                            var9 = this.getLogonMsg("0425", new Object[]{var4[0]});
                            if (var1 != null && var1.equals("M")) {
                                var10 = new DlgMobileMessage(this.m_frame, var8, var9);
                                var10.show();
                                var10.dispose();
                                break;
                            }

                            JOptionPane.showMessageDialog(this.m_frame, var9, var8, 0);
                            break;
                        }
                    }

                    var5 = this.doLogon(var4[0], var4[1], var6);
                    if (var5) {
                        break;
                    }

                    --var6;
                    if (var6 <= 0) {
                        break;
                    }
                }

                if (var6 > 1) {
                    return;
                }

                JOptionPane.showMessageDialog(this.m_frame, MessagesUtil.getMessageByKey("loginfailed.disableorexpired"), "Logon Message", 0);
            } catch (Runtime.PasswordExpiredException var14) {
                var8 = this.getLogonMsg("8023", (Object[])null);
                var9 = this.getLogonMsg("0426", (Object[])null);
                if (var1 != null && var1.equals("M")) {
                    var10 = new DlgMobileMessage(this.m_frame, var9, var8);
                    var10.show();
                    var10.dispose();
                } else {
                    JOptionPane.showMessageDialog(this.m_frame, var8, var9, 0);
                }

                this.doChangePassword(var4[0]);
                return;
            }
        } finally {
            if (null != var3) {
                var3.dispose();
            }

            ((DlgLogon)var2).dispose();
        }

    }

    private String getLogonMsg(String var1, Object[] var2) {
        DsMessages var3 = null;

        try {
            var3 = (DsMessages)this.getFactoryOwner().getMessagesManager().getSystemErrorMessages();
            String var4 = var3.getValue(var1);
            if (var4 == null) {
                var4 = var3.getValue(var1, Locale.US);
            }

            if (var2 != null) {
                return var4 != null ? MessageFormat.format(var4, var2) : null;
            } else {
                return var4;
            }
        } catch (DatasweepException var5) {
            return "Can not get the message from database.";
        }
    }

    protected boolean doLogon(String var1, String var2) throws Runtime.PasswordExpiredException {
        return this.doLogon(var1, var2, 9999);
    }

    protected boolean doLogon(String var1, String var2, int var3) throws Runtime.PasswordExpiredException {
        try {
            this.getEnvironment()._logon(var1, var2, true);
            this.setCursor(Cursor.getPredefinedCursor(3));
            this.getFactoryOwner().getMessagesManager().getSystemErrorMessages();
            this.m_dbInfo = new DBInfo(this.m_factoryOwner.getProxyFactory().getSystemProxy().getDBInfo((DReadInfo)null));
            if (this.m_options.get("user." + var1) != null) {
                this.setLocale(this.m_options.get("user." + var1));
            }

            this.miLocale.setEnabled(this.m_environment.isShowLocale());
            this.setCursor(Cursor.getPredefinedCursor(0));
            if (this.getFactoryOwner().isLoggedIn()) {
                User var4 = this.getFactoryOwner().getUserManager().getCurrentUser();
                String var5 = this.getLogonMsg("0426", (Object[])null);
                if (this.getDBInfo().isSecurityProviderIsDatasweep() && var4 != null && var4.isPasswordModifiable()) {
                    String var6;
                    if (var4.getStatus().equals("ChangePassword")) {
                        var6 = "You are required to change your password now.";
                        JOptionPane.showConfirmDialog(this.m_frame, var6, var5, -1);
                        this.doChangePassword(var4.getName());
                    } else if (Time.compare(var4.getPasswordExpiration(), (new Time()).addDays(7)) <= 0) {
                        var6 = this.getLogonMsg("8009", new Object[]{var4.getPasswordExpiration().formatShortDate()});
                        int var7 = JOptionPane.showConfirmDialog(this.m_frame, var6, var5, 0);
                        if (var7 == 0) {
                            this.doChangePassword(var4.getName());
                        }
                    }
                } else if (this.getDBInfo().isSecurityProviderIsDatasweep() && var4 != null && !var4.isPasswordModifiable()) {
                    if (var4.getStatus().equals("ChangePassword")) {
                        JOptionPane.showMessageDialog(this.m_frame, MessagesUtil.getMessageByKey("loginfailed.disableorexpired"), "Logon Message", 0);
                    } else if (Time.compare(var4.getPasswordExpiration(), (new Time()).addDays(7)) <= 0) {
                        JOptionPane.showMessageDialog(this, "Your password will expire on " + var4.getPasswordExpiration().getCalendar().getTime().toString() + ". Please contact your system administrator for assistance.", "Logon Message", 0);
                    }
                }

                if (this.getDBInfo().isSecurityProviderIsDatasweep() && var4 != null && var4.getStatus().equals("ChangePassword")) {
                    this.setCursor(Cursor.getPredefinedCursor(0));
                    this.getFactoryOwner().logout();
                    return false;
                }

                if (!var4.hasPrivilege("ShopOperations")) {
                    this.getEnvironment().logoff();
                    this.setCursor(Cursor.getPredefinedCursor(0));
                    JOptionPane.showMessageDialog(this, "You do not have ShopOperations privileges." + System.getProperty("line.separator") + "The system could not log you on to Shop Operations.", "Insufficient Privilege", 0);
                }
            }
        } catch (Throwable var8) {
            this.setCursor(Cursor.getPredefinedCursor(0));
            if (this.isPasswordExpired(var8)) {
                throw new Runtime.PasswordExpiredException();
            }

            if (var3 > 1) {
                if (var8 instanceof DatasweepException) {
                    EventLog.dispatchException(var8, this, var8.getMessage());
                } else {
                    EventLog.dispatchException(var8, this, "doLogon(" + var1 + ", ...)");
                }
            }
        }

        return true;
    }

    private void doOpen() {
        String var1 = this.getFormFactor();
        DlgOpen var2 = new DlgOpen(this.m_frame, this.getFactoryOwner().getFormManager(), var1);
        String var3 = var2.showDialog() ? var2.getResult() : null;
        var2.dispose();
        if (var3 != null) {
            try {
                this.getEnvironment().showAsForm(var3);
                if (this.m_frame.getTitle() == null || this.m_frame.getTitle().trim() == "") {
                    this.m_frame.setTitle("操作客户端");//TODO 修改国际化
                }

                this.miLocale.setEnabled(this.m_environment.isShowLocale());
            } catch (Throwable var5) {
                EventLog.dispatchException(var5, this, "doOpen(" + var3 + ")");
            }
        }

    }

    private void doOptions() {
        try {
            List var1 = Utility.sort(this.getAllStationNames());
            var1.add(0, (Object)null);
            JComboBox var2 = new JComboBox(Utility.toArray(String.class, var1));
            var2.setSelectedItem(this.m_options.get("Station"));
            JCheckBox var3 = new JCheckBox(UIConstants.disableAutoTimeoutText, this.m_disableAutoTimeout);
            var3.setBorder(BorderFactory.createEmptyBorder(11, 0, 11, 0));
            String var4 = this.getFormFactor();
            int var5 = 0;
            if (var4 != null && var4.equals("M")) {
                Object[] var6 = new Object[]{UIConstants.stationText, var2, var3, UIConstants.inputText};
                DlgMobileOptions var7 = new DlgMobileOptions(this.m_frame, var6);
                var5 = var7.showDialog();
                var7.dispose();
            } else if (var4 != null) {
                var5 = JOptionPane.showOptionDialog(this.m_frame, new Object[]{UIConstants.stationText, var2, var3}, UIConstants.inputText, 2, 3, (Icon)null, (Object[])null, (Object)null);
            }

            if (var5 == 0) {
                String var9 = (String)var2.getSelectedItem();
                this.m_options.set("Station", var9);
                this.getEnvironment().setStation(var9);
                this.m_disableAutoTimeout = var3.isSelected();
                this.m_options.set("DisableAutoTimeout", Boolean.toString(this.m_disableAutoTimeout));
                this.setupSession(this.getFactoryOwner().isLoggedIn());
            }
        } catch (Throwable var8) {
            EventLog.dispatchException(var8, this, "doOptions()");
        }

    }

    private String doMode() {
        DlgSelectMode var1 = new DlgSelectMode(this.m_frame);
        String var2 = var1.showDialog();
        var1.dispose();
        String var3 = this.getFormFactor();
        if (var3 == null || !var3.equals(var2)) {
            this.setFormFactor(var2);
        }

        return var2;
    }

    private void doPrint() {
        FormPrinter var1 = new FormPrinter(this.m_frame, false);
        var1.print();
    }

    private void doOutputWindow(boolean var1) {
        if (this.console == null) {
            this.console = new Runtime.OutputWindow();
            this.console.addComponentListener(new ComponentAdapter() {
                public void componentHidden(ComponentEvent var1) {
                    if (Runtime.this.m_environment != null) {
                        Runtime.this.m_environment.setOutputStream((Object)null);
                        Runtime.this.m_environment.setErrorStream((Object)null);
                    }

                    Runtime.this.miOutputWindow.setSelected(false);
                }
            });
        }

        this.console.setVisible(var1);
        if (this.m_environment != null) {
            this.m_environment.setOutputStream(var1 ? this.console.panel.getOut() : null);
            this.m_environment.setErrorStream(var1 ? this.console.panel.getErr() : null);
        }

    }

    private void doTiming(boolean var1) {
        if (this.m_environment != null) {
            this.m_environment.setCollectTiming(var1);
            if (!var1) {
                ArrayList var2 = this.m_environment.getTimingData();
                TimingData.displayResults(var2);
            }
        }

    }

    private void doStatusBar(boolean var1) {
        this.getEnvironment().setStatusBarVisible(var1);
    }

    private void doTopics() {
        try {
            String var1 = this.getFactoryOwner().getServerInfo().getDownloadsURL();
            if (var1 != null && !var1.equalsIgnoreCase("null")) {
                URL var2 = new URL(var1);
                String var3 = var2.toString() + "/docs/help/shopops/shopops%2Ehtm";
                BrowserControl.displayURL(var3);
            } else {
                JOptionPane.showMessageDialog(this, "The Help system has not been configured." + System.getProperty("line.separator") + "Please contact the Administrator.", "Error", 0);
            }
        } catch (Throwable var4) {
            JOptionPane.showMessageDialog(this, "The Help system has not been configured correctly." + System.getProperty("line.separator") + "Please contact the Administrator.", "Error", 0);
            EventLog.dispatchException(var4, this, "doTopics()");
        }

    }

    private void doViewLog() {
        try {
            File var1 = EventLog.getLogFile();
            if (var1 != null) {
                if (BrowserControl.isWindowsPlatform()) {
                    BrowserControl.displayURL("file://" + var1.getAbsolutePath());
                } else {
                    BrowserControl.displayURL(var1.toURL().toString());
                }
            }
        } catch (Throwable var2) {
            EventLog.logException(var2, this, "doViewLog()");
        }

    }

    private List getAllStationNames() throws Throwable {
        StationFilter var1 = new StationFilter(this.getFactoryOwner());
        var1.setDistinctOnly(true);

        try {
            return this.getFactoryOwner().getStationManager().getObjectAttributes(new String[]{"STATION.station"}, var1);
        } catch (Throwable var3) {
            EventLog.logException(var3, this, "getAllStationNames()");
            throw var3;
        }
    }

    private boolean isPasswordExpired(Throwable var1) {
        if (var1 instanceof DatasweepException) {
            try {
                String var2 = ((DatasweepException)var1).getResponse().getErrors()[0].getMessageId();
                if ("0262".equals(var2)) {
                    return true;
                }
            } catch (Throwable var3) {
            }
        }

        return false;
    }

    private boolean isPasswordComplexityException(Throwable var1) {
        if (var1 instanceof DatasweepException) {
            try {
                Error[] var2 = ((DatasweepException)var1).getResponse().getErrors();

                for(int var3 = 0; var3 < var2.length; ++var3) {
                    String var4 = var2[var3].getMessageId();
                    if ("8012".equals(var4)) {
                        return true;
                    }
                }
            } catch (Throwable var5) {
            }
        }

        return false;
    }

    public void onEvaluateScript(EvaluateScriptEvent var1) {
        Object var2 = var1.getSource();
        if (!(var2 instanceof ObjectBinder) && !(var2 instanceof SerialPort) && !(var2 instanceof SocketControl) && !(var2 instanceof com.datasweep.compatibility.ui.Timer)) {
            this.m_sessionExpiration = (new Time()).addMinutes((long)this.m_timeout);
        }

    }

    public void onExceptionEvent(ExceptionEvent var1) {
        Response var3;
        String var4;
        if (var1.getSource() != this && var1.getSource() != SessionMonitor.class) {
            if (!(var1.getSource() instanceof DlgOpen) || !(var1.getThrowable() instanceof DatasweepException)) {
                return;
            }

            String var2 = "is required to change password";
            var3 = ((DatasweepException)var1.getThrowable()).getResponse();
            var4 = var3.getFirstErrorMessage();
            if (!var4.contains(var2)) {
                return;
            }
        }

        Throwable var6 = var1.getThrowable();
        var3 = null;
        String var7;
        if (var6 instanceof DatasweepException) {
            if (this.isPasswordComplexityException(var6)) {
                Response var8 = ((DatasweepException)var6).getResponse();
                var7 = var8.getPasswordComplexityErrorMessage();
            } else {
                var7 = this.format((DatasweepException)var6);
            }
        } else if (var6 instanceof PnutsException) {
            var7 = this.format((PnutsException)var6);
        } else {
            var7 = var6.getMessage();
        }

        if (var7 == null || var7.length() == 0) {
            var7 = var6.toString();
        }

        if (var7 == null || var7.length() == 0) {
            var7 = var6.getClass().getName();
        }

        var4 = this.getFormFactor();
        if (var4 != null && !var4.equals("M")) {
            MessageBox.show(this.m_frame, var7, Utility.stripClassName(var6.getClass().getName()), 0);
        } else {
            DlgMobileMessage var5 = new DlgMobileMessage(this.m_frame, UIConstants.errorText, var7);
            var5.show();
            var5.dispose();
        }

    }

    protected String format(PnutsException var1) {
        String var2 = var1.getMessage();
        if (var2 != null && var2.length() > 0) {
            return var2;
        } else {
            var2 = var1.toString();
            if (var1.getThrowable() instanceof DatasweepException) {
                int var3 = var2.indexOf(":\n");
                if (var3 > 0) {
                    return var2.substring(0, var3) + ":" + System.getProperty("line.separator") + this.format((DatasweepException)var1.getThrowable());
                }
            }

            return var2;
        }
    }

    protected String format(DatasweepException var1) {
        Response var2 = var1.getResponse();
        Error[] var3 = var2.getErrors();
        StringBuffer var4 = new StringBuffer();

        for(int var5 = 0; var5 < var3.length; ++var5) {
            var4.append(var3[var5].getLocalizedErrorMessage() + System.getProperty("line.separator"));
        }

        return var4.toString();
    }

    private void logSessionTimeout() {
        try {
            this.getFactoryOwner().getUtilityManager().createAppLogEntry("Session Timeout", InetAddress.getLocalHost().getHostName(), (AccessPrivilege)null);
        } catch (Exception var2) {
            EventLog.logException(var2, this, "logSessionTimeout()");
        }

    }

    private void sessionTimer() {
        Time var1 = new Time();
        if (Time.compare(var1, this.m_sessionExpiration) > 0) {
            this.m_closeExpiration = var1.addSeconds(5L);
            this.closeTimer.start();
        }

    }

    private void setupButton(User var1) {
        boolean var2 = this.getDBInfo() != null && this.getDBInfo().isSecurityProviderIsDatasweep();
        if (var1 == null) {
            this.miOpen.setEnabled(false);
            this.miClose.setEnabled(false);
            this.miOptions.setEnabled(false);
            this.miTiming.setEnabled(false);
            this.miLogoff.setEnabled(false);
            this.miChangeUser.setEnabled(false);
            this.miChangePassword.setEnabled(false);
            this.miLocale.setEnabled(false);
            this.miStatusBar.setEnabled(false);
        } else {
            this.miOpen.setEnabled(var1.hasPrivilege("OpenCloseForm"));
            this.miOptions.setEnabled(var1.hasPrivilege("RuntimeOptions"));
            this.miTiming.setEnabled(var1.hasPrivilege("RuntimeOptions"));
            this.miLogoff.setEnabled(true);
            this.miChangeUser.setEnabled(true);
            this.miChangePassword.setEnabled(var2 && var1.isPasswordModifiable());
            this.miLocale.setEnabled(true);
            this.miStatusBar.setEnabled(true);
        }

        this.setupButton();
    }

    private void setupButton() {
        DsMessages var1 = null;

        try {
            var1 = (DsMessages)this.getFactoryOwner().getMessagesManager().getUIMessages();
        } catch (Throwable var3) {
        }

        if (var1 != null) {
            String var2 = var1.getValue("SHOP_OPERATIONS");
            if (var2 != null && this.m_frame.getTitle() != null && this.m_frame.getTitle().equals("SHOP_OPERATIONS")) {
                this.m_frame.setTitle("操作客户端");//TODO 修改国际化
            }

            FormEnvironment.setupMenuItem(this.menuFile, var1, "FILE", false);
            FormEnvironment.setupMenuItem(this.miOpen, var1, "OPEN", true);
            FormEnvironment.setupMenuItem(this.miClose, var1, "CLOSE", false);
            FormEnvironment.setupMenuItem(this.miPrint, var1, "PRINT", true);
            FormEnvironment.setupMenuItem(this.miOptions, var1, "OPTIONS", true);
            FormEnvironment.setupMenuItem(this.miTiming, var1, "TIME_SCRIPTS", false);
            FormEnvironment.setupMenuItem(this.miMode, var1, "FORM_FACTOR", true);
            FormEnvironment.setupMenuItem(this.miLogon, var1, "LOGON", true);
            FormEnvironment.setupMenuItem(this.miLogoff, var1, "LOGOFF", false);
            FormEnvironment.setupMenuItem(this.miChangeUser, var1, "CHANGE_USER", true);
            FormEnvironment.setupMenuItem(this.miChangePassword, var1, "CHANGE_PASSWORD", true);
            FormEnvironment.setupMenuItem(this.miExit, var1, "EXIT", false);
            FormEnvironment.setupMenuItem(this.menuView, var1, "VIEW", false);
            FormEnvironment.setupMenuItem(this.miOutputWindow, var1, "OUTPUT_WINDOW", false);
            FormEnvironment.setupMenuItem(this.miStatusBar, var1, "STATUS_BAR", false);
            FormEnvironment.setupMenuItem(this.miLocale, var1, "LOCALE", true);
            FormEnvironment.setupMenuItem(this.miViewLog, var1, "VIEW_LOG", false);
            FormEnvironment.setupMenuItem(this.menuHelp, var1, "HELP", false);
            FormEnvironment.setupMenuItem(this.miTopics, var1, "HELP_TOPICS", false);
            FormEnvironment.setupMenuItem(this.miAbout, var1, "ABOUT_SHOP_OPERATIONS", false);
        }

    }

    private void setupSession(boolean var1) {
        if (this.m_timeout > 0 && var1 && !this.m_disableAutoTimeout) {
            this.m_environment.addEvaluateScriptEventListener(this);
            this.m_sessionExpiration = (new Time()).addMinutes((long)this.m_timeout);
            this.sessionTimer.start();
        } else {
            this.m_environment.removeEvaluateScriptEventListener(this);
            this.m_sessionExpiration = null;
            this.sessionTimer.stop();
        }

    }

    private void shutdownFormEnvironment() {
        if (this.m_environment != null) {
            this.m_environment.shutdown();
        }

    }

    private void Runtime_closing() {
        if (this.m_options.isChanged()) {
            this.functions.setOptionsSetting(this.m_options);
        }

        if (this.getFactoryOwner() != null && this.getFactoryOwner().isLoggedIn()) {
            this.setCursor(Cursor.getPredefinedCursor(3));

            try {
                this.getFactoryOwner().logout();
            } catch (Throwable var2) {
                EventLog.logException(var2, this, "Runtime_closing()");
            }

            this.setCursor(Cursor.getPredefinedCursor(0));
        }

        System.exit(0);
    }

    private void initForm() {
        String var1 = System.getProperty("uiDefaultButtonFollowFocus", "false");
        if (var1.equalsIgnoreCase("true")) {
            UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
        }

        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        this.miOpen.setAccelerator(KeyStroke.getKeyStroke(79, 2));
        this.miOpen.setMnemonic(79);
        this.miOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.doOpen();
            }
        });
        this.miClose.setAccelerator(KeyStroke.getKeyStroke(76, 2));
        this.miClose.setMnemonic(76);
        this.miClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.doClose();
            }
        });
        this.miPrint.setAccelerator(KeyStroke.getKeyStroke(80, 2));
        this.miPrint.setMnemonic(80);
        this.miPrint.setEnabled(false);
        this.miPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.doPrint();
            }
        });
        this.miOptions.setAccelerator(KeyStroke.getKeyStroke(73, 2));
        this.miOptions.setMnemonic(73);
        this.miOptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.doOptions();
            }
        });
        this.miTiming.setMnemonic(84);
        this.miTiming.setSelected(false);
        this.miTiming.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.doTiming(Runtime.this.miTiming.isSelected());
            }
        });
        this.miMode.setAccelerator(KeyStroke.getKeyStroke(82, 2));
        this.miMode.setMnemonic(82);
        this.miMode.setSelected(false);
        this.miMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.doMode();
            }
        });
        this.miLogon.setAccelerator(KeyStroke.getKeyStroke(71, 2));
        this.miLogon.setMnemonic(71);
        this.miLogon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.doLogon();
            }
        });
        this.miLogoff.setMnemonic(70);
        this.miLogoff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.doLogoff();
            }
        });
        this.miChangeUser.setAccelerator(KeyStroke.getKeyStroke(85, 2));
        this.miChangeUser.setMnemonic(85);
        this.miChangeUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.doChangeUser();
            }
        });
        this.miChangePassword.setMnemonic(67);
        this.miChangePassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.doChangePassword();
            }
        });
        this.miExit.setMnemonic(88);
        this.miExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.shutdownFormEnvironment();
                Runtime.this.Runtime_closing();
            }
        });
        this.miOutputWindow.setMnemonic(87);
        this.miOutputWindow.setSelected(false);
        this.miOutputWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.doOutputWindow(Runtime.this.miOutputWindow.isSelected());
            }
        });
        this.miStatusBar.setMnemonic(66);
        this.miStatusBar.setSelected(false);
        this.miStatusBar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.doStatusBar(Runtime.this.miStatusBar.isSelected());
            }
        });
        this.miLocale.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.doLocale();
            }
        });
        this.miViewLog.setAccelerator(KeyStroke.getKeyStroke(76, 3));
        this.miViewLog.setVisible(false);
        this.miViewLog.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.doViewLog();
            }
        });
        this.miTopics.setAccelerator(KeyStroke.getKeyStroke(112, 0));
        this.miTopics.setMnemonic(72);
        this.miTopics.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.doTopics();
            }
        });
        this.miAbout.setMnemonic(65);
        this.miAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Runtime.this.doAbout();
            }
        });
        this.menuFile.setMnemonic(70);
        this.menuFile.add(this.miOpen);
        this.menuFile.add(this.miClose);
        this.menuFile.addSeparator();
        this.menuFile.add(this.miPrint);
        this.menuFile.add(this.miOptions);
        this.menuFile.add(this.miTiming);
        this.menuFile.add(this.miMode);
        this.menuFile.addSeparator();
        this.menuFile.add(this.miLogon);
        this.menuFile.add(this.miLogoff);
        this.menuFile.add(this.miChangeUser);
        this.menuFile.add(this.miChangePassword);
        this.menuFile.addSeparator();
        this.menuFile.add(this.miExit);
        this.menuView.setMnemonic(86);
        this.menuView.add(this.miOutputWindow);
        this.menuView.addSeparator();
        this.menuView.add(this.miStatusBar);
        this.menuView.addSeparator();
        this.menuView.add(this.miLocale);
        this.menuView.add(this.miViewLog);
        this.menuHelp.setMnemonic(72);
        this.menuHelp.add(this.miTopics);
        this.menuHelp.addSeparator();
        this.menuHelp.add(this.miAbout);
        this.menuBar.add(this.menuFile);
        this.menuBar.add(this.menuView);
        this.menuBar.add(this.menuHelp);
        this.setLayout(new BorderLayout());
        this.add(this.statusBar, "South");
    }

    protected void loadClientActivities() {
        ActivityList.setJarLocation(ActivityList.SOJAR);
        ActivityList.loadClientActivities(this.getFactoryOwner());
    }

    private void intializeSettings() {
        this.functions = new FunctionsImpl(this.getEnvironment().getFunctionContext());
        this.m_options = (OptionsSetting)this.functions.getOptionsSetting().getResult();
        FormEnvironment.optionSetting = this.m_options;
        this.m_disableAutoTimeout = Boolean.valueOf(this.m_options.get("DisableAutoTimeout"));
        this.m_environment.setStation(this.m_options.get("Station"));
    }

    public static void main(String[] var0) {
        if (SwingUtilities.isEventDispatchThread()) {
            try {
                PlasticLookAndFeel.setMyCurrentTheme(new DatasweepPlasticTheme());
                PlasticLookAndFeel.setTabStyle("metal");
                PlasticXPLookAndFeel var1 = new PlasticXPLookAndFeel();
                UIManager.setLookAndFeel(var1);
            } catch (Throwable var20) {
            }
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        try {
                            PlasticLookAndFeel.setMyCurrentTheme(new DatasweepPlasticTheme());
                            PlasticLookAndFeel.setTabStyle("metal");
                            PlasticXPLookAndFeel var1 = new PlasticXPLookAndFeel();
                            UIManager.setLookAndFeel(var1);
                        } catch (Throwable var2) {
                        }

                    }
                });
            } catch (InvocationTargetException var18) {
                EventLog.logException(var18, Runtime.class, "main() in Event Dispatch Thread throw InvocationTargetException: " + var18.getStackTrace());
            } catch (InterruptedException var19) {
                EventLog.logException(var19, Runtime.class, "main() in Event Dispatch Thread throw InterruptedException: " + var19.getStackTrace());
            }
        }

        final JFrame var22 = new JFrame("Shop Operations");
        final SplashScreen var2 = new SplashScreen(var22, "runtime.jpg");
        final Runtime var3 = new Runtime(var22);
        String var4 = "";
        if (var0 != null) {
            if (var0.length != 3 && var0.length != 4 && var0.length != 5) {
                String var23 = "\nUSAGE: <iiopURL> <httpURL>";
                var23 = var23 + "\n IIOP URL Format: iiop://<hostName>:<port>";
                var23 = var23 + "\n HTTP URL Format: http://<hostName>:<port> or https://<hostName>:<port>";
                var23 = var23 + "\n Help Server Format: http://<hostName>:<port> or file:///C:/PlantOpsDownloads";
                throw new RuntimeException("Incorrect parameters:" + var23);
            }

            if (var0.length == 4) {
                EventLog.setLogLevel(var0[3]);
            }

            if (SwingUtilities.isEventDispatchThread()) {
                var2.setVisible(false);
            } else {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            var2.setVisible(false);
                        }
                    });
                } catch (InvocationTargetException var16) {
                    EventLog.logException(var16, Runtime.class, "logo.setVisible() in Event Dispatch Thread throw InvocationTargetException: " + var16.getStackTrace());
                } catch (InterruptedException var17) {
                    EventLog.logException(var17, Runtime.class, "logo.setVisible() in Event Dispatch Thread throw InterruptedException: " + var17.getStackTrace());
                }
            }

            var3.preconnect(var0[0], var0[1], var0[2]);
            var3.intializeSettings();
            var3.connect(var0[0], var0[1], var0[2]);

            try {
                final User var5 = var3.getFactoryOwner().getUserManager().getCurrentUser();
                if (SwingUtilities.isEventDispatchThread()) {
                    var3.setupButton(var5);
                } else {
                    try {
                        SwingUtilities.invokeAndWait(new Runnable() {
                            public void run() {
                                var3.setupButton(var5);
                            }
                        });
                    } catch (InvocationTargetException var14) {
                        EventLog.logException(var14, Runtime.class, "app.setupButton() in Event Dispatch Thread throw InvocationTargetException: " + var14.getStackTrace());
                    } catch (InterruptedException var15) {
                        EventLog.logException(var15, Runtime.class, "app.setupButton() in Event Dispatch Thread throw InterruptedException: " + var15.getStackTrace());
                    }
                }

                DeAnzaForm var6 = var5.getForm();
                if (var6 != null) {
                    Form var7 = (Form)CComponent.fromByteArray(var6.getForm());
                    var3.miLocale.setEnabled(false);
                }

                var3.refreshEnv();
            } catch (Exception var21) {
                if (SwingUtilities.isEventDispatchThread()) {
                    var3.setupButton((User)null);
                } else {
                    try {
                        SwingUtilities.invokeAndWait(new Runnable() {
                            public void run() {
                                var3.setupButton((User)null);
                            }
                        });
                    } catch (InvocationTargetException var12) {
                        EventLog.logException(var12, Runtime.class, "app.setupButton() in Event Dispatch Thread throw InvocationTargetException: " + var12.getStackTrace());
                    } catch (InterruptedException var13) {
                        EventLog.logException(var13, Runtime.class, "app.setupButton() in Event Dispatch Thread throw InterruptedException: " + var13.getStackTrace());
                    }
                }
            }

            if (var0.length == 5) {
                var4 = var0[4];
            }
        }

        if (SwingUtilities.isEventDispatchThread()) {
            var3.initializeFormFactor();
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        var3.initializeFormFactor();
                    }
                });
            } catch (InvocationTargetException var10) {
                EventLog.logException(var10, Runtime.class, "app.initializeFormFactor() in Event Dispatch Thread throw InvocationTargetException: " + var10.getStackTrace());
            } catch (InterruptedException var11) {
                EventLog.logException(var11, Runtime.class, "app.initializeFormFactor() in Event Dispatch Thread throw InterruptedException: " + var11.getStackTrace());
            }
        }

        BeanBinder.initEditors();
        if (var4.equals("DEBUGACTIVITY")) {
            ActivityList.loadClientActivitiesForDebug(var3.getFactoryOwner());
        }

        if (var22.getIconImage() == null) {
            if (SwingUtilities.isEventDispatchThread()) {
                var22.setVisible(true);
                var22.setIconImage((new ImageIcon(Runtime.class.getResource("Runtime.gif"))).getImage());
            } else {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            var22.setVisible(true);
                            var22.setIconImage((new ImageIcon(Runtime.class.getResource("Runtime.gif"))).getImage());
                        }
                    });
                } catch (InvocationTargetException var8) {
                    EventLog.logException(var8, Runtime.class, "frame.setVisible()/frame.setIconImage() in Event Dispatch Thread throw InvocationTargetException: " + var8.getStackTrace());
                } catch (InterruptedException var9) {
                    EventLog.logException(var9, Runtime.class, "frame.setVisible()/frame.setIconImage() in Event Dispatch Thread throw InterruptedException: " + var9.getStackTrace());
                }
            }
        }

    }

    private void refreshEnv() {
        String var1 = System.getProperty("user.language.format");
        if (var1 == null) {
            var1 = System.getProperty("user.language");
        }

        String var2 = System.getProperty("user.country.format");
        if (var2 == null) {
            var2 = System.getProperty("user.country");
        }

        Locale var3 = new Locale(var1, var2);
        this.m_environment.refreshUIConstants(var3);
    }

    static {
        Thread var0 = new Thread() {
            public void run() {
                System.out.println("loadLibrary jdic in Runtime ..." + Thread.currentThread().getName());
                System.loadLibrary("jdic");
            }
        };
        var0.start();
    }

    class OutputWindow extends JFrame {
        DebuggerConsole panel = new DebuggerConsole((EditorPanel)null, (DebugWindow)null, (String)null);
        JToolBar toolBar = new JToolBar();
        JButton tbClear = new Runtime.OutputWindow.ToolBarButton("Clear.gif");

        OutputWindow() {
            if (SwingUtilities.isEventDispatchThread()) {
                this.initForm();
                Dimension var2 = Toolkit.getDefaultToolkit().getScreenSize();
                Rectangle var3 = Runtime.this.m_frame.getBounds();
                Rectangle[] var4 = new Rectangle[]{new Rectangle(var3.x, 0, var3.width, var3.y), new Rectangle(0, var3.y, var3.x, var3.height), new Rectangle(var3.x, var3.y + var3.height, var3.width, Math.max(0, var2.height - var3.y - var3.height)), new Rectangle(var3.x + var3.width, var3.y, Math.max(0, var2.width - var3.x - var3.width), var3.height)};
                Rectangle var5 = var4[0];

                for(int var6 = 1; var6 < var4.length; ++var6) {
                    if (var4[var6].width * var4[var6].height >= var5.width * var5.height) {
                        var5 = var4[var6];
                    }
                }

                if (var5.width * var5.height == 0) {
                    var5 = new Rectangle(0, var3.height - 100, var3.width, 100);
                }

                this.setBounds(var5);
            } else {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            OutputWindow.this.initForm();
                            Dimension var1 = Toolkit.getDefaultToolkit().getScreenSize();
                            Rectangle var2 = Runtime.this.m_frame.getBounds();
                            Rectangle[] var3 = new Rectangle[]{new Rectangle(var2.x, 0, var2.width, var2.y), new Rectangle(0, var2.y, var2.x, var2.height), new Rectangle(var2.x, var2.y + var2.height, var2.width, Math.max(0, var1.height - var2.y - var2.height)), new Rectangle(var2.x + var2.width, var2.y, Math.max(0, var1.width - var2.x - var2.width), var2.height)};
                            Rectangle var4 = var3[0];

                            for(int var5 = 1; var5 < var3.length; ++var5) {
                                if (var3[var5].width * var3[var5].height >= var4.width * var4.height) {
                                    var4 = var3[var5];
                                }
                            }

                            if (var4.width * var4.height == 0) {
                                var4 = new Rectangle(0, var2.height - 100, var2.width, 100);
                            }

                            OutputWindow.this.setBounds(var4);
                        }
                    });
                } catch (InvocationTargetException var7) {
                    EventLog.logException(var7, Runtime.class, "OutputWindow() in Event Dispatch Thread throw InvocationTargetException: " + var7.getStackTrace());
                } catch (InterruptedException var8) {
                    EventLog.logException(var8, Runtime.class, "OutputWindow() in Event Dispatch Thread throw InterruptedException: " + var8.getStackTrace());
                }
            }

        }

        private void initForm() {
            this.tbClear.setToolTipText("Clear Console");
            this.tbClear.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent var1) {
                    OutputWindow.this.panel.clear();
                }
            });
            this.toolBar.setFloatable(false);
            this.toolBar.add(this.tbClear);
            this.getContentPane().setLayout(new BorderLayout());
            this.getContentPane().add(this.toolBar, "North");
            this.getContentPane().add(this.panel, "Center");
            this.pack();
            this.setIconImage((new ImageIcon(Runtime.class.getResource("Runtime.gif"))).getImage());
        }

        class ToolBarButton extends JButton {
            ToolBarButton(String var2) {
                super((String)null, new ImageIcon(Runtime.OutputWindow.ToolBarButton.class.getResource(var2)));
                this.setBorder(BorderFactory.createRaisedBevelBorder());
                this.setBorderPainted(false);
                this.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent var1) {
                        ((JButton)var1.getSource()).setBorderPainted(true);
                    }

                    public void mouseExited(MouseEvent var1) {
                        ((JButton)var1.getSource()).setBorderPainted(false);
                    }
                });
            }
        }
    }

    class PasswordExpiredException extends Exception {
        PasswordExpiredException() {
        }
    }

    class FactoryOwner extends ServerImpl {
        FactoryOwner(ServerInfo var2) throws Exception {
            super(var2);
            setDefaultServer(this);
        }
    }
}
