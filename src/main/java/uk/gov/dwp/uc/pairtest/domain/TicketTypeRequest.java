package uk.gov.dwp.uc.pairtest.domain;

import uk.gov.dwp.uc.pairtest.constant.TicketConstants;

/**
 * Represents a request for purchasing tickets of a specific type.
 *
 * This immutable class encapsulates information about the type of ticket and the number of tickets requested.
 *
 * Example Usage:
 * ```java
 * TicketTypeRequest adultTicketRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 3);
 * ```
 *
 * @see TicketConstants
 */
public class TicketTypeRequest {

	private int noOfTickets;
	private Type type;

	/**
	 * Constructs a new TicketTypeRequest.
	 *
	 * @param type         The type of the ticket (ADULT, CHILD, or INFANT).
	 * @param noOfTickets  The number of tickets requested.
	 */
	public TicketTypeRequest(Type type, int noOfTickets) {
		this.type = type;
		this.noOfTickets = noOfTickets;
	}

	/**
	 * Gets the number of tickets requested.
	 *
	 * @return The number of tickets requested.
	 */
	public int getNoOfTickets() {
		return noOfTickets;
	}

	/**
	 * Gets the type of the ticket.
	 *
	 * @return The type of the ticket (ADULT, CHILD, or INFANT).
	 */
	public Type getTicketType() {
		return type;
	}

	/**
	 * Enum representing the type of tickets along with their prices.
	 */
	public enum Type {
		ADULT(TicketConstants.ADULT_TICKET_PRICE), // Sets the price for ADULT ticket
		CHILD(TicketConstants.CHILD_TICKET_PRICE), // Sets the price for CHILD ticket
		INFANT(TicketConstants.INFANT_TICKET_PRICE); // Sets the price for INFANT ticket

		private int price;

		/**
		 * Constructs a Type with the given price.
		 *
		 * @param price The price of the ticket type.
		 */
		private Type(int price) {
			this.price = price;
		}

		/**
		 * Gets the price of the ticket type.
		 *
		 * @return The price of the ticket type.
		 */
		public int getPrice() {
			return price;
		}
	}
}
