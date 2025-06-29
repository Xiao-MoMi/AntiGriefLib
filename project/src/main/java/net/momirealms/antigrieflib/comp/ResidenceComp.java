package net.momirealms.antigrieflib.comp;

import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.listeners.ResidenceEntityListener;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.utils.Utils;
import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class ResidenceComp extends AbstractComp {

    public ResidenceComp(JavaPlugin plugin) {
        super(plugin, "Residence");
    }

    @Override
    public void init() {
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return Optional.ofNullable(com.bekvon.bukkit.residence.Residence.getInstance().getResidenceManager().getByLoc(location))
                .map(claimedResidence -> {
                    boolean canBuild = claimedResidence.getPermissions().playerHas(player, Flags.build, false);
                    boolean canPlace = claimedResidence.getPermissions().playerHas(player, Flags.place, canBuild);
                    if (canBuild && !canPlace) {
                        return false;
                    }
                    return canBuild || canPlace;
                })
                .orElse(true);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return Optional.ofNullable(com.bekvon.bukkit.residence.Residence.getInstance().getResidenceManager().getByLoc(location))
                .map(claimedResidence -> {
                    boolean canBuild = claimedResidence.getPermissions().playerHas(player, Flags.build, false);
                    boolean canDestroy = claimedResidence.getPermissions().playerHas(player, Flags.destroy, canBuild);
                    if (canBuild && !canDestroy) {
                        return false;
                    }
                    return canBuild || canDestroy;
                })
                .orElse(true);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return Optional.ofNullable(com.bekvon.bukkit.residence.Residence.getInstance().getResidenceManager().getByLoc(location))
                .map(claimedResidence -> {
                    boolean canBuild = claimedResidence.getPermissions().playerHas(player, Flags.build, false);
                    boolean canUse = claimedResidence.getPermissions().playerHas(player, Flags.use, canBuild);
                    if (canBuild && !canUse) {
                        return false;
                    }
                    return canBuild || canUse;
                })
                .orElse(true);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(com.bekvon.bukkit.residence.Residence.getInstance().getResidenceManager().getByLoc(entity.getLocation()))
                .map(claimedResidence -> claimedResidence.getPermissions().playerHas(player, Flags.build, false))
                .orElse(true);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return Optional.ofNullable(com.bekvon.bukkit.residence.Residence.getInstance().getResidenceManager().getByLoc(entity.getLocation()))
                .map(claimedResidence -> {
                    if (entity instanceof Player e) {
                        final boolean src = com.bekvon.bukkit.residence.Residence.getInstance().getPermsByLoc(player.getLocation()).has(Flags.pvp, FlagPermissions.FlagCombo.TrueOrNone);
                        final boolean target = com.bekvon.bukkit.residence.Residence.getInstance().getPermsByLoc(e.getLocation()).has(Flags.pvp, FlagPermissions.FlagCombo.TrueOrNone);
                        return src && target;
                    }
                    if (Utils.isAnimal(entity)) {
                        return claimedResidence.getPermissions().playerHas(player, Flags.animalkilling, false);
                    } else if (ResidenceEntityListener.isMonster(entity)) {
                        return claimedResidence.getPermissions().playerHas(player, Flags.mobkilling, false);
                    } else {
                        return claimedResidence.getPermissions().playerHas(player, Flags.damage, false);
                    }
                })
                .orElse(true);
    }
}