package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/doesNotExist.json");
        try {
            CategoryManager categoryManager = reader.readCM();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCM() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCM.json");
        try {
            CategoryManager categoryManager = reader.readCM();
            assertEquals("My category manager", categoryManager.getName());
            assertEquals(0, categoryManager.getCategories().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCM() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCM.json");
        try {
            CategoryManager categoryManager = reader.readCM();
            assertEquals("Main", categoryManager.getName());
            List<Category> categories = categoryManager.getCategories();
            assertEquals(2, categories.size());
            checkCategory("Food", 1, categories.get(0));
            checkCategory("Appliances", 0, categories.get(1));

            List<Product> productsInFood = categories.get(0).getProductList();
            checkProduct("Apple", 2, productsInFood.get(0));

            List<ProductAccount> productAccountsInApple= productsInFood.get(0).getProductAccounts();
            checkProductAccount(12, 213.1, "2002-12-12", productAccountsInApple.get(0));
            checkProductAccount(90, 218.21, "2002-08-01", productAccountsInApple.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}