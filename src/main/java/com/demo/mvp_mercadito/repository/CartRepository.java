package com.demo.mvp_mercadito.repository;

import com.demo.mvp_mercadito.model.Cart;
import com.demo.mvp_mercadito.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
