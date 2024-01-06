package com.example.myapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.myapp.models.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    
}
