package com.alan.billdesk.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "discount")
@Data
public class Discount {

    @Id
    private Integer id;

    @Column(name = "discount_description")
    private String discountDescription;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "discount_type")
    private String discountType;

}