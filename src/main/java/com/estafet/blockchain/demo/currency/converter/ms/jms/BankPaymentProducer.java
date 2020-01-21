package com.estafet.blockchain.demo.currency.converter.ms.jms;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;

import com.estafet.blockchain.demo.messages.lib.bank.BankPaymentBlockChainMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

@Component
public class BankPaymentProducer {

	@Autowired 
	private JmsTemplate jmsTemplate;
	
	public BankPaymentBlockChainMessage sendMessage(BankPaymentBlockChainMessage message) {
		jmsTemplate.setPubSubDomain(true);
		jmsTemplate.convertAndSend("currency.converter.out.topic", message.toJSON(), new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws JMSException {
				message.setStringProperty("message.event.interaction.reference", UUID.randomUUID().toString());
				return message;
			}
		});
		return message;
	}
}
