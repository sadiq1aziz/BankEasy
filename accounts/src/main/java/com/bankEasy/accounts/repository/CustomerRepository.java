package com.bankEasy.accounts.repository;

import com.bankEasy.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


//as part of Spring JPA we utilize repo to effect changes into Schema via CRUD operations
//This is possible without having to write  SQL queries, as all methods are provided by the JPA
//framework. We create an interface extending JPA repo with the Entity and the Id datatype reference
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // use spring JPA to create name based methods wherein the method signature itself can
    // be used to create the method
    public Optional<Customer> findByMobileNumber(String phoneNumber);

    //find customer details based on customerId
    public Optional<Customer> findByCustomerId (Long customerId);

}
