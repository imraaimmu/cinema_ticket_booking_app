package uk.gov.dwp.uc.pairtest.validation;

import java.util.EnumMap;
import java.util.List;

import uk.gov.dwp.uc.pairtest.contstant.TicketConstants;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import static uk.gov.dwp.uc.pairtest.contstant.TicketConstants.*;

public class TicketValidationServiceImpl implements TicketValidationService {

	public void preValidateTicketRequest(Long accountId, TicketTypeRequest[] ticketTypeRequests, EnumMap<Type, Integer> typeCountMap, List<String> errorList) {
		
		if(accountId == null  || accountId <= 0) {
			errorList.add(INVALID_ACCOUNT_ID);
			return;
		}

		if (ticketTypeRequests == null || ticketTypeRequests.length == 0) {
			errorList.add(INVALID_REQUEST_OBJECT);
			return;
		}
		
	}
	
	public void validateTicketRequest(Long accountId, TicketTypeRequest[] ticketTypeRequests, EnumMap<Type, Integer> typeCountMap, List<String> errorList) {

		int totalTicketCount = typeCountMap.get(Type.CHILD) + typeCountMap.get(Type.ADULT) + typeCountMap.get(Type.INFANT);
		
		if(totalTicketCount <= 0) {
			errorList.add(TOTAL_TICKET_COUNT_LESS_THAN_REQUIRED);
			return;
		}

		if(totalTicketCount > TicketConstants.MAX_TICKETS_PER_PURCHASE) {
			errorList.add(String.format(TOTAL_TICKET_COUNT_MORE_THAN_REQUIRED, TicketConstants.MAX_TICKETS_PER_PURCHASE));
			return;
		}

		if(typeCountMap.get(Type.ADULT) == 0) {
			errorList.add(String.format(CANNOT_BOOK_TICKET_WITHOUT_ADULT, Type.ADULT));
		}
		
		if(typeCountMap.get(Type.INFANT) > typeCountMap.get(Type.ADULT)) {
			errorList.add(String.format(CANNOT_BOOK_TICKET_FOR_INFANT_WITHOUT_ADULT, Type.INFANT, Type.CHILD));
		}

	}

}





