package com.leateck.model.lossQuantityAccountConsumption;

/**
 * This file is generated by ATDefAccessClassGenerator and FMPP 2.3.15
 *
 * Please do not modify this file manually !!
 */
import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.MeasuredValue;
import com.datasweep.compatibility.client.Response;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;

/**
 * Generated class definition for application table LC_LossQtyAccountCon.<BR>
 * Application table description: 保存消耗phase损耗量
 */
public abstract class MESGeneratedLCLossQtyAccountCon extends MESATObject //
        implements IMESGeneratedLCLossQtyAccountCon {

    @Override
    public String getATDefinitionName() {
        return ATDEFINITION_NAME;
    }

    /**
     * Generated constructor
     * 
     * @param key The key of the ATRow to load.
     */
    public MESGeneratedLCLossQtyAccountCon(long key) {
        super(key);
    }

    /**
     * Generated copy constructor
     * 
     * @param source The source to copy.
     */
    public MESGeneratedLCLossQtyAccountCon(MESGeneratedLCLossQtyAccountCon source) {
        super(source);
    }

    /**
     * Generated constructor
     * 
     * @param baseATRow The ATRow to wrap.
     */
    public MESGeneratedLCLossQtyAccountCon(ATRow baseATRow) {
        super(baseATRow);
    }

    /**
     * Generated default constructor
     */
    public MESGeneratedLCLossQtyAccountCon() {
        super();
    }

    @Override
    protected void synchronizeAfterATRowRefresh() {
        super.synchronizeAfterATRowRefresh();
    }

    @Override
    public MeasuredValue getLossQty() {
        return (MeasuredValue) this.dgtATRow.getValue(COL_NAME_LOSSQTY);
    }

    @Override
    public void setLossQty(MeasuredValue value) {
        MeasuredValue oldValue = this.getLossQty();
        this.dgtATRow.setValue(COL_NAME_LOSSQTY, value);
        firePropertyChange(PROP_NAME_LOSSQTY, oldValue, value);
    }

    @Override
    public Long getPhaseKey() {
        return (Long) this.dgtATRow.getValue(COL_NAME_PHASEKEY);
    }

    @Override
    public void setPhaseKey(Long value) {
        Long oldValue = this.getPhaseKey();
        this.dgtATRow.setValue(COL_NAME_PHASEKEY, value);
        firePropertyChange(PROP_NAME_PHASEKEY, oldValue, value);
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
    protected Response prepareATRowForSave() {
        // Check if transient references are valid and store the corresponding keys in the ATRow:
        Response res = super.prepareATRowForSave();



        return res;
    }

}