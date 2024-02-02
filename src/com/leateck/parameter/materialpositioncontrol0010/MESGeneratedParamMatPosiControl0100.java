package com.leateck.parameter.materialpositioncontrol0010;

/**
 * This file is generated by ParameterClassManager
 *
 * Please do not modify this file manually !!
 */
import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.AccessPrivilege;
import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.Response;
import com.datasweep.compatibility.ui.Time;
import com.rockwell.mes.commons.base.ifc.objects.BulkSaveableMESATObject;




/**
 * Generated class definition
* <br/>Application table: LC_ParamMatPosiControl0100
 */
public abstract class MESGeneratedParamMatPosiControl0100
 extends BulkSaveableMESATObject {

    /** Generated attribute definition */
    protected static final String ATDEFINITION_NAME = "LC_ParamMatPosiControl0100";


    @Override
    public String getATDefinitionName() {
        return ATDEFINITION_NAME;
    }

    /**
     * Generated constructor
     *
     * @param key The key of the ATRow to load.
     */
    public MESGeneratedParamMatPosiControl0100(long key) {
        super(key);     
    }

    /**
     * Generated copy constructor
     *
     * @param source the source to copy.
     */
    public MESGeneratedParamMatPosiControl0100(MESGeneratedParamMatPosiControl0100 source) {
        super(source);
    }

    /**
     * Generated constructor
     *
     * @param baseATRow The ATRow to wrap.
     */
    public MESGeneratedParamMatPosiControl0100(ATRow baseATRow) {
        super(baseATRow);    
    }

    /**
     * Generated constructor
     */
    public MESGeneratedParamMatPosiControl0100() {
        super();
    }

    @Override
    protected void synchronizeAfterATRowRefresh() {
        super.synchronizeAfterATRowRefresh();
    }    
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public Boolean getEnable() {
        return (Boolean) this.dgtATRow.getValue("LC_Enable");
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setEnable(Boolean value) {
        Boolean oldValue = this.getEnable();
        this.dgtATRow.setValue("LC_Enable", value);
        pcs.firePropertyChange("enable", oldValue, value);
    }

    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public String getStorageArea() {
        return (String) this.dgtATRow.getValue("LC_StorageArea");
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setStorageArea(String value) {
        String oldValue = this.getStorageArea();
        this.dgtATRow.setValue("LC_StorageArea", value);
        pcs.firePropertyChange("storageArea", oldValue, value);
    }

    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public String getStorageLocation() {
        return (String) this.dgtATRow.getValue("LC_StorageLocation");
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setStorageLocation(String value) {
        String oldValue = this.getStorageLocation();
        this.dgtATRow.setValue("LC_StorageLocation", value);
        pcs.firePropertyChange("storageLocation", oldValue, value);
    }

    @Override
    public Response prepareATRowForSave(boolean isBulkSave) {
        // Check if cached references are valid:
        Response res = super.prepareATRowForSave(isBulkSave);

        return res;
    }

    /**
     * Overridden to make the method <b>final</b> to ensure that it does nothing else but calling the method from the
     * base class. Note: "nothing else" matters, so do not remove or change anything.
     *
     * {@inheritDoc}
     */
    // CHECKSTYLE:MethodNameCheck:off
    //@SuppressFBWarnings(value = "NM_METHOD_NAMING_CONVENTION", justification = "super class")
    @Override
    public final void Save(Time time, String comment, AccessPrivilege accessPrivilege) //
            throws DatasweepException {
        super.Save(time, comment, accessPrivilege);
    }
    // CHECKSTYLE:MethodNameCheck:on
}
