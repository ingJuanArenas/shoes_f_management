package com.shoes_f_management.Domain.Repositories;

import com.shoes_f_management.Domain.DTOs.Requests.CustomerRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.CustomerResponseDTO;

public interface CustomerRepository extends Repository<CustomerResponseDTO,CustomerRequestDto> {
    
}
