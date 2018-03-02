package com.chriniko.spring.reactor.example.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PurchasedTicket {

    private final UUID ticketId;
    private final String eventName;
    private final BigDecimal price;
    private final String seatNo;
}
