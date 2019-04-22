package com.supermarkets.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarkets.exceptions.ProductException;
import com.supermarkets.model.Product;
import com.supermarkets.repository.ProductRepository;
import com.supermarkets.services.ProductService;

/**
 * An implementation of {@link ProductService}
 * 
 * @author mehri
 *
 */
@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product addProduct(Product product) throws ProductException {
		return productRepository.save(product);
	}


	@Override
	public Optional<Product> getProduct(Long productId) {
		return productRepository.findById(productId);
	}

	@Override
	public void deleteProduct(Long productId) throws ProductException {
		Optional<Product> product = productRepository.findById(productId);
		if (!product.isPresent()) {
			throw new ProductException("The product does not exist");
		}
		productRepository.deleteById(productId);
	}

}
