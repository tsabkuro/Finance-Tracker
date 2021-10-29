package model;

import java.util.ArrayList;

// Represents category of product having an id, name and product list
public class Category {
    private static int nextCategoryId = 1;  // tracks id of category
    private int id;                        // category id
    private String name;                   // category name
    private ArrayList<Product> productList; // list of products

    /*
     * REQUIRES: categoryName has a non-zero length and is unique
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
        for (Product i: productList) {
            if (i.getName().equals(product.getName())) {
                return productList;
            }
        }
        productList.add(product);
        return productList;
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes product from category
     *          returns product list
     */
    public ArrayList<Product> removeProduct(Product product) {
        productList.remove(product);
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
}
