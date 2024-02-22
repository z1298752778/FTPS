package com.leateck.phase.wdmaterialidentification0100.messaging;

import com.rockwell.mes.services.wd.ifc.messaging.*;
import com.datasweep.compatibility.client.*;
import com.rockwell.mes.commons.messaging.ifc.*;
import org.apache.commons.logging.*;

public class OSOPositionClosedHandler0610 extends PharmaSuiteUIMessageListener<OSOPositionCompletedMessage>
{
    private static final Log LOGGER;
    private final IOutputWeighingPositionClosedCallBack0610 callBackClient;

    public OSOPositionClosedHandler0610(final IOutputWeighingPositionClosedCallBack0610 callBackClientArg) {
        super(OSOPositionCompletedMessage.createPositionCompletedBroadcast((OrderStepOutput)null));
        this.callBackClient = callBackClientArg;
    }

    public void onUIMessage(final OSOPositionCompletedMessage message) {
        OSOPositionClosedHandler0610.LOGGER.debug((Object)("Closed output weighing position message: " + message + " (osoKey=" + message.getOrderStepOutputKey() + ")..."));
        this.callBackClient.callBack(message.getOrderStepOutputKey());
    }

    static {
        LOGGER = LogFactory.getLog((Class)OSOPositionClosedHandler0610.class);
    }

    public interface IOutputWeighingPositionClosedCallBack0610
    {
        void callBack(final Long osoKey);
    }
}
