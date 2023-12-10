package com.x.ecommerce.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String token;
    private Long customerId;
}
