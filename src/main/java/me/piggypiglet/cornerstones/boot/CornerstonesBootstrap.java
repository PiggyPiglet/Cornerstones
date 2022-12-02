package me.piggypiglet.cornerstones.boot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.piggypiglet.cornerstones.boot.exceptions.BootstrapHaltException;
import me.piggypiglet.cornerstones.boot.framework.Registerable;
import me.piggypiglet.cornerstones.events.ListenersRegisterable;
import me.piggypiglet.cornerstones.file.boot.FileDataRegisterable;
import me.piggypiglet.cornerstones.file.boot.GsonRegisterable;
import me.piggypiglet.cornerstones.file.boot.InitialDeserializationRegisterable;
import me.piggypiglet.cornerstones.guice.ExceptionalInjector;
import me.piggypiglet.cornerstones.guice.modules.InitialModule;
import me.piggypiglet.cornerstones.world.boot.PopulatorRegisterable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class CornerstonesBootstrap extends JavaPlugin {
    private static final List<Class<? extends Registerable>> REGISTERABLES = List.of(
            FileDataRegisterable.class,
            GsonRegisterable.class,
            InitialDeserializationRegisterable.class,
            ListenersRegisterable.class,
            PopulatorRegisterable.class
    );

    @Override
    public void onEnable() {
        final InitialModule initialModule = new InitialModule(this);
        final AtomicReference<Injector> injector = new AtomicReference<>(new ExceptionalInjector(Guice.createInjector(initialModule)));

        for (final Class<? extends Registerable> registerableClass : REGISTERABLES) {
            final Registerable registerable = injector.get().getInstance(registerableClass);
            registerable.run(injector.get());

            final BootstrapHaltException halt = registerable.halt();

            if (halt != null) {
                throw halt;
            }

            registerable.createModule()
                    .map(injector.get()::createChildInjector)
                    .ifPresent(injector::set);
        }
    }
}
