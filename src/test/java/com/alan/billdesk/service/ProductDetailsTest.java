package com.alan.billdesk.service;

import com.alan.billdesk.entity.Category;
import com.alan.billdesk.entity.Discount;
import com.alan.billdesk.entity.Product;
import com.alan.billdesk.entity.ProductDetails;
import com.alan.billdesk.entity.Unit;
import com.alan.billdesk.utils.CommonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductDetailsTest {

  @Autowired
  private CommonUtils commonUtils;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductDetailsService pdService;

  @Autowired
  private DiscountService discountService;

  @Autowired
  private UnitService unitService;

  private ProductDetails defaultPD;

  @BeforeEach
  public void setUp() {
    List<ProductDetails> products = pdService.findAllByOrderByIdAsc().getBody();
    if (!commonUtils.isNullOrEmpty(products)) {
      defaultPD = products.get(0);
    }
  }

  @Test
  @Order(1)
  public void saveProduct() {
    Discount discount = new Discount();
    discount.setDiscount(0d);
    discount.setDiscountName("Default");
    discount.setStatus("active");
    Discount discountEntity = discountService.saveDiscount(discount).getBody();
    assertTrue(discount.getId() == discountEntity.getId());

    Unit unit = new Unit();
    unit.setDescription("Single Unit");
    unit.setSize(1d);
    Unit unitEntity = unitService.saveUnit(unit).getBody();
    assertTrue(unit.getId() == unitEntity.getId());

    Category category = new Category();
    category.setName("Recharge");
    Category categoryEntity = categoryService.saveCategory(category).getBody();
    assertTrue(category.getId() == categoryEntity.getId());

    Product product = new Product();
    product.setProductName("Airtel Recharge");
    product.setProductInvoiceName(product.getProductName());
    product.setCategory(category);
    Product productEntity = productService.saveProduct(product).getBody();
    assertTrue(productEntity.getId() == productEntity.getId());

    ProductDetails productDetails = new ProductDetails();
    productDetails.setDiscount(discount);
    productDetails.setUnit(unit);
    productDetails.setProduct(product);
    productDetails.setPrice(500d);
    productDetails.setTax(0d);

    ProductDetails pdEntity = pdService.saveProductDetails(productDetails).getBody();
    assertTrue(productDetails.getId() == pdEntity.getId());
  }

  @Test
  @Order(2)
  public void findProductDetailsByIdTest() {
    ProductDetails productDetails = pdService.findProductDetailsById(defaultPD.getId()).getBody();
    assertTrue(null != productDetails);
    assertTrue(null != productDetails.getProduct());
    assertTrue(null != productDetails.getUnit());
    assertTrue(null != productDetails.getDiscount());
    assertTrue(null != productDetails.getProduct().getCategory());
  }

  @Test
  @Order(3)
  public void findProductDetailsByPriceTest() {
    List<ProductDetails> productDetails = pdService.findProductDetailsByPrice(defaultPD.getPrice()).getBody();
    for(ProductDetails productDetail : productDetails) {
      assertTrue(null != productDetail);
      assertTrue(null != productDetail.getProduct());
      assertTrue(null != productDetail.getUnit());
      assertTrue(null != productDetail.getDiscount());
      assertTrue(null != productDetail.getProduct().getCategory());
    }
  }

  @Test
  @Order(5)
  public void findProductDetailsByTaxTest() {
    List<ProductDetails> productDetails = pdService.findProductDetailsByTax(defaultPD.getTax()).getBody();
    for(ProductDetails productDetail : productDetails) {
      assertTrue(null != productDetail);
      assertTrue(null != productDetail.getProduct());
      assertTrue(null != productDetail.getUnit());
      assertTrue(null != productDetail.getDiscount());
      assertTrue(null != productDetail.getProduct().getCategory());
    }
  }

//  @Test
//  @Order(6)
//  public void deleteAll() {
//    pdService.deleteAll();
//    discountService.deleteAll();
//    unitService.deleteAll();
//    productService.deleteAll();
//    categoryService.deleteAll();
//    assertTrue(Constants.FAILED.equals(pdService.findAllByOrderByIdAsc().getStatus()));
//    assertTrue(Constants.FAILED.equals(productService.findAllByOrderByIdAsc().getStatus()));
//    assertTrue(Constants.FAILED.equals(categoryService.findAllByOrderByIdAsc().getStatus()));
//    assertTrue(Constants.FAILED.equals(discountService.findAllByOrderByIdAsc().getStatus()));
//    assertTrue(Constants.FAILED.equals(unitService.findAllByOrderByIdAsc().getStatus()));
//  }
}
