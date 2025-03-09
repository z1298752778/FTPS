package com.leateck.commons.materiaInventoryImport.entity;

import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.Response;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;

/**
 * Generated class definition for application table CD_MateriaInventorySublot.<BR>
 * Application table description: 
 */
public abstract class MESGeneratedCDMateriaInventorySublot extends MESATObject //
        implements IMESGeneratedCDMateriaInventorySublot {

    @Override
    public String getATDefinitionName() {
        return ATDEFINITION_NAME;
    }

    /**
     * Generated constructor
     * 
     * @param key The key of the ATRow to load.
     */
    public MESGeneratedCDMateriaInventorySublot(long key) {
        super(key);
    }

    /**
     * Generated copy constructor
     * 
     * @param source The source to copy.
     */
    public MESGeneratedCDMateriaInventorySublot(MESGeneratedCDMateriaInventorySublot source) {
        super(source);
    }

    /**
     * Generated constructor
     * 
     * @param baseATRow The ATRow to wrap.
     */
    public MESGeneratedCDMateriaInventorySublot(ATRow baseATRow) {
        super(baseATRow);
    }

    /**
     * Generated default constructor
     */
    public MESGeneratedCDMateriaInventorySublot() {
        super();
    }

    @Override
    protected void synchronizeAfterATRowRefresh() {
        super.synchronizeAfterATRowRefresh();
    }

    @Override
    public String getBatchNo() {
        return (String) this.dgtATRow.getValue(COL_NAME_BATCHNO);
    }

    @Override
    public void setBatchNo(String value) {
        String oldValue = this.getBatchNo();
        this.dgtATRow.setValue(COL_NAME_BATCHNO, value);
        firePropertyChange(PROP_NAME_BATCHNO, oldValue, value);
    }

    @Override
    public String getImportNumber() {
        return (String) this.dgtATRow.getValue(COL_NAME_IMPORTNUMBER);
    }

    @Override
    public void setImportNumber(String value) {
        String oldValue = this.getImportNumber();
        this.dgtATRow.setValue(COL_NAME_IMPORTNUMBER, value);
        firePropertyChange(PROP_NAME_IMPORTNUMBER, oldValue, value);
    }

    @Override
    public String getInventoryQty() {
        return (String) this.dgtATRow.getValue(COL_NAME_INVENTORYQTY);
    }

    @Override
    public void setInventoryQty(String value) {
        String oldValue = this.getInventoryQty();
        this.dgtATRow.setValue(COL_NAME_INVENTORYQTY, value);
        firePropertyChange(PROP_NAME_INVENTORYQTY, oldValue, value);
    }

    @Override
    public String getMaterialDesc() {
        return (String) this.dgtATRow.getValue(COL_NAME_MATERIALDESC);
    }

    @Override
    public void setMaterialDesc(String value) {
        String oldValue = this.getMaterialDesc();
        this.dgtATRow.setValue(COL_NAME_MATERIALDESC, value);
        firePropertyChange(PROP_NAME_MATERIALDESC, oldValue, value);
    }

    @Override
    public String getMaterialNo() {
        return (String) this.dgtATRow.getValue(COL_NAME_MATERIALNO);
    }

    @Override
    public void setMaterialNo(String value) {
        String oldValue = this.getMaterialNo();
        this.dgtATRow.setValue(COL_NAME_MATERIALNO, value);
        firePropertyChange(PROP_NAME_MATERIALNO, oldValue, value);
    }

    @Override
    public String getQuantity() {
        return (String) this.dgtATRow.getValue(COL_NAME_QUANTITY);
    }

    @Override
    public void setQuantity(String value) {
        String oldValue = this.getQuantity();
        this.dgtATRow.setValue(COL_NAME_QUANTITY, value);
        firePropertyChange(PROP_NAME_QUANTITY, oldValue, value);
    }

    @Override
    public String getSpecifications() {
        return (String) this.dgtATRow.getValue(COL_NAME_SPECIFICATIONS);
    }

    @Override
    public void setSpecifications(String value) {
        String oldValue = this.getSpecifications();
        this.dgtATRow.setValue(COL_NAME_SPECIFICATIONS, value);
        firePropertyChange(PROP_NAME_SPECIFICATIONS, oldValue, value);
    }

    @Override
    public String getSublotNumber() {
        return (String) this.dgtATRow.getValue(COL_NAME_SUBLOTNUMBER);
    }

    @Override
    public void setSublotNumber(String value) {
        String oldValue = this.getSublotNumber();
        this.dgtATRow.setValue(COL_NAME_SUBLOTNUMBER, value);
        firePropertyChange(PROP_NAME_SUBLOTNUMBER, oldValue, value);
    }

    @Override
    public String getUnit() {
        return (String) this.dgtATRow.getValue(COL_NAME_UNIT);
    }

    @Override
    public void setUnit(String value) {
        String oldValue = this.getUnit();
        this.dgtATRow.setValue(COL_NAME_UNIT, value);
        firePropertyChange(PROP_NAME_UNIT, oldValue, value);
    }

    @Override
    public String getUom() {
        return (String) this.dgtATRow.getValue(COL_NAME_UOM);
    }

    @Override
    public void setUom(String value) {
        String oldValue = this.getUom();
        this.dgtATRow.setValue(COL_NAME_UOM, value);
        firePropertyChange(PROP_NAME_UOM, oldValue, value);
    }

    @Override
    public String getValidityData() {
        return (String) this.dgtATRow.getValue(COL_NAME_VALIDITYDATA);
    }

    @Override
    public void setValidityData(String value) {
        String oldValue = this.getValidityData();
        this.dgtATRow.setValue(COL_NAME_VALIDITYDATA, value);
        firePropertyChange(PROP_NAME_VALIDITYDATA, oldValue, value);
    }

    @Override
    protected Response prepareATRowForSave() {
        // Check if transient references are valid and store the corresponding keys in the ATRow:
        Response res = super.prepareATRowForSave();



        return res;
    }

}