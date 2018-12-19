package com.nutritionalStylist.healthyKitch;

import com.nutritionalStylist.healthyKitch.controller.FileController;
import com.nutritionalStylist.healthyKitch.model.Mail;
import com.nutritionalStylist.healthyKitch.service.EmailServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    private static Logger log = Logger.getLogger(Application.class);

    @Autowired
    static EmailServiceImpl emailService;

    public static void main(String[] args) {
        log.debug("blah bah");
        SpringApplication.run(Application.class, args);
        setSystemProperties();

    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };

    }


    private static void setSystemProperties(){

        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "application.properties";

        Properties appProps = new Properties();
        try{
            appProps.load(new FileInputStream(appConfigPath));
            System.out.println("App properties are: " + appProps.stringPropertyNames());

            System.setProperty("com.nutritionalStylist.TMP_FOLDER", appProps.getProperty("com.nutritionalStylist.TMP_FOLDER"));
            System.setProperty("com.nutritionalStylist.FILES_PRODUCTION_FOLDER", appProps.getProperty("com.nutritionalStylist.FILES_PRODUCTION_FOLDER"));
            System.setProperty("com.nutritionalStylist.ROOT_FOLDER", appProps.getProperty("com.nutritionalStylist.ROOT_FOLDER"));


            System.out.println("Production file proprtly: " + appProps.getProperty("com.nutritionalStylist.TMP_FOLDER"));
            System.out.println("Production file proprtly: " + appProps.getProperty("com.nutritionalStylist.FILES_PRODUCTION_FOLDER"));
            System.out.println("Production file proprtly: " + appProps.getProperty("com.nutritionalStylist.ROOT_FOLDER"));


            System.out.println("System property is: " + System.getProperty("com.nutritionalStylist.TMP_FOLDER"));
        }catch(Exception e){
            e.printStackTrace();
        }
        }



}