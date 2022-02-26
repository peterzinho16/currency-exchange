package com.example.currencyexchange.service;

import com.example.currencyexchange.advice.NotFoundValidationException;
import com.example.currencyexchange.domain.Currency;
import com.example.currencyexchange.generic.BaseService;
import io.reactivex.Single;

import java.util.UUID;

public interface CurrencyService extends BaseService<Currency, Integer> {

    Single<Currency> findById(Integer id) throws NotFoundValidationException;
}
