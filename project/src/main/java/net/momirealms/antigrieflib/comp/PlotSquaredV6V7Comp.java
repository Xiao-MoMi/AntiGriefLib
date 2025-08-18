package net.momirealms.antigrieflib.comp;

import com.plotsquared.bukkit.util.BukkitUtil;
import net.momirealms.antigrieflib.AbstractComp;
import net.momirealms.antigrieflib.util.ReflectionUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class PlotSquaredV6V7Comp extends AbstractComp {

    public PlotSquaredV6V7Comp(JavaPlugin plugin) {
        super(plugin, "PlotSquared");
    }

    @Override
    public boolean checkClazz() {
        return ReflectionUtils.classExists("com.plotsquared.bukkit.util.BukkitUtil");
    }

    @Override
    public void init() {
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

    private boolean isPlotMember(Player player, Location location) {
        var psLocation = BukkitUtil.adapt(location);
        if (psLocation.isPlotRoad()) return false;
        if (!psLocation.isPlotArea()) return true;
        return Optional.ofNullable(psLocation.getPlotArea().getPlot(psLocation)).map(plot -> plot.isAdded(player.getUniqueId()) || plot.isOwner(player.getUniqueId())).orElse(false);
    }
}
