package me.piggypiglet.cornerstones.file.boot;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import me.piggypiglet.cornerstones.boot.framework.Registerable;
import me.piggypiglet.cornerstones.file.DeserializationManager;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class InitialDeserializationRegisterable extends Registerable {
    private final Map<Class<?>, Object> files;
    private final DeserializationManager deserializationManager;

    @Inject
    public InitialDeserializationRegisterable(@NotNull @Named("files") final Map<Class<?>, Object> files, @NotNull final DeserializationManager deserializationManager) {
        this.files = files;
        this.deserializationManager = deserializationManager;
    }

    @Override
    public void execute() {
        files.keySet().forEach(deserializationManager::load);
    }
}
