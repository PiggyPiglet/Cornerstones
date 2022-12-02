package me.piggypiglet.cornerstones.file;

import me.piggypiglet.cornerstones.boot.CornerstonesBootstrap;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.regex.Pattern;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class FileUtils {
    private static final Class<CornerstonesBootstrap> MAIN = CornerstonesBootstrap.class;
    private static final Pattern LINE_DELIMITER = Pattern.compile("\n");

    private FileUtils() {
        throw new AssertionError("This class cannot be instantiated.");
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @NotNull
    public static File create(@NotNull final String internalPath, @NotNull final String externalPath) throws IOException {
        final File file = new File(externalPath);

        if (file.exists()) return file;

        file.getParentFile().mkdirs();
        file.createNewFile();

        exportResource(internalPath, externalPath);
        return file;
    }

    public static void exportResource(@NotNull final String internalPath, @NotNull final String externalPath) throws IOException {
        Files.copy(MAIN.getResourceAsStream(internalPath), Paths.get(externalPath),
                StandardCopyOption.REPLACE_EXISTING);
    }

    @NotNull
    public static String read(@NotNull final File file) throws IOException {
        return String.join("\n", Files.readAllLines(Paths.get(file.toURI())));
    }

    public static void write(@NotNull final String path, @NotNull final String content) throws IOException {
        Files.write(Paths.get(path), Arrays.asList(LINE_DELIMITER.split(content)), StandardCharsets.UTF_8,
                StandardOpenOption.TRUNCATE_EXISTING);
    }
}
