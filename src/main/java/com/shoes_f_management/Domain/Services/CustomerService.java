package com.shoes_f_management.Domain.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shoes_f_management.Domain.DTOs.Requests.CustomerRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.CustomerResponseDTO;
import com.shoes_f_management.Domain.Exceptions.NotFoundException;
import com.shoes_f_management.Persistence.Repositories.CustomerRepositoryImpl;


@Service
public class CustomerService implements ServiceInterface<CustomerResponseDTO,CustomerRequestDto> {

    private final CustomerRepositoryImpl customerRepository;

    public CustomerService(CustomerRepositoryImpl customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerResponseDTO> getAll() {
      var customers = customerRepository.getAll();
      if (customers.isEmpty()) throw new NotFoundException("No customers found");

      return customers;
    }

    @Override
    public CustomerResponseDTO getById(Long id) {
       return customerRepository.getById(id);
    }

    @Override
    public CustomerResponseDTO create(CustomerRequestDto request) {
       return customerRepository.create(request);
    }

    @Override
    public CustomerResponseDTO update(Long id, CustomerRequestDto request) {
        return customerRepository.update(id, request);
    }

    @Override
    public void delete(Long id) {
       customerRepository.delete(id);
    }

    




   
}
