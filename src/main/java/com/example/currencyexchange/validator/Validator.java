package com.example.currencyexchange.validator;

import io.reactivex.Completable;

public interface Validator {

    Completable validateUUIDFormat(String uuid);
}
