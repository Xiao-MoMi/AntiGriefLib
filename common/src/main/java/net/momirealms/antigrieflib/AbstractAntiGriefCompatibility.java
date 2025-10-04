package net.momirealms.antigrieflib;

import org.bukkit.plugin.Plugin;

public abstract class AbstractAntiGriefCompatibility implements AntiGriefCompatibility {
    protected final Plugin plugin;

    public AbstractAntiGriefCompatibility(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Plugin plugin() {
        return this.plugin;
    }
}
