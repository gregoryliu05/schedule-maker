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
    private List<ScheduleEvent> scheduleEvents;
    private String scheduleName;

    public Schedule(String scheduleName) {
        this.scheduleEvents = new ArrayList<>();
        this.scheduleName = scheduleName;

    }

    // REQUIRES: the time of the event added cannot overlap with any existing events
    // MODIFIES: this
    // EFFECTS: adds an event to the schedule and sorts them according to starting time
    public void addEvent(ScheduleEvent scheduleEvent) {
        this.scheduleEvents.add(scheduleEvent);
        Collections.sort(scheduleEvents);
        EventLog.getInstance().logEvent(
                new Event("Added Event " + scheduleEvent.getEventName() + " To Schedule "
                        + this.getScheduleName()));


    }

    //REQUIRES: events to not be empty, and for the event to be in the schedule
    //MODIFIES: this
    //EFFECTS: takes an event name and returns success if it finds it and removes it, if not
    // it returns failure
    public String removeEvent(String eventName) {
        for (ScheduleEvent scheduleEvent : scheduleEvents) {
            if (eventName.equals(scheduleEvent.getEventName())) {
                this.scheduleEvents.remove(scheduleEvent);
                EventLog.getInstance().logEvent(new Event("Removed Event From Schedule"));
                return "Success!";
            }
        }
        return "Failure";
    }

    // EFFECTS: returns all the events in the schedule
    public List<ScheduleEvent> getEvents() {
        return this.scheduleEvents;
    }

    // EFFECTS: gets the names of all the events
    public List getEventNames() {
        List names = new ArrayList();
        for (ScheduleEvent scheduleEvent : scheduleEvents) {
            names.add(scheduleEvent.getEventName());
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
        EventLog.getInstance().logEvent(new Event("Set New Schedule Name of" + this.getScheduleName()));
    }


    // EFFECTS: prints the schedule in 24hr time with the schedule name at the top
    public String printSchedule() {
        System.out.println("Schedule: " + scheduleName);
        String printedSchedule = "";
        for (int i = 0; i < 24; i++) {
            System.out.println(i + ":00");
            printedSchedule = printedSchedule + i + ":00\n";
            for (ScheduleEvent scheduleEvent : scheduleEvents) {
                if (scheduleEvent.getStartHour() == i) {
                    System.out.println(scheduleEvent.getStartTime() + ": " + scheduleEvent.getEventName() + " starts");
                    printedSchedule = printedSchedule
                            + scheduleEvent.getStartTime() + ": " + scheduleEvent.getEventName() + " starts\n";
                } else if (scheduleEvent.getEndHour() == i) {
                    System.out.println(scheduleEvent.getEndTime() + ": " + scheduleEvent.getEventName() + " ends");
                    printedSchedule = printedSchedule
                            + scheduleEvent.getEndTime() + ": " + scheduleEvent.getEventName() + " ends\n";
                }
            }
        }
        return printedSchedule;
    }

    // EFFECTS: logs to the EventLog that the user viewed the schedule
    public void viewSchedule() {
        EventLog.getInstance().logEvent(new Event("Viewing Schedule " + this.getScheduleName()));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", scheduleName);
        json.put("events", eventsToJson());
        return json;

    }

    // EFFECTS: returns events in this schedule as a JSON array
    private JSONArray eventsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (ScheduleEvent e: scheduleEvents) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}
