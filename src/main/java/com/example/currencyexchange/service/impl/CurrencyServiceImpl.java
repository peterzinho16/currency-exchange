package com.example.currencyexchange.service.impl;

import com.example.currencyexchange.advice.CustomValidationException;
import com.example.currencyexchange.advice.NotFoundValidationException;
import com.example.currencyexchange.domain.Currency;
import com.example.currencyexchange.generic.BaseServiceImpl;
import com.example.currencyexchange.repository.CurrencyRepository;
import com.example.currencyexchange.service.CurrencyService;
import io.reactivex.Single;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class CurrencyServiceImpl extends BaseServiceImpl<CurrencyRepository> implements CurrencyService {

    public CurrencyServiceImpl(CurrencyRepository repository) {
        super(repository);
    }

    @Override
    public Mono<Currency> save(Currency entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(entity);
    }

    @Override
    public Mono<Currency> update(Currency entity) {
        return repository.save(entity);
    }

    @Override
    public Mono<Currency> findOne(Integer id) throws NotFoundValidationException {
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
    public Flux<Currency> findAll() {
        return repository.findAll();
    }

    @Override
    public Single<Currency> findById(Integer id) throws NotFoundValidationException {
        var msProfessionMono = repository.findById(id).switchIfEmpty(
                Mono.defer(
                        () -> Mono.error(new NotFoundValidationException("Not found"))
                ));
        return Single.fromPublisher(msProfessionMono);
    }
}
