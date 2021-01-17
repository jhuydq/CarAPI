package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;
import com.udacity.pricing.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * Creates a Spring Boot Application to run the Pricing Service.
 * TODO: Convert the application from a REST API to a microservice.
 */
@SpringBootApplication
@EnableEurekaClient
public class PricingServiceApplication {
    @Autowired
    private PriceRepository priceRepository;

    public static void main(String[] args) {
        SpringApplication.run(PricingServiceApplication.class, args);
    }

    //insert data into database to test
    @Bean
    public CommandLineRunner run() throws Exception{
        return args -> {
            for(int i = 0; i < 19; i++){
                long id = i+1;
                Price price = PricingService.getPrice(id);
                priceRepository.save(price);
            }
        };
    }

}
