package com.x.ecommerce.service;

import com.x.ecommerce.dto.OrderProductInfo;
import com.x.ecommerce.dto.OrderRequest;
import com.x.ecommerce.model.Order;
import com.x.ecommerce.model.OrderDetail;
import com.x.ecommerce.model.Product;
import com.x.ecommerce.repository.OrderDetailRepository;
import com.x.ecommerce.repository.OrderRepository;
import com.x.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
//TODO: Log ekleyiniz.
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    private void productUnitStockCheck(List<OrderProductInfo> orderProductInfoList) {
        orderProductInfoList.forEach(productInfo -> {
            Long productStock = productRepository.findById(productInfo.getProductId())
                    .map(Product::getUnitsInStock)
                    .orElseThrow(() -> new RuntimeException("Ürün bulunamadı: " + productInfo.getProductId()));

            if (productStock - productInfo.getQuantity() < 0) {
                log.error("ürün db de istenilen kadar yok ürün id: {} adedi: {}", productInfo.getProductId(), productInfo.getQuantity());
                throw new RuntimeException("Sepetteki ürünlerden en az bir tanesinin stoğu istenilen kadar yoktur.");
            }
        });
    }
    public Order doOrder(OrderRequest orderRequest) {
        log.info("order isteği geldi time: {} customer : {}", LocalDateTime.now(), orderRequest.getCustomerId());
        //MapStruct kütüphanesini araştıralım ve örnek yapalım.
        productUnitStockCheck(orderRequest.getOrderProductInfoList());
        Order order = new Order();
        order.setCustomerId(orderRequest.getCustomerId());
        order.setOrderDate(LocalDateTime.now());
        Order ordered = orderRepository.save(order);
        //orderDetailRepository işlemleri için queue mekanizması yazılabilir.
        //Yani ben ordered'ı return deyip, detail işlemleri queue ile yaparım.
        orderRequest.getOrderProductInfoList().forEach(e -> {
            //Double price = productRepository.findProductPriceById(e.getProductId());
            Optional<Product> product = productRepository.findById(e.getProductId());
            if(product.isPresent()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setPrice(product.get().getPrice());
                orderDetail.setQuantity(e.getQuantity());
                orderDetail.setProductId(e.getProductId());
                orderDetail.setOrderId(ordered.getId());
                orderDetailRepository.save(orderDetail);
                Long lastUnitInStock = product.get().getUnitsInStock();
                if(lastUnitInStock - e.getQuantity() == 0) {
                    product.get().setActive(false);
                }
                //TODO: Eğer fron-end olur da elimizdeki ürün adedinden daha fazlasını satın almak istediği quantity yollarsa hata atın.(if condition ve new throw Error)
                /**
                 * if(lastUnitInStock (5) - e.getQuantity()(10) < 0) {
                 */
                product.get().setUnitsInStock(lastUnitInStock - e.getQuantity());
                productRepository.save(product.get());
            }
        });
        return ordered;
    }
}
