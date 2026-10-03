package net.tayebi.customerservice;

import net.tayebi.customerservice.config.CustomerConfigParams;
import net.tayebi.customerservice.entities.Customer;
import net.tayebi.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
@EnableConfigurationProperties(CustomerConfigParams.class)
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(CustomerRepository repository, CustomerRepository customerRepository) {
        return args -> {
            customerRepository.save(Customer.builder().name("siham") .email("siham@gmail.com").build());
            customerRepository.save(Customer.builder().name("imane") .email("imane@gmail.com").build());
            customerRepository.save(Customer.builder().name("fouzia") .email("fouzia@gmail.com").build());
            customerRepository.save(Customer.builder().name("hamid") .email("hamid@gmail.com").build());



        };
    }

}
