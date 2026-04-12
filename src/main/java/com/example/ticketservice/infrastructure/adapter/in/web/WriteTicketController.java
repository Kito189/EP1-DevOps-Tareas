package com.example.ticketservice.infrastructure.adapter.in.web;

import com.example.ticketservice.application.port.in.TicketUseCase;
import com.example.ticketservice.domain.Ticket;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@Profile("write")
public class WriteTicketController {

    private final TicketUseCase ticketUseCase;

    public WriteTicketController(TicketUseCase ticketUseCase) {
        this.ticketUseCase = ticketUseCase;
    }

    public record TicketRequest(String descripcion, boolean consentimiento) {}

    @PostMapping
    public ResponseEntity<?> registrarTicket(@RequestBody TicketRequest request) {
        try {
            Ticket nuevoTicket = ticketUseCase.registrarTicket(request.descripcion(), request.consentimiento());
            return new ResponseEntity<>(nuevoTicket, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }
}
