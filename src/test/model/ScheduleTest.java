package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleTest {

    private Event event1;
    private Event event2;
    private Schedule schedule1;
    private List testList;

    @BeforeEach
    void runBefore() {
        event1 = new Event("event1", 9,30,10,30);
        event2 = new Event("event2",15, 00,16,45);
        schedule1 = new Schedule("schedule1");
        testList = new ArrayList<>();
    }

    @Test
    void testConstructor() {
        assertEquals("schedule1", schedule1.getScheduleName());
    }

    @Test
    void testAddEvent() {
        schedule1.addEvent(event1);
        testList.add(event1);
        assertEquals(schedule1.getEvents(),testList);
        schedule1.addEvent(event2);
        testList.add(event2);
        assertEquals(schedule1.getEvents(),testList);

    }

    @Test
    void testRemoveEvent() {
        schedule1.addEvent(event1);
        schedule1.removeEvent("event2");
        testList.add(event1);
        assertEquals(schedule1.getEvents(), testList);
        schedule1.removeEvent("event1");
        testList = new ArrayList<>();
        assertEquals(schedule1.getEvents(),testList);

    }








}
