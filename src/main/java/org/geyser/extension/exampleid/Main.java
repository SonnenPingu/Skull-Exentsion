package org.geyser.extension.exampleid;

import org.geysermc.geyser.api.event.Subscribe;
import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.extension.Extension;
import org.geysermc.geyser.api.event.manager.EventManager;
import java.util.logging.Logger;

public class Main extends Extension {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Override
    public void onInitialize() {
        // Ereignis-Manager erhalten
        EventManager eventManager = this.getEventManager();
        
        // Ereignis registrieren
        if (eventManager != null) {
            eventManager.register(this, new Skull());
            LOGGER.info("Skull wurde erfolgreich registriert.");
        } else {
            LOGGER.warning("EventManager konnte nicht initialisiert werden.");
        }
    }

    @Subscribe
    public void onPostInitialize(GeyserPostInitializeEvent event) {
        LOGGER.info("Geyser Extension für benutzerdefinierte Skulls ist aktiv.");
    }
}
