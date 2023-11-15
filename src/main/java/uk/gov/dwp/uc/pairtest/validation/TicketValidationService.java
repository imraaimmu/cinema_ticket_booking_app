package uk.gov.dwp.uc.pairtest.validation;

import java.util.EnumMap;
import java.util.List;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

public interface TicketValidationService {

	void preValidateTicketRequest(Long accountId, TicketTypeRequest[] ticketTypeRequests, EnumMap<Type, Integer> typeCountMap, List<String> errorList);
	
	void validateTicketRequest(Long accountId, TicketTypeRequest[] ticketTypeRequests, EnumMap<Type, Integer> typeCountMap, List<String> errorList);

}
