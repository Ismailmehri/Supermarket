package com.supermarkets.services;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.supermarkets.enums.PromotionType;
import com.supermarkets.exceptions.ProductException;
import com.supermarkets.model.Price;
import com.supermarkets.model.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

	@Autowired
	ProductService productService;
	
	private Product product;
	
	@Before
	public void setup() throws ProductException {
		
		//Simple Product
		String expectedName = "name";

		product = new Product();		
		product.setName(expectedName);
				
		// Product with promotion
		Price expectedPriceForManyProduct = new Price();
		expectedPriceForManyProduct.setUnitPrice(BigDecimal.valueOf(0.99));
		expectedPriceForManyProduct.setQuantity(3);
		expectedPriceForManyProduct.setPromoPrice(BigDecimal.valueOf(1.99));
		
	}
	
	@Test
	public void should_add_product() throws ProductException {
		
		//Given
		String expectedName = "name";
		Price expectedPrice = new Price();
		expectedPrice.setUnitPrice(BigDecimal.valueOf(1.99));
		expectedPrice.setDiscount(BigDecimal.valueOf(50.00));
		expectedPrice.setQuantity(1);
		expectedPrice.setPromoPrice(BigDecimal.valueOf(0.99));
		expectedPrice.setFreeProducts(3);
		
		Product product = new Product();
		product.setName(expectedName);
		product.setPrice(expectedPrice);
		product.setType(PromotionType.PRICE_FOR_MANY_PRODUCT);
		
		// when
		Product newProduct = productService.addProduct(product);
		
		//then
		assertEquals(expectedName, newProduct.getName());
		assertNotNull(product.getPrice());
				
		Price price = newProduct.getPrice();
		assertTrue(expectedPrice.getDiscount().equals(price.getDiscount()));
		assertEquals(expectedPrice.getQuantity(), price.getQuantity());
		assertEquals(expectedPrice.getUnitPrice(), price.getUnitPrice());
		assertEquals(expectedPrice.getFreeProducts(), price.getFreeProducts());
		assertEquals(expectedPrice.getPromoPrice(), price.getPromoPrice());
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
		expectedProduct.setPrice(expectedPrice);
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
		expectedProduct.setPrice(expectedPrice);
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
	
	@Test
	public void should_check_product_with_no_promotion() {
		
		// Given
		Price expectedPrice = new Price();
		expectedPrice.setUnitPrice(BigDecimal.valueOf(1.99));
		product.setPrice(expectedPrice);
		product.setType(PromotionType.NO_PROMOTION);
		
		// When
		try {
			product.check();
		} catch (ProductException e) {
			fail();
		}
	}
	
	@Test(expected = ProductException.class)
	public void should_throw_exception_product_with_no_promotion() throws ProductException {
		
		// Given
		Price expectedPrice = new Price();
		product.setPrice(expectedPrice);
		product.setType(PromotionType.DISCOUNT);
		
		// When
		product.check();

	}
	
	@Test
	public void should_check_product_with_discount() {
		
		// Given
		Price expectedPriceWithDiscount = new Price();
		expectedPriceWithDiscount.setUnitPrice(BigDecimal.valueOf(1.99));
		expectedPriceWithDiscount.setDiscount(BigDecimal.valueOf(25.00));
		product.setPrice(expectedPriceWithDiscount);
		product.setType(PromotionType.DISCOUNT);
		
		// When
		try {
			product.check();
		} catch (ProductException e) {
			fail();
		}
	}
	
	@Test(expected = ProductException.class)
	public void should_throw_exception_product_with_discount() throws ProductException {
		
		// Given
		Price expectedPriceWithDiscount = new Price();
		expectedPriceWithDiscount.setUnitPrice(BigDecimal.valueOf(1.99));
		product.setPrice(expectedPriceWithDiscount);
		product.setType(PromotionType.DISCOUNT);
		
		// When
		product.check();

	}
	
	@Test
	public void should_check_product_with_free_products_promotion() {
		
		// Given
		Price expectedPrice = new Price();
		expectedPrice.setUnitPrice(BigDecimal.valueOf(1.99));
		expectedPrice.setFreeProducts(2);
		expectedPrice.setQuantity(3);
		product.setPrice(expectedPrice);
		product.setType(PromotionType.FREE_PRODUCTS);
		
		// When
		try {
			product.check();
		} catch (ProductException e) {
			fail();
		}
	}
	
	@Test(expected = ProductException.class)
	public void should_throw_exception_product_free_products_promotion() throws ProductException {
		
		// Given
		Price expectedPrice = new Price();
		expectedPrice.setUnitPrice(BigDecimal.valueOf(1.99));
		product.setPrice(expectedPrice);
		product.setType(PromotionType.FREE_PRODUCTS);
		
		// When
		product.check();

	}
	
	@Test
	public void should_check_product_with_price_many_products() {
		
		// Given
		Price expectedPrice = new Price();
		expectedPrice.setUnitPrice(BigDecimal.valueOf(1.99));
		expectedPrice.setPromoPrice(BigDecimal.valueOf(2.99));
		expectedPrice.setQuantity(3);
		product.setPrice(expectedPrice);
		product.setType(PromotionType.PRICE_FOR_MANY_PRODUCT);
		
		// When
		try {
			product.check();
		} catch (ProductException e) {
			fail();
		}
	}
	
	@Test(expected = ProductException.class)
	public void should_throw_exception_product_with_price_many_products() throws ProductException {
		
		// Given
		Price expectedPrice = new Price();
		expectedPrice.setUnitPrice(BigDecimal.valueOf(1.99));
		product.setPrice(expectedPrice);
		product.setType(PromotionType.PRICE_FOR_MANY_PRODUCT);
		
		// When
		product.check();

	}
}
