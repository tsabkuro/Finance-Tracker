package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product testProductBread;
    private ProductAccount breadAccount1;
    private ProductAccount breadAccount2;
    private ProductAccount breadAccount3;
    private ProductAccount breadAccount4;
    private ProductAccount breadAccount5;

    @BeforeEach
    void runBefore() {
        testProductBread = new Product("Bread");
        breadAccount1 = new ProductAccount(3, 9.50, "2018-09-26");
        breadAccount2 = new ProductAccount(15, 45, "2018-09-26");
        breadAccount3 = new ProductAccount(10, 30, "2018-10-26");
        breadAccount4 = new ProductAccount(2, 7.44, "2015-10-02");
        breadAccount5 = new ProductAccount(1, 2.34, "2018-09-12");
    }

    @Test
    void testConstructor() {
        assertEquals("Bread", testProductBread.getName());
    }

    @Test
    void testAddProductAccount() {
        assertEquals(0, testProductBread.getProductAccounts().size());
        testProductBread.addProductAccount(breadAccount1);
        testProductBread.addProductAccount(breadAccount2);
        assertEquals(2, testProductBread.getProductAccounts().size());
        assertTrue(testProductBread.getProductAccounts().contains(breadAccount1));
        assertTrue(testProductBread.getProductAccounts().contains(breadAccount2));
        assertFalse(testProductBread.getProductAccounts().contains(breadAccount3));
    }

    @Test
    void testRemoveProductAccount() {
        testProductBread.addProductAccount(breadAccount1);
        testProductBread.addProductAccount(breadAccount2);
        testProductBread.removeProductAccount(breadAccount1);
        assertEquals(1, testProductBread.getProductAccounts().size());
        assertFalse(testProductBread.getProductAccounts().contains(breadAccount1));
        assertTrue(testProductBread.getProductAccounts().contains(breadAccount2));
        testProductBread.removeProductAccount(breadAccount2);
        assertEquals(0, testProductBread.getProductAccounts().size());
        assertFalse(testProductBread.getProductAccounts().contains(breadAccount2));
        testProductBread.removeProductAccount(breadAccount3);
        assertEquals(0, testProductBread.getProductAccounts().size());
        assertFalse(testProductBread.getProductAccounts().contains(breadAccount3));
    }

    @Test
    void testGetDayAmount() {
        testProductBread.addProductAccount(breadAccount1);
        testProductBread.addProductAccount(breadAccount4);
        assertEquals(3, testProductBread.getDayAmount("2018-09-26"));
        assertEquals(0, testProductBread.getDayAmount("2018-10-26"));
        testProductBread.addProductAccount(breadAccount2);
        testProductBread.addProductAccount(breadAccount3);
        testProductBread.addProductAccount(breadAccount5);
        assertEquals(18, testProductBread.getDayAmount("2018-09-26"));
        assertEquals(1, testProductBread.getDayAmount("2018-09-12"));
        assertEquals(10, testProductBread.getDayAmount("2018-10-26"));
        assertEquals(2, testProductBread.getDayAmount("2015-10-02"));
    }

    @Test
    void testGetMonthAmount() {
        testProductBread.addProductAccount(breadAccount1);
        testProductBread.addProductAccount(breadAccount4);
        assertEquals(3, testProductBread.getMonthAmount("2018-09"));
        assertEquals(0, testProductBread.getMonthAmount("2018-10"));
        testProductBread.addProductAccount(breadAccount2);
        testProductBread.addProductAccount(breadAccount3);
        assertEquals(18, testProductBread.getMonthAmount("2018-09"));
        assertEquals(10, testProductBread.getMonthAmount("2018-10"));
        assertEquals(2, testProductBread.getMonthAmount("2015-10"));
    }

    @Test
    void testGetYearAmount() {
        assertEquals(0, testProductBread.getYearAmount("2018"));
        testProductBread.addProductAccount(breadAccount1);
        testProductBread.addProductAccount(breadAccount4);
        assertEquals(3, testProductBread.getYearAmount("2018"));
        assertEquals(2, testProductBread.getYearAmount("2015"));
        testProductBread.addProductAccount(breadAccount2);
        testProductBread.addProductAccount(breadAccount3);
        assertEquals(28, testProductBread.getYearAmount("2018"));
        assertEquals(2, testProductBread.getYearAmount("2015"));
    }

    @Test
    void testGetTotalAmount() {
        assertEquals(0, testProductBread.getTotalAmount());
        testProductBread.addProductAccount(breadAccount1);
        testProductBread.addProductAccount(breadAccount4);
        assertEquals(5, testProductBread.getTotalAmount());
        testProductBread.addProductAccount(breadAccount2);
        testProductBread.addProductAccount(breadAccount3);
        assertEquals(30, testProductBread.getTotalAmount());
    }

    @Test
    void testGetDayCost() {
        testProductBread.addProductAccount(breadAccount1);
        testProductBread.addProductAccount(breadAccount4);
        assertEquals(9.5, testProductBread.getDayCost("2018-09-26"));
        assertEquals(0, testProductBread.getDayCost("2018-10-26"));
        testProductBread.addProductAccount(breadAccount2);
        testProductBread.addProductAccount(breadAccount3);
        testProductBread.addProductAccount(breadAccount5);
        assertEquals(54.5, testProductBread.getDayCost("2018-09-26"));
        assertEquals(2.34, testProductBread.getDayCost("2018-09-12"));
        assertEquals(30, testProductBread.getDayCost("2018-10-26"));
        assertEquals(7.44, testProductBread.getDayCost("2015-10-02"));
    }

    @Test
    void testGetMonthCost() {
        testProductBread.addProductAccount(breadAccount1);
        testProductBread.addProductAccount(breadAccount4);
        testProductBread.getMonthCost("2018-09");
        assertEquals(9.50, testProductBread.getMonthCost("2018-09"));
        assertEquals(0, testProductBread.getMonthCost("2018-10"));
        testProductBread.addProductAccount(breadAccount2);
        testProductBread.addProductAccount(breadAccount3);
        assertEquals(54.5, testProductBread.getMonthCost("2018-09"));
        assertEquals(30, testProductBread.getMonthCost("2018-10"));
        assertEquals(7.44, testProductBread.getMonthCost("2015-10"));
    }

    @Test
    void testGetYearCost() {
        assertEquals(0, testProductBread.getYearCost("2018"));
        testProductBread.addProductAccount(breadAccount1);
        testProductBread.addProductAccount(breadAccount4);
        assertEquals(9.5, testProductBread.getYearCost("2018"));
        assertEquals(7.44, testProductBread.getYearCost("2015"));
        testProductBread.addProductAccount(breadAccount2);
        testProductBread.addProductAccount(breadAccount3);
        assertEquals(84.5, testProductBread.getYearCost("2018"));
        assertEquals(7.44, testProductBread.getYearCost("2015"));
    }

    @Test
    void testGetTotalCost() {
        assertEquals(0, testProductBread.getTotalCost());
        testProductBread.addProductAccount(breadAccount1);
        testProductBread.addProductAccount(breadAccount4);
        assertEquals(16.94, testProductBread.getTotalCost());
        testProductBread.addProductAccount(breadAccount2);
        testProductBread.addProductAccount(breadAccount3);
        assertEquals(91.94, testProductBread.getTotalCost());
    }
}
