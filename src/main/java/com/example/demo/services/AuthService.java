package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;
import com.example.demo.util.JwtUtil;

@Service
public class AuthService {
    // Definicion de atributos que vamios a usar
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    // esto para incriptar la contraseña
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserModel signup(SignUpRequest request) {
        // validamos que el email no exista, con el metodo del userRepository
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        // luego creamos el UserModel apartir del DTO
        UserModel user = new UserModel();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setContractNumber(request.getContractNumber());

        // hasheamos la contraseña
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // guardamos en la bd
        return userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        UserModel user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email o contraseña incorrectos"));
        // verificar contraseña
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Email o contraseña incorrectos");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());

        return new AuthResponse(token, user.getEmail(), user.getFullName());
    }

}
