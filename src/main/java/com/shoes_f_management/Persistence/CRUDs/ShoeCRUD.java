package com.shoes_f_management.Persistence.CRUDs;

import com.shoes_f_management.Persistence.Models.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShoeCRUD extends JpaRepository<Shoe, Long> {
    
}
