package com.shoes_f_management.Persistence.Repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.shoes_f_management.Domain.DTOs.Requests.SaleRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.SaleResponseDTO;
import com.shoes_f_management.Domain.Exceptions.NotFoundException;
import com.shoes_f_management.Domain.Repositories.SaleRepository;
import com.shoes_f_management.Persistence.CRUDs.SaleCRUD;
import com.shoes_f_management.Persistence.Mappers.SaleMapper;

@Repository
public class SaleRepositoryImpl implements SaleRepository {

    private final SaleCRUD saleCRUD;
    private final SaleMapper saleMapper;

    public SaleRepositoryImpl(SaleCRUD saleCRUD, SaleMapper saleMapper) {
        this.saleCRUD = saleCRUD;
        this.saleMapper = saleMapper;
    }

    @Override
    public List<SaleResponseDTO> getAll() {
        var sales = saleCRUD.findAll();
        return saleMapper.toDTOs(sales);
    }

    @Override
    public SaleResponseDTO getById(Long id) {
        var sale = saleCRUD.findById(id).orElseThrow(() -> new NotFoundException("Sale not found"));
        return saleMapper.toDTO(sale);
    }

    @Override
    public SaleResponseDTO create(SaleRequestDto request) {
        var sale = saleMapper.toEntity(request);
        var savedSale = saleCRUD.save(sale);
        return saleMapper.toDTO(savedSale);
    }

    @Override
    public SaleResponseDTO update(Long id, SaleRequestDto request) {
        var existingSale = saleCRUD.findById(id).orElseThrow(() -> new NotFoundException("Sale not found"));
        saleMapper.updateEntityFromDTO(request, existingSale);
        var updatedSale = saleCRUD.save(existingSale);
        return saleMapper.toDTO(updatedSale);
    }

    @Override
    public void delete(Long id) {
        var sale = saleCRUD.findById(id).orElseThrow(() -> new NotFoundException("Sale not found"));
        saleCRUD.delete(sale);
    }
}
