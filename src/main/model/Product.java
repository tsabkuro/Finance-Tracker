package model;

import java.util.ArrayList;

// Represents a product having a name and list of product accounts
public class Product {
    private String name;        // the product name
    private ArrayList<ProductAccount> productAccounts;

    /*
     * REQUIRES: productName has a non-zero length
     * EFFECTS: name on product set to productName
     */
    public Product(String productName) {
        name = productName;
        productAccounts = new ArrayList<ProductAccount>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<ProductAccount> getProductAccounts() {
        return productAccounts;
    }

    /*
     * REQUIRES: productDate in the format yyyy-MM-dd
     * EFFECTS: returns total amount of product in a day
     */
    public int getDayAmount(String productDate) {
        return 0;
    }

    /*
     * REQUIRES: productDate in the format yyyy-MM
     * EFFECTS: returns total amount of product in a month of a year
     */
    public int getMonthAmount(String productDate) {
        return 0;
    }

    /*
     * EFFECTS: returns total amount of product in a year
     */
    public int getYearAmount(String year) {
        return 0;
    }

    /*
     *  EFFECTS: returns total amount of product
     */
    public int getTotalAmount() {
        return 0;
    }

    /*
     * REQUIRES: productDate in the format yyyy-MM-dd
     * EFFECTS: returns total amount of product in a day
     */
    public double getDayCost(String productDate) {
        return 0;
    }

    /*
     *  EFFECTS: returns cost of product in a month
     */
    public double getMonthCost(String productDate) {
        return 0;
    }

    /*
     * EFFECTS: returns total cost of product in a year
     */
    public double getYearCost(String year) {
        return 0;
    }

    /*
     *  EFFECTS: returns total cost of product
     */
    public double getTotalCost() {
        return 0;
    }

    /*
     *  EFFECTS: adds productAccount to product
     *          returns product account list
     */
    public ArrayList<ProductAccount> addProductAccount(ProductAccount productAccount) {
        return new ArrayList<ProductAccount>();
    }

    /*
     *  EFFECTS: removes productAccount from product
     *          returns product account list
     */
    public ArrayList<ProductAccount> removeProductAccount(ProductAccount productAccount) {
        return new ArrayList<ProductAccount>();
    }
}
