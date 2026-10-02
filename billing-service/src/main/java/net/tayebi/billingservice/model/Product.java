package net.tayebi.billingservice.model;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class Product {
    private Long id;
    private String name;
    private double price;
    private int quantity;
}
