//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.datasweep.compatibility.controls;

import com.datasweep.core.VersionInformation;
import com.datasweep.core.utility.Utility;
import com.datasweep.plantops.common.dataobjects.DDBInfo;
import com.datasweep.plantops.swing.JBaseDialog;
import com.datasweep.plantops.swing.UIConstants;
import com.datasweep.plantops.swing.util.UIUtilities;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;

public class DlgAbout extends JBaseDialog {
    boolean rc;
    HashMap properties;
    DlgAbout.ImagePanel logo;
    JLabel title;
    JLabel copyright;
    JLabel copyright1;
    JLabel copyright2;
    JLabel reservedRight;
    JTable table;
    JScrollPane spTable;
    JTextArea trademark;
    JScrollPane spTrademark;
    JButton btnOK;

    public DlgAbout(Component var1, String var2, DDBInfo var3, String var4) {
        this((Frame)((Frame)(var1 instanceof Frame ? var1 : SwingUtilities.getAncestorOfClass(Frame.class, var1))), true);
        this.title.setText((var2.equals("Shop Operations")?"操作客户端":"配置客户端") + "   " + UIConstants.VERSION_TEXT + " " + VersionInformation.VERSION);//TODO 修改国际化
        this.setLocationRelativeTo(var1);
        this.properties.put(UIConstants.BUILD_TEXT, VersionInformation.BUILD);
        this.properties.put(UIConstants.JAVA_VERSION, System.getProperty("java.version"));
        this.properties.put(UIConstants.JAVA_VENDOR, System.getProperty("java.vendor"));
        this.properties.put(UIConstants.PROTOCOL, var4);

        try {
            this.properties.put(UIConstants.SITE_ID, var3.getActiveDBSiteId());
            String var5 = ".";
            if (var3.getActiveDBSchemaMinorVersion() < 10) {
                var5 = ".0";
            }

            this.properties.put(UIConstants.SCHEMA, var3.getActiveDBSchemaMajorVersion() + var5 + var3.getActiveDBSchemaMinorVersion());
            this.properties.put(UIConstants.DATABASE, var3.getActiveDBDatabaseName());
        } catch (Exception var6) {
        }

        this.table.setModel(new DlgAbout.TableModelAdapter());
    }

    private DlgAbout(Frame var1, boolean var2) {
        super(var1, var2);
        this.rc = false;
        this.properties = new HashMap();
        this.logo = new DlgAbout.ImagePanel("images/logo.gif");
        this.title = new JLabel();
        this.copyright = new JLabel();
        this.copyright1 = new JLabel();
        this.copyright2 = new JLabel();
        this.reservedRight = new JLabel();
        this.table = new JTable();
        this.spTable = new JScrollPane(this.table);
        this.trademark = new JTextArea();
        this.spTrademark = new JScrollPane(this.trademark);
        this.btnOK = new JButton();
        this.initForm();
        UIUtilities.setPreferredSizeToAveragePreferredSize(new JComponent[]{this.spTable, this.spTrademark});
    }

    public boolean showDialog() {
        this.show();
        return this.rc;
    }

    private void close(boolean var1) {
        this.rc = var1;
        this.DlgAbout_windowClosing();
    }

    private void DlgAbout_windowClosing() {
        this.setVisible(false);
    }

    private void initForm() {
        this.logo.setMinimumSize(new Dimension(160, 40));
        this.logo.setPreferredSize(new Dimension(160, 40));
        this.logo.setMaximumSize(new Dimension(160, 40));
        this.logo.setBorder(BorderFactory.createEtchedBorder(0));
        this.copyright.setText(VersionInformation.COPYRIGHT);
        this.copyright1.setText(VersionInformation.COPYRIGHT1);
        this.copyright1.setVisible(false);
        this.copyright2.setText(VersionInformation.COPYRIGHT2);
        this.copyright2.setVisible(false);
        this.reservedRight.setText(VersionInformation.RESERVEDRIGHT);
        this.table.setTableHeader((JTableHeader)null);
        this.table.setColumnSelectionAllowed(false);
        this.table.setRowSelectionAllowed(false);
        this.spTable.setEnabled(false);
        this.trademark.setEditable(false);
        this.trademark.setLineWrap(true);
        this.trademark.setWrapStyleWord(true);
        this.trademark.setText(VersionInformation.TRADEMARK);
        this.spTrademark.setEnabled(false);
        this.btnOK.setText(UIConstants.okButtonText);
        this.btnOK.setMnemonic(UIConstants.okButtonMnemonic);
        this.btnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent var1) {
                DlgAbout.this.close(true);
            }
        });
        JPanel var1 = new JPanel();
        var1.setLayout(new BoxLayout(var1, 1));
        var1.add(this.title);
        var1.add(Box.createVerticalGlue());
        var1.add(this.copyright);
        var1.add(this.copyright1);
        var1.add(this.copyright2);
        var1.add(Box.createVerticalGlue());
        var1.add(this.copyright2);
        var1.add(Box.createVerticalGlue());
        var1.add(this.reservedRight);
        JPanel var2 = new JPanel();
        var2.setLayout(new BorderLayout(11, 0));
        var2.add(this.logo, "West");
        var2.add(var1, "Center");
        JPanel var3 = new JPanel();
        var3.setLayout(new FlowLayout(1, 0, 0));
        var3.add(this.btnOK);
        JPanel var4 = new JPanel();
        var4.setLayout(new BoxLayout(var4, 1));
        var4.add(var2);
        var4.add(Box.createRigidArea(new Dimension(0, 11)));
        var4.add(this.spTable);
        var4.add(Box.createRigidArea(new Dimension(0, 11)));
        var4.add(this.spTrademark);
        var4.add(Box.createRigidArea(new Dimension(0, 11)));
        var4.add(var3);
        var4.setBorder(UIConstants.dialogBorder);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(var4, "Center");
        this.getRootPane().setDefaultButton(this.btnOK);
        this.setTitle("关于");//TODO 修改国际化
        this.setVisible(false);
        this.setSize(500, 300);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent var1) {
                DlgAbout.this.DlgAbout_windowClosing();
            }
        });
    }

    protected void setLogoVisible(boolean var1) {
        this.logo.setVisible(var1);
    }

    protected JTable getTable() {
        return this.table;
    }

    protected JLabel[] getCopyrights() {
        JLabel[] var1 = new JLabel[]{this.copyright, this.copyright1, this.copyright2};
        return var1;
    }

    protected void closeDialog() {
        this.close(false);
    }

    class TableModelAdapter extends AbstractTableModel {
        String[] columns = new String[]{"Property", "Value"};
        Object[] keys;

        TableModelAdapter() {
            this.keys = Utility.sort(DlgAbout.this.properties.keySet().toArray());
        }

        public boolean isCellEditable(int var1, int var2) {
            return false;
        }

        public int getColumnCount() {
            return this.columns.length;
        }

        public int getRowCount() {
            return this.keys.length;
        }

        public Object getValueAt(int var1, int var2) {
            if (var1 < this.getRowCount()) {
                Object var3 = this.keys[var1];
                return var2 == 0 ? var3 : DlgAbout.this.properties.get(var3);
            } else {
                return null;
            }
        }
    }

    class ImagePanel extends JPanel {
        Image image;

        ImagePanel(String var2) {
            this.image = Toolkit.getDefaultToolkit().getImage(DlgAbout.class.getResource(var2));
        }

        public void paintComponent(Graphics var1) {
            super.paintComponent(var1);
            Insets var2 = this.getInsets();
            var1.drawImage(this.image, var2 != null ? var2.left : 0, var2 != null ? var2.top : 0, this);
        }
    }
}
