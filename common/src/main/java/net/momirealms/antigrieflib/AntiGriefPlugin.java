package net.momirealms.antigrieflib;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface AntiGriefPlugin {

    void init();

    boolean canPlace(Player player, Location location);

    boolean canBreak(Player player, Location location);

    boolean canInteract(Player player, Location location);

    boolean canInteractEntity(Player player, Entity entity);

    boolean canDamage(Player player, Entity entity);

    String getAntiGriefPluginName();
}
