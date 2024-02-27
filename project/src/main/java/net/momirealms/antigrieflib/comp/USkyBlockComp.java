package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import us.talabrek.ultimateskyblock.api.uSkyBlockAPI;

import java.util.Optional;

public class USkyBlockComp extends AbstractComp {

    private uSkyBlockAPI api;

    public USkyBlockComp(JavaPlugin plugin) {
        super(plugin, "uSkyBlock");
    }

    @Override
    public void init() {
        api = (uSkyBlockAPI) Bukkit.getPluginManager().getPlugin("uSkyBlock");
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
        return Optional.ofNullable(api.getIslandInfo(location)).map(islandInfo -> {
            return islandInfo.getMembers().contains(player.getName());
        }).orElse(true);
    }
}
