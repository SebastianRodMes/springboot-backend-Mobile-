package com.example.demo.repositories;
import com.example.demo.enums.TicketStatus;
import com.example.demo.models.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface TicketRepository extends JpaRepository<TicketModel, Long> {
    
    // Buscar todos los tickets de un usuario
    List<TicketModel> findByUserId(Long userId);
    
    // Buscar tickets por estado
    List<TicketModel> findByStatus(TicketStatus status);
    
    // Buscar tickets de un usuario por estado
    List<TicketModel> findByUserIdAndStatus(Long userId, TicketStatus status);
    
    // Buscar tickets asignados a un agente
    List<TicketModel> findByAssignedAgentId(String agentId);
    
    // Buscar tickets asignados a un técnico
    List<TicketModel> findByAssignedTechnicianId(String technicianId);
}