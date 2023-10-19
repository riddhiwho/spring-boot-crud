package com.example.demo.student;

import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            Student riddhi = new Student("Riddhi", "sharmariddhi81@gmail.com", LocalDate.of(1999, 10, 5));
            Student vishal = new Student("Vishal", "vishal.sharma@gmail.com", LocalDate.of(1997, 12, 17));

            repository.saveAll(
                List.of(riddhi, vishal)
            );
        };
    }
}
