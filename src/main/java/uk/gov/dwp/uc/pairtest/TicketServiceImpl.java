package uk.gov.dwp.uc.pairtest;

import java.util.Arrays;
import java.util.EnumMap;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.util.TicketUtil;
import uk.gov.dwp.uc.pairtest.validation.TicketValidationService;

public class TicketServiceImpl extends BaseTicketService implements TicketService {

	private final TicketPaymentService paymentService;
	private final SeatReservationService reservationService;
	private final TicketValidationService ticketValidationService;

	public TicketServiceImpl(TicketPaymentService paymentService, SeatReservationService reservationService, TicketValidationService ticketValidationService) {
		this.paymentService = paymentService;
		this.reservationService = reservationService;
		this.ticketValidationService = ticketValidationService;
	}

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

	private void calculateTicketCountForEachType(EnumMap<Type, Integer> ticketCountMap, TicketTypeRequest... ticketTypeRequests) {

		Arrays.asList(ticketTypeRequests).stream()
		.filter(ticketTypeRequest -> ticketTypeRequest != null && 
		ticketTypeRequest.getTicketType() != null)
		.forEach(ticketTypeRequest -> {
			int noOfTickets = ticketTypeRequest.getNoOfTickets();
			if(noOfTickets < 0) {
				TicketUtil.throwException(String.format("Error : Invalid ticket count for  %s", ticketTypeRequest.getTicketType()));
			}
			ticketCountMap.merge(ticketTypeRequest.getTicketType(), noOfTickets, Integer::sum);
		});

	}

}
