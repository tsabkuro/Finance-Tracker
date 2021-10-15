package ui;

import java.util.Scanner;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
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
        } else if (command.equals("delete")) {
            deleteCategory();
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
        System.out.println("\tdelete -> delete a category");
        System.out.println("\tchoose -> choose a category");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays menu of options from category
    private void displayCategoryChoice() {
        System.out.println("\nSelect from:");
        System.out.println("\tstats -> look at category stats");
        System.out.println("\tcreate -> create product");
        System.out.println("\tdelete -> delete a product");
        System.out.println("\tchoose -> choose a product");
        System.out.println("\tback -> go back");
        System.out.println("\tmain -> return to main menu");
    }

    // EFFECTS: displays menu of options from product
    private void displayProductChoice() {
        System.out.println("\nSelect from:");
        System.out.println("\tstats -> look at product stats");
        System.out.println("\tcreate -> create product account");
        System.out.println("\tdelete -> delete a product account");
        System.out.println("\tchoose -> choose a product account to update");
        System.out.println("\tback -> go back");
        System.out.println("\tmain -> return to main menu");
    }

    // EFFECTS: displays menu of options from product account
    private String displayProductAccountChoice() {
        System.out.println("\nSelect from:");
        System.out.println("\tcost -> change cost of product account");
        System.out.println("\tdate -> change date of product account");
        System.out.println("\tadd -> add certain amount of product");
        System.out.println("\tremove -> remove certain amount of product");
        System.out.println("\tback -> go back");
        System.out.println("\tmain -> return to main menu");
        return input.next();
    }

    private void displayProductAccountUpdate(String command, Category category,
                                             Product product, ProductAccount productAccount) {
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
            chooseProductAccount(product, category);
        } else if (command.equals("main")) {
            displayCategoryChoice();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a category
    private void createCategory() {
        System.out.print("Enter a category name: ");

        String categoryName = input.next();

        categoryManager.addCategory(new Category(categoryName));
    }

    // EFFECTS: Lists all categories
    private void listCategories() {
        System.out.print("List of Categories: ");
        for (Category i: categoryManager.getCategories()) {
            System.out.println(i.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes a category
    private void deleteCategory() {
        listCategories();

        String choice = input.next();

        for (Category i: categoryManager.getCategories()) {
            if (i.getName().equals(choice)) {
                categoryManager.removeCategory(i);
                System.out.println("Category " + i.getName() + " deleted.");
                return;
            }
        }

        System.out.print("Category does not exist");
    }


    // EFFECTS: gives user list of category choices
    private void chooseCategory() {
        listCategories();

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

        if (command.equals("stats")) {
            categoryStats(category);
        } else if (command.equals("create")) {
            createProduct(category);
        } else if (command.equals("choose")) {
            chooseProduct(category);
        } else if (command.equals("delete")) {
            deleteProduct(category);
        } else if (command.equals("back")) {
            chooseCategory();
        } else if (command.equals("main")) {
            displayCategoryManagerChoice();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: gives user lists of category stat choices
    private String categoryStatChoices() {
        System.out.println("\nSelect from:");
        System.out.println("\tday -> get cost of products in category for a day");
        System.out.println("\tmonth -> get cost of products in category for a month");
        System.out.println("\tyear -> get cost of products in category for a year");
        System.out.println("\ttotal -> get total cost of products");
        System.out.println("\tback -> go back");
        System.out.println("\tmain -> return to main menu");
        return input.next();
    }

    // EFFECTS: show category stats
    private void categoryStats(Category category) {
        String command = categoryStatChoices();

        if (command.equals("day")) {
            System.out.println("Enter a date (yyyy-MM-dd): ");
            String day = input.next();
            System.out.println(category.getDayCost(day));
        } else if (command.equals("month")) {
            System.out.println("Enter a month (yyyy-MM): ");
            String month = input.next();
            System.out.println(category.getMonthCost(month));
        } else if (command.equals("year")) {
            System.out.println("Enter a year (yyyy): ");
            String year = input.next();
            System.out.println(category.getMonthCost(year));
        } else if (command.equals("total")) {
            category.getTotalCost();
        } else if (command.equals("back")) {
            chosenCategory(category);
        } else if (command.equals("main")) {
            displayCategoryManagerChoice();
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

    // EFFECTS: gives user list of products
    private void listProducts(Category category) {
        System.out.println("List of Products: ");
        for (Product i: category.getProductList()) {
            System.out.println(i.getName());
        }
    }

    // EFFECTS: gives user a list of product choices
    private void deleteProduct(Category category) {
        listProducts(category);

        String choice = input.next();

        for (Product i: category.getProductList()) {
            if (i.getName().equals(choice)) {
                category.removeProduct(i);
                System.out.println("Product " + i.getName() + " deleted.");
                chosenCategory(category);
                return;
            }
        }

        System.out.print("Product does not exist");
        chosenCategory(category);
    }

    // EFFECTS: gives user a list of product choices
    private void chooseProduct(Category category) {
        listProducts(category);

        String choice = input.next();

        for (Product i: category.getProductList()) {
            if (i.getName().equals(choice)) {
                chosenProduct(i, category);
                return;
            }
        }

        System.out.print("Product does not exist");
        chosenCategory(category);
    }

    // EFFECTS: gives user a list of choices for chosen product
    private void chosenProduct(Product product, Category category) {
        displayProductChoice();
        String command = input.next();

        if (command.equals("stats")) {
            productStats(product, category);
        } else if (command.equals("create")) {
            createProductAccount(product, category);
        } else if (command.equals("choose")) {
            chooseProductAccount(product, category);
        } else if (command.equals("delete")) {
            deleteProductAccount(product, category);
        } else if (command.equals("back")) {
            chooseProduct(category);
        } else if (command.equals("main")) {
            displayCategoryManagerChoice();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: gives user a list of product stat choices
    private String productStatChoices() {
        System.out.println("\nSelect from:");
        System.out.println("\tcost -> get list of cost stats");
        System.out.println("\tamount -> get list of amount stats");
        System.out.println("\tback -> go back");
        System.out.println("\tmain -> return to main menu");
        return input.next();
    }

    // EFFECTS: gives user lists of product cost stat choices
    private String productCostStatChoices() {
        System.out.println("\nSelect from:");
        System.out.println("\tday cost -> get cost of product for a day");
        System.out.println("\tmonth cost -> get cost of product for a month");
        System.out.println("\tyear cost -> get cost of product for a year");
        System.out.println("\ttotal cost -> get total cost of product");
        System.out.println("\tback -> go back");
        System.out.println("\tmain -> return to main menu");
        return input.next();
    }

    // EFFECTS: gives user lists of product amount stat choices
    private String productAmountStatChoices() {
        System.out.println("\nSelect from:");
        System.out.println("\tday amount -> get cost of product for a day");
        System.out.println("\tmonth amount -> get cost of product for a month");
        System.out.println("\tyear amount -> get cost of product for a year");
        System.out.println("\ttotal amount -> get total cost of product");
        System.out.println("\tback -> go back");
        System.out.println("\tmain -> return to main menu");
        return input.next();
    }

    // EFFECTS: show product stats
    private void productStats(Product product, Category category) {
        String command = productStatChoices();

        if (command.equals("cost")) {
            String choices = productCostStatChoices();
            productCostCommand(choices, product, category);
        } else if (command.equals("amount")) {
            String choices = productAmountStatChoices();
            productAmountCommand(choices, product, category);
        } else if (command.equals("back")) {
            chosenProduct(product, category);
        } else if (command.equals("main")) {
            displayCategoryManagerChoice();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: prints cost of product
    private void productCostCommand(String command, Product product, Category category) {
        if (command.equals("day cost")) {
            System.out.println("Enter a date (yyyy-MM-dd): ");
            String day = input.next();
            System.out.println(product.getDayCost(day));
        } else if (command.equals("month cost")) {
            System.out.println("Enter a month (yyyy-MM): ");
            String month = input.next();
            System.out.println(product.getMonthCost(month));
        } else if (command.equals("year cost")) {
            System.out.println("Enter a year (yyyy): ");
            String year = input.next();
            System.out.println(product.getMonthCost(year));
        } else if (command.equals("total cost")) {
            System.out.println(product.getTotalCost());
        }  else if (command.equals("back")) {
            chosenProduct(product, category);
        } else if (command.equals("main")) {
            displayCategoryManagerChoice();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: prints amount of product
    private void productAmountCommand(String command, Product product, Category category) {
        if (command.equals("day amount")) {
            System.out.println("Enter a date (yyyy-MM-dd): ");
            String day = input.next();
            System.out.println(product.getDayAmount(day));
        } else if (command.equals("month amount")) {
            System.out.println("Enter a month (yyyy-MM): ");
            String month = input.next();
            System.out.println(product.getMonthAmount(month));
        } else if (command.equals("year amount")) {
            System.out.println("Enter a year (yyyy): ");
            String year = input.next();
            System.out.println(product.getMonthAmount(year));
        } else if (command.equals("total amount")) {
            System.out.println(product.getTotalAmount());
        } else if (command.equals("back")) {
            chosenProduct(product, category);
        } else if (command.equals("main")) {
            displayCategoryManagerChoice();
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

    // EFFECTS: gives user list of product accounts
    private void listProductAccounts(Product product) {
        System.out.println("List of Products Accounts: ");
        for (ProductAccount i: product.getProductAccounts()) {
            System.out.println("Date: " + i.getDate() + "\tCost: " + i.getCost() + "\tAmount: " + i.getAmount());
        }
    }

    // EFFECTS: gives user a list of product accounts to choose from
    private void deleteProductAccount(Product product, Category category) {
        listProductAccounts(product);

        System.out.print("Enter date of Product Account to delete (yyyy-MM-dd): ");
        String choice = input.next();

        for (ProductAccount i: product.getProductAccounts()) {
            if (i.getDate().equals(choice)) {
                product.removeProductAccount(i);
                System.out.println("Product Account deleted.");
                chosenProduct(product, category);
                return;
            }
        }

        System.out.print("Product Account does not exist");
        chosenProduct(product, category);
    }

    // EFFECTS: gives user a list of product accounts to choose from
    private void chooseProductAccount(Product product, Category category) {
        listProductAccounts(product);

        System.out.print("Enter date of Product Account to update (yyyy-MM-dd): ");
        String choice = input.next();

        for (ProductAccount i: product.getProductAccounts()) {
            if (i.getDate().equals(choice)) {
                chosenProductAccount(i, product, category);
                return;
            }
        }

        System.out.print("Product Account does not exist");
        chosenProduct(product, category);
    }

    // EFFECTS: gives user a list of choices for chosen product account
    private void chosenProductAccount(ProductAccount productAccount, Product product, Category category) {
        String command = displayProductAccountChoice();

        displayProductAccountUpdate(command, category, product, productAccount);

        chosenProduct(product, category);
    }
}
