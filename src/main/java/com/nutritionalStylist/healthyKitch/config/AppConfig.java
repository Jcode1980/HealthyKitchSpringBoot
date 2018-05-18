package com.nutritionalStylist.healthyKitch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("application.properties")
public class AppConfig {

    @Value("${com.nutritionalStylist.FILES_PRODUCTION_FOLDER}")
    private String productionFolder;
    @Value("${com.nutritionalStylist.ROOT_FOLDER}")
    private String rootFolder;
    @Value("${com.nutritionalStylist.TEMP_FOLDER}")
    private String tmpFolder;

    @Bean
    public AppConfig appConfig(){
      return appConfig();
    }

}
