package com.example.currencyexchange.validator;

import com.example.currencyexchange.advice.CustomValidationException;
import io.reactivex.Completable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ValidatorImpl implements Validator {

    @Override
    public Completable validateUUIDFormat(String uuid) {
        int len = uuid.length();
        if (len > 36) {
            throw new IllegalArgumentException("UUID string too large");
        }

        int dash1 = uuid.indexOf('-', 0);
        int dash2 = uuid.indexOf('-', dash1 + 1);
        int dash3 = uuid.indexOf('-', dash2 + 1);
        int dash4 = uuid.indexOf('-', dash3 + 1);
        int dash5 = uuid.indexOf('-', dash4 + 1);

        // For any valid input, dash1 through dash4 will be positive and dash5
        // negative, but it's enough to check dash4 and dash5:
        // - if dash1 is -1, dash4 will be -1
        // - if dash1 is positive but dash2 is -1, dash4 will be -1
        // - if dash1 and dash2 is positive, dash3 will be -1, dash4 will be
        //   positive, but so will dash5
        if (dash4 < 0 || dash5 >= 0) {
            throw new IllegalArgumentException("Invalid UUID string: " + uuid);
        }
        return Completable.complete();
    }

    @Override
    public Mono<Void> validateDifferentIds(Integer primaryId, Integer secondaryId) throws CustomValidationException {
        if (!primaryId.equals(secondaryId)) return Mono.empty();
        throw new CustomValidationException("Validation failed");
    }
}
