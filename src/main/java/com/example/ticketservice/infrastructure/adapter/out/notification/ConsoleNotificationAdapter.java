package com.example.ticketservice.infrastructure.adapter.out.notification;

import com.example.ticketservice.application.port.out.NotificationPort;
import org.springframework.stereotype.Component;

// Adaptador de Notificación (Output Adapter). Simula un proceso externo.
@Component
public class ConsoleNotificationAdapter implements NotificationPort {
    
    @Override
    public void notificarTicketCreado(String ticketId, String descripcion) {
        System.out.println("\n=======================================================");
        System.out.println("[\uD83D\uDCE9 SIMULACIÓN DE NOTIFICACIÓN WHATSAPP / EMAIL]");
        System.out.println("=======================================================");
        System.out.println("Destinatario: Sistema de Gestión de Alertas / Usuario");
        System.out.println("Mensaje: Se ha registrado un nuevo ticket en el sistema.");
        System.out.println("ID del Ticket: " + ticketId);
        System.out.println("Descripción: " + descripcion);
        System.out.println("=======================================================\n");
    }
}
