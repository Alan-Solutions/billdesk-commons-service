package com.alan.billdesk.repository;

import com.alan.billdesk.entity.Category;
import com.alan.billdesk.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void saveTest() {
        Category category = new Category();
        category.setName("Recharge");
        categoryService.save(category);
    }


}
