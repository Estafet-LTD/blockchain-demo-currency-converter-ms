package com.estafet.blockchain.demo.currency.converter.ms.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
public class ExchangeRate implements Serializable {

	@Id
	@NotNull
	private String currency;
	
	@Field
	@NotNull
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ExchangeRate that = (ExchangeRate) o;
		return Objects.equals(currency, that.currency) &&
				Objects.equals(rate, that.rate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(currency, rate);
	}
}
