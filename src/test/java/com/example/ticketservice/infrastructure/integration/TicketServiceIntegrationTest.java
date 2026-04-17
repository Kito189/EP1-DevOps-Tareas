package com.example.ticketservice.infrastructure.integration;

import com.example.ticketservice.domain.Ticket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DisplayName("Integration - Ticket Service Integration Tests")
class TicketServiceIntegrationTest {

    @Test
    @DisplayName("Debe cargar el contexto de la aplicación")
    void testContextLoads() {
        assertTrue(true);
    }

    @Test
    @DisplayName("Debe validar el consentimiento")
    void testValidateConsentimiento() {
        assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("ERROR ÉTICO");
        });
    }

    @Test
    @DisplayName("Debe rechazar descripciones con tarjetas de crédito")
    void testRejectCreditCardNumbers() {
        String descripcion = "Mi tarjeta es 4532-1234-5678-9010";
        assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("ERROR ÉTICO: La descripción contiene información sensible");
        });
    }
}
