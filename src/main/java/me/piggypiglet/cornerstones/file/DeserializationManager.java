package me.piggypiglet.cornerstones.file;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.piggypiglet.cornerstones.file.annotations.FileData;
import me.piggypiglet.cornerstones.file.adapter.FileAdapter;
import me.piggypiglet.cornerstones.file.adapter.YamlFileAdapter;
import me.piggypiglet.cornerstones.file.exceptions.FileLoadException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class DeserializationManager {
    private static final FileAdapter ADAPTER = new YamlFileAdapter();

    private final Map<Class<?>, FileData> fileData;

    private final Gson gson;

    @Inject
    public DeserializationManager(@NotNull @Named("files") final Map<Class<?>, FileData> fileData, @NotNull @Named("files") final Gson gson) {
        this.fileData = fileData;
        this.gson = gson;
    }

    public void load(@NotNull final Class<?> clazz) {
        final FileData data = fileData.get(clazz);

        if (data == null) {
            return;
        }

        try {
            gson.fromJson(gson.toJson(ADAPTER.deserialize(FileUtils.read(prepareFile(data)))), clazz);
        } catch (IOException exception) {
            throw new FileLoadException(exception);
        }
    }

    @NotNull
    private File prepareFile(@NotNull final FileData fileData) throws IOException {
        return FileUtils.create(fileData.internalPath(), fileData.externalPath());
    }
}
