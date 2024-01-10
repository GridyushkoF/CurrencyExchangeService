package ru.skillbox.currency.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CurrencyDto {
    private Long id;

    private String name;

    private Long nominal;

    private Double value;

    private Long isoNumCode;
    private String isoLiteralCode;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getNominal() {
        return nominal;
    }

    public Double getValue() {
        return value;
    }

    public Long getIsoNumCode() {
        return isoNumCode;
    }

    public String getIsoLiteralCode() {
        return isoLiteralCode;
    }
}