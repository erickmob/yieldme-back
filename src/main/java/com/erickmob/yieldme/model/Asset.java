package com.erickmob.yieldme.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    @NotBlank(message = "Ticker may not be blank")
    private String ticker;

    @NotBlank(message = "Name may not be blank")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "AssetCategory may not be null")
    private AssetCategory assetCategory;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Currency may not be null")
    private Currency currency;

    public Asset(@NotBlank(message = "Ticker may not be blank") String ticker,
                 @NotBlank(message = "Name may not be blank") String name,
                 @NotNull(message = "AssetCategory may not be null") AssetCategory assetCategory,
                 @NotNull(message = "Currency may not be null") Currency currency) {
        this.ticker = ticker;
        this.name = name;
        this.assetCategory = assetCategory;
        this.currency = currency;
    }
}
