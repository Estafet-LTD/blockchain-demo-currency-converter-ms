package com.estafet.blockchain.demo.ms.currency.converter.ms.ms.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estafet.blockchain.demo.ms.currency.converter.ms.ms.model.API;

@RestController
public class CurrencyConverterController {

	@Value("${app.version}")
	private String appVersion;
	
	@GetMapping("/api")
	public API getAPI() {
		return new API(appVersion);
	}
	
}
