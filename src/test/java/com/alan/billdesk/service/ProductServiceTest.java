package com.alan.billdesk.service;

import com.alan.billdesk.constants.Constants;
import com.alan.billdesk.entity.Category;
import com.alan.billdesk.entity.Product;
import com.alan.billdesk.response.BillDeskResponse;
import com.alan.billdesk.utils.CommonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

  @Autowired
  private CommonUtils commonUtils;

  @Autowired
  private ProductService productService;

  @Autowired
  private CategoryService categoryService;

  private Product defaultProduct;

  @BeforeEach
  public void setUp() {
    List<Product> products = productService.findAllByOrderByIdAsc().getBody();
    if (!commonUtils.isNullOrEmpty(products)) {
      defaultProduct = products.get(0);
    }
  }

  @Test
  @Order(1)
  public void saveTest() {

    Category category = new Category();
    category.setName("Recharge");

    BillDeskResponse<Category> response = categoryService.findByName(category.getName());
    if (Constants.SUCCESS.equals(response.getStatus())) {
      category = response.getBody();
    } else {
      category = categoryService.saveCategory(category).getBody();
    }

    Product product = new Product();
    product.setProductName("Airtel - Recharge");
    product.setProductInvoiceName(product.getProductName());
    product.setCategory(category);
    product.setCreateTs(LocalDateTime.now());
    Product savedProduct = productService.saveProduct(product).getBody();

    assertTrue(null != savedProduct);
    assertTrue(savedProduct.getProductName().equals(product.getProductName()));
    assertTrue(savedProduct.getCategory().equals(product.getCategory()));
  }

  @Test
  @Order(2)
  public void findProductByIdTest() {
    BillDeskResponse<Product> response = productService.findProductById(defaultProduct.getId());
    assertTrue(null != response);
    assertTrue(Constants.SUCCESS.equals(response.getStatus()));
  }

  @Test
  @Order(3)
  public void findProductByProductNameTest() {
    BillDeskResponse<List<Product>> response = productService.findProductByName("Airtel - Recharge");
    assertTrue(null != response);
    assertTrue(Constants.SUCCESS.equals(response.getStatus()));
    List<Product> products = response.getBody();
    for (Product p : products) {
      assertTrue("Airtel - Recharge".equals(p.getProductName()));
    }
  }

  @Test
  @Order(4)
  public void updateTest() {
    Product product = new Product();
    product.setId(defaultProduct.getId());
    product.setProductName("Airtel VIP - Recharge");
    product.setProductInvoiceName("Airtel VIP - Recharge");
    BillDeskResponse<Product> response = productService.update(product);
    assertTrue(null != response);
    Product pEntity = response.getBody();
    System.out.println("pEntity "+pEntity);
    pEntity = productService.findProductById(pEntity.getId()).getBody();

    assertTrue(product.getProductName().equals(pEntity.getProductName()));
    assertTrue(product.getProductInvoiceName().equals(pEntity.getProductInvoiceName()));

    product.setProductInvoiceName("Air-VIP - RC");
    response = productService.update(product);
    assertTrue(null != response);

    pEntity = response.getBody();
    pEntity = productService.findProductById(pEntity.getId()).getBody();
    assertTrue(product.getProductName().equals(pEntity.getProductName()));
    System.out.println("pEntity name = "+pEntity.getProductName()+" - name=() "+product.getProductName());
    System.out.println("pEntity = "+pEntity.getProductInvoiceName()+" - "+product.getProductInvoiceName());
    assertTrue(product.getProductInvoiceName().equals(pEntity.getProductInvoiceName()));
  }

  @Test
  @Order(6)
  public void deleteAll() {
    productService.deleteAll();
    categoryService.deleteAll();

    BillDeskResponse response = productService.findProductById(defaultProduct.getId());
    assertTrue(Constants.FAILED.equals(response.getStatus()));

    response = categoryService.findAllByOrderByIdAsc();
    assertTrue(Constants.FAILED.equals(response.getStatus()));
  }

}
