package com.estafet.blockchain.demo.currency.converter.ms.service;


import java.util.List;

import com.estafet.blockchain.demo.messages.lib.bank.BankPaymentBlockChainMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estafet.blockchain.demo.messages.lib.bank.BankPaymentCurrencyConverterMessage;
import com.estafet.blockchain.demo.currency.converter.ms.dao.CurrencyConverterDAO;
import com.estafet.blockchain.demo.currency.converter.ms.jms.BankPaymentProducer;
import com.estafet.blockchain.demo.currency.converter.ms.model.ExchangeRate;


@Service
public class CurrencyConverterService {

	@Autowired
	private CurrencyConverterDAO currencyConverterDAO;
	
	@Autowired
	private BankPaymentProducer bankPaymentProducer;

	@Transactional
	public void deleteAll() {
		 currencyConverterDAO.deleteAll();
	}
	


	@Transactional
	public ExchangeRate updateExchangeRate(ExchangeRate message) {
		
		ExchangeRate exchangeRate = currencyConverterDAO.getExchangeRate(message.getCurrency());
		exchangeRate.setRate(message.getRate());		
		return currencyConverterDAO.update(exchangeRate);

			
		
	}

	@Transactional
	public ExchangeRate newExchangeRate(ExchangeRate message) {
		ExchangeRate exchangeRate = new ExchangeRate(message.getCurrency(), message.getRate());
		return currencyConverterDAO.create(exchangeRate);
	}


	@Transactional(readOnly = true)
	public List<ExchangeRate> getExchangeRates() {
		return currencyConverterDAO.getExchangeRates();
	}

	@Transactional(readOnly = true)
	public ExchangeRate getExchangeRate(String currency) {
		return currencyConverterDAO.getExchangeRate(currency);
	}
	

	@Transactional(readOnly = true)
	public BankPaymentBlockChainMessage convert(BankPaymentCurrencyConverterMessage bankToCurrencyConvMessage) {
		ExchangeRate exchangeRate = currencyConverterDAO.getExchangeRate(bankToCurrencyConvMessage.getCurrency());

		return bankPaymentProducer.sendMessage(new BankPaymentBlockChainMessage((int) Math.round(exchangeRate.convert(bankToCurrencyConvMessage.getCurrencyAmount())),
				bankToCurrencyConvMessage.getWalletAddress(),
				bankToCurrencyConvMessage.getSignature(), bankToCurrencyConvMessage.getTransactionId()));
	}

}

