package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

// represents a schedule with a list of events and a name
// method printSchedule inspired by printBookingsList in HairSalon App
// https://github.students.cs.ubc.ca/CPSC210/Control-And-Data-Flow-Lecture-Starters.git
public class Schedule implements Writable {
    private List<Event> events;
    private String scheduleName;

    public Schedule(String scheduleName) {
        this.events = new ArrayList<>();
        this.scheduleName = scheduleName;

    }

    //MODIFIES: this
    //EFFECTS: adds an event to the schedule and sorts them according to starting time
    public void addEvent(Event event) {
        this.events.add(event);
        Collections.sort(events);


    }

    //REQUIRES: events to not be empty, and for the event to be in the schedule
    //MODIFIES: this
    //EFFECTS: takes an event name and returns success if it finds it and removes it, if not
    // it returns failure
    public String removeEvent(String eventName) {
        for (Event event: events) {
            if (eventName.equals(event.getEventName())) {
                this.events.remove(event);
                return "Success!";
            }
        }
        return "Failure";
    }

    // EFFECTS: returns all the events in the schedule
    public List getEvents() {
        return this.events;
    }

    // EFFECTS: gets the names of all the events
    public List getEventNames() {
        List names = new ArrayList();
        for (Event event: events) {
            names.add(event.getEventName());
        }
        return names;
    }

    // EFFECTS: gets the name of the schedule
    public String getScheduleName() {
        return this.scheduleName;
    }

    // EFFECTS: sets the name of the schedule
    public void setScheduleName(String name) {
        this.scheduleName = name;
    }


    // EFFECTS: prints the schedule in 24hr time with the schedule name at the top
    public String printSchedule() {
        System.out.println("Schedule: " + scheduleName);
        String printedSchedule = "";
        for (int i = 0; i < 24; i++) {
            System.out.println(i + ":00");
            printedSchedule = printedSchedule + i + ":00\n";
            for (Event event: events) {
                if (event.getStartHour() == i) {
                    System.out.println(event.getStartTime() + ": " + event.getEventName() + " starts");
                    printedSchedule = printedSchedule
                            + event.getStartTime() + ": " + event.getEventName() + " starts\n";
                } else if (event.getEndHour() == i) {
                    System.out.println(event.getEndTime() + ": " + event.getEventName() + " ends");
                    printedSchedule = printedSchedule
                            + event.getEndTime() + ": " + event.getEventName() + " ends\n";
                }
            }
        }
        return printedSchedule;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name",scheduleName);
        json.put("events", eventsToJson());
        return json;

    }

    // EFFECTS: returns events in this schedule as a JSON array
    private JSONArray eventsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Event e: events) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}
