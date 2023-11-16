package uk.gov.dwp.uc.pairtest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doCallRealMethod;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.common.CommonTicketServiceImpl;
import uk.gov.dwp.uc.pairtest.constant.TicketConstants;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.validation.TicketValidationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

	private static final long VALID_ACCOUNT_ID = 1L;
	private static final long INVALID_ACCOUNT_ID = 0L;

	@Mock
	TicketPaymentService paymentService;

	@Mock
	TicketValidationServiceImpl ticketValidationServiceImpl;

	@Mock
	SeatReservationService reservationService;

	@Spy
	CommonTicketServiceImpl commonTicketService;

	@InjectMocks
	TicketServiceImpl ticketService;


	TicketTypeRequest adultRequest;
	TicketTypeRequest childRequest;
	TicketTypeRequest infantRequest;

	@BeforeEach
	public void populateTicketCountMap() {
		adultRequest = new TicketTypeRequest(Type.ADULT, 10);
		childRequest = new TicketTypeRequest(Type.CHILD, 5);
		infantRequest = new TicketTypeRequest(Type.INFANT, 5);
	}

	@Test
	public void validateInvalidAccountId_failure() {

		doCallRealMethod().when(ticketValidationServiceImpl).preValidateTicketRequest(INVALID_ACCOUNT_ID, adultRequest, childRequest, infantRequest);

		Throwable exception = assertThrows(InvalidPurchaseException.class,
				() -> ticketService.purchaseTickets(INVALID_ACCOUNT_ID, adultRequest, childRequest, infantRequest));
		assertEquals(TicketConstants.INVALID_ACCOUNT_ID, exception.getMessage());
	}

	@Test
	public void validateInvalidTicketTypeRequestObject_failure() {
		doCallRealMethod().when(ticketValidationServiceImpl).preValidateTicketRequest(VALID_ACCOUNT_ID);
		Throwable exception = assertThrows(InvalidPurchaseException.class,
				() -> ticketService.purchaseTickets(VALID_ACCOUNT_ID));
		assertEquals(TicketConstants.INVALID_REQUEST_OBJECT, exception.getMessage());
	}

	@Test
	public void validateValidPurchase_success() {

		EnumMap<Type,Integer> ticketCountMap = new EnumMap<>(Type.class);
		ticketCountMap.put(Type.ADULT, 10);
		ticketCountMap.put(Type.CHILD, 5);
		ticketCountMap.put(Type.INFANT, 5);

		assertDoesNotThrow(() -> ticketService.purchaseTickets(VALID_ACCOUNT_ID, adultRequest, childRequest, infantRequest));
		Mockito.verify(ticketValidationServiceImpl).preValidateTicketRequest(VALID_ACCOUNT_ID, adultRequest, childRequest, infantRequest);
		Mockito.verify(ticketValidationServiceImpl).validateTicketRequest(VALID_ACCOUNT_ID, ticketCountMap);
		Mockito.verify(paymentService).makePayment(VALID_ACCOUNT_ID, 250);
		Mockito.verify(reservationService).reserveSeat(VALID_ACCOUNT_ID, 15);
	}

}
