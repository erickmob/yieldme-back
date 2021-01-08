package com.erickmob.yieldme.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currency {
    DOLLAR("U$"),
    REAIS("R$");

    private final String name;
}
