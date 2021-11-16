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

public class TemplateUI extends JFrame implements ActionListener {
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

    // GUI for the category manager
    public TemplateUI() {
        super("Finance Tracker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(50, 50, 50, 50));
        setLayout(new FlowLayout());
        categoryManager = new CategoryManager("Main");


        add(categoryListMenu());
        add(categoryManagerButtonMenu());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }





    public void addComponentToPane(Container pane) {
        //Put the JComboBox in a JPanel to get a nicer look.
//        JPanel comboBoxPane = new JPanel(); //use FlowLayout
//        String comboBoxItems[] = { BUTTONPANEL, TEXTPANEL };
//        JComboBox cb = new JComboBox(comboBoxItems);
//        cb.setEditable(false);
//        cb.addItemListener(this);
//        comboBoxPane.add(cb);


        //Create the panel that contains the "cards".
        mainPanel = new JPanel(new CardLayout());

        mainPanel.add(categoryListMenu(), "menu");
        mainPanel.add(productListMenu(), "productListMenu");

        pane.add(mainPanel, BorderLayout.CENTER);
        pane.add(categoryManagerButtonMenu(), BorderLayout.PAGE_END);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("CardLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        TemplateUI demo = new TemplateUI();
        demo.addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
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
    private JScrollPane productListMenu() {
        productListModel = new DefaultListModel<Category>();
        for (Product val : categoryManager.getCategories().get(0).getProductList()) {
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
            categoryManager.addCategory(new Category(createCategoryJTextField.getText()));
            if (categoryManager.getCategories().size() > categoryListModel.size()) {
                categoryListModel.add(0,
                        categoryManager.getCategories().get(categoryManager.getCategories().size() - 1));
            }
        }
        if (e.getActionCommand().equals("chooseCategory")) {
            Category test = (Category) categoryJList.getSelectedValue();
            System.out.println(test);

            CardLayout cl = (CardLayout)(mainPanel.getLayout());
            cl.show(mainPanel, e.getActionCommand());
            System.out.println(e.getActionCommand());
        }
        if (e.getActionCommand().equals("deleteCategory")) {
            Category test = (Category) categoryJList.getSelectedValue();
            categoryManager.removeCategory(test);
            categoryListModel.removeElement(test);
        }

        if (e.getActionCommand().equals("printCategory")) {
            System.out.println(categoryManager.getCategories());
        }
    }

//    public static void main(String[] args) {
//        new TemplateUI();
//    }
}