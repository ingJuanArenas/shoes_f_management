package com.shoes_f_management.Domain.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shoes_f_management.Domain.DTOs.Requests.ExpenseRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.ExpenseResponseDTO;
import com.shoes_f_management.Domain.Exceptions.NotFoundException;
import com.shoes_f_management.Persistence.Repositories.ExpenseRepositoryImpl;

@Service
public class ExpenseService implements ServiceInterface<ExpenseResponseDTO,ExpenseRequestDto> {

    private final ExpenseRepositoryImpl expenseRepository;

    public ExpenseService(ExpenseRepositoryImpl expenseRepository) {
        this.expenseRepository = expenseRepository;
     }

    @Override
    public List<ExpenseResponseDTO> getAll() {
       var expenses =  expenseRepository.getAll();
       if (expenses.isEmpty()) throw new NotFoundException("No expenses found");

      return expenses;
    }

    @Override
    public ExpenseResponseDTO getById(Long id) {
        return expenseRepository.getById(id);
    }

    @Override
    public ExpenseResponseDTO create(ExpenseRequestDto request) {
        return expenseRepository.create(request);
    }

    @Override
    public ExpenseResponseDTO update(Long id, ExpenseRequestDto request) {
        return expenseRepository.update(id, request);
    }

    @Override
    public void delete(Long id) {
        expenseRepository.delete(id);
    }

     
    


    
}
