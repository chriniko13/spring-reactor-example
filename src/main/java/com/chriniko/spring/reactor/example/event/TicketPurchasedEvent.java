package com.chriniko.spring.reactor.example.event;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TicketPurchasedEvent {

    private final UUID ticketId;
    private final String eventName;
    private final BigDecimal price;
    private final String seatNo;
}
