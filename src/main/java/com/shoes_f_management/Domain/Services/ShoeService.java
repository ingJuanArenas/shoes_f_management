package com.shoes_f_management.Domain.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shoes_f_management.Domain.DTOs.Requests.ShoeRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.ShoeResponseDTO;
import com.shoes_f_management.Domain.Exceptions.NotFoundException;
import com.shoes_f_management.Persistence.Repositories.ShoeRepositoryImpl;

@Service
public class ShoeService implements ServiceInterface<ShoeResponseDTO,ShoeRequestDto> {
    
    private final ShoeRepositoryImpl shoeRepository;

    public ShoeService(ShoeRepositoryImpl shoeRepository) {
        this.shoeRepository = shoeRepository;
    }

    @Override
    public List<ShoeResponseDTO> getAll() {
        var shoes=  shoeRepository.getAll();
        if (shoes.isEmpty()) throw new NotFoundException("No shoes found");

      return shoes;
    }
    @Override
    public ShoeResponseDTO getById(Long id) {
        return shoeRepository.getById(id);
    }
    @Override
    public ShoeResponseDTO create(ShoeRequestDto request) {
        return shoeRepository.create(request);
    }

     @Override
    public ShoeResponseDTO update(Long id, ShoeRequestDto request) {
        return shoeRepository.update(id, request);
    }

     @Override
    public void delete(Long id) {
         shoeRepository.delete(id);
     
    }



}
