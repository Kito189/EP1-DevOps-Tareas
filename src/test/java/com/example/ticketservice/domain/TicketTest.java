package com.example.ticketservice.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Domain - Ticket Tests")
class TicketTest {

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = new Ticket("Reparar fuga de agua");
    }

    @Test
    @DisplayName("Debe crear un ticket con estado CREADO")
    void testTicketCreation() {
        assertNotNull(ticket);
        assertEquals(TicketStatus.CREADO, ticket.getEstado());
    }

    @Test
    @DisplayName("Debe cambiar estado a EN_PROGRESO")
    void testChangeStatusToEnProgreso() {
        ticket.setEstado(TicketStatus.EN_PROGRESO);
        assertEquals(TicketStatus.EN_PROGRESO, ticket.getEstado());
    }

    @Test
    @DisplayName("Debe cambiar estado a RESUELTO")
    void testChangeStatusToResuelto() {
        ticket.setEstado(TicketStatus.RESUELTO);
        assertEquals(TicketStatus.RESUELTO, ticket.getEstado());
    }

    @Test
    @DisplayName("Debe obtener la descripción correctamente")
    void testGetDescripcion() {
        assertEquals("Reparar fuga de agua", ticket.getDescripcion());
    }

    @Test
    @DisplayName("Debe obtener el ID")
    void testGetId() {
        assertNull(ticket.getId());
    }
}
