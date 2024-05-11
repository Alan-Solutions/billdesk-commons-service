package com.alan.billdesk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "product_details")
@DynamicInsert
public class ProductDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_details_gen")
  @SequenceGenerator(name = "product_details_gen", sequenceName = "product_details_seq", allocationSize = 1)
  private int id;

  @Column(name = "product_price")
  private Double price;

  @Column(name = "product_tax")
  private Double tax;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @ManyToOne
  @JoinColumn(name = "unit_id")
  private Unit unit;

  @ManyToOne
  @JoinColumn(name = "discount_id")
  private Discount discount;

  @Column(name = "product_loc")
  private String item_loc;

  @Column(name = "update_ts")
  private LocalDateTime updateTs;
}
