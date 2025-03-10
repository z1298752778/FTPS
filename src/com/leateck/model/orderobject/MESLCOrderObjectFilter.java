package com.leateck.model.orderobject;

/**
 * This file was generated by ATDefAccessClassGenerator and FMPP 2.3.15
 */
import com.datasweep.compatibility.client.Server;

import com.leateck.model.orderobject.IMESLCOrderObjectFilter;
import com.leateck.model.orderobject.MESGeneratedLCOrderObjectFilter;

/**
 * Generated filter class for application table LC_OrderObject.
 */
public class MESLCOrderObjectFilter extends MESGeneratedLCOrderObjectFilter //nl
                                              implements IMESLCOrderObjectFilter {

    /** Generated attribute definition */
    private static final long serialVersionUID = 1L;

    /**
     * Generated constructor
     *
     * @param server The Server object
     */
    public MESLCOrderObjectFilter(Server server) {
        super(server);
    }

    /**
     * Generated default constructor
     */
    public MESLCOrderObjectFilter() {
        super();
    }

    /**
     * Generated method definition
     * 
     * @return the filter object
     */
    public static IMESLCOrderObjectFilter createFilter() {
        return new MESLCOrderObjectFilter();
    }

}