package com.leateck.model.processpackitem;

/**
 * This file is generated by ATDefAccessClassGenerator and FMPP 2.3.15
 *
 * Please do not modify this file manually !!
 */
import com.leateck.model.processpackitem.IMESGeneratedLCProcessPackItem;
import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.Response;
import com.rockwell.mes.commons.base.ifc.objects.MESATObject;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectReferenceFieldHandler;
import com.leateck.model.processpack.IMESLCProcessPack;

/**
 * Generated class definition for application table LC_ProcessPackItem.<BR>
 * Application table description: 工艺包项表
 */
public abstract class MESGeneratedLCProcessPackItem extends MESATObject //
        implements IMESGeneratedLCProcessPackItem {

    /** Generated reference field handler */
    private final MESATObjectReferenceFieldHandler<IMESLCProcessPack> refProcessPackKey = // 
            MESATObjectReferenceFieldHandler.createReferenceFieldHandler(this, IMESLCProcessPack.class, COL_NAME_PROCESSPACKKEY);

    @Override
    public String getATDefinitionName() {
        return ATDEFINITION_NAME;
    }

    /**
     * Generated constructor
     * 
     * @param key The key of the ATRow to load.
     */
    public MESGeneratedLCProcessPackItem(long key) {
        super(key);
    }

    /**
     * Generated copy constructor
     * 
     * @param source The source to copy.
     */
    public MESGeneratedLCProcessPackItem(MESGeneratedLCProcessPackItem source) {
        super(source);

        this.refProcessPackKey.initFromCopyConstructor(source.refProcessPackKey);
    }

    /**
     * Generated constructor
     * 
     * @param baseATRow The ATRow to wrap.
     */
    public MESGeneratedLCProcessPackItem(ATRow baseATRow) {
        super(baseATRow);
    }

    /**
     * Generated default constructor
     */
    public MESGeneratedLCProcessPackItem() {
        super();
    }

    @Override
    protected void synchronizeAfterATRowRefresh() {
        this.refProcessPackKey.synchronizeAfterATRowRefresh();

        super.synchronizeAfterATRowRefresh();
    }

    @Override
    public Long getObjectKey() {
        return (Long) this.dgtATRow.getValue(COL_NAME_OBJECTKEY);
    }

    @Override
    public void setObjectKey(Long value) {
        Long oldValue = this.getObjectKey();
        this.dgtATRow.setValue(COL_NAME_OBJECTKEY, value);
        firePropertyChange(PROP_NAME_OBJECTKEY, oldValue, value);
    }

    @Override
    public String getProcessItemDes() {
        return (String) this.dgtATRow.getValue(COL_NAME_PROCESSITEMDES);
    }

    @Override
    public void setProcessItemDes(String value) {
        String oldValue = this.getProcessItemDes();
        this.dgtATRow.setValue(COL_NAME_PROCESSITEMDES, value);
        firePropertyChange(PROP_NAME_PROCESSITEMDES, oldValue, value);
    }

    @Override
    public String getProcessItemId() {
        return (String) this.dgtATRow.getValue(COL_NAME_PROCESSITEMID);
    }

    @Override
    public void setProcessItemId(String value) {
        String oldValue = this.getProcessItemId();
        this.dgtATRow.setValue(COL_NAME_PROCESSITEMID, value);
        firePropertyChange(PROP_NAME_PROCESSITEMID, oldValue, value);
    }

    @Override
    public Long getProcessItemType() {
        return (Long) this.dgtATRow.getValue(COL_NAME_PROCESSITEMTYPE);
    }

    @Override
    public void setProcessItemType(Long value) {
        Long oldValue = this.getProcessItemType();
        this.dgtATRow.setValue(COL_NAME_PROCESSITEMTYPE, value);
        firePropertyChange(PROP_NAME_PROCESSITEMTYPE, oldValue, value);
    }

    @Override
    public String getProcessItemeVersion() {
        return (String) this.dgtATRow.getValue(COL_NAME_PROCESSITEMEVERSION);
    }

    @Override
    public void setProcessItemeVersion(String value) {
        String oldValue = this.getProcessItemeVersion();
        this.dgtATRow.setValue(COL_NAME_PROCESSITEMEVERSION, value);
        firePropertyChange(PROP_NAME_PROCESSITEMEVERSION, oldValue, value);
    }

    @Override
    public IMESLCProcessPack getProcessPackKey() {
        return this.refProcessPackKey.getReference();
    }

    @Override
    public void setProcessPackKey(IMESLCProcessPack value) {
        this.refProcessPackKey.setReference(value, pcs, PROP_NAME_PROCESSPACKKEY);
    }

    @Override
    protected Response prepareATRowForSave() {
        // Check if transient references are valid and store the corresponding keys in the ATRow:
        Response res = super.prepareATRowForSave();


        this.refProcessPackKey.prepareATRowForSave(res);

        return res;
    }

}