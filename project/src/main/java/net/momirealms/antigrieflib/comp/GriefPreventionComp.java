package net.momirealms.antigrieflib.comp;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import net.momirealms.antigrieflib.AbstractComp;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GriefPreventionComp extends AbstractComp {

    public GriefPreventionComp(JavaPlugin plugin) {
        super(plugin, "GriefPrevention");
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

    private boolean checkPermission(Player player, Location location, ClaimPermission permission) {
        PlayerData playerData = GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId());
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, false, playerData.lastClaim);
        if (claim == null || playerData.ignoreClaims) return true;
        playerData.lastClaim = claim;
        return claim.checkPermission(player, permission, null) == null;
    }
}
