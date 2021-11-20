package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.util.ArrayList;

// Represents a product having a name and list of product accounts
public class Product implements Writable {
    private String name;        // the product name
    private ArrayList<ProductAccount> productAccounts; // list of product accounts
    private DefaultListModel productAccountListModel; // model for list of product account
    private JPanel buttonPanel;                       // JPanel for buttons and text fields
    private JTextField productAccountCostField;       // cost input
    private JTextField productAccountDateField;       // date input
    private JTextField productAccountAmountField;     // amount input
    private JList productAccountJList;                // JList of product accounts


    /*
     * REQUIRES: productName has a non-zero length and is unique
     * EFFECTS: name on product set to productName
     *          productAccounts is ArrayList of type ProductAccount
     *          productAccountListModel is DefaultListModel of type ProductAccount
     *          buttonPanel is new JPanel
     *          productAccountCostField is a new JTextField with 10 columns
     *          productAccountDateField is a new JTextField with 10 columns
     *          productAccountAmountField is a new JTextField with 10 columns
     *          productAccountJList is a new JList that contains productAccountListModel
     */
    public Product(String productName) {
        name = productName;
        productAccounts = new ArrayList<ProductAccount>();
        productAccountListModel = new DefaultListModel();
        buttonPanel = new JPanel();
        productAccountCostField = new JTextField(10);
        productAccountDateField = new JTextField(10);
        productAccountAmountField = new JTextField(10);
        productAccountJList = new JList(productAccountListModel);
    }

    public JList getProductAccountJList() {
        return productAccountJList;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public JTextField getProductAccountCostField() {
        return productAccountCostField;
    }

    public JTextField getProductAccountAmountField() {
        return productAccountAmountField;
    }

    public JTextField getProductAccountDateField() {
        return productAccountDateField;
    }

    public String getName() {
        return name;
    }

    public DefaultListModel getProductAccountListModel() {
        return productAccountListModel;
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("product accounts", productAccountsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray productAccountsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (ProductAccount pa : productAccounts) {
            jsonArray.put(pa.toJson());
        }

        return jsonArray;
    }
}
