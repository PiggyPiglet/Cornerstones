package me.piggypiglet.cornerstones;

import com.google.inject.Inject;
import me.piggypiglet.cornerstones.storage.CornerstoneStorage;
import me.piggypiglet.cornerstones.world.CornerstoneRegions;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class CornerstoneManager {
    private final CornerstoneStorage storage;
    private final CornerstoneRegions regions;


    @Inject
    public CornerstoneManager(@NotNull final CornerstoneStorage storage, @NotNull final CornerstoneRegions regions) {
        this.storage = storage;
        this.regions = regions;
    }

    public void register(@NotNull final UUID world, final int chunkX,
                         final int chunkZ) {
        storage.register(world, chunkX, chunkZ);
        regions.register(world, chunkX, chunkZ);
    }
}
