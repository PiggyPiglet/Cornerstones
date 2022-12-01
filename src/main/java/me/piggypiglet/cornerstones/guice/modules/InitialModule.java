package me.piggypiglet.cornerstones.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import me.piggypiglet.cornerstones.boot.CornerstonesBootstrap;
import me.piggypiglet.cornerstones.scanning.framework.Scanner;
import me.piggypiglet.cornerstones.scanning.implementations.JarScanner;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class InitialModule extends AbstractModule {
    private final JavaPlugin main;

    public InitialModule(@NotNull final JavaPlugin main) {
        this.main = main;
    }

    @Provides
    @Singleton
    public JavaPlugin providesMain() {
        return main;
    }

    @Provides
    @Singleton
    public Scanner providesScanner() {
        return JarScanner.create(CornerstonesBootstrap.class, "me.piggypiglet.cornerstones");
    }
}
