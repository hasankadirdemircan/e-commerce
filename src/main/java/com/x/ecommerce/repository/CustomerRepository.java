package com.x.ecommerce.repository;

import com.x.ecommerce.model.Customer;
import com.x.ecommerce.model.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByFirstName(String firstName);
}
