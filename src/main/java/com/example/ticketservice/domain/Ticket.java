package com.example.ticketservice.domain;


public class Ticket {
    private String id;
    private String descripcion;
    private TicketStatus estado;

    public Ticket(String descripcion) {
        this.descripcion = descripcion;
        this.estado = TicketStatus.CREADO;
    }

    public Ticket(String id, String descripcion, TicketStatus estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public String getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public TicketStatus getEstado() { return estado; }
    
    public void setEstado(TicketStatus estado) { 
        this.estado = estado; 
    }
}
