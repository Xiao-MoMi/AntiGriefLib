package net.momirealms.antigrieflib.comp;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.island.IslandPrivilege;
import net.momirealms.antigrieflib.AbstractComp;
import net.momirealms.antigrieflib.CustomFlag;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SuperiorSkyblockComp extends AbstractComp implements CustomFlag {

    public SuperiorSkyblockComp(JavaPlugin plugin) {
        super(plugin, "SuperiorSkyblock2");
    }

    @Override
    public void init() {
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        Island island = SuperiorSkyblockAPI.getIslandAt(location);
        if (island == null) return true;
        return island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivilege.getByName("BUILD"));
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        Island island = SuperiorSkyblockAPI.getIslandAt(location);
        if (island == null) return true;
        return island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivilege.getByName("BREAK"));
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        Island island = SuperiorSkyblockAPI.getIslandAt(location);
        if (island == null) return true;
        return island.hasPermission(SuperiorSkyblockAPI.getPlayer(player), IslandPrivilege.getByName("INTERACT"));
    }
}
