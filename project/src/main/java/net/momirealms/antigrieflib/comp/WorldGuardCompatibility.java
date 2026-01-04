package net.momirealms.antigrieflib.comp;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.bukkit.util.Entities;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WorldGuardCompatibility extends AbstractAntiGriefCompatibility {
    private StateFlag PLACE_FLAG;
    private StateFlag BREAK_FLAG;
    private StateFlag INTERACT_FLAG;
    private RegionContainer container;

    public WorldGuardCompatibility(Plugin plugin) {
        super(plugin);
    }

    public void setPlaceFlag(StateFlag PLACE_FLAG) {
        this.PLACE_FLAG = PLACE_FLAG;
    }

    public void setBreakFlag(StateFlag BREAK_FLAG) {
        this.BREAK_FLAG = BREAK_FLAG;
    }

    public void setInteractFlag(StateFlag INTERACT_FLAG) {
        this.INTERACT_FLAG = INTERACT_FLAG;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        registerFlagTester(Flag.PLACE, this::canPlace);
        registerFlagTester(Flag.BREAK, this::canBreak);
        registerFlagTester(Flag.INTERACT, this::canInteract);
        registerFlagTester(Flag.INTERACT_ENTITY, this::canInteractEntity);
        registerFlagTester(Flag.DAMAGE_ENTITY, this::canDamageEntity);
        registerFlagTester(Flag.OPEN_CONTAINER, this::canOpenContainer);
        registerFlagTester(Flag.OPEN_DOOR, this::canOpenDoor);
        registerFlagTester(Flag.USE_BUTTON, this::canUseButton);
        registerFlagTester(Flag.USE_PRESSURE_PLATE, this::canUsePressurePlate);
    }

    private boolean canPlace(Player player, Location location) {
        return container.createQuery()
                .testBuild(
                        BukkitAdapter.adapt(location),
                        WorldGuardPlugin.inst().wrapPlayer(player),
                        PLACE_FLAG == null ? Flags.BLOCK_PLACE : PLACE_FLAG
                );
    }

    private boolean canBreak(Player player, Location location) {
        return container.createQuery()
                .testBuild(
                        BukkitAdapter.adapt(location),
                        WorldGuardPlugin.inst().wrapPlayer(player),
                        BREAK_FLAG == null ? Flags.BLOCK_BREAK : BREAK_FLAG
                );
    }

    private boolean canInteract(Player player, Location location) {
        return container.createQuery()
                .testBuild(
                        BukkitAdapter.adapt(location),
                        WorldGuardPlugin.inst().wrapPlayer(player),
                        INTERACT_FLAG == null ? Flags.INTERACT : INTERACT_FLAG
                );
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        return container.createQuery()
                .testBuild(
                        BukkitAdapter.adapt(entity.getLocation()),
                        WorldGuardPlugin.inst().wrapPlayer(player),
                        Flags.INTERACT
                );
    }

    private boolean canDamageEntity(Player player, Entity entity) {
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

    private boolean canOpenContainer(Player player, Location location) {
        return container.createQuery()
                .testBuild(
                        BukkitAdapter.adapt(location),
                        WorldGuardPlugin.inst().wrapPlayer(player),
                        Flags.CHEST_ACCESS
                );
    }

    private boolean canOpenDoor(Player player, Location location) {
        return container.createQuery()
                .testBuild(
                        BukkitAdapter.adapt(location),
                        WorldGuardPlugin.inst().wrapPlayer(player),
                        Flags.INTERACT
                );
    }

    private boolean canUseButton(Player player, Location location) {
        return container.createQuery()
                .testBuild(
                        BukkitAdapter.adapt(location),
                        WorldGuardPlugin.inst().wrapPlayer(player),
                        Flags.INTERACT
                );
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        return container.createQuery()
                .testBuild(
                        BukkitAdapter.adapt(location),
                        WorldGuardPlugin.inst().wrapPlayer(player),
                        Flags.INTERACT
                );
    }
}
