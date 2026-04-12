package com.example.ticketservice.infrastructure.adapter.in.web;

import com.example.ticketservice.application.port.in.TicketUseCase;
import com.example.ticketservice.domain.Ticket;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@Profile("read")
public class ReadTicketController {

    private final TicketUseCase ticketUseCase;

    public ReadTicketController(TicketUseCase ticketUseCase) {
        this.ticketUseCase = ticketUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> consultarEstado(@PathVariable String id) {
        try {
            Ticket ticket = ticketUseCase.consultarEstadoTicket(id);
            return ResponseEntity.ok(ticket);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
