package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    private Category testCategory1;
    private Product bread;
    private ProductAccount breadAccount1;
    private ProductAccount breadAccount2;
    private ProductAccount breadAccount3;
    private Product apple;
    private ProductAccount appleAccount1;
    private ProductAccount appleAccount2;
    private ProductAccount appleAccount3;

    @BeforeEach
    void runBefore() {
        testCategory1 = new Category("Food");
        bread = new Product("Bread");
        breadAccount1 = new ProductAccount(3, 9.50, "2018-09-23");
        breadAccount2 = new ProductAccount(15, 45, "2018-09-26");
        breadAccount3 = new ProductAccount(10, 30, "2015-09-23");
        apple = new Product("Apple");
        appleAccount1 = new ProductAccount(1, 3.55, "2018-09-23");
        appleAccount2 = new ProductAccount(15, 45, "2015-10-23");
        appleAccount3 = new ProductAccount(10, 30, "2017-11-26");
    }

    @Test
    void testConstructor() {
        assertEquals("Food", testCategory1.getName());
        assertTrue(testCategory1.getId() > 0);
    }

    @Test
    void testAddProduct() {
        assertEquals(0, testCategory1.getProductList().size());
        testCategory1.addProduct(bread);
        testCategory1.addProduct(bread);
        assertEquals(1, testCategory1.getProductList().size());
        assertTrue(testCategory1.getProductList().contains(bread));
        testCategory1.addProduct(apple);
        assertEquals(2, testCategory1.getProductList().size());
        assertTrue(testCategory1.getProductList().contains(apple));
    }

    @Test
    void testRemoveProduct() {
        testCategory1.addProduct(bread);
        testCategory1.addProduct(apple);
        testCategory1.removeProduct(bread);
        assertEquals(1, testCategory1.getProductList().size());
        assertFalse(testCategory1.getProductList().contains(bread));
        assertTrue(testCategory1.getProductList().contains(apple));
        testCategory1.removeProduct(apple);
        testCategory1.removeProduct(apple);
        assertEquals(0, testCategory1.getProductList().size());
        assertFalse(testCategory1.getProductList().contains(bread));
        assertTrue(testCategory1.getProductList().contains(apple));
    }

    @Test
    void testGetDayCost() {
        testCategory1.addProduct(bread);
        testCategory1.addProduct(apple);
        assertEquals(0, testCategory1.getDayCost("2018-09-23"));
        bread.addProductAccount(breadAccount1);
        assertEquals(9.5, testCategory1.getDayCost("2018-09-23"));
        assertEquals(0, testCategory1.getDayCost("2018-06-23"));
        assertEquals(0, testCategory1.getDayCost("2015-09-23"));

        bread.addProductAccount(breadAccount2);
        bread.addProductAccount(breadAccount3);
        apple.addProductAccount(appleAccount1);
        apple.addProductAccount(appleAccount2);
        assertEquals(13.05, testCategory1.getDayCost("2018-09-23"));
        assertEquals(30, testCategory1.getDayCost("2015-09-23"));
        assertEquals(45, testCategory1.getDayCost("2015-10-23"));
        assertEquals(45, testCategory1.getDayCost("2018-09-26"));
    }

    @Test
    void testGetMonthCost() {
        testCategory1.addProduct(bread);
        testCategory1.addProduct(apple);
        assertEquals(0, testCategory1.getMonthCost("2018-09"));
        bread.addProductAccount(breadAccount1);
        assertEquals(9.5, testCategory1.getMonthCost("2018-09"));
        assertEquals(0, testCategory1.getMonthCost("2018-08"));
        assertEquals(9.5, testCategory1.getMonthCost("2017-09"));

        bread.addProductAccount(breadAccount2);
        bread.addProductAccount(breadAccount3);
        apple.addProductAccount(appleAccount1);
        apple.addProductAccount(appleAccount2);
        assertEquals(58.04, testCategory1.getMonthCost("2018-09"));
        assertEquals(30, testCategory1.getMonthCost("2015-09"));
        assertEquals(45, testCategory1.getMonthCost("2015-10"));
    }

    @Test
    void testGetYearCost() {
        testCategory1.addProduct(bread);
        testCategory1.addProduct(apple);
        assertEquals(0, testCategory1.getYearCost("2018"));
        bread.addProductAccount(breadAccount1);
        assertEquals(9.5, testCategory1.getYearCost("2018"));
        assertEquals(0, testCategory1.getYearCost("2015"));

        bread.addProductAccount(breadAccount2);
        bread.addProductAccount(breadAccount3);
        apple.addProductAccount(appleAccount1);
        apple.addProductAccount(appleAccount2);
        apple.addProductAccount(appleAccount3);
        assertEquals(58.04, testCategory1.getYearCost("2018"));
        assertEquals(75, testCategory1.getYearCost("2015"));
        assertEquals(30, testCategory1.getYearCost("2017"));
    }

    @Test
    void testGetTotalCost() {
        testCategory1.addProduct(bread);
        testCategory1.addProduct(apple);
        assertEquals(0, testCategory1.getTotalCost());
        bread.addProductAccount(breadAccount1);
        assertEquals(9.5, testCategory1.getTotalCost());

        bread.addProductAccount(breadAccount2);
        bread.addProductAccount(breadAccount3);
        apple.addProductAccount(appleAccount1);
        apple.addProductAccount(appleAccount2);
        apple.addProductAccount(appleAccount3);
        assertEquals(163.05, testCategory1.getTotalCost());
    }
}