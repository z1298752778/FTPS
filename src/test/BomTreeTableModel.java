package test;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;

// 树表模型
public class BomTreeTableModel extends AbstractTreeTableModel {
    private final String[] COLUMNS = {"物料编码", "物料名称", "数量"};

    public BomTreeTableModel(BomNode root) {
        super(root);
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public Object getValueAt(Object node, int column) {
        BomNode bomNode = (BomNode) node;
        switch (column) {
            case 0:
                return bomNode.getPartNumber();
            case 1:
                return bomNode.getPartName();
            case 2:
                return bomNode.getQuantity();
            default:
                return "";
        }
    }

    @Override
    public Object getChild(Object parent, int index) {
        return ((BomNode) parent).getChildren().get(index);
    }

    @Override
    public int getChildCount(Object parent) {
        return ((BomNode) parent).getChildren().size();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return ((BomNode) parent).getChildren().indexOf(child);
    }
}