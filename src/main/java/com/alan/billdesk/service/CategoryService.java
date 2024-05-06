package com.alan.billdesk.service;

import com.alan.billdesk.entity.Category;
import com.alan.billdesk.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  public Category findFirstByOrderByIdAsc() {
    return categoryRepository.findFirstByOrderByIdAsc();
  }

  public List<Category> findAll() {
    return (List<Category>) categoryRepository.findAll();
  }

  public Category save(Category category) {
    return categoryRepository.save(category);
  }

  public Category update(Category category) {
    Category updateCategory = findById(category.getId());
    updateCategory.setName(category.getName());
    return save(updateCategory);
  }

  public Category findByName(String name) {
    return categoryRepository.findByName(name);
  }

  public Category findById(int id) {
    Category category = null;
    Optional<Category> oCategory = categoryRepository.findById(id);
    if (oCategory.isPresent()) {
      category = oCategory.get();
    }
    return category;
  }

  public void delete(int id) {
    categoryRepository.deleteById(id);
  }

  public void delete(Category category) {
    categoryRepository.delete(category);
  }

  public void deleteAll(){
    categoryRepository.deleteAll();
  }

}
