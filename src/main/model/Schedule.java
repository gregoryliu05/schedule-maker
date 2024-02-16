package model;


import java.util.List;
import java.util.ArrayList;

// represents a schedule with a list of events and a name
public class Schedule {
    private List<Event> events;
    private String scheduleName;

    public Schedule(String scheduleName) {
        this.events = new ArrayList<>();
        this.scheduleName = scheduleName;

    }

    //MODIFIES: this
    //EFFECTS: adds an event to the schedule
    public void addEvent(Event event) {
        this.events.add(event);

    }

    //REQUIRES: events to not be empty, and for the event to be in the schedule
    //MODIFIES: this
    //EFFECTS: removes an event from the schedule
    public void removeEvent(Event event) {
        this.events.remove(event);
    }


    public List getEvents() {
        return this.events;
    }


    public String getScheduleName() {
        return this.scheduleName;
    }


    public void setScheduleName(String name) {
        this.scheduleName = name;
    }

}
