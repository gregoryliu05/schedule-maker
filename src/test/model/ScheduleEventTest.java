package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleEventTest {

    private ScheduleEvent scheduleEvent1;
    private ScheduleEvent scheduleEvent2;

    @BeforeEach
    void runBefore() {
        scheduleEvent1 = new ScheduleEvent("event1", 9,30,10,30);
        scheduleEvent2 = new ScheduleEvent("event2",15,0,16,45);
    }

    @Test
    void testConstructor() {
        assertEquals("event1", scheduleEvent1.getEventName());
        assertEquals(LocalTime.of(9,30), scheduleEvent1.getStartTime());
        assertEquals(LocalTime.of(10,30), scheduleEvent1.getEndTime());
    }

    @Test
    void testSetEventName() {
        scheduleEvent1.setEventName("hello");
        assertEquals("hello", scheduleEvent1.getEventName());
    }

    @Test
    void testSetStartTime() {
        scheduleEvent1.setStartTime(10,00);
        assertEquals( LocalTime.of(10,00), scheduleEvent1.getStartTime());
        assertEquals( 10, scheduleEvent1.getStartHour());
        assertEquals(0, scheduleEvent1.getStartMin());
    }

    @Test
    void testSetStartMinute() {
        scheduleEvent2.setStartMinute(10);
        assertEquals(10, scheduleEvent2.getStartMin());
    }

    @Test
    void testSetStartHour() {
        scheduleEvent2.setStartHour(20);
        assertEquals(20, scheduleEvent2.getStartHour());
    }

    @Test
    void testSetEndTime() {
        scheduleEvent2.setEndTime(22,59);
        assertEquals(LocalTime.of(22,59), scheduleEvent2.getEndTime());
        assertEquals(22, scheduleEvent2.getEndHour());
        assertEquals(59, scheduleEvent2.getEndMin());
    }

    @Test
    void testSetEndMinute() {
        scheduleEvent1.setEndMinute(47);
        assertEquals(47, scheduleEvent1.getEndMin());
    }

    @Test
    void testSetEndHour() {
        scheduleEvent1.setEndHour(19);
        assertEquals(19, scheduleEvent1.getEndHour());
    }


}
