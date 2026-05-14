package com.shoes_f_management.Persistence.Repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.shoes_f_management.Domain.DTOs.Requests.PaymentRequestDto;
import com.shoes_f_management.Domain.DTOs.Responses.PaymentResponseDTO;
import com.shoes_f_management.Domain.Exceptions.NotFoundException;
import com.shoes_f_management.Domain.Repositories.PaymentRepository;
import com.shoes_f_management.Persistence.CRUDs.PaymentCRUD;
import com.shoes_f_management.Persistence.Mappers.PaymentMapper;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentCRUD paymentCRUD;
    private final PaymentMapper paymentMapper;

    public PaymentRepositoryImpl(PaymentCRUD paymentCRUD, PaymentMapper paymentMapper) {
        this.paymentCRUD = paymentCRUD;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public List<PaymentResponseDTO> getAll() {
        var payments = paymentCRUD.findAll();
        return paymentMapper.toDTOs(payments);
    }

    @Override
    public PaymentResponseDTO getById(Long id) {
        var payment = paymentCRUD.findById(id).orElseThrow(() -> new NotFoundException("Payment not found"));
        return paymentMapper.toDTO(payment);
    }

    @Override
    public PaymentResponseDTO create(PaymentRequestDto request) {
        var payment = paymentMapper.toEntity(request);
        var savedPayment = paymentCRUD.save(payment);
        return paymentMapper.toDTO(savedPayment);
    }

    @Override
    public PaymentResponseDTO update(Long id, PaymentRequestDto request) {
        var existingPayment = paymentCRUD.findById(id).orElseThrow(() -> new NotFoundException("Payment not found"));
        paymentMapper.updateEntityFromDTO(request, existingPayment);
        var updatedPayment = paymentCRUD.save(existingPayment);
        return paymentMapper.toDTO(updatedPayment);
    }

    @Override
    public void delete(Long id) {
        var payment = paymentCRUD.findById(id).orElseThrow(() -> new NotFoundException("Payment not found"));
        paymentCRUD.delete(payment);
    }
}
