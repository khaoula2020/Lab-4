package com.example.shop.repository;

import com.example.shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // <--- Pense bien à importer List

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Ajoute cette ligne ici :
    List<Order> findByUsername(String username);

}