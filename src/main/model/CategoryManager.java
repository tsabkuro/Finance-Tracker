package model;

import java.util.ArrayList;

public class CategoryManager {
    private ArrayList<Category> categories = new ArrayList<Category>();

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
                return categories;
            }
        }
        categories.add(category);
        return categories;
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes category from categories
     *          returns categories
     */
    public ArrayList<Category> removeCategory(Category category) {
        categories.remove(category);
        return categories;
    }
}
