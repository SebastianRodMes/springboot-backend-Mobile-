package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserModel> findAll() {
        return (List<UserModel>) userRepository.findAll(); // se hace un casteo a List<UserModel>
    }

    public UserModel saveUser(UserModel user) {
       
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("El email ya existe xd");
        }
        return userRepository.save(user);
    }   
    
    public boolean deleteUser(Long id) {
       try {
           userRepository.deleteById(id);
           return true;
       } catch (Exception e) {
           return false;
       }
    }

}
