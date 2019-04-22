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
import com.supermarkets.model.Order;
import com.supermarkets.model.Price;
import com.supermarkets.model.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	private Product product;
	
	private Product productWithDiscount;
	
	private Product productWithPromo;
	
	private Product productWithPricManyProducts;
	
	@Before
	public void setup() throws ProductException {
		
		//Simple Product
		String expectedName = "name";
		Price expectedPrice = new Price();
		expectedPrice.setUnitPrice(BigDecimal.valueOf(1.99));

		product = new Product();		
		product.setName(expectedName);
		product.setPrice(expectedPrice);
		product.setType(PromotionType.NO_PROMOTION);
		product.check();
		productService.addProduct(product);
		
		// Product with discount promotion
		Price expectedPriceWithDiscount = new Price();
		expectedPriceWithDiscount.setUnitPrice(BigDecimal.valueOf(1.99));
		expectedPriceWithDiscount.setDiscount(BigDecimal.valueOf(25.00));
		
		productWithDiscount = new Product();		
		productWithDiscount.setName(expectedName);
		productWithDiscount.setPrice(expectedPriceWithDiscount);
		productWithDiscount.setType(PromotionType.DISCOUNT);
		productWithDiscount.check();
		productService.addProduct(productWithDiscount);
		
		// Product with promotion
		Price expectedPriceWithPromo = new Price();
		expectedPriceWithPromo.setUnitPrice(BigDecimal.valueOf(0.99));
		expectedPriceWithPromo.setQuantity(3);
		expectedPriceWithPromo.setFreeProducts(2);
		
		productWithPromo = new Product();		
		productWithPromo.setName(expectedName);
		productWithPromo.setPrice(expectedPriceWithPromo);
		productWithPromo.setType(PromotionType.FREE_PRODUCTS);
		productWithPromo.check();
		productService.addProduct(productWithPromo);
		
		// Product with promotion
		Price expectedPriceForManyProduct = new Price();
		expectedPriceForManyProduct.setUnitPrice(BigDecimal.valueOf(0.99));
		expectedPriceForManyProduct.setQuantity(3);
		expectedPriceForManyProduct.setPromoPrice(BigDecimal.valueOf(1.99));
		
		productWithPricManyProducts = new Product();		
		productWithPricManyProducts.setName(expectedName);
		productWithPricManyProducts.setPrice(expectedPriceForManyProduct);
		productWithPricManyProducts.setType(PromotionType.PRICE_FOR_MANY_PRODUCT);
		productWithPricManyProducts.check();
		productService.addProduct(productWithPromo);
	}
	
	@Test
	public void should_add_order() throws ProductException {
		
		// given
		Integer quantity = 2;

		Order order = new Order();
		order.setQuantity(quantity);
		order.setProduct(product);
		
		// when
		Order newOrder = orderService.addOrder(order);
		
		// then
		assertNotNull(newOrder);
		assertEquals(quantity, newOrder.getQuantity());
		assertEquals(product.getId(), newOrder.getProduct().getId());
	}
	
	@Test
	public void should_get_order() throws ProductException {
		
		// given
		Integer quantity = 2;

		Order order = new Order();
		order.setQuantity(quantity);
		order.setProduct(product);
		Order newOrder = orderService.addOrder(order);
		
		// when
		Optional<Order> optionlOrder =  orderService.getOrder(newOrder.getId());

		// then
		assertNotNull(optionlOrder);
		assertTrue(optionlOrder.isPresent());
		assertNotNull(optionlOrder.get());
	}
	
	@Test
	public void should_delete_order() throws ProductException {
		
		// given
		Integer quantity = 2;

		Order order = new Order();
		order.setQuantity(quantity);
		order.setProduct(product);
		Order newOrder = orderService.addOrder(order);
		
		// when
		orderService.deleteOrder(newOrder);

		// then
		Optional<Order> deletedOrder = orderService.getOrder(newOrder.getId());
		assertNotNull(deletedOrder);
		assertFalse(deletedOrder.isPresent());
	}
	
	@Test
	public void should_return_final_price_no_promo() throws ProductException {
		
		// given
		Integer quantity = 2;

		Order order = new Order();
		order.setQuantity(quantity);
		order.setProduct(product);
		
		// when
		BigDecimal finalPrice = order.getFinalPrice();
		
		// then
		assertNotNull(finalPrice);
		assertEquals(product.getPrice().getUnitPrice().multiply(BigDecimal.valueOf(quantity)), finalPrice);
	}

	@Test
	public void should_return_final_price_discount() throws ProductException {
		
		// given
		Integer quantity = 2;
		BigDecimal discount = BigDecimal.valueOf(0.25);

		Order order = new Order();
		order.setQuantity(quantity);
		order.setProduct(productWithDiscount);
		
		// when
		BigDecimal finalPrice = order.getFinalPrice();
		
		// then
		assertNotNull(finalPrice);
		assertEquals(product.getPrice().getUnitPrice().multiply(BigDecimal.valueOf(quantity)).multiply(discount), finalPrice);
	}
	
	@Test
	public void should_return_final_price_with_promo() throws ProductException {
		
		// given
		Integer quantity = 7;

		Order order = new Order();
		order.setQuantity(quantity);
		order.setProduct(productWithPromo);
		
		// when
		BigDecimal finalPrice = order.getFinalPrice();
		
		// then
		assertNotNull(finalPrice);
		/**
		 * buy tree, get tow free : for 7 products we get 2 free product 
		 * finalPrice = 3 * UnitPrice and 1 free product
		 */
		assertEquals(productWithPromo.getPrice().getUnitPrice().multiply(BigDecimal.valueOf(quantity - 2)), finalPrice);
	}
	
	@Test
	public void should_return_final_price_for_products() throws ProductException {
		
		// given
		Integer quantity = 5;

		Order order = new Order();
		order.setQuantity(quantity);
		order.setProduct(productWithPricManyProducts);
		
		// when
		BigDecimal finalPrice = order.getFinalPrice();
		
		// then
		assertNotNull(finalPrice);
		/**
		 * three for 1.99 and unit price is 0.99 : for 5 products 1.99 + 2 * 0.99
		 */
		assertEquals(productWithPricManyProducts.getPrice().getUnitPrice()
				.multiply(BigDecimal.valueOf(2))
				.add(productWithPricManyProducts.getPrice().getPromoPrice()), finalPrice);
	}
	
	@Test(expected = ProductException.class)
	public void should_throw_exception() throws ProductException {
		
		// given
		Integer quantity = 5;

		Order order = new Order();
		order.setQuantity(quantity);
		order.setProduct(null);
		
		// when
		order.getFinalPrice();
	}
}
