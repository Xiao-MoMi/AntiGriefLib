package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractMemberAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import us.talabrek.ultimateskyblock.api.uSkyBlockAPI;

import java.util.Optional;

public class USkyBlockCompatibility extends AbstractMemberAntiGriefCompatibility {

    private uSkyBlockAPI api;

    public USkyBlockCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        api = (uSkyBlockAPI) Bukkit.getPluginManager().getPlugin("uSkyBlock");
    }

    @Override
    public <T> boolean test(Player player, @NotNull Flag<T> flag, T value) {
        if (value instanceof Location location) {
            return isIslandMember(player, location);
        } else if (value instanceof Entity entity) {
            return isIslandMember(player, entity);
        } else {
            return false;
        }
    }

    private boolean isIslandMember(Player player, Entity entity) {
        return isIslandMember(player, entity.getLocation());
    }

    private boolean isIslandMember(Player player, Location location) {
        return Optional.ofNullable(api.getIslandInfo(location))
                .map(islandInfo -> islandInfo.getMembers().contains(player.getName()))
                .orElse(true);
    }
}
