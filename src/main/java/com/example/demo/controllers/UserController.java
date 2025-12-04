package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.UserModel;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/user") // REDIRECCIONA LA RUTA HACIA ESE /user
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all") // Mapea la ruta /all y es de tipo GET
    public List<UserModel> findAll() {
        return userService.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody UserModel user) {
        try {
            UserModel savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException e) {
            // Errores de validación (DNI/email duplicado)
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        } catch (Exception e) {
            // Mostrar el error real para debugging
            return ResponseEntity.status(500).body("Error al guardar el usuario: " + e.getMessage());
        }
    }
    
    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable("id") Long id){
        boolean ok = userService.deleteUser(id);
        if (ok) {
            return "Se eliminó el usuario con id" + id;
        } else {
            return "No se pudo eliminar el usuario con id" + id;
        }
    }

}
