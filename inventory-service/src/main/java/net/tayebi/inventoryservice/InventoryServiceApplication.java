package net.tayebi.inventoryservice;

import net.tayebi.inventoryservice.entities.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
          productRepository.save(Product.builder()
                          .name("Computer")
                          .price(8000)
                          .quantity(12)
                          .build());
            productRepository.save(Product.builder()
                    .name("Printer")
                    .price(3000)
                    .quantity(5)
                    .build());
            productRepository.save(Product.builder()
                    .name("Smartphone")
                    .price(10000)
                    .quantity(10)
                    .build());
            productRepository.save(Product.builder()
                    .name("Mouse")
                    .price(100)
                    .quantity(30)
                    .build());

        };

    }

}
