package model;

import java.time.LocalDateTime;
import java.util.Locale;

public class Event {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String eventName;

    public Event(String eventName, LocalDateTime startTime, LocalDateTime endTime) {
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;


    }
}
