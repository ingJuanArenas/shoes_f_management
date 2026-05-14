package com.shoes_f_management.Persistence.Mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.shoes_f_management.Domain.DTOs.Requests.PaymentRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.PaymentResponseDTO;
import com.shoes_f_management.Persistence.Models.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    List<PaymentResponseDTO> toDTOs(List<Payment> payments);

    @Mapping(source = "sale.id", target = "saleId")
    PaymentResponseDTO toDTO(Payment payment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sale", ignore = true)
    Payment toEntity(PaymentRequestDto request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sale", ignore = true)
    void updateEntityFromDTO(PaymentRequestDto request, @MappingTarget Payment payment);
}
