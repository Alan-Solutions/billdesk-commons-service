package com.alan.billdesk.service;


import com.alan.billdesk.entity.Discount;
import com.alan.billdesk.utils.CommonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.List;

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
    List<Discount> discounts = discountService.findAllByOrderByIdAsc();
    if (!commonUtils.isNullOrEmpty(discounts)) {
      defaultDiscount = discounts.get(0);
    }
  }

  @Test
  @Order(1)
  public void saveTest() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Discount discount = new Discount();
    discount.setDiscount(0d);
    discount.setDiscountName("No Discount");
    Discount savedDiscount = discountService.save(discount);
    assertTrue(discount.getDiscountName().equals(savedDiscount.getDiscountName()));
    savedDiscount = discountService.findById(savedDiscount.getId());

    /** Testing Default values are working or not, @DynamicInsert is used in this class **/
    assertTrue("relative".equalsIgnoreCase(savedDiscount.getDiscountType()));
    assertTrue("active".equalsIgnoreCase(savedDiscount.getStatus()));
    String dateTime = sdf.format(savedDiscount.getDiscountExpiry());
    assertTrue("1970-01-01 00:00:00".equals(dateTime));
  }

}
