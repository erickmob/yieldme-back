package com.erickmob.yieldme.dto;

import com.erickmob.yieldme.model.Asset;
import com.erickmob.yieldme.model.Exchanges;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ContributionDataDTO {

    @Enumerated(EnumType.STRING)
    private Exchanges exchange;
    //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate date;
    private Asset asset;
    private BigDecimal amount;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
