package com.erickmob.yieldme.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssetCategory {
    ACAO("Ação"),
    FII("FII"),
    ETF("ETF"),
    STOCK("Stock"),
    CRYPTO("CRYPTO");

    private final String name;
}
