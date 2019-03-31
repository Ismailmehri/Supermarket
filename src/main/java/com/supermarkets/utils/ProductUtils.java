package com.supermarkets.utils;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

import com.supermarkets.exceptions.ProductException;
import com.supermarkets.model.Price;
import com.supermarkets.model.Product;

public class ProductUtils {

	private static final int MAX_PRODUCT_NAME_LENGTH = 60;
	
	/**
	 * Check product params
	 * @param product the product
	 * @throws ProductException on error
	 */
	public static void checkProduct(Product product) throws ProductException {
		if (StringUtils.isEmpty(product.getName())
				|| product.getName().length() > MAX_PRODUCT_NAME_LENGTH) {
			throw new ProductException("Incorrect product name");
		} else if (product.getPrices().isEmpty()) {
			throw new ProductException("No price founded");
		} else {
			for (Price price : product.getPrices())
				checkPrice(price);
		}
	}

	/**
	 * Check the price params
	 * @param price the {@link Price}
	 * @throws ProductException on error
	 */
	public static void checkPrice(Price price) throws ProductException {
		if (price.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new ProductException("The price should be greater than 0.00");
		} else if (1 > price.getQuantity()) {
			throw new ProductException("The quantity should be greater than 0");
		} else if (null == price.getDiscount()
				|| price.getDiscount().compareTo(BigDecimal.valueOf(100.00)) > 0
				|| price.getDiscount().compareTo(BigDecimal.ZERO) < 0) {
			throw new ProductException("Incorrect Discount value");
		}
	}

}
