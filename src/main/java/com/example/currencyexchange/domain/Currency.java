package com.example.currencyexchange.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Table
@Data
public class Currency {

    @Id
    private Integer id;

    @NotBlank
    @Size(max=100)
    private String name;

    @NotBlank
    @Size(min=3, max=3)
    private String code;

    @CreatedDate
    private LocalDateTime creationDate;

    @Size(max = 60)
    private String modifiedBy;

    public Currency(){}

}
