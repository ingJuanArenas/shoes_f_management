package com.shoes_f_management.Domain.DTOs.Requests;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseRequestDto(
    String description,
    BigDecimal amount,
    String category,
    LocalDateTime date
) {}
