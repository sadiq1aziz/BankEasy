package com.bankEasy.accounts.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DateFormat;
import java.time.LocalDateTime;


//annotation for JPA to handle auditing entities
@EntityListeners(AuditingEntityListener.class)
//We use this class as a base for other entities
//As we ensure that the metadata fields pertaining to the SCHEMA can be reused as well
@MappedSuperclass
// lombok annotation for getters, setters, and also toString
@Getter @Setter @ToString
public class BaseEntity {

   // annotations for auditing
   @CreatedDate
   //ensure columns for auditing cannot be updated
   @Column(updatable = false)
   private LocalDateTime createdAt;

   @CreatedBy
   @Column(updatable = false)
   private String createdBy;

   @LastModifiedDate
   //ensure that columns do not allow values to be inserted but only updated
   @Column(insertable = false)
   private LocalDateTime updatedAt;

   @LastModifiedBy
   @Column(insertable = false)
   private String updatedBy;
}
