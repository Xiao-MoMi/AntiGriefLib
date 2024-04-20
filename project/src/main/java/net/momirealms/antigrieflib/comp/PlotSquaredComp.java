package net.momirealms.antigrieflib.comp;

import com.plotsquared.bukkit.util.BukkitUtil;
import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.Optional;

public class PlotSquaredComp extends AbstractComp {

    private Method mGetLocationV5 = null;
    public PlotSquaredComp(JavaPlugin plugin) {
        super(plugin, "PlotSquared");
    }

    @Override
    public void init() {
        Plugin plotPlugin = getPlugin().getServer().getPluginManager().getPlugin(getAntiGriefPluginName());
        if (plotPlugin.getDescription().getVersion().startsWith("5.")) {
            try {
                mGetLocationV5 = BukkitUtil.class.getDeclaredMethod("getLocation", Location.class);
            } catch (Throwable t) {
                getPlugin().getLogger().warning("Can't get PlotSquared V5 getLocation method: " + t.getClass().getName() + ": " + t.getMessage());
            }
        }
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return isPlotMember(player, location);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return isPlotMember(player, location);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return isPlotMember(player, location);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return isPlotMember(player, entity.getLocation());
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return isPlotMember(player, entity.getLocation());
    }

    private com.plotsquared.core.location.Location adapt(Location location) {
        if (mGetLocationV5 == null) {
            return com.plotsquared.core.location.Location.at(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }
        try {
            return (com.plotsquared.core.location.Location) mGetLocationV5.invoke(null, location);
        } catch (ReflectiveOperationException e) {
            getPlugin().getLogger().warning("Can't call BukkitUtil.getLocation(Location) of PlotSquared: " + e.getClass() + ": " + e.getMessage());
            return null;
        }
    }

    private boolean isPlotMember(Player player, Location location) {
        var psLocation = adapt(location);
        if (psLocation == null) return true;
        if (psLocation.isPlotRoad()) return false;
        if (!psLocation.isPlotArea()) return true;
        return Optional.ofNullable(psLocation.getPlotArea().getPlot(psLocation)).map(plot -> plot.isAdded(player.getUniqueId())).orElse(false);
    }
}
