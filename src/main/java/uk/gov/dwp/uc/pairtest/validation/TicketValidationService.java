package uk.gov.dwp.uc.pairtest.validation;

import java.util.EnumMap;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

/**
 * Service interface for validating ticket requests.
 */
public interface TicketValidationService {

    /**
     * Pre-validates the ticket request.
     *
     * @param accountId          The ID of the account making the request.
     * @param ticketTypeRequests The ticket type requests to be pre-validated.
     */
    void preValidateTicketRequest(Long accountId, TicketTypeRequest... ticketTypeRequests);

    /**
     * Validates the ticket request based on the count map.
     *
     * @param accountId      The ID of the account making the request.
     * @param ticketCountMap The count map of ticket types.
     */
    void validateTicketRequest(Long accountId, EnumMap<Type, Integer> ticketCountMap);
}
