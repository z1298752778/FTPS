package test.model;

import java.util.ArrayList;
import java.util.List;

public class MaterialTraceabilityModel {
    private int level; // 层级
    private String batchNumber; // 批号
    private String subBatchNumber; // 子批次号
    private String materialCode; // 物料编码
    private String materialName; // 物料名称
    private String workOrderNumber; // 工单号
    private String workOrderBatchNumber; // 工单批次号
    private String workOrderProductMaterialCode; // 工单产品物料编码
    private String workOrderProductMaterialName; // 工单产品物料名称
    private String feedingUnitProcess; // 投料单位工序
    private double feedingAmount; // 投料量
    private String feeder; // 投料人
    private String feedingTime; // 投料时间
    private List<MaterialTraceabilityModel> children = new ArrayList<>(); // 子节点
    boolean expanded; // 是否展开
    boolean hasChildren; // 是否有子节点

    public MaterialTraceabilityModel() {

    }

    public MaterialTraceabilityModel(int level, String subBatchNumber, String batchNumber, String materialCode, boolean expanded, boolean hasChildren){
        this.level = level;
        this.batchNumber = batchNumber;
        this.subBatchNumber = subBatchNumber;
        this.materialCode = materialCode;
        this.expanded = expanded;
        this.hasChildren = hasChildren;
    }

    public MaterialTraceabilityModel(int level, String subBatchNumber, String batchNumber, String materialCode, String materialName, String workOrderNumber,
                                     String workOrderBatchNumber, String workOrderProductMaterialCode,
                                     String workOrderProductMaterialName, String feedingUnitProcess, double feedingAmount,
                                     String feeder, String feedingTime, List<MaterialTraceabilityModel> children) {
        this.level = level;
        this.batchNumber = batchNumber;
        this.subBatchNumber = subBatchNumber;
        this.materialCode = materialCode;
        this.materialName = materialName;
        this.workOrderNumber = workOrderNumber;
        this.workOrderBatchNumber = workOrderBatchNumber;
        this.workOrderProductMaterialCode = workOrderProductMaterialCode;
        this.workOrderProductMaterialName = workOrderProductMaterialName;
        this.feedingUnitProcess = feedingUnitProcess;
        this.feedingAmount = feedingAmount;
        this.feeder = feeder;
        this.feedingTime = feedingTime;
        this.children = children;
    }


    public void setLevel(int level) {
        this.level = level;
    }

    public List<MaterialTraceabilityModel> getChildren() {
        return children;
    }
    public void setChildren(List<MaterialTraceabilityModel> children) {
        this.children = children;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public int getLevel() {
        return level;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getSubBatchNumber() {
        return subBatchNumber;
    }

    public void setSubBatchNumber(String subBatchNumber) {
        this.subBatchNumber = subBatchNumber;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public void setWorkOrderNumber(String workOrderNumber) {
        this.workOrderNumber = workOrderNumber;
    }

    public String getWorkOrderBatchNumber() {
        return workOrderBatchNumber;
    }

    public void setWorkOrderBatchNumber(String workOrderBatchNumber) {
        this.workOrderBatchNumber = workOrderBatchNumber;
    }

    public String getWorkOrderProductMaterialCode() {
        return workOrderProductMaterialCode;
    }

    public void setWorkOrderProductMaterialCode(String workOrderProductMaterialCode) {
        this.workOrderProductMaterialCode = workOrderProductMaterialCode;
    }

    public String getWorkOrderProductMaterialName() {
        return workOrderProductMaterialName;
    }

    public void setWorkOrderProductMaterialName(String workOrderProductMaterialName) {
        this.workOrderProductMaterialName = workOrderProductMaterialName;
    }

    public String getFeedingUnitProcess() {
        return feedingUnitProcess;
    }

    public void setFeedingUnitProcess(String feedingUnitProcess) {
        this.feedingUnitProcess = feedingUnitProcess;
    }

    public double getFeedingAmount() {
        return feedingAmount;
    }

    public void setFeedingAmount(double feedingAmount) {
        this.feedingAmount = feedingAmount;
    }

    public String getFeeder() {
        return feeder;
    }

    public void setFeeder(String feeder) {
        this.feeder = feeder;
    }

    public String getFeedingTime() {
        return feedingTime;
    }

    public void setFeedingTime(String feedingTime) {
        this.feedingTime = feedingTime;
    }
}