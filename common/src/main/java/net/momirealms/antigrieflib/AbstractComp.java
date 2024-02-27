package net.momirealms.antigrieflib;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public abstract class AbstractComp implements AntiGriefPlugin {

    private final JavaPlugin plugin;
    private final String pluginName;

    public AbstractComp(JavaPlugin plugin, String antiGriefPlugin) {
        this.plugin = plugin;
        this.pluginName = antiGriefPlugin;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    public String getAntiGriefPluginName() {
        return pluginName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractComp that = (AbstractComp) o;
        return Objects.equals(pluginName, that.pluginName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pluginName);
    }
}
