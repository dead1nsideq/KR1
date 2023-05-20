package org.example.services;

import org.example.util.getValuesFromScanner;

public class Menu {
    public static void showMenu() {
        while (true) {
            System.out.println("""
                    Pick option
                    1.Search souvenirs
                    2.Add/Edit/Delete menu
                    3.Show all makers and their souvenirs
                    4.Exit
                    """);
            int option = getValuesFromScanner.getIntFromConsole();
            switch (option) {
                case 1 -> SouvenirSearch.searchSouvenirs();
                case 2 -> edit();
                case 3 -> SouvenirSearch.showAllMakersAndSouvenirs();
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid input");
            }
        }
    }
    private static void edit() {
        while (true) {
            System.out.println("""
                    Pick option
                    1.Add/Edit/Delete Souvenirs
                    2.Add/Edit/Delete Makers
                    3.Back
                    """);
            int option = getValuesFromScanner.getIntFromConsole();
            switch (option) {
                case 1 -> new SouvenirCRUD().invokeMenu();
                case 2 -> new MakerCRUD().invokeMenu();
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid input");
            }
        }
    }
}
