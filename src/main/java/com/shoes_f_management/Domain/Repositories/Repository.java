package com.shoes_f_management.Domain.Repositories;

import java.util.List;


public interface Repository<RS,RQ> {
    
    List<RS> getAll();
    RS getById(Long id);
    RS create(RQ entity);
    RS update(Long id, RQ entity);
    void delete(Long id);
}
