package org.example.services;

import org.example.data.SouvenirsStorage;
import org.example.models.Maker;
import org.example.models.Souvenir;
import org.example.util.getValuesFromScanner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class SouvenirCRUD {

    public void invokeMenu() {
        while (true) {
            System.out.println("Please select an option:");
            System.out.println("""
                    1. Add souvenir
                    2. Edit souvenir
                    3. Delete souvenir
                    4. Show all makers and souvenirs
                    5. Go back
                    """);
            int option = getValuesFromScanner.getIntFromConsole();
            switch (option) {
                case 1 -> addSouvenir(getSouvenir());
                case 2 -> editSouvenir();
                case 3 -> deleteSouvenir();
                case 4 -> SouvenirSearch.showAllMakersAndSouvenirs();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid input");
            }
        }
    }

    private Souvenir getSouvenirByName(Maker maker) {
        Scanner scanner = new Scanner(System.in);
        Souvenir souvenir;

        do {
            System.out.println("Please enter the name of the souvenir:");
            String name = scanner.nextLine();

            souvenir = SouvenirsStorage.getSouvenirs()
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().equals(maker))
                    .flatMap(entry -> entry.getValue().stream())
                    .filter(s -> s.getName().equals(name))
                    .findFirst()
                    .orElse(null);

            if (souvenir == null) {
                System.out.println("Invalid souvenir name. Please try again.");
            }
        } while (souvenir == null);

        return souvenir;
    }

    public Souvenir getSouvenirThatProducedBySpecifyMaker(Maker maker) {
        Souvenir souvenir = getSouvenirByName(maker);
        boolean exit = false;

        while (!souvenir.getMaker().equals(maker) && !exit) {
            System.out.println("Please enter the correct souvenir's info:");
            System.out.println("""
                            1. Show all souvenirs
                            2. Enter the souvenir again
                            3. Go back""");
            int option = getValuesFromScanner.getIntFromConsole();

            switch (option) {
                case 1 -> SouvenirsStorage.getSouvenirs().values().stream().flatMap(Collection::stream).forEach(Souvenir::showInfo);
                case 2 -> souvenir = getSouvenirByName(maker);
                case 3 -> exit = true;
                default -> System.out.println("Invalid input");
            }
        }

        return souvenir;
    }


    private Souvenir findSouvenirByName(String name) {
        return SouvenirsStorage.getSouvenirs().values().stream()
                .flatMap(List::stream)
                .filter(souvenir -> souvenir.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
    private Souvenir getSouvenir() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name for the souvenir:");
        String name = scanner.nextLine();
        LocalDate date = validateDateInput();
        System.out.println("Enter the price for the souvenir:");
        int price = getValuesFromScanner.getIntFromConsole();
        System.out.println("Enter the maker for the souvenir");
        Maker maker = MakerCRUD.getMaker();
        return new Souvenir(name,date,price,maker);
    }
    private void editSouvenir() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Firstly please enter maker that produce souvenir that you want to edit");
        Maker maker = MakerCRUD.getMakerThatInList();
        Souvenir souvenir = getSouvenirThatProducedBySpecifyMaker(maker);
        while (true) {
            System.out.println("Please select the parameter you want to edit:");
            System.out.println("""
                1. Souvenir's name
                2. Souvenir's date of creation
                3. Souvenir's price
                4. Souvenir's maker
                5. Go back
                """);
            int option = getValuesFromScanner.getIntFromConsole();
            switch (option) {
                case 1 -> {
                    System.out.println("Enter the new name for the souvenir:");
                    String newName = scanner.nextLine();
                    updateSouvenir(souvenir,newName, souvenir.getDateOfCreation(), souvenir.getPrice(), souvenir.getMaker());
                }
                case 2 -> {
                    System.out.println("Enter the new date (yyyy-MM-dd) for the souvenir:");
                    LocalDate newDate = validateDateInput();
                    updateSouvenir(souvenir,souvenir.getName(), newDate, souvenir.getPrice(), souvenir.getMaker());
                }
                case 3 -> {
                    System.out.println("Enter the new price for the souvenir:");
                    int newPrice = getValuesFromScanner.getIntFromConsole();
                    updateSouvenir(souvenir,souvenir.getName(), souvenir.getDateOfCreation(), newPrice, souvenir.getMaker());
                }
                case 4 -> {
                    System.out.println("Enter the new maker for the souvenir:");
                    Maker newMaker = MakerCRUD.getMaker();
                    updateSouvenir(souvenir,souvenir.getName(), souvenir.getDateOfCreation(), souvenir.getPrice(), newMaker);
                }
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid input");
            }
        }
    }

    private void updateSouvenir(Souvenir souvenir,String newName, LocalDate newDateOfCreation, int newPrice, Maker newMaker) {
        List<Souvenir> makerSouvenirs = SouvenirsStorage.getSouvenirs().get(souvenir.getMaker());
        if (makerSouvenirs != null) {
            makerSouvenirs.remove(souvenir);
            souvenir.setName(newName);
            souvenir.setMaker(newMaker);
            souvenir.setDateOfCreation(newDateOfCreation);
            souvenir.setPrice(newPrice);
            addSouvenir(souvenir);
            SouvenirsStorage.writeSouvenirsToFile();
        } else {
            System.out.println("No souvenirs found for the given maker.");
        }
    }

    private void deleteSouvenir() {
        System.out.println("Firstly please enter maker that produce souvenir that you want to delete");
        Maker maker = MakerCRUD.getMakerThatInList();
        Souvenir souvenir = getSouvenirThatProducedBySpecifyMaker(maker);
        List<Souvenir> makerSouvenirs = SouvenirsStorage.getSouvenirs().get(souvenir.getMaker());
        makerSouvenirs.remove(souvenir);
        System.out.println("Souvenir has removed successfully");
        SouvenirsStorage.writeSouvenirsToFile();
    }

    private void addSouvenir(Souvenir souvenir) {
        Souvenir souvenir1 = findSouvenirByName(souvenir.getName());
        if (souvenir1 != null && souvenir1.getMaker().equals(souvenir.getMaker())) {
            System.out.println("This maker already has this souvenir");
        } else {
            SouvenirsStorage.getSouvenirs().computeIfAbsent(souvenir.getMaker(), list -> new ArrayList<>()).add(souvenir);
            SouvenirsStorage.writeSouvenirsToFile();
            System.out.println("Souvenir has added successfully");
        }
    }
    public static LocalDate validateDateInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a date (yyyy-MM-dd):");
            String dateString = scanner.nextLine();
            try {
                LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate currentDate = LocalDate.now();
                LocalDate minDate = LocalDate.of(1960, 1, 1);

                if (date.isAfter(currentDate) || date.isBefore(minDate)) {
                    throw new IllegalArgumentException("Invalid date. Please enter a date between 1960 and the current date.");
                }

                return date;
            } catch (Exception e) {
                System.out.println("Invalid date format or date value. Please enter a valid date in the format yyyy-MM-dd.");
            }
        }
    }
}

