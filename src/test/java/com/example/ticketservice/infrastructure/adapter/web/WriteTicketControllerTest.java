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
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Infrastructure - WriteTicketController Tests")
class WriteTicketControllerTest {

    @Mock
    private TicketUseCase ticketUseCase;

    @InjectMocks
    private WriteTicketController writeTicketController;

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = TicketFactory.createWithDefaults("Test description");
    }

    @Test
    @DisplayName("Debe registrar un ticket correctamente")
    void testRegistrarTicket() {
        when(ticketUseCase.registrarTicket(anyString(), anyBoolean())).thenReturn(ticket);

        WriteTicketController.TicketRequest request = 
            new WriteTicketController.TicketRequest("Test description", true);

        ResponseEntity<?> response = writeTicketController.registrarTicket(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Debe retornar 400 sin consentimiento")
    void testRegistrarTicketSinConsentimiento() {
        when(ticketUseCase.registrarTicket(anyString(), anyBoolean()))
                .thenThrow(new IllegalArgumentException("ERROR ÉTICO"));

        WriteTicketController.TicketRequest request = 
            new WriteTicketController.TicketRequest("Test", false);

        ResponseEntity<?> response = writeTicketController.registrarTicket(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("Debe manejar excepciones internas")
    void testRegistrarTicketConError() {
        when(ticketUseCase.registrarTicket(anyString(), anyBoolean()))
                .thenThrow(new RuntimeException("Error interno"));

        WriteTicketController.TicketRequest request = 
            new WriteTicketController.TicketRequest("Test", true);

        ResponseEntity<?> response = writeTicketController.registrarTicket(request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
