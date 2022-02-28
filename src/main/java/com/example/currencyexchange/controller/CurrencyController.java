package com.example.currencyexchange.controller;

import com.example.currencyexchange.advice.CustomValidationException;
import com.example.currencyexchange.advice.NotFoundValidationException;
import com.example.currencyexchange.domain.Currency;
import com.example.currencyexchange.service.CurrencyService;
import com.example.currencyexchange.validator.Validator;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("${service.ingress.context-path}/currency")
@Slf4j
public class CurrencyController {

    private final Validator validator;

    private final CurrencyService currencyService;

    public CurrencyController(Validator validator, CurrencyService currencyService) {
        this.validator = validator;
        this.currencyService = currencyService;
    }

    @ApiResponse(description = "Storage a currency",
            responseCode = "201")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> save(@Valid @RequestBody Currency currency,
                           @RequestHeader(name = "Authorization") String authorization) throws CustomValidationException, NotFoundValidationException {
        return currencyService.save(currency)
                .then(Mono.empty());
    }

    @ApiResponse(description = "Get all currencies",
            responseCode = "200")
    @PreAuthorize("hasRole('ROLE_UMA_AUTHORIZATION')")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.ALL_VALUE})
    public Flux<Currency> getAll(
            @RequestHeader(name = "Authorization") String authorization) {
        return currencyService.findAll();
    }

    @ApiResponses({
            @ApiResponse(description = "Delete all currencies",
                    responseCode = "200"),
            @ApiResponse(description = "Operation failed",
                    responseCode = "400")
    })
    @DeleteMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.ALL_VALUE})
    public Mono<Void> removeAll() throws CustomValidationException, NotFoundValidationException {
        return currencyService.delete();
    }

    @GetMapping(value = "/{id}")
    public Single<Currency> getById(@PathVariable Integer id) throws NotFoundValidationException {
        return currencyService.findById(id);
    }

}
