package com.leateck.phase.materialalternativeidentification0010;

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

 /**
 * Generated class definition
 * <br/>Application table: SC_PhOutMatAlterIdent0010
 */
public abstract class MESGeneratedRtPhaseOutputMatAlterIdent0010
 extends MESRtPhaseOutput {

    /** Generated attribute definition */
    protected static final String ATDEFINITION_NAME = "SC_PhOutMatAlterIdent0010";


    @Override
    public String getATDefinitionName() {
        return ATDEFINITION_NAME;
    }

    /**
     * Generated constructor
     *
     * @param key The key of the ATRow to load.
     */
    public MESGeneratedRtPhaseOutputMatAlterIdent0010(long key) {
        super(key);
    }

    /**
     * Generated copy constructor
     *
     * @param source the source to copy.
     */
    public MESGeneratedRtPhaseOutputMatAlterIdent0010(MESGeneratedRtPhaseOutputMatAlterIdent0010 source) {
        super(source);
    }

    /**
     * Generated constructor
     *
     * @param baseATRow The ATRow to wrap.
     */
    public MESGeneratedRtPhaseOutputMatAlterIdent0010(ATRow baseATRow) {
        super(baseATRow);
    }

    /**
     * Generated constructor
     */
    public MESGeneratedRtPhaseOutputMatAlterIdent0010() {
        super();
    }

    @Override
    protected void synchronizeAfterATRowRefresh() {
        super.synchronizeAfterATRowRefresh();
    }    
    
    /**
     * Generated method definition
     *
     * @return the requested value
     */
    public String getResult() {
        return StringUtilsEx.decodeStringForUI((String) this.dgtATRow.getValue("SC_Result"));
    }

    /**
     * Generated method definition
     *
     * @param value The new value to be assigned
     */
    public void setResult(String value) {
        String oldValue = this.getResult();
        this.dgtATRow.setValue("SC_Result", StringUtilsEx.encodeStringForDB(value));
        pcs.firePropertyChange("result", oldValue, value);
    }
    
    @Override
    protected Response prepareATRowForSave() {
        // Check if transient references are valid and store the corresponding keys in the ATRow:
        Response res = super.prepareATRowForSave();

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
                           new Object[] { "Result", "Result", String.class }
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
