package com.learn.ShopperStop.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopperConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
