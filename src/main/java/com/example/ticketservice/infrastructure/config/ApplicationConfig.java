package com.example.ticketservice.infrastructure.config;

import com.example.ticketservice.application.port.out.NotificationPort;
import com.example.ticketservice.application.port.out.TicketRepositoryPort;
import com.example.ticketservice.application.service.TicketService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Configuración de Spring para inyectar las dependencias en nuestro servicio de aplicación
@Configuration
public class ApplicationConfig {

    @Bean
    public TicketService ticketService(TicketRepositoryPort repository, NotificationPort notificationPort) {
        return new TicketService(repository, notificationPort);
    }
}
