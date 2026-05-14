package com.shoes_f_management.Domain.Services;

import com.shoes_f_management.Domain.DTOs.Requests.SaleItemRequestDto;
import com.shoes_f_management.Domain.DTOs.Requests.SaleRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.SaleResponseDTO;
import com.shoes_f_management.Domain.Exceptions.NotFoundException;
import com.shoes_f_management.Persistence.CRUDs.CustomerCRUD;
import com.shoes_f_management.Persistence.CRUDs.PaymentCRUD;
import com.shoes_f_management.Persistence.CRUDs.SaleCRUD;
import com.shoes_f_management.Persistence.CRUDs.ShoeCRUD;
import com.shoes_f_management.Persistence.Mappers.SaleItemMapper;
import com.shoes_f_management.Persistence.Mappers.SaleMapper;
import com.shoes_f_management.Persistence.Models.Sale;
import com.shoes_f_management.Persistence.Models.SaleItem;
import com.shoes_f_management.Persistence.Repositories.SaleRepositoryImpl;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService implements ServiceInterface<SaleResponseDTO, SaleRequestDto> {

    private final SaleRepositoryImpl saleRepository;
    private final SaleCRUD saleCRUD;
    private final CustomerCRUD customerCRUD;
    private final ShoeCRUD shoeCRUD;
    private final PaymentCRUD paymentCRUD;
    private final SaleMapper saleMapper;
    private final SaleItemMapper saleItemMapper;

    public SaleService(SaleRepositoryImpl saleRepository,
                       SaleCRUD saleCRUD,
                       CustomerCRUD customerCRUD,
                       ShoeCRUD shoeCRUD,
                       PaymentCRUD paymentCRUD,
                       SaleMapper saleMapper,
                       SaleItemMapper saleItemMapper) {
        this.saleRepository = saleRepository;
        this.saleCRUD = saleCRUD;
        this.customerCRUD = customerCRUD;
        this.shoeCRUD = shoeCRUD;
        this.paymentCRUD = paymentCRUD;
        this.saleMapper = saleMapper;
        this.saleItemMapper = saleItemMapper;
    }

    @Override
    public List<SaleResponseDTO> getAll() {
        var sales = saleRepository.getAll();
        if (sales.isEmpty()) {
            throw new NotFoundException("No sales found");
        }
        return sales;
    }

    @Override
    public SaleResponseDTO getById(Long id) {
        return saleRepository.getById(id);
    }

    @Override
    public SaleResponseDTO create(SaleRequestDto request) {
        var customer = customerCRUD.findById(request.customerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        var sale = saleMapper.toEntity(request);
        sale.setCustomer(customer);
        sale.setCreatedAt(LocalDateTime.now());
        sale.setModality(request.modality() != null ? request.modality() : Sale.Modality.CASH);
        sale.setPaymentMethod(request.paymentMethod() != null ? request.paymentMethod() : Sale.PaymentMethod.CASH);
        sale.setNotes(request.notes());

        List<SaleItem> items = request.saleItems() != null ? buildSaleItems(sale, request.saleItems()) : new ArrayList<>();
        sale.setSaleItems(items);
        refreshSaleTotals(sale);


        var savedSale = saleCRUD.save(sale);
        return saleMapper.toDTO(savedSale);
    }

    @Override
    public SaleResponseDTO update(Long id, SaleRequestDto request) {
        var sale = saleCRUD.findById(id).orElseThrow(() -> new NotFoundException("Sale not found"));

        if (request.customerId() != null) {
            var customer = customerCRUD.findById(request.customerId())
                    .orElseThrow(() -> new NotFoundException("Customer not found"));
            sale.setCustomer(customer);
        }

        if (request.modality() != null) {
            sale.setModality(request.modality());
        }

        if (request.paymentMethod() != null) {
            sale.setPaymentMethod(request.paymentMethod());
        }

        if (request.notes() != null) {
            sale.setNotes(request.notes());
        }

        if (request.saleItems() != null) {
            if (!paymentCRUD.findBySaleId(id).isEmpty()) {
                throw new IllegalStateException("Cannot update sale items after payments have been recorded");
            }
            restoreStockForSaleItems(sale.getSaleItems());
            var newItems = buildSaleItems(sale, request.saleItems());
            sale.getSaleItems().clear();
            sale.getSaleItems().addAll(newItems);
        }

        refreshSaleTotals(sale);
        var updatedSale = saleCRUD.save(sale);
        return saleMapper.toDTO(updatedSale);
    }

    @Override
    public void delete(Long id) {
        var sale = saleCRUD.findById(id).orElseThrow(() -> new NotFoundException("Sale not found"));
        paymentCRUD.deleteBySaleId(id);
        saleCRUD.delete(sale);
    }

    private List<SaleItem> buildSaleItems(Sale sale, List<SaleItemRequestDto> requestItems) {
        var items = new ArrayList<SaleItem>();
        for (var itemRequest : requestItems) {
            if (itemRequest.quantity() == null || itemRequest.quantity() <= 0) {
                throw new IllegalArgumentException("SaleItem quantity must be greater than zero");
            }
            var shoe = shoeCRUD.findById(itemRequest.shoeId())
                    .orElseThrow(() -> new NotFoundException("Shoe not found"));

            if (shoe.getInitialStock() < itemRequest.quantity()) {
                throw new IllegalArgumentException("Insufficient stock for shoe id " + itemRequest.shoeId());
            }

            var item = saleItemMapper.toEntity(itemRequest);
            item.setSale(sale);
            item.setShoe(shoe);
            if (item.getUnitPrice() == null || item.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                item.setUnitPrice(shoe.getRetailPrice());
            }

            shoe.setInitialStock(shoe.getInitialStock() - item.getQuantity());
            shoeCRUD.save(shoe);
            items.add(item);
        }
        return items;
    }

    private void restoreStockForSaleItems(List<SaleItem> saleItems) {
        if (saleItems == null) {
            return;
        }
        for (var item : saleItems) {
            var shoe = item.getShoe();
            if (shoe != null) {
                shoe.setInitialStock(shoe.getInitialStock() + item.getQuantity());
                shoeCRUD.save(shoe);
            }
        }
    }

    private void refreshSaleTotals(Sale sale) {
        var total = sale.getSaleItems() == null || sale.getSaleItems().isEmpty()
                ? BigDecimal.ZERO
                : sale.getSaleItems().stream()
                        .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        sale.setTotal(total);

        if (sale.getModality() == Sale.Modality.CASH) {
            sale.setPendingBalance(BigDecimal.ZERO);
            sale.setStatus(Sale.Status.PAID);
            return;
        }

        if (sale.getPendingBalance() == null || sale.getPendingBalance().compareTo(BigDecimal.ZERO) <= 0) {
            sale.setPendingBalance(total);
        }

        if (sale.getPendingBalance().compareTo(BigDecimal.ZERO) == 0) {
            sale.setStatus(Sale.Status.PAID);
        } else if (sale.getPendingBalance().compareTo(total) == 0) {
            sale.setStatus(Sale.Status.PENDING);
        } else {
            sale.setStatus(Sale.Status.PARTIAL);
        }
    }

}
