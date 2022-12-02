package me.piggypiglet.cornerstones.storage.events;

import com.google.inject.Inject;
import me.piggypiglet.cornerstones.config.Config;
import me.piggypiglet.cornerstones.file.SerializationManager;
import me.piggypiglet.cornerstones.storage.CornerstoneStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class StorageSaveHandler implements Listener {
    private final Config config;
    private final SerializationManager serializationManager;

    @Inject
    public StorageSaveHandler(@NotNull final Config config, @NotNull final SerializationManager serializationManager) {
        this.config = config;
        this.serializationManager = serializationManager;
    }

    @EventHandler
    public void onWorldSave(@NotNull final WorldSaveEvent event) {
        if (!config.worlds().contains(event.getWorld().getName())) {
            return;
        }

        serializationManager.save(CornerstoneStorage.class);
    }
}
