package com.alan.billdesk.repository;

import com.alan.billdesk.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

  List<Product> findAllByOrderByIdAsc();

  List<Product> findByProductName(String name);

}
