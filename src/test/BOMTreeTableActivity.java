package test;

import com.datasweep.compatibility.client.Response;
import com.datasweep.compatibility.ui.ActivityControl;
import com.datasweep.compatibility.ui.CComponent;
import com.datasweep.compatibility.ui.Edit;
import com.datasweep.compatibility.ui.LayoutStyle;
import com.rockwell.activity.ItemDescriptor;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.SwingWrapperPanel;
import org.jdesktop.swingx.JXTreeTable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BOMTreeTableActivity extends ActivityControl {


    final int LABEL_WIDTH = 100;
    final int EDIT_WIDTH = 100;
    final int TESTGUI_HEIGHT = 20;

    public static final String COLUMN_NAMES = "ColumnNames";
    public static final String DATA_SOURCE = "DataSource";
    public static final String AUTO_EXPAND = "AutoExpand";

    private static final String NODE_SELECTED_EVENT = "nodeSelected";
    private static final String DATA_LOADED_EVENT = "dataLoaded";
    private static final String TREE_EXPANDED_EVENT = "treeExpanded";

    public JXTreeTable treeTable;
    private BomTreeTableModel model;
    private Map<String, Object> configurationItems = new HashMap<String, Object>();



    public BOMTreeTableActivity() {
        super();
        setup();
    }

    @Override
    public Response activityExecute() {
        return null;
    }

    @Override
    protected void configurationItemSet(String s, Object o) {

    }

    @Override
    protected void configurationLoaded() {

    }

    @Override
    protected String[] getActivityEvents() {
        return new String[0];
    }


    @Override
    protected void inputItemSet(String s, Object o) {

    }

    @Override
    protected void startup() {

    }

    @Override
    protected void shutdown() {

    }

    @Override
    protected void updateAfterExecute() {

    }


    @Override
    public ItemDescriptor[] configurationDescriptors() {
        return new ItemDescriptor[0];
    }

    @Override
    public ItemDescriptor[] inputDescriptors() {
        return new ItemDescriptor[0];
    }

    @Override
    public ItemDescriptor[] outputDescriptors() {
        return new ItemDescriptor[0];
    }

    @Override
    public String getActivityDescription() {
        return null;
    }

    public void setup() {
        setLayoutStyle(LayoutStyle.GRID2);
        setGridLayoutColumns(2);
        setGridLayoutRows(0);
        setControlSize(this, LABEL_WIDTH + EDIT_WIDTH, 4 * TESTGUI_HEIGHT);

        add(getSwingWrapperPanel());
        // Add GUI components.
//        add(getEditInput());
//        add(new BOMTreeTableActivity);
//        add(getCheckbox());
//        add(getOKButton());

    }

    private Edit getEditInput()
    {
        String internalName = getBaseName() + "test";
        Edit edit = (Edit) findActivityControl(internalName);
        // Only create the edit field if it is not already on the activity.
        if (edit == null)
        {
            edit = new Edit();
            setControlSize(edit, EDIT_WIDTH, TESTGUI_HEIGHT);
            edit.setName(edit.getBaseName() + "test");
            edit.setActivityName(internalName);
//            edit.addCComponentEventListener(this);
        }
        return edit;
    }

    private SwingWrapperPanel getSwingWrapperPanel()
    {

        treeTable =   new BOMSwingXDemo().getBOMSwingXDemo();
        JPanel jpanel = new JPanel();
        JTextField jTest = new JTextField();
        jTest.setText("11232312ss");
        jpanel.add(jTest);
        SwingWrapperPanel swingWrapperPanel = new SwingWrapperPanel(jpanel,false);
        add(swingWrapperPanel);

        String internalName = getBaseName() + "1";
        // Only create the edit field if it is not already on the activity.
            setControlSize(swingWrapperPanel, EDIT_WIDTH, TESTGUI_HEIGHT);
        swingWrapperPanel.setName(swingWrapperPanel.getBaseName() + "1");
        swingWrapperPanel.setActivityName(internalName);
//            edit.addCComponentEventListener(this);
        return swingWrapperPanel;
    }


//
//    // 配置项声明
//    @Override
//    public ItemDescriptor[] configurationDescriptors() {
//        return new ItemDescriptor[]{
//                new ItemDescriptor(COLUMN_NAMES, BOMTreeTableActivity.class, String[].class),
//                new ItemDescriptor(AUTO_EXPAND, BOMTreeTableActivity.class, Boolean.class)
//        };
//    }
//
//    // 配置项更新处理
//    @Override
//    protected void configurationItemSet(String key, Object value) {
//        switch (key) {
//            case COLUMN_NAMES:
//                updateColumnHeaders((String[]) value);
//                break;
//            case AUTO_EXPAND:
//                if (Boolean.TRUE.equals(value)) {
////                    (JXTreeTable)value.expandAll();
//                }
//                break;
//        }
//    }
//
//    private void updateColumnHeaders(String[] value) {
//
//    }
//
//    // 配置加载完成
//    @Override
//    protected void configurationLoaded() {
////        if (configurationItems.containsKey(COLUMN_NAMES)) {
////            updateColumnHeaders((String[]) configurationItems.get(COLUMN_NAMES));
////        }
////        if (configurationItems.containsKey(DATA_SOURCE)) {
////            loadDataFromSource((DatabaseConfig) configurationItems.get(DATA_SOURCE));
////        }
//    }
//
//
//
//    // 声明支持的事件类型
//    @Override
//    protected String[] getActivityEvents() {
//        return new String[]{
//                NODE_SELECTED_EVENT,
//                DATA_LOADED_EVENT,
//                TREE_EXPANDED_EVENT
//        };
//    }

}



