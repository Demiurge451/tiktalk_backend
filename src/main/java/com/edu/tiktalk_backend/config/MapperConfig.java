package com.edu.tiktalk_backend.config;

import com.edu.tiktalk_backend.mapper.IdMapper;
import com.edu.tiktalk_backend.model.id_container.IdContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public <T extends IdContainer<ID>, ID> IdMapper<T, ID> idMapper() {
        return new IdMapper<>();
    }
}
