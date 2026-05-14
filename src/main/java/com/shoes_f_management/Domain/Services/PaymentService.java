package com.shoes_f_management.Domain.Services;

import com.shoes_f_management.Domain.DTOs.Requests.PaymentRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.PaymentResponseDTO;
import com.shoes_f_management.Domain.Exceptions.NotFoundException;
import com.shoes_f_management.Persistence.CRUDs.PaymentCRUD;
import com.shoes_f_management.Persistence.CRUDs.SaleCRUD;
import com.shoes_f_management.Persistence.Mappers.PaymentMapper;
import com.shoes_f_management.Persistence.Models.Payment;
import com.shoes_f_management.Persistence.Models.Sale;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService implements ServiceInterface<PaymentResponseDTO, PaymentRequestDto> {

    private final PaymentCRUD paymentCRUD;
    private final SaleCRUD saleCRUD;
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentCRUD paymentCRUD,
                          SaleCRUD saleCRUD,
                          PaymentMapper paymentMapper) {
        this.paymentCRUD = paymentCRUD;
        this.saleCRUD = saleCRUD;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public List<PaymentResponseDTO> getAll() {
        return paymentMapper.toDTOs(paymentCRUD.findAll());
    }

    @Override
    public PaymentResponseDTO getById(Long id) {
        var payment = paymentCRUD.findById(id).orElseThrow(() -> new NotFoundException("Payment not found"));
        return paymentMapper.toDTO(payment);
    }

    @Override
    public PaymentResponseDTO create(PaymentRequestDto request) {
        var sale = saleCRUD.findById(request.saleId()).orElseThrow(() -> new NotFoundException("Sale not found"));
        if (request.amount() == null || request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero");
        }
        if (sale.getPendingBalance() == null || sale.getPendingBalance().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Sale has no pending balance");
        }
        if (request.amount().compareTo(sale.getPendingBalance()) > 0) {
            throw new IllegalArgumentException("Payment amount exceeds pending balance");
        }

        var payment = paymentMapper.toEntity(request);
        payment.setSale(sale);
        payment.setPaymentDate(LocalDateTime.now());
        if (request.paymentMethod() != null) {
            payment.setPaymentMethod(request.paymentMethod());
        }

        var savedPayment = paymentCRUD.save(payment);
        updateSaleBalance(sale, request.amount());
        saleCRUD.save(sale);
        return paymentMapper.toDTO(savedPayment);
    }

    @Override
    public PaymentResponseDTO update(Long id, PaymentRequestDto request) {
        var payment = paymentCRUD.findById(id).orElseThrow(() -> new NotFoundException("Payment not found"));
        var sale = payment.getSale();
        if (sale == null) {
            throw new NotFoundException("Sale not found for payment");
        }
        if (request.saleId() != null && !request.saleId().equals(sale.getId())) {
            throw new IllegalArgumentException("Cannot change sale on an existing payment");
        }
        if (request.amount() == null || request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero");
        }

        var currentAmount = payment.getAmount();
        var amountDifference = request.amount().subtract(currentAmount);
        if (amountDifference.compareTo(BigDecimal.ZERO) > 0 && amountDifference.compareTo(sale.getPendingBalance()) > 0) {
            throw new IllegalArgumentException("Updated amount exceeds pending balance");
        }

        paymentMapper.updateEntityFromDTO(request, payment);
        payment.setPaymentDate(payment.getPaymentDate());
        payment.setPaymentMethod(request.paymentMethod() != null ? request.paymentMethod() : payment.getPaymentMethod());
        payment = paymentCRUD.save(payment);

        updateSaleBalance(sale, amountDifference);
        saleCRUD.save(sale);

        return paymentMapper.toDTO(payment);
    }

    @Override
    public void delete(Long id) {
        var payment = paymentCRUD.findById(id).orElseThrow(() -> new NotFoundException("Payment not found"));
        var sale = payment.getSale();
        if (sale == null) {
            paymentCRUD.delete(payment);
            return;
        }
        sale.setPendingBalance(sale.getPendingBalance().add(payment.getAmount()));
        updateSaleStatus(sale);
        saleCRUD.save(sale);
        paymentCRUD.delete(payment);
    }

    private void updateSaleBalance(Sale sale, BigDecimal amountChange) {
        var pending = sale.getPendingBalance() == null ? BigDecimal.ZERO : sale.getPendingBalance().subtract(amountChange);
        sale.setPendingBalance(pending.max(BigDecimal.ZERO));
        updateSaleStatus(sale);
    }

    private void updateSaleStatus(Sale sale) {
        if (sale.getPendingBalance().compareTo(BigDecimal.ZERO) == 0) {
            sale.setStatus(Sale.Status.PAID);
        } else if (sale.getPendingBalance().compareTo(sale.getTotal()) == 0) {
            sale.setStatus(Sale.Status.PENDING);
        } else {
            sale.setStatus(Sale.Status.PARTIAL);
        }
    }
}
