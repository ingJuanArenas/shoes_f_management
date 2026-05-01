package com.shoes_f_management.Persistence.CRUDs;

import com.shoes_f_management.Persistence.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCRUD extends JpaRepository<Customer, Long> {

}
