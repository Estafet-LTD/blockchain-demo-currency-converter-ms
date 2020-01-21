package com.estafet.blockchain.demo.currency.converter.ms.controller;

import java.util.List;

import com.estafet.blockchain.demo.messages.lib.bank.BankPaymentBlockChainMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.estafet.blockchain.demo.currency.converter.ms.model.API;
import com.estafet.blockchain.demo.currency.converter.ms.model.ExchangeRate;
import com.estafet.blockchain.demo.currency.converter.ms.service.CurrencyConverterService;
import com.estafet.blockchain.demo.messages.lib.bank.BankPaymentCurrencyConverterMessage;


@RestController
public class CurrencyConverterController {

	@Value("${app.version}")
	private String appVersion;
	
	@Autowired
	private CurrencyConverterService currencyConverterService;
	
	@GetMapping("/api")
	public API getAPI() {
		return new API(appVersion);
	}
		
	@GetMapping(value = "/exchange-rate/{currency}")
	public ExchangeRate getRate(@PathVariable String currency) {
		return currencyConverterService.getExchangeRate(currency);
	}
		
	@GetMapping(value = "/exchange-rates")
	public List<ExchangeRate> getRates() {
		return currencyConverterService.getExchangeRates();
	}

	@PostMapping(value = "/exchange-rate")
	public ResponseEntity<ExchangeRate> addRate(@RequestBody ExchangeRate exchangeRate) {
		return new ResponseEntity<ExchangeRate>(currencyConverterService.newExchangeRate(exchangeRate), HttpStatus.OK);
	}

	@PutMapping("/exchange-rate")
	public ResponseEntity<ExchangeRate> updateRate(@RequestBody ExchangeRate exchangeRate) {
		return new ResponseEntity<ExchangeRate>(currencyConverterService.updateExchangeRate(exchangeRate), HttpStatus.OK);
	}

	@DeleteMapping("/exchange-rates")
	public ResponseEntity<String> deleteAll() {
		currencyConverterService.deleteAll();
		return new ResponseEntity<String>("Exchange Rates Deleted", HttpStatus.OK);
	}
	
	@PostMapping("/testCurrencyConverter")
	public ResponseEntity<BankPaymentBlockChainMessage> calculateSprints(@RequestBody BankPaymentCurrencyConverterMessage bankToCurrencyConvMessage) {
		return new ResponseEntity<BankPaymentBlockChainMessage>(currencyConverterService.convert(bankToCurrencyConvMessage), HttpStatus.OK);
	}
		
}	

