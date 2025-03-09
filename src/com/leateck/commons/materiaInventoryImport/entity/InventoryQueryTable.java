package com.leateck.commons.materiaInventoryImport.entity;

/**
 * XXX
 *
 * @author litao
 * @version 1.01.1
 * @since 2023/3/27
 */
public class InventoryQueryTable {
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
    // 最小包装规格
    public String packagingSpecifications  ;
    // 单位
    public String unit ;
    // 子批次个数
    public String sublotNum;
    // 接收状态
    public String tip;
    // 导入编号
    public String importNo;

    public InventoryQueryTable() {
    }

    public InventoryQueryTable(String materialNo, String materialDesc, String batchNo, String inventoryQty, String uom, String specifications, String validityData, String packagingSpecifications, String unit, String sublotNum, String tip, String importNo) {
        this.materialNo = materialNo;
        this.materialDesc = materialDesc;
        this.batchNo = batchNo;
        this.inventoryQty = inventoryQty;
        this.uom = uom;
        this.specifications = specifications;
        this.validityData = validityData;
        this.packagingSpecifications = packagingSpecifications;
        this.unit = unit;
        this.sublotNum = sublotNum;
        this.tip = tip;
        this.importNo = importNo;
    }

    @Override
    public String toString() {
        return "InventoryQueryTable{" +
                "materialNo='" + materialNo + '\'' +
                ", materialDesc='" + materialDesc + '\'' +
                ", batchNo='" + batchNo + '\'' +
                ", inventoryQty='" + inventoryQty + '\'' +
                ", uom='" + uom + '\'' +
                ", specifications='" + specifications + '\'' +
                ", validityData='" + validityData + '\'' +
                ", packagingSpecifications='" + packagingSpecifications + '\'' +
                ", unit='" + unit + '\'' +
                ", sublotNum='" + sublotNum + '\'' +
                ", tip='" + tip + '\'' +
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

    public String getPackagingSpecifications() {
        return packagingSpecifications;
    }

    public void setPackagingSpecifications(String packagingSpecifications) {
        this.packagingSpecifications = packagingSpecifications;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSublotNum() {
        return sublotNum;
    }

    public void setSublotNum(String sublotNum) {
        this.sublotNum = sublotNum;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getImportNo() {
        return importNo;
    }

    public void setImportNo(String importNo) {
        this.importNo = importNo;
    }
}
