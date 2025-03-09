package test.utility;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class SimpleTreeTable extends JFrame {
    private final JTable table;
    private final DefaultTableModel model;
    private final List<Node> nodes = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimpleTreeTable().setVisible(true));
    }

    public SimpleTreeTable() {
        setTitle("简单树形表格");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 初始化表格模型
        model = new DefaultTableModel(new Object[]{"结构", "名称", "数据"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // 初始化测试数据
        initTestData();
        refreshTable();

        table = new JTable(model);
        table.setRowHeight(25);
        table.getColumnModel().getColumn(0).setCellRenderer((TableCellRenderer) new TreeCellRenderer());
        table.addMouseListener(new TreeCellClickListener());

        add(new JScrollPane(table));
    }

    // 节点数据结构
    static class Node {
        String name;
        int level;
        boolean expanded;
        boolean hasChildren;
        List<Node> children = new ArrayList<>();

        public Node(String name, int level, boolean hasChildren) {
            this.name = name;
            this.level = level;
            this.hasChildren = hasChildren;
        }
    }

    // 初始化测试数据
    private void initTestData() {
        nodes.add(new Node("部门A", 0, true));
        nodes.add(new Node("部门B", 0, true));
        nodes.get(0).children.add(new Node("子部门A1", 1, false));
        nodes.get(0).children.add(new Node("子部门A2", 1, true));
        nodes.get(1).children.add(new Node("子部门B1", 1, false));
    }

    // 刷新表格显示
    private void refreshTable() {
        model.setRowCount(0);
        for (Node node : nodes) {
            addNodeToTable(node);
        }
    }

    // 递归添加节点到表格
    private void addNodeToTable(Node node) {
        model.addRow(new Object[]{node.name, "节点数据", "其他信息"});
        if (node.expanded) {
            for (Node child : node.children) {
                addNodeToTable(child);
            }
        }
    }

    // 树形单元格渲染器
    class TreeCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 0) {
                Node node = findNodeByRow(row);
                String prefix = node.hasChildren ?
                        (node.expanded ? "- " : "+ ") : "  ";
                int indentSize = Math.max(1, node.level * 2); // 确保至少有一个空格
                String indent = String.format("%" + indentSize + "s", "");
                setText(indent + prefix + node.name.replaceFirst("[+-]\\s*", ""));
            }
            return c;
        }
    }

    // 查找对应行号的节点
    private Node findNodeByRow(int targetRow) {
        List<Node> path = new ArrayList<>();
        int currentRow = -1;
        for (Node node : nodes) {
            currentRow = traverseNodes(node, currentRow, targetRow, path);
            if (currentRow >= targetRow) break;
        }
        return path.get(path.size()-1);
    }

    // 递归遍历节点查找对应行
    private int traverseNodes(Node node, int currentRow, int targetRow, List<Node> path) {
        path.add(node);
        currentRow++;
        if (currentRow == targetRow) return currentRow;

        if (node.expanded) {
            for (Node child : node.children) {
                currentRow = traverseNodes(child, currentRow, targetRow, path);
                if (currentRow >= targetRow) break;
                path.remove(child);
            }
        }
        return currentRow;
    }

    // 单元格点击监听
    class TreeCellClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int row = table.rowAtPoint(e.getPoint());
            int col = table.columnAtPoint(e.getPoint());

            if (row >= 0 && col == 0) {
                Node clickedNode = findNodeByRow(row);
                if (clickedNode.hasChildren) {
                    clickedNode.expanded = !clickedNode.expanded;
                    refreshTable();
                }
            }
        }
    }
}