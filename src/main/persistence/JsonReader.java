package persistence;

import model.CategoryManager;
import model.Category;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Product;
import model.ProductAccount;
import org.json.*;

// Represents a reader that reads CategoryManager from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads CategoryManager from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CategoryManager readCM() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCategoryManager(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses CategoryManager from JSON object and returns it
    private CategoryManager parseCategoryManager(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        CategoryManager cm = new CategoryManager(name);
        addCategories(cm, jsonObject);
        return cm;
    }

    // MODIFIES: cm
    // EFFECTS: parses categories from JSON object and adds them to CategoryManager
    private void addCategories(CategoryManager cm, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("categories");
        for (Object json : jsonArray) {
            JSONObject nextCategory = (JSONObject) json;
            addCategory(cm, nextCategory);
        }
    }

    // MODIFIES: cm
    // EFFECTS: parses category from JSON object and adds it to CategoryManager
    private void addCategory(CategoryManager cm, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Category category = new Category(name);
        addProducts(category, jsonObject);
        cm.addCategory(category);
    }

    // MODIFIES: category
    // EFFECTS: parses products from JSON object and adds them to category
    private void addProducts(Category category, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("products");
        for (Object json : jsonArray) {
            JSONObject nextProduct = (JSONObject) json;
            addProduct(category, nextProduct);
        }
    }

    // MODIFIES: category
    // EFFECTS: parses product from JSON object and adds it to category
    private void addProduct(Category category, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Product product = new Product(name);
        addProductAccounts(product, jsonObject);
        category.addProduct(product);
    }

    // MODIFIES: product
    // EFFECTS: parses product accounts from JSON object and adds them to product
    private void addProductAccounts(Product product, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("product accounts");
        for (Object json : jsonArray) {
            JSONObject nextProductAccount = (JSONObject) json;
            addProductAccount(product, nextProductAccount);
        }
    }

    // MODIFIES: product
    // EFFECTS: parses product account from JSON object and adds it to product
    private void addProductAccount(Product product, JSONObject jsonObject) {
        int amount = jsonObject.getInt("amount");
        double cost = jsonObject.getDouble("cost");
        String date = jsonObject.getString("date");
        ProductAccount productAccount = new ProductAccount(amount, cost, date);
        product.addProductAccount(productAccount);
    }
}