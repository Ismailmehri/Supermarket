package com.supermarkets.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.supermarkets.enums.PromotionType;
import com.supermarkets.exceptions.ProductException;

/**
 * {@link Product} model class
 * 
 * @author mehri
 *
 */
@Entity
@Table(name = "T_PRODUCT")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private Long id;

	@Column(length = 60)
	private String name;

	@Column(length = 32, columnDefinition = "varchar(32) default 'NO_PROMOTION'")
	@Enumerated(value = EnumType.STRING)
	private PromotionType type;

	@JoinColumn(name = "price_id", referencedColumnName = "price_id")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Price price;

	@JoinColumn(name = "order_id", referencedColumnName = "order_id")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Order order;

	
	/**
	 * The product Id
	 * 
	 * @return the product Id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * The product name
	 * 
	 * @return the product name
	 */
	public String getName() {
		return name;
	}

	/**
	 * The product prices
	 * 
	 * @return list of product prices
	 */
	public Price getPrice() {
		return price;
	}

	/**
	 * Set the product id
	 * 
	 * @param pId
	 *            the product id
	 */
	public void setId(Long pId) {
		id = pId;
	}

	/**
	 * Set the product name
	 * 
	 * @param pName
	 *            the product name
	 */
	public void setName(String pName) {
		name = pName;
	}


	/**
	 * return {@link Order}
	 * @return
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * Set {@link Order}
	 * @param order the order
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * Return {@link PromotionType}
	 * @return
	 */
	public PromotionType getType() {
		return type;
	}
	
	/**
	 * Set the {@link PromotionType}
	 * @param type
	 */
	public void setType(PromotionType type) {
		this.type = type;
	}

	/**
	 * Set the price
	 * @param price the {@link Price}
	 */
	public void setPrice(Price price) {
		this.price = price;
	}

	/**
	 * Check the product
	 * @throws ProductException on error
	 */
	public void check() throws ProductException {
		
		if (price == null || type == null) {
			throw new ProductException("The price and the type must not be null");
		}
		
		switch (type) {
			case NO_PROMOTION:
				checkUnitPrice(price);
				break;
			case DISCOUNT:
				checkUnitPrice(price);
				checkDiscountValue(price);
				break;
			case FREE_PRODUCTS:
				checkUnitPrice(price);
				checkQuantity(price);
				checkfreeProductsValue(price);
				break;
			case PRICE_FOR_MANY_PRODUCT:
				checkUnitPrice(price);
				checkQuantity(price);
				checkPromoPrice(price);
				break;
			default:
				throw new ProductException("Promotion type should be one of ("
						+ PromotionType.values() + ") ");
			}
	}

	private void checkQuantity(Price pPrice) throws ProductException {
		if (pPrice.getQuantity() == null || pPrice.getQuantity() < 1) {
			throw new ProductException("Quantity must be 1 or greater");
		}
	}

	private void checkfreeProductsValue(Price pPrice) throws ProductException {
		if (pPrice.getFreeProducts() == null || pPrice.getFreeProducts() < 1) {
			throw new ProductException(
					"Free product value must be 1 or greater");
		}
	}

	private void checkDiscountValue(Price pPrice) throws ProductException {
		if (pPrice.getDiscount() == null
				|| pPrice.getDiscount().compareTo(BigDecimal.ZERO) < 0) {
			throw new ProductException(
					"Discount value must be a positive big decimal");
		}
	}

	private void checkPromoPrice(Price pPrice) throws ProductException {
		if (pPrice.getPromoPrice() == null
				|| pPrice.getPromoPrice().compareTo(BigDecimal.ZERO) < 0) {
			throw new ProductException(
					"Promo price value must be a positive big decimal");
		}
	}

	private void checkUnitPrice(Price pPrice) throws ProductException {
		if (pPrice.getUnitPrice() == null
				|| pPrice.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
			throw new ProductException("The price must not be null");
		}
	}
	
}
