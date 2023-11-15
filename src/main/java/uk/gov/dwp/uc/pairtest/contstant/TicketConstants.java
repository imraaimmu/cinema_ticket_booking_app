package uk.gov.dwp.uc.pairtest.contstant;

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
	public static final String PREFIX_BEFORE_ERROR_MESSAGE = "Error : ";
	public static final String CANNOT_BOOK_TICKET_FOR_INFANT_WITHOUT_ADULT = PREFIX_BEFORE_ERROR_MESSAGE + "Each %s should accompained with an %s";
	public static final String CANNOT_BOOK_TICKET_WITHOUT_ADULT = PREFIX_BEFORE_ERROR_MESSAGE + "Cannot book ticket without %s";
	public static final String TOTAL_TICKET_COUNT_MORE_THAN_REQUIRED = PREFIX_BEFORE_ERROR_MESSAGE + "Cannot book more than %i tickets at a time";
	public static final String TOTAL_TICKET_COUNT_LESS_THAN_REQUIRED = PREFIX_BEFORE_ERROR_MESSAGE + "Select atleast 1 ticket";
	public static final String INVALID_REQUEST_OBJECT = PREFIX_BEFORE_ERROR_MESSAGE + "Invalid Request, add atleast 1 ticket";
	public static final String INVALID_ACCOUNT_ID = PREFIX_BEFORE_ERROR_MESSAGE + "AccountID is not valid";
	//ERROR MESSAGES END HERE
}
