package com.example.demo.services;

import com.example.demo.enums.TicketStatus;
import com.example.demo.models.TicketModel;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.TicketRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    // Crear un nuevo ticket
    public TicketModel createTicket(TicketModel ticket, Long userId) {
        // Buscar el usuario
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Asignar el usuario al ticket
        ticket.setUser(user);

        // Guardar el ticket
        return ticketRepository.save(ticket);
    }

    // Obtener todos los tickets
    public List<TicketModel> getAllTickets() {
        return ticketRepository.findAll();
    }

    // Obtener un ticket por ID
    public TicketModel getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket no encontrado"));
    }

    // Obtener tickets de un usuario
    public List<TicketModel> getTicketsByUserId(Long userId) {
        return ticketRepository.findByUserId(userId);
    }

    // Obtener tickets por estado
    public List<TicketModel> getTicketsByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status);
    }

    // Actualizar el estado de un ticket
    public TicketModel updateTicketStatus(Long ticketId, TicketStatus newStatus) {
        TicketModel ticket = getTicketById(ticketId);
        ticket.setStatus(newStatus);

        // Si se cierra, guardar la fecha de cierre
        if (newStatus == TicketStatus.CLOSED || newStatus == TicketStatus.CANCELLED) {
            ticket.setClosedAt(java.time.LocalDateTime.now());
        }

        return ticketRepository.save(ticket);
    }

    // Asignar un agente a un ticket
    public TicketModel assignAgent(Long ticketId, String agentId) {
        TicketModel ticket = getTicketById(ticketId);
        ticket.setAssignedAgentId(agentId);
        ticket.setStatus(TicketStatus.ASSIGNED);
        return ticketRepository.save(ticket);
    }

    // Asignar un técnico a un ticket
    public TicketModel assignTechnician(Long ticketId, String technicianId) {
        TicketModel ticket = getTicketById(ticketId);
        ticket.setAssignedTechnicianId(technicianId);
        return ticketRepository.save(ticket);
    }

    // Calificar un ticket
    public TicketModel rateTicket(Long ticketId, Integer rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }

        TicketModel ticket = getTicketById(ticketId);

        if (ticket.getStatus() != TicketStatus.CLOSED) {
            throw new IllegalArgumentException("Solo se pueden calificar tickets cerrados");
        }

        ticket.setRating(rating);
        return ticketRepository.save(ticket);
    }

    // Eliminar un ticket
    public void deleteTicket(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    // Actualizar detalles del ticket (descripción, foto, subcategoría)
    public TicketModel updateTicketDetails(Long ticketId, String description, String photoUrl, String subcategory) {
        TicketModel ticket = getTicketById(ticketId);

        if (description != null && !description.isEmpty()) {
            ticket.setDescription(description);
        }

        if (photoUrl != null && !photoUrl.isEmpty()) {
            ticket.setPhotoUrl(photoUrl);
        }

        if (subcategory != null && !subcategory.isEmpty()) {
            ticket.setSubcategory(subcategory);
        }

        return ticketRepository.save(ticket);
    }
}