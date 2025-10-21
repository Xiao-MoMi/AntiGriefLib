package net.momirealms.antigrieflib;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface AntiGriefCompatibility {

    void init();

    boolean canPlace(Player player, Location location);

    boolean canBreak(Player player, Location location);

    boolean canInteract(Player player, Location location);

    boolean canInteractEntity(Player player, Entity entity);

    boolean canDamage(Player player, Entity entity);

    boolean canInteractContainer(Player player, Location location);

    Plugin plugin();

    default String id() {
        return plugin().getName();
    }
}
