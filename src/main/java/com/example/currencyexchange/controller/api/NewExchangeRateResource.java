package com.example.currencyexchange.controller.api;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class NewExchangeRateResource {

    @NotNull
    private Integer sourceId;

    @NotNull
    private Integer destinyId;

    @Max(value = 1)
    @Min(value = 1)
    private byte sourceUnit;

    @Max(value = 1000)
    private Double destinyRate;

    private Boolean enabled;
}
