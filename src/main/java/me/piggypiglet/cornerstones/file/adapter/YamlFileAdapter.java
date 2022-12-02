package me.piggypiglet.cornerstones.file.adapter;

import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class YamlFileAdapter implements FileAdapter {
    private static final Yaml YAML = new Yaml();

    @NotNull
    @Override
    public String serialize(final @NotNull Map<String, Object> data) {
        return YAML.dump(data);
    }

    @NotNull
    @Override
    public Map<String, Object> deserialize(final @NotNull String content) {
        return YAML.load(content);
    }
}
