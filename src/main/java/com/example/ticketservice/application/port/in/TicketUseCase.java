package com.example.ticketservice.application.port.in;

import com.example.ticketservice.domain.Ticket;
import java.util.List;

public interface TicketUseCase {
    Ticket registrarTicket(String descripcion, boolean consentimiento);
    Ticket consultarEstadoTicket(String id);
    List<Ticket> listarTickets();
}
