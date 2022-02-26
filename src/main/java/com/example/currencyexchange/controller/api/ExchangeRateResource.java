package com.example.currencyexchange.controller.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
public class ExchangeRateResource {

    private Integer id;
    private Double exchangeRate;
    private Integer sourceId;
    private Integer destinyId;
    private double destinyRate;
    private LocalDateTime creationDate;
    private LocalDateTime modifiedDate;
    private String createdBy;
    private boolean enabled;
    private LocalDateTime disabledDate;

}
