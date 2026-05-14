package com.shoes_f_management.Persistence.CRUDs;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoes_f_management.Web.Config.Model.UserEntity;

public interface UserEntityCRUD extends JpaRepository<UserEntity,Long>{
    // find by username
   Optional<UserEntity> findByUsername(String username);
}
