package com.example.shop.service;

import com.example.shop.entity.Order;
import com.example.shop.entity.OrderItem;
import com.example.shop.entity.Product;
import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    // Récupérer toutes les commandes (Réservé ADMIN)
    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    // Récupérer les commandes d'un utilisateur spécifique (Réservé USER)
    public List<Order> findByUsername(String username) {
        return orderRepo.findByUsername(username);
    }

    // Créer une commande à partir d'une liste d'IDs de produits et d'un username
    @Transactional
    public Order createOrder(String username, List<Long> productIds) {
        Order order = new Order();
        order.setUsername(username); // Assure-toi que ton entité Order possède un champ username

        for (Long productId : productIds) {
            Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(1); // Quantité par défaut à 1 selon l'OrderRequest simple
            item.setOrder(order);
            order.getItems().add(item);
        }

        return orderRepo.save(order);
    }

    // Supprimer une commande par son ID (Réservé ADMIN)
    @Transactional
    public void delete(Long id) {
        if (!orderRepo.existsById(id)) {
            throw new EntityNotFoundException("Order not found with id: " + id);
        }
        orderRepo.deleteById(id);
    }
}