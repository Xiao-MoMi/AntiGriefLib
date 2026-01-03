package net.momirealms.antigrieflib.comp;

import com.plotsquared.bukkit.util.BukkitUtil;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class PlotSquaredV5Compatibility extends AbstractAntiGriefCompatibility {

    public PlotSquaredV5Compatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        registerFlagTester(Flag.PLACE, this::isPlotMember);
        registerFlagTester(Flag.BREAK, this::isPlotMember);
        registerFlagTester(Flag.INTERACT, this::isPlotMember);
        registerFlagTester(Flag.INTERACT_ENTITY, this::isPlotMember);
        registerFlagTester(Flag.DAMAGE_ENTITY, this::isPlotMember);
        registerFlagTester(Flag.OPEN_CONTAINER, this::isPlotMember);
        registerFlagTester(Flag.OPEN_DOOR, this::isPlotMember);
        registerFlagTester(Flag.USE_BUTTON, this::isPlotMember);
        registerFlagTester(Flag.USE_PRESSURE_PLATE, this::isPlotMember);
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
