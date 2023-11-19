package com.x.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class OrderRequest {

    private Long customerId;

    private List<OrderProductInfo> orderProductInfoList;

}
