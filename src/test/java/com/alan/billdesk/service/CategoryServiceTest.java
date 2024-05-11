package com.alan.billdesk.service;

import com.alan.billdesk.constants.Constants;
import com.alan.billdesk.entity.Category;
import com.alan.billdesk.response.BillDeskResponse;
import com.alan.billdesk.utils.CommonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryServiceTest {

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private CategoryService categoryService;

    private Category defaultCategory;

    @BeforeEach
    public void setUp() {
        List<Category> categories = categoryService.findAllByOrderByIdAsc().getBody();
        if(!commonUtils.isNullOrEmpty(categories)) {
            defaultCategory = categories.get(0);
        }
    }

    @Test
    @Order(1)
    public void saveTest() {
        Category category = new Category();
        category.setName("Recharge");
        Category savedCategory = categoryService.saveCategory(category).getBody();
        assertEquals(category.getName(), savedCategory.getName());
    }

    @Test
    @Order(2)
    public void findByIdTest() {
        Category category = categoryService.findCategoryById(defaultCategory.getId()).getBody();
        assertTrue(null != category);
        List<Category> categories = categoryService.findAllByOrderByIdAsc().getBody();
        category = categories.get(categories.size() - 1);
        BillDeskResponse resp = categoryService.findCategoryById(category.getId() + 100);
        assertTrue(resp.getStatus().equals(Constants.FAILED));
    }

    @Test
    @Order(3)
    public void findByNameTest() {
        Category category = categoryService.findByName("Recharge").getBody();
        assertTrue(category != null);
    }


    @Test
    @Order(4)
    public void updateTest() {
        Category category = new Category();
        category.setId(defaultCategory.getId());
        category.setName("New Recharge");
        Category updatedCategory = categoryService.update(category).getBody();
        assertTrue(null != updatedCategory);
        updatedCategory = categoryService.findCategoryById(category.getId()).getBody();
        assertTrue(updatedCategory.getName().equals(category.getName()));
    }

//    @Test
//    @Order(5)
//    public void deleteByIdTest() {
//        categoryService.deleteById(defaultCategory.getId());
//        Category category = categoryService.findCategoryById(defaultCategory.getId()).getBody();
//        assertTrue(null == category);
//    }

//    @Test
//    @Order(6)
//    public void deleteAllTest() {
//        categoryService.deleteAll();
//        List<Category> categories = categoryService.findAllByOrderByIdAsc().getBody();
//        assertTrue(categories.isEmpty());
//    }

}
