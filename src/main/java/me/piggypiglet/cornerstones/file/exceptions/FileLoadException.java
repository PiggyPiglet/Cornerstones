package me.piggypiglet.cornerstones.file.exceptions;

import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class FileLoadException extends RuntimeException {
    public FileLoadException(@NotNull final Exception exception) {
        super(exception);
    }
}
