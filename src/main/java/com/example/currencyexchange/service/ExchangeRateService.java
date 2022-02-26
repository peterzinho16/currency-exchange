package com.example.currencyexchange.service;

import com.example.currencyexchange.advice.NotFoundValidationException;
import com.example.currencyexchange.domain.ExchangeRate;
import com.example.currencyexchange.generic.BaseService;
import io.reactivex.Single;
import reactor.core.publisher.Mono;

public interface ExchangeRateService extends BaseService<ExchangeRate, Integer> {

    Mono<ExchangeRate> findById(Integer id) throws NotFoundValidationException;
}
