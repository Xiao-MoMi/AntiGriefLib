package net.momirealms.antigrieflib.comp;

import biz.princeps.landlord.api.ILandLord;
import net.momirealms.antigrieflib.AbstractMemberAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class LandlordCompatibility extends AbstractMemberAntiGriefCompatibility {

    private ILandLord landLord;

    public LandlordCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
        landLord = (ILandLord) Bukkit.getPluginManager().getPlugin("Landlord");
    }

    @Override
    public <T> boolean test(Player player, @NotNull Flag<T> flag, T value) {
        if (value instanceof Location location) {
            return landlordMemberCheck(player, location);
        } else if (value instanceof Entity entity) {
            return landlordMemberCheck(player, entity);
        } else {
            return false;
        }
    }

    private boolean landlordMemberCheck(Player player, Entity entity) {
        return landlordMemberCheck(player, entity.getLocation());
    }

    private boolean landlordMemberCheck(final Player player, final Location location) {
        return Optional.ofNullable(landLord.getWGManager().getRegion(location))
                .map(region -> region.isOwner(player.getUniqueId()) || region.isFriend(player.getUniqueId()))
                .orElse(false);
    }
}
