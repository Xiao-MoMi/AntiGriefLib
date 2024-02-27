package net.momirealms.antigrieflib.comp;

import com.bekvon.bukkit.residence.containers.Flags;
import net.momirealms.antigrieflib.AbstractComp;
import net.momirealms.antigrieflib.CustomFlag;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class ResidenceComp extends AbstractComp implements CustomFlag {

    public ResidenceComp(JavaPlugin plugin) {
        super(plugin, "Residence");
    }

    @Override
    public void init() {
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return Optional.ofNullable(com.bekvon.bukkit.residence.Residence.getInstance().getResidenceManager().getByLoc(location))
                .map(claimedResidence -> claimedResidence.getPermissions().playerHas(player, Flags.place, true))
                .orElse(true);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(com.bekvon.bukkit.residence.Residence.getInstance().getResidenceManager().getByLoc(location))
                .map(claimedResidence -> claimedResidence.getPermissions().playerHas(player, Flags.destroy, true))
                .orElse(true);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(com.bekvon.bukkit.residence.Residence.getInstance().getResidenceManager().getByLoc(location))
                .map(claimedResidence -> claimedResidence.getPermissions().playerHas(player, Flags.use, true))
                .orElse(true);
    }
}
