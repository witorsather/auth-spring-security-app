package com.example.authspringsecurityapp.repositories;

import com.example.authspringsecurityapp.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}
