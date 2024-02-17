package model;


import java.util.Collections;
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
    //EFFECTS: adds an event to the schedule and sorts them according to starting time
    public void addEvent(Event event) {
        this.events.add(event);
        Collections.sort(events);


    }

    //REQUIRES: events to not be empty, and for the event to be in the schedule
    //MODIFIES: this
    //EFFECTS: removes an event from the schedule
    public void removeEvent(String eventName) {
        for (Event event: events){
            if (eventName.equals(event.getEventName())) {
                this.events.remove(event);
            }
        }
    }


    public List getEvents() {
        return this.events;
    }
    public List getEventNames() {
        List names = new ArrayList();
        for (Event event: events) {
            names.add(event.getEventName());
        }
        return names;
    }


    public String getScheduleName() {
        return this.scheduleName;
    }


    public void setScheduleName(String name) {
        this.scheduleName = name;
    }

    public void printSchedule() {
        System.out.println(scheduleName);
        for (int i = 0; i<24; i++) {
            System.out.println(i +":00");
            for (Event event: events) {
                if (event.getStartHour() == i) {
                    System.out.println(event.getStartTime() + ": "+ event.getEventName() + " starts");
                }
            }
            for (Event event: events) {
                if (event.getEndHour() == i) {
                    System.out.println(event.getEndTime() + ": "+ event.getEventName() + " ends");
                }
            }

        }
    }


}
