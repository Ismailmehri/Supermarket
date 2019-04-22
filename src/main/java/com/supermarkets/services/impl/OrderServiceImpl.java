package com.supermarkets.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarkets.model.Order;
import com.supermarkets.repository.OrderRepository;
import com.supermarkets.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public Order addOrder(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public Optional<Order> getOrder(Long orderId) {
		return orderRepository.findById(orderId);
	}

	@Override
	public void deleteOrder(Order order) {
		orderRepository.delete(order);
	}

}
