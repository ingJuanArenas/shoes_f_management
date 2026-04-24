package com.shoes_f_management.Domain.DTOs.Responses;

import java.math.BigDecimal;

public record ShoeResponseDTO(
    Long id,
    String reference,
    String modelName,
    String color,
    Double size,
    Integer initialStock,
    BigDecimal costPrice,
    BigDecimal retailPrice,
    BigDecimal wholesalePrice
) {}