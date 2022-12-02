package me.piggypiglet.cornerstones.config.deserialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.inject.util.Types;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class MaterialSetDeserializer implements JsonDeserializer<Set<Material>> {
    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    public Set<Material> deserialize(@NotNull final JsonElement json, @NotNull final Type typeOfT,
                                     @NotNull final JsonDeserializationContext context) throws JsonParseException {
        return ((Set<String>) context.deserialize(json, Types.setOf(String.class))).stream()
                .map(String::toUpperCase)
                .map(Material::valueOf)
                .collect(Collectors.toSet());
    }
}
