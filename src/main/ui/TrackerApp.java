package ui;

import java.util.Scanner;

import model.CategoryManager;
import model.Product;
import model.ProductAccount;
import model.Category;

// Finance tracker application
public class TrackerApp {
    private CategoryManager categoryManager;
    private Scanner input;

    // EFFECTS: runs finance tracker application
    public TrackerApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        boolean running = true;
        String command;

        init();

        while (running) {
            displayCategoryManagerChoice();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                running = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nProcess ended!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("create")) {
            createCategory();
        } else if (command.equals("choose")) {
            chooseCategory();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes category manager
    private void init() {
        categoryManager = new CategoryManager();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options from category manager
    private void displayCategoryManagerChoice() {
        System.out.println("\nSelect from:");
        System.out.println("\tcreate -> create category");
        System.out.println("\tchoose -> choose a category");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays menu of options from category
    private void displayCategoryChoice() {
        System.out.println("\nSelect from:");
        System.out.println("\tcreate -> create product");
        System.out.println("\tchoose -> choose a product");
        System.out.println("\tback -> go back");
    }

    // EFFECTS: displays menu of options from product
    private void displayProductChoice() {
        System.out.println("\nSelect from:");
        System.out.println("\tcreate -> create product account");
        System.out.println("\tchoose -> choose a product account to update");
        System.out.println("\tback -> go back");
    }

    // EFFECTS: displays menu of options from product account
    private String displayProductAccountChoice() {
        System.out.println("\nSelect from:");
        System.out.println("\tcost -> change cost of product account");
        System.out.println("\tdate -> change date of product account");
        System.out.println("\tadd -> add certain amount of product");
        System.out.println("\tremove -> remove certain amount of product");
        System.out.println("\tback -> go back");
        return input.next();
    }

    // MODIFIES: this
    // EFFECTS: creates a category
    private void createCategory() {
        System.out.print("Enter a category name: ");

        String categoryName = input.next();

        categoryManager.addCategory(new Category(categoryName));
    }


    // EFFECTS: gives user list of category choices
    private void chooseCategory() {
        System.out.print("List of Categories: ");
        for (Category i: categoryManager.getCategories()) {
            System.out.println(i.getName());
        }

        String choice = input.next();

        for (Category i: categoryManager.getCategories()) {
            if (i.getName().equals(choice)) {
                chosenCategory(i);
                return;
            }
        }

        System.out.print("Category does not exist");
    }

    // EFFECTS: gives user a list of choices for chosen category
    private void chosenCategory(Category category) {
        displayCategoryChoice();
        String command = input.next();

        if (command.equals("create")) {
            createProduct(category);
        } else if (command.equals("choose")) {
            chooseProduct(category);
        } else if (command.equals("back")) {
            chooseCategory();
        } else {
            System.out.println("Selection not valid...");
        }

    }

    // MODIFIES: this
    // EFFECTS: creates a product
    private void createProduct(Category category) {
        System.out.println("Enter product name: ");
        String productName = input.next();

        category.addProduct(new Product(productName));

        chosenCategory(category);
    }

    // EFFECTS: gives user a list of product choices
    private void chooseProduct(Category category) {
        System.out.println("List of Products: ");
        for (Product i: category.getProductList()) {
            System.out.println(i.getName());
        }

        String choice = input.next();

        for (Product i: category.getProductList()) {
            if (i.getName().equals(choice)) {
                chosenProduct(i, category);
                return;
            }
        }

        System.out.print("Product does not exist");
    }

    // EFFECTS: gives user a list of choices for chosen product
    private void chosenProduct(Product product, Category category) {
        displayProductChoice();
        String command = input.next();

        if (command.equals("create")) {
            createProductAccount(product, category);
        } else if (command.equals("choose")) {
            chooseProductAccount(product);
        } else if (command.equals("back")) {
            chooseProduct(category);
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a product account
    private void createProductAccount(Product product, Category category) {
        System.out.print("Enter cost of product: $");
        double productCost = input.nextDouble();
        System.out.print("\nEnter date (yyyy-MM-dd): ");
        String productDate = input.next();
        System.out.print("\nEnter quantity of product: ");
        int productAmount = input.nextInt();

        product.addProductAccount(new ProductAccount(productAmount, productCost, productDate));

        chosenProduct(product, category);
    }

    // EFFECTS: gives user a list of product accounts to choose from
    private void chooseProductAccount(Product product) {
        System.out.println("List of Products Accounts: ");
        for (ProductAccount i: product.getProductAccounts()) {
            System.out.println("Date: " + i.getDate() + "\tCost: " + i.getCost() + "\tAmount: " + i.getAmount());
        }

        System.out.print("Enter date of Product Account to update (yyyy-MM-dd): ");
        String choice = input.next();

        for (ProductAccount i: product.getProductAccounts()) {
            if (i.getDate().equals(choice)) {
                chosenProductAccount(i, product);
                return;
            }
        }

        System.out.print("Product Account does not exist");
    }

    // EFFECTS: gives user a list of choices for chosen product account
    private void chosenProductAccount(ProductAccount productAccount, Product product) {
        String command = displayProductAccountChoice();

        if (command.equals("cost")) {
            System.out.print("Enter a price: $");
            double newCost = input.nextDouble();
            productAccount.setCost(newCost);
        } else if (command.equals("date")) {
            System.out.print("Enter a date(yyyy-MM-dd): ");
            String newDate = input.next();
            productAccount.setDate(newDate);
        } else if (command.equals("add")) {
            System.out.print("Enter an amount to add: ");
            int amountToAdd = input.nextInt();
            productAccount.addAmount(amountToAdd);
        } else if (command.equals("remove")) {
            System.out.print("Enter an amount to remove: ");
            int amountToRemove = input.nextInt();
            productAccount.removeAmount(amountToRemove);
        } else if (command.equals("back")) {
            chooseProductAccount(product);
        } else {
            System.out.println("Selection not valid...");
        }

        chooseProductAccount(product);
    }
}
