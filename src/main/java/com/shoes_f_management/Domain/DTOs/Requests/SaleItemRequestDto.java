package com.shoes_f_management.Domain.DTOs.Requests;

import java.math.BigDecimal;

public record SaleItemRequestDto(
    Long saleId,
    Long shoeId,
    Integer quantity,
    BigDecimal unitPrice
) {}
