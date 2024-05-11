package com.alan.billdesk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "product_details")
public class ProductDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "product_price")
  private float price;

  @Column(name = "product_tax")
  private float tax;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product item;

  @ManyToOne
  @JoinColumn(name = "unit_id")
  private Unit unit;

  @ManyToOne
  @JoinColumn(name = "discount_id")
  private Discount discount;

  @Column(name = "product_loc")
  private String item_loc;

  @Column(name = "update_ts")
  private Date updateTs;
}
