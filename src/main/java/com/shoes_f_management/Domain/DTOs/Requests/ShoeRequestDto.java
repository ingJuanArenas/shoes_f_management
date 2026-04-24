package com.shoes_f_management.Domain.DTOs.Requests;

import java.math.BigDecimal;

public record ShoeRequestDto(
    String reference,
    String modelName,
    String color,
    Double size,
    Integer initialStock,
    BigDecimal costPrice,
    BigDecimal retailPrice,
    BigDecimal wholesalePrice
) {}
