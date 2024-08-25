package org.geyser.extension.exampleid;

import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomSkullsEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomSkullsEvent.SkullTextureType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Skull {

    private static final Logger LOGGER = Logger.getLogger(Skull.class.getName());
    private static final String DATABASE_URL = "jdbc:sqlite:/home/container/extensions/Skull-Expand/skulls.db"; // Datenbankpfad als Konstante


    // Statische Initialisierung, um den SQLite-Treiber zu laden
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onDefineCustomSkulls(GeyserDefineCustomSkullsEvent event) {
        try {
            List<String> skinHashes = fetchSkullSkinHashesFromDatabase();
            for (String skinHash : skinHashes) {
                // Registriere den Skin-Hash für benutzerdefinierte Schädelformen
                event.register(skinHash, SkullTextureType.SKIN_HASH);
                LOGGER.info("Registered custom skull skin hash: " + skinHash);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error registering custom skulls:", e);
        }
    }

    private List<String> fetchSkullSkinHashesFromDatabase() throws SQLException {
        List<String> skinHashes = new ArrayList<>();
        String query = "SELECT texture_url FROM custom_skulls";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String textureUrl = rs.getString("texture_url");
                if (textureUrl != null && !textureUrl.isEmpty()) {
                    // Extrahiere den Hash aus der URL (hinter dem letzten '/')
                    String skinHash = textureUrl.substring(textureUrl.lastIndexOf('/') + 1);
                    skinHashes.add(skinHash);
                } else {
                    LOGGER.warning("Encountered null or empty texture_url in database.");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching skull skin hashes from database:", e);
            throw e; // Weiterleiten der Ausnahme nach dem Protokollieren
        }

        return skinHashes;
    }
}
