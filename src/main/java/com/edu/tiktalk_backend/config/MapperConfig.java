package com.edu.tiktalk_backend.config;

import com.edu.tiktalk_backend.mapper.IdMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public IdMapper idMapper() {
        return new IdMapper();
    }
}
