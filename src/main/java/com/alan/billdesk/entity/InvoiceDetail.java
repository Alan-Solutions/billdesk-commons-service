package com.alan.billdesk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "invoice_detail")
public class InvoiceDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "invoice_id")
  private long invoiceId;

  @Column(name = "item_details_id")
  private String itemDetailsId;

  @Column(name = "tax")
  private float tax;

  @Column(name = "discount")
  private float discount;

  @Column(name = "total_amount")
  private double totalBill;
}
