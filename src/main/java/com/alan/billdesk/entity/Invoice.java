package com.alan.billdesk.entity;

import com.alan.user.entity.Users;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "invoice")
public class Invoice {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @SequenceGenerator(name = "invoice_gen", sequenceName = "invoice_seq", allocationSize = 1)
  private Long id;

  @Column(name = "quantity")
  private Long quantity;

  @Column(name = "create_ts", nullable = false, columnDefinition = "timestamp default current_timestamp")
  private Timestamp createTs;

  @Column(name = "type", nullable = true, columnDefinition = "varchar default 'invoice'")
  private String type;

  @ManyToOne
  @JoinColumn(name = "customer_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "invoice_customer_id_fkey"))
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "invoice_user_id_fkey"))
  private Users user;

}
