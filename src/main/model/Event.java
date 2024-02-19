package model;

import java.time.LocalTime;

// Represents an event in a schedule, with a start and end time, and an event name
public class Event implements Comparable<Event> {
    private String eventName;     // name of the event
    private LocalTime startTime;  // starting time of the event
    private int startHour;        // starting hour of the event
    private int startMin;         // starting minute of the event
    private LocalTime endTime;    // end time of the event
    private int endHour;          // ending hour of the event
    private int endMin;           // ending minute of the event


    // REQUIRES: startingHour and endingHour <24, startingMinute and endingMinute <60,
    // endTime has to be a time after starting time
    // EFFECTS: creates a new event with name, starting time and ending time
    public Event(String eventName, int startHour, int startMinute, int endHour, int endMinute) {
        this.eventName = eventName;
        this.startHour = startHour;
        this.startMin = startMinute;
        this.startTime = LocalTime.of(this.startHour, this.startMin);
        this.endHour = endHour;
        this.endMin = endMinute;
        this.endTime = LocalTime.of(this.endHour, this.endMin);
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    public void setStartTime(int startHour, int startMinute) {
        this.startHour = startHour;
        this.startMin = startMinute;
        this.startTime = LocalTime.of(this.startHour,this.startMin);

    }

    public void setStartMinute(int startMinute) {
        this.startMin = startMinute;
        this.startTime = LocalTime.of(this.startHour, this.startMin);

    }

    public void setStartHour(int startingHour) {
        this.startHour = startingHour;
        this.startTime = LocalTime.of(this.startHour, this.startMin);

    }

    public void setEndTime(int endHour, int endMinute) {
        this.endHour = endHour;
        this.endMin = endMinute;
        this.endTime = LocalTime.of(this.endHour,this.endMin);

    }

    public void setEndMinute(int endMinute) {
        this.endMin = endMinute;
        this.endTime = LocalTime.of(this.endHour, this.endMin);

    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
        this.endTime = LocalTime.of(this.endHour, this.endMin);

    }

    public String getEventName() {
        return this.eventName;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public int getStartHour() {
        return this.startHour;
    }

    public int getStartMin() {
        return this.startMin;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public int getEndHour() {
        return this.endHour;
    }

    public int getEndMin() {
        return this.endMin;
    }

    @Override
    public int compareTo(Event other) {
        return this.startTime.compareTo(other.startTime);
    }







}
