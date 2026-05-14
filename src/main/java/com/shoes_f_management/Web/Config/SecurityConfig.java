
package com.shoes_f_management.Web.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .httpBasic(Customizer.withDefaults())
            .formLogin(form -> form.disable())

            .authorizeHttpRequests(auth -> {
                auth.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN");
                auth.requestMatchers(HttpMethod.PUT).hasRole("ADMIN");
                auth.requestMatchers(HttpMethod.POST,"/api/expenses/").hasRole("ADMIN");
                auth.requestMatchers(HttpMethod.POST,"/api/auth/register").hasRole("ADMIN");

                auth.requestMatchers(HttpMethod.GET, "/api/shoes/**").permitAll();
                auth.anyRequest().authenticated();
            });


        return http.build();
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
