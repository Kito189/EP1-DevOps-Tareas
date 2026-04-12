package com.example.ticketservice.application.port.in;

import com.example.ticketservice.domain.Ticket;

public interface TicketUseCase {
    Ticket registrarTicket(String descripcion, boolean consentimiento);
    Ticket consultarEstadoTicket(String id);
}
