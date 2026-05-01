package com.shoes_f_management.Persistence.Repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shoes_f_management.Domain.DTOs.Requests.ShoeRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.ShoeResponseDTO;
import com.shoes_f_management.Domain.Exceptions.NotFoundException;
import com.shoes_f_management.Domain.Repositories.ShoeRepository;
import com.shoes_f_management.Persistence.CRUDs.ShoeCRUD;
import com.shoes_f_management.Persistence.Mappers.ShoeMapper;

@Repository
public class ShoeRepositoryImpl implements ShoeRepository {

    @Autowired
    private  ShoeCRUD shoeCRUD;
    @Autowired
    private  ShoeMapper shoeMapper;

    @Override
    public List<ShoeResponseDTO> getAll() {
        var shoes = shoeCRUD.findAll();
        return shoeMapper.toDTOs(shoes);
    }

    @Override
    public ShoeResponseDTO getById(Long id) {
        var shoe = shoeCRUD.findById(id).orElseThrow(() -> new NotFoundException("Shoe not found"));
        return shoeMapper.toDTO(shoe);
    }

    @Override
    public ShoeResponseDTO create(ShoeRequestDto entity) {
        var shoe = shoeMapper.toEntity(entity);
        var savedShoe = shoeCRUD.save(shoe);
        return shoeMapper.toDTO(savedShoe);
    }

    @Override
    public ShoeResponseDTO update(Long id, ShoeRequestDto entity) {
        var shoe = shoeCRUD.findById(id).orElseThrow(() -> new NotFoundException("Shoe not found"));
        shoeMapper.updateEntityFromDTO(entity, shoe);
        var updatedShoe = shoeCRUD.save(shoe);
        return shoeMapper.toDTO(updatedShoe);
    }

    @Override
    public void delete(Long id) {
        shoeCRUD.deleteById(id);
    }
    
}
