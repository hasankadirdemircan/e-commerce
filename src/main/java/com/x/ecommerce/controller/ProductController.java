package com.x.ecommerce.controller;

import com.x.ecommerce.model.Product;
import com.x.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping("/category/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<Product>>  getProductList(@PathVariable(value = "categoryId") Long categoryId) {
        return new ResponseEntity<>(productService.getProductList(categoryId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public Product getProduct(@PathVariable(value = "id") Long id) {
        return productService.getProductById(id);
    }
    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @PutMapping("/deactive/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deactiveProduct(@PathVariable(value = "id") Long id) {
         productService.deactiveProduct(id);
    }

    @PutMapping("/active/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void activeProduct(@PathVariable(value = "id") Long id) {
         productService.activeProduct(id);
    }
}
