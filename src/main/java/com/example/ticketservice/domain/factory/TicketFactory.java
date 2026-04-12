package com.example.ticketservice.domain.factory;

import com.example.ticketservice.domain.Ticket;
import com.example.ticketservice.domain.TicketStatus;
import java.util.UUID;

public class TicketFactory {

    public static Ticket createWithDefaults(String descripcion) {
        String id = UUID.randomUUID().toString();

        return new Ticket(id, descripcion, TicketStatus.CREADO);
    }
}