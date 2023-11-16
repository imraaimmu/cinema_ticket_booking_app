package uk.gov.dwp.uc.pairtest.util;

import java.util.List;

import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

/**
 * Utility class for handling ticket-related exceptions.
 */
public class TicketUtil {

    // PRIVATE CONSTRUCTOR TO PREVENT CREATING OBJECT FOR THIS CLASS DIRECTLY
    private TicketUtil() {
    }

    /**
     * Throws an {@code InvalidPurchaseException} with the specified message.
     *
     * @param message The error message to include in the exception.
     * @throws InvalidPurchaseException Always thrown to indicate an invalid purchase.
     */
    public static void throwException(String message) {
        throw new InvalidPurchaseException(message);
    }

    /**
     * Throws an {@code InvalidPurchaseException} with the specified list of messages joined together.
     *
     * @param messages The list of error messages to join and include in the exception.
     * @throws InvalidPurchaseException Always thrown to indicate an invalid purchase.
     */
    public static void throwException(List<String> messages) {
        throw new InvalidPurchaseException(String.join(", ", messages));
    }
}
