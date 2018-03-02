package com.chriniko.spring.reactor.example.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketRequestDto {

    private final String eventName;
    private final BigDecimal price;
    private final String seatNo;

}
