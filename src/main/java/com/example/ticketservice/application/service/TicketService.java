package com.example.ticketservice.application.service;

import com.example.ticketservice.application.port.in.TicketUseCase;
import com.example.ticketservice.application.port.out.NotificationPort;
import com.example.ticketservice.application.port.out.TicketRepositoryPort;
import com.example.ticketservice.domain.Ticket;
import com.example.ticketservice.domain.factory.TicketFactory;

public class TicketService implements TicketUseCase {

    private final TicketRepositoryPort ticketRepository;
    private final NotificationPort notificationPort;

    public TicketService(TicketRepositoryPort ticketRepository, NotificationPort notificationPort) {
        this.ticketRepository = ticketRepository;
        this.notificationPort = notificationPort;
    }

    @Override
    public Ticket registrarTicket(String descripcion, boolean consentimiento) {

        com.example.ticketservice.domain.EthicalValidator.validate(descripcion, consentimiento);

        Ticket ticket = TicketFactory.createWithDefaults(descripcion);

        Ticket savedTicket = ticketRepository.save(ticket);

        notificationPort.notificarTicketCreado(savedTicket.getId(), savedTicket.getDescripcion());

        return savedTicket;
    }

    @Override
    public Ticket consultarEstadoTicket(String id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con el ID: " + id));
    }
}
