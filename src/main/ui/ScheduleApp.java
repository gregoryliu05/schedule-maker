package ui;
//TODO: DOCUMENTATION FOR THIS CLASS
import model.Event;
import model.Schedule;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

// Schedule application, UI partly inspired by TellerApp application
// https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class ScheduleApp {
    private static final String JSON_STORE = "./data/schedule.json";
    private Schedule schedule;
    private Scanner input;

    // EFFECTS: runs the schedule application
    public ScheduleApp() {
        runSchedule();
    }


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
        }
        System.out.println("Closed app!");
    }


    private void displayStartScreen() {
        System.out.println("Select an option:");
        System.out.println("press s to create a new schedule");
        System.out.println("press q to close the application");

    }

    private void createNewSchedule() {
        input  = new Scanner(System.in);
        System.out.println("write a name for the schedule");
        schedule.setScheduleName(input.nextLine());
        scheduleScreen();
    }

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
            System.out.println("Closed app!");
        } else {
            System.out.println("typed invalid command, try again");
            scheduleScreen();
        }
    }


    private void scheduleScreenDisplay() {
        System.out.println("press e to create a new event to add to the schedule");
        System.out.println("press r to remove an event from the schedule");
        System.out.println("press v to view schedule");
        System.out.println("press n to change name of the schedule");
        System.out.println("press q to exit");

    }

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

    private void removeEvent() {
        input = new Scanner(System.in);
        List names = schedule.getEventNames();
        System.out.println("type the name of the event listed below you want to remove");
        System.out.println(names);
        String output = input.nextLine();
        checkEvent(schedule.removeEvent(output));

        scheduleScreen();

    }

    private void checkEvent(String name) {
        if (name.equals("Success!")) {
            System.out.println("Successfully removed event!");
        } else {
            System.out.println("you typed in the wrong event, please try again");
            removeEvent();
        }
    }

    private void showSchedule() {
        schedule.printSchedule();
        System.out.println("press b to go back to previous screen");
        System.out.println("press q to exit the application");
        input  = new Scanner(System.in);
        String command = null;
        command = input.next();
        command = command.toLowerCase();
        if (command.equals("b")) {
            scheduleScreen();
        } else if (command.equals("q")) {
            System.out.println("closed app!");
        }


    }

    private void changeScheduleName() {
        input = new Scanner(System.in);
        System.out.println("enter the new name of the schedule");
        schedule.setScheduleName(input.nextLine());
        System.out.println("name change success!");
        scheduleScreen();

    }

    private void saveSchedule() {
        //TODO
    }

    private void loadSchedule() {
        //TODO
    }














}
