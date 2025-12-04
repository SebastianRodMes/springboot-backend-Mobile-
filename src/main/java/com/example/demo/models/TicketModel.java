package com.example.demo.models;
import com.example.demo.enums.Priority;
import com.example.demo.enums.TicketStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "ticket")
public class TicketModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Relación con el usuario
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;
    // Categoría y subcategoría
    private String category;
    private String subcategory;
    // Descripción del problema
    @Column(length = 2000)
    private String description;
    // Ubicación
    private String location;
    // URL de la foto (guardamos la ruta, no el Bitmap)
    private String photoUrl;
    // Estado del ticket
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.PENDING;
    // Prioridad
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority = Priority.MEDIUM;
    // IDs de agente y técnico asignados
    private String assignedAgentId;
    private String assignedTechnicianId;
    // Timestamps
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "closed_at")
    private LocalDateTime closedAt;
    // Rating (calificación al cerrar)
    private Integer rating; // 1-5 estrellas
    // Relación con timeline (un ticket tiene muchos eventos)
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimelineEventModel> timeline = new ArrayList<>();
    // Auto-generar timestamps
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    // Métodos de utilidad
    public boolean isActive() {
        return status != TicketStatus.CLOSED && status != TicketStatus.CANCELLED;
    }
    public String getFormattedId() {
        return "#" + id;
    }
    public void addTimelineEvent(TimelineEventModel event) {
        timeline.add(event);
        event.setTicket(this);
        updatedAt = LocalDateTime.now();
    }
    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public UserModel getUser() {
        return user;
    }
    public void setUser(UserModel user) {
        this.user = user;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getSubcategory() {
        return subcategory;
    }
    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    public TicketStatus getStatus() {
        return status;
    }
    public void setStatus(TicketStatus status) {
        this.status = status;
    }
    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public String getAssignedAgentId() {
        return assignedAgentId;
    }
    public void setAssignedAgentId(String assignedAgentId) {
        this.assignedAgentId = assignedAgentId;
    }
    public String getAssignedTechnicianId() {
        return assignedTechnicianId;
    }
    public void setAssignedTechnicianId(String assignedTechnicianId) {
        this.assignedTechnicianId = assignedTechnicianId;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public LocalDateTime getClosedAt() {
        return closedAt;
    }
    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public List<TimelineEventModel> getTimeline() {
        return timeline;
    }
    public void setTimeline(List<TimelineEventModel> timeline) {
        this.timeline = timeline;
    }
}