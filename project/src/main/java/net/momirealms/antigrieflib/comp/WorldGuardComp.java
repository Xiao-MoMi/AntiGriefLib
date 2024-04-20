package net.momirealms.antigrieflib.comp;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.bukkit.util.Entities;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldGuardComp extends AbstractComp {

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
    public boolean canInteractEntity(Player player, Entity entity) {
        return container.createQuery()
                .testBuild(
                        BukkitAdapter.adapt(entity.getLocation()),
                        WorldGuardPlugin.inst().wrapPlayer(player),
                        Flags.INTERACT
                );
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        final StateFlag flag;
        if (Entities.isHostile(entity)) flag = Flags.MOB_DAMAGE;
        else if (Entities.isNonHostile(entity)) flag = Flags.DAMAGE_ANIMALS;
        else flag = Flags.INTERACT;
        return container.createQuery()
                .testBuild(
                        BukkitAdapter.adapt(entity.getLocation()),
                        WorldGuardPlugin.inst().wrapPlayer(player),
                        entity instanceof Player ? Flags.PVP : flag
                );
    }
}
