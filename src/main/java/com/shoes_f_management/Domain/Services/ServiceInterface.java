package com.shoes_f_management.Domain.Services;

import java.util.List;



public interface ServiceInterface <D,R> {
    List<D> getAll() ;
    D getById(Long id) ;
    D create(R request) ;  
    D update(Long id, R request) ;  
    void delete(Long id) ;
        
}
