package com.demo.mvp_mercadito.service;

import com.demo.mvp_mercadito.model.Cart;
import com.demo.mvp_mercadito.model.CartItem;
import com.demo.mvp_mercadito.model.Product;
import com.demo.mvp_mercadito.model.User;
import com.demo.mvp_mercadito.model.request.CartRequest;
import com.demo.mvp_mercadito.repository.CartRepository;
import com.demo.mvp_mercadito.repository.ProductRepository;
import com.demo.mvp_mercadito.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Cart addItemToCart(CartRequest cartRequest) {
        User user = userRepository.findById(cartRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar o crear el carrito del usuario
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        // Buscar el producto y validar el stock
        Product product = productRepository.findById(cartRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (product.getStock() < cartRequest.getQuantity()) {
            throw new RuntimeException("Stock insuficiente para el producto");
        }

        // Buscar si el producto ya está en el carrito
        Optional<CartItem> existingCartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(cartRequest.getProductId()))
                .findFirst();

        if (existingCartItem.isPresent()) {
            // Si ya existe, actualizar la cantidad
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartRequest.getQuantity());
        } else {
            // Si no existe, crear un nuevo CartItem
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(cartRequest.getQuantity());
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
        }

        // Actualizar el stock del producto
        product.setStock(product.getStock() - cartRequest.getQuantity());
        productRepository.save(product);

        return cartRepository.save(cart);
    }
}
