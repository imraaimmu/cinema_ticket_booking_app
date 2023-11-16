package uk.gov.dwp.uc.pairtest;

import java.util.EnumMap;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.validation.TicketValidationService;

/**
 * {@code TicketServiceImpl} provides the implementation of the {@code TicketService} interface.
 * It handles the purchase of tickets, including validation, calculation, payment, and seat reservation.
 */
public class TicketServiceImpl extends BaseTicketService implements TicketService {

	private final TicketPaymentService paymentService;
	private final SeatReservationService reservationService;
	private final TicketValidationService ticketValidationService;

	/**
	 * Constructs a {@code TicketServiceImpl} with the specified dependencies.
	 *
	 * @param paymentService         The service responsible for processing ticket payments.
	 * @param reservationService     The service responsible for reserving seats.
	 * @param ticketValidationService The service responsible for validating ticket requests.
	 */
	public TicketServiceImpl(TicketPaymentService paymentService, SeatReservationService reservationService, TicketValidationService ticketValidationService) {
		this.paymentService = paymentService;
		this.reservationService = reservationService;
		this.ticketValidationService = ticketValidationService;
	}

	/**
	 * Purchase tickets for a specified account with the given ticket requests.
	 *
	 * @param accountId          The identifier of the account making the purchase.
	 * @param ticketTypeRequests Variable number of ticket requests representing the types and quantities of tickets to purchase.
	 */
	@Override
	public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) {
		EnumMap<Type, Integer> ticketCountMap = new EnumMap<>(Type.class);

		ticketValidationService.preValidateTicketRequest(accountId, ticketTypeRequests);

		calculateTicketCountForEachType(ticketCountMap, ticketTypeRequests);

		ticketValidationService.validateTicketRequest(accountId, ticketCountMap);

		int totalAmountToPay = calculateTotalAmount(ticketCountMap);
		paymentService.makePayment(accountId, totalAmountToPay);

		int totalSeatsToAllocate = calculateTotalTicketCount(ticketCountMap) - ticketCountMap.get(Type.INFANT);
		reservationService.reserveSeat(accountId, totalSeatsToAllocate);
	}

}
