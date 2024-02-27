package net.momirealms.antigrieflib.comp;

import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.listeners.FactionsBlockListener;
import com.massivecraft.factions.perms.PermissibleActions;
import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FactionsUUIDComp extends AbstractComp {

    private FactionsPlugin plugin;

    public FactionsUUIDComp(JavaPlugin plugin) {
        super(plugin, "FactionsUUID");
    }

    @Override
    public void init() {
        plugin = FactionsPlugin.getInstance();
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        if (!plugin.worldUtil().isEnabled(location.getWorld())) return true;
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, location, PermissibleActions.BUILD, false);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        if (!plugin.worldUtil().isEnabled(location.getWorld())) return true;
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, location, PermissibleActions.DESTROY, false);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        if (!plugin.worldUtil().isEnabled(location.getWorld())) return true;
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, location, PermissibleActions.CONTAINER, false);
    }
}
