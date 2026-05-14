package com.shoes_f_management.Domain.DTOs.Responses;

import java.math.BigDecimal;

public record SaleItemResponseDTO(
    Long id,
    Long saleId,
    Long shoeId,
    Integer quantity,
    BigDecimal unitPrice
) {}
