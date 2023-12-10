package com.x.ecommerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private Long customerId;

    private List<OrderProductInfo> orderProductInfoList;

}
