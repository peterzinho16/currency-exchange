package com.example.currencyexchange.country;

import com.example.currencyexchange.advice.CustomValidationException;
import com.example.currencyexchange.advice.NotFoundValidationException;
import com.example.currencyexchange.controller.CountryController;
import com.example.currencyexchange.controller.CountryController;
import com.example.currencyexchange.domain.Country;
import com.example.currencyexchange.repository.CountryRepository;
import com.example.currencyexchange.service.CountryService;
import com.example.currencyexchange.service.impl.CountryServiceImpl;
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
@WebFluxTest(controllers = CountryController.class)
@Import({CountryServiceImpl.class})
public class CountryControllerTest {

    @MockBean
    CountryRepository repository;

    @MockBean
    R2dbcMappingContext r2dbcMappingContext;

    @Autowired
    private WebTestClient webTestClient;

    @Value("${service.ingress.context-path}/country")
    private String controllerPath;

    @MockBean
    private CountryService currencyService;

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
    void testgetAllCountry() throws NotFoundValidationException, CustomValidationException {

//        Mockito.when(repository.save(currency)).thenReturn(Mono.just(currency));
//        Mockito.when(currencyService.save(currency)).thenReturn(Mono.just(currency));

        webTestClient.get()
                .uri(controllerPath)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, prepareAuthorizationHeader(jwtForTests))
                .exchange()
                .expectStatus().isOk();
    }
}
