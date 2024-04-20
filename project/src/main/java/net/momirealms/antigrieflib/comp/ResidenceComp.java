package net.momirealms.antigrieflib.comp;

import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.listeners.ResidenceEntityListener;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.utils.Utils;
import net.momirealms.antigrieflib.AbstractComp;
import net.momirealms.antigrieflib.CustomFlag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
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

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(com.bekvon.bukkit.residence.Residence.getInstance().getResidenceManager().getByLoc(entity.getLocation()))
                .map(claimedResidence -> claimedResidence.getPermissions().playerHas(player, Flags.build, true))
                .orElse(true);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return Optional.ofNullable(com.bekvon.bukkit.residence.Residence.getInstance().getResidenceManager().getByLoc(entity.getLocation()))
                .map(claimedResidence -> {
                    if (entity instanceof Player e) {
                        final boolean src = com.bekvon.bukkit.residence.Residence.getInstance().getPermsByLoc(player.getLocation()).has(Flags.pvp, FlagPermissions.FlagCombo.TrueOrNone);
                        final boolean target = com.bekvon.bukkit.residence.Residence.getInstance().getPermsByLoc(e.getLocation()).has(Flags.pvp, FlagPermissions.FlagCombo.TrueOrNone);
                        return src && target && player.getWorld().getPVP();
                    }
                    if (Utils.isAnimal(entity)) {
                        return claimedResidence.getPermissions().playerHas(player, Flags.animalkilling, true);
                    } else if (ResidenceEntityListener.isMonster(entity)) {
                        return claimedResidence.getPermissions().playerHas(player, Flags.mobkilling, true);
                    }
                    return null;
                })
                .orElse(true);
    }
}
