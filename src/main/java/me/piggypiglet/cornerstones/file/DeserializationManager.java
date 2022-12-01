package me.piggypiglet.cornerstones.file;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.piggypiglet.cornerstones.file.annotations.FileData;
import me.piggypiglet.cornerstones.file.deserialization.FileDeserializer;
import me.piggypiglet.cornerstones.file.deserialization.YamlFileDeserializer;
import me.piggypiglet.cornerstones.file.exceptions.FileLoadException;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class DeserializationManager {
    private static final FileDeserializer DESERIALIZER = new YamlFileDeserializer();

    private final Map<Class<?>, FileData> fileData;

    private final Gson gson;

    @Inject
    public DeserializationManager(@NotNull @Named("files") final Map<Class<?>, FileData> fileData, @NotNull@Named("files") final Map<Class<?>, Object> files) {
        this.fileData = fileData;

        final AtomicReference<GsonBuilder> builder = new AtomicReference<>(new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES));
        files.forEach((clazz, instance) -> builder.set(builder.get()
                .registerTypeAdapter(clazz, instanceCreator(instance))));
        this.gson = builder.get().create();
    }

    private static <T> InstanceCreator<T> instanceCreator(@NotNull final T instance) {
        return type -> instance;
    }

    public void load(@NotNull final Class<?> clazz) {
        final FileData data = fileData.get(clazz);

        if (data == null) {
            return;
        }

        try {
            gson.fromJson(gson.toJson(DESERIALIZER.deserialize(FileUtils.readFile(prepareFile(data)))), clazz);
        } catch (IOException exception) {
            throw new FileLoadException(exception);
        }
    }

    @NotNull
    private File prepareFile(@NotNull final FileData fileData) throws IOException {
        return FileUtils.createFile(fileData.internalPath(), fileData.externalPath());
    }
}
