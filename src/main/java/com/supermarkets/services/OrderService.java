package com.supermarkets.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.supermarkets.model.Order;

/**
 * The Order services interface
 * @author mehri
 *
 */
@Service
public interface OrderService {

	/**
	 * add new order
	 * @param order the new order
	 * @return created {@link Order}
	 */
	public Order addOrder(Order order);
	
	/**
	 * get an order by id
	 * @param orderId the {@link Order}
	 * @return the {@link Order}
	 */
	public Optional<Order> getOrder(Long orderId);
	
	/**
	 * Delete an Order
	 * @param order the order
	 */
	public void deleteOrder(Order order);
}
