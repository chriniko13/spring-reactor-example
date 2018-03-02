package com.chriniko.spring.reactor.example.publisher;

import com.chriniko.spring.reactor.example.dto.TicketRequestDto;
import com.chriniko.spring.reactor.example.event.TicketPurchasedEvent;
import com.chriniko.spring.reactor.example.init.EventBusInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.UUID;

@Component
public class TicketPurchasedEventPublisher {

    private final EventBus eventBus;

    @Autowired
    public TicketPurchasedEventPublisher(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void purchase(TicketRequestDto ticketRequestDto) {

        TicketPurchasedEvent ticketPurchasedEvent = new TicketPurchasedEvent(
                UUID.randomUUID(),
                ticketRequestDto.getEventName(),
                ticketRequestDto.getPrice(),
                ticketRequestDto.getSeatNo()
        );

        eventBus.notify(EventBusInitializer.TICKETS_EVENT_KEY, Event.wrap(ticketPurchasedEvent));
    }

}
