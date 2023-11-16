package uk.gov.dwp.uc.pairtest.validation;

import static uk.gov.dwp.uc.pairtest.constant.TicketConstants.CANNOT_BOOK_TICKET_FOR_INFANT_AND_CHILD_WITHOUT_ADULT;
import static uk.gov.dwp.uc.pairtest.constant.TicketConstants.CANNOT_BOOK_TICKET_WITHOUT_ADULT;
import static uk.gov.dwp.uc.pairtest.constant.TicketConstants.INVALID_ACCOUNT_ID;
import static uk.gov.dwp.uc.pairtest.constant.TicketConstants.INVALID_REQUEST_OBJECT;
import static uk.gov.dwp.uc.pairtest.constant.TicketConstants.INVALID_TICKET_COUNT;
import static uk.gov.dwp.uc.pairtest.constant.TicketConstants.MAX_TICKETS_PER_PURCHASE;
import static uk.gov.dwp.uc.pairtest.constant.TicketConstants.TOTAL_TICKET_COUNT_LESS_THAN_REQUIRED;
import static uk.gov.dwp.uc.pairtest.constant.TicketConstants.TOTAL_TICKET_COUNT_MORE_THAN_REQUIRED;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import uk.gov.dwp.uc.pairtest.BaseTicketService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.util.TicketUtil;

/**
 * Implementation of {@link TicketValidationService} for validating ticket requests.
 */
public class TicketValidationServiceImpl extends BaseTicketService implements TicketValidationService {

	/**
	 * Pre-validates the ticket request.
	 *
	 * @param accountId          The ID of the account making the request.
	 * @param ticketTypeRequests The ticket type requests to be pre-validated.
	 * @throws InvalidPurchaseException if encounters any error
	 */
	public void preValidateTicketRequest(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {

		if(accountId == null  || accountId <= 0) {
			TicketUtil.throwException(INVALID_ACCOUNT_ID);
			return;
		}

		if (ticketTypeRequests == null || ticketTypeRequests.length == 0) {
			TicketUtil.throwException(INVALID_REQUEST_OBJECT);
			return;
		}

	}

	/**
	 * Validates the ticket request based on the ticketCount map.
	 *
	 * @param accountId      The ID of the account making the request.
	 * @param ticketCountMap The count map of ticket types.
	 * @throws InvalidPurchaseException
	 */
	public void validateTicketRequest(Long accountId, EnumMap<Type, Integer> ticketCountMap) throws InvalidPurchaseException {
		List<String> errorList = new ArrayList<>();

		int totalTicketCount = calculateTotalTicketCount(ticketCountMap);

		if (ticketCountMap.getOrDefault(Type.INFANT, 0) < 0 ||
				ticketCountMap.getOrDefault(Type.CHILD, 0) < 0 ||
				ticketCountMap.getOrDefault(Type.ADULT, 0) < 0) {
			TicketUtil.throwException(INVALID_TICKET_COUNT);
			return;
		}

		if (totalTicketCount <= 0) {
			TicketUtil.throwException(TOTAL_TICKET_COUNT_LESS_THAN_REQUIRED);
			return;
		}

		if (totalTicketCount > MAX_TICKETS_PER_PURCHASE) {
			TicketUtil.throwException(String.format(TOTAL_TICKET_COUNT_MORE_THAN_REQUIRED, MAX_TICKETS_PER_PURCHASE));
			return;
		}

		if (ticketCountMap.getOrDefault(Type.ADULT, 0) == 0) {
			errorList.add(CANNOT_BOOK_TICKET_WITHOUT_ADULT);
		}

		if (ticketCountMap.getOrDefault(Type.INFANT, 0) > ticketCountMap.getOrDefault(Type.ADULT, 0)) {
			errorList.add(CANNOT_BOOK_TICKET_FOR_INFANT_AND_CHILD_WITHOUT_ADULT);
		}

		if (!errorList.isEmpty()) {
			TicketUtil.throwException(errorList);
		}
	}

}