package uk.gov.dwp.uc.pairtest.validation.TicketValidationServiceTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import uk.gov.dwp.uc.pairtest.constant.TicketConstants;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.validation.TicketValidationService;
import uk.gov.dwp.uc.pairtest.validation.TicketValidationServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
public class TicketValidationServiceTest {

    private static final long VALID_ACCOUNT_ID = 1L;
    private static final long INVALID_ACCOUNT_ID = 0L;

    private TicketValidationService ticketValidationService;
    private EnumMap<Type, Integer> ticketCountMap;

    @BeforeAll
    public void setup() {
    	ticketValidationService = new TicketValidationServiceImpl();
    }
    
    @BeforeEach
    public void populateTicketCountMap() {
        ticketCountMap = new EnumMap<>(Type.class);
        ticketCountMap.put(Type.ADULT, 10);
        ticketCountMap.put(Type.CHILD, 5);
        ticketCountMap.put(Type.INFANT, 5);
    }

    @Test
    public void validateInvalidAccountIdThrowsException() {
        TicketTypeRequest adultRequest = new TicketTypeRequest(Type.ADULT, 10);
        Throwable exception = assertThrows(InvalidPurchaseException.class,
                () -> ticketValidationService.preValidateTicketRequest(INVALID_ACCOUNT_ID, adultRequest));
        assertEquals(TicketConstants.INVALID_ACCOUNT_ID, exception.getMessage());
    }

    @Test
    public void validateInvalidTicketTypeRequestObjectThrowsException() {
        Throwable exception = assertThrows(InvalidPurchaseException.class,
                () -> ticketValidationService.preValidateTicketRequest(VALID_ACCOUNT_ID));
        assertEquals(TicketConstants.INVALID_REQUEST_OBJECT, exception.getMessage());
    }

    @Test
    public void validateZeroAdultTicketsThrowsException() {
        ticketCountMap.put(Type.ADULT, 0);
        Throwable exception = assertThrows(InvalidPurchaseException.class,
                () -> ticketValidationService.validateTicketRequest(VALID_ACCOUNT_ID, ticketCountMap));
        assertEquals(TicketConstants.CANNOT_BOOK_TICKET_WITHOUT_ADULT + ", "
                + TicketConstants.CANNOT_BOOK_TICKET_FOR_INFANT_AND_CHILD_WITHOUT_ADULT, exception.getMessage());
    }

    @Test
    public void validateTicketsWithoutAdultThrowsException() {
        ticketCountMap.put(Type.ADULT, 0);
        ticketCountMap.put(Type.CHILD, 10);
        ticketCountMap.put(Type.INFANT, 10);
        Throwable exception = assertThrows(InvalidPurchaseException.class,
                () -> ticketValidationService.validateTicketRequest(VALID_ACCOUNT_ID, ticketCountMap));
        assertEquals(TicketConstants.CANNOT_BOOK_TICKET_WITHOUT_ADULT + ", "
                + TicketConstants.CANNOT_BOOK_TICKET_FOR_INFANT_AND_CHILD_WITHOUT_ADULT, exception.getMessage());
    }

    @Test
    public void validateMoreThan20TicketsThrowsException() {
        ticketCountMap.put(Type.ADULT, 10);
        ticketCountMap.put(Type.CHILD, 5);
        ticketCountMap.put(Type.INFANT, 6); // Total count is 21 (10 + 5 + 6)
        Throwable exception = assertThrows(InvalidPurchaseException.class,
                () -> ticketValidationService.validateTicketRequest(VALID_ACCOUNT_ID, ticketCountMap));
        assertEquals(String.format(TicketConstants.TOTAL_TICKET_COUNT_MORE_THAN_REQUIRED,
                TicketConstants.MAX_TICKETS_PER_PURCHASE), exception.getMessage());
    }

    @Test
    public void validateZeroTicketsThrowsException() {
        ticketCountMap.put(Type.ADULT, 0);
        ticketCountMap.put(Type.CHILD, 0);
        ticketCountMap.put(Type.INFANT, 0); // Total count is 0
        Throwable exception = assertThrows(InvalidPurchaseException.class,
                () -> ticketValidationService.validateTicketRequest(VALID_ACCOUNT_ID, ticketCountMap));
        assertEquals(TicketConstants.TOTAL_TICKET_COUNT_LESS_THAN_REQUIRED, exception.getMessage());
    }

    @Test
    public void validateValidPurchaseDoesNotThrowException() {
        assertDoesNotThrow(() -> ticketValidationService.validateTicketRequest(VALID_ACCOUNT_ID, ticketCountMap));
    }
}
