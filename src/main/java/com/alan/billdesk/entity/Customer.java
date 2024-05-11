package com.alan.billdesk.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "customer")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "customer_gen", sequenceName = "customer_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private List<String> phone;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "create_ts")
    private String createTs;

    @Column(name = "address")
    private String address;

}