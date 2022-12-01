package me.piggypiglet.cornerstones.guice.objects;

import com.google.inject.Key;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public record Binding<T>(@NotNull Key<? super T> key, @NotNull T instance) {
}
