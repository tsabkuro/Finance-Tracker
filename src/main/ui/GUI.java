package ui;

import model.CategoryManager;
import model.Category;
import model.Product;
import model.ProductAccount;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

// GUI for the Finance Tracker Application
public class GUI implements ActionListener {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private static final String JSON_STORE = "./data/categoryManager.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private CategoryManager categoryManager;
    private CardLayout cl;

    private JPanel mainPanel;
    private JPanel productPanel;
    private JPanel productAccountPanel;
    private JPanel productAccountUpdatePanel;

    private JPanel buttonJPanel;

    private JScrollPane categoryListJScrollPane;
    private JList categoryJList;
    private JTextField createCategoryJTextField;

    private JScrollPane productListJScrollPane;

    private JScrollPane productAccountListJScrollPane;

    private JLabel productAccountCostLabel;
    private JLabel productAccountDateLabel;
    private JLabel productAccountAmountLabel;

    private JPanel productAccountToBeAddedPanel;

    private Category chosenCategory;
    private Product chosenProduct;
    private ProductAccount chosenProductAccount;

    private JPanel categoryStats;
    private JTextField dayCostInput;
    private JTextField monthCostInput;
    private JTextField yearCostInput;

    private JPanel productStats;
    private JTextField dayCostInputProduct;
    private JTextField monthCostInputProduct;
    private JTextField yearCostInputProduct;
    private JTextField dayAmountInputProduct;
    private JTextField monthAmountInputProduct;
    private JTextField yearAmountInputProduct;

    private JLabel dayCostLabel;
    private JLabel monthCostLabel;
    private JLabel yearCostLabel;
    private JLabel totalCostLabel;

    private JLabel dayCostLabelProduct;
    private JLabel monthCostLabelProduct;
    private JLabel yearCostLabelProduct;
    private JLabel totalCostLabelProduct;
    private JLabel dayAmountLabelProduct;
    private JLabel monthAmountLabelProduct;
    private JLabel yearAmountLabelProduct;
    private JLabel totalAmountLabelProduct;

    private Category categoryToBeAdded;

    private JFrame frame;

    // Initializes everything
    public GUI(CategoryManager categoryManager) {
        frame = new JFrame("Finance Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        ((JPanel) frame.getContentPane()).setBorder(new EmptyBorder(50, 50, 50, 50));
        frame.setLayout(new FlowLayout());
        this.categoryManager = categoryManager;

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        mainPanel = new JPanel(new CardLayout());

        cl = (CardLayout)(mainPanel.getLayout());

        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new GridLayout(2, 1));

        categoryPanel.add(categoryListMenu());
        categoryPanel.add(categoryManagerButtonMenu());

        mainPanel.add(categoryPanel, "menu");
        cl.show(mainPanel, "menu");

        frame.add(mainPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates JPanel for the product list and buttons inside category
    public Component productPanelCreator(Category category) {
        productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(2, 1));
        productPanel.add(productListMenu(category));
        productPanel.add(categoryButtonMenu(category));
        return productPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates JPanel for the product accounts list and buttons inside product
    public Component productAccountPanelCreator(Product product) {
        productAccountPanel = new JPanel();
        productAccountPanel.setLayout(new GridLayout(2, 1));
        productAccountPanel.add(productAccountListMenu(product));
        productAccountPanel.add(productButtonMenu(product));
        return productAccountPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates JPanel with buttons to update product account
    public JPanel productAccountUpdatePanelCreator(ProductAccount productAccount) {
        productAccountUpdatePanel = new JPanel();
        productAccountUpdatePanel.setLayout(new GridLayout(2, 1));
        productAccountUpdatePanel.add(productAccountButtonMenu(productAccount));
        return productAccountUpdatePanel;
    }

    // MODIFIES: this
    // EFFECTS: lists categories inside category manager
    private JScrollPane categoryListMenu() {
        for (Category val : categoryManager.getCategories()) {
            categoryManager.getCategoryListModel().addElement(val);
        }

        categoryJList = new JList(categoryManager.getCategoryListModel());
        categoryListJScrollPane = new JScrollPane(categoryJList);
        return listMenuHelper(categoryListJScrollPane);
    }

    // MODIFIES: this
    // EFFECTS: lists products inside category
    private JScrollPane productListMenu(Category category) {
        for (Product val : category.getProductList()) {
            category.getProductListModel().addElement(val);
        }

        productListJScrollPane = new JScrollPane(category.getProductJList());

        return listMenuHelper(productListJScrollPane);
    }

    // MODIFIES: this
    // EFFECTS: lists product accounts inside product
    private JScrollPane productAccountListMenu(Product product) {
        for (ProductAccount val : product.getProductAccounts()) {
            product.getProductAccountListModel().addElement(val);
        }

        productAccountListJScrollPane = new JScrollPane(product.getProductAccountJList());
        return listMenuHelper(productAccountListJScrollPane);
    }

    // EFFECTS: helper to create JScrollPane for the list menus
    public JScrollPane listMenuHelper(JScrollPane pane) {
        pane.setPreferredSize(new Dimension(800, 400));

        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        listPane.add(Box.createRigidArea(new Dimension(0,5)));
        listPane.add(pane);
        listPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        return pane;
    }


    // REQUIRES: category name to be unique
    // MODIFIES: this
    // EFFECTS: creates panel that contains buttons for categories
    private JPanel categoryManagerButtonMenu() {
        buttonJPanel = new JPanel();
        createCategoryJTextField = new JTextField(10);
        buttonJPanel.add(createCategoryJTextField);
        jbuttonCreator("Create", "createCategory", buttonJPanel);
        jbuttonCreator("Select", "chooseCategory", buttonJPanel);
        jbuttonCreator("Delete", "deleteCategory", buttonJPanel);
        jbuttonCreator("Save", "save", buttonJPanel);
        jbuttonCreator("Load", "loadCategory", buttonJPanel);
        return buttonJPanel;
    }

    // EFFECTS: creates panel that contains buttons for products
    private JPanel categoryButtonMenu(Category category) {
        category.getButtonPanel().add(category.getCreateProductJTextField());
        jbuttonCreator("Stats", "categoryStats", category.getButtonPanel());
        jbuttonCreator("Create", "createProduct", category.getButtonPanel());
        jbuttonCreator("Select", "chooseProduct", category.getButtonPanel());
        jbuttonCreator("Delete", "deleteProduct", category.getButtonPanel());
        jbuttonCreator("Save", "save", category.getButtonPanel());
        jbuttonCreator("main", "main", category.getButtonPanel());
        return category.getButtonPanel();
    }

    // MODIFIES: this
    // EFFECTS: creates panel that contains buttons for product accounts
    private JPanel productButtonMenu(Product product) {
        productAccountCostLabel = new JLabel("Cost");
        productAccountDateLabel = new JLabel("Date (yyyy-MM-dd)");
        productAccountAmountLabel = new JLabel("Amount");

        product.getButtonPanel().add(productAccountCostLabel);
        product.getButtonPanel().add(product.getProductAccountCostField());
        product.getButtonPanel().add(productAccountDateLabel);
        product.getButtonPanel().add(product.getProductAccountDateField());
        product.getButtonPanel().add(productAccountAmountLabel);
        product.getButtonPanel().add(product.getProductAccountAmountField());
        jbuttonCreator("Stats", "productStats", product.getButtonPanel());
        jbuttonCreator("Create", "createProductAccount", product.getButtonPanel());
        jbuttonCreator("Update", "chooseProductAccount", product.getButtonPanel());
        jbuttonCreator("Delete", "deleteProductAccount", product.getButtonPanel());
        jbuttonCreator("Save", "save", product.getButtonPanel());
        jbuttonCreator("back", "backToChooseProduct", product.getButtonPanel());
        jbuttonCreator("main", "main", product.getButtonPanel());
        return product.getButtonPanel();
    }


    // EFFECTS: creates panel that contains buttons to update product account
    private JPanel productAccountButtonMenu(ProductAccount productAccount) {
        productAccount.getButtonPanel().add(productAccount.getProductAccountUpdateCostField());
        jbuttonCreator("Update Cost", "updateCost", productAccount.getButtonPanel());
        productAccount.getButtonPanel().add(productAccount.getProductAccountUpdateDateField());
        jbuttonCreator("Update Date", "updateDate", productAccount.getButtonPanel());
        productAccount.getButtonPanel().add(productAccount.getProductAccountUpdateAmountField());
        jbuttonCreator("Add Amount", "addAmount", productAccount.getButtonPanel());
        jbuttonCreator("Remove Amount", "removeAmount", productAccount.getButtonPanel());
        jbuttonCreator("Save", "save", productAccount.getButtonPanel());
        jbuttonCreator("back", "backToChooseProductAccount", productAccount.getButtonPanel());
        jbuttonCreator("main", "main", productAccount.getButtonPanel());
        return productAccount.getButtonPanel();
    }

    // EFFECTS: helper to create JButtons and add to JPanel
    private JButton jbuttonCreator(String name, String actionCommand, JPanel panel) {
        JButton createdJButton = new JButton(name);
        createdJButton.setActionCommand(actionCommand);
        createdJButton.addActionListener(this);
        panel.add(createdJButton);
        return createdJButton;
    }

    // EFFECTS: listens to action event and shows specified panels tied to the action event
    public void actionPerformed(ActionEvent e) {
        actionPerformedCategoryManager(e);
        actionPerformedCategory(e);
        actionPerformedProduct(e);
        actionPerformedProductAccount(e);
        actionPerformedCategoryStats(e);
        actionPerformedProductStats(e);
        actionPerformedProductStatsAmount(e);
        if (e.getActionCommand().equals("main")) {
            cl.show(mainPanel, "menu");
        }

        if (e.getActionCommand().equals("loadCategory")) {
            loadCategoryManager();
            mainPanel.revalidate();
            mainPanel.repaint();
            mainPanel.updateUI();
        }

        if (e.getActionCommand().equals("save")) {
            saveCategoryManager();
        }
    }

    // MODIFIES: this
    // EFFECTS: actions for inside category manager
    public void actionPerformedCategoryManager(ActionEvent e) {
        if (e.getActionCommand().equals("createCategory")) {
            categoryToBeAdded = new Category(createCategoryJTextField.getText());
            categoryManager.addCategory(categoryToBeAdded);
            mainPanel.add(productPanelCreator(categoryToBeAdded), categoryToBeAdded.getName());
            if (categoryManager.getCategories().size() > categoryManager.getCategoryListModel().size()) {
                categoryManager.getCategoryListModel().add(0, categoryToBeAdded);
            }
        }

        if (e.getActionCommand().equals("chooseCategory")) {
            chosenCategory = (Category) categoryJList.getSelectedValue();

            if (!(chosenCategory == null)) {
                cl.show(mainPanel, chosenCategory.getName());
            }
        }

        if (e.getActionCommand().equals("deleteCategory")) {
            Category value = (Category) categoryJList.getSelectedValue();
            categoryManager.removeCategory(value);
            categoryManager.getCategoryListModel().removeElement(value);
        }
    }

    // REQUIRES: dates to be in form yyyy-MM-dd
    // MODIFIES: this
    // EFFECTS: creates menu for category stats panel
    public void categoryStatsHelper() {
        categoryStats = new JPanel();
        dayCostInput = new JTextField(10);
        monthCostInput = new JTextField(10);
        yearCostInput = new JTextField(10);
        dayCostLabel = new JLabel();
        monthCostLabel = new JLabel();
        yearCostLabel = new JLabel();
        totalCostLabel = new JLabel();

        categoryStats.add(dayCostLabel);
        categoryStats.add(dayCostInput);
        jbuttonCreator("Day Cost", "getDayCostCategory", categoryStats);
        categoryStats.add(monthCostLabel);
        categoryStats.add(monthCostInput);
        jbuttonCreator("Month Cost", "getMonthCostCategory", categoryStats);
        categoryStats.add(yearCostLabel);
        categoryStats.add(yearCostInput);
        jbuttonCreator("Year Cost", "getYearCostCategory", categoryStats);
        categoryStats.add(totalCostLabel);
        jbuttonCreator("Total Cost", "getTotalCostCategory", categoryStats);
        jbuttonCreator("Main", "main", categoryStats);
        mainPanel.add(categoryStats, "categoryStats");
    }

    // MODIFIES: this
    // EFFECTS: helper that listens to actions for inside category
    public void actionPerformedCategoryHelper(ActionEvent e) {
        if (e.getActionCommand().equals("chooseProduct")) {
            chosenProduct = (Product) chosenCategory.getProductJList().getSelectedValue();
            if (!(chosenProduct == null)) {
                cl.show(mainPanel, chosenProduct.getName());
            }
        }

        if (e.getActionCommand().equals("deleteProduct")) {
            Product value = (Product) chosenCategory.getProductJList().getSelectedValue();
            chosenCategory.removeProduct(value);
            chosenCategory.getProductListModel().removeElement(value);
        }
    }

    // EFFECTS: actions for inside category
    public void actionPerformedCategory(ActionEvent e) {
        if (e.getActionCommand().equals("categoryStats")) {
            categoryStatsHelper();
            cl.show(mainPanel, "categoryStats");
        }

        if (e.getActionCommand().equals("createProduct")) {
            Product productToBeAdded = new Product(chosenCategory.getCreateProductJTextField().getText());
            chosenCategory.addProduct(productToBeAdded);
            mainPanel.add(productAccountPanelCreator(productToBeAdded),
                    chosenCategory.getCreateProductJTextField().getText());
            if (chosenCategory.getProductList().size() > chosenCategory.getProductListModel().size()) {
                chosenCategory.getProductListModel().add(0, productToBeAdded);
            }
        }

        actionPerformedCategoryHelper(e);
    }

    // EFFECTS: rounds double to specified number of places
    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    // MODIFIES: this
    // EFFECTS: actions for inside category stats
    public void actionPerformedCategoryStats(ActionEvent e) {
        if (e.getActionCommand().equals("getDayCostCategory")) {
            dayCostLabel.setText(Double.toString(round(chosenCategory.getDayCost(dayCostInput.getText()), 2)));
            dayCostLabel.setFont(new Font("Serif", Font.PLAIN, 20));
            categoryStats.updateUI();
        }

        if (e.getActionCommand().equals("getMonthCostCategory")) {
            monthCostLabel.setText(Double.toString(round(chosenCategory.getMonthCost(monthCostInput.getText()), 2)));
            monthCostLabel.setFont(new Font("Serif", Font.PLAIN, 20));
            categoryStats.updateUI();
        }

        if (e.getActionCommand().equals("getYearCostCategory")) {
            yearCostLabel.setText(Double.toString(round(chosenCategory.getYearCost(yearCostInput.getText()), 2)));
            yearCostLabel.setFont(new Font("Serif", Font.PLAIN, 20));
            categoryStats.updateUI();
        }

        if (e.getActionCommand().equals("getTotalCostCategory")) {
            totalCostLabel.setText(Double.toString(round(chosenCategory.getTotalCost(), 2)));
            totalCostLabel.setFont(new Font("Serif", Font.PLAIN, 20));
            categoryStats.updateUI();
        }
    }

    // REQUIRES: dates to be in format yyyy-MM-dd
    // MODIFIES: this
    // EFFECTS: assigns variables for product stats
    public void productStatsVariableAssigner() {
        productStats = new JPanel();
        dayCostInputProduct = new JTextField(10);
        monthCostInputProduct = new JTextField(10);
        yearCostInputProduct = new JTextField(10);
        dayCostLabelProduct = new JLabel();
        monthCostLabelProduct = new JLabel();
        yearCostLabelProduct = new JLabel();
        totalCostLabelProduct = new JLabel();

        dayAmountInputProduct = new JTextField(10);
        monthAmountInputProduct = new JTextField(10);
        yearAmountInputProduct = new JTextField(10);
        dayAmountLabelProduct = new JLabel();
        monthAmountLabelProduct = new JLabel();
        yearAmountLabelProduct = new JLabel();
        totalAmountLabelProduct = new JLabel();
    }

    // MODIFIES: this
    // EFFECTS: hlper for creating menu for product stats
    public void productStatsMenuCreator1() {
        productStats.add(dayCostLabelProduct);
        productStats.add(dayCostInputProduct);
        jbuttonCreator("Day Cost", "getDayCostProduct", productStats);
        productStats.add(monthCostLabelProduct);
        productStats.add(monthCostInputProduct);
        jbuttonCreator("Month Cost", "getMonthCostProduct", productStats);
        productStats.add(yearCostLabelProduct);
        productStats.add(yearCostInputProduct);
        jbuttonCreator("Year Cost", "getYearCostProduct", productStats);
        productStats.add(totalCostLabelProduct);
        jbuttonCreator("Total Cost", "getTotalCostProduct", productStats);
    }

    // MODIFIES: this
    // EFFECTS: helper for creating menu for product stats
    public void productStatsMenuCreator2() {
        productStats.add(dayAmountLabelProduct);
        productStats.add(dayAmountInputProduct);
        jbuttonCreator("Day Amount", "getDayAmountProduct", productStats);
        productStats.add(monthAmountLabelProduct);
        productStats.add(monthAmountInputProduct);
        jbuttonCreator("Month Amount", "getMonthAmountProduct", productStats);
        productStats.add(yearAmountLabelProduct);
        productStats.add(yearAmountInputProduct);
        jbuttonCreator("Year Amount", "getYearAmountProduct", productStats);
        productStats.add(totalAmountLabelProduct);
        jbuttonCreator("Total Amount", "getTotalAmountProduct", productStats);
        jbuttonCreator("Main", "main", productStats);
        mainPanel.add(productStats, "productStats");
    }

    // MODIFIES: this
    // EFFECTS: creates menu for product stats
    public void productStatsHelper() {
        productStatsVariableAssigner();
        productStatsMenuCreator1();
        productStatsMenuCreator2();
    }

    // MODIFIES: this
    // EFFECTS: helper for actions for inside product. Creates product account
    public void actionPerformedProductHelper(ActionEvent e) {
        if (e.getActionCommand().equals("createProductAccount")) {
            ProductAccount productAccountToBeAdded =
                    new ProductAccount(Integer.parseInt(chosenProduct.getProductAccountAmountField().getText()),
                            Double.parseDouble(chosenProduct.getProductAccountCostField().getText()),
                            chosenProduct.getProductAccountDateField().getText());
            chosenProduct.addProductAccount(productAccountToBeAdded);
            productAccountToBeAddedPanel = productAccountUpdatePanelCreator(productAccountToBeAdded);
            mainPanel.add(productAccountToBeAddedPanel, chosenProduct.getProductAccountDateField().getText());
            if (chosenProduct.getProductAccounts().size() > chosenProduct.getProductAccountListModel().size()) {
                chosenProduct.getProductAccountListModel().add(0, productAccountToBeAdded);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: actions for inside product
    public void actionPerformedProduct(ActionEvent e) {
        if (e.getActionCommand().equals("productStats")) {
            productStatsHelper();
            cl.show(mainPanel, "productStats");
        }

        actionPerformedProductHelper(e);

        if (e.getActionCommand().equals("chooseProductAccount")) {
            chosenProductAccount = (ProductAccount) chosenProduct.getProductAccountJList().getSelectedValue();
            if (!(chosenProductAccount == null)) {
                cl.show(mainPanel, chosenProductAccount.getDate());
            }
        }

        if (e.getActionCommand().equals("deleteProductAccount")) {
            ProductAccount value = (ProductAccount) chosenProduct.getProductAccountJList().getSelectedValue();
            chosenProduct.removeProductAccount(value);
            chosenProduct.getProductAccountListModel().removeElement(value);
        }

        if (e.getActionCommand().equals("backToChooseProduct")) {
            cl.show(mainPanel, chosenCategory.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: actions for inside product stats
    public void actionPerformedProductStats(ActionEvent e) {
        if (e.getActionCommand().equals("getDayCostProduct")) {
            dayCostLabelProduct.setText(Double.toString(
                    round(chosenProduct.getDayCost(dayCostInputProduct.getText()), 2)));
            dayCostLabelProduct.setFont(new Font("Serif", Font.PLAIN, 20));
            productStats.updateUI();
        }

        if (e.getActionCommand().equals("getMonthCostProduct")) {
            monthCostLabelProduct.setText(Double.toString(
                    round(chosenProduct.getMonthCost(monthCostInputProduct.getText()), 2)));
            monthCostLabelProduct.setFont(new Font("Serif", Font.PLAIN, 20));
            productStats.updateUI();
        }

        if (e.getActionCommand().equals("getYearCostProduct")) {
            yearCostLabelProduct.setText(Double.toString(
                    round(chosenProduct.getYearCost(yearCostInputProduct.getText()), 2)));
            yearCostLabelProduct.setFont(new Font("Serif", Font.PLAIN, 20));
            productStats.updateUI();
        }

        if (e.getActionCommand().equals("getTotalCostProduct")) {
            totalCostLabelProduct.setText(Double.toString(round(chosenProduct.getTotalCost(), 2)));
            totalCostLabelProduct.setFont(new Font("Serif", Font.PLAIN, 20));
            productStats.updateUI();
        }
    }

    // MODIFIES: this
    // EFFECTS: actions for inside product stat amount
    public void actionPerformedProductStatsAmount(ActionEvent e) {
        if (e.getActionCommand().equals("getDayAmountProduct")) {
            dayAmountLabelProduct.setText(
                    Integer.toString(chosenProduct.getDayAmount(dayAmountInputProduct.getText())));
            dayAmountLabelProduct.setFont(new Font("Serif", Font.PLAIN, 20));
            productStats.updateUI();
        }

        if (e.getActionCommand().equals("getMonthAmountProduct")) {
            monthAmountLabelProduct.setText(
                    Integer.toString(chosenProduct.getMonthAmount(monthAmountInputProduct.getText())));
            monthAmountLabelProduct.setFont(new Font("Serif", Font.PLAIN, 20));
            productStats.updateUI();
        }

        if (e.getActionCommand().equals("getYearAmountProduct")) {
            yearAmountLabelProduct.setText(
                    Integer.toString(chosenProduct.getYearAmount(yearAmountInputProduct.getText())));
            yearAmountLabelProduct.setFont(new Font("Serif", Font.PLAIN, 20));
            productStats.updateUI();
        }

        if (e.getActionCommand().equals("getTotalAmountProduct")) {
            totalAmountLabelProduct.setText(Integer.toString(chosenProduct.getTotalAmount()));
            totalAmountLabelProduct.setFont(new Font("Serif", Font.PLAIN, 20));
            productStats.updateUI();
        }
    }

    // MODIFIES: this
    // EFFECTS: helper for actions for inside product stat amount
    public void actionPerformedProductAccountHelper(ActionEvent e) {
        if (e.getActionCommand().equals("addAmount")) {
            chosenProductAccount.addAmount(Integer.parseInt(
                    chosenProduct.getProductAccountAmountField().getText()));
            cl.show(mainPanel, chosenProduct.getName());
        }

        if (e.getActionCommand().equals("removeAmount")) {
            chosenProductAccount.removeAmount(Integer.parseInt(
                    chosenProduct.getProductAccountAmountField().getText()));
            cl.show(mainPanel, chosenProduct.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: actions for inside product account
    public void actionPerformedProductAccount(ActionEvent e) {
        if (e.getActionCommand().equals("updateCost")) {
            chosenProductAccount.setCost(
                    Double.parseDouble(chosenProductAccount.getProductAccountUpdateCostField().getText()));
            cl.show(mainPanel, chosenProduct.getName());
        }

        if (e.getActionCommand().equals("updateDate")) {
            mainPanel.remove(productAccountToBeAddedPanel);
            chosenProductAccount.setDate(chosenProductAccount.getProductAccountUpdateDateField().getText());
            productAccountToBeAddedPanel = productAccountUpdatePanelCreator(chosenProductAccount);
            mainPanel.add(productAccountToBeAddedPanel, chosenProductAccount.getDate());
            cl.show(mainPanel, chosenProduct.getName());
        }

        actionPerformedProductAccountHelper(e);

        if (e.getActionCommand().equals("backToChooseProductAccount")) {
            cl.show(mainPanel, chosenProduct.getName());
        }
    }

    // EFFECTS: saves the categoryManager to file
    private void saveCategoryManager() {
        try {
            jsonWriter.open();
            jsonWriter.write(categoryManager);
            jsonWriter.close();
            System.out.println("Saved " + categoryManager.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads categoryManager from file
    private void loadCategoryManager() {
        try {
            categoryManager = jsonReader.readCM();
            System.out.println("Loaded " + categoryManager.getName() + " from " + JSON_STORE);
            this.frame.dispose();
            new GUI(categoryManager);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}