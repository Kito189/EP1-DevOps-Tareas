package com.example.ticketservice.application.service;

import com.example.ticketservice.domain.Ticket;
import com.example.ticketservice.domain.factory.TicketFactory;
import com.example.ticketservice.application.port.out.NotificationPort;
import com.example.ticketservice.application.port.out.TicketRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Application - TicketService Tests")
class TicketServiceTest {

    @Mock
    private TicketRepositoryPort ticketRepository;

    @Mock
    private NotificationPort notificationPort;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        // Reset mocks before each test
        reset(ticketRepository, notificationPort);
    }

    @Test
    @DisplayName("Debe registrar un nuevo ticket")
    void testRegistrarTicket() {
        Ticket ticketARegistrar = TicketFactory.createWithDefaults("Nueva reparación");
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticketARegistrar);

        Ticket resultado = ticketService.registrarTicket("Nueva reparación", true);

        assertNotNull(resultado);
        assertEquals("Nueva reparación", resultado.getDescripcion());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
        verify(notificationPort, times(1)).notificarTicketCreado(anyString(), anyString());
    }

    @Test
    @DisplayName("Debe consultar un ticket por ID")
    void testConsultarTicket() {
        Ticket ticket = TicketFactory.createWithDefaults("Test");
        when(ticketRepository.findById("1")).thenReturn(Optional.of(ticket));

        Ticket resultado = ticketService.consultarEstadoTicket("1");

        assertNotNull(resultado);
        assertEquals(ticket.getDescripcion(), resultado.getDescripcion());
        verify(ticketRepository, times(1)).findById("1");
    }

    @Test
    @DisplayName("Debe lanzar excepción si el ticket no existe")
    void testConsultarTicketNoExistente() {
        when(ticketRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> ticketService.consultarEstadoTicket("999"));
        verify(ticketRepository, times(1)).findById("999");
    }

    @Test
    @DisplayName("Debe notificar después de registrar ticket")
    void testNotificacionAlRegistrar() {
        Ticket ticket = TicketFactory.createWithDefaults("Test");
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        ticketService.registrarTicket("Test", true);

        verify(notificationPort, times(1)).notificarTicketCreado(anyString(), anyString());
    }

    @Test
    @DisplayName("Debe validar el consentimiento")
    void testValidarConsentimiento() {
        assertThrows(IllegalArgumentException.class, () -> 
            ticketService.registrarTicket("Sin consentimiento", false)
        );
    }

    @Test
    @DisplayName("Debe rechazar descripciones con lenguaje no permitido")
    void testRechazarLenguajeNoPermitido() {
        assertThrows(IllegalArgumentException.class, () ->
            ticketService.registrarTicket("Esto es un insulto", true)
        );
    }
}
