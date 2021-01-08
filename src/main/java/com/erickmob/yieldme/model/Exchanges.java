package com.erickmob.yieldme.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Exchanges {
    XP("XP"),
    AVENUE("Avenue"),
    XUMM("Xumm"),
    BLOCKCHAIN("Blockchain");

    private final String name;
}
