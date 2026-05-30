package com.example.ticketservice.application.port.out;

import com.example.ticketservice.domain.Ticket;
import java.util.List;
import java.util.Optional;

public interface TicketRepositoryPort {
    Ticket save(Ticket ticket);
    Optional<Ticket> findById(String id);
    List<Ticket> findAll();
}
