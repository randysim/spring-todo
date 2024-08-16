package com.example.backend.config;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User randy = new User(
                    "randy",
                    "randysim9@gmail.com",
                    "password",
                    LocalDate.of(2024, Month.AUGUST, 14)
            );

            userRepository.saveAll(
                    List.of(randy)
            );
        };
    }
}
