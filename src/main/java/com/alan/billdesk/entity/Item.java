package com.alan.billdesk.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Table(name = "item")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_invoice_name")
    private String itemInvoiceName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "create_ts")
    private Timestamp createTs;

}