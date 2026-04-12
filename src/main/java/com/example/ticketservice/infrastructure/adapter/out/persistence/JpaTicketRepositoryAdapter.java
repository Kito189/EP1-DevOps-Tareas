package com.example.ticketservice.infrastructure.adapter.out.persistence;

import com.example.ticketservice.application.port.out.TicketRepositoryPort;
import com.example.ticketservice.domain.Ticket;
import com.example.ticketservice.infrastructure.adapter.out.persistence.entity.TicketEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Primary
public class JpaTicketRepositoryAdapter implements TicketRepositoryPort {

    private final SpringDataTicketRepository springDataTicketRepository;

    public JpaTicketRepositoryAdapter(SpringDataTicketRepository springDataTicketRepository) {
        this.springDataTicketRepository = springDataTicketRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {

        TicketEntity entity = new TicketEntity(
                ticket.getId(),
                ticket.getDescripcion(),
                ticket.getEstado());

        TicketEntity savedEntity = springDataTicketRepository.save(entity);

        return new Ticket(
                savedEntity.getId(),
                savedEntity.getDescripcion(),
                savedEntity.getEstado());
    }

    @Override
    public Optional<Ticket> findById(String id) {
        return springDataTicketRepository.findById(id)
                .map(entity -> new Ticket(
                        entity.getId(),
                        entity.getDescripcion(),
                        entity.getEstado()));
    }
}
