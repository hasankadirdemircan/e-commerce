package com.x.ecommerce.service;

import com.x.ecommerce.model.Product;
import com.x.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getProductList(Long categoryId) {
        return productRepository.findProductListByCategoryId(categoryId);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product id bulunamadı"));
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }


    public void deactiveProduct(Long id) {
        productRepository.updateStatusOfProductById(false, id);
    }

    public void activeProduct(Long id) {
        productRepository.updateStatusOfProductById(true, id);
    }
}
