package ui;
//TODO: DOCUMENTATION FOR THIS CLASS
import model.Event;
import model.Schedule;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

// Schedule application, UI partly inspired by TellerApp application
// https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class ScheduleApp {
    private static final String JSON_STORE = "./data/schedule.json";
    private Schedule schedule;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the schedule application
    public ScheduleApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runSchedule();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runSchedule() {
        boolean keepRunning = true;
        String command;

        schedule = new Schedule("");
        List<Event> savedEvents = new ArrayList<>();
        input  = new Scanner(System.in);

        while (keepRunning) {
            displayStartScreen();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                keepRunning = false;
            }
            if (command.equals("s")) {
                createNewSchedule();
            }
            if (command.equals("l")) {
                loadSchedule();
            }
            if (command.equals("s")) {
                saveSchedule();
            } if (command.equals("v")) {
                showSchedule();
            }
        }
        System.out.println("Closed app!");
    }

    // EFFECTS: displays menu of options to user
    private void displayStartScreen() {
        System.out.println("Select an option:");
        System.out.println("press s to create a new schedule");
        System.out.println("press q to close the application");
        System.out.println("press l to load schedule from file");
        System.out.println("press x to save schedule to file");
        System.out.println("press v to view schedule");

    }

    // EFFECTS: creates a new schedule with a name given by user input
    private void createNewSchedule() {
        input  = new Scanner(System.in);
        System.out.println("write a name for the schedule");
        schedule.setScheduleName(input.nextLine());
        scheduleScreen();
    }

    // EFFECTS: displays the schedule screen and takes in input
    private void scheduleScreen() {
        scheduleScreenDisplay();
        input  = new Scanner(System.in);
        String command;
        command = input.next();
        command = command.toLowerCase();
        if (command.equals("e")) {
            addEvent();
        } else if (command.equals("r")) {
            removeEvent();
        } else if (command.equals("v")) {
            showSchedule();
        } else if (command.equals("n")) {
            changeScheduleName();
        } else if (command.equals("q")) {
            displayStartScreen();
        } else {
            System.out.println("typed invalid command, try again");
            scheduleScreen();
        }
    }

    // EFFECTS: displays the main schedule screen
    private void scheduleScreenDisplay() {
        System.out.println("press e to create a new event to add to the schedule");
        System.out.println("press r to remove an event from the schedule");
        System.out.println("press v to view schedule");
        System.out.println("press n to change name of the schedule");
        System.out.println("press q to go back to starting screen");

    }

    // MODIFIES: this
    // EFFECTS: adds event to the schedule
    private void addEvent() {
        Event event = new Event("",0,0,0,0);
        input = new Scanner(System.in);

        System.out.println("enter in the name of the event");
        event.setEventName(input.next());
        System.out.println("enter the starting hour of the event");
        event.setStartHour(input.nextInt());
        input.nextLine();
        System.out.println("enter the starting minute of the event");
        event.setStartMinute(input.nextInt());
        input.nextLine();
        System.out.println("enter the ending hour of the event");
        event.setEndHour(input.nextInt());
        input.nextLine();
        System.out.println("enter the ending minute of the event");
        event.setEndMinute(input.nextInt());
        input.nextLine();
        schedule.addEvent(event);
        System.out.println("event successfully added!");
        scheduleScreen();
    }

    // MODIFIES: this
    // EFFECTS: removes event given by user input
    private void removeEvent() {
        input = new Scanner(System.in);
        List names = schedule.getEventNames();
        System.out.println("type the name of the event listed below you want to remove");
        System.out.println(names);
        String output = input.nextLine();
        checkEvent(schedule.removeEvent(output));

        scheduleScreen();

    }

    // EFFECTS: checks if the user's input is equal to the name of an event, if it does, removes that event
    private void checkEvent(String name) {
        if (name.equals("Success!")) {
            System.out.println("Successfully removed event!");
        } else {
            System.out.println("you typed in the wrong event, please try again");
            removeEvent();
        }
    }

    // EFFECTS: prints the schedule out to the user
    private void showSchedule() {
        schedule.printSchedule();
        System.out.println("press b to go back to previous screen");
        System.out.println("press q to go back to starting screen");
        input  = new Scanner(System.in);
        String command = null;
        command = input.next();
        command = command.toLowerCase();
        if (command.equals("b")) {
            scheduleScreen();
        } else if (command.equals("q")) {
            displayStartScreen();
        }


    }

    // MODIFIES: this
    // EFFECTS: changes the schedule name
    private void changeScheduleName() {
        input = new Scanner(System.in);
        System.out.println("enter the new name of the schedule");
        schedule.setScheduleName(input.nextLine());
        System.out.println("name change success!");
        scheduleScreen();

    }

    // EFFECTS: saves the schedule to file
    private void saveSchedule() {
        try {
            jsonWriter.open();
            jsonWriter.write(schedule);
            jsonWriter.close();
            System.out.println("Saved " + schedule.getScheduleName() + " to " + JSON_STORE);

        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);

        }


    }

    // MODIFIES: this
    // EFFECTS: loads the schedule from file
    private void loadSchedule() {
        try {
            schedule = jsonReader.read();
            System.out.println("Loaded " + schedule.getScheduleName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        scheduleScreen();
    }















}
