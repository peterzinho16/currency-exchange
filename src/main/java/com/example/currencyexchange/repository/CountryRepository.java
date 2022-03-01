package com.example.currencyexchange.repository;

import com.example.currencyexchange.domain.Country;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends ReactiveCrudRepository<Country, Integer> {
}
