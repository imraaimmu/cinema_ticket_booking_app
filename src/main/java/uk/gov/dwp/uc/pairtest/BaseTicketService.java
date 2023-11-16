package uk.gov.dwp.uc.pairtest;

import java.util.EnumMap;
import java.util.EnumSet;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

/**
 * BaseTicketService provides common functionality related to ticket calculations.
 */
public class BaseTicketService {

    /**
     * Calculates the total amount for the given ticket types and their counts.
     *
     * @param typeCountMap EnumMap containing the counts of each ticket type.
     * @return The total amount calculated based on the counts and prices of ticket types.
     */
    public int calculateTotalAmount(EnumMap<Type, Integer> typeCountMap) {
        return EnumSet.allOf(Type.class)
                .stream()
                .mapToInt(type -> typeCountMap.getOrDefault(type, 0) * type.getPrice())
                .sum();
    }

    /**
     * Calculates the total count of tickets for the given ticket types.
     *
     * @param typeCountMap EnumMap containing the counts of each ticket type.
     * @return The total count of tickets across all ticket types.
     */
    public int calculateTotalTicketCount(EnumMap<Type, Integer> typeCountMap) {
		return EnumSet.allOf(Type.class)
				.stream()
				.mapToInt(type -> typeCountMap.getOrDefault(type, 0))
				.sum();
	}


}