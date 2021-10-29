package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryManagerTest {
    private CategoryManager categoryManager;
    private Category testCategory1;
    private Category testCategory2;

    @BeforeEach
    void runBefore() {
        categoryManager = new CategoryManager("Test");
        testCategory1 = new Category("Food");
        testCategory2 = new Category("Appliances");
    }

    @Test
    void testAddCategory() {
        assertEquals(0, categoryManager.getCategories().size());
        categoryManager.addCategory(testCategory1);
        categoryManager.addCategory(testCategory1);
        assertEquals(1, categoryManager.getCategories().size());
        assertTrue(categoryManager.getCategories().contains(testCategory1));
    }

    @Test
    void testRemoveCategory() {
        categoryManager.addCategory(testCategory1);
        categoryManager.addCategory(testCategory2);
        categoryManager.removeCategory(testCategory1);
        categoryManager.removeCategory(testCategory1);
        assertEquals(1, categoryManager.getCategories().size());
        assertTrue(categoryManager.getCategories().contains(testCategory2));
        categoryManager.removeCategory(testCategory2);
        assertFalse(categoryManager.getCategories().contains(testCategory2));
        assertEquals(0, categoryManager.getCategories().size());
    }
}