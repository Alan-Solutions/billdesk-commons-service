package com.alan.billdesk.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Data
@DynamicInsert
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_gen")
    @SequenceGenerator(name = "category_gen", sequenceName = "category_seq", allocationSize = 1)
    private int id;

    @Column(name = "name")
    private String productName;

    @Column(name = "product_invoice_name")
    private String productInvoiceName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "create_ts")
    private LocalDateTime createTs;

}