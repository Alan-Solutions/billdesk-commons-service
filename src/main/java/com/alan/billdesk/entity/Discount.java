package com.alan.billdesk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Table(name = "discount")
@Data
@DynamicInsert
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discount_gen")
    @SequenceGenerator(name = "discount_gen", sequenceName = "discount_seq", allocationSize = 1)
    private int id;

    @Column(name = "discount_name")
    private String discountName;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "discount_expiry")
    private Date discountExpiry;

    @Column(name = "status")
    private String status;

}