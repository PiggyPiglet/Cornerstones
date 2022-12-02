package me.piggypiglet.cornerstones.config.deserialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class MaterialDeserializer implements JsonDeserializer<Material> {
    @NotNull
    @Override
    public Material deserialize(@NotNull final JsonElement json, @NotNull final Type typeOfT,
                                @NotNull final JsonDeserializationContext context) throws JsonParseException {
        return Material.valueOf(json.getAsString().toUpperCase());
    }
}
