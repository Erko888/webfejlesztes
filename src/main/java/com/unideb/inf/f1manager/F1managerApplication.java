package com.unideb.inf.f1manager;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class F1managerApplication {

	public static void main(String[] args) {
		SpringApplication.run(F1managerApplication.class, args);
	}
    @Bean
    ModelMapper modelMapper(){
        ModelMapper m = new ModelMapper();
        return m;
        //return new ModelMapper();
    }

}
