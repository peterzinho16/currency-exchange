package com.example.currencyexchange.controller.api;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;

@Data
public class ExchangeRatePatchResource {

    private Optional<@NotBlank @Size(max=4000) String> destinyRate;
    private Optional<Boolean> enabled;

}
