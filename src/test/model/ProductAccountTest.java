package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductAccountTest {
    private ProductAccount testProductAccount;

    @BeforeEach
    void runBefore() {
        testProductAccount = new ProductAccount(10, 8.99, "2021-08-02");
    }

    @Test
    void testConstructor() {
        assertEquals(10, testProductAccount.getAmount());
        assertEquals(8.99, testProductAccount.getCost());
        assertEquals("2021-08-02", testProductAccount.getDate());
    }

    @Test
    void testGetMonth() {
        assertEquals("2021-08", testProductAccount.getMonth());
    }

    @Test
    void testGetYear() {
        assertEquals("2021", testProductAccount.getYear());
    }

    @Test
    void testSetCost() {
        testProductAccount.setCost(0.12);
        assertEquals(0.12, testProductAccount.getCost());
        testProductAccount.setCost(0);
        assertEquals(0, testProductAccount.getCost());
    }

    @Test
    void testSetDate() {
        testProductAccount.setDate("2019-02-1");
        assertEquals("2019-02-1", testProductAccount.getDate());
    }

    @Test
    void testAddAmount() {
        testProductAccount.addAmount(2);
        assertEquals(12, testProductAccount.getAmount());
        testProductAccount.addAmount(0);
        assertEquals(12, testProductAccount.getAmount());
        testProductAccount.addAmount(5);
        assertEquals(17, testProductAccount.getAmount());
    }

    @Test
    void testRemoveAmount() {
        testProductAccount.removeAmount(2);
        assertEquals(8, testProductAccount.getAmount());
        testProductAccount.removeAmount(0);
        assertEquals(8, testProductAccount.getAmount());
        testProductAccount.removeAmount(8);
        assertEquals(0, testProductAccount.getAmount());
    }
}
