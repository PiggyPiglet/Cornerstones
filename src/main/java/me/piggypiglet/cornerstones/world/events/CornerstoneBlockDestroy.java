package me.piggypiglet.cornerstones.world.events;

import com.google.inject.Inject;
import me.piggypiglet.cornerstones.storage.CornerstoneStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class CornerstoneBlockDestroy implements Listener {
    private final CornerstoneStorage storage;

    @Inject
    public CornerstoneBlockDestroy(@NotNull final CornerstoneStorage storage) {
        this.storage = storage;
    }

    @EventHandler
    public void onCornerstoneBlockDestruction(@NotNull final BlockBreakEvent event) {

    }
}
