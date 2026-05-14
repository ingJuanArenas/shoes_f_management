package com.shoes_f_management.Persistence.Mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.shoes_f_management.Domain.DTOs.Requests.SaleItemRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.SaleItemResponseDTO;
import com.shoes_f_management.Persistence.Models.SaleItem;

@Mapper(componentModel = "spring")
public interface SaleItemMapper {

    List<SaleItemResponseDTO> toDTOs(List<SaleItem> saleItems);

    @Mapping(source = "sale.id", target = "saleId")
    @Mapping(source = "shoe.id", target = "shoeId")
    SaleItemResponseDTO toDTO(SaleItem saleItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sale", ignore = true)
    @Mapping(target = "shoe", ignore = true)
    SaleItem toEntity(SaleItemRequestDto request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sale", ignore = true)
    @Mapping(target = "shoe", ignore = true)
    void updateEntityFromDTO(SaleItemRequestDto request, @MappingTarget SaleItem saleItem);
}
