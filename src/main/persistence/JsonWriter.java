package persistence;

import model.Event;
import model.Schedule;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.List;

import java.io.*;

// Represents a writer that writes JSON representation of Schedule to file
// Class inspired by JsonWriter in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriter {
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs a writer with String destination as the destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Schedules to file
    public void write(List<Schedule> schedules) {
        JSONArray jsonArray = new JSONArray();
        for (Schedule s : schedules) {
            JSONObject json = s.toJson();
            jsonArray.put(json);
        }
        saveToFile(jsonArray.toString(4));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    public void saveToFile(String json) {
        writer.print(json);
    }


}
