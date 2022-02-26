package com.example.currencyexchange.service.impl;

import com.example.currencyexchange.advice.CustomValidationException;
import com.example.currencyexchange.advice.NotFoundValidationException;
import com.example.currencyexchange.domain.ExchangeRate;
import com.example.currencyexchange.generic.BaseServiceImpl;
import com.example.currencyexchange.repository.ExchangeRateRepository;
import com.example.currencyexchange.service.ExchangeRateService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ExchangeRateServiceImpl extends BaseServiceImpl<ExchangeRateRepository> implements ExchangeRateService {

    public ExchangeRateServiceImpl(ExchangeRateRepository repository) {
        super(repository);
    }

    @Override
    public Mono<ExchangeRate> save(ExchangeRate entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(entity);
    }

    @Override
    public Mono<ExchangeRate> update(ExchangeRate entity) {
        return repository.save(entity).switchIfEmpty(
                Mono.defer(
                        () -> Mono.error(new NotFoundValidationException("Not success"))
                ));
    }

    @Override
    public Mono<ExchangeRate> findOne(Integer id) throws NotFoundValidationException {
        return repository.findById(id);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Mono<Void> delete() {
        return repository.deleteAll();
    }

    @Override
    public Flux<ExchangeRate> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<ExchangeRate> findById(Integer id) throws NotFoundValidationException {
        return repository.findById(id).switchIfEmpty(
                Mono.defer(
                        () -> Mono.error(new NotFoundValidationException("Not found"))
                ));
    }
}
