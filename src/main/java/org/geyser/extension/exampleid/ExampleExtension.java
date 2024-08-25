package org.geyser.extension.exampleid;

import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.extension.Extension;
import org.geysermc.event.subscribe.Subscribe;
import org.geysemc.geyser.api.event.GeyserDefineCustomeSkullEvent

import java.util.logging.Logger;

public class ExampleExtension implements Extension {

    private static final Logger LOGGER = Logger.getLogger(ExampleExtension.class.getName());
    private Skull skullHandler;
    
    @Subscribe
    public void onInitialize() {
        // Initialisiere den Skull-Handler
        skullHandler = new Skull();
    }

    @Subscribe
    public void onPostInitialize(GeyserPostInitializeEvent event) {
        // Beispiel: Zeigt, dass deine Erweiterung geladen wird.
        LOGGER.info("Loading example extension...");

        // Beispiel: Zugriff auf das Datenverzeichnis der Erweiterung
        Path exampleDataFolder = this.dataFolder();
        LOGGER.info("Data folder: " + exampleDataFolder.toString());

        // Registriere die benutzerdefinierten Skulls
        // Hier stellen wir sicher, dass die Methode aufgerufen wird, um die Skulls zu registrieren
        skullHandler.onDefineCustomSkulls(new GeyserDefineCustomSkullsEvent() {
            // Implementiere die Methode, um die Event-Daten zu simulieren
        });
    }
}
