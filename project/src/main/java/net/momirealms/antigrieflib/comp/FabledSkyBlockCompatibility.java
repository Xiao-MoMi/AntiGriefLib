package net.momirealms.antigrieflib.comp;

import com.craftaro.skyblock.api.SkyBlockAPI;
import com.craftaro.skyblock.api.island.IslandRole;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class FabledSkyBlockCompatibility extends AbstractAntiGriefCompatibility {

    public FabledSkyBlockCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return isIslandMember(player, location);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return isIslandMember(player, location);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return isIslandMember(player, location);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return isIslandMember(player, entity.getLocation());
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return isIslandMember(player, entity.getLocation());
    }

    private boolean isIslandMember(Player player, Location location) {
        return Optional.ofNullable(SkyBlockAPI.getIslandManager().getIslandAtLocation(location))
                .map(island -> {
                     if (island.isCoopPlayer(player))
                         return true;
                     return Optional.ofNullable(island.getRole(player))
                            .map(islandRole -> islandRole != IslandRole.VISITOR)
                            .orElse(false);
                     }
                )
                .orElse(true);
    }
}