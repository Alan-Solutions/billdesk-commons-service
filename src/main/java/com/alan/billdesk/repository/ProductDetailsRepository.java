package com.alan.billdesk.repository;

import com.alan.billdesk.entity.ProductDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductDetailsRepository extends CrudRepository<ProductDetails, Integer> {
  List<ProductDetails> findAllByOrderByIdAsc();

  List<ProductDetails> findByPrice(double price);

  List<ProductDetails> findByTax(double price);
}
