package com.leateck.phase.identifyequipment0100;

/**
 * This file is generated by the PhaseLibManager
 */
import com.datasweep.compatibility.client.ATRow;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.eqpropertylist.IEquipmentPropertyListRtPhaseData;
import com.rockwell.mes.commons.base.ifc.utility.MesXMLConstants;
import com.rockwell.mes.services.s88.ifc.processdata.IMESRtPhaseData;

/**
 * Generated class definition
 */
public class MESRtPhaseDataIdentEq0100 extends MESGeneratedRtPhaseDataIdentEq0100 implements IEquipmentPropertyListRtPhaseData, IMESRtPhaseData {
    public MESRtPhaseDataIdentEq0100(long key) {
        super(key);
    }

    public MESRtPhaseDataIdentEq0100(MESRtPhaseDataIdentEq0100 source) {
        super(source);
    }

    public MESRtPhaseDataIdentEq0100(ATRow baseATRow) {
        super(baseATRow);
    }

    public MESRtPhaseDataIdentEq0100() {
    }

    public String getEqIdentPropValsBase64() {
        return this.byteArrayToBase64EncodedString(this.getEqIdentPropVals());
    }

    public String getEqIdentPropValsStatesBase64() {
        return this.byteArrayToBase64EncodedString(this.getEqIdentPropValsStates());
    }

    public String getEqReqPropValsBase64() {
        return this.byteArrayToBase64EncodedString(this.getEqReqPropVals());
    }

    public String getEqReqPropValsStatesBase64() {
        return this.byteArrayToBase64EncodedString(this.getEqReqPropValsStates());
    }

    public String getChildGridDataBase64() {
        return this.byteArrayToBase64EncodedString(this.getChildGridData());
    }

    public String getEqPropertyListBase64() {
        return this.byteArrayToBase64EncodedString(this.getEqPropertyList());
    }

    private String byteArrayToBase64EncodedString(byte[] byteArray) {
        return MesXMLConstants.byteArrayToBase64EncodedString(byteArray);
    }

    public byte[] getEquipmentPropertyListBlob() {
        return this.getEqPropertyList();
    }

    public void setEquipmentPropertyListBlob(byte[] propertyListSerialized) {
        this.setEqPropertyList(propertyListSerialized);
    }
}
