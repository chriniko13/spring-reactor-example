package com.chriniko.spring.reactor.example.init;

import com.chriniko.spring.reactor.example.consumer.TicketPurchasedEventConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.bus.EventBus;
import reactor.bus.selector.Selector;
import reactor.bus.selector.Selectors;

import javax.annotation.PostConstruct;

@Component
public class EventBusInitializer {

    public static final String TICKETS_EVENT_KEY = "tickets";

    private final EventBus eventBus;
    private final TicketPurchasedEventConsumer ticketPurchasedEventConsumer;

    @Autowired
    public EventBusInitializer(EventBus eventBus, TicketPurchasedEventConsumer ticketPurchasedEventConsumer) {
        this.eventBus = eventBus;
        this.ticketPurchasedEventConsumer = ticketPurchasedEventConsumer;
    }

    @PostConstruct
    void init() {
        Selector predicate = Selectors.predicate(o -> o instanceof String && TICKETS_EVENT_KEY.equals(o));
        eventBus.on(predicate, ticketPurchasedEventConsumer);
    }

}
