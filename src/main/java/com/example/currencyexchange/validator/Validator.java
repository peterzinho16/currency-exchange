package com.example.currencyexchange.validator;

import com.example.currencyexchange.advice.CustomValidationException;
import io.reactivex.Completable;
import reactor.core.publisher.Mono;

public interface Validator {

    Completable validateUUIDFormat(String uuid);

    Mono<Void> validateDifferentIds(Integer primaryId, Integer secondaryId) throws CustomValidationException;
}
