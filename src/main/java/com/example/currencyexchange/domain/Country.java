package com.example.currencyexchange.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Table
@Data
public class Country {

    @Id
    private Integer id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(min = 2, max = 2)
    @Pattern(regexp = "[A-Z]{2}")
    private String code;

    @CreatedDate
    private LocalDateTime creationDate;

    @Size(max = 60)
    private String modifiedBy;

    public Country() {
    }

}
