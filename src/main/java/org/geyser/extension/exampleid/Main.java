package org.geyser.extension.exampleid;

import org.geysermc.geyser.api.event.Subscribe;
import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.extension.Extension;

public class Main extends Extension {

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
