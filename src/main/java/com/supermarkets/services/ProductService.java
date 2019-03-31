package com.supermarkets.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.supermarkets.exceptions.ProductException;
import com.supermarkets.model.Product;

/**
 * The Product services interface
 * @author mehri
 *
 */
@Service
public interface ProductService {
	
	/**
	 * Add a {@link Product}
	 * @param product the product
	 * @throws ProductException on error
	 */
	public Product addProduct(Product product) throws ProductException;
	
	/**
	 * Get a {@link Product} by id
	 * @param productId the product id
	 * @return the {@link Product}
	 * @throws ProductException on error
	 */
	public Optional<Product> getProduct(Long productId);
	
	/**
	 * delete a product
	 * @param productId the product id
	 * @throws ProductException on error
	 */
	public void deleteProduct(Long productId) throws ProductException;

}
