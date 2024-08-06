package com.bankEasy.accounts.mapper;


import com.bankEasy.accounts.dto.CustomerDto;
import com.bankEasy.accounts.entity.Customer;

// mapper to handle Dto to Entity conversion and vice versa
public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(CustomerDto customerDto, Customer customer){
        customerDto.setEmail(customer.getEmail());
        customerDto.setName(customer.getName());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }
    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer){
        customer.setEmail(customerDto.getEmail());
        customer.setName(customerDto.getName());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}
