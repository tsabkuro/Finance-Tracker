package persistence;

import model.Category;
import model.CategoryManager;
import model.Product;
import org.json.JSONObject;


import java.io.*;

// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
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
    // EFFECTS: writes JSON representation of category manager to file
    public void write(CategoryManager cm) {
        JSONObject jsonCM = cm.toJson();
        saveToFile(jsonCM.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of category manager to file
    public void write(Category c) {
        JSONObject jsonCM = c.toJson();
        saveToFile(jsonCM.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}