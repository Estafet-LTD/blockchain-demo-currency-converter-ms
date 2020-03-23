package com.estafet.blockchain.demo.currency.converter.ms.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.estafet.blockchain.demo.currency.converter.ms.service.CurrencyConverterService;

import com.estafet.blockchain.demo.messages.lib.bank.BankPaymentCurrencyConverterMessage;


import io.opentracing.Tracer;

@Component
public class CurrencyConverterConsumer {

	public final static String TOPIC = "currency.converter.input.topic";
	
	@Autowired
	private Tracer tracer;

	@Autowired
	private CurrencyConverterService currencyConverterService;

	@JmsListener(destination = TOPIC, containerFactory = "myFactory")
	public void onMessage(String message) {
		try {
			currencyConverterService.convert(BankPaymentCurrencyConverterMessage.fromJSON(message));
		} finally {
			if (tracer.activeSpan() != null) {
				tracer.activeSpan().close();	
			}
		}
	}

}