package org.example.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class getValuesFromScanner {
    public static int getIntFromConsole() {
        Scanner scanner = new Scanner(System.in);
        int x;
        while (true) {
            try {
                x = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please re-enter");
                scanner.next();
            }
        }
        return x;
    }
}

