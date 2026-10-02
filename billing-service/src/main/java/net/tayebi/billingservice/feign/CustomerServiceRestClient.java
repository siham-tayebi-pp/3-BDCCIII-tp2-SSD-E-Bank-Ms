package net.tayebi.billingservice.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.tayebi.billingservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("customer-service")
public interface CustomerServiceRestClient {
//    on envoie au servcice customer-service"  le id  lui retrn customer
    @GetMapping("/customers/{id}")
    @CircuitBreaker(name = "customer-service",fallbackMethod = "getDefaultCustomer")
    Customer findCustomerById(@PathVariable  Long id);
    default Customer getDefaultCustomer(Long customerId,Exception exception){
        Customer customer = new Customer();
        customer.setId(String.valueOf(customerId));
        customer.setName("Default Customer");
        customer.setEmail("default@email.com");
        return customer;
    };
}
