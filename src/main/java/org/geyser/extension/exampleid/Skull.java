package org.geyser.extension.exampleid;

import org.geysermc.geyser.api.event.Subscribe;
import org.geysermc.geyser.api.event.GeyserDefineCustomSkullsEvent;
import org.geysermc.geyser.api.event.block.SkullTextureType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterCustomSkull {

    private static final Logger LOGGER = Logger.getLogger(RegisterCustomSkull.class.getName());

    @Subscribe
    public void onDefineCustomSkulls(GeyserDefineCustomSkullsEvent event) {
        try {
            // Verbindung zur Datenbank herstellen und Texturen abrufen
            List<String> skinHashes = fetchSkullSkinHashesFromDatabase();

            // Jeden Skin-Hash registrieren
            for (String skinHash : skinHashes) {
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
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:extensions/skulldb/skulls.db");
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String textureUrl = rs.getString("texture_url");
                // Extrahiere den Teil hinter dem letzten '/'
                String skinHash = textureUrl.substring(textureUrl.lastIndexOf('/') + 1);
                skinHashes.add(skinHash);
            }
        }
        return skinHashes;
    }
}
