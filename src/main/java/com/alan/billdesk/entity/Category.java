package com.alan.billdesk.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

}