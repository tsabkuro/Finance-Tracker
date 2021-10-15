package model;

import java.util.ArrayList;

// Represents category of product having an id and name
public class Category {
    private static int nextCategoryId = 1;  // tracks id of category
    private int id;                        // category id
    private String name;                   // category name
    private ArrayList<Product> productList;

    /*
     * REQUIRES: categoryName has a non-zero length
     * EFFECTS: name on category set to categoryName
     *          id on category is a positive integer and unique
     *          productList is ArrayList of type Product
     */
    public Category(String categoryName) {
        id = nextCategoryId++;
        name = categoryName;
        productList = new ArrayList<Product>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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
        return new ArrayList<Product>();
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes product from category
     *          if product does not exist then changes nothing
     *          returns product list
     */
    public ArrayList<Product> removeProduct(Product product) {
        return new ArrayList<Product>();
    }

    /*
     * REQUIRES: productDate in the format yyyy-MM-dd
     * EFFECTS: returns cost of products in category for a day
     */
    public double getDayCost(String productDate) {
        return 0;
    }

    /*
     * REQUIRES: productDate in the format yyyy-MM
     * EFFECTS: returns cost of products in category for a month
     */
    public double getMonthCost(String productDate) {
        return 0;
    }

    /*
     * EFFECTS: returns cost of products in category for a year
     */
    public double getYearCost(String year) {
        return 0;
    }

    /*
     * EFFECTS: returns total cost of products in category
     */
    public double getTotalCost() {
        return 0;
    }
}
