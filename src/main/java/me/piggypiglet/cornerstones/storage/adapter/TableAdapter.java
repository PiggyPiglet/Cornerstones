package me.piggypiglet.cornerstones.storage.adapter;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.*;
import com.google.inject.util.Types;
import me.piggypiglet.cornerstones.storage.objects.CornerstoneChunk;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class TableAdapter implements JsonSerializer<Table<UUID, CornerstoneChunk, UUID>>, JsonDeserializer<Table<UUID, CornerstoneChunk, UUID>> {
    private static final UUID DEFAULT = UUID.nameUUIDFromBytes("$".getBytes());

    @NotNull
    @Override
    public JsonElement serialize(@NotNull final Table<UUID, CornerstoneChunk, UUID> src, @NotNull final Type typeOfSrc,
                                 @NotNull final JsonSerializationContext context) {
        final Map<UUID, Map<CornerstoneChunk, UUID>> rows = src.rowMap();
        final Map<UUID, Map<String, String>> semiSerialized = new HashMap<>();

        rows.forEach((world, v) -> v.forEach((chunk, player) -> {
            semiSerialized.putIfAbsent(world, new HashMap<>());
            semiSerialized.get(world).put(chunk.x() + "_" + chunk.z(), player.equals(DEFAULT) ? "e" : player.toString());
        }));

        return context.serialize(semiSerialized);
    }

    @NotNull
    @Override
    public Table<UUID, CornerstoneChunk, UUID> deserialize(@NotNull final JsonElement json, @NotNull final Type typeOfT,
                                                @NotNull final JsonDeserializationContext context) throws JsonParseException {
        final Table<UUID, CornerstoneChunk, UUID> table = HashBasedTable.create();
        final Map<UUID, Map<String, String>> map = context.deserialize(json, Types.mapOf(UUID.class, Types.mapOf(String.class, String.class)));

        map.forEach((world, v) -> v.forEach((chunk, player) -> {
            final String[] chunkParts = chunk.split("_");
            table.put(world, new CornerstoneChunk(Integer.parseInt(chunkParts[0]), Integer.parseInt(chunkParts[1])), player.equals("e") ? DEFAULT : UUID.fromString(player));
        }));

        return table;
    }
}
