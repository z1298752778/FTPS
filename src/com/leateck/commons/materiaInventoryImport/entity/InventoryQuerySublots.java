package com.leateck.commons.materiaInventoryImport.entity;

/**
 * XXX
 *
 * @author litao
 * @version 1.01.1
 * @since 2023/3/27
 */
public class InventoryQuerySublots {
    // 物料代码
    public String materialNo;
    // 物料名称
    public String materialDesc;
    // 物料批次
    public String batchNo  ;
    // 入厂编号
    public String inventoryQty ;
    // 供应商名称
    public String uom  ;
    // 规格
    public String specifications  ;
    // 有效期
    public String validityData ;
    // 数量
    public String quantity;
    // 单位
    public String unit ;
    // 子批次号
    public String sublotNumber;
    // 导入编号
    public String importNo;

    public InventoryQuerySublots() {
    }

    public InventoryQuerySublots(String materialNo, String materialDesc, String batchNo, String inventoryQty, String uom, String specifications, String validityData, String quantity, String unit, String sublotNumber, String importNo) {
        this.materialNo = materialNo;
        this.materialDesc = materialDesc;
        this.batchNo = batchNo;
        this.inventoryQty = inventoryQty;
        this.uom = uom;
        this.specifications = specifications;
        this.validityData = validityData;
        this.quantity = quantity;
        this.unit = unit;
        this.sublotNumber = sublotNumber;
        this.importNo = importNo;
    }

    @Override
    public String toString() {
        return "InventoryQuerySublots{" +
                "materialNo='" + materialNo + '\'' +
                ", materialDesc='" + materialDesc + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", inventoryQty='" + inventoryQty + '\'' +
                ", uom='" + uom + '\'' +
                ", specifications='" + specifications + '\'' +
                ", validityData='" + validityData + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unit='" + unit + '\'' +
                ", sublotNumber='" + sublotNumber + '\'' +
                ", importNo='" + importNo + '\'' +
                '}';
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getMaterialDesc() {
        return materialDesc;
    }

    public void setMaterialDesc(String materialDesc) {
        this.materialDesc = materialDesc;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getInventoryQty() {
        return inventoryQty;
    }

    public void setInventoryQty(String inventoryQty) {
        this.inventoryQty = inventoryQty;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getValidityData() {
        return validityData;
    }

    public void setValidityData(String validityData) {
        this.validityData = validityData;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSublotNumber() {
        return sublotNumber;
    }

    public void setSublotNumber(String sublotNumber) {
        this.sublotNumber = sublotNumber;
    }

    public String getImportNo() {
        return importNo;
    }

    public void setImportNo(String importNo) {
        this.importNo = importNo;
    }
}
