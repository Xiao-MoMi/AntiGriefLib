package net.momirealms.antigrieflib.comp;

import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.listeners.ResidenceEntityListener;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.utils.Utils;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class ResidenceCompatibility extends AbstractAntiGriefCompatibility {

    public ResidenceCompatibility(Plugin plugin) {
        super(plugin);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void init() {
        registerFlagTester(Flag.PLACE, this::canPlace);
        registerFlagTester(Flag.BREAK, this::canBreak);
        registerFlagTester(Flag.INTERACT, this::canInteract);
        registerFlagTester(Flag.INTERACT_ENTITY, this::canInteractEntity);
        registerFlagTester(Flag.DAMAGE_ENTITY, this::canDamageEntity);
        registerFlagTester(Flag.OPEN_CONTAINER, this::canOpenContainer);
        registerFlagTester(Flag.OPEN_DOOR, this::canOpenDoor);
        registerFlagTester(Flag.USE_BUTTON, this::canUseButton);
        registerFlagTester(Flag.USE_PRESSURE_PLATE, this::canUsePressurePlate);
    }

    private boolean canPlace(Player player, Location location) {
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

    private boolean canBreak(Player player, Location location) {
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

    private boolean canInteract(Player player, Location location) {
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

    private boolean canInteractEntity(Player player, Entity entity) {
        return Optional.ofNullable(com.bekvon.bukkit.residence.Residence.getInstance().getResidenceManager().getByLoc(entity.getLocation()))
                .map(claimedResidence -> claimedResidence.getPermissions().playerHas(player, Flags.build, false))
                .orElse(true);
    }

    private boolean canDamageEntity(Player player, Entity entity) {
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

    private boolean canOpenContainer(Player player, Location location) {
        return Optional.ofNullable(com.bekvon.bukkit.residence.Residence.getInstance().getResidenceManager().getByLoc(location))
                .map(claimedResidence -> claimedResidence.getPermissions().playerHas(player, Flags.container, false))
                .orElse(true);
    }

    private boolean canOpenDoor(Player player, Location location) {
        return Optional.ofNullable(com.bekvon.bukkit.residence.Residence.getInstance().getResidenceManager().getByLoc(location))
                .map(claimedResidence -> claimedResidence.getPermissions().playerHas(player, Flags.door, false))
                .orElse(true);
    }

    private boolean canUseButton(Player player, Location location) {
        return Optional.ofNullable(com.bekvon.bukkit.residence.Residence.getInstance().getResidenceManager().getByLoc(location))
                .map(claimedResidence -> claimedResidence.getPermissions().playerHas(player, Flags.button, false))
                .orElse(true);
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        return Optional.ofNullable(com.bekvon.bukkit.residence.Residence.getInstance().getResidenceManager().getByLoc(location))
                .map(claimedResidence -> claimedResidence.getPermissions().playerHas(player, Flags.pressure, false))
                .orElse(true);
    }
}