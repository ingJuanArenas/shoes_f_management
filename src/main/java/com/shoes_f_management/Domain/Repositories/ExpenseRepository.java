package com.shoes_f_management.Domain.Repositories;

import com.shoes_f_management.Domain.DTOs.Requests.ExpenseRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.ExpenseResponseDTO;

public interface ExpenseRepository extends Repository<ExpenseResponseDTO,ExpenseRequestDto> {
    
}
