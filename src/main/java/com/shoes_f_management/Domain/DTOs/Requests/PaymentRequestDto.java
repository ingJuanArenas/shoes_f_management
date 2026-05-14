package com.shoes_f_management.Domain.DTOs.Requests;

import java.math.BigDecimal;

import com.shoes_f_management.Persistence.Models.Payment;

public record PaymentRequestDto(
    Long saleId,
    BigDecimal amount,
    Payment.PaymentMethod paymentMethod,
    String notes
) {}
