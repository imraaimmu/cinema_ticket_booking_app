package uk.gov.dwp.uc.pairtest.validation;

import java.util.EnumMap;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

/**
 * Service interface for validating ticket requests.
 */
public interface TicketValidationService {

    /**
     * Pre-validates the ticket request for inappropriate request object.
     *
     * @param accountId          The ID of the account making the request.
     * @param ticketTypeRequests The ticket type requests to be pre-validated.
     * @throws InvalidPurchaseException if encounters any error
     */
    void preValidateTicketRequest(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException;

    /**
	 * Validates the ticket purchase request, checking for various conditions to ensure a valid purchase.
	 *
	 * @param accountId           The identifier of the account making the ticket purchase.
	 * @param ticketCountMap      An EnumMap representing the count of each ticket type in the purchase request.
	 *                            The keys are TicketType.Type enum values, and values are the corresponding ticket counts.
	 * @throws InvalidPurchaseException If any of the following conditions are met:
	 *                                  - Any ticket count (Infant, Child, Adult) is negative.
	 *                                  - The total ticket count is non-positive.
	 *                                  - The total ticket count exceeds the maximum allowed tickets per purchase.
	 *                                  - There are no Adult tickets in the purchase.
	 *                                  - The count of Infant tickets exceeds the count of Adult tickets.
	 */
    void validateTicketRequest(Long accountId, EnumMap<Type, Integer> ticketCountMap) throws InvalidPurchaseException;
}
