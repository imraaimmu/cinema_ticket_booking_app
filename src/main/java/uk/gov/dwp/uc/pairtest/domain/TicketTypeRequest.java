package uk.gov.dwp.uc.pairtest.domain;

import uk.gov.dwp.uc.pairtest.constant.TicketConstants;

/**
 * Immutable Object
 */

public class TicketTypeRequest {

    private int noOfTickets;
    private Type type;

    public TicketTypeRequest(Type type, int noOfTickets) {
        this.type = type;
        this.noOfTickets = noOfTickets;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public Type getTicketType() {
        return type;
    }

    public enum Type {
        ADULT(TicketConstants.ADULT_TICKET_PRICE), // Set the price for ADULT tickets
        CHILD(TicketConstants.CHILD_TICKET_PRICE), // Set the price for CHILD tickets
        INFANT(TicketConstants.INFANT_TICKET_PRICE); // Set the price for INFANT tickets

        private int price;

        private Type(int price) {
            this.price = price;
        }

        public int getPrice() {
            return price;
        }
    }

}
