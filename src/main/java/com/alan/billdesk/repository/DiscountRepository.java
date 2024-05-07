package com.alan.billdesk.repository;

import com.alan.billdesk.entity.Discount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiscountRepository  extends CrudRepository<Discount, Integer> {

  List<Discount> findByDiscountName(String name);

  List<Discount> findAllByOrderByIdAsc();
}
