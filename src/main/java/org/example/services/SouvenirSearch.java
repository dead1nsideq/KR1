package org.example.services;

import org.example.data.SouvenirsStorage;
import org.example.models.Maker;
import org.example.models.Souvenir;
import org.example.util.getValuesFromScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SouvenirSearch {
    public static void searchSouvenirs() {
        while (true) {
            System.out.println("""
                    Pick option
                    1.Show all makers and their souvenirs
                    2.Show all souvenirs that make certain maker
                    3.Show all makers that souvenirs price lower than input price
                    4.Show all souvenirs that produced in certain country
                    5.Show all souvenirs that made in certain year
                    6.For each souvenir show their year
                    7.Back
                    """);
            int option = getValuesFromScanner.getIntFromConsole();
            Scanner scanner = new Scanner(System.in);
            switch (option) {
                case 1 -> showAllMakersAndSouvenirs();
                case 2 -> {
                    Maker maker = MakerCRUD.getMakerThatInList();
                    showAllSouvenirsThatCertainMakerProduce(maker);
                }
                case 3 -> {
                    System.out.println("Please enter price:");
                    int price = getValuesFromScanner.getIntFromConsole();
                    showAllMakersWithLowerPriceProduct(price);
                }
                case 4 -> {
                    System.out.println("Please enter county:");
                    String country = scanner.nextLine();
                    showAllMakersThatProducedInCertainCountry(country);
                }
                case 5 -> {
                    System.out.println("Please enter year:");
                    int year = getValuesFromScanner.getIntFromConsole();
                    showAllSouvenirsThatMadeInCurrentYear(year);
                }
                case 6 -> showSouvenirsAntTheirYearMade();
                case 7 -> {
                    return;
                }
                default -> System.out.println("Invalid input");
            }
        }
    }

    private static void showSouvenirsAntTheirYearMade() {
        Map<Integer, List<Souvenir>> yearAndSouvenirs = SouvenirsStorage.getSouvenirs().entrySet().stream()
                .flatMap(x->x.getValue().stream())
                .collect(Collectors.groupingBy(n->n.getDateOfCreation().getYear()));
        if (!yearAndSouvenirs.isEmpty()) {
            for (Map.Entry<Integer, List<Souvenir>> entry : yearAndSouvenirs.entrySet()) {
                Integer integer = entry.getKey();
                List<Souvenir> souvenirs = entry.getValue();
                System.out.println("Year - " + integer);
                for (Souvenir souvenir : souvenirs) {
                    souvenir.showInfo();
                }
                System.out.println();
            }
        } else {
            System.out.println("No result about your query");
        }
    }

    private static void showAllSouvenirsThatMadeInCurrentYear(int year) {
        var result = SouvenirsStorage.getSouvenirs().entrySet().stream()
                .flatMap(x->x.getValue().stream())
                .filter(y->y.getDateOfCreation().getYear() == year).toList();
        if (result.isEmpty()) {
            System.out.println("No result about your query");
        } else {
            result.forEach(Souvenir::showInfo);
        }
    }

    private static void showAllMakersThatProducedInCertainCountry(String country) {
        var result = SouvenirsStorage.getSouvenirs().entrySet()
                .stream()
                .filter(x->x.getKey().getCountry().equals(country))
                .flatMap(x->x.getValue().stream()).toList();
        if (result.isEmpty()) {
            System.out.println("No result about your query");
        } else {
            result.forEach(Souvenir::showInfo);
        }
    }

    private static void showAllMakersWithLowerPriceProduct(int price) {
        var result = SouvenirsStorage.getSouvenirs().entrySet()
                .stream()
                .flatMap(x -> x.getValue().stream())
                .filter(y->y.getPrice() < price)
                .toList();
        if (result.isEmpty()) {
            System.out.println("No result about your query");
        } else {
            result.forEach(Souvenir::showInfo);
        }
    }

    private static void showAllSouvenirsThatCertainMakerProduce(Maker maker) {
        var result = SouvenirsStorage.getSouvenirs().get(maker).stream().toList();
        if (result.isEmpty()) {
            System.out.println("No result about your query");
        } else {
            result.forEach(Souvenir::showInfo);
        }
    }

    public static void showAllMakersAndSouvenirs() {
        for (Map.Entry<Maker, ArrayList<Souvenir>> entry : SouvenirsStorage.getSouvenirs().entrySet()) {
            Maker maker = entry.getKey();
            List<Souvenir> souvenirs = entry.getValue();
            maker.showInfo();
            for (Souvenir souvenir : souvenirs) {
                souvenir.showInfo();
            }
            System.out.println();
        }
    }
}
