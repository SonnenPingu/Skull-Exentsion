package com.example.geyserextension;

import org.geysermc.geyser.api.extension.Extension;
import org.geysermc.geyser.api.event.Subscribe;
import org.geysermc.geyser.api.event.GeyserDefineCustomSkullsEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.event.block.SkullTextureType;
import org.geysermc.geyser.api.extension.GeyserExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends GeyserExtension {

    @Override
    public void onInitialize() {
        // Registriere die RegisterCustomSkull-Klasse
        this.getEventBus().register(new RegisterCustomSkull());
    }

    @Subscribe
    public void onPostInitialize(GeyserPostInitializeEvent event) {
        logger().info("Geyser Extension für benutzerdefinierte Skulls ist aktiv.");
    }
}

class RegisterCustomSkull implements Extension {

    private static final Logger LOGGER = Logger.getLogger(RegisterCustomSkull.class.getName());

    @Subscribe
    public void onDefineCustomSkulls(GeyserDefineCustomSkullsEvent event) {
        try {
            // Verbindung zur Datenbank herstellen und Texturen abrufen
            List<String> textureUrls = fetchSkullTextureUrlsFromDatabase();

            // Jede Textur URL registrieren
            for (String textureUrl : textureUrls) {
                event.register(textureUrl, SkullTextureType.PROFILE);
                LOGGER.info("Registered custom skull texture: " + textureUrl);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error registering custom skulls:", e);
        }
    }

    private List<String> fetchSkullTextureUrlsFromDatabase() throws SQLException {
        List<String> textureUrls = new ArrayList<>();
        String query = "SELECT texture_url FROM custom_skulls";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:extensions/skulldb/skulls.db");
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                textureUrls.add(rs.getString("texture_url"));
            }
        }
        return textureUrls;
    }
}
