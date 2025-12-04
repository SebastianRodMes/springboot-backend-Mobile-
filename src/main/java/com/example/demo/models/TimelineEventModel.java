package com.example.demo.models;
import com.example.demo.enums.ActorRole;
import com.example.demo.enums.TicketStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "timeline_event")
public class TimelineEventModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Relación con el ticket (muchos eventos pertenecen a un ticket)
    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private TicketModel ticket;
    // Estado del ticket en este evento (puede ser null)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    // ID del actor (userId, agentId, "system", etc.)
    private String actorId;
    // Nombre del actor
    private String actorName;
    // Rol del actor
    @Enumerated(EnumType.STRING)
    private ActorRole actorRole;
    // Mensaje del evento
    @Column(length = 1000)
    private String message;
    // URLs de archivos adjuntos (fotos, documentos)
    @ElementCollection
    @CollectionTable(name = "timeline_attachments", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "attachment_url")
    private List<String> attachments;
    // Timestamp del evento
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
    // Auto-generar timestamp al crear
    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public TicketModel getTicket() {
        return ticket;
    }
    public void setTicket(TicketModel ticket) {
        this.ticket = ticket;
    }
    public TicketStatus getStatus() {
        return status;
    }
    public void setStatus(TicketStatus status) {
        this.status = status;
    }
    public String getActorId() {
        return actorId;
    }
    public void setActorId(String actorId) {
        this.actorId = actorId;
    }
    public String getActorName() {
        return actorName;
    }
    public void setActorName(String actorName) {
        this.actorName = actorName;
    }
    public ActorRole getActorRole() {
        return actorRole;
    }
    public void setActorRole(ActorRole actorRole) {
        this.actorRole = actorRole;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List<String> getAttachments() {
        return attachments;
    }
    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}