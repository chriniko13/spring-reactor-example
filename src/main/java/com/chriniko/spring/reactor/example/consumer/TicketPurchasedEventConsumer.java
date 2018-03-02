package com.chriniko.spring.reactor.example.consumer;

import com.chriniko.spring.reactor.example.domain.PurchasedTicket;
import com.chriniko.spring.reactor.example.event.TicketPurchasedEvent;
import com.chriniko.spring.reactor.example.persistence.PurchasedTicketsDB;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.bus.Event;
import reactor.fn.Consumer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Log4j
@Component
public class TicketPurchasedEventConsumer implements Consumer<Event<TicketPurchasedEvent>> {

    private static final boolean ADD_LOGGING = false;

    private final CountDownLatch countDownLatch;
    private final PurchasedTicketsDB purchasedTicketsDB;
    private final Boolean addIo;

    @Autowired
    public TicketPurchasedEventConsumer(CountDownLatch countDownLatch, PurchasedTicketsDB purchasedTicketsDB, Boolean addIo) {
        this.countDownLatch = countDownLatch;
        this.purchasedTicketsDB = purchasedTicketsDB;
        this.addIo = addIo;
    }

    @Override
    public void accept(Event<TicketPurchasedEvent> ticketPurchasedEvent) {

        if (ADD_LOGGING) {
            log.info("TicketPurchasedEventConsumer#accept, incoming event = " + ticketPurchasedEvent);
        }

        TicketPurchasedEvent data = ticketPurchasedEvent.getData();

        PurchasedTicket purchasedTicket = new PurchasedTicket(
                data.getTicketId(),
                data.getEventName(),
                data.getPrice(),
                data.getSeatNo()
        );

        if (addIo) {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException ignored) {
            }
        }

        purchasedTicketsDB.store(purchasedTicket);

        countDownLatch.countDown();
    }

}
