package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest {

    private ScheduleEvent scheduleEvent1;
    private ScheduleEvent scheduleEvent2;
    private Schedule schedule1;
    private List testList;

    @BeforeEach
    void runBefore() {
        scheduleEvent1 = new ScheduleEvent("event1", 9,30,10,30);
        scheduleEvent2 = new ScheduleEvent("event2",15, 00,16,45);
        schedule1 = new Schedule("schedule1");
        testList = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertEquals("schedule1", schedule1.getScheduleName());
    }

    @Test
    void testAddEvent() {
        schedule1.addEvent(scheduleEvent1);
        testList.add(scheduleEvent1);
        assertEquals(schedule1.getEvents(),testList);
        schedule1.addEvent(scheduleEvent2);
        testList.add(scheduleEvent2);
        assertEquals(schedule1.getEvents(),testList);

    }

    @Test
    void testRemoveEvent() {
        schedule1.addEvent(scheduleEvent1);
        schedule1.removeEvent("event2");
        testList.add(scheduleEvent1);
        assertEquals(schedule1.getEvents(), testList);
        schedule1.removeEvent("event1");
        testList = new ArrayList<>();
        assertEquals(schedule1.getEvents(),testList);

    }

    @Test
    void testSetScheduleName() {
        schedule1.setScheduleName("hello");
        assertEquals("hello", schedule1.getScheduleName());
    }


    @Test
    void testGetEventNames() {
        schedule1.addEvent(scheduleEvent1);
        schedule1.addEvent(scheduleEvent2);
        testList.add("event1");
        testList.add("event2");
        assertEquals(testList, schedule1.getEventNames());
    }

    @Test
    void testPrintSchedule() {
        assertEquals("0:00\n" +
                "1:00\n" +
                "2:00\n" +
                "3:00\n" +
                "4:00\n" +
                "5:00\n" +
                "6:00\n" +
                "7:00\n" +
                "8:00\n" +
                "9:00\n" +
                "10:00\n" +
                "11:00\n" +
                "12:00\n" +
                "13:00\n" +
                "14:00\n" +
                "15:00\n" +
                "16:00\n" +
                "17:00\n" +
                "18:00\n" +
                "19:00\n" +
                "20:00\n" +
                "21:00\n" +
                "22:00\n" +
                "23:00\n", schedule1.printSchedule() );
        schedule1.addEvent(scheduleEvent1);
        schedule1.addEvent(scheduleEvent2);
        assertEquals( "0:00\n" +
                "1:00\n" +
                "2:00\n" +
                "3:00\n" +
                "4:00\n" +
                "5:00\n" +
                "6:00\n" +
                "7:00\n" +
                "8:00\n" +
                "9:00\n" +
                "09:30: event1 starts\n" +
                "10:00\n" +
                "10:30: event1 ends\n" +
                "11:00\n" +
                "12:00\n" +
                "13:00\n" +
                "14:00\n" +
                "15:00\n" +
                "15:00: event2 starts\n" +
                "16:00\n" +
                "16:45: event2 ends\n" +
                "17:00\n" +
                "18:00\n" +
                "19:00\n" +
                "20:00\n" +
                "21:00\n" +
                "22:00\n" +
                "23:00\n", schedule1.printSchedule());

    }


}
