package com.alan.billdesk.service;

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

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnitServiceTest {

  @Autowired
  private CommonUtils commonUtils;

  @Autowired
  private UnitService unitService;

  private Unit defaultUnit;

  @BeforeEach
  public void setUp() {
    List<Unit> units = unitService.findAllByOrderByIdAsc();
    if (!commonUtils.isNullOrEmpty(units)) {
      defaultUnit = units.get(0);
    }
  }

  @Test
  @Order(1)
  public void saveTest() {
    Unit unit = new Unit();
    unit.setDescription("Single Unit");
    unit.setSize(1);
    Unit savedUnit = unitService.save(unit);
    assertTrue(unit.getId() == savedUnit.getId());
  }

  @Test
  @Order(2)
  public void findByIdTest() {
    Unit unit = unitService.findById(defaultUnit.getId());
    assertTrue(null != unit);
  }

  @Test
  @Order(3)
  public void updateTest() {
    Unit unit = new Unit();
    unit.setId(defaultUnit.getId());
    unit.setDescription("Single Unit / Normal Unit");
    unit.setSize(1);
    Unit updatedUnit = unitService.update(unit);
    assertTrue(updatedUnit.getDescription().equals(unit.getDescription()));
    assertTrue(updatedUnit.getSize() == unit.getSize());
  }

  @Test
  @Order(4)
  public void deleteByIdTest() {
    unitService.deleteById(defaultUnit.getId());
    Unit unit = unitService.findById(defaultUnit.getId());
    assertTrue(null == unit);
  }

//  @Test
//  @Order(5)
//  public void deleteAll(){
//    unitService.deleteAll();
//    List<Unit> units = unitService.findAllByOrderByIdAsc();
//    assertTrue(units.isEmpty());
//  }

}
