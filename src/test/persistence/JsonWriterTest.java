package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            CategoryManager categoryManager = new CategoryManager("My category manager");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCM() {
        try {
            CategoryManager categoryManager = new CategoryManager("Test Category Manager");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCM.json");
            writer.open();
            writer.write(categoryManager);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCM.json");
            categoryManager = reader.readCM();
            assertEquals("Test Category Manager", categoryManager.getName());
            assertEquals(0, categoryManager.getCategories().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCM() {
        try {
            CategoryManager categoryManager = new CategoryManager("Test Main Category");
            categoryManager.addCategory(new Category("Food"));
            categoryManager.addCategory(new Category("Appliances"));

            List<Category> categories = categoryManager.getCategories();
            categories.get(0).addProduct(new Product("Bread"));
            categories.get(0).addProduct(new Product("Apple"));
            categories.get(1).addProduct(new Product("Oven"));

            List<Product> productsInFood = categories.get(0).getProductList();
            List<Product> productsInAppliances = categories.get(1).getProductList();
            productsInFood.get(1).addProductAccount(
                    new ProductAccount(12, 4.22, "2002-08-01"));
            productsInAppliances.get(0).addProductAccount(
                    new ProductAccount(12, 123.22, "2015-12-12"));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCM.json");
            writer.open();
            writer.write(categoryManager);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCM.json");
            categoryManager = reader.readCM();
            assertEquals("Test Main Category", categoryManager.getName());
            assertEquals(2, categories.size());
            checkCategory("Food", 2, categories.get(0));
            checkCategory("Appliances", 1, categories.get(1));
            checkProduct("Bread", 0, productsInFood.get(0));
            checkProduct("Apple", 1, productsInFood.get(1));
            checkProduct("Oven", 1, productsInAppliances.get(0));
            List<ProductAccount> productAccountsInApple = productsInFood.get(1).getProductAccounts();
            List<ProductAccount> productAccountsInOven = productsInAppliances.get(0).getProductAccounts();
            checkProductAccount(12, 4.22, "2002-08-01", productAccountsInApple.get(0));
            checkProductAccount(12, 123.22, "2015-12-12", productAccountsInOven.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}