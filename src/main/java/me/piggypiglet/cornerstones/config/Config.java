package me.piggypiglet.cornerstones.config;

import com.google.inject.Singleton;
import me.piggypiglet.cornerstones.file.annotations.File;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
@Singleton @File
public final class Config {
    private double chance;
    private List<String> worlds;

    public double chance() {
        return chance;
    }

    @NotNull
    public List<String> worlds() {
        return worlds;
    }
}
