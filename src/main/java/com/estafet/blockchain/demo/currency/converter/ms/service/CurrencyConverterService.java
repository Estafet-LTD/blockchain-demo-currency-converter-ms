package com.estafet.blockchain.demo.currency.converter.ms.service;

import com.estafet.blockchain.demo.currency.converter.ms.model.ExchangeRate;
import com.estafet.blockchain.demo.messages.lib.bank.BankPaymentBlockChainMessage;
import com.estafet.blockchain.demo.messages.lib.bank.BankPaymentCurrencyConverterMessage;

import java.util.List;

public interface CurrencyConverterService {

	ExchangeRate getExchangeRate(String currency);

	List<ExchangeRate> getExchangeRates();

	ExchangeRate newExchangeRate(ExchangeRate exchangeRate);

	ExchangeRate updateExchangeRate(ExchangeRate exchangeRate);

	void deleteAll();

	BankPaymentBlockChainMessage convert(BankPaymentCurrencyConverterMessage bankToCurrencyConvMessage);
}

