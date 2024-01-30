package com.leateck.phase.identifyequipment0100.propertylist;

import com.leateck.phase.identifyequipment0100.RtPhaseExecutorIdentEq0100;
import com.leateck.phase.identifyequipment0100.RtPhaseViewIdentEq0100;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.eqpropertylist.EquipmentPropertyListModel;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.eqpropertylist.EquipmentPropertyListView;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.eqpropertylist.IExecutorPluginDelegate;
import com.rockwell.mes.services.s88.ifc.execution.IMESRtPhase;

public class EquipmentPropertyListAdapter0100 implements IExecutorPluginDelegate<EquipmentPropertyListModel, EquipmentPropertyListView> {
    private final RtPhaseExecutorIdentEq0100 delegate;

    public EquipmentPropertyListAdapter0100(final RtPhaseExecutorIdentEq0100 executor) {
        this.delegate = executor;
    }

    public IMESRtPhase getRtPhase() {
        return this.delegate.getRtPhase();
    }

    public EquipmentPropertyListView getView() {
        RtPhaseViewIdentEq0100 var1 = (RtPhaseViewIdentEq0100)this.delegate.getView();
        return var1 != null ? var1.getEquipmentPropertyListView() : null;
    }

    public EquipmentPropertyListModel getModel() {
        EquipmentPropertyListView var1 = this.getView();
        return var1 != null ? var1.getModel() : null;
    }

    public String getMessagePack() {
        return "PhaseEqmEqIdentification0210";
    }
}
