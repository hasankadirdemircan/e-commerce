package com.x.ecommerce.repository;

import com.x.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM Product p WHERE p.categoryId = :categoryId")
    List<Product> findProductListByCategoryId(@Param("categoryId") Long categoryId);

    @Query("UPDATE Product p SET p.active = :active WHERE p.id = :id")
    @Modifying
    int updateStatusOfProductById(@Param("active") Boolean active, @Param("id") Long id);

    @Query("SELECT p.price FROM Product p where id = :id")
    Double findProductPriceById(@Param("id") Long id);

    //TODO: bu adet(unitInStock kontrolü için)
}
