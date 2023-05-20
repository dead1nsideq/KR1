package org.example.services;

import org.example.data.SouvenirsStorage;
import org.example.models.Maker;
import org.example.models.Souvenir;
import org.example.util.getValuesFromScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MakerCRUD {
    public void invokeMenu() {
        while (true) {
            System.out.println("Please select an option:");
            System.out.println("""
                    1. Add maker
                    2. Edit maker
                    3. Delete maker
                    4. Show all makers
                    5. Go back
                    """);
            int option = getValuesFromScanner.getIntFromConsole();
            switch (option) {
                case 1 -> addMaker();
                case 2 -> editMaker();
                case 3 -> deleteMaker();
                case 4 -> SouvenirsStorage.getSouvenirs().keySet().forEach(Maker::showInfo);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid input");
            }
        }
    }

    private void deleteMaker() {
        System.out.println("Please enter the maker's name and country to delete:");
        Maker maker = getMaker();
        if (isMakerInList(maker)) {
            SouvenirsStorage.getSouvenirs().remove(maker);
            System.out.println("Delete operation succeed");
            SouvenirsStorage.writeSouvenirsToFile();
        } else {
            System.out.println("No maker like this in the list, delete operation failed");
        }
    }

    private void addMaker() {
        Maker maker = getMaker();
        if (isMakerInList(maker)) {
            System.out.println("This maker is already in list");
        } else {
            SouvenirsStorage.getSouvenirs().put(maker,new ArrayList<>());
            SouvenirsStorage.writeSouvenirsToFile();
        }
    }

    private void editMaker() {
        System.out.println("Please enter the maker's name and country to edit:");
        Maker maker = getMakerThatInList();
        if (maker != null) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Please select the parameter you want to edit:");
                System.out.println("""
                        1. Maker's name
                        2. Maker's country
                        3. Go back
                        """);
                int option = getValuesFromScanner.getIntFromConsole();
                switch (option) {
                    case 1 -> {
                        System.out.println("Enter the new name for the maker:");
                        String newName = scanner.nextLine();
                        updateMakerAndSouvenirs(maker, newName, maker.getCountry());
                    }
                    case 2 -> {
                        System.out.println("Enter the new country for the maker:");
                        String newCountry = scanner.nextLine();
                        updateMakerAndSouvenirs(maker, maker.getName(), newCountry);
                    }
                    case 3 -> {
                        return;
                    }
                    default -> System.out.println("Invalid input");
                }
            }
        }
    }

    private void updateMakerAndSouvenirs(Maker maker, String newName, String newCountry) {
        Maker editedMaker = new Maker(newName, newCountry);
        List<Souvenir> souvenirList = SouvenirsStorage.getSouvenirs().get(maker);
        for (Souvenir souvenir : souvenirList) {
            souvenir.setMaker(editedMaker);
        }
        SouvenirsStorage.getSouvenirs().remove(maker);
        SouvenirsStorage.getSouvenirs().computeIfAbsent(editedMaker, list -> new ArrayList<>()).addAll(souvenirList);
        SouvenirsStorage.writeSouvenirsToFile();
    }

    public static Maker getMaker() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the maker's name:");
        String name = scanner.nextLine();
        System.out.println("Please enter the maker's country:");
        String country = scanner.nextLine();
        return new Maker(name, country);
    }

    public static Maker getMakerThatInList() {
        Maker maker = getMaker();
        boolean exit = false;
        while (!isMakerInList(maker)) {
            if (exit) {
                break;
            }
            System.out.println("Please enter a correct maker's name and country:");
            System.out.println("""
                                1. Show all makers
                                2. Enter maker again
                                3. Go back""");
            int option = getValuesFromScanner.getIntFromConsole();
            switch (option) {
                case 1 -> SouvenirsStorage.getSouvenirs().keySet().forEach(Maker::showInfo); // show all makers;
                case 2 -> maker = getMaker();
                case 3 -> exit = true;
            }
        }
        return maker;
    }

    private static boolean isMakerInList(Maker maker) {
        return SouvenirsStorage.getSouvenirs().containsKey(maker);
    }
}

