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
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

/**
 * {@link Price} model class
 * @author mehri
 *
 */
@Entity
@Table(name = "T_PRICE")
public class Price {

    @Id
    @Column(name = "price_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition="Decimal(10,2)")
    private BigDecimal unitPrice;

    @Column(nullable = true)
    @Min(value = 1)
    private Integer quantity;
    
    @Column(nullable = true)
    @Min(value = 0)
    private Integer freeProducts;
    
    @Column(columnDefinition="Decimal(10,2)")
    private BigDecimal promoPrice;
    
    @Column(nullable=true)
    @Digits(integer=3, fraction=2)
    private BigDecimal discount;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    /**
     * The price id
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Return the product
     * @return the priduct
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Set the price id
     * @param pId the price id
     */
    public void setId(Long pId) {
        id = pId;
    }

    /**
     * Set the product
     * @param pProduct the product
     */
    public void setProduct(Product pProduct) {
        product = pProduct;
    }

    /**
     * Return the unit price
     * @return the price
     */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * Set the unit price
	 * @param unitPrice
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * Return quantity
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Set the quantity
	 * @param quantity the quantity
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * Return the discount value
	 * @return the discount value
	 */
	public BigDecimal getDiscount() {
		return discount;
	}

	/**
	 * Set the discount
	 * @param discount
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	/**
	 * Return free product value
	 * @return free product value
	 */
	public Integer getFreeProducts() {
		return freeProducts;
	}

	/**
	 * Set free product value
	 * @param freeProducts free product value
	 */
	public void setFreeProducts(Integer freeProducts) {
		this.freeProducts = freeProducts;
	}

	/**
	 * Return promo price
	 * @return the promo price
	 */
	public BigDecimal getPromoPrice() {
		return promoPrice;
	}

	/**
	 * Set promo price
	 * @param promoPrice the promo price
	 */
	public void setPromoPrice(BigDecimal promoPrice) {
		this.promoPrice = promoPrice;
	}
    
}
