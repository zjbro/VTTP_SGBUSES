package vttp2022.project.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;


import vttp2022.project.model.ArrivalBus;
import vttp2022.project.model.NextBus;


@SpringBootTest
public class ArrivalBusTest {


    @Test
    void testCreateArrivalBus(){

        NextBus nextBus = new NextBus();
        NextBus nextBus2 = new NextBus();
        NextBus nextBus3 = new NextBus();

        ArrivalBus arrivalBus = new ArrivalBus();
        arrivalBus.setServiceNo("123");
        arrivalBus.setOperator("SBS");
        arrivalBus.setNextBus(nextBus);
        arrivalBus.setNextBus2(nextBus2);
        arrivalBus.setNextBus3(nextBus3);
        assertEquals(arrivalBus.getServiceNo(),"123");
        assertEquals(arrivalBus.getOperator(),"SBS");
        assertEquals(arrivalBus.getNextBus(),nextBus);
        assertEquals(arrivalBus.getNextBus2(),nextBus2);
        assertEquals(arrivalBus.getNextBus3(),nextBus3);
        }

    
    
}
