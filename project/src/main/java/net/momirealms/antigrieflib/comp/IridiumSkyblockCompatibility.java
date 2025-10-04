package net.momirealms.antigrieflib.comp;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import com.iridium.iridiumteams.PermissionType;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class IridiumSkyblockCompatibility extends AbstractAntiGriefCompatibility {

    private IridiumSkyblockAPI api;

    public IridiumSkyblockCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        api = IridiumSkyblockAPI.getInstance();
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return api.getIslandViaLocation(location)
                        .map(island -> api.getIslandPermission(
                                island,
                                api.getUser(player),
                                PermissionType.BLOCK_PLACE)
                        )
                        .orElse(true);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return api.getIslandViaLocation(location)
                        .map(island -> api.getIslandPermission(
                                island,
                                api.getUser(player),
                                PermissionType.BLOCK_BREAK)
                        )
                        .orElse(true);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return api.getIslandViaLocation(location)
                        .map(island -> api.getIslandPermission(
                                island,
                                api.getUser(player),
                                PermissionType.OPEN_CONTAINERS)
                        )
                        .orElse(true);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return api.getIslandViaLocation(entity.getLocation())
                .map(island -> api.getIslandPermission(
                        island,
                        api.getUser(player),
                        PermissionType.OPEN_CONTAINERS)
                ).orElse(true);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return api.getIslandViaLocation(entity.getLocation())
                .map(island -> api.getIslandPermission(
                        island,
                        api.getUser(player),
                        PermissionType.KILL_MOBS)
                ).orElse(true);
    }
}
