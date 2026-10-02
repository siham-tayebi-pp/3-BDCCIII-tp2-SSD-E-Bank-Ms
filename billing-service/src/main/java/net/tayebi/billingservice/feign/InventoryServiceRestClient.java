package net.tayebi.billingservice.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.tayebi.billingservice.model.Customer;
import net.tayebi.billingservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("inventory-service")
public interface InventoryServiceRestClient {
//    on envoie au servcice customer-service"  le id  lui retrn customer
    @GetMapping("/products/{id}")
    @CircuitBreaker(name = "inventory-service",fallbackMethod = "getDefaultProduct")

    Product findProductById(@PathVariable  Long id);
    default Product getDefaultProduct(Long id,Exception exception){
        Product product=new Product();
        product.setId(id);
        product.setName("default product");
        product.setPrice(-1);
        product.setQuantity(-1);
        return product;
    };
}
