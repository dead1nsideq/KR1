package org.example;

import org.example.data.SouvenirsStorage;
import org.example.services.Menu;

public class App {

    static {
        SouvenirsStorage.readSouvenirsFromFile();
    }

    public static void main( String[] args ) {
        Menu.showMenu();
    }
}
