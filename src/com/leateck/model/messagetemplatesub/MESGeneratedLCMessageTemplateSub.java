package com.leateck.model.messagetemplatesub;


/**
 * This file is generated by ATDefAccessClassGenerator and FMPP 2.3.15
 *
 * Please do not modify this file manually !!
 */
import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.MeasuredValue;
import com.datasweep.compatibility.client.Response;
import com.leateck.model.messagetemplate.IMESLCMessageTemplate;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectReferenceFieldHandler;

/**
 * Generated class definition for application table LC_MessageTemplateSub.<BR>
 * Application table description: 
 */
public abstract class MESGeneratedLCMessageTemplateSub extends MESATObject //
        implements IMESGeneratedLCMessageTemplateSub {

    /** Generated reference field handler */
    private final MESATObjectReferenceFieldHandler<IMESLCMessageTemplate> refMessageTemplate = //
            MESATObjectReferenceFieldHandler.createReferenceFieldHandler(this, IMESLCMessageTemplate.class, COL_NAME_MESSAGETEMPLATE);

    @Override
    public String getATDefinitionName() {
        return ATDEFINITION_NAME;
    }

    /**
     * Generated constructor
     * 
     * @param key The key of the ATRow to load.
     */
    public MESGeneratedLCMessageTemplateSub(long key) {
        super(key);
    }

    /**
     * Generated copy constructor
     * 
     * @param source The source to copy.
     */
    public MESGeneratedLCMessageTemplateSub(MESGeneratedLCMessageTemplateSub source) {
        super(source);

        this.refMessageTemplate.initFromCopyConstructor(source.refMessageTemplate);
    }

    /**
     * Generated constructor
     * 
     * @param baseATRow The ATRow to wrap.
     */
    public MESGeneratedLCMessageTemplateSub(ATRow baseATRow) {
        super(baseATRow);
    }

    /**
     * Generated default constructor
     */
    public MESGeneratedLCMessageTemplateSub() {
        super();
    }

    @Override
    protected void synchronizeAfterATRowRefresh() {
        this.refMessageTemplate.synchronizeAfterATRowRefresh();

        super.synchronizeAfterATRowRefresh();
    }

    @Override
    public MeasuredValue getCriticalTime() {
        return (MeasuredValue) this.dgtATRow.getValue(COL_NAME_CRITICALTIME);
    }

    @Override
    public void setCriticalTime(MeasuredValue value) {
        MeasuredValue oldValue = this.getCriticalTime();
        this.dgtATRow.setValue(COL_NAME_CRITICALTIME, value);
        firePropertyChange(PROP_NAME_CRITICALTIME, oldValue, value);
    }

    @Override
    public String getEquipStatusModel() {
        return (String) this.dgtATRow.getValue(COL_NAME_EQUIPSTATUSMODEL);
    }

    @Override
    public void setEquipStatusModel(String value) {
        String oldValue = this.getEquipStatusModel();
        this.dgtATRow.setValue(COL_NAME_EQUIPSTATUSMODEL, value);
        firePropertyChange(PROP_NAME_EQUIPSTATUSMODEL, oldValue, value);
    }

    @Override
    public IMESLCMessageTemplate getMessageTemplate() {
        return this.refMessageTemplate.getReference();
    }

    @Override
    public void setMessageTemplate(IMESLCMessageTemplate value) {
        this.refMessageTemplate.setReference(value, pcs, PROP_NAME_MESSAGETEMPLATE);
    }

    @Override
    protected Response prepareATRowForSave() {
        // Check if transient references are valid and store the corresponding keys in the ATRow:
        Response res = super.prepareATRowForSave();


        this.refMessageTemplate.prepareATRowForSave(res);

        return res;
    }

}