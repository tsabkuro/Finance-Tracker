package ui;

import model.CategoryManager;

public class Main {
    public static void main(String[] args) {
        new GUI(new CategoryManager("Main"));
    }
}
