package com.bankEasy.accounts.repository;

import com.bankEasy.accounts.entity.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
        //fetch account from accounts table via customerId
        public Optional<Accounts> findByCustomerId(Long customerId);


        //fetch account of customer using account Number
        public Optional<Accounts> findByAccountNumber(Long accountNumber);


        //to let framework know we are modifying record and to roll back incase of issue
        @Transactional
        @Modifying
        //delete account details based on customerId * cant use deletebyid as not primary key
        void deleteByCustomerId (Long customerId);
}
