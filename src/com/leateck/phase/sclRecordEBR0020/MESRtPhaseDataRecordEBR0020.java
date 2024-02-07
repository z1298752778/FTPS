package com.leateck.phase.sclRecordEBR0020;

import com.datasweep.compatibility.client.ATRow;
import com.rockwell.mes.services.s88.ifc.processdata.IMESRtPhaseData;

public class MESRtPhaseDataRecordEBR0020 extends MESGeneratedRtPhaseDataRecordEBR0020 implements IMESRtPhaseData {
    public MESRtPhaseDataRecordEBR0020(long key) {
        super(key);
    }

    public MESRtPhaseDataRecordEBR0020(MESRtPhaseDataRecordEBR0020 source) {
        super(source);
    }

    public MESRtPhaseDataRecordEBR0020(ATRow baseATRow) {
        super(baseATRow);
    }

    public MESRtPhaseDataRecordEBR0020() {
        super();
    }
}
