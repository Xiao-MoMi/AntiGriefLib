package net.momirealms.antigrieflib.comp;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import net.momirealms.antigrieflib.AbstractAntiGriefCompatibility;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GriefPreventionCompatibility extends AbstractAntiGriefCompatibility {

    public GriefPreventionCompatibility(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void init() {
    }

    @Override
    public boolean canPlace(Player player, Location location) {
        return checkPermission(player, location, ClaimPermission.Build);
    }

    @Override
    public boolean canBreak(Player player, Location location) {
        return checkPermission(player, location, ClaimPermission.Build);
    }

    @Override
    public boolean canInteract(Player player, Location location) {
        return checkPermission(player, location, ClaimPermission.Inventory);
    }

    @Override
    public boolean canInteractEntity(Player player, Entity entity) {
        return checkPermission(player, entity.getLocation(), ClaimPermission.Inventory);
    }

    @Override
    public boolean canDamage(Player player, Entity entity) {
        return checkPermission(player, entity.getLocation(), ClaimPermission.Inventory);
    }

    private boolean checkPermission(Player player, Location location, ClaimPermission permission) {
        PlayerData playerData = GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId());
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, false, playerData.lastClaim);
        if (claim == null || playerData.ignoreClaims) return true;
        playerData.lastClaim = claim;
        return claim.checkPermission(player, permission, null) == null;
    }
}
