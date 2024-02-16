package ui;
import model.Event;
import model.Schedule;

public class Main {
    public static void main(String[] args) {
        Event event = new Event("fds", 11, 30,12,30);
        Event event1 = new Event("asd",10,0,12,0);
        Schedule schedule = new Schedule("sch");
        schedule.addEvent(event);
        schedule.addEvent(event1);
        schedule.removeEvent(event);
        schedule.getEvents();
        System.out.println(schedule.getEvents());


    }
}
