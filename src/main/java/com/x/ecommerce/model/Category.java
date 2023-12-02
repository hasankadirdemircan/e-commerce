package com.x.ecommerce.model;

import jakarta.persistence.*;
import jdk.jfr.ContentType;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; //TODO enum yap

}
