package net.momirealms.antigrieflib;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface AntiGriefCompatibility {

    void init();

    <T> boolean test(Player player, @NotNull Flag<T> flag, T value);

    Plugin plugin();

    default String id() {
        return plugin().getName();
    }
}
