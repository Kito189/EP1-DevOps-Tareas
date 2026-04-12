package com.example.ticketservice.infrastructure.adapter.out.persistence.entity;

import com.example.ticketservice.domain.TicketStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class TicketEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus estado;

    // Constructores, getters y setters requeridos por JPA
    public TicketEntity() {
    }

    public TicketEntity(String id, String descripcion, TicketStatus estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TicketStatus getEstado() {
        return estado;
    }

    public void setEstado(TicketStatus estado) {
        this.estado = estado;
    }
}
