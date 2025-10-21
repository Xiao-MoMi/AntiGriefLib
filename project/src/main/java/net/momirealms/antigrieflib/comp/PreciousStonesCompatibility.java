package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.sacredlabyrinth.Phaed.PreciousStones.api.Api;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PreciousStonesCompatibility extends AbstractAntiGriefCompatibility {

    private Api api;

    public PreciousStonesCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        this.api = new Api();
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return api.canPlace(player, location);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return api.canBreak(player, location);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return api.canPlace(player, location);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return api.canPlace(player, entity.getLocation());
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return api.canBreak(player, entity.getLocation());
    }

    @Override
    public boolean canInteractContainer(Player player, Location location) {
        return api.canPlace(player, location);
    }
}
