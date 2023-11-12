package com.x.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Double price;

    @Column(name = "units_in_stock")
    private Long unitsInStock;

    @Column(name = "category_id")
    private Long categoryId;

    private Boolean active;
    //TODO: yorumlar - puanlama
    //TODO: ürün özellikleri
}
