package com.bankEasy.accounts.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Customer extends BaseEntity {

    @Id
    // ensure that this field value is dynamically generated by the JPA framework
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // ensure that generated Id can be handled by any native SQL framework
    @Column(name = "customer_id")
    private long customerId;

    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    private String name;

}
