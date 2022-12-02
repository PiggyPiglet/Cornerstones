package me.piggypiglet.cornerstones.file;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.util.Types;
import me.piggypiglet.cornerstones.file.adapter.FileAdapter;
import me.piggypiglet.cornerstones.file.adapter.YamlFileAdapter;
import me.piggypiglet.cornerstones.file.annotations.FileData;
import me.piggypiglet.cornerstones.file.exceptions.FileSaveException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class SerializationManager {
    private static final Type MAP_TYPE = Types.mapOf(String.class, Object.class);
    private static final FileAdapter ADAPTER = new YamlFileAdapter();

    private final Map<Class<?>, Object> files;
    private final Map<Class<?>, FileData> fileData;
    private final Gson gson;

    @Inject
    public SerializationManager(@NotNull @Named("files") final Map<Class<?>, Object> files, @NotNull @Named("files") final Map<Class<?>, FileData> fileData,
                                @NotNull final Gson gson) {
        this.files = files;
        this.fileData = fileData;
        this.gson = gson;
    }

    public void save(@NotNull final Class<?> clazz) {
        if (!fileData.containsKey(clazz) || !files.containsKey(clazz)) {
            return;
        }

        final Object object = files.get(clazz);

        final String path = fileData.get(clazz).externalPath();
        final String content = ADAPTER.serialize(gson.fromJson(gson.toJsonTree(object), MAP_TYPE));

        try {
            FileUtils.write(path, content);
        } catch (IOException exception) {
            throw new FileSaveException(exception);
        }
    }
}
