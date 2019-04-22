package com.supermarkets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supermarkets.model.Order;
import com.supermarkets.model.Price;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
