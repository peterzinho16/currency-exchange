package com.example.currencyexchange.currency;

import com.example.currencyexchange.advice.CustomValidationException;
import com.example.currencyexchange.advice.NotFoundValidationException;
import com.example.currencyexchange.controller.CurrencyController;
import com.example.currencyexchange.domain.Currency;
import com.example.currencyexchange.repository.CurrencyRepository;
import com.example.currencyexchange.service.CurrencyService;
import com.example.currencyexchange.service.impl.CurrencyServiceImpl;
import com.example.currencyexchange.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static com.example.currencyexchange.util.Utils.prepareAuthorizationHeader;

@PropertySource("${private.file}")
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CurrencyController.class)
@Import({CurrencyServiceImpl.class})
public class CurrencyControllerTest {

    @MockBean
    CurrencyRepository repository;

    @MockBean
    R2dbcMappingContext r2dbcMappingContext;

    @Autowired
    private WebTestClient webTestClient;

    @Value("${service.ingress.context-path}/currency")
    private String controllerPath;

    @MockBean
    private CurrencyService currencyService;

    @Value("${jwt.expired}")
    private String jwtExpired;
    @Value("${jwt.for-tests}")
    private String jwtForTests;

    @MockBean
    private Validator validator;

    @BeforeEach
    public void setUp() {

        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();
    }

    @Test
    void testCreateCurrency() throws NotFoundValidationException, CustomValidationException {
        Currency currency = this.getCurrencyWithValidFields();

        Mockito.when(repository.save(currency)).thenReturn(Mono.just(currency));
        Mockito.when(currencyService.save(currency)).thenReturn(Mono.just(currency));

        webTestClient.post()
                .uri(controllerPath)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(currency))
                .header(HttpHeaders.AUTHORIZATION, prepareAuthorizationHeader(jwtForTests))
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void testCreateCurrencyNotAuthorized() throws NotFoundValidationException, CustomValidationException {
        Currency exchangeRate = this.getCurrencyWithValidFields();

        Mockito.when(repository.save(exchangeRate)).thenReturn(Mono.just(exchangeRate));
        Mockito.when(currencyService.save(exchangeRate)).thenReturn(Mono.just(exchangeRate));

        webTestClient.post()
                .uri(controllerPath)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(exchangeRate))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void testCreateCurrencyInvalidJWTorExpired() throws NotFoundValidationException, CustomValidationException {
        Currency currency = getCurrencyWithValidFields();

        Mockito.when(repository.save(currency)).thenReturn(Mono.just(currency));
        Mockito.when(currencyService.save(currency)).thenReturn(Mono.just(currency));
        ;
        webTestClient.post()
                .uri(controllerPath)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(currency))
                .header(HttpHeaders.AUTHORIZATION, prepareAuthorizationHeader(jwtExpired))
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testCreateCurrencyInvalidFields() throws NotFoundValidationException, CustomValidationException {
        Currency currency = getCurrencyWithInvalidFields();

        Mockito.when(repository.save(currency)).thenReturn(Mono.just(currency));
        Mockito.when(currencyService.save(currency)).thenReturn(Mono.just(currency));

        webTestClient.post()
                .uri(controllerPath)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(currency))
                .header(HttpHeaders.AUTHORIZATION, prepareAuthorizationHeader(jwtForTests))
                .exchange()
                .expectStatus().isBadRequest();
    }

    private Currency getCurrencyWithValidFields() {
        var currency = new Currency();
        currency.setName("Peso Argentino");
        currency.setCode("ARS");
        return currency;
    }

    private Currency getCurrencyWithInvalidFields() {
        var currency = new Currency();
        currency.setName("Sol");
        currency.setCode("pen");
        return currency;
    }
}
