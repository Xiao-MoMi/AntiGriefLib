package net.momirealms.antigrieflib.comp;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumskyblock.dependencies.iridiumteams.PermissionType;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class IridiumSkyblockCompatibility extends AbstractAntiGriefCompatibility {

    private IridiumSkyblockAPI api;

    public IridiumSkyblockCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        api = IridiumSkyblockAPI.getInstance();
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
        return api.getIslandViaLocation(location)
                        .map(island -> api.getIslandPermission(
                                island,
                                api.getUser(player),
                                PermissionType.BLOCK_PLACE)
                        )
                        .orElse(true);
    }

    private boolean canBreak(Player player, Location location) {
        return api.getIslandViaLocation(location)
                        .map(island -> api.getIslandPermission(
                                island,
                                api.getUser(player),
                                PermissionType.BLOCK_BREAK)
                        )
                        .orElse(true);
    }

    private boolean canInteract(Player player, Location location) {
        return api.getIslandViaLocation(location)
                        .map(island -> api.getIslandPermission(
                                island,
                                api.getUser(player),
                                PermissionType.INTERACT)
                        )
                        .orElse(true);
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        return api.getIslandViaLocation(entity.getLocation())
                .map(island -> api.getIslandPermission(
                        island,
                        api.getUser(player),
                        PermissionType.INTERACT)
                ).orElse(true);
    }

    private boolean canDamageEntity(Player player, Entity entity) {
        return api.getIslandViaLocation(entity.getLocation())
                .map(island -> api.getIslandPermission(
                        island,
                        api.getUser(player),
                        PermissionType.KILL_MOBS)
                ).orElse(true);
    }

    private boolean canOpenContainer(Player player, Location location) {
        return api.getIslandViaLocation(location)
                .map(island -> api.getIslandPermission(
                        island,
                        api.getUser(player),
                        PermissionType.OPEN_CONTAINERS)
                ).orElse(true);
    }

    private boolean canOpenDoor(Player player, Location location) {
        return api.getIslandViaLocation(location)
                .map(island -> api.getIslandPermission(
                        island,
                        api.getUser(player),
                        PermissionType.DOORS)
                ).orElse(true);
    }

    private boolean canUseButton(Player player, Location location) {
        return api.getIslandViaLocation(location)
                .map(island -> api.getIslandPermission(
                        island,
                        api.getUser(player),
                        PermissionType.INTERACT)
                ).orElse(true);
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        return api.getIslandViaLocation(location)
                .map(island -> api.getIslandPermission(
                        island,
                        api.getUser(player),
                        PermissionType.INTERACT)
                ).orElse(true);
    }
}
