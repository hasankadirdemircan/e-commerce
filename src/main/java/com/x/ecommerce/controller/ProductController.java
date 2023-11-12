package com.x.ecommerce.controller;

import com.x.ecommerce.model.Product;
import com.x.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/save")
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getProductList(@PathVariable(value = "categoryId") Long categoryId) {
        return productService.getProductList(categoryId);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable(value = "id") Long id) {
        return productService.getProductById(id);
    }
    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @PutMapping("/deactive/{id}")
    public void deactiveProduct(@PathVariable(value = "id") Long id) {
         productService.deactiveProduct(id);
    }

    @PutMapping("/active/{id}")
    public void activeProduct(@PathVariable(value = "id") Long id) {
         productService.activeProduct(id);
    }
}
