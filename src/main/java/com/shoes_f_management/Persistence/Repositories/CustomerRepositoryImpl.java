package com.shoes_f_management.Persistence.Repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.shoes_f_management.Domain.DTOs.Requests.CustomerRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.CustomerResponseDTO;
import com.shoes_f_management.Domain.Exceptions.NotFoundException;
import com.shoes_f_management.Domain.Repositories.CustomerRepository;
import com.shoes_f_management.Persistence.CRUDs.CustomerCRUD;
import com.shoes_f_management.Persistence.Mappers.CustomerMapper;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository  {
    
    private final CustomerCRUD customerCRUD;
    private final CustomerMapper customerMapper;


    public CustomerRepositoryImpl(CustomerCRUD customerCRUD, CustomerMapper customerMapper) {
        this.customerCRUD = customerCRUD;
        this.customerMapper = customerMapper;
    }


    @Override
    public List<CustomerResponseDTO> getAll() {
        var customers = customerCRUD.findAll();
        return customerMapper.toDTOs(customers);
    }


    @Override
    public CustomerResponseDTO getById(Long id) {
        var customer = customerCRUD.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
        return customerMapper.toDTO(customer);
    }


    @Override
    public CustomerResponseDTO create(CustomerRequestDto entity) {
        var customer = customerMapper.toEntity(entity);
        var savedCustomer = customerCRUD.save(customer);
        return customerMapper.toDTO(savedCustomer);
    }


    @Override
    public CustomerResponseDTO update(Long id, CustomerRequestDto entity) {
        var existingCustomer = customerCRUD.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
        customerMapper.updateEntityFromDTO(entity, existingCustomer);
        var updatedCustomer = customerCRUD.save(existingCustomer);
        return customerMapper.toDTO(updatedCustomer);
    }


    @Override
    public void delete(Long id) {
        var customer = customerCRUD.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
        customerCRUD.delete(customer);
    }

    
    
}
