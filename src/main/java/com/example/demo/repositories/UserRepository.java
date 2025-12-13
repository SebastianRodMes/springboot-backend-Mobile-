package com.example.demo.repositories;

import com.example.demo.models.UserModel;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//TIPO DE DATO DEL ID LONG
@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {

    

    boolean existsByEmail(String email);

    // ¿Por qué Optional?
    // Porque el usuario puede no existir
    // Evita null y es más seguro
    Optional<UserModel> findByEmail(String email);

     Optional<UserModel> findAddressByEmail(String email);

}
