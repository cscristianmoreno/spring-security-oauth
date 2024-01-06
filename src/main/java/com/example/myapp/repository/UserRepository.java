package com.example.myapp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.myapp.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);   
}
