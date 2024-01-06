package com.example.myapp.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.myapp.models.Product;
import com.example.myapp.repository.ProductRepository;

@Controller
@ResponseBody
@RequestMapping(value = "/products")
public class ProductController {
    
    private final ProductRepository productRepository;

    public ProductController(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/all")
    public List<Product> productsAll() {
        Iterable<Product> products = this.productRepository.findAll();
        return (List<Product>) products;
    }
}
