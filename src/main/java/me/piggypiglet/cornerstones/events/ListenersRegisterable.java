package me.piggypiglet.cornerstones.events;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.piggypiglet.cornerstones.boot.framework.Registerable;
import me.piggypiglet.cornerstones.scanning.framework.Scanner;
import me.piggypiglet.cornerstones.scanning.rules.Rules;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class ListenersRegisterable extends Registerable {
    private final Scanner scanner;
    private final JavaPlugin main;

    @Inject
    public ListenersRegisterable(@NotNull final Scanner scanner, @NotNull final JavaPlugin main) {
        this.scanner = scanner;
        this.main = main;
    }

    @Override
    public void execute(final @NotNull Injector injector) {
        scanner.classes(Rules.builder()
                .typeExtends(Listener.class)
                .build()).map(injector::getInstance).forEach(listener -> main.getServer().getPluginManager().registerEvents(listener, main));
    }
}
