package persistence;

import org.json.JSONObject;

// Interface for classes that can be converted to Json and written to file
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
