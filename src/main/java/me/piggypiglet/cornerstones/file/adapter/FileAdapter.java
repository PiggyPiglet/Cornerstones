package me.piggypiglet.cornerstones.file.adapter;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public interface FileAdapter {
    @NotNull
    String serialize(@NotNull final Map<String, Object> data);

    @NotNull
    Map<String, Object> deserialize(@NotNull final String content);
}
