package com.shoes_f_management.Domain.DTOs.Requests;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.shoes_f_management.Persistence.Models.Sale;

public record SaleRequestDto(
    Long customerId,
    BigDecimal total,
    Sale.Modality modality,
    Sale.PaymentMethod paymentMethod,
    String notes,
    List<SaleItemRequestDto> saleItems
) {}
