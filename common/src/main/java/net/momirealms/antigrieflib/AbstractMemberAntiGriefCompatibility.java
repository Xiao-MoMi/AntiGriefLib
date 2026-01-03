package net.momirealms.antigrieflib;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMemberAntiGriefCompatibility implements AntiGriefCompatibility {
    protected final Plugin plugin;

    public AbstractMemberAntiGriefCompatibility(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Plugin plugin() {
        return this.plugin;
    }

    @Override
    public <T> boolean test(Player player, @NotNull Flag<T> flag, T value) {
        if (value instanceof Location location) {
            return isMemberAt(player, location);
        } else if (value instanceof Entity entity) {
            return isMember(player, entity);
        } else {
            return false;
        }
    }

    public boolean isMember(Player player, Entity entity) {
        return isMemberAt(player, entity.getLocation());
    }

    public abstract boolean isMemberAt(Player player, Location location);
}
