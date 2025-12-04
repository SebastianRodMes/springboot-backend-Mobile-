package com.example.demo.controllers;
import com.example.demo.enums.TicketStatus;
import com.example.demo.models.TicketModel;
import com.example.demo.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/tickets")
public class TicketController {
    
    @Autowired
    private TicketService ticketService;
    
    // Crear un nuevo ticket
    @PostMapping("/create")
    public ResponseEntity<?> createTicket(@RequestBody TicketModel ticket, @RequestParam Long userId) {
        try {
            TicketModel createdTicket = ticketService.createTicket(ticket, userId);
            return ResponseEntity.ok(createdTicket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear ticket: " + e.getMessage());
        }
    }
    
    // Obtener todos los tickets
    @GetMapping("/all")
    public ResponseEntity<List<TicketModel>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }
    
    // Obtener un ticket por ID
    @GetMapping("/{ticketId}")
    public ResponseEntity<?> getTicketById(@PathVariable Long ticketId) {
        try {
            TicketModel ticket = ticketService.getTicketById(ticketId);
            return ResponseEntity.ok(ticket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Obtener tickets de un usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketModel>> getTicketsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(ticketService.getTicketsByUserId(userId));
    }
    
    // Obtener tickets por estado
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TicketModel>> getTicketsByStatus(@PathVariable TicketStatus status) {
        return ResponseEntity.ok(ticketService.getTicketsByStatus(status));
    }
    
    // Actualizar el estado de un ticket
    @PutMapping("/{ticketId}/status")
    public ResponseEntity<?> updateTicketStatus(@PathVariable Long ticketId, @RequestParam TicketStatus status) {
        try {
            TicketModel updatedTicket = ticketService.updateTicketStatus(ticketId, status);
            return ResponseEntity.ok(updatedTicket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Asignar un agente
    @PutMapping("/{ticketId}/assign-agent")
    public ResponseEntity<?> assignAgent(@PathVariable Long ticketId, @RequestParam String agentId) {
        try {
            TicketModel updatedTicket = ticketService.assignAgent(ticketId, agentId);
            return ResponseEntity.ok(updatedTicket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Asignar un técnico
    @PutMapping("/{ticketId}/assign-technician")
    public ResponseEntity<?> assignTechnician(@PathVariable Long ticketId, @RequestParam String technicianId) {
        try {
            TicketModel updatedTicket = ticketService.assignTechnician(ticketId, technicianId);
            return ResponseEntity.ok(updatedTicket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Calificar un ticket
    @PutMapping("/{ticketId}/rate")
    public ResponseEntity<?> rateTicket(@PathVariable Long ticketId, @RequestParam Integer rating) {
        try {
            TicketModel updatedTicket = ticketService.rateTicket(ticketId, rating);
            return ResponseEntity.ok(updatedTicket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Eliminar un ticket
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long ticketId) {
        try {
            ticketService.deleteTicket(ticketId);
            return ResponseEntity.ok("Ticket eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar ticket: " + e.getMessage());
        }
    }
}