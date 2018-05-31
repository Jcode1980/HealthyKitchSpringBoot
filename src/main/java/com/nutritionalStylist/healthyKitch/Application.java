package com.nutritionalStylist.healthyKitch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.util.Properties;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        setSystemProperties();

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