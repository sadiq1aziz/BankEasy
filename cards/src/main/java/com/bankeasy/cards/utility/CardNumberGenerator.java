package com.bankeasy.cards.utility;
import java.security.SecureRandom;

public class CardNumberGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final long MIN_CARD_NUMBER = 100000000000L;
    private static final long MAX_CARD_NUMBER = 999999999999L; // For 12 digits

    private CardNumberGenerator() {
        // Private constructor to prevent instantiation of this utility class
    }
    /**
     * Generates a secure random 12-digit card number.
     *
     * @return A 12-digit long representing a card number.
     */
    public static long generateSecureCardNumber() {
        long randomNumber;
        do {
            randomNumber = secureRandom.nextLong();
        } while (randomNumber < 0); // Ensure positive

        // Adjust to the desired 12-digit range
        if (randomNumber < MIN_CARD_NUMBER || randomNumber > MAX_CARD_NUMBER) {
            randomNumber = (randomNumber % (MAX_CARD_NUMBER - MIN_CARD_NUMBER + 1)) + MIN_CARD_NUMBER;
        }
        return randomNumber;
    }

}
