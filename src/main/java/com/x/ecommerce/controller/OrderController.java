package com.x.ecommerce.controller;

import com.x.ecommerce.dto.OrderRequest;
import com.x.ecommerce.model.Order;
import com.x.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Order> doOrder(@RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.doOrder(orderRequest), HttpStatus.OK);
    }
}
/*
TODO: MapStruct'ı araştır,uygulayabilirsen buraya uygula. DTO ve Entity farkları tekrar et.
TODO: quantity kontrolü sağla(product unitInStock kontrolü) -> yani üründen 5 tane kaldıysa, 5 den fazla satamaz.
TODO: ürün satıldığında Product tablosundan quantity'i update et.-> yani 5 ürün var 2 tane satıldı, 3 kalacak.
TODO: eğer 5 ürün var ve 5 üründe satıldı. Product tablosundan product active=false
 */