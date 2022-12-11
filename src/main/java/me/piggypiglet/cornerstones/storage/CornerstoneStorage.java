package me.piggypiglet.cornerstones.storage;

import com.google.common.collect.Table;
import com.google.gson.annotations.JsonAdapter;
import com.google.inject.Singleton;
import me.piggypiglet.cornerstones.file.annotations.File;
import me.piggypiglet.cornerstones.storage.adapter.TableAdapter;
import me.piggypiglet.cornerstones.storage.objects.CornerstoneChunk;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
@Singleton @File(internalPath = "/data.yml")
public final class CornerstoneStorage {
    private static final UUID DEFAULT = UUID.nameUUIDFromBytes("$".getBytes());

    @JsonAdapter(TableAdapter.class) private Table<UUID, CornerstoneChunk, UUID> cornerstones;

    // also used to unclaim
    public void register(@NotNull final UUID world, final int chunkX,
                         final int chunkZ) {
        cornerstones.put(world, new CornerstoneChunk(chunkX, chunkZ), DEFAULT);
    }

    public void claim(@NotNull final UUID world, final int chunkX,
                      final int chunkZ, @NotNull final UUID player) {
        cornerstones.put(world, new CornerstoneChunk(chunkX, chunkZ), player);
    }

    public boolean cornerstoneAt(@NotNull final UUID world, final int chunkX,
                                 final int chunkZ) {
        return cornerstones.contains(world, new CornerstoneChunk(chunkX, chunkZ));
    }

    @NotNull
    public Optional<UUID> claimer(@NotNull final UUID world, final int chunkX,
                                  final int chunkZ) {
        return Optional.ofNullable(cornerstones.get(world, new CornerstoneChunk(chunkX, chunkZ)))
                .filter(uuid -> !uuid.equals(DEFAULT));
    }
}
