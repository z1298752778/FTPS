package com.leateck.model.processPackObject;

/**
 * This file was generated by ATDefAccessClassGenerator and FMPP 2.3.15
 */
import com.datasweep.compatibility.client.Server;


/**
 * Generated filter class for application table LC_UserProcessPack.
 */
public class MESLCUserProcessPackFilter extends MESGeneratedLCUserProcessPackFilter //nl
                                              implements IMESLCUserProcessPackFilter {

    /** Generated attribute definition */
    private static final long serialVersionUID = 1L;

    /**
     * Generated constructor
     *
     * @param server The Server object
     */
    public MESLCUserProcessPackFilter(Server server) {
        super(server);
    }

    /**
     * Generated default constructor
     */
    public MESLCUserProcessPackFilter() {
        super();
    }

    /**
     * Generated method definition
     * 
     * @return the filter object
     */
    public static IMESLCUserProcessPackFilter createFilter() {
        return new MESLCUserProcessPackFilter();
    }

}