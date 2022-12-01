package me.piggypiglet.cornerstones.guice.exceptions;

import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class InjectionException extends RuntimeException {
    public InjectionException(@NotNull final Exception exception) {
        super(exception);
    }
}
