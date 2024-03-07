package persistence;

import org.json.JSONArray;
import org.json.JSONObject;
import model.Schedule;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import model.Event;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        
        this.source = source;
    }

    // EFFECTS: reads schedule from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Schedule read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSchedule(jsonObject);
        
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses schedule from jsonObject and returns it
    private Schedule parseSchedule(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Schedule s = new Schedule(name);
        addEvents(s, jsonObject);
        return s;

    }

    // MODIFIES: s
    // EFFECTS: parses events from JSON object and adds them to the schedule
    private void addEvents(Schedule s, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("events");
        for (Object json : jsonArray) {
            JSONObject nextEvent = (JSONObject) json;
            addEvent(s, nextEvent);
        }

    }

    // MODIFIES: s
    // EFFECTS: parses event from JSON object and adds it to workroom
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
    
    



