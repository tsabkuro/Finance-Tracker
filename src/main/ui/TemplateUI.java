package ui;


// https://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
// https://stackoverflow.com/questions/1313390/is-there-any-way-to-accept-only-numeric-values-in-a-jtextfield
// https://stackoverflow.com/questions/25769024/handling-date-format-in-jspinner-with-spindatemodel THIS THE FIRST ONE

import model.CategoryManager;
import model.Category;
import model.Product;
import model.ProductAccount;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class TemplateUI implements ActionListener {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private static final String JSON_STORE = "./data/categoryManager.json";

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
    private DefaultListModel categoryListModel;

    private JScrollPane productListJScrollPane;
    private JList productJList;
    private JTextField createProductJTextField;
    private DefaultListModel productListModel;

    private JScrollPane productAccountListJScrollPane;
    private JList productAccountJList;
    private JLabel productAccountCostLabel;
    private JTextField productAccountCostField;
    private JLabel productAccountDateLabel;
    private JTextField productAccountDateField;
    private JLabel productAccountAmountLabel;
    private JTextField productAccountAmountField;
    private DefaultListModel productAccountListModel;

    private JPanel productAccountToBeAddedPanel;

    private JTextField productAccountUpdateCostField;
    private JTextField productAccountUpdateDateField;
    private JTextField productAccountUpdateAmountField;

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


    private JLabel statOutput;
    private JPanel statWrapper;

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

    // GUI for the category manager
    public TemplateUI() {
        JFrame frame = new JFrame("Finance Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        ((JPanel) frame.getContentPane()).setBorder(new EmptyBorder(50, 50, 50, 50));
        frame.setLayout(new FlowLayout());
        categoryManager = new CategoryManager("Main");

        mainPanel = new JPanel(new CardLayout());

        cl = (CardLayout)(mainPanel.getLayout());

        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new GridLayout(2, 1));

        categoryPanel.add(categoryListMenu());
        categoryPanel.add(categoryManagerButtonMenu());

        mainPanel.add(categoryPanel, "menu");
        mainPanel.add(productPanelCreator(new Category("test")), "test");
        cl.show(mainPanel, "menu");

        frame.add(mainPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Panel for inside a category
    public Component productPanelCreator(Category category) {
        productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(2, 1));
        productPanel.add(productListMenu(category));
        productPanel.add(categoryButtonMenu());
        return productPanel;
    }

    // Panel for inside a product
    public Component productAccountPanelCreator(Product product) {
        productAccountPanel = new JPanel();
        productAccountPanel.setLayout(new GridLayout(2, 1));
        productAccountPanel.add(productAccountListMenu(product));
        productAccountPanel.add(productButtonMenu());
        return productAccountPanel;
    }

    // Panel for inside a product account to update
    public JPanel productAccountUpdatePanelCreator(ProductAccount productAccount) {
        productAccountUpdatePanel = new JPanel();
        productAccountUpdatePanel.setLayout(new GridLayout(2, 1));
        productAccountUpdatePanel.add(productAccountButtonMenu());
        return productAccountUpdatePanel;
    }

    // CATEGORY LIST MENU
    private JScrollPane categoryListMenu() {
        categoryListModel = new DefaultListModel<Category>();
        for (Category val : categoryManager.getCategories()) {
            categoryListModel.addElement(val);
        }

        categoryJList = new JList(categoryListModel);
        categoryListJScrollPane = new JScrollPane(categoryJList);
        return listMenuHelper(categoryJList, categoryListModel, categoryListJScrollPane);
    }

    // PRODUCT LIST MENU
    private JScrollPane productListMenu(Category category) {
        productListModel = new DefaultListModel<Product>();
        for (Product val : category.getProductList()) {
            productListModel.addElement(val);
        }

        productJList = new JList(productListModel);
        productListJScrollPane = new JScrollPane(productJList);
        return listMenuHelper(productJList, productListModel, productListJScrollPane);
    }

    // PRODUCT ACCOUNT LIST MENU
    private JScrollPane productAccountListMenu(Product product) {
        productAccountListModel = new DefaultListModel<Product>();
        for (ProductAccount val : product.getProductAccounts()) {
            productAccountListModel.addElement(val);
        }

        productAccountJList = new JList(productAccountListModel);
        productAccountListJScrollPane = new JScrollPane(productAccountJList);
        return listMenuHelper(productAccountJList, productAccountListModel, productAccountListJScrollPane);
    }

    //list menu helper
    public JScrollPane listMenuHelper(JList jlist, DefaultListModel model, JScrollPane pane) {
        pane.setPreferredSize(new Dimension(800, 400));

        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        listPane.add(Box.createRigidArea(new Dimension(0,5)));
        listPane.add(pane);
        listPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        return pane;
    }

    // BUTTON MENU FOR CATEGORIES
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

    // BUTTON MENU FOR PRODUCTS
    private JPanel categoryButtonMenu() {
        buttonJPanel = new JPanel();
        createProductJTextField = new JTextField(10);
        buttonJPanel.add(createProductJTextField);
        jbuttonCreator("Stats", "categoryStats", buttonJPanel);
        jbuttonCreator("Create", "createProduct", buttonJPanel);
        jbuttonCreator("Select", "chooseProduct", buttonJPanel);
        jbuttonCreator("Delete", "deleteProduct", buttonJPanel);
        jbuttonCreator("Save", "save", buttonJPanel);
        jbuttonCreator("main", "main", buttonJPanel);
        return buttonJPanel;
    }

    // BUTTON MENU FOR PRODUCT ACCOUNTS
    private JPanel productButtonMenu() {
        buttonJPanel = new JPanel();

        productAccountCostLabel = new JLabel("Cost");
        productAccountCostField = new JTextField(10);
        productAccountDateLabel = new JLabel("Date (yyyy-MM-dd)");
        productAccountDateField = new JTextField(10);
        productAccountAmountLabel = new JLabel("Amount");
        productAccountAmountField = new JTextField(10);
        buttonJPanel.add(productAccountCostLabel);
        buttonJPanel.add(productAccountCostField);
        buttonJPanel.add(productAccountDateLabel);
        buttonJPanel.add(productAccountDateField);
        buttonJPanel.add(productAccountAmountLabel);
        buttonJPanel.add(productAccountAmountField);
        jbuttonCreator("Stats", "productStats", buttonJPanel);
        jbuttonCreator("Create", "createProductAccount", buttonJPanel);
        jbuttonCreator("Update", "chooseProductAccount", buttonJPanel);
        jbuttonCreator("Delete", "deleteProductAccount", buttonJPanel);
        jbuttonCreator("Print", "printProductAccount", buttonJPanel);
        jbuttonCreator("Save", "save", buttonJPanel);
        jbuttonCreator("back", "backToChooseProduct", buttonJPanel);
        jbuttonCreator("main", "main", buttonJPanel);
        return buttonJPanel;
    }


    // BUTTON MENU FOR PRODUCT ACCOUNT UPDATE
    private JPanel productAccountButtonMenu() {
        buttonJPanel = new JPanel();
        productAccountUpdateCostField = new JTextField(10);
        productAccountUpdateDateField = new JTextField(10);
        productAccountUpdateAmountField = new JTextField(10);
        buttonJPanel.add(productAccountUpdateCostField);
        jbuttonCreator("Update Cost", "updateCost", buttonJPanel);
        buttonJPanel.add(productAccountUpdateDateField);
        jbuttonCreator("Update Date", "updateDate", buttonJPanel);
        buttonJPanel.add(productAccountUpdateAmountField);
        jbuttonCreator("Add Amount", "addAmount", buttonJPanel);
        jbuttonCreator("Remove Amount", "removeAmount", buttonJPanel);
        jbuttonCreator("Save", "save", buttonJPanel);
        jbuttonCreator("back", "backToChooseProductAccount", buttonJPanel);
        jbuttonCreator("main", "main", buttonJPanel);
        return buttonJPanel;
    }

    //  JBUTTON HELPER
    private JButton jbuttonCreator(String name, String actionCommand, JPanel panel) {
        JButton createdJButton = new JButton(name);
        createdJButton.setActionCommand(actionCommand);
        createdJButton.addActionListener(this);
        panel.add(createdJButton);
        return createdJButton;
    }

    //    This is the method that is called when the the JButton btn is clicked
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

        if (e.getActionCommand().equals("save")) {
            return;
        }
    }

    // Actions for inside a category manager
    public void actionPerformedCategoryManager(ActionEvent e) {
        if (e.getActionCommand().equals("createCategory")) {
            Category categoryToBeAdded = new Category(createCategoryJTextField.getText());
            categoryManager.addCategory(categoryToBeAdded);
            mainPanel.add(productPanelCreator(categoryToBeAdded), createCategoryJTextField.getText());
            if (categoryManager.getCategories().size() > categoryListModel.size()) {
                categoryListModel.add(0, categoryToBeAdded);
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
            categoryListModel.removeElement(value);
        }
    }

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

    // Actions for inside a category
    public void actionPerformedCategory(ActionEvent e) {
        if (e.getActionCommand().equals("categoryStats")) {
            categoryStatsHelper();
            cl.show(mainPanel, "categoryStats");
        }

        if (e.getActionCommand().equals("createProduct")) {
            Product productToBeAdded = new Product(createProductJTextField.getText());
            chosenCategory.addProduct(productToBeAdded);
            mainPanel.add(productAccountPanelCreator(productToBeAdded), createProductJTextField.getText());
            if (chosenCategory.getProductList().size() > productListModel.size()) {
                productListModel.add(0, productToBeAdded);
            }
        }

        if (e.getActionCommand().equals("chooseProduct")) {
            chosenProduct = (Product) productJList.getSelectedValue();
            if (!(chosenProduct == null)) {
                cl.show(mainPanel, chosenProduct.getName());
            }
        }

        if (e.getActionCommand().equals("deleteProduct")) {
            Product value = (Product) productJList.getSelectedValue();
            chosenCategory.removeProduct(value);
            productListModel.removeElement(value);
        }
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

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

    public void productStatsHelperHelper() {
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

    public void productStatsHelperHelper2() {
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

    public void productStatsHelperHelper3() {
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

    public void productStatsHelper() {
        productStatsHelperHelper();
        productStatsHelperHelper2();
        productStatsHelperHelper3();
    }

    public void actionPerformedProductHelper(ActionEvent e) {
        if (e.getActionCommand().equals("createProductAccount")) {
            ProductAccount productAccountToBeAdded =
                    new ProductAccount(Integer.parseInt(productAccountAmountField.getText()),
                            Double.parseDouble(productAccountCostField.getText()), productAccountDateField.getText());
            chosenProduct.addProductAccount(productAccountToBeAdded);
            productAccountToBeAddedPanel = productAccountUpdatePanelCreator(productAccountToBeAdded);
            mainPanel.add(productAccountToBeAddedPanel, productAccountDateField.getText());
            if (chosenProduct.getProductAccounts().size() > productAccountListModel.size()) {
                productAccountListModel.add(0, productAccountToBeAdded);
            }
        }
    }

    // Actions for inside a product
    public void actionPerformedProduct(ActionEvent e) {
        if (e.getActionCommand().equals("productStats")) {
            productStatsHelper();
            cl.show(mainPanel, "productStats");
        }

        actionPerformedProductHelper(e);

        if (e.getActionCommand().equals("chooseProductAccount")) {
            chosenProductAccount = (ProductAccount) productAccountJList.getSelectedValue();
            if (!(chosenProductAccount == null)) {
                cl.show(mainPanel, chosenProductAccount.getDate());
            }
        }

        if (e.getActionCommand().equals("deleteProductAccount")) {
            Product value = (Product) productJList.getSelectedValue();
            chosenCategory.removeProduct(value);
            productListModel.removeElement(value);
        }

        if (e.getActionCommand().equals("backToChooseProduct")) {
            cl.show(mainPanel, chosenCategory.getName());
        }
    }

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

    // Actions for inside a product account
    public void actionPerformedProductAccount(ActionEvent e) {
        if (e.getActionCommand().equals("updateCost")) {
            chosenProductAccount.setCost(Double.parseDouble(productAccountUpdateCostField.getText()));
            cl.show(mainPanel, chosenProduct.getName());
        }

        if (e.getActionCommand().equals("updateDate")) {
            mainPanel.remove(productAccountToBeAddedPanel);
            chosenProductAccount.setDate(productAccountUpdateDateField.getText());
            productAccountToBeAddedPanel = productAccountUpdatePanelCreator(chosenProductAccount);
            mainPanel.add(productAccountToBeAddedPanel, chosenProductAccount.getDate());
            cl.show(mainPanel, chosenProduct.getName());
        }

        if (e.getActionCommand().equals("addAmount")) {
            chosenProductAccount.addAmount(Integer.parseInt(productAccountUpdateAmountField.getText()));
            cl.show(mainPanel, chosenProduct.getName());
        }

        if (e.getActionCommand().equals("removeAmount")) {
            chosenProductAccount.removeAmount(Integer.parseInt(productAccountUpdateAmountField.getText()));
            cl.show(mainPanel, chosenProduct.getName());
        }

        if (e.getActionCommand().equals("backToChooseProductAccount")) {
            cl.show(mainPanel, chosenProduct.getName());
        }
    }

    public static void main(String[] args) {
        new TemplateUI();
    }
}