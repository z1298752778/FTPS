package com.leateck.phase.identifyequipment0100;

/**
 * This file is generated by the PhaseGenerator
 *
 * Please do not modify this file manually !!
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.datasweep.compatibility.client.ATRow;
import com.datasweep.compatibility.client.Response;
import com.rockwell.mes.commons.base.ifc.utility.ObjectFactory;
import com.rockwell.mes.services.s88.ifc.library.IBuildingBlockOutputDescriptor;
import com.rockwell.mes.services.s88.ifc.processdata.MESRtPhaseOutput;

import com.rockwell.mes.commons.base.ifc.utility.StringUtilsEx;
import com.rockwell.mes.commons.base.ifc.objects.MESATObjectReferenceFieldHandler;
import com.rockwell.mes.services.s88equipment.ifc.IMESS88Equipment;
import com.rockwell.mes.commons.base.ifc.objects.PersistentMESATObject;

 /**
 * Generated class definition
 * <br/>Application table: RS_PhOutIdentEq0100
 */
public abstract class MESGeneratedRtPhaseOutputIdentEq0100
 extends MESRtPhaseOutput {

    /** Generated attribute definition */
    protected static final String ATDEFINITION_NAME = "RS_PhOutIdentEq0100";

    /** Generated reference field handler */
    private final MESATObjectReferenceFieldHandler<IMESS88Equipment> refEqObject = 
            MESATObjectReferenceFieldHandler.createReferenceFieldHandler(this, IMESS88Equipment.class, "RS_eqObject");

    @Override
    public String getATDefinitionName() {
        return ATDEFINITION_NAME;
    }

    /**
     * Generated constructor
     *
     * @param key The key of the ATRow to load.
     */
    public MESGeneratedRtPhaseOutputIdentEq0100(long key) {
        super(key);
    }

    /**
     * Generated copy constructor
     *
     * @param source the source to copy.
     */
    public MESGeneratedRtPhaseOutputIdentEq0100(MESGeneratedRtPhaseOutputIdentEq0100 source) {
        super(source);

        this.refEqObject.initFromCopyConstructor(source.refEqObject);
    }

    /**
     * Generated constructor
     *
     * @param baseATRow The ATRow to wrap.
     */
    public MESGeneratedRtPhaseOutputIdentEq0100(ATRow baseATRow) {
        super(baseATRow);
    }

    /**
     * Generated constructor
     */
    public MESGeneratedRtPhaseOutputIdentEq0100() {
        super();
    }

    @Override
    protected void synchronizeAfterATRowRefresh() {
        super.synchronizeAfterATRowRefresh();

        this.refEqObject.synchronizeAfterATRowRefresh();
    }    
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public String getEqId() {
        return StringUtilsEx.decodeStringForUI((String) this.dgtATRow.getValue("RS_eqId"));
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setEqId(String value) {
        String oldValue = this.getEqId();
        this.dgtATRow.setValue("RS_eqId", StringUtilsEx.encodeStringForDB(value));
        pcs.firePropertyChange("eqId", oldValue, value);
    }
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public IMESS88Equipment getEqObject() {
        return this.refEqObject.getReference();
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setEqObject(IMESS88Equipment value) {
        this.refEqObject.setReference(value, pcs, "eqObject");
    }

    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public String getEqShortDescription() {
        return StringUtilsEx.decodeStringForUI((String) this.dgtATRow.getValue("RS_eqShortDescription"));
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setEqShortDescription(String value) {
        String oldValue = this.getEqShortDescription();
        this.dgtATRow.setValue("RS_eqShortDescription", StringUtilsEx.encodeStringForDB(value));
        pcs.firePropertyChange("eqShortDescription", oldValue, value);
    }
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public String getResult() {
        return StringUtilsEx.decodeStringForUI((String) this.dgtATRow.getValue("RS_Result"));
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setResult(String value) {
        String oldValue = this.getResult();
        this.dgtATRow.setValue("RS_Result", StringUtilsEx.encodeStringForDB(value));
        pcs.firePropertyChange("result", oldValue, value);
    }
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public Boolean getIsClean() {
        return (Boolean) this.dgtATRow.getValue("RS_IsClean");
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setIsClean(Boolean value) {
        Boolean oldValue = this.getIsClean();
        this.dgtATRow.setValue("RS_IsClean", value);
        pcs.firePropertyChange("isClean", oldValue, value);
    }

    @Override
    protected Response prepareATRowForSave() {
        // Check if transient references are valid and store the corresponding keys in the ATRow:
        Response res = super.prepareATRowForSave();

        this.refEqObject.prepareATRowForSave(res);

        return res;
    }

    /** output descriptors */
    private static final List<IBuildingBlockOutputDescriptor> OUTPUT_DESCRIPTORS =
            new ArrayList<IBuildingBlockOutputDescriptor>();

    /**
     * Initializes the output descriptors.
     */
    static {
        IBuildingBlockOutputDescriptor descriptor;

        descriptor = ObjectFactory.getInstance().createObject(IBuildingBlockOutputDescriptor.class,
                           new Class[] { String.class, String.class, Class.class },
                           new Object[] { "eqId", "Equipment ID", String.class }
                          );
        OUTPUT_DESCRIPTORS.add(descriptor);

        descriptor = ObjectFactory.getInstance().createObject(IBuildingBlockOutputDescriptor.class,
                           new Class[] { String.class, String.class, Class.class, Class.class },
                           new Object[] { "eqObject", "Equipment object", IMESS88Equipment.class, PersistentMESATObject.class }
                          );
        OUTPUT_DESCRIPTORS.add(descriptor);

        descriptor = ObjectFactory.getInstance().createObject(IBuildingBlockOutputDescriptor.class,
                           new Class[] { String.class, String.class, Class.class },
                           new Object[] { "eqShortDescription", "Equipment short description", String.class }
                          );
        OUTPUT_DESCRIPTORS.add(descriptor);

        descriptor = ObjectFactory.getInstance().createObject(IBuildingBlockOutputDescriptor.class,
                           new Class[] { String.class, String.class, Class.class },
                           new Object[] { "Result", "Result", String.class }
                          );
        OUTPUT_DESCRIPTORS.add(descriptor);

        descriptor = ObjectFactory.getInstance().createObject(IBuildingBlockOutputDescriptor.class,
                           new Class[] { String.class, String.class, Class.class },
                           new Object[] { "IsClean", "IsClean", Boolean.class }
                          );
        OUTPUT_DESCRIPTORS.add(descriptor);
    }

    /**
     * Retrieves the output descriptors of this class.
     *  
     * @return unmodifiable list of output descriptors
     */
    public static List<IBuildingBlockOutputDescriptor> getOutputDescriptorsOfClass() {
        return Collections.unmodifiableList(OUTPUT_DESCRIPTORS);
    }
    
    @Override
    public List<IBuildingBlockOutputDescriptor> getOutputDescriptors() {
        return getOutputDescriptorsOfClass();
    }
    
}
