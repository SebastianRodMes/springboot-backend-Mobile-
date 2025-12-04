package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.models.UserModel;
import com.example.demo.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request){
        try{
            UserModel user = authService.signup(request);
            return ResponseEntity.ok("Usuario registrado exitosamente: " + user.getEmail());

        }catch(IllegalArgumentException e){
             return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            // Cualquier otro error
            return ResponseEntity.status(500).body("Error al registrar usuario: " + e.getMessage());
        }
    
    }


    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest request){
        try{
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(Exception e){
           return ResponseEntity.status(500).body("Error al iniciar sesión: " + e.getMessage());
        }
    }

    
}
