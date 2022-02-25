package com.example.currencyexchange.repository;

import com.bindord.eureka.auth.domain.master.MsProfession;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CurrencyRepository extends ReactiveMongoRepository<MsProfession, UUID> {

}
