package com.leateck.parameter.materialpositioncontrol0010;

/**
 * This file is generated by ParameterClassManager
 */
import com.datasweep.compatibility.client.ATRow;
import com.rockwell.mes.services.s88.ifc.recipe.IMESProcessParameterData;

/**
 * Generated class definition
 */
public class MESParamMatPositionCtr0100 extends MESGeneratedParamMatPositionCtr0100 //nl
                                                  implements IMESProcessParameterData {

    /**
     * Generated method definition
     *
     * @param key The key of the ATRow to load.
     */
    public MESParamMatPositionCtr0100(long key) {
        super(key);
    }

    /**
     * Generated method definition
     *
     * @param source the source to copy.
     */
    public MESParamMatPositionCtr0100(MESParamMatPositionCtr0100 source) {
        super(source);
    }

    /**
     * Generated method definition
     *
     * @param baseATRow The ATRow to wrap.
     */
    public MESParamMatPositionCtr0100(ATRow baseATRow) {
        super(baseATRow);
    }

    /**
     * Generated method definition
     *
     */
    public MESParamMatPositionCtr0100() {
        super();
    }

    /**
     * {@inheritDoc}
     * @see IMESProcessParameterData#getDataAsString()
     */
    public String getDataAsString() {
        // TASK: implement!
        return "";
    }
}
