package com.supermarkets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supermarkets.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
