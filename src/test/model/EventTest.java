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

    @Test
    void testSetEventName() {
        event1.setEventName("hello");
        assertEquals("hello", event1.getEventName());
    }

    @Test
    void testSetStartTime() {
        event1.setStartTime(10,00);
        assertEquals( LocalTime.of(10,00), event1.getStartTime());
        assertEquals( 10, event1.getStartHour());
        assertEquals(0, event1.getStartMin());
    }

    @Test
    void testSetStartMinute() {
        event2.setStartMinute(10);
        assertEquals(10, event2.getStartMin());
    }

    @Test
    void testSetStartHour() {
        event2.setStartHour(20);
        assertEquals(20, event2.getStartHour());
    }

    @Test
    void testSetEndTime() {
        event2.setEndTime(22,59);
        assertEquals(LocalTime.of(22,59),event2.getEndTime());
        assertEquals(22, event2.getEndHour());
        assertEquals(59, event2.getEndMin());
    }

    @Test
    void testSetEndMinute() {
        event1.setEndMinute(47);
        assertEquals(47, event1.getEndMin());
    }

    @Test
    void testSetEndHour() {
        event1.setEndHour(19);
        assertEquals(19, event1.getEndHour());
    }


}
