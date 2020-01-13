package com.estafet.blockchain.demo.currency.converter.ms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EXCHANGE_RATE")
public class ExchangeRate {

	@Id
	@Column(name = "CURRENCY", nullable = false)
	private String currency;
	
	@Column(name = "RATE", nullable = false)
	private Double rate;
	
	public double convert(double amount) {
		return amount * rate;
	}
	


	
	public ExchangeRate() {
	}

	public ExchangeRate(String currency, double rate) {
		this.currency = currency;
		this.rate = rate;
	}



	public ExchangeRate addRate() {
		return new ExchangeRate(currency, rate);
	}


	public String getCurrency() {
		return currency;
	}

	public double getRate() {
		return rate;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public List<ExchangeRate> toList() {
		return toList(new ArrayList<ExchangeRate>());
	}

	private List<ExchangeRate> toList( List<ExchangeRate> exchangeRates) {
			return exchangeRates;
		
	}
/*
	public static ExchangeRate fromJSON(String message) {
		try {
			return new ObjectMapper().readValue(message, ExchangeRate.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String toJSON() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
*/
}
