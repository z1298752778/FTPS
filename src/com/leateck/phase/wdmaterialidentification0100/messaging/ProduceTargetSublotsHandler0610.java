package com.leateck.phase.wdmaterialidentification0100.messaging;

import com.rockwell.mes.services.wd.ifc.messaging.*;
import com.rockwell.mes.commons.messaging.ifc.*;
import java.util.*;
import org.apache.commons.logging.*;

public class ProduceTargetSublotsHandler0610 extends PharmaSuiteUIMessageListener<ProduceSublotMessage>
{
    private static final Log LOGGER;
    private final IProduceTargetSublotsHandlerCallBack0610 callBackClient;

    public ProduceTargetSublotsHandler0610(final IProduceTargetSublotsHandlerCallBack0610 callBackClientArg) {
        super(ProduceSublotMessage.createTemplateMessageProduceTargetSublotsBroadcast());
        this.callBackClient = callBackClientArg;
    }

    public void onUIMessage(final ProduceSublotMessage message) {
        ProduceTargetSublotsHandler0610.LOGGER.debug((Object)("Produced target sublots message: " + message + " (sublotKeys=" + message.getSublotKeys() + ", osoKey=" + message.getOrderStepOutputKey() + ")..."));
        this.callBackClient.callBack(message.getSublotKeys(), message.getOrderStepOutputKey());
    }

    static {
        LOGGER = LogFactory.getLog((Class)ProduceTargetSublotsHandler0610.class);
    }

    public interface IProduceTargetSublotsHandlerCallBack0610
    {
        void callBack(final List<Long> sublotKeys, final Long osoKey);
    }
}
