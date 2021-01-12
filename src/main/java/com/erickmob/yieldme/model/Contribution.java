package com.erickmob.yieldme.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class  Contribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Exchange may not be null")
    private Exchanges exchange;

    @NotNull(message = "Exchange may not be null")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name="asset_id", nullable=false)
    @NotNull(message = "Asset may not be null")
    private Asset asset;

    @NotNull(message = "Amount may not be null")
    private BigDecimal amount;

    @NotNull(message = "Unit Price may not be null")
    private BigDecimal unitPrice;

    @NotNull(message = "Total Price may not be null")
    private BigDecimal totalPrice;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="wallet_id", nullable=false)
    private Wallet wallet;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull(message = "User may not be null")
    private User user;

    public Contribution(@NotNull(message = "Exchange may not be null") Exchanges exchange,
                        @NotNull(message = "Exchange may not be null") LocalDate date,
                        @NotNull(message = "Asset may not be null") Asset asset,
                        @NotNull(message = "Amount may not be null") BigDecimal amount,
                        @NotNull(message = "Unit Price may not be null") BigDecimal unitPrice,
                        @NotNull(message = "Total Price may not be null") BigDecimal totalPrice
                      ,  Wallet wallet
    ) {
        this.exchange = exchange;
        this.date = date;
        this.asset = asset;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return "Contribution{" +
                "id=" + id +
                ", exchange=" + exchange +
                ", date=" + date +
                ", asset=" + asset +
                ", amount=" + amount +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                ", walletID=" + null +
                '}';
    }


}
