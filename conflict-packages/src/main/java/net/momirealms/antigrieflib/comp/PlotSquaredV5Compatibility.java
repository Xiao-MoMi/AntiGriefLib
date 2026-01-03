package net.momirealms.antigrieflib.comp;

import com.plotsquared.bukkit.util.BukkitUtil;
import net.momirealms.antigrieflib.AbstractMemberAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlotSquaredV5Compatibility extends AbstractMemberAntiGriefCompatibility {

    public PlotSquaredV5Compatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
    }

    @Override
    public <T> boolean test(Player player, @NotNull Flag<T> flag, T value) {
        if (value instanceof Location location) {
            return isPlotMember(player, location);
        } else if (value instanceof Entity entity) {
            return isPlotMember(player, entity);
        } else {
            return false;
        }
    }

    private boolean isPlotMember(Player player, Entity entity) {
        return isPlotMember(player, entity.getLocation());
    }

    private boolean isPlotMember(Player player, Location location) {
        var psLocation = BukkitUtil.getLocation(location);
        if (psLocation.isPlotRoad()) return false;
        if (!psLocation.isPlotArea()) return true;
        return Optional.ofNullable(psLocation.getPlotArea().getPlot(psLocation)).map(plot -> plot.isAdded(player.getUniqueId()) || plot.isOwner(player.getUniqueId())).orElse(false);
    }
}
