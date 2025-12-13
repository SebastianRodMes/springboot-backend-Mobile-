package com.example.demo.controllers;

import com.example.demo.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<TicketModel>> createTicket(@RequestBody TicketModel ticket,
            @RequestParam Long userId) {
        try {
            TicketModel createdTicket = ticketService.createTicket(ticket, userId);
            return ResponseEntity.ok(ApiResponse.success("Ticket creado exitosamente", createdTicket));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.badRequest(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.serverError("Error al crear ticket: " + e.getMessage()));
        }
    }

    // Obtener todos los tickets
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<TicketModel>>> getAllTickets() {
        return ResponseEntity.ok(ApiResponse.success(ticketService.getAllTickets()));
    }

    // Obtener un ticket por ID
    @GetMapping("/{ticketId}")
    public ResponseEntity<ApiResponse<TicketModel>> getTicketById(@PathVariable Long ticketId) {
        try {
            TicketModel ticket = ticketService.getTicketById(ticketId);
            return ResponseEntity.ok(ApiResponse.success(ticket));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.badRequest(e.getMessage()));
        }
    }

    // Obtener tickets de un usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<TicketModel>>> getTicketsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(ticketService.getTicketsByUserId(userId)));
    }

    // Obtener tickets por estado
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<TicketModel>>> getTicketsByStatus(@PathVariable TicketStatus status) {
        return ResponseEntity.ok(ApiResponse.success(ticketService.getTicketsByStatus(status)));
    }

    // Actualizar el estado de un ticket
    @PutMapping("/{ticketId}/status")
    public ResponseEntity<ApiResponse<TicketModel>> updateTicketStatus(@PathVariable Long ticketId,
            @RequestParam TicketStatus status) {
        try {
            TicketModel updatedTicket = ticketService.updateTicketStatus(ticketId, status);
            return ResponseEntity.ok(ApiResponse.success("Estado actualizado exitosamente", updatedTicket));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.badRequest(e.getMessage()));
        }
    }

    // Asignar un agente
    @PutMapping("/{ticketId}/assign-agent")
    public ResponseEntity<ApiResponse<TicketModel>> assignAgent(@PathVariable Long ticketId,
            @RequestParam String agentId) {
        try {
            TicketModel updatedTicket = ticketService.assignAgent(ticketId, agentId);
            return ResponseEntity.ok(ApiResponse.success("Agente asignado exitosamente", updatedTicket));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.badRequest(e.getMessage()));
        }
    }

    // Asignar un técnico
    @PutMapping("/{ticketId}/assign-technician")
    public ResponseEntity<ApiResponse<TicketModel>> assignTechnician(@PathVariable Long ticketId,
            @RequestParam String technicianId) {
        try {
            TicketModel updatedTicket = ticketService.assignTechnician(ticketId, technicianId);
            return ResponseEntity.ok(ApiResponse.success("Técnico asignado exitosamente", updatedTicket));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.badRequest(e.getMessage()));
        }
    }

    // Calificar un ticket
    @PutMapping("/{ticketId}/rate")
    public ResponseEntity<ApiResponse<TicketModel>> rateTicket(@PathVariable Long ticketId,
            @RequestParam Integer rating) {
        try {
            TicketModel updatedTicket = ticketService.rateTicket(ticketId, rating);
            return ResponseEntity.ok(ApiResponse.success("Ticket calificado exitosamente", updatedTicket));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.badRequest(e.getMessage()));
        }
    }

    // Eliminar un ticket
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<ApiResponse<String>> deleteTicket(@PathVariable Long ticketId) {
        try {
            ticketService.deleteTicket(ticketId);
            return ResponseEntity.ok(ApiResponse.success("Ticket eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.serverError("Error al eliminar ticket: " + e.getMessage()));
        }
    }

    // Actualizar detalles del ticket
    @PutMapping("/{ticketId}/details")
    public ResponseEntity<ApiResponse<TicketModel>> updateTicketDetails(
            @PathVariable Long ticketId,
            @RequestBody TicketModel ticketUpdates) {
        try {
            TicketModel updatedTicket = ticketService.updateTicketDetails(
                    ticketId,
                    ticketUpdates.getDescription(),
                    ticketUpdates.getPhotoUrl(),
                    ticketUpdates.getSubcategory());
            return ResponseEntity.ok(ApiResponse.success("Ticket actualizado exitosamente", updatedTicket));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.badRequest(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.serverError("Error al actualizar ticket: " + e.getMessage()));
        }
    }
}