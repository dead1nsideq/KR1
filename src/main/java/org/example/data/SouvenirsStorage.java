package org.example.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.adapters.LocalDateAdapter;
import org.example.models.Maker;
import org.example.models.Souvenir;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class SouvenirsStorage {

    private static HashMap<Maker, ArrayList<Souvenir>> souvenirs = new HashMap<>();

    public static void writeSouvenirsToFile() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .enableComplexMapKeySerialization()
                    .create();
            String json = gson.toJson(souvenirs);
            FileWriter writer = new FileWriter("souvenirs.json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readSouvenirsFromFile() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .enableComplexMapKeySerialization()
                    .create();
            String jsonContent = new String(Files.readAllBytes(Paths.get("souvenirs.json")));
            Type type = new TypeToken<HashMap<Maker, ArrayList<Souvenir>>>(){}.getType();
            souvenirs = gson.fromJson(jsonContent, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Maker, ArrayList<Souvenir>> getSouvenirs() {
        return souvenirs;
    }
}
