package com.example.ticketservice.infrastructure.adapter.out.persistence;

import com.example.ticketservice.infrastructure.adapter.out.persistence.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataTicketRepository extends JpaRepository<TicketEntity, String> {
}
