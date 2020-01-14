package com.estafet.blockchain.demo.currency.converter.ms.container.tests;

import com.estafet.blockchain.demo.messages.lib.bank.BankPaymentMessage;
import com.estafet.microservices.scrum.lib.commons.jms.TopicConsumer;

public class BankPaymentConsumer extends TopicConsumer {

	public BankPaymentConsumer() {
		super("currency.converter.out.topic");
	}

	public BankPaymentMessage consume() {
		return super.consume(BankPaymentMessage.class);
	}

}
