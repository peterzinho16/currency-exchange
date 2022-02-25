package com.bindord.eureka.auth.validator;

import io.reactivex.Completable;

public interface Validator {

    Completable validateUUIDFormat(String uuid);
}
