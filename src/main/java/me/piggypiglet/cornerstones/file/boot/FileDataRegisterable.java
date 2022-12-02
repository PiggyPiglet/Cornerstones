package me.piggypiglet.cornerstones.file.boot;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import me.piggypiglet.cornerstones.boot.framework.Registerable;
import me.piggypiglet.cornerstones.file.annotations.File;
import me.piggypiglet.cornerstones.file.annotations.FileData;
import me.piggypiglet.cornerstones.scanning.framework.Scanner;
import me.piggypiglet.cornerstones.scanning.rules.Rules;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class FileDataRegisterable extends Registerable {
    private static final Named FILES = Names.named("files");

    private final Scanner scanner;
    private final String dataDirectory;

    @Inject
    public FileDataRegisterable(@NotNull final Scanner scanner, @NotNull final JavaPlugin main) {
        this.scanner = scanner;
        this.dataDirectory = main.getDataFolder().getPath();
    }

    @Override
    public void execute(final @NotNull Injector injector) {
        final Map<Class<?>, Object> files = scanner.classes(Rules.builder()
                .hasAnnotation(File.class)
                .build()).collect(Collectors.toMap(clazz -> clazz, injector::getInstance));

        addBinding(new TypeLiteral<Map<Class<?>, Object>>(){}, FILES, files);

        final Map<Class<?>, FileData> fileData = new HashMap<>();

        for (final Class<?> clazz : files.keySet()) {
            final File annotation = clazz.getAnnotation(File.class);

            String internalPath = annotation.internalPath();

            if (internalPath.isEmpty()) {
                internalPath = '/' + clazz.getSimpleName().toLowerCase() + ".yml";
            }

            String externalPath = annotation.externalPath();

            if (externalPath.isEmpty()) {
                externalPath = internalPath;
            }

            externalPath = dataDirectory + (externalPath.startsWith("/") ? "" : '/') + externalPath;

            fileData.put(clazz, new FileData(internalPath, externalPath));
        }

        addBinding(new TypeLiteral<Map<Class<?>, FileData>>(){}, FILES, fileData);
    }
}
