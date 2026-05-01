package com.shoes_f_management.Persistence.Mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.shoes_f_management.Domain.DTOs.Requests.ShoeRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.ShoeResponseDTO;
import com.shoes_f_management.Persistence.Models.Shoe;

@Mapper(componentModel = "spring")
public interface ShoeMapper {

    List<ShoeResponseDTO> toDTOs(List<Shoe> shoes);

    ShoeResponseDTO toDTO(Shoe shoe);

    @Mapping(target = "id", ignore = true)
    Shoe toEntity(ShoeRequestDto shoeRequestDto);
     @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ShoeRequestDto request,@MappingTarget Shoe shoe);
    
    
}
