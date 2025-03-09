package test;

import org.jdesktop.swingx.JXTreeTable;

import javax.swing.*;

public class BOMSwingXDemo {

    public BOMSwingXDemo (){

    }

    public JXTreeTable getBOMSwingXDemo (){

        // 创建测试数据模型
        BomTreeTableModel model = new BomTreeTableModel(createTestData());

        // 创建树表组件
        JXTreeTable treeTable = new JXTreeTable(model);
        treeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        treeTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        treeTable.getColumnModel().getColumn(1).setPreferredWidth(400);
        treeTable.getColumnModel().getColumn(2).setPreferredWidth(100);

        // 展开所有节点
        treeTable.expandAll();

        return treeTable;

    }

    // 测试数据构造
    private static BomNode createTestData() {
        BomNode root = new BomNode("ROOT", "总成产品", 1);

        BomNode l1_1 = new BomNode("A-100", "主壳体组件", 1);
        BomNode l1_2 = new BomNode("B-200", "控制系统组件", 1);

        BomNode l2_1 = new BomNode("A-101", "上壳体", 1);
        BomNode l2_2 = new BomNode("A-102", "下壳体", 1);
        BomNode l2_3 = new BomNode("B-201", "控制主板", 2);

        l1_1.addChild(l2_1);
        l1_1.addChild(l2_2);
        l1_2.addChild(l2_3);

        l2_1.addChild(new BomNode("A-101-1", "壳体面板", 1));
        l2_3.addChild(new BomNode("B-201-1", "CPU芯片", 4));

        root.addChild(l1_1);
        root.addChild(l1_2);
        return root;
    }
}



