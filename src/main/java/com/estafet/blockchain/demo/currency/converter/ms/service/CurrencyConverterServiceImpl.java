package com.estafet.blockchain.demo.currency.converter.ms.service;

import com.estafet.blockchain.demo.currency.converter.ms.jms.BankPaymentProducer;
import com.estafet.blockchain.demo.currency.converter.ms.model.ExchangeRate;
import com.estafet.blockchain.demo.currency.converter.ms.repository.ExchangeRateRepository;
import com.estafet.blockchain.demo.messages.lib.bank.BankPaymentBlockChainMessage;
import com.estafet.blockchain.demo.messages.lib.bank.BankPaymentCurrencyConverterMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService{

    @Autowired
    private BankPaymentProducer bankPaymentProducer;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Override
    @Transactional
    public void deleteAll() {
        exchangeRateRepository.deleteAll();
    }

    @Override
    @Transactional
    public ExchangeRate updateExchangeRate(ExchangeRate message) {

        ExchangeRate exchangeRate = exchangeRateRepository.findOne(message.getCurrency());
        exchangeRate.setRate(message.getRate());
        exchangeRateRepository.getCouchbaseOperations().update(exchangeRate);
        return exchangeRate;
    }

    @Override
    @Transactional
    public ExchangeRate newExchangeRate(ExchangeRate message) {
        ExchangeRate exchangeRate = new ExchangeRate(message.getCurrency(), message.getRate());
        return exchangeRateRepository.save(exchangeRate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExchangeRate> getExchangeRates() {
        return exchangeRateRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ExchangeRate getExchangeRate(String currency) {
        return exchangeRateRepository.findOne(currency);
    }


    @Override
    @Transactional(readOnly = true)
    public BankPaymentBlockChainMessage convert(BankPaymentCurrencyConverterMessage bankToCurrencyConvMessage) {
        ExchangeRate exchangeRate = exchangeRateRepository.findOne(bankToCurrencyConvMessage.getCurrency());

        return bankPaymentProducer.sendMessage(new BankPaymentBlockChainMessage((int) Math.round(exchangeRate.convert(bankToCurrencyConvMessage.getCurrencyAmount())),
                bankToCurrencyConvMessage.getWalletAddress(),
                bankToCurrencyConvMessage.getSignature(), bankToCurrencyConvMessage.getTransactionId()));
    }
}
