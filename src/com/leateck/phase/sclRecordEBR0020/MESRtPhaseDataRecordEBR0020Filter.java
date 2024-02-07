package com.leateck.phase.sclRecordEBR0020;

import com.datasweep.compatibility.client.DatasweepException;
import com.datasweep.compatibility.client.Server;
import com.rockwell.mes.services.s88.ifc.execution.IMESRtPhase;
import com.rockwell.mes.services.s88.ifc.processdata.IMESRtPhaseDataFilter;


public class MESRtPhaseDataRecordEBR0020Filter extends MESGeneratedRtPhaseDataRecordEBR0020Filter implements IMESRtPhaseDataFilter {
    private static final long serialVersionUID = 1L;

    public MESRtPhaseDataRecordEBR0020Filter(Server server) {
        super(server);
    }

    public MESRtPhaseDataRecordEBR0020Filter() {}
}
