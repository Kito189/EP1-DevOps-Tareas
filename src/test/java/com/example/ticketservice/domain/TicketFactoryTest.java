package com.example.ticketservice.domain;

import com.example.ticketservice.domain.factory.TicketFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Domain - TicketFactory Tests")
class TicketFactoryTest {

    @Test
    @DisplayName("Debe crear un ticket válido con descripción")
    void testCrearTicketValido() {
        Ticket ticket = TicketFactory.createWithDefaults("Test descripción");
        
        assertNotNull(ticket);
        assertEquals("Test descripción", ticket.getDescripcion());
        assertEquals(TicketStatus.CREADO, ticket.getEstado());
        assertNotNull(ticket.getId());
    }

    @Test
    @DisplayName("Debe generar un ID único para cada ticket")
    void testGenerateUniqueId() {
        Ticket ticket1 = TicketFactory.createWithDefaults("Descripción 1");
        Ticket ticket2 = TicketFactory.createWithDefaults("Descripción 2");
        
        assertNotEquals(ticket1.getId(), ticket2.getId());
    }

    @Test
    @DisplayName("Debe crear ticket con descripción válida")
    void testCrearTicketConDescripcionValida() {
        Ticket ticket = TicketFactory.createWithDefaults("Reparación urgente");
        assertNotNull(ticket.getDescripcion());
        assertFalse(ticket.getDescripcion().isEmpty());
    }

    @Test
    @DisplayName("Debe crear ticket con estado inicial CREADO")
    void testCrearTicketConEstadoInicial() {
        Ticket ticket = TicketFactory.createWithDefaults("Test");
        assertEquals(TicketStatus.CREADO, ticket.getEstado());
    }
}
