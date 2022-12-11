package me.piggypiglet.cornerstones.world;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

// ------------------------------
// Copyright (c) PiggyPiglet 2022
// https://www.piggypiglet.me
// ------------------------------
public final class CornerstoneRegions {
    private static final RegionContainer REGION_CONTAINER = WorldGuard.getInstance().getPlatform().getRegionContainer();

    public void register(@NotNull final UUID worldUuid, final int chunkX,
                         final int chunkZ) {
        final World world = Bukkit.getWorld(worldUuid);

        if (world == null) {
            return;
        }

        final RegionManager regions = REGION_CONTAINER.get(BukkitAdapter.adapt(world));

        if (regions == null) {
            return;
        }

        final String id = chunkX + '_' + chunkZ + "-cs";
        ProtectedRegion region = regions.getRegion(id);

        if (region != null) {
            return;
        }

        region = createRegion(world, chunkX, chunkZ);
        defaultFlags(region);

        regions.addRegion(region);
    }

    private void defaultFlags(@NotNull final ProtectedRegion region) {
        region.setFlag(Flags.BUILD, StateFlag.State.DENY);
        region.setFlag(Flags.GREET_MESSAGE, "test");
    }

    @NotNull
    private ProtectedRegion createRegion(@NotNull final World world, final int chunkX,
                                         final int chunkZ) {
        final int x1 = chunkX * 16;
        final int z1 = chunkZ * 16;

        final int x2Offset = chunkX > 0 ? -1 : 1;
        final int z2Offset = chunkZ > 0 ? -1 : 1;

        final int x2 = chunkX * 17 + x2Offset;
        final int z2 = chunkZ * 17 + z2Offset;

        final BlockVector2 corner1 = BlockVector2.at(x1, z1);
        final BlockVector2 corner2 = BlockVector2.at(x2, z2);

        System.out.println(x1 + "," + z1 + '_' + x2 + ',' + z2);

        final String id = chunkX + '_' + chunkZ + "-cs";

        return new ProtectedPolygonalRegion(id, List.of(corner1, corner2), world.getMinHeight(), world.getMaxHeight() - 1);
    }
}
