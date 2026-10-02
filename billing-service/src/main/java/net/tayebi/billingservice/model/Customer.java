package net.tayebi.billingservice.model;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customer {
    private String id;
    private String name;
    private String email;
}
