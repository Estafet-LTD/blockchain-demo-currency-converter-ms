package com.estafet.blockchain.demo.currency.converter.ms.container.tests;

import com.estafet.blockchain.demo.messages.lib.bank.BankPaymentBlockChainMessage;
import com.estafet.openshift.boost.commons.lib.jms.TopicConsumer;

public class BankPaymentConsumer extends TopicConsumer {

	public BankPaymentConsumer() {
		super("currency.converter.out.topic");
	}

	public BankPaymentBlockChainMessage consume() {
		return super.consume(BankPaymentBlockChainMessage.class);
	}

}
