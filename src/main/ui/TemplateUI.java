package ui;

import model.CategoryManager;
import model.Category;
import model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

public class TemplateUI implements ActionListener {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private static final String JSON_STORE = "./data/categoryManager.json";

    private CategoryManager categoryManager;


    private JPanel buttonJPanel;

    private JScrollPane categoryListJScrollPane;
    private JList categoryJList;
    JTextField createCategoryJTextField;
    private DefaultListModel categoryListModel;

    private JScrollPane productListJScrollPane;
    private JList productJList;
    JTextField createProductJTextField;
    private DefaultListModel productListModel;


    private JPanel mainPanel;

    private CardLayout cl;

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

        frame.add(mainPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public Component productPanelCreator(Category category) {
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(2, 1));

        productPanel.add(productListMenu(category));
        productPanel.add(categoryButtonMenu());
        return productPanel;
    }

    public static void main(String[] args) {
        new TemplateUI();
    }

    // CATEGORY LIST MENU
    private JScrollPane categoryListMenu() {
        categoryListModel = new DefaultListModel<Category>();
        for (Category val : categoryManager.getCategories()) {
            categoryListModel.addElement(val);
        }

        categoryJList = new JList(categoryListModel);
        categoryListJScrollPane = new JScrollPane(categoryJList);
        categoryListJScrollPane.setPreferredSize(new Dimension(800, 400));

        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        listPane.add(Box.createRigidArea(new Dimension(0,5)));
        listPane.add(categoryListJScrollPane);
        listPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        return categoryListJScrollPane;
    }

    // PRODUCT LIST MENU
    private JScrollPane productListMenu(Category category) {
        productListModel = new DefaultListModel<Category>();
        for (Product val : category.getProductList()) {
            productListModel.addElement(val);
        }

        productJList = new JList(productListModel);
        productListJScrollPane = new JScrollPane(categoryJList);
        productListJScrollPane.setPreferredSize(new Dimension(800, 400));

        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        listPane.add(Box.createRigidArea(new Dimension(0,5)));
        listPane.add(productListJScrollPane);
        listPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        return productListJScrollPane;
    }

    // BUTTON MENU FOR CATEGORIES
    private JPanel categoryManagerButtonMenu() {
        buttonJPanel = new JPanel();
        createCategoryJTextField = new JTextField(10);
        buttonJPanel.add(createCategoryJTextField);
        jbuttonCreator("Create", "createCategory", buttonJPanel);
        jbuttonCreator("Select", "chooseCategory", buttonJPanel);
        jbuttonCreator("Delete", "deleteCategory", buttonJPanel);
        jbuttonCreator("Print", "printCategory", buttonJPanel);
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
        jbuttonCreator("Print", "printProduct", buttonJPanel);
        jbuttonCreator("Save", "save", buttonJPanel);
        jbuttonCreator("back", "backToChooseCategory", buttonJPanel);
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
        if (e.getActionCommand().equals("createCategory")) {
            Category categoryToBeAdded = new Category(createCategoryJTextField.getText());
            categoryManager.addCategory(categoryToBeAdded);
//            mainPanel.add(productPanelCreator(categoryToBeAdded), createCategoryJTextField.getText());
            if (categoryManager.getCategories().size() > categoryListModel.size()) {
                categoryListModel.add(0, categoryToBeAdded);
            }
        }

        if (e.getActionCommand().equals("chooseCategory")) {
            Category value = (Category) categoryJList.getSelectedValue();
            System.out.println(value);
            if (!(value == null)) {
                cl.show(mainPanel, value.getName());
            }
        }

        if (e.getActionCommand().equals("deleteCategory")) {
            Category test = (Category) categoryJList.getSelectedValue();
            categoryManager.removeCategory(test);
            categoryListModel.removeElement(test);
        }

        if (e.getActionCommand().equals("printCategory")) {
            System.out.println(categoryManager.getCategories());
            System.out.println(mainPanel.getComponents());
        }
    }

//    public static void main(String[] args) {
//        new TemplateUI();
//    }
}