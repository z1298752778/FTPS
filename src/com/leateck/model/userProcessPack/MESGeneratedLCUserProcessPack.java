package com.leateck.model.userProcessPack;

/**
 * This file is generated by ATDefAccessClassGenerator and FMPP 2.3.15
 *
 * Please do not modify this file manually !!
 */
import com.leateck.model.userProcessPack.IMESGeneratedLCUserProcessPack;
import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.Response;
import com.datasweep.compatibility.ui.Time;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;

/**
 * Generated class definition for application table LC_UserProcessPack.<BR>
 * Application table description: 
 */
public abstract class MESGeneratedLCUserProcessPack extends MESATObject //
        implements IMESGeneratedLCUserProcessPack {

    @Override
    public String getATDefinitionName() {
        return ATDEFINITION_NAME;
    }

    /**
     * Generated constructor
     * 
     * @param key The key of the ATRow to load.
     */
    public MESGeneratedLCUserProcessPack(long key) {
        super(key);
    }

    /**
     * Generated copy constructor
     * 
     * @param source The source to copy.
     */
    public MESGeneratedLCUserProcessPack(MESGeneratedLCUserProcessPack source) {
        super(source);
    }

    /**
     * Generated constructor
     * 
     * @param baseATRow The ATRow to wrap.
     */
    public MESGeneratedLCUserProcessPack(ATRow baseATRow) {
        super(baseATRow);
    }

    /**
     * Generated default constructor
     */
    public MESGeneratedLCUserProcessPack() {
        super();
    }

    @Override
    protected void synchronizeAfterATRowRefresh() {
        super.synchronizeAfterATRowRefresh();
    }

    @Override
    public Time getCreateTime() {
        return (Time) this.dgtATRow.getValue(COL_NAME_CREATETIME);
    }

    @Override
    public void setCreateTime(Time value) {
        Time oldValue = this.getCreateTime();
        this.dgtATRow.setValue(COL_NAME_CREATETIME, value);
        firePropertyChange(PROP_NAME_CREATETIME, oldValue, value);
    }

    @Override
    public Time getEntryTime() {
        return (Time) this.dgtATRow.getValue(COL_NAME_ENTRYTIME);
    }

    @Override
    public void setEntryTime(Time value) {
        Time oldValue = this.getEntryTime();
        this.dgtATRow.setValue(COL_NAME_ENTRYTIME, value);
        firePropertyChange(PROP_NAME_ENTRYTIME, oldValue, value);
    }

    @Override
    public Time getExpiryTime() {
        return (Time) this.dgtATRow.getValue(COL_NAME_EXPIRYTIME);
    }

    @Override
    public void setExpiryTime(Time value) {
        Time oldValue = this.getExpiryTime();
        this.dgtATRow.setValue(COL_NAME_EXPIRYTIME, value);
        firePropertyChange(PROP_NAME_EXPIRYTIME, oldValue, value);
    }

    @Override
    public String getProcessPackDes() {
        return (String) this.dgtATRow.getValue(COL_NAME_PROCESSPACKDES);
    }

    @Override
    public void setProcessPackDes(String value) {
        String oldValue = this.getProcessPackDes();
        this.dgtATRow.setValue(COL_NAME_PROCESSPACKDES, value);
        firePropertyChange(PROP_NAME_PROCESSPACKDES, oldValue, value);
    }

    @Override
    public String getProcessPackIde() {
        return (String) this.dgtATRow.getValue(COL_NAME_PROCESSPACKIDE);
    }

    @Override
    public void setProcessPackIde(String value) {
        String oldValue = this.getProcessPackIde();
        this.dgtATRow.setValue(COL_NAME_PROCESSPACKIDE, value);
        firePropertyChange(PROP_NAME_PROCESSPACKIDE, oldValue, value);
    }

    @Override
    public Long getProcessPackState() {
        return (Long) this.dgtATRow.getValue(COL_NAME_PROCESSPACKSTATE);
    }

    @Override
    public void setProcessPackState(Long value) {
        Long oldValue = this.getProcessPackState();
        this.dgtATRow.setValue(COL_NAME_PROCESSPACKSTATE, value);
        firePropertyChange(PROP_NAME_PROCESSPACKSTATE, oldValue, value);
    }

    @Override
    public String getVersion() {
        return (String) this.dgtATRow.getValue(COL_NAME_VERSION);
    }

    @Override
    public void setVersion(String value) {
        String oldValue = this.getVersion();
        this.dgtATRow.setValue(COL_NAME_VERSION, value);
        firePropertyChange(PROP_NAME_VERSION, oldValue, value);
    }

    @Override
    protected Response prepareATRowForSave() {
        // Check if transient references are valid and store the corresponding keys in the ATRow:
        Response res = super.prepareATRowForSave();



        return res;
    }

}