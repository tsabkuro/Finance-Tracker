package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkCategory(String name, int size, Category category) {
        assertEquals(name, category.getName());
        assertEquals(size, category.getProductList().size());
    }

    protected void checkProduct(String name, int size, Product product) {
        assertEquals(name, product.getName());
        assertEquals(size, product.getProductAccounts().size());
    }

    protected void checkProductAccount(int amount, double cost, String date, ProductAccount productAccount) {
        assertEquals(amount, productAccount.getAmount());
        assertEquals(cost, productAccount.getCost());
        assertEquals(date, productAccount.getDate());
    }
}
