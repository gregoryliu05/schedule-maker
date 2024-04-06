package ui;

import model.Event;
import model.EventLog;
import model.ScheduleEvent;
import model.Schedule;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.Border;

// Schedule application
public class ScheduleApp extends JFrame {
    private static final String JSON_STORE = "./data/schedules.json";
    private List<Schedule> schedules;
    private Schedule currentSchedule;
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
    private JPanel editSchedulePanel;
    private JPanel buttonPanel;
    private JPanel scheduleButtonPanel;


    // EFFECTS: runs the schedule application
    public ScheduleApp() throws FileNotFoundException {
        currentSchedule = new Schedule("");
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
        editSchedulePanel = new JPanel();
        buttonPanel = new JPanel();
        scheduleButtonPanel = new JPanel();


        runSchedule();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runSchedule() {
        initFrame();

        mainPanel.setSize(750,750);
        addScheduleName(); //** ADD ALL SCREENS HERE
        scheduleScreen();
        displayStartScreen();
        loadScheduleScreen();
        editScheduleScreen();
        editScheduleNamePanel();
        addEventScreen();
        removeEventScreen();
        mainPanel.add(startingScreenPanel, "Starting Screen Panel"); // THEN INITIALIZE THEM HERE
        mainPanel.add(scheduleScreenPanel, "Schedule Screen Panel");
        mainPanel.add(scheduleNamePanel, "Schedule Name Panel");
        mainPanel.add(loadSchedulePanel, "Load Schedule Panel");
        mainPanel.add(editSchedulePanel, "Edit Schedule Panel");
        mainPanel.add(editNamePanel, "Edit Name Panel");
        mainPanel.add(addEventPanel, "Add Event Panel");
        mainPanel.add(removeEventPanel, "Remove Event Panel");
        cl.show(mainPanel, "Starting Screen Panel");

    }

    // EFFECTS: Initializes the main JFrame
    private void initFrame() {
        this.setLayout(null);
        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(750, 750);
        this.setTitle("Schedule App");
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EventLog eventLog = EventLog.getInstance();
                for (Event event : eventLog) {
                    System.out.println(event.toString());
                }
            }
        });

    }

    // EFFECTS: displays menu of options to user
    private void displayStartScreen() {
        ImageIcon image = new ImageIcon("scheduleicon.png");
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        startingScreenPanel.setLayout(null);
        startingScreenPanel.setBorder(border);
        startingScreenPanel.setSize(750,750);
        JPanel infoPanel = new JPanel();

        infoPanel.setSize(750, 300);
        JLabel scheduleNameLabel = new JLabel("Current Schedule: " + currentSchedule.getScheduleName());
        scheduleNameLabel.setIcon(image);
        //scheduleNameLabel.setBounds(325, 10, 300,300);
        infoPanel.add(scheduleNameLabel, 0);
        infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        startingScreenPanel.add(infoPanel);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBounds(0, 301, 500,400);

        createStartingScreenButtons();


        startingScreenPanel.add(buttonPanel);


    }

    //EFFECTS: adds all the buttons to the starting screen
    private void createStartingScreenButtons() {
        JButton newScheduleButton = new JButton("New Schedule");
        initButtonsForStartingScreens(newScheduleButton, "New Schedule", "Schedule Name Panel");

        JButton loadScheduleButton = new JButton("Load Schedule");
        initButtonsForStartingScreens(loadScheduleButton, "Load Schedule", "Load Schedule Panel");

        JButton saveScheduleButton = new JButton("Save Schedule");
        saveScheduleButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        saveScheduleButton.setSize(250,200);
        saveScheduleButton.addActionListener(e -> saveScheduleScreen());
        buttonPanel.add(saveScheduleButton);


        JButton viewScheduleButton = new JButton();
        initViewScheduleButton(viewScheduleButton, "View Schedule", "Schedule Screen Panel");
        JButton editScheduleScreenButton = new JButton();
        initButtonsForStartingScreens(editScheduleScreenButton, "Modify Schedule", "Edit Schedule Panel");



    }

    // EFFECTS: helper function that creates the buttons for the starting screen
    private void initButtonsForStartingScreens(JButton button, String name, String navPath) {
        button.setText(name);
        button.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        button.setSize(150, 100);
        button.addActionListener(e -> cl.show(mainPanel, navPath));
        buttonPanel.add(button);

    }

    // EFFECTS: helper function for viewScheduleButton
    private void initViewScheduleButton(JButton button, String name, String navPath) {
        button.setText(name);
        button.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        button.setSize(150, 100);
        button.addActionListener(e -> {
            cl.show(mainPanel, navPath);
            currentSchedule.viewSchedule();
        });
        buttonPanel.add(button);
    }

    // MODIFIES: this
    // EFFECTS: screen for adding a name to the schedule
    private void addScheduleName() {
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
            public void actionPerformed(ActionEvent e) {
                String userInput = scheduleName.getText();
                currentSchedule.setScheduleName(userInput);
                cl.show(mainPanel, "Starting Screen Panel");
                scheduleName.setText("");
                startingScreenPanel.remove(0); // Remove the old label
                JLabel scheduleLabel =  new JLabel("Current Schedule: " + currentSchedule.getScheduleName());
                scheduleLabel.setBounds(325, 10, 750,25);
                startingScreenPanel.add(scheduleLabel, 0); // Add the new label at index 0
                startingScreenPanel.revalidate();
                startingScreenPanel.repaint();
            }
        });
    }


    // EFFECTS: displays the screen to select options to load the schedule in
    private void loadScheduleScreen() {
        initLoadSchedule();
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBounds(0,150, 750, 300);
        try {
            schedules = jsonReader.read();
            for (Schedule s : schedules) {
                JButton button = new JButton(s.getScheduleName());
                button.setSize(100,50);
                button.addActionListener(e -> {
                    currentSchedule = s;
                    resetName();
                    cl.show(mainPanel, "Starting Screen Panel");
                    updateScheduleScreen();
                });
                buttonPanel.add(button);
            }
            loadSchedulePanel.add(buttonPanel);

        } catch (IOException e) {
            errorLabel();
        }



    }

    // EFFECTS: updates the schedule name on the starting screen
    private void resetName() {
        startingScreenPanel.remove(0); // Remove the old label
        JLabel scheduleLabel =  new JLabel("Current Schedule: " + currentSchedule.getScheduleName());
        startingScreenPanel.add(scheduleLabel, 0); // Add the new label at index 0
        scheduleLabel.setBounds(325, 10, 750,25);
        startingScreenPanel.revalidate();
        startingScreenPanel.repaint();
    }

    // EFFECTS: displays an error label if loadScheduleScreen catches an exception
    private void errorLabel() {
        JLabel errorLabel = new JLabel();
        errorLabel.setBounds(425,50,200,50);
        errorLabel.setText("Unable to read from file: " + JSON_STORE);
        loadSchedulePanel.add(errorLabel);
    }

    // EFFECTS: initializes the load schedule screen
    private void initLoadSchedule() {
        loadSchedulePanel.setSize(750,750);
        JLabel label = new JLabel();
        label.setBounds(375,50, 200, 50);
        label.setText("Select A schedule to Load:");
        loadSchedulePanel.add(label);
        loadSchedulePanel.setVisible(true);


    }

    // EFFECTS: saves the current schedule to file
    private void saveScheduleScreen() {
        JFrame saveFrame = new JFrame();
        try {

            jsonWriter.open();
            for (Schedule s: schedules) {
                if (s.getScheduleName().equals(currentSchedule.getScheduleName())) {
                    schedules.remove(s);
                }
            }
            schedules.add(currentSchedule);
            jsonWriter.write(schedules);
            jsonWriter.close();
            saveFrame.setSize(400,200);
            JLabel label = new JLabel();
            label.setText("Saved Schedule " + currentSchedule.getScheduleName() + " to file");
            label.setSize(400,200);
            label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
            saveFrame.add(label);
            saveFrame.setVisible(true);

        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);

        }



    }

    // EFFECTS: displays the schedule screen
    private void scheduleScreen() {
        scheduleScreenPanel.setSize(750,750);

        JPanel timePanel = new JPanel(new GridLayout(24,1));
        timePanel.setBounds(10,60,100,690);
        for (int i = 1; i <= 24; i++) {
            JLabel time = new JLabel(i - 1 + ":00");
            time.setBounds(0,i * 28, 100,28);
            time.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
            timePanel.add(time);
        }
        timePanel.setVisible(true);
        addNameLabel();
        addEventsToPanel();
        addBackButton();
        scheduleScreenPanel.add(timePanel,3);
        scheduleScreenPanel.setLayout(null);
        scheduleScreenPanel.setVisible(true);


    }

    // EFFECTS: adds a back button to the schedule screen panel
    private void addBackButton() {
        JButton button = new JButton();
        button.setBounds(600,0,150,50);
        button.setText("go back");
        button.addActionListener(e -> cl.show(mainPanel, "Starting Screen Panel"));
        scheduleScreenPanel.add(button,2);
    }

    // EFFECTS: adds all the events to the view schedule screen
    private void addEventsToPanel() {
        JPanel eventPanel = new JPanel();
        eventPanel.setLayout(null);
        eventPanel.setBounds(110, 60, 640, 690);
        for (ScheduleEvent e : currentSchedule.getEvents()) {
            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBounds(0, 28 * e.getStartHour() + 28, 200, (e.getEndHour() - e.getStartHour()) * 28);
            panel.setBackground(Color.LIGHT_GRAY);
            JLabel event = new JLabel();
            event.setBounds(0, 0, 50, (e.getEndHour() - e.getStartHour()) * 28);
            event.setText(e.getEventName());
            panel.add(event);
            eventPanel.add(panel);
        }

        scheduleScreenPanel.add(eventPanel,1);
        eventPanel.setVisible(true);
    }

    // EFFECTS: updates the schedule screen whenever there is a change in the current schedule
    private void updateScheduleScreen() {
        scheduleScreenPanel.remove(0);
        addNameLabel();
        scheduleScreenPanel.remove(1);
        addEventsToPanel();
        scheduleScreenPanel.revalidate();
        scheduleScreenPanel.repaint();
    }

    // EFFECTS: adds the current schedule name to the schedule screen
    private void addNameLabel() {
        JLabel label = new JLabel();
        label.setText("Current Schedule: " + currentSchedule.getScheduleName());
        label.setBounds(325,10,750,50);
        label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        label.setVisible(true);
        scheduleScreenPanel.add(label,0);
    }


    // EFFECTS: initializes the edit schedule screen
    private void editScheduleScreen() {
        editSchedulePanel.setSize(750,750);
        editSchedulePanel.setLayout(null);
        scheduleButtonPanel.setBounds(0,100,750,650);
        editSchedulePanel.setVisible(true);
        editSchedulePanel.add(scheduleButtonPanel);
        editSchedulePanel.setVisible(true);
        initEditButtons();
    }

    // EFFECTS: initializes the edit schedule buttons
    private void initEditButtons() {
        JButton editNameButton = new JButton("Edit Schedule Name");
        constructButtons(editNameButton, "Edit Name Panel");

        JButton addEventButton = new JButton("Add Event");
        constructButtons(addEventButton, "Add Event Panel");

        JButton removeEventButton = new JButton("Remove Event");
        constructButtons(removeEventButton, "Remove Event Panel");
        removeEventButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        removeEventButton.setSize(150, 100);
        removeEventButton.addActionListener(e -> {
            cl.show(mainPanel, "Remove Event Panel");
            removeEventPanel.repaint();
        });
        scheduleButtonPanel.add(removeEventButton);

        JButton viewScheduleButton = new JButton("View Schedule");
        initViewScheduleButtonForEdit(viewScheduleButton, "View Schedule", "Schedule Screen Panel");

        JButton backButton = new JButton("Go Back");
        constructButtons(backButton, "Starting Screen Panel");


    }

    // EFFECTS: initializes viewScheduleButton for scheduleButtonPanel
    private void initViewScheduleButtonForEdit(JButton button, String name, String navPath) {
        button.setText(name);
        button.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        button.setSize(150, 100);
        button.addActionListener(e -> {
            cl.show(mainPanel, navPath);
            currentSchedule.viewSchedule();
        });
        scheduleButtonPanel.add(button);

    }

    // EFFECTS: helper function that constructs the buttons
    private void constructButtons(JButton button, String navPath) {
        button.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        button.setSize(150, 100);
        button.addActionListener(e -> cl.show(mainPanel, navPath));
        scheduleButtonPanel.add(button);
    }

    // MODIFIES: this
    // EFFECTS: initializes the edit schedule name panel
    private void editScheduleNamePanel() {
        editNamePanel.setSize(750,750);
        editNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("Enter a new Name for the Schedule");
        JTextField textField = new JTextField(30);
        label.setBounds(20,50,100,50);
        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            String userInput = textField.getText();
            currentSchedule.setScheduleName(userInput);
            cl.show(mainPanel, "Starting Screen Panel");
            textField.setText("");
            startingScreenPanel.remove(0); // Remove the old label
            JLabel scheduleLabel =  new JLabel("Current Schedule: " + currentSchedule.getScheduleName());
            scheduleLabel.setBounds(325, 10, 750,25);
            updateScheduleScreen();
            startingScreenPanel.add(scheduleLabel, 0); // Add the new label at index 0
            startingScreenPanel.revalidate();
            startingScreenPanel.repaint();
        });
        editNamePanel.add(label);
        editNamePanel.add(textField);
        editNamePanel.add(submit);
        editScheduleNamePanelAddBackButton();

    }


    // EFFECTS: adds a back button to the edit schedule panel
    private void editScheduleNamePanelAddBackButton() {
        JButton button = new JButton();
        button.setBounds(600,0,150,50);
        button.setText("go back");
        button.addActionListener(e -> cl.show(mainPanel, "Edit Schedule Panel"));
        editNamePanel.add(button);
    }

    // EFFECTS: initializes the add event screen
    private void addEventScreen() {
        addEventPanel.setSize(750,750);
        addEventPanel.setLayout(null);
        JButton button = new JButton();
        button.setBounds(600,0,150,50);
        button.setText("go back");
        button.addActionListener(e -> cl.show(mainPanel, "Edit Schedule Panel"));
        addEventPanel.add(button);
        initAllFields();

    }

    // MODIFIES: this
    // EFFECTS: creates and initializes all the fields in the add event screen
    @SuppressWarnings("methodlength")
    private void initAllFields() {
        JLabel nameLabel = new JLabel("Enter a name for the event");
        nameLabel.setBounds(250, 20, 175, 50);
        JTextField nameText = new JTextField(30);
        nameText.setBounds(425, 20,100,  50);
        addEventPanel.add(nameLabel);
        addEventPanel.add(nameText);
        JLabel startHr = new JLabel();
        JTextField startHrText = new JTextField(30);
        addHr(startHr,startHrText,100, "starting");

        JLabel startMin = new JLabel("Enter starting min");
        startMin.setBounds(400, 100, 150, 50);
        JTextField startMinText = new JTextField(30);
        startMinText.setBounds(550, 100,150,50);

        JLabel endHr = new JLabel("Enter end hour");
        JTextField endHrText = new JTextField(30);
        addHr(endHr,endHrText,180, "end");

        JLabel endMin = new JLabel("Enter end min");
        endMin.setBounds(400, 180, 150, 50);
        JTextField endMinText = new JTextField(30);
        endMinText.setBounds(550, 180,150,50);

        addEventPanel.add(startHr);
        addEventPanel.add(startHrText);
        addEventPanel.add(startMin);
        addEventPanel.add(startMinText);
        addEventPanel.add(endHr);
        addEventPanel.add(endHrText);
        addEventPanel.add(endMin);
        addEventPanel.add(endMinText);

        JButton submit = new JButton("Add Event");
        submit.setBounds(375,300,200,50);
        submit.addActionListener(e -> {
            String name = nameText.getText();
            int startHour = Integer.parseInt(startHrText.getText());
            int startingMin = Integer.parseInt(startMinText.getText());
            int endHour = Integer.parseInt(endHrText.getText());
            int endingMin = Integer.parseInt(endMinText.getText());
            ScheduleEvent scheduleEvent = new ScheduleEvent(name, startHour,startingMin,endHour,endingMin);
            currentSchedule.addEvent(scheduleEvent);

            nameText.setText("");
            startHrText.setText("");
            startMinText.setText("");
            endHrText.setText("");
            endMinText.setText("");
            resetName();
            updateScheduleScreen();
            cl.show(mainPanel, "Starting Screen Panel");

        });
        addEventPanel.add(submit);
    }

    // EFFECTS: helper method that creates the labels and fields
    private void addHr(JLabel label, JTextField field, int i, String text) {
        label.setBounds(50,i,150,50);
        label.setText("Enter " + text + " hour");
        field.setBounds(200, i,150, 50);

    }



    // MODIFIES: currentSchedule
    // EFFECTS: creates and initializes remove event screen
    private void removeEventScreen() {
        removeEventPanel.setSize(750,750);
        removeEventPanel.setLayout(null);
        JLabel label = new JLabel("Type the name of the event you want to remove:");
        label.setBounds(200, 50, 350,50);
        listAllEvents();
        JTextField textField = new JTextField(20);
        textField.setBounds(200,375,100,50);
        JButton button = new JButton();
        button.setBounds(300, 375,100,50);
        button.setText("Submit");
        button.addActionListener(e -> {
            String output = textField.getText();
            currentSchedule.removeEvent(output);
            cl.show(mainPanel, "Edit Schedule Panel");
            resetName();
            updateScheduleScreen();
        });
        addBackButtonRemoveEventPanel();
        removeEventPanel.add(label);
        removeEventPanel.add(textField);
        removeEventPanel.add(button);
        removeEventPanel.setVisible(true);

    }

    // EFFECTS: lists out all the events in the remove event screen
    private void listAllEvents() {
        JPanel eventsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (ScheduleEvent e: currentSchedule.getEvents()) {
            JLabel label = new JLabel(e.getEventName());
            label.setVisible(true);
            eventsPanel.add(label);
        }
        eventsPanel.setBounds(200,100,300,200);
        eventsPanel.setVisible(true);
        removeEventPanel.add(eventsPanel);
    }

    // EFFECTS: adds a back button to the remove event screen
    private void addBackButtonRemoveEventPanel() {
        JButton button = new JButton();
        button.setBounds(600,0,150,50);
        button.setText("go back");
        button.addActionListener(e -> cl.show(mainPanel, "Edit Schedule Panel"));
        removeEventPanel.add(button);
    }

























}
