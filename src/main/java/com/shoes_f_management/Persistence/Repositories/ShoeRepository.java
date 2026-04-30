package com.shoes_f_management.Persistence.Repositories;

import com.shoes_f_management.Persistence.Models.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoeRepository extends JpaRepository<Shoe, Long> {
}
