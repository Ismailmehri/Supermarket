package com.supermarkets.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * {@link Product} model class
 * @author mehri
 *
 */
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    @Column(length = 60)
    private String name;

    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Price> prices;

    /**
     * The product Id
     * @return the product Id
     */
    public Long getId() {
        return id;
    }

    /**
     * The product name
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * The product prices
     * @return list of product prices
     */
    public List<Price> getPrices() {
        return prices;
    }

    /**
     * Set the product id
     * @param pId the product id
     */
    public void setId(Long pId) {
        id = pId;
    }

    /**
     * Set the product name
     * @param pName the product name
     */
    public void setName(String pName) {
        name = pName;
    }

    /**
     * Set the product prices
     * @param pPrices the product prices
     */
    public void setPrices(List<Price> pPrices) {
        prices = pPrices;
    }

}
