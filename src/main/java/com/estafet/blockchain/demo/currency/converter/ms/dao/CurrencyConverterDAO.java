package com.estafet.blockchain.demo.currency.converter.ms.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.estafet.blockchain.demo.currency.converter.ms.model.ExchangeRate;

@Repository
public class CurrencyConverterDAO {

	@PersistenceContext
	private EntityManager entityManager;


	
	public ExchangeRate getExchangeRate(String currency) {
		ExchangeRate exchangeRate = (ExchangeRate) entityManager.createQuery("SELECT t FROM EXCHANGE_RATE t where t.CURRENCY = :value1")
                .setParameter("value1", currency).getSingleResult();
		return exchangeRate;
				
				
	}

	public void delete(String currency) {
		ExchangeRate exchangeRate = getExchangeRate(currency);
		entityManager.remove(exchangeRate);
	}

	public ExchangeRate create(ExchangeRate exchangeRate) {
		entityManager.persist(exchangeRate);
		//newExchangeRateProducer.sendMessage(exchangeRate);
		return exchangeRate;
	}

	public ExchangeRate update(ExchangeRate exchangeRate) {
		entityManager.merge(exchangeRate);
		//updateSprintProducer.sendMessage(exchangeRate);
		return exchangeRate;
	}

	public List<ExchangeRate> getExchangeRates() {
		return entityManager.createQuery("select * from EXCHANGE_RATE", ExchangeRate.class)
				.getResultList();
	}
	
	public int deleteAll() {
		return entityManager.createQuery("DELETE FROM EXCHANGE_RATE").executeUpdate();
	}

}
