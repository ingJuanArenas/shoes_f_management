package com.shoes_f_management.Domain.DTOs.Responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.shoes_f_management.Persistence.Models.Payment;

public record PaymentResponseDTO(
    Long id,
    Long saleId,
    BigDecimal amount,
    Payment.PaymentMethod paymentMethod,
    String notes,
    LocalDateTime paymentDate
) {}
