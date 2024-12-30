package com.opticalarc.secure_web_app.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
