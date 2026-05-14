package com.shoes_f_management.Persistence.Mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.shoes_f_management.Domain.DTOs.Requests.SaleRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.SaleResponseDTO;
import com.shoes_f_management.Persistence.Models.Sale;

@Mapper(componentModel = "spring", uses = {SaleItemMapper.class})
public interface SaleMapper {

    List<SaleResponseDTO> toDTOs(List<Sale> sales);

    @Mapping(source = "customer.id", target = "customerId")
    SaleResponseDTO toDTO(Sale sale);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "saleItems", ignore = true)
    Sale toEntity(SaleRequestDto request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "saleItems", ignore = true)
    void updateEntityFromDTO(SaleRequestDto request, @MappingTarget Sale sale);
}
