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
import java.util.Date;

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

//    // PRODUCT ACCOUNT UPDATE LIST MENU
//    private JScrollPane productAccountUpdateListMenu(ProductAccount productAccount) {
//        productAccountUpdateListModel = new DefaultListModel<ProductAccount>();
//        for (ProductAccount val : product.getProductAccounts()) {
//            productAccountListModel.addElement(val);
//        }
//
//        return listMenuHelper(productAccountJList, productAccountListModel, productAccountListJScrollPane);
//    }

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
//        jbuttonCreator("Print", "printCategory", buttonJPanel);
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
        jbuttonCreator("Back", "backToChooseCategory", buttonJPanel);
        jbuttonCreator("main", "main", buttonJPanel);
        return buttonJPanel;
    }

    // BUTTON MENU FOR PRODUCT ACCOUNTS
    private JPanel productButtonMenu() {
        buttonJPanel = new JPanel();

        productAccountCostLabel = new JLabel("Cost");
        productAccountCostField = new JTextField(10);
        productAccountCostLabel.setLabelFor(productAccountCostField);
        productAccountDateLabel = new JLabel("Date (yyyy-MM-dd)");
        productAccountDateField = new JTextField(10);
        productAccountDateLabel.setLabelFor(productAccountDateField);
        productAccountAmountLabel = new JLabel("Amount");
        productAccountAmountField = new JTextField(10);
        productAccountAmountLabel.setLabelFor(productAccountAmountField);
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
//
//        // delete this later
//        if (e.getActionCommand().equals("printCategory")) {
//            System.out.println(categoryManager.getCategories());
//            System.out.println(mainPanel.getComponents());
//        }
    }

    // Actions for inside a category
    public void actionPerformedCategory(ActionEvent e) {
        if (e.getActionCommand().equals("categoryStats")) {
            return;
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

        if (e.getActionCommand().equals("backToChooseCategory")) {
            cl.show(mainPanel, "menu");
        }
    }

    // Actions for inside a product
    public void actionPerformedProduct(ActionEvent e) {
        if (e.getActionCommand().equals("productStats")) {
            return;
        }

        if (e.getActionCommand().equals("createProductAccount")) {
            ProductAccount productAccountToBeAdded =
                    new ProductAccount(Integer.parseInt(productAccountAmountField.getText()),
                            Double.parseDouble(productAccountCostField.getText()),
                            productAccountDateField.getText());
            chosenProduct.addProductAccount(productAccountToBeAdded);
            productAccountToBeAddedPanel = productAccountUpdatePanelCreator(productAccountToBeAdded);
            mainPanel.add(productAccountToBeAddedPanel,
                    productAccountDateField.getText());
            if (chosenProduct.getProductAccounts().size() > productAccountListModel.size()) {
                productAccountListModel.add(0, productAccountToBeAdded);
            }
        }

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

        //USED TO BE PART OF CATEGORY MANAGER
        if (e.getActionCommand().equals("printProductAccount")) {
            System.out.println(chosenProduct.getProductAccounts());
            System.out.println(chosenProduct.getProductAccounts().get(0).getAmount());
            System.out.println(chosenProduct.getProductAccounts().get(0).getCost());
            System.out.println(chosenProduct.getProductAccounts().get(0).getDate());
        }

        if (e.getActionCommand().equals("backToChooseProduct")) {
            cl.show(mainPanel, chosenCategory.getName());
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