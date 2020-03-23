package com.estafet.blockchain.demo.currency.converter.ms.repository;

import com.estafet.blockchain.demo.currency.converter.ms.model.ExchangeRate;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@ViewIndexed(designDoc = "exchangeRate")
@N1qlPrimaryIndexed
public interface ExchangeRateRepository extends CouchbasePagingAndSortingRepository<ExchangeRate, String> {

    List<ExchangeRate> findAll();

}
