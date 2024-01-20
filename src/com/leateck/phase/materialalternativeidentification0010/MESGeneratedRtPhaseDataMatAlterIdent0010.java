package com.leateck.phase.materialalternativeidentification0010;

/**
 * This file is generated by the PhaseGenerator
 *
 * Please do not modify this file manually !!
 */

import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.Response;
import com.rockwell.mes.services.s88.ifc.processdata.MESRtPhaseData;

import com.rockwell.mes.commons.base.ifc.utility.StringUtilsEx;
import com.datasweep.compatibility.client.MeasuredValue;

 /**
 * Generated class definition
 * <br/>Application table: LC_PhDatMatAlterIdent0010
 */
public abstract class MESGeneratedRtPhaseDataMatAlterIdent0010
 extends MESRtPhaseData {

    /** Generated attribute definition */
    protected static final String ATDEFINITION_NAME = "LC_PhDatMatAlterIdent0010";


    @Override
    public String getATDefinitionName() {
        return ATDEFINITION_NAME;
    }

    /**
     * Generated constructor
     *
     * @param key The key of the ATRow to load.
     */
    public MESGeneratedRtPhaseDataMatAlterIdent0010(long key) {
        super(key);
    }

    /**
     * Generated copy constructor
     *
     * @param source the source to copy.
     */
    public MESGeneratedRtPhaseDataMatAlterIdent0010(MESGeneratedRtPhaseDataMatAlterIdent0010 source) {
        super(source);
    }

    /**
     * Generated constructor
     *
     * @param baseATRow The ATRow to wrap.
     */
    public MESGeneratedRtPhaseDataMatAlterIdent0010(ATRow baseATRow) {
        super(baseATRow);
    }

    /**
     * Generated constructor
     */
    public MESGeneratedRtPhaseDataMatAlterIdent0010() {
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
    public String getMaterialID() {
        return StringUtilsEx.decodeStringForUI((String) this.dgtATRow.getValue("LC_materialID"));
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setMaterialID(String value) {
        String oldValue = this.getMaterialID();
        this.dgtATRow.setValue("LC_materialID", StringUtilsEx.encodeStringForDB(value));
        pcs.firePropertyChange("materialID", oldValue, value);
    }
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public String getMfcPos() {
        return StringUtilsEx.decodeStringForUI((String) this.dgtATRow.getValue("LC_mfcPos"));
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setMfcPos(String value) {
        String oldValue = this.getMfcPos();
        this.dgtATRow.setValue("LC_mfcPos", StringUtilsEx.encodeStringForDB(value));
        pcs.firePropertyChange("mfcPos", oldValue, value);
    }
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public Boolean getIsHeader() {
        return (Boolean) this.dgtATRow.getValue("LC_isHeader");
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setIsHeader(Boolean value) {
        Boolean oldValue = this.getIsHeader();
        this.dgtATRow.setValue("LC_isHeader", value);
        pcs.firePropertyChange("isHeader", oldValue, value);
    }

    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public String getMaterialDescription() {
        return StringUtilsEx.decodeStringForUI((String) this.dgtATRow.getValue("LC_materialDescription"));
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setMaterialDescription(String value) {
        String oldValue = this.getMaterialDescription();
        this.dgtATRow.setValue("LC_materialDescription", StringUtilsEx.encodeStringForDB(value));
        pcs.firePropertyChange("materialDescription", oldValue, value);
    }
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public byte[] getCommentToExecution() {
        return (byte[]) this.dgtATRow.getValue("LC_commentToExecution");
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setCommentToExecution(byte[] value) {
        byte[] oldValue = this.getCommentToExecution();
        this.dgtATRow.setValue("LC_commentToExecution", value);
        pcs.firePropertyChange("commentToExecution", oldValue, value);
    }

    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public byte[] getBatchID() {
        return (byte[]) this.dgtATRow.getValue("LC_batchID");
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setBatchID(byte[] value) {
        byte[] oldValue = this.getBatchID();
        this.dgtATRow.setValue("LC_batchID", value);
        pcs.firePropertyChange("batchID", oldValue, value);
    }

    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public byte[] getSublotID() {
        return (byte[]) this.dgtATRow.getValue("LC_sublotID");
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setSublotID(byte[] value) {
        byte[] oldValue = this.getSublotID();
        this.dgtATRow.setValue("LC_sublotID", value);
        pcs.firePropertyChange("sublotID", oldValue, value);
    }

    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public String getLogisticUnitID() {
        return StringUtilsEx.decodeStringForUI((String) this.dgtATRow.getValue("LC_logisticUnitID"));
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setLogisticUnitID(String value) {
        String oldValue = this.getLogisticUnitID();
        this.dgtATRow.setValue("LC_logisticUnitID", StringUtilsEx.encodeStringForDB(value));
        pcs.firePropertyChange("logisticUnitID", oldValue, value);
    }
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public byte[] getBatchSublotDisplStr() {
        return (byte[]) this.dgtATRow.getValue("LC_batchSublotDisplStr");
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setBatchSublotDisplStr(byte[] value) {
        byte[] oldValue = this.getBatchSublotDisplStr();
        this.dgtATRow.setValue("LC_batchSublotDisplStr", value);
        pcs.firePropertyChange("batchSublotDisplStr", oldValue, value);
    }

    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public MeasuredValue getPlannedQty() {
        return (MeasuredValue) this.dgtATRow.getValue("LC_plannedQty");
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setPlannedQty(MeasuredValue value) {
        MeasuredValue oldValue = this.getPlannedQty();
        this.dgtATRow.setValue("LC_plannedQty", value);
        pcs.firePropertyChange("plannedQty", oldValue, value);
    }

    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public MeasuredValue getIdentifiedQty() {
        return (MeasuredValue) this.dgtATRow.getValue("LC_identifiedQty");
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setIdentifiedQty(MeasuredValue value) {
        MeasuredValue oldValue = this.getIdentifiedQty();
        this.dgtATRow.setValue("LC_identifiedQty", value);
        pcs.firePropertyChange("identifiedQty", oldValue, value);
    }

    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public Boolean getIsUnidentified() {
        return (Boolean) this.dgtATRow.getValue("LC_isUnidentified");
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setIsUnidentified(Boolean value) {
        Boolean oldValue = this.getIsUnidentified();
        this.dgtATRow.setValue("LC_isUnidentified", value);
        pcs.firePropertyChange("isUnidentified", oldValue, value);
    }

    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public MeasuredValue getAccountedQty() {
        return (MeasuredValue) this.dgtATRow.getValue("LC_accountedQty");
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setAccountedQty(MeasuredValue value) {
        MeasuredValue oldValue = this.getAccountedQty();
        this.dgtATRow.setValue("LC_accountedQty", value);
        pcs.firePropertyChange("accountedQty", oldValue, value);
    }

    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public String getPlannedQtyWithLimits() {
        return StringUtilsEx.decodeStringForUI((String) this.dgtATRow.getValue("LC_plannedQtyWithLimits"));
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setPlannedQtyWithLimits(String value) {
        String oldValue = this.getPlannedQtyWithLimits();
        this.dgtATRow.setValue("LC_plannedQtyWithLimits", StringUtilsEx.encodeStringForDB(value));
        pcs.firePropertyChange("plannedQtyWithLimits", oldValue, value);
    }
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public String getPlannedQtyDisplString() {
        return StringUtilsEx.decodeStringForUI((String) this.dgtATRow.getValue("LC_plannedQtyDisplString"));
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setPlannedQtyDisplString(String value) {
        String oldValue = this.getPlannedQtyDisplString();
        this.dgtATRow.setValue("LC_plannedQtyDisplString", StringUtilsEx.encodeStringForDB(value));
        pcs.firePropertyChange("plannedQtyDisplString", oldValue, value);
    }
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public String getAccountingStatus() {
        return StringUtilsEx.decodeStringForUI((String) this.dgtATRow.getValue("LC_accountingStatus"));
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setAccountingStatus(String value) {
        String oldValue = this.getAccountingStatus();
        this.dgtATRow.setValue("LC_accountingStatus", StringUtilsEx.encodeStringForDB(value));
        pcs.firePropertyChange("accountingStatus", oldValue, value);
    }
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public Boolean getLocalIdentified() {
        return (Boolean) this.dgtATRow.getValue("LC_localIdentified");
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setLocalIdentified(Boolean value) {
        Boolean oldValue = this.getLocalIdentified();
        this.dgtATRow.setValue("LC_localIdentified", value);
        pcs.firePropertyChange("localIdentified", oldValue, value);
    }

    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public String getResult() {
        return StringUtilsEx.decodeStringForUI((String) this.dgtATRow.getValue("LC_Result"));
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setResult(String value) {
        String oldValue = this.getResult();
        this.dgtATRow.setValue("LC_Result", StringUtilsEx.encodeStringForDB(value));
        pcs.firePropertyChange("result", oldValue, value);
    }
    
    @Override
    protected Response prepareATRowForSave() {
        // Check if transient references are valid and store the corresponding keys in the ATRow:
        Response res = super.prepareATRowForSave();

        return res;
    }

}
