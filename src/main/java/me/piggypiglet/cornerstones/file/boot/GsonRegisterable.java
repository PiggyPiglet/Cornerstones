package me.piggypiglet.cornerstones.file.boot;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import me.piggypiglet.cornerstones.boot.framework.Registerable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class GsonRegisterable extends Registerable {
    private static final Named FILES = Names.named("files");

    private final Map<Class<?>, Object> files;

    @Inject
    public GsonRegisterable(@NotNull @Named("files") final Map<Class<?>, Object> files) {
        this.files = files;
    }

    @Override
    public void execute() {
        final AtomicReference<GsonBuilder> builder = new AtomicReference<>(new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES));
        files.forEach((clazz, instance) -> builder.set(builder.get()
                .registerTypeAdapter(clazz, instanceCreator(instance))));
        addBinding(Gson.class, FILES, builder.get().create());
    }

    private static <T> InstanceCreator<T> instanceCreator(@NotNull final T instance) {
        return type -> instance;
    }
}
