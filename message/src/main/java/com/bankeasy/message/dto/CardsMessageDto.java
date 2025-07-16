package com.bankeasy.message.dto;

/**
 * This record is created to ensure that once instantiated,
 * the fields in this Dto Object are final and immutable
 * easy for read only
 * @param cardNumber
 * @param email
 * @param name
 * @param mobileNumber
 */
public record CardsMessageDto(String cardNumber, String name, String email, String mobileNumber ) {
}
