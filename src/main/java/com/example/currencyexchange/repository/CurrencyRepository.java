package com.example.currencyexchange.repository;

import com.example.currencyexchange.domain.Currency;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends ReactiveCrudRepository<Currency, Integer> {

}
