package persistence;

import org.json.JSONArray;
import org.json.JSONObject;
import model.Schedule;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.List;
import model.Event;

// JsonReader class, reads all data to json file
// Class inspired by JsonWriter in JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        
        this.source = source;
    }

    // EFFECTS: reads schedules from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<Schedule> read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseSchedules(jsonArray);
        
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses all schedules from jsonArray and returns it
    private List<Schedule> parseSchedules(JSONArray jsonArray) {
        List<Schedule> schedules = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            String name = jsonObject.getString("name");
            Schedule s = new Schedule(name);
            addEvents(s, jsonObject);
            schedules.add(s);
        }
        return schedules;

    }

    // MODIFIES: this
    // EFFECTS: parses events from JSON object and adds them to the schedule
    private void addEvents(Schedule s, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("events");
        for (Object json : jsonArray) {
            JSONObject nextEvent = (JSONObject) json;
            addEvent(s, nextEvent);
        }

    }

    // MODIFIES: this
    // EFFECTS: parses event from JSON object and adds it to schedule
    private void addEvent(Schedule s, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Integer startHour = jsonObject.getInt("start hour");
        Integer startMin = jsonObject.getInt("start minute");
        Integer endHour = jsonObject.getInt("end hour");
        Integer endMin = jsonObject.getInt("end minute");
        Event e = new Event(name, startHour,startMin,endHour,endMin);
        s.addEvent(e);
    }

}
    
    



