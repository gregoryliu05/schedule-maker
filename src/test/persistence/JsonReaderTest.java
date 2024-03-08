package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            List<Schedule> schedules = reader.read();
            fail("should catch exception");

        } catch (IOException e) {
            // pass
        }

    }

    @Test
    void testReaderEmptyScheduleList() {
        JsonReader reader = new JsonReader("./data/emptyScheduleList.json");
        try {
            List<Schedule> schedules = reader.read();
            assertTrue(schedules.isEmpty());

        } catch (IOException e) {
            fail("shouldn't catch exception");
        }


    }

    @Test
    void testReaderNormalScheduleList() {
        JsonReader reader = new JsonReader("./data/testSchedules.json");
        try {
            List<Schedule> schedules = reader.read();
            Event event1;
            assertEquals(3, schedules.size());
            Schedule schedule1 = schedules.get(0);
            assertEquals("schedule1", schedule1.getScheduleName());
            assertEquals(1, schedule1.getEvents().size());
            List<Event> events = schedule1.getEvents();
            event1 = events.get(0);
            assertEquals("event1", event1.getEventName());
            assertEquals(12, event1.getStartHour());

        } catch (IOException e) {
            fail("shouldn't catch exception");
        }

    }



}
