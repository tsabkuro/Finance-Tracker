package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.util.ArrayList;

// Represents category of product having an id, name and product list
public class Category implements Writable {
    private static int nextCategoryId = 1;  // tracks id of category
    private int id;                        // category id
    private String name;                   // category name
    private ArrayList<Product> productList; // list of products
    private DefaultListModel productListModel; // model for list of products
    private JList productJList;                // JList of products
    private JPanel buttonPanel;                 // JPanel for buttons and text fields
    private JTextField createProductJTextField; // JTextField for naming products inside category

    /*
     * REQUIRES: categoryName has a non-zero length and is unique
     * EFFECTS: name on category set to categoryName
     *          id on category is a positive integer and unique
     *          productList is ArrayList of type Product
     *          productListModel is DefaultListModel of type Product
     *          buttonPanel is new JPanel
     *          createProductJTextField is new JTextField with 10 columns
     *          productJList is new JList that contains productListModel
     */
    public Category(String categoryName) {
        id = nextCategoryId++;
        name = categoryName;
        productList = new ArrayList<Product>();
        productListModel = new DefaultListModel();
        buttonPanel = new JPanel();
        createProductJTextField = new JTextField(10);
        productJList = new JList(productListModel);
    }

    public JList getProductJList() {
        return productJList;
    }

    public JTextField getCreateProductJTextField() {
        return createProductJTextField;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DefaultListModel getProductListModel() {
        return productListModel;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds product to category
     *          if product already exists then does not add anything
     *          returns product list
     */
    public ArrayList<Product> addProduct(Product product) {
        for (Product i: productList) {
            if (i.getName().equals(product.getName())) {
                return productList;
            }
        }
        productList.add(product);
        EventLog.getInstance().logEvent(new Event(product.getName() + "Product added"));
        return productList;
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes product from category
     *          returns product list
     */
    public ArrayList<Product> removeProduct(Product product) {
        productList.remove(product);
        EventLog.getInstance().logEvent(new Event(product.getName() + "Product removed"));
        return productList;
    }

    /*
     * REQUIRES: productDate in the format yyyy-MM-dd
     * EFFECTS: returns cost of products in category for a day
     */
    public double getDayCost(String productDate) {
        double dayCost = 0;
        for (Product i: productList) {
            dayCost += i.getDayCost(productDate);
        }
        return dayCost;
    }

    /*
     * REQUIRES: productDate in the format yyyy-MM
     * EFFECTS: returns cost of products in category for a month
     */
    public double getMonthCost(String productDate) {
        double monthCost = 0;
        for (Product i: productList) {
            monthCost += i.getMonthCost(productDate);
        }
        return monthCost;
    }

    /*
     * EFFECTS: returns cost of products in category for a year
     */
    public double getYearCost(String year) {
        double yearCost = 0;
        for (Product i: productList) {
            yearCost += i.getYearCost(year);
        }
        return yearCost;
    }

    /*
     * EFFECTS: returns total cost of products in category
     */
    public double getTotalCost() {
        double totalCost = 0;
        for (Product i: productList) {
            totalCost += i.getTotalCost();
        }
        return totalCost;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("products", productsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray productsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Product p : productList) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    public String toString() {
        return this.name;
    }
}
