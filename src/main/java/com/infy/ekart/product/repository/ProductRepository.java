package com.infy.ekart.product.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.infy.ekart.product.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

	List<Product> findAll();

}
