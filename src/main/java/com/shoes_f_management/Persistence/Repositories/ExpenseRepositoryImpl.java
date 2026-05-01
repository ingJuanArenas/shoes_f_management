package com.shoes_f_management.Persistence.Repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.shoes_f_management.Domain.DTOs.Requests.ExpenseRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.ExpenseResponseDTO;
import com.shoes_f_management.Domain.Repositories.ExpenseRepository;
import com.shoes_f_management.Persistence.CRUDs.ExpenseCRUD;
import com.shoes_f_management.Persistence.Mappers.ExpenseMapper;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository {

    private final ExpenseCRUD expenseCRUD;
    private final ExpenseMapper expenseMapper;

    

    public ExpenseRepositoryImpl(ExpenseCRUD expenseCRUD, ExpenseMapper expenseMapper) {
        this.expenseCRUD = expenseCRUD;
        this.expenseMapper = expenseMapper;
    }

    @Override
    public List<ExpenseResponseDTO> getAll() {
        var expenses = expenseCRUD.findAll();
        return expenseMapper.toDTOs(expenses);
    }

    @Override
    public ExpenseResponseDTO getById(Long id) {
       var expense = expenseCRUD.findById(id).orElseThrow(() -> new IllegalArgumentException("Expense not found"));
       return expenseMapper.toDTO(expense);
    }

    @Override
    public ExpenseResponseDTO create(ExpenseRequestDto entity) {
        var expense = expenseMapper.toEntity(entity);
        return expenseMapper.toDTO(expenseCRUD.save(expense));
    }

    @Override
    public ExpenseResponseDTO update(Long id, ExpenseRequestDto entity) {
        var expense = expenseCRUD.findById(id).orElseThrow(() -> new IllegalArgumentException("Expense not found"));
        expenseMapper.updateEntityFromDTO(entity, expense);
        return expenseMapper.toDTO(expenseCRUD.save(expense));
    }

    @Override
    public void delete(Long id) {
       expenseCRUD.deleteById(id);
    }
    
}
