package com.alan.billdesk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "unit")
public class Unit {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_gen")
  @SequenceGenerator(name = "unit_gen", sequenceName = "unit_seq", allocationSize = 1)
  private int id;
  @Column(name = "unit_size")
  private Double size;
  @Column(name = "unit_description")
  private String description;

}
