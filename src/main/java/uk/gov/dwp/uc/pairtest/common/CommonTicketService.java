package uk.gov.dwp.uc.pairtest.common;

import java.util.EnumMap;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

/**
 * CommonTicketService provides common functionality related to ticket and seat calculations.
 */
public interface CommonTicketService {

	/**
	 * Calculates the total amount for the given ticket types and their counts.
	 *
	 * @param typeCountMap EnumMap containing the counts of each ticket type.
	 * @return The total amount calculated based on the counts and prices of ticket types.
	 */
	public int calculateTotalAmount(EnumMap<Type, Integer> typeCountMap);

	/**
	 * Calculates the total count of tickets for the given ticket types.
	 *
	 * @param typeCountMap EnumMap containing the counts of each ticket type.
	 * @return The total count of tickets across all ticket types.
	 */
	public int calculateTotalTicketCount(EnumMap<Type, Integer> typeCountMap);

	/**
	 * Calculates the count of each ticket type based on the provided TicketTypeRequest objects
	 * and updates the given EnumMap with the results.
	 *
	 * @param ticketCountMap EnumMap to be updated with the count of each ticket type.
	 * @param ticketTypeRequests Array of TicketTypeRequest objects representing the ticket purchases.
	 *                           Null or invalid entries will be filtered out.
	 */
	public void calculateTicketCountForEachType(EnumMap<Type, Integer> ticketCountMap, TicketTypeRequest... ticketTypeRequests);


}
