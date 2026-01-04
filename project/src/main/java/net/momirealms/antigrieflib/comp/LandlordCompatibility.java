package net.momirealms.antigrieflib.comp;

import biz.princeps.landlord.api.ILandLord;
import net.momirealms.antigrieflib.AbstractMemberAntiGriefCompatibility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

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
    public boolean isMemberAt(Player player, Location location) {
        return Optional.ofNullable(landLord.getWGManager().getRegion(location))
                .map(region -> region.isOwner(player.getUniqueId()) || region.isFriend(player.getUniqueId()))
                .orElse(false);
    }
}
