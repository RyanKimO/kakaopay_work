package com.kakaopay.money.service.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
@Component
public class ReceiveDividendRequestSender {

    private final JmsTemplate jmsTemplate;


    @Autowired
    public ReceiveDividendRequestSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


    public void send(String token, Long receiverId) {

        jmsTemplate.convertAndSend(ReceiveDividendRequest.DESTINATION_NAME,
                new ReceiveDividendRequest(token, receiverId));
    }


}
