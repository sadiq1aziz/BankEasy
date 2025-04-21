package com.bankeasy.cards.repository;


import com.bankeasy.cards.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//Note: <Cards, Long> => 1st Param : denotes Entity for which CRUD is being performed,
//                       2nd Param : denotes primary Key for the Entity
public interface CardRepository extends JpaRepository<Cards, Long> {

    //basic operations such as save, delete are already accessible via the JPA interface implicit methods
    //since params are already known these methods are resolved
    //for ex: Optional<T> = findById(ID id) gets resolved to
    //        Optional<Cards> = findById(Long Id);

    //Custom methods

    //find Card by mobile
    Optional<Cards> findByMobileNumber(String mobileNumber);

    //find using card ID
    Optional<Cards> findByCardNumber(String cardNumber);

}
