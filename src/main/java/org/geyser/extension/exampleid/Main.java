package org.geyser.extension.exampleid;

import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomSkullsEvent;
import org.geysermc.geyser.api.extension.Extension;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class Main implements Extension {

    private Connection connection;

    @Override
    public void onPostInitialize(GeyserPostInitializeEvent event) {
        // Verbindung zur SQLite-Datenbank herstellen
        connectToDatabase();

        // Event-Handler registrieren
        getEventManager().register(this);
    }

    @Subscribe
    public void onDefineCustomSkulls(GeyserDefineCustomSkullsEvent event) {
        // Daten aus der Datenbank abrufen und Skulls registrieren
        Map<String, String> skulls = fetchSkullsFromDatabase();
        
        for (Map.Entry<String, String> entry : skulls.entrySet()) {
            String hash = entry.getKey();
            String url = entry.getValue();
            
            // Skull registrieren
            event.register(hash, url);
            logger().info("Skull registriert: " + hash + " mit URL: " + url);
        }
    }

    private void connectToDatabase() {
        try {
            // SQLite-Datenbankverbindung herstellen
            String url = "jdbc:sqlite:path/to/your/database.db";
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            logger().severe("Datenbankverbindung fehlgeschlagen: " + e.getMessage());
        }
    }

    private Map<String, String> fetchSkullsFromDatabase() {
        Map<String, String> skulls = new HashMap<>();
        try {
            String query = "SELECT url FROM skulls_table";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                String url = resultSet.getString("url");
                String hash = extractHashFromUrl(url);
                skulls.put(hash, url);
            }
        } catch (Exception e) {
            logger().severe("Fehler beim Abrufen von Skulls: " + e.getMessage());
        }
        return skulls;
    }

    private String extractHashFromUrl(String url) {
        // Extrahiere den Hash-Wert aus der URL, der nach dem letzten '/' kommt
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }
}
