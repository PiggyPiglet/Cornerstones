package me.piggypiglet.cornerstones.boot.exceptions;

import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class BootstrapHaltException extends RuntimeException {
    public BootstrapHaltException(@NotNull final String message) {
        super(message);
    }

    public BootstrapHaltException(@NotNull final Throwable cause) {
        super(cause);
    }

    public BootstrapHaltException(@NotNull final String message, @NotNull final Throwable cause) {
        super(message, cause);
    }
}
