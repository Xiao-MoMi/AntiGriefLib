package net.momirealms.antigrieflib.comp;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.island.privilege.IslandPrivileges;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class SuperiorSkyblockCompatibility extends AbstractAntiGriefCompatibility {

    public SuperiorSkyblockCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
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
        return Optional.ofNullable(SuperiorSkyblockAPI.getIslandAt(location))
                .map(island -> island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivileges.BUILD))
                .orElse(true);
    }

    private boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(SuperiorSkyblockAPI.getIslandAt(location))
                .map(island -> island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivileges.BUILD))
                .orElse(true);
    }

    private boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(SuperiorSkyblockAPI.getIslandAt(location))
                .map(island -> island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivileges.BUILD))
                .orElse(true);
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(SuperiorSkyblockAPI.getIslandAt(entity.getLocation()))
                .map(island -> island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivileges.BUILD))
                .orElse(true);
    }

    private boolean canDamageEntity(Player player, Entity entity) {
        return Optional.ofNullable(SuperiorSkyblockAPI.getIslandAt(entity.getLocation()))
                .map(island -> island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivileges.MONSTER_DAMAGE))
                .orElse(true);
    }

    private boolean canOpenContainer(Player player, Location location) {
        return Optional.ofNullable(SuperiorSkyblockAPI.getIslandAt(location))
                .map(island -> island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivileges.BUILD))
                .orElse(true);
    }

    private boolean canOpenDoor(Player player, Location location) {
        return Optional.ofNullable(SuperiorSkyblockAPI.getIslandAt(location))
                .map(island -> island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivileges.BUILD))
                .orElse(true);
    }

    private boolean canUseButton(Player player, Location location) {
        return Optional.ofNullable(SuperiorSkyblockAPI.getIslandAt(location))
                .map(island -> island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivileges.BUILD))
                .orElse(true);
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        return Optional.ofNullable(SuperiorSkyblockAPI.getIslandAt(location))
                .map(island -> island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivileges.BUILD))
                .orElse(true);
    }
}
