package com.example.currencyexchange.controller;

import com.example.currencyexchange.domain.Country;
import com.example.currencyexchange.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("${service.ingress.context-path}/country")
@AllArgsConstructor
public class CountryController {

    private CountryService countryService;

    @GetMapping("")
    public Flux<Country> getAll(@RequestHeader(name = "Authorization") String authorization) {
        return countryService.findAll();

    }
}
