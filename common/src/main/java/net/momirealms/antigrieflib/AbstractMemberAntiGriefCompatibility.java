package net.momirealms.antigrieflib;

import org.bukkit.plugin.Plugin;

public abstract class AbstractMemberAntiGriefCompatibility implements AntiGriefCompatibility {
    protected final Plugin plugin;

    public AbstractMemberAntiGriefCompatibility(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Plugin plugin() {
        return this.plugin;
    }
}
