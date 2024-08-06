package com.bankeasy.loans.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// Loans is the entity that we create in DB with JPA
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Loans extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    private String loanNumber;

    private String mobileNumber;

    private String loanType;

    private int totalAmount;

    private int outstandingAmount;

    private int paidAmount;

}
