package uk.gov.dwp.uc.pairtest.util;

import java.util.List;

import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class TicketUtil {
	
	//PRIVATE CONSTRUCTOR TO PREVENT CREATING OBJECT FOR THIS CLASS DIRECTLY
	private TicketUtil() {
	}

	public static void throwException(String message) {
		throw new InvalidPurchaseException(message);
	}
	
	public static void throwException(List<String> message) {
		throw new InvalidPurchaseException(String.join(", ", message));
	}
}
