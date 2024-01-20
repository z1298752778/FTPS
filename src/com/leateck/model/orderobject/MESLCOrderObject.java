package com.leateck.model.orderobject;

/**
 * This file was generated by ATDefAccessClassGenerator and FMPP 2.3.15
 */
import com.datasweep.compatibility.client.ATRow;

import com.leateck.model.orderobject.IMESLCOrderObject;
import com.leateck.model.orderobject.MESGeneratedLCOrderObject;

/**
 * Generated class definition for application table LC_OrderObject.
 * Application table description: 
 */
public class MESLCOrderObject extends MESGeneratedLCOrderObject //nl
                                        implements IMESLCOrderObject {

    /**
     * Generated constructor
     *
     * @param key The key of the ATRow to load.
     */
    public MESLCOrderObject(long key) {
        super(key);
    }

    /**
     * Generated constructor
     *
     * @param source the source to copy.
     */
    public MESLCOrderObject(MESLCOrderObject source) {
        super(source);
    }

    /**
     * Generated constructor
     *
     * @param baseATRow The ATRow to wrap.
     */
    public MESLCOrderObject(ATRow baseATRow) {
        super(baseATRow);
    }

    /**
     * Generated default constructor
     */
    public MESLCOrderObject() {
        super();
    }

}
