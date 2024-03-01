package com.rockwell.mes.services.s88equipment.impl.statusgraph.generated;

import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.Response;
import com.datasweep.compatibility.client.StateProxy;
import com.rockwell.mes.commons.base.ifc.choicelist.IMESChoiceElement;
import com.rockwell.mes.commons.base.ifc.choicelist.MESChoiceListHelper;
import com.rockwell.mes.commons.base.ifc.objects.IMESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectReferenceFieldHandler;
import com.rockwell.mes.services.s88equipment.ifc.AbstractStatusControlledS88EquipmentWithChangeHistory;
import com.rockwell.mes.services.s88equipment.ifc.statusgraph.generated.IMESGeneratedS88StatusGraph;
import java.beans.PropertyChangeSupport;

public abstract class MESGeneratedS88StatusGraph extends AbstractStatusControlledS88EquipmentWithChangeHistory
        implements IMESGeneratedS88StatusGraph {
    protected static final String PROP_NAME_NEXTTRANSITIONID = "nextTransitionId";

    protected static final String PROP_NAME_STATEPROXY = "stateProxy";

    private final MESATObjectReferenceFieldHandler<StateProxy> refStateProxy =
            MESATObjectReferenceFieldHandler.createReferenceFieldHandler((IMESATObject) this, StateProxy.class, (String) "X_stateProxy");

    protected static final String PROP_NAME_TILECOLOR = "tileColor";

    protected static final String PROP_NAME_TILETEXTCOLOR = "tileTextColor";

    public String getATDefinitionName() {
        return "X_S88StatusGraph";
    }

    public MESGeneratedS88StatusGraph(long l) {
        super(l);
    }

    public MESGeneratedS88StatusGraph(MESGeneratedS88StatusGraph mESGeneratedS88StatusGraph) {
        super((AbstractStatusControlledS88EquipmentWithChangeHistory) mESGeneratedS88StatusGraph);
        this.refStateProxy.initFromCopyConstructor(mESGeneratedS88StatusGraph.refStateProxy);
    }

    public MESGeneratedS88StatusGraph(ATRow aTRow) {
        super(aTRow);
    }

    public MESGeneratedS88StatusGraph() {
    }

    protected void synchronizeAfterATRowRefresh() {
        this.refStateProxy.synchronizeAfterATRowRefresh();
        super.synchronizeAfterATRowRefresh();
    }

    //
    public String getProcessPackName() {
        return (String) this.dgtATRow.getValue("processPackName");
    }

    public void setProcessPackName(String string) {
        String string2 = this.getProcessPackName();
        this.dgtATRow.setValue("processPackName", (Object) string);
        this.firePropertyChange("processPackName", (Object) string2, (Object) string);
    }

    public String getDescription() {
        return (String) this.dgtATRow.getValue("X_description");
    }

    public void setDescription(String string) {
        String string2 = this.getDescription();
        this.dgtATRow.setValue("X_description", (Object) string);
        this.firePropertyChange("description", (Object) string2, (Object) string);
    }

    public String getDisplayName() {
        return (String) this.dgtATRow.getValue("X_displayName");
    }

    public void setDisplayName(String string) {
        String string2 = this.getDisplayName();
        this.dgtATRow.setValue("X_displayName", (Object) string);
        this.firePropertyChange("displayName", (Object) string2, (Object) string);
    }

    public String getIdentifier() {
        return (String) this.dgtATRow.getValue("X_identifier");
    }

    public void setIdentifier(String string) {
        String string2 = this.getIdentifier();
        this.dgtATRow.setValue("X_identifier", (Object) string);
        this.firePropertyChange("identifier", (Object) string2, (Object) string);
    }

    protected Long getNextTransitionId() {
        return (Long) this.dgtATRow.getValue("X_nextTransitionId");
    }

    protected void setNextTransitionId(Long l) {
        Long l2 = this.getNextTransitionId();
        this.dgtATRow.setValue("X_nextTransitionId", (Object) l);
        this.firePropertyChange(PROP_NAME_NEXTTRANSITIONID, (Object) l2, (Object) l);
    }

    public IMESChoiceElement getPurpose() {
        Long l = (Long) this.dgtATRow.getValue("X_purpose");
        return MESChoiceListHelper.getChoiceElement((String) "S88StatusGraphPurpose", (Long) l);
    }

    public final String getPurposeAsMeaning() {
        return null == this.getPurpose() ? null : this.getPurpose().getMeaning();
    }

    public final Long getPurposeAsValue() {
        return null == this.getPurpose() ? null : this.getPurpose().getValue();
    }

    public void setPurpose(IMESChoiceElement iMESChoiceElement) {
        IMESChoiceElement iMESChoiceElement2 = this.getPurpose();
        String string = iMESChoiceElement2 != null ? iMESChoiceElement2.getMeaning() : null;
        String string2 = iMESChoiceElement != null ? iMESChoiceElement.getMeaning() : null;
        Long l = iMESChoiceElement2 != null ? iMESChoiceElement2.getValue() : null;
        Long l2 = iMESChoiceElement != null ? iMESChoiceElement.getValue() : null;
        this.dgtATRow.setValue("X_purpose", (Object) l2);
        this.firePropertyChange("purpose", (Object) iMESChoiceElement2, (Object) iMESChoiceElement);
        this.firePropertyChange("purposeAsMeaning", (Object) string, (Object) string2);
        this.firePropertyChange("purposeAsValue", (Object) l, (Object) l2);
    }

    public final void setPurposeAsMeaning(String string) {
        this.setPurpose(null == string ? null : MESChoiceListHelper.getChoiceElement((String) "S88StatusGraphPurpose", (String) string));
    }

    public final void setPurposeAsValue(Long l) {
        this.setPurpose(null == l ? null : MESChoiceListHelper.getChoiceElement((String) "S88StatusGraphPurpose", (Long) l));
    }

    public String getShortDescription() {
        return (String) this.dgtATRow.getValue("X_shortDescription");
    }

    public void setShortDescription(String string) {
        String string2 = this.getShortDescription();
        this.dgtATRow.setValue("X_shortDescription", (Object) string);
        this.firePropertyChange("shortDescription", (Object) string2, (Object) string);
    }

    protected StateProxy getStateProxy() {
        return (StateProxy) this.refStateProxy.getReference();
    }

    protected void setStateProxy(StateProxy stateProxy) {
        this.refStateProxy.setReference((StateProxy) stateProxy, this.pcs, PROP_NAME_STATEPROXY);
    }

    protected Long getTileColor() {
        return (Long) this.dgtATRow.getValue("X_tileColor");
    }

    protected void setTileColor(Long l) {
        Long l2 = this.getTileColor();
        this.dgtATRow.setValue("X_tileColor", (Object) l);
        this.firePropertyChange(PROP_NAME_TILECOLOR, (Object) l2, (Object) l);
    }

    protected Long getTileTextColor() {
        return (Long) this.dgtATRow.getValue("X_tileTextColor");
    }

    protected void setTileTextColor(Long l) {
        Long l2 = this.getTileTextColor();
        this.dgtATRow.setValue("X_tileTextColor", (Object) l);
        this.firePropertyChange(PROP_NAME_TILETEXTCOLOR, (Object) l2, (Object) l);
    }

    protected Response prepareATRowForSave() {
        Response response = super.prepareATRowForSave();
        this.refStateProxy.prepareATRowForSave(response);
        return response;
    }
}