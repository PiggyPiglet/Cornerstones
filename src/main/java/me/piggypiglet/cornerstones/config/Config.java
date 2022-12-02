package me.piggypiglet.cornerstones.config;

import com.google.gson.annotations.JsonAdapter;
import com.google.inject.Singleton;
import me.piggypiglet.cornerstones.config.deserialization.MaterialDeserializer;
import me.piggypiglet.cornerstones.file.annotations.File;
import me.piggypiglet.cornerstones.config.deserialization.MaterialSetDeserializer;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
@Singleton @File
public final class Config {
    private double chance;
    @JsonAdapter(MaterialDeserializer.class) private Material cornerstoneMaterial;
    private List<String> worlds;
    @JsonAdapter(MaterialSetDeserializer.class) private Set<Material> surfaceMaterials;
    @JsonAdapter(MaterialSetDeserializer.class) private Set<Material> structureMaterials;

    public double chance() {
        return chance;
    }

    @NotNull
    public Material cornerstoneMaterial() {
        return cornerstoneMaterial;
    }

    @NotNull
    public List<String> worlds() {
        return worlds;
    }

    @NotNull
    public Set<Material> surfaceMaterials() {
        return surfaceMaterials;
    }

    @NotNull
    public Set<Material> structureMaterials() {
        return structureMaterials;
    }
}
