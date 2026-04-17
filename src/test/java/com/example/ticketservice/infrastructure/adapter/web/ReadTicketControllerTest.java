package com.example.ticketservice.infrastructure.adapter.in.web;

import com.example.ticketservice.application.port.in.TicketUseCase;
import com.example.ticketservice.domain.Ticket;
import com.example.ticketservice.domain.factory.TicketFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Infrastructure - ReadTicketController Tests")
class ReadTicketControllerTest {

    @Mock
    private TicketUseCase ticketUseCase;

    @InjectMocks
    private ReadTicketController readTicketController;

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = TicketFactory.createWithDefaults("Consultar ticket");
    }

    @Test
    @DisplayName("Debe consultar un ticket por ID")
    void testConsultarTicket() {
        when(ticketUseCase.consultarEstadoTicket("1")).thenReturn(ticket);

        ResponseEntity<Ticket> response = readTicketController.consultarEstado("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Consultar ticket", response.getBody().getDescripcion());
    }

    @Test
    @DisplayName("Debe retornar 404 si el ticket no existe")
    void testConsultarTicketNoExistente() {
        when(ticketUseCase.consultarEstadoTicket("999"))
                .thenThrow(new RuntimeException("Ticket no encontrado"));

        ResponseEntity<Ticket> response = readTicketController.consultarEstado("999");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Debe retornar null en el cuerpo cuando no encuentra")
    void testConsultarTicketNoExistenteBody() {
        when(ticketUseCase.consultarEstadoTicket("999"))
                .thenThrow(new RuntimeException("Ticket no encontrado"));

        ResponseEntity<Ticket> response = readTicketController.consultarEstado("999");

        assertNull(response.getBody());
    }
}
