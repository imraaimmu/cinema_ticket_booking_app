package uk.gov.dwp.uc.pairtest.validation;

import static uk.gov.dwp.uc.pairtest.constant.TicketConstants.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import uk.gov.dwp.uc.pairtest.BaseTicketService;
import uk.gov.dwp.uc.pairtest.constant.TicketConstants;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.util.TicketUtil;

public class TicketValidationServiceImpl extends BaseTicketService implements TicketValidationService {

	public void preValidateTicketRequest(Long accountId, TicketTypeRequest... ticketTypeRequests) {

		if(accountId == null  || accountId <= 0) {
			TicketUtil.throwException(INVALID_ACCOUNT_ID);
			return;
		}

		if (ticketTypeRequests == null || ticketTypeRequests.length == 0) {
			TicketUtil.throwException(INVALID_REQUEST_OBJECT);
			return;
		}

	}

	public void validateTicketRequest(Long accountId, EnumMap<Type, Integer> ticketCountMap) {

		List<String> errorList = new ArrayList<>();

		int totalTicketCount = calculateTotalTicketCount(ticketCountMap);

		if(totalTicketCount <= 0) {
			TicketUtil.throwException(TOTAL_TICKET_COUNT_LESS_THAN_REQUIRED);
			return;
		}

		if(totalTicketCount > TicketConstants.MAX_TICKETS_PER_PURCHASE) {
			TicketUtil.throwException(String.format(TOTAL_TICKET_COUNT_MORE_THAN_REQUIRED,TicketConstants.MAX_TICKETS_PER_PURCHASE));
			return;
		}

		if(ticketCountMap.get(Type.ADULT) == 0) {
			errorList.add(CANNOT_BOOK_TICKET_WITHOUT_ADULT);
		}

		if(ticketCountMap.get(Type.INFANT) > ticketCountMap.get(Type.ADULT)) {
			errorList.add(CANNOT_BOOK_TICKET_FOR_INFANT_AND_CHILD_WITHOUT_ADULT);
		}

		if(!errorList.isEmpty()) {
			TicketUtil.throwException(errorList);
		}

	}

}