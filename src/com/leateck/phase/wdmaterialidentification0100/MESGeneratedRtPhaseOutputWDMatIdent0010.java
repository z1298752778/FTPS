package com.leateck.phase.wdmaterialidentification0100;

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
 * <br/>Application table: LC_PhOutWDMatIdent0010
 */
public abstract class MESGeneratedRtPhaseOutputWDMatIdent0010
 extends MESRtPhaseOutput {

    /** Generated attribute definition */
    protected static final String ATDEFINITION_NAME = "LC_PhOutWDMatIdent0010";

    /** Generated reference field handler */
    private final MESATObjectReferenceFieldHandler<IMESS88Equipment> refContObject = 
            MESATObjectReferenceFieldHandler.createReferenceFieldHandler(this, IMESS88Equipment.class, "LC_contObject");

    @Override
    public String getATDefinitionName() {
        return ATDEFINITION_NAME;
    }

    /**
     * Generated constructor
     *
     * @param key The key of the ATRow to load.
     */
    public MESGeneratedRtPhaseOutputWDMatIdent0010(long key) {
        super(key);
    }

    /**
     * Generated copy constructor
     *
     * @param source the source to copy.
     */
    public MESGeneratedRtPhaseOutputWDMatIdent0010(MESGeneratedRtPhaseOutputWDMatIdent0010 source) {
        super(source);

        this.refContObject.initFromCopyConstructor(source.refContObject);
    }

    /**
     * Generated constructor
     *
     * @param baseATRow The ATRow to wrap.
     */
    public MESGeneratedRtPhaseOutputWDMatIdent0010(ATRow baseATRow) {
        super(baseATRow);
    }

    /**
     * Generated constructor
     */
    public MESGeneratedRtPhaseOutputWDMatIdent0010() {
        super();
    }

    @Override
    protected void synchronizeAfterATRowRefresh() {
        super.synchronizeAfterATRowRefresh();

        this.refContObject.synchronizeAfterATRowRefresh();
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
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public Boolean getTargetClosed() {
        return (Boolean) this.dgtATRow.getValue("LC_TargetClosed");
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setTargetClosed(Boolean value) {
        Boolean oldValue = this.getTargetClosed();
        this.dgtATRow.setValue("LC_TargetClosed", value);
        pcs.firePropertyChange("targetClosed", oldValue, value);
    }

    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public String getMFCPosition() {
        return StringUtilsEx.decodeStringForUI((String) this.dgtATRow.getValue("LC_MFCPosition"));
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setMFCPosition(String value) {
        String oldValue = this.getMFCPosition();
        this.dgtATRow.setValue("LC_MFCPosition", StringUtilsEx.encodeStringForDB(value));
        pcs.firePropertyChange("mFCPosition", oldValue, value);
    }
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public String getSublotIdentifier() {
        return StringUtilsEx.decodeStringForUI((String) this.dgtATRow.getValue("LC_sublotIdentifier"));
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setSublotIdentifier(String value) {
        String oldValue = this.getSublotIdentifier();
        this.dgtATRow.setValue("LC_sublotIdentifier", StringUtilsEx.encodeStringForDB(value));
        pcs.firePropertyChange("sublotIdentifier", oldValue, value);
    }
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public String getContId() {
        return StringUtilsEx.decodeStringForUI((String) this.dgtATRow.getValue("LC_contId"));
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setContId(String value) {
        String oldValue = this.getContId();
        this.dgtATRow.setValue("LC_contId", StringUtilsEx.encodeStringForDB(value));
        pcs.firePropertyChange("contId", oldValue, value);
    }
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public IMESS88Equipment getContObject() {
        return this.refContObject.getReference();
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setContObject(IMESS88Equipment value) {
        this.refContObject.setReference(value, pcs, "contObject");
    }

    @Override
    protected Response prepareATRowForSave() {
        // Check if transient references are valid and store the corresponding keys in the ATRow:
        Response res = super.prepareATRowForSave();

        this.refContObject.prepareATRowForSave(res);

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
                           new Object[] { "Result", "Identification result", String.class }
                          );
        OUTPUT_DESCRIPTORS.add(descriptor);

        descriptor = ObjectFactory.getInstance().createObject(IBuildingBlockOutputDescriptor.class,
                           new Class[] { String.class, String.class, Class.class },
                           new Object[] { "TargetClosed", "Target closed", Boolean.class }
                          );
        OUTPUT_DESCRIPTORS.add(descriptor);

        descriptor = ObjectFactory.getInstance().createObject(IBuildingBlockOutputDescriptor.class,
                           new Class[] { String.class, String.class, Class.class },
                           new Object[] { "MFCPosition", "MFC Position", String.class }
                          );
        OUTPUT_DESCRIPTORS.add(descriptor);

        descriptor = ObjectFactory.getInstance().createObject(IBuildingBlockOutputDescriptor.class,
                           new Class[] { String.class, String.class, Class.class },
                           new Object[] { "sublotIdentifier", "Sublot identifier", String.class }
                          );
        OUTPUT_DESCRIPTORS.add(descriptor);

        descriptor = ObjectFactory.getInstance().createObject(IBuildingBlockOutputDescriptor.class,
                           new Class[] { String.class, String.class, Class.class },
                           new Object[] { "contId", "Container ID", String.class }
                          );
        OUTPUT_DESCRIPTORS.add(descriptor);

        descriptor = ObjectFactory.getInstance().createObject(IBuildingBlockOutputDescriptor.class,
                           new Class[] { String.class, String.class, Class.class, Class.class },
                           new Object[] { "contObject", "Container object", IMESS88Equipment.class, PersistentMESATObject.class }
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
