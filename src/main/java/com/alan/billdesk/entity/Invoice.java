package com.alan.billdesk.entity;

import com.alan.user.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "invoice")
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String type;
  private float quantity;
  @Column(name = "create_ts")
  private Date createTs;

  @OneToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @OneToOne
  @JoinColumn(name = "user_id")
  private Users users;

}
