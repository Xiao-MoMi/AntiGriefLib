package net.momirealms.antigrieflib.comp;

import net.momirealms.antigrieflib.AbstractComp;
import net.momirealms.antigrieflib.util.ReflectionUtils;
import net.sacredlabyrinth.Phaed.PreciousStones.api.Api;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PreciousStonesComp extends AbstractComp {

    private Api api;

    public PreciousStonesComp(JavaPlugin plugin) {
        super(plugin, "PreciousStones");
    }

    @Override
    public boolean checkClazz() {
        return ReflectionUtils.classExists("net.sacredlabyrinth.Phaed.PreciousStones.api.Api");
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
}
