package uk.gov.dwp.uc.pairtest.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

public class BaseTicketServiceTest {

    private CommonTicketServiceImpl commonTicketService;
    private EnumMap<Type, Integer> ticketCountMap;

    @BeforeEach
    public void populateTicketCountMap() {
    	commonTicketService = new CommonTicketServiceImpl();
        ticketCountMap = new EnumMap<>(Type.class);
        ticketCountMap.put(Type.ADULT, 10);
        ticketCountMap.put(Type.CHILD, 5);
        ticketCountMap.put(Type.INFANT, 5);
    }

    @Test
    public void validateTotalAmount_success() {
    	//PRICE -> ADULT 20, CHILD 10, INFANT 0 = 250 pounds
        int result = commonTicketService.calculateTotalAmount(ticketCountMap);
        assertEquals(250, result);
    }
    
    @Test
    public void validateTotalTicketCount_success() {
    	//COUNT -> ADULT 10, CHILD 5, INFANT 5 = 20 seats/tickets
        int result = commonTicketService.calculateTotalTicketCount(ticketCountMap);
        assertEquals(20, result);
    }
    
}
