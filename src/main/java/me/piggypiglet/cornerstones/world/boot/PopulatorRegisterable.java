package me.piggypiglet.cornerstones.world.boot;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.piggypiglet.cornerstones.boot.framework.Registerable;
import me.piggypiglet.cornerstones.config.Config;
import me.piggypiglet.cornerstones.world.populator.CornerstonePopulator;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class PopulatorRegisterable extends Registerable {
    private final JavaPlugin main;
    private final Config config;
    private final CornerstonePopulator populator;

    @Inject
    public PopulatorRegisterable(@NotNull final JavaPlugin main, @NotNull final Config config,
                                 @NotNull final CornerstonePopulator populator) {
        this.main = main;
        this.config = config;
        this.populator = populator;
    }

    @Override
    public void execute() {
        for (final String name : config.worlds()) {
            final World world = main.getServer().getWorld(name);

            if (world == null) {
                main.getLogger().warning("No world found for given name: " + name);
                continue;
            }

            world.getPopulators().add(populator);
        }
    }
}
