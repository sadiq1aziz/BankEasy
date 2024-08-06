package com.bankEasy.accounts.service.impl;

import com.bankEasy.accounts.dto.AccountsDto;
import com.bankEasy.accounts.dto.CustomerDto;
import com.bankEasy.accounts.entity.Accounts;
import com.bankEasy.accounts.exceptions.CustomerAlreadyExistsException;
import com.bankEasy.accounts.exceptions.ResourceNotFoundException;
import com.bankEasy.accounts.mapper.AccountsMapper;
import com.bankEasy.accounts.mapper.CustomerMapper;
import com.bankEasy.accounts.repository.AccountsRepository;
import com.bankEasy.accounts.repository.CustomerRepository;
import com.bankEasy.accounts.service.IAccountsService;
import com.bankEasy.accounts.constants.AccountsConstants;
import com.bankEasy.accounts.entity.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

// service layer to implement comms with the data layer and handle business logic
@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {

    //include Repositories as needed
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {

        //create a customer entity
        Customer customer = new Customer();
        Customer customerEntity = CustomerMapper.mapToCustomer(customerDto, customer);

        //check if customer already exists
        Optional<Customer> existingCustomer = customerRepository.findByMobileNumber(customerEntity.getMobileNumber());

        if (existingCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already exists with the following number : "+
                    existingCustomer.get().getMobileNumber());
        }

        //add in respective fields
        customerEntity.setCreatedBy("User");
        customerEntity.setCreatedAt(LocalDateTime.now());
        //use Spring JPA to persist data into DB and obtain on return the customer object
        //populated with the ID that is auto gen in the DB via JPA framework
        Customer savedCustomer = customerRepository.save(customerEntity);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        // fetch customer based on mobile
       Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
               // using lambda expressions which is a way to write expressive code
               // comprises of params input, arrow token and function definition
               // used especially in conjunction with functional interfaces which contain only abstract method
               // lamda provides a means to define the functional behaviour on the spot inline without the need for
               // separate classes for implementation
               () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
       );
       //fetch account based on customerId
       String customerId = String.valueOf(customer.getCustomerId());
       Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
               () -> new ResourceNotFoundException("Account", "customerId", customerId)
       );

       CustomerDto customerDto = CustomerMapper.mapToCustomerDto(new CustomerDto(), customer);
       customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(new AccountsDto(), accounts));
       return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        //check if account exists
        AccountsDto accountsDto = customerDto.getAccountsDto();
        Accounts updatedAccount = null;
        if (accountsDto != null) {
            String accountNumber = String.valueOf(accountsDto.getAccountNumber());
            Accounts fetchedAccount = accountsRepository.findByAccountNumber(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "accountNumber", accountNumber)
            );

            // now update with new values
            updatedAccount = AccountsMapper.mapToAccounts(accountsDto, fetchedAccount);
            accountsRepository.save(updatedAccount);
        }

        //check if customer exists
        if( updatedAccount != null) {
            String customerId = String.valueOf(updatedAccount.getCustomerId());
            Customer fetchedCustomer = customerRepository.findByCustomerId(updatedAccount.getCustomerId()).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", customerId)
            );
            Customer updatedCustomer = CustomerMapper.mapToCustomer(customerDto, fetchedCustomer);
            customerRepository.save(updatedCustomer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Long customerId = null;
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        customerId = customer.getCustomerId();
        customerRepository.deleteById(customerId);
        accountsRepository.deleteByCustomerId(customerId);
        return  true;
    }


    private Accounts createNewAccount(Customer customer) {
        //create an account for the user
        Accounts account = new Accounts();

        //populate all account details
        account.setCustomerId(customer.getCustomerId());
        //gen account ID based on custom logic
        Random random = new Random();
        Long genRandomAccountNumber = 1000000000L + random.nextInt(1000000000);
        account.setAccountNumber(genRandomAccountNumber);
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setBranchAddress(AccountsConstants.ADDRESS);
        account.setCreatedAt(LocalDateTime.now());
        account.setCreatedBy("Bank");
        return account;
    }
}
