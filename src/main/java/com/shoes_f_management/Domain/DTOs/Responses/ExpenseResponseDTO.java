package com.shoes_f_management.Domain.DTOs.Responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseResponseDTO(
    Long id,
    String description,
    BigDecimal amount,
    String category,
    LocalDateTime date
) {}
