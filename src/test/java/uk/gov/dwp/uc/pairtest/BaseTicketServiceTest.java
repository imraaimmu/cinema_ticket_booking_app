package uk.gov.dwp.uc.pairtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

@TestInstance(Lifecycle.PER_CLASS)
public class BaseTicketServiceTest {

    private BaseTicketService baseTicketService;
    private EnumMap<Type, Integer> ticketCountMap;
    

    @BeforeAll
    public void setup() {
    	baseTicketService = new BaseTicketService();
    }
    
    @BeforeEach
    public void populateTicketCountMap() {
        ticketCountMap = new EnumMap<>(Type.class);
        ticketCountMap.put(Type.ADULT, 10);
        ticketCountMap.put(Type.CHILD, 5);
        ticketCountMap.put(Type.INFANT, 5);
    }

    @Test
    public void validateTotalAmount_success() {
    	//PRICE -> ADULT 20, CHILD 10, INFANT 0 = 250 pounds
        int result = baseTicketService.calculateTotalAmount(ticketCountMap);
        assertEquals(250, result);
    }
    
    @Test
    public void validateTotalTicketCount_success() {
    	//COUNT -> ADULT 10, CHILD 5, INFANT 5 = 20 seats/tickets
        int result = baseTicketService.calculateTotalTicketCount(ticketCountMap);
        assertEquals(20, result);
    }
    
}
