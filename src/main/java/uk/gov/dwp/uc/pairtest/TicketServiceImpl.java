package uk.gov.dwp.uc.pairtest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.validation.TicketValidationService;

public class TicketServiceImpl implements TicketService {

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
		
		List<String> errorList = new ArrayList<>();
		
		EnumMap<Type, Integer> typeCountMap = new EnumMap<>(Type.class);
		
		ticketValidationService.preValidateTicketRequest(accountId, ticketTypeRequests, typeCountMap, errorList);
		
		checkErrors(errorList);
		
		calculateTicketTypeCount(errorList, typeCountMap, ticketTypeRequests);
		
		checkErrors(errorList);
		
		ticketValidationService.validateTicketRequest(accountId, ticketTypeRequests, typeCountMap, errorList);
        
		checkErrors(errorList);

        int totalAmountToPay = calculateTotalAmount(typeCountMap);
        paymentService.makePayment(accountId, totalAmountToPay);
        System.out.println(totalAmountToPay);
        
        int totalSeatsToAllocate = calculateTotalSeatsToReserve(typeCountMap);
        reservationService.reserveSeat(accountId, totalSeatsToAllocate);
        System.out.println(totalSeatsToAllocate);
    }

	private void checkErrors(List<String> errorList) {
		if(!errorList.isEmpty()) {
            throw new InvalidPurchaseException(String.join(",", errorList));
        }
	}

    private void calculateTicketTypeCount(List<String> errorList, EnumMap<Type, Integer> typeCountMap, TicketTypeRequest... ticketTypeRequests) {

    	List<TicketTypeRequest> ticketTypeRequestList = Arrays.asList(ticketTypeRequests);

		AtomicInteger adultTicketCount = new AtomicInteger(0);
		AtomicInteger childTicketCount = new AtomicInteger(0);
		AtomicInteger infantTicketCount = new AtomicInteger(0);

    	ticketTypeRequestList.stream().
		filter(data -> data != null && data.getTicketType() != null).
		forEach(action -> {
			int noOfTickets = action.getNoOfTickets();

			if(noOfTickets < 0) {
				errorList.add(String.format("Error : Invalid ticket count for  %s", action.getTicketType()));
				return;
			}

			switch(action.getTicketType()) {

			case ADULT -> adultTicketCount.addAndGet(noOfTickets);
			case CHILD -> childTicketCount.addAndGet(noOfTickets);
			case INFANT -> infantTicketCount.addAndGet(noOfTickets);

			}

		});
    	
    	typeCountMap.put(Type.ADULT, adultTicketCount.get());
		typeCountMap.put(Type.CHILD, childTicketCount.get());
		typeCountMap.put(Type.INFANT, childTicketCount.get());
    	
	}

	private int calculateTotalAmount(EnumMap<Type, Integer> typeCountMap) {
    	return (typeCountMap.get(Type.ADULT) * Type.ADULT.getPrice()) + 
    			(typeCountMap.get(Type.CHILD) * Type.CHILD.getPrice()) +
    			(typeCountMap.get(Type.INFANT) * Type.INFANT.getPrice());  
    }

    private int calculateTotalSeatsToReserve(EnumMap<Type, Integer> typeCountMap) {
        // No seat needed for INFANTS
        return typeCountMap.get(Type.ADULT) + typeCountMap.get(Type.CHILD);
    }
}





