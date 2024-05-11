package com.alan.billdesk.service;


import com.alan.billdesk.constants.Constants;
import com.alan.billdesk.entity.Discount;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DiscountServiceTest {

  @Autowired
  private CommonUtils commonUtils;

  @Autowired
  private DiscountService discountService;

  private Discount defaultDiscount;

  @BeforeEach
  public void setUp() {
    BillDeskResponse<List<Discount>> response = discountService.findAllByOrderByIdAsc();
    List<Discount> discounts = response.getBody();
    if (!commonUtils.isNullOrEmpty(discounts)) {
      defaultDiscount = discounts.get(0);
    }
  }

  @Test
  @Order(1)
  public void saveTest() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    Discount discount = new Discount();
    discount.setDiscount(0d);
    discount.setDiscountName("No Discount");
    Discount savedDiscount = discountService.saveDiscount(discount).getBody();
    assertTrue(discount.getDiscountName().equals(savedDiscount.getDiscountName()));
    savedDiscount = discountService.findDiscountById(savedDiscount.getId()).getBody();

    /** Testing DB Default values are working or not, @DynamicInsert is used in this class **/
    assertTrue("relative".equalsIgnoreCase(savedDiscount.getDiscountType()));
    assertTrue("active".equalsIgnoreCase(savedDiscount.getStatus()));

    String dateTime = savedDiscount.getDiscountExpiry().format(dtf);
    assertTrue("1970-01-01 00:00:00".equals(dateTime));
  }

  @Test
  @Order(2)
  public void findByDiscountName() {
    List<Discount> discounts = discountService.findByDiscountName("No Discount").getBody();
    assertTrue(discounts.size() > 0);
    assertEquals("No Discount".toUpperCase(), discounts.get(0).getDiscountName());
  }

  @Test
  @Order(3)
  public void updateDiscountTest() {
    /** Discount Name Validation **/
    Discount fromUi = new Discount();
    fromUi.setId(defaultDiscount.getId());
    fromUi.setDiscountName("Default Discount");
    Discount dEntity = discountService.update(fromUi).getBody();
//    System.out.println("dEntity "+dEntity);
//    System.out.println("fromUi "+fromUi);
    assertTrue(fromUi.getDiscountName().toUpperCase().equals(dEntity.getDiscountName()));
    /** Discount Name Validation ends **/

    /** Discount Validation **/
    fromUi = new Discount();
    fromUi.setId(defaultDiscount.getId());
    fromUi.setDiscountName("AADI Discount");
    fromUi.setDiscount(10d);
    dEntity = discountService.update(fromUi).getBody();
    assertTrue(commonUtils.equals(fromUi.getDiscount(), dEntity.getDiscount()));
    assertTrue(commonUtils.equals(fromUi.getDiscountName().toUpperCase(), dEntity.getDiscountName()));
    assertTrue(fromUi.getId() == dEntity.getId());
    /** Discount Validation Ends **/

    /** DiscountType Validation **/
    fromUi = new Discount();
    fromUi.setId(defaultDiscount.getId());
    fromUi.setDiscountType("fixed");
    dEntity = discountService.update(fromUi).getBody();
    assertTrue(commonUtils.equals(fromUi.getDiscountType(), dEntity.getDiscountType()));
    assertTrue(fromUi.getId() == dEntity.getId());
    /** DiscountType Validation - Ends **/

    /** DiscountExpiry Validation **/
    fromUi = new Discount();
    fromUi.setId(defaultDiscount.getId());
    fromUi.setDiscountExpiry(LocalDateTime.now().plusDays(10));
    dEntity = discountService.update(fromUi).getBody();
    assertTrue(commonUtils.equals(fromUi.getDiscountExpiry(), dEntity.getDiscountExpiry()));
    assertTrue(fromUi.getId() == dEntity.getId());

    fromUi = new Discount();
    fromUi.setId(defaultDiscount.getId());
    fromUi.setDiscountExpiry(LocalDateTime.now().plusDays(10).minusDays(4));
    dEntity = discountService.update(fromUi).getBody();
    assertTrue(commonUtils.equals(fromUi.getDiscountExpiry(), dEntity.getDiscountExpiry()));
    assertTrue(fromUi.getId() == dEntity.getId());
    /** DiscountExpiry Validation - Ends **/

    /** DiscountStatus Validation **/
    fromUi = new Discount();
    fromUi.setId(defaultDiscount.getId());
    fromUi.setStatus(Constants.IN_ACTIVE);
    dEntity = discountService.update(fromUi).getBody();
    assertTrue(commonUtils.equals(fromUi.getStatus(), dEntity.getStatus()));
    assertTrue(fromUi.getId() == dEntity.getId());
    /** DiscountStatus Validation - Ends **/
  }

  @Test
  @Order(4)
  public void deleteById() {
    List<Discount> discounts = discountService.findAllByOrderByIdAsc().getBody();
    Discount discount = discounts.get(discounts.size() - 1);
    discountService.deleteById(discount.getId());
    System.out.println("id "+discount.getId());
    assertTrue( null == discountService.findDiscountById(discount.getId()).getBody());
  }


}
