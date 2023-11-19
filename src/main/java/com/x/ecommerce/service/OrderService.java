package com.x.ecommerce.service;

import com.x.ecommerce.dto.OrderRequest;
import com.x.ecommerce.model.Order;
import com.x.ecommerce.model.OrderDetail;
import com.x.ecommerce.model.Product;
import com.x.ecommerce.repository.OrderDetailRepository;
import com.x.ecommerce.repository.OrderRepository;
import com.x.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    public void doOrder(OrderRequest orderRequest) {
        //MapStruct kütüphanesini araştıralım ve örnek yapalım.
        Order order = new Order();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setOrderDate(LocalDateTime.now());
        Order ordered = orderRepository.save(order);
        //orderDetailRepository işlemleri için queue mekanizması yazılabilir.
        //Yani ben ordered'ı return deyip, detail işlemleri queue ile yaparım.
        orderRequest.getOrderProductInfoList().forEach(e -> {
            Double price = productRepository.findProductPriceById(e.getProductId());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setPrice(price);
            orderDetail.setQuantity(e.getQuantity());
            orderDetail.setProductId(e.getProductId());
            orderDetail.setOrderId(ordered.getId());
            orderDetailRepository.save(orderDetail);
        });
    }
}
