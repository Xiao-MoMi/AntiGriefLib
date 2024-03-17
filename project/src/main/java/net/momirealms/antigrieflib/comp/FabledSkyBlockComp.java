package net.momirealms.antigrieflib.comp;

import com.craftaro.skyblock.api.SkyBlockAPI;
import com.craftaro.skyblock.api.island.IslandRole;
import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class FabledSkyBlockComp extends AbstractComp {

    public FabledSkyBlockComp(JavaPlugin plugin) {
        super(plugin, "FabledSkyBlock");
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

    private boolean isIslandMember(Player player, Location location) {
        return Optional.ofNullable(SkyBlockAPI.getIslandManager().getIslandAtLocation(location))
                .map(island -> Optional.ofNullable(island.getRole(player))
                        .map(islandRole -> islandRole != IslandRole.VISITOR)
                        .orElse(false)
                )
                .orElse(true);
    }
}
