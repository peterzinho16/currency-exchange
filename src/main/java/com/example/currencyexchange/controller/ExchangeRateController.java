package com.example.currencyexchange.controller;

import com.example.currencyexchange.advice.CustomValidationException;
import com.example.currencyexchange.advice.NotFoundValidationException;
import com.example.currencyexchange.controller.api.ExchangeRatePatchResource;
import com.example.currencyexchange.controller.api.NewExchangeRateResource;
import com.example.currencyexchange.domain.ExchangeRate;
import com.example.currencyexchange.mapper.ExchangeRateMapper;
import com.example.currencyexchange.service.ExchangeRateService;
import com.example.currencyexchange.validator.Validator;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("${service.ingress.context-path}/exchange-rate")
@Slf4j
@RequiredArgsConstructor
public class ExchangeRateController {

    private final Validator validator;

    private final ExchangeRateMapper exchangeRateMapper;

    private final ExchangeRateService exchangeRateService;

    @ApiResponse(description = "Storage a exchangeRate",
            responseCode = "201")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> save(@Valid @RequestBody NewExchangeRateResource newExchangeRateResource,
                           @RequestHeader(name = "Authorization") String authorization) throws CustomValidationException, NotFoundValidationException {
        return validator
                .validateDifferentIds(
                        newExchangeRateResource.getSourceId(),
                        newExchangeRateResource.getDestinyId())
                .and(exchangeRateService.save(exchangeRateMapper.toModel(newExchangeRateResource)))
                .then(Mono.empty());
    }

    @ApiResponse(description = "Get all currencies",
            responseCode = "200")
    @PreAuthorize("hasRole('ROLE_UMA_AUTHORIZATION')")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.ALL_VALUE})
    public Flux<ExchangeRate> getAll() {
        return exchangeRateService.findAll();
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
        return exchangeRateService.delete();
    }

    @GetMapping(value = "/{id}")
    public Mono<ExchangeRate> getById(@PathVariable Integer id) throws NotFoundValidationException {
        return exchangeRateService.findById(id);
    }

    @ApiResponse(description = "Update exchange rate",
            responseCode = "204")
    @PatchMapping(value = "/{id}")
    public Mono<ResponseEntity<Void>> update(@PathVariable @NotNull final Integer id,
                                             @Valid @RequestBody final ExchangeRatePatchResource patch,
                                             @RequestHeader(name = "Authorization") String authorization) throws NotFoundValidationException {

        // Find the item and update the instance
        return exchangeRateService.findById(id)
                .map(item -> exchangeRateMapper.patch(patch, item))
                .flatMap(exchangeRateService::update)
                .map(itemId -> noContent().build());
    }

}
