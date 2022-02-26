package com.example.currencyexchange.exchangerate;

import com.example.currencyexchange.advice.CustomValidationException;
import com.example.currencyexchange.advice.NotFoundValidationException;
import com.example.currencyexchange.controller.ExchangeRateController;
import com.example.currencyexchange.controller.api.NewExchangeRateResource;
import com.example.currencyexchange.domain.ExchangeRate;
import com.example.currencyexchange.mapper.ExchangeRateMapper;
import com.example.currencyexchange.repository.ExchangeRateRepository;
import com.example.currencyexchange.service.ExchangeRateService;
import com.example.currencyexchange.service.impl.ExchangeRateServiceImpl;
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
@WebFluxTest(controllers = ExchangeRateController.class)
@Import({ExchangeRateServiceImpl.class})
public class ExchangeRateControllerTest {

    @MockBean
    ExchangeRateRepository repository;

    @MockBean
    R2dbcMappingContext r2dbcMappingContext;

    @Autowired
    private WebTestClient webTestClient;

    @Value("${service.ingress.context-path}/exchange-rate")
    private String controllerPath;

    @MockBean
    private ExchangeRateMapper exchangeRateMapper;

    @MockBean
    private ExchangeRateService exchangeRateService;

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
    void testCreateExchangeRate() throws NotFoundValidationException, CustomValidationException {
        ExchangeRate exchangeRate = this.getExchangeRate();
        NewExchangeRateResource newExchangeRateResource = this.getExchangeResourceWithValidFields();

        Mockito.when(repository.save(exchangeRate)).thenReturn(Mono.just(exchangeRate));
        Mockito.when(exchangeRateService.save(exchangeRate)).thenReturn(Mono.just(exchangeRate));
        Mockito.when(exchangeRateMapper.toModel(newExchangeRateResource)).thenReturn(exchangeRate);

        webTestClient.post()
                .uri(controllerPath)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(newExchangeRateResource))
                .header(HttpHeaders.AUTHORIZATION, prepareAuthorizationHeader(jwtForTests))
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void testCreateExchangeRateNotAuthorized() throws NotFoundValidationException, CustomValidationException {
        ExchangeRate exchangeRate = this.getExchangeRate();
        NewExchangeRateResource newExchangeRateResource = this.getExchangeResourceWithValidFields();

        Mockito.when(repository.save(exchangeRate)).thenReturn(Mono.just(exchangeRate));
        Mockito.when(exchangeRateService.save(exchangeRate)).thenReturn(Mono.just(exchangeRate));
        Mockito.when(exchangeRateMapper.toModel(newExchangeRateResource)).thenReturn(exchangeRate);

        webTestClient.post()
                .uri(controllerPath)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(newExchangeRateResource))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void testCreateExchangeRateInvalidJWTorExpired() throws NotFoundValidationException, CustomValidationException {
        ExchangeRate exchangeRate = getExchangeRate();
        NewExchangeRateResource newExchangeRateResource = this.getExchangeResourceWithValidFields();

        Mockito.when(repository.save(exchangeRate)).thenReturn(Mono.just(exchangeRate));
        Mockito.when(exchangeRateService.save(exchangeRate)).thenReturn(Mono.just(exchangeRate));
        Mockito.when(exchangeRateMapper.toModel(newExchangeRateResource)).thenReturn(exchangeRate);
        ;
        webTestClient.post()
                .uri(controllerPath)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(newExchangeRateResource))
                .header(HttpHeaders.AUTHORIZATION, prepareAuthorizationHeader(jwtExpired))
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testCreateExchangeRateInvalidFields() throws NotFoundValidationException, CustomValidationException {
        ExchangeRate exchangeRate = getExchangeRate();
        NewExchangeRateResource newExchangeRateResource = this.getExchangeRateResourceWithInvalidFieldValues();

        Mockito.when(repository.save(exchangeRate)).thenReturn(Mono.just(exchangeRate));
        Mockito.when(exchangeRateService.save(exchangeRate)).thenReturn(Mono.just(exchangeRate));
        Mockito.when(exchangeRateMapper.toModel(newExchangeRateResource)).thenReturn(exchangeRate);

        webTestClient.post()
                .uri(controllerPath)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(newExchangeRateResource))
                .header(HttpHeaders.AUTHORIZATION, prepareAuthorizationHeader(jwtForTests))
                .exchange()
                .expectStatus().isBadRequest();
    }

    private NewExchangeRateResource getExchangeResourceWithValidFields() {
        var newExchangeRateResource = new NewExchangeRateResource();
        newExchangeRateResource.setSourceId(1);
        newExchangeRateResource.setDestinyId(1);
        newExchangeRateResource.setSourceUnit((byte) 0b00000001);
        newExchangeRateResource.setDestinyRate(3.36d);
        newExchangeRateResource.setEnabled(true);
        return newExchangeRateResource;
    }

    private ExchangeRate getExchangeRate() {
        var exchangeRate = new ExchangeRate();
        exchangeRate.setSourceId(1);
        exchangeRate.setDestinyId(1);
        exchangeRate.setSourceUnit((byte) 0b00000001);
        exchangeRate.setDestinyRate(3.36d);
        return exchangeRate;
    }

    private NewExchangeRateResource getExchangeRateResourceWithInvalidFieldValues() {
        var newExchangeRateResource = new NewExchangeRateResource();
        newExchangeRateResource.setSourceId(null);
        newExchangeRateResource.setDestinyId(null);
        newExchangeRateResource.setSourceUnit((byte) 0b00000011);
        newExchangeRateResource.setDestinyRate(100000000D);
        return newExchangeRateResource;
    }
}
