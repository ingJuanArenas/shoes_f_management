package com.shoes_f_management.Domain.Repositories;

import com.shoes_f_management.Domain.DTOs.Requests.ShoeRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.ShoeResponseDTO;

public interface ShoeRepository extends Repository<ShoeResponseDTO,ShoeRequestDto> {
    
}
