package com.example.currencyexchange.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Table
@Data
public class ExchangeRate {

    private Integer id;

    @NotNull
    private Integer sourceId;

    @NotNull
    private Integer destinyId;

    @Max(value = 1)
    @Min(value = 1)
    private byte sourceUnit;

    @Max(value = 1000)
    private double destinyRate;

    @CreatedDate
    private LocalDateTime creationDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Size
    private String createdBy;

    private boolean enabled;

    private LocalDateTime disabledDate;

    public ExchangeRate() {
    }
}
