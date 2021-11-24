package model;

import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;

// Represents a product account having a cost, amount, and date bought
public class ProductAccount implements Writable {
    private double cost;                    // the product cost (in dollars)
    private String date;                    // date the product was bought
    private int amount;                     // amount of product bought
    private JPanel buttonPanel;             // JPanel for buttons and text fields
    private JTextField productAccountUpdateCostField;
    private JTextField productAccountUpdateDateField;
    private JTextField productAccountUpdateAmountField;

    /*
     * REQUIRES: productName has a non-zero length
     *           productDate is in format yyyy-MM-dd
     * EFFECTS: cost on product set to productCost
     *          date product was bought set to productDate
     *          amount of product bought set to productAmount
     */
    public ProductAccount(int productAmount, double productCost, String productDate) {
        amount = productAmount;
        cost = productCost;
        date = productDate;
        buttonPanel = new JPanel();
        productAccountUpdateCostField = new JTextField(10);
        productAccountUpdateDateField = new JTextField(10);
        productAccountUpdateAmountField = new JTextField(10);
    }

    public JTextField getProductAccountUpdateAmountField() {
        return productAccountUpdateAmountField;
    }

    public JTextField getProductAccountUpdateCostField() {
        return productAccountUpdateCostField;
    }

    public JTextField getProductAccountUpdateDateField() {
        return productAccountUpdateDateField;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public int getAmount() {
        return amount;
    }

    public double getCost() {
        return cost;
    }

    public String getDate() {
        return date;
    }

    public String getMonth() {
        return date.substring(0, date.length() - 3);
    }

    public String getYear() {
        return date.substring(0, date.length() - 6);
    }

    /*
     * REQUIRES: productCost >= 0
     * MODIFIES: this
     * EFFECTS: cost updated to productCost
     * 		    returns cost
     */
    public double setCost(double productCost) {
        cost = productCost;
        EventLog.getInstance().logEvent(new Event("Cost set to " + productCost));
        return cost;
    }

    /*
     * MODIFIES: this
     * EFFECTS: date updated to productDate
     * 		    returns date
     */
    public String setDate(String productDate) {
        date = productDate;
        EventLog.getInstance().logEvent(new Event("Date set to " + productDate));
        return date;
    }

    /*
     * REQUIRES: productAmount >= 0
     * MODIFIES: this
     * EFFECTS: productAmount is added to amount and updated
     * 			returns amount
     */
    public int addAmount(int productAmount) {
        amount += productAmount;
        EventLog.getInstance().logEvent(new Event(productAmount + " amount added"));
        return amount;
    }

    /*
     * REQUIRES: 0 <= productAmount <= getAmount()
     * MODIFIES: this
     * EFFECTS: productAmount is removed from amount and updated
     * 		    returns amount
     */
    public int removeAmount(int productAmount) {
        amount -= productAmount;
        EventLog.getInstance().logEvent(new Event(productAmount + " amount removed"));
        return amount;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("amount", amount);
        json.put("cost", cost);
        json.put("date", date);
        return json;
    }

    public String toString() {
        return this.date;
    }
}

