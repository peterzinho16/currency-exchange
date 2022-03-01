package com.example.currencyexchange.service.impl;

import com.example.currencyexchange.advice.CustomValidationException;
import com.example.currencyexchange.advice.NotFoundValidationException;
import com.example.currencyexchange.domain.Country;
import com.example.currencyexchange.generic.BaseServiceImpl;
import com.example.currencyexchange.repository.CountryRepository;
import com.example.currencyexchange.service.CountryService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CountryServiceImpl extends BaseServiceImpl<CountryRepository> implements CountryService {


    public CountryServiceImpl(CountryRepository repository){
        super(repository);
    }

    @Override
    public Mono<Country> save(Country entity) throws NotFoundValidationException, CustomValidationException {
        return null;
    }

    @Override
    public Mono<Country> update(Country entity) {
        return null;
    }

    @Override
    public Mono<Country> findOne(Integer id) throws NotFoundValidationException {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Mono<Void> delete() {
        return null;
    }

    @Override
    public Flux<Country> findAll() {
        return repository.findAll();
    }
}
