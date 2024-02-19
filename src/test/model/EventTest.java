package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    private Event event1;
    private Event event2;

    @BeforeEach
    void runBefore() {
        event1 = new Event("event1", 9,30,10,30);
        event2 = new Event("event2",15,0,16,45);
    }

    @Test
    void testConstructor() {
        assertEquals("event1", event1.getEventName());
        assertEquals(LocalTime.of(9,30), event1.getStartTime());
        assertEquals(LocalTime.of(10,30), event1.getEndTime());
    }



}
