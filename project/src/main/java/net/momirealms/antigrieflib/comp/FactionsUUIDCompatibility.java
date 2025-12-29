package net.momirealms.antigrieflib.comp;

import com.massivecraft.factions.FactionsPlugin;
import com.massivecraft.factions.listeners.FactionsBlockListener;
import com.massivecraft.factions.listeners.FactionsEntityListener;
import com.massivecraft.factions.perms.PermissibleActions;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FactionsUUIDCompatibility extends AbstractAntiGriefCompatibility {

    private FactionsPlugin plugin;

    public FactionsUUIDCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        plugin = FactionsPlugin.getInstance();
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        if (!plugin.worldUtil().isEnabled(location.getWorld())) return true;
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, location, PermissibleActions.BUILD, true);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        if (!plugin.worldUtil().isEnabled(location.getWorld())) return true;
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, location, PermissibleActions.DESTROY, true);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        if (!plugin.worldUtil().isEnabled(location.getWorld())) return true;
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, location, PermissibleActions.CONTAINER, true);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        if (!plugin.worldUtil().isEnabled(entity.getWorld())) return true;
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, entity.getLocation(), PermissibleActions.CONTAINER, true);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        if (!plugin.worldUtil().isEnabled(entity.getWorld())) return true;
        return FactionsEntityListener.canDamage(player, entity, true);
    }

    @Override
    public boolean canOpenContainer(Player player, Location location) {
        if (!plugin.worldUtil().isEnabled(location.getWorld())) return true;
        return FactionsBlockListener.playerCanBuildDestroyBlock(player, location, PermissibleActions.CONTAINER, true);
    }
}
