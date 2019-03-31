package com.supermarkets.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.supermarkets.exceptions.ProductException;
import com.supermarkets.model.Price;
import com.supermarkets.model.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

	@Autowired
	ProductService productService;
	
	@Test
	public void should_add_product() throws ProductException {
		
		//Given
		String expectedName = "name";
		Price expectedPrice = new Price();
		expectedPrice.setUnitPrice(BigDecimal.valueOf(1.99));
		expectedPrice.setDiscount(BigDecimal.valueOf(50.00));
		expectedPrice.setQuantity(1);
		
		Product product = new Product();
		product.setName(expectedName);
		product.setPrices(Arrays.asList(expectedPrice));
		
		// when
		Product newProduct = productService.addProduct(product);
		
		//then
		assertEquals(expectedName, newProduct.getName());
		assertNotNull(product.getPrices());
		
		Price price = newProduct.getPrices().get(0);
		assertEquals(expectedPrice.getDiscount(), price.getDiscount());
		assertEquals(expectedPrice.getQuantity(), price.getQuantity());
		assertEquals(expectedPrice.getUnitPrice(), price.getUnitPrice());
	}
	
	@Test
	public void should_add_product_with_many_prices() throws ProductException {
		
		//Given
		String expectedName = "name";
		Price expectedPriceWithDiscount = new Price();
		expectedPriceWithDiscount.setUnitPrice(BigDecimal.valueOf(1.99));
		expectedPriceWithDiscount.setDiscount(BigDecimal.valueOf(50.00));
		expectedPriceWithDiscount.setQuantity(1);
		
		Price expectedPrice = new Price();
		expectedPrice.setUnitPrice(BigDecimal.valueOf(0.99));
		expectedPrice.setDiscount(BigDecimal.valueOf(0.00));
		expectedPrice.setQuantity(1);
		
		Price expectedPriceForManyProducts = new Price();
		expectedPriceForManyProducts.setUnitPrice(BigDecimal.valueOf(2.99));
		expectedPriceForManyProducts.setDiscount(BigDecimal.valueOf(0.00));
		expectedPriceForManyProducts.setQuantity(3);
		
		Product product = new Product();
		product.setName(expectedName);
		product.setPrices(Arrays.asList(expectedPrice, expectedPriceForManyProducts, expectedPriceWithDiscount));
		
		// when
		Product newProduct = productService.addProduct(product);
		
		//then
		assertEquals(expectedName, newProduct.getName());
		assertNotNull(product.getPrices());
		assertEquals(3, product.getPrices().size());
	}
	
	@Test
	public void should_return_product_by_id() throws ProductException {
		
		//Given
		String expectedName = "name";
		Price expectedPrice = new Price();
		expectedPrice.setUnitPrice(BigDecimal.valueOf(1.99));
		expectedPrice.setDiscount(BigDecimal.valueOf(50.00));
		expectedPrice.setQuantity(1);
		
		Product expectedProduct = new Product();
		expectedProduct.setName(expectedName);
		expectedProduct.setPrices(Arrays.asList(expectedPrice));
		Product newProduct = productService.addProduct(expectedProduct);
		
		// when
		Optional<Product> optionalProduct = productService.getProduct(newProduct.getId());
		
		//then
		assertTrue(optionalProduct.isPresent());
		Product product = optionalProduct.get();
		assertEquals(newProduct.getId(), product.getId());
	}
	
	@Test
	public void should_delete_product() throws ProductException {
		
		//Given
		String expectedName = "name";
		Price expectedPrice = new Price();
		expectedPrice.setUnitPrice(BigDecimal.valueOf(1.99));
		expectedPrice.setDiscount(BigDecimal.valueOf(50.00));
		expectedPrice.setQuantity(1);
		
		Product expectedProduct = new Product();
		expectedProduct.setName(expectedName);
		expectedProduct.setPrices(Arrays.asList(expectedPrice));
		Product newProduct = productService.addProduct(expectedProduct);
		
		// when
		productService.deleteProduct(newProduct.getId());
		
		//then
		Optional<Product> optionalProduct = productService.getProduct(newProduct.getId());
		assertFalse(optionalProduct.isPresent());
	}
	
	@Test(expected = ProductException.class)
	public void should_throw_exception_delete_product() throws ProductException {
		
		//Given
		Long incorrectProductId = 22L;
		
		// when
		productService.deleteProduct(incorrectProductId);
	}
}
