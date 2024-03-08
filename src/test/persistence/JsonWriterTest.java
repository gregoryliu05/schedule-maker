package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            List<Schedule> schedules = new ArrayList<>();

            JsonWriter writer = new JsonWriter("./data/my\0Illegal:fileName.json");
            writer.open();
            fail("expected to catch exception");

        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterEmptyScheduleList() {
        try {
            List<Schedule> schedules = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyScheduleList");
            writer.open();
            writer.write(schedules);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyScheduleList");
            schedules = reader.read();
            assertTrue(schedules.isEmpty());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

    }

    @Test
    void testWriterNormalScheduleList() {
        try {
            List<Schedule> schedules = new ArrayList<>();
            Schedule schedule1 = new Schedule("schedule1");
            Event event1 = new Event("event1", 12,30,13,0);
            schedule1.addEvent(event1);

            Schedule schedule2 = new Schedule("schedule2");
            Event event2 = new Event("event2", 1,0,2,0);
            schedule2.addEvent(event2);
            schedules.add(schedule1);
            schedules.add(schedule2);

            JsonWriter writer = new JsonWriter("./data/testWriterNormalScheduleList");
            writer.open();
            writer.write(schedules);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNormalScheduleList");
            schedules = reader.read();
            assertEquals(2,schedules.size());
            schedule1 = schedules.get(0);
            assertEquals("schedule1", schedule1.getScheduleName());
            assertEquals(1, schedule1.getEvents().size());
            List<Event> events = schedule1.getEvents();
            event1 = events.get(0);
            assertEquals("event1", event1.getEventName());
            assertEquals(12, event1.getStartHour());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
