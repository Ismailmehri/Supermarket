package com.supermarkets.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.supermarkets.exceptions.ProductException;

@Entity
@Table(name = "T_ORDER")
public class Order {

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	private Product product;

	@Column(nullable = true)
	private Integer quantity;

	/**
	 * the order id
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set the order id
	 * 
	 * @param id
	 *            {@link id}
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * The product
	 * 
	 * @return the {@link Product}
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Set the product
	 * 
	 * @param product
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * the quantity
	 * 
	 * @return quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Set the quantity
	 * 
	 * @param quantity
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * Return final price
	 * @return the final price
	 * @throws ProductException on error
	 */
	public BigDecimal getFinalPrice() throws ProductException {
		
		if (product == null) {
			throw new ProductException("The product must not be null");
		}
		
		switch(product.getType()) {
		case NO_PROMOTION : 
			return product.getPrice().getUnitPrice().multiply(BigDecimal.valueOf(quantity));
		case DISCOUNT :
			return product.getPrice().getUnitPrice().multiply(BigDecimal.valueOf(quantity))
					.multiply(product.getPrice().getDiscount().divide(BigDecimal.valueOf(100)));
		case FREE_PRODUCTS:
			Integer productsInPromo = quantity / (product.getPrice().getQuantity() 
					+ product.getPrice().getFreeProducts()) * product.getPrice().getFreeProducts();
			Integer productsNotInPromo = quantity - productsInPromo;
			return product.getPrice().getUnitPrice().multiply(BigDecimal.valueOf(productsNotInPromo));
		case PRICE_FOR_MANY_PRODUCT:
			Integer productInPromo = quantity / product.getPrice().getQuantity() ;
			Integer productNotInPromo = quantity - (productInPromo * product.getPrice().getQuantity());
			return product.getPrice().getUnitPrice().multiply(BigDecimal.valueOf(productNotInPromo))
					.add(product.getPrice().getPromoPrice().multiply(BigDecimal.valueOf(productInPromo)));

			default :
				throw new ProductException("Invalid product price");
		}
	}
}
