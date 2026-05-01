package com.shoes_f_management.Persistence.Mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.shoes_f_management.Domain.DTOs.Requests.CustomerRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.CustomerResponseDTO;
import com.shoes_f_management.Persistence.Models.Customer;

@Mapper(componentModel= "spring")
public interface CustomerMapper {

    List<CustomerResponseDTO> toDTOs(List<Customer> customers);
    CustomerResponseDTO toDTO(Customer customer);

    @Mapping(target = "id", ignore = true)
    Customer toEntity(CustomerRequestDto customerRequestDto);
     @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(CustomerRequestDto request,@MappingTarget Customer customer);
    
}
