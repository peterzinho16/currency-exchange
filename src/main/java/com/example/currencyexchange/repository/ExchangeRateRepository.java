package com.example.currencyexchange.repository;

import com.example.currencyexchange.domain.ExchangeRate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends ReactiveCrudRepository<ExchangeRate, Integer> {

}
