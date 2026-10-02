package net.tayebi.inventoryservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok .*;

@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Product {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private double price;
    private int quantity;
}
