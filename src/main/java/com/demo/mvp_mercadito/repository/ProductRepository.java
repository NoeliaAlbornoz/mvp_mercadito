package com.demo.mvp_mercadito.repository;

import com.demo.mvp_mercadito.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
