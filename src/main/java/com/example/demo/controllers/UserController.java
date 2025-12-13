package com.example.demo.controllers;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

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

import com.example.demo.dto.ApiResponse;
import com.example.demo.models.UserModel;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserModel>>> findAll() {
        return ResponseEntity.ok(ApiResponse.success(userService.findAll()));
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<UserModel>> saveUser(@RequestBody UserModel user) {
        try {
            UserModel savedUser = userService.saveUser(user);
            return ResponseEntity.ok(ApiResponse.success("Usuario guardado exitosamente", savedUser));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.badRequest(e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(ApiResponse.badRequest("El usuario ya existe"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.serverError("Error al guardar el usuario: " + e.getMessage()));
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<String>> deleteById(@PathVariable("id") Long id) {
        boolean ok = userService.deleteUser(id);
        if (ok) {
            return ResponseEntity.ok(ApiResponse.success("Usuario eliminado exitosamente", "ID: " + id));
        } else {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.badRequest("No se pudo eliminar el usuario con id " + id));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getByEmail(@PathVariable("email") String email) {
        try {
            UserModel user = userService.findByEmail(email);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("id", user.getId());
            responseData.put("email", user.getEmail());
            responseData.put("address", user.getAddress());
            return ResponseEntity.ok(ApiResponse.success(responseData));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.badRequest(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.serverError("Error al obtener el usuario: " + e.getMessage()));
        }
    }
}
