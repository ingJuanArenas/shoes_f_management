package com.shoes_f_management.Domain.DTOs.Responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.shoes_f_management.Persistence.Models.Sale;

public record SaleResponseDTO(
    Long id,
    Long customerId,
    BigDecimal total,
    Sale.Modality modality,
    BigDecimal pendingBalance,
    Sale.Status status,
    Sale.PaymentMethod paymentMethod,
    String notes,
    LocalDateTime createdAt,
    List<SaleItemResponseDTO> saleItems
) {}
