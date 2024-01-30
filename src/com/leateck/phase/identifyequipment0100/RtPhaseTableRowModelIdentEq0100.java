package com.leateck.phase.identifyequipment0100;

import com.rockwell.mes.commons.base.ifc.objects.IMESBasePropertyChangeSupport;
import com.rockwell.mes.commons.shared.phase.mvc.PhaseViewHelper0200;
import com.rockwell.mes.phase.eqidentification.RtPhaseViewEqIdent0210;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88Equipment;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88EquipmentClass;
import com.rockwell.mes.shared.childgrid.EqChildGridHelper0100;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
public class RtPhaseTableRowModelIdentEq0100 implements IMESBasePropertyChangeSupport {
    private IMESS88EquipmentClass eqReqClass;
    private List<String[]> eqReqProps;
    private IMESS88Equipment eq;
    private List<String[]> eqProps;
    public static final String MSGPACK = "DataDictionary_Default_RtPhaseTableRowModelEqIdent0210";
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public RtPhaseTableRowModelIdentEq0100(IMESS88EquipmentClass equipmentRequirementClass, List<String[]> requiredProps) {
        this.eqReqClass = equipmentRequirementClass;
        this.eqReqProps = requiredProps;
        this.pcs.firePropertyChange("eq", (Object)null, equipmentRequirementClass);
    }

    public RtPhaseTableRowModelIdentEq0100(IMESS88Equipment identifiedEquipment, List<String[]> identifiedEquipmentProps) {
        this.eq = identifiedEquipment;
        this.eqProps = identifiedEquipmentProps;
        this.pcs.firePropertyChange("eq", (Object)null, identifiedEquipment);
    }

    public String getEquipmentClass() {
        //RtPhaseViewEqIdent0210
        String var1 = "";
        if (this.eqReqClass != null) {
            var1 = PhaseViewHelper0200.formatIdentifierAndShortDescription("DataDictionary_Default_RtPhaseTableRowModelEqIdent0210", "equipmentClassNoShortDescription_Content", "equipmentClassWithShortDescription_Content", this.eqReqClass.getIdentifier(), this.eqReqClass.getShortDescription());
        }

        return var1;
    }

    public String getEquipmentIdentifier() {
        String var1 = "";
        if (this.eq != null) {
            var1 = PhaseViewHelper0200.formatIdentifierAndShortDescription("DataDictionary_Default_RtPhaseTableRowModelEqIdent0210", "equipmentIdentifierNoShortDescription_Content", "equipmentIdentifierWithShortDescription_Content", this.eq.getIdentifier(), this.eq.getShortDescription());
        }

        return var1;
    }

    public String getRequiredPropertyValues() {
        return EqChildGridHelper0100.getPropertyValuesString(this.eqReqProps, true);
    }

    public String getIdentifiedPropertyValues() {
        return EqChildGridHelper0100.getPropertyValuesString(this.eqProps, false);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }
}