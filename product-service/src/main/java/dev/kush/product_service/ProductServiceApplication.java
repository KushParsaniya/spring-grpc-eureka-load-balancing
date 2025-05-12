package dev.kush.product_service;

import dev.kush.product_service.product.Product;
import dev.kush.product_service.product.ProductRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }


//    @Bean
    ApplicationRunner applicationRunner(ProductRepository productRepository) {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                Product product = new Product(1L, "","Product 1", 10, 100.0);
                productRepository.save(product);
            }
        };
    }
}
