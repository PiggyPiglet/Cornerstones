package me.piggypiglet.cornerstones.world.populator;

import com.google.inject.Inject;
import me.piggypiglet.cornerstones.CornerstoneManager;
import me.piggypiglet.cornerstones.config.Config;
import me.piggypiglet.cornerstones.storage.CornerstoneStorage;
import org.bukkit.Material;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class CornerstonePopulator extends BlockPopulator {
    private final Config config;
    private final CornerstoneManager manager;

    @Inject
    public CornerstonePopulator(@NotNull final Config config, @NotNull final CornerstoneManager manager) {
        this.config = config;
        this.manager = manager;
    }

    @Override
    public void populate(@NotNull final WorldInfo worldInfo, @NotNull final Random random,
                         final int chunkX, final int chunkZ,
                         @NotNull final LimitedRegion region) {
        if (random.nextDouble() > config.chance()) {
            return;
        }

        final int maxHeight = worldInfo.getMaxHeight() - 1;
        final int minHeight = worldInfo.getMinHeight();
        final List<Integer> heights = new ArrayList<>();

        for (int i = 0; i < 16; ++i) {
            final int x = chunkX * 16 + i;

            for (int j = 0; j < 16; ++j) {
                final int z = chunkZ * 16 + j;

                for (int y = maxHeight; y > minHeight; --y) {
                    final Material mat = region.getBlockState(x, y, z).getType();

                    if (config.structureMaterials().contains(mat)) {
                        return;
                    }
                }

                for (int y = maxHeight; y > minHeight; --y) {
                    final Material mat = region.getBlockState(x, y, z).getType();

                    if (!config.surfaceMaterials().contains(mat)) {
                        continue;
                    }

                    heights.add(y);
                    break;
                }
            }
        }

        final int averageHeight = heights.stream()
                .mapToInt(i -> i)
                .sum() / heights.size();

        for (int i = 0; i < 16; ++i) {
            final int x = chunkX * 16 + i;

            for (int j = 0; j < 16; ++j) {
                final int z = chunkZ * 16 + j;

                for (int y = averageHeight + 1; y < maxHeight; ++y) {
                    region.setType(x, y, z, Material.AIR);
                }

                for (int y = averageHeight; y > minHeight; --y) {
                    region.setType(x, y, z, config.cornerstoneMaterial());
                }
            }
        }

        manager.register(worldInfo.getUID(), chunkX, chunkZ);
    }
}
