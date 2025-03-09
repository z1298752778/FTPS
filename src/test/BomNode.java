package test;

import java.util.ArrayList;
import java.util.List;

// 数据节点类
class BomNode {
    private String partNumber;
    private String partName;
    private int quantity;
    private List<BomNode> children = new ArrayList<>();
    private BomNode parent;

    public BomNode(String pn, String name, int qty) {
        this.partNumber = pn;
        this.partName = name;
        this.quantity = qty;
    }

    // 添加子节点方法
    public void addChild(BomNode child) {
        child.parent = this;
        children.add(child);
    }

    // Getters...
    public List<BomNode> getChildren() { return children; }
    public String getPartNumber() { return partNumber; }
    public String getPartName() { return partName; }
    public int getQuantity() { return quantity; }
}