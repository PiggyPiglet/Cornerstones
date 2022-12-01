package me.piggypiglet.cornerstones.scanning.rules.element;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.AnnotatedElement;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public record ElementWrapper(@NotNull Class<?> type, @NotNull AnnotatedElement element) {
}
