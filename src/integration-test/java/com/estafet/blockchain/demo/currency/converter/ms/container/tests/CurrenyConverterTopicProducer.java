package com.estafet.blockchain.demo.currency.converter.ms.container.tests;

import com.estafet.boostcd.jms.TopicProducer;

public class CurrenyConverterTopicProducer extends TopicProducer {

	public CurrenyConverterTopicProducer() {
		super("currency.converter.input.topic");
	}
	
	public static void send(String message) {
		new CurrenyConverterTopicProducer().sendMessage(message);
	}

}
