package com.supermarkets.utils;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Test;

import com.supermarkets.exceptions.ProductException;
import com.supermarkets.model.Price;
import com.supermarkets.model.Product;

/**
 * Tests for {@link ProductUtils}
 * @author mehri
 *
 */
public class ProductUtilsTest {

	@Test(expected = ProductException.class)
	public void should_throw_exception_incorrect_name() throws ProductException {
		
		//Given
		Product product = new Product();
		String name = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
				+ "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
		product.setName(name);
		
		// when
		ProductUtils.checkProduct(product);
	}
	
	@Test(expected = ProductException.class)
	public void should_throw_exception_no_prices() throws ProductException {
		
		//Given
		Product product = new Product();
		String name = "Product_Name";
		product.setName(name);
		product.setPrices(new ArrayList<>());
		
		// when
		ProductUtils.checkProduct(product);
	}
	
	@Test(expected = ProductException.class)
	public void should_throw_exception_price_zero() throws ProductException {
		
		//Given
		Price price = new Price();
		price.setUnitPrice(BigDecimal.ZERO);
		
		// when
		ProductUtils.checkPrice(price);
	}
	
	@Test(expected = ProductException.class)
	public void should_throw_exception_quantity_zero() throws ProductException {
		
		//Given
		Price price = new Price();
		price.setUnitPrice(BigDecimal.valueOf(0.99));
		price.setQuantity(0);
		
		// when
		ProductUtils.checkPrice(price);
	}
	
	@Test(expected = ProductException.class)
	public void should_throw_exception_discount_null() throws ProductException {
		
		//Given
		Price price = new Price();
		price.setUnitPrice(BigDecimal.valueOf(0.99));
		price.setQuantity(3);
		
		// when
		ProductUtils.checkPrice(price);
	}
	
	@Test(expected = ProductException.class)
	public void should_throw_exception_discount_greater_than_100() throws ProductException {
		
		//Given
		Price price = new Price();
		price.setUnitPrice(BigDecimal.valueOf(0.99));
		price.setQuantity(3);
		price.setDiscount(BigDecimal.valueOf(200.00));
		
		// when
		ProductUtils.checkPrice(price);
	}
}
