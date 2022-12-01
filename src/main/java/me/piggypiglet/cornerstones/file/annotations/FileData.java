package me.piggypiglet.cornerstones.file.annotations;

import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public record FileData(@NotNull String internalPath, @NotNull String externalPath) {
}
