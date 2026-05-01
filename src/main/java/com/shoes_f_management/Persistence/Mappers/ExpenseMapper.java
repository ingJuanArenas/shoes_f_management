package com.shoes_f_management.Persistence.Mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.shoes_f_management.Domain.DTOs.Requests.ExpenseRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.ExpenseResponseDTO;
import com.shoes_f_management.Persistence.Models.Expense;



@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    List<ExpenseResponseDTO> toDTOs(List<Expense> expenses);
    ExpenseResponseDTO toDTO(Expense Expense);

    @Mapping(target = "id", ignore = true)
    Expense toEntity(ExpenseRequestDto expenseRequestDto);
     @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ExpenseRequestDto request,@MappingTarget Expense expense);
}
