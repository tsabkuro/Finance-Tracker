package model;

import java.util.ArrayList;

// Represents a product having a name and list of product accounts
public class Product {
    private String name;        // the product name
    private ArrayList<ProductAccount> productAccounts; // list of product accounts

    /*
     * REQUIRES: productName has a non-zero length and is unique
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
        int dayAmount = 0;
        for (ProductAccount i: productAccounts) {
            if (i.getDate().equals(productDate)) {
                dayAmount += i.getAmount();
            }
        }
        return dayAmount;
    }

    /*
     * REQUIRES: productDate in the format yyyy-MM
     * EFFECTS: returns total amount of product in a month of a year
     */
    public int getMonthAmount(String productDate) {
        int monthAmount = 0;
        for (ProductAccount i: productAccounts) {
            if (i.getMonth().equals(productDate)) {
                monthAmount += i.getAmount();
            }
        }
        return monthAmount;
    }

    /*
     * REQUIRES: year is in format yyyy
     * EFFECTS: returns total amount of product in a year
     */
    public int getYearAmount(String year) {
        int yearAmount = 0;
        for (ProductAccount i: productAccounts) {
            if (i.getYear().equals(year)) {
                yearAmount += i.getAmount();
            }
        }
        return yearAmount;
    }

    /*
     *  EFFECTS: returns total amount of product
     */
    public int getTotalAmount() {
        int totalAmount = 0;
        for (ProductAccount i: productAccounts) {
            totalAmount += i.getAmount();
        }
        return totalAmount;
    }

    /*
     * REQUIRES: productDate in the format yyyy-MM-dd
     * EFFECTS: returns total amount of product in a day
     */
    public double getDayCost(String productDate) {
        double dayCost = 0;
        for (ProductAccount i: productAccounts) {
            if (i.getDate().equals(productDate)) {
                dayCost += i.getCost();
            }
        }
        return dayCost;
    }

    /*
     *  EFFECTS: returns cost of product in a month
     */
    public double getMonthCost(String productDate) {
        double monthCost = 0;
        for (ProductAccount i: productAccounts) {
            if (i.getMonth().equals(productDate)) {
                monthCost += i.getCost();
            }
        }
        return monthCost;
    }

    /*
     * EFFECTS: returns total cost of product in a year
     */
    public double getYearCost(String year) {
        double yearCost = 0;
        for (ProductAccount i: productAccounts) {
            if (i.getYear().equals(year)) {
                yearCost += i.getCost();
            }
        }
        return yearCost;
    }

    /*
     *  EFFECTS: returns total cost of product
     */
    public double getTotalCost() {
        double totalCost = 0;
        for (ProductAccount i: productAccounts) {
            totalCost += i.getCost();
        }
        return totalCost;
    }

    /*
     *  EFFECTS: adds productAccount to product
     *          returns product account list
     */
    public ArrayList<ProductAccount> addProductAccount(ProductAccount productAccount) {
        productAccounts.add(productAccount);
        return productAccounts;
    }

    /*
     *  EFFECTS: removes productAccount from product
     *          returns product account list
     */
    public ArrayList<ProductAccount> removeProductAccount(ProductAccount productAccount) {
        productAccounts.remove(productAccount);
        return productAccounts;
    }
}
