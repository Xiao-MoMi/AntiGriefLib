package net.momirealms.antigrieflib.comp;

import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.IslandPrivilege;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import com.bgsoftware.superiorskyblock.island.privilege.IslandPrivileges;
import com.bgsoftware.superiorskyblock.world.BukkitEntities;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class SuperiorSkyblockCompatibility extends AbstractAntiGriefCompatibility {

    public SuperiorSkyblockCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return Optional.ofNullable(SuperiorSkyblockAPI.getIslandAt(location))
                .map(island -> island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivileges.BUILD))
                .orElse(true);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(SuperiorSkyblockAPI.getIslandAt(location))
                .map(island -> island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivileges.BREAK))
                .orElse(true);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(SuperiorSkyblockAPI.getIslandAt(location))
                .map(island -> island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivileges.INTERACT))
                .orElse(true);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(SuperiorSkyblockAPI.getIslandAt(entity.getLocation()))
                .map(island -> island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivileges.INTERACT))
                .orElse(true);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        final BukkitEntities.EntityCategory category = BukkitEntities.getCategory(entity.getType());
        final SuperiorSkyblockPlugin plugin = SuperiorSkyblockPlugin.getPlugin(SuperiorSkyblockPlugin.class);
        return Optional.ofNullable(SuperiorSkyblockAPI.getIslandAt(entity.getLocation()))
                .map(island -> {
                    boolean banPvp = false;
                    if (entity instanceof Player e) {
                        final SuperiorPlayer targetPlayer = plugin.getPlayers().getSuperiorPlayer(e);
                        if (category.equals(BukkitEntities.EntityCategory.UNKNOWN)) {
                            banPvp = island.isSpawn() ? (plugin.getSettings().getSpawn().isProtected() && !plugin.getSettings().getSpawn().isPlayersDamage()) :
                                    ((!plugin.getSettings().isVisitorsDamage() && island.isVisitor(targetPlayer, false)) ||
                                            (!plugin.getSettings().isCoopDamage() && island.isCoop(targetPlayer)));
                        }
                    }
                    return entity instanceof Player ? !banPvp : island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), category.getDamagePrivilege());
                })
                .orElse(true);
    }

    @Override
    public boolean canInteractContainer(Player player, Location location) {
        return Optional.ofNullable(SuperiorSkyblockAPI.getIslandAt(location))
                .map(island -> island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivileges.CHEST_ACCESS))
                .orElse(true);
    }
}
