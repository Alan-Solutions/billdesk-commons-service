package com.alan.billdesk.repository;

import com.alan.billdesk.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

  Category findByName(String name);

  List<Category> findAllByOrderByIdAsc();

}
