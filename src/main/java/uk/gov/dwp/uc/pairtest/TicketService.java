package uk.gov.dwp.uc.pairtest;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

/**
 * TicketService defines the contract for purchasing tickets.
 */
public interface TicketService {

    /**
     * Purchase tickets for a specified account with the given ticket requests.
     *
     * @param accountId         The identifier of the account making the purchase.
     * @param ticketTypeRequests Variable number of ticket requests representing the types and quantities of tickets to purchase.
     * @throws InvalidPurchaseException If the ticket purchase is invalid or encounters an error.
     */
    void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException;
}
