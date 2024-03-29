package ui;

import model.Event;
import model.Schedule;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.Border;

// Schedule application, UI partly inspired by TellerApp application
// https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class ScheduleApp extends JFrame{
    private static final String JSON_STORE = "./data/schedules.json";
    private List<Schedule> schedules;
    private Schedule currentSchedule;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private CardLayout cl;
    private JPanel mainPanel;
    private JPanel startingScreenPanel;
    private JPanel scheduleNamePanel;
    private JPanel loadSchedulePanel;
    private JPanel scheduleScreenPanel;
    private JPanel addEventPanel;
    private JPanel removeEventPanel;
    private JPanel editNamePanel;
    private JPanel viewSchedulePanel;
    private JPanel buttonPanel;


    // EFFECTS: runs the schedule application
    public ScheduleApp() throws FileNotFoundException {
        currentSchedule = null;
        schedules = new ArrayList<>();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        cl = new CardLayout();
        mainPanel = new JPanel(cl);
        startingScreenPanel = new JPanel();
        scheduleNamePanel = new JPanel();
        loadSchedulePanel = new JPanel();
        scheduleScreenPanel = new JPanel();
        addEventPanel = new JPanel();
        removeEventPanel = new JPanel();
        editNamePanel = new JPanel();
        viewSchedulePanel = new JPanel();
        buttonPanel = new JPanel();


        runSchedule();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runSchedule() {
        currentSchedule = new Schedule("");
        this.setLayout(null);
        this.add(mainPanel);
        mainPanel.setSize(750,750);
        addScheduleName(); //** ADD ALL SCREENS HERE
        displayStartScreen();
        loadScheduleScreen();
        mainPanel.add(startingScreenPanel, "Starting Screen Panel"); // THEN INITIALIZE THEM HERE
        mainPanel.add(scheduleNamePanel, "Schedule Name Panel");
        mainPanel.add(loadSchedulePanel, "Load Schedule Panel");
        cl.show(mainPanel, "Starting Screen Panel");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(750, 750);
        this.setTitle("Schedule App");
        this.setVisible(true);

    }

    // EFFECTS: displays menu of options to user
    private void displayStartScreen() {
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        startingScreenPanel.setLayout(null);
        startingScreenPanel.setBorder(border);
        startingScreenPanel.setSize(750,750);
        JPanel infoPanel = new JPanel();

        infoPanel.setLayout(null);
        infoPanel.setSize(750 , 50);
        JLabel scheduleNameLabel = new JLabel("Current Schedule: "+ currentSchedule.getScheduleName());
        scheduleNameLabel.setBounds(325, 10, 750,25);
        infoPanel.add(scheduleNameLabel, 0);
        startingScreenPanel.add(infoPanel);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBounds(0, 51, 500,500);

        createStartingScreenButtons();


        startingScreenPanel.add(buttonPanel);


    }

    private void createStartingScreenButtons() {
        JButton newScheduleButton = new JButton();
        initButtonsForStartingScreens(newScheduleButton, "New Schedule", "Schedule Name Panel");

        JButton loadScheduleButton = new JButton();
        initButtonsForStartingScreens(loadScheduleButton, "Load Schedule", "Load Schedule Panel");

        JButton saveScheduleButton = new JButton();
        saveScheduleButton.setText("Save Schedule");
        saveScheduleButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        saveScheduleButton.setSize(250,200);
        saveScheduleButton.addActionListener(e -> saveScheduleScreen());
        buttonPanel.add(saveScheduleButton);


        JButton viewScheduleButton = new JButton();
        initButtonsForStartingScreens(viewScheduleButton, "View Schedule", "Schedule Name Panel");


    }
    private void initButtonsForStartingScreens(JButton button, String name, String navPath) {
        button.setText(name);
        button.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        button.setSize(250, 200);
        button.addActionListener(e -> cl.show(mainPanel, navPath));
        buttonPanel.add(button);

    }

    // EFFECTS: screen for adding a name to the schedule
    private void addScheduleName() {
        scheduleScreenPanel.setLayout(null);
        scheduleNamePanel.setLayout(new FlowLayout());
        scheduleNamePanel.setSize(750,750);
        JTextField scheduleName = new JTextField(20);
        scheduleName.setPreferredSize(new Dimension(200,50));
        JButton submitButton = new JButton("Submit");
        scheduleNamePanel.add(new JLabel("Change Schedule Name"));
        scheduleNamePanel.add(scheduleName);
        scheduleNamePanel.add(submitButton);
        scheduleNamePanel.setVisible(true);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = scheduleName.getText();
                currentSchedule.setScheduleName(userInput);
                cl.show(mainPanel, "Starting Screen Panel");
                scheduleName.setText("");
                startingScreenPanel.remove(0); // Remove the old label
                JLabel scheduleLabel =  new JLabel("Current Schedule: "+ currentSchedule.getScheduleName());
                startingScreenPanel.add(scheduleLabel, 0); // Add the new label at index 0
                scheduleLabel.setBounds(325, 10, 750,25);
                startingScreenPanel.revalidate();
                startingScreenPanel.repaint();
            }
        });

    }


    // EFFECTS: displays the screen to select options to load the schedule in
    private void loadScheduleScreen() {
        loadSchedulePanel.setSize(750,750);
        JLabel label = new JLabel();
        label.setBounds(375,50 , 200, 50);
        label.setText("Select A schedule to Load:");
        loadSchedulePanel.add(label);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBounds(0,150, 750, 300);

        try {
            schedules = jsonReader.read();
            List<String> names = new ArrayList<>();
            for (Schedule s : schedules) {
                JButton button = new JButton();
                button.setSize(100,50);
                button.setText(s.getScheduleName());
                button.addActionListener(e-> {
                    currentSchedule = s;
                    startingScreenPanel.remove(0); // Remove the old label
                    JLabel scheduleLabel =  new JLabel("Current Schedule: "+ currentSchedule.getScheduleName());
                    startingScreenPanel.add(scheduleLabel, 0); // Add the new label at index 0
                    scheduleLabel.setBounds(325, 10, 750,25);
                    startingScreenPanel.revalidate();
                    startingScreenPanel.repaint();
                    cl.show(mainPanel, "Starting Screen Panel");

                });
                buttonPanel.add(button);
            }
            loadSchedulePanel.add(buttonPanel);


        } catch (IOException e) {
            JLabel errorLabel = new JLabel();
            errorLabel.setBounds(425,50,200,50);
            errorLabel.setText("Unable to read from file: " + JSON_STORE);
            loadSchedulePanel.add(errorLabel);
        }
        loadSchedulePanel.setVisible(true);


    }

    // EFFECTS: saves all the schedules to file
    private void saveScheduleScreen() {
        JFrame saveFrame = new JFrame();
        try {
            jsonWriter.open();
            jsonWriter.write(schedules);
            jsonWriter.close();
            saveFrame.setSize(300,200);
            JLabel label = new JLabel();
            label.setText("Saved Schedule " + currentSchedule.getScheduleName() + " to file");
            label.setSize(300,200);
            label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
            saveFrame.add(label);
            saveFrame.setVisible(true);




        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);

        }


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
        currentSchedule.addEvent(event);
        System.out.println("event successfully added!");
        scheduleScreen();
    }

    // MODIFIES: this
    // EFFECTS: removes event given by user input
    private void removeEvent() {
        input = new Scanner(System.in);
        List names = currentSchedule.getEventNames();
        System.out.println("type the name of the event listed below you want to remove");
        System.out.println(names);
        String output = input.nextLine();
        checkEvent(currentSchedule.removeEvent(output));

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
        currentSchedule.printSchedule();
        System.out.println("press b to go back to previous screen");
        System.out.println("press q to go back to starting screen");
        input  = new Scanner(System.in);
        String command = null;
        command = input.next();
        command = command.toLowerCase();
        if (command.equals("b")) {
            scheduleScreen();
        }


    }

    // MODIFIES: this
    // EFFECTS: changes the schedule name
    private void changeScheduleName() {
        input = new Scanner(System.in);
        System.out.println("enter the new name of the schedule");
        currentSchedule.setScheduleName(input.nextLine());
        System.out.println("name change success!");
        scheduleScreen();

    }



    // MODIFIES: this
    // EFFECTS: loads the schedule from file
    private void loadSchedule() {
        try {
            schedules = jsonReader.read();
            List<String> names = new ArrayList<>();
            for (Schedule s : schedules) {
                names.add(s.getScheduleName());
            }
            System.out.println("type the name of the schedule you want to load:" + names);
            setSchedule();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        scheduleScreen();
    }

    // MODIFIES: this
    // EFFECTS: sets the current schedule
    private void setSchedule() {
        Scanner scanner = new Scanner(System.in);
        String scheduleName = scanner.nextLine();
        for (Schedule s: schedules) {
            if (s.getScheduleName().equals(scheduleName)) {
                currentSchedule = s;
            }
        }
        if (currentSchedule != null) {
            System.out.println("Loaded " + currentSchedule.getScheduleName() + " from " + JSON_STORE);
        } else {
            System.out.println("Schedule not found");
            loadSchedule();
        }
    }















}
