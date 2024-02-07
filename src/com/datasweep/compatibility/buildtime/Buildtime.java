//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.datasweep.compatibility.buildtime;

import com.datasweep.client.event.KeyedStatusEvent;
import com.datasweep.client.event.KeyedStatusListener;
import com.datasweep.compatibility.buildtime.DlgViewEditor.Callback;
import com.datasweep.compatibility.buildtime.ObjectCollections.TreeNodeAdapter;
import com.datasweep.compatibility.client.ATDefinition;
import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.AbstractClient;
import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.ActivitySet;
import com.datasweep.compatibility.client.ActivitySetTransition;
import com.datasweep.compatibility.client.AddOn;
import com.datasweep.compatibility.client.AddOnFilter;
import com.datasweep.compatibility.client.ApplicationItem;
import com.datasweep.compatibility.client.Carrier;
import com.datasweep.compatibility.client.ClientUtility;
import com.datasweep.compatibility.client.DBInfo;
import com.datasweep.compatibility.client.DataDictionary;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.DeAnzaForm;
import com.datasweep.compatibility.client.DlgKeyedSelector;
import com.datasweep.compatibility.client.ESignatureDefinition;
import com.datasweep.compatibility.client.Error;
import com.datasweep.compatibility.client.EventSheetHolder;
import com.datasweep.compatibility.client.FSMConfiguration;
import com.datasweep.compatibility.client.Filter;
import com.datasweep.compatibility.client.FilterSupport;
import com.datasweep.compatibility.client.FilterUtility;
import com.datasweep.compatibility.client.IAccessControl;
import com.datasweep.compatibility.client.Keyed;
import com.datasweep.compatibility.client.LibraryHolder;
import com.datasweep.compatibility.client.Locale;
import com.datasweep.compatibility.client.Location;
import com.datasweep.compatibility.client.MasterRecipe;
import com.datasweep.compatibility.client.NamedFilter;
import com.datasweep.compatibility.client.Part;
import com.datasweep.compatibility.client.ProcessBillOfMaterials;
import com.datasweep.compatibility.client.ReportDataDefinition;
import com.datasweep.compatibility.client.ReportDesign;
import com.datasweep.compatibility.client.Response;
import com.datasweep.compatibility.client.Route;
import com.datasweep.compatibility.client.STAInstance;
import com.datasweep.compatibility.client.ServerInfo;
import com.datasweep.compatibility.client.Subroutine;
import com.datasweep.compatibility.client.TemplateRecipe;
import com.datasweep.compatibility.client.UDADefinition;
import com.datasweep.compatibility.client.UnitOfMeasure;
import com.datasweep.compatibility.client.User;
import com.datasweep.compatibility.client.VPVersionConfiguration;
import com.datasweep.compatibility.client.XRefResults;
import com.datasweep.compatibility.controls.DlgAbout;
import com.datasweep.compatibility.controls.DlgChangePassword;
import com.datasweep.compatibility.controls.DlgLogon;
import com.datasweep.compatibility.controls.SplashScreen;
import com.datasweep.compatibility.diff.EventSheetSelection;
import com.datasweep.compatibility.diff.FormsSelection;
import com.datasweep.compatibility.diff.SubroutinesSelection;
import com.datasweep.compatibility.dsx.DSXExporter;
import com.datasweep.compatibility.dsx.DSXImporter;
import com.datasweep.compatibility.dsx.DSXUtilities;
import com.datasweep.compatibility.dsx.ImportTask;
import com.datasweep.compatibility.dsx.ImportUtility;
import com.datasweep.compatibility.formdesigner.CompatibilityEditor;
import com.datasweep.compatibility.manager.ObjectManager;
import com.datasweep.compatibility.pnutsfunctions.gui.DlgESignature;
import com.datasweep.compatibility.ui.Time;
import com.datasweep.core.ExceptionEvent;
import com.datasweep.core.IExceptionEventListener;
import com.datasweep.core.ISelectEvent;
import com.datasweep.core.ISelectEventListener;
import com.datasweep.core.SelectEvent;
import com.datasweep.core.eventlog.EventLog;
import com.datasweep.core.message.MessagesUtil;
import com.datasweep.core.utility.Preference;
import com.datasweep.core.utility.Utility;
import com.datasweep.plantops.common.dataobjects.DATRow;
import com.datasweep.plantops.common.dataobjects.DDBInfo;
import com.datasweep.plantops.common.dataobjects.DKeyed;
import com.datasweep.plantops.common.dataobjects.DUserTicket;
import com.datasweep.plantops.common.exceptions.DatasweepServerException;
import com.datasweep.plantops.common.utility.ATRowTypeHelper;
import com.datasweep.plantops.swing.DatasweepPlasticTheme;
import com.datasweep.plantops.swing.MessageBox;
import com.datasweep.plantops.swing.MultiLineToolTipUI;
import com.datasweep.plantops.swing.util.UIUtilities;
import com.datasweep.plantops.utility.BrowserControl;
import com.jgoodies.plaf.plastic.PlasticLookAndFeel;
import com.jgoodies.plaf.plastic.PlasticXPLookAndFeel;
import com.rockwell.activity.ActivityList;
import com.rockwell.datadictionary.ExtendedBeanInfoFactory;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultDesktopManager;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class Buildtime extends JPanel implements IEditEventListener, ISelectEventListener, IExceptionEventListener, PropertyChangeListener, KeyedStatusListener {
    final Class[] ALL_TYPES;
    private JFrame m_frame;
    private ObjectFactory m_objectFactory;
    private Class m_type;
    private Keyed m_object;
    private IObjectNode m_target;
    private Hashtable m_mapObject;
    private Hashtable m_mapButton;
    private Buildtime.ViewManager m_views;
    private DBInfo m_dbInfo;
    private boolean isSelectCollect;
    private boolean isSelectObject;
    protected static final boolean USE_PLASTIC_LAF = true;
    private boolean bHasPrivilagelogin;
    private static Buildtime app = null;
    private KeyedStatusListener m_mapListener;
    private Buildtime.PermissionCache permissionCache;
    JMenuBar menuBar;
    JMenu menuFile;
    JMenuItem miNew;
    JMenuItem miOpen;
    JMenuItem miSave;
    JMenuItem miSaveAs;
    JMenuItem miSaveAll;
    JMenuItem miRestore;
    JMenuItem miExport;
    JMenuItem miImport;
    JMenuItem miTransfer;
    JMenuItem miLogon;
    JMenuItem miLogoff;
    JMenuItem miChangePassword;
    JMenuItem miPrint;
    JMenuItem miExit;
    JMenu menuEdit;
    JMenuItem miCut;
    JMenuItem miCopy;
    JMenuItem miPaste;
    JMenuItem miDelete;
    JMenu menuCompare;
    JMenuItem miCompareEventSheets;
    JMenuItem miCompareForms;
    JMenuItem miCompareSubroutines;
    JMenu menuView;
    JCheckBoxMenuItem miToolbar;
    JCheckBoxMenuItem miStatusBar;
    JMenuItem miRefresh;
    JMenuItem miRefreshCount;
    JMenuItem miClearCache;
    JMenu menuAccessControl;
    JMenuItem miAccessPrivilege;
    JMenuItem miReadOnly;
    JMenuItem miReadWrite;
    JMenuItem miCheckOut;
    JMenuItem miCheckIn;
    JMenuItem miUndoCheckOut;
    JMenuItem miShowVersions;
    JMenu menuWindow;
    JMenuItem miCascade;
    JMenuItem miTileHorizontally;
    JMenuItem miTileVertically;
    JMenuItem miCloseAll;
    JMenu menuHelp;
    JMenuItem miTopics;
    JMenuItem miAbout;
    JPopupMenu contextMenu;
    JMenuItem cmiNew;
    JMenuItem cmiOpen;
    JMenuItem cmiSave;
    JMenuItem cmiRestore;
    JMenuItem cmiDelete;
    JMenu cmAccessControl;
    JMenuItem cmiAccessPrivilege;
    JMenuItem cmiReadOnly;
    JMenuItem cmiReadWrite;
    JMenuItem cmiCheckOut;
    JMenuItem cmiCheckIn;
    JMenuItem cmiUndoCheckOut;
    JMenuItem cmiShowVersions;
    JToolBar toolBar;
    JButton tbNew;
    JButton tbOpen;
    JButton tbSave;
    JButton tbSaveAll;
    JButton tbPrint;
    JButton tbCut;
    JButton tbCopy;
    JButton tbPaste;
    JButton tbDelete;
    JButton tbXRef;
    JToolBar toolBarView;
    JButton tbvEditFilter;
    JButton tbvRefresh;
    JButton tbvClearCache;
    JToolBar toolBarView1;
    JLabel lView;
    JComboBox cbView;
    JButton tbvViewRefresh;
    JButton tbvViewNew;
    JButton tbvViewEdit;
    JButton tbvViewDelete;
    ObjectTreeView tView;
    JToolBar toolBarSearch;
    JButton tbsNewFilter;
    JButton tbsEditFilter;
    JButton tbsRefresh;
    JButton tbsDelete;
    ObjectTreeView tSearch;
    JToolBar toolBarHistory;
    JButton tbhEditFilter;
    JButton tbhRefresh;
    JButton tbhReset;
    ObjectTreeView tHistory;
    ObjectTreeView tConfiguration;
    ObjectTreeView tFSMConfiguration;
    ObjectTreeView tVersionPlatform;
    VersioningPlatformTab versioningPlatformTab;
    JTabbedPane browser;
    JPanel browserPane;
    JPanel leftPane;
    JDesktopPane rightPane;
    JSplitPane hSplitPane;
    Buildtime.StatusBar statusBar;
    private static final String DEFAULT_BUTTON_FOLLOW_FOCUS_PROPERTY = "uiDefaultButtonFollowFocus";

    public Buildtime(JFrame var1) {
        this();
        this.m_frame = var1;
        var1.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent var1) {
                Buildtime.this.Buildtime_closing();
            }
        });
        var1.setDefaultCloseOperation(0);
        var1.getContentPane().add(this, "Center");
        var1.setJMenuBar(this.menuBar);
        var1.pack();
        var1.setSize(999, 617);
        var1.setLocationRelativeTo((Component)null);
        MultiLineToolTipUI.initialize();
    }

    private Buildtime() {
        this.ALL_TYPES = (Class[])((Class[])Utility.add(new Object[][]{ObjectFactory.ACTIVE_VIEW_CLASSES, ObjectFactory.CONFIGURATION_VIEW_CLASSES, ObjectFactory.FSM_CONFIGURATION_VIEW_CLASSES, ObjectFactory.VPVERSION_CONFIGURATION_VIEW_CLASSES}));
        this.m_frame = null;
        this.m_objectFactory = null;
        this.m_type = null;
        this.m_object = null;
        this.m_target = null;
        this.m_mapObject = new Hashtable();
        this.m_mapButton = new Hashtable();
        this.m_views = new Buildtime.ViewManager();
        this.m_dbInfo = null;
        this.isSelectCollect = false;
        this.isSelectObject = false;
        this.bHasPrivilagelogin = true;
        this.m_mapListener = new KeyedStatusListener() {
            public void objectSaved(KeyedStatusEvent var1) {
            }

            public void objectRefreshed(KeyedStatusEvent var1) {
            }

            public void objectDeleted(KeyedStatusEvent var1) {
                Buildtime.this.setupButton(var1.getSource().getClass(), (AbstractClient)null);
            }
        };
        this.permissionCache = null;
        this.menuBar = new JMenuBar();
        this.menuFile = new JMenu("文件");//TODO 修改国际化
        this.miNew = new JMenuItem("创建");//TODO 修改国际化
        this.miOpen = new JMenuItem("打开");//TODO 修改国际化
        this.miSave = new JMenuItem("保存");//TODO 修改国际化
        this.miSaveAs = new JMenuItem("另存为");//TODO 修改国际化
        this.miSaveAll = new JMenuItem("保存全部");//TODO 修改国际化
        this.miRestore = new JMenuItem("恢复");//TODO 修改国际化
        this.miExport = new JMenuItem("导出");//TODO 修改国际化
        this.miImport = new JMenuItem("导入");//TODO 修改国际化
        this.miTransfer = new JMenuItem("转移");//TODO 修改国际化
        this.miLogon = new JMenuItem("登录");//TODO 修改国际化
        this.miLogoff = new JMenuItem("登出");//TODO 修改国际化
        this.miChangePassword = new JMenuItem("修改密码");//TODO 修改国际化
        this.miPrint = new JMenuItem("打印");//TODO 修改国际化
        this.miExit = new JMenuItem("退出");//TODO 修改国际化
        this.menuEdit = new JMenu("编辑");//TODO 修改国际化
        this.miCut = new JMenuItem("剪切");//TODO 修改国际化
        this.miCopy = new JMenuItem("复制");//TODO 修改国际化
        this.miPaste = new JMenuItem("粘贴");//TODO 修改国际化
        this.miDelete = new JMenuItem("删除");//TODO 修改国际化
        this.menuCompare = new JMenu("比较");//TODO 修改国际化
        this.miCompareEventSheets = new JMenuItem("Event Sheets...");
        this.miCompareForms = new JMenuItem("Forms...");
        this.miCompareSubroutines = new JMenuItem("Subroutines...");
        this.menuView = new JMenu("查看");//TODO 修改国际化
        this.miToolbar = new JCheckBoxMenuItem("工具栏");//TODO 修改国际化
        this.miStatusBar = new JCheckBoxMenuItem("状态栏");//TODO 修改国际化
        this.miRefresh = new JMenuItem("刷新");//TODO 修改国际化
        this.miRefreshCount = new JMenuItem("刷新数");//TODO 修改国际化
        this.miClearCache = new JMenuItem("清除缓存");//TODO 修改国际化
        this.menuAccessControl = new JMenu("访问控制");//TODO 修改国际化
        this.miAccessPrivilege = new JMenuItem("访问权限");//TODO 修改国际化
        this.miReadOnly = new JMenuItem("只读");//TODO 修改国际化
        this.miReadWrite = new JMenuItem("读写");//TODO 修改国际化
        this.miCheckOut = new JMenuItem("迁出");//TODO 修改国际化
        this.miCheckIn = new JMenuItem("迁入");//TODO 修改国际化
        this.miUndoCheckOut = new JMenuItem("迁出不保存");//TODO 修改国际化
        this.miShowVersions = new JMenuItem("显示版本");//TODO 修改国际化
        this.menuWindow = new JMenu("窗口");//TODO 修改国际化
        this.miCascade = new JMenuItem("层叠");//TODO 修改国际化
        this.miTileHorizontally = new JMenuItem("水平排列");//TODO 修改国际化
        this.miTileVertically = new JMenuItem("垂直并排");//TODO 修改国际化
        this.miCloseAll = new JMenuItem("关闭全部");//TODO 修改国际化
        this.menuHelp = new JMenu("帮助");//TODO 修改国际化
        this.miTopics = new JMenuItem("帮助主题");//TODO 修改国际化
        this.miAbout = new JMenuItem("关于配置客户端");//TODO 修改国际化
        this.contextMenu = new JPopupMenu();
        this.cmiNew = new JMenuItem("创建");//TODO 修改国际化
        this.cmiOpen = new JMenuItem("打开");//TODO 修改国际化
        this.cmiSave = new JMenuItem("保存");//TODO 修改国际化
        this.cmiRestore = new JMenuItem("恢复");//TODO 修改国际化
        this.cmiDelete = new JMenuItem("删除");//TODO 修改国际化
        this.cmAccessControl = new JMenu("访问控制");
        this.cmiAccessPrivilege = new JMenuItem("访问权限");//TODO 修改国际化
        this.cmiReadOnly = new JMenuItem("只读");//TODO 修改国际化
        this.cmiReadWrite = new JMenuItem("读写");//TODO 修改国际化
        this.cmiCheckOut = new JMenuItem("迁出");//TODO 修改国际化
        this.cmiCheckIn = new JMenuItem("迁入");//TODO 修改国际化
        this.cmiUndoCheckOut = new JMenuItem("迁出不保存");//TODO 修改国际化
        this.cmiShowVersions = new JMenuItem("显示版本");//TODO 修改国际化
        this.toolBar = new JToolBar();
        this.tbNew = new ToolBarButton("images/new.gif");
        this.tbOpen = new ToolBarButton("images/open.gif");
        this.tbSave = new ToolBarButton("images/save.gif");
        this.tbSaveAll = new ToolBarButton("images/saveall.gif");
        this.tbPrint = new ToolBarButton("images/print.gif");
        this.tbCut = new ToolBarButton("images/cut.gif");
        this.tbCopy = new ToolBarButton("images/copy.gif");
        this.tbPaste = new ToolBarButton("images/paste.gif");
        this.tbDelete = new ToolBarButton("images/delete.gif");
        this.tbXRef = new ToolBarButton("images/xref.gif");
        this.toolBarView = new JToolBar();
        this.tbvEditFilter = new ToolBarButton("images/btnEditFilter.gif");
        this.tbvRefresh = new ToolBarButton("images/btnRefresh.gif");
        this.tbvClearCache = new ToolBarButton("images/btnClearCache.gif");
        this.toolBarView1 = new JToolBar();
        this.lView = new JLabel("View:");
        this.cbView = new JComboBox();
        this.tbvViewRefresh = new ToolBarButton("images/btnRefresh.gif");
        this.tbvViewNew = new ToolBarButton("images/new.gif");
        this.tbvViewEdit = new ToolBarButton("images/btnEditFilter.gif");
        this.tbvViewDelete = new ToolBarButton("images/delete.gif");
        this.tView = new ObjectTreeView();
        this.toolBarSearch = new JToolBar();
        this.tbsNewFilter = new ToolBarButton("images/new.gif");
        this.tbsEditFilter = new ToolBarButton("images/btnEditFilter.gif");
        this.tbsRefresh = new ToolBarButton("images/btnRefresh.gif");
        this.tbsDelete = new ToolBarButton("images/delete.gif");
        this.tSearch = new ObjectTreeView();
        this.toolBarHistory = new JToolBar();
        this.tbhEditFilter = new ToolBarButton("images/btnEditFilter.gif");
        this.tbhRefresh = new ToolBarButton("images/btnRefresh.gif");
        this.tbhReset = new ToolBarButton("images/btnReset.gif");
        this.tHistory = new ObjectTreeView();
        this.tConfiguration = new ObjectTreeView();
        this.tFSMConfiguration = new ObjectTreeView();
        this.tVersionPlatform = new ObjectTreeView();
        this.versioningPlatformTab = new VersioningPlatformTab(this, this.tVersionPlatform);
        this.browser = new JTabbedPane();
        this.browserPane = new JPanel();
        this.leftPane = new JPanel();
        this.rightPane = new JDesktopPane();
        this.hSplitPane = new JSplitPane(1);
        this.statusBar = new Buildtime.StatusBar();
        this.initForm();
        this.refreshView((Object)null);
        FontMetrics var1 = this.cbView.getFontMetrics(this.cbView.getFont());
        this.cbView.setPreferredSize(new Dimension(this.cbView.getPreferredSize().width, var1.getMaxAscent() + var1.getMaxDescent()));
        EventLog.addEventListener(this);
    }

    private void Buildtime_closing() {
        if (this.querySaveChanges(this.ALL_TYPES)) {
            if (this.getObjectFactory() != null && this.getObjectFactory().isLoggedIn()) {
                this.setCursor(Cursor.getPredefinedCursor(3));

                try {
                    this.getObjectFactory().logout();
                } catch (Throwable var2) {
                    EventLog.logException(var2, this, "Buildtime_closing()");
                }

                this.setCursor(Cursor.getPredefinedCursor(0));
            }

            EventLog.removeEventListener(this);
            System.exit(0);
        }
    }

    public void todo() {
    }

    private void addEditor(JInternalFrame var1) {
        if (var1.isMaximum()) {
            var1.setLocation(0, 0);
        } else {
            int var2 = UIUtilities.getTitleHeight(var1) + 1;
            int var3 = var2 * this.rightPane.getAllFrames().length;
            int var4 = var3;
            int var5 = var3;
            int var6 = var3 + var1.getWidth() - this.rightPane.getWidth();
            int var7 = var3 + var1.getHeight() - this.rightPane.getHeight();
            if (var6 > 0) {
                var4 = var6 - var6 % var2;
            }

            if (var7 > 0) {
                var5 = var7 - var7 % var2;
            }

            var1.setLocation(var4, var5);
        }

        this.rightPane.add(var1);
        if (var1 instanceof IEditEvent) {
            ((IEditEvent)var1).addEventListener(this);
        }

        if (var1 instanceof ISelectEvent) {
            ((ISelectEvent)var1).addEventListener(this);
        }

    }

    private void clearCache(JTree var1, Class var2) {
        TreePath var3 = this.getTreePath(var1, var2);
        Object var4 = var3 != null ? ((ObjectTreeNode)var3.getLastPathComponent()).getUserObject() : null;
        this.setupButton(var2, (AbstractClient)null);
        this.closeFrame(var2);
        if (var3 != null) {
            var1.collapsePath(var3);
        }

        this.getObjectFactory().clearCache(var2);
        if (var4 instanceof FilteredCollection) {
            ((FilteredCollection)var4).setObject((Object)null);
        } else if (var4 instanceof FilterCollection) {
            ListIterator var5 = ((FilterCollection)var4).getChildren().listIterator();

            while(var5.hasNext()) {
                ((FilteredCollection)var5.next()).setObject((Object)null);
            }

            if (var2.equals(ATRow.class) || var2.equals(STAInstance.class)) {
                this.doRefresh(var4);
            }
        } else if (var4 instanceof FSMConfigurationCollection) {
            ((FSMConfigurationCollection)var4).setObject((Object)null);
        } else if (var4 instanceof VPConfigurationCollection) {
            ((VPConfigurationCollection)var4).setObject((Object)null);
        }

        if (var2.isInstance(this.m_object)) {
            this.setObject((Keyed)null);
        }

    }

    private void clearCache(JTree var1, Class[] var2) {
        for(int var3 = 0; var3 < var2.length; ++var3) {
            this.clearCache(var1, var2[var3]);
        }

    }

    private void closeFrame(Class var1) {
        JInternalFrame[] var2 = this.rightPane.getAllFrames();

        for(int var3 = var2.length - 1; var3 >= 0; --var3) {
            Object var4 = null;
            if (var2[var3] instanceof EditFrame) {
                ((EditFrame)var2[var3]).close(var1);
            } else if (var2[var3] instanceof CompatibilityEditor) {
                ((CompatibilityEditor)var2[var3]).close(var1);
            } else if (var2[var3] instanceof SummaryFrame) {
                ((SummaryFrame)var2[var3]).close(var1);
            }
        }

    }

    private void closeFrame(Class[] var1) {
        for(int var2 = 0; var2 < var1.length; ++var2) {
            this.closeFrame(var1[var2]);
        }

    }

    public void connect(String var1, String var2, String var3, boolean var4, String var5, String var6) {
        try {
            if (this.m_objectFactory == null) {
                this.m_objectFactory = new ObjectFactory(new ServerInfo(var1, var2, var3));
            }

            if (app.getObjectFactory() != null && !app.getObjectFactory().isLoggedIn() && !var4) {
                app.doLogon();
            } else {
                try {
                    app.doLogon(var5, var6);
                } catch (Exception var13) {
                    var13.printStackTrace();
                    app.doLogon();
                }
            }

            this.m_dbInfo = this.m_objectFactory.getDBInfo();
            if (this.m_dbInfo == null) {
                return;
            }

            if (!this.m_dbInfo.isValidSchema()) {
                int[] var7 = this.m_dbInfo.getExpectedDBSchemaVersion();
                String var8 = "" + var7[0];
                String var9 = "" + var7[1];
                if (var7[1] < 10) {
                    var9 = "0" + var9;
                }

                String var10 = "" + this.m_dbInfo.getActiveDBSchemaMajorVersion();
                String var11 = "" + this.m_dbInfo.getActiveDBSchemaMinorVersion();
                if (var7[1] < 10) {
                    var11 = "0" + var11;
                }

                String var12 = "";
                var12 = "This build requires schema " + var8 + "." + var9 + ", you have " + var10 + "." + var11 + "." + System.getProperty("line.separator") + "Please contact your system administrator to migrate the database.";
                JOptionPane.showMessageDialog(this, var12, "Errors", 0);
                System.exit(0);
            }

            if (this.m_dbInfo != null) {
                EventLog.setConsolidatedLogEnabled(this.m_dbInfo.isEnableConsolidatedLogging());
            }
        } catch (Throwable var14) {
            EventLog.dispatchException(var14, this, "connect(" + var1 + "," + var2 + "," + var3 + ")");
        }

    }

    private boolean _isVersioning(IAccessControl var1) {
        return this.getDBInfo().getObjectRevisioningLevel() >= (ClassUtility.isRevisioningLevel1(var1.getClass()) ? 1 : 2);
    }

    private void _delete(Keyed var1, AccessPrivilege var2) {
        this.statusBar.setMessage(0, "Deleting '" + var1.toString() + "' ...");
        this.setCursor(Cursor.getPredefinedCursor(3));

        try {
            this.getObjectFactory().delete(var1, var2);
        } catch (Throwable var4) {
            EventLog.dispatchException(var4, this, "_delete(" + var1.toString() + ")");
        }

        this.setCursor(Cursor.getPredefinedCursor(0));
        this.statusBar.setMessage(0, (String)null);
    }

    public void inputESignature(AccessPrivilege var1) {
        if (var1 != null && var1.getPerformedBySignature() != null) {
            DlgESignature var2 = new DlgESignature(this, var1, this.getObjectFactory(), ESignatureDefinition.ESIGDIALOG_CUD);

            try {
                var2.showDialog();
            } catch (Throwable var7) {
                EventLog.dispatchException(var7, this, "inputESignature()");
            } finally {
                var2.dispose();
            }
        }

    }

    public static void save(IAccessControl var0) throws Exception {
        if (null != app) {
            app._save(var0);
        }

    }

    private void _save(IAccessControl var1) throws Exception {
        if (var1.getAccessLevel() == 1) {
            throw new RuntimeException(ClassUtility.getDisplayName(var1.getClass()) + " '" + var1.toString() + "' is read only");
        } else {
            AccessPrivilege var2;
            if (((Keyed)var1).isNew()) {
                var2 = this.getSavePrivilegeWithEsignature((Keyed)var1);
                this.getObjectFactory().save((Keyed)var1, var2);
            } else if (!this._isVersioning(var1)) {
                var2 = this.getSavePrivilegeWithEsignature((Keyed)var1);
                this.getObjectFactory().save((Keyed)var1, var2);
            } else if (this.getCurrentUser().equals(var1.getCheckedOutBy())) {
                var2 = this.getSavePrivilegeWithEsignature((Keyed)var1);
                this.getObjectFactory().save((Keyed)var1, var2);
            } else {
                if (var1.isCheckedOut()) {
                    throw new RuntimeException(ClassUtility.getDisplayName(var1.getClass()) + " '" + var1.toString() + "' is checked out");
                }

                int var4 = JOptionPane.showConfirmDialog(this, "Auditing is turned on for " + ClassUtility.getDisplayName(var1.getClass()) + " '" + var1.toString() + "'." + System.getProperty("line.separator") + "Do you want to check out and save the changes?", "Confirm Save", 0);
                if (var4 == 0) {
                    AccessPrivilege var3 = this.getUpdatePrivilegeWithEsignature((Keyed)var1);
                    var1.checkOut(false, var3);
                    this.getObjectFactory().save((Keyed)var1, var3);
                }
            }

        }
    }

    private void enableToolBarButtons() {
        boolean var1 = this.cbView.getSelectedItem() != null;
        this.tbvViewDelete.setEnabled(var1);
        this.tbvViewEdit.setEnabled(var1);
        this.tbvViewRefresh.setEnabled(var1);
    }

    private boolean exist(JInternalFrame var1) {
        JInternalFrame[] var2 = this.rightPane.getAllFrames();

        for(int var3 = 0; var3 < var2.length; ++var3) {
            if (var2[var3] == var1) {
                return true;
            }
        }

        return false;
    }

    private String[] generateName(Class var1) {
        return this.generateName(var1, ClassUtility.getDisplayName(var1).replace(' ', '_'));
    }

    private String[] generateName(Class var1, String var2) {
        String var3 = var2;

        StringBuilder var10000;
        for(int var4 = 0; this.getObjectFactory().getByName(var1, new String[]{var3, "1"}) != null; var3 = var10000.append(var4).toString()) {
            var10000 = (new StringBuilder()).append(var2);
            ++var4;
        }

        return new String[]{var3, "1"};
    }

    private List getChangedObjects(Class[] var1) {
        Vector var2 = new Vector();

        for(int var3 = 0; var3 < var1.length; ++var3) {
            ListIterator var4 = this.getObjectFactory().getModifiedObjects(var1[var3]).listIterator();

            while(var4.hasNext()) {
                Object var5 = var4.next();
                if (this.getPermission((AbstractClient)var5).canSave()) {
                    var2.addElement(var5);
                }
            }
        }

        return var2;
    }

    private User getCurrentUser() {
        try {
            return this.getObjectFactory().getUserManager().getCurrentUser();
        } catch (Throwable var2) {
            EventLog.logException(var2, this, "getCurrentUser()");
            return null;
        }
    }

    private Buildtime.Permission getPermission(Class var1) {
        if (this.permissionCache == null) {
            this.permissionCache = new Buildtime.PermissionCache();
        }

        return this.permissionCache.getPermission(var1);
    }

    private Buildtime.Permission getPermission(AbstractClient var1) {
        if (this.permissionCache == null) {
            this.permissionCache = new Buildtime.PermissionCache();
        }

        return this.permissionCache.getPermission(var1);
    }

    private DBInfo getDBInfo() {
        return this.m_objectFactory.getDBInfo();
    }

    private boolean isDatasweepSecurity() {
        DBInfo var1 = this.getDBInfo();
        return var1 != null && var1.isSecurityProviderIsDatasweep();
    }

    private AccessPrivilege getUpdatePrivilegeWithEsignature(Keyed var1) {
        AccessPrivilege var2 = null;

        try {
            if (var1 instanceof UDADefinition) {
                var2 = this.getObjectFactory().getAccessPrivilegeManager().getAccessPrivilegeFromDB("PDUDADesigner");
            } else if (var1 instanceof FSMConfiguration) {
                var2 = this.getObjectFactory().getAccessPrivilegeManager().getAccessPrivilegeFromDB("FSMConfigurationDesigner");
            } else {
                var2 = var1.getUpdatePrivilegeWithCUDSupport();
            }

            this.inputESignature(var2);
        } catch (DatasweepException var4) {
            EventLog.dispatchException(var4, this, "getUpdatePrivilegeWithEsignature()");
        }

        return var2;
    }

    private AccessPrivilege getDeletePrivilegeWithEsignature(Keyed var1) {
        AccessPrivilege var2 = null;

        try {
            if (var1 instanceof UDADefinition) {
                var2 = this.getObjectFactory().getAccessPrivilegeManager().getAccessPrivilegeFromDB("PDUDADesigner");
            } else {
                var2 = var1.getDeletePrivilegeWithCUDSupport();
            }

            this.inputESignature(var2);
        } catch (DatasweepException var4) {
            EventLog.dispatchException(var4, this, "getDeletePrivilegeWithEsignature()");
        }

        return var2;
    }

    private AccessPrivilege getSavePrivilegeWithEsignature(Keyed var1) {
        AccessPrivilege var2 = null;

        try {
            if (var1 instanceof UDADefinition) {
                var2 = this.getObjectFactory().getAccessPrivilegeManager().getAccessPrivilegeFromDB("PDUDADesigner");
            } else if (var1 instanceof FSMConfiguration) {
                var2 = this.getObjectFactory().getAccessPrivilegeManager().getAccessPrivilegeFromDB("FSMConfigurationDesigner");
            } else if (var1 instanceof VPVersionConfiguration) {
                var2 = this.getObjectFactory().getAccessPrivilegeManager().getAccessPrivilegeFromDB("VersioningPlatformDesigner");
            } else {
                var2 = var1.getCreateOrUpdatePrivilegeWithCUDSupport();
            }

            this.inputESignature(var2);
        } catch (DatasweepException var4) {
            EventLog.dispatchException(var4, this, "getSavePrivilegeWithEsignature()");
        }

        return var2;
    }

    protected ObjectFactory getObjectFactory() {
        return this.m_objectFactory;
    }

    private Object getSelectedObject(JTree var1) {
        TreePath var2 = var1.getSelectionPath();
        Object var3 = var2 != null ? var2.getLastPathComponent() : null;
        if (var3 instanceof DefaultMutableTreeNode) {
            var3 = ((DefaultMutableTreeNode)var3).getUserObject();
        }

        return var3;
    }

    private Object getSelectedObject(JTree var1, int var2) {
        TreePath var3 = var1.getSelectionPath();
        if (var3 == null) {
            return null;
        } else {
            Object var4 = ((DefaultMutableTreeNode)var3.getLastPathComponent()).getUserObject();
            if ((var4 instanceof FilterCollection || var4 instanceof FilteredCollection) && (((AbstractCollection)var4).getType().equals(ATRow.class) || ((AbstractCollection)var4).getType().equals(STAInstance.class))) {
                ++var2;
            }

            Object var5 = var3 != null && var3.getPathCount() > var2 ? var3.getPathComponent(var2) : null;
            if (var5 instanceof DefaultMutableTreeNode) {
                var5 = ((DefaultMutableTreeNode)var5).getUserObject();
            }

            return var5;
        }
    }

    private TreePath getTreePath(JTree var1, Class var2) {
        DefaultTreeModel var3 = (DefaultTreeModel)var1.getModel();
        int var4 = var3.getChildCount(var3.getRoot());

        for(int var5 = 0; var5 < var4; ++var5) {
            ObjectTreeNode var6 = (ObjectTreeNode)var3.getChild(var3.getRoot(), var5);
            AbstractCollection var7 = (AbstractCollection)var6.getUserObject();
            if (var2.equals(var7.getType())) {
                return new TreePath(var3.getPathToRoot(var6));
            }
        }

        return null;
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

    private void menuAccessControl_popup() {
        boolean var4;
        if (this.isSelectCollect) {
            if (this.m_target == null || this.m_target instanceof FilterCollection) {
                return;
            }

            if (this.m_target instanceof IObjectCollection) {
                if (this.m_target instanceof FilteredCollection) {
                    FilteredCollection var1 = (FilteredCollection)this.m_target;
                    if (!var1.isLoaded() && !FilterFactory.verbose(this, var1.getFilter())) {
                        return;
                    }
                }

                Object var12 = ((IObjectCollection)this.m_target).getObject();
                Class var2 = ((IObjectCollection)this.m_target).getType();
                int var3 = var12 instanceof List ? ((List)var12).size() : 0;
                var4 = this.getObjectFactory().getUserSecurity(var2) == 'W';
                int var5 = 0;
                int var6 = 0;
                boolean var7 = false;
                boolean var8 = false;
                boolean var9 = false;
                if (var3 != 0) {
                    ListIterator var10 = ((List)var12).listIterator();

                    while(var10.hasNext()) {
                        AbstractClient var11 = (AbstractClient)var10.next();
                        if (var11.isChanged()) {
                            ++var5;
                        } else if (var11.isNew()) {
                            ++var6;
                        }
                    }

                    Object var18 = ((List)var12).get(0);
                    var7 = var4 && var18 instanceof IAccessControl;
                    var9 = var7 && this._isVersioning((IAccessControl)var18);
                }

                this.miReadOnly.setEnabled(var7);
                this.miReadWrite.setEnabled(var7);
                this.miCheckIn.setEnabled(var9);
                this.miCheckOut.setEnabled(var9);
                this.miUndoCheckOut.setEnabled(var9);
                this.miShowVersions.setEnabled(var9 && var8);
                this.miAccessPrivilege.setEnabled(var4);
                if (this.browser.getSelectedIndex() != 0) {
                    this.miAccessPrivilege.setEnabled(false);
                }
            }
        } else if (this.m_object instanceof IAccessControl && this.getObjectFactory().getUserSecurity(this.m_object) == 'W' && !this.m_object.isNew() && !this.m_object.isAudit()) {
            boolean var13 = this.m_object instanceof DeAnzaForm || this.m_object instanceof Subroutine || this.m_object instanceof EventSheetHolder || this.m_object instanceof AddOn;
            boolean var14 = this._isVersioning((IAccessControl)this.m_object);
            boolean var15 = ((IAccessControl)this.m_object).isCheckedOut();
            var4 = ((IAccessControl)this.m_object).getAccessLevel() == 1;
            boolean var16 = ((IAccessControl)this.m_object).getCheckedOutBy() == this.getCurrentUser();
            boolean var17 = !(this.m_object instanceof DataDictionary);
            this.miReadOnly.setEnabled(!var15 && !var4);
            this.miReadWrite.setEnabled(!var15 && var4);
            this.miCheckIn.setEnabled(var14 && var15 && var16);
            this.miCheckOut.setEnabled(var14 && !var15 && !var4);
            this.miUndoCheckOut.setEnabled(var14 && var15 && (var16 || this.getCurrentUser().getPrivilege() == 4));
            this.miShowVersions.setEnabled(var14 && var13);
            this.miAccessPrivilege.setEnabled(var17);
            if (this.browser.getSelectedIndex() != 0) {
                this.miAccessPrivilege.setEnabled(false);
            }
        } else {
            this.miReadOnly.setEnabled(false);
            this.miReadWrite.setEnabled(false);
            this.miCheckIn.setEnabled(false);
            this.miCheckOut.setEnabled(false);
            this.miUndoCheckOut.setEnabled(false);
            this.miShowVersions.setEnabled(false);
            this.miAccessPrivilege.setEnabled(true);
            if (this.browser.getSelectedIndex() != 0) {
                this.miAccessPrivilege.setEnabled(false);
            }
        }

    }

    private boolean querySaveChanges(Class[] var1) {
        boolean var2 = true;
        if (this.getObjectFactory() != null && this.getObjectFactory().isLoggedIn()) {
            List var3 = this.getChangedObjects(var1);
            if (var3.size() > 0) {
                DlgSaveObjects var4 = new DlgSaveObjects(this, var3);
                switch(var4.showDialog()) {
                    case 0:
                        var2 = this.saveObjects(var4.getResult());
                        break;
                    case 2:
                        var2 = false;
                }

                var4.dispose();
            }
        }

        return var2;
    }

    private void refreshView(Object var1) {
        Vector var2 = this.m_views.getAll();
        var2.insertElementAt((Object)null, 0);
        this.cbView.setModel(new DefaultComboBoxModel(var2));
        this.cbView.setSelectedItem(var1);
        this.enableToolBarButtons();
    }

    private boolean saveObjects(List var1) {
        boolean var2 = true;
        ListIterator var3 = var1.listIterator();

        while(var3.hasNext()) {
            Keyed var4 = (Keyed)var3.next();
            this.statusBar.setMessage(0, "Saving '" + var4.toString() + "' ...");
            this.setCursor(Cursor.getPredefinedCursor(3));

            try {
                if (var4 instanceof IAccessControl) {
                    this._save((IAccessControl)var4);
                } else {
                    AccessPrivilege var5 = this.getSavePrivilegeWithEsignature(var4);
                    this.getObjectFactory().save(var4, var5);
                }
            } catch (Throwable var6) {
                EventLog.dispatchException(var6, this, "saveObjects()");
                var2 = false;
            }

            this.setCursor(Cursor.getPredefinedCursor(0));
            this.statusBar.setMessage(0, (String)null);
        }

        return var2;
    }

    public void setBusy(boolean var1) {
        if (var1) {
            this.m_frame.getGlassPane().setCursor(Cursor.getPredefinedCursor(3));
            this.m_frame.getGlassPane().setVisible(true);
        } else {
            this.m_frame.getGlassPane().setVisible(false);
            this.m_frame.getGlassPane().setCursor(Cursor.getPredefinedCursor(0));
        }

    }

    public void setEditor(JInternalFrame var1) {
        try {
            if (!var1.isVisible()) {
                var1.setVisible(true);
            }

            if (var1.isIcon()) {
                var1.setIcon(false);
            }

            if (!this.exist(var1)) {
                this.addEditor(var1);
                if (var1.getWidth() > this.rightPane.getWidth() || var1.getHeight() > this.rightPane.getHeight()) {
                    var1.setMaximum(true);
                }
            }

            var1.setSelected(true);
        } catch (Throwable var3) {
            EventLog.dispatchException(var3, this, "setEditor()");
        }

    }

    private void setObject(Keyed var1) {
        if (this.m_object instanceof Keyed) {
            this.m_object.removePropertyChangeListener(this);
            this.m_object.removeKeyedStatusListener(this);
        }

        if (var1 instanceof ATDefinition && ((ATDefinition)var1).isDependent()) {
            var1 = ((ATDefinition)var1).getParentATDefinition();
        }

        this.m_object = (Keyed)var1;
        if (this.m_object != null) {
            this.setType(this.m_object.getClass());
            Object var2 = NodeFactory.getObjectNode(var1);
            this.tView.setSelection(var2);
            this.tSearch.setSelection(var2);
            this.tConfiguration.setSelection(var2);
        }

        if (var1 == null) {
            this.miOpen.setEnabled(false);
            this.tbOpen.setToolTipText("Open");
            this.tbOpen.setEnabled(false);
            this.miSave.setEnabled(false);
            this.tbSave.setToolTipText("Save");
            this.tbSave.setEnabled(false);
            this.miSaveAs.setEnabled(false);
            this.miRestore.setEnabled(false);
            this.miDelete.setEnabled(false);
            this.tbDelete.setToolTipText("Delete");
            this.tbDelete.setEnabled(false);
            this.tbXRef.setToolTipText("Xref");
            this.tbXRef.setEnabled(false);
        } else if (var1 instanceof AbstractClient) {
            Buildtime.Permission var3 = this.getPermission((AbstractClient)var1);
            this.miOpen.setEnabled(var3.canOpen());
            this.tbOpen.setToolTipText("Open " + ((Keyed)var1).toString());
            this.tbOpen.setEnabled(var3.canOpen());
            this.miSave.setEnabled(var3.canSave());
            this.tbSave.setToolTipText("Save " + ((Keyed)var1).toString());
            this.tbSave.setEnabled(var3.canSave());
            this.miSaveAs.setEnabled(var3.canSaveAs());
            this.miRestore.setEnabled(var3.canRestore());
            this.miDelete.setEnabled(var3.canDelete());
            this.tbDelete.setToolTipText("Delete " + ((Keyed)var1).toString());
            this.tbDelete.setEnabled(var3.canDelete());
            this.tbXRef.setToolTipText("Xref " + ((Keyed)var1).toString());
            this.tbXRef.setEnabled(var3.canXRef());
            this.setupButton(var1.getClass(), (AbstractClient)var1);
        }

        if (this.m_object instanceof Keyed) {
            this.m_object.addKeyedStatusListener(this);
            this.m_object.addPropertyChangeListener(this);
        }

        this.m_frame.setTitle("配置客户端" + (this.m_object != null ? " - " + this.m_object.toString() : ""));
    }

    private void setType(Class var1) {
        if (this.m_type != var1) {
            this.m_type = var1;
            if (var1 == null) {
                this.miNew.setEnabled(false);
                this.tbNew.setToolTipText("New");
                this.tbNew.setEnabled(false);
            } else {
                Buildtime.Permission var2 = this.getPermission(var1);
                this.miNew.setEnabled(var2.canNew());
                this.tbNew.setToolTipText("New " + ClassUtility.getDisplayName(var1));
                this.tbNew.setEnabled(var2.canNew());
            }
        }

    }

    private void setupButton(boolean var1) {
        boolean var2 = false;
        if (!var1) {
            this.setObject((Keyed)null);
            this.setType((Class)null);
        } else {
            try {
                var2 = this.getCurrentUser().isPasswordModifiable();
            } catch (Throwable var4) {
                EventLog.logException(var4, this, "setupButton(" + var1 + ")");
            }
        }

        this.tbSaveAll.setEnabled(var1);
        this.miSaveAll.setEnabled(var1);
        this.miExport.setEnabled(var1);
        this.miImport.setEnabled(var1);
        this.miTransfer.setEnabled(var1);
        this.miLogon.setEnabled(!var1);
        this.miLogoff.setEnabled(var1);
        this.miChangePassword.setEnabled(var1);
        this.miCompareForms.setEnabled(var1);
        this.miCompareSubroutines.setEnabled(var1);
        this.miRefresh.setEnabled(var1);
        this.miRefreshCount.setEnabled(var1);
        this.miClearCache.setEnabled(var1);
        this.hSplitPane.setVisible(var1);
    }

    private void setupButton(Class var1, AbstractClient var2) {
        Object var3 = this.m_mapObject.get(var1);
        if (var3 != var2) {
            if (var3 instanceof Keyed) {
                ((Keyed)var3).removeKeyedStatusListener(this.m_mapListener);
            }

            if (var2 != null) {
                this.m_mapObject.put(var1, var2);
            } else {
                this.m_mapObject.remove(var1);
            }

            if (var2 instanceof Keyed) {
                ((Keyed)var2).addKeyedStatusListener(this.m_mapListener);
            }

            if (var2 instanceof Keyed) {
                ((Keyed)var2).addKeyedStatusListener(this.m_mapListener);
            }

            JButton var4 = (JButton)this.m_mapButton.get(var1);
            if (var4 != null) {
                var4.setEnabled(var2 != null);
                var4.setVisible(var2 != null);
                var4.setToolTipText(ClassUtility.getDisplayName(var1) + (var2 != null ? " '" + var2.toString() + "'" : ""));
            }

            this.miSaveAs.setEnabled(var1 != Locale.class && var1 != ATDefinition.class);
        }
    }

    private Vector toVectorOfVectorOfInnerData(List var1) {
        Vector var2 = new Vector();
        Hashtable var3 = new Hashtable();

        AbstractClient var5;
        Vector var6;
        for(ListIterator var4 = var1.listIterator(); var4.hasNext(); var6.addElement(ClientUtility.getInnerData(var5))) {
            var5 = (AbstractClient)var4.next();
            var6 = (Vector)var3.get(var5.getClass());
            if (var6 == null) {
                var6 = new Vector();
                var3.put(var5.getClass(), var6);
                var2.addElement(var6);
            }
        }

        var3.clear();
        return var2;
    }

    private void _undoCheckOut(IAccessControl var1) {
        this.statusBar.setMessage(0, "Undo check out '" + var1.toString() + "' ...");
        this.setCursor(Cursor.getPredefinedCursor(3));

        try {
            AccessPrivilege var2 = this.getUpdatePrivilegeWithEsignature((Keyed)var1);
            var1.undoCheckOut(var2);
            this.getObjectFactory().getUserManager().getServer().getTrxInfo().setSignature((DUserTicket)null);
            this.getObjectFactory().getUserManager().getCurrentUser().clearApprover();
        } catch (Throwable var3) {
            EventLog.dispatchException(var3, this, "_undoCheckOut()");
        }

        this.setCursor(Cursor.getPredefinedCursor(0));
        this.statusBar.setMessage(0, (String)null);
    }

    private void doAbout() {
        DDBInfo var1 = null;
        String var2 = null;

        try {
            var2 = this.getObjectFactory().getActiveProtocol();
            var1 = this.getDBInfo().getDDBInfo();
        } catch (Throwable var4) {
        }

        DlgAbout var3 = new DlgAbout(this, "Process Designer", var1, var2);
        var3.showDialog();
        var3.dispose();
    }

    private void doCascade() {
        JInternalFrame[] var1 = this.rightPane.getAllFrames();
        if (var1 != null && var1.length != 0) {
            double var2 = 1.0D;

            for(int var4 = 0; var4 < var1.length; ++var4) {
                var2 *= 0.95D;
            }

            var2 = Math.max(0.6666666666666666D, var2);
            Dimension var12 = this.rightPane.getSize();
            int var5 = (int)((double)var12.width * var2);
            int var6 = (int)((double)var12.height * var2);
            int var7 = var1.length > 1 ? (var12.width - var5) / (var1.length - 1) : 0;
            int var8 = var1.length > 1 ? (var12.height - var6) / (var1.length - 1) : 0;
            int var9 = 0;
            int var10 = 0;
            var12 = new Dimension(var5, var6);

            for(int var11 = 0; var11 < var1.length; ++var11) {
                var1[var11].setSize(var12);
                var1[var11].setLocation(var9, var10);
                var9 += var7;
                var10 += var8;
            }

        }
    }

    private void doChangePassword() {
        User var1 = this.getCurrentUser();
        this.doChangePassword(var1 != null ? var1.getName() : null);
    }

    private void doChangePassword(String var1) {
        DlgChangePassword var2 = new DlgChangePassword(this, var1);

        try {
            if (var2.showDialog()) {
                String[] var3 = var2.getResult();
                this.setCursor(Cursor.getPredefinedCursor(3));
                boolean var4 = this.getObjectFactory().isLoggedIn();
                this.getObjectFactory().changePassword(var3[0], var3[1], var3[2], (Time)null, (String)null);
                if (var4) {
                    this.getObjectFactory().logout();
                    this.getObjectFactory().login(var3[0], var3[2]);
                }

                this.setCursor(Cursor.getPredefinedCursor(0));
            }
        } catch (Throwable var8) {
            this.setCursor(Cursor.getPredefinedCursor(0));
            EventLog.dispatchException(var8, this, "doChangePassword()");
            this.doChangePassword(var1);
        } finally {
            var2.dispose();
        }

    }

    private void doCheckIn(Keyed var1) {
        if (var1 instanceof IAccessControl) {
            if (((IAccessControl)var1).isCheckedOut()) {
                boolean var2 = true;
                boolean var3 = false;
                if (var1.isChanged()) {
                    String var4 = ClassUtility.getDisplayName(var1.getClass());
                    int var5 = JOptionPane.showConfirmDialog(this, var4 + " '" + var1.toString() + "' has been changed." + System.getProperty("line.separator") + "Do you want to check in with the changes?", "Confirm Check In", 1);
                    switch(var5) {
                        case 0:
                            var3 = true;
                            break;
                        case 1:
                            var2 = false;
                            break;
                        case 2:
                            return;
                    }
                }

                this.statusBar.setMessage(0, "Checking in '" + var1.toString() + "' ...");
                this.setCursor(Cursor.getPredefinedCursor(3));

                try {
                    AccessPrivilege var7;
                    if (var3) {
                        var7 = this.getSavePrivilegeWithEsignature(var1);
                        this.getObjectFactory().save(var1, var7);
                    }

                    var7 = this.getUpdatePrivilegeWithEsignature(var1);
                    ((IAccessControl)var1).checkIn(var2, var7);
                    this.getObjectFactory().getUserManager().getServer().getTrxInfo().setSignature((DUserTicket)null);
                    this.getObjectFactory().getUserManager().getCurrentUser().clearApprover();
                } catch (Throwable var6) {
                    EventLog.dispatchException(var6, this, "doCheckIn()");
                }

                this.setCursor(Cursor.getPredefinedCursor(0));
                this.statusBar.setMessage(0, (String)null);
            }
        }
    }

    private void doCheckInControl(Object var1) {
        if (this.isSelectCollect) {
            this.doCheckIn(this.m_target);
        } else {
            this.doCheckIn(this.m_object);
        }

    }

    private void doCheckIn(IObjectNode var1) {
        if (var1 instanceof IObjectCollection) {
            List var2 = (List)var1.getObject();

            for(int var3 = 0; var3 < var2.size(); ++var3) {
                this.doCheckIn((Keyed)var2.get(var3));
            }
        } else if (var1 instanceof IObjectNode) {
            this.doCheckIn((Keyed)var1.getObject());
        }

    }

    private void doCheckOut(Keyed var1) {
        if (var1 instanceof IAccessControl) {
            if (!((IAccessControl)var1).isCheckedOut()) {
                if (((IAccessControl)var1).getAccessLevel() == 0) {
                    boolean var2 = true;
                    if (var1.isChanged()) {
                        String var3 = ClassUtility.getDisplayName(var1.getClass());
                        int var4 = JOptionPane.showConfirmDialog(this, var3 + " '" + var1.toString() + "' has been changed." + System.getProperty("line.separator") + "Do you want to refresh it from the database?", "Confirm Check Out", 1);
                        switch(var4) {
                            case 0:
                            default:
                                break;
                            case 1:
                                var2 = false;
                                break;
                            case 2:
                                return;
                        }
                    }

                    this.statusBar.setMessage(0, "Checking out '" + var1.toString() + "' ...");
                    this.setCursor(Cursor.getPredefinedCursor(3));

                    try {
                        AccessPrivilege var6 = this.getUpdatePrivilegeWithEsignature(var1);
                        ((IAccessControl)var1).checkOut(var2, var6);
                        this.getObjectFactory().getUserManager().getServer().getTrxInfo().setSignature((DUserTicket)null);
                        this.getObjectFactory().getUserManager().getCurrentUser().clearApprover();
                    } catch (Throwable var5) {
                        EventLog.dispatchException(var5, this, "doCheckOut()");
                    }

                    this.setCursor(Cursor.getPredefinedCursor(0));
                    this.statusBar.setMessage(0, (String)null);
                }
            }
        }
    }

    private void doCheckOutControl(Object var1) {
        if (this.isSelectCollect) {
            this.doCheckOut(this.m_target);
        } else {
            this.doCheckOut(this.m_object);
        }

    }

    private void doCheckOut(IObjectNode var1) {
        if (var1 instanceof IObjectCollection) {
            List var2 = (List)var1.getObject();

            for(int var3 = 0; var3 < var2.size(); ++var3) {
                this.doCheckOut((Keyed)var2.get(var3));
            }
        } else if (var1 instanceof IObjectNode) {
            this.doCheckOut((Keyed)var1.getObject());
        }

    }

    private void doClearCache() {
        DlgClearCache var1 = new DlgClearCache(this);
        if (var1.showDialog()) {
            Class[] var2 = var1.getResult();
            if (this.querySaveChanges(var2)) {
                this.clearCache(this.tView, (Class[])var2);
                this.clearCache(this.tSearch, (Class[])var2);
                this.clearCache(this.tFSMConfiguration, (Class[])var2);
            }
        }

        var1.dispose();
    }

    private void doClearCache(Object var1) {
        if (var1 instanceof FilteredCollection) {
            Class var2 = ((FilteredCollection)var1).getType();
            if (this.querySaveChanges(new Class[]{var2})) {
                this.clearCache(this.tView, (Class)var2);
                this.clearCache(this.tSearch, (Class)var2);
                this.clearCache(this.tFSMConfiguration, (Class)var2);
            }
        }

    }

    private void doClose(Keyed var1) {
        JInternalFrame[] var2 = this.rightPane.getAllFrames();

        for(int var3 = var2.length - 1; var3 >= 0; --var3) {
            if (var2[var3] instanceof EditFrame) {
                ((EditFrame)var2[var3]).close(var1);
            }
        }

    }

    private void doCloseAll() {
        this.closeFrame(this.ALL_TYPES);
        JInternalFrame[] var1 = this.rightPane.getAllFrames();

        for(int var2 = var1.length - 1; var2 >= 0; --var2) {
            try {
                var1[var2].setClosed(true);
            } catch (Throwable var4) {
                EventLog.dispatchException(var4, this, "doCloseAll(" + var1[var2].getClass().getName() + ")");
            }
        }

    }

    private void doCompareEventSheets() {
        EventSheetSelection.showEventSheetSelectionDialog(this);
    }

    private void doCompareForms() {
        FormsSelection.showFormSelectionDialog(this);
    }

    private void doCompareSubroutines() {
        SubroutinesSelection.showSubroutineSelectionDialog(this);
    }

    private void doCopy() {
        this.todo();
    }

    private void doCut() {
        this.todo();
    }

    private void doDelete(Keyed var1) {
        if (var1 != null) {
            int var2 = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + ClassUtility.getDisplayName(var1.getClass()) + " '" + var1.toString() + "'?", "Confirm Delete", 0);
            if (var2 == 0) {
                AccessPrivilege var3 = this.getDeletePrivilegeWithEsignature(var1);
                this._delete(var1, var3);
            }
        }
    }

    private void doDelete(IObjectNode var1) {
        if (!(var1 instanceof IObjectCollection)) {
            if (var1 instanceof IObjectNode) {
                this.doDelete((Keyed)var1.getObject());
            }
        } else {
            int var2 = 1;
            List var3 = (List)var1.getObject();

            for(int var4 = var3.size() - 1; var4 >= 0; --var4) {
                Keyed var5 = (Keyed)var3.get(var4);
                switch(var2) {
                    case 0:
                    case 1:
                        DlgConfirm var6 = new DlgConfirm(this, "Are you sure you want to delete " + ClassUtility.getDisplayName(var5.getClass()) + " '" + var5.toString() + "'?", "Confirm Delete");
                        var2 = var6.showDialog();
                        var6.dispose();
                        if (var2 != 0 && var2 != -1) {
                            break;
                        }
                    case -1:
                        AccessPrivilege var7 = this.getDeletePrivilegeWithEsignature(var5);
                        this._delete(var5, var7);
                        break;
                    default:
                        return;
                }
            }
        }

    }

    private void doDelete(Object var1, Object var2) {
        if (var1 instanceof FilteredCollection && var2 instanceof FilterCollection) {
            ((FilterCollection)var2).removeObject(var1);
            this.getObjectFactory().saveFilters();
        }

    }

    private void doEditFilter(Object var1) {
        Filter var2;
        DlgFilterEditor var3;
        if (var1 instanceof FilteredCollection) {
            var2 = FilterFactory.dupFilter(((FilteredCollection)var1).getFilter());
            var3 = new DlgFilterEditor(this, var2);
            if (var3.showDialog()) {
                ((FilteredCollection)var1).setFilter(var2);
                this.setCursor(Cursor.getPredefinedCursor(3));
                ((FilteredCollection)var1).refresh();
                this.setCursor(Cursor.getPredefinedCursor(0));
                this.getObjectFactory().saveFilters();
                FilterUtility.setFilterAttributes(((FilteredCollection)var1).getType().toString(), ((FilteredCollection)var1).getFilter());
            }

            var3.dispose();
        } else if (var1 instanceof AuditFilteredCollection && ((AuditFilteredCollection)var1).getType() != UDADefinition.class) {
            var2 = FilterFactory.dupFilter(((AuditFilteredCollection)var1).getFilter());
            var3 = new DlgFilterEditor(this, var2);
            if (var3.showDialog()) {
                ((AuditFilteredCollection)var1).setFilter(var2);
                this.setCursor(Cursor.getPredefinedCursor(3));
                ((AuditFilteredCollection)var1).refresh();
                this.setCursor(Cursor.getPredefinedCursor(0));
                this.getObjectFactory().saveFilters();
            }

            var3.dispose();
        }

    }

    private void doExport(String var1, String var2, List var3) {
        this.setCursor(Cursor.getPredefinedCursor(3));

        try {
            DSXExporter.exportObjects(var3, var1, var2);
        } catch (Throwable var5) {
            EventLog.dispatchException(var5, this, "doExport(" + var1 + ",...)");
        }

        this.setCursor(Cursor.getPredefinedCursor(0));
    }

    private void doExport() {
        int var1 = JOptionPane.showConfirmDialog(this, "This could be a time consuming operation. All runtime objects will not be exported.\nAre you sure you want to proceed?", "Continue?", 0);
        if (var1 == 0) {
            DlgExport var2 = new DlgExport(this, this.getObjectFactory(), this.getObjectFactory().getExportCollections());
            Object[] var3 = var2.showDialog() ? var2.getResult() : null;
            var2.dispose();
            if (var3 != null) {
                boolean var4 = var3.length > 3 && var3[3] instanceof Boolean ? (Boolean)var3[3] : false;
                this.doExport((String)var3[0], (String)var3[1], (List)var3[2]);
                if (var3[2] instanceof List && !((List)var3[2]).isEmpty() && var4) {
                    String var5 = (String)var3[0];
                    if (!var5.trim().toUpperCase().endsWith(".DSX")) {
                        var5 = var5 + ".dsx";
                    }

                    File var6 = new File(var5);
                    if (var6.exists()) {
                        DSXUtilities.show(this, "Export Summary", var6);
                    }
                }
            }

        }
    }

    private void doImport() {
        DlgImport var1 = new DlgImport(this);
        Object[] var2 = var1.showDialog() ? (Object[])var1.getResult() : null;
        var1.dispose();
        if (var2 != null) {
            boolean var3 = var2.length > 3 && var2[3] instanceof Boolean ? (Boolean)var2[3] : false;
            boolean var4 = var2.length > 4 && var2[4] instanceof Boolean ? (Boolean)var2[4] : false;
            if (var4) {
                this.doImport2((DKeyed[])((DKeyed[])var2[0]), (DKeyed[])((DKeyed[])var2[1]), (String)var2[2], var3);
            } else {
                this.doImport((DKeyed[])((DKeyed[])var2[0]), (DKeyed[])((DKeyed[])var2[1]), (String)var2[2], var3);
            }
        }

    }

    private void doImport(DKeyed[] var1, DKeyed[] var2, String var3, boolean var4) {
        Buildtime.ImportLogCallback var5 = var4 ? new Buildtime.ImportLogCallback() : null;
        this.setCursor(Cursor.getPredefinedCursor(3));

        try {
            DSXImporter.importObjects(var1, var2, new Buildtime.ImportSecurityCallback(), new Buildtime.ImportOverWriteCallback(), new Buildtime.ImportAccessControlCallback(), new Buildtime.ImportStatusCallback(), new Buildtime.ImportExceptionHandlerCallback(), new Buildtime.ImportESignatureCallback(), var5);
        } catch (Throwable var7) {
            EventLog.dispatchException(var7, this, "doImport(" + var3 + ")");
        }

        this.setCursor(Cursor.getPredefinedCursor(0));
        this.doneImport();
        if (var5 != null) {
            var5.show();
        }

    }

    private void doImport2(DKeyed[] var1, DKeyed[] var2, String var3, boolean var4) {
        ImportTask var5 = new ImportTask(this, this.getObjectFactory(), var1, var2, var3, var4);
        var5.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent var1) {
                if ("done".equals(var1.getPropertyName())) {
                    Buildtime.this.doneImport();
                } else if ("reload".equals(var1.getPropertyName())) {
                    final DKeyed var2 = (DKeyed)var1.getNewValue();
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            try {
                                Buildtime.this.getObjectFactory().reload(var2);
                            } catch (Throwable var2x) {
                                EventLog.logException(var2x, this, "reload(" + ImportUtility.getDisplayName(var2) + ")");
                            }

                        }
                    });
                }

            }
        });
        var5.execute();
    }

    private void doneImport() {
        this.doRefreshCount();
        this.doViewSelected();
        IObjectCollection[] var1 = this.getObjectFactory().getActiveView().getCollections();

        for(int var2 = 0; var2 < var1.length; ++var2) {
            if (var1[var2] instanceof FilteredCollection) {
                ((FilteredCollection)var1[var2]).objectRefreshed((KeyedStatusEvent)null);
            }
        }

        this.tView.updateUI();
        this.statusBar.setMessage(0, (String)null);
    }

    private void doLogon(String var1, String var2) throws Buildtime.PasswordExpiredException {
        this.doLogon(var1, var2, 9999);
    }

    private void doLogon(String var1, String var2, int var3) throws Buildtime.PasswordExpiredException {
        if (this.getObjectFactory() != null) {
            try {
                this.setCursor(Cursor.getPredefinedCursor(3));
                User var4 = this.getObjectFactory().login(var1, var2);
                this.getObjectFactory().getMessagesManager().getSystemErrorMessages();
                if (this.getObjectFactory().isLoggedIn()) {
                    if (!var4.hasPrivilege("ProcessDesigner") && !var4.hasPrivilege("ProcessModeler")) {
                        this.bHasPrivilagelogin = false;
                        this.getObjectFactory().logout();
                        this.setCursor(Cursor.getPredefinedCursor(0));
                        JOptionPane.showMessageDialog(this, "You do not have ProcessDesigner or ProcessModeler privileges." + System.getProperty("line.separator") + "The system could not log you on to Process Designer.", "Insufficient Privilege", 0);
                    } else {
                        Calendar var5 = Calendar.getInstance();
                        var5.add(5, 7);
                        if (this.isDatasweepSecurity() && var4.isPasswordModifiable()) {
                            int var6;
                            if (var4.getStatus().equals("ChangePassword")) {
                                var6 = JOptionPane.showConfirmDialog(this, "You are required to change your password now.", "Logon Message", -1);
                                this.doChangePassword(var4.getName());
                            } else if (var4.getPasswordExpiration().getCalendar().getTime().compareTo(var5.getTime()) <= 0) {
                                var6 = JOptionPane.showConfirmDialog(this, "Your password will expire on " + var4.getPasswordExpiration().getCalendar().getTime().toString() + ", would you like to change it now?", "Logon Message", 0);
                                if (var6 == 0) {
                                    this.doChangePassword(var4.getName());
                                }
                            }
                        } else if (this.isDatasweepSecurity() && !var4.isPasswordModifiable() && var4.getStatus().equals("ChangePassword")) {
                            JOptionPane.showMessageDialog(this.m_frame, MessagesUtil.getMessageByKey("loginfailed.disableorexpired"), "Logon Message", 0);
                        }

                        if (this.isDatasweepSecurity() && var4.getStatus().equals("ChangePassword")) {
                            this.setCursor(Cursor.getPredefinedCursor(0));
                            this.getObjectFactory().logout();
                            return;
                        }

                        this.tView.setModel(this.getObjectFactory().getActiveView().createTreeModel());
                        this.searchInit();
                        this.tConfiguration.setModel(this.getObjectFactory().getConfigurationView().createTreeModel());
                        this.tHistory.setModel(this.getObjectFactory().getHistoryView().createTreeModel());
                        this.tFSMConfiguration.setModel(this.getObjectFactory().getFSMView().createTreeModel());
                        this.tVersionPlatform.setModel(this.getObjectFactory().getVPVersionConfigurationView().createTreeModel());
                        app.versioningPlatformTab.retrieveGlobalPropertyConfiguration();
                        this.setCursor(Cursor.getPredefinedCursor(0));
                    }
                }
            } catch (Throwable var7) {
                this.setCursor(Cursor.getPredefinedCursor(0));
                if (this.isPasswordExpired(var7)) {
                    throw new Buildtime.PasswordExpiredException();
                }

                if (var3 > 1) {
                    if (var7 instanceof DatasweepException) {
                        EventLog.dispatchException(var7, this, var7.getMessage());
                    } else {
                        EventLog.dispatchException(var7, this, "doLogon(" + var1 + ", ...)");
                    }
                }
            }

            this.setupButton(this.getObjectFactory().isLoggedIn());
            this.tView.setSelectionRow(0);
            this.tView.requestFocus();
        }
    }

    private void doLogon() {
        JFrame var1 = new JFrame();
        var1.setIconImage((new ImageIcon(Buildtime.class.getResource("Buildtime.gif"))).getImage());
        var1.setUndecorated(true);
        var1.setVisible(true);
        var1.setLocationRelativeTo((Component)null);
        DlgLogon var2 = new DlgLogon(var1);
        var2.setModalityType(ModalityType.TOOLKIT_MODAL);
        String[] var3 = null;
        int var4 = 3;
        this.bHasPrivilagelogin = true;

        try {
            while(var2.showDialog()) {
                var3 = var2.getResult();
                this.doLogon(var3[0], var3[1], var4);
                if (this.getObjectFactory().isLoggedIn()) {
                    break;
                }

                --var4;
                if (var4 <= 0) {
                    break;
                }
            }

            if (var4 <= 0 && this.bHasPrivilagelogin) {
                JOptionPane.showMessageDialog(this.m_frame, MessagesUtil.getMessageByKey("loginfailed.disableorexpired"), "Logon Message", 0);
            }
        } catch (Buildtime.PasswordExpiredException var9) {
            JOptionPane.showMessageDialog(this.m_frame, "Your password has expired, you are required to change your password now.", "Logon Message", 0);
            this.doChangePassword(var3[0]);
        } finally {
            var1.dispose();
            var2.dispose();
        }

    }

    private void doLogoff() {
        if (this.getObjectFactory() != null) {
            if (this.querySaveChanges(this.ALL_TYPES)) {
                this.clearCache(this.tView, (Class[])ObjectFactory.ACTIVE_VIEW_CLASSES);
                this.clearCache(this.tSearch, (Class[])ObjectFactory.FILTERED_VIEW_CLASSES);
                this.clearCache(this.tConfiguration, (Class[])ObjectFactory.CONFIGURATION_VIEW_CLASSES);
                this.clearCache(this.tFSMConfiguration, (Class[])ObjectFactory.FSM_CONFIGURATION_VIEW_CLASSES);
                this.clearCache(this.tVersionPlatform, (Class[])ObjectFactory.VPVERSION_CONFIGURATION_VIEW_CLASSES);
                this.refreshView((Object)null);
                this.doCloseAll();
                this.setCursor(Cursor.getPredefinedCursor(3));

                try {
                    this.getObjectFactory().reSetLoginInfo();
                    this.getObjectFactory().logout();
                } catch (Throwable var2) {
                    EventLog.dispatchException(var2, this, "doLogoff()");
                }

                this.setCursor(Cursor.getPredefinedCursor(0));
                this.setupButton(this.getObjectFactory().isLoggedIn());
                ((TreeNodeAdapter)this.tView.getModel().getRoot()).clear();
                ((TreeNodeAdapter)this.tSearch.getModel().getRoot()).clear();
                ((TreeNodeAdapter)this.tConfiguration.getModel().getRoot()).clear();
                ((TreeNodeAdapter)this.tHistory.getModel().getRoot()).clear();
                ((TreeNodeAdapter)this.tFSMConfiguration.getModel().getRoot()).clear();
                ((TreeNodeAdapter)this.tVersionPlatform.getModel().getRoot()).clear();
                this.tView.setModel((TreeModel)null);
                this.tSearch.setModel((TreeModel)null);
                this.tConfiguration.setModel((TreeModel)null);
                this.tHistory.setModel((TreeModel)null);
                this.tFSMConfiguration.setModel((TreeModel)null);
                this.tVersionPlatform.setModel((TreeModel)null);
                this.getObjectFactory().clearViews();
                this.permissionCache = null;
                this.miChangePassword.setEnabled(false);
                System.gc();
            }
        }
    }

    private void doNew(Class var1) {
        if (var1 != null && var1 != VPVersionConfiguration.class && var1 != FSMConfiguration.class) {
            try {
                Filter var2 = null;
                short var3 = 0;
                Object[] var4 = null;
                Object var5 = null;
                boolean var6 = false;
                TemplateRecipe var7 = null;
                ProcessBillOfMaterials var8 = null;
                Vector var9 = null;
                int var11;
                if (var1 == Carrier.class) {
                    Buildtime.CarrierType var20 = new Buildtime.CarrierType();
                    var11 = JOptionPane.showOptionDialog(this, new Object[]{var20}, "New " + ClassUtility.getDisplayName(Carrier.class), -1, -1, (Icon)null, (Object[])null, (Object)null);
                    if (var11 != 0) {
                        return;
                    }

                    var3 = var20.getType();
                } else if (var1 == NamedFilter.class) {
                    DlgFilterType var19 = new DlgFilterType(this);
                    var2 = var19.showDialog() ? var19.getResult() : null;
                    var19.dispose();
                    if (var2 == null) {
                        return;
                    }
                } else if (var1 == ProcessBillOfMaterials.class) {
                    Buildtime.ProcessBomPanel var18 = new Buildtime.ProcessBomPanel();
                    var11 = JOptionPane.showOptionDialog(this, new Object[]{var18}, "New " + ClassUtility.getDisplayName(ProcessBillOfMaterials.class), -1, -1, (Icon)null, (Object[])null, (Object)null);
                    var4 = var18.getArgs();
                    if (var11 != 0) {
                        return;
                    }

                    if (var4 == null || var4.length != 1) {
                        return;
                    }

                    if (!(var4[0] instanceof Part)) {
                        JOptionPane.showMessageDialog(this, "Required " + ClassUtility.getDisplayName(Part.class) + " missing", "Errors", 0);
                        return;
                    }
                } else if (var1 == ReportDesign.class) {
                    Buildtime.ReportDesignPanel var17 = new Buildtime.ReportDesignPanel();
                    var11 = JOptionPane.showOptionDialog(this, new Object[]{var17}, "New " + ClassUtility.getDisplayName(ReportDesign.class), -1, -1, (Icon)null, (Object[])null, (Object)null);
                    var4 = var17.getArgs();
                    if (var11 != 0) {
                        return;
                    }

                    if (var4 == null || var4.length != 1) {
                        return;
                    }

                    if (!(var4[0] instanceof ReportDataDefinition)) {
                        JOptionPane.showMessageDialog(this, "Required " + ClassUtility.getDisplayName(ReportDataDefinition.class) + " missing", "Errors", 0);
                        return;
                    }
                } else if (var1 == MasterRecipe.class) {
                    Buildtime.MasterRecipeCreationPanel var16 = new Buildtime.MasterRecipeCreationPanel();
                    var11 = JOptionPane.showOptionDialog(this, new Object[]{var16}, "New " + ClassUtility.getDisplayName(MasterRecipe.class), 2, -1, (Icon)null, (Object[])null, (Object)null);
                    if (var11 != 0) {
                        return;
                    }

                    if (var16.createFromPBOM_ROUTE()) {
                        var4 = var16.masterRecipePanel.getArgs();
                        if (var11 != 0) {
                            return;
                        }

                        if (var4 == null || var4.length != 2) {
                            return;
                        }

                        if (!(var4[0] instanceof ProcessBillOfMaterials)) {
                            JOptionPane.showMessageDialog(this, "Required " + ClassUtility.getDisplayName(ProcessBillOfMaterials.class) + " missing", "Errors", 0);
                            return;
                        }

                        if (!(var4[1] instanceof Route)) {
                            JOptionPane.showMessageDialog(this, "Required " + ClassUtility.getDisplayName(Route.class) + " missing", "Errors", 0);
                            return;
                        }
                    } else {
                        var6 = true;
                        var7 = var16.getSelectedTemplateRecipe();
                        var8 = var16.getSlectedPBOM();
                        var9 = var16.getParts();
                    }
                } else if (var1 == TemplateRecipe.class) {
                    Buildtime.TemplateRecipePanel var10 = new Buildtime.TemplateRecipePanel();
                    var11 = JOptionPane.showOptionDialog(this, var10, "New " + ClassUtility.getDisplayName(TemplateRecipe.class), 2, -1, (Icon)null, (Object[])null, (Object)null);
                    var5 = var10.getSelectedRoute();
                    if (var11 != 0 || var5 == null || !(var5 instanceof Route)) {
                        return;
                    }
                }

                String[] var21;
                if (var2 != null) {
                    if (var2 instanceof AddOnFilter) {
                        var21 = this.generateName(var1, "Add-OnFilter");
                    } else {
                        var21 = this.generateName(var1, Utility.stripClassName(var2.getClass().getName()));
                    }
                } else if (var1 == Carrier.class && var3 == 1) {
                    var21 = this.generateName(var1, "Bin");
                } else if (var1 == Carrier.class && var3 == 2) {
                    var21 = this.generateName(var1, "Inventory");
                } else if (var1 == ProcessBillOfMaterials.class) {
                    var21 = this.generateName(var1, ((Part)var4[0]).getPartNumber() + " BoM");
                } else if (var1 == MasterRecipe.class) {
                    if (var4 != null) {
                        var21 = this.generateName(var1, ((ProcessBillOfMaterials)var4[0]).getPartProduced().getPartNumber() + " Recipe");
                    } else if (var8 != null) {
                        var21 = this.generateName(var1, var8.getPartProduced().getPartNumber() + " Recipe");
                    } else {
                        var21 = this.generateName(var1, "MasterRecipe");
                    }
                } else if (var1 == DataDictionary.class) {
                    String var23 = JOptionPane.showInputDialog(this, "Name:", ExtendedBeanInfoFactory.getInstance(this.getObjectFactory()).getDataDictionaryNameSpace());
                    if (var23 == null || var23.length() == 0) {
                        return;
                    }

                    DataDictionary var12 = (DataDictionary)this.getObjectFactory().getDataDictionaryManager().getObject(var23);
                    if (var12 != null) {
                        this.onEditEvent(new EditEvent(var12, 0));
                        return;
                    }

                    var21 = new String[]{var23};
                } else if (var1 == UnitOfMeasure.class) {
                    var21 = this.generateName(var1, "UOM");
                } else {
                    var21 = this.generateName(var1);
                }

                Object var24 = null;
                if (var6) {
                    if (var7 != null && var8 != null) {
                        try {
                            if (var9.size() > 0) {
                                var24 = var7.createMasterRecipe_(var21[0], var8, var9, true, false);
                            } else {
                                var24 = var7.createMasterRecipe_(var21[0], var8, true, false);
                            }

                            this.getObjectFactory().getMasterRecipeManager().registerMasterRecipeCreatedFromTemplateRecipe((MasterRecipe)var24);
                            this.getObjectFactory().getCollection(var24).addObject(var24);
                        } catch (Exception var14) {
                            JOptionPane.showMessageDialog(this, var14.getMessage(), "Creating MasterRecipe from TemplateRecipe", 0);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid input data to create MasterRecipe", "Creating MasterRecipe from TemplateRecipe", 0);
                    }
                } else {
                    var24 = this.getObjectFactory().create(var1, var21);
                    if (var1 == ActivitySet.class) {
                        ActivitySet var22 = (ActivitySet)var24;
                        ActivitySetTransition var13 = var22.addTransition(var22.getInitialStep(), var22.getTerminalStep());
                        var13.setLocation(var22.getGridSize().width * 12, var22.getGridSize().height * 7);
                    } else if (var1 == Carrier.class) {
                        switch(var3) {
                            case 0:
                            default:
                                ((Carrier)var24).setTypeNormal();
                                break;
                            case 1:
                                ((Carrier)var24).setTypeBin();
                                break;
                            case 2:
                                ((Carrier)var24).setTypeInventory();
                        }
                    } else if (var1 == NamedFilter.class) {
                        ((NamedFilter)var24).setFilter(var2);
                    } else if (var1 == ProcessBillOfMaterials.class) {
                        ((ProcessBillOfMaterials)var24).setPartProduced((Part)var4[0]);
                    } else if (var1 == ReportDesign.class) {
                        ((ReportDesign)var24).setReportDataDefinition((ReportDataDefinition)var4[0]);
                    } else if (var1 == MasterRecipe.class) {
                        if (var4 != null) {
                            ((MasterRecipe)var24).setProcessBOM((ProcessBillOfMaterials)var4[0]);
                            ((MasterRecipe)var24).setRoute((Route)var4[1]);
                        }
                    } else if (var1 == TemplateRecipe.class) {
                        ((TemplateRecipe)var24).setRoute((Route)var5);
                    }
                }

                this.onEditEvent(new EditEvent(var24, 0));
            } catch (Throwable var15) {
                EventLog.dispatchException(var15, this, "doNew(" + var1.getName() + ")");
                var15.printStackTrace();
            }

        }
    }

    private void doNew(IObjectNode var1) {
        if (var1 != null) {
            this.doNew(var1.getType());
        }

    }

    private void doNewSearch(Object var1) {
        if (var1 instanceof FilterCollection) {
            FilteredCollection var2 = ((FilterCollection)var1).create();
            DlgFilterEditor var3 = new DlgFilterEditor(this, var2.getFilter());
            if (var3.showDialog()) {
                ((FilterCollection)var1).addObject(var2);
                this.getObjectFactory().saveFilters();
            }

            var3.dispose();
        }

    }

    private void doOpen(Object var1) {
        if (var1 != null) {
            this.onEditEvent(new EditEvent(var1, 0));
        }
    }

    private void doPaste() {
        this.todo();
    }

    private void doPrint() {
        this.todo();
    }

    private void doAccessPrivilege(IObjectNode var1) {
        Class var2 = null;
        DlgAccessPrivilege var3 = null;
        if (var1 instanceof IObjectCollection) {
            var2 = ((IObjectCollection)this.m_target).getType();
            var3 = new DlgAccessPrivilege(this.m_frame, false, this.getObjectFactory(), var2, this.m_object);
        } else if (var1 instanceof IObjectNode) {
            var2 = this.m_target.getType();
            var3 = new DlgAccessPrivilege(this.m_frame, true, this.getObjectFactory(), var2, this.m_object);
        }

        if (var3 != null) {
            var3.show();
        }

    }

    private void doReadOnly(Keyed var1) {
        if (var1 instanceof IAccessControl) {
            if (!((IAccessControl)var1).isCheckedOut()) {
                this.setCursor(Cursor.getPredefinedCursor(3));

                try {
                    AccessPrivilege var2 = this.getUpdatePrivilegeWithEsignature(var1);
                    ((IAccessControl)var1).setAccessLevel((short)1, var2);
                    this.getObjectFactory().getUserManager().getServer().getTrxInfo().setSignature((DUserTicket)null);
                    this.getObjectFactory().getUserManager().getCurrentUser().clearApprover();
                } catch (Throwable var3) {
                    EventLog.dispatchException(var3, this, "doReadOnly()");
                }

                this.setCursor(Cursor.getPredefinedCursor(0));
            }
        }
    }

    private void doReadOnlyControl(Object var1) {
        if (this.isSelectCollect) {
            this.doReadOnly(this.m_target);
        } else {
            this.doReadOnly(this.m_object);
        }

    }

    private void doReadOnly(IObjectNode var1) {
        if (var1 instanceof IObjectCollection) {
            ListIterator var2 = ((List)var1.getObject()).listIterator();

            while(var2.hasNext()) {
                this.doReadOnly((Keyed)var2.next());
            }
        } else if (var1 instanceof IObjectNode) {
            this.doReadOnly((Keyed)var1.getObject());
        }

    }

    private void doReadWriteControl(Object var1) {
        if (this.isSelectCollect) {
            this.doReadWrite(this.m_target);
        } else {
            this.doReadWrite(this.m_object);
        }

    }

    private void doReadWrite(Keyed var1) {
        if (var1 instanceof IAccessControl) {
            if (!((IAccessControl)var1).isCheckedOut()) {
                this.setCursor(Cursor.getPredefinedCursor(3));

                try {
                    AccessPrivilege var2 = this.getUpdatePrivilegeWithEsignature(var1);
                    ((IAccessControl)var1).setAccessLevel((short)0, var2);
                    this.getObjectFactory().getUserManager().getServer().getTrxInfo().setSignature((DUserTicket)null);
                    this.getObjectFactory().getUserManager().getCurrentUser().clearApprover();
                } catch (Throwable var3) {
                    EventLog.dispatchException(var3, this, "doReadWrite()");
                }

                this.setCursor(Cursor.getPredefinedCursor(0));
            }
        }
    }

    private void doReadWrite(IObjectNode var1) {
        if (var1 instanceof IObjectCollection) {
            ListIterator var2 = ((List)var1.getObject()).listIterator();

            while(var2.hasNext()) {
                this.doReadWrite((Keyed)var2.next());
            }
        } else if (var1 instanceof IObjectNode) {
            this.doReadWrite((Keyed)var1.getObject());
        }

    }

    private void doRefresh() {
        if (this.querySaveChanges(this.ALL_TYPES)) {
            this.clearCache(this.tView, (Class[])ObjectFactory.ACTIVE_VIEW_CLASSES);
            this.clearCache(this.tSearch, (Class[])ObjectFactory.FILTERED_VIEW_CLASSES);
            this.clearCache(this.tConfiguration, (Class[])ObjectFactory.CONFIGURATION_VIEW_CLASSES);
            this.clearCache(this.tFSMConfiguration, (Class[])ObjectFactory.FSM_CONFIGURATION_VIEW_CLASSES);
            this.clearCache(this.tVersionPlatform, (Class[])ObjectFactory.VPVERSION_CONFIGURATION_VIEW_CLASSES);
            this.doReset();
            this.tView.clearSelection();
            this.tSearch.clearSelection();
            this.tConfiguration.clearSelection();
            this.tHistory.clearSelection();
            this.tFSMConfiguration.clearSelection();
            this.tVersionPlatform.clearSelection();
            System.gc();
        }
    }

    private void doRefresh(Object var1) {
        this.setCursor(Cursor.getPredefinedCursor(3));
        if (var1 instanceof FilteredCollection) {
            ((FilteredCollection)var1).refresh();
        } else if (var1 instanceof FilterCollection) {
            int var2 = this.getObjectFactory().getATRowAt();
            int var3 = this.getObjectFactory().getSTAInstanceAt();
            ObjectTreeNode[] var5;
            int var6;
            DefaultTreeNode var7;
            if (((FilterCollection)var1).getType().equals(ATRow.class) && ((FilterCollection)var1).getOwnerObjectName() == null) {
                var7 = (DefaultTreeNode)((DefaultTreeModel)this.tSearch.getModel()).getRoot();
                ((ObjectTreeNode)var7.getChildAt(var2)).removeAllChildren();
                var5 = this.getObjectFactory().getATRowFilterView(true).createNodeModel();

                for(var6 = 0; var6 < var5.length; ++var6) {
                    ((ObjectTreeNode)var7.getChildAt(var2)).add(var5[var6]);
                }

                ((DefaultTreeModel)this.tSearch.getModel()).reload((ObjectTreeNode)var7.getChildAt(var2));
            } else if (((FilterCollection)var1).getType().equals(STAInstance.class) && ((FilterCollection)var1).getOwnerObjectName() == null) {
                var7 = (DefaultTreeNode)((DefaultTreeModel)this.tSearch.getModel()).getRoot();
                ((ObjectTreeNode)var7.getChildAt(var3)).removeAllChildren();
                var5 = this.getObjectFactory().getSTAInstanceFilterView(true).createNodeModel();

                for(var6 = 0; var6 < var5.length; ++var6) {
                    ((ObjectTreeNode)var7.getChildAt(var3)).add(var5[var6]);
                }

                ((DefaultTreeModel)this.tSearch.getModel()).reload((ObjectTreeNode)var7.getChildAt(var3));
            } else {
                ListIterator var4 = ((FilterCollection)var1).getChildren().listIterator();

                while(var4.hasNext()) {
                    ((FilteredCollection)var4.next()).refresh();
                }
            }
        } else if (var1 instanceof AuditFilteredCollection) {
            ((AuditFilteredCollection)var1).refresh();
        } else if (var1 instanceof RevisionCollection) {
            ((RevisionCollection)var1).refresh();
        } else if (var1 instanceof VPConfigurationCollection) {
            ((VPConfigurationCollection)var1).refresh();
        }

        this.setCursor(Cursor.getPredefinedCursor(0));
    }

    private void doRefreshCount() {
        this.getObjectFactory().clearCount();
    }

    private void doReset() {
        if (this.getObjectFactory() != null && this.getObjectFactory().isLoggedIn()) {
            try {
                TreePath var1 = new TreePath(this.tHistory.getModel().getRoot());
                this.tHistory.collapsePath(var1);
                IObjectCollection[] var2 = this.getObjectFactory().getHistoryView().getCollections();

                for(int var3 = 0; var3 < var2.length; ++var3) {
                    ((AuditFilteredCollection)var2[var3]).reset();
                }

                this.tHistory.expandPath(var1);
            } catch (Throwable var4) {
                EventLog.dispatchException(var4, this, "doReset()");
            }

        }
    }

    private void doRestore(Keyed var1) {
        if (var1 != null) {
            if (var1.isChanged()) {
                this.statusBar.setMessage(0, "Restoring '" + var1.toString() + "' ...");
                this.setCursor(Cursor.getPredefinedCursor(3));

                try {
                    this.getObjectFactory().refresh(var1);
                } catch (Throwable var3) {
                    EventLog.dispatchException(var3, this, "doRestore(" + var1.toString() + ")");
                }

                this.setCursor(Cursor.getPredefinedCursor(0));
                this.statusBar.setMessage(0, (String)null);
            }
        }
    }

    private void doRestoreControl(Object var1) {
        if (this.isSelectCollect) {
            this.doRestore(this.m_target);
        } else {
            this.doRestore(this.m_object);
        }

    }

    private void doRestore(IObjectNode var1) {
        if (var1 instanceof IObjectCollection) {
            ListIterator var2 = ((List)var1.getObject()).listIterator();

            while(var2.hasNext()) {
                this.doRestore((Keyed)var2.next());
            }
        } else if (var1 instanceof IObjectNode) {
            this.doRestore((Keyed)var1.getObject());
            if (var1 instanceof CompatibilityObject) {
                ((CompatibilityObject)var1).restoreObject();
            }
        }

    }

    private void doSave(Keyed var1) {
        if (var1 != null) {
            if (var1.isChanged() || var1.isNew()) {
                if (var1 instanceof Locale && ((Locale)var1).isSystemDefined()) {
                    JOptionPane.showMessageDialog(this, "System-Defined Locales cannot be deleted or modified.");
                } else {
                    this.statusBar.setMessage(0, "Saving '" + var1.toString() + "' ...");
                    this.setCursor(Cursor.getPredefinedCursor(3));

                    try {
                        if (var1 instanceof IAccessControl) {
                            this._save((IAccessControl)var1);
                        } else {
                            AccessPrivilege var2 = this.getSavePrivilegeWithEsignature(var1);
                            this.getObjectFactory().save(var1, var2);
                        }
                    } catch (Throwable var3) {
                        EventLog.dispatchException(var3, this, "doSave(" + var1.toString() + ")");
                    }

                    this.setCursor(Cursor.getPredefinedCursor(0));
                    this.statusBar.setMessage(0, (String)null);
                }
            }
        }
    }

    private void doSaveControl(Object var1) {
        if (this.isSelectCollect) {
            this.doSave(this.m_target);
        } else {
            this.doSave(this.m_object);
        }

    }

    private void doSave(IObjectNode var1) {
        if (var1 instanceof IObjectCollection) {
            ListIterator var2 = ((List)var1.getObject()).listIterator();

            while(var2.hasNext()) {
                this.doSave((Keyed)var2.next());
            }
        } else if (var1 instanceof IObjectNode) {
            this.doSave((Keyed)var1.getObject());
        }

    }

    private void doSaveAll() {
        this.saveObjects(this.getChangedObjects(this.ALL_TYPES));
    }

    private void doSaveAs(Keyed var1) {
        if (var1 != null && !(var1 instanceof Locale)) {
            DlgSaveAs var2 = new DlgSaveAs(this, var1);
            String[] var3 = var2.showDialog() ? var2.getResult() : null;
            var2.dispose();
            if (var3 != null) {
                Keyed var4 = null;

                try {
                    var4 = this.getObjectFactory().dup(var1, var3);
                    if (var4 != null) {
                        this.onEditEvent(new EditEvent(var4, 0));
                    }
                } catch (Throwable var6) {
                    EventLog.dispatchException(var6, this, "doSaveAs(" + var1.toString() + ")");
                }

                this.doSave(var4);
            }

        }
    }

    private void doShow(Class var1) {
        Object var2 = this.m_mapObject.get(var1);
        if (var2 instanceof AbstractClient) {
            this.onEditEvent(new EditEvent(var2, 0));
        }

    }

    private void doShowVersions(Keyed var1) {
        DlgShowVersions var2 = new DlgShowVersions(this, var1);
        var2.showDialog();
        var2.dispose();
    }

    private void doShowVersionsControl(Object var1) {
        if (this.isSelectCollect) {
            this.doShowVersions(this.m_target);
        } else {
            this.doShowVersions(this.m_object);
        }

    }

    private void doShowVersions(IObjectNode var1) {
        if (!(var1 instanceof IObjectCollection) && var1 instanceof IObjectNode) {
            this.doShowVersions((Keyed)var1.getObject());
        }

    }

    private void doStatusBar() {
        this.statusBar.setVisible(this.miStatusBar.isSelected());
    }

    private void doTileHorizontally() {
        JInternalFrame[] var1 = this.rightPane.getAllFrames();
        if (var1 != null && var1.length != 0) {
            Dimension var2 = this.rightPane.getSize();
            int var3 = var2.width;
            int var4 = var2.height / var1.length;
            byte var5 = 0;
            int var6 = 0;
            var2 = new Dimension(var3, var4);

            for(int var7 = 0; var7 < var1.length; ++var7) {
                if (var1[var7].isMaximum()) {
                    try {
                        var1[var7].setMaximum(false);
                    } catch (Exception var9) {
                    }
                }

                var1[var7].setSize(var2);
                var1[var7].setLocation(var5, var6);
                var6 += var4;
            }

        }
    }

    private void doToolbar() {
        this.toolBar.setVisible(this.miToolbar.isSelected());
    }

    private void doTileVertically() {
        JInternalFrame[] var1 = this.rightPane.getAllFrames();
        if (var1 != null && var1.length != 0) {
            Dimension var2 = this.rightPane.getSize();
            int var3 = var2.width / var1.length;
            int var4 = var2.height;
            int var5 = 0;
            byte var6 = 0;
            var2 = new Dimension(var3, var4);

            for(int var7 = 0; var7 < var1.length; ++var7) {
                if (var1[var7].isMaximum()) {
                    try {
                        var1[var7].setMaximum(false);
                    } catch (Exception var9) {
                    }
                }

                var1[var7].setSize(var2);
                var1[var7].setLocation(var5, var6);
                var5 += var3;
            }

        }
    }

    private void doTopics() {
        try {
            String var1 = this.getObjectFactory().getServerInfo().getDownloadsURL();
            if (var1 != null && !var1.equalsIgnoreCase("null")) {
                URL var2 = new URL(var1);
                String var3 = var2.toString() + "/docs/help/pd/index%2Ehtm";
                BrowserControl.displayURL(var3);
            } else {
                JOptionPane.showMessageDialog(this, "The Help system has not been configured." + System.getProperty("line.separator") + "Please contact the Administrator.", "Error", 0);
            }
        } catch (Throwable var4) {
            JOptionPane.showMessageDialog(this, "The Help system has not been configured correctly." + System.getProperty("line.separator") + "Please contact the Administrator.", "Error", 0);
            EventLog.dispatchException(var4, this, "doTopics()");
        }

    }

    private void doTransfer() {
        this.todo();
    }

    private void doUndoCheckOut(Keyed var1) {
        if (var1 instanceof IAccessControl) {
            if (((IAccessControl)var1).isCheckedOut()) {
                int var2 = JOptionPane.showConfirmDialog(this, "Are you sure you want to undo check out " + ClassUtility.getDisplayName(var1.getClass()) + " '" + var1.toString() + "' and lose changes?", "Confirm Undo Check Out", 0);
                if (var2 == 0) {
                    this._undoCheckOut((IAccessControl)var1);
                }
            }
        }
    }

    private void doUndoCheckOutControl(Object var1) {
        if (this.isSelectCollect) {
            this.doUndoCheckOut(this.m_target);
        } else {
            this.doUndoCheckOut(this.m_object);
        }

    }

    private void doUndoCheckOut(IObjectNode var1) {
        if (!(var1 instanceof IObjectCollection)) {
            if (var1 instanceof IObjectNode) {
                this.doUndoCheckOut((Keyed)var1.getObject());
            }
        } else {
            int var2 = 1;
            List var3 = (List)var1.getObject();

            for(int var4 = var3.size() - 1; var4 >= 0; --var4) {
                Keyed var5 = (Keyed)var3.get(var4);
                if (var5 instanceof IAccessControl && ((IAccessControl)var5).isCheckedOut()) {
                    switch(var2) {
                        case 0:
                        case 1:
                            DlgConfirm var6 = new DlgConfirm(this, "Are you sure you want to undo check out " + ClassUtility.getDisplayName(var5.getClass()) + " '" + var5.toString() + "' and lose changes?", "Confirm Undo Check Out");
                            var2 = var6.showDialog();
                            var6.dispose();
                            if (var2 != 0 && var2 != -1) {
                                break;
                            }
                        case -1:
                            this._undoCheckOut((IAccessControl)var5);
                            break;
                        default:
                            return;
                    }
                }
            }
        }

    }

    private void doViewDelete() {
        Object var1 = this.cbView.getSelectedItem();
        if (var1 instanceof ViewFilter) {
            this.m_views.remove((ViewFilter)var1);
            this.refreshView((Object)null);
        }

    }

    private void doViewEdit() {
        Object var1 = this.cbView.getSelectedItem();
        if (var1 instanceof ViewFilter) {
            ViewFilter var2 = ((ViewFilter)var1).dup();
            DlgViewEditor var3 = new DlgViewEditor(this, var2, new Callback() {
                public boolean isValidName(ViewFilter var1) {
                    ViewFilter var2 = Buildtime.this.m_views.get(var1.getName());
                    return var2 == null || var2 == Buildtime.this.cbView.getSelectedItem();
                }
            });
            if (var3.showDialog()) {
                ((ViewFilter)var1).copy(var2);
                this.m_views.save((ViewFilter)var1);
                this.refreshView(var1);
            }

            var3.dispose();
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

    private void doViewNew() {
        ViewFilter var1 = this.m_views.create();
        DlgViewEditor var2 = new DlgViewEditor(this, var1, new Callback() {
            public boolean isValidName(ViewFilter var1) {
                ViewFilter var2 = Buildtime.this.m_views.get(var1.getName());
                return var2 == null;
            }
        });
        if (var2.showDialog()) {
            this.m_views.add(var1);
            this.refreshView(var1);
        }

        var2.dispose();
    }

    private void doViewRefresh() {
        Object var1 = this.cbView.getSelectedItem();
        if (var1 instanceof ViewFilter) {
            this.refreshView(var1);
        }

    }

    private void doViewSelected() {
        Object var1 = this.cbView.getSelectedItem();
        if (this.getObjectFactory() != null && this.getObjectFactory().isLoggedIn()) {
            IObjectCollection[] var2 = this.getObjectFactory().getActiveView().getCollections();

            for(int var3 = 0; var3 < var2.length; ++var3) {
                if (var2[var3] instanceof ObjectCollection) {
                    ((ObjectCollection)var2[var3]).setView((ViewFilter)var1);
                }
            }
        }

        this.browser.setTitleAt(0, var1 instanceof ViewFilter ? var1.toString() : "[All]");
        this.enableToolBarButtons();
    }

    private void doXRef(Keyed var1) {
        if (var1 != null && !(var1 instanceof UDADefinition) && !(var1 instanceof FSMConfiguration) && !(var1 instanceof VPVersionConfiguration)) {
            XRefResults var2 = null;
            this.setCursor(Cursor.getPredefinedCursor(3));

            try {
                var2 = this.getObjectFactory().xref(var1);
            } catch (Throwable var4) {
                EventLog.dispatchException(var4, this, "doXRef(" + var1.toString() + ")");
            }

            this.setCursor(Cursor.getPredefinedCursor(0));
            if (var2 != null) {
                Object var3 = null;
                if (!var2.hasReferences()) {
                    var3 = "No reference available for " + ClassUtility.getDisplayName(var1.getClass()) + " '" + var1.toString() + "'";
                } else {
                    var3 = new XRefView(var2);
                }

                JOptionPane.showMessageDialog(this, var3, "Cross Reference for '" + var1.toString() + "'", 1);
            }

        }
    }

    private void setMyTarget(Object var1) {
        if (var1 instanceof TreePath) {
            var1 = ((TreePath)var1).getLastPathComponent();
        }

        if (var1 instanceof ObjectTreeNode) {
            var1 = ((ObjectTreeNode)var1).getUserObject();
        }

        this.m_target = var1 instanceof IObjectNode ? (IObjectNode)var1 : null;
    }

    private void contextMenu_popup(MouseEvent var1) {
        TreePath var2 = null;
        if (var1.getComponent() instanceof JTree) {
            var2 = ((JTree)var1.getComponent()).getPathForLocation(var1.getX(), var1.getY());
        }

        this.setMyTarget(var2);
        if (this.m_target != null && !(this.m_target instanceof FilterCollection)) {
            boolean var7;
            boolean var8;
            boolean var11;
            boolean var12;
            if (!(this.m_target instanceof IObjectCollection)) {
                if (this.m_target instanceof IObjectNode) {
                    AbstractClient var17 = (AbstractClient)this.m_target.getObject();
                    String var18 = var17.toString();
                    Buildtime.Permission var19 = this.getPermission(var17);
                    boolean var20 = var19.canSave() && var17 instanceof IAccessControl && !var17.isNew() && !var17.isAudit();
                    var7 = var17 instanceof DeAnzaForm || var17 instanceof Subroutine || var17 instanceof EventSheetHolder || var17 instanceof AddOn;
                    var8 = var20 && this._isVersioning((IAccessControl)var17);
                    boolean var21 = var17 instanceof IAccessControl ? ((IAccessControl)var17).isCheckedOut() : false;
                    boolean var22 = var17 instanceof IAccessControl ? ((IAccessControl)var17).getAccessLevel() == 1 : false;
                    var11 = var17 instanceof IAccessControl ? ((IAccessControl)var17).getCheckedOutBy() == this.getCurrentUser() : false;
                    var12 = !(var17 instanceof DataDictionary);
                    this.cmiNew.setEnabled(false);
                    this.cmiNew.setText("New");
                    this.contextMenu.remove(this.cmiNew);
                    this.cmiOpen.setEnabled(var19.canOpen());
                    this.cmiOpen.setText("Open " + var18);
                    this.cmiSave.setEnabled(var19.canSave());
                    this.cmiSave.setText("Save " + var18);
                    this.cmiRestore.setEnabled(var19.canRestore());
                    this.cmiRestore.setText("Restore " + var18);
                    this.cmiDelete.setEnabled(var19.canDelete());
                    this.cmiDelete.setText("Delete " + var18);
                    this.cmAccessControl.setText("Access Control " + var18);
                    this.cmiReadOnly.setEnabled(var20 && !var21 && !var22);
                    this.cmiReadWrite.setEnabled(var20 && !var21 && var22);
                    this.cmiCheckIn.setEnabled(var8 && var21 && var11);
                    this.cmiCheckOut.setEnabled(var8 && !var21 && !var22);
                    this.cmiUndoCheckOut.setEnabled(var8 && var21 && (var11 || this.getCurrentUser().getPrivilege() == 4));
                    this.cmiShowVersions.setEnabled(var8 && var7);
                    this.cmiAccessPrivilege.setEnabled(var19.canSave() && var12);
                    if (this.browser.getSelectedIndex() != 0) {
                        this.cmiAccessPrivilege.setEnabled(false);
                    }
                }
            } else {
                if (this.m_target instanceof FilteredCollection) {
                    FilteredCollection var3 = (FilteredCollection)this.m_target;
                    if (!var3.isLoaded() && !FilterFactory.verbose(this, var3.getFilter())) {
                        return;
                    }

                    if (ATRowTypeHelper.isAnyATRowObjectType(var3.getFilter().getObjectType()) || var3.getFilter().getObjectType() == 186) {
                        return;
                    }
                }

                Object var16 = ((IObjectCollection)this.m_target).getObject();
                Class var4 = ((IObjectCollection)this.m_target).getType();
                int var5 = var16 instanceof List ? ((List)var16).size() : 0;
                String var6 = ClassUtility.getCollectionString(var4) + " (" + var5 + ")";
                var7 = this.isDatasweepSecurity();
                var8 = this.getObjectFactory().getUserSecurity(var4) == 'W';
                int var9 = 0;
                int var10 = 0;
                var11 = false;
                var12 = false;
                boolean var13 = false;
                if (var5 != 0) {
                    ListIterator var14 = ((List)var16).listIterator();

                    while(var14.hasNext()) {
                        AbstractClient var15 = (AbstractClient)var14.next();
                        if (var15.isChanged()) {
                            ++var9;
                        } else if (var15.isNew()) {
                            ++var10;
                        }
                    }

                    Object var23 = ((List)var16).get(0);
                    var11 = var8 && var23 instanceof IAccessControl;
                    var13 = var11 && this._isVersioning((IAccessControl)var23);
                }

                this.cmiNew.setEnabled(var8 && var4 != UDADefinition.class && var4 != FSMConfiguration.class && var4 != VPVersionConfiguration.class && (var4 != User.class || var7));
                this.cmiNew.setText("New " + ClassUtility.getDisplayName(var4));
                this.contextMenu.add(this.cmiNew, 0);
                this.cmiOpen.setEnabled(true);
                this.cmiOpen.setText("Open " + var6);
                this.cmiSave.setEnabled(var9 + var10 > 0 && var8);
                this.cmiSave.setText("Save " + var6);
                this.cmiRestore.setEnabled(var9 > 0);
                this.cmiRestore.setText("Restore " + var6);
                this.cmiDelete.setEnabled(var5 > 0 && var8 && var4 != UDADefinition.class && var4 != FSMConfiguration.class && var4 != VPVersionConfiguration.class && (var4 != User.class || var7));
                this.cmiDelete.setText("Delete " + var6);
                this.cmAccessControl.setText("Access Control " + var6);
                this.cmiReadOnly.setEnabled(var11);
                this.cmiReadWrite.setEnabled(var11);
                this.cmiCheckIn.setEnabled(var13);
                this.cmiCheckOut.setEnabled(var13);
                this.cmiUndoCheckOut.setEnabled(var13);
                this.cmiShowVersions.setEnabled(var13 && var12);
                this.cmiAccessPrivilege.setEnabled(var8);
                if (this.browser.getSelectedIndex() != 0) {
                    this.cmiAccessPrivilege.setEnabled(false);
                }
            }

            if (this.m_target != null) {
                this.contextMenu.show(var1.getComponent(), var1.getX(), var1.getY());
            }

        }
    }

    public void onExceptionEvent(ExceptionEvent var1) {
        Throwable var2 = var1.getThrowable();
        String var3 = var2.getMessage();
        if (var2 instanceof InvocationTargetException) {
            var2 = var2.getCause();
        }

        if (var2 instanceof DatasweepException) {
            Response var4 = ((DatasweepException)var2).getResponse();
            if (this.isPasswordComplexityException(var2)) {
                var3 = var4.getPasswordComplexityErrorMessage();
            } else if (var4 != null && var4.getErrors() != null && var4.getErrors().length == 1) {
                var3 = var4.getFirstErrorMessage();
            }
        }

        if (var3 == null) {
            var3 = Utility.stripClassName(var2.getClass().getName());
        }

        MessageBox.show(this, var3, "ProductionCentre Exception", 0);
    }

    public void onEditEvent(EditEvent var1) {
        Object var2 = var1.getSource();
        if (var1.getType() == 1) {
            if (var2 instanceof Keyed) {
                this.doSave((Keyed)var2);
            }

        } else if (var1.getType() == 2) {
            if (var2 instanceof Keyed) {
                this.doRestore((Keyed)var2);
            }

        } else if (var1.getType() == 16) {
            if (var2 instanceof Keyed) {
                this.doClose((Keyed)var2);
            }

        } else {
            if (var2 instanceof ApplicationItem) {
                Keyed var3 = ((ApplicationItem)var2).getReferencedObject();
                if (var3 != null) {
                    var2 = var3;
                }
            }

            if (var2 instanceof AbstractClient) {
                var2 = NodeFactory.getObjectNode(var2);
            }

            if (var2 instanceof IObjectNode && this.getPermission(((IObjectNode)var2).getType()).canOpen()) {
                this.setBusy(true);
                JInternalFrame var4 = (JInternalFrame)((IObjectNode)var2).getEditor();
                this.setBusy(false);
                if (var4 != null) {
                    this.setEditor(var4);
                }

                var2 = ((IObjectNode)var2).getObject();
                if (var2 instanceof Keyed) {
                    this.setObject((Keyed)var2);
                }
            }

        }
    }

    public void onSelectEvent(SelectEvent var1) {
        Object var2 = var1.getSource();
        if (var2 instanceof AuditFilteredCollection) {
            ((AuditFilteredCollection)var2).setEnabled(true);
            this.setSelectType(false, false);
        } else if (var2 instanceof RevisionCollection) {
            ((RevisionCollection)var2).setEnabled(true);
            this.setSelectType(false, false);
        } else if (var2 instanceof IObjectCollection) {
            this.setType(((IObjectCollection)var2).getType());
            this.setSelectType(true, false);
            this.setMyTarget(var2);
        } else if (var2 instanceof IObjectNode) {
            this.setMyTarget(var2);
            var2 = ((IObjectNode)var2).getObject();
            this.setSelectType(false, true);
        }

        if (var2 instanceof Keyed) {
            this.setObject((Keyed)var2);
        }

    }

    private void searchInit() {
        this.tSearch.setModel(this.getObjectFactory().getFilterView().createTreeModel());
        DefaultTreeNode var1 = (DefaultTreeNode)((DefaultTreeModel)this.tSearch.getModel()).getRoot();
        int var2 = this.getObjectFactory().getATRowAt();
        int var3 = this.getObjectFactory().getSTAInstanceAt();
        ObjectTreeNode[] var4;
        int var5;
        if (((ObjectTreeNode)var1.getChildAt(var2)).getChildCount() == 0) {
            var4 = this.getObjectFactory().getATRowFilterView(false).createNodeModel();

            for(var5 = 0; var5 < var4.length; ++var5) {
                ((ObjectTreeNode)var1.getChildAt(var2)).add(var4[var5]);
            }

            ((DefaultTreeModel)this.tSearch.getModel()).reload((ObjectTreeNode)var1.getChildAt(var2));
        }

        if (((ObjectTreeNode)var1.getChildAt(var3)).getChildCount() == 0) {
            var4 = this.getObjectFactory().getSTAInstanceFilterView(false).createNodeModel();

            for(var5 = 0; var5 < var4.length; ++var5) {
                ((ObjectTreeNode)var1.getChildAt(var3)).add(var4[var5]);
            }

            ((DefaultTreeModel)this.tSearch.getModel()).reload((ObjectTreeNode)var1.getChildAt(var3));
        }

    }

    public void setSelectType(boolean var1, boolean var2) {
        this.isSelectCollect = var1;
        this.isSelectObject = var2;
    }

    public void propertyChange(PropertyChangeEvent var1) {
        if ("name".equals(var1.getPropertyName())) {
            this.setObject(this.m_object);
        }

    }

    public void objectSaved(KeyedStatusEvent var1) {
    }

    public void objectRefreshed(KeyedStatusEvent var1) {
        this.setObject(this.m_object);
    }

    public void objectDeleted(KeyedStatusEvent var1) {
        this.setObject((Keyed)null);
    }

    public boolean isSelectCollect() {
        return this.isSelectCollect;
    }

    public void setSelectCollect(boolean var1) {
        this.isSelectCollect = var1;
    }

    public boolean isSelectObject() {
        return this.isSelectObject;
    }

    public void setSelectObject(boolean var1) {
        this.isSelectObject = var1;
    }

    private void initForm() {
        String var1 = System.getProperty("uiDefaultButtonFollowFocus", "false");
        if (var1.equalsIgnoreCase("true")) {
            UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
        }

        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        this.miNew.setAccelerator(KeyStroke.getKeyStroke(78, 2));
        this.miNew.setMnemonic(78);
        this.miNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doNew(Buildtime.this.m_type);
            }
        });
        this.miOpen.setAccelerator(KeyStroke.getKeyStroke(79, 2));
        this.miOpen.setMnemonic(79);
        this.miOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doOpen(Buildtime.this.m_object);
            }
        });
        this.miSave.setAccelerator(KeyStroke.getKeyStroke(83, 2));
        this.miSave.setMnemonic(83);
        this.miSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doSave(Buildtime.this.m_object);
            }
        });
        this.miSaveAs.setMnemonic(65);
        this.miSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doSaveAs(Buildtime.this.m_object);
            }
        });
        this.miSaveAll.setAccelerator(KeyStroke.getKeyStroke(83, 3));
        this.miSaveAll.setMnemonic(86);
        this.miSaveAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doSaveAll();
            }
        });
        this.miRestore.setAccelerator(KeyStroke.getKeyStroke(82, 2));
        this.miRestore.setMnemonic(82);
        this.miRestore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doRestore(Buildtime.this.m_object);
            }
        });
        this.miExport.setMnemonic(69);
        this.miExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doExport();
            }
        });
        this.miImport.setMnemonic(73);
        this.miImport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doImport();
            }
        });
        this.miTransfer.setMnemonic(84);
        this.miTransfer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doTransfer();
            }
        });
        this.miLogon.setAccelerator(KeyStroke.getKeyStroke(73, 2));
        this.miLogon.setMnemonic(71);
        this.miLogon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doLogon();
            }
        });
        this.miLogoff.setMnemonic(70);
        this.miLogoff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doLogoff();
            }
        });
        this.miChangePassword.setMnemonic(67);
        this.miChangePassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doChangePassword();
            }
        });
        this.miPrint.setAccelerator(KeyStroke.getKeyStroke(80, 2));
        this.miPrint.setMnemonic(80);
        this.miPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doPrint();
            }
        });
        this.miExit.setMnemonic(88);
        this.miExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.Buildtime_closing();
            }
        });
        this.menuFile.setMnemonic(70);
        this.menuFile.add(this.miNew);
        this.menuFile.add(this.miOpen);
        this.menuFile.addSeparator();
        this.menuFile.add(this.miSave);
        this.menuFile.add(this.miSaveAs);
        this.menuFile.add(this.miSaveAll);
        this.menuFile.add(this.miRestore);
        this.menuFile.addSeparator();
        this.menuFile.add(this.miExport);
        this.menuFile.add(this.miImport);
        this.menuFile.addSeparator();
        this.menuFile.add(this.miLogon);
        this.menuFile.add(this.miLogoff);
        this.menuFile.add(this.miChangePassword);
        this.menuFile.addSeparator();
        this.menuFile.add(this.miExit);
        this.miCut.setAccelerator(KeyStroke.getKeyStroke(88, 2));
        this.miCut.setMnemonic(84);
        this.miCut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doCut();
            }
        });
        this.miCopy.setAccelerator(KeyStroke.getKeyStroke(67, 2));
        this.miCopy.setMnemonic(67);
        this.miCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doCopy();
            }
        });
        this.miPaste.setAccelerator(KeyStroke.getKeyStroke(86, 2));
        this.miPaste.setMnemonic(80);
        this.miPaste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doPaste();
            }
        });
        this.miDelete.setMnemonic(68);
        this.miDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doDelete(Buildtime.this.m_object);
            }
        });
        this.miCompareEventSheets.setMnemonic(70);
        this.miCompareEventSheets.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doCompareEventSheets();
            }
        });
        this.miCompareForms.setMnemonic(70);
        this.miCompareForms.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doCompareForms();
            }
        });
        this.miCompareSubroutines.setMnemonic(83);
        this.miCompareSubroutines.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doCompareSubroutines();
            }
        });
        this.menuCompare.add(this.miCompareEventSheets);
        this.menuCompare.add(this.miCompareForms);
        this.menuCompare.add(this.miCompareSubroutines);
        this.menuEdit.setMnemonic(69);
        this.menuEdit.add(this.miDelete);
        this.menuEdit.addSeparator();
        this.menuEdit.add(this.menuCompare);
        this.miToolbar.setMnemonic(84);
        this.miToolbar.setSelected(true);
        this.miToolbar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doToolbar();
            }
        });
        this.miStatusBar.setMnemonic(66);
        this.miStatusBar.setSelected(true);
        this.miStatusBar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doStatusBar();
            }
        });
        this.miRefresh.setAccelerator(KeyStroke.getKeyStroke(116, 0));
        this.miRefresh.setMnemonic(82);
        this.miRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doRefresh();
            }
        });
        this.miRefreshCount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doRefreshCount();
            }
        });
        this.miClearCache.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doClearCache();
            }
        });
        this.menuView.setMnemonic(86);
        this.menuView.add(this.miToolbar);
        this.menuView.add(this.miStatusBar);
        this.menuView.addSeparator();
        this.menuView.add(this.miRefresh);
        this.menuView.add(this.miRefreshCount);
        this.menuView.add(this.miClearCache);
        this.miAccessPrivilege.setMnemonic(77);
        this.miAccessPrivilege.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doAccessPrivilege(Buildtime.this.m_target);
            }
        });
        this.miReadOnly.setMnemonic(82);
        this.miReadOnly.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doReadOnlyControl(Buildtime.this.m_object);
            }
        });
        this.miReadWrite.setMnemonic(87);
        this.miReadWrite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doReadWriteControl(Buildtime.this.m_object);
            }
        });
        this.miCheckOut.setMnemonic(79);
        this.miCheckOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doCheckOutControl(Buildtime.this.m_object);
            }
        });
        this.miCheckIn.setMnemonic(73);
        this.miCheckIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doCheckInControl(Buildtime.this.m_object);
            }
        });
        this.miUndoCheckOut.setMnemonic(85);
        this.miUndoCheckOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doUndoCheckOutControl(Buildtime.this.m_object);
            }
        });
        this.miShowVersions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doShowVersionsControl(Buildtime.this.m_object);
            }
        });
        this.menuAccessControl.setMnemonic(65);
        this.menuAccessControl.add(this.miAccessPrivilege);
        this.menuAccessControl.add(this.miReadOnly);
        this.menuAccessControl.add(this.miReadWrite);
        this.menuAccessControl.addSeparator();
        this.menuAccessControl.add(this.miCheckOut);
        this.menuAccessControl.add(this.miCheckIn);
        this.menuAccessControl.add(this.miUndoCheckOut);
        this.menuAccessControl.addSeparator();
        this.menuAccessControl.add(this.miShowVersions);
        this.menuAccessControl.addSeparator();
        this.menuAccessControl.add(this.miAccessPrivilege);
        this.menuAccessControl.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent var1) {
                Buildtime.this.menuAccessControl_popup();
            }

            public void menuDeselected(MenuEvent var1) {
            }

            public void menuCanceled(MenuEvent var1) {
            }
        });
        this.miCascade.setMnemonic(67);
        this.miCascade.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doCascade();
            }
        });
        this.miTileHorizontally.setMnemonic(72);
        this.miTileHorizontally.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doTileHorizontally();
            }
        });
        this.miTileVertically.setMnemonic(86);
        this.miTileVertically.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doTileVertically();
            }
        });
        this.miCloseAll.setMnemonic(65);
        this.miCloseAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doCloseAll();
            }
        });
        this.menuWindow.setMnemonic(87);
        this.menuWindow.add(this.miCascade);
        this.menuWindow.add(this.miTileHorizontally);
        this.menuWindow.add(this.miTileVertically);
        this.menuWindow.add(this.miCloseAll);
        this.miTopics.setAccelerator(KeyStroke.getKeyStroke(112, 0));
        this.miTopics.setMnemonic(72);
        this.miTopics.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doTopics();
            }
        });
        this.miAbout.setMnemonic(65);
        this.miAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doAbout();
            }
        });
        this.menuHelp.setMnemonic(72);
        this.menuHelp.add(this.miTopics);
        this.menuHelp.addSeparator();
        this.menuHelp.add(this.miAbout);
        this.menuBar.add(this.menuFile);
        this.menuBar.add(this.menuEdit);
        this.menuBar.add(this.menuView);
        this.menuBar.add(this.menuAccessControl);
        this.menuBar.add(this.menuWindow);
        this.menuBar.add(this.menuHelp);
        this.cmiNew.setMnemonic(78);
        this.cmiNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doNew(Buildtime.this.m_target);
            }
        });
        this.cmiOpen.setMnemonic(79);
        this.cmiOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doOpen(Buildtime.this.m_target);
            }
        });
        this.cmiSave.setMnemonic(83);
        this.cmiSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doSave(Buildtime.this.m_target);
            }
        });
        this.cmiRestore.setMnemonic(82);
        this.cmiRestore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doRestore(Buildtime.this.m_target);
            }
        });
        this.cmiDelete.setMnemonic(68);
        this.cmiDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doDelete(Buildtime.this.m_target);
            }
        });
        this.cmiAccessPrivilege.setMnemonic(77);
        this.cmiAccessPrivilege.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doAccessPrivilege(Buildtime.this.m_target);
            }
        });
        this.cmiReadOnly.setMnemonic(82);
        this.cmiReadOnly.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doReadOnly(Buildtime.this.m_target);
            }
        });
        this.cmiReadWrite.setMnemonic(87);
        this.cmiReadWrite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doReadWrite(Buildtime.this.m_target);
            }
        });
        this.cmiCheckOut.setMnemonic(79);
        this.cmiCheckOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doCheckOut(Buildtime.this.m_target);
            }
        });
        this.cmiCheckIn.setMnemonic(73);
        this.cmiCheckIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doCheckIn(Buildtime.this.m_target);
            }
        });
        this.cmiUndoCheckOut.setMnemonic(85);
        this.cmiUndoCheckOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doUndoCheckOut(Buildtime.this.m_target);
            }
        });
        this.cmiShowVersions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doShowVersions(Buildtime.this.m_target);
            }
        });
        this.cmAccessControl.setMnemonic(65);
        this.cmAccessControl.add(this.cmiReadOnly);
        this.cmAccessControl.add(this.cmiReadWrite);
        this.cmAccessControl.addSeparator();
        this.cmAccessControl.add(this.cmiCheckOut);
        this.cmAccessControl.add(this.cmiCheckIn);
        this.cmAccessControl.add(this.cmiUndoCheckOut);
        this.cmAccessControl.addSeparator();
        this.cmAccessControl.add(this.cmiShowVersions);
        this.cmAccessControl.addSeparator();
        this.cmAccessControl.add(this.cmiAccessPrivilege);
        this.contextMenu.add(this.cmiNew);
        this.contextMenu.add(this.cmiOpen);
        this.contextMenu.addSeparator();
        this.contextMenu.add(this.cmiSave);
        this.contextMenu.add(this.cmiRestore);
        this.contextMenu.addSeparator();
        this.contextMenu.add(this.cmiDelete);
        this.contextMenu.addSeparator();
        this.contextMenu.add(this.cmAccessControl);
        this.tbNew.setToolTipText("New");
        this.tbNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doNew(Buildtime.this.m_type);
            }
        });
        this.tbOpen.setToolTipText("Open");
        this.tbOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doOpen(Buildtime.this.m_object);
            }
        });
        this.tbSave.setToolTipText("Save");
        this.tbSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doSave(Buildtime.this.m_object);
            }
        });
        this.tbSaveAll.setToolTipText("Save All");
        this.tbSaveAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doSaveAll();
            }
        });
        this.tbPrint.setToolTipText("Print");
        this.tbPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doPrint();
            }
        });
        this.tbCut.setToolTipText("Cut");
        this.tbCut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doCut();
            }
        });
        this.tbCopy.setToolTipText("Copy");
        this.tbCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doCopy();
            }
        });
        this.tbPaste.setToolTipText("Paste");
        this.tbPaste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doPaste();
            }
        });
        this.tbDelete.setToolTipText("Delete");
        this.tbDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doDelete(Buildtime.this.m_object);
            }
        });
        this.tbXRef.setToolTipText("Cross Reference");
        this.tbXRef.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doXRef(Buildtime.this.m_object);
            }
        });
        this.toolBar.setFloatable(false);
        this.toolBar.add(this.tbNew);
        this.toolBar.add(this.tbOpen);
        this.toolBar.add(this.tbSave);
        this.toolBar.add(this.tbSaveAll);
        this.toolBar.addSeparator();
        this.toolBar.add(this.tbDelete);
        this.toolBar.addSeparator();
        this.toolBar.add(this.tbXRef);
        this.toolBar.addSeparator();
        Class[] var2 = ObjectFactory.ACTIVE_VIEW_CLASSES;

        for(int var3 = 0; var3 < var2.length; ++var3) {
            ToolBarButton var4 = new ToolBarButton(var2[var3]);
            var4.setToolTipText(ClassUtility.getDisplayName(var2[var3]));
            var4.setEnabled(false);
            var4.setVisible(false);
            var4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent var1) {
                    Buildtime.this.doShow((Class)Buildtime.this.m_mapButton.get(var1.getSource()));
                }
            });
            this.toolBar.add(var4);
            this.m_mapButton.put(var2[var3], var4);
            this.m_mapButton.put(var4, var2[var3]);
        }

        this.tbvEditFilter.setToolTipText("Edit Filter");
        this.tbvEditFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doEditFilter(Buildtime.this.getSelectedObject(Buildtime.this.tView, 1));
            }
        });
        this.tbvRefresh.setToolTipText("Refresh");
        this.tbvRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doRefresh(Buildtime.this.getSelectedObject(Buildtime.this.tView, 1));
            }
        });
        this.tbvClearCache.setToolTipText("Clear Cache");
        this.tbvClearCache.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doClearCache(Buildtime.this.getSelectedObject(Buildtime.this.tView, 1));
            }
        });
        this.toolBarView.setFloatable(false);
        this.toolBarView.add(this.tbvEditFilter);
        this.toolBarView.add(this.tbvRefresh);
        this.toolBarView.add(this.tbvClearCache);
        this.cbView.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        this.cbView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doViewSelected();
            }
        });
        this.tbvViewRefresh.setToolTipText("Refresh View");
        this.tbvViewRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doViewRefresh();
            }
        });
        this.tbvViewNew.setToolTipText("New View");
        this.tbvViewNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doViewNew();
            }
        });
        this.tbvViewEdit.setToolTipText("Edit View");
        this.tbvViewEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doViewEdit();
            }
        });
        this.tbvViewDelete.setToolTipText("Delete View");
        this.tbvViewDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doViewDelete();
            }
        });
        this.toolBarView1.setFloatable(false);
        this.toolBarView1.add(this.lView);
        this.toolBarView1.add(this.cbView);
        this.toolBarView1.add(this.tbvViewRefresh);
        this.toolBarView1.add(this.tbvViewNew);
        this.toolBarView1.add(this.tbvViewEdit);
        this.toolBarView1.add(this.tbvViewDelete);
        this.tView.getSelectionModel().setSelectionMode(1);
        this.tView.setModel((TreeModel)null);
        this.tView.setRootVisible(false);
        this.tView.setShowsRootHandles(true);
        this.tView.addEventListener((IEditEventListener) this);
        this.tView.addEventListener((IEditEventListener) this);
        this.tView.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent var1) {
                if (SwingUtilities.isRightMouseButton(var1)) {
                    Buildtime.this.contextMenu_popup(var1);
                }

            }
        });
        JPanel var7 = new JPanel();
        var7.setLayout(new BorderLayout());
        var7.add(this.toolBarView, "North");
        var7.add(this.toolBarView1, "South");
        JPanel var8 = new JPanel();
        var8.setLayout(new BorderLayout());
        var8.add(var7, "North");
        var8.add(new JScrollPane(this.tView), "Center");
        this.tbsNewFilter.setToolTipText("New Search");
        this.tbsNewFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doNewSearch(Buildtime.this.getSelectedObject(Buildtime.this.tSearch, 1));
            }
        });
        this.tbsEditFilter.setToolTipText("Edit Search");
        this.tbsEditFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doEditFilter(Buildtime.this.getSelectedObject(Buildtime.this.tSearch, 2));
            }
        });
        this.tbsRefresh.setToolTipText("Refresh");
        this.tbsRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doRefresh(Buildtime.this.getSelectedObject(Buildtime.this.tSearch));
            }
        });
        this.tbsDelete.setToolTipText("Delete");
        this.tbsDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doDelete(Buildtime.this.getSelectedObject(Buildtime.this.tSearch, 2), Buildtime.this.getSelectedObject(Buildtime.this.tSearch, 1));
            }
        });
        this.toolBarSearch.setFloatable(false);
        this.toolBarSearch.add(this.tbsNewFilter);
        this.toolBarSearch.add(this.tbsEditFilter);
        this.toolBarSearch.add(this.tbsRefresh);
        this.toolBarSearch.add(this.tbsDelete);
        this.tSearch.getSelectionModel().setSelectionMode(1);
        this.tSearch.setModel((TreeModel)null);
        this.tSearch.setRootVisible(false);
        this.tSearch.setShowsRootHandles(true);
        this.tSearch.addEventListener((IEditEventListener) this);
        this.tSearch.addEventListener((IEditEventListener) this);
        this.tSearch.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent var1) {
                if (SwingUtilities.isRightMouseButton(var1)) {
                    Buildtime.this.contextMenu_popup(var1);
                }

            }
        });
        JPanel var5 = new JPanel();
        var5.setLayout(new BorderLayout());
        var5.add(this.toolBarSearch, "North");
        var5.add(new JScrollPane(this.tSearch), "Center");
        this.tConfiguration.getSelectionModel().setSelectionMode(1);
        this.tConfiguration.setModel((TreeModel)null);
        this.tConfiguration.setRootVisible(false);
        this.tConfiguration.setShowsRootHandles(true);
        this.tConfiguration.setCellRenderer(new Buildtime.TreeCellRenderer());
        this.tConfiguration.addEventListener((IEditEventListener) this);
        this.tConfiguration.addEventListener((IEditEventListener) this);
        this.tConfiguration.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent var1) {
                if (SwingUtilities.isRightMouseButton(var1)) {
                    Buildtime.this.contextMenu_popup(var1);
                }

            }
        });
        this.tFSMConfiguration.getSelectionModel().setSelectionMode(1);
        this.tFSMConfiguration.setModel((TreeModel)null);
        this.tFSMConfiguration.setRootVisible(false);
        this.tFSMConfiguration.setShowsRootHandles(true);
        this.tFSMConfiguration.setCellRenderer(new Buildtime.TreeCellRenderer());
        this.tFSMConfiguration.addEventListener((IEditEventListener) this);
        this.tFSMConfiguration.addEventListener((IEditEventListener) this);
        this.tFSMConfiguration.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent var1) {
                if (SwingUtilities.isRightMouseButton(var1)) {
                    Buildtime.this.contextMenu_popup(var1);
                }

            }
        });
        this.tVersionPlatform.getSelectionModel().setSelectionMode(1);
        this.tVersionPlatform.setModel((TreeModel)null);
        this.tVersionPlatform.setRootVisible(false);
        this.tVersionPlatform.setShowsRootHandles(true);
        this.tVersionPlatform.addEventListener((IEditEventListener) this);
        this.tVersionPlatform.addEventListener((IEditEventListener) this);
        this.tVersionPlatform.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent var1) {
                if (SwingUtilities.isRightMouseButton(var1)) {
                    Buildtime.this.contextMenu_popup(var1);
                }

            }
        });
        this.tbhEditFilter.setToolTipText("Edit Filter");
        this.tbhEditFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doEditFilter(Buildtime.this.getSelectedObject(Buildtime.this.tHistory, 1));
            }
        });
        this.tbhRefresh.setToolTipText("Refresh");
        this.tbhRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Object var2 = Buildtime.this.getSelectedObject(Buildtime.this.tHistory, 2);
                Buildtime.this.doRefresh(var2 != null ? var2 : Buildtime.this.getSelectedObject(Buildtime.this.tHistory, 1));
            }
        });
        this.tbhReset.setToolTipText("Reset");
        this.tbhReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doReset();
            }
        });
        this.toolBarHistory.setFloatable(false);
        this.toolBarHistory.add(this.tbhEditFilter);
        this.toolBarHistory.add(this.tbhRefresh);
        this.toolBarHistory.add(this.tbhReset);
        this.tHistory.setForeground(new Color(0, 0, 128));
        this.tHistory.getSelectionModel().setSelectionMode(1);
        this.tHistory.setModel((TreeModel)null);
        this.tHistory.setRootVisible(false);
        this.tHistory.setShowsRootHandles(true);
        this.tHistory.addEventListener((IEditEventListener) this);
        this.tHistory.addEventListener((IEditEventListener) this);
        JPanel var6 = new JPanel();
        var6.setLayout(new BorderLayout());
        var6.add(this.toolBarHistory, "North");
        var6.add(new JScrollPane(this.tHistory), "Center");
        this.browser.setTabPlacement(3);
        this.browser.addTab("[All]", var8);
        this.browser.addTab("Search", var5);
        this.browser.addTab("Configuration", new JScrollPane(this.tConfiguration));
        this.browser.addTab("Versions", var6);
        this.browser.addTab("FSM", new JScrollPane(this.tFSMConfiguration));
        this.browser.addTab("Versioning Platform", this.versioningPlatformTab);
        this.browserPane.setLayout(new BorderLayout());
        this.browserPane.add(this.browser, "Center");
        this.leftPane.setPreferredSize(new Dimension(0, 0));
        this.leftPane.setLayout(new BorderLayout());
        this.leftPane.add(this.browserPane, "Center");
        this.rightPane.setDesktopManager(new Buildtime.DesktopManagerAdapter());
        this.rightPane.setPreferredSize(new Dimension(0, 0));
        this.rightPane.setDragMode(1);
        this.rightPane.setBackground(this.getBackground());
        this.hSplitPane.setLeftComponent(this.leftPane);
        this.hSplitPane.setRightComponent(this.rightPane);
        this.hSplitPane.setDividerLocation(0.25D);
        this.hSplitPane.setResizeWeight(0.25D);
        this.hSplitPane.setOneTouchExpandable(true);
        this.setLayout(new BorderLayout());
        this.add(this.toolBar, "North");
        this.add(this.hSplitPane, "Center");
        this.add(this.statusBar, "South");
        this.getInputMap(1).put(KeyStroke.getKeyStroke(76, 192), "ViewLog");
        this.getActionMap().put("ViewLog", new AbstractAction() {
            public void actionPerformed(ActionEvent var1) {
                Buildtime.this.doViewLog();
            }
        });
    }

    public static void main(String[] var0) {
        try {
            PlasticLookAndFeel.setMyCurrentTheme(new DatasweepPlasticTheme());
            PlasticLookAndFeel.setTabStyle("metal");
            PlasticXPLookAndFeel var1 = new PlasticXPLookAndFeel();
            UIManager.setLookAndFeel(var1);
        } catch (Throwable var6) {
        }

        JFrame var7 = new JFrame("Process Designer");
        SplashScreen var2 = new SplashScreen(var7, "buildtime.jpg");
        app = new Buildtime(var7);
        String var3 = "";
        if (var0 != null) {
            if (var0.length != 3 && var0.length != 4 && var0.length != 5 && var0.length != 6) {
                String var4 = System.getProperty("line.separator");
                StringBuffer var5 = new StringBuffer(var4);
                var5.append("USAGE: <iiopURL> <httpURL> <helpURL>").append(var4).append(" IIOP URL Format: iiop://<hostName>:<port>").append(var4).append(" HTTP URL Format: http://<hostName>:<port> or https://<hostName>:<port>").append(var4).append(" Help Server Format: http://<hostName>:<port> or file://networkSharedDrive/PlantOpsDownloads");
                throw new RuntimeException("Incorrect parameters:" + var5.toString());
            }

            if (var0.length == 4) {
                EventLog.setLogLevel(var0[3]);
            }

            app.setupButton(false);
            if (var0.length == 6) {
                app.connect(var0[0], var0[1], var0[2], true, var0[4], var0[5]);
            } else {
                app.connect(var0[0], var0[1], var0[2], false, (String)null, (String)null);
            }

            var2.hide();
            var7.setVisible(true);
            var7.setIconImage((new ImageIcon(Buildtime.class.getResource("Buildtime.gif"))).getImage());
            if (var0.length == 4) {
                var3 = var0[3];
            }
        }

        ActivityList.setJarLocation(ActivityList.PDJAR);
        if (app.getObjectFactory().isLoggedIn() && app.getDBInfo() != null) {
            ActivityList.loadClientActivities(app.getObjectFactory());
            if ("DEBUGACTIVITY".equals(var3)) {
                ActivityList.loadClientActivitiesForDebug(app.getObjectFactory());
            }
        }

    }

    private List fetch(Class var1) {
        try {
            ObjectManager var2 = this.getObjectFactory().getObjectManager(var1);
            List var3 = var2.getCachedObjects();
            if (var3.isEmpty()) {
                Filter var4 = FilterSupport.createFilter(var1, this.getObjectFactory());
                var4.setMaxRows(100);
                var3 = var2.getObjects(var4);
            }

            return var3;
        } catch (Exception var5) {
            EventLog.logException(var5, this, "fetch(" + var1.getName() + ")");
            return new Vector();
        }
    }

    private List filter(List var1) {
        Vector var2 = new Vector();
        if (var1 != null) {
            ListIterator var3 = var1.listIterator();

            while(var3.hasNext()) {
                Object var4 = var3.next();
                var2.add(var4);
            }
        }

        return var2;
    }

    protected List getAvailable(Class var1) {
        return this.filter(this.fetch(var1));
    }

    static {
        Thread var0 = new Thread() {
            public void run() {
                System.out.println("loadLibrary jdic in BuildTime ..." + Thread.currentThread().getName());
                System.loadLibrary("jdic");
            }
        };
        var0.start();
    }

    class PermissionCache {
        final int EXPIRED_INTERVAL = 5;
        HashMap mapCache = new HashMap();
        HashMap mapCreation = new HashMap();

        PermissionCache() {
        }

        boolean isExpired(Class var1) {
            Calendar var2 = (Calendar)this.mapCreation.get(var1);
            if (var2 != null) {
                Calendar var3 = (Calendar)var2.clone();
                var3.add(13, 5);
                return Calendar.getInstance().after(var3);
            } else {
                return true;
            }
        }

        Buildtime.Permission getPermission(Class var1) {
            Buildtime.Permission var2 = !this.isExpired(var1) ? (Buildtime.Permission)this.mapCache.get(var1) : null;
            if (var2 == null) {
                var2 = Buildtime.this.new Permission(var1);
                this.mapCache.put(var1, var2);
                this.mapCreation.put(var1, Calendar.getInstance());
            }

            return var2;
        }

        Buildtime.Permission getPermission(AbstractClient var1) {
            Buildtime.Permission var2 = var1 != null ? this.getPermission(var1.getClass()) : null;
            if (var2 != null) {
                var2.setObject(var1);
                return var2;
            } else {
                return var2;
            }
        }
    }

    class Permission {
        char userSecurity;
        Class type;
        AbstractClient object;

        Permission(Class var2) {
            this.setType(var2);
        }

        Permission(AbstractClient var2) {
            this.setObject(var2);
        }

        Class getType() {
            return this.type;
        }

        void setType(Class var1) {
            if (this.type != var1) {
                this.type = var1;
                this.userSecurity = Buildtime.this.getObjectFactory().getUserSecurity(var1);
            }

        }

        AbstractClient getObject() {
            return this.object;
        }

        void setObject(AbstractClient var1) {
            this.setType(var1.getClass());
            this.object = var1;
        }

        boolean canNew() {
            if (this.type != UDADefinition.class && this.type != VPVersionConfiguration.class && this.type != FSMConfiguration.class && this.type != ATRow.class) {
                if (this.type == User.class && !Buildtime.this.isDatasweepSecurity()) {
                    return false;
                } else {
                    return this.userSecurity == 'W';
                }
            } else {
                return false;
            }
        }

        boolean canOpen() {
            return this.userSecurity != '-';
        }

        boolean canRestore() {
            return this.object instanceof AbstractClient && !this.object.isNew();
        }

        boolean canSave() {
            if (Buildtime.this.getCurrentUser() == null) {
                return false;
            } else if (!(this.object instanceof AbstractClient) || !this.object.isAudit() && !this.object.isHistorical()) {
                return this.userSecurity == 'W';
            } else {
                return false;
            }
        }

        boolean canSaveAs() {
            if (this.type != AddOn.class && this.type != ATDefinition.class && this.type != LibraryHolder.class && this.type != Locale.class && this.type != Location.class && this.type != UDADefinition.class && this.type != FSMConfiguration.class && this.type != VPVersionConfiguration.class) {
                return this.type == User.class && !Buildtime.this.isDatasweepSecurity() ? false : this.canSave();
            } else {
                return false;
            }
        }

        boolean canDelete() {
            if (this.type != UDADefinition.class && this.type != FSMConfiguration.class && this.type != VPVersionConfiguration.class) {
                return this.type == User.class && !Buildtime.this.isDatasweepSecurity() ? false : this.canSave();
            } else {
                return false;
            }
        }

        boolean canXRef() {
            if (this.type != UDADefinition.class && this.type != FSMConfiguration.class && this.type != VPVersionConfiguration.class) {
                return !(this.object instanceof AbstractClient) || !this.object.isAudit() && !this.object.isHistorical();
            } else {
                return false;
            }
        }
    }

    class TreeCellRenderer extends DefaultTreeCellRenderer {
        TreeCellRenderer() {
        }

        public Component getTreeCellRendererComponent(JTree var1, Object var2, boolean var3, boolean var4, boolean var5, int var6, boolean var7) {
            Component var8 = super.getTreeCellRendererComponent(var1, var2, var3, var4, var5, var6, var7);
            if (var2 instanceof ObjectTreeNode) {
                var2 = ((ObjectTreeNode)var2).getUserObject();
            }

            Icon var9 = var2 instanceof IObjectNode ? ((IObjectNode)var2).getImage() : null;
            if (var9 != null) {
                this.setIcon(var9);
            }

            if (var2 instanceof IObjectNode) {
                var2 = ((IObjectNode)var2).getObject();
            }

            if (var2 instanceof UDADefinition && !((UDADefinition)var2).getNamedItems().isEmpty()) {
                this.setFont(this.getFont().deriveFont(1));
            } else if (var2 instanceof FSMConfiguration && !((FSMConfiguration)var2).getConfigurationItems().isEmpty()) {
                this.setFont(this.getFont().deriveFont(1));
            } else {
                this.setFont(this.getFont().deriveFont(0));
            }

            this.setForeground(var1.getForeground());
            return var8;
        }
    }

    class TemplateRecipePanel extends JPanel {
        JComboBox cbRoute;

        TemplateRecipePanel() {
            JLabel var2 = new JLabel("Route:", 4);
            this.cbRoute = new JComboBox();
            this.cbRoute.setModel(new DefaultComboBoxModel(Utility.sort(Utility.toArray(Buildtime.this.getAvailable(Route.class)))));
            this.setLayout(new GridBagLayout());
            GridBagConstraints var3 = new GridBagConstraints();
            var3.anchor = 17;
            var3.insets = new Insets(5, 5, 0, 5);
            var3.fill = 0;
            FoundationGUIHelper.makeConst(var3, 0, 0, 1, 1, 0.0D, 1.0D, 0, 0);
            this.add(var2, var3);
            var3.fill = 2;
            FoundationGUIHelper.makeConst(var3, 1, 0, 2, 1, 1.0D, 1.0D, 0, 0);
            this.add(this.cbRoute, var3);
        }

        Object getSelectedRoute() {
            return this.cbRoute.getSelectedItem();
        }
    }

    class PartListItem {
        String partNumber;
        String partRevision;
        String description;
        long key;

        PartListItem(String var2, String var3, String var4, Number var5) {
            this.partNumber = var2;
            this.partRevision = var3;
            this.description = var4;
            this.key = var5.longValue();
        }

        public String getPartNumber() {
            return this.partNumber;
        }

        public String getPartRevision() {
            return this.partRevision;
        }

        public String getDescription() {
            return this.description;
        }

        public long getKey() {
            return this.key;
        }

        public String toString() {
            return this.partNumber + "." + this.partRevision;
        }
    }

    class PartPanel extends JPanel {
        JList filteredPartList;
        FilterControl filterControl;
        JButton btnRefresh;
        JList selectedPartList;

        PartPanel(JList var2) {
            this.setBorder(BorderFactory.createTitledBorder("Part Selector"));
            this.setLayout(new BorderLayout(0, 11));
            this.filteredPartList = new JList(new DefaultListModel());
            this.btnRefresh = new JButton("Refresh");
            this.filterControl = new FilterControl();
            this.selectedPartList = var2;
            this.initForm();
            this.filterControl.setObject(FilterFactory.createFilter(Buildtime.this.getObjectFactory().getObjectManager(Part.class).getServer(), Part.class, 100));
            this.showList(this.filteredPartList, new Vector());
        }

        private void addPart(Buildtime.PartListItem var1) {
            DefaultListModel var2 = (DefaultListModel)this.selectedPartList.getModel();
            if (var2.indexOf(var1) < 0) {
                var2.addElement(var1);
            }

        }

        protected void showList(JList var1, List<?> var2) {
            var1.setListData(Utility.toArray(var2));
        }

        private void onRefresh() {
            try {
                this.setCursor(Cursor.getPredefinedCursor(3));
                this.showList(this.filteredPartList, this.fetch((Filter)this.filterControl.getObject()));
                this.setCursor(Cursor.getPredefinedCursor(0));
            } catch (Exception var2) {
                this.setCursor(Cursor.getPredefinedCursor(0));
                EventLog.dispatchException(var2, this, "onRefresh()");
            }

        }

        private List<?> fetch(Filter var1) throws Exception {
            String[] var2 = FilterUtility.getDisplayColumns(Part.class);
            if (var2 == null) {
                return var1.exec();
            } else {
                Vector var3 = var1.getArrayData(var2);
                Vector var4 = new Vector(var3.size());

                for(int var5 = 0; var5 < var3.size(); ++var5) {
                    String[] var6 = (String[])((String[])var3.get(var5));
                    var4.add(Buildtime.this.new PartListItem(var6[0], var6[1], var6[2], Long.valueOf(var6[3])));
                }

                return var4;
            }
        }

        private void initForm() {
            this.filteredPartList.setLayoutOrientation(1);
            this.filteredPartList.setVisibleRowCount(0);
            this.filteredPartList.setSelectionMode(0);
            MouseAdapter var1 = new MouseAdapter() {
                public void mouseClicked(MouseEvent var1) {
                    if (var1.getClickCount() == 2) {
                        int var2 = PartPanel.this.filteredPartList.locationToIndex(var1.getPoint());
                        Object var3 = PartPanel.this.filteredPartList.getModel().getElementAt(var2);
                        PartPanel.this.addPart((Buildtime.PartListItem)var3);
                    }

                }
            };
            this.filteredPartList.addMouseListener(var1);
            JScrollPane var2 = new JScrollPane(this.filteredPartList);
            var2.getViewport().setPreferredSize(new Dimension(200, 0));
            var2.getViewport().setMinimumSize(new Dimension(200, 0));
            this.btnRefresh.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent var1) {
                    PartPanel.this.onRefresh();
                }
            });
            JPanel var3 = new JPanel();
            var3.setLayout(new FlowLayout(1));
            var3.add(this.btnRefresh);
            JPanel var4 = new JPanel();
            var4.setLayout(new BorderLayout(0, 11));
            var4.add(this.filterControl, "Center");
            var4.add(var3, "South");
            var4.setPreferredSize(new Dimension(300, 300));
            var4.setMinimumSize(new Dimension(300, 300));
            this.setPreferredSize(new Dimension(500, 300));
            this.setMinimumSize(new Dimension(500, 300));
            this.setLayout(new GridBagLayout());
            GridBagConstraints var5 = new GridBagConstraints();
            var5.anchor = 11;
            var5.insets = new Insets(0, 2, 0, 2);
            var5.fill = 1;
            FoundationGUIHelper.makeConst(var5, 0, 0, 1, 1, 1.0D, 1.0D, 0, 0);
            this.add(var2, var5);
            FoundationGUIHelper.makeConst(var5, 1, 0, 3, 1, 1.0D, 1.0D, 0, 0);
            this.add(var4, var5);
        }
    }

    class MasterRecipeCreationPanel extends JPanel implements ActionListener {
        Buildtime.MasterRecipePanel masterRecipePanel = Buildtime.this.new MasterRecipePanel();
        private JComboBox templateRecipeCombo;
        private JComboBox processBomCombo;
        private JList selectedPartList;
        private JRadioButton pbomAndRouteRadio;
        private JRadioButton templateRecipeRadio;
        private JPanel cardPanel;
        private String PBOM_ROUTE = "PBOM and Route";
        private String TEMPLATE_RECIPE = "TemplateRecipe";

        MasterRecipeCreationPanel() {
            super(new BorderLayout());
            JLabel var2 = new JLabel("TemplateRecipe");
            JLabel var3 = new JLabel("Process BOM");
            JLabel var4 = new JLabel("Parts");
            this.templateRecipeCombo = new JComboBox();
            this.templateRecipeCombo.setModel(new DefaultComboBoxModel(Utility.sort(Utility.toArray(Buildtime.this.getAvailable(TemplateRecipe.class)))));
            this.processBomCombo = new JComboBox();
            this.processBomCombo.setModel(new DefaultComboBoxModel(Utility.sort(Utility.toArray(Buildtime.this.getAvailable(ProcessBillOfMaterials.class)))));
            this.selectedPartList = new JList(new DefaultListModel());
            JPanel var5 = new JPanel();
            var5.setLayout(new GridBagLayout());
            GridBagConstraints var6 = new GridBagConstraints();
            var6.anchor = 17;
            var6.insets = new Insets(5, 5, 5, 5);
            var6.fill = 0;
            FoundationGUIHelper.makeConst(var6, 0, 0, 1, 1, 1.0D, 1.0D, 0, 0);
            var5.add(var2, var6);
            var6.fill = 2;
            FoundationGUIHelper.makeConst(var6, 1, 0, 1, 1, 1.0D, 1.0D, 0, 0);
            var5.add(this.templateRecipeCombo, var6);
            var6.fill = 0;
            FoundationGUIHelper.makeConst(var6, 0, 1, 1, 1, 1.0D, 1.0D, 0, 0);
            var5.add(var3, var6);
            var6.fill = 2;
            FoundationGUIHelper.makeConst(var6, 1, 1, 1, 1, 1.0D, 1.0D, 0, 0);
            var5.add(this.processBomCombo, var6);
            FoundationGUIHelper.makeConst(var6, 0, 2, 2, 1, 1.0D, 1.0D, 0, 0);
            var5.add(var4, var6);
            var6.fill = 1;
            FoundationGUIHelper.makeConst(var6, 0, 3, 2, 1, 1.0D, 1.0D, 0, 0);
            var5.add(new JScrollPane(this.selectedPartList), var6);
            JPanel var7 = new JPanel(new BorderLayout());
            var7.setBorder(BorderFactory.createTitledBorder("MasterRecipe components"));
            var7.add(var5, "North");
            Buildtime.PartPanel var8 = Buildtime.this.new PartPanel(this.selectedPartList);
            JPanel var9 = new JPanel();
            var9.setLayout(new GridBagLayout());
            GridBagConstraints var10 = new GridBagConstraints();
            var10.anchor = 11;
            var10.insets = new Insets(0, 0, 0, 0);
            var10.fill = 1;
            FoundationGUIHelper.makeConst(var10, 0, 0, 1, 1, 1.0D, 1.0D, 0, 0);
            var9.add(var7, var10);
            FoundationGUIHelper.makeConst(var10, 1, 0, 1, 1, 1.0D, 1.0D, 0, 0);
            var9.add(var8, var10);
            this.pbomAndRouteRadio = new JRadioButton(this.PBOM_ROUTE);
            this.pbomAndRouteRadio.setSelected(true);
            this.pbomAndRouteRadio.addActionListener(this);
            this.templateRecipeRadio = new JRadioButton("Template Recipe");
            this.templateRecipeRadio.addActionListener(this);
            ButtonGroup var11 = new ButtonGroup();
            var11.add(this.pbomAndRouteRadio);
            var11.add(this.templateRecipeRadio);
            JPanel var12 = new JPanel(new GridLayout(1, 2));
            var12.add(this.pbomAndRouteRadio);
            var12.add(this.templateRecipeRadio);
            this.cardPanel = new JPanel(new CardLayout());
            this.cardPanel.add(this.masterRecipePanel, this.PBOM_ROUTE);
            this.cardPanel.add(var9, this.TEMPLATE_RECIPE);
            CardLayout var13 = (CardLayout)((CardLayout)this.cardPanel.getLayout());
            var13.show(this.cardPanel, this.PBOM_ROUTE);
            this.add(var12, "North");
            this.add(this.cardPanel, "Center");
        }

        public void actionPerformed(ActionEvent var1) {
            Object var2 = var1.getSource();
            CardLayout var3 = (CardLayout)((CardLayout)this.cardPanel.getLayout());
            if (var2.equals(this.pbomAndRouteRadio)) {
                var3.show(this.cardPanel, this.PBOM_ROUTE);
            } else {
                var3.show(this.cardPanel, this.TEMPLATE_RECIPE);
            }

        }

        boolean createFromPBOM_ROUTE() {
            return this.pbomAndRouteRadio.isSelected();
        }

        TemplateRecipe getSelectedTemplateRecipe() {
            return (TemplateRecipe)this.templateRecipeCombo.getSelectedItem();
        }

        ProcessBillOfMaterials getSlectedPBOM() {
            return (ProcessBillOfMaterials)this.processBomCombo.getSelectedItem();
        }

        Vector<Part> getParts() {
            Vector var1 = new Vector();
            DefaultListModel var2 = (DefaultListModel)this.selectedPartList.getModel();
            Object[] var3 = var2.toArray();
            ObjectManager var4 = Buildtime.this.getObjectFactory().getObjectManager(Part.class);

            for(int var5 = 0; var5 < var3.length; ++var5) {
                Object var6 = var3[var5];

                try {
                    if (var6 instanceof Buildtime.PartListItem) {
                        Part var7 = (Part)var4.getObject(((Buildtime.PartListItem)var6).getKey());
                        if (var7 != null) {
                            var1.add(var7);
                        }
                    }
                } catch (Exception var8) {
                    EventLog.writeError("Part " + var6 + " error. " + var8.getMessage());
                }
            }

            return var1;
        }
    }

    class MasterRecipePanel extends JPanel {
        JComboBox cbProcessBOM = new JComboBox();
        JComboBox cbRoute = new JComboBox();

        MasterRecipePanel() {
            super(new BorderLayout());
            this.initForm();
            this.cbProcessBOM.setModel(new DefaultComboBoxModel(Utility.sort(Utility.toArray(Buildtime.this.getAvailable(ProcessBillOfMaterials.class)))));
            this.cbRoute.setModel(new DefaultComboBoxModel(Utility.sort(Utility.toArray(Buildtime.this.getAvailable(Route.class)))));
        }

        public Object[] getArgs() {
            return new Object[]{this.cbProcessBOM.getSelectedItem(), this.cbRoute.getSelectedItem()};
        }

        protected boolean isValid(Object var1) {
            return true;
        }

        private void initForm() {
            GridBagConstraints[][] var1 = new GridBagConstraints[2][2];

            for(int var2 = 0; var2 < var1.length; ++var2) {
                for(int var3 = 0; var3 < var1[var2].length; ++var3) {
                    var1[var2][var3] = new GridBagConstraints();
                    var1[var2][var3].fill = 2;
                    var1[var2][var3].gridx = var3;
                    var1[var2][var3].gridy = var2;
                    if (var3 == 1) {
                        var1[var2][var3].weightx = 100.0D;
                    } else {
                        var1[var2][var3].insets = new Insets(11, 0, 11, 8);
                    }

                    var1[var2][var3].weighty = 100.0D;
                }
            }

            JPanel var4 = new JPanel();
            var4.setLayout(new GridBagLayout());
            var4.add(new JLabel("Process BOM:", 4), var1[0][0]);
            var4.add(this.cbProcessBOM, var1[0][1]);
            var4.add(new JLabel("Route:", 4), var1[1][0]);
            var4.add(this.cbRoute, var1[1][1]);
            this.add(var4, "North");
        }
    }

    class ReportDesignPanel extends JPanel implements ActionListener {
        Object result = null;
        private JLabel label;
        private JTextField selected;
        private JButton button;

        ReportDesignPanel() {
            this.initForm();
        }

        public Object[] getArgs() {
            return new Object[]{this.result};
        }

        public void actionPerformed(ActionEvent var1) {
            Object var2 = var1.getSource();
            if (var2 instanceof JButton) {
                DlgKeyedSelector var3 = new DlgKeyedSelector((JButton)var2, ReportDataDefinition.class, (Object)null);
                if (var3.showDialog()) {
                    this.result = var3.getResult();
                    this.selected.setText(this.result != null ? this.result.toString() : "");
                }

                var3.dispose();
            }

        }

        private void initForm() {
            this.label = new JLabel(ClassUtility.getDisplayName(ReportDataDefinition.class) + ": ");
            this.label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
            this.selected = new JTextField();
            this.selected.setEditable(false);
            this.button = new JButton("...");
            this.button.setMargin(new Insets(0, 0, 0, 0));
            this.button.addActionListener(this);
            this.setLayout(new BorderLayout());
            this.add(this.label, "West");
            this.add(this.selected, "Center");
            this.add(this.button, "East");
        }
    }

    class ProcessBomPanel extends JPanel implements ActionListener {
        Object result = null;
        private JLabel label;
        private JTextField selected;
        private JButton button;

        ProcessBomPanel() {
            this.initForm();
        }

        public Object[] getArgs() {
            return new Object[]{this.result};
        }

        public void actionPerformed(ActionEvent var1) {
            Object var2 = var1.getSource();
            if (var2 instanceof JButton) {
                DlgKeyedSelector var3 = new DlgKeyedSelector((JButton)var2, Part.class, (Object)null);
                if (var3.showDialog()) {
                    this.result = var3.getResult();
                    this.selected.setText(this.result != null ? this.result.toString() : "");
                }

                var3.dispose();
            }

        }

        private void initForm() {
            this.label = new JLabel(ClassUtility.getDisplayName(Part.class) + ": ");
            this.label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
            this.selected = new JTextField();
            this.selected.setEditable(false);
            this.button = new JButton("...");
            this.button.setMargin(new Insets(0, 0, 0, 0));
            this.button.addActionListener(this);
            this.setLayout(new BorderLayout());
            this.add(this.label, "West");
            this.add(this.selected, "Center");
            this.add(this.button, "East");
        }
    }

    class CarrierType extends JPanel {
        JRadioButton rbNormalCarrier = new JRadioButton("Normal Carrier");
        JRadioButton rbBin = new JRadioButton("Bin");
        JRadioButton rbInventory = new JRadioButton("Inventory");

        CarrierType() {
            this.initForm();
        }

        public short getType() {
            if (this.rbNormalCarrier.isSelected()) {
                return 0;
            } else if (this.rbBin.isSelected()) {
                return 1;
            } else {
                return (short)(this.rbInventory.isSelected() ? 2 : -1);
            }
        }

        private void initForm() {
            this.rbNormalCarrier.setSelected(true);
            ButtonGroup var1 = new ButtonGroup();
            var1.add(this.rbNormalCarrier);
            var1.add(this.rbBin);
            var1.add(this.rbInventory);
            this.setLayout(new BoxLayout(this, 1));
            this.add(this.rbNormalCarrier);
            this.add(this.rbBin);
            this.add(this.rbInventory);
        }
    }

    class PasswordExpiredException extends Exception {
        PasswordExpiredException() {
        }
    }

    class ImportLogCallback implements com.datasweep.compatibility.dsx.ImportLogCallback {
        List log = new Vector();

        ImportLogCallback() {
        }

        private String getDisplayType(DKeyed var1) {
            Class var2 = ImportUtility.getType(var1);
            if (var2 == ATRow.class) {
                try {
                    Keyed var3 = Buildtime.this.getObjectFactory().getATDefinitionManager().getObject(((DATRow)var1).getAtDefinitionKey());
                    if (var3 != null) {
                        return "ATRow :: " + var3.getName();
                    }
                } catch (Throwable var4) {
                    EventLog.logException(var4, this, "getDisplayType(" + ((DATRow)var1).getAtDefinitionKey() + ")");
                }
            }

            return ClassUtility.getDisplayName(var2);
        }

        private String getDisplayName(DKeyed var1) {
            return ImportUtility.getDisplayName(var1);
        }

        private void show() {
            Object[][] var1 = new Object[this.log.size()][3];

            for(int var2 = 0; var2 < var1.length; ++var2) {
                var1[var2] = (Object[])((Object[])this.log.get(var2));
            }

            DSXUtilities.show(Buildtime.this, "Import Summary", var1, new String[]{"Type", "Name", "Action"});
        }

        public void log(DKeyed var1, short var2) {
            switch(var2) {
                case 1:
                    this.log.add(new String[]{this.getDisplayType(var1), this.getDisplayName(var1), "added"});
                    break;
                case 2:
                    this.log.add(new String[]{this.getDisplayType(var1), this.getDisplayName(var1), "updated"});
            }

        }
    }

    class ImportStatusCallback extends com.datasweep.compatibility.dsx.ImportStatusCallback {
        ImportStatusCallback() {
        }

        public void beforeImporting(DKeyed var1) {
            Buildtime.this.statusBar.setMessage(0, "Importing " + ClassUtility.getDisplayName(ImportUtility.getType(var1)) + " '" + ImportUtility.getDisplayName(var1) + "' ...");
        }

        public void afterImporting(DKeyed var1) {
            try {
                Buildtime.this.getObjectFactory().reload(var1);
            } catch (Throwable var3) {
                EventLog.logException(var3, this, "afterImporting(" + ImportUtility.getDisplayName(var1) + ")");
            }

        }
    }

    class ImportExceptionHandlerCallback implements com.datasweep.compatibility.dsx.ImportExceptionHandlerCallback {
        ImportExceptionHandlerCallback() {
        }

        public boolean handleException(DKeyed var1, Exception var2) {
            if (var2 instanceof DatasweepServerException) {
                String var3 = ((DatasweepServerException)var2).getLocalizedMessage();
                String var4 = ((DatasweepServerException)var2).getDError().getMessageId();
                if ("8014".equals(var4) || "8015".equals(var4) || "8017".equals(var4) || "8018".equals(var4)) {
                    int var5 = JOptionPane.showConfirmDialog(Buildtime.this, var3 + " skip and continue?", "Confirm", 0);
                    return var5 == 0;
                }
            }

            Buildtime.this.setCursor(Cursor.getPredefinedCursor(0));
            EventLog.dispatchException(var2, this, "doImport()");
            return true;
        }
    }

    class ImportESignatureCallback implements com.datasweep.compatibility.dsx.ImportESignatureCallback {
        ImportESignatureCallback() {
        }

        public void inputESignature(AccessPrivilege var1) {
            if (var1 != null && var1.getPerformedBySignature() != null) {
                DlgESignature var2 = new DlgESignature(Buildtime.this, var1, Buildtime.this.getObjectFactory(), ESignatureDefinition.ESIGDIALOG_CUD);

                try {
                    var2.showDialog();
                } catch (Throwable var7) {
                    EventLog.dispatchException(var7, this, "inputESignature()");
                } finally {
                    var2.dispose();
                }
            }

        }
    }

    class ImportAccessControlCallback implements com.datasweep.compatibility.dsx.ImportAccessControlCallback {
        ImportAccessControlCallback() {
        }

        public boolean verboseReadOnly(DKeyed var1) {
            Class var2 = ImportUtility.getType(var1);
            String var3 = ClassUtility.getDisplayName(var2);
            String var4 = ImportUtility.getDisplayName(var1);
            int var5 = JOptionPane.showConfirmDialog(Buildtime.this, var3 + " '" + var4 + "' is read only, skip and continue?", "Confirm", 0);
            return var5 == 0;
        }
    }

    class ImportOverWriteCallback implements com.datasweep.compatibility.dsx.ImportOverWriteCallback {
        Object options;

        ImportOverWriteCallback() {
        }

        public short overWrite(DKeyed var1) {
            if (this.options instanceof Boolean) {
                return (short)((Boolean)this.options ? 1 : 2);
            } else {
                Class var2 = ImportUtility.getType(var1);
                String var3 = ClassUtility.getDisplayName(var2);
                String var4 = ImportUtility.getDisplayName(var1);
                DlgConfirmReplace var5 = new DlgConfirmReplace(Buildtime.this, var3 + " '" + var4 + "' already exists, replace it?", "Confirm Replace");
                int var6 = var5.showDialog();
                var5.dispose();
                switch(var6) {
                    case -2:
                        this.options = Boolean.FALSE;
                    case 1:
                        return 2;
                    case -1:
                        this.options = Boolean.TRUE;
                    case 0:
                        return 1;
                    case 2:
                        return 3;
                    default:
                        throw new RuntimeException("Invalid option : " + var6);
                }
            }
        }
    }

    class ImportSecurityCallback implements com.datasweep.compatibility.dsx.ImportSecurityCallback {
        ImportSecurityCallback() {
        }

        public boolean canSave(DKeyed var1) {
            Class var2 = ImportUtility.getType(var1);
            return Buildtime.this.getObjectFactory().getUserSecurity(var2) == 'W';
        }

        public void displayError(DKeyed var1) {
            MessageBox.show(Buildtime.this, "Unable to import '" + ImportUtility.getDisplayName(var1) + "'\nAccess is denied.", "Import " + ClassUtility.getDisplayName(ImportUtility.getType(var1)), 0);
        }
    }

    class StatusBar extends JPanel {
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        JLabel label3 = new JLabel();
        GridBagConstraints c1 = new GridBagConstraints();
        GridBagConstraints c2 = new GridBagConstraints();
        GridBagConstraints c3 = new GridBagConstraints();

        StatusBar() {
            this.initForm();
        }

        void setMessage(int var1, String var2) {
            JLabel[] var3 = new JLabel[]{this.label1, this.label2, this.label3};
            if (0 <= var1 && var1 < var3.length) {
                var3[var1].setText(var2);
                if (this.isVisible()) {
                    this.validate();
                    this.paint(this.getGraphics());
                }
            }

        }

        private void initForm() {
            this.label2.setText(" ");
            this.label2.setBorder(BorderFactory.createBevelBorder(1));
            this.label3.setText(" ");
            this.label3.setBorder(BorderFactory.createBevelBorder(1));
            this.c1.fill = 2;
            this.c1.gridx = 0;
            this.c1.gridy = 0;
            this.c1.weightx = 0.9D;
            this.c2.fill = 2;
            this.c2.gridx = 1;
            this.c2.gridy = 0;
            this.c2.weightx = 0.05D;
            this.c3.fill = 2;
            this.c3.gridx = 2;
            this.c3.gridy = 0;
            this.c3.weightx = 0.05D;
            this.setLayout(new GridBagLayout());
            this.add(this.label1, this.c1);
            this.add(this.label2, this.c2);
            this.add(this.label3, this.c3);
            this.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        }
    }

    class ViewManager implements PropertyChangeListener {
        Vector views = new Vector();

        ViewManager() {
            File[] var2 = this.getFiles();

            for(int var3 = 0; var3 < var2.length; ++var3) {
                Preference var4 = new Preference(var2[var3].getName());
                ViewFilter var5 = (ViewFilter)var4.load();
                if (var5 == null) {
                    var4.delete();
                } else {
                    this.views.add(var5);
                    var5.addEventListener(this);
                }
            }

        }

        public void propertyChange(PropertyChangeEvent var1) {
            if ("name".equals(var1.getPropertyName())) {
                try {
                    (new Preference(var1.getOldValue() + "." + "fvw")).rename(var1.getNewValue() + "." + "fvw");
                } catch (Throwable var3) {
                    EventLog.logException(var3, this, "propertyChange()");
                }
            }

        }

        private File[] getFiles() {
            File var1 = new File(Preference.USERAPPDATA_DIR);
            return var1.listFiles(new FileFilter() {
                public boolean accept(File var1) {
                    return "fvw".equalsIgnoreCase(this.getExtension(var1));
                }

                private String getExtension(File var1) {
                    String var2 = var1.getName();
                    int var3 = var2.lastIndexOf(46);
                    return 0 < var3 && var3 < var2.length() - 1 ? var2.substring(var3 + 1) : null;
                }
            });
        }

        private ViewFilter get(String var1) {
            for(int var2 = 0; var2 < this.views.size(); ++var2) {
                ViewFilter var3 = (ViewFilter)this.views.get(var2);
                if (var1.equalsIgnoreCase(var3.getName())) {
                    return var3;
                }
            }

            return null;
        }

        Vector getAll() {
            Vector var1 = (Vector)this.views.clone();
            Utility.sort(var1);
            return var1;
        }

        ViewFilter create() {
            boolean var1 = false;
            int var2 = 1;

            String var3;
            do {
                var3 = "filter" + var2++;
                var1 = this.get(var3) != null;
            } while(var1);

            return new ViewFilter(var3);
        }

        void add(ViewFilter var1) {
            this.save(var1);
            this.views.add(var1);
            var1.addEventListener(this);
        }

        void remove(ViewFilter var1) {
            var1.removeEventListener(this);
            this.views.remove(var1);
            this.delete(var1);
        }

        private void save(ViewFilter var1) {
            (new Preference(var1.getName() + "." + "fvw")).save(var1);
        }

        private void delete(ViewFilter var1) {
            (new Preference(var1.getName() + "." + "fvw")).delete();
        }
    }

    class DesktopManagerAdapter extends DefaultDesktopManager {
        DesktopManagerAdapter() {
        }

        public void activateFrame(JInternalFrame var1) {
            super.activateFrame(var1);
            Object var2 = null;
            if (var1 instanceof EditFrame) {
                var2 = ((EditFrame)var1).getObject();
            } else if (var1 instanceof CompatibilityEditor) {
                var2 = ((CompatibilityEditor)var1).getCurrentObject();
            }

            if (var2 != null) {
                Buildtime.this.onSelectEvent(new SelectEvent(var2));
            }

        }
    }
}
