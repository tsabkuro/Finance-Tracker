package model;

// Represents a product account having a cost, amount, and date bought
public class ProductAccount {
    private double cost;                    // the product cost (in dollars)
    private String date;                    // date the product was bought
    private int amount;                     // amount of product bought

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
    }

    public int getAmount() {
        return amount;
    }

    public double getCost() {
        return cost;
    }

    /*
     * EFFECTS: returns date of product account in string form
     */
    public String getDate() {
        return "";
    }

    /*
     * REQUIRES: productCost >= 0
     * MODIFIES: this
     * EFFECTS: cost updated to productCost
     * 		    returns cost
     */
    public double setCost(double productCost) {
        return cost;
    }

    /*
     * MODIFIES: this
     * EFFECTS: date updated to productDate
     * 		    returns date
     */
    public String setDate(String productDate) {
        return date;
    }

    /*
     * REQUIRES: productAmount >= 0
     * MODIFIES: this
     * EFFECTS: productAmount is added to amount and updated
     * 			returns amount
     */
    public int addAmount(int productAmount) {
        return amount;
    }

    /*
     * REQUIRES: 0 <= productAmount <= getAmount()
     * MODIFIES: this
     * EFFECTS: productAmount is removed from amount and updated
     * 		    returns amount
     */
    public int removeAmount(int productAmount) {
        return amount;
    }
}
