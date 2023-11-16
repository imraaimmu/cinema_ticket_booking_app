package uk.gov.dwp.uc.pairtest.validation;

import java.util.EnumMap;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

public interface TicketValidationService {

	void preValidateTicketRequest(Long accountId, TicketTypeRequest... ticketTypeRequests);
	
	void validateTicketRequest(Long accountId , EnumMap<Type, Integer> ticketCountMap);
	
}
