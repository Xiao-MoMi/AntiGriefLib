package net.momirealms.antigrieflib.comp;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.momirealms.antigrieflib.AbstractComp;
import net.momirealms.antigrieflib.CustomFlag;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldGuardComp extends AbstractComp implements CustomFlag {

    private StateFlag PLACE_FLAG;
    private StateFlag BREAK_FLAG;
    private StateFlag INTERACT_FLAG;
    private RegionContainer container;

    public WorldGuardComp(JavaPlugin plugin) {
        super(plugin, "WorldGuard");
    }

    @Override
    public void init() {
        container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return container.createQuery()
                .testBuild(
                        BukkitAdapter.adapt(location),
                        WorldGuardPlugin.inst().wrapPlayer(player),
                        PLACE_FLAG == null ? Flags.BLOCK_PLACE : PLACE_FLAG
                );
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return container.createQuery()
                .testBuild(
                        BukkitAdapter.adapt(location),
                        WorldGuardPlugin.inst().wrapPlayer(player),
                        BREAK_FLAG == null ? Flags.BLOCK_BREAK : BREAK_FLAG
                );
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return container.createQuery()
                .testBuild(
                        BukkitAdapter.adapt(location),
                        WorldGuardPlugin.inst().wrapPlayer(player),
                        INTERACT_FLAG == null ? Flags.INTERACT : INTERACT_FLAG
                );
    }

    @Override
    public void registerOnLoad(Flag customPlaceFlag, Flag customBreakFlag, Flag customInteractFlag) {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        if (customPlaceFlag != null) {
            PLACE_FLAG = new StateFlag(customPlaceFlag.getName(), customPlaceFlag.getDefaultValue());
            registry.register(PLACE_FLAG);
        }
        if (customBreakFlag != null) {
            BREAK_FLAG = new StateFlag(customBreakFlag.getName(), customBreakFlag.getDefaultValue());
            registry.register(BREAK_FLAG);
        }
        if (customInteractFlag != null) {
            INTERACT_FLAG = new StateFlag(customInteractFlag.getName(), customInteractFlag.getDefaultValue());
            registry.register(INTERACT_FLAG);
        }
    }
}
