package com.supermarkets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supermarkets.model.Price;

public interface PriceRepository extends JpaRepository<Price, Long> {

}
