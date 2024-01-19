package com.leateck.model.areaFilterObject;

import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.Response;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;

/**
 * Generated class definition for application table LC_StorageAreaFilterObject.<BR>
 * Application table description: 
 */
public abstract class MESGeneratedLCStorageAreaFilterObject extends MESATObject //
        implements IMESGeneratedLCStorageAreaFilterObject {

    @Override
    public String getATDefinitionName() {
        return ATDEFINITION_NAME;
    }

    /**
     * Generated constructor
     * 
     * @param key The key of the ATRow to load.
     */
    public MESGeneratedLCStorageAreaFilterObject(long key) {
        super(key);
    }

    /**
     * Generated copy constructor
     * 
     * @param source The source to copy.
     */
    public MESGeneratedLCStorageAreaFilterObject(MESGeneratedLCStorageAreaFilterObject source) {
        super(source);
    }

    /**
     * Generated constructor
     * 
     * @param baseATRow The ATRow to wrap.
     */
    public MESGeneratedLCStorageAreaFilterObject(ATRow baseATRow) {
        super(baseATRow);
    }

    /**
     * Generated default constructor
     */
    public MESGeneratedLCStorageAreaFilterObject() {
        super();
    }

    @Override
    protected void synchronizeAfterATRowRefresh() {
        super.synchronizeAfterATRowRefresh();
    }

    @Override
    public String getStorageArea() {
        return (String) this.dgtATRow.getValue(COL_NAME_STORAGEAREA);
    }

    @Override
    public void setStorageArea(String value) {
        String oldValue = this.getStorageArea();
        this.dgtATRow.setValue(COL_NAME_STORAGEAREA, value);
        firePropertyChange(PROP_NAME_STORAGEAREA, oldValue, value);
    }

    @Override
    public String getStorageAreaDesc() {
        return (String) this.dgtATRow.getValue(COL_NAME_STORAGEAREADESC);
    }

    @Override
    public void setStorageAreaDesc(String value) {
        String oldValue = this.getStorageAreaDesc();
        this.dgtATRow.setValue(COL_NAME_STORAGEAREADESC, value);
        firePropertyChange(PROP_NAME_STORAGEAREADESC, oldValue, value);
    }

    @Override
    public String getWarehouse() {
        return (String) this.dgtATRow.getValue(COL_NAME_WAREHOUSE);
    }

    @Override
    public void setWarehouse(String value) {
        String oldValue = this.getWarehouse();
        this.dgtATRow.setValue(COL_NAME_WAREHOUSE, value);
        firePropertyChange(PROP_NAME_WAREHOUSE, oldValue, value);
    }

    @Override
    protected Response prepareATRowForSave() {
        // Check if transient references are valid and store the corresponding keys in the ATRow:
        Response res = super.prepareATRowForSave();



        return res;
    }

}