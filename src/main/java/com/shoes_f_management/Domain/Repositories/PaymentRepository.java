package com.shoes_f_management.Domain.Repositories;

import com.shoes_f_management.Domain.DTOs.Requests.PaymentRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.PaymentResponseDTO;

public interface PaymentRepository extends Repository<PaymentResponseDTO, PaymentRequestDto> {
}
