package com.leateck.model.userProcessPackRecord;

/**
 * This file is generated by ATDefAccessClassGenerator and FMPP 2.3.15
 *
 * Please do not modify this file manually !!
 */
import com.leateck.model.userProcessPackRecord.IMESGeneratedLCUserProcessPackRecord;
import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.Response;
import com.datasweep.compatibility.ui.Time;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;

/**
 * Generated class definition for application table LC_UserProcessPackRecord.<BR>
 * Application table description: 
 */
public abstract class MESGeneratedLCUserProcessPackRecord extends MESATObject //
        implements IMESGeneratedLCUserProcessPackRecord {

    @Override
    public String getATDefinitionName() {
        return ATDEFINITION_NAME;
    }

    /**
     * Generated constructor
     * 
     * @param key The key of the ATRow to load.
     */
    public MESGeneratedLCUserProcessPackRecord(long key) {
        super(key);
    }

    /**
     * Generated copy constructor
     * 
     * @param source The source to copy.
     */
    public MESGeneratedLCUserProcessPackRecord(MESGeneratedLCUserProcessPackRecord source) {
        super(source);
    }

    /**
     * Generated constructor
     * 
     * @param baseATRow The ATRow to wrap.
     */
    public MESGeneratedLCUserProcessPackRecord(ATRow baseATRow) {
        super(baseATRow);
    }

    /**
     * Generated default constructor
     */
    public MESGeneratedLCUserProcessPackRecord() {
        super();
    }

    @Override
    protected void synchronizeAfterATRowRefresh() {
        super.synchronizeAfterATRowRefresh();
    }

    @Override
    public String getComments() {
        return (String) this.dgtATRow.getValue(COL_NAME_COMMENTS);
    }

    @Override
    public void setComments(String value) {
        String oldValue = this.getComments();
        this.dgtATRow.setValue(COL_NAME_COMMENTS, value);
        firePropertyChange(PROP_NAME_COMMENTS, oldValue, value);
    }

    @Override
    public String getOperator() {
        return (String) this.dgtATRow.getValue(COL_NAME_OPERATOR);
    }

    @Override
    public void setOperator(String value) {
        String oldValue = this.getOperator();
        this.dgtATRow.setValue(COL_NAME_OPERATOR, value);
        firePropertyChange(PROP_NAME_OPERATOR, oldValue, value);
    }

    @Override
    public String getProcessPackIDes() {
        return (String) this.dgtATRow.getValue(COL_NAME_PROCESSPACKIDES);
    }

    @Override
    public void setProcessPackIDes(String value) {
        String oldValue = this.getProcessPackIDes();
        this.dgtATRow.setValue(COL_NAME_PROCESSPACKIDES, value);
        firePropertyChange(PROP_NAME_PROCESSPACKIDES, oldValue, value);
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
    public Time getStateTime() {
        return (Time) this.dgtATRow.getValue(COL_NAME_STATETIME);
    }

    @Override
    public void setStateTime(Time value) {
        Time oldValue = this.getStateTime();
        this.dgtATRow.setValue(COL_NAME_STATETIME, value);
        firePropertyChange(PROP_NAME_STATETIME, oldValue, value);
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