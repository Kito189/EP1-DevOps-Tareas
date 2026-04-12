package com.example.ticketservice.application.port.out;

public interface NotificationPort {
    void notificarTicketCreado(String ticketId, String descripcion);
}
