package com.bindord.eureka.auth.service;

import com.bindord.eureka.auth.advice.NotFoundValidationException;
import com.bindord.eureka.auth.domain.master.MsProfession;
import com.bindord.eureka.auth.generic.BaseService;
import io.reactivex.Single;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MsProfessionService extends BaseService<MsProfession, UUID> {

    Single<MsProfession> findById(UUID id) throws NotFoundValidationException;
}
