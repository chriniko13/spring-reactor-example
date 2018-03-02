package com.chriniko.spring.reactor.example.persistence;

import com.chriniko.spring.reactor.example.domain.PurchasedTicket;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PurchasedTicketsDB {

    private Map<UUID, PurchasedTicket> purchasedTickets;

    @PostConstruct
    void init() {
        purchasedTickets = new ConcurrentHashMap<>();
    }

    public void store(PurchasedTicket purchasedTicket) {
        purchasedTickets.put(purchasedTicket.getTicketId(), purchasedTicket);
    }
}
