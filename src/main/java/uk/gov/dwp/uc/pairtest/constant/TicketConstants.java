package uk.gov.dwp.uc.pairtest.constant;

public class TicketConstants {
	
	//PRIVATE CONSTRUCTOR TO PREVENT CREATING OBJECT FOR THIS CLASS DIRECTLY
	private TicketConstants() {
	}

	//CONDITIONS
	public static final int MAX_TICKETS_PER_PURCHASE = 20;
	
	//MIGHT HAVE TO GET PRICE FROM EXTERNAL SERVICE OR DB, FOR NOW, SETTING IT AS CONSTANT
	public static final int ADULT_TICKET_PRICE = 20;
	public static final int CHILD_TICKET_PRICE = 10;
	public static final int INFANT_TICKET_PRICE = 0;
	
	//ERROR MESSAGES STARTS HERE
	private static final String ERROR_MESSAGE_PREFIX = "Error : ";
	public static final String CANNOT_BOOK_TICKET_FOR_INFANT_AND_CHILD_WITHOUT_ADULT = ERROR_MESSAGE_PREFIX + "Infants and Children should be accompained with Adults";
	public static final String CANNOT_BOOK_TICKET_WITHOUT_ADULT = ERROR_MESSAGE_PREFIX + "Cannot book ticket without any Adult";
	public static final String TOTAL_TICKET_COUNT_MORE_THAN_REQUIRED = ERROR_MESSAGE_PREFIX + "Cannot book more than %d tickets at a time";
	public static final String TOTAL_TICKET_COUNT_LESS_THAN_REQUIRED = ERROR_MESSAGE_PREFIX + "Select atleast 1 ticket";
	public static final String INVALID_REQUEST_OBJECT = ERROR_MESSAGE_PREFIX + "Invalid Request, add atleast 1 ticket";
	public static final String INVALID_ACCOUNT_ID = ERROR_MESSAGE_PREFIX + "Account ID is not valid";
	//ERROR MESSAGES END HERE
}
