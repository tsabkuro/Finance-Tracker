package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.util.ArrayList;

// Represents category manager having a list of categories
public class CategoryManager implements Writable {
    private String name;
    private ArrayList<Category> categories; // list of categories
    private DefaultListModel categoryListModel;

    // EFFECTS: constructs workroom with a name and empty list of thingies
    public CategoryManager(String name) {
        this.name = name;
        categories = new ArrayList<>();
        categoryListModel = new DefaultListModel();
    }

    public DefaultListModel getCategoryListModel() {
        return categoryListModel;
    }

    /*
     * EFFECTS: returns name
     */
    public String getName() {
        return name;
    }

    /*
    * EFFECTS: returns list of categories
    */
    public ArrayList<Category> getCategories() {
        return categories;
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds category to categories
     *          if category already exists then does not add anything
     *          returns categories
     */
    public ArrayList<Category> addCategory(Category category) {
        for (Category i: categories) {
            if (i.getName().equals(category.getName())) {
                EventLog.getInstance().logEvent(new Event(category.getName()
                        + " category not added: Already exists"));
                return categories;
            }
        }
        categories.add(category);
        EventLog.getInstance().logEvent(new Event(category.getName() + " category added"));
        return categories;
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes category from categories
     *          returns categories
     */
    public ArrayList<Category> removeCategory(Category category) {
        categories.remove(category);
        EventLog.getInstance().logEvent(new Event(category.getName() + " category removed"));
        return categories;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("categories", categoriesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray categoriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Category c : categories) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
