package net.momirealms.antigrieflib.comp;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import net.momirealms.antigrieflib.Flag;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GriefPreventionCompatibility extends AbstractAntiGriefCompatibility {

    public GriefPreventionCompatibility(Plugin plugin) {
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
        return checkPermission(player, location, ClaimPermission.Build);
    }

    private boolean canBreak(Player player, Location location) {
        return checkPermission(player, location, ClaimPermission.Build);
    }

    private boolean canInteract(Player player, Location location) {
        return checkPermission(player, location, ClaimPermission.Inventory);
    }

    private boolean canInteractEntity(Player player, Entity entity) {
        return checkPermission(player, entity.getLocation(), ClaimPermission.Inventory);
    }

    private boolean canDamageEntity(Player player, Entity entity) {
        return checkPermission(player, entity.getLocation(), ClaimPermission.Inventory);
    }

    private boolean canOpenContainer(Player player, Location location) {
        return checkPermission(player, location, ClaimPermission.Inventory);
    }

    private boolean checkPermission(Player player, Location location, ClaimPermission permission) {
        PlayerData playerData = GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId());
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, false, playerData.lastClaim);
        if (claim == null || playerData.ignoreClaims) return true;
        playerData.lastClaim = claim;
        return claim.checkPermission(player, permission, null) == null;
    }

    private boolean canOpenDoor(Player player, Location location) {
        return checkPermission(player, location, ClaimPermission.Build);
    }

    private boolean canUseButton(Player player, Location location) {
        return checkPermission(player, location, ClaimPermission.Build);
    }

    private boolean canUsePressurePlate(Player player, Location location) {
        return checkPermission(player, location, ClaimPermission.Build);
    }
}
