package com.erickmob.yieldme.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currency {
    DOLLAR("U$"),
    REAL("R$");

    private final String name;
}
