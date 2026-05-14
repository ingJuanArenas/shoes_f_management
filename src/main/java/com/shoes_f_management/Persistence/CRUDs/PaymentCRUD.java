package com.shoes_f_management.Persistence.CRUDs;

import java.util.List;

import com.shoes_f_management.Persistence.Models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCRUD extends JpaRepository<Payment, Long> {
    List<Payment> findBySaleId(Long saleId);
    void deleteBySaleId(Long saleId);
}
