package ui;

import model.Event;

import java.io.FileNotFoundException;
import java.time.LocalTime;
import model.Schedule;

public class Main {
    public static void main(String[] args) {
        try {
            new ScheduleApp();
        } catch (FileNotFoundException e) {
            System.out.println("file not found, unable to run application");
        }
    }
}
