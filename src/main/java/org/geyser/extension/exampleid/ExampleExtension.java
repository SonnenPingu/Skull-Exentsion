package org.geyser.extension.main;

import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.extension.Extension;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomSkullsEvent;

import java.io.File;
import java.util.logging.Logger;

public class ExampleExtension implements Extension {

    private static final Logger LOGGER = Logger.getLogger(ExampleExtension.class.getName());
    private Skull skullHandler;

    @Subscribe
    public void onPostInitialize(GeyserPostInitializeEvent event) {
        // Zeigt, dass die Erweiterung erfolgreich geladen wurde
        LOGGER.info("Example extension successfully loaded.");

        // Initialisiere den Skull-Handler
        skullHandler = new Skull();

        // Erstelle den eigenen Ordner, wenn er nicht existiert
        createExtensionDirectory();

        LOGGER.info("Skull handler initialized.");
    }

    @Subscribe
    public void onDefineCustomSkulls(GeyserDefineCustomSkullsEvent event) {
        // Delegiere das Event an den Skull-Handler
        LOGGER.info("Handling custom skulls.");
        if (skullHandler != null) {
            skullHandler.onDefineCustomSkulls(event);
        } else {
            LOGGER.warning("Skull handler is not initialized.");
        }
    }

    private void createExtensionDirectory() {
        File extensionDir = new File("extensions/Skull Expand");
        if (!extensionDir.exists()) {
            if (extensionDir.mkdirs()) {
                LOGGER.info("Created extension directory: " + extensionDir.getAbsolutePath());
            } else {
                LOGGER.severe("Failed to create extension directory: " + extensionDir.getAbsolutePath());
            }
        }
    }

}