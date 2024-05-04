package com.alan.billdesk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "item_details")
public class ItemDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "item_price")
  private float price;

  @Column(name = "item_tax")
  private float tax;

  @OneToOne
  @JoinColumn(name = "item_id")
  private Item item;

  @OneToMany
  @JoinColumn(name = "unit_id")
  private Unit unit;

  @OneToMany
  @JoinColumn(name = "discount_id")
  private Discount discount;

  @Column(name = "item_loc")
  private String item_loc;

  @Column(name = "update_ts")
  private Date updateTs;
}
