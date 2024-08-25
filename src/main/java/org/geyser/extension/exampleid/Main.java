package org.geyser.extension.exampleid;

import org.geysermc.geyser.api.event.Subscribe;
import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.extension.Extension;
import org.geysermc.geyser.api.event.EventBus;
import java.util.logging.Logger;

public class Main extends Extension {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    @Override
    public void onInitialize() {
        // Ereignis-Manager erhalten
        EventBus eventBus = this.getEventBus();

        // Ereignis registrieren
        if (eventBus != null) {
            eventBus.register(this, new Skull());
            LOGGER.info("Skull wurde erfolgreich registriert.");
        } else {
            LOGGER.warning("EventBus konnte nicht initialisiert werden.");
        }
    }

    @Subscribe
    public void onPostInitialize(GeyserPostInitializeEvent event) {
        LOGGER.info("Geyser Extension für benutzerdefinierte Skulls ist aktiv.");
    }
}
