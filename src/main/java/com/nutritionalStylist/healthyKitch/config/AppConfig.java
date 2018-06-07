package com.nutritionalStylist.healthyKitch.config;


import com.nutritionalStylist.healthyKitch.service.NoEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
public class AppConfig {

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/HealthyKitch?useSSL=false");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("games");
        return driverManagerDataSource;
    }

    @Bean
    public NoEncoder noEncoder(){
        return new NoEncoder();
    }

}
