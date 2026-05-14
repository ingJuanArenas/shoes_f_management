package com.shoes_f_management.Persistence.CRUDs;

import com.shoes_f_management.Persistence.Models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleCRUD extends JpaRepository<Sale, Long> {
}
