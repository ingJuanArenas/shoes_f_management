package com.shoes_f_management.Persistence.CRUDs;

import com.shoes_f_management.Persistence.Models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseCRUD extends JpaRepository<Expense, Long> {

}
