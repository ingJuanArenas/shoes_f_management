package com.shoes_f_management.Web.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shoes_f_management.Domain.Exceptions.NotFoundException;
import com.shoes_f_management.Persistence.CRUDs.UserEntityCRUD;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserEntityCRUD userEntityCRUD;

    public UserDetailsService(UserEntityCRUD userEntityCRUD) {
        this.userEntityCRUD = userEntityCRUD;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      var userFound= userEntityCRUD.findByUsername(username).orElseThrow(()-> new NotFoundException("Username not found"));
      return User.builder().username(userFound.getUsername())
                            .password(userFound.getPassword())
                            .roles(userFound.getRole().toString())
                            .build();
    }
    
}
