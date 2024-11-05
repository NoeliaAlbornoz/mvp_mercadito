package com.demo.mvp_mercadito.controller;

import com.demo.mvp_mercadito.model.Cart;
import com.demo.mvp_mercadito.model.request.CartRequest;
import com.demo.mvp_mercadito.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartRequest cartRequest) {
        Cart cart = cartService.addItemToCart(cartRequest);
        return ResponseEntity.ok(cart);
    }
}


