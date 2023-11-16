package uk.gov.dwp.uc.pairtest.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.dwp.uc.pairtest.common.CommonTicketServiceImpl;
import uk.gov.dwp.uc.pairtest.constant.TicketConstants;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

@ExtendWith(MockitoExtension.class)
public class TicketValidationServiceTest {

	private static final long VALID_ACCOUNT_ID = 1L;
	private static final long INVALID_ACCOUNT_ID = 0L;

	private EnumMap<Type, Integer> ticketCountMap;

	@InjectMocks
	TicketValidationServiceImpl ticketValidationService;

	@Spy
	CommonTicketServiceImpl commonTicketServiceImpl;

	@BeforeEach
	public void populateTicketCountMap() {
		ticketCountMap = new EnumMap<>(Type.class);
		ticketCountMap.put(Type.ADULT, 10);
		ticketCountMap.put(Type.CHILD, 5);
		ticketCountMap.put(Type.INFANT, 5);
	}

	@Test
	public void validateInvalidAccountId_failure() {
		TicketTypeRequest adultRequest = new TicketTypeRequest(Type.ADULT, 10);
		Throwable exception = assertThrows(InvalidPurchaseException.class,
				() -> ticketValidationService.preValidateTicketRequest(INVALID_ACCOUNT_ID, adultRequest));
		assertEquals(TicketConstants.INVALID_ACCOUNT_ID, exception.getMessage());
	}

	@Test
	public void validateInvalidTicketTypeRequestObject_failure() {
		Throwable exception = assertThrows(InvalidPurchaseException.class,
				() -> ticketValidationService.preValidateTicketRequest(VALID_ACCOUNT_ID));
		assertEquals(TicketConstants.INVALID_REQUEST_OBJECT, exception.getMessage());
	}

	@Test
	public void validateZeroAdultTickets_failure() {
		ticketCountMap.put(Type.ADULT, 0);
		Throwable exception = assertThrows(InvalidPurchaseException.class,
				() -> ticketValidationService.validateTicketRequest(VALID_ACCOUNT_ID, ticketCountMap));
		assertEquals(TicketConstants.CANNOT_BOOK_TICKET_WITHOUT_ADULT + ", "
				+ TicketConstants.CANNOT_BOOK_TICKET_FOR_INFANT_AND_CHILD_WITHOUT_ADULT, exception.getMessage());
	}

	@Test
	public void validateTicketsWithoutAdult_failure() {
		ticketCountMap.put(Type.ADULT, 0);
		ticketCountMap.put(Type.CHILD, 10);
		ticketCountMap.put(Type.INFANT, 10);
		Throwable exception = assertThrows(InvalidPurchaseException.class,
				() -> ticketValidationService.validateTicketRequest(VALID_ACCOUNT_ID, ticketCountMap));
		assertEquals(TicketConstants.CANNOT_BOOK_TICKET_WITHOUT_ADULT + ", "
				+ TicketConstants.CANNOT_BOOK_TICKET_FOR_INFANT_AND_CHILD_WITHOUT_ADULT, exception.getMessage());
	}

	@Test
	public void validateMoreThan20Tickets_failure() {
		ticketCountMap.put(Type.ADULT, 10);
		ticketCountMap.put(Type.CHILD, 5);
		ticketCountMap.put(Type.INFANT, 6); // Total count is 21 (10 + 5 + 6)
		Throwable exception = assertThrows(InvalidPurchaseException.class,
				() -> ticketValidationService.validateTicketRequest(VALID_ACCOUNT_ID, ticketCountMap));
		assertEquals(String.format(TicketConstants.TOTAL_TICKET_COUNT_MORE_THAN_REQUIRED,
				TicketConstants.MAX_TICKETS_PER_PURCHASE), exception.getMessage());
	}

	@Test
	public void validateZeroTickets_failure() {
		ticketCountMap.put(Type.ADULT, 0);
		ticketCountMap.put(Type.CHILD, 0);
		ticketCountMap.put(Type.INFANT, 0); // Total count is 0
		Throwable exception = assertThrows(InvalidPurchaseException.class,
				() -> ticketValidationService.validateTicketRequest(VALID_ACCOUNT_ID, ticketCountMap));
		assertEquals(TicketConstants.TOTAL_TICKET_COUNT_LESS_THAN_REQUIRED, exception.getMessage());
	}

	@Test
	public void validateNegativeTicketCount_failure() {
		ticketCountMap.put(Type.ADULT, -1);
		ticketCountMap.put(Type.CHILD, -2);
		ticketCountMap.put(Type.INFANT, 4); // Total count is 0
		Throwable exception = assertThrows(InvalidPurchaseException.class,
				() -> ticketValidationService.validateTicketRequest(VALID_ACCOUNT_ID, ticketCountMap));
		assertEquals(TicketConstants.INVALID_TICKET_COUNT, exception.getMessage());
	}


	@Test
	public void validateValidPurchase_success() {
		assertDoesNotThrow(() -> ticketValidationService.validateTicketRequest(VALID_ACCOUNT_ID, ticketCountMap));
	}
}
